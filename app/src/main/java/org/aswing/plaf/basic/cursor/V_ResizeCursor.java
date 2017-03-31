/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.cursor;

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


import flash.display.Shape;
import org.aswing.graphics.*;
import org.aswing.UIManager;
import org.aswing.ASColor;

/**
 * @private
 */
public class V_ResizeCursor extends Shape{
	
	private ASColor resizeArrowColor ;
	private ASColor resizeArrowLightColor ;
	private ASColor resizeArrowDarkColor ;
	
	public  V_ResizeCursor (){
		super();
		
	    resizeArrowColor = UIManager.getColor("Frame.resizeArrow");
	    resizeArrowLightColor = UIManager.getColor("Frame.resizeArrowLight");
	    resizeArrowDarkColor = UIManager.getColor("Frame.resizeArrowDark");

		double w =1;//arrowAxisHalfWidth
		double r =4;
		Array arrowPoints ;
		
		arrowPoints = [{y:-r*2, x:0}, {y:-r, x:-r}, {y:-r, x:-w},
						 {y:r, x:-w}, {y:r, x:-r}, {y:r*2, x:0},
						 {y:r, x:r}, {y:r, x:w}, {y:-r, x:w},
						 {y:-r, x:r}];
		Graphics2D gdi =new Graphics2D(graphics );
		gdi.drawPolygon(new Pen(resizeArrowColor.changeAlpha(0.4), 4), arrowPoints);
		gdi.fillPolygon(new SolidBrush(resizeArrowLightColor), arrowPoints);
		gdi.drawPolygon(new Pen(resizeArrowDarkColor, 1), arrowPoints);			
	}
}


