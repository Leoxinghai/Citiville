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

	
import flash.display.*;
import flash.events.*;
import flash.net.*;
import flash.system.*;

import org.aswing.geom.*;
import org.aswing.util.*;

/**
 * Dispatched when data has loaded successfully. The complete event is always dispatched after the init event. 
 * @eventType flash.events.Event.COMPLETE
 */
.get(Event(name="complete", type="flash.events.Event"))

/**
 * Dispatched when a network request is made over HTTP and Flash Player can detect the HTTP status code.  
 * @eventType flash.events.HTTPStatusEvent.HTTP_STATUS
 */
.get(Event(name="httpStatus", type="flash.events.HTTPStatusEvent"))

/**
 * Dispatched when the properties and methods of a loaded SWF file are accessible. A LoaderInfo object dispatches the init event when the following two conditions exist:  
 * @eventType flash.events.Event.INIT 
 */
.get(Event(name="init", type="flash.events.Event"))

/**
 * Dispatched when an input or output error occurs that causes a load operation to fail. 
 * @eventType flash.events.IOErrorEvent.IO_ERROR 
 */
.get(Event(name="ioError", type="flash.events.IOErrorEvent"))

/**
 * Dispatched when a load operation starts. 
 * @eventType  flash.events.Event.OPEN
 */
.get(Event(name="open", type="flash.events.Event"))

/**
 * Dispatched when data is received as the download operation progresses. 
 * @eventType flash.events.ProgressEvent.PROGRESS 
 */
.get(Event(name="progress", type="flash.events.ProgressEvent"))

/**
 * Dispatched by a LoaderInfo object whenever a loaded object is removed by using the unload() method of the Loader object, 
 * or when a second load is performed by the same Loader object and the original content is removed prior to the load beginning. 
 * @eventType flash.events.Event.UNLOAD
 */
.get(Event(name="unload", type="flash.events.Event"))

/**
 * JLoadPane, a container load a external image/animation to be its asset.
 * @see org.aswing.JAttachPane
 * @author iiley
 */	
public class JLoadPane extends AssetPane{
	
	protected Loader loader ;
	protected boolean loadedError ;
	protected URLRequest urlRequest ;
	protected LoaderContext context ;
	protected DisplayObjectContainer regularAssetContainer ;
	
	/**
	 * Creates a JLoadPane with a path to load external image or animation file.
	 * <p>The asset of the JLoadPane will only be available after load completed. It mean 
	 * <code>getAsset()</code> will return null before load completed.</p>
	 * @param url the path string or a URLRequst instance, null to make it do not load any thing.
	 * @param prefferSizeStrategy the prefferedSize count strategy. Must be one of below:
	 * <ul>
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_BOTH}
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_IMAGE}
	 * <li>{@link org.aswing.AssetPane#PREFER_SIZE_LAYOUT}
	 * </ul>
	 * @param context the loader context.
	 * @see #setPath()
	 */
	( = JLoadPaneurlnull,intprefferSizeStrategy=1,LoaderContextcontext=null){
		super(null, prefferSizeStrategy);
		setName("JLoadPane");
		loadedError = false;
		if(url == null){
			urlRequest = null;
		}else if(url is URLRequest){
			urlRequest =(URLRequest) url;
		}else{
			urlRequest = new URLRequest((String)url);
		}
		this.context = context;
		regularAssetContainer = assetContainer;
		loader = createLoader();
		loadAsset();
	}
	
	 public void  setAsset (DisplayObject asset ){
		if(assetContainer == loader){
			assetContainer = regularAssetContainer;
			removeChild(loader);
			loader.mask = null;
			addChild(assetContainer);
			bringToBottom(assetContainer);
			applyMaskAsset();
		}
		super.setAsset(asset);
	}
	
	/**
	 * Sets the asset loaded by JLoadPane's loader.
	 */
	protected void  setLoadedAsset (DisplayObject asset ){
		if(assetContainer == regularAssetContainer){
			assetContainer = loader;
			if (this.contains(regularAssetContainer)){
				if(this.asset && regularAssetContainer.contains(this.asset)){
					regularAssetContainer.removeChild(this.asset);
				}
				removeChild(regularAssetContainer);
				regularAssetContainer.mask = null;
			}
			addChild(assetContainer);
			bringToBottom(assetContainer);
			applyMaskAsset();
		}
		this.asset = asset;
		storeOriginalScale();
		setLoaded(asset != null);
		resetAsset();
	}
	
