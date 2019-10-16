package it.polito.se2.ticketmachine;

import javax.swing.JFrame;

public class TicketMachineGUI extends JFrame {
	private TicketMachineClient client;
	
	public TicketMachineGUI(TicketMachineClient client) {
		this.client = client;
		
		initComponents();
		createEvents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ...
	}
	
	private void createEvents() {
		// register event listeners
	}
		
	public static void main(String[] args) {
		new TicketMachineClient("localhost", TicketMachineClient.PORT_NUMBER);
	}
}
