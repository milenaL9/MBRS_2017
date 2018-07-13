package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FMClass extends FMType {

	private String visibility;

	private String controllerName;

	private boolean incrementBrojFakture;

	private boolean convertToDate;

	private boolean findStavkeCenovnika;

	private boolean findStavkeFakture;

	private boolean createSF;

	private boolean deleteSF;

	private boolean editSF;

	private boolean showMenu;

	private String label;

	// Class properties
	private List<FMProperty> FMProperties = new ArrayList<FMProperty>(); // svi

	private List<FMProperty> propertiesManyToOne = new ArrayList<FMProperty>(); // samo sa anotacijama ManyToOne

	private List<FMProperty> classProperties = new ArrayList<FMProperty>(); // bez anotacija OneToMany i ManyToOne

	private List<FMProperty> propertiesNoZoom = new ArrayList<FMProperty>(); // bez anotacija OneToMany

	// list of packages (for import declarations)
	private List<String> importedPackages = new ArrayList<String>();

	/** @ToDo: add list of methods */

	public FMClass(String name, String classPackage, String visibility, String controllerName, String label,
			boolean incrementBrojFakture, boolean convertToDate, boolean findStavkeCenovnika,
			boolean findStavkeFakture) {
		super(name, classPackage);
		this.visibility = visibility;
		this.controllerName = controllerName;
		this.label = label;
		this.incrementBrojFakture = incrementBrojFakture;
		this.convertToDate = convertToDate;
		this.findStavkeCenovnika = findStavkeCenovnika;
		this.findStavkeFakture = findStavkeFakture;
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

	public List<FMProperty> getClassProperties() {
		return classProperties;
	}

	public void setClassProperties(List<FMProperty> classProperties) {
		this.classProperties = classProperties;
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

	public boolean isIncrementBrojFakture() {
		return incrementBrojFakture;
	}

	public void setIncrementBrojFakture(boolean incrementBrojFakture) {
		this.incrementBrojFakture = incrementBrojFakture;
	}

	public boolean isConvertToDate() {
		return convertToDate;
	}

	public void setConvertToDate(boolean convertToDate) {
		this.convertToDate = convertToDate;
	}

	public boolean isFindStavkeCenovnika() {
		return findStavkeCenovnika;
	}

	public void setFindStavkeCenovnika(boolean findStavkeCenovnika) {
		this.findStavkeCenovnika = findStavkeCenovnika;
	}

	public boolean isFindStavkeFakture() {
		return findStavkeFakture;
	}

	public void setFindStavkeFakture(boolean findStavkeFakture) {
		this.findStavkeFakture = findStavkeFakture;
	}

	public boolean isCreateSF() {
		return createSF;
	}

	public void setCreateSF(boolean createSF) {
		this.createSF = createSF;
	}

	public boolean isDeleteSF() {
		return deleteSF;
	}

	public void setDeleteSF(boolean deleteSF) {
		this.deleteSF = deleteSF;
	}

	public boolean isEditSF() {
		return editSF;
	}

	public void setEditSF(boolean editSF) {
		this.editSF = editSF;
	}

	public boolean isShowMenu() {
		return showMenu;
	}

	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
	}

}