	/**
	 * Load the asset.
	 * <p>The asset of the JLoadPane will only be available after load completed. It mean 
	 * <code>getAsset()</code> will return null before load completed.</p>
	 * @param request The absolute or relative URL of the SWF, JPEG, GIF, or PNG file to be loaded. 
	 * 		A relative path must be relative to the main SWF file. Absolute URLs must include 
	 * 		the protocol reference, such as http:// or file:///. Filenames cannot include disk drive specifications. 
	 * @param context (default = null) — A LoaderContext object.
	 * @see flash.display.Loader#load()
	 */
	public void  load (URLRequest request ,LoaderContext context =null ){
		this.urlRequest = request;
		this.context = context;
		loadAsset();
	}
	
	/**
	 * unload the loaded asset;
	 */ 
	 public void  unloadAsset (){
		this.urlRequest = null;
		this.context = null;
		if(assetContainer == loader){
			loader.unload();
			this.asset = null;
			setLoaded(false);
			resetAsset();
		}else{
			super.unloadAsset();
		}
	}
	
	/**
	 * return the path of image/animation file
	 * @return the path of image/animation file
	 */ 
	public URLRequest  getURLRequest (){
		return urlRequest;
	}	
	
	/**
	 * Re load the asset from with last url request and context.
	 */
	 public void  reload (){
		loadAsset();
	}
	
	/**
	 * Returns is error loaded.
	 * @see #ON_LOAD_ERROR
	 */
	public boolean  isLoadedError (){
		return loadedError;
	}
	
	protected void  loadAsset (){
		if(urlRequest != null){
			loadedError = false;
			setLoaded(false);
			loader.load(urlRequest, context);
		}
	}
	
	protected Loader  createLoader (){
		loader = new Loader();
		loader.contentLoaderInfo.addEventListener(Event.COMPLETE, __onLoadComplete);
		loader.contentLoaderInfo.addEventListener(Event.INIT, __onLoadInit);
		loader.contentLoaderInfo.addEventListener(Event.OPEN, __onLoadStart);
		loader.contentLoaderInfo.addEventListener(Event.UNLOAD, __onUnload);
		loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, __onLoadHttpStatus);
		loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, __onLoadError);	
		loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, __onLoadProgress);
		return loader;
	}
	
	/**
	 * Returns a object contains <code>bytesLoaded</code> and <code>bytesTotal</code> 
	 * properties that indicate the current loading status.
	 */
	public ProgressEvent  getProgress (){
		return new ProgressEvent(ProgressEvent.PROGRESS, false, false, 
			loader.contentLoaderInfo.bytesLoaded, 
			loader.contentLoaderInfo.bytesTotal);
	}
	
	public LoaderInfo  getAssetLoaderInfo (){
		return loader.contentLoaderInfo;
	}
	
	public Loader  getLoader (){
		return loader;
	}
	
	//-----------------------------------------------

	private void  __onLoadComplete (Event e ){
		setLoadedAsset(loader.content);
		dispatchEvent(new Event(Event.COMPLETE));
	}
	
	private void  __onLoadError (IOErrorEvent e ){
		loadedError = true;
		setLoadedAsset(loader.content);
		dispatchEvent(new IOErrorEvent(IOErrorEvent.IO_ERROR, false, false, e.toString()));
	}
	
	private void  __onLoadInit (Event e ){
		dispatchEvent(new Event(Event.INIT));
	}
	
	private void  __onLoadProgress (ProgressEvent e ){
		dispatchEvent(new ProgressEvent(ProgressEvent.PROGRESS, false, false, e.bytesLoaded, e.bytesTotal));
	}
	
	private void  __onLoadStart (Event e ){
		dispatchEvent(new Event(Event.OPEN));
	}
	
	private void  __onUnload (Event e ){
		dispatchEvent(new Event(Event.UNLOAD));
	}
	
	private void  __onLoadHttpStatus (HTTPStatusEvent e ){
		dispatchEvent(new HTTPStatusEvent(HTTPStatusEvent.HTTP_STATUS,false,false,e.status));		
	}
}


