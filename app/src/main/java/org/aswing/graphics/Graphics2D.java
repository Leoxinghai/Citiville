/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.graphics;

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


import flash.display.Graphics;

/**
 * Encapsulate and enhance flash.display.graphics drawing API.
 * <br>
 * To draw with this API, you need to create a pen.
 * To fill an area with this API, you need to create a brush.
 * <br>
*Youcanfindsampeusageofthisapiinthetestpackage whichwillcomewiththesourcepackage .;

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

 * <br>
 * <br>
*Hereisansampleonhowtousetheorg.aswing.grphicspackage todrawstuff;

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

 * 
 * <listing version="3.0"> 
 *Sprite mySprite =new Sprite ();
 * 			g = new Graphics2D(mySprite.graphics);
 *			ASColor color =new ASColor ();
 *			Matrix matrix =new Matrix ();
 *			matrix.createGradientBox(100,100,90/(Math.PI*2),200,200);
 *			
 *			
 *			Pen pen =new Pen(2,color.getRGB ());
 *			g.drawLine(pen,0,0,100,100);
 *			 
 *
 *			GradientPen gpen =new GradientPen(1,GradientType.LINEAR ,.get(0xff00ff ,0x00ff00 ,0xffff00) ,.get(1 ,1,1) ,.get(0 ,200,255) ,matrix );
 *			g.drawLine(gpen,400,100,500,500);
 *			
 *			GradientBrush gBrush =new GradientBrush(GradientType.LINEAR ,.get(0xff00ff ,0x00ff00 ,0xffff00) ,.get(1 ,1,1) ,.get(0 ,200,255) ,matrix );
 *			SolidBrush sBrush =new SolidBrush(ASColor.HALO_BLUE.getRGB (),0.3);
 *			BitmapBrush bBrush =new BitmapBrush(new BitmapData(50,50,false ,0xffff00 ));
 *			
 *			g.fillEllipse(gBrush,100,100,300,200);
 *			g.fillRectangle(sBrush,100,100,100,100);
 *			g.fillRectangle(bBrush,0,0,50,50);
 * 
 * 			addChild(mySprite);
 * </listing> 
 * 
 * @see org.aswing.graphics
 * @author iiley
 */
public class Graphics2D {
	
	protected Graphics target ;
	private IBrush brush ;
	
	/**
	 * Constructor take instance of flash.display.Graphics as parameter. usually, you can the instance from a displayObject's graphics property
	 * 
	 * @param target where the graphics contexts will be paint on. the target is an instance of flash.display.Graphics
	 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html
	 */
	public  Graphics2D (Graphics target ){
		this.target = target;
	}
	
	protected void  setTarget (Graphics target ){
		this.target = target;
	}
	
	protected void  dispose (){
		target = null;
	}
	
	private void  startPen (IPen p ){
		p.setTo(target);
	}
	
	private void  endPen (){
		target.lineStyle();
		target.moveTo(0, 0); //avoid a drawing error
	}
	
	private void  startBrush (IBrush b ){
		brush = b;
		b.beginFill(target);
	}
	
	private void  endBrush (){
		brush.endFill(target);
		target.moveTo(0, 0); //avoid a drawing error
	}
	
	//-------------------------------Public Functions-------------------
	/**
	 * Clears the graphics contexts drawn on the target Graphics.
	 */
	public void  clear (){
		if(target!= null) target.clear();
	}
	
	/**
	 * Draw a line between the points (x1, y1) and (x2, y2) in the target Graphics. 
	 * @param p the pen to draw. Pen can be a normal Pen or a GradientPen
	 * @param x1 the x corrdinate of the first point.
	 * @param y1 the y corrdinate of the first point.
	 * @param x2 the x corrdinate of the sencod point.
	 * @param y2 the y corrdinate of the sencod point.
	 */
	public void  drawLine (IPen p ,double x1 ,double y1 ,double x2 ,double y2 ){
		startPen(p);
		line(x1, y1, x2, y2);
		endPen();
	}
	
	/**
	 * Draws a polyline. (not close figure automaticlly)<br>
	 * Start with the points.get(0) and end with the points.get(points.length-1) as a closed path. 
	 * 
	 * @param p the pen to draw
	 * @param points the Array contains all vertex points in the polygon.
	 * @see #polyline()
	 */
	public void  drawPolyline (IPen p ,Array points ){
		startPen(p);
		polyline(points);
		endPen();
	}
	
