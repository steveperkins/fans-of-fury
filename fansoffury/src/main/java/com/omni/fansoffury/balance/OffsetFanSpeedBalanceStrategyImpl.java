package com.omni.fansoffury.balance;

import org.springframework.stereotype.Component;

import com.omni.fansoffury.model.Player;

@Component
public class OffsetFanSpeedBalanceStrategyImpl implements FanSpeedBalanceStrategy {

	@Override
	public Double apply(Player player, Integer inputPercentage) {
		return new Double(inputPercentage);
	}

}
