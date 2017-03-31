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
import flash.display.Shape;

import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.*;
import org.aswing.error.ImpMissError;
import flash.display.Sprite;
import flash.display.DisplayObjectContainer;

/**
 * Abstract class for A container with a decorative asset.
 * <p>
 * External content will be load automatically when the pane was created if floorEnabled.
 * </p>
 * @see org.aswing.JLoadPane
 * @see org.aswing.JAttachPane
 * @author iiley
 */	
public class AssetPane extends Container{
	
	/**
	 * preffered size of this component will be the fit to contain both size of extenal image/animation
	 *  and counted from <code>LayoutManager</code>
	 */
	public static  int PREFER_SIZE_BOTH =0;
	/**
	 * preffered size of this component will be the size of extenal image/animation
	 */
	public static  int PREFER_SIZE_IMAGE =1;
	/**
	 * preffered size of this component will be counted by <code>LayoutManager</code>
	 */	
	public static  int PREFER_SIZE_LAYOUT =2;
	
	/**
	 * Image scale mode is disabled.
	 */
	public static  int SCALE_NONE =0;
	/**
	 * Proportional scale mode to fit pane.
	 */
	public static  int SCALE_FIT_PANE =1;
	/**
	 * Stretch content to fill whole pane.
	 */
	public static  int SCALE_STRETCH_PANE =2;
	/**
	 * Proportional image scale mode to fit pane's width.
	 */
	public static  int SCALE_FIT_WIDTH =3;
	/**
	 * Proportional scale mode to fit pane's height.
	 */
	public static  int SCALE_FIT_HEIGHT =4;
	/**
	 * Custom scaling of the image.
	 * @see setCustomScale
	 */
	public static  int SCALE_CUSTOM =5;
	
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int CENTER =AsWingConstants.CENTER ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int TOP =AsWingConstants.TOP ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int LEFT =AsWingConstants.LEFT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int BOTTOM =AsWingConstants.BOTTOM ;
 	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int RIGHT =AsWingConstants.RIGHT ;
    
	protected DisplayObject asset ;
	protected DisplayObjectContainer assetContainer ;
	protected Shape assetMask ;
	
	private boolean assetVisible ;
	private boolean maskFloor ;
	private boolean floorLoaded ;
	private int prefferSizeStrategy ;
    private int verticalAlignment ;
    private int horizontalAlignment ;
    private int scaleMode ;
    private int customScale ;
    private int actualScale ;
    private IntDimension floorOriginalSize ;
    private double floorOriginalScaleX ;
    private double floorOriginalScaleY ;
    private boolean hadscaled ;
    private double offsetX ;
    private double offsetY ;
	
	/**
	 * AssetPane(path:String, prefferSizeStrategy:int) <br>
	 * AssetPane(path:String) prefferSizeStrategy default to PREFER_SIZE_BOTH<br>
	 * AssetPane() path default to null,prefferSizeStrategy default to PREFER_SIZE_IMAGE
	 * <p>
	 * Creates a AssetPane with a path to load external content.
	 * </p>
	 * @param asset the asset to be placed on this pane.
	 * @param prefferSizeStrategy the prefferedSize count strategy. Must be one of below:
	 * <ul>
	 * <li>{@link #PREFER_SIZE_BOTH}
	 * <li>{@link #PREFER_SIZE_IMAGE}
	 * <li>{@link #PREFER_SIZE_LAYOUT}
	 * </ul>
	 * @see #setAsset()
	 */
	public  AssetPane (DisplayObject asset =null ,int prefferSizeStrategy =PREFER_SIZE_IMAGE ){
		super();
		this.prefferSizeStrategy = prefferSizeStrategy;
		
    	verticalAlignment = TOP;
    	horizontalAlignment = LEFT;
    	scaleMode = SCALE_NONE;
    	actualScale = 100;
    	customScale = 100;
    	hadscaled = false;
    	maskFloor = true;
		floorOriginalSize = null;
		assetVisible = true;
		floorLoaded = false;
		offsetX = 0;
		offsetY = 0;
		floorOriginalScaleX = 1;
		floorOriginalScaleY = 1;
		setFocusable(false);
		assetContainer = AsWingUtils.createSprite(this, "assetContainer");
		assetMask = AsWingUtils.createShape(this, "assetMask");
		assetMask.graphics.beginFill(0xFF0000);
		assetMask.graphics.drawRect(0, 0, 1, 1);
		assetMask.visible = false;
		setAsset(asset);
	}
	
	
	/**
	 * set the asset of the pane
	 * This method will cause old asset to be removed and new asset to be added.
	 * @param asset the asset of the pane.
	 */ 
	public void  setAsset (DisplayObject asset ){
		if (this.asset != asset){
			if(this.asset){
				if(this.asset.parent == assetContainer){
					assetContainer.removeChild(this.asset);
				}
			}
			this.asset = asset;
			if(asset){
				storeOriginalScale();
				assetContainer.addChild(asset);
			}
			setLoaded(asset != null);
			resetAsset();
		}
	}
	