	/**
	 * Fills a polygon.(not close figure automaticlly)<br>
	 * Start with the points.get(0) and end with the points.get(points.length-1) as a closed path. 
	 * 
	 * @param b the brush to fill.
	 * @param points the Array contains all vertex points in the polygon.
	 * @see #polyline()
	 */	
	public void  fillPolyline (IBrush b ,Array points ){
		startBrush(b);
		polyline(points);
		endBrush();
	}
	
	/**
	 * Draws a polygon.(close figure automaticlly)<br>
	 * Start to draw a ploygon with the points.get(0) as the start point and go through all the points in the array then go back to the points.get(0) end it as a closed path. 
	 * 
	 * @param pen the pen to draw
	 * @param points the Array contains all vertex points in the polygon.
	 * @see #polygon()
	 */
	public void  drawPolygon (Pen pen ,Array points ){
		startPen(pen);
		polygon(points);
		endPen();
	}
	
	/**
	 * Fills a polygon.(close figure automaticlly)<br>
	 * Start with the points.get(0) and end of the points.get(0) as a closed path. 
	 * 
	 * @param brush the brush to fill.
	 * @param points the Array contains all vertex points in the polygon.
	 * @see #polygon()
	 */	
	public void  fillPolygon (IBrush brush ,Array points ){
		startBrush(brush);
		polygon(points);
		endBrush();
	}
	
	/**
	 * Fills a polygon ring.
	 * 
	 * @param b the brush to fill.
	 * @param points1 the first polygon's points.
	 * @param points2 the second polygon's points.
	 * @see #fillPolygon()
	 */
	public void  fillPolygonRing (IBrush brush ,Array points1 ,Array points2 ){
		startBrush(brush);
		polygon(points1);
		polygon(points2);
		endBrush();
	}
	
	/**
	 * Draws a rectange.
	 * 
	 * @param pen the pen to draw.
	 * @param x the left top the rectange bounds' x corrdinate.
	 * @param y the left top the rectange bounds' y corrdinate.
	 * @param width the width of rectange bounds.
	 * @param height the height of rectange bounds.
	 */
	public void  drawRectangle (IPen pen ,double x ,double y ,double width ,double height ){
		this.startPen(pen);
		this.rectangle(x, y, width, height);
		this.endPen();
	}
	
	/**
	 * Fills a rectange.
	 * 
	 * @param brush the brush to fill.
	 * @param x the left top the rectange bounds' x corrdinate.
	 * @param y the left top the rectange bounds' y corrdinate.
	 * @param width the width of rectange bounds.
	 * @param height the height of rectange bounds.
	 */	
	public void  fillRectangle (IBrush brush ,double x ,double y ,double width ,double height ){
		startBrush(brush);
		rectangle(x,y,width,height);
		endBrush();
	}
	
	/**
	 * Fills a rectange ring.
	 * 
	 * @param brush the brush to fill.
	 * @param centerX the center of the ring's x corrdinate.
	 * @param centerY the center of the ring's y corrdinate.
	 * @param width1 the first rectange's width.
	 * @param height1 the first rectange's height.
	 * @param width2 the second rectange's width.
	 * @param height2 the second rectange's height.
	 */	
	public void  fillRectangleRing (IBrush brush ,double centerX ,double centerY ,double width1 ,double height1 ,double width2 ,double height2 ){
		startBrush(brush);
		rectangle(centerX-width1/2, centerY-height1/2, width1, height1);
		rectangle(centerX-width2/2, centerY-height2/2, width2, height2);
		endBrush();
	}
	
	/**
	 * Fills a rectange ring with a specified thickness.
	 * 
	 * @param brush the brush to fill.
	 * @param x the left top the ring bounds' x corrdinate.
	 * @param y the left top the ring bounds' y corrdinate.
	 * @param width the width of ring periphery bounds.
	 * @param height the height of ring periphery bounds.
	 * @param thickness the thickness of the ring.
	 */
	public void  fillRectangleRingWithThickness (IBrush brush ,double x ,double y ,double width ,double height ,double thickness ){
		startBrush(brush);
		rectangle(x, y, width, height);
		rectangle(x+thickness, y+thickness, width -thickness*2, height - thickness*2);
		endBrush();
	}	
	
