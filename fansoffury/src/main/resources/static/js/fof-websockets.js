var scoreWs = null;
function connectScoreListener(onOpenCallback, onMessageCallback, onCloseCallback) {
	var target = "/ws/score";
	scoreWs = new SockJS(target);
	scoreWs.onopen = function () {
		log('Info: /ws/score WebSocket connection opened.');
		if(onOpenCallback) onOpenCallback(event);
	};
	scoreWs.onmessage = function (event) {
		log('/ws/score Received: ' + event.data);
		if(onMessageCallback) onMessageCallback(JSON.parse(event.data));
	};
	scoreWs.onclose = function () {
		log('Info: /ws/score WebSocket connection closed.');
		if(onCloseCallback) onCloseCallback(event);
	};
}
function disconnectScoreListener() {
	if (scoreWs != null) {
		scoreWs.close();
		scoreWs = null;
	}
	scoreListenerConnected(false);
}

var eegWs = null;
function connectEegListener(onOpenCallback, onMessageCallback, onCloseCallback) {
	var target = "/ws/eeg";
	eegWs = new SockJS(target);
	eegWs.onopen = function () {
		log('Info: /ws/eeg WebSocket connection opened.');
		if(onOpenCallback) onOpenCallback(event);
	};
	eegWs.onmessage = function (event) {
		log('/ws/eeg Received: ' + event.data);
		if(onMessageCallback) onMessageCallback(JSON.parse(event.data));
	};
	eegWs.onclose = function () {
		log('Info: /ws/eeg WebSocket connection closed.');
		if(onCloseCallback) onCloseCallback(event);
	};
}
function disconnectEegListener() {
	if (eegWs != null) {
		eegWs.close();
		eegWs = null;
	}
	eegListenerConnected(false);
}

function sendWsMessage(websocket, message) {
	if (websocket != null) {
		websocket.send(message);
		log('Sent: ' + message);
	} else {
		log('WebSocket connection not established, please connect.');
	}
}

function log(message) {
	console.log(message);
}