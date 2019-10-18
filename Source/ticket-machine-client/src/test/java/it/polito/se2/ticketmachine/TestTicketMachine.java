package it.polito.se2.ticketmachine;
import org.junit.jupiter.api.Test;

class TestTicketMachine {

	@Test
	void testTicketRequest() throws Exception {	
		MockServer mockServer = new MockServer();
		mockServer.start();

//		Thread.sleep(1000);   
		TicketMachineClient client = new TicketMachineClient(null, "localhost", 1500);
		client.sendTicketRequest("TestRequest");
		mockServer.test();
	}

}
