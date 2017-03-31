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


import org.aswing.graphics.Graphics2D;
import org.aswing.Icon;
import org.aswing.Component;
import flash.display.DisplayObject;
import org.aswing.plaf.UIResource;

/**
 * @private
 */
public class MenuCheckIcon implements Icon, UIResource{
	
	public  MenuCheckIcon (){
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
	}
	
	public int  getIconHeight (Component c ){
		return 8;
	}
	
	public int  getIconWidth (Component c ){
		return 8;
	}
	
	public DisplayObject  getDisplay (Component c ){
		return null;
	}
	
}


