package com.omni.fansoffury.api.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.json.JsonResponse;
import com.omni.fansoffury.player.PlayerService;
import com.sperkins.mindwave.event.EventType;

@RestController
public class PlayerController {
	private static Logger logger = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private HeadsetService headsetService;
	
	@RequestMapping("/api/player/setuptest")
	public JsonResponse setupTest() {
		logger.debug("GET to /api/player/setuptest");
		
		String playerId = "0";
		createPlayer(playerId);
		mapPlayerIdToHeadsetId(playerId, "74E543D575B0", EventType.ATTENTION);
		mapPlayerIdToDeviceId(playerId, "0");
		
		playerId = "1";
		createPlayer(playerId);
		mapPlayerIdToHeadsetId(playerId, "20689D4C0A08", EventType.MEDITATION);
		mapPlayerIdToDeviceId(playerId, "1");
		
		JsonResponse response = new JsonResponse();
		response.setStatus("success");
		
		return response;
	}
	
	@RequestMapping(value = "/api/player/{playerId}", method = RequestMethod.GET)
	public JsonResponse getPlayerById(@PathVariable("playerId") String playerId) {
		logger.debug("GET to /api/player/{}", playerId);
		
		JsonResponse response = new JsonResponse();
		Player player = playerService.getPlayer(playerId);
		response.setObject(player);
		response.setStatus("success");
		
		return response;
	}
	
	@RequestMapping(value = "/api/player/{playerId}", method = RequestMethod.PUT)
	public JsonResponse createPlayer(@PathVariable("playerId") String playerId) {
		logger.debug("PUT to /api/player/{}", playerId);
		
		JsonResponse response = new JsonResponse();
		response.setStatus("success");
		
		Player existingPlayer = playerService.getPlayer(playerId);
		if(null != existingPlayer) {
			response.setObject(existingPlayer);
		} else {
			Player player = new Player();
			player.setId(playerId);
		
			player = playerService.createPlayer(player);
			
			response.setObject(player);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/api/player/map/{playerId}/headset/{headsetId}/measurement/{measurementType}", method = RequestMethod.PUT)
    public JsonResponse mapPlayerIdToHeadsetId(@PathVariable("playerId") String playerId, @PathVariable("headsetId") String headsetId, @PathVariable("measurementType") EventType measurementType) {
		logger.debug("PUT to /api/player/map/{}/headset/{}", playerId, headsetId);
		JsonResponse response = new JsonResponse();
		
		try {
			Player player = playerService.getPlayer(playerId);
			if(null == player) throw new IllegalArgumentException("Player " + playerId + " does not exist!");
			
			player.setMeasurementType(measurementType);
			headsetService.mapHeadsetToPlayer(player, headsetService.getHeadset(headsetId));
			response.setStatus("success");
		} catch(Exception e) {
			response.setStatus("error");
			String error = "Could not map player to headset: " + e.getMessage();
			response.setErrors(Arrays.asList( new String[]{ error }));
			logger.error(error, e);
		}
		
        return response;
    }
	
	@RequestMapping(value = "/api/player/map/{playerId}/device/{deviceId}", method = RequestMethod.PUT)
	public JsonResponse mapPlayerIdToDeviceId(@PathVariable("playerId") String playerId, @PathVariable("deviceId") String deviceId) {
		logger.debug("PUT to /api/player/map/{}/device/{}", playerId, deviceId);
		JsonResponse response = new JsonResponse();
		
		try {
			Player player = playerService.getPlayer(playerId);
			if(null == player) throw new IllegalArgumentException("Player " + playerId + " does not exist!");
			
			Device device = deviceService.getDevice(deviceId);
			if(null == device) throw new IllegalArgumentException("Device" + deviceId + " does not exist!");
			
			playerService.mapPlayerToDevice(player, device);
			response.setStatus("success");
		} catch(Exception e) {
			response.setStatus("error");
			String error = "Could not map player to device: " + e.getMessage();
			response.setErrors(Arrays.asList( new String[]{ error }));
			logger.error(error, e);
		}
		
        return response;
	}
	
}
