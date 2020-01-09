package ie.gov.agriculture.fisheries.la.capacityservice.config;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

/**
 * ModelMapper Converter for Date types
 * 
 * @author garret.okeeffe
 *
 */
public class MapperConverter {
	public static Converter<Date, String> getDateMapper() {
		return new AbstractConverter<Date, String>() {
			@Override
			protected String convert(Date source) {
				return (source != null) ? getDateIso8601(source) : null;
			}
		};
	}

	/**
	 * Method returns the date in ISO8601 format e.g. 2020-01-08T12:37:57.921Z
	 * 
	 * @param date - the date to be converted. If its an SQL date, the (lack of) time component is handled.
	 * @return ISO8601 or an empty string if null date was passed.
	 */
	public static String getDateIso8601(Date date) {
		final ZoneId id = ZoneId.systemDefault();

		if (date instanceof java.sql.Date) { // sql.Date has no time component
			return ZonedDateTime.of(((java.sql.Date) date).toLocalDate().atStartOfDay(), id).format(DateTimeFormatter.ISO_INSTANT);
		} 
		else {
			return ZonedDateTime.ofInstant(date.toInstant(), id).format(DateTimeFormatter.ISO_INSTANT);
		}
	}
}
