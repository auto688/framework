package utilities;
import java.io.IOException;
import java.util.logging.*;

public class Log {
    private static Log instance;
    private Logger logger;

    private Log() {
    	logger = Logger.getLogger(Log.class.getName());
        logger.setUseParentHandlers(false);

        // Console Handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        // File Handler
        try {
            FileHandler fileHandler = new FileHandler("Logs/log.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Log getInstance() {
        if (instance == null) {
            synchronized (Log.class) {
                if (instance == null) {
                    instance = new Log();
                }
            }
        }
        return instance;
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void error(String message) {
        log(Level.SEVERE, message);
    }

    private void log(Level level, String message) {
        logger.log(level, message);
    }

    private class SimpleFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return String.format("[%1$tF %1$tT] [%2$-7s] %3$s %n",
                    new java.util.Date(record.getMillis()),
                    record.getLevel().getLocalizedName(),
                    record.getMessage());
        }
    }
}

