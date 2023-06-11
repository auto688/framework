package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
    private static ReadConfig instance;
    private Properties pro;

    private ReadConfig() {
        File src = new File("./Configurations/config.properties");
        try {
            FileInputStream fis = new FileInputStream(src);
            pro = new Properties();
            pro.load(fis);
        } catch (IOException e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public static synchronized ReadConfig getInstance() {
        if (instance == null) {
            instance = new ReadConfig();
        }
        return instance;
    }

    // Other methods of the class
    public String getProperty(String key) {
        return pro.getProperty(key);
    }
}

