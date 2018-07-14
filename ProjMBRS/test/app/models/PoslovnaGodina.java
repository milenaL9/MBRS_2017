//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: PoslovnaGodina

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class PoslovnaGodina extends Model {
  
  	public String brojGodine;
  	
  	public char aktivna;
  	
    @OneToMany(mappedBy = "poslovnaGodina")
  	public List<Faktura> fakture;
  	
	@ManyToOne
  	public Preduzece preduzece;
  	

	public PoslovnaGodina(String brojGodine, char aktivna){
		super();
		this.brojGodine = brojGodine;
		this.aktivna = aktivna;
	}

}
