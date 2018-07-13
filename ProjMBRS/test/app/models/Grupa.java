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
