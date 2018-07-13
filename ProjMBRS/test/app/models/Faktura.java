package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Faktura extends Model {
  
  	public String datumFakture;
  	
  	public int brojFakture;
  	
  	public String datumValute;
  	
  	public float ukupnoOsnovica;
  	
  	public float ukupnoPDV;
  	
  	public float ukupnoZaPlacanje;
  	
    @OneToMany(mappedBy = "faktura")
  	public List<StavkaFakture> stavkeFakture;
  	
	@ManyToOne
  	public PoslovnaGodina poslovnaGodina;
  	
	@ManyToOne
  	public PoslovniPartner poslovniPartner;
  	
	@ManyToOne
  	public Preduzece preduzece;
  	

	public Faktura(String datumFakture, int brojFakture, String datumValute, float ukupnoOsnovica, float ukupnoPDV, float ukupnoZaPlacanje){
		super();
		this.datumFakture = datumFakture;
		this.brojFakture = brojFakture;
		this.datumValute = datumValute;
		this.ukupnoOsnovica = ukupnoOsnovica;
		this.ukupnoPDV = ukupnoPDV;
		this.ukupnoZaPlacanje = ukupnoZaPlacanje;
	}

}
