package it.polito.se2.ticketmachine;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import static java.lang.Thread.sleep;

public class TicketMachineClient {
	private Socket clientSocket;
	public static final int PORT_NUMBER = 1500;

	public TicketMachineClient(String host, int portNumber) {
		while (!openConnection(host, portNumber)) {
			try {
                System.out.println("Waiting for the server...\n");
				sleep(2000);
			} catch (InterruptedException e) {
				// show pop-up with error message
				e.printStackTrace();
			}
		}
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

		return true;
	}

//	public void send() {
//		
//	}

//	public void read()	{
//			
//	}
}
