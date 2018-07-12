package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Artikal;
import models.Faktura;
import models.StavkaFakture;

public class StavkeFakture extends Controller{ 

	public static void show(String mode) {	
		session.put("mode", "edit");
	    mode = session.get("mode");

		List<Artikal> artikli = Artikli.checkCache();
		List<Faktura> fakture = Fakture.checkCache();
		List<StavkaFakture> stavkeFakture = checkCache();

		render(mode, stavkeFakture, artikli, fakture);
	}

	public static void create(StavkaFakture stavkaFakture,Long artikal,Long faktura) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<StavkaFakture> stavkeFakture = null;
		List<Artikal> artikli = Artikli.checkCache();
		List<Faktura> fakture = Fakture.checkCache();

		stavkeFakture = StavkaFakture.findAll();

		Artikal findArtikal = Artikal.findById(artikal);
		stavkaFakture.artikal = findArtikal;
		Faktura findFaktura = Faktura.findById(faktura);
		stavkaFakture.faktura = findFaktura;

		stavkaFakture.save();
		stavkeFakture.add(stavkaFakture);
		Cache.set("stavkeFakture", stavkeFakture);

		Long idd = stavkaFakture.id;

		stavkeFakture.clear();
		stavkeFakture = StavkaFakture.findAll();

		renderTemplate("StavkeFakture/show.html", idd, mode, stavkeFakture, artikli, fakture);
		
	}
	
	public static void edit(StavkaFakture stavkaFakture,Long artikal,Long faktura) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<StavkaFakture> stavkeFakture = null;
		List<Artikal> artikli = Artikli.checkCache();
		List<Faktura> fakture = Fakture.checkCache();

	
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
				tmp.save();
				break;
			}
		}

		Cache.set("stavkeFakture ", stavkeFakture );

		stavkeFakture.clear();
		stavkeFakture = StavkaFakture.findAll();
			
		renderTemplate("StavkeFakture/show.html", mode, stavkeFakture, artikli, fakture);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Artikal> artikli = Artikli.checkCache();
		List<Faktura> fakture = Fakture.checkCache();
		List<StavkaFakture> stavkeFakture = checkCache();

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
		Cache.set("stavkeFakture", stavkeFakture);

		renderTemplate("StavkeFakture/show.html", idd, mode, stavkeFakture, artikli, fakture);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<StavkaFakture> checkCache() {
		List<StavkaFakture> stavkeFakture = (List<StavkaFakture>) Cache.get("stavkeFakture");

		if ((stavkeFakture == null) || (stavkeFakture.size() == 0)) {
			stavkeFakture = StavkaFakture.findAll();
			Cache.set("stavkeFakture", stavkeFakture);
		}

		return stavkeFakture;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Artikal> artikli = Artikli.checkCache();
		List<Faktura> fakture = Fakture.checkCache();
		List<StavkaFakture> stavkeFakture = checkCache();

		renderTemplate("StavkeFakture/show.html", stavkeFakture, mode, artikli, fakture);
	}
	
	
	
	
	
	
	
	
	
	
}