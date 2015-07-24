package com.omni.fansoffury.player;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.level.PlayerLevelingScoreListener;
import com.omni.fansoffury.model.event.ScoreChangedEvent;

@Service
public class ScoreServiceImpl implements ScoreService {
	
	@Autowired
	private HeadsetService headsetService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private PlayerLevelingScoreListener playerLevelingScoreListener;
	
	private List<ScoreListener> listeners = new ArrayList<ScoreListener>();

	@Override
	public void addListener(ScoreListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ScoreListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void raiseEvent(ScoreChangedEvent event) {
		for(ScoreListener listener: listeners) {
			listener.scoreChanged(event);
		}
	}
	
	@PostConstruct
	private void init() {
		addListener(playerLevelingScoreListener);
	}
	
}
