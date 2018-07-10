package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Preduzece extends Model {
  
  	public String naziv;
  	
  	public String pib;
  	
  	public String mesto;
  	
  	public String adresa;
  	
  	public String telefon;
  	
  	public String maticniBroj;
  	
  	public String tekuciRacun;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "preduzece")
  	public List<PoslovniPartner> poslovniPartneri;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "preduzece")
  	public List<PoslovnaGodina> poslovneGodine;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "preduzece")
  	public List<Grupa> grupe;
  	

	public Preduzece(String naziv, String pib, String mesto, String adresa, String telefon, String maticniBroj, String tekuciRacun){
		super();
		this.naziv = naziv;
		this.pib = pib;
		this.mesto = mesto;
		this.adresa = adresa;
		this.telefon = telefon;
		this.maticniBroj = maticniBroj;
		this.tekuciRacun = tekuciRacun;
	}

}
