package com.omni.fansoffury.headset.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.service.FanControllerService;
import com.sperkins.mindwave.event.AttentionEvent;
import com.sperkins.mindwave.event.Event;
import com.sperkins.mindwave.event.EventType;
import com.sperkins.mindwave.event.MeditationEvent;
import com.sperkins.mindwave.event.MindwaveEventListener;

@Scope(value = "prototype")
@Component
public class FanControlMindwaveEventListener implements MindwaveEventListener {
	
	private static Logger logger = LoggerFactory.getLogger(FanControlMindwaveEventListener.class);
	
	@Autowired
	private FanControllerService fanControllerService;
	
	private Headset headset;
	private Integer lastPercentage = 0;
	
	public Headset getHeadset() {
		return headset;
	}
	public void setHeadset(Headset headset) {
		this.headset = headset;
		lastPercentage = 0;
	}
	
	@Override
	public void onEvent(Event event) {
		logger.trace("{} event raised", event.getEventType().name());
		if (null != headset && null != headset.getPlayer() && null != headset.getPlayer().getMeasurementType() && headset.getId().equals(event.getDeviceAddress()) && headset.getPlayer().getMeasurementType().equals(event.getEventType())) {
			Integer newPercentage = null;
			if(EventType.ATTENTION.equals(event.getEventType())) newPercentage = getNewValue((AttentionEvent)event);
			if(EventType.MEDITATION.equals(event.getEventType())) newPercentage = getNewValue((MeditationEvent)event);
			
			logger.trace("New {} value is {}%", event.getEventType().name(), newPercentage);
			if (!lastPercentage.equals(newPercentage)) {
				logger.debug("{} changed from {}% to {}%", event.getDeviceAddress(), lastPercentage, newPercentage);
				lastPercentage = newPercentage;

				fanControllerService.changeFanSpeed(headset, newPercentage);
			}
		}
	}
	
	private Integer getNewValue(AttentionEvent event) {
		return event.getValue();
	}
	
	private Integer getNewValue(MeditationEvent event) {
		return event.getValue();
	}

}
