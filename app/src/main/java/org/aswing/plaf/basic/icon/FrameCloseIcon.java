/*
 Copyright aswing.org, see the LICENCE.txt.
*/


package org.aswing.plaf.basic.icon;

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

	
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;

/**
 * The icon for frame close.
 * @author iiley
 * @private
 */
public class FrameCloseIcon extends FrameIcon
	public  FrameCloseIcon (){
		super();
	}
	
	 public void  updateIconImp (Component c ,Graphics2D g ,int x ,int y )
	{
		double w =width /2;
		g.drawLine(
			new Pen(getColor(c), w/3), 
			x+(width-w)/2, y+(width-w)/2,
			x+(width+w)/2, y+(width+w)/2);
		g.drawLine(
			new Pen(getColor(c), w/3), 
			x+(width-w)/2, y+(width+w)/2,
			x+(width+w)/2, y+(width-w)/2);		
	}	
	
	 public int  getIconWidth (Component c ){
		return super.getIconWidth(c) + 2;
	}
}


