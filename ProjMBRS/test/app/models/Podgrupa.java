//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: Podgrupa

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Podgrupa extends Model {
  
  	public String nazivPodgrupe;
  	
    @OneToMany(mappedBy = "podgrupa")
  	public List<Artikal> artikli;
  	
	@ManyToOne
  	public Grupa grupa;
  	

	public Podgrupa(String nazivPodgrupe){
		super();
		this.nazivPodgrupe = nazivPodgrupe;
	}

}