	/**
	 * unload the asset of the pane
	 */ 
	public void  unloadAsset (){
		setAsset(null);
	}
	
	protected void  storeOriginalScale (){
		if(asset){
			floorOriginalScaleX = asset.scaleX;
			floorOriginalScaleY = asset.scaleY;
		}
	}
	
	protected void  resetAsset (){
		if (asset){
			asset.scaleX = floorOriginalScaleX;
			asset.scaleY = floorOriginalScaleY;
			setAssetOriginalSize(new IntDimension(asset.width, asset.height));
			asset.visible = assetVisible;
		}
		revalidate();
	}
	
	/**
	 * Returns the asset of the pane.
	 * <p>
	 * You should take care to do operation at this display object, 
	 * if you want to remove it, you should call <code>setAsset(null)</code> instead of 
	 * call <code>asset.parent.removeChild(asset)</code>;
	 * </p>
	 * @return the asset.
	 */
	public DisplayObject  getAsset (){
		return asset;
	}
	
	/**
	 * Sets the preffered size counting strategy. Must be one of below:
	 * <ul>
	 * <li>{@link #PREFER_SIZE_BOTH}
	 * <li>{@link #PREFER_SIZE_IMAGE}
	 * <li>{@link #PREFER_SIZE_LAYOUT}
	 * </ul>
	 */
	public void  setPrefferSizeStrategy (double p ){
		prefferSizeStrategy = p;
	}
	
	/**
	 * Returns the preffered size counting strategy.
	 * @see #setPrefferSizeStrategy()
	 */
	public double  getPrefferSizeStrategy (){
		return prefferSizeStrategy;
	}	
	
    /**
     * Returns the vertical alignment of the image/animation.
     *
     * @return the <code>verticalAlignment</code> property, one of the
     *		following values: 
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public double  getVerticalAlignment (){
        return verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment of the image/animation. 
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     * Default is CENTER.
     */
    public void  setVerticalAlignment (int alignment =AsWingConstants .CENTER ){
        if (alignment == verticalAlignment){
        	return;
        }else{
        	verticalAlignment = alignment;
        	revalidate();
        }
    }
    
    /**
     * Returns the horizontal alignment of the image/animation.
     * @return the <code>horizontalAlignment</code> property,
     *		one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     * Default is LEFT.
     */
    public double  getHorizontalAlignment (){
        return horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment of the image/animation.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)
     * <li>AsWingConstants.LEFT
     * <li>AsWingConstants.CENTER
     * </ul>
     */
    public void  setHorizontalAlignment (int alignment =AsWingConstants .RIGHT ){
        if (alignment == horizontalAlignment){
        	return;
        }else{
        	horizontalAlignment = alignment;
        	revalidate();
        }
    }
    
    /**
     * Sets new content scale mode.
     * <p><b>Note:</b>Take care to use #scaleMode to load a swf, 
     * because swf has different size at different frame or
     * when some symbol invisible/visible.
     * @param mode the new image scale mode.
	 * <ul>
	 * <li>{@link #SCALE_NONE}
	 * <li>{@link #SCALE_PROPORTIONAL}
	 * <li>{@link #SCALE_COMPLETE}
	 * </ul>
     */
    public void  setScaleMode (int mode ){
    	if(scaleMode != mode){
    		scaleMode = mode;
    		revalidate();
    	}
    }
    
