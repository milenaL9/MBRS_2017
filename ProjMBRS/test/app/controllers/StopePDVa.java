//Sat Jul 14 19:05:03 CEST 2018
//Generisano na osnovu sablona: controller.ftl
//Element modela: StopaPDVa

package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.VrstaPDVa;



import models.StopaPDVa;

public class StopePDVa extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();
		List<StopaPDVa> stopePDVa = StopaPDVa.findAll();


		render(mode, stopePDVa, vrstePDVa);
	}
 
	public static void create(StopaPDVa stopaPDVa,Long vrstaPDVa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<StopaPDVa> stopePDVa = null;
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();

		stopePDVa = StopaPDVa.findAll();

		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		stopaPDVa.vrstaPDVa = findVrstaPDVa;

		
		
		// Postavljanje stavke fakture
		
		stopaPDVa.save();
		stopePDVa.add(stopaPDVa);
		Long idd = stopaPDVa.id;
		stopePDVa.clear();
		stopePDVa = StopaPDVa.findAll();
		
		// Za Stavku Fakture

		// Za sve osim Fakture i StavkeFakture
		renderTemplate("StopePDVa/show.html", idd, mode, stopePDVa, vrstePDVa);
	}
		 
	public static void edit(StopaPDVa stopaPDVa,Long vrstaPDVa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<StopaPDVa> stopePDVa = null;
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();

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
	
		renderTemplate("StopePDVa/show.html", mode, stopePDVa, vrstePDVa);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();
		List<StopaPDVa> stopePDVa = StopaPDVa.findAll();

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

		renderTemplate("StopePDVa/show.html", idd, mode, stopePDVa, vrstePDVa);
	}
	
		
	
	
	
	
	
	
	
	
}