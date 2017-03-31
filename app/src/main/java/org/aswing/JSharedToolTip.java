/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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


import flash.utils.Dictionary;
import flash.display.InteractiveObject;
	
/**
 * Shared instance Tooltip to saving instances.
 * @author iiley
 */
public class JSharedToolTip extends JToolTip{
	
	private static JSharedToolTip sharedInstance ;
	
	private InteractiveObject targetedComponent ;
	private Dictionary textMap ;
	
	public  JSharedToolTip (){
		super();
		setName("JSharedToolTip");
		textMap = new Dictionary(true);
	}
	
	/**
	 * Returns the shared JSharedToolTip instance.
	 * <p>
	 * You can create a your shared tool tip instance too, if you want to 
	 * shared by the default.
	 * </p>
	 * @return a singlton shared instance.
	 */
	public static JSharedToolTip  getSharedInstance (){
		if(sharedInstance == null){
			sharedInstance = new JSharedToolTip();
		}
		return sharedInstance;
	}
	
    /**
     * Registers a component for tooltip management.
     *
     * @param c  a <code>InteractiveObject</code> object to add.
     * @param (optional)tipText the text to show when tool tip display. If the c 
     * 		is a <code>Component</code> this param is useless, if the c is only a 
     * 		<code>InteractiveObject</code> this param is required.
     */
	public void  registerComponent (InteractiveObject c ,String tipText =null ){
		//TODO chech whether the week works
		listenOwner(c, true);
		textMap.put(c,  tipText);
		if(getTargetComponent() == c){
			setTipText(getTargetToolTipText(c));
		}
	}
	

    /**
     * Removes a component from tooltip control.
     *
     * @param component  a <code>InteractiveObject</code> object to remove
     */
	public void  unregisterComponent (InteractiveObject c ){
		unlistenOwner(c);
		delete textMap.get(c);
		if(getTargetComponent() == c){
			disposeToolTip();
			targetedComponent = null;
		}
	}
	
	/**
	 * Registers a component that the tooltip describes. 
	 * The component c may be null and will have no effect. 
	 * <p>
	 * This method is overrided just to call registerComponent of this class.
	 * @param the InteractiveObject being described
	 * @see #registerComponent()
	 */
	 public void  setTargetComponent (InteractiveObject c ){
		registerComponent(c);
	}
	
	/** 
	 * Returns the lastest targeted component. 
	 * @return the lastest targeted component. 
	 */
	 public InteractiveObject  getTargetComponent (){
		return targetedComponent;
	}
	
	protected String  getTargetToolTipText (InteractiveObject c ){
		if(c is Component){
			Component co =(Component)c;
			return co.getToolTipText();
		}else{
			return textMap.get(c);
		}
	}
	
	//-------------
	 protected void  __compRollOver (InteractiveObject source ){
		String tipText =getTargetToolTipText(source );
		if(tipText != null && isWaitThenPopupEnabled()){
			targetedComponent = source;
			setTipText(tipText);
			startWaitToPopup();
		}
	}
	
	 protected void  __compRollOut (InteractiveObject source ){
		if(source == targetedComponent && isWaitThenPopupEnabled()){
			disposeToolTip();
			targetedComponent = null;
		}
	}	
}


