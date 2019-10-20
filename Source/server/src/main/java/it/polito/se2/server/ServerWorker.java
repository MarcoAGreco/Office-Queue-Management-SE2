package it.polito.se2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;

public class ServerWorker extends Thread {
	private Socket client;
	private Connection connection;
	private BufferedReader clientReader;
	private ServerMaster master;

	public ServerWorker(Socket client, Connection connection, ServerMaster master) throws SQLException {
		this.client = client;
		this.connection = connection;
		this.master = master;

		try {
			clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			System.err.println("Server Worker: Could not open input stream");
			e.printStackTrace();
		}

		this.start();
	}

	public void run() {
		Service service = new Service(client, connection, master);

		while (true) {
			try {
				String msg = clientReader.readLine();
				System.out.println("Message received: " + msg);
				if(msg != null && !msg.isEmpty()){
					service.doService(msg);
				} else { //Null or empty message
					return;
				}
			} catch (IOException e)	{
				System.err.println("Server Worker: Could not read from client socket");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}