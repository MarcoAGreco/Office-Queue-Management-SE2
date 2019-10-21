package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
import it.polito.se2.database.DatabaseQuery;

public class Service {
	private Socket socket;
	//	Connection connection;
	private DatabaseQuery db;
	ServerMaster master;

	public Service(Socket socket, Connection connection, ServerMaster master) {
		this.socket = socket;
		//		this.connection = connection;
		this.db = new DatabaseQuery(connection);
		this.master = master;
	}

	public void doService(String msg) throws JSONException, SQLException {
		PrintWriter clientWriter;

		// receive JSON request from client
		JSONObject json = new JSONObject(msg);
		String operation = json.getString("operation");
		System.out.print("read: " + operation + " ");
		Date todayDate = new Date(Calendar.getInstance().getTime().getTime());

		switch(operation) {
		case "serve_next":
			try {
				int counterID = json.getInt("id");
				String ticket = db.selectTicketToServe(counterID, todayDate);
				
				JSONObject response = new JSONObject();
				response.put("operation", "serve");
				response.put("counterID", counterID);
				response.put("ticketID", ticket);
				clientWriter = new PrintWriter(socket.getOutputStream(), true);
				clientWriter.println(response);
				
				// Send JSON to all clients -> only 'board-client' will handle it 
				JSONObject resp = new JSONObject();
				resp.put("operation", "queue_update");
				resp.put("queueALenght", db.getQueueLength("Accounting", todayDate));
				resp.put("queueBLenght", db.getQueueLength("Package", todayDate));
				resp.put("lastTicket", ticket);
				resp.put("counterID", counterID);
				master.broadcast(resp);
				
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
				int id = db.getTicketId(todayDate);
				System.out.println("Id received " + id);
				
				db.insertTicket(id, reqType); // update db -> insert new ticket
				db.updateQueueInfo(); //update db -> update view queueInfo

				JSONObject obj = new JSONObject();
				JSONObject cont = new JSONObject();
				obj.put("operation", "new_ticket");
				cont.put("ticket_number", id);
				cont.put("time", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
				cont.put("request_type", reqType);
				obj.put("content", cont);

				clientWriter = new PrintWriter(socket.getOutputStream(), true);
				clientWriter.println(obj);
				
				// Send JSON to all clients -> only 'board-client' will handle it 
				JSONObject resp = new JSONObject();
				resp.put("operation", "queue_update");
				resp.put("queueALenght", db.getQueueLength("Accounting", todayDate));
				resp.put("queueBLenght", db.getQueueLength("Package", todayDate));
				resp.put("lastTicket", "-");
				resp.put("counterID", -1);
				master.broadcast(resp);

			} catch (IOException e) {
				System.err.println("Server Worker: Could not open output stream");
				e.printStackTrace();
			}
			break;
		case "counter_setup":
			try {
				JSONObject content = json.getJSONObject("content");
				String reqType = content.getString("request_type");
				int counterId = content.getInt("id");

				counterId = db.setupCounter(reqType, counterId); 

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
