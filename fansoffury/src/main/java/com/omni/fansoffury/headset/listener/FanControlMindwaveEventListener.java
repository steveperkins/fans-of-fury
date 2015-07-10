package com.omni.fansoffury.headset.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
	
	private Player player;
	private EventType eventType;
	private Integer lastPercentage = 0;
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		lastPercentage = 0;
		this.player = player;
		this.eventType = player.getMeasurementType();
	}
	
	@Override
	public void onEvent(Event event) {
		logger.trace("{} event raised", event.getEventType().name());
		if (null != player && null != eventType && null != player.getHeadset() && player.getHeadset().getId().equals(event.getDeviceAddress()) && eventType.equals(event.getEventType())) {
			Integer newPercentage = null;
			if(EventType.ATTENTION.equals(eventType)) newPercentage = getNewValue((AttentionEvent)event);
			if(EventType.MEDITATION.equals(eventType)) newPercentage = getNewValue((MeditationEvent)event);
			
			logger.trace("New {} value is {}%", eventType.name(), newPercentage);
			if (!lastPercentage.equals(newPercentage)) {
				logger.debug("{} changed from {}% to {}%", event.getDeviceAddress(), lastPercentage, newPercentage);
				lastPercentage = newPercentage;

				fanControllerService.changeFanSpeed(player,	newPercentage);
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
