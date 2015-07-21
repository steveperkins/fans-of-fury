package com.omni.fansoffury.headset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.headset.listener.FanControlMindwaveEventListener;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;
import com.sperkins.mindwave.event.MindwaveEventListener;

@Service
public class HeadsetServiceImpl implements HeadsetService {
	
	@Autowired
    private ObjectFactory<FanControlMindwaveEventListener> eventListenerFactory;
	
	
	@Autowired
	private BluetoothSocketService bluetoothSocketService;
	
	// TODO Load headsets from data store
	private Map<String, Headset> headsets = new HashMap<String, Headset>();
	private Map<Headset, MindwaveEventListener> headsetEventListenerMap = new HashMap<Headset, MindwaveEventListener>();
	
	@Override
	public Headset getHeadset(String id) {
		return headsets.get(id);
	}
	
	@Override
	public Headset getByDeviceId(String deviceId) {
		for(Headset headset: headsets.values()) {
			if(null != headset.getDevice() && headset.getDevice().getId().equals(deviceId)) return headset;
		}
		return null;
	}
	
	@Override
	public void changeHeadsetPlayer(Headset headset, Player player) {
		headset.setPlayer(player);
		
		MindwaveEventListener currentHeadsetListener = headsetEventListenerMap.get(headset);
		if(null == currentHeadsetListener) {
			FanControlMindwaveEventListener listener = eventListenerFactory.getObject();
			listener.setHeadset(headset);
			
			// Start listening to headset events
			bluetoothSocketService.addListener(listener);
			currentHeadsetListener = listener;
		}
		headsetEventListenerMap.put(headset, currentHeadsetListener);
	}
	
	@Override
	public void changeHeadsetDevice(Headset headset, Device device) {
		headset.setDevice(device);
	}
	
	@Override
	public void shutdown() {
		bluetoothSocketService.shutdown();
	}
	

	/**
	 * Returns a copy of the current list of headsets. The copy will NOT be updated.
	 */
	@Override
	public List<Headset> getHeadsets() {
		return new ArrayList<Headset>(headsets.values());
	}
	
	@PostConstruct
	private void loadHeadsets() {
		// Headset 1
		Headset headset = new Headset("74E543D575B0");
		headsets.put(headset.getId(), headset);
		
		// Headset 2
		headset = new Headset("20689D88BC4A");
		headsets.put(headset.getId(), headset);
		
		// Headset 3
		headset = new Headset("20689D4C0A08");
		headsets.put(headset.getId(), headset);
		
		// Headset 4
		headset = new Headset("11111");
		headsets.put(headset.getId(), headset);
	}
	
}
