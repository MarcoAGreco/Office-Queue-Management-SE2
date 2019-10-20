package it.polito.se2.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

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
        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());
        Time todayTime = new Time(Calendar.getInstance().getTime().getTime()); 
        
    	try {       
    		String query = "INSERT INTO Ticket(TicketID, RequestType, Date, Time) VALUES (" + id + ", '" + reqType + "', '" 
    																				+ todayDate + "', '" + todayTime + "')";
            PreparedStatement stat = connection.prepareStatement(query);
            stat.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public synchronized int setupCounter(String reqType, int counterID) throws SQLException {
    	int id = counterID;        
    	try {       
	    	String query =  "LOCK TABLES Counter WRITE;";
	    	PreparedStatement pStat = connection.prepareStatement(query);
    		pStat.executeUpdate(query);
    		Statement stat = connection.createStatement(); 

	    	// calculate the new ID, if the counter does not already have one
	    	if (counterID == -1) {
	    		query = "SELECT DISTINCT CounterID FROM Counter"; 
	    		ResultSet result = stat.executeQuery(query);

	    		// the new id obtained as the lowest non already existing id
	    		HashSet<Integer> alreadyExistingIds = new HashSet<>();
	    		while(result.next()) { 
	    			alreadyExistingIds.add(result.getInt("CounterID"));
	    		}

	    		int i = 1;
	    		while (alreadyExistingIds.contains(i)) {
	    			i++;
	    		}
	    		id = i;
	    	}
	    	
	    	// delete entry related to the counter id
  			query = "DELETE FROM Counter WHERE CounterID = '" + id + "'";
  			pStat = connection.prepareStatement(query);
      		stat.executeUpdate(query);
      		
  			// add the new entries
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
    				+ "GROUP BY RequestType )"
    				+ "ORDER BY QueueLenght DESC;";
    		
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
    			System.out.println("Ticket that need to be served: " + ticketId);
    		}
    		
    		if(ticketId >= 0) {
    			query = "UPDATE Ticket SET CounterAssigned = " + counterID + " WHERE TicketID = " + ticketId + ";";
    			System.out.println("Query: " + query);
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

	public synchronized int selectTicketToServe(int counterID, Date todayDate) {
		int ticketToServe = -1; 
    	try {       
	    	String query =  "LOCK TABLES Ticket WRITE, Counter C1 WRITE, Counter C2 WRITE, Ticket T WRITE, Ticket T1 WRITE, Ticket T2 WRITE, RequestType R1 WRITE;";
	    	PreparedStatement pStat = connection.prepareStatement(query);
    		pStat.executeUpdate(query);
    		Statement stat = connection.createStatement(); 

    		query = "SELECT T.TicketID\n" + 
    				"FROM Ticket T\n" + 
    				"WHERE Date = '" + todayDate + "' AND\n" + 
    				"	CounterAssigned IS NULL AND\n" + 
    				"	T.RequestType = (\n" + 
    				"		/* select request type with maximum queue lenght\n" + 
    				"		(and minimum service time in case of same lengths) */\n" + 
    				"		SELECT C1.RequestType\n" + 
    				"		FROM Ticket T1, Counter C1, RequestType R1\n" + 
    				"		WHERE C1.RequestType = T1.RequestType AND C1.RequestType = R1.RequestName AND\n" + 
    				"			C1.CounterID = " + counterID + " AND\n" + 
    				"			Date = '" + todayDate + "' AND\n" + 
    				"			CounterAssigned IS NULL\n" + 
    				"			GROUP BY C1.RequestType\n" + 
    				"			HAVING COUNT(*) = (\n" + 
    				"				/* select maximum value of the queue lenght */\n" + 
    				"				SELECT COUNT(*) AS REQ_COUNT\n" + 
    				"				FROM Ticket T2, Counter C2\n" + 
    				"				WHERE C2.RequestType = T2.RequestType AND\n" + 
    				"				C2.CounterID = " + counterID + " AND \n" + 
    				"				Date = '" + todayDate + "' AND\n" + 
    				"				CounterAssigned IS NULL\n" + 
    				"				GROUP BY C2.RequestType\n" + 
    				"				ORDER BY REQ_COUNT DESC\n" + 
    				"				LIMIT 1\n" + 
    				"			)\n" + 
    				"		ORDER BY R1.ServiceTimeMinutes\n" + 
    				"		LIMIT 1\n" + 
    				"	)\n" + 
    				"ORDER BY T.TicketID\n" + 
    				"LIMIT 1";
    		System.out.println("query:\n" + query);
    		
    		ResultSet result = stat.executeQuery(query);
    		if (!result.next()) {
    			query =  "UNLOCK TABLES;"; 
    	    	pStat = connection.prepareStatement(query);
    	    	pStat.executeUpdate(query);
    			return -1;
            }
    		ticketToServe = result.getInt("TicketID");
    		System.out.println("Ticket to serve: " + ticketToServe);
    		
	    	// set ticket as served
    		pStat.close();
  			query = "UPDATE Ticket SET CounterAssigned = " + counterID + " WHERE TicketID = " + ticketToServe + " AND Date = '" + todayDate + "'";
  			pStat = connection.prepareStatement(query);
      		stat.executeUpdate(query);
      		
    		query =  "UNLOCK TABLES;"; 
	    	pStat = connection.prepareStatement(query);
	    	pStat.executeUpdate(query);
    		
        } catch(SQLException e) {
            e.printStackTrace();
        }   
    	return ticketToServe;
	}

	public int getQueueLength(String requestType, Date todayDate) throws SQLException {
		int queueLength = 0;
        String query = "SELECT COUNT(*) AS QueueLength FROM Ticket WHERE Date = '" + todayDate + "' AND "
        		+ "CounterAssigned IS NULL AND RequestType = '" + requestType + "'"; 
        Statement stat = connection.createStatement();
        ResultSet result = stat.executeQuery(query);
        
        if (result.next()) { 
        	queueLength = result.getInt("QueueLength");
        }
        
        return queueLength;
	}

}