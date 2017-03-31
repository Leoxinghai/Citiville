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

import org.aswing.event.*;
import org.aswing.util.Timer;
import flash.events.Event;
import org.aswing.plaf.basic.BasicProgressBarUI;

/**
 * Dispatched when the scrollBar's state changed. 
 * @see BoundedRangeModel
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A component that, by default, displays an integer value within a bounded 
 * interval. A progress bar typically communicates the progress of some 
 * work by displaying its percentage of completion and possibly a textual
 * display of this percentage.
 * @author iiley
 */
public class JProgressBar extends Component implements Orientable{

    /** 
     * Horizontal orientation.
     */
    public static  int HORIZONTAL =AsWingConstants.HORIZONTAL ;
    /** 
     * Vertical orientation.
     */
    public static  int VERTICAL =AsWingConstants.VERTICAL ;
	
	private int orientation ;
	private boolean indeterminate ;
	private String string ;
	private BoundedRangeModel model ;
	private Timer indeterminatePaintTimer ;
	private boolean indeterminateDelaySet ;
	
	/**
	 * JProgressBar(orient:int, min:int, max:int)<br>
	 * JProgressBar(orient:int)<br>
	 * JProgressBar()
	 * <p>
	 * @param orient (optional)the desired orientation of the progress bar, 
	 *  just can be <code>JProgressBar.HORIZONTAL</code> or <code>JProgressBar.VERTICAL</code>,
	 *  default is <code>JProgressBar.HORIZONTAL</code>
	 * @param min (optional)the minimum value of the progress bar, default is 0
	 * @param max (optional)the maximum value of the progress bar, default is 100
	 */
	public  JProgressBar (int orient =AsWingConstants .HORIZONTAL ,int min =0,int max =100){
		super();
		setName("ProgressBar");
		
		orientation = orient;
		model = new DefaultBoundedRangeModel(min, 0, min, max);
		addListenerToModel();
		
		indeterminate = false;
		string = null;
		
		indeterminateDelaySet = false;
		indeterminatePaintTimer = new Timer(40);
		indeterminatePaintTimer.addActionListener(__indeterminateInterval);
		addEventListener(Event.ADDED_TO_STAGE, __progressAddedToStage);
		addEventListener(Event.REMOVED_FROM_STAGE, __progressRemovedFromStage);
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicProgressBarUI;
    }
	
	 public String  getUIClassID (){
		return "ProgressBarUI";
	} 
	
	public void  setIndeterminateDelay (int delay ){
		indeterminatePaintTimer.setDelay(delay);
		setIndeterminateDelaySet(true);
	}
	
	public int  getIndeterminateDelay (){
		return indeterminatePaintTimer.getDelay();
	}
	
	public void  setIndeterminateDelaySet (boolean b ){
		indeterminateDelaySet = b;
	}
	
	public boolean  isIndeterminateDelaySet (){
		return indeterminateDelaySet;
	}
	    
	/**
     * Returns the data model used by this progress bar.
     *
     * @return the <code>BoundedRangeModel</code> currently in use
     * @see    org.aswing.BoundedRangeModel
     */
	public BoundedRangeModel  getModel (){
		return model;
	}
	
    /**
     * Sets the data model used by the <code>JProgressBar</code>.
     *
     * @param  newModel the <code>BoundedRangeModel</code> to use
     */
	public void  setModel (BoundedRangeModel newModel ){
		if (model != null){
			model.removeStateListener(__onModelStateChanged);
		}
		model = newModel;
		if (model != null){
			addListenerToModel();
		}
	}
	
    /**
     * Returns the current value of the progress string.
     * @return the value of the progress string
     * @see    #setString
     */
	public String  getString (){
		return string;
	}

    /**
     * Sets the value of the progress string. By default,
     * this string is <code>null</code>, will paint nothing text.
     * @param  s the value of the progress string
     * @see    #getString()
     */
	public void  setString (String s ){
		if(string != s){
			string = s;
			repaint();
		}
	}

    /**
     * Returns <code>JProgressBar.VERTICAL</code> or 
     * <code>JProgressBar.HORIZONTAL</code>, depending on the orientation
     * of the progress bar. The default orientation is 
     * <code>HORIZONTAL</code>.
     *
     * @return <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @see #setOrientation()
     */
	public int  getOrientation (){
		return orientation;
	}
	
    /**
     * Sets the progress bar's orientation to <code>newOrientation</code>, 
     * which must be <code>JProgressBar.VERTICAL</code> or 
     * <code>JProgressBar.HORIZONTAL</code>. The default orientation 
     * is <code>HORIZONTAL</code>.
     * <p>
     * Note that If the orientation is set to <code>VERTICAL</code>,
     *  the progress string can only be displayable when the progress bar's font 
     *  is a embedFonts.
     * 
     * @param  newOrientation  <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @see #getOrientation()
     * @see org.aswing.ASFont#getEmbedFonts()
     */
	public void  setOrientation (int newOrientation ){
		if(newOrientation != HORIZONTAL && newOrientation!= VERTICAL){
			newOrientation = HORIZONTAL;
		}
		if(orientation != newOrientation){
			orientation = newOrientation;
			revalidate();
			repaint();
		}
	}
	
