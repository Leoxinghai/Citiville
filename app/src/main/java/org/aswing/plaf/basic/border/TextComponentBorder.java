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
import org.aswing.geom.IntRectangle;
import org.aswing.*;
import flash.display.DisplayObject;
import org.aswing.plaf.*;
import org.aswing.error.ImpMissError;

/**
 * @private
 */
public class TextComponentBorder implements Border, UIResource{

    private ASColor light ;
    private ASColor shadow ;
    	
	public  TextComponentBorder (){
		
	}
	
    protected String  getPropertyPrefix (){
    	throw new ImpMissError();
        return "";
    }
	
	private void  reloadColors (ComponentUI ui ){
		light = ui.getColor(getPropertyPrefix()+"light");
		shadow = ui.getColor(getPropertyPrefix()+"shadow");
	}
    	
	public void  updateBorder (Component c ,Graphics2D g ,IntRectangle r )
	{
		if(light == null){
			reloadColors(c.getUI());
		}
	    double x1 =r.x ;
		double y1 =r.y ;
		double w =r.width ;
		double h =r.height ;
		EditableComponent textCom =EditableComponent(c );
		if(textCom.isEditable() && c.isEnabled()){
			g.drawRectangle(new Pen(shadow, 1), x1+0.5, y1+0.5, w-1, h-1);
		}
		g.drawRectangle(new Pen(light, 1), x1+1.5, y1+1.5, w-3, h-3);		
	}
	
	public Insets  getBorderInsets (Component com ,IntRectangle bounds )
	{
		return new Insets(2, 2, 2, 2);
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


