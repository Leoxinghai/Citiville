
package org.aswing.colorchooser;

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

import org.aswing.ASColor;
import org.aswing.colorchooser.ColorSelectionModel;
import org.aswing.colorchooser.DefaultColorSelectionModel;
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.event.*;

/**
 * Dispatched when user adjusting to a new color
 * @eventType org.aswing.event.ColorChooserEvent.COLOR_ADJUSTING
 * @see org.aswing.AbstractButton#addActionListener()
 */
.get(Event(name="colorAdjusting", type="org.aswing.event.ColorChooserEvent"))

/**
 * Dispatched when the color selection changed. 
 * @see ColorSelectionModel
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))
/**
 * @author iiley
 */
public class AbstractColorChooserPanel extends Container {
	private boolean alphaSectionVisible ;
	private boolean hexSectionVisible ;
	private boolean noColorSectionVisible ;
	private ColorSelectionModel model ;
	
	public  AbstractColorChooserPanel (){
		super();
		alphaSectionVisible = true;
		hexSectionVisible   = true;
		noColorSectionVisible = false;
		setModel(new DefaultColorSelectionModel());
	}
	
	/**
	 * Sets the color selection model to this chooser panel.
	 * @param model the color selection model
	 */
	public void  setModel (ColorSelectionModel model ){
		if(model == null) return;
		if(this.model != model){
			uninstallListener(model);
			this.model = model;
			installListener(model);
			repaint();
		}
	}
	
	private void  installListener (ColorSelectionModel model ){
		model.addChangeListener(__modelValueChanged);
		model.addColorAdjustingListener(__colorAdjusting);
	}
	
	private void  uninstallListener (ColorSelectionModel model ){
		model.removeChangeListener(__modelValueChanged);
		model.removeColorAdjustingListener(__colorAdjusting);
	}
	
	private void  __modelValueChanged (InteractiveEvent e ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED));
	}
	private void  __colorAdjusting (ColorChooserEvent e ){
		dispatchEvent(new ColorChooserEvent(ColorChooserEvent.COLOR_ADJUSTING, e.getColor()));
	}
	
	/**
	 * Returns the color selection model of this chooser panel
	 * @return the color selection model of this chooser panel
	 */
	public ColorSelectionModel  getModel (){
		return model;
	}
	
	/**
	 * Sets the color selected, null indicate that no color is selected.
	 * @param c the color to be selected, null indicate no color to be selected.
	 */
	public void  setSelectedColor (ASColor c ){
		getModel().setSelectedColor(c);
	}
	
	/**
	 * Returns the color selected, null will be return if there is no color selected.
	 * @return the color selected, or null.
	 */
	public ASColor  getSelectedColor (){
		return getModel().getSelectedColor();
	}
	
	/**
	 * Sets whether showing the alpha editing section.
	 * <p>
	 * Default value is true.
	 * @param b true to show the alpha editing section, false no.
	 */
	public void  setAlphaSectionVisible (boolean b ){
		if(alphaSectionVisible != b){
			alphaSectionVisible = b;
			repaint();
		}
	}
	
	/**
	 * Returns true if the alpha editing section is shown, otherwise false.
	 * @return true if the alpha editing section is shown, otherwise false.
	 */
	public boolean  isAlphaSectionVisible (){
		return alphaSectionVisible;
	}
	
	/**
	 * Sets whether showing the hex editing section.
	 * <p>
	 * Default value is true.
	 * @param b true to show the hex editing section, false no.
	 */	
	public void  setHexSectionVisible (boolean b ){
		if(hexSectionVisible != b){
			hexSectionVisible = b;
			repaint();
		}
	}
	
	/**
	 * Returns true if the hex editing section is shown, otherwise false.
	 * @return true if the hex editing section is shown, otherwise false.
	 */	
	public boolean  isHexSectionVisible (){
		return hexSectionVisible;
	}	
	
	/**
	 * Sets whether showing the no color toggle button section. Depend on LAF, not 
	 * every LAFs will implement this functionity.
	 * <p>
	 * Default value is false.
	 * @param b true to show the no color toggle button section, false no.
	 */	
	public void  setNoColorSectionVisible (boolean b ){
		if(noColorSectionVisible != b){
			noColorSectionVisible = b;
			repaint();
		}
	}
	
	/**
	 * Returns true if the  no color toggle button is shown, otherwise false.
	 * @return true if the  no color toggle button is shown, otherwise false.
	 */	
	public boolean  isNoColorSectionVisible (){
		return noColorSectionVisible;
	}		
}


