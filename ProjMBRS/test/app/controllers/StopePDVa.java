package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.VrstaPDVa;
import models.StopaPDVa;

public class StopePDVa extends Controller{ 

	public static void show(String mode) {	
		session.put("mode", "edit");
	    mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<StopaPDVa> stopePDVa = checkCache();

		render(mode, stopePDVa, vrstePDVa);
	}

	public static void create(StopaPDVa stopaPDVa,Long vrstaPDVa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<StopaPDVa> stopePDVa = null;
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();

		stopePDVa = StopaPDVa.findAll();

		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		stopaPDVa.vrstaPDVa = findVrstaPDVa;

		stopaPDVa.save();
		stopePDVa.add(stopaPDVa);
		Cache.set("stopePDVa", stopePDVa);

		Long idd = stopaPDVa.id;

		stopePDVa.clear();
		stopePDVa = StopaPDVa.findAll();

		renderTemplate("StopePDVa/show.html", idd, mode, stopePDVa, vrstePDVa);
		
	}
	
	public static void edit(StopaPDVa stopaPDVa,Long vrstaPDVa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<StopaPDVa> stopePDVa = null;
		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();

	
		stopePDVa  = StopaPDVa.findAll();

		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		stopaPDVa.vrstaPDVa = findVrstaPDVa;


		for (StopaPDVa tmp : stopePDVa ) {
			if (tmp.id == stopaPDVa.id) {
				tmp.vrstaPDVa = findVrstaPDVa;
				tmp.datumKreiranja = stopaPDVa.datumKreiranja;
				tmp.procenatPDVa = stopaPDVa.procenatPDVa;
				tmp.save();
				break;
			}
		}

		Cache.set("stopePDVa ", stopePDVa );

		stopePDVa.clear();
		stopePDVa = StopaPDVa.findAll();
			
		renderTemplate("StopePDVa/show.html", mode, stopePDVa, vrstePDVa);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<StopaPDVa> stopePDVa = checkCache();

		StopaPDVa stopaPDVa = StopaPDVa.findById(id);
		Long idd = null;

		for (int i = 1; i < stopePDVa.size(); i++) {
			if (stopePDVa.get(i).id == id) {
				StopaPDVa prethodni = stopePDVa.get(i - 1);
				idd = prethodni.id;
			}
		}
		stopaPDVa.delete();

		stopePDVa.clear();
		stopePDVa = StopaPDVa.findAll();
		Cache.set("stopePDVa", stopePDVa);

		renderTemplate("StopePDVa/show.html", idd, mode, stopePDVa, vrstePDVa);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<StopaPDVa> checkCache() {
		List<StopaPDVa> stopePDVa = (List<StopaPDVa>) Cache.get("stopePDVa");

		if ((stopePDVa == null) || (stopePDVa.size() == 0)) {
			stopePDVa = StopaPDVa.findAll();
			Cache.set("stopePDVa", stopePDVa);
		}

		return stopePDVa;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<VrstaPDVa> vrstePDVa = VrstePDVa.checkCache();
		List<StopaPDVa> stopePDVa = checkCache();

		renderTemplate("StopePDVa/show.html", stopePDVa, mode, vrstePDVa);
	}
	
	
	
	
	
	
	
	
	
	
}