
package org.aswing.border;

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
import org.aswing.Border;
import org.aswing.geom.IntRectangle;
import org.aswing.Component;
import org.aswing.Insets;
import flash.display.DisplayObject;
import flash.display.Sprite;
import org.aswing.error.ImpMissError;

/**
 * DecorateBorder make your border can represented as many border arounded.
 * DecorateBorder is a abstract class, you need to inherit it to implement your 
 * real decrator border.
 * <p>
 *<b >You Note should only need to </ b >
 * <ul>
 * <li><code>getDisplayImp</code></li>
 * <li><code>updateBorderImp</code></li>
 * <li><code>getBorderInsetsImp</code></li>
 * </ul>
 * methods in sub-class.
 * </p>
 * @author iiley
 */
public class DecorateBorder implements Border{
		
	private Border interior ;
	private Sprite disContainer ;
	
	public  DecorateBorder (Border interior ){
		this.interior = interior;
	}
	
	/**
	 * Sets new interior border.
	 * @param interior the new interior border
	 */
	public void  setInterior (Border interior ){
		this.interior = interior;	
	}

	/**
	 * Returns current interior border.
	 * @return current interior border
	 */
	public Border  getInterior (){
		return interior;	
	}
	
	/**
	 * Override this method in sub-class to return the display object if needed.
	 * @return a display object, or null, do not need a display object.
	 */
	public DisplayObject  getDisplayImp (){
		return null;
	}
	
	/**
	 * Override this method in sub-class to draw border on the specified mc.
	 * @param c the component for which this border is being painted 
	 * @param g the paint graphics
	 * @param bounds the bounds of border
	 */
	public void  updateBorderImp (Component com ,Graphics2D g ,IntRectangle bounds ){
    	throw new ImpMissError();
	}
	
    /**
     *You should  this method to count this border 's insets.
     * @see #getBorderInsets
     */
    public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	throw new ImpMissError();
    	return new Insets();
    }
	
    /**
     *You should  this method to return the display object .
     * @see #getDisplayImp()
     */	   
	final public DisplayObject  getDisplay (Component c )
	{
		Border inter =getInterior ();
		if(inter != null){
			DisplayObject interDis =inter.getDisplay(c );
			DisplayObject selfDis =getDisplayImp ();
			if(interDis == null){
				return selfDis;
			}else if(selfDis == null){
				return interDis;
			}else{
				if(disContainer == null){
					disContainer = new Sprite();
					disContainer.addChild(selfDis);
					disContainer.addChild(interDis);
				}
				return disContainer;
			}
		}else{
			return getDisplayImp();
		}
	}
	
	/**
	 * call <code>super.paintBorder</code> paint the border first and then 
	 * paint the interior border on the interior bounds.
	 * <br>
	 *subclass Note should not  this method ,should  paintBorderImp .
	 * @see #paintBorderImp
	 */
	final public void  updateBorder (Component c ,Graphics2D g ,IntRectangle bounds ){
    	updateBorderImp(c, g, bounds);
    	//then paint interior border
    	if(getInterior() != null){
    		IntRectangle interiorBounds =getBorderInsetsImp(c ,bounds ).getInsideBounds(bounds );
    		getInterior().updateBorder(c, g, interiorBounds);
    	}
    }
    

    /**
     * Returns the insets of the border.<br>
     *subclass Note should not  this method ,should  getBorderInsetsImp .
     * @see #getBorderInsetsImp
     * @param c the component for which this border insets value applies
     * @param bounds the bounds of the border would paint in.
     */
    final public Insets  getBorderInsets (Component c ,IntRectangle bounds ){
    	Insets insets =getBorderInsetsImp(c ,bounds );
    	if(getInterior() != null){
    		IntRectangle interiorBounds =insets.getInsideBounds(bounds );
    		insets.addInsets(getInterior().getBorderInsets(c, interiorBounds));
    	}
    	return insets;
    }	
}


