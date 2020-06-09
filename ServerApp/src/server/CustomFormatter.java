package server;

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
public class CustomFormatter extends Formatter {

	public CustomFormatter() {
		super();
	}

	@Override
	public String format(LogRecord record) {

		StringBuffer sb = new StringBuffer();

		Date date = new Date(record.getMillis());
		sb.append(date.toString());
		sb.append(";");

		sb.append(record.getSourceClassName());
		sb.append(";");

		sb.append(record.getLevel().getName());
		sb.append(";");

		sb.append(formatMessage(record));
		sb.append("\r\n");

		return sb.toString();
	}
}