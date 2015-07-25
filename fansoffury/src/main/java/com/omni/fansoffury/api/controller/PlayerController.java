package com.omni.fansoffury.api.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetId;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.json.JsonHeadset;
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
		
		JsonHeadset player = new JsonHeadset();
		player.setPlayerId("0");
		player.setHeadsetId(HeadsetId.HEADSET_1.getId());
		player.setMeasurementType(EventType.ATTENTION);
		assignPlayer(player);
		
		player.setPlayerId("1");
		player.setHeadsetId(HeadsetId.HEADSET_2.getId());
		player.setMeasurementType(EventType.MEDITATION);
		assignPlayer(player);
		
		player.setPlayerId("2");
		player.setHeadsetId(HeadsetId.HEADSET_3.getId());
		player.setMeasurementType(EventType.ATTENTION);
		assignPlayer(player);
		
		// This headset is in Appleton right now
		player.setPlayerId("3");
		player.setHeadsetId(HeadsetId.HEADSET_4.getId());
		player.setMeasurementType(EventType.MEDITATION);
		
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
	
	@RequestMapping(value = "/api/player", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonResponse assignPlayer(@RequestBody JsonHeadset jsonHeadset) {
		logger.debug("PUT to /api/player");
		
		JsonResponse response = new JsonResponse();
		response.setStatus("success");
		try {
			if(StringUtils.isEmpty(jsonHeadset.getHeadsetId())) throw new IllegalArgumentException("Headset ID is required");
			if(StringUtils.isEmpty(jsonHeadset.getPlayerId())) throw new IllegalArgumentException("Player ID is required");
			if(null == jsonHeadset.getMeasurementType()) jsonHeadset.setMeasurementType(EventType.ATTENTION);
			
			Headset headset = headsetService.getHeadset(jsonHeadset.getHeadsetId());
			if(null == headset) throw new IllegalArgumentException("Headset '" + jsonHeadset.getHeadsetId() + "' doesn't exist!");
			
			Boolean createNewSession = Boolean.FALSE;
			Player player = playerService.getPlayer(jsonHeadset.getPlayerId());
			player.setMeasurementType(jsonHeadset.getMeasurementType());

			// TODO what is the desired logic for when a player is moved from on-deck (no fan) to paying the game (conected fan)
			// should a new player session be created?  Right now the fan for a player in this case will not be recorded
			// and the start time will be from when they were on deck
			if(null == headset.getPlayer()) {
				createNewSession = Boolean.TRUE;
			} else if(!headset.getPlayer().getId().equals(player.getId())) {
				playerService.endPlayerSession(headset);
				createNewSession = Boolean.TRUE;
			}

			// Map the specified headset to this player
			headsetService.changeHeadsetPlayer(headset, player);

			Device device = null;
			// If a fan ID has been provided, map the specified headset to the fan
			if(!StringUtils.isEmpty(jsonHeadset.getFanId())) {
				device = deviceService.getDevice(jsonHeadset.getFanId());
				if(null == device) throw new IllegalArgumentException("Fan ID '" + jsonHeadset.getFanId() + "' doesn't exist!");
				headsetService.changeHeadsetDevice(headset, device);
			} else if(null != headset.getDevice()){
				// Otherwise the player is being disassociated from its current device
				headsetService.changeHeadsetDevice(headset, null);
			}
			
			if(createNewSession) playerService.startPlayerSession(headset);

			response.setObject(player);
		} catch(Exception e) {
			response.setStatus("error");
			String error = "Could not map player to headset: " + e.getMessage();
			response.setErrors(Arrays.asList( new String[]{ error }));
			logger.error(error, e);
		}
		return response;
	}
	
}
