//Sat Jul 14 20:10:42 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: Cenovnik

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
  	
    @OneToMany(mappedBy = "cenovnik")
  	public List<StavkaCenovnika> stavkeCenovnika;
  	
  	public String naziv;
  	

	public Cenovnik(String datumVazenja, String naziv){
		super();
		this.datumVazenja = datumVazenja;
		this.naziv = naziv;
	}

}
