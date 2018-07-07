package ${class.typePackage};

import play.mvc.Controller;

${class.visibility} class ${class.controllerName} extends Controller{ 

	<#if class.create >	 
	public static void create(${class.name} ${class.name?uncap_first}<#list class.propertiesManyToOne as property>,Long ${property.name}</#list>) {
	}
	</#if>
}