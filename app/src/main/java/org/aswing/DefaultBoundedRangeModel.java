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

	
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;
import org.aswing.event.InteractiveEvent;

/**
 * The default implementation of BoundedRangeModel.
 * @author iiley
 */
public class DefaultBoundedRangeModel extends EventDispatcher implements BoundedRangeModel{
	
	private int value ;
	private int extent ;
	private int min ;
	private int max ;
	private boolean isAdjusting ;
	
	/**
	 * Create a DefaultBoundedRangeModel
	 * @throws RangeError when invalid range properties
	 */
	public  DefaultBoundedRangeModel (int value =0,int extent =0,int min =0,int max =100){
		isAdjusting = false;
		
		if (max >= min && value >= min && value + extent >= value && value + extent <= max){
			this.value = value;
			this.extent = extent;
			this.min = min;
			this.max = max;
		}else{
			throw new RangeError("invalid range properties");
		}
	}
	
	public int  getValue (){
		return value;
	}
	
	public int  getExtent (){
		return extent;
	}
	
	public int  getMinimum (){
		return min;
	}
	
	public int  getMaximum (){
		return max;
	}
	
	public void  setValue (int n ,boolean programmatic =true ){
		n = Math.min(n, max - extent);
		int newValue =Math.max(n ,min );
		setRangeProperties(newValue, extent, min, max, isAdjusting, programmatic);
	}
	
	public void  setExtent (int n ){
		int newExtent =Math.max(0,n );
		if (value + newExtent > max){
			newExtent = max - value;
		}
		setRangeProperties(value, newExtent, min, max, isAdjusting);
	}
	
	public void  setMinimum (int n ){
		int newMax =Math.max(n ,max );
		int newValue =Math.max(n ,value );
		int newExtent =Math.min(newMax -newValue ,extent );
		setRangeProperties(newValue, newExtent, n, newMax, isAdjusting);
	}
	
	public void  setMaximum (int n ){
		int newMin =Math.min(n ,min );
		int newExtent =Math.min(n -newMin ,extent );
		int newValue =Math.min(n -newExtent ,value );
		setRangeProperties(newValue, newExtent, newMin, n, isAdjusting);
	}
	
	public void  setValueIsAdjusting (boolean b ){
		setRangeProperties(value, extent, min, max, b, false);
	}
	
	public boolean  getValueIsAdjusting (){
		return isAdjusting;
	}
	
	public void  setRangeProperties (int newValue ,int newExtent ,int newMin ,int newMax ,boolean adjusting ,boolean programmatic =true ){
		if (newMin > newMax){
			newMin = newMax;
		}
		if (newValue > newMax){
			newMax = newValue;
		}
		if (newValue < newMin){
			newMin = newValue;
		}
		if (newExtent + newValue > newMax){
			newExtent = newMax - newValue;
		}
		if (newExtent < 0){
			newExtent = 0;
		}
		boolean isChange =newValue != value || newExtent != extent || newMin != min || newMax != max || adjusting != isAdjusting;
		if (isChange){
			value = newValue;
			extent = newExtent;
			min = newMin;
			max = newMax;
			isAdjusting = adjusting;
			fireStateChanged(programmatic);
		}
	}
	
	public void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, listener, false, priority);
	}
	
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}
		
	protected void  fireStateChanged (boolean programmatic ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
	
	 public String  toString (){
		String modelString ="value="+getValue ()+", "+"extent="+getExtent ()+", "+"min="+getMinimum ()+", "+"max="+getMaximum ()+", "+"adj="+getValueIsAdjusting ();
		return "DefaultBoundedRangeModel" + "[" + modelString + "]";
	}
	
}