	/**
	 * Draws a circle.
	 * 
	 * @param pen the pen to draw.
	 * @param cx the center of the circle's x corrdinate.
	 * @param cy the center of the circle's y corrdinate.
	 * @param radius the radius of the circle.
	 */
	public void  drawCircle (IPen pen ,double centerX ,double centerY ,double radius ){
		startPen(pen);
		circle(centerX, centerY, radius);
		endPen();		
	}
	
	/**
	 * Fills a circle.
	 * 
	 * @param brush the brush to draw.
	 * @param centerX the center of the circle's x corrdinate.
	 * @param centerY the center of the circle's y corrdinate.
	 * @param radius the radius of the circle.
	 */
	public void  fillCircle (IBrush brush ,double centerX ,double centerY ,double radius ){
		startBrush(brush);
		circle(centerX, centerY, radius);
		endBrush();
	}
	
	/**
	 * Fills a circle ring.
	 * 
	 * @param brush the brush to draw.
	 * @param centerX the center of the ring's x corrdinate.
	 * @param centerY the center of the ring's y corrdinate.
	 * @param radius1 the first circle radius.
	 * @param radius2 the second circle radius.
	 */
	public void  fillCircleRing (IBrush brush ,double centerX ,double centerY ,double radius1 ,double radius2 ){
		startBrush(brush);
		circle(centerX, centerY, radius1);
		circle(centerX, centerY, radius2);
		endBrush();
	}
	
	/**
	 * Fills a circle ring with a specified thickness.
	 * 
	 * @param brush the brush to draw.
	 * @param centerX the center of the ring's x corrdinate.
	 * @param centerY the center of the ring's y corrdinate.
	 * @param radius the radius of circle periphery.
	 * @param thickness the thickness of the ring.
	 */
	public void  fillCircleRingWithThickness (IBrush brush ,double centerX ,double centerY ,double radius ,double thickness ){
		startBrush(brush);
		circle(centerX, centerY, radius);
		radius -= thickness;
		circle(centerX, centerY, radius);
		endBrush();
	}
	
	/**
	 * Draws a ellipse.
	 * 
	 * @param pen  the pen to draw.
	 * @param x the left top the ellipse bounds' x corrdinate.
	 * @param y the left top the ellipse bounds' y corrdinate.
	 * @param width the width of ellipse bounds.
	 * @param height the height of ellipse bounds.
	 */	
	public void  drawEllipse (IPen pen ,double x ,double y ,double width ,double height ){
		startPen(pen);
		ellipse(x, y, width, height);
		endPen();
	}
	
	/**
	 * Fills a rectange.
	 * @param brush the brush to fill.
	 * @param x the left top the ellipse bounds' x corrdinate.
	 * @param y the left top the ellipse bounds' y corrdinate.
	 * @param width the width of ellipse bounds.
	 * @param height the height of ellipse bounds.
	 */		
	public void  fillEllipse (IBrush brush ,double x ,double y ,double width ,double height ){
		startBrush(brush);
		ellipse(x, y, width, height);
		endBrush();
	}
	
	/**
	 * Fill a ellipse ring.
	 * @param brush the brush to fill.
	 * @param centerX the center of the ring's x corrdinate.
	 * @param centerY the center of the ring's y corrdinate.
	 * @param width1 the first eclipse's width.
	 * @param height1 the first eclipse's height.
	 * @param width2 the second eclipse's width.
	 * @param height2 the second eclipse's height.
	 */
	public void  fillEllipseRing (IBrush brush ,double centerX ,double centerY ,double width1 ,double height1 ,double width2 ,double height2 ){
		startBrush(brush);
		ellipse(centerX-width1/2, centerY-height1/2, width1, height1);
		ellipse(centerX-width2/2, centerY-height2/2, width2, height2);
		endBrush();
	}
	
	/**
	 * Fill a ellipse ring with specified thickness.
	 * 
	 * @param brush the brush to fill.
	 * @param x the left top the ring bounds' x corrdinate.
	 * @param y the left top the ring bounds' y corrdinate.
	 * @param width the width of ellipse periphery bounds.
	 * @param height the height of ellipse periphery bounds.
	 * @param thickness the thickness of the ring.
	 */
	public void  fillEllipseRingWithThickness (IBrush brush ,double x ,double y ,double width ,double height ,double thickness ){
		startBrush(brush);
		ellipse(x, y, width, height);
		ellipse(x+thickness, y+thickness, width-thickness*2, height-thickness*2);
		endBrush();
	}	
	
