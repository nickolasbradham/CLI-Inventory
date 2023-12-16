package nbradham.inv.dataFields;

final class IntField extends DataField {

	static final String TYPE = "int";

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Byte defVal() {
		return 0;
	}

	@Override
	public String restrictions() {
		return String.format("[%d, %d]", Byte.MIN_VALUE, Byte.MAX_VALUE);
	}
}