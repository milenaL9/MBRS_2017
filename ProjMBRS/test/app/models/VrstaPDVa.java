package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class VrstaPDVa extends Model {
  
  	public String nazivVrstePDVa;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vrstaPDVa")
  	public List<Grupa> grupa;
  	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vrstaPDVa")
  	public List<StopaPDVa> stopePDVa;
  	

	public VrstaPDVa(String nazivVrstePDVa){
		super();
		this.nazivVrstePDVa = nazivVrstePDVa;
	}

}