	/**
	 * Draws a round rectangle.
	 * 
	 * @param pen the pen to draw.
	 * @param x the left top the rectangle bounds' x corrdinate.
	 * @param y the left top the rectangle bounds' y corrdinate.
	 * @param width the width of rectangle bounds.
	 * @param height the height of rectangle bounds.
	 * @param radius the top left corner's round radius.
	 * @param trR (optional)the top right corner's round radius. (miss this param default to same as radius)
	 * @param blR (optional)the bottom left corner's round radius. (miss this param default to same as radius)
	 * @param brR (optional)the bottom right corner's round radius. (miss this param default to same as radius)
	 */
	public void  drawRoundRect (IPen pen ,double x ,double y ,double width ,double height ,double radius ,double trR =-1,double blR =-1,double brR =-1){
		startPen(pen);
		roundRect(x, y, width, height, radius, trR, blR, brR);
		endPen();
	}
	
	/**
	 * Fills a round rectangle.
	 * @param brush the brush to fill.
	 * @param x the left top the rectangle bounds' x corrdinate.
	 * @param y the left top the rectangle bounds' y corrdinate.
	 * @param width the width of rectangle bounds.
	 * @param height the height of rectangle bounds.
	 * @param radius the radius of the top left corner, if other corner radius is -1, will use this radius as default
	 * @param topRightRadius	 the radius of the top right corner, if omitted, use the top left as default.
	 * @param bottomLeftRadius   the radius of the bottom left corner, if omitted, use the top left as default.
	 * @param bottomRightRadius  the radius of the bottom right corner, if omitted, use the top left as default.
	 */	
	public void  fillRoundRect (IBrush brush ,double x ,double y ,double width ,double height ,double radius ,double topRightRadius =-1,double bottomLeftRadius =-1,double bottomRightRadius =-1){
		startBrush(brush);
		roundRect(x,y,width,height,radius,topRightRadius,bottomLeftRadius,bottomRightRadius);
		endBrush();
	}
	
	/**
	 * Fill a round rect ring.
	 * @param brush the brush to fill
	 * @param centerX the center of the ring's x corrdinate
	 * @param centerY the center of the ring's y corrdinate
	 * @param width1 the first round rect's width
	 * @param height1 the first round rect's height
	 * @param radius1 the first round rect's round radius
	 * @param width2 the second round rect's width
	 * @param height2 the second round rect's height
	 * @param radius2 the second round rect's round radius
	 */	
	public void  fillRoundRectRing (IBrush brush ,double centerX ,double centerY ,double width1 ,double height1 ,double radius1 ,double width2 ,double height2 ,double radius2 ){
		startBrush(brush);
		roundRect(centerX-width1/2, centerY-height1/2, width1, height1, radius1);
		roundRect(centerX-width2/2, centerY-height2/2, width2, height2, radius2);
		endBrush();
	}
	
	/**
	 * Fill a round rect ring with specified thickness.
	 * @param brush the brush to fill
	 * @param x the left top the ring bounds' x corrdinate
	 * @param y the left top the ring bounds' y corrdinate
	 * @param width the width of ring periphery bounds
	 * @param height the height of ring periphery bounds
	 * @param radius the round radius of the round rect
	 * @param thickness the thickness of the ring
	 * @param innerRadius the inboard round radius, default is <code>r-t</code>
	 */	
	public void  fillRoundRectRingWithThickness (IBrush brush ,double x ,double y ,double width ,double height ,double radius ,double thickness ,double innerRadius =-1){
		startBrush(brush);
		roundRect(x, y, width, height, radius);
		if(innerRadius == -1) innerRadius = radius - thickness;
		roundRect(x+thickness, y+thickness, width-thickness*2, height-thickness*2, innerRadius);
		endBrush();
	}	
	
	/**
	 * Start to fill a closed area with brush
	 */
	public void  beginFill (IBrush brush ){
		startBrush(brush);
	}
	
	/**
	 * Stop filling a closed area with brush
	 */
	public void  endFill (){
		endBrush();
		target.moveTo(0, 0); //avoid a drawing error
	}
	
	/**
	 * Start to draw lines with pen 
	 */
	public void  beginDraw (IPen pen ){
		startPen(pen);
	}
	
	/**
	 * Stop drawing
	 */
	public void  endDraw (){
		endPen();
		target.moveTo(0, 0); //avoid a drawing error
	}
	
	/**
	 * Delegate Graphics.moveTo(x,y);
	 */
	public void  moveTo (double x ,double y ){
		target.moveTo(x, y);
	}
	
