package nbradham.inv;

@FunctionalInterface
public interface Validator {

	boolean isValid(String str);
}