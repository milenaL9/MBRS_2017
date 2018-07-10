package ${class.typePackage};

import java.util.ArrayList;
import java.util.List;
import play.cache.Cache;
import play.mvc.Controller;
<#list class.propertiesManyToOne as property>
import models.${property.type};
</#list>
import models.${class.name};

${class.visibility} class ${class.controllerName} extends Controller{ 

	public static void show() {	
		session.put("mode", "edit");
		String mode = session.get("mode");

		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.controllerName}.checkCache();
		</#list>
		List<${class.name}> ${class.controllerName?uncap_first} = checkCache();

		render(mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}

	<#if class.create >	 
	public static void create(${class.name} ${class.name?uncap_first}<#list class.propertiesManyToOne as property>,Long ${property.name}</#list>) {
		session.put("mode", "add");
		String mode = session.get("mode");

		List<${class.name}> ${class.controllerName?uncap_first} = null;
		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.controllerName}.checkCache();
		</#list>

		${class.controllerName?uncap_first} = ${class.name}.findAll();

		<#list class.propertiesManyToOne as property>
		${property.type} find${property.type} = ${property.name?cap_first}.findById(${property.name?uncap_first});
		${class.name?uncap_first}.${property.name?uncap_first} = find${property.type};
		</#list>

		${class.name?uncap_first}.save();
		${class.controllerName?uncap_first}.add(${class.name?uncap_first});
		Cache.set("${class.controllerName?uncap_first}", ${class.controllerName?uncap_first});

		Long idd = ${class.name?uncap_first}.id;

		${class.controllerName?uncap_first}.clear();
		${class.controllerName?uncap_first} = ${class.name}.findAll();

		renderTemplate("${class.controllerName}/show.html", idd, mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
		
	}
	</#if>
	
	<#if class.edit >	 
	public static void edit(${class.name} ${class.name?uncap_first}<#list class.propertiesManyToOne as property>,Long ${property.name}</#list>) {
		session.put("mode", "edit");
		String mode = session.get("mode");

		List<${class.name}> ${class.controllerName?uncap_first} = null;
		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.controllerName}.checkCache();
		</#list>

	
		${class.controllerName?uncap_first}  = ${class.name}.findAll();

		<#list class.propertiesManyToOne as property>
		${property.type} find${property.type} = ${property.name?cap_first}.findById(${property.name?uncap_first});
		${class.name?uncap_first}.${property.name?uncap_first} = find${property.type};
		</#list>


		for (${class.name} tmp : ${class.controllerName?uncap_first} ) {
			if (tmp.id == ${class.name?uncap_first}.id) {
				<#list class.propertiesManyToOne as property>
				tmp.${property.name} = find${property.type};
				</#list>
				<#list class.classProperties as property>
				tmp.${property.name} = ${class.name?uncap_first}.${property.name};
				</#list>
				tmp.save();
				break;
			}
		}

		Cache.set("${class.controllerName?uncap_first} ", ${class.controllerName?uncap_first} );

		${class.controllerName?uncap_first}.clear();
		${class.controllerName?uncap_first} = ${class.name}.findAll();
			
		renderTemplate("${class.controllerName}/show.html", mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	
	
	
	}
	</#if>
	
	<#if class.delete >
	public static void delete(Long id) {
		String mode = session.get("mode");

		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.controllerName}.checkCache();
		</#list>
		List<${class.name}> ${class.controllerName?uncap_first} = checkCache();

		${class.name} ${class.name?uncap_first} = ${class.name}.findById(id);
		Long idd = null;

		for (int i = 1; i < ${class.controllerName?uncap_first}.size(); i++) {
			if (${class.controllerName?uncap_first}.get(i).id == id) {
				${class.name} prethodni = ${class.controllerName?uncap_first}.get(i - 1);
				idd = prethodni.id;
			}
		}
		${class.name?uncap_first}.delete();

		${class.controllerName?uncap_first}.clear();
		${class.controllerName?uncap_first} = ${class.name}.findAll();
		Cache.set("${class.controllerName?uncap_first}", ${class.controllerName?uncap_first});

		renderTemplate("${class.controllerName}/show.html", idd, mode, ${class.controllerName?uncap_first}<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}
	</#if>
	
	
	
	/**
	 * Pomocna metoda za proveravanje kesa.
	 */
	public static List<${class.name}> checkCache() {
		List<${class.name}> ${class.controllerName?uncap_first} = (List<${class.name}>) Cache.get("${class.controllerName?uncap_first}");

		if ((${class.controllerName?uncap_first} == null) || (${class.controllerName?uncap_first}.size() == 0)) {
			${class.controllerName?uncap_first} = ${class.name}.findAll();
			Cache.set("${class.controllerName?uncap_first}", ${class.controllerName?uncap_first});
		}

		return ${class.controllerName?uncap_first};
	}
	
	
	public static void changeMode(String mode) {
		if (mode == null || mode.equals("")) {
			mode = "edit";
		}
		session.put("mode", mode);

		<#list class.propertiesManyToOne as property>
		List<${property.type}> ${property.controllerName?uncap_first} = ${property.controllerName}.checkCache();
		</#list>
		List<${class.name}> ${class.controllerName?uncap_first} = checkCache();

		renderTemplate("${class.controllerName}/show.html", ${class.controllerName?uncap_first}, mode<#list class.propertiesManyToOne as property>, ${property.controllerName?uncap_first}</#list>);
	}
	
	
	
	
	
	
	
	
	
	
}