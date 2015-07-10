package com.omni.fansoffury.balance;

import com.omni.fansoffury.model.Player;

/**
 * Describes a balancing strategy that alters the actual speed sent to a fan based on input from another system or device
 * @author steve.perkins
 *
 */
public interface FanSpeedBalanceStrategy {
	Double apply(Player player, Integer inputPercentage);
}
