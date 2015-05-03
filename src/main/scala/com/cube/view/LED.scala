package com.cube.view

import com.cube.model._
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.j3d._
import javax.vecmath._
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

// represents the LED that is displayed in the cube. Its the visual representation
// of the LED. The LED is displayed as a java 3d sphere. It can be turned on or off.
// location is the location in the java 3d world
class LED(location : Point) {
  
   // this will change based on whether the led is on or off..
   var appearance : Appearance = new Appearance()
   
   // the LED (Sphere) is displayed under this java 3d construct
   var renderedImage : TransformGroup = new TransformGroup()

       
    // the constructor...  
    {
       appearance  = createAppearance()
 
       val material = createMaterial()
       appearance.setMaterial(material)
	   val led : Sphere = new Sphere(0.3f,appearance)
       positionLED()
  	   renderedImage.addChild(led)
   }
  
   
   //-----------------  state of the LED ---------------------------------
   
   // switch it on...
    def on() = {
         val material = appearance.getMaterial()
      	 material.setEmissiveColor(new Color3f(0.1f,0.9f,0.1f))
	     material.setDiffuseColor(new Color3f(0.1f,0.9f,0.1f))
	     material.setAmbientColor(new Color3f(0.1f,0.9f,0.1f))
         appearance.getTransparencyAttributes().setTransparency(0f)
         material.setShininess(40)	
    }
 
    //switch it off
    def off() = {
        val material = appearance.getMaterial()
      	material.setEmissiveColor(new Color3f(0.6f,0.6f,0.6f))
	    material.setDiffuseColor(new Color3f(0.6f,0.6f,0.6f))
        appearance.getTransparencyAttributes().setTransparency(0.8f)
        material.setShininess(10)	
 
    }

   //---------------------------------------------------------------------
  
    // where the LED will appear in 3d Space
    def positionLED() = {
       val spherePosition : Transform3D = new Transform3D()
	   spherePosition.setTranslation(new Vector3d(location.x,location.y,location.z))
	   renderedImage.setTransform(spherePosition)
    }
    
   //below are methods for creating the java 3d attributes (e.g. Appearance )
   
    def createAppearance() : Appearance = {
      	val a : Appearance = new Appearance()
        a.setCapability( Appearance.ALLOW_MATERIAL_READ )
        a.setCapability( Appearance.ALLOW_MATERIAL_WRITE);
        a.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        a.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        a.setCapability(TransparencyAttributes.ALLOW_MODE_WRITE)
  	    a.setCapability(TransparencyAttributes.ALLOW_MODE_READ)
  	    a.setCapability(TransparencyAttributes.ALLOW_VALUE_READ)
  	    
	    a.setColoringAttributes(new ColoringAttributes(new Color3f(0.5f, 0f, 1.0f),ColoringAttributes.SHADE_GOURAUD))
	    val transpancy : TransparencyAttributes = new TransparencyAttributes()
	    transpancy.setTransparencyMode(TransparencyAttributes.FASTEST)
        transpancy.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE)
        transpancy.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
       // transpancy.setCapabilityIsFrequent(TransparencyAttributes.ALLOW_VALUE_READ);
       // transpancy.setCapabilityIsFrequent(TransparencyAttributes.ALLOW_VALUE_WRITE);
	    a.setTransparencyAttributes(transpancy)
        a
    }
  
    def createMaterial() : Material = {
        val m : Material = new Material()
       	m.setCapability(Material.ALLOW_COMPONENT_WRITE)
       	m.setCapability(Material.ALLOW_COMPONENT_READ ) 
        m
    }
        
   //--------------------------------------------------------------
    
  /**
   * draws the object as a java 3d object
   */
  def as3dScene() : Node = renderedImage

}