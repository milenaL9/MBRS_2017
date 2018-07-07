package ${class.typePackage};

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import play.db.jpa.Model;

@Entity
${class.visibility} class ${class.name} extends Model {
  
<#list properties as property>
	<#if property.upper == 1 >
		<#if property.next>		
	@ManyToOne
		</#if> 
  	${property.visibility} ${property.type} ${property.name};
  	
    <#elseif property.upper == -1 >    
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "${property.mappedBy}")
  	${property.visibility} List<${property.type}> ${property.name};
  	
    <#else>   
    	<#list 1..property.upper as i>
    	
  	${property.visibility} ${property.type} ${property.name}${i};
		</#list>  
    </#if>     
</#list>


}
