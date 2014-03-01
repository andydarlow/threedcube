package com.cube.view

import com.cube.model._
import java.awt.BorderLayout
import java.awt.MouseInfo
import java.awt.event._

import javax.media.j3d._
import javax.vecmath._
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.swing._
import java.awt.EventQueue
  
/**
 *this is the  world that the cube will be displayed in. 
 */
class Cube3DWorld(cube : Cube) {

  // position of the screne graph (object) in the world
  var objectPosition : Vector3d = new Vector3d(0,0,-16)
  var xAngle : Angle = new Angle(math.Pi+0.2)
  var yAngle : Angle = new Angle(-0.5)
  val canvas : Canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration(), false)
  
  //-------------- constructor -------------------------------------
  // create the world (called when instance created)
  {
     val scene = createScene(cube) 
	 val universe : SimpleUniverse = new SimpleUniverse(canvas)
     universe.getViewingPlatform().setNominalViewingTransform()
	 universe.addBranchGraph(scene);
     addRotationControlTo(canvas)
     refreshView(xAngle,yAngle,objectPosition)
  }
   
  //---------------------- setup -----------------------------------------
  
  // create the scene on which will be displayed to the user.
  def createScene(cube : Cube) :BranchGroup =  {
     val scene: BranchGroup = new BranchGroup()
     scene.addChild(cube)
	 scene.compile()
     scene
  }

  //------------------ user interaction ------------------------------------
  
  //allows the user to rotate around the scene
  def addRotationControlTo(canvas : Canvas3D) : Unit = {
     canvas.addMouseMotionListener(new MouseMotionListener {
           var lastX : Int = 0
           var lastY : Int = 0 
           def mouseMoved(e: MouseEvent): Unit = {}
           def mouseDragged(e : MouseEvent) : Unit =  {
             //rotate based on movement of mose in X/Y position. 
             def updateAngle(newVal:Int, oldValue: Int, angle : Angle) : Angle = {
                    val rotationSpeed = 10f // bigger the number the slower rotation will look
                    val angleDifference : Float = Math.signum(newVal - oldValue)/rotationSpeed
                    angle.increase(angleDifference)
              }
              yAngle = updateAngle(e.getX,lastX,yAngle)
              xAngle = updateAngle(e.getY,lastY,xAngle)
              lastX = e.getX
              lastY = e.getY
              refreshView(xAngle,yAngle,objectPosition)
           }                     
      })
    }    
  
    //-------------------------- manage the view -------------------------------
  
      //updates the view with the latest positions of the component in the 3d world
      def refreshView(xAngle: Angle, yAngle : Angle, translation : Vector3d) : Unit = {
        		             
                val xRotation : Transform3D = new Transform3D()
				val yRotation : Transform3D = new Transform3D()
				val matrix    : Transform3D =  new Transform3D()

				xRotation.rotY(yAngle)
				yRotation.rotX(xAngle)
				
				matrix.setTranslation(translation)
				matrix.mul(xRotation)
				matrix.mul(yRotation)
				cube.setTransform(matrix);	
      }

      //--------------------------------------------------------------
      
      //shows the world on a java screen...
      def show() : Unit = {
        EventQueue.invokeLater(new Runnable {
                  def run(): Unit = {
                      val frame : JFrame = new JFrame("the cube")
                  	  frame.getContentPane().add(canvas)
                  	  canvas.setBounds(100,100,500,500)
                  	  frame.pack()
                  	  frame.setVisible(true)
                  }               
         })        
      }
}