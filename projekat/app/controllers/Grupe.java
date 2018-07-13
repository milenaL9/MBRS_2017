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
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		List<Preduzece> preduzeca = Preduzece.findAll();
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();
		List<Grupa> grupe = Grupa.findAll();

		render(mode, grupe, preduzeca, vrstePDVa);
	}
 
	public static void create(Grupa grupa,Long preduzece,Long vrstaPDVa) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<Grupa> grupe = null;
		List<Preduzece> preduzeca = Preduzece.findAll();
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();

		grupe = Grupa.findAll();

		Preduzece findPreduzece = Preduzece.findById(preduzece);
		grupa.preduzece = findPreduzece;
		VrstaPDVa findVrstaPDVa = VrstaPDVa.findById(vrstaPDVa);
		grupa.vrstaPDVa = findVrstaPDVa;
		

		grupa.save();
		grupe.add(grupa);

		Long idd = grupa.id;

		grupe.clear();
		grupe = Grupa.findAll();

		renderTemplate("Grupe/show.html", idd, mode, grupe, preduzeca, vrstePDVa);
	}
		 
	public static void edit(Grupa grupa,Long preduzece,Long vrstaPDVa) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Grupa> grupe = null;
		List<Preduzece> preduzeca = Preduzece.findAll();
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();

	
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
	
		renderTemplate("Grupe/show.html", mode, grupe, preduzeca, vrstePDVa);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Preduzece> preduzeca = Preduzece.findAll();
		List<VrstaPDVa> vrstePDVa = VrstaPDVa.findAll();
		List<Grupa> grupe = Grupa.findAll();

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

		renderTemplate("Grupe/show.html", idd, mode, grupe, preduzeca, vrstePDVa);
	}
	
		
	
	
	
	
}