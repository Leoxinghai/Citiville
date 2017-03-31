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
import org.aswing.geom.IntRectangle;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;

/**
 * Basic frame border
 * @author iiley
 * @private
 */
public class FrameBorder implements Border, UIResource{
	
	private static  int GLASS =4;
	
	private ASColor activeColor ;
	private ASColor inactiveColor ;
	
	public  FrameBorder (){
	}
		
	public void  updateBorder (Component c ,Graphics2D g ,IntRectangle b )
	{
		if(activeColor == null){
			activeColor   = c.getUI().getColor("Frame.activeCaptionBorder");
			inactiveColor = c.getUI().getColor("Frame.inactiveCaptionBorder");   
		}
		JFrame frame =JFrame(c );
		ASColor color =frame.getFrameUI ().isPaintActivedFrame ()? activeColor : inactiveColor;
		
		//draw the shadow
		g.beginDraw(new Pen(new ASColor(0, 0.3), 4));
		g.moveTo(b.x + b.width - 3, b.y + 14);
		g.lineTo(b.x + b.width - 2, b.y+16);
		g.lineTo(b.x + b.width - 2, b.y+b.height-2 - 9);
		g.curveTo(b.x + b.width - 2, b.y+b.height-2, b.x + b.width - 2 - 9,  b.y+b.height-2);
		g.lineTo(b.x + 10, b.y+b.height-2);
		g.lineTo(b.x + 8, b.y+b.height-3);
		g.endDraw();
		b = b.clone();
		b.width -= 3;
		b.height -= 3;
		
		//fill alpha rect
		g.drawRoundRect(
			new Pen(color, 1), 
			b.x+0.5, b.y+0.5, b.width-1, b.height-1, 
			GLASS + 4);
		g.drawRoundRect(
			new Pen(ASColor.WHITE, 1), 
			b.x+1.5, b.y+1.5, b.width-3, b.height-3, 
			GLASS + 3);
		g.fillRoundRectRingWithThickness(
			new SolidBrush(color.changeAlpha(0.2)),
			b.x + 2, b.y + 2, b.width - 4, b.height - 4,
			GLASS+2, GLASS, GLASS+1);
		g.drawRoundRect(
			new Pen(color, 1), 
			b.x+2.5+GLASS, b.y+2.5+GLASS, b.width-5-GLASS*2, b.height-5-GLASS*2, 
			GLASS, GLASS, 0, 0);
	}
	
	public Insets  getBorderInsets (Component com ,IntRectangle bounds )
	{
		int w =GLASS +3;
		return new Insets(w, w, w+3, w+3);
	}
	
	public DisplayObject  getDisplay (Component c )
	{
		return null;
	}
	
}


