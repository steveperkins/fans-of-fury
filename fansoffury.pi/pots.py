#!/usr/bin/env python
# Written by Limor "Ladyada" Fried for Adafruit Industries, (c) 2015 
# This code is released into the public domain
import time 
import os 
import RPi.GPIO as GPIO

class Motor(object):
    def __init__(self, gpioPinOut):
        self.MIN_DUTY_CYCLE = 1
        self.MAX_DUTY_CYCLE = 60
		self.DUTY_CYCLE_STEP = 0.1
        self.dutyCycle = self.MIN_DUTY_CYCLE
        self.gpioOut = gpioPinOut
        GPIO.setup(self.gpioOut, GPIO.OUT)

        self.pwm = GPIO.PWM(self.gpioOut, 480)

    def changeSpeed(self, value):
      if value >= self.MIN_DUTY_CYCLE and value < self.MAX_DUTY_CYCLE:
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

    def initialize(self):
      print "Initializing motor"
      # The motors will not start spinning until they've exceeded 10% of the possible duty cycle range. This means initialization must be 1 to 100%, and the motors will start spinning at 11%.
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
	  
class Pot(object):
    def __init__(self, channel, clockPin, mosiPin, misoPin, csPin, motor):
        self.channel = channel
		self.clockPin = clockPin
		self.mosiPin = mosiPin
		self.misoPin = misoPin
		self.csPin = csPin
		self.motor = motor
		self.lastReadValue = 0
		self.movementTolerance = 2
	
	def processInput(self):
		currentValue = self.getInputValue()
		potAdjustment = abs(currentValue - self.last_read)
		print "Current pot " + self.channel + " value: " + potAdjustment
		if potAdjustment >= tolerance:
			# Translate this 0 - 1023 value to a percentage of the motor's range
			newDutyCycle = 0
			if potAdjustment == 0:
				newDutyCycle = 0
			elif potAdjustment = 1023:
				newDutyCycle = motor.MAX_DUTY_CYCLE
			else:
				potPercentage = potAdjustment / 1023
				newDutyCycle = self.motor.MAX_DUTY_CYCLE * potPercentage
			
			print "New duty cycle: " + newDutyCycle
			motor.changeDutyCycle(newDutyCycle)
			last_read = potAdjustment
		
	def getInputValue(self):
		GPIO.output(self.csPin, True)
		GPIO.output(self.clockPin, False) # start clock low
		GPIO.output(self.csPin, False) # bring CS low
		commandout = self.channel
		commandout |= 0x18 # start bit + single-ended bit
		commandout <<= 3 # we only need to send 5 bits here
		for i in range(5):
				if (commandout & 0x80):
						GPIO.output(self.mosiPin, True)
				else:
						GPIO.output(self.mosiPin, False)
				commandout <<= 1
				GPIO.output(self.clockPin, True)
				GPIO.output(self.clockPin, False)
		adcout = 0
		# read in one empty bit, one null bit and 10 ADC bits
		for i in range(12):
				GPIO.output(self.clockPin, True)
				GPIO.output(self.clockPin, False)
				adcout <<= 1
				if (GPIO.input(self.misoPin)):
						adcout |= 0x1
		GPIO.output(self.csPin, True)
		
		adcout >>= 1 # first bit is 'null' so drop it
		return adcout
	  

GPIO.setmode(GPIO.BCM) 
GPIO_PIN_OUT_1 = 17
GPIO_PIN_OUT_2 = 26

# change these as desired - they're the pins connected from the SPI port 
# on the ADC to the Cobbler
SPICLK = 18
SPIMISO = 23
SPIMOSI = 24
SPICS = 25
# set up the SPI interface pins
GPIO.setup(SPIMOSI, GPIO.OUT)
GPIO.setup(SPIMISO, GPIO.IN) 
GPIO.setup(SPICLK, GPIO.OUT)
GPIO.setup(SPICS, GPIO.OUT)

MOTORS = [ Motor(GPIO_PIN_OUT_1), Motor(GPIO_PIN_OUT_2) ]
POTS = [ Pot(0, SPICLK, SPIMOSI, SPIMISO, SPICS, MOTORS[0]), Pot(1, SPICLK, SPIMOSI, SPIMISO, SPICS, MOTORS[1]) ]

def shutdown():
  MOTORS[0].changeSpeed(MOTORS[0].MIN_DUTY_CYCLE)
  MOTORS[1].changeSpeed(MOTORS[1].MIN_DUTY_CYCLE)
  MOTORS[0].shutdown()
  MOTORS[1].shutdown()
  GPIO.cleanup(SPIMOSI)
  GPIO.cleanup(SPIMISO)
  GPIO.cleanup(SPICLK)
  GPIO.cleanup(SPICS)
  sys.exit()
 

try:
	MOTORS[0].initialize();
	MOTORS[1].initialize();

	while True:
		POTS[0].processInput()
		POTS[1].processInput()
		
		# Rest a bit
		time.sleep(0.1)
except KeyboardInterrupt:
  MOTORS[0].shutdown()
  MOTORS[1].shutdown()
finally:
  shutdown()

shutdown()
