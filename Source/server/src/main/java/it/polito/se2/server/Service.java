package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
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
//	Connection connection;
	private DatabaseQuery db;

	public Service(Socket socket, Connection connection) {
		this.socket = socket;
//		this.connection = connection;
		this.db = new DatabaseQuery(connection);
	}

	public void doService(String msg) throws JSONException, SQLException {
		PrintWriter clientWriter;

		// receive JSON request from client
		JSONObject json = new JSONObject(msg);
		String operation = json.getString("operation");
		System.out.print("read: " + operation + " ");

		switch(operation) {
		case "serve_next":
			try {
				int counterID = json.getInt("id");
				String tickedID = db.getTicketToServe(counterID); //TODO: To be implemented 
				JSONObject response = new JSONObject();
				
				response.put("operation", "serve");
				response.put("counterID", counterID);
				response.put("ticketID", tickedID);
				
				db.updateQueueInfo();
				clientWriter = new PrintWriter(socket.getOutputStream(), true);
				clientWriter.println(response);
				
			} catch (IOException e) {
				System.err.println("Server Worker: Could not open output stream");
				e.printStackTrace();
			}
			
			break;
		case "new_ticket":
			// send JSON response to client
			try {
				JSONObject content = json.getJSONObject("content");
				String reqType = content.getString("request_type");

				// get correct id from db
//				Connection connection = DatabaseMaster.getConnection();
				int id = db.getTicketId(new Date(Calendar.getInstance().getTime().getTime()));
//				connection.close();
				System.out.println("Id received " + id);

				// update db -> insert new ticket
				db.insertTicket(id, reqType);
				
				//update db -> update view queueInfo
				db.updateQueueInfo();

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
		case "counter_setup": //TODO: Test
			try {
				JSONObject content = json.getJSONObject("content");
				String reqType = content.getString("request_type");

				int counterId = db.setupCounter(reqType); 

				//TODO: Check these lines
				JSONObject response = new JSONObject();
				response.put("operation", "setup_response");
				response.put("id", counterId);

				clientWriter = new PrintWriter(socket.getOutputStream(), true);
				clientWriter.println(response);
			} catch (IOException e1) {
				System.err.println("Server Worker: Could not open output stream");
				e1.printStackTrace();
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
