package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.Faktura;

public class Fakture extends Controller{ 

	public static void show(String mode) {	
		session.put("mode", "edit");
	    mode = session.get("mode");

		List<PoslovnaGodina> poslovneGodine = PoslovneGodine.checkCache();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartneri.checkCache();
		List<Faktura> fakture = checkCache();

		render(mode, fakture, poslovneGodine, poslovniPartneri);
	}

	public static void create(Faktura faktura,Long poslovnaGodina,Long poslovniPartner) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Faktura> fakture = null;
		List<PoslovnaGodina> poslovneGodine = PoslovneGodine.checkCache();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartneri.checkCache();

		fakture = Faktura.findAll();

		PoslovnaGodina findPoslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		faktura.poslovnaGodina = findPoslovnaGodina;
		PoslovniPartner findPoslovniPartner = PoslovniPartner.findById(poslovniPartner);
		faktura.poslovniPartner = findPoslovniPartner;

		faktura.save();
		fakture.add(faktura);
		Cache.set("fakture", fakture);

		Long idd = faktura.id;

		fakture.clear();
		fakture = Faktura.findAll();

		renderTemplate("Fakture/show.html", idd, mode, fakture, poslovneGodine, poslovniPartneri);
		
	}
	
	public static void edit(Faktura faktura,Long poslovnaGodina,Long poslovniPartner) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Faktura> fakture = null;
		List<PoslovnaGodina> poslovneGodine = PoslovneGodine.checkCache();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartneri.checkCache();

	
		fakture  = Faktura.findAll();

		PoslovnaGodina findPoslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		faktura.poslovnaGodina = findPoslovnaGodina;
		PoslovniPartner findPoslovniPartner = PoslovniPartner.findById(poslovniPartner);
		faktura.poslovniPartner = findPoslovniPartner;


		for (Faktura tmp : fakture ) {
			if (tmp.id == faktura.id) {
				tmp.poslovnaGodina = findPoslovnaGodina;
				tmp.poslovniPartner = findPoslovniPartner;
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

		Cache.set("fakture ", fakture );

		fakture.clear();
		fakture = Faktura.findAll();
			
		renderTemplate("Fakture/show.html", mode, fakture, poslovneGodine, poslovniPartneri);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<PoslovnaGodina> poslovneGodine = PoslovneGodine.checkCache();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartneri.checkCache();
		List<Faktura> fakture = checkCache();

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
		Cache.set("fakture", fakture);

		renderTemplate("Fakture/show.html", idd, mode, fakture, poslovneGodine, poslovniPartneri);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<Faktura> checkCache() {
		List<Faktura> fakture = (List<Faktura>) Cache.get("fakture");

		if ((fakture == null) || (fakture.size() == 0)) {
			fakture = Faktura.findAll();
			Cache.set("fakture", fakture);
		}

		return fakture;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<PoslovnaGodina> poslovneGodine = PoslovneGodine.checkCache();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartneri.checkCache();
		List<Faktura> fakture = checkCache();

		renderTemplate("Fakture/show.html", fakture, mode, poslovneGodine, poslovniPartneri);
	}
	
	
	
	
	
	
	
	
	
	
}