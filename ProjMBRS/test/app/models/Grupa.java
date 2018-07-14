//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: Grupa

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class Grupa extends Model {
  
  	public String nazivGrupe;
  	
    @OneToMany(mappedBy = "grupa")
  	public List<Podgrupa> podgrupe;
  	
	@ManyToOne
  	public Preduzece preduzece;
  	
	@ManyToOne
  	public VrstaPDVa vrstaPDVa;
  	

	public Grupa(String nazivGrupe){
		super();
		this.nazivGrupe = nazivGrupe;
	}

}
