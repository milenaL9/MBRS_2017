//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: StopaPDVa

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
