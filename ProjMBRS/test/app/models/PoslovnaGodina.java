//Sat Jul 14 20:10:42 CEST 2018
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
  	
  	public String aktivna;
  	
    @OneToMany(mappedBy = "poslovnaGodina")
  	public List<Faktura> fakture;
  	
	@ManyToOne
  	public Preduzece preduzece;
  	

	public PoslovnaGodina(String brojGodine, String aktivna){
		super();
		this.brojGodine = brojGodine;
		this.aktivna = aktivna;
	}

}
