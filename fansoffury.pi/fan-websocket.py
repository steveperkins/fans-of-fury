import RPi.GPIO as GPIO
import time
import sys
# Support for keyboard input
import select
import termios
import tty
# For serialization/deserialization of objects
import json

# Websocket support
import websocket
import thread

# Support for emergency shutoff loop
from threading import Timer
from datetime import datetime

class FanDevice(object):
    def __init__(self, minInputValue, maxInputValue, id, type):
        self.minInputValue = minInputValue
        self.maxInputValue = maxInputValue
        self.id = id;
        self.type = type

    def toJson(self):
      return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, ensure_ascii=False)

class DeviceController(object):
    def __init__(self, id, devices):
      self.id = id
      self.devices = devices

    def toJson(self):
      return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, ensure_ascii=False)

class DeviceControllerRegistrationEvent(object):
    def __init__(self, deviceController):
      self.deviceController = deviceController
      self.messageType = "DEVICE_CONTROLLER_REGISTRATION"

    def toJson(self):
      return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, ensure_ascii=False)

class FanSpeedChangeEvent(object):
    def __init__(self, device, newSpeed):
        self.device = device
        self.newSpeed = newSpeed

class DeviceScoreChangeEvent(object):
    def __init__(self, device):
	self.device = device
	self.messageType = "DEVICE_SCORE_CHANGE"

    def toJson(self):
      return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, ensure_ascii=False)


class Motor(object):

    def __init__(self, gpioPinOut, safetyPin):
        self.MIN_DUTY_CYCLE = 1
        self.MAX_DUTY_CYCLE = 60
	self.DUTY_CYCLE_STEP = 1
        self.dutyCycle = self.MIN_DUTY_CYCLE
        self.gpioOut = gpioPinOut
        GPIO.setup(self.gpioOut, GPIO.OUT)

        self.pwm = GPIO.PWM(self.gpioOut, 480)
	self.lastEventDate = datetime.now()

	self.gpioPinSafety = safetyPin
        GPIO.setup(self.gpioPinSafety, GPIO.IN, pull_up_down=GPIO.PUD_UP)
        GPIO.add_event_detect(self.gpioPinSafety, GPIO.BOTH, callback=self.safetyTurnedOnEvent)
        self.safetyOn = GPIO.input(self.gpioPinSafety)

    def changeSpeed(self, value):
      if value == self.MIN_DUTY_CYCLE or (self.safetyIsOn() == 0 and value > self.MIN_DUTY_CYCLE and value < self.MAX_DUTY_CYCLE):
        if value > self.dutyCycle:
	  while self.dutyCycle < value:
	    self.dutyCycle = self.dutyCycle + self.DUTY_CYCLE_STEP
	    self.pwm.ChangeDutyCycle(self.dutyCycle)
	elif value < self.dutyCycle:
	  while self.dutyCycle > value and self.dutyCycle > 0:
	    self.dutyCycle = self.dutyCycle - self.DUTY_CYCLE_STEP
	    self.pwm.ChangeDutyCycle(self.dutyCycle)

        self.dutyCycle = value
       # self.pwm.ChangeDutyCycle(self.dutyCycle)

    def speedUp(self):
      newSpeed = self.dutyCycle + self.DUTY_CYCLE_STEP
      print "Increasing speed of motor " + str(self.gpioOut) + " to " + str(newSpeed)
      self.changeSpeed(newSpeed)

    def slowDown(self):
      newSpeed = self.dutyCycle - self.DUTY_CYCLE_STEP
      print "Decreasing speed of motor " + str(self.gpioOut) + " to " + str(newSpeed)
      self.changeSpeed(newSpeed)

    def minSpeed(self):
      self.changeSpeed(self.MIN_DUTY_CYCLE)

    def maxSpeed(self):
      self.changeSpeed(self.MAX_DUTY_CYCLE)

    def safetyIsOn(self):
#	return False
        return GPIO.input(self.gpioPinSafety)

    def safetyTurnedOnEvent(self, channel):
        safety = self.safetyIsOn()

        if self.safetyOn and safety == False:
          print "Safety turned off"
          self.safetyOn = False
        elif self.safetyOn == False and safety == True:
          print "Safety turned on"
	  # Button is no longer depressed
          # Stop the motor from moving until the safety is off again
          self.pwm.ChangeDutyCycle(self.MIN_DUTY_CYCLE)
          self.dutyCycle = self.MIN_DUTY_CYCLE
          self.safetyOn = True

    def initialize(self):
      print "Initializing motor"
      # The motors will not start spinning until they've exceeded 10% of the possible duty cycle range. This means initialization must be 1 to 100%, and the motors will start spinning at 11%."
      print "Moving to 1%"
      self.pwm.start(1)
      time.sleep(1)
      print "Moving to 100%"
      self.pwm.ChangeDutyCycle(100)
      time.sleep(1)
      print "Moving to 1%"
      self.pwm.ChangeDutyCycle(1)
      print "Ready for action"

    def shutdown(self):
      print "Shutting down motor"
      self.pwm.stop()
      GPIO.cleanup(self.gpioOut)
      GPIO.cleanup(self.gpioPinSafety)

