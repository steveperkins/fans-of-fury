# fans-of-fury
# Omni Resources That Conference 2015 Fans of Fury

![Fans of Fury at That Conference 2015](https://scontent-ord1-1.xx.fbcdn.net/hphotos-xtf1/t31.0-8/11864912_931711303557446_5529343220323690516_o.jpg "Omni Resources Fans of Fury, That Conference 2015")
![Fans of Fury at That Conference 2015](https://scontent-ord1-1.xx.fbcdn.net/hphotos-xpf1/t31.0-8/11872311_1117004178327888_191247630323205132_o.jpg "Fans of Fury at That Conference 2015")
See more photos and videos at [Omni Resources' Facebook page](https://www.facebook.com/omniresources),

[That Conference's Facebook page](https://www.facebook.com/ThatConference/photos_stream),

or [That Conference's Flickr gallery](https://www.flickr.com/photos/thatconference/sets/72157656998516056?utm_content=buffer4566f&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer) .

For an electronic parts list to build your own Fans of Fury, see http://www.omniresources.com/fans-of-fury-parts-list .


This repository contains the code from the Omni Resources Fans of Fury project demonstrated at That Conference 2015 in Wisconsin Dells, WI.

There are four projects in this repository:

<table>
 <thead>
  <tr><td>Project</td><td>Description</td></tr>
 </thead>
 <tbody>
  <tr><td>fansoffury</td><td>Fans of Fury server</td></tr>
  <tr><td>fansoffury.py</td><td>Python code to be run on Raspberry Pi (motor/score controL)</td></tr>
  <tr><td>fansoffury.mobile</td><td>Mobile app (player control)</td></tr>
  <tr><td>OmniResources.FansOfFuryPublicWeb</td><td>High score website</td></tr>
 </tbody>
</table>

## fansoffury
Web server project that listens to the Mindwave Mobile Bluetooth headset data streams and coordinates all other game components, including fan motor control and scoreboard display. The project is a Spring Boot application, so you can run it as a Java application. See `fansoffury/src/main/resources/static/readme.html` for more information. As indicated in the readme, you will need to create a Postgres database (default name is 'fof') before starting the server. The readme also describes the interactions between the server, headsets, fan motors, and scoring sensors.

## fansoffury.pi
Python scripts for use in development/testing and production. The production script is `fan-websocket.py`. fandual.py is a simple script that initializes two fan ESCs and changes the motor speed based on keyboard input.

## fansoffury.mobile
iPhone application used to manage players. The application was built in Xamarin iOS and allows the user to scan a QR code, associate it to a player name, associate it to a headset ID, and associate it to a fan motor.

## OmniResources.FansOfFuryPublicWeb
Separate high score website. High scores are pulled from a database and displayed on a web page.

# Other Information
Fans of Fury was first demonstrated at the [Omni Resources](http://www.omniresources.com) booth at That Conference 2015. It was created as a social object sparking imagination, communication, and engagement.

The Omni Resources Fans of Fury game provides an electronic platform for interested community members to build their own game or tool. The electronic parts list is available at http://www.omniresources.com/fans-of-fury-parts-list and a brief photo description of the development process at http://www.omniresources.com/fans-of-fury-how-we-built-it. Building your own mind-controlled device game based on the Fans of Fury platform is easy and the possibilities are imagination-limited. Want to summon a robotic butler with just a thought? Build the butler and integrate the Fans of Fury platform. Want to snap a photo and tweet it when you get angry? Grab a webcam and clone the fans-of-fury repo. Want to show an automatic "Do Not Disturb" sign when you're in the zone? Make the sign and grab the fans-of-fury repo.

If you find bugs or have improvements, Omni Resources encourages you to better the code in this repository via pull requests. If you have questions about building your own Fans of Fury game, [hit us up on Facebook](https://www.facebook.com/omniresources)! We love hearing about the games people have built too!
