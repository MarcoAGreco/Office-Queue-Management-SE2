package it.polito.se2.ticketboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBoardClient {
	TicketBoardGUI testgui;

	@BeforeEach
	void setup() {
		testgui = new TicketBoardGUI();
		testgui.setLblQueue1("001A");
		testgui.setLblQueue2("002A");
		testgui.setLblQueue3("003B");
		testgui.setLblAccountingNo("0");
		testgui.setLblPackageNo("0");
	}

	@Test
	void testQueueInfo() {
		testgui.setLblQueue1("004A");
		testgui.setLblQueue2("005B");
		boolean valuesCorrect = true;
		if (!testgui.getLblQueue1().equalsIgnoreCase("004A")) {
			valuesCorrect = false;
		}
		if (!testgui.getLblQueue2().equalsIgnoreCase("005B")) {
			valuesCorrect = false;
		}
		if (!testgui.getLblQueue3().equalsIgnoreCase("003B")) {
			valuesCorrect = false;
		}
		assertEquals(valuesCorrect, "Check testQueueInfo code");
	}

	@Test
	void testQueueLenght() {
		testgui.setLblAccountingNo("3");
		testgui.setLblPackageNo("2");
		boolean correctValues = true;
		if (!testgui.getLblAccountingNo().equals("3")) {
			correctValues = false;
		}
		if (!testgui.getLblPackageNo().equals("2")) {
			correctValues = false;
		}
		assertEquals(correctValues, "Check testQueueLenght code");
	}
}