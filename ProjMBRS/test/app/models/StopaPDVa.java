package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class StopaPDVa extends Model {
  
  	public String datumKreiranja;
  	
  	public float procenatPDVa;
  	
	@ManyToOne
  	public VrstaPDVa vrstaPDVa;
  	

	public StopaPDVa(String datumKreiranja, float procenatPDVa){
		super();
		this.datumKreiranja = datumKreiranja;
		this.procenatPDVa = procenatPDVa;
	}

}
