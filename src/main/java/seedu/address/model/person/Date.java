package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Represents a Person's next appointment date in the address book.
 * Guarantees: immutable; is always valid
 */
public class Date {
    private static final String DATE_PATTERN =
            "^([1-9]|[12][0-9]|3[01])/([1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final String FORMAT_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public final LocalDateTime value;

    /**
     * Constructs a {@code Date} from a LocalDateTime.
     *
     * @param date A valid LocalDateTime.
     */
    public Date(LocalDateTime date) {
        requireNonNull(date);
        value = date;
    }

    @Override
    public String toString() {
        return value != LocalDateTime.MIN ? value.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm")) : "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }


    /**
     * Parses a date string into a {@code LocalDateTime} object.
     *
     * <p>This method checks if the provided date string matches the expected format and
     * validates the values of the date and time. The expected format is 'd/M/yyyy HHmm'.
     * For example, '2/12/2024 1800' represents 2nd December 2024 at 18:00 hours.</p>
     *
     * @param date The date string to be parsed.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException If the date format is invalid or if the date and time values are incorrect.
     */
    public static LocalDateTime parseDateString(String date) throws ParseException {
        validateDateFormat(date);
        return parseDateTime(date);
    }

    /**
     * Validates the date format and checks if the values are correct.
     *
     * @param date The date string to validate.
     * @throws ParseException If the date format is invalid or if the date and time values are incorrect.
     */
    private static void validateDateFormat(String date) throws ParseException {
        if (!date.matches(DATE_PATTERN)) {
            if (!date.matches(FORMAT_PATTERN)) {
                throw new ParseException("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                        + "For example, '2/12/2024 1800'.");
            } else {
                throw new ParseException("Invalid date or time values! "
                        + "Ensure day, month, hour, and minute ranges are correct.");
            }
        }
    }


    /**
     * Parses the date string into a {@code LocalDateTime} object.
     *
     * @param date The date string to parse.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException If the date and time values are incorrect.
     */
    private static LocalDateTime parseDateTime(String date) throws ParseException {
        String[] dateAndTime = date.split(" ");
        String[] dateParts = dateAndTime[0].split("/");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        // Check month-day combinations, including leap year validation
        if (month == 2) {
            if (day == 29 && !Year.of(year).isLeap()) {
                throw new ParseException("Invalid date: " + Month.of(month) + " "
                        + day + " is only valid in leap years.");
            } else if (day > 29) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 29 days.");
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 30 days.");
            }
        }


        return LocalDateTime.parse(date, FORMATTER);

    }
}