    /**
     * Returns current image scale mode. 
     * @return current image scale mode.
	 * <ul>
	 * <li>{@link #SCALE_NONE}
	 * <li>{@link #SCALE_PROPORTIONAL}
	 * <li>{@link #SCALE_COMPLETE}
	 * </ul>
     */
    public int  getScaleMode (){
    	return scaleMode;
    }
    
    /**
     * Sets new custom scale value in percents. Automatically turns scale mode into #SCALE_CUSTOM.
     * @param scale the new scale 
     * @see #setScaleMode
     */
    public void  setCustomScale (int scale ){
    	setScaleMode(SCALE_CUSTOM);
    	if (customScale != scale) {
    		customScale = scale;
    		revalidate();	
    	}
    }
    
    /**
     * Returns current actual scale value in percents. If <code>scaleMode</code> is
     * #SCALE_STRETCH_PANE returns <code>null</code>. 
     */
    public int  getActualScale (){
    	return actualScale;	
    }

    /**
     * Returns current custom scale value in percents.
     */
    public int  getCustomScale (){
    	return customScale;	
    }
    
    /**
     * Sets the x offset of the position of the loaded image/animation.
     * If you dont want to locate the content to the topleft of the pane, you can set the offsets.
     * @param offset the x offset 
     */
    public void  setOffsetX (double offset ){
    	if(offsetX != offset){
    		offsetX = offset;
    		revalidate();
    	}
    }
    
    /**
     * Sets the y offset of the position of the loaded image/animation.
     * If you dont want to locate the content to the topleft of the pane, you can set the offsets.
     * @param offset the y offset 
     */
    public void  setOffsetY (double offset ){
    	if(offsetY != offset){
    		offsetY = offset;
    		revalidate();
    	}
    }    
    
    /**
     * @see #setOffsetX()
     */
    public double  getOffsetX (){
    	return offsetX;
    }
    
    /**
     * @see #setOffsetY()
     */
    public double  getOffsetY (){
    	return offsetY;
    }
	
	/**
	 * Sets the visible of the assets.
	 * @param b the visible property.
	 */
	public void  setAssetVisible (boolean b ){
		assetVisible = b;
		if(asset){
			asset.visible = b;
		}
	}
	
	/**
	 * Returns the asset visible property.
	 * @return the asset visible property.
	 */
	public boolean  isAssetVisible (){
		return assetVisible;
	}
	
	/**
	 * Returns is the asset was loaded ok.
	 * @return true if the asset loaded ok, otherwise return false
	 */
	public boolean  isLoaded (){
		return floorLoaded;
	}
	
	/**
	 * Returns the asset's original size.
	 * If the asset are not loaded yet, return null.
	 * @return the asset original size. null if it is not loaded yet.
	 */
	public IntDimension  getAssetOriginalSize (){
		if(isLoaded()){
			return floorOriginalSize;
		}else{
			return null;
		}
	}
	
	/**
	 * layout this container
	 */
	 public void  doLayout (){
		super.doLayout();
		fitImage();
	}	
	
	private void  fitImage (){
		if(isLoaded()){
			// for child classes which redefines floorMC
			DisplayObject floor =getAsset ();
			IntRectangle b =getPaintBounds ();
			IntDimension s =countFloorSize ();
			assetMask.x = b.x;
			assetMask.y = b.y;
			assetMask.width = b.width;
			assetMask.height = b.height;
			if(scaleMode == SCALE_STRETCH_PANE){
				floor.x = b.x - offsetX;
				floor.y = b.y - offsetY;
				floor.width = s.width;
				floor.height = s.height;
				hadscaled = true;
			} else if (scaleMode == SCALE_FIT_PANE || scaleMode == SCALE_FIT_WIDTH || scaleMode == SCALE_FIT_HEIGHT || scaleMode == SCALE_CUSTOM) {
				floor.width = s.width;
				floor.height = s.height;
				alignFloor();
				hadscaled = true;
			}else{
				if(hadscaled){
					if(floor.width != floorOriginalSize.width){
						floor.width = floorOriginalSize.width;
					}
					if(floor.height != floorOriginalSize.height){
						floor.height = floorOriginalSize.height;
					}
					hadscaled = false;
				}
				alignFloor();
			}
			// calc current scale
			if (scaleMode != SCALE_STRETCH_PANE) {
				actualScale = Math.floor(floor.width / floorOriginalSize.width * 100);
			} else {
				actualScale = 0;
			}
			setMaskAsset(maskFloor);
		}
	}
	
