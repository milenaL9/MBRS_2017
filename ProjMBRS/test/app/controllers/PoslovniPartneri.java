package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Preduzece;
import models.PoslovniPartner;

public class PoslovniPartneri extends Controller{ 

	public static void show(String mode) {	
		session.put("mode", "edit");
	    mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<PoslovniPartner> poslovniPartneri = checkCache();

		render(mode, poslovniPartneri, preduzeca);
	}

	public static void create(PoslovniPartner poslovniPartner,Long preduzece) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<PoslovniPartner> poslovniPartneri = null;
		List<Preduzece> preduzeca = Preduzeca.checkCache();

		poslovniPartneri = PoslovniPartner.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		poslovniPartner.preduzece = findPreduzece;

		poslovniPartner.save();
		poslovniPartneri.add(poslovniPartner);
		Cache.set("poslovniPartneri", poslovniPartneri);

		Long idd = poslovniPartner.id;

		poslovniPartneri.clear();
		poslovniPartneri = PoslovniPartner.findAll();

		renderTemplate("PoslovniPartneri/show.html", idd, mode, poslovniPartneri, preduzeca);
		
	}
	
	public static void edit(PoslovniPartner poslovniPartner,Long preduzece) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<PoslovniPartner> poslovniPartneri = null;
		List<Preduzece> preduzeca = Preduzeca.checkCache();

	
		poslovniPartneri  = PoslovniPartner.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		poslovniPartner.preduzece = findPreduzece;


		for (PoslovniPartner tmp : poslovniPartneri ) {
			if (tmp.id == poslovniPartner.id) {
				tmp.preduzece = findPreduzece;
				tmp.naziv = poslovniPartner.naziv;
				tmp.mesto = poslovniPartner.mesto;
				tmp.adresa = poslovniPartner.adresa;
				tmp.vrsta = poslovniPartner.vrsta;
				tmp.telefon = poslovniPartner.telefon;
				tmp.pib = poslovniPartner.pib;
				tmp.tekuciRacun = poslovniPartner.tekuciRacun;
				tmp.save();
				break;
			}
		}

		Cache.set("poslovniPartneri ", poslovniPartneri );

		poslovniPartneri.clear();
		poslovniPartneri = PoslovniPartner.findAll();
			
		renderTemplate("PoslovniPartneri/show.html", mode, poslovniPartneri, preduzeca);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<PoslovniPartner> poslovniPartneri = checkCache();

		PoslovniPartner poslovniPartner = PoslovniPartner.findById(id);
		Long idd = null;

		for (int i = 1; i < poslovniPartneri.size(); i++) {
			if (poslovniPartneri.get(i).id == id) {
				PoslovniPartner prethodni = poslovniPartneri.get(i - 1);
				idd = prethodni.id;
			}
		}
		poslovniPartner.delete();

		poslovniPartneri.clear();
		poslovniPartneri = PoslovniPartner.findAll();
		Cache.set("poslovniPartneri", poslovniPartneri);

		renderTemplate("PoslovniPartneri/show.html", idd, mode, poslovniPartneri, preduzeca);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<PoslovniPartner> checkCache() {
		List<PoslovniPartner> poslovniPartneri = (List<PoslovniPartner>) Cache.get("poslovniPartneri");

		if ((poslovniPartneri == null) || (poslovniPartneri.size() == 0)) {
			poslovniPartneri = PoslovniPartner.findAll();
			Cache.set("poslovniPartneri", poslovniPartneri);
		}

		return poslovniPartneri;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<PoslovniPartner> poslovniPartneri = checkCache();

		renderTemplate("PoslovniPartneri/show.html", poslovniPartneri, mode, preduzeca);
	}
	
	
	
	
	
	
	
	
	
	
}