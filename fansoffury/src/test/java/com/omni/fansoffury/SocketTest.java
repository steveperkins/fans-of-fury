package com.omni.fansoffury;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class SocketTest {

	@Test
	public void test() {
		List<Transport> transports = new ArrayList<>(2);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		transports.add(new RestTemplateXhrTransport());

		TestEchoWebSocketHandler handler = new TestEchoWebSocketHandler();
		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.doHandshake(new TestEchoWebSocketHandler(), "ws://localhost:8080/fancontroller");
		sockJsClient.start();
		
		new Thread() {
			public void run() {
				// Keep the thread alive
				while(true)
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			};
		}.start();
		
//		try {
//			handler.sendMessage("Hi you");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
