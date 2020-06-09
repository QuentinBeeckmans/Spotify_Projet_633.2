package logsConstructor;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class extends Formatter to custom output logger
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class CustomLogFormatter extends Formatter {

	private String className;

	/**
	 * Class constructor
	 * 
	 * @param className the String of className
	 */
	public CustomLogFormatter(String className) {

		super();

		this.className = className;

	}

	/**
	 * Public String format method
	 * 
	 * @return String
	 */
	@Override
	public String format(LogRecord record) {

		StringBuffer sb = new StringBuffer();

		Date date = new Date(record.getMillis());
		sb.append(date.toString());
		sb.append(" ; ");

		sb.append("class " + className);
		sb.append(" ; ");

		sb.append(record.getLevel());
		sb.append(" ; ");

		sb.append(record.getMessage());

		sb.append("\r\n");

		return sb.toString();
	}
}
