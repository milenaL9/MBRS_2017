package myplugin.generator.fmmodel;

public class FMProperty extends FMElement {
	// Property type
	private String type;

	// Property visibility (public, private, protected, package)
	private String visibility;

	// Multiplicity (lower value)
	private Integer lower;

	// Multiplicity (upper value)
	private Integer upper;

	private boolean zoom;

	private boolean next;
	private String mappedBy;

	private boolean readonly;
	private boolean editable;
	private boolean lookup;

	private String label;

	private String lookupName;

	// za imena kontrolera
	private String controllerName;

	private boolean isDateObican;
	private boolean isDateMin;
	private boolean isDateMax;

	private boolean radioBtnPolja;
	private String vrednost1RBtn;
	private String vrednost2RBtn;
	private String vrednost3RBtn;

	/**
	 * @ToDo: Add length, precision, unique... whatever is needed for ejb class
	 *        generation Also, provide these meta-attributes or tags in the modeling
	 *        languange metaclass or stereotype
	 */

	public FMProperty(String name, String type, String visibility, int lower, int upper, boolean zoom, boolean next,
			String mappedBy, boolean readonly, boolean editable, boolean lookup, String controllerName, String label,
			String lookupName) {
		super(name);
		this.type = type;
		this.visibility = visibility;

		this.lower = lower;
		this.upper = upper;

		this.zoom = zoom;
		this.next = next;
		this.mappedBy = mappedBy;
		this.readonly = readonly;
		this.editable = editable;
		this.lookup = lookup;
		this.controllerName = controllerName;
		this.label = label;
		this.lookupName = lookupName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public boolean isZoom() {
		return zoom;
	}

	public void setZoom(boolean zoom) {
		this.zoom = zoom;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isLookup() {
		return lookup;
	}

	public void setLookup(boolean lookup) {
		this.lookup = lookup;
	}

	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}

	public boolean isDateObican() {
		return isDateObican;
	}

	public void setIsDateObican(boolean isDateObican) {
		this.isDateObican = isDateObican;
	}

	public boolean isDateMin() {
		return isDateMin;
	}

	public void setDateMin(boolean isDateMin) {
		this.isDateMin = isDateMin;
	}

	public boolean isDateMax() {
		return isDateMax;
	}

	public void setDateMax(boolean isDateMax) {
		this.isDateMax = isDateMax;
	}

	public boolean isRadioBtnPolja() {
		return radioBtnPolja;
	}

	public void setRadioBtnPolja(boolean radioBtnPolja) {
		this.radioBtnPolja = radioBtnPolja;
	}

	public String getVrednost1RBtn() {
		return vrednost1RBtn;
	}

	public void setVrednost1RBtn(String vrednost1rBtn) {
		vrednost1RBtn = vrednost1rBtn;
	}

	public String getVrednost2RBtn() {
		return vrednost2RBtn;
	}

	public void setVrednost2RBtn(String vrednost2rBtn) {
		vrednost2RBtn = vrednost2rBtn;
	}

	public String getVrednost3RBtn() {
		return vrednost3RBtn;
	}

	public void setVrednost3RBtn(String vrednost3rBtn) {
		vrednost3RBtn = vrednost3rBtn;
	}

}
