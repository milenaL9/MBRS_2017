//Sat Jul 14 20:10:42 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: StavkaCenovnika

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class StavkaCenovnika extends Model {
  
  	public double cena;
  	
	@ManyToOne
  	public Artikal artikal;
  	
	@ManyToOne
  	public Cenovnik cenovnik;
  	

	public StavkaCenovnika(double cena){
		super();
		this.cena = cena;
	}

}
