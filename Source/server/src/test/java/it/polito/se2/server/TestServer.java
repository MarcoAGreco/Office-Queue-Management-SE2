package it.polito.se2.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.CDATASection;

import it.polito.se2.counter.CounterClient;
import it.polito.se2.counter.CounterGUI;
import it.polito.se2.database.DatabaseMaster;
import it.polito.se2.database.DatabaseQuery;
import it.polito.se2.ticketmachine.TicketMachineClient;
import net.jodah.concurrentunit.Waiter;



class TestServer {
	private Connection connection;
	private Date testDate;
	public static final String DATABASE_CONFIG_TEST = "database_test.properties";
	public CounterGUI frame;
	

	@BeforeEach
	public void init() throws IOException, ClassNotFoundException, SQLException {
		String url, username, password;
		Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(DATABASE_CONFIG_TEST);
		properties.load(in);  // load the file .properties

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
		testDate = new Date(2019 - 1900, 10 - 1, 18); // check the documentation of the constructor for details

	}

	@Test
	void testTicketRequest() throws SQLException {
		DatabaseQuery query = new DatabaseQuery(connection);
		int ticketNumber = query.getTicketId(testDate);
		
		assertEquals(5, ticketNumber);
	}

	@Test
	void testInsertTicket() throws SQLException {
		DatabaseQuery db = new DatabaseQuery(connection);
		db.insertTicket(300, "TEST_TYPE");
		
		String query = "SELECT count(*) as cntTestTicket FROM Ticket WHERE TicketID=300";
		Statement stat = connection.createStatement(); 
        ResultSet result = stat.executeQuery(query);
        int value = 0;
        while(result.next()) { 
        	value = result.getInt("cntTestTicket");
        }
        assertEquals(1, value);
        
        try { 
			String query2 = "DELETE FROM Ticket WHERE TicketID=300";
			PreparedStatement stat2 = connection.prepareStatement(query2);
			stat2.executeUpdate(query2);
        } catch(SQLException e) {
            e.printStackTrace();
        }        
	}
	
	@Test
	void testServeNext() throws SQLException {
		DatabaseQuery db = new DatabaseQuery(connection);
		String ticket = db.selectTicketToServe(1, testDate);
		//System.out.println("hi :"+ ticket);
		assertEquals("2P", ticket);
		
	try { 
		String query = "UPDATE Ticket SET CounterAssigned = NULL WHERE Ticket.TicketID = 2 AND Ticket.Date = '2019-10-18'";
		PreparedStatement stat = connection.prepareStatement(query);
		stat.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }   
	}
	
	@Test
	void testIntegration() throws Throwable {
		final Waiter waiter = new Waiter();

		// send request to server to ticket type "TEST_TYPE"
		new Thread(() -> {
			TicketMachineClient client = new TicketMachineClient(null, "localhost", 1500);
			client.sendTicketRequest("TEST_TYPE");
			waiter.resume();
		}).start();

		// server: handle request from client
		try (
			ServerSocket serverSocket = new ServerSocket(1500);
			Socket socketClient = serverSocket.accept();
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		) {
			// receive request from client
			String msg = clientReader.readLine();
			// System.out.println("Message received: " + msg);

			// parse json
			JSONObject json = new JSONObject(msg);
			String operation = json.getString("operation");
			JSONObject content = json.getJSONObject("content");
			String reqType = content.getString("request_type");

			// check the content of the request
			if(msg != null && !msg.isEmpty()) {
				waiter.assertEquals("new_ticket", operation);
				waiter.assertEquals("TEST_TYPE", reqType);

				// check correctness of ticket number returned
				DatabaseQuery query = new DatabaseQuery(connection);
				int ticketNumber = query.getTicketId(testDate);

				waiter.assertEquals(5, ticketNumber);
			} else {
				waiter.fail("Unable to read client request");
			}
		} catch (Exception e) {
			waiter.fail();
		}
		waiter.await();
	}
	
	@Test
	void testSetReqTypeToCounter() throws Throwable {
		final Waiter waiter = new Waiter();		
		
		
		// send request to server
		new Thread(() -> {
			CounterClient client = new CounterClient(null, "localhost", 1500);
			client.setId(1);		
			String[] reqTypes = new String[2];
			reqTypes[0] = "Accounting";
			reqTypes[1] = "";
			client.setReqTypeToCounter(reqTypes);
		}).start();

		// server: handle request from client
		try (
			ServerSocket serverSocket = new ServerSocket(1500);
			Socket socketClient = serverSocket.accept();
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		) {
			// receive request from client
			String msg = clientReader.readLine();
			 System.out.println("Message received: " + msg);

			// parse json
			JSONObject json = new JSONObject(msg);
			String operation = json.getString("operation");
			JSONObject content = json.getJSONObject("content");
			String reqType = content.getString("request_type");
			int counterId = content.getInt("id");

			// check the content of the request
			if(msg != null && !msg.isEmpty()) {
				//System.out.println(operation + " "+ reqType);
				waiter.assertEquals("counter_setup", operation);
				waiter.assertEquals("Accounting", reqType);
				waiter.assertEquals(1, counterId);
				
			} else {
				waiter.fail("Unable to read client request");
			}
			serverSocket.close();
			socketClient.close();
		} catch (Exception e) {			
			waiter.fail();			
		}		
		waiter.resume();
		waiter.await();		
	}
	
}
