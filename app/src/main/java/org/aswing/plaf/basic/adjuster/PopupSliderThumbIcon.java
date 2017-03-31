/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.adjuster;

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

	
import org.aswing.plaf.basic.icon.SliderThumbIcon;
import org.aswing.Component;

/**
 * @private
 */
public class PopupSliderThumbIcon extends SliderThumbIcon
	public  PopupSliderThumbIcon ()
	{
		super();
	}
	
	 protected String  getPropertyPrefix (){
		return "Adjuster.";
	}	
	
	 public int  getIconHeight (Component c )
	{
		return 12;
	}
	
	 public int  getIconWidth (Component c )
	{
		return 6;
	}	
	
}


