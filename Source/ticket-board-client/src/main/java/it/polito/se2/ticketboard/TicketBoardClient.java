package it.polito.se2.ticketboard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONObject;

public class TicketBoardClient {
	private Socket clientSocket;
	private PrintWriter write;
	private BufferedReader reader;
	private Scanner keyboard;
	private ClientListener listener;
	private TicketBoardGUI frame;
	public static final int PORT_NUMBER = 1500;
	
	public TicketBoardClient(TicketBoardGUI frame, String host, int portNumber) {
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
			System.err.println("Error: Could not open connection to " + host + " on port " + portNumber);
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
	
	public void askForUpdate() {
		JSONObject obj = new JSONObject();
		obj.put("operation", "require_update");
		
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
				if (msg == null) // server closed connenction
					return false;
				System.out.println("Received from server:" +msg);
				JSONObject obj = new JSONObject(msg);
				
				String operation = obj.getString("operation");
				
				switch(operation) {
					case "queue_update":
						System.out.println("DEBUG: queue");
						int queueALenght = obj.getInt("queueALenght");
						int queueBLenght = obj.getInt("queueBLenght");
						String lastTicketServed = obj.getString("lastTicket");
						int counterID = obj.getInt("counterID"); //-1 for queuelenght update only
						
						if (frame != null) {
							frame.updateGUI(queueALenght,queueBLenght, lastTicketServed, counterID);
						}
					break;
					default:
						break;
				}
				
			} catch (IOException e)	{
				e.printStackTrace();
			}
			return true;
		}
	}
}