package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Preduzece;



import models.PoslovnaGodina;

public class PoslovneGodine extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Preduzece> preduzeca = Preduzece.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();


		render(mode, poslovneGodine, preduzeca);
	}
 
	public static void create(PoslovnaGodina poslovnaGodina,Long preduzece) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<PoslovnaGodina> poslovneGodine = null;
		List<Preduzece> preduzeca = Preduzece.findAll();

		poslovneGodine = PoslovnaGodina.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		poslovnaGodina.preduzece = findPreduzece;

		
		
		// Postavljanje stavke fakture
		

		poslovnaGodina.save();
		poslovneGodine.add(poslovnaGodina);

		Long idd = poslovnaGodina.id;

		poslovneGodine.clear();
		poslovneGodine = PoslovnaGodina.findAll();
		
		
		// Za Stavku Fakture


		
		
		// Za sve osim Fakture i StavkeFakture
		renderTemplate("PoslovneGodine/show.html", idd, mode, poslovneGodine, preduzeca);
		
	}
		 
	public static void edit(PoslovnaGodina poslovnaGodina,Long preduzece) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<PoslovnaGodina> poslovneGodine = null;
		List<Preduzece> preduzeca = Preduzece.findAll();

	
		poslovneGodine  = PoslovnaGodina.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		poslovnaGodina.preduzece = findPreduzece;


		for (PoslovnaGodina tmp : poslovneGodine ) {
			if (tmp.id == poslovnaGodina.id) {
				tmp.preduzece = findPreduzece;
				tmp.brojGodine = poslovnaGodina.brojGodine;
				tmp.aktivna = poslovnaGodina.aktivna;
				tmp.save();
				break;
			}
		}
	
		renderTemplate("PoslovneGodine/show.html", mode, poslovneGodine, preduzeca);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzece.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();

		PoslovnaGodina poslovnaGodina = PoslovnaGodina.findById(id);
		Long idd = null;

		for (int i = 1; i < poslovneGodine.size(); i++) {
			if (poslovneGodine.get(i).id == id) {
				PoslovnaGodina prethodni = poslovneGodine.get(i - 1);
				idd = prethodni.id;
			}
		}
		poslovnaGodina.delete();

		poslovneGodine.clear();
		poslovneGodine = PoslovnaGodina.findAll();

		renderTemplate("PoslovneGodine/show.html", idd, mode, poslovneGodine, preduzeca);
	}
	
		
	
	
	
	
	
	
	
	
}