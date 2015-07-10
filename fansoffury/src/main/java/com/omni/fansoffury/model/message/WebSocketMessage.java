package com.omni.fansoffury.model.message;

import java.io.Serializable;

/**
 * Parent class for all messages serialized over websockets
 * @author steve.perkins
 *
 */
public class WebSocketMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String messageType;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
