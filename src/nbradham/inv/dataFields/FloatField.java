package nbradham.inv.dataFields;

final class FloatField extends DataField {

	static final String TYPE = "float";

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Float defVal() {
		return 0f;
	}

	@Override
	public String restrictions() {
		return String.format("[%f, %f]", Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
	}
}