package nbradham.inv.dataFields;

import java.util.Arrays;

public final class SelectionField extends DataField {

	public static final String TYPE = "select";

	private final String[] vals;
	private final int def;

	public SelectionField(String[] values, String setDefault) {
		def = Arrays.binarySearch(vals = values, setDefault);
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Object defVal() {
		return vals[def];
	}

	@Override
	public String restrictions() {
		return Arrays.toString(vals);
	}
}