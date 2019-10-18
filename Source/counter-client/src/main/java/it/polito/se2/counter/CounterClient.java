package it.polito.se2.counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONObject;

public class CounterClient {
	private Socket clientSocket;
	private PrintWriter write;
	private BufferedReader reader;
	private Scanner keyboard;
	private ClientListener listener;
	private CounterGUI frame;
	public static final int PORT_NUMBER = 1500;
	private static id;
	
	public CounterClient(CounterGUI frame, String host, int portNumber) {
		this.frame = frame;
		keyboard = new Scanner(System.in);
		
		if (!openConnection(host, portNumber)) {
			frame.showPopUp("Unable to connect to the server...\nPlease retry later.\n");
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

	public void serveNext() {	
		JSONObject obj = new JSONObject();
		obj.put("operation", "serve_next");
		
		System.out.println("Sending json to server: " + obj);
		write.println(obj);
	}

	public void setReqTypeToCounter(String[] reqTypes) {
		JSONObject obj = new JSONObject();
		JSONObject content = new JSONObject();

		obj.put("operation", "set_req_type");
		
		if(!reqTypes[0].isEmpty() && !reqTypes[1].isEmpty()) {
			content.put("request_type1", reqTypes[0]);
			content.put("request_type2", reqTypes[1]);
		} else if(!reqTypes[0].isEmpty())
			content.put("request_type1", reqTypes[0]);
		else 
			content.put("request_type2", reqTypes[1]);

		obj.put("content", content);

		System.out.println("Sending json to server: " + obj);
		write.println(obj);
	}
	
	class ClientListener extends Thread	{
		public void run() {
			while (read());	
			System.out.println("Server closed the connection");
			System.exit(-1);
		}

		private boolean read()	{
			try	{
				String msg = reader.readLine();
				if (msg == null) // server closed connenction
					return false;
				System.out.println(msg);
			} catch (IOException e)	{
				e.printStackTrace();
			}
			return true;
		}
	}
}