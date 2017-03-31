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


import flash.display.Stage;
import flash.events.Event;
import flash.events.TimerEvent;
import flash.utils.Timer;

import org.aswing.util.HashSet;
/**
 * RepaintManager use to manager the component's painting.
 * 
 * <p>If you want to repaint a component, call its repaint method, the component will register itself
 * to RepaintManager, when this frame end, it will call its paintImmediately method to paint its.
 * So component's repaint method is fast. But it will not paint immediately, if you want to paint
 * it immediately, you should call paintImmediately method, but it is not fast.</p>
 * @author iiley
 */	
public class RepaintManager{
	
	private static RepaintManager instance ;
	
	/**
	 * Although it's a set in fact, but it work more like a queue
	 * The component will not be added twice into the repaintQueue (one time a component do not need more than one painting)
	 */
	private HashSet repaintQueue ;
	/**
	 * similar to repaintQueue
	 */
	private HashSet validateQueue ;
	
	private Timer timer ;
	private boolean renderring ;
	private boolean alwaysUseTimer ;
	
	/**
	 * Singleton class, 
	 * Don't create instance directly, in stead you should call <code>getInstance()</code>.
	 */
	public  RepaintManager (){
		if(instance != null){
			throw new Error("Singleton can't be create more than once!");
		}
		repaintQueue = new HashSet();
		validateQueue = new HashSet();
		renderring = false;
		alwaysUseTimer = false;
		timer = new Timer(19, 1);
		timer.addEventListener(TimerEvent.TIMER_COMPLETE, __timerRender);
	}
	
	/**
	 * Init the repaint manager, it will works better when it is inited.
	 * By default, it will be inited when a component is added to stage automatically.
	 */	
	/*internal void  init (Stage theStage ){
		if(stage == null){
			stage = theStage;
			stage.addEventListener(Event.RENDER, __render);
		}
	}*/
	
	public static RepaintManager  getInstance (){
		if(instance == null){
			instance = new RepaintManager();
		}
		return instance;
	}
	
	/**
	 * Sets whether or not always use timer to trigger the repaint progress.
	 * By default it is false, means use stage Event.RENDER to trigger at most time.
	 * It is better smooth for the rendering for Event.RENDER way, but if you make AsWing 
	 * components works with Flex component, you should change to timer way.
	 * @param b true to make it always use timer, false not.
	 * @param delay the timer delay, by default it is 19 ms.
	 */
	public void  setAlwaysUseTimer (boolean b ,int delay =19){
		alwaysUseTimer = b;
		timer.delay = delay;
	}
		
	/**
	 * Regist A Component need to repaint.
	 * @see org.aswing.Component#repaint()
	 */
	public void  addRepaintComponent (Component com ){
		repaintQueue.add(com);
		renderLater(com);
	}
	
	/**
	 * Find the Component's validate root parent and regist it need to validate.
	 * @see org.aswing.Component#revalidate()
	 * @see org.aswing.Component#validate()
	 * @see org.aswing.Component#invalidate()
	 */	
	public void  addInvalidComponent (Component com ){
		Component validateRoot =getValidateRootComponent(com );
		if(validateRoot != null){
			validateQueue.add(validateRoot);
			renderLater(com);
		}
	}
	
	/**
	 * Regists it need to be validated.
	 * @see org.aswing.Component#validate()
	 */	
	public void  addInvalidRootComponent (Component com ){
		validateQueue.add(com);
		renderLater(com);
	}
		
	private void  renderLater (Component c ){
		Stage st =c.stage ;
		if(alwaysUseTimer || st == null || renderring){
			if(!timer.running){
				timer.reset();
				timer.start();
			}
		}else{
			st.addEventListener(Event.RENDER, __render, false, 0, true);
			st.invalidate();
		}
	}
	
	/**
	 * If the ancestor of the component has no parent or it is isValidateRoot 
	 * and it's parent are visible, then it will be the validate root component,
	 * else it has no validate root component.
	 */
	private Component  getValidateRootComponent (Component com ){
		Component validateRoot =null ;
		Component i ;
		for(i = com; i!=null; i=i.getParent()){
			if(i.isValidateRoot()){
				validateRoot = i;
				break;
			}
		}
		
		//Component root =null ;
		//check TODO if the root here is needed ,if not delte the declar
		for(i = validateRoot; i!=null; i=i.getParent()){
			if(!i.isVisible()){
				//return null;
			}
		}
		return validateRoot;
	}
	
	private void  __timerRender (TimerEvent e ){
		__render();
		e.updateAfterEvent();
	}
	
        public void  forceRender ()
        {
            this.__render();
            return;
        }//end  

	/**
	 * Every frame this method will be executed to invoke the painting of components needed.
	 */
	private void  __render (Event e =null ){
		if(e){
			Stage st =(Stage)e.currentTarget;
			st.removeEventListener(Event.RENDER, __render);
		}
		int i ;
		int n ;
		Component com ;
		//time++;
		
		renderring = true;
		
//		var time:Number = getTimer();
		Array processValidates =validateQueue.toArray ();
		//must clear here, because there maybe addRepaintComponent at below operation
		validateQueue.clear();
		n = processValidates.length;
		i = -1;
		if(n > 0){
			//trace("------------------------one tick---------------------------");
		}
		while((++i) < n){
			com = processValidates.get(i);
			com.validate();
			//trace("validating com : " + com);
		}
//		if(n > 0){
//			trace(n + " validate time : " + (getTimer() - time));
//		}
//		time = getTimer();
		
		
		Array processRepaints =repaintQueue.toArray ();
		//must clear here, because there maybe addInvalidComponent at below operation
		repaintQueue.clear();
		
		n = processRepaints.length;
		i = -1;
		while((++i) < n){
			com = processRepaints.get(i);
			com.paintImmediately();
		}
//		if(n > 0){
//			trace(n + " paint time : " + (getTimer() - time));
//		}
		renderring = false;
	}	
}


