
package org.aswing.border;

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
import org.aswing.graphics.*;
import org.aswing.geom.*;
import flash.display.*;

/**
 * A border that draw a line at one side of a component.
 * @author iiley
 */	
public class SideLineBorder extends DecorateBorder
	
    /**
     * The north side constraint (top of component).
     */
    public static  int NORTH =0;

    /**
     * The south side constraint (bottom of component).
     */
    public static  int SOUTH =1;

    /**
     * The east side constraint (right side of component).
     */
    public static  int EAST =2;

    /**
     * The west side constraint (left side of component).
     */
    public static  int WEST =3;
	
	private int side ;
	private ASColor color ;
	private double thickness ;
	
	/**
	 * SideLineBorder(interior:Border, side:Number, color:ASColor, thickness:Number) <br>
	 * SideLineBorder(interior:Border, side:Number, color:ASColor) <br>
	 * SideLineBorder(interior:Border, side:Number) <br>
	 * SideLineBorder(interior:Border) <br>
	 * SideLineBorder() <br>
	 * <p>
	 * @param interior interior border. Default is null;
	 * @param side the side of the line. Must be one of bottom value:
	 * <ul>
	 *   <li>#NORTH
	 *   <li>#SOUTH
	 *   <li>#EAST
	 *   <li>#WEST
	 * </ul>
	 * .Default is NORTH.
	 * @param color the color of the border. Default is ASColor.BLACK
	 * @param thickness the thickness of the border. Default is 1
	 */
	public  SideLineBorder (Border interior =null ,double side =NORTH ,ASColor color =null ,double thickness =1){
		super(interior);
		if (color == null) color = ASColor.BLACK;
		
		this.side = side;
		this.color = color;
		this.thickness = thickness;
	}

	 public void  updateBorderImp (Component com ,Graphics2D g ,IntRectangle b ){
 		Pen pen =new Pen(color ,thickness );
 		double x1 ,x2 double ,y1 ,y2 ;
 		if(side == SOUTH){
 			x1 = b.x;
 			y1 = b.y + b.height - thickness/2;
 			x2 = b.x + b.width;
 			y2 = y1;
 		}else if(side == EAST){
 			x1 = b.x + b.width - thickness/2;
 			y1 = b.y;
 			x2 = x1;
 			y2 = b.y + b.height;
 		}else if(side == WEST){
 			x1 = b.x + thickness/2;
 			y1 = b.y;
 			x2 = x1;
 			y2 = b.y + b.height;
 		}else{
 			x1 = b.x;
 			y1 = b.y + thickness/2;
 			x2 = b.x + b.width;
 			y2 = y1;
 		}
 		g.drawLine(pen, x1, y1, x2, y2);
	}
    
     public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	Insets i =new Insets ();
 		if(side == SOUTH){
 			i.bottom = thickness;
 		}else if(side == EAST){
 			i.right = thickness;
 		}else if(side == WEST){
 			i.left = thickness;
 		}else{
 			i.top = thickness;
 		}
    	return i;
    }   
     
	 public DisplayObject  getDisplayImp ()
	{
		return null;
	}
	
	public ASColor  getColor (){
		return color;
	}

	public void  setColor (ASColor color ){
		this.color = color;
	}

	public double  getThickness (){
		return thickness;
	}

	public void  setThickness (double thickness ){
		this.thickness = thickness;
	}

	public double  getSide (){
		return side;
	}

	public void  setSide (double side ){
		this.side = side;
	}
}



