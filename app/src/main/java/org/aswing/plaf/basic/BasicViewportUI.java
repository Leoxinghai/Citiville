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


import org.aswing.*;
import org.aswing.event.*;
import org.aswing.plaf.BaseComponentUI;
import org.aswing.geom.IntPoint;
import flash.ui.Keyboard;
import flash.events.MouseEvent;

/**
 * @private
 */
public class BasicViewportUI extends BaseComponentUI{
	
	protected JViewport viewport ;
	
	public  BasicViewportUI (){
		super();
	}
	
	 public void  installUI (Component c ){
		viewport = JViewport(c);
		installDefaults();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		viewport = JViewport(c);
		uninstallDefaults();
		uninstallListeners();
	}

    protected String  getPropertyPrefix (){
        return "Viewport.";
    }

	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(viewport, pp);
		LookAndFeel.installBorderAndBFDecorators(viewport, pp);
		LookAndFeel.installBasicProperties(viewport, pp);
	}

	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(viewport);
	}
	
	protected void  installListeners (){
		viewport.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		viewport.addEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
	}
	
	protected void  uninstallListeners (){
		viewport.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
		viewport.removeEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
	}
	
	private void  __onMouseWheel (MouseEvent e ){
		if(!(viewport.isEnabled() && viewport.isShowing())){
			return;
		}
    	IntPoint viewPos =viewport.getViewPosition ();
    	if(e.shiftKey){
    		viewPos.x -= e.delta*viewport.getHorizontalUnitIncrement();
    	}else{
    		viewPos.y -= e.delta*viewport.getVerticalUnitIncrement();
    	}
    	viewport.setViewPosition(viewPos);
	}
	
	private void  __onKeyDown (FocusKeyEvent e ){
		if(!(viewport.isEnabled() && viewport.isShowing())){
			return;
		}
    	int code =e.keyCode ;
    	IntPoint viewpos =viewport.getViewPosition ();
    	switch(code){
    		case Keyboard.UP:
    			viewpos.move(0, -viewport.getVerticalUnitIncrement());
    			break;
    		case Keyboard.DOWN:
    			viewpos.move(0, viewport.getVerticalUnitIncrement());
    			break;
    		case Keyboard.LEFT:
    			viewpos.move(-viewport.getHorizontalUnitIncrement(), 0);
    			break;
    		case Keyboard.RIGHT:
    			viewpos.move(viewport.getHorizontalUnitIncrement(), 0);
    			break;
    		case Keyboard.PAGE_UP:
    			if(e.shiftKey){
    				viewpos.move(-viewport.getHorizontalBlockIncrement(), 0);
    			}else{
    				viewpos.move(0, -viewport.getVerticalBlockIncrement());
    			}
    			break;
    		case Keyboard.PAGE_DOWN:
    			if(e.shiftKey){
    				viewpos.move(viewport.getHorizontalBlockIncrement(), 0);
    			}else{
    				viewpos.move(0, viewport.getVerticalBlockIncrement());
    			}
    			break;
    		case Keyboard.HOME:
    			viewpos.setLocationXY(0, 0);
    			break;
    		case Keyboard.END:
    			viewpos.setLocationXY(int.MAX_VALUE, int.MAX_VALUE);
    			break;
    		default:
    			return;
    	}
    	viewport.setViewPosition(viewpos);
	}
}


