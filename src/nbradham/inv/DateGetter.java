package nbradham.inv;

import java.time.LocalDate;

@FunctionalInterface
public interface DateGetter {
	LocalDate getDate();
}