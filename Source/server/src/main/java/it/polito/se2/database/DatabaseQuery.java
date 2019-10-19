package it.polito.se2.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DatabaseQuery 
{
    public int getTicketId() throws SQLException {
        
        Connection conn = DatabaseMaster.getConnection(); 
        ArrayList<Integer> ids = new ArrayList<Integer>(); 
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        int max = 0;
        System.out.println(todayDate);
        try {
            String query = "SELECT MAX(TicketID) AS MaxTicket FROM Ticket WHERE Date = '" + todayDate + "'"; 
            Statement stat = conn.createStatement(); 
    		ResultSet result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			max = result.getInt("MaxTicket");
    		}
        } catch (SQLException e) {
            System.out.println("SQLException: impossibile effettuare la query al database.");            
        } finally {
            conn.close(); 
        }
        return max + 1;
    }
    
    public synchronized void insertTicket(int id, String reqType) throws SQLException {
        Connection conn = DatabaseMaster.getConnection(); 
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Time todayTime = new java.sql.Time(Calendar.getInstance().getTime().getTime()); 
        
    	try {       
    		String query = "INSERT INTO Ticket(TicketID, RequestType, Date, Time) VALUES (" + id + ", '" + reqType + "', '" 
    																				+ todayDate + "', '" + todayTime + "')";
            PreparedStatement stat = conn.prepareStatement(query);
            stat.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }     
    }
    
    public synchronized int setupCounter(String reqType) throws SQLException {
    	int id=-1;
        Connection conn = DatabaseMaster.getConnection();
        
    	try {       
	    	String query =  "LOCK TABLES Counter WRITE;"; 
	    	PreparedStatement pStat = conn.prepareStatement(query);
	    	pStat.executeUpdate(query);
            
            query = "SELECT MAX(CounterID) AS MaxID FROM Counter"; 
            Statement stat = conn.createStatement(); 
    		ResultSet result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			id = result.getInt("MaxID");
    		}
    		
    		//Create a new ID
      		id += 1;
      		
      		for(String type : reqType.split(("/"))){
      			query = "INSERT INTO Counter(CounterID, RequestType) VALUES (" + id + ", '" + type +"')";
          		pStat = conn.prepareStatement(query);
          		stat.executeUpdate(query);
      		}
    		
    		query =  "UNLOCK TABLES;"; 
	    	pStat = conn.prepareStatement(query);
	    	pStat.executeUpdate(query);
    		
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }     
    	
    	return id;
    }
    
    public void updateQueueInfo() throws SQLException {
        Connection conn = DatabaseMaster.getConnection();
    	
    	try {
    		String queryA = "DROP VIEW IF EXISTS QueueInfo;"; 
    		String queryB = "CREATE VIEW QueueInfo AS (SELECT COUNT(*) AS QueueLenght, RequestType as RequestType FROM Ticket WHERE CounterAssigned IS NULL GROUP BY RequestType); ";
    		PreparedStatement pStat = conn.prepareStatement(queryA);
    		pStat.executeUpdate(queryA);
    		pStat = conn.prepareStatement(queryB);
    		pStat.executeUpdate(queryB);
    		
    		
    	}catch(SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }   
    }
    
    public synchronized int getTicketToServe(int counterID) throws SQLException {
    	int tickedId = 0;
    	Connection conn = DatabaseMaster.getConnection();
    	
    	try {
            String query = "SELECT RequestType AS RequesType FROM QueueInfo"; 
            Statement stat = conn.createStatement(); 
    		ResultSet result = stat.executeQuery(query);
            
    		while(result.next()) { 
    			
    		}
        } catch (SQLException e) {
            System.out.println("SQLException: impossibile effettuare la query al database.");            
        } finally {
            conn.close(); 
        }
    	
    	return tickedId;
    }

}