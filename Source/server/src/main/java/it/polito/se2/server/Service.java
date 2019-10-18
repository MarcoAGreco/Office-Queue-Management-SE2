package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.polito.se2.database.DatabaseMaster;
import it.polito.se2.database.DatabaseQuery;

public class Service {
	private Socket socket;
	private DatabaseQuery db;

	public Service(Socket socket) {
		this.socket = socket;
		this.db = new DatabaseQuery();
	}
	
	public void doService(String msg) throws JSONException, SQLException {
		PrintWriter clientWriter;
		
		// receive JSON request from client
		JSONObject json = new JSONObject(msg);
		String operation = json.getString("operation");
		System.out.print("read: " + operation + " ");

		switch(operation) {
			case "serve_next":
				System.out.println("Server has been notified!");
				break;
			case "new_ticket":
				// send JSON response to client
				try {
					JSONObject content = json.getJSONObject("content");
			        String reqType = content.getString("request_type");
			        
			        // get correct id from db
					int id = db.getTicketId();
					System.out.println("Id received " + id);
					
					// update db -> insert new ticket
					db.insertTicket(id, reqType);
					
					JSONObject obj = new JSONObject();
					JSONObject cont = new JSONObject();
					obj.put("operation", "new_ticket");
					cont.put("ticket_number", id);
					cont.put("time", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					cont.put("request_type", reqType);
					obj.put("content", cont);
					
					clientWriter = new PrintWriter(socket.getOutputStream(), true);
					clientWriter.println(obj);
				} catch (IOException e) {
					System.err.println("Server Worker: Could not open output stream");
					e.printStackTrace();
				}
				break;
			default:
				try {
					JSONObject obj = new JSONObject();
					obj.put("operation", "operation");
					obj.put("content", "message");
					
					clientWriter = new PrintWriter(socket.getOutputStream(), true);
					clientWriter.println(obj);
				} catch (IOException e) {
					System.err.println("Server Worker: Could not open output stream");
					e.printStackTrace();
				}
				break;
		}
	}
}
