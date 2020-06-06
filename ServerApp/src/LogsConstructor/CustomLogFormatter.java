package LogsConstructor;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Class customFormatter Herited of Formatter Format data for logger
 * 
 * @author Administrator
 *
 */
public class CustomLogFormatter extends Formatter {

	private String className;

	/**
	 * Class constructor
	 * 
	 * @param className
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
		sb.append(" ; ");

		sb.append("\r\n");

		return sb.toString();
	}
}
