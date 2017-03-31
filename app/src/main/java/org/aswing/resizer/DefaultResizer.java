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


import flash.display.*;
import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.*;
import org.aswing.event.AWEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.UIResource;
import org.aswing.util.DepthManager;

/**
 * Resizer is a resizer for Components to make it resizable when user mouse on 
 * component's edge.
 * @author iiley
 */
public class DefaultResizer implements Resizer, UIResource{
	
	protected static double RESIZE_MC_WIDTH =4;
		
	protected Component owner ;

	//-----------resize equiments--------------
	protected Sprite resizeMC ;
	
	protected DisplayObject resizeArrowCursor ;
	protected Shape boundsShape ;
	
	protected AWSprite topResizeMC ;
	protected AWSprite leftResizeMC ;
	protected AWSprite bottomResizeMC ;
	protected AWSprite rightResizeMC ;
	
	protected AWSprite topLeftResizeMC ;
	protected AWSprite topRightResizeMC ;
	protected AWSprite bottomLeftResizeMC ;
	protected AWSprite bottomRightResizeMC ;
	
	private double startX ;
	private double startY ;
	private IntRectangle startBounds ;
	
	protected boolean enabled ;
	protected boolean resizeDirectly ;
	
	protected ASColor resizeArrowColor ;
	protected ASColor resizeArrowLightColor ;
	protected ASColor resizeArrowDarkColor ;
	
	/**
	 * Create a Resizer for specified component.
	 */
	public  DefaultResizer (){
		enabled = true;
		resizeDirectly = false;
		startX = 0;
		startY = 0;
		startBounds = new IntRectangle();
		//Default colors
	    resizeArrowColor = UIManager.getColor("resizeArrow");
	    resizeArrowLightColor = UIManager.getColor("resizeArrowLight");
	    resizeArrowDarkColor = UIManager.getColor("resizeArrowDark");
	}
	
	public void  setResizeArrowColor (ASColor c ){
		resizeArrowColor = c;
	}
	
	public void  setResizeArrowLightColor (ASColor c ){
		resizeArrowLightColor = c;
	}
	
	public void  setResizeArrowDarkColor (ASColor c ){
		resizeArrowDarkColor = c;
	}
	
	public void  setOwner (Component c ){
		if(owner != null){
			owner.removeEventListener(AWEvent.PAINT, locate);
			if(resizeMC != null){
				owner.removeChild(resizeMC);
			}
			hideBoundsMC();
		}
		owner = c;
		if(owner != null){
			owner.addEventListener(AWEvent.PAINT, locate);
			if(resizeMC == null){
				createResizeMCs();
			}
			owner.addChildAt(resizeMC, owner.numChildren);
		}
		locate();
	}
		
	/**
	 * <p>Indicate whether need resize component directly when drag the resizer arrow.
	 * <p>if set to false, there will be a rectange to represent then size what will be resized to.
	 * <p>if set to true, the component will be resize directly when drag, but this is need more cpu counting.
	 * <p>Default is false.
	 * @see org.aswing.JFrame
	 */	
	public void  setResizeDirectly (boolean r ){
		resizeDirectly = r;
	}
	
	/**
	 * Returns whether need resize component directly when drag the resizer arrow.
	 * @see #setResizeDirectly
	 */
	public boolean  isResizeDirectly (){
		return resizeDirectly;
	}
	
	//-----------------------For Handlers-------------------------
	
	public void  setArrowRotation (double r ){
		resizeArrowCursor.rotation = r;
	}
	
	public void  startArrowCursor (){
		if(resizeMC && resizeMC.stage){
			CursorManager.getManager(resizeMC.stage).showCustomCursor(resizeArrowCursor);
		}
	}
	
	public void  stopArrowCursor (){
		if(resizeMC && resizeMC.stage){
			CursorManager.getManager(resizeMC.stage).hideCustomCursor(resizeArrowCursor);
		}
	}
	
	private boolean resizingNow =false ;
	public void  setResizing (boolean b ){
		resizingNow = b;
	}
	
	public boolean  isResizing (){
		return resizingNow;
	}
	
	public void  startResize (ResizeStrategy strategy ,MouseEvent e ){
		if(!resizeDirectly){
			representRect(owner.getComBounds());
		}
		startX = e.stageX;
		startY = e.stageY;
		startBounds = owner.getComBounds();
	}
	
	public void  resizing (ResizeStrategy strategy ,MouseEvent e ){
		IntRectangle bounds =strategy.getBounds(
			startBounds, 
			owner.getMinimumSize(), 
			owner.getMaximumSize(), 
			e.stageX - startX, e.stageY - startY);
		if(resizeDirectly){
			owner.setBounds(bounds);
			owner.revalidate();
			e.updateAfterEvent();
		}else{
			representRect(bounds);
		}
	}
	
	public void  finishResize (ResizeStrategy strategy ){
		if(!resizeDirectly){
			owner.setComBounds(lastRepresentedBounds);
			hideBoundsMC();
			owner.revalidate();
		}
	}
	
	
	private void  hideBoundsMC (){
		DisplayObjectContainer par =owner.parent ;
		if(boundsShape != null && par != null && par.contains(boundsShape)){
			par.removeChild(boundsShape);
		}
	}
	
	private IntRectangle lastRepresentedBounds ;
	
	private void  representRect (IntRectangle bounds ){
		if(!resizeDirectly){
			DisplayObjectContainer par =owner.parent ;
			if(!par.contains(boundsShape)){
				par.addChild(boundsShape);
			}
			DepthManager.bringToTop(boundsShape);
			double x =bounds.x ;
			double y =bounds.y ;
			double w =bounds.width ;
			double h =bounds.height ;
			Graphics2D g =new Graphics2D(boundsShape.graphics );
			boundsShape.graphics.clear();
			g.drawRectangle(new Pen(resizeArrowLightColor), x-1,y-1,w+2,h+2);
			g.drawRectangle(new Pen(resizeArrowColor), x,y,w,h);
			g.drawRectangle(new Pen(resizeArrowDarkColor), x+1,y+1,w-2,h-2);
			lastRepresentedBounds = bounds;
		}
	}
	
