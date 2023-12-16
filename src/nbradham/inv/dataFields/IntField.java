package nbradham.inv.dataFields;

public final class IntField extends DataField {

	public static final String TYPE = "int";

	private final int min, max, def;

	public IntField(int setMin, int setMax, int setDefault) {
		min = setMin;
		max = setMax;
		def = setDefault;
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Integer defVal() {
		return def;
	}

	@Override
	public String restrictions() {
		return String.format("[%d, %d]", min, max);
	}
}