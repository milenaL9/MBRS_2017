package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;

import models.Preduzece;

public class Preduzeca extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Preduzece> preduzeca = Preduzece.findAll();

		render(mode, preduzeca);
	}
 
	public static void create(Preduzece preduzece) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Preduzece> preduzeca = null;

		preduzeca = Preduzece.findAll();

		

		preduzece.save();
		preduzeca.add(preduzece);

		Long idd = preduzece.id;

		preduzeca.clear();
		preduzeca = Preduzece.findAll();

		renderTemplate("Preduzeca/show.html", idd, mode, preduzeca);
	}
		 
	public static void edit(Preduzece preduzece) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Preduzece> preduzeca = null;

	
		preduzeca  = Preduzece.findAll();



		for (Preduzece tmp : preduzeca ) {
			if (tmp.id == preduzece.id) {
				tmp.naziv = preduzece.naziv;
				tmp.pib = preduzece.pib;
				tmp.mesto = preduzece.mesto;
				tmp.adresa = preduzece.adresa;
				tmp.telefon = preduzece.telefon;
				tmp.maticniBroj = preduzece.maticniBroj;
				tmp.tekuciRacun = preduzece.tekuciRacun;
				tmp.save();
				break;
			}
		}
	
		renderTemplate("Preduzeca/show.html", mode, preduzeca);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzece.findAll();

		Preduzece preduzece = Preduzece.findById(id);
		Long idd = null;

		for (int i = 1; i < preduzeca.size(); i++) {
			if (preduzeca.get(i).id == id) {
				Preduzece prethodni = preduzeca.get(i - 1);
				idd = prethodni.id;
			}
		}
		preduzece.delete();

		preduzeca.clear();
		preduzeca = Preduzece.findAll();

		renderTemplate("Preduzeca/show.html", idd, mode, preduzeca);
	}
	
		
	
	
	
	
}