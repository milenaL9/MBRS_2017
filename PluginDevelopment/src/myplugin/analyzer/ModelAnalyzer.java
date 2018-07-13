package myplugin.analyzer;

import java.util.Iterator;

import java.util.List;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Model Analyzer takes necessary metadata from the MagicDraw model and puts it
 * in the intermediate data structure (@see myplugin.generator.fmmodel.FMModel)
 * optimized for code generation using freemarker. Model Analyzer now takes
 * metadata only for ejb code generation
 * 
 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and
 *        Model Analyzer methods in order to support GUI generation.
 */

public class ModelAnalyzer {
	// root model package
	private Package root;

	// java root package for generated code
	private String filePackage;

	public ModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}

	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getEnumerations().clear();
		processPackage(root, filePackage);
	}

	private void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		// Recursive procedure that extracts data from package elements and stores it in
		// the
		// intermediate data structure

		if (pack.getName() == null)
			throw new AnalyzeException("Packages must have names!");

		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Class) {
					Class cl = (Class) ownedElement;
					FMClass fmClass = getClassData(cl, packageName);
					FMModel.getInstance().getClasses().add(fmClass);
				}

				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration) ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
				}
			}

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {
					Package ownedPackage = (Package) ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null)
						// only packages with stereotype BusinessApp are candidates for metadata
						// extraction and code generation:
						processPackage(ownedPackage, packageName);
				}
			}

			/**
			 * @ToDo: Process other package elements, as needed
			 */
		}
	}

	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null)
			throw new AnalyzeException("Classes must have names!");

		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString(), "", "", false, false, false, false);

		// StereotypesHelper.getStereotypedElement(cl);

		Stereotype controllerStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, "Controller");

		if (controllerStereotype != null) {
			fmClass.setControllerName(controllerStereotype.getName());
			List showPropertiesList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype,
					"controllerName");
			if (showPropertiesList.size() > 0) {
				fmClass.setControllerName(showPropertiesList.get(0).toString());
			}

			showPropertiesList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "label");
			if (showPropertiesList.size() > 0) {
				fmClass.setLabel(showPropertiesList.get(0).toString());
			}
		}
		
		if(controllerStereotype != null) {
			List incrementBrojFaktureList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "incrementBrojFakture");
			if (incrementBrojFaktureList.size() > 0) {
				fmClass.setIncrementBrojFakture((Boolean)incrementBrojFaktureList.get(0));
			}
			
			List convertToDateList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "convertToDate");
			if (convertToDateList.size() > 0) {
				fmClass.setConvertToDate((Boolean)convertToDateList.get(0));
			}
			
			List findStavkeCenovnikaList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "findStavkeCenovnika");
			if (findStavkeCenovnikaList.size() > 0) {
				fmClass.setFindStavkeCenovnika((Boolean)findStavkeCenovnikaList.get(0));
			}
			
			List findStavkeFaktureList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "findStavkeFakture");
			if (findStavkeFaktureList.size() > 0) {
				fmClass.setFindStavkeFakture((Boolean)findStavkeCenovnikaList.get(0));
			}
			
			
			List setuUpList = StereotypesHelper.getStereotypePropertyValue(cl, controllerStereotype, "setUpFaktura");
			if (setuUpList.size() > 0) {
				fmClass.setSetUp((Boolean)setuUpList.get(0));
			}
		}
		
		Stereotype standardFormStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, "StandardForm");
		
		if(standardFormStereotype != null) {
			List createList = StereotypesHelper.getStereotypePropertyValue(cl, standardFormStereotype, "create");
			if (createList.size() > 0) {
				fmClass.setCreateSF((Boolean)createList.get(0));
			}
			
			List editList = StereotypesHelper.getStereotypePropertyValue(cl, standardFormStereotype, "edit");
			if (editList.size() > 0) {
				fmClass.setEditSF((Boolean)editList.get(0));
			}
			
			List deleteList = StereotypesHelper.getStereotypePropertyValue(cl, standardFormStereotype, "delete");
			if (deleteList.size() > 0) {
				fmClass.setDeleteSF((Boolean)deleteList.get(0));
			}
			
			List showMenuList = StereotypesHelper.getStereotypePropertyValue(cl, standardFormStereotype, "showMenu");
			if (showMenuList.size() > 0) {
				fmClass.setShowMenu((Boolean)showMenuList.get(0));
			}
			
		}

		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			fmClass.addProperty(prop);

			if (prop.isNext()) {
				fmClass.getPropertiesManyToOne().add(prop);
			}

			if (!prop.isNext() && !prop.isZoom()) {
				fmClass.getClassProperties().add(prop);
			}

			if (!prop.isZoom()) {
				fmClass.getPropertiesNoZoom().add(prop);
			}
		}

		/**
		 * @ToDo: Add import declarations etc.
		 */
		return fmClass;
	}

	private FMProperty getPropertyData(Property p, Class cl) throws AnalyzeException {
		String attName = p.getName();
		if (attName == null)
			throw new AnalyzeException("Properties of the class: " + cl.getName() + " must have names!");
		Type attType = p.getType();
		if (attType == null)
			throw new AnalyzeException("Property " + cl.getName() + "." + p.getName() + " must have type!");

		String typeName = attType.getName();
		if (typeName == null)
			throw new AnalyzeException("Type ot the property " + cl.getName() + "." + p.getName() + " must have name!");

		int lower = p.getLower();
		int upper = p.getUpper();

		FMProperty prop = new FMProperty(attName, typeName, p.getVisibility().toString(), lower, upper, false, false,
				"", false, false, false, "", "", "");

		/*------------------------------------------------------------------------------------------------------------------------------ */
		Stereotype zoomStereotype = StereotypesHelper.getAppliedStereotypeByString(p, "Zoom");
		if (zoomStereotype != null) {
			prop.setZoom(true);
			List showPropertiesList = StereotypesHelper.getStereotypePropertyValue(p, zoomStereotype, "mappedBy");
			if (showPropertiesList.size() > 0) {
				prop.setMappedBy(showPropertiesList.get(0).toString());
			}
		}

		// uzimamo kontrolere iz ManyToOne veza(tome sluzi controllerNameProp)
		Stereotype nextStereotype = StereotypesHelper.getAppliedStereotypeByString(p, "Next");
		if (nextStereotype != null) {
			prop.setNext(true);
			List showPropertiesList = StereotypesHelper.getStereotypePropertyValue(p, nextStereotype,
					"controllerNameProp");
			if (showPropertiesList.size() > 0) {
				prop.setControllerName(showPropertiesList.get(0).toString());
			}
		}

		if (nextStereotype != null) {
			List labelList = StereotypesHelper.getStereotypePropertyValue(p, nextStereotype, "label");
			if (!labelList.isEmpty()) {
				String label = labelList.get(0).toString();
				prop.setLabel(label);
			}
		}

		if (nextStereotype != null) {
			List labelList = StereotypesHelper.getStereotypePropertyValue(p, nextStereotype, "lookupName");
			if (!labelList.isEmpty()) {
				String label = labelList.get(0).toString();
				prop.setLookupName(label);
			}
		}

		Stereotype editableStereotype = StereotypesHelper.getAppliedStereotypeByString(p, "Editable");
		if (editableStereotype != null) {
			prop.setEditable(true);
		}

		Stereotype readonlyStereotype = StereotypesHelper.getAppliedStereotypeByString(p, "ReadOnly");
		if (readonlyStereotype != null) {
			prop.setReadonly(true);
		}

		Stereotype lookupStereotype = StereotypesHelper.getAppliedStereotypeByString(p, "Lookup");
		if (lookupStereotype != null) {
			prop.setLookup(true);
		}

		if (editableStereotype != null) {
			List labelList = StereotypesHelper.getStereotypePropertyValue(p, editableStereotype, "label");
			if (!labelList.isEmpty()) {
				String label = labelList.get(0).toString();
				prop.setLabel(label);
			}
		} else if (readonlyStereotype != null) {
			List labelList = StereotypesHelper.getStereotypePropertyValue(p, readonlyStereotype, "label");
			if (!labelList.isEmpty()) {
				String label = labelList.get(0).toString();
				prop.setLabel(label);
			}
		}

		/*
		 * Stereotype sfStereotype = StereotypesHelper.getAppliedStereotypeByString(p,
		 * "UIElement"); if (sfStereotype != null) { List showPropertiesList =
		 * StereotypesHelper.getStereotypePropertyValue(p, sfStereotype, "label"); if
		 * (showPropertiesList.size() > 0) {
		 * prop.setLabel(showPropertiesList.get(0).toString()); } }
		 */

		return prop;
	}

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size() - 1; i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() + " must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}

	public String getFilePackage() {
		return filePackage;
	}

	public void setFilePackage(String filePackage) {
		this.filePackage = filePackage;
	}

}
