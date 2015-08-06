import RPi.GPIO as GPIO
import time
import sys
import select
import termios
import tty

class Motor(object):

    def __init__(self, gpioPinOut):
        self.MIN_DUTY_CYCLE = 1
        self.MAX_DUTY_CYCLE = 100
        self.DUTY_CYCLE_STEP = 1
        self.dutyCycle = self.MIN_DUTY_CYCLE
        self.gpioOut = gpioPinOut
        GPIO.setup(self.gpioOut, GPIO.OUT)
        
        self.pwm = GPIO.PWM(self.gpioOut, 100)

    def changeSpeed(self, value):
      if value > self.MIN_DUTY_CYCLE and value < self.MAX_DUTY_CYCLE:
        self.pwm.ChangeDutyCycle(value)
        self.dutyCycle = value

    def speedUp(self):
      newSpeed = self.dutyCycle + self.DUTY_CYCLE_STEP
      print "Increasing speed of motor " + str(self.gpioOut) + " to " + str(newSpeed)
      self.changeSpeed(newSpeed)

    def slowDown(self):
      newSpeed = self.dutyCycle - self.DUTY_CYCLE_STEP
      print "Decreasing speed of motor " + str(self.gpioOut) + " to " + str(newSpeed)
      self.changeSpeed(newSpeed)

    def initialize(self):
      print "Initializing motor"
      print "Moving to 1%"
      self.pwm.start(self.MIN_DUTY_CYCLE)
      time.sleep(2)
      print "Moving to 100%"
      self.changeSpeed(self.MAX_DUTY_CYCLE)
      time.sleep(2)
#      print "Moving to 10%"
#      self.changeSpeed(10)
      
      print "Ready for action"

    def shutdown(self):
      print "Shutting down motor"
      self.pwm.stop()
      GPIO.cleanup(self.gpioOut)
      

def keyboardInputAvailable():
  return select.select([sys.stdin], [], [], 0) == ([sys.stdin], [], [])

def shutdown():
  MOTORS[0].changeSpeed(MOTORS[0].MIN_DUTY_CYCLE)
  MOTORS[1].changeSpeed(MOTORS[1].MIN_DUTY_CYCLE)
  MOTORS[0].shutdown()
  MOTORS[1].shutdown()
  sys.exit()


GPIO.setmode(GPIO.BCM)

GPIO_PIN_OUT_1 = 17
GPIO_PIN_OUT_2 = 26

MOTOR_1_DECREASE_SPEED_KEY = "z"
MOTOR_1_INCREASE_SPEED_KEY = "x"
MOTOR_2_DECREASE_SPEED_KEY = "."
MOTOR_2_INCREASE_SPEED_KEY = "/"

MOTORS = [ Motor(GPIO_PIN_OUT_1), Motor(GPIO_PIN_OUT_2) ]

try:
  MOTORS[0].initialize()
  MOTORS[1].initialize()

  old_termios_settings = termios.tcgetattr(sys.stdin)
  tty.setcbreak(sys.stdin.fileno())

  while 1:
    if keyboardInputAvailable():
      input = sys.stdin.read(1)
      #if input == '\x1b': # x1b is ESC
      print "Rec'd input: " + str(input)
      if input == MOTOR_1_DECREASE_SPEED_KEY:
        MOTORS[0].slowDown()
      elif input == MOTOR_1_INCREASE_SPEED_KEY:
        MOTORS[0].speedUp()
      elif input == MOTOR_2_DECREASE_SPEED_KEY:
        MOTORS[1].slowDown()
      elif input == MOTOR_2_INCREASE_SPEED_KEY:
        MOTORS[1].speedUp()
      elif input == "0":
        shutdown()

    # Allow a short break
    time.sleep(0.01)
except KeyboardInterrupt:
  MOTORS[0].shutdown()
  MOTORS[1].shutdown()
finally:
  termios.tcsetattr(sys.stdin, termios.TCSADRAIN, old_termios_settings)

shutdown()
