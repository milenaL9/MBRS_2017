package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.Preduzece;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import models.Cenovnik;
import models.StavkaCenovnika;
import models.StavkaFakture;
import models.Artikal;


import models.Faktura;

public class Fakture extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		List<Faktura> fakture = Faktura.findAll();


		render(mode, fakture, poslovneGodine, poslovniPartneri, preduzeca);
	}
 
	public static void create(Faktura faktura,Long poslovnaGodina,Long poslovniPartner,Long preduzece) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Faktura> fakture = null;
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();

		fakture = Faktura.findAll();

		PoslovnaGodina findPoslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		faktura.poslovnaGodina = findPoslovnaGodina;
		PoslovniPartner findPoslovniPartner = PoslovniPartner.findById(poslovniPartner);
		faktura.poslovniPartner = findPoslovniPartner;
		Preduzece findPreduzece = Preduzece.findById(preduzece);
		faktura.preduzece = findPreduzece;

		
		// Postavljanje fakture
		faktura = setUpFaktura(faktura);
		
		// Postavljanje stavke fakture
		
		faktura.save();
		fakture.add(faktura);
		Long idd = faktura.id;
		fakture.clear();
		fakture = Faktura.findAll();
		
		// Za Stavku Fakture

		// Poziv pomocnih metoda za Fakturu
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = findStavkeCenovnika(idd);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		List<StavkaFakture> stavkeFakture = findStavkeFakture(idd);
		List<Artikal> artikli = Artikal.findAll();
		session.put("idFakture", faktura.id);
		renderTemplate("StavkeFakture/show.html", stavkeFakture, stavkeCenovnika, idd, mode, artikli, fakture, poslovneGodine, poslovniPartneri, preduzeca);
	}
		 
	public static void edit(Faktura faktura,Long poslovnaGodina,Long poslovniPartner,Long preduzece) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Faktura> fakture = null;
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();

		fakture  = Faktura.findAll();

		PoslovnaGodina findPoslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		faktura.poslovnaGodina = findPoslovnaGodina;
		PoslovniPartner findPoslovniPartner = PoslovniPartner.findById(poslovniPartner);
		faktura.poslovniPartner = findPoslovniPartner;
		Preduzece findPreduzece = Preduzece.findById(preduzece);
		faktura.preduzece = findPreduzece;


		for (Faktura tmp : fakture ) {
			if (tmp.id == faktura.id) {
				tmp.poslovnaGodina = findPoslovnaGodina;
				tmp.poslovniPartner = findPoslovniPartner;
				tmp.preduzece = findPreduzece;
				tmp.datumFakture = faktura.datumFakture;
				tmp.brojFakture = faktura.brojFakture;
				tmp.datumValute = faktura.datumValute;
				tmp.ukupnoOsnovica = faktura.ukupnoOsnovica;
				tmp.ukupnoPDV = faktura.ukupnoPDV;
				tmp.ukupnoZaPlacanje = faktura.ukupnoZaPlacanje;
				tmp.save();
				break;
			}
		}
	
		renderTemplate("Fakture/show.html", mode, fakture, poslovneGodine, poslovniPartneri, preduzeca);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		List<Faktura> fakture = Faktura.findAll();

		Faktura faktura = Faktura.findById(id);
		Long idd = null;

		for (int i = 1; i < fakture.size(); i++) {
			if (fakture.get(i).id == id) {
				Faktura prethodni = fakture.get(i - 1);
				idd = prethodni.id;
			}
		}
		faktura.delete();

		fakture.clear();
		fakture = Faktura.findAll();

		renderTemplate("Fakture/show.html", idd, mode, fakture, poslovneGodine, poslovniPartneri, preduzeca);
	}
	
	public static int incrementBrojFakture() {
		List<Faktura> fakture = Faktura.findAll();
		int brojFakture = 0;
		if (fakture.size() > 0) {
			brojFakture = fakture.get(fakture.size() - 1).brojFakture;
			brojFakture++;
		} else {
			brojFakture = 1;
		}

		return brojFakture;
	}
		
	public static Faktura setUpFaktura(Faktura faktura){
		faktura.brojFakture = incrementBrojFakture();
		List<StavkaFakture> stavkeFakture = faktura.stavkeFakture;
		faktura.ukupnoOsnovica = 0;
		faktura.ukupnoPDV = 0;
		faktura.ukupnoZaPlacanje = 0;
		if (stavkeFakture != null) {
			for (StavkaFakture sf : stavkeFakture) {
				faktura.ukupnoOsnovica += sf.osnovicaZaPDV;
				faktura.ukupnoPDV += sf.iznosPDVa;
				faktura.ukupnoZaPlacanje += sf.ukupno;
			}
 
		}
		
		return faktura;
	}
	
	public static Date convertToDate(String receivedDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = formatter.parse(receivedDate);
		return date;
	}
	
	public static List<StavkaCenovnika> findStavkeCenovnika(Long idFakture) throws ParseException {
		Faktura faktura = Faktura.findById(idFakture);
		String datumFakture = faktura.datumFakture;
		Date datumFaktureDate = convertToDate(datumFakture);

		List<Cenovnik> cenovniciSaDatumima = new ArrayList<>();
		List<Cenovnik> cenovnici = Cenovnik.findAll();
		for (Cenovnik tmp : cenovnici) {
			String datumCenovnika = tmp.datumVazenja;
			Date datumCenovnikaDate = convertToDate(datumCenovnika);

			if (!datumCenovnikaDate.after(datumFaktureDate)) {
				cenovniciSaDatumima.add(tmp);
			}
		}

		List<Date> datumi = new ArrayList<>();
		// trazim cenovnik sa najvisim datumom
		for (Cenovnik tmp : cenovniciSaDatumima) {
			Date d = convertToDate(tmp.datumVazenja);
			datumi.add(d);
		}

		Collections.sort(datumi, new Comparator<Date>() {
			@Override
			public int compare(Date arg0, Date arg1) {
				return arg0.compareTo(arg1);
			}
		});

		// trazim stavke cenovnika
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<>();
		for (Cenovnik tmp : cenovniciSaDatumima) {
			String string = new SimpleDateFormat("MM/dd/yyyy").format(datumi.get(datumi.size() - 1));
			if (tmp.datumVazenja.equals(string)) {
				stavkeCenovnika = tmp.stavkeCenovnika;
			}
		}

		return stavkeCenovnika;
	}
	
	public static List<StavkaFakture> findStavkeFakture(Long idFakture) {
		List<StavkaFakture> stavkeFaktureAll = StavkaFakture.findAll();
		List<StavkaFakture> stavkeFakture = new ArrayList<>();

		for (StavkaFakture sc : stavkeFaktureAll) {
			if (sc.faktura.id == idFakture) {
				stavkeFakture.add(sc);
			}
		}

		return stavkeFakture;
	}
	
	
	
	
	
}