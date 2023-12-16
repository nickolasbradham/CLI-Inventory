package nbradham.inv.dataFields;

final class StringField extends DataField {

	static final String TYPE = "string";

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Object defVal() {
		return "";
	}

	@Override
	public String restrictions() {
		return "None";
	}
}