/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.resizer;

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

	
import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.AWSprite;
import org.aswing.event.*;
	
/**
 * The Handler for Resizer's mc bars.
 * @author iiley
 */
public class DefaultResizeBarHandler{
	
	private DefaultResizer resizer ;
	private AWSprite mc ;
	private double arrowRotation ;
	private ResizeStrategy strategy ;
	
	public  DefaultResizeBarHandler (DefaultResizer resizer ,AWSprite barMC ,double arrowRotation ,ResizeStrategy strategy ){
		this.resizer = resizer;
		mc = barMC;
		this.arrowRotation = arrowRotation;
		this.strategy = strategy;
		handle();
	}
	
	public static DefaultResizeBarHandler  createHandler (DefaultResizer resizer ,AWSprite barMC ,double arrowRotation ,ResizeStrategy strategy ){
		return new DefaultResizeBarHandler(resizer, barMC, arrowRotation, strategy);
	}
	
	private void  handle (){
		mc.addEventListener(MouseEvent.ROLL_OVER, __onRollOver);
		mc.addEventListener(MouseEvent.ROLL_OUT, __onRollOut);
		mc.addEventListener(MouseEvent.MOUSE_DOWN, __onPress);
		mc.addEventListener(MouseEvent.MOUSE_UP, __onUp);
		mc.addEventListener(MouseEvent.CLICK, __onRelease);
		mc.addEventListener(ReleaseEvent.RELEASE_OUT_SIDE, __onReleaseOutside);
		mc.addEventListener(Event.REMOVED_FROM_STAGE, __onDestroy);
	}
	
	private void  __onRollOver (MouseEvent e ){
		if(!resizer.isResizing() && (e ==null || !e.buttonDown)){
			resizer.startArrowCursor();
			__rotateArrow();
			if(mc.stage){
				mc.stage.addEventListener(MouseEvent.MOUSE_MOVE, __rotateArrow, false, 0, true);
			}
		}
	}
	
	private void  __onRollOut (MouseEvent e ){
		if(!resizer.isResizing() && !e.buttonDown){
			if(mc.stage){
				mc.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __rotateArrow);
			}
			resizer.stopArrowCursor();
		}
	}
	
	private void  __onPress (MouseEvent e ){
		resizer.setResizing(true);
		startResize(e);
		if(mc.stage){
			mc.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __rotateArrow);
			mc.stage.addEventListener(MouseEvent.MOUSE_MOVE, resizing, false, 0, true);
		}
	}
	
	private void  __onUp (MouseEvent e ){
		__onRollOver(null);
	}
	
	private void  __onRelease (Event e ){
		resizer.setResizing(false);
		resizer.stopArrowCursor();
		if(mc.stage){
			mc.stage.removeEventListener(MouseEvent.MOUSE_MOVE, resizing);
		}
		finishResize();
	}
	
	private void  __onReleaseOutside (Event e ){
		__onRelease(e);
	}
	
	private void  __onDestroy (Event e ){
		mc.stage.removeEventListener(MouseEvent.MOUSE_MOVE, resizing);
		mc.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __rotateArrow);
	}
	
	private void  __rotateArrow (Event e =null ){
		resizer.setArrowRotation(arrowRotation);
	}
	
	private void  startResize (MouseEvent e ){
		resizer.startResize(strategy, e);
	}
	
	private void  resizing (MouseEvent e ){
		resizer.resizing(strategy, e);
	}
	
	private void  finishResize (){
		resizer.finishResize(strategy);
	}
}