	/**
	 * Paths a curve 
	 * @see http://livedocs.macromedia.com/flex/2/langref/flash/display/Graphics.html#curveTo()
	 */
	public void  curveTo (double controlX ,double controlY ,double anchorX ,double anchorY ){
		target.curveTo(controlX, controlY, anchorX, anchorY);
	}
	public void  lineTo (double x ,double y ){
		target.lineTo(x, y);
	}
	
	//---------------------------------------------------------------------------
	/**
	 * Paths a line between the points (x1, y1) and (x2, y2) in the target Graphics. 
	 * 
	 * @param x1 the x corrdinate of the first point.
	 * @param y1 the y corrdinate of the first point.
	 * @param x2 the x corrdinate of the sencod point.
	 * @param y2 the y corrdinate of the sencod point.
	 */
	public void  line (double x1 ,double y1 ,double x2 ,double y2 ){
		target.moveTo(x1, y1);
		target.lineTo(x2, y2);
	}
	
	/**
	 * Paths a polygon.(close figure automaticlly)
	 * 
	 * @param points the points of the polygon, the array length should be larger than 1
	 * @see #drawPolygon()
	 * @see #fillPolygon()
	 * @see #polyline()
	 */
	public void  polygon (Array points ){
		if(points.length > 1){
			polyline(points);
			target.lineTo(points.get(0).x, points.get(0).y);
		}
	}
	
	/**
	 * Paths a polyline(not close figure automaticlly).
	 * 
	 * @param points the points of the polygon, the array length should be larger than 1
	 * @see #drawPolyline()
	 * @see #fillPolyline()
	 * @see #polygon()
	 */
	public void  polyline (Array points ){
		if(points.length > 1){
			target.moveTo(points.get(0).x, points.get(0).y);
			for(double i =1;i <points.length ;i ++){
				target.lineTo(points.get(i).x, points.get(i).y);
			}
		}
	}
	
	/**
	 * Paths a rectangle.
	 * 
	 * @param x the x corrdinate of the rectangle.
	 * @param y the y corrdinate of the rectangle.
	 * @param width  the width corrdinate of rectangle.
	 * @param height the width corrdinate of rectangle.
	 * @see #drawRectangle()
	 * @see #fillRectangle()
	 */
	public void  rectangle (double x ,double y ,double width ,double height ){
		
		target.drawRect(x,y,width,height);
		/* target.moveTo(x, y);
		target.lineTo(x+width,y);
		target.lineTo(x+width,y+height);
		target.lineTo(x,y+height);
		target.lineTo(x,y);*/	
 	}
	
	/**
	 * Paths an ellipse.
	 * @param x the x corrdinate of the ellipse.
	 * @param y the y corrdinate of the ellipse.
	 * @param width  the width corrdinate of ellipse.
	 * @param height the width corrdinate of ellipse.
	 * @see #drawEllipse()
	 * @see #fillEllipse()
	 */
	public void  ellipse (double x ,double y ,double width ,double height ){
		
		target.drawEllipse(x,y,width,height);
		/*double pi =Math.PI ;
        double xradius =width /2;
        double yradius =height /2;
        double cx =x +xradius ;
        double cy =y +yradius ;
        double tanpi8 =Math.tan(pi /8);
        double cospi4 =Math.cos(pi /4);
        double sinpi4 =Math.sin(pi /4);
        target.moveTo(xradius + cx, 0 + cy);
        target.curveTo(xradius + cx, (yradius * tanpi8) + cy, (xradius * cospi4) + cx, (yradius * sinpi4) + cy);
        target.curveTo((xradius * tanpi8) + cx, yradius + cy, 0 + cx, yradius + cy);
        target.curveTo(((-xradius) * tanpi8) + cx, yradius + cy, ((-xradius) * cospi4) + cx, (yradius * sinpi4) + cy);
        target.curveTo((-xradius) + cx, (yradius * tanpi8) + cy, (-xradius) + cx, 0 + cy);
        target.curveTo((-xradius) + cx, ((-yradius) * tanpi8) + cy, ((-xradius) * cospi4) + cx, ((-yradius) * sinpi4) + cy);
        target.curveTo(((-xradius) * tanpi8) + cx, (-yradius) + cy, 0 + cx, (-yradius) + cy);
        target.curveTo((xradius * tanpi8) + cx, (-yradius) + cy, (xradius * cospi4) + cx, ((-yradius) * sinpi4) + cy);
        target.curveTo(xradius + cx, ((-yradius) * tanpi8) + cy, xradius + cx, 0 + cy);		
 */	}
	
