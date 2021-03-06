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
public class CheckBoxMenuItemCheckIcon extends MenuCheckIcon{
	
	private Shape shape ;
	
	public  CheckBoxMenuItemCheckIcon (){
		shape = new Shape();
	}
	
	 public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		shape.graphics.clear();
		g = new Graphics2D(shape.graphics);
		AbstractButton menu =AbstractButton(c );
		if(menu.isSelected()){
			g.beginDraw(new Pen(ASColor.BLACK, 2));
			g.moveTo(x, y+4);
			g.lineTo(x+3, y+7);
			g.lineTo(x+7, y+2);
			g.endDraw();
		}
	}
	
	 public DisplayObject  getDisplay (Component c ){
		return shape;
	}
}


