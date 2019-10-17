package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseQuery;

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
        JSONObject content = json.getJSONObject("content");
		String operation = json.getString("operation");
        String reqType = content.getString("request_type");
		System.out.println("read: " + operation + " " + reqType);
		
		// get correct id from db
		int id = db.getTicketId();
		
		// update db -> insert new ticket
		db.insertTicket(id, reqType);
		
		switch(operation) {
			case "new_ticket":
				System.out.println("Sono entrato!");
				// send JSON response to client
				try {
					JSONObject obj = new JSONObject();
					JSONObject cont = new JSONObject();
					obj.put("operation", "new_ticket");
					obj.put("content", cont);
					cont.put("ticket_number", id);
					cont.put("time", new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime()));
					cont.put("request_type", reqType);
						
					obj.toString().getBytes();
					
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
					obj.toString().getBytes();
					
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
