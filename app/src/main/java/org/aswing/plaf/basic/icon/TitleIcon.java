/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.icon;

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

	
import org.aswing.graphics.*;
import org.aswing.*;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;
import flash.geom.Point;
import flash.display.Shape;

/**
 * The default frame title icon.
 * @author iiley
 * @private
 */
public class TitleIcon implements Icon, UIResource{
	
	private static  int WIDTH =16;
	private static  int HEIGHT =12;
	protected Shape shape ;
	
	public  TitleIcon (){
		shape = new Shape();
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		shape.graphics.clear();
		g = new Graphics2D(shape.graphics);
		//This is just for test the icon
		//TODO draw a real beautiful icon for AsWing frame title
		//g.fillCircleRingWithThickness(new SolidBrush(ASColor.GREEN), x + WIDTH/2, y + WIDTH/2, WIDTH/2, WIDTH/4);
		ASColor outterRect =c.getUI ().getColor("Frame.activeCaptionBorder");
		//ASColor innerRect =UIManager.getColor("Frame.inactiveCaptionBorder");
		ASColor innerRect =new ASColor(0xFFFFFF );
		
		x = x + 2;
		int width =WIDTH ;
		int height =HEIGHT ;
		
		double w4 =width /4;
		double h23 =2*height /3;
		double w2 =width /2;
		double h =height ;
		double w =width ;
		
		Array points =new Array ();
		points.push(new Point(x, y));
		points.push(new Point(x+w4, y+h));
		points.push(new Point(x+w2, y+h23));
		points.push(new Point(x+w4*3, y+h));
		points.push(new Point(x+w, y));
		points.push(new Point(x+w2, y+h23));
		
		g.drawPolygon(new Pen(outterRect, 2), points);
		g.fillPolygon(new SolidBrush(innerRect), points);		
	}
	
	public int  getIconHeight (Component c )
	{
		return HEIGHT;
	}
	
	public int  getIconWidth (Component c )
	{
		return WIDTH + 2;
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return shape;
	}
	
}


