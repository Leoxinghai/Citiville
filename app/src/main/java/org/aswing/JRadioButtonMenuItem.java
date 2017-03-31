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


import org.aswing.plaf.basic.BasicRadioButtonMenuItemUI;

/**
 * An implementation of a radio button menu item.
 * A <code>JRadioButtonMenuItem</code> is
 * a menu item that is part of a group of menu items in which only one
 * item in the group can be selected. The selected item displays its
 * selected state. Selecting it causes any other selected item to
 * switch to the unselected state.
 * To control the selected state of a group of radio button menu items,  
 * use a <code>ButtonGroup</code> object.
 * @author iiley
 */
public class JRadioButtonMenuItem extends JMenuItem{
	
	public  JRadioButtonMenuItem (String text ="",Icon icon =null ){
		super(text, icon);
		setName("JRadioButtonMenuItem");
    	setModel(new ToggleButtonModel());
	}

	 public String  getUIClassID (){
		return "RadioButtonMenuItemUI";
	}
	
	 public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicRadioButtonMenuItemUI;
    }
	
}


