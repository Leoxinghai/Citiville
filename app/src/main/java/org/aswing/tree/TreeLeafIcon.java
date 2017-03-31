/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.tree;

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


import flash.geom.Point;
import org.aswing.Component;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.*;
import org.aswing.Icon;
import org.aswing.plaf.UIResource;
import flash.display.DisplayObject;
import flash.geom.Matrix;
import org.aswing.ASColor;

/**
 * The default leaf icon for JTree.
 * TODO draw a nicer icon
 * @author iiley
 */
public class TreeLeafIcon implements Icon, UIResource{
	
	public  TreeLeafIcon (){
	}
	
	public int  getIconWidth (Component c ){
		return 16;
	}

	public int  getIconHeight (Component c ){
		return 16;
	}

	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		IntRectangle b =new IntRectangle(0,0,16,16);
		b.grow( -2, -1 );
		b.move( x, y );
		
		int foldSize =4;
		Array points =new Array ();
		points.push( new Point( b.x, 								b.y ) );
		points.push( new Point( b.width - foldSize, b.y ) );
		points.push( new Point( b.width, 						b.y + foldSize ) );
		points.push( new Point( b.width, 						b.height ) );
		points.push( new Point( b.x, 								b.height ) );
		
    Array colors =.get(0xE6E9EE ,0x8E94BD) ;
		Array alphas =.get(100 /255,100/255) ;
		Array ratios =.get(0 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(b.width, b.height, 0, b.x, b.y);     
    GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
    g.fillPolygon(brush, points);
		
		g.drawPolygon(new Pen(ASColor.BLACK, 0.5), points );
		
		Array foldPoints =new Array ();
		foldPoints.push( new Point( b.width - foldSize, b.y ) );
		foldPoints.push( new Point( b.width - foldSize, b.y + foldSize ) );
		foldPoints.push( new Point( b.width, 						b.y + foldSize ) );
		g.drawPolyline( new Pen(ASColor.BLACK, 0.5), foldPoints );
	}

	public DisplayObject  getDisplay (Component c ){
		return null;
	}
}


