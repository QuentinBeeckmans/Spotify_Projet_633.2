package client;



import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {



    @Override
    public String format(LogRecord record) {

        StringBuffer sb = new StringBuffer();

        Date date = new Date(record.getMillis());
        sb.append(date.toString());
        sb.append(" ; ");

        sb.append(record.getLevel());
        sb.append(" ; ");

        sb.append(record.getMessage());
        sb.append(" ; ");

        sb.append(record.getSourceMethodName());
        sb.append("\r\n");


        return sb.toString();
    }
}
