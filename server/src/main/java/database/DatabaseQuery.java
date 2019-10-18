package database;

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

}