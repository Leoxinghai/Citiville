/*
 Copyright aswing.org, see the LICENCE.txt.
*/

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
import flash.display.Loader;
import flash.utils.*;

import org.aswing.event.AttachEvent;
import org.aswing.graphics.Graphics2D;
import flash.display.Sprite;
import flash.system.ApplicationDomain;

/**
 * Attach a displayObject from library with the linkage name to be a icon.
 * @author senkay
 * @author iiley
 */
public class AttachIcon extends AssetIcon{
	
	private ApplicationDomain applicationDomain ;
	private String className ;
	
	/**
	 * Attach a mc from library to be a icon.<br>
	 * If speciaficed the width and height, the mc will be scale to be this size if scale setted true.
	 * else the width and height will be the symbol's width and height.
	 * @param linkage the linkageID of the symbol to attach
	 * @param loader the loaderObject with attach symbol,if loader is null then loader is root
	 * @param width (optional)if you specifiled the width of the Icon, and scale is true,
	 * the mc will be scale to this width when paint.
	 * @param height (optional)if you specifiled the height of the Icon, and scale is true, 
	 * the mc will be scale to this height when paint.
	 * @param scale (optional)whether scale MC to fix the width and height specified. Default is true
	 */
	public  AttachIcon (String assetClassName ,ApplicationDomain applicationDomain =null ,int width =-1,int height =-1,boolean scale =false ){
		super(getAttachDisplayObject(assetClassName, applicationDomain), width, height, scale);
	}
	
	/**
	 * return the attach displayObject
	 * if cannot create from Class then return null
	 */
	protected DisplayObject  getAttachDisplayObject (String assetClassName ,ApplicationDomain ad ){
		className = assetClassName;
		applicationDomain = (ad == null ? ApplicationDomain.currentDomain : ad);
		Class classReference ;
		try{
			classReference =(Class) applicationDomain.getDefinition(className);
		}catch(e:Error){
			return null;
		}
		DisplayObject attachMC =new classReference ()as DisplayObject ;
		return attachMC;
	}
}


