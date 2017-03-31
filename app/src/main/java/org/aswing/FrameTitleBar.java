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

public interface FrameTitleBar{
	
	/**
	 * Returns the component(must be FrameTitleBar instance self, that means the implamentation 
	 * must extends Component and getPane() return itself) that represents the title bar.
	 */
	Component  getSelf ();
	
	/**
	 * Sets the owner of this title bar. null to uninstall from current frame.
	 *You can a JFrame or a JWindow ,if it is JWindow ,some  will be lost .
	 */
	void  setFrame (JWindow frame );
	
	JWindow  getFrame ();
	
	/**
	 * This method will be call when owner ui changed.
	 */
	void  updateUIPropertiesFromOwner ();
	
	/**
	 * Adds extra control to title bar
	 * @param c the control
	 * @param position left or right behind the title buttons. 
	 * 			<code>AsWingConstants.LEFT</code> or <code>AsWingConstants.RIGHT</code>
	 */
	void  addExtraControl (Component c ,int position );
	
	/**
	 * Returns the extra control already added.
	 * @returns the removed control, null will be returned if the control is not in title bar.
	 */
	Component  removeExtraControl (Component c );
	
	/**
	 * Sets the enabled property, if enabled, the title should have ability to iconified, maximize, restore, close, move frame.
	 * If not enabled, that abilities should be disabled.
	 */
	void  setTitleEnabled (boolean b );
	
	/**
	 * Returns is title enabled.
	 * @see #setTitleEnabled()
	 */
	boolean  isTitleEnabled ();
	
	void  setIcon (Icon i );
	
	Icon  getIcon ();
	
	void  setText (String t );
	
	String  getText ();
	
	JLabel  getLabel ();
	
	void  setIconifiedButton (AbstractButton b );
	
	void  setMaximizeButton (AbstractButton b );
	
	void  setRestoreButton (AbstractButton b );
	
	void  setCloseButton (AbstractButton b );
	
	AbstractButton  getIconifiedButton ();
	
	AbstractButton  getMaximizeButton ();
	
	AbstractButton  getRestoreButton ();
	
	AbstractButton  getCloseButton ();
	
}


