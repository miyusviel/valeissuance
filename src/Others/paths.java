package Others;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.DBConn;

public class paths {

    static String path;

    public static String getPicPathConfig() {

        String filePath = System.getProperty("user.dir") + "\\config.properties";

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }

        path = properties.getProperty("picpathconfig");
        return path;
    }
    
    
    public static void main(String[] args) {

    }
    
}
