package com.omni.fansoffury.level;

import org.springframework.stereotype.Component;

import com.omni.fansoffury.model.Player;
import com.sperkins.mindwave.event.EventType;

/**
 * Increments a player's level by a set value per score
 * @author steve.perkins
 *
 */
@Component
public class ScoreLevelingStrategy implements LevelingStrategy {

	public static final Double LEVEL_INCREMENT = 0.2;
	@Override
	public void scoreChanged(Player player) {
		if(EventType.ATTENTION.equals(player.getMeasurementType())) {
			player.setAttentionLevel(player.getAttentionLevel() + LEVEL_INCREMENT);
		} else if(EventType.MEDITATION.equals(player.getMeasurementType())) {
			player.setMeditationLevel(player.getMeditationLevel() + LEVEL_INCREMENT);
		}
	}

}
