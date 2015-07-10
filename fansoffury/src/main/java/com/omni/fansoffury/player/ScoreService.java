package com.omni.fansoffury.player;

import com.omni.fansoffury.model.event.ScoreChangedEvent;

public interface ScoreService {

	public void addListener(ScoreListener listener);
	
	public void removeListener(ScoreListener listener);
	
	public void raiseEvent(ScoreChangedEvent event);
	
}
