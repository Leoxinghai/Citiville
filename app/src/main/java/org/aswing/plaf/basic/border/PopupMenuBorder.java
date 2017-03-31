package org.aswing.plaf.basic.border;

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

import org.aswing.geom.*;
import org.aswing.*;
import flash.display.*;
import org.aswing.plaf.*;
import org.aswing.graphics.*;

/**
 * @private
 */
public class PopupMenuBorder implements Border, UIResource{
	
	protected ASColor color ;
	
	public  PopupMenuBorder (){
	}
	
	public void  updateBorder (Component c ,Graphics2D g ,IntRectangle bounds ){
		if(color == null){
			color = c.getUI().getColor("PopupMenu.borderColor");
		}
		g.beginDraw(new Pen(color.changeAlpha(0.5), 4));
		g.moveTo(bounds.x + bounds.width - 2, bounds.y+8);
		g.lineTo(bounds.x + bounds.width - 2, bounds.y+bounds.height-2);
		g.lineTo(bounds.x + 8, bounds.y+bounds.height-2);
		g.endDraw();
		g.drawRectangle(new Pen(color, 1), 
			bounds.x+0.5, bounds.y+0.5, 
			bounds.width - 4,
			bounds.height - 4);
	}
	
	public Insets  getBorderInsets (Component com ,IntRectangle bounds ){
		return new Insets(1, 1, 4, 4);
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}
	
}


