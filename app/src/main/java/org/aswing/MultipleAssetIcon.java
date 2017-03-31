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
import flash.display.DisplayObjectContainer;
import flash.display.Shape;

import org.aswing.geom.IntDimension;
import org.aswing.graphics.Graphics2D;
import org.aswing.util.ArrayList;

/**
 * MultipleAssetIcon is a Icon impelmentation that use multiple decorative DisplayObject-s.
 * 
 * @see org.aswing.AssetIcon
 * @see org.aswing.AttachIcon
 * @see org.aswing.LoadIcon
 * 
 * @author srdjan
 */	
public class MultipleAssetIcon implements Icon{
	
	/**
	 * Align asset to horizontal or vertical center position.
	 */
	public static  int CENTER =0;
	
	/**
	 * Align asset to top position.
	 */
	public static  int TOP =1;
	
	/**
	 * Align asset to bottom position.
	 */
	public static  int BOTTOM =2;

	/**
	 * Align asset to left position.
	 */
	public static  int LEFT =3;

	/**
	 * Align asset to right position.
	 */
	 
	public static  int RIGHT =4;
	
	/**
	 * Contains AssetItem objects in order that need to be shown.
	 */
	private ArrayList assets ;
	
	private DisplayObjectContainer assetContainer ;
	private Insets assetGapInsets ;
	
	/**
	 * if relativeMaxSize is used if absoluteSize is not in use
	 */
	private IntDimension relativeMaxSize ;
	
	/**
	 * if absoluteSize is used,  the relativeMaxSize is not in use.
	 */
	private IntDimension absoluteSize ;
	
	/**
	 * Creates a MultipleAssetIcon.
	 * 
	 * @param absoluteSize set the absolute size of a asset. 
	 * If it's not set it will be used size of the biggest 
	 * asset that it's added with <code>addAsset()</code> method.
	 */
	public  MultipleAssetIcon (IntDimension absoluteSize =null ){
		this.absoluteSize = absoluteSize;
		this.relativeMaxSize = new IntDimension(-1,-1);
		assets =  new ArrayList();
		assetContainer = AsWingUtils.createSprite(null, "assetContainer");
		assetGapInsets = new Insets(0,0,0,0);
	}
	
	/**
	 * Add a new asset to asset container. Last added asset will be at the top of all other.
	 * 
	 * @param asset display object to be show.
	 * @param width (optional)if you specifiled the width of the Icon, and scale is true,
	 * 		the mc will be scale to this width when paint. If you do not specified the with, it will use 
	 * 		asset.width.
	 * @param height (optional)if you specifiled the height of the Icon, and scale is true, 
	 * 		the mc will be scale to this height when paint. If you do not specified the height, it will use 
	 * 		asset.height.
	 * @param scale (optional)whether scale MC to fix the width and height specified. Default is true
	 * @param horizontalPosition The horizontal position of asset 
	 * @param verticalPosition The vertical position of asset (affect only if scale is not true and asset size is bigged than asset)
	 *
	 * @see #addAssetItem()
	 */
	public void  addAsset (DisplayObject asset ,double width =-1,double height =-1,boolean scale =false ,int horizontalPosition =-1,int verticalPosition =-1){
		AssetItem assetItem =new AssetItem(asset ,width ,height ,scale ,horizontalPosition ,verticalPosition );
		addAssetItem(assetItem);
	}
	
	/**
	 * Add a new asset to asset container. Last added asset will be at the top of all other.
	 *
	 * @param assetItem
	 * 	
	 *  @see #addAsset()
	 */
	private void  addAssetItem (AssetItem assetItem ){
		
		assets.append(assetItem);
		
		if (assetItem.getWidth()==-1 && assetItem.getHeight()==-1){
			assetItem.setWidth(assetItem.getAsset().width);
			assetItem.setHeight(assetItem.getAsset().height);				
		}
		
		relativeMaxSize.width = Math.max(relativeMaxSize.width, assetItem.getWidth());
		relativeMaxSize.height = Math.max(relativeMaxSize.height, assetItem.getHeight());
		
		Shape maskShape =AsWingUtils.createShape(assetContainer ,"maskShape");
		assetContainer.addChild(assetItem.getAsset());
		assetItem.getAsset().mask = maskShape;
	}
	
	/**
	 * Set  value for a blank space from each one side of asset.
	 * 
	 * @param insets 
	 */
	public void  setGapFromEdge (Insets insets ){
		assetGapInsets = insets;
	}
		
	/**
	 * Return value for a blank space from each one side of asset.
	 * 
	 * @return 
	 */
	public Insets  getGapFromEdge (){
		return assetGapInsets;
	}
	
	/**
	 * Return the number of assets.
	 * 
	 * @return
	 */
	private int  getAssetsCount (){
		return assets.size();
	}
	
	/**
	 * Return the asset item at index position.
	 * 
	 * @return
	 */
	private AssetItem  getAssetItemAt (int index ){
		return assets.elementAt(index);
	}
	
	/**
	 * Clone the MultipleAssetIcon instance
	 * 
	 * @param multipleAssetIcon Source MultipleAssetIcon
	 * @return Cloned MultipleAssetIcon
	 */
	public MultipleAssetIcon  clone (){
		 
		MultipleAssetIcon clonedMultipleAssetIcon =new MultipleAssetIcon(absoluteSize );
		clonedMultipleAssetIcon.setGapFromEdge(getGapFromEdge());
		 	
		for(int i =0;i <assets.size ();i ++){
			clonedMultipleAssetIcon.addAssetItem(getAssetItemAt(i).clone());
		}
			 	
		return clonedMultipleAssetIcon;
	}
	 
