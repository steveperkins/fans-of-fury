package com.omni.fansoffury.balance;

import org.springframework.stereotype.Component;

import com.omni.fansoffury.model.Player;
import com.sperkins.mindwave.event.EventType;

@Component
public class OffsetFanSpeedBalanceStrategyImpl implements FanSpeedBalanceStrategy {
	
	private static final Double BASE_OFFSET = 50.0;
	
	@Override
	public Double apply(Player player, Integer inputPercentage) {
		Double offset = calculateOffset(player);
		Double output = offset + new Double(inputPercentage);
		if(output > 100) output = 100.0;
		if(output < 1) output = 1.0;
		return output;
	}
	
	/**
	 * Determines an appropriate offset based on a player's level. This implementation provides a linear offset algorithm whereby a player at level 0 has a high bonus offset but players at higher levels have much lower bonus offsets. A player at a very high level may receive a negative offset to compensate for his success. O. Henry, eat your heart out.
	 * With a BASE_OFFSET of 50, this implementation stops providing a positive bonus when a player reaches level 10. 
	 * @param player
	 * @param inputPercentage
	 * @return
	 */
	protected Double calculateOffset(Player player) {
		Double offset = 0.0;
		if(EventType.ATTENTION.equals(player.getMeasurementType())) offset = BASE_OFFSET - (player.getAttentionLevel() * 5);
		else offset = BASE_OFFSET - (player.getAttentionLevel() * 5);
		return offset;
	}

}
