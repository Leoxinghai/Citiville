/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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
import org.aswing.plaf.BaseComponentUI;
import org.aswing.geom.IntDimension;

/**
 * @private
 */
public class BasicSpacerUI extends BaseComponentUI
	public  BasicSpacerUI (){
		super();
	}
	
	protected String  getPropertyPrefix (){
		return "Spacer.";
	}
	
	 public void  installUI (Component c ){
		installDefaults(JSpacer(c));
	}
	
	 public void  uninstallUI (Component c ){
		uninstallDefaults(JSpacer(c));
	}
	
	protected void  installDefaults (JSpacer s ){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColors(s, pp);
		LookAndFeel.installBasicProperties(s, pp);
		LookAndFeel.installBorderAndBFDecorators(s, pp);
	}
	
	protected void  uninstallDefaults (JSpacer s ){
		LookAndFeel.uninstallBorderAndBFDecorators(s);
	}
	
	 public IntDimension  getPreferredSize (Component c ){
		return c.getInsets().getOutsideSize(new IntDimension(0, 0));
	}
	
	 public IntDimension  getMaximumSize (Component c )
	{
		return IntDimension.createBigDimension();
	}
	/**
	 * Returns null
	 */	
	 public IntDimension  getMinimumSize (Component c )
	{
		return new IntDimension(0, 0);
	}
}


