package it.polito.se2.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseQuery 
{
	private Connection connection;
	
	public DatabaseQuery(Connection connection) {
		this.connection = connection;
	}
	
    public synchronized int getTicketId(Date todayDate) throws SQLException { 
//        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());
//        System.out.println(todayDate);

        int max = 0;
        String query = "SELECT MAX(TicketID) AS MaxTicket FROM Ticket WHERE Date = '" + todayDate + "'"; 
        Statement stat = connection.createStatement(); 
        ResultSet result = stat.executeQuery(query);
        
        while(result.next()) { 
        	max = result.getInt("MaxTicket");
        }
        
        return max + 1;
    }
    
    public synchronized void insertTicket(int id, String reqType) throws SQLException {
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Time todayTime = new java.sql.Time(Calendar.getInstance().getTime().getTime()); 
        
    	try {       
    		String query = "INSERT INTO Ticket(TicketID, RequestType, Date, Time) VALUES (" + id + ", '" + reqType + "', '" 
    																				+ todayDate + "', '" + todayTime + "')";
            PreparedStatement stat = connection.prepareStatement(query);
            stat.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public synchronized int setupCounter(String reqType) throws SQLException {
    	int id=-1;        
    	try {       
	    	String query =  "LOCK TABLES Counter WRITE;"; 
	    	PreparedStatement pStat = connection.prepareStatement(query);
	    	pStat.executeUpdate(query);
            
            query = "SELECT MAX(CounterID) AS MaxID FROM Counter"; 
            Statement stat = connection.createStatement(); 
    		ResultSet result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			id = result.getInt("MaxID");
    		}
    		
    		//Create a new ID
      		id += 1;
      		
      		for(String type : reqType.split(("/"))){
      			query = "INSERT INTO Counter(CounterID, RequestType) VALUES (" + id + ", '" + type +"')";
          		pStat = connection.prepareStatement(query);
          		stat.executeUpdate(query);
      		}
    		
    		query =  "UNLOCK TABLES;"; 
	    	pStat = connection.prepareStatement(query);
	    	pStat.executeUpdate(query);
    		
        } catch(SQLException e) {
            e.printStackTrace();
        }   
    	
    	return id;
    }
    
    public void updateQueueInfo() throws SQLException {   	
    	try {
    		String queryA = "DROP VIEW IF EXISTS QueueInfo;";    		
    		String queryB = "CREATE VIEW QueueInfo AS "
    				+ "(SELECT COUNT(*) AS QueueLenght, RequestType as RequestType "
    				+ "FROM Ticket "
    				+ "WHERE CounterAssigned IS NULL "
    				+ "GROUP BY RequestType "
    				+ "ORDER BY RequestType DESC);";
    		
    		PreparedStatement pStat = connection.prepareStatement(queryA);
    		pStat.executeUpdate(queryA);
    		
    		pStat = connection.prepareStatement(queryB);
    		pStat.executeUpdate(queryB);
    		
    		
    	}catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized String getTicketToServe(int counterID) throws SQLException {
    	int ticketId = -1;
    	String requestType = "";
    	ArrayList<String> allowedRequestType = new ArrayList<>();
    	
    	try {
    		 
    		String query = "SELECT RequestType AS RequesType FROM Counter WHERE CounterID = "+counterID; //Top1 
            Statement stat = connection.createStatement(); 
     		ResultSet result = stat.executeQuery(query);
             
     		while(result.next()) { 
     			allowedRequestType.add(result.getString("RequesType")); //TODO: NOT IMPLEMENTED
     		}
     		
            query = "SELECT RequestType AS RequesType FROM QueueInfo LIMIT 1"; //Top1 
            stat = connection.createStatement(); 
    		result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			requestType = result.getString("RequesType");
    		}
    		
    		query = "SELECT TicketID FROM Ticket "
    				+ "WHERE date = (SELECT MIN(date) FROM Ticket WHERE CounterAssigned IS NULL) "
    				+ "AND time = (SELECT MIN(time) FROM Ticket WHERE CounterAssigned IS NULL) "
    				+ "FOR UPDATE;";
    		
    		stat = connection.createStatement(); 
    		result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			ticketId = result.getInt("TicketID");
    			System.out.println("Ticket that need to be served: "+ticketId);
    		}
    		
    		if(ticketId >= 0) {
    			query = "UPDATE Ticket SET CounterAssigned = "+counterID+" WHERE TicketID = "+ticketId+";";
    			System.out.println("Query: "+ query);
    			PreparedStatement pStat = connection.prepareStatement(query);
          		stat.executeUpdate(query);
    		} else {
    			System.out.println("No pending tickets");
    		}
    		
        } catch (SQLException e) {
            System.out.println("SQLException: impossibile effettuare la query al database.");            
        }
    	
    	return String.valueOf(ticketId);
    }

}