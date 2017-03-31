/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf;

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


import org.aswing.*;
import org.aswing.error.ImpMissError;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntRectangle;
import org.aswing.graphics.Graphics2D;
import org.aswing.graphics.SolidBrush;
import org.aswing.graphics.Pen;
import flash.display.InteractiveObject;

/**
 * The base class for ComponentUI.
 * @author iiley
 */
public class BaseComponentUI implements ComponentUI

	private UIDefaults defaults ;


	public void  installUI (Component c )
	{
		throw new ImpMissError();
	}

	public void  uninstallUI (Component c )
	{
		throw new ImpMissError();
	}

	public void  putDefault (String key ,*)value
	{
		if(defaults == null){
			defaults = new UIDefaults();
		}
		defaults.put(key, value);
	}

	public Object getDefault (String key ) {
		if(containsDefaultsKey(key)){
			return defaults.get(key);
		}else{
			return UIManager.get(key);
		}
	}

	public void  paint (Component c ,Graphics2D g ,IntRectangle b )
	{
		paintBackGround(c, g, b);
	}

	public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
		if (g != null){
    		g.drawRectangle(new Pen(getDefaultFocusColorInner(), 1), b.x+0.5, b.y+0.5, b.width-1, b.height-1);
    		g.drawRectangle(new Pen(getDefaultFocusColorOutter(), 1), b.x+1.5, b.y+1.5, b.width-3, b.height-3);
		}
	}

    protected ASColor  getDefaultFocusColorInner (){
    	return getColor("focusInner");
    }
    protected ASColor  getDefaultFocusColorOutter (){
    	return getColor("focusOutter");
    }

	protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
		if(c.isOpaque()){
			g.fillRectangle(new SolidBrush(c.getBackground()), b.x, b.y, b.width, b.height);
		}
	}

    /**
     * Returns the object to receive the focus for the component.
     * The default implementation just return the component self.
     * @param c the component
     * @return the object to receive the focus.
     */
	public InteractiveObject  getInternalFocusObject (Component c ){
		return c;
	}

	/**
	 * Returns null
	 */
	public IntDimension  getMaximumSize (Component c )
	{
		return null;
	}
	/**
	 * Returns null
	 */
	public IntDimension  getMinimumSize (Component c )
	{
		return null;
	}
	/**
	 * Returns null
	 */
	public IntDimension  getPreferredSize (Component c )
	{
		return null;
	}

	//-----------------------------------------------------------
	//           Convernent methods
	//-----------------------------------------------------------

	public boolean  containsDefaultsKey (String key ){
		return defaults != null && defaults.containsKey(key);
	}

	public boolean  containsKey (String key ){
		return containsDefaultsKey(key) || UIManager.containsKey(key);
	}

	public boolean  getBoolean (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getBoolean(key);
		}
		return UIManager.getBoolean(key);
	}

	public double  getNumber (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getNumber(key);
		}
		return UIManager.getNumber(key);
	}

	public int  getInt (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getInt(key);
		}
		return UIManager.getInt(key);
	}

	public int  getUint (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getUint(key);
		}
		return UIManager.getUint(key);
	}

	public String  getString (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getString(key);
		}
		return UIManager.getString(key);
	}

	public Border  getBorder (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getBorder(key);
		}
		return UIManager.getBorder(key);
	}

	public Icon  getIcon (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getIcon(key);
		}
		return UIManager.getIcon(key);
	}

	public GroundDecorator  getGroundDecorator (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getGroundDecorator(key);
		}
		return UIManager.getGroundDecorator(key);
	}

	public ASColor  getColor (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getColor(key);
		}
		return UIManager.getColor(key);
	}

	public ASFont  getFont (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getFont(key);
		}
		return UIManager.getFont(key);
	}

	public Insets  getInsets (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getInsets(key);
		}
		return UIManager.getInsets(key);
	}

	public Object getInstance (String key ) {
		if(containsDefaultsKey(key)){
			return defaults.getInstance(key);
		}
		return UIManager.getInstance(key);
	}

	public Class  getClass (String key ){
		if(containsDefaultsKey(key)){
			return defaults.getConstructor(key);
		}
		return UIManager.getClass(key);
	}

}