class PlaySide(object):
    def __init__(self, motor, fanDevice, pirScorePin):
	self.motor = motor
	self.fanDevice = fanDevice
	self.pirScorePin = pirScorePin

	GPIO.setup(self.pirScorePin, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
	# The PIR sensor has its own debouncing resistor. Its minimum possible duration is 2500ms.
	GPIO.add_event_detect(self.pirScorePin, GPIO.RISING, callback=self.scoreChangeSensedEvent, bouncetime=2501)

    def scoreChangeSensedEvent(self, channel):
        print "score changed"
	#scoreChanged(self)

    def initalize(self):
	self.motor.initialize()

def keyboardInputAvailable():
  return select.select([sys.stdin], [], [], 0) == ([sys.stdin], [], [])

def shutdown():
  MOTORS[0].changeSpeed(MOTORS[0].MIN_DUTY_CYCLE)
  MOTORS[1].changeSpeed(MOTORS[1].MIN_DUTY_CYCLE)
  MOTORS[0].shutdown()
  MOTORS[1].shutdown()
  sys.exit()

def scoreChanged(playSide):
  sendWebsocketMessage(DeviceScoreChangeEvent(playSide.fanDevice))


GPIO.setmode(GPIO.BCM)

GPIO_PIN_OUT_1 = 17
GPIO_PIN_OUT_2 = 26
GPIO_PIN_SAFETY_1 = 18
GPIO_PIN_SAFETY_2 = 21
GPIO_PIN_PIR_1 = 14
GPIO_PIN_PIR_2 = 20

MOTORS = [ Motor(GPIO_PIN_OUT_1, GPIO_PIN_SAFETY_1), Motor(GPIO_PIN_OUT_2, GPIO_PIN_SAFETY_2) ]

fans = [ FanDevice(1, MOTORS[0].MAX_DUTY_CYCLE, "0", "fan"), FanDevice(1, MOTORS[1].MAX_DUTY_CYCLE, "1", "fan") ]

deviceControllerConfig = DeviceController("0", fans)

PLAY_SIDES = [ PlaySide(MOTORS[0], fans[0], GPIO_PIN_PIR_1), PlaySide(MOTORS[1], fans[1], GPIO_PIN_PIR_2) ]


try:
  server_socket = None
  PLAY_SIDES[0].motor.initialize()
  PLAY_SIDES[1].motor.initialize()

  def emergencyStop():
    now = datetime.now()
    for x in range(0, len(MOTORS)):
      if PLAY_SIDES[x].motor.dutyCycle > PLAY_SIDES[x].motor.MIN_DUTY_CYCLE and (now - PLAY_SIDES[x].motor.lastEventDate).seconds >= 10:
        print "No speed events received for Play Side " + str(x) + " in > 10 seconds, dropping to min speed"
        PLAY_SIDES[x].motor.minSpeed()
    # Reset the timer so this method will run again in 11 seconds
    Timer(11.0, emergencyStop).start()

  Timer(11.0, emergencyStop).start()

  def parseJsonToObject(json):
    if 'messageType' in json:
      if json['messageType'] == 'FAN_SPEED_CHANGE':
        print "Fan speed change rec'd"
        device = parseJsonToObject(json.loads(json['device']))
        return FanSpeedChangeEvent(device, json['newSpeed'])
    elif 'fan' in json:
      if json['type'] == 'fan':
        return FanDevice(json['minInputValue'], json['maxInputValue'], json['id'], json['type'])
    return json

  def on_message(ws, message):
    print "Rec'd message: " +  message
    #fanSpeedChangeEvent = json.loads(message, object_hook=parseJsonToObject)
    dic = json.loads(message)
    dic['device'] = FanDevice(**dic['device'])
    fanSpeedChangeEvent = FanSpeedChangeEvent(**dic)
    print 'New value: ' + str(fanSpeedChangeEvent.newSpeed)
    fanId = int(fanSpeedChangeEvent.device.id)
    print "Fan ID is " + fanSpeedChangeEvent.device.id
    newValue = float(fanSpeedChangeEvent.newSpeed)
    PLAY_SIDES[fanId].motor.changeSpeed(newValue)
    if newValue > PLAY_SIDES[fanId].motor.MIN_DUTY_CYCLE:
      PLAY_SIDES[fanId].motor.lastEventDate = datetime.now()

  def on_error(ws, error):
    print "Socket error: " + str(error)

  def on_close(ws):
    print "### socket closed ###"
    PLAY_SIDES[0].motor.minSpeed()
    PLAY_SIDES[1].motor.minSpeed()

  def on_open(ws):
    print "Socket opened"
    global server_socket
    server_socket = ws
    # Register this device controller and its devices
    registrationMessage = DeviceControllerRegistrationEvent(deviceControllerConfig)
    sendWebsocketMessage(registrationMessage)

  def sendWebsocketMessage(messageObject):
    if server_socket == None:
      print "Attempted to send message but web socket is not open!"

    if server_socket != None:
      msg = messageObject.toJson().encode('utf8')
      print "Sending message through socket: " + msg
      server_socket.send(msg)

  if __name__ == "__main__":
    # Loop for REAL forever
    print "Connecting to websocket server..."
    while 1:
      try:
        websocket.enableTrace(True)
        # Socket.io (underlying framework for websocket-client) requires '/websocket/' appended to the target URL so it can identify it as a websocket URL. Apparently ws:// protocol wasn't enough?
	# sp - my LAN IP is 192.168.21.41
	# sp - my wifi IP is 192.168.21.26
        ws = websocket.WebSocketApp("ws://192.168.21.39:8080/ws/fancontroller/websocket",
                              on_message = on_message,
                              on_error = on_error,
                              on_close = on_close)
        ws.on_open = on_open
        # 'run_forever' is a lie, like the cake. It actually exits when all sockets have closed.
        ws.run_forever()
      except:
       e = sys.exc_info()[1]
       print e
       print "Reconnecting..."
       time.sleep(20)

except KeyboardInterrupt:
  PLAY_SIDES[0].motor.shutdown()
  PLAY_SIDES[1].motor.shutdown()
finally:
  shutdown()

shutdown()
