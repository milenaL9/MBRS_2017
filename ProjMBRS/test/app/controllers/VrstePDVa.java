package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.VrstaPDVa;

public class VrstePDVa extends Controller{ 

	public static void show() {	
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = checkCache();

		render(mode, vrstePDVa);
	}

	public static void create(VrstaPDVa vrstaPDVa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = null;

		vrstePDVa = VrstaPDVa.findAll();


		vrstaPDVa.save();
		vrstePDVa.add(vrstaPDVa);
		Cache.set("vrstePDVa", vrstePDVa);

		Long idd = vrstaPDVa.id;

		vrstePDVa.clear();
		vrstePDVa = VrstaPDVa.findAll();

		renderTemplate("VrstePDVa/show.html", idd, mode, vrstePDVa);
		
	}
	
	public static void edit(VrstaPDVa vrstaPDVa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = null;

	
		vrstePDVa  = VrstaPDVa.findAll();



		for (VrstaPDVa tmp : vrstePDVa ) {
			if (tmp.id == vrstaPDVa.id) {
				tmp.nazivVrstePDVa = vrstaPDVa.nazivVrstePDVa;
				tmp.save();
				break;
			}
		}

		Cache.set("vrstePDVa ", vrstePDVa );

		vrstePDVa.clear();
		vrstePDVa = VrstaPDVa.findAll();
			
		renderTemplate("VrstePDVa/show.html", mode, vrstePDVa);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = checkCache();

		VrstaPDVa vrstaPDVa = VrstaPDVa.findById(id);
		Long idd = null;

		for (int i = 1; i < vrstePDVa.size(); i++) {
			if (vrstePDVa.get(i).id == id) {
				VrstaPDVa prethodni = vrstePDVa.get(i - 1);
				idd = prethodni.id;
			}
		}
		vrstaPDVa.delete();

		vrstePDVa.clear();
		vrstePDVa = VrstaPDVa.findAll();
		Cache.set("vrstePDVa", vrstePDVa);

		renderTemplate("VrstePDVa/show.html", idd, mode, vrstePDVa);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<VrstaPDVa> checkCache() {
		List<VrstaPDVa> vrstePDVa = (List<VrstaPDVa>) Cache.get("vrstePDVa");

		if ((vrstePDVa == null) || (vrstePDVa.size() == 0)) {
			vrstePDVa = VrstaPDVa.findAll();
			Cache.set("vrstePDVa", vrstePDVa);
		}

		return vrstePDVa;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<VrstaPDVa> vrstePDVa = checkCache();

		renderTemplate("VrstePDVa/show.html", vrstePDVa, mode);
	}
	
	
	
	
	
	
	
	
	
	
}