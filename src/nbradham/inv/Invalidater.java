package nbradham.inv;

@FunctionalInterface
interface Invalidater {

	boolean notValid(String str);
}