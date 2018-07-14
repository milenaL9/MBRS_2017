//Sat Jul 14 20:10:44 CEST 2018
//Generisano na osnovu sablona: controller.ftl
//Element modela: Artikal

package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Podgrupa;



import models.Artikal;

public class Artikli extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Podgrupa> podgrupe = Podgrupa.findAll();
		List<Artikal> artikli = Artikal.findAll();


		render(mode, artikli, podgrupe);
	}
 
	public static void create(Artikal artikal,Long podgrupa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Artikal> artikli = null;
		List<Podgrupa> podgrupe = Podgrupa.findAll();

		artikli = Artikal.findAll();

		Podgrupa findPodgrupa = Podgrupa.findById(podgrupa);
		artikal.podgrupa = findPodgrupa;

		
		
		// Postavljanje stavke fakture
		
		artikal.save();
		artikli.add(artikal);
		Long idd = artikal.id;
		artikli.clear();
		artikli = Artikal.findAll();
		
		// Za Stavku Fakture

		// Za sve osim Fakture i StavkeFakture
		renderTemplate("Artikli/show.html", idd, mode, artikli, podgrupe);
	}
		 
	public static void edit(Artikal artikal,Long podgrupa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Artikal> artikli = null;
		List<Podgrupa> podgrupe = Podgrupa.findAll();

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
	
		renderTemplate("Artikli/show.html", mode, artikli, podgrupe);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = Podgrupa.findAll();
		List<Artikal> artikli = Artikal.findAll();

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

		renderTemplate("Artikli/show.html", idd, mode, artikli, podgrupe);
	}
	
		
	
	
	
	
	
	
	
	
}