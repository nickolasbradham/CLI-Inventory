package nbradham.inv.dataFields;

public final class FloatField extends DataField {

	public static final String TYPE = "float";

	private final float min, max, def;

	public FloatField(float setMin, float setMax, float setDefault) {
		min = setMin;
		max = setMax;
		def = setDefault;
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Float defVal() {
		return def;
	}

	@Override
	public String restrictions() {
		return String.format("[%f, %f]", min, max);
	}
}