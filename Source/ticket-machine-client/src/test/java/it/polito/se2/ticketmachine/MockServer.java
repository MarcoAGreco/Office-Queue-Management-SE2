package it.polito.se2.ticketmachine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

class MockServer {
	private Exception exception = null;
	private Thread thread;
	
	public MockServer() {
		thread = new Thread(new Runnable() {
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(1500);
						Socket clientSocket = serverSocket.accept()) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

					String msg = reader.readLine();
					if (msg == null)
						throw new Exception("Unable to receive message");

					JSONObject json = new JSONObject(msg);
					String operation = json.getString("operation");
					if (!operation.equals("new_ticket"))
						throw new Exception("Expected <new_ticket> but was <" + operation + ">");

					JSONObject content = json.getJSONObject("content");
					String request = content.getString("request_type");
					if (!request.equals("TestRequest"))
						throw new Exception("Expected <TestRequest> but was <" + request + ">");
				} catch (Exception e) {
					exception = e;
				}     
			}
		});
	}
	
	public void start() {
		thread.start();
	}
	
	public void test() throws Exception {
		thread.join();
		if (exception != null)
			throw exception;
	}
}
