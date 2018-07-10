package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Cenovnik extends Model {
  
  	public String datumVazenja;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cenovnik")
  	public List<StavkaCenovnika> stavkeCenovnika;
  	
  	public String naziv;
  	

	public Cenovnik(String datumVazenja, String naziv){
		super();
		this.datumVazenja = datumVazenja;
		this.naziv = naziv;
	}

}
