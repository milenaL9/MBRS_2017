package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FMClass extends FMType {

	private String visibility;

	private String controllerName;

	private boolean create;

	private boolean edit;

	private boolean delete;

	private String label;

	// Class properties
	private List<FMProperty> FMProperties = new ArrayList<FMProperty>(); // svi

	private List<FMProperty> propertiesManyToOne = new ArrayList<FMProperty>(); // samo sa anotacijama ManyToOne

	private List<FMProperty> classProperties = new ArrayList<FMProperty>(); // bez anotacija OneToMany i ManyToOne

	private List<FMProperty> propertiesNoZoom = new ArrayList<FMProperty>(); // bez anotacija OneToMany

	// list of packages (for import declarations)
	private List<String> importedPackages = new ArrayList<String>();

	/** @ToDo: add list of methods */

	public FMClass(String name, String classPackage, String visibility, String controllerName, boolean create,
			boolean edit, boolean delete, String label) {
		super(name, classPackage);
		this.visibility = visibility;
		this.controllerName = controllerName;
		this.create = create;
		this.edit = edit;
		this.delete = delete;
		this.label = label;
	}

	public List<FMProperty> getProperties() {
		return FMProperties;
	}

	public Iterator<FMProperty> getPropertyIterator() {
		return FMProperties.iterator();
	}

	public void addProperty(FMProperty property) {
		FMProperties.add(property);
	}

	public int getPropertyCount() {
		return FMProperties.size();
	}

	public List<String> getImportedPackages() {
		return importedPackages;
	}

	public Iterator<String> getImportedIterator() {
		return importedPackages.iterator();
	}

	public void addImportedPackage(String importedPackage) {
		importedPackages.add(importedPackage);
	}

	public int getImportedCount() {
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

	public List<FMProperty> getClassProperties() {
		return classProperties;
	}

	public void setClassProperties(List<FMProperty> classProperties) {
		this.classProperties = classProperties;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<FMProperty> getPropertiesNoZoom() {
		return propertiesNoZoom;
	}

	public void setPropertiesNoZoom(List<FMProperty> propertiesNoZoom) {
		this.propertiesNoZoom = propertiesNoZoom;
	}

}
