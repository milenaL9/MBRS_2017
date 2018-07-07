package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FMClass extends FMType {	
	
	private String visibility;
	
	private String controllerName;
	
	private boolean create;
	
	//Class properties
	private List<FMProperty> FMProperties = new ArrayList<FMProperty>();
	
	private List<FMProperty> propertiesManyToOne = new ArrayList<FMProperty>();
	
	//list of packages (for import declarations) 
	private List<String> importedPackages = new ArrayList<String>();
	
	/** @ToDo: add list of methods */
	
	public FMClass(String name, String classPackage, String visibility, String controllerName, boolean create) {
		super(name, classPackage);		
		this.visibility = visibility;
		this.controllerName = controllerName;
		this.create = create;
	}	
	
	public List<FMProperty> getProperties(){
		return FMProperties;
	}
	
	public Iterator<FMProperty> getPropertyIterator(){
		return FMProperties.iterator();
	}
	
	public void addProperty(FMProperty property){
		FMProperties.add(property);		
	}

	
	public int getPropertyCount(){
		return FMProperties.size();
	}
	
	public List<String> getImportedPackages(){
		return importedPackages;
	}

	public Iterator<String> getImportedIterator(){
		return importedPackages.iterator();
	}
	
	public void addImportedPackage(String importedPackage){
		importedPackages.add(importedPackage);		
	}
	
	public int getImportedCount(){
		return FMProperties.size();
	}
	
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public List<FMProperty> getPropertiesManyToOne() {
		return propertiesManyToOne;
	}

	public void setPropertiesManyToOne(List<FMProperty> propertiesManyToOne) {
		this.propertiesManyToOne = propertiesManyToOne;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}	
	
	

	
	
}
