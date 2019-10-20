package it.polito.se2.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;
import it.polito.se2.database.DatabaseMaster;

public class ServerMaster {
	private ServerSocket serverSocket;
	private DatabaseMaster database;
	private Connection connection;
	public static final int PORT_NUMBER = 1500;
	public static final String DATABASE_CONFIG = "database.properties";
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	
	public ServerMaster(int portNumber) throws SQLException {
		try	{
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println("Server error: Opening socket failed.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			database = new DatabaseMaster(DATABASE_CONFIG);
			connection = database.getConnection();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to database");
			System.exit(-1);
		}
		waitForConnection(portNumber);
	}
	
	public void waitForConnection(int port) throws SQLException	{
		while (true) {
			System.out.println("Waiting for clients to connect...");
			try	{
				Socket client = serverSocket.accept();
				sockets.add(client);
				new ServerWorker(client, connection);
				System.out.println("New connection");
			} catch (IOException e)	{
				System.err.println("Server error: Failed to connect to client.");
				e.printStackTrace();
			}
		}
	}

	public static void broadcast(JSONObject message) {
        try {
            for (Socket socket : sockets) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String args[]) throws SQLException {
		new ServerMaster(PORT_NUMBER);
	}
}
