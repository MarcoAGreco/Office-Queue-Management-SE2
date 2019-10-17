package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class Service {
	private Socket socket;
	
	public Service(Socket socket) {
		this.socket = socket;
	}
	
	public void send(String msg) {
		PrintWriter clientWriter;
		
		try {
			JSONObject obj = new JSONObject();
			obj.put("operation", "message");
			obj.put("content", msg);

			obj.toString().getBytes();
			
			clientWriter = new PrintWriter(socket.getOutputStream(), true);
			clientWriter.println(obj);
		} catch (IOException e) {
			System.err.println("Server Worker: Could not open output stream");
			e.printStackTrace();
		}
	}
}
