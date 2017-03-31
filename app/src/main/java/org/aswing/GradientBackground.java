package org.aswing;

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
import org.aswing.geom.IntRectangle;
import flash.display.DisplayObject;
import flash.geom.*;
import flash.display.Shape;

/**
 * A background decorator that paint a gradient color.
 * @author
 */
public class GradientBackground implements GroundDecorator{
	
	private GradientBrush brush ;
	private double direction ;
	private Shape shape ;
	
	public  GradientBackground (String fillType ,Array colors ,Array alphas ,Array ratios ,double direction =0,
					spreadMethod:String = "pad", interpolationMethod:String = "rgb", focalPointRatio:Number = 0){
		this.brush = new GradientBrush(fillType, colors, alphas, ratios, new Matrix(), 
			spreadMethod, interpolationMethod, focalPointRatio);
		this.direction = direction;
		shape = new Shape();
	}
	
	public void  updateDecorator (Component com ,Graphics2D g ,IntRectangle bounds ){
		shape.graphics.clear();
		g = new Graphics2D(shape.graphics);
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(bounds.width, bounds.height, direction, bounds.x, bounds.y);    
		brush.setMatrix(matrix);
		g.fillRectangle(brush, bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public DisplayObject  getDisplay (Component c ){
		return shape;
	}
	
}


