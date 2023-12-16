package nbradham.inv.dataFields;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import nbradham.inv.DateGetter;
import nbradham.inv.Manager;
import nbradham.inv.Validator;

public final class DateField extends DataField {

	public static final String TYPE = "date", S_NOW = "now", S_CUR = "cur";
	public static final DateGetter DG_CUR = () -> LocalDate.now();

	private final DateGetter min, max, def;

	public DateField(DateGetter setMin, DateGetter setMax, DateGetter defaultVal) {
		min = setMin;
		max = setMax;
		def = defaultVal;
	}

	@Override
	public String type() {
		return TYPE;
	}

	@Override
	public LocalDate defVal() {
		return def.getDate();
	}

	@Override
	public String restrictions() {
		return String.format("[%s,%s]", getterToStrable(min), getterToStrable(max));
	}

	@Override
	public String defValStr() {
		return (String) getterToStrable(def);
	}

	public static DateGetter getDateGetter(DateGetter defaultVal, String title, Validator addCheck) {
		String str = Manager.getStr(String.format(
				"Enter %s (leave blank for default [%s], enter \"%s\" for today's date [%s], enter \"%s\" for the current date when editing):",
				title, getterToStrable(defaultVal), S_NOW, DG_CUR.getDate(), S_CUR), s -> {
					LocalDate ld;
					try {
						ld = LocalDate.parse(s);
					} catch (DateTimeParseException e) {
						boolean empty = s.isEmpty();
						return (s.equals(S_NOW) || s.equals(S_CUR) || empty) && addCheck
								.isValid(empty ? defaultVal.getDate().toString() : LocalDate.now().toString());
					}
					return addCheck.isValid(ld.toString());
				}, "Invalid value. Try again:");
		switch (str) {
		case S_NOW:
			LocalDate now = LocalDate.now();
			return () -> now;
		case S_CUR:
			return DG_CUR;
		case "":
			return defaultVal;
		}
		LocalDate date = LocalDate.parse(str);
		return () -> date;
	}

	private static Object getterToStrable(DateGetter defaultVal) {
		return defaultVal == DG_CUR ? "Edit Time" : defaultVal.getDate();
	}
}