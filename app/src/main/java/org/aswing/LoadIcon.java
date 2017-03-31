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


import org.aswing.graphics.Graphics2D;
import flash.display.DisplayObject;
import flash.display.Loader;
import flash.events.*;
import flash.net.URLRequest;
import flash.display.Sprite;
import flash.system.LoaderContext;

/**
 * LoadIcon allow you load extenal image/animation to be the icon content.
 * @author senkay
 */
public class LoadIcon extends AssetIcon{
	
	protected Loader loader ;
	protected Component owner ;
	protected URLRequest urlRequest ;
	protected LoaderContext context ;
	protected boolean needCountSize ;
	
	/**
	 * Creates a LoadIcon with specified url/URLRequest, width, height.
	 * @param url the url/URLRequest for a asset location.
	 * @param width (optional)the width of this icon.(miss this param mean use image width)
	 * @param height (optional)the height of this icon.(miss this param mean use image height)
	 * @param scale (optional)whether scale the extenal image/anim to fit the size 
	 * 		specified by front two params, default is false
	 */
	public  LoadIcon (*url ,double width =-1,double height =-1,boolean scale =false ,LoaderContext context =null ){
		super(getLoader(), width, height, false);
		this.scale = scale;
		if(url is URLRequest){
			urlRequest = url;
		}else{
			urlRequest = new URLRequest(url);
		}
		this.context = context;
		needCountSize = (width == -1 || height == -1);
		getLoader().load(urlRequest, context);
	}
	
	/**
	 * Return the loader
	 * @return this loader
	 */
	public Loader  getLoader (){
		if (loader == null){
			loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, __onComplete);
			loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, __onLoadError);
		}
		return loader;
	}
	
	/**
	 * when the loader init updateUI
	 */
	private void  __onComplete (Event e ){
		if(needCountSize){
			setWidth(loader.width);
			setHeight(loader.height);
		}
		if(scale){
			loader.width = width;
			loader.height = height;
		}
		if(owner){
			owner.repaint();
			owner.revalidate();	
		}
	}
	
	private void  __onLoadError (IOErrorEvent e ){
		//do nothing
	}
	

	 public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		super.updateIcon(c, g, x, y);
		owner = c;
	}
	
	 public int  getIconHeight (Component c ){
		owner = c;
		return super.getIconHeight(c);
	}
	
	 public int  getIconWidth (Component c ){
		owner = c;
		return super.getIconWidth(c);
	}
	
	 public DisplayObject  getDisplay (Component c ){
		owner = c;
		return super.getDisplay(c);
	}
	
	public LoadIcon  clone (){
		return new LoadIcon(urlRequest, needCountSize ? -1 : width, needCountSize ? -1 : height, scale, context);
	}
}


