package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Artikal;
import models.Faktura;

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

import models.StopaPDVa;
import models.VrstaPDVa;
import controllers.Fakture;

import models.StavkaFakture;

public class StavkeFakture extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Artikal> artikli = Artikal.findAll();
		List<Faktura> fakture = Faktura.findAll();
		List<StavkaFakture> stavkeFakture = StavkaFakture.findAll();

		stavkeFakture.clear();
		Long idFak = Long.parseLong(session.get("idFakture"));
		stavkeFakture = Fakture.findStavkeFakture(idFak);

		render(mode, stavkeFakture, artikli, fakture);
	}
 
	public static void create(StavkaFakture stavkaFakture,Long artikal,Long faktura) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<StavkaFakture> stavkeFakture = null;
		List<Artikal> artikli = Artikal.findAll();
		List<Faktura> fakture = Faktura.findAll();

		stavkeFakture = StavkaFakture.findAll();

		Artikal findArtikal = Artikal.findById(artikal);
		stavkaFakture.artikal = findArtikal;
		Faktura findFaktura = Faktura.findById(faktura);
		stavkaFakture.faktura = findFaktura;

		
		
		// Postavljanje stavke fakture
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = fillListStavkeCenovnika();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (StavkaCenovnika sc : stavkeCenovnika) {
			if (sc.artikal.id == stavkaFakture.artikal.id) {
				stavkaFakture.cena = (float) sc.cena;
			}
		}
		
		
		stavkaFakture = setUpStavkaFakture(stavkaFakture);
		
		try {
			stavkaFakture.stopaPDVa = findStopaPDVa(findFaktura.id,
					stavkaFakture.artikal.podgrupa.grupa.vrstaPDVa).procenatPDVa;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stavkaFakture.faktura = findFaktura;
		

		stavkaFakture.save();
		stavkeFakture.add(stavkaFakture);

		Long idd = stavkaFakture.id;

		stavkeFakture.clear();
		stavkeFakture = StavkaFakture.findAll();
		
		
		// Za Stavku Fakture
		stavkeFakture.clear();
		stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = fillListStavkeCenovnika();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		// Poziv za Stavku fakture
		stavkeFakture.clear();
		Long idFak = Long.parseLong(session.get("idFakture"));
		stavkeFakture = Fakture.findStavkeFakture(idFak);
		renderTemplate("StavkeFakture/show.html", stavkeFakture, fakture, artikli, idd,
					mode, stavkeCenovnika);
		
		

	}
		 
	public static void edit(StavkaFakture stavkaFakture,Long artikal,Long faktura) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<StavkaFakture> stavkeFakture = null;
		List<Artikal> artikli = Artikal.findAll();
		List<Faktura> fakture = Faktura.findAll();

	
		stavkeFakture  = StavkaFakture.findAll();

		Artikal findArtikal = Artikal.findById(artikal);
		stavkaFakture.artikal = findArtikal;
		Faktura findFaktura = Faktura.findById(faktura);
		stavkaFakture.faktura = findFaktura;


		for (StavkaFakture tmp : stavkeFakture ) {
			if (tmp.id == stavkaFakture.id) {
				tmp.artikal = findArtikal;
				tmp.faktura = findFaktura;
				tmp.kolicina = stavkaFakture.kolicina;
				tmp.cena = stavkaFakture.cena;
				tmp.rabat = stavkaFakture.rabat;
				tmp.osnovicaZaPDV = stavkaFakture.osnovicaZaPDV;
				tmp.stopaPDVa = stavkaFakture.stopaPDVa;
				tmp.ukupno = stavkaFakture.ukupno;
				tmp.iznosPDVa = stavkaFakture.iznosPDVa;
				tmp.iznosRabata = stavkaFakture.iznosRabata;
				tmp.vrednost = stavkaFakture.vrednost;
				tmp.save();
				break;
			}
		}
	
		renderTemplate("StavkeFakture/show.html", mode, stavkeFakture, artikli, fakture);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Artikal> artikli = Artikal.findAll();
		List<Faktura> fakture = Faktura.findAll();
		List<StavkaFakture> stavkeFakture = StavkaFakture.findAll();

		StavkaFakture stavkaFakture = StavkaFakture.findById(id);
		Long idd = null;

		for (int i = 1; i < stavkeFakture.size(); i++) {
			if (stavkeFakture.get(i).id == id) {
				StavkaFakture prethodni = stavkeFakture.get(i - 1);
				idd = prethodni.id;
			}
		}
		stavkaFakture.delete();

		stavkeFakture.clear();
		stavkeFakture = StavkaFakture.findAll();

		renderTemplate("StavkeFakture/show.html", idd, mode, stavkeFakture, artikli, fakture);
	}
	
		
	
	
	
	
	public static void saveStavke() {
		Long id = Long.parseLong(session.get("idFakture"));
		Faktura faktura = Faktura.findById(id);

		if (faktura.stavkeFakture.size() == 0) {
			Fakture.delete(id);
		} else {
			Fakture.show("edit");
		}
	}	
	
	public static List<StavkaCenovnika> fillListStavkeCenovnika() throws ParseException {
		List<StavkaCenovnika> stavkeCenovnika = null;

		if (!session.get("idFakture").equals("null")) {
			Long id = Long.parseLong(session.get("idFakture"));
			stavkeCenovnika = Fakture.findStavkeCenovnika(id);
		}

		return stavkeCenovnika;
	}
	
	public static StopaPDVa findStopaPDVa(Long idFakture, VrstaPDVa vrstaPDVa) throws ParseException {
		Faktura faktura = Faktura.findById(idFakture);
		String datumFakture = faktura.datumFakture;
		Date datumFaktureDate = Fakture.convertToDate(datumFakture);

		List<StopaPDVa> stopePDVaSaDatumima = new ArrayList<>();
		List<StopaPDVa> stopePDVa = StopaPDVa.findAll();
		for (StopaPDVa tmp : stopePDVa) {
			String datumStopePDVa = tmp.datumKreiranja;
			Date datumStopePDVaDate = Fakture.convertToDate(datumStopePDVa);

			if (!datumStopePDVaDate.after(datumFaktureDate) && (tmp.vrstaPDVa.id == vrstaPDVa.id)) {
				stopePDVaSaDatumima.add(tmp);
			}
		}

		List<Date> datumi = new ArrayList<>();
		for (StopaPDVa tmp : stopePDVaSaDatumima) {
			Date d = Fakture.convertToDate(tmp.datumKreiranja);
			datumi.add(d);
		}

		Collections.sort(datumi, new Comparator<Date>() {
			@Override
			public int compare(Date arg0, Date arg1) {
				return arg0.compareTo(arg1);
			}
		});

		// trazim stopuPDVa
		StopaPDVa stopaPDVa = null;
		for (StopaPDVa tmp : stopePDVaSaDatumima) {
			String string = new SimpleDateFormat("MM/dd/yyyy").format(datumi.get(datumi.size() - 1));
			if (tmp.datumKreiranja.equals(string)) {
				stopaPDVa = tmp;
			}
		}

		return stopaPDVa;
	}
	
	public static StavkaFakture setUpStavkaFakture(StavkaFakture stavkaFakture){
		// kada je disable- ovan combobox ne pokupi vrednost
		/*Faktura findFaktura = null;
		if (faktura == null) {
			Long id = Long.parseLong(session.get("idFakture"));
			findFaktura = Faktura.findById(id);
		} else {
			findFaktura = Faktura.findById(faktura);
		}*/
		
		stavkaFakture.vrednost = stavkaFakture.cena * stavkaFakture.kolicina;
		stavkaFakture.iznosRabata = stavkaFakture.vrednost * (stavkaFakture.rabat / 100);
		stavkaFakture.osnovicaZaPDV = stavkaFakture.vrednost - stavkaFakture.iznosRabata;

		
		stavkaFakture.iznosPDVa = (stavkaFakture.osnovicaZaPDV * stavkaFakture.stopaPDVa) / 100;
		stavkaFakture.ukupno = stavkaFakture.vrednost - stavkaFakture.iznosRabata + stavkaFakture.iznosPDVa;

		stavkaFakture.faktura.ukupnoPDV += stavkaFakture.iznosPDVa;
		stavkaFakture.faktura.ukupnoZaPlacanje += stavkaFakture.ukupno;
		stavkaFakture.faktura.ukupnoOsnovica += stavkaFakture.osnovicaZaPDV;

		stavkaFakture.faktura.save();
		
		return stavkaFakture;
	}
	
}