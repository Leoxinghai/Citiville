package org.aswing.plaf.basic.background;

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
import org.aswing.GroundDecorator;
import org.aswing.geom.IntRectangle;
import org.aswing.Component;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;
import flash.geom.Matrix;

/**
 * @private
 */
public class TextComponentBackBround implements GroundDecorator, UIResource{
	
	public void  updateDecorator (Component c ,Graphics2D g ,IntRectangle r ){
    	if(c.isOpaque() && c.isEnabled()){
			double x =r.x ;
			double y =r.y ;
			double w =r.width ;
			double h =r.height ;
			g.fillRectangle(new SolidBrush(c.getBackground()), x, y, w, h);
			
			Array colors =.get(0xF7F7F7 ,c.getBackground ().getRGB ()) ;
			Array alphas =.get(0 .5,0) ;
			Array ratios =.get(0 ,100) ;
			Matrix matrix =new Matrix ();
			matrix.createGradientBox(w, h, (90/180)*Math.PI, x, y);     
		    GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
		    g.fillRectangle(brush, x, y, w, h);
    	}
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


