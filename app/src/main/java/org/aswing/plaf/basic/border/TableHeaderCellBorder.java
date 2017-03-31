/*
 Copyright aswing.org, see the LICENCE.txt.
*/

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

	
import org.aswing.graphics.*;
import org.aswing.*;
import org.aswing.geom.*;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;
import org.aswing.plaf.ComponentUI;

/**
 * @private
 */
public class TableHeaderCellBorder implements Border, UIResource{
	
	private ASColor shadow ;
    private ASColor darkShadow ;
    private ASColor highlight ;
    private ASColor lightHighlight ;
    
	public  TableHeaderCellBorder (){
	}
	
	private void  reloadColors (ComponentUI ui ){
		shadow = ui.getColor("Button.shadow");
		darkShadow = ui.getColor("Button.darkShadow");
		highlight = ui.getColor("Button.light");
		lightHighlight = ui.getColor("Button.highlight");
	}
	
	public void  updateBorder (Component c ,Graphics2D g ,IntRectangle b ){
		if(shadow == null){
			reloadColors(c.getUI());
		}
		Pen pen =new Pen(darkShadow ,1);
		g.drawLine(pen, b.x+b.width-0.5, b.y+4, b.x+b.width-0.5, Math.max(b.y+b.height-2, b.y+4));
		g.fillRectangle(new SolidBrush(darkShadow), b.x, b.y+b.height-1, b.width, 1);
	}
	
	public Insets  getBorderInsets (Component com ,IntRectangle bounds )
	{
		return new Insets(0, 0, 1, 1);
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


