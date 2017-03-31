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
import flash.display.MovieClip;
import flash.utils.*;

import org.aswing.event.AttachEvent;
import org.aswing.geom.IntDimension;
import flash.display.Loader;
import flash.display.Sprite;
import flash.events.*;
import flash.net.URLRequest;
import flash.system.ApplicationDomain;

/**
 * Dispatched when when the symbol was attached.
 * @eventType org.aswing.event.AttachEvent.ATTACHED
 */
.get(Event(name="attached", type="org.aswing.event.AttachEvent"))

/**
 * JAttachPane, a container attach flash symbol in library to be its floor.
 * @see org.aswing.JLoadPane
 * @author iiley
 */
public class JAttachPane extends AssetPane{
	
	private String className ;
	private ApplicationDomain applicationDomain ;
	
	/**
	 * Creates a JAttachPane with a path to attach a symbol from library.
	 * 
	 * @param assetClassName the class name of the symbol in library
	 * @param prefferSizeStrategy the prefferedSize count strategy. Must be one of below:
	 * <ul>
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_BOTH}
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_IMAGE}
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_LAYOUT}
	 * </ul>
	 * Default is PREFER_SIZE_IMAGE.
	 * @param applicationDomain the applicationDomain for the class placed in. default is null means current domain.
	 * @see #setPath()
	 */
	public  JAttachPane (String assetClassName =null ,int prefferSizeStrategy =1,ApplicationDomain applicationDomain =null ){
		super(null, prefferSizeStrategy);		
		setName("JAttachPane");
		this.className = assetClassName;
		this.applicationDomain = (applicationDomain == null ? ApplicationDomain.currentDomain : applicationDomain);
		setAsset(createAsset());
	}
	
	/**
	 * Sets the class name of the asset.
	 * @param assetClassName the asset class name.
	 */
	public void  setAssetClassName (String assetClassName ){
		if(className != assetClassName){
			className = assetClassName;
			setAsset(createAsset());
		}
	}
	
	/**
	 * Sets the applicationDomain.
	 * @param ad the applicationDomain.
	 */
	public void  setApplicationDomain (ApplicationDomain ad ){
		if(applicationDomain != ad){
			applicationDomain = ad;
			setAsset(createAsset());
		}
	}
	
	public String  getAssetClassName (){
		return className;
	}
	
	public ApplicationDomain  getApplicationDomain (){
		return applicationDomain;
	}
	
	/**
	 * Sets the path to attach displayObject from library of loader.
	 * @param assetClassName the linkageID of a displayObject.
	 * @param loader the loader that its contentLoaderInfo.appliactionDomain to be used.
	 */
	public void  setAssetClassNameAndLoader (String assetClassName ,Loader loader ){
		if(className != assetClassName 
			|| applicationDomain != loader.contentLoaderInfo.applicationDomain){
			className = assetClassName;
			applicationDomain = loader.contentLoaderInfo.applicationDomain;
			setAsset(createAsset());
		}
	}
	
	/**
	 * unload the asset of the pane
	 */ 
	 public void  unloadAsset (){
		className = null;
		super.unloadAsset();
	}
	
	/**
	 * return the class name of the asset.
	 * @return the class name.
	 */ 
	public String  getClassName (){
		return className;
	}
	
	private DisplayObject  createAsset (){
		if(className == null){
			return null;
		}
		Class classReference ;
		if (applicationDomain == null){
			classReference =(Class) getDefinitionByName(className);
		}else{
			classReference =(Class) applicationDomain.getDefinition(className);
		}
		if(classReference == null){
			return null;
		}
		DisplayObject attachMC =new classReference ()as DisplayObject ;
		if(attachMC == null){
			return null;
		}
		setAssetOriginalSize(new IntDimension(attachMC.width, attachMC.height));
		dispatchEvent(new AttachEvent(AttachEvent.ATTACHED));
		return attachMC;
	}
}


