package com.omni.fansoffury.headset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.headset.listener.FanControlMindwaveEventListener;
import com.omni.fansoffury.model.Headset;
import com.sperkins.mindwave.event.AttentionEvent;
import com.sperkins.mindwave.event.Event;
import com.sperkins.mindwave.event.EventType;
import com.sperkins.mindwave.event.MeditationEvent;
import com.sperkins.mindwave.event.MindwaveEventListener;

/**
 * Generates fake headset data for testing purposes
 * @author steve.perkins
 *
 */
@Service
public class FakeBluetoothSocketServiceImpl implements BluetoothSocketService {
	private static Logger logger = LoggerFactory.getLogger(FakeBluetoothSocketServiceImpl.class);
	
	@Autowired
	private HeadsetService headsetService;
	
    private List<MindwaveEventListener> listeners = new ArrayList<MindwaveEventListener>();
    private List<FanControlMindwaveEventListener> fanControlListeners = new ArrayList<FanControlMindwaveEventListener>();
    private RandomHeadsetDataGenerator generator;
    
    @Override
	public void addListener(MindwaveEventListener listener) {
    	if(listener instanceof FanControlMindwaveEventListener) {
	    	fanControlListeners.add((FanControlMindwaveEventListener)listener);
    	} else {
    		listeners.add(listener);
    	}
    	
    	if(null == generator) {
			generator = new RandomHeadsetDataGenerator();
			generator.start();
    	}
	}
	
	@Override
	public void removeListener(MindwaveEventListener listener) {
		listeners.remove(listener);
		fanControlListeners.remove(listener);
	}
	
	@Override
	public void shutdown() {
		generator.shutdown();
		generator = null;
		listeners = null;
		fanControlListeners = null;
	}
	
	class RandomHeadsetDataGenerator extends Thread {
		Boolean keepRunning = Boolean.TRUE;
		Random random = new Random();
		
		@Override
		public void run() {
			while(keepRunning) {
				List<FanControlMindwaveEventListener> fanControlListenersCopy = new ArrayList<FanControlMindwaveEventListener>(fanControlListeners);
				for(FanControlMindwaveEventListener listener: fanControlListenersCopy) {
					if(null != listener && null != listener.getPlayer() && null != listener.getPlayer().getHeadset()) {
						Event event = null;
						Integer newValue = random.nextInt(100);
						if(EventType.ATTENTION.equals(listener.getPlayer().getMeasurementType())) {
							event = new AttentionEvent(listener.getPlayer().getHeadset().getId(), newValue);
						} else if(EventType.MEDITATION.equals(listener.getPlayer().getMeasurementType())) {
							event = new MeditationEvent(listener.getPlayer().getHeadset().getId(), newValue);
						}
						logger.debug("FAKE: Generating new {} event with value {}", event.getEventType().name(), newValue);
						listener.onEvent(event);
					}
				}
				
				List<Headset> headsetsCopy = new ArrayList<Headset>(headsetService.getHeadsets());
				List<MindwaveEventListener> listenersCopy = new ArrayList<MindwaveEventListener>(listeners);
				for(Headset headset: headsetsCopy) {
					Integer newValue = random.nextInt(100);
					AttentionEvent attentionEvent = new AttentionEvent(headset.getId(), newValue);
					
					newValue = random.nextInt(100);
					MeditationEvent meditationEvent = new MeditationEvent(headset.getId(), newValue);
					
					for(MindwaveEventListener listener: listenersCopy) {
						logger.debug("FAKE: Generating new {} event with value {}", attentionEvent.getEventType().name(), attentionEvent.getValue());
						listener.onEvent(attentionEvent);
						logger.debug("FAKE: Generating new {} event with value {}", meditationEvent.getEventType().name(), meditationEvent.getValue());
						listener.onEvent(meditationEvent);
					}
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void shutdown() {
			keepRunning = Boolean.FALSE;
			logger.debug("FAKE: Shutting down fake headset data thread");
		}
	}
	
		
}