	public void  updateIcon (Component c ,Graphics2D g ,int x ,int y ){
		
		assetContainer.x = x+assetGapInsets.left;
		assetContainer.y = y+assetGapInsets.top;
		
		int w =relativeMaxSize.width ;
		int h =relativeMaxSize.height ;
		if(absoluteSize!=null){
		
			w = absoluteSize.width;
			h = absoluteSize.height;
		}
		
		for(int i =0;i <assets.size ();i ++){
			AssetItem assetItem =assets.elementAt(i );
			DisplayObject asset =assetItem.getAsset ();

			// draw mask
			Shape maskShape =Shape(asset.mask );
			maskShape.graphics.clear();
			maskShape.graphics.beginFill(0xFF0000);
			maskShape.graphics.drawRect(0, 0, w, h);
			maskShape.graphics.endFill();
			
			//scale
			if(assetItem.getScale()){
				asset.width = w;
				asset.height = h;
			}
			// set position
			else if(assetItem.getHorizontalPosition()!=-1 && assetItem.getVerticalPosition()!=-1){
				switch(assetItem.getHorizontalPosition()){
					case CENTER:
						asset.x = Math.floor((w - assetItem.getWidth())/2);
					break;
					case RIGHT:
						asset.x = Math.floor(w - assetItem.getWidth());
					break;
					case LEFT:
					default:
						asset.x = 0;
					break;
				}
				switch(assetItem.getVerticalPosition()){
					case CENTER:
						asset.y = Math.floor((h - assetItem.getHeight())/2);
					break;
					case BOTTOM:
						asset.y = Math.floor(h - assetItem.getHeight());
					break;
					case TOP:
					default:
						asset.y = 0;
					break;
				}
			}
		}

	}
	
	public int  getIconHeight (Component c ){
		if(absoluteSize != null){
			return absoluteSize.height + assetGapInsets.getMarginHeight();
		}
		return relativeMaxSize.height + assetGapInsets.getMarginHeight();
	}
	
	public int  getIconWidth (Component c ){
		if(absoluteSize != null){
			return absoluteSize.width + assetGapInsets.getMarginWidth();
		}
		return relativeMaxSize.width + assetGapInsets.getMarginWidth();
	}
	
	public DisplayObject  getDisplay (Component c ){
		return assetContainer;
	}
	 
}


import flash.display.DisplayObject;

/**
 * AssetItem is a holder for asset properties.
 * 
 * @see com.awssoft.fwindows.components.MultipleAssetIcon
 */	
internal class AssetItem{
	
	private int width ;
	private int height ;
	private boolean scale ;
	private DisplayObject asset ;
	private int hPosition ;
	private int vPosition ;
	
	/**
	 * Creates a AssetItem that holds asset properties.
	 * 
	 * @param asset The display object that will be shown.
	 * @param width (optional)if you specifiled the width of the Icon, and scale is true,
	 * 		the mc will be scale to this width when paint. If you do not specified the with, it will use 
	 * 		asset.width.
	 * @param height (optional)if you specifiled the height of the Icon, and scale is true, 
	 * 		the mc will be scale to this height when paint. If you do not specified the height, it will use 
	 * 		asset.height.
	 * @param scale (optional)whether scale MC to fix the width and height specified. Default is true
	 * @param hPosition The horizontal position of asset display object (affect only if scale is not true and asset size is bigged than asset display object)
	 * @param vPosition The vertical position of asset display object (affect only if scale is not true and asset size is bigged than asset display object)
	 */ 
	public  AssetItem (DisplayObject asset =null ,int width =-1,int height =-1,boolean scale =false ,int hPosition =-1,int vPosition =-1){
		this.asset = asset;
		this.scale = scale;
		this.width = width;
		this.height = height;
		this.hPosition=hPosition;
		this.vPosition=vPosition;
	}
	
	/**
	 * Return asset.
	 */
	public DisplayObject  getAsset (){
		return asset;
	}
	
	/**
	 * Return whatever the asset need to be scaled.
	 */
	public boolean  getScale (){
		return scale;
	}
	
	/**
	 * Return asset width.
	 */
	public int  getWidth (){
		return width;
	}
	
	/**
	 * Return asset height.
	 */
	public int  getHeight (){
		return height;
	}
	
	/**
	 * Return hPosition.
	 */
	public int  getHorizontalPosition (){
		return hPosition;
	}
	
	/**
	 * Return vPosition.
	 */
	public int  getVerticalPosition (){
		return vPosition;
	}
	
	/**
	 * Set asset.
	 */
	public void  setAsset (DisplayObject asset ){
		this.asset = asset;
	}
	
	/**
	 * Set whatever the asset need to be scaled.
	 */
	public void  setScale (boolean scale ){
		this.scale=scale;
	}
	
	/**
	 * Set asset width.
	 */
	public void  setWidth (int width ){
		this.width=width;
	}
	
	/**
	 * Set asset height.
	 */
	public void  setHeight (int height ){
		this.height=height;
	}
	
	/**
	 * Set hPosition.
	 */
	public void  setHorizontalPosition (int hPosition ){
		this.hPosition=hPosition;
	}
	
	/**
	 * Set vPosition.
	 */
	public void  setVerticalPosition (int vPosition ){
		this.vPosition=vPosition;
	}
	
	/**
	 * Clone the asset item.
	 * 
	 * @return Clone of this
	 */
	 
	 public AssetItem  clone (){
	 	Class clazz =Object(asset ).constructor ;
	 	DisplayObject clonedAsset =new clazz ();
	 	AssetItem clone =new AssetItem(clonedAsset ,width ,height ,scale ,hPosition ,vPosition );
	 	return clone;
	 }
}


