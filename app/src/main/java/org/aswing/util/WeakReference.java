package org.aswing.util;

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

import flash.utils.Dictionary;

/**
 * WeakReference, the value will be weak referenced.
 * @author iiley
 */
public class WeakReference{

	private Dictionary weakDic ;

	public  WeakReference (){
		super();
	}

	public void  value (*)v {
		if(v == null){
			weakDic = null;
		}else{
			weakDic = new Dictionary(true);
			weakDic.put(v,  null);
		}
	}

	public Object value () {
		if(weakDic){
			for(in v *weakDic ){
				return v;
			}
		}
		return null;
	}

	/**
	 * Clear the value, same to <code>WeakReference.value=null;</code>
	 */
	public void  clear (){
		weakDic = null;
	}
}


