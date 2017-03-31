package org.aswing.plaf.basic.frame;

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
import flash.geom.Matrix;

import org.aswing.ASColor;
import org.aswing.Component;
import org.aswing.FrameTitleBar;
import org.aswing.GroundDecorator;
import org.aswing.UIManager;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.GradientBrush;
import org.aswing.graphics.Graphics2D;
import org.aswing.graphics.Pen;
import org.aswing.plaf.FrameUI;
import org.aswing.plaf.UIResource;

public class BasicFrameTitleBarBG implements GroundDecorator, UIResource{
		
	protected ASColor activeColor ;
	protected ASColor inactiveColor ;
	protected ASColor activeBorderColor ;
	
	public  BasicFrameTitleBarBG (){
		super();
	}

	public DisplayObject  getDisplay (Component c ){
		return null;
	}
	
	public void  updateDecorator (Component c ,Graphics2D g ,IntRectangle b ){
		FrameTitleBar bar =FrameTitleBar(c );
		FrameUI frameUI =bar.getFrame ().getUI ()as FrameUI ;
		if(activeColor == null){
			ASColor activeColor ;
			ASColor inactiveColor ;
			if(frameUI){
				activeColor = frameUI.getColor("Frame.activeCaption");
				inactiveColor = frameUI.getColor("Frame.inactiveCaption");
				activeBorderColor = frameUI.getColor("Frame.activeCaptionBorder");
			}else{
				activeColor = UIManager.getColor("Frame.activeCaption");
				inactiveColor = UIManager.getColor("Frame.inactiveCaption");
				activeBorderColor = UIManager.getColor("Frame.activeCaptionBorder");
			}
		}
        double x =b.x ;
		double y =b.y ;
		double w =b.width ;
		double h =b.height ;
		
		Array colors ;
		if(frameUI == null || frameUI.isPaintActivedFrame()){
	    	colors = .get(activeColor.darker(0.9).getRGB(), activeColor.getRGB());
		}else{
	    	colors = .get(inactiveColor.darker(0.9).getRGB(), inactiveColor.getRGB());
		}
		Array alphas =.get(1 ,1) ;
		Array ratios =.get(75 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w, h, (90/180)*Math.PI, x, y);
	    GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
	    g.fillRoundRect(brush, x, y, w, h, 4, 4, 0, 0);    
		
		if(frameUI == null || frameUI.isPaintActivedFrame()){
			colors = .get(activeColor.getRGB(), activeColor.getRGB());
		}else{
			colors = .get(inactiveColor.getRGB(), inactiveColor.getRGB());
		}
        
		alphas = .get(1, 0);
		ratios = .get(0, 100);
        brush = new GradientBrush(GradientBrush.LINEAR, colors, alphas, ratios, matrix);
        g.fillRoundRect(brush, x, y, w, h-h/4, 4, 4, 0, 0);
        Pen penTool =new Pen(activeBorderColor );
        g.drawLine(penTool, x, y+h-1, x+w, y+h-1);		
	}
	
}


