package nbradham.inv;

import java.util.HashMap;

final class ItemCategory {

	private final String name;
	private final HashMap<String, DataField> fields = new HashMap<>();

	ItemCategory(String iName) {
		name = iName;
	}

	String name() {
		return name;
	}

	HashMap<String, DataField> fields() {
		return fields;
	}
}