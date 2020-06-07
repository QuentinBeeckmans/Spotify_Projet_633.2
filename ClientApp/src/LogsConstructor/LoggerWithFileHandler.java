package LogsConstructor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class LoggerWithFileHandler logger implementation
 */
public class LoggerWithFileHandler {

	private File logDirectory;
	private FileHandler fh;
	private CustomLogFormatter myFormatter;
	private Logger logger;
	private String fileOFHandlerName = "";
	private String className;
	private String msg;
	private String msgSystem;
	private Level logLevel;
	private String date;
	
	/**
	 * Class constructor
	 * 
	 * @param fileName
	 */
	public LoggerWithFileHandler(String fileName) {
		
		Date dNow = new Date( );
	      SimpleDateFormat ft = 
	      new SimpleDateFormat ("yyyy_MM");
	    date = ft.format(dNow);
	    
	    
		try {

			logDirectory = new File(fileName);

			if (!logDirectory.exists()) {
				logDirectory.mkdirs();				
			} 
						
			File[] files = logDirectory.listFiles();
			
			if (files.length > 0) {
				File lastFile = files[files.length - 1];
				boolean testLastFile = lastFile.getName().substring(0, lastFile.getName().indexOf(".txt"))
						.equals(date);
	
				if (lastFile.exists() && testLastFile) {
					fileOFHandlerName = lastFile.getName();
				
				} else {
					fileOFHandlerName = date + ".txt";
				}
			} else {
				fileOFHandlerName = date + ".txt";
			}

			fh = new FileHandler(logDirectory.getAbsolutePath() + "\\" + fileOFHandlerName, true);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Private format method Format log messages
	 * 
	 * @return
	 */
	private String formatMsg() {

		String msgFormated = msg + ";" + msgSystem;

		return msgFormated;
	}

	/**
	 * Private void level of log setter method
	 * 
	 * @param logLevel
	 */
	private void setLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Public void addHandel method instentiate logger
	 * 
	 * @param className
	 * @param logLevel
	 * @param msg
	 * @param msgSystem
	 */
	public void addHandler(String className, Level logLevel, String msg, String msgSystem) {

		setLevel(logLevel);
		this.msg = msg;
		this.className = className;
		this.msgSystem = msgSystem;

		myFormatter = new CustomLogFormatter(this.className);
		fh.setFormatter(myFormatter);

		writeLog();

	}

	/**
	 * Private void writter logger method writte log in file
	 */
	private void writeLog() {

		logger = Logger.getLogger(this.className);

		logger.addHandler(fh);

		logger.log(logLevel, formatMsg());
	
	}

	public void closeHandler () {
		fh.close();
	}
}
