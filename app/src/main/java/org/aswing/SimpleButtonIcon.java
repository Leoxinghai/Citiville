package org.aswing;

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
import org.aswing.graphics.Graphics2D;
import flash.display.SimpleButton;
import flash.filters.ColorMatrixFilter;

public class SimpleButtonIcon implements Icon{

	private SimpleButton asset ;
	private int width ;
	private int height ;

	private static Array disabledFilters ;
	private static Array eabledFilters =new Array();

	public  SimpleButtonIcon (SimpleButton asset ){
		this.asset = asset;
		width = Math.ceil(asset.width);
		height = Math.ceil(asset.height);

		if(disabledFilters == null){
			Array cmatrix =.get(0 .3,0.59,0.11,0,0,0.3,0.59,0.11,0,0,0.3,0.59,0.11,0,0,0,0,0,1,0) ;
			disabledFilters = .get(new ColorMatrixFilter(cmatrix));
		}
	}

	public DisplayObject  getDisplay (Component c ){
		return asset;
	}

	public int  getIconWidth (Component c ){
		return width;
	}

	public int  getIconHeight (Component c ){
		return height;
	}

	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		asset.x = x;
		asset.y = y;
		asset.mouseEnabled = c.isEnabled();
		asset.filters = c.isEnabled() ? eabledFilters : disabledFilters;
	}

}


