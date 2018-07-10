package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Artikal extends Model {
  
  	public String nazivStavkeKataloga;
  	
  	public String opisStavkeKataloga;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "artikal")
  	public List<StavkaFakture> stavkeFakture;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "artikal")
  	public List<StavkaCenovnika> stavkeCenovnika;
  	
	@ManyToOne
  	public Podgrupa podgrupa;
  	

	public Artikal(String nazivStavkeKataloga, String opisStavkeKataloga){
		super();
		this.nazivStavkeKataloga = nazivStavkeKataloga;
		this.opisStavkeKataloga = opisStavkeKataloga;
	}

}
