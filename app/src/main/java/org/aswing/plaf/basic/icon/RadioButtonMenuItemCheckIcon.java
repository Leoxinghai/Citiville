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

	
import flash.display.*;
import org.aswing.*;
import org.aswing.graphics.*;

/**
 * @private
 */
public class RadioButtonMenuItemCheckIcon extends MenuCheckIcon{
	
	private Shape shape ;
	
	public  RadioButtonMenuItemCheckIcon (){
		shape = new Shape();
	}
	
	 public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		shape.graphics.clear();
		g = new Graphics2D(shape.graphics);
		AbstractButton menu =AbstractButton(c );
		if(menu.isSelected()){
			g.fillCircle(new SolidBrush(ASColor.BLACK), x+4, y+5, 3);
		}
	}
	
	 public DisplayObject  getDisplay (Component c ){
		return shape;
	}
}


