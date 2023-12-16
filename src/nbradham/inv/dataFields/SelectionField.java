package nbradham.inv.dataFields;

final class SelectionField extends DataField {

	static final String TYPE = "selection";

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Object defVal() {
		return null;
	}

	@Override
	public String restrictions() {
		return "[]";
	}
}