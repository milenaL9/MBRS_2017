//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: PoslovniPartner

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class PoslovniPartner extends Model {
  
  	public String naziv;
  	
  	public String mesto;
  	
  	public String adresa;
  	
  	public String vrsta;
  	
  	public String telefon;
  	
  	public int pib;
  	
  	public String tekuciRacun;
  	
    @OneToMany(mappedBy = "poslovniPartner")
  	public List<Faktura> fakture;
  	
	@ManyToOne
  	public Preduzece preduzece;
  	

	public PoslovniPartner(String naziv, String mesto, String adresa, String vrsta, String telefon, int pib, String tekuciRacun){
		super();
		this.naziv = naziv;
		this.mesto = mesto;
		this.adresa = adresa;
		this.vrsta = vrsta;
		this.telefon = telefon;
		this.pib = pib;
		this.tekuciRacun = tekuciRacun;
	}

}
