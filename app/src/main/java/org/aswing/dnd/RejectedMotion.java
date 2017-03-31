
package org.aswing.dnd;

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


import org.aswing.Component;
import flash.display.Sprite;
import flash.utils.Timer;
import flash.events.TimerEvent;
import flash.geom.Point;
import org.aswing.geom.IntPoint;

/**
 * The motion of the drop target does not accept the dropped initiator. 
 * @author iiley
 */
public class RejectedMotion implements DropMotion{
		
	private Timer timer ;
	private IntPoint initiatorPos ;
	private Sprite dragObject ;
	
	public  RejectedMotion (){
		timer = new Timer(40);
		timer.addEventListener(TimerEvent.TIMER, __enterFrame);
	}
	
	private void  startNewMotion (Component dragInitiator ,Sprite dragObject ){
		this.dragObject = dragObject;
		initiatorPos = dragInitiator.getGlobalLocation();
		if(initiatorPos == null){
			initiatorPos = new IntPoint();
		}
		timer.start();
	}
	
	public void  forceStop (){
		finishMotion();
	}
	
	public void  startMotionAndLaterRemove (Component dragInitiator ,Sprite dragObject ){
		startNewMotion(dragInitiator, dragObject);
	}
	
	private void  finishMotion (){
		if(timer.running){
			timer.stop();
			dragObject.alpha = 1;
			if(dragObject.parent != null){
				dragObject.parent.removeChild(dragObject);
			}
		}
	}
	
	private void  __enterFrame (TimerEvent e ){
		//check first
		double speed =0.25;
		
		Point p =new Point(dragObject.x ,dragObject.y );
		p = dragObject.parent.localToGlobal(p);
		p.x += (initiatorPos.x - p.x) * speed;
		p.y += (initiatorPos.y - p.y) * speed;
		if(Point.distance(p, initiatorPos.toPoint()) < 2){
			finishMotion();
			return;
		}
		p = dragObject.parent.globalToLocal(p);
		dragObject.alpha += (0.04 - dragObject.alpha) * speed;
		dragObject.x = p.x;
		dragObject.y = p.y;
	}
	
}