    /**
     * Returns the percent complete for the progress bar.
     * Note that this number is between 0.0 and 1.0.
     *
     * @return the percent complete for this progress bar
     */
    public double  getPercentComplete (){
		int span =model.getMaximum ()-model.getMinimum ();
		int currentValue =model.getValue ();
		double pc =(currentValue -model.getMinimum ())/span ;
		return pc;
    }
    
    /**
     * Returns the progress bar's current value,
     * which is stored in the progress bar's <code>BoundedRangeModel</code>.
     * The value is always between the 
     * minimum and maximum values, inclusive. By default, the 
     * value is initialized to be equal to the minimum value.
     *
     * @return  the current value of the progress bar
     * @see     #setValue()
     * @see     org.aswing.BoundedRangeModel#getValue()
     */
	public int  getValue (){
		return getModel().getValue();
	}
    /**
     * Returns the progress bar's minimum value,
     * which is stored in the progress bar's <code>BoundedRangeModel</code>.
     * By default, the minimum value is <code>0</code>.
     *
     * @return  the progress bar's minimum value
     * @see     #setMinimum()
     * @see     org.aswing.BoundedRangeModel#getMinimum()
     */	
	public int  getMinimum (){
		return getModel().getMinimum();
	}
	/**
     * Returns the progress bar's maximum value,
     * which is stored in the progress bar's <code>BoundedRangeModel</code>.
     * By default, the maximum value is <code>100</code>.
     *
     * @return  the progress bar's maximum value
     * @see     #setMaximum()
     * @see     org.aswing.BoundedRangeModel#getMaximum()
     */
	public int  getMaximum (){
		return getModel().getMaximum();
	}
    /**
     * Sets the progress bar's current value 
     * (stored in the progress bar's data model) to <code>n</code>.
     * The data model (a <code>BoundedRangeModel</code> instance)
     * handles any mathematical
     * issues arising from assigning faulty values.
     * <p>
     * If the new value is different from the previous value,
     * all change listeners are notified.
     *
     * @param   n       the new value
     * @see     #getValue()
     * @see    #addChangeListener()
     * @see     org.aswing.BoundedRangeModel#setValue()
     */	
	public void  setValue (int n ){
		getModel().setValue(n);
	}
    /**
     * Sets the progress bar's minimum value 
     * (stored in the progress bar's data model) to <code>n</code>.
     * The data model (a <code>BoundedRangeModel</code> instance)
     * handles any mathematical
     * issues arising from assigning faulty values.
     * <p>
     * If the minimum value is different from the previous minimum,
     * all change listeners are notified.
     *
     * @param  n       the new minimum
     * @see    #getMinimum()
     * @see    #addChangeListener()
     * @see    org.aswing.BoundedRangeModel#setMinimum()
     */	
	public void  setMinimum (int n ){
		getModel().setMinimum(n);
	}
    /**
     * Sets the progress bar's maximum value
     * (stored in the progress bar's data model) to <code>n</code>.
     * The underlying <code>BoundedRangeModel</code> handles any mathematical
     * issues arising from assigning faulty values.
     * <p>
     * If the maximum value is different from the previous maximum,
     * all change listeners are notified.
     *
     * @param  n       the new maximum
     * @see    #getMaximum()
     * @see    #addChangeListener()
     * @see    org.aswing.BoundedRangeModel#setMaximum()
     */	
	public void  setMaximum (int n ){
		getModel().setMaximum(n);
	}
    /**
     * Sets the <code>indeterminate</code> property of the progress bar,
     * which determines whether the progress bar is in determinate
     * or indeterminate mode.
     * An indeterminate progress bar continuously displays animation
     * indicating that an operation of unknown length is occurring.
     * By default, this property is <code>false</code>.
     * <p>
     * An indeterminate progress bar will start a <code>Timer</code> to 
     * call repaint continuously when it is displayable, it make the progress can paint continuously.
     * Make sure the current <code>Icon</code> for this bar support indeterminate 
     * if you set indeterminate to true.
     * <p>
     * @param newValue	<code>true</code> if the progress bar
     * 			should change to indeterminate mode;
     * 			<code>false</code> if it should revert to normal.
     *
     * @see #isIndeterminate()
     */	
	public void  setIndeterminate (boolean newValue ){
		indeterminate = newValue;
		__validateIndeterminateIntervalIfNecessary();
	}
    /**
     * Returns the value of the <code>indeterminate</code> property.
     *
     * @return the value of the <code>indeterminate</code> property
     * @see    #setIndeterminate()
     */	
	public boolean  isIndeterminate (){
		return indeterminate;
	}
	
	//------------------
	    
	private void  addListenerToModel (){
		model.addStateListener(__onModelStateChanged);		
	}
	
	private void  __progressAddedToStage (Event e ){
		__validateIndeterminateIntervalIfNecessary();
	}
	
	private void  __progressRemovedFromStage (Event e ){
		__validateIndeterminateIntervalIfNecessary();
	}
	
	private void  __onModelStateChanged (InteractiveEvent event ){
		repaint();
	}
	
	private void  __indeterminateInterval (AWEvent e ){
		repaint();
	}
	
	private void  __validateIndeterminateIntervalIfNecessary (){
		if(isIndeterminate() && isOnStage()){
			if(!indeterminatePaintTimer.isRunning()){
				indeterminatePaintTimer.start();
			}
		}else{
			if(indeterminatePaintTimer.isRunning()){
				indeterminatePaintTimer.stop();
			}
		}
	}	
}


