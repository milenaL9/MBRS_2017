package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Preduzece;
import models.VrstaPDVa;
import models.Grupa;

public class Grupe extends Controller{ 

	public static void show(String mode) {	
		session.put("mode", "edit");
	    mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<Grupa> grupe = checkCache();

		render(mode, grupe, preduzeca, vrstePDVa);
	}

	public static void create(Grupa grupa,Long preduzece,Long vrstaPDVa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Grupa> grupe = null;
		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();

		grupe = Grupa.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		grupa.preduzece = findPreduzece;
		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		grupa.vrstaPDVa = findVrstaPDVa;

		grupa.save();
		grupe.add(grupa);
		Cache.set("grupe", grupe);

		Long idd = grupa.id;

		grupe.clear();
		grupe = Grupa.findAll();

		renderTemplate("Grupe/show.html", idd, mode, grupe, preduzeca, vrstePDVa);
		
	}
	
	public static void edit(Grupa grupa,Long preduzece,Long vrstaPDVa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Grupa> grupe = null;
		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();

	
		grupe  = Grupa.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		grupa.preduzece = findPreduzece;
		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		grupa.vrstaPDVa = findVrstaPDVa;


		for (Grupa tmp : grupe ) {
			if (tmp.id == grupa.id) {
				tmp.preduzece = findPreduzece;
				tmp.vrstaPDVa = findVrstaPDVa;
				tmp.nazivGrupe = grupa.nazivGrupe;
				tmp.save();
				break;
			}
		}

		Cache.set("grupe ", grupe );

		grupe.clear();
		grupe = Grupa.findAll();
			
		renderTemplate("Grupe/show.html", mode, grupe, preduzeca, vrstePDVa);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<Grupa> grupe = checkCache();

		Grupa grupa = Grupa.findById(id);
		Long idd = null;

		for (int i = 1; i < grupe.size(); i++) {
			if (grupe.get(i).id == id) {
				Grupa prethodni = grupe.get(i - 1);
				idd = prethodni.id;
			}
		}
		grupa.delete();

		grupe.clear();
		grupe = Grupa.findAll();
		Cache.set("grupe", grupe);

		renderTemplate("Grupe/show.html", idd, mode, grupe, preduzeca, vrstePDVa);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<Grupa> checkCache() {
		List<Grupa> grupe = (List<Grupa>) Cache.get("grupe");

		if ((grupe == null) || (grupe.size() == 0)) {
			grupe = Grupa.findAll();
			Cache.set("grupe", grupe);
		}

		return grupe;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Preduzece> preduzeca = Preduzeca.checkCache();
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<Grupa> grupe = checkCache();

		renderTemplate("Grupe/show.html", grupe, mode, preduzeca, vrstePDVa);
	}
	
	
	
	
	
	
	
	
	
	
}