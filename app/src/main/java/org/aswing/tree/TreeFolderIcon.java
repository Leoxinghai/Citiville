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


import flash.display.DisplayObject;
import flash.geom.Point;
import org.aswing.ASColor;
import org.aswing.Component;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.*;
import org.aswing.Icon;
import org.aswing.plaf.UIResource;
import flash.geom.Matrix;

/**
 * The default folder icon for JTree.
 * TODO draw a nicer icon
 * @author iiley
 */
public class TreeFolderIcon implements Icon, UIResource{
	
	public  TreeFolderIcon (){
	}
	
	public int  getIconWidth (Component c ){
		return 16;
	}

	public int  getIconHeight (Component c ){
		return 16;
	}

	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y ){
		//ASColor borderColor =ASColor.BLACK ;
		ASColor borderColor =new ASColor(0x555555 );
		
		IntRectangle b =new IntRectangle(0,0,16,16);
		b.grow( 0, -1 );
		b.height -= 1;
		b.move( x, y );
		
		// Draw back face
		int flapSize =5;
		Array backPoints =new Array ();
		backPoints.push( new Point( b.x, 								b.y ) );
		backPoints.push( new Point( b.x + flapSize, 		b.y ) );
		backPoints.push( new Point( b.x + flapSize + 1, b.y + 1 ) );
		backPoints.push( new Point( b.width - 3, 				b.y + 1 ) );
		backPoints.push( new Point( b.width - 3, 				b.height ) );
		backPoints.push( new Point( b.x, 								b.height ) );
		
    Array colors =.get(0xE6E9EE ,0x8E94BD) ;
		Array alphas =.get(100 /255,100/255) ;
		Array ratios =.get(0 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(b.width, b.height, 0, b.x, b.y);     
    GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
    g.fillPolygon(brush, backPoints);
		
		//g.drawPolygon( new Pen( ASColor.BLACK, 0.5 ), backPoints );
		g.drawPolygon( new Pen( borderColor, 0.5 ), backPoints );
		
		// Draw front face
		alphas = .get(230/255, 230/255);
		brush = new GradientBrush(GradientBrush.LINEAR, colors, alphas, ratios, matrix);
		
		Array frontPoints =new Array ();
		frontPoints.push( new Point( b.x, 				b.height ) );
		frontPoints.push( new Point( b.x + 3, 		b.y + 3 ) );
		frontPoints.push( new Point( b.width, 		b.y + 3 ) );
		frontPoints.push( new Point( b.width - 3, b.height ) );
		
		g.fillPolygon(brush, frontPoints);
		
		g.drawPolygon( new Pen( borderColor, 0.25 ), frontPoints );
		
	}

	public DisplayObject  getDisplay (Component c ){
		return null;
	}

}


