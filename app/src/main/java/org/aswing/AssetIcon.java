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

	
import org.aswing.graphics.*;
import flash.display.*;
import org.aswing.error.*;

/**
 * Abstract class for A icon with a decorative displayObject.
 * @see org.aswing.AttachIcon
 * @see org.aswing.LoadIcon
 * @author senkay
 * @author iiley
 */	
public class AssetIcon implements Icon{
	
	protected int width ;
	protected int height ;
	protected boolean scale ;
	protected DisplayObject asset ;
	protected DisplayObjectContainer assetContainer ;
	protected Shape maskShape ;
	
	/**
	 * Creates a AssetIcon with a path to load external content.
	 * @param path the path of the external content.
	 * @param width (optional)if you specifiled the width of the Icon, and scale is true,
	 * 		the mc will be scale to this width when paint. If you do not specified the with, it will use 
	 * 		asset.width.
	 * @param height (optional)if you specifiled the height of the Icon, and scale is true, 
	 * 		the mc will be scale to this height when paint. If you do not specified the height, it will use 
	 * 		asset.height.
	 * @param scale (optional)whether scale MC to fix the width and height specified. Default is true
	 */
	public  AssetIcon (DisplayObject asset =null ,int width =-1,int height =-1,boolean scale =false ){
		this.asset = asset;
		this.scale = scale;
		
		if (width==-1 && height==-1){
			if (asset){
				this.width = asset.width;
				this.height = asset.height;				
			}else{
				this.width = 0;
				this.height = 0;
			}
		}else{
			this.width = width;
			this.height = height;
			assetContainer = AsWingUtils.createSprite(null, "assetContainer");
			maskShape = AsWingUtils.createShape(assetContainer, "maskShape");
			maskShape.graphics.beginFill(0xFF0000);
			maskShape.graphics.drawRect(0, 0, width, height);
			maskShape.graphics.endFill();
			if(asset){
				assetContainer.addChild(asset);
				asset.mask = maskShape;
				if(scale){
					asset.width = width;
					asset.height = height;
				}
			}
		}
	}
	
	public DisplayObject  getAsset (){
		return asset;
	}
	
	protected void  setWidth (int width ){
		this.width = width;
	}
	
	protected void  setHeight (int height ){
		this.height = height;
	}
	
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		DisplayObject floor =getDisplay(c );
		if(floor){
			floor.x = x;
			floor.y = y;
		}
	}
	
	public int  getIconHeight (Component c ){
		return height;
	}
	
	public int  getIconWidth (Component c ){
		return width;
	}
	
	public DisplayObject  getDisplay (Component c ){
		if(assetContainer){
			return assetContainer;
		}else{
			return asset;
		}
	}
	
}


