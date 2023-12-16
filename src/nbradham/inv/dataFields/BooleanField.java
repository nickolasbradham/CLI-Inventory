package nbradham.inv.dataFields;

public final class BooleanField extends DataField {

	public static final String TYPE = "bool";

	private final boolean def;

	public BooleanField(boolean defaultVal) {
		def = defaultVal;
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Object defVal() {
		return def;
	}

	@Override
	public String restrictions() {
		return "f or t";
	}
}