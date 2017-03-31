
package org.aswing.dnd;

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
import org.aswing.*;
import org.aswing.graphics.*;

/**
 * The default drag image.
 * @author iiley
 */
public class DefaultDragImage implements DraggingImage{
	
	private Shape image ;
	private int width ;
	private int height ;
	
	public  DefaultDragImage (Component dragInitiator ){
		width = dragInitiator.width;
		height = dragInitiator.height;
		
		image = new Shape();
	}
	
	public DisplayObject  getDisplay ()
	{
		return image;
	}
	
	public void  switchToRejectImage ()
	{
		image.graphics.clear();
		double r =Math.min(width ,height )-2;
		double x =0;
		double y =0;
		double w =width ;
		double h =height ;
		Graphics2D g =new Graphics2D(image.graphics );
		g.drawLine(new Pen(ASColor.RED, 2), x+1, y+1, x+1+r, y+1+r);
		g.drawLine(new Pen(ASColor.RED, 2), x+1+r, y+1, x+1, y+1+r);
		g.drawRectangle(new Pen(ASColor.GRAY), x, y, w, h);
	}
	
	public void  switchToAcceptImage ()
	{
		image.graphics.clear();
		Graphics2D g =new Graphics2D(image.graphics );
		g.drawRectangle(new Pen(ASColor.GRAY), 0, 0, width, height);
	}
	
}


