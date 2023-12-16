package nbradham.inv.dataFields;

import java.util.regex.Pattern;

public final class StringField extends DataField {

	public static final String TYPE = "string";

	private final Pattern pat;
	private final String def;

	public StringField(Pattern regex, String defVal) {
		pat = regex;
		def = defVal;
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public String defVal() {
		return def;
	}

	@Override
	public String restrictions() {
		return pat.toString();
	}
}