	protected void  createResizeMCs (){
		double r =RESIZE_MC_WIDTH ;
		resizeMC = new Sprite();
		resizeMC.name = "resizer";
		resizeArrowCursor = Cursor.createCursor(Cursor.H_RESIZE_CURSOR);
		resizeArrowCursor.name = "resizeCursor";
		boundsShape = new Shape();
		boundsShape.name = "bounds";
		
		topResizeMC = new AWSprite();
		leftResizeMC = new AWSprite();
		rightResizeMC = new AWSprite();
		bottomResizeMC = new AWSprite();
		
		topLeftResizeMC = new AWSprite();
		topRightResizeMC = new AWSprite();
		bottomLeftResizeMC = new AWSprite();
		bottomRightResizeMC = new AWSprite();
		
		resizeMC.addChild(topResizeMC);
		resizeMC.addChild(leftResizeMC);
		resizeMC.addChild(rightResizeMC);
		resizeMC.addChild(bottomResizeMC);
		
		resizeMC.addChild(topLeftResizeMC);
		resizeMC.addChild(topRightResizeMC);
		resizeMC.addChild(bottomLeftResizeMC);
		resizeMC.addChild(bottomRightResizeMC);
		
		DefaultResizeBarHandler.createHandler(this, topResizeMC, 90, createResizeStrategy(0, -1));
		DefaultResizeBarHandler.createHandler(this, leftResizeMC, 0, createResizeStrategy(-1, 0));
		DefaultResizeBarHandler.createHandler(this, rightResizeMC, 0, createResizeStrategy(1, 0));
		DefaultResizeBarHandler.createHandler(this, bottomResizeMC, 90, createResizeStrategy(0, 1));
		
		DefaultResizeBarHandler.createHandler(this, topLeftResizeMC, 45, createResizeStrategy(-1, -1));
		DefaultResizeBarHandler.createHandler(this, topRightResizeMC, -45, createResizeStrategy(1, -1));
		DefaultResizeBarHandler.createHandler(this, bottomLeftResizeMC, -45, createResizeStrategy(-1, 1));
		DefaultResizeBarHandler.createHandler(this, bottomRightResizeMC, 45, createResizeStrategy(1, 1));
		
		SolidBrush brush =new SolidBrush(new ASColor(0,0));
		Graphics2D gdi =new Graphics2D(topResizeMC.graphics );
		gdi.fillRectangle(brush, 0, 0, r, r);
		gdi = new Graphics2D(leftResizeMC.graphics);
		gdi.fillRectangle(brush, 0, 0, r, r);
		gdi = new Graphics2D(rightResizeMC.graphics);
		gdi.fillRectangle(brush, -r, 0, r, r);	
		gdi = new Graphics2D(bottomResizeMC.graphics);
		gdi.fillRectangle(brush, 0, -r, r, r);	
		
		gdi = new Graphics2D(topLeftResizeMC.graphics);
		gdi.fillRectangle(brush, 0, 0, r*2, r);
		gdi.fillRectangle(brush, 0, 0, r, r*2);
		gdi = new Graphics2D(topRightResizeMC.graphics);
		gdi.fillRectangle(brush, -r*2, 0, r*2, r);
		gdi.fillRectangle(brush, -r, 0, r, r*2);
		gdi = new Graphics2D(bottomLeftResizeMC.graphics);
		gdi.fillRectangle(brush, 0, -r, r*2, r);
		gdi.fillRectangle(brush, 0, -r*2, r, r*2);
		gdi = new Graphics2D(bottomRightResizeMC.graphics);
		gdi.fillRectangle(brush, -r*2, -r, r*2, r);
		gdi.fillRectangle(brush, -r, -r*2, r, r*2);
		
		resizeMC.visible = enabled;
	}
	
	/**
	 * Override this method if you want to use another resize strategy.
	 */
	private ResizeStrategy  createResizeStrategy (double wSign ,double hSign ){
		return new ResizeStrategyImp(wSign, hSign); 
	}
	
	public void  setEnabled (boolean e ){
		enabled = e;
		resizeMC.visible = enabled;
	}
	
	public boolean  isEnabled (){
		return enabled;
	}
		
	/**
	 * Locate the resizer mcs to fit the component.
	 */
	private void  locate (Event e =null ){
		//double x =0;
		//double y =0;
		if(owner == null){
			return;
		}
		double w =owner.getWidth ();
		double h =owner.getHeight ();
		double r =RESIZE_MC_WIDTH ;
		
		topResizeMC.width = Math.max(0, w-r*2);
		topResizeMC.x = r;
		topResizeMC.y = 0;
		leftResizeMC.height = Math.max(0, h-r*2);
		leftResizeMC.x = 0;
		leftResizeMC.y = r;
		rightResizeMC.height = Math.max(0, h-r*2);
		rightResizeMC.x = w;
		rightResizeMC.y = r;
		bottomResizeMC.width = Math.max(0, w-r*2);
		bottomResizeMC.x = r;
		bottomResizeMC.y = h;
		
		topLeftResizeMC.x = 0;
		topLeftResizeMC.y = 0;
		topRightResizeMC.x = w;
		topRightResizeMC.y = 0;
		bottomLeftResizeMC.x = 0;
		bottomLeftResizeMC.y = h;
		bottomRightResizeMC.x = w;
		bottomRightResizeMC.y = h;
	}
}


