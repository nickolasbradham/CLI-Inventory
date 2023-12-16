package nbradham.inv.dataFields;

import java.util.Arrays;
import java.util.HashSet;

public abstract class DataField {

	public static final HashSet<String> TYPES = new HashSet<>(Arrays.asList(new String[] { BooleanField.TYPE,
			DateField.TYPE, FloatField.TYPE, IntField.TYPE, SelectionField.TYPE, StringField.TYPE }));

	public abstract String type();

	public abstract Object defVal();

	public abstract String restrictions();

	public String defValStr() {
		return defVal().toString();
	};
}