	/**
	 * Paths a circle
	 * 
	 * @param centerX x corrdinate of the center of the circle.
	 * @param centerY y corrdinate of the center of the circle.
	 * @param radius  the radius of circle.
	 * @see #drawCircle()
	 * @see #fillCircle()
	 */
	public void  circle (double centerX ,double centerY ,double radius ){
		target.drawCircle(centerX,centerY,radius);
		//ellipse(cx-r, cy-r, r*2, r*2);
//		target.moveTo(cx, cy - r);
//		target.curveTo(cx + r, cy - r, cx + r, cy);
//		target.curveTo(cx + r, cy + r, cx, cy + r);
//		target.curveTo(cx - r, cy + r, cx - r, cy);
//		target.curveTo(cx - r, cy - r, cx, cy - r);
	}
	
	/**
	 * Paths a round rect.
	 * 
	 * @param x the x corrdinate of the roundRect.
	 * @param y the y corrdinate of the roundRect.
	 * @param width  the width corrdinate of roundRect.
	 * @param height the width corrdinate of roundRect.
	 * @param radius the radius of the top left corner, if other corner radius is -1, will use this radius as default
	 * @param topRightRadius	 (optional)the radius of the top right corner, if omitted, use the top left as default.
	 * @param bottomLeftRadius   (optional)the radius of the bottom left corner, if omitted, use the top left as default.
	 * @param bottomRightRadius  (optional)the radius of the bottom right corner, if omitted, use the top left as default.
	 * @see #drawRoundRect()
	 * @see #fillRoundRect()
	 */
	public void  roundRect (double x ,double y ,double width ,double height ,double radius ,double topRightRadius =-1,double bottomLeftRadius =-1,double bottomRightRadius =-1){
		//double tlR =radius ;
		if(topRightRadius == -1) topRightRadius = radius;
		if(bottomLeftRadius == -1) bottomLeftRadius = radius;
		if(bottomRightRadius == -1) bottomRightRadius = radius;
		
		target.drawRoundRectComplex(x,y,width,height,radius,topRightRadius,bottomLeftRadius,bottomRightRadius);
		
		/* 
		//Bottom right
		target.moveTo(x+blR, y+height);
		target.lineTo(x+width-brR, y+height);
		target.curveTo(x+width, y+height, x+width, y+height-blR);
		//Top right
		target.lineTo (x+width, y+trR);
		target.curveTo(x+width, y, x+width-trR, y);
		//Top left
		target.lineTo (x+tlR, y);
		target.curveTo(x, y, x, y+tlR);
		//Bottom left
		target.lineTo (x, y+height-blR );
		target.curveTo(x, y+height, x+blR, y+height); */
	}
	
	/**
	 * @private
	 * don't generate doc for this method yet
	 * Paths a wedge.
	 */
	public void  wedge (double radius ,double x ,double y ,double angle ){
		target.moveTo(0, 0);
		target.lineTo(radius, 0);
		double nSeg =Math.floor(angle /30);
		double pSeg =angle -nSeg *30;
		double a =0.268;
		double endx ;
		double endy ;
		double ax ;
		double ay ;
		double storeCount =0;
		for(double i =0;i <nSeg ;i ++){
			endx = radius*Math.cos((i+1)*30*(Math.PI/180));
			endy = radius*Math.sin((i+1)*30*(Math.PI/180));
			ax = endx+radius*a*Math.cos(((i+1)*30-90)*(Math.PI/180));
			ay = endy+radius*a*Math.sin(((i+1)*30-90)*(Math.PI/180));
			target.curveTo(ax, ay, endx, endy);
			storeCount=i+1;
		}
		if (pSeg>0) {
			a = Math.tan(pSeg/2*(Math.PI/180));
			endx = radius*Math.cos((storeCount*30+pSeg)*(Math.PI/180));
			endy = radius*Math.sin((storeCount*30+pSeg)*(Math.PI/180));
			ax = endx+radius*a*Math.cos((storeCount*30+pSeg-90)*(Math.PI/180));
			ay = endy+radius*a*Math.sin((storeCount*30+pSeg-90)*(Math.PI/180));
			target.curveTo(ax, ay, endx, endy);
		}
		target.lineTo(0, 0);
	/* 	target._rotation = rot;
		target._x = x;
		target._y = y; */
	}	
	
	


}


