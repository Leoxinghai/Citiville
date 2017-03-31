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


import flash.display.DisplayObjectContainer;
import flash.display.Sprite;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.events.TimerEvent;
import flash.geom.Rectangle;
import flash.utils.Timer;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.frame.*;
import org.aswing.resizer.Resizer;

/**
 * Basic frame ui imp.
 * @author iiley
 * @private
 */
public class BasicFrameUI extends BaseComponentUI implements FrameUI{

	protected JFrame frame ;
	protected FrameTitleBar titleBar ;

	private ASColor resizeArrowColor ;
	private ASColor resizeArrowLightColor ;
	private ASColor resizeArrowDarkColor ;

	protected Object mouseMoveListener ;
	protected Sprite boundsMC ;
	protected Timer flashTimer ;

	public  BasicFrameUI (){
		super();
	}

     public void  installUI (Component c ){
        frame = JFrame(c);
        installDefaults();
		installComponents();
		installListeners();
    }

	protected String  getPropertyPrefix (){
		return "Frame.";
	}

    protected void  installDefaults (){
    	String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(frame, pp);
		LookAndFeel.installBorderAndBFDecorators(frame, pp);
		LookAndFeel.installBasicProperties(frame, pp);

	    resizeArrowColor = getColor("resizeArrow");
	    resizeArrowLightColor = getColor("resizeArrowLight");
	    resizeArrowDarkColor = getColor("resizeArrowDark");
	    Icon ico =frame.getIcon ();
	    if(ico is UIResource){
	    	frame.setIcon(getIcon(getPropertyPrefix()+"icon"));
	    }
    }

    protected void  installComponents (){
    	if(frame.getResizer() == null || frame.getResizer() is UIResource){
	    	Resizer resizer =getInstance(getPropertyPrefix ()+"resizer")as Resizer ;
	    	frame.setResizer(resizer);
    	}
    	if(!frame.isDragDirectlySet()){
    		frame.setDragDirectly(getBoolean(getPropertyPrefix()+"dragDirectly"));
    		frame.setDragDirectlySet(false);
    	}
    	boundsMC = new Sprite();
    	boundsMC.name = "drag_bounds";
	}

