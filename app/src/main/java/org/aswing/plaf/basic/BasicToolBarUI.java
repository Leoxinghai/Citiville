/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

	
import org.aswing.plaf.BaseComponentUI;
import org.aswing.*;
import org.aswing.event.*;

/**
 * ToolBar basic ui imp.
 * @author iiley
 * @private
 */
public class BasicToolBarUI extends BaseComponentUI{
	
	protected Container bar ;
	
	public  BasicToolBarUI (){
		super();
	}
	
    protected String  getPropertyPrefix (){
        return "ToolBar.";
    }
    
	 public void  installUI (Component c ){
		bar = Container(c);
		installDefaults();
		installComponents();
		installListeners();
	}
    
	 public void  uninstallUI (Component c ){
		bar = Container(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
 	}
 	
 	protected void  installDefaults (){
        String pp =getPropertyPrefix ();
        
        LookAndFeel.installColorsAndFont(bar, pp);
        LookAndFeel.installBorderAndBFDecorators(bar, pp);
        LookAndFeel.installBasicProperties(bar, pp);
 	}
	
 	protected void  uninstallDefaults (){
 		LookAndFeel.uninstallBorderAndBFDecorators(bar);
 	}
 	
 	protected void  installComponents (){
 		for(int i =0;i <bar.getComponentCount ();i ++){
 			adaptChild(bar.getComponent(i));
 		}
 	}
	
 	protected void  uninstallComponents (){
 		for(int i =0;i <bar.getComponentCount ();i ++){
 			unadaptChild(bar.getComponent(i));
 		}
 	}
 	
 	protected void  installListeners (){
 		bar.addEventListener(ContainerEvent.COM_ADDED, __onComAdded);
 		bar.addEventListener(ContainerEvent.COM_REMOVED, __onComRemoved);
 	}
	
 	protected void  uninstallListeners (){
 		bar.removeEventListener(ContainerEvent.COM_ADDED, __onComAdded);
 		bar.removeEventListener(ContainerEvent.COM_REMOVED, __onComRemoved);
 	}
 	
 	protected void  adaptChild (Component c ){
    	AbstractButton btn =(AbstractButton)c;
    	if(btn != null){
    		GroundDecorator bg =btn.getBackgroundDecorator ();
    		if(bg != null){
    			ToolBarButtonBgAdapter bgAdapter =new ToolBarButtonBgAdapter(bg );
    			btn.setBackgroundDecorator(bgAdapter);
    		}
    		btn.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __propertyChanged);
    	}
 	}
 	
 	protected void  unadaptChild (Component c ){
    	AbstractButton btn =(AbstractButton)c;
    	if(btn != null){
    		btn.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __propertyChanged);
    		ToolBarButtonBgAdapter bg =btn.getBackgroundDecorator ()as ToolBarButtonBgAdapter ;
    		if(bg != null){
    			btn.setBackgroundDecorator(bg.getOriginalBg());
    		}
    	}
 	}
    
    //------------------------------------------------
 	
 	private void  __propertyChanged (PropertyChangeEvent e ){
 		if(e.getPropertyName() == "backgroundDecorator"){
 			AbstractButton btn =(AbstractButton)e.target;
 			//GroundDecorator oldG =e.getOldValue ();
 			GroundDecorator newG =e.getNewValue ();
 			if(!(newG is ToolBarButtonBgAdapter)){
    			ToolBarButtonBgAdapter bgAdapter =new ToolBarButtonBgAdapter(newG );
    			btn.setBackgroundDecorator(bgAdapter);
 			}
 		}
 	}
 	
    private void  __onComAdded (ContainerEvent e ){
    	adaptChild(e.getChild());
    }
    
    private void  __onComRemoved (ContainerEvent e ){
    	unadaptChild(e.getChild());
    }
}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                 BG Decorator Adapter
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import org.aswing.graphics.Graphics2D;
import org.aswing.GroundDecorator;
import org.aswing.geom.IntRectangle;
import org.aswing.Component;
import flash.display.DisplayObject;
import org.aswing.AbstractButton;
import org.aswing.plaf.UIResource;

/**
 * This background adapter will invisible the original background, and visible it 
 * only when button be rollover.
 * @author iiley
 */
class ToolBarButtonBgAdapter implements GroundDecorator, UIResource{
	
	private GroundDecorator originalBg ;
	
	public  ToolBarButtonBgAdapter (GroundDecorator originalBg ){
		this.originalBg = originalBg;
	}
	
	public GroundDecorator  getOriginalBg (){
		return originalBg;
	}
	
	public void  updateDecorator (Component c ,Graphics2D g ,IntRectangle bounds ){
		if(originalBg == null){
			return;
		}
		AbstractButton btn =(AbstractButton)c;
		boolean needPaint =false ;
		if(btn == null || btn.getModel().isArmed() || btn.isSelected() 
			|| (btn.getModel().isRollOver() && !btn.getModel().isPressed())){
			needPaint = true;
		}
		DisplayObject dis =getDisplay(c );
		if(dis != null) dis.visible = needPaint;
		if(needPaint){
			originalBg.updateDecorator(c, g, bounds);
		}
	}
	
	public DisplayObject  getDisplay (Component c ){
		if(originalBg == null){
			return null;
		}
		return originalBg.getDisplay(c);
	}
	
}


