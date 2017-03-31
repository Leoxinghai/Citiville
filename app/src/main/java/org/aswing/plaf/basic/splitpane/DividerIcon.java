package org.aswing.plaf.basic.splitpane;

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
import org.aswing.Component;
import org.aswing.Icon;
import org.aswing.JSplitPane;
import org.aswing.geom.*;
import org.aswing.graphics.*;

/**
 * @private
 */
public class DividerIcon implements Icon
	public  DividerIcon (){
	}
	
	public void  updateIcon (Component com ,Graphics2D g ,int x ,int y )
	{
    	double w =com.getWidth ();
    	double h =com.getHeight ();
    	double ch =h /2;
    	double cw =w /2;
    	Divider divider =Divider(com );
    	Pen p =new Pen(divider.getOwner ().getForeground (),0);
    	if(divider.getOwner().getOrientation() == JSplitPane.VERTICAL_SPLIT){
	    	double hl =Math.min(5,w -1);
	    	g.drawLine(p, cw-hl, ch, cw+hl, ch);
	    	if(ch + 2 < h){
	    		g.drawLine(p, cw-hl, ch+2, cw+hl, ch+2);
	    	}
	    	if(ch - 2 > 0){
	    		g.drawLine(p, cw-hl, ch-2, cw+hl, ch-2);
	    	}
    	}else{
	    	double h2 =Math.min(5,h -1);
	    	g.drawLine(p, cw, ch-h2, cw, ch+h2);
	    	if(cw + 2 < h){
	    		g.drawLine(p, cw+2, ch-h2, cw+2, ch+h2);
	    	}
	    	if(cw - 2 > 0){
	    		g.drawLine(p, cw-2, ch-h2, cw-2, ch+h2);
	    	}
    	}			
	}
	
	public int  getIconHeight (Component c )
	{
		return 0;
	}
	
	public int  getIconWidth (Component c )
	{
		return 0;
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


