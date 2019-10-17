package it.polito.se2.ticketmachine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.lang.Thread;
import org.json.JSONObject;

public class TicketMachineClient {
	private Socket clientSocket;
	private PrintWriter write;
	private BufferedReader reader;
	private Scanner keyboard;
	private ClientListener listener;
	public static final int PORT_NUMBER = 1500;

	public TicketMachineClient(String host, int portNumber) {
		keyboard = new Scanner(System.in);
		
		while (!openConnection(host, portNumber)) {
			try {
                System.out.println("Waiting for the server...\n");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// show pop-up with error message
				e.printStackTrace();
			}
		}
		
		listener = new ClientListener();
		System.out.println("Starting listener = : " + listener);
		listener.start();
		
		listenToUserRequests();
	}

	private boolean openConnection(String host, int portNumber) {
		try {
			clientSocket = new Socket(host, portNumber);
		} catch (UnknownHostException e) {
			System.err.println("Error: Unknown Host " + host);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.err.println("Error: Could not open connection to " + host
					+ " on port " + portNumber);
			e.printStackTrace();
			return false;
		}

		try {
			write = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Error: Could not open output stream");
			e.printStackTrace();
			return false;
		}

		try {
			reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Error: Could not open input stream");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void listenToUserRequests() {
		while (true) {
			String cmd = keyboard.nextLine();
			if (cmd.equals("test"))
				System.out.println("test");
			else if (cmd.contentEquals("quit"))
				break;
			
			JSONObject obj = new JSONObject();
			obj.put("operation", "message");
			obj.put("command", cmd);

//			obj.toString().getBytes();
			System.out.print(obj);
			write.println(obj);
		}
	}

	class ClientListener extends Thread	{
		public void run() {
			System.out.println("ClientListener running");
			while (read());
			
			System.out.println("Server closed the connection");
			System.exit(0);
		}

		private boolean read()	{
			try	{
				System.out.println("Listening to socket");
				String msg = reader.readLine();
				if (msg == null)
					return false;

				// parse JSON
				JSONObject obj = new JSONObject(msg);
				String operation = obj.getString("operation");
				String content = obj.getString("content");
				System.out.println("read: " + operation + " " + content);
			} catch (IOException e)	{
				e.printStackTrace();
			}
			return true;
		}
	}
}
