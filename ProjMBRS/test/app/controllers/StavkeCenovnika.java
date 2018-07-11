package controllers;

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
import models.Artikal;
import models.Cenovnik;
import models.StavkaCenovnika;

public class StavkeCenovnika extends Controller{ 

	public static void show() {	
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<Artikal> artikli = Artikli.checkCache();
		List<Cenovnik> cenovnici = Cenovnici.checkCache();
		List<StavkaCenovnika> stavkeCenovnika = checkCache();

		render(mode, stavkeCenovnika, artikli, cenovnici);
	}

	public static void create(StavkaCenovnika stavkaCenovnika,Long artikal,Long cenovnik) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<StavkaCenovnika> stavkeCenovnika = null;
		List<Artikal> artikli = Artikli.checkCache();
		List<Cenovnik> cenovnici = Cenovnici.checkCache();

		stavkeCenovnika = StavkaCenovnika.findAll();

		Artikal findArtikal = Artikal.findById(artikal);
		stavkaCenovnika.artikal = findArtikal;
		Cenovnik findCenovnik = Cenovnik.findById(cenovnik);
		stavkaCenovnika.cenovnik = findCenovnik;

		stavkaCenovnika.save();
		stavkeCenovnika.add(stavkaCenovnika);
		Cache.set("stavkeCenovnika", stavkeCenovnika);

		Long idd = stavkaCenovnika.id;

		stavkeCenovnika.clear();
		stavkeCenovnika = StavkaCenovnika.findAll();

		renderTemplate("StavkeCenovnika/show.html", idd, mode, stavkeCenovnika, artikli, cenovnici);
		
	}
	
	public static void edit(StavkaCenovnika stavkaCenovnika,Long artikal,Long cenovnik) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<StavkaCenovnika> stavkeCenovnika = null;
		List<Artikal> artikli = Artikli.checkCache();
		List<Cenovnik> cenovnici = Cenovnici.checkCache();

	
		stavkeCenovnika  = StavkaCenovnika.findAll();

		Artikal findArtikal = Artikal.findById(artikal);
		stavkaCenovnika.artikal = findArtikal;
		Cenovnik findCenovnik = Cenovnik.findById(cenovnik);
		stavkaCenovnika.cenovnik = findCenovnik;


		for (StavkaCenovnika tmp : stavkeCenovnika ) {
			if (tmp.id == stavkaCenovnika.id) {
				tmp.artikal = findArtikal;
				tmp.cenovnik = findCenovnik;
				tmp.cena = stavkaCenovnika.cena;
				tmp.save();
				break;
			}
		}

		Cache.set("stavkeCenovnika ", stavkeCenovnika );

		stavkeCenovnika.clear();
		stavkeCenovnika = StavkaCenovnika.findAll();
			
		renderTemplate("StavkeCenovnika/show.html", mode, stavkeCenovnika, artikli, cenovnici);
	
	
	
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		List<Artikal> artikli = Artikli.checkCache();
		List<Cenovnik> cenovnici = Cenovnici.checkCache();
		List<StavkaCenovnika> stavkeCenovnika = checkCache();

		StavkaCenovnika stavkaCenovnika = StavkaCenovnika.findById(id);
		Long idd = null;

		for (int i = 1; i < stavkeCenovnika.size(); i++) {
			if (stavkeCenovnika.get(i).id == id) {
				StavkaCenovnika prethodni = stavkeCenovnika.get(i - 1);
				idd = prethodni.id;
			}
		}
		stavkaCenovnika.delete();

		stavkeCenovnika.clear();
		stavkeCenovnika = StavkaCenovnika.findAll();
		Cache.set("stavkeCenovnika", stavkeCenovnika);

		renderTemplate("StavkeCenovnika/show.html", idd, mode, stavkeCenovnika, artikli, cenovnici);
	}
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<StavkaCenovnika> checkCache() {
		List<StavkaCenovnika> stavkeCenovnika = (List<StavkaCenovnika>) Cache.get("stavkeCenovnika");

		if ((stavkeCenovnika == null) || (stavkeCenovnika.size() == 0)) {
			stavkeCenovnika = StavkaCenovnika.findAll();
			Cache.set("stavkeCenovnika", stavkeCenovnika);
		}

		return stavkeCenovnika;
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		List<Artikal> artikli = Artikli.checkCache();
		List<Cenovnik> cenovnici = Cenovnici.checkCache();
		List<StavkaCenovnika> stavkeCenovnika = checkCache();

		renderTemplate("StavkeCenovnika/show.html", stavkeCenovnika, mode, artikli, cenovnici);
	}
	
	
	
	
	
	
	
	
	
	
}