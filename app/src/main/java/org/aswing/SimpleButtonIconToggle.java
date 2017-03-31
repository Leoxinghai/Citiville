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

public class SimpleButtonIconToggle implements Icon{

	private SimpleButton asset ;
	private DisplayObject upState ;
	private DisplayObject overState ;
	private DisplayObject downState ;
	private int width ;
	private int height ;

	private static Array disabledFilters ;
	private static Array eabledFilters =new Array();

	public  SimpleButtonIconToggle (SimpleButton asset ){
		this.asset = asset;
		width = Math.ceil(asset.width);
		height = Math.ceil(asset.height);
		asset.mouseEnabled = false;
		upState = asset.upState;
		overState = asset.overState;
		downState = asset.downState;

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
		AbstractButton b =(AbstractButton)c;
		DisplayObject state =null ;
		if(b){
			ButtonModel model =b.getModel ();
			if(model.isPressed() && model.isArmed() || model.isSelected()) {
				state = downState;
			}else if(b.isRollOverEnabled() && model.isRollOver()) {
				state = overState;
			}else{
				state = upState;
			}
			asset.upState = state;
			asset.filters = model.isEnabled() ? eabledFilters : disabledFilters;
		}
	}

}


