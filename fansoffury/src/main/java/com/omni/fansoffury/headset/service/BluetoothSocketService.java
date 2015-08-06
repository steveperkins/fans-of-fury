package com.omni.fansoffury.headset.service;

import java.io.IOException;

import com.sperkins.mindwave.event.MindwaveEventListener;

public interface BluetoothSocketService {
	
	public void addListener(MindwaveEventListener listener);
	
	public void removeListener(MindwaveEventListener listener);
	
	public void reconnect(String headsetId) throws IOException;
	
	public void shutdown();

}
