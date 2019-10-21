package it.polito.se2.counter;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

class TestCounter {
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
						System.out.println(msg);
						if (msg == null)
							throw new Exception("Unable to receive message");
						
						JSONObject json = new JSONObject(msg);
						String operation = json.getString("operation");
						if (!operation.equals("counter_setup"))
							throw new Exception("Expected <counter_setup> but was <" + operation + ">");

						System.out.println("content:" + json);

						JSONObject content = json.getJSONObject("content");
						String reqType = content.getString("request_type");
						if (!reqType.equals("TestRequest"))
							throw new Exception("Expected <TestRequest> but was <" + reqType + ">");
						
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
	@Test
	void testCounterTypeRequestSetting() throws Exception {
		MockServer mockServer = new MockServer();
		mockServer.start();
		
		CounterClient client = new CounterClient(null, "localhost", 1500);
		client.setReqTypeToCounter(new String[] {"TestRequest", ""});

		mockServer.test();
	}

}
