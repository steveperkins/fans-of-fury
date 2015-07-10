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
import com.omni.fansoffury.player.PlayerService;
import com.sperkins.mindwave.event.MindwaveEventListener;

@Service
public class HeadsetServiceImpl implements HeadsetService {
	
	@Autowired
    private ObjectFactory<FanControlMindwaveEventListener> eventListenerFactory;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private BluetoothSocketService bluetoothSocketService;
	
	// TODO Load headsets from data store
	private Map<String, Headset> headsets = new HashMap<String, Headset>();
	private Map<Headset, MindwaveEventListener> headsetEventListenerMap = new HashMap<Headset, MindwaveEventListener>();
	
	public Headset getHeadset(String id) {
		return headsets.get(id);
	}
	
	@Override
	public void mapHeadsetToPlayer(Player player, Headset headset) {
		// TODO Persist player here, as their headset assignment has changed?
		player.setHeadset(headset);
		
		MindwaveEventListener currentHeadsetListener = headsetEventListenerMap.get(headset);
		if(null == currentHeadsetListener) {
			FanControlMindwaveEventListener listener = eventListenerFactory.getObject();
			listener.setPlayer(player);
			
			
			// Start listening to headset events
			bluetoothSocketService.addListener(listener);
			currentHeadsetListener = listener;
		}
		headsetEventListenerMap.put(headset, currentHeadsetListener);
	}
	
	public void shutdown() {
		bluetoothSocketService.shutdown();
	}
	
	@PostConstruct
	private void loadHeadsets() {
		// Headset 1
		Headset headset = new Headset("74E543D575B0");
		headsets.put(headset.getId(), headset);
		
		// Headset 3
		headset = new Headset("20689D4C0A08");
		headsets.put(headset.getId(), headset);
	}

	/**
	 * Returns a copy of the current list of headsets. The copy will NOT be updated.
	 */
	@Override
	public List<Headset> getHeadsets() {
		return new ArrayList<Headset>(headsets.values());
	}
	
}
