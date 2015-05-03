3D LED cube
==========

3d graphical view of a led cube. Basically, a 3d version of this...

http://www.youtube.com/watch?v=2jOtkJAWFkE

Was written as a play pen for trying out scala. Pretty old now!

The idea was to create a simulator to try out ideas on the real cube quickly.
There's a simple DSL for creating messages and a simulator that shows how
the data will look and the code dunps the binary data for the cube to 
standard input

To run, simply install gradle, point at the root directory and run

gradle run

You can use the mouse to move around the image to see if from different views

This will show you one of the 2 demos found under com.cube.samples package

The code uses http://jogamp.org to do the 3d stuff. I'm using their customised
version of java3d that plays nicely with their JOGL implementation

You can easily set this project up in eclipse by using the eclispe grade plugin (refresh the
project at the project level). Import the project as a general project, add java facets to it, 
install the scala project, then use the gradle tool to setup the eclipse project (greadle eclipse)
. Remember to edit in the scala perspective

NOTE THAT I'VE ONLY TESTED THIS ON MAC (OSX 10.10). DUNNO HOW IT WILL WORK ON A WINDOWS MACHINE

if you want to see the simulator in action then goto https://youtu.be/aXc93Iysu7Q
