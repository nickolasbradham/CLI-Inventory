package nbradham.inv;

@FunctionalInterface
public interface Validator {

	boolean isValid(Object f);
}