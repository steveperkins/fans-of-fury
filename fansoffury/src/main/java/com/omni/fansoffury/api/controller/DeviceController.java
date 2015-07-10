package com.omni.fansoffury.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.json.JsonResponse;
import com.omni.fansoffury.player.PlayerService;

@RestController
public class DeviceController {
	private static Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private HeadsetService headsetService;
	
	@RequestMapping(value = "/api/devices", method = RequestMethod.GET)
	public JsonResponse getDevices() {
		logger.debug("GET to /api/devices");
		
		JsonResponse response = new JsonResponse();
		response.setObject(deviceService.getDevices());
		response.setStatus("success");
		
		return response;
	}
	
}
