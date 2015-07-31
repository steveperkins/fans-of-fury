package com.omni.fansoffury.headset.service;

import java.io.IOException;

import com.omni.fansoffury.headset.listener.NoOpListener;
import com.sperkins.mindwave.event.MindwaveEventListener;
import com.sperkins.mindwave.socket.BluetoothSocket;

//@Service
public class BluetoothSocketServiceImpl implements BluetoothSocketService {
	
    private BluetoothSocket socket;
	
    @Override
	public void addListener(MindwaveEventListener listener) {
		if(null == socket) {
			createSocket();
		}
		socket.addListener(listener);
	}
	
	@Override
	public void removeListener(MindwaveEventListener listener) {
		socket.removeListener(listener);
	}
	
	
	@Override	
	public void reconnect(String headsetId) throws IOException {
		socket.connect(headsetId);
	}
	
	@Override
	public void shutdown() {
		if(null != socket) socket.stop();
	}
	
	private void createSocket() {
		socket = new BluetoothSocket(new NoOpListener());
        try {
        	socket.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("Bluetooth socket listener started");
	}
		
}
