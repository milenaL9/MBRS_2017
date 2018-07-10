package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Grupa;
import models.Podgrupa;

public class Podgrupe extends Controller{ 

	public static void show() {	
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Grupa> grupe = Grupe.checkCache();
		List<Podgrupa> podgrupe = checkCache();

		render(mode, podgrupe, grupe);
	}

	public static void create(Podgrupa podgrupa,Long grupa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = null;
		List<Grupa> grupe = Grupe.checkCache();

		podgrupe = Podgrupa.findAll();

		Grupa findGrupa = Grupa.findById(grupa);
		podgrupa.grupa = findGrupa;

		podgrupa.save();
		podgrupe.add(podgrupa);
		Cache.set("podgrupe", podgrupe);

		Long idd = podgrupa.id;

		podgrupe.clear();
		podgrupe = Podgrupa.findAll();

		renderTemplate("Podgrupe/show.html", idd, mode, podgrupe, grupe);
		
	}
	
	public static void edit(Podgrupa podgrupa,Long grupa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = null;
		List<Grupa> grupe = Grupe.checkCache();

	
		podgrupe  = Podgrupa.findAll();

		Grupa findGrupa = Grupa.findById(grupa);
		podgrupa.grupa = findGrupa;


		for (Podgrupa tmp : podgrupe ) {
			if (tmp.id == podgrupa.id) {
				tmp.grupa = findGrupa;
				tmp.nazivPodgrupe = podgrupa.nazivPodgrupe;
				tmp.save();
				break;
			}
		}

		Cache.set("podgrupe ", podgrupe );

		podgrupe.clear();
		podgrupe = Podgrupa.findAll();
			
		renderTemplate("Podgrupe/show.html", mode, podgrupe, grupe);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Grupa> grupe = Grupe.checkCache();
		List<Podgrupa> podgrupe = checkCache();

		Podgrupa podgrupa = Podgrupa.findById(id);
		Long idd = null;

		for (int i = 1; i < podgrupe.size(); i++) {
			if (podgrupe.get(i).id == id) {
				Podgrupa prethodni = podgrupe.get(i - 1);
				idd = prethodni.id;
			}
		}
		podgrupa.delete();

		podgrupe.clear();
		podgrupe = Podgrupa.findAll();
		Cache.set("podgrupe", podgrupe);

		renderTemplate("Podgrupe/show.html", idd, mode, podgrupe, grupe);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<Podgrupa> checkCache() {
		List<Podgrupa> podgrupe = (List<Podgrupa>) Cache.get("podgrupe");

		if ((podgrupe == null) || (podgrupe.size() == 0)) {
			podgrupe = Podgrupa.findAll();
			Cache.set("podgrupe", podgrupe);
		}

		return podgrupe;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Grupa> grupe = Grupe.checkCache();
		List<Podgrupa> podgrupe = checkCache();

		renderTemplate("Podgrupe/show.html", podgrupe, mode, grupe);
	}
	
	
	
	
	
	
	
	
	
	
}