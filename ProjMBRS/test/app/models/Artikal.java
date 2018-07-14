//Sat Jul 14 20:10:42 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: Artikal

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
  	
    @OneToMany(mappedBy = "artikal")
  	public List<StavkaFakture> stavkeFakture;
  	
    @OneToMany(mappedBy = "artikal")
  	public List<StavkaCenovnika> stavkeCenovnika;
  	
	@ManyToOne
  	public Podgrupa podgrupa;
  	

	public Artikal(String nazivStavkeKataloga, String opisStavkeKataloga){
		super();
		this.nazivStavkeKataloga = nazivStavkeKataloga;
		this.opisStavkeKataloga = opisStavkeKataloga;
	}

}
