package Connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBConn {

    static String password, user;
    static String url;
    static Connection conn;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (java.lang.ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Database Driver error. Please check the configuration!");
        }

        try {

            String filePath = System.getProperty("user.dir") + "\\config.properties";

            Properties properties = new Properties();

            try {
                properties.load(new FileInputStream(filePath));
            } catch (IOException ex) {
                Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String port = properties.getProperty("port");
            String host = properties.getProperty("host");
            String database = properties.getProperty("database");

            url = "jdbc:mysql://" + host + ":" + port + "/" + database;
//            user = "root";
//            password = "Master2007@@@";
            user = "noreco1_db_user";
            password = "It'sMoreFunInBindoy";
            
            conn = DriverManager.getConnection(url, user, password);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return conn;
    }
}
