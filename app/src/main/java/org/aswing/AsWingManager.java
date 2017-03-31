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

	
import flash.display.*;
import flash.events.Event;
import flash.events.TimerEvent;
import flash.utils.Timer;

import org.aswing.error.AsWingManagerNotInited;
import org.aswing.geom.IntDimension;
import org.aswing.util.HashSet;

/**
 * The main manager for AsWing framework.
 * <p>
 * You may need to call <code>setRoot()</code> to set a default root container for 
 * AsWing Popups.
 * </p>
 * @see JPopup
 * @author iiley
 */
public class AsWingManager{
	
	private static Stage stage =null ;
    private static DisplayObjectContainer ROOT =null ;
    private static int INITIAL_STAGE_WIDTH ;
    private static int INITIAL_STAGE_HEIGHT ;
    private static Timer timer ;
    private static Array nextFrameCalls =new Array ();
    private static boolean preventNullFocus =true ;
    
    /**
     * Sets the root container for AsWing components based on.
     * <p>
     * You'd better call this method before application start, before flashplayer 
     * stage resized.
     * </p>
     * Default is <code>AsWingManager.getStage()</code>.
     * @param root the root container for AsWing popups.
     */
    public static void  setRoot (DisplayObjectContainer root ){
        ROOT = root;
        if(root != null && stage == null && root.stage != null){
        	initStage(root.stage);
        }
    }
    
    /**
     * Init AsWing as a standard setting. This method is very important for your App.
     * <p>
     * <ul>
     * <li>If your app is a general Web app or AIR app just have one window(i mean just one stage), you can easy 
     * to pass your root here, and then later you can easy to use <code>AsWingManager.getStage()</code>.
     * </li>
     * <li>If your app is a multi-stage app, (for example AIR app with more than one NativeWindow), you'd better to pass null for this, 
     * 	then the manager will not reference to your stage to make it is GC able when the NativeWindow is close.
     * </li>
     * </ul>
     * </p>
     * @param root the default root container for aswing popups, or null to make no default root.
     * @param _preventNullFocus set true to prevent focus transfer to null, false, not manage to do this
     * @param workWithFlex set this to true if your application ui has both AsWing components and Flex components.
     * @see #setRoot()
     * @see #setPreventNullFocus()
     * @see RepaintManager#setAlwaysUseTimer()
     */
    public static  initAsStandard (
    				root:DisplayObjectContainer, 
    				_preventNullFocus:Boolean=true, 
    				workWithFlex:Boolean=false):void{
		setRoot(root);
		if(stage){
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.stageFocusRect = false;
		}
		preventNullFocus = _preventNullFocus;
		RepaintManager.getInstance().setAlwaysUseTimer(workWithFlex);
    }
    
    /**
     * Sets whether or not prevent focus transfer to null when user click a blank(not focusable object).
     * The default value is true, it is suit for normal applications, if you are develop a app 
     * that are complex and may have other interactive object is not aswing components, you may need to 
     * set this value to false.
     * @param prevent set true to prevent focus transfer to null, false, not manage to do this
     */
    public static void  setPreventNullFocus (boolean prevent ){
    	preventNullFocus = prevent;
    }
    
    /**
     * Returns the preventNullFocus property.
     * @return true means will prevent focus transfer to null, false means do not manage this.
     * @see #setPreventNullFocus()
     */
    public static boolean  isPreventNullFocus (){
    	return preventNullFocus;
    }
    
    /**
     * Sets the intial stage size, this method generally do not need to use.
     * But some times, you know the manager is not initied at right time, i means 
     * some times the manager is inited after the stage is resized, so, you maybe need 
     * to call this method to correct the size.
     * @param width the width of stage when application start.
     * @param width the height of stage when application start.
     */
    public static void  setInitialStageSize (int width ,int height ){
    	INITIAL_STAGE_WIDTH = width;
    	INITIAL_STAGE_HEIGHT = height;
    }
    
    /**
     * Returns the stage initial size.
     * @return the size. 
     */
    public static IntDimension  getInitialStageSize (){
    	if(ROOT == null){
    		throw new AsWingManagerNotInited();
    	}
    	return new IntDimension(INITIAL_STAGE_WIDTH, INITIAL_STAGE_HEIGHT);
    }
    
    /**
     * Returns the root container which components base on.
     * If you have not set a specified root, the first stage will be the root to be returned.
     * <p>
     * Take care to use this method if you are working on a multiple native windows AIR project, 
     * because there maybe more than one stage.
     * </p>
	 * @param checkError whethor or not check root is inited set.
     * @return the root container, or null--not root set and AsWingManager not stage inited.
	 * @throws AsWingManagerNotInited if checkError and both root and stage is null.
     * @see #setRoot()
     * @see #getStage()
     */ 
    public static DisplayObjectContainer  getRoot (boolean checkError =true ){
        if(ROOT == null){
            return getStage(checkError);
        }
        return ROOT;
    }	
	
	/**
	 * Init the stage for AsWing, this method should be better called when flashplayer start.
	 * This method will be automatically called when a component is added to stage.
	 * @param theStage the stage
	 */
	internal static void  initStage (Stage theStage ){
		if(stage == null){
			stage = theStage;
	        INITIAL_STAGE_WIDTH = stage.stageWidth;
	        INITIAL_STAGE_HEIGHT = stage.stageHeight;
		}
	}
	
	/**
	 * Returns whether or not stage is set to the manager.
	 * @return whether or not stage is set to the manager.
	 */
	internal static boolean  isStageInited (){
		return stage != null;
	}
	
	/**
	 * Returns the stage inited by <code>initAsStandard()</code> or <code>setRoot</code>.
	 * <p>
     * Take care to use this method if you are working on a multiple native windows AIR project, 
     * because there maybe more than one stage.
     * </p>
	 * @param checkError whethor or not check is stage is inited set.
	 * @return the stage.
	 * @throws AsWingManagerNotInited if checkError and stage is null.
	 * @see #initAsStandard()
	 * @see #setRoot()
	 */
	public static Stage  getStage (boolean checkError =true ){
		if(checkError && stage==null){
			throw new AsWingManagerNotInited();
		}
		return stage;
	}
	
	/**
	 * Force the screen to be updated after a time.
	 * @param delay the time
	 */
	public static void  updateAfterMilliseconds (int delay =20){
		if(timer == null){
			timer = new Timer(delay, 1);
			timer.addEventListener(TimerEvent.TIMER, __update);
		}
		if(!timer.running){
			timer.reset();
			timer.start();
		}
	}
	
	private static Sprite frameTrigger ;
	/**
	 *Adds a  to the queue to be invoked at next enter frame time 
	 * @param func the function to be invoked at next frame
	 */
	public static void  callNextFrame (Function func ){
		if(frameTrigger == null){
			frameTrigger = new Sprite();
			frameTrigger.addEventListener(Event.ENTER_FRAME, __enterFrame);
		}
		nextFrameCalls.push(func);
	}
	
	public static void  callLater (Function func ,int time =40){
		Timer timer =new Timer(time ,1);
		timer .addEventListener (TimerEvent .TIMER ,void  (TimerEvent e ){
			func();
		});
		timer.start();
	}
	
	private static void  __update (TimerEvent e ){
		e.updateAfterEvent();
	}
	
	private static void  __enterFrame (Event e ){
		Array calls =nextFrameCalls ;
		nextFrameCalls = new Array();
		for(int i =0;i <calls.length ;i ++){
			Function func =calls.get(i) ;
			func();
		}
	}

}


