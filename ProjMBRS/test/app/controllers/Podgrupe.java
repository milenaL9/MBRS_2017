//Sat Jul 14 19:05:03 CEST 2018
//Generisano na osnovu sablona: controller.ftl
//Element modela: Podgrupa

package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Grupa;



import models.Podgrupa;

public class Podgrupe extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Grupa> grupe = Grupa.findAll();
		List<Podgrupa> podgrupe = Podgrupa.findAll();


		render(mode, podgrupe, grupe);
	}
 
	public static void create(Podgrupa podgrupa,Long grupa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = null;
		List<Grupa> grupe = Grupa.findAll();

		podgrupe = Podgrupa.findAll();

		Grupa findGrupa = Grupa.findById(grupa);
		podgrupa.grupa = findGrupa;

		
		
		// Postavljanje stavke fakture
		
		podgrupa.save();
		podgrupe.add(podgrupa);
		Long idd = podgrupa.id;
		podgrupe.clear();
		podgrupe = Podgrupa.findAll();
		
		// Za Stavku Fakture

		// Za sve osim Fakture i StavkeFakture
		renderTemplate("Podgrupe/show.html", idd, mode, podgrupe, grupe);
	}
		 
	public static void edit(Podgrupa podgrupa,Long grupa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Podgrupa> podgrupe = null;
		List<Grupa> grupe = Grupa.findAll();

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
	
		renderTemplate("Podgrupe/show.html", mode, podgrupe, grupe);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Grupa> grupe = Grupa.findAll();
		List<Podgrupa> podgrupe = Podgrupa.findAll();

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

		renderTemplate("Podgrupe/show.html", idd, mode, podgrupe, grupe);
	}
	
		
	
	
	
	
	
	
	
	
}