package ${class.typePackage};

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
<#list class.propertiesManyToOne as property>
import models.${property.type};
</#list>

<#if class.incrementBrojFakture || class.findStopaPDVa>
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import models.Cenovnik;
import models.StavkaCenovnika;
import models.StavkaFakture;
import models.Artikal;
</#if>

<#if class.setUpStavkaFakture>
import models.StopaPDVa;
import models.VrstaPDVa;
import controllers.Fakture;
</#if>

import models.${class.name};

${class.visibility} class ${class.controllerName} extends Controller{ 

	public static void show(String mode) {	
	    if(mode == null || mode.equals("")) {
	    	mode = "edit";
	    }
	    
	    session.put("mode", mode);

		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.type}.findAll();
		</#list>
		List<${class.name}> ${class.controllerName?uncap_first} = ${class.name}.findAll();

		<#if class.setUpStavkaFakture>
		stavkeFakture.clear();
		Long idFak = Long.parseLong(session.get("idFakture"));
		stavkeFakture = Fakture.findStavkeFakture(idFak);
		</#if>

		render(mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}
 
	public static void create(${class.name} ${class.name?uncap_first}<#list class.propertiesManyToOne as property>,Long ${property.name}</#list>) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<${class.name}> ${class.controllerName?uncap_first} = null;
		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.type}.findAll();
		</#list>

		${class.controllerName?uncap_first} = ${class.name}.findAll();

		<#list class.propertiesManyToOne as property>
		${property.type} find${property.type} = ${property.name?cap_first}.findById(${property.name?uncap_first});
		${class.name?uncap_first}.${property.name?uncap_first} = find${property.type};
		</#list>

		
		<#if class.incrementBrojFakture>
		// Postavljanje fakture
		faktura = setUpFaktura(faktura);
		</#if>
		
		// Postavljanje stavke fakture
		<#if class.setUpStavkaFakture>
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = fillListStavkeCenovnika();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (StavkaCenovnika sc : stavkeCenovnika) {
			if (sc.artikal.id == stavkaFakture.artikal.id) {
				stavkaFakture.cena = (float) sc.cena;
			}
		}

		try {
			stavkaFakture.stopaPDVa = findStopaPDVa(findFaktura.id,
					stavkaFakture.artikal.podgrupa.grupa.vrstaPDVa).procenatPDVa;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stavkaFakture = setUpStavkaFakture(stavkaFakture);
		stavkaFakture.faktura = findFaktura;
		</#if>
		
		${class.name?uncap_first}.save();
		${class.controllerName?uncap_first}.add(${class.name?uncap_first});
		Long idd = ${class.name?uncap_first}.id;
		${class.controllerName?uncap_first}.clear();
		${class.controllerName?uncap_first} = ${class.name}.findAll();
		
		// Za Stavku Fakture
		<#if class.findStopaPDVa>
		stavkeFakture.clear();
		stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = fillListStavkeCenovnika();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		</#if>

		<#if class.setUpStavkaFakture>
		// Poziv za Stavku fakture
		stavkeFakture.clear();
		Long idFak = Long.parseLong(session.get("idFakture"));
		stavkeFakture = Fakture.findStavkeFakture(idFak);
		renderTemplate("StavkeFakture/show.html", stavkeFakture, fakture, artikli, idd,
					mode, stavkeCenovnika);
		
		<#elseif class.incrementBrojFakture >
		// Poziv pomocnih metoda za Fakturu
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		try {
			stavkeCenovnika = findStavkeCenovnika(idd);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		List<StavkaFakture> stavkeFakture = findStavkeFakture(idd);
		List<Artikal> artikli = Artikal.findAll();
		session.put("idFakture", faktura.id);
		renderTemplate("StavkeFakture/show.html", stavkeFakture, stavkeCenovnika, idd, mode, artikli, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
		<#else>
		// Za sve osim Fakture i StavkeFakture
		renderTemplate("${class.controllerName}/show.html", idd, mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
		</#if>
	}
		 
	public static void edit(${class.name} ${class.name?uncap_first}<#list class.propertiesManyToOne as property>,Long ${property.name}</#list>) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<${class.name}> ${class.controllerName?uncap_first} = null;
		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.type}.findAll();
		</#list>

		${class.controllerName?uncap_first}  = ${class.name}.findAll();

		<#list class.propertiesManyToOne as property>
		${property.type} find${property.type} = ${property.name?cap_first}.findById(${property.name?uncap_first});
		${class.name?uncap_first}.${property.name?uncap_first} = find${property.type};
		</#list>


		for (${class.name} tmp : ${class.controllerName?uncap_first} ) {
			if (tmp.id == ${class.name?uncap_first}.id) {
				<#list class.propertiesManyToOne as property>
				tmp.${property.name} = find${property.type};
				</#list>
				<#list class.classProperties as property>
				tmp.${property.name} = ${class.name?uncap_first}.${property.name};
				</#list>
				tmp.save();
				break;
			}
		}
	
		renderTemplate("${class.controllerName}/show.html", mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}
	
	public static void delete(Long id) {
		String mode = session.get("mode");

		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.type}.findAll();
		</#list>
		List<${class.name}> ${class.controllerName?uncap_first} = ${class.name}.findAll();

		${class.name} ${class.name?uncap_first} = ${class.name}.findById(id);
		Long idd = null;

		for (int i = 1; i < ${class.controllerName?uncap_first}.size(); i++) {
			if (${class.controllerName?uncap_first}.get(i).id == id) {
				${class.name} prethodni = ${class.controllerName?uncap_first}.get(i - 1);
				idd = prethodni.id;
			}
		}
		${class.name?uncap_first}.delete();

		${class.controllerName?uncap_first}.clear();
		${class.controllerName?uncap_first} = ${class.name}.findAll();

		renderTemplate("${class.controllerName}/show.html", idd, mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}
	
	<#if class.incrementBrojFakture>
	public static int incrementBrojFakture() {
		List<Faktura> fakture = Faktura.findAll();
		int brojFakture = 0;
		if (fakture.size() > 0) {
			brojFakture = fakture.get(fakture.size() - 1).brojFakture;
			brojFakture++;
		} else {
			brojFakture = 1;
		}

		return brojFakture;
	}
	</#if>
		
	<#if class.setUp>
	public static Faktura setUpFaktura(Faktura faktura){
		faktura.brojFakture = incrementBrojFakture();
		List<StavkaFakture> stavkeFakture = faktura.stavkeFakture;
		faktura.ukupnoOsnovica = 0;
		faktura.ukupnoPDV = 0;
		faktura.ukupnoZaPlacanje = 0;
		if (stavkeFakture != null) {
			for (StavkaFakture sf : stavkeFakture) {
				faktura.ukupnoOsnovica += sf.osnovicaZaPDV;
				faktura.ukupnoPDV += sf.iznosPDVa;
				faktura.ukupnoZaPlacanje += sf.ukupno;
			}
 
		}
		
		return faktura;
	}
	</#if>
	
	<#if class.convertToDate>
	public static Date convertToDate(String receivedDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = formatter.parse(receivedDate);
		return date;
	}
	</#if>
	
	<#if class.findStavkeCenovnika>
	public static List<StavkaCenovnika> findStavkeCenovnika(Long idFakture) throws ParseException {
		Faktura faktura = Faktura.findById(idFakture);
		String datumFakture = faktura.datumFakture;
		Date datumFaktureDate = convertToDate(datumFakture);

		List<Cenovnik> cenovniciSaDatumima = new ArrayList<>();
		List<Cenovnik> cenovnici = Cenovnik.findAll();
		for (Cenovnik tmp : cenovnici) {
			String datumCenovnika = tmp.datumVazenja;
			Date datumCenovnikaDate = convertToDate(datumCenovnika);

			if (!datumCenovnikaDate.after(datumFaktureDate)) {
				cenovniciSaDatumima.add(tmp);
			}
		}

		List<Date> datumi = new ArrayList<>();
		// trazim cenovnik sa najvisim datumom
		for (Cenovnik tmp : cenovniciSaDatumima) {
			Date d = convertToDate(tmp.datumVazenja);
			datumi.add(d);
		}

		Collections.sort(datumi, new Comparator<Date>() {
			@Override
			public int compare(Date arg0, Date arg1) {
				return arg0.compareTo(arg1);
			}
		});

		// trazim stavke cenovnika
		List<StavkaCenovnika> stavkeCenovnika = new ArrayList<>();
		for (Cenovnik tmp : cenovniciSaDatumima) {
			String string = new SimpleDateFormat("MM/dd/yyyy").format(datumi.get(datumi.size() - 1));
			if (tmp.datumVazenja.equals(string)) {
				stavkeCenovnika = tmp.stavkeCenovnika;
			}
		}

		return stavkeCenovnika;
	}
	</#if>
	
	<#if class.findStavkeFakture>
	public static List<StavkaFakture> findStavkeFakture(Long idFakture) {
		List<StavkaFakture> stavkeFaktureAll = StavkaFakture.findAll();
		List<StavkaFakture> stavkeFakture = new ArrayList<>();

		for (StavkaFakture sc : stavkeFaktureAll) {
			if (sc.faktura.id == idFakture) {
				stavkeFakture.add(sc);
			}
		}

		return stavkeFakture;
	}
	</#if>
	
	<#if class.saveStavke>
	public static void saveStavke() {
		Long id = Long.parseLong(session.get("idFakture"));
		Faktura faktura = Faktura.findById(id);

		if (faktura.stavkeFakture.size() == 0) {
			Fakture.delete(id);
		} else {
			Fakture.show("edit");
		}
	}	
	</#if>
	
	<#if class.fillListStavkeCenovnika>
	public static List<StavkaCenovnika> fillListStavkeCenovnika() throws ParseException {
		List<StavkaCenovnika> stavkeCenovnika = null;

		if (!session.get("idFakture").equals("null")) {
			Long id = Long.parseLong(session.get("idFakture"));
			stavkeCenovnika = Fakture.findStavkeCenovnika(id);
		}

		return stavkeCenovnika;
	}
	</#if>
	
	<#if class.findStopaPDVa>
	public static StopaPDVa findStopaPDVa(Long idFakture, VrstaPDVa vrstaPDVa) throws ParseException {
		Faktura faktura = Faktura.findById(idFakture);
		String datumFakture = faktura.datumFakture;
		Date datumFaktureDate = Fakture.convertToDate(datumFakture);

		List<StopaPDVa> stopePDVaSaDatumima = new ArrayList<>();
		List<StopaPDVa> stopePDVa = StopaPDVa.findAll();
		for (StopaPDVa tmp : stopePDVa) {
			String datumStopePDVa = tmp.datumKreiranja;
			Date datumStopePDVaDate = Fakture.convertToDate(datumStopePDVa);

			if (!datumStopePDVaDate.after(datumFaktureDate) && (tmp.vrstaPDVa.id == vrstaPDVa.id)) {
				stopePDVaSaDatumima.add(tmp);
			}
		}

		List<Date> datumi = new ArrayList<>();
		for (StopaPDVa tmp : stopePDVaSaDatumima) {
			Date d = Fakture.convertToDate(tmp.datumKreiranja);
			datumi.add(d);
		}

		Collections.sort(datumi, new Comparator<Date>() {
			@Override
			public int compare(Date arg0, Date arg1) {
				return arg0.compareTo(arg1);
			}
		});

		// trazim stopuPDVa
		StopaPDVa stopaPDVa = null;
		for (StopaPDVa tmp : stopePDVaSaDatumima) {
			String string = new SimpleDateFormat("MM/dd/yyyy").format(datumi.get(datumi.size() - 1));
			if (tmp.datumKreiranja.equals(string)) {
				stopaPDVa = tmp;
			}
		}

		return stopaPDVa;
	}
	</#if>
	
	<#if class.setUpStavkaFakture>
	public static StavkaFakture setUpStavkaFakture(StavkaFakture stavkaFakture){
		// kada je disable- ovan combobox ne pokupi vrednost
		/*Faktura findFaktura = null;
		if (faktura == null) {
			Long id = Long.parseLong(session.get("idFakture"));
			findFaktura = Faktura.findById(id);
		} else {
			findFaktura = Faktura.findById(faktura);
		}*/
		
		stavkaFakture.vrednost = stavkaFakture.cena * stavkaFakture.kolicina;
		stavkaFakture.iznosRabata = stavkaFakture.vrednost * (stavkaFakture.rabat / 100);
		stavkaFakture.osnovicaZaPDV = stavkaFakture.vrednost - stavkaFakture.iznosRabata;

		
		stavkaFakture.iznosPDVa = (stavkaFakture.osnovicaZaPDV * stavkaFakture.stopaPDVa) / 100;
		stavkaFakture.ukupno = stavkaFakture.vrednost - stavkaFakture.iznosRabata + stavkaFakture.iznosPDVa;

		stavkaFakture.faktura.ukupnoPDV += stavkaFakture.iznosPDVa;
		stavkaFakture.faktura.ukupnoZaPlacanje += stavkaFakture.ukupno;
		stavkaFakture.faktura.ukupnoOsnovica += stavkaFakture.osnovicaZaPDV;

		stavkaFakture.faktura.save();
		
		return stavkaFakture;
	}
	</#if>
	
}