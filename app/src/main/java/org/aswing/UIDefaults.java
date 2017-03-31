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


import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.util.HashMap;

/**
 * A table of defaults for AsWing components.  Applications can set/get
 * default values via the <code>UIManager</code>.
 *
 * @see UIManager
 * @author iiley
 */
public class UIDefaults extends HashMap
	public  UIDefaults ()
	{
		super();
	}

    /**
     * Sets the value of <code>key</code> to <code>value</code>.
     * If value is <code>null</code>, the key is removed from the table.
     *
     * @param key    the unique <code>Object</code> who's value will be used
     *          to retrieve the data value associated with it
     * @param value  the new <code>Object</code> to store as data under
     *		that key
     * @return the previous <code>Object</code> value, or <code>null</code>
     * @see #putDefaults()
     * @see org.aswing.utils.HashMap#put()
     */
 	 public  put (*key ,Object value)*{
 		oldValue = value(==null)? super.remove(key) : super.put(key, value);
 		return oldValue;
 	}

	/**
     * Puts all of the key/value pairs in the database.
     * @param keyValueList  an array of key/value pairs
     * @see #put()
     * @see org.aswing.utils.Hashtable#put()
     */
	public void  putDefaults (Array keyValueList ){
		for(double i =0;i <keyValueList.length ;i +=2){
            value = keyValueList.get(i +1) ;
            if (value == null) {
                super.remove(keyValueList.get(i));
            }else {
                super.put(keyValueList.get(i), value);
            }
        }
	}

	/**
	 * Returns the component LookAndFeel specified UI object
	 * @return target's UI object, or null if there is not his UI object
	 */
	public ComponentUI  getUI (Component target ){
		ComponentUI ui =getInstance(target.getUIClassID ())as ComponentUI ;
		if(ui == null){
			ui =(ComponentUI) getCreateInstance(target.getDefaultBasicUIClass());
		}
		return ui;
	}

	public boolean  getBoolean (String key ){
		return (this.get(key) == true);
	}

	public double  getNumber (String key ){
		return this.get(key) as Number;
	}

	public int  getInt (String key ){
		return this.get(key) as int;
	}

	public int  getUint (String key ){
		return this.get(key) as uint;
	}

	public String  getString (String key ){
		return (String)this.get(key);
	}

	public Border  getBorder (String key ){
		Border border =getInstance(key )as Border ;
		if(border == null){
			border =EmptyUIResources .BORDER ;//make it to be an ui resource then can  by next LAF
		}
		return border;
	}

	public Icon  getIcon (String key ){
		Icon icon =getInstance(key )as Icon ;
		if(icon == null){
			icon =EmptyUIResources .ICON ;//make it to be ui resource property then can  by next LAF
		}
		return icon;
	}

	public GroundDecorator  getGroundDecorator (String key ){
		GroundDecorator dec =getInstance(key )as GroundDecorator ;
		if(dec == null){
			dec =EmptyUIResources .DECORATOR ;//make it to be ui resource property then can  by next LAF
		}
		return dec;
	}

	public ASColor  getColor (String key ){
		ASColor color =getInstance(key )as ASColor ;
		if(color == null){
			color =EmptyUIResources .COLOR ;//make it to be an ui resource then can  by next LAF
		}
		return color;
	}

	public ASFont  getFont (String key ){
		ASFont font =getInstance(key )as ASFont ;
		if(font == null){
			font =EmptyUIResources .FONT ;//make it to be an ui resource then can  by next LAF
		}
		return font;
	}

	public Insets  getInsets (String key ){
		Insets i =getInstance(key )as Insets ;
		if(i == null){
			i =EmptyUIResources .INSETS ;//make it to be an ui resource then can  by next LAF
		}
		return i;
	}

	//-------------------------------------------------------------
	public Class  getConstructor (String key ){
		return this.get(key) as Class;
	}

	public Object getInstance (String key ) {
		value = this.get(key);
		if(value is Class){
			return getCreateInstance((Class)value);
		}else{
			return value;
		}
	}

	private Object  getCreateInstance (Class constructor ){
		return new constructor();
	}

}


