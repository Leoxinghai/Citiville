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
 * A map that both key and value are weaks.
 */
public class WeakMap{

	private Dictionary dic ;

	public  WeakMap (){
		super();
		dic = new Dictionary(true);
	}

	public void  put (*key ,Object value){
		Dictionary wd =new Dictionary(true );
		wd.put(value,  null);
		dic.put(key,  wd);
	}

	public  getValue (*)key *{
		Dictionary wd =dic.get(key) ;
		if(wd){
			for(in v *wd ){
				return v;
			}
		}
		return null;
	}

	public  remove (*)key *{
		value = getValue(key);
		delete dic.get(key);
		return value;
	}
}


