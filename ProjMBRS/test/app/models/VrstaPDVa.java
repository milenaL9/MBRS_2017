//Sat Jul 14 20:10:42 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: VrstaPDVa

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
  	
    @OneToMany(mappedBy = "vrstaPDVa")
  	public List<Grupa> grupa;
  	
    @OneToMany(mappedBy = "vrstaPDVa")
  	public List<StopaPDVa> stopePDVa;
  	

	public VrstaPDVa(String nazivVrstePDVa){
		super();
		this.nazivVrstePDVa = nazivVrstePDVa;
	}

}
