package com.omni.fansoffury.level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.omni.fansoffury.model.event.ScoreChangedEvent;
import com.omni.fansoffury.player.ScoreListener;

/**
 * Applies leveling strategy to in-memory players as they score
 * @author steve.perkins
 *
 */
@Component
public class PlayerLevelingScoreListener implements ScoreListener {

	@Autowired
	private LevelingStrategy levelingStrategy;
	
	@Override
	public void scoreChanged(ScoreChangedEvent event) {
		levelingStrategy.scoreChanged(event.getHeadset().getPlayer());
	}

}