	/**
	 * Aligns floorMC clip. 
	 */
	private void  alignFloor (IntRectangle b =null ){
		// for child classes which redefines floorMC
		DisplayObject floorMC =getAsset ();
		if (b == null) b = getPaintBounds();
		
		double mx ,my double ;
		if(horizontalAlignment == CENTER){
			mx = b.x + (b.width - floorMC.width)/2;
		}else if(horizontalAlignment == RIGHT){
			mx = b.x + (b.width - floorMC.width);
		}else{
			mx = b.x;
		}
		if(verticalAlignment == CENTER){
			my = b.y + (b.height - floorMC.height)/2;
		}else if(verticalAlignment == BOTTOM){
			my = b.y + (b.height - floorMC.height);
		}else{
			my = b.y;
		}
		floorMC.x = mx - offsetX;
		floorMC.y = my - offsetY;
	}	
	
	/**
	 * count preffered size base on prefferSizeStrategy.
	 */
	 protected IntDimension  countPreferredSize (){
		IntDimension size =super.countPreferredSize ();
		IntDimension sizeByMC ;
		if(isLoaded()){
			sizeByMC = countFloorSize();
			sizeByMC = getInsets().getOutsideSize(sizeByMC);
		}else{
			sizeByMC = size;
		}
		
		if(prefferSizeStrategy == PREFER_SIZE_IMAGE){
			return sizeByMC;
		}else if(prefferSizeStrategy == PREFER_SIZE_LAYOUT){
			return size;
		}else{
			return new IntDimension(
				Math.max(sizeByMC.width, size.width), 
				Math.max(sizeByMC.height, size.height));
		}
	}	
	
	private IntDimension  countFloorSize (){
		IntRectangle b =getPaintBounds ();
		IntDimension size =new IntDimension ();
		
		if(scaleMode == SCALE_STRETCH_PANE){
			size.width = b.width;
			size.height = b.height;
		} else if (scaleMode == SCALE_FIT_PANE || scaleMode == SCALE_FIT_WIDTH || scaleMode == SCALE_FIT_HEIGHT) {
			double hScale =floorOriginalSize.width /b.width ;
			double vScale =floorOriginalSize.height /b.height ;
			double scale =1;
			if (scaleMode == SCALE_FIT_WIDTH) {
				scale = hScale;
			} else if (scaleMode == SCALE_FIT_HEIGHT) {
				scale = vScale;
			} else {
				scale = Math.max(hScale, vScale);
			}
			size.width = floorOriginalSize.width/scale;
			size.height = floorOriginalSize.height/scale;
		} else if (scaleMode == SCALE_CUSTOM){
			size.width = floorOriginalSize.width*(customScale/100);
			size.height = floorOriginalSize.height*(customScale/100);
		} else {
			size.width = floorOriginalSize.width - offsetX;
			size.height = floorOriginalSize.height - offsetY;
		}
		
		return size; 
	}
	
	/**
	 * Reload the asset. This will reset the asset to count currenty size and re-layout.
	 * @see org.aswing.JLoadPane
	 * @see org.aswing.JAttachPane
	 */
	public void  reload (){
		resetAsset();
	}
	
	public boolean  isMaskAsset (){
		return maskFloor;
	}
	
	public void  setMaskAsset (boolean m ){
		maskFloor = m;
		applyMaskAsset();
	}
	
	protected void  applyMaskAsset (){
		if(maskFloor){
			assetMask.visible = true;
			assetContainer.mask = assetMask;
		}else{
			assetContainer.mask = null;
			assetMask.visible = false;
		}
	}
	
	protected void  setLoaded (boolean b ){
		floorLoaded = b;
	}
	
	protected void  setAssetOriginalSize (IntDimension size ){
		floorOriginalSize = new IntDimension(size.width, size.height);
	}
	
	//////////////////////
	
	/**
	 * load the floor content.
	 * <p> here it is empty.
	 *Subclass must  this method to make loading .
	 */
	protected void  loadFloor (){

	}
	
        public void  clearMask ()
        {
            if (this.assetMask)
            {
                this.assetMask.visible = false;
                int _loc_1 =0;
                this.assetMask.width = 0;
                this.assetMask.height = _loc_1;
            }
            return;
        }//	
	
}