	protected void  installListeners (){
		frame.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __titleBarChanged);
		frame.addEventListener(WindowEvent.WINDOW_ACTIVATED, __activeChange);
		frame.addEventListener(WindowEvent.WINDOW_DEACTIVATED, __activeChange);
		frame.addEventListener(PopupEvent.POPUP_CLOSED, __frameClosed);
		frame.addEventListener(Event.REMOVED_FROM_STAGE, __frameClosed);
		__titleBarChanged(null);
	}

     public void  uninstallUI (Component c ){
        uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }

    protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(frame);
		frame.filters = new Array();
    }

	protected void  uninstallComponents (){
		removeBoundsMC();
	}

	protected void  uninstallListeners (){
		frame.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __titleBarChanged);
		frame.removeEventListener(WindowEvent.WINDOW_ACTIVATED, __activeChange);
		frame.removeEventListener(WindowEvent.WINDOW_DEACTIVATED, __activeChange);
		frame.removeEventListener(PopupEvent.POPUP_CLOSED, __frameClosed);
		frame.removeEventListener(Event.REMOVED_FROM_STAGE, __frameClosed);
		removeTitleBarListeners();
		if(flashTimer != null){
			flashTimer.stop();
			flashTimer = null;
		}
	}

	private boolean flashing ;
	private boolean flashingActivedColor ;

	/**
	 * Flash the modal frame. (User clicked other where is not in the modal frame,
	 * flash the frame to make notice this frame is modal.)
	 */
	public void  flashModalFrame (){
		if(flashTimer == null){
			flashTimer = new Timer(50, 8);
			flashTimer.addEventListener(TimerEvent.TIMER, __flashTick);
			flashTimer.addEventListener(TimerEvent.TIMER_COMPLETE, __flashComplete);
		}
		flashing = true;
		flashingActivedColor = false;
		flashTimer.reset();
		flashTimer.start();
	}

	private void  __flashTick (TimerEvent e ){
		flashingActivedColor = !flashingActivedColor;
		frame.repaint();
		titleBar.getSelf().repaint();
	}

	private void  __flashComplete (TimerEvent e ){
		flashing = false;
		frame.repaint();
		titleBar.getSelf().repaint();
	}

	/**
	 * For <code>flashModalFrame</code> to judge whether paint actived color or inactived color.
	 */
	public boolean  isPaintActivedFrame (){
		if(flashing){
			return flashingActivedColor;
		}else{
			return frame.isActive();
		}
	}
    //----------------------------------------------------------
     protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
    	Insets bgMargin =c.getUI ().getInsets(getPropertyPrefix ()+"backgroundMargin");
    	if(bgMargin){
    		b = bgMargin.getInsideBounds(b);
    	}
    	super.paintBackGround(c, g, b);
    }

    //----------------------------------------------------------

	private void  __titleBarChanged (PropertyChangeEvent e ){
		if(e != null && e.getPropertyName() != JFrame.PROPERTY_TITLE_BAR){
			return;
		}
		Component oldTC ;
		if(e && e.getOldValue()){
			FrameTitleBar oldT =e.getOldValue ();
			oldTC = oldT.getSelf();
		}
		if(oldTC){
			oldTC.removeEventListener(MouseEvent.MOUSE_DOWN, __onTitleBarPress);
			oldTC.removeEventListener(ReleaseEvent.RELEASE, __onTitleBarRelease);
			oldTC.removeEventListener(MouseEvent.DOUBLE_CLICK, __onTitleBarDoubleClick);
			oldTC.doubleClickEnabled = false;
		}
		titleBar = frame.getTitleBar();
		addTitleBarListeners();
	}

	protected void  addTitleBarListeners (){
		if(titleBar){
			Component titleBarC =titleBar.getSelf ();
			titleBarC.addEventListener(MouseEvent.MOUSE_DOWN, __onTitleBarPress);
			titleBarC.addEventListener(ReleaseEvent.RELEASE, __onTitleBarRelease);
			titleBarC.doubleClickEnabled = true;
			titleBarC.addEventListener(MouseEvent.DOUBLE_CLICK, __onTitleBarDoubleClick);
		}
	}

	protected void  removeTitleBarListeners (){
		if(titleBar){
			Component titleBarC =titleBar.getSelf ();
			titleBarC.removeEventListener(MouseEvent.MOUSE_DOWN, __onTitleBarPress);
			titleBarC.removeEventListener(ReleaseEvent.RELEASE, __onTitleBarRelease);
			titleBarC.doubleClickEnabled = false;
			titleBarC.removeEventListener(MouseEvent.DOUBLE_CLICK, __onTitleBarDoubleClick);
		}
	}

	private boolean  isMaximizedFrame (){
		double state =frame.getState ();
		return ((state & JFrame.MAXIMIZED_HORIZ) == JFrame.MAXIMIZED_HORIZ)
				|| ((state & JFrame.MAXIMIZED_VERT) == JFrame.MAXIMIZED_VERT);
	}

	private void  __activeChange (Event e ){
		frame.repaint();
	}

	private IntPoint startPos ;
	private IntPoint startMousePos ;
    private void  __onTitleBarPress (MouseEvent e ){
    	if(e.target != titleBar && e.target != titleBar.getLabel()){
    		return;
    	}
    	if(!titleBar.isTitleEnabled()){
    		return;
    	}
    	if(frame.isDragable() && !isMaximizedFrame()){
    		if(frame.isDragDirectly()){
    			Rectangle db =frame.getInsets ().getInsideBounds(frame.getMaximizedBounds ()).toRectangle ();
    			double gap =titleBar.getSelf ().getHeight ();
    			db.x -= (frame.width - gap);
    			db.y -= frame.getInsets().top;
    			db.width += (frame.width - gap*2);
    			db.height -= gap;

    			frame.startDrag(false, db);
    		}else{
    			startMousePos = frame.getMousePosition();
    			startPos = frame.getLocation();
    			if(frame.stage){
    				frame.stage.addEventListener(MouseEvent.MOUSE_MOVE, __onMouseMove, false, 0, true);
    			}
    		}
    	}
    }

    private void  __onTitleBarRelease (ReleaseEvent e ){
    	if(e.getPressTarget() != titleBar && e.getPressTarget() != titleBar.getLabel()){
    		return;
    	}
    	if(!titleBar.isTitleEnabled()){
    		return;
    	}
    	frame.stopDrag();
    	if(frame.stage){
    		frame.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMove);
    	}
    	if(frame.isDragable() && !isMaximizedFrame() && !frame.isDragDirectly()){
	    	IntPoint dest =representMoveBounds ();
	    	frame.setLocation(dest);
	    	frame.validate();
    	}
    	removeBoundsMC();
    }

    private void  __onTitleBarDoubleClick (Event e ){
    	if(e.target != titleBar && e.target != titleBar.getLabel()){
    		return;
    	}
    	if(!titleBar.isTitleEnabled()){
    		return;
    	}
		if(frame.isResizable()){
			int state =frame.getState ();

			if((state & JFrame.MAXIMIZED_HORIZ) == JFrame.MAXIMIZED_HORIZ
				|| (state & JFrame.MAXIMIZED_VERT) == JFrame.MAXIMIZED_VERT
				|| (state & JFrame.ICONIFIED) == JFrame.ICONIFIED){
					frame.setState(JFrame.NORMAL, false);
			}else{
				frame.setState(JFrame.MAXIMIZED, false);
			}
		}
    }

    private void  __frameClosed (Event e ){
    	removeBoundsMC();
    	if(flashTimer != null){
    		flashTimer.stop();
    		flashTimer = null;
    	}
    	if(frame.stage){
    		frame.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMouseMove);
    	}
    }

    private void  removeBoundsMC (){
    	if(frame.parent != null && frame.parent.contains(boundsMC)){
    		frame.parent.removeChild(boundsMC);
    	}
    }

    private IntPoint  representMoveBounds (MouseEvent e =null ){
    	DisplayObjectContainer par =frame.parent ;
    	if(boundsMC.parent != par){
    		par.addChild(boundsMC);
    	}
    	IntPoint currentMousePos =frame.getMousePosition ();
    	IntRectangle bounds =frame.getComBounds ();
    	bounds.x = startPos.x + currentMousePos.x - startMousePos.x;
    	bounds.y = startPos.y + currentMousePos.y - startMousePos.y;

    	//these make user can't drag frames out the stage
    	double gap =titleBar.getSelf ().getHeight ();
    	IntRectangle frameMaxBounds =frame.getMaximizedBounds ();

    	IntPoint topLeft =frameMaxBounds.leftTop ();
    	IntPoint topRight =frameMaxBounds.rightTop ();
    	IntPoint bottomLeft =frameMaxBounds.leftBottom ();
    	if(bounds.x < topLeft.x - bounds.width + gap){
    		bounds.x = topLeft.x - bounds.width + gap;
    	}
    	if(bounds.x > topRight.x - gap){
    		bounds.x = topRight.x - gap;
    	}
    	if(bounds.y < topLeft.y){
    		bounds.y = topLeft.y;
    	}
    	if(bounds.y > bottomLeft.y - gap){
    		bounds.y = bottomLeft.y - gap;
    	}

		double x =bounds.x ;
		double y =bounds.y ;
		double w =bounds.width ;
		double h =bounds.height ;
		Graphics2D g =new Graphics2D(boundsMC.graphics );
		boundsMC.graphics.clear();
		g.drawRectangle(new Pen(resizeArrowLightColor, 1), x-1,y-1,w+2,h+2);
		g.drawRectangle(new Pen(resizeArrowColor, 1), x,y,w,h);
		g.drawRectangle(new Pen(resizeArrowDarkColor, 1), x+1,y+1,w-2,h-2);
		return bounds.leftTop();
    }
    private void  __onMouseMove (MouseEvent e ){
    	representMoveBounds(e);
    }
}


