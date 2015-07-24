package com.omni.fansoffury.level;

import com.omni.fansoffury.model.Player;

/**
 * A leveling strategy determines when a player's level should change and by how much. Implementations should modify the passed-in reference.
 * @author steve.perkins
 *
 */
public interface LevelingStrategy {
	public void scoreChanged(Player player);
}
