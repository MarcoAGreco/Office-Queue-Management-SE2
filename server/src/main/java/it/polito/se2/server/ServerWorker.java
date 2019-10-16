package it.polito.se2.server;

import java.net.Socket;

public class ServerWorker extends Thread {
	private Socket client;

	public ServerWorker(Socket client) {
		this.client = client;
		this.start();
	}

	public void run() {
		Service service = new Service(client);
		
		while (true) {
//			if (read from json what king of operation) {
				service.doSomething();
//			} else ...
		}
	}

}