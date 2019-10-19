package it.polito.se2.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.CDATASection;

import it.polito.se2.database.DatabaseMaster;
import it.polito.se2.database.DatabaseQuery;

class TestServer {
	private Connection connection;
	public static final String DATABASE_CONFIG_TEST = "database_test.properties";
	
	@BeforeEach
	public void init() throws IOException, ClassNotFoundException, SQLException {
		String url, username, password;
		Properties properties = new Properties();
        FileInputStream in = new FileInputStream(DATABASE_CONFIG_TEST);
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
        
        connection = DriverManager.getConnection(url, username, password);
	}
	@Test
	void test() throws SQLException {
		DatabaseQuery query = new DatabaseQuery(connection);
		Date date = new Date(2019 - 1900, 10 - 1, 18); // check the documentation of the constructor for details
		int ticketNumber = query.getTicketId(date);
		
		assertEquals(5, ticketNumber);
	}
}
