package ie.gov.agriculture.fisheries.la.capacityservice.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	static final ZoneId UTC_ZONE = ZoneId.of("UTC");
	
	public static Converter<Date, String> getDateMapper() {
		return new AbstractConverter<Date, String>() {
			@Override
			protected String convert(Date source) {
				return (source != null) ? getDateIso8601(source) : null;
			}
		};
	}
	
	
	public static Converter<LocalDate, String> geLocaltDateMapper() {
		return new AbstractConverter<LocalDate, String>() {
			@Override
			protected String convert(LocalDate source) {
				return getDateIso8601(source);
			}
		};
	}


	/**
	 * Method returns the date in ISO8601 format e.g. 2020-01-08T12:37:57.921Z
	 * 
	 * @param date - the date to be converted. If its an SQL date, the (lack of) time component is handled.
	 * @return ISO8601.
	 */
	public static String getDateIso8601(Date date) {
		if (date instanceof java.sql.Date) { // sql.Date has no time component
			return ZonedDateTime.of(((java.sql.Date) date).toLocalDate().atStartOfDay(), UTC_ZONE).format(DateTimeFormatter.ISO_INSTANT);
		} 
		else {
			return ZonedDateTime.ofInstant(date.toInstant(), UTC_ZONE).format(DateTimeFormatter.ISO_INSTANT);
		}
	}
	
	
	/**
	 * Method returns the date in ISO8601 format e.g. 2020-01-08T12:37:57.921Z
	 * 
	 * Note: requirement is to always return the full date-time format with TZ. LocalDate does not have a time component so this is added and also the TZ/ 
	 * 
	 * @param date - the date to be converted.
	 * @return ISO8601.
	 */
	public static String getDateIso8601(LocalDate date) {
		if (date == null) {
			return null;
		}
		LocalDateTime localDateTime = date.atStartOfDay();
		return ZonedDateTime.of(localDateTime, UTC_ZONE).format(DateTimeFormatter.ISO_INSTANT);		
	}
}