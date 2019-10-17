package it.polito.se2.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	private static String url;
    private static String username;
    private static String password;
    
    public Database(String configFile) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(configFile);
		properties.load(in); // load the file .properties
        
        String driver = properties.getProperty("jdbc.driver");
        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        if (username == null)
        	username = "";
        password = properties.getProperty("jdbc.password");
        if (password == null)
        	password = "";
        
        if (driver != null) 
            Class.forName(driver);
        
        DriverManager.getConnection(url, username, password);
    }

}
