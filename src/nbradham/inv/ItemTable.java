package nbradham.inv;

import java.util.ArrayList;

final class ItemTable {

	private final ArrayList<ItemModel> models = new ArrayList<>();
	private final String name;

	ItemTable(String tableName) {
		name = tableName;
	}

	@Override
	public String toString() {
		return name;
	}

	ArrayList<ItemModel> getModels() {
		return models;
	}
}