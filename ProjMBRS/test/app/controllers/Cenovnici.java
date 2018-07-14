package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;



import models.Cenovnik;

public class Cenovnici extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Cenovnik> cenovnici = Cenovnik.findAll();


		render(mode, cenovnici);
	}
 
	public static void create(Cenovnik cenovnik) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Cenovnik> cenovnici = null;

		cenovnici = Cenovnik.findAll();


		
		
		// Postavljanje stavke fakture
		

		cenovnik.save();
		cenovnici.add(cenovnik);

		Long idd = cenovnik.id;

		cenovnici.clear();
		cenovnici = Cenovnik.findAll();
		
		
		// Za Stavku Fakture


		
		
		// Za sve osim Fakture i StavkeFakture
		renderTemplate("Cenovnici/show.html", idd, mode, cenovnici);
		
	}
		 
	public static void edit(Cenovnik cenovnik) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Cenovnik> cenovnici = null;

	
		cenovnici  = Cenovnik.findAll();



		for (Cenovnik tmp : cenovnici ) {
			if (tmp.id == cenovnik.id) {
				tmp.datumVazenja = cenovnik.datumVazenja;
				tmp.naziv = cenovnik.naziv;
				tmp.save();
				break;
			}
		}
	
		renderTemplate("Cenovnici/show.html", mode, cenovnici);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Cenovnik> cenovnici = Cenovnik.findAll();

		Cenovnik cenovnik = Cenovnik.findById(id);
		Long idd = null;

		for (int i = 1; i < cenovnici.size(); i++) {
			if (cenovnici.get(i).id == id) {
				Cenovnik prethodni = cenovnici.get(i - 1);
				idd = prethodni.id;
			}
		}
		cenovnik.delete();

		cenovnici.clear();
		cenovnici = Cenovnik.findAll();

		renderTemplate("Cenovnici/show.html", idd, mode, cenovnici);
	}
	
		
	
	
	
	
	
	
	
	
}