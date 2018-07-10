package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Podgrupa;
import models.Artikal;

public class Artikli extends Controller{ 

	public static void show() {	
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = Podgrupe.checkCache();
		List<Artikal> artikli = checkCache();

		render(mode, artikli, podgrupe);
	}

	public static void create(Artikal artikal,Long podgrupa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Artikal> artikli = null;
		List<Podgrupa> podgrupe = Podgrupe.checkCache();

		artikli = Artikal.findAll();

		Podgrupa findPodgrupa = Podgrupa.findById(podgrupa);
		artikal.podgrupa = findPodgrupa;

		artikal.save();
		artikli.add(artikal);
		Cache.set("artikli", artikli);

		Long idd = artikal.id;

		artikli.clear();
		artikli = Artikal.findAll();

		renderTemplate("Artikli/show.html", idd, mode, artikli, podgrupe);
		
	}
	
	public static void edit(Artikal artikal,Long podgrupa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Artikal> artikli = null;
		List<Podgrupa> podgrupe = Podgrupe.checkCache();

	
		artikli  = Artikal.findAll();

		Podgrupa findPodgrupa = Podgrupa.findById(podgrupa);
		artikal.podgrupa = findPodgrupa;


		for (Artikal tmp : artikli ) {
			if (tmp.id == artikal.id) {
				tmp.podgrupa = findPodgrupa;
				tmp.nazivStavkeKataloga = artikal.nazivStavkeKataloga;
				tmp.opisStavkeKataloga = artikal.opisStavkeKataloga;
				tmp.save();
				break;
			}
		}

		Cache.set("artikli ", artikli );

		artikli.clear();
		artikli = Artikal.findAll();
			
		renderTemplate("Artikli/show.html", mode, artikli, podgrupe);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = Podgrupe.checkCache();
		List<Artikal> artikli = checkCache();

		Artikal artikal = Artikal.findById(id);
		Long idd = null;

		for (int i = 1; i < artikli.size(); i++) {
			if (artikli.get(i).id == id) {
				Artikal prethodni = artikli.get(i - 1);
				idd = prethodni.id;
			}
		}
		artikal.delete();

		artikli.clear();
		artikli = Artikal.findAll();
		Cache.set("artikli", artikli);

		renderTemplate("Artikli/show.html", idd, mode, artikli, podgrupe);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<Artikal> checkCache() {
		List<Artikal> artikli = (List<Artikal>) Cache.get("artikli");

		if ((artikli == null) || (artikli.size() == 0)) {
			artikli = Artikal.findAll();
			Cache.set("artikli", artikli);
		}

		return artikli;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Podgrupa> podgrupe = Podgrupe.checkCache();
		List<Artikal> artikli = checkCache();

		renderTemplate("Artikli/show.html", artikli, mode, podgrupe);
	}
	
	
	
	
	
	
	
	
	
	
}