package it.polito.se2.ticketmachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.lang.Thread;
import org.json.JSONObject;

public class TicketMachineClient {
	private Socket clientSocket;
	private PrintWriter write;
	private BufferedReader reader;
	private Scanner keyboard;
	private ClientListener listener;
	private TicketMachineGUI frame;
	public static final int PORT_NUMBER = 1500;

	public TicketMachineClient(TicketMachineGUI frame, String host, int portNumber) {
		this.frame = frame;
		keyboard = new Scanner(System.in);
		
		if (!openConnection(host, portNumber)) {
			if (frame != null)
				frame.showPopUp("Unable to connect to the server...\nPlease retry later.\n");
			else
				System.out.println("Unable to connect to the server...\nPlease retry later.\n");
			System.exit(-1);
		}
		
		System.out.println("Connected to server...");
		listener = new ClientListener();
		System.out.println("Starting listener...");
		listener.start();
	}

	private boolean openConnection(String host, int portNumber) {
		try {
			clientSocket = new Socket(host, portNumber);
		} catch (UnknownHostException e) {
			System.err.println("Error: Unknown Host " + host);
			return false;
		} catch (IOException e) {
			System.err.println("Error: Could not open connection to " + host
					+ " on port " + portNumber);
			return false;
		}

		try {
			write = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Error: Could not open output stream");
			return false;
		}

		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Error: Could not open input stream");
			return false;
		}
		return true;
	}

	public void sendTicketRequest(String requestType) {	
		JSONObject obj = new JSONObject();
		obj.put("operation", "new_ticket");
		JSONObject content = new JSONObject();
		content.put("request_type", requestType);
		obj.put("content", content);

		System.out.println("Sending json to server: " + obj);
		write.println(obj);
	}

	class ClientListener extends Thread	{
		public void run() {
			while (read());
			System.out.println("Server closed the connection");
		}

		private boolean read()	{
			try	{
				String msg = reader.readLine();
				if (msg == null) // server closed connection
					return false;

				// parse JSON
				System.out.println(msg);
				JSONObject obj = new JSONObject(msg);
				if (!obj.get("operation").equals("new_ticket"))
					return true;
				
				JSONObject content = obj.getJSONObject("content");
				int ticketNumber = content.getInt("ticket_number");
				String time = content.getString("time");
				String requestType = content.getString("request_type");

				SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			    Date date = new Date();  
			    if (frame != null) {
			    	frame.showPopUp("Number: " + ticketNumber + requestType.charAt(0) + "\n" +
									"Type: " + requestType + "\n" +
									"Date: " + formatter.format(date) + "\n" +
									"Time: " + time);
			    }

				System.out.println("Number: " + ticketNumber + requestType.charAt(0) +
									" Type: " + requestType +
									" Date: " + formatter.format(date) +
									" Time: " + time);
			} catch (IOException e)	{
				e.printStackTrace();
			}
			return true;
		}
	}
}
