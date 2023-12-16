package nbradham.inv.dataFields;

import java.util.Date;

final class DateField extends DataField {

	static final String TYPE = "date";

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public Object defVal() {
		return new Date();
	}

	@Override
	public String restrictions() {
		return "None";
	}
}