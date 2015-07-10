package com.omni.fansoffury.player;

import com.omni.fansoffury.model.event.ScoreChangedEvent;

public interface ScoreListener {
	public void scoreChanged(ScoreChangedEvent event);
}
