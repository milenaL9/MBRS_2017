//Sat Jul 14 19:05:00 CEST 2018
//Generisano na osnovu sablona: model.ftl
//Element modela: StavkaFakture

package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
public class StavkaFakture extends Model {
  
  	public float kolicina;
  	
  	public float cena;
  	
  	public float rabat;
  	
  	public float osnovicaZaPDV;
  	
  	public float stopaPDVa;
  	
  	public float ukupno;
  	
	@ManyToOne
  	public Artikal artikal;
  	
	@ManyToOne
  	public Faktura faktura;
  	
  	public float iznosPDVa;
  	
  	public float iznosRabata;
  	
  	public float vrednost;
  	

	public StavkaFakture(float kolicina, float cena, float rabat, float osnovicaZaPDV, float stopaPDVa, float ukupno, float iznosPDVa, float iznosRabata, float vrednost){
		super();
		this.kolicina = kolicina;
		this.cena = cena;
		this.rabat = rabat;
		this.osnovicaZaPDV = osnovicaZaPDV;
		this.stopaPDVa = stopaPDVa;
		this.ukupno = ukupno;
		this.iznosPDVa = iznosPDVa;
		this.iznosRabata = iznosRabata;
		this.vrednost = vrednost;
	}

}
