package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DatabaseQuery 
{
    public int getTicketId() throws SQLException {
        
        Connection conn = DatabaseMaster.getConnection(); 
        ArrayList<Integer> ids = new ArrayList<Integer>(); 
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        try {
            String query = "SELECT TicketID FROM ticket WHERE Date = '" + todayDate + "'"; 
            PreparedStatement stat = conn.prepareStatement(query); 
            ResultSet result = stat.executeQuery();
            
            while(result.next()) { 
            	ids.add((int)result.getInt(0));
            }      
        } catch (SQLException e) {
            System.out.println("SQLException: impossibile effettuare la query al database.");            
        } finally {
            conn.close(); 
        }
        return Collections.max(ids) + 1;
    }
    
    public synchronized void insertTicket(int id, String reqType) throws SQLException {
        Connection conn = DatabaseMaster.getConnection(); 
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Time todayTime = new java.sql.Time(Calendar.getInstance().getTime().getTime()); 
        
    	try {       
    		String query = "INSERT INTO ticket(TicketID, RequestType, Date, Time) VALUES (" + id + ", '" + reqType + "', '" 
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