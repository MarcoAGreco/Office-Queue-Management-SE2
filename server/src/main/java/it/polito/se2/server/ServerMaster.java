package it.polito.se2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerMaster {
	private ServerSocket serverSocket;
	private LinkedList<ServerWorker> allConnections = new LinkedList<>();
	public static final int PORT_NUMBER = 1500;
	
	public ServerMaster(int portNumber) {
		try	{
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println("Server error: Opening socket failed.");
			e.printStackTrace();
			System.exit(-1);
		}

		waitForConnection(portNumber);
	}
	
	public void waitForConnection(int port)	{
		while (true) {
			System.out.println("Waiting for clients to connect...");
			try	{
				Socket client = serverSocket.accept();
				ServerWorker worker = new ServerWorker(client);
				System.out.println("New connection");
				allConnections.add(worker);
			} catch (IOException e)	{
				System.err.println("Server error: Failed to connect to client.");
				e.printStackTrace();
			}
		}
	}

	public void broadcast(String s)	{
		for (ServerWorker workers : allConnections)
		{

		}
	}

	public static void main(String args[]) {
		new ServerMaster(PORT_NUMBER);
	}
}
