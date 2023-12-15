package nbradham.inv;

import java.util.HashMap;

final class Manager {

	private void start() {
		StringBuilder token = new StringBuilder();
		String opts;
		HashMap<Character, String> params = new HashMap<>();
		boolean run = true;
		while (run) {
			token.setLength(0);
			opts = null;
			params.clear();
			//TODO Gen tokens, parse opts, parse params, parse command, execute.
		}
	}

	public static void main(String[] args) {
		new Manager().start();
	}
}