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


import org.aswing.*;
import org.aswing.plaf.ComponentUI;
import org.aswing.plaf.basic.BasicLookAndFeel;


/**
 * This class keeps track of the current look and feel and its defaults.
 *
 * <p>We manage three levels of defaults: user defaults,
 * look and feel defaults, system defaults. A call to UIManager.get
 * checks all three levels in order and returns the first non-null
 * value for a key, if any. A call to UIManager.put just affects the
 * user defaults. Note that a call to setLookAndFeel doesn't affect
 * the user defaults, it just replaces the middle defaults "level".
 * @author iiley
 */
public class UIManager

	private static UIDefaults lookAndFeelDefaults ;
	private static LookAndFeel lookAndFeel ;

	/**
	 * @see org.aswing.ASWingUtils#updateAllComponentUI()
	 */
	public static void  setLookAndFeel (LookAndFeel laf ){
		lookAndFeel = laf;
		setLookAndFeelDefaults(laf.getDefaults());
	}

	public static LookAndFeel  getLookAndFeel (){
		checkLookAndFeel();
		return lookAndFeel;
	}

	public static UIDefaults  getDefaults (){
		return getLookAndFeelDefaults();
	}

	public static UIDefaults  getLookAndFeelDefaults (){
		checkLookAndFeel();
		return lookAndFeelDefaults;
	}

	private static void  setLookAndFeelDefaults (UIDefaults d ){
		lookAndFeelDefaults = d;
	}

	private static void  checkLookAndFeel (){
		if(lookAndFeel == null){
			setLookAndFeel(new BasicLookAndFeel());
		}
	}

	public static boolean  containsKey (String key ){
		return getDefaults().containsKey(key);
	}

	public static Object (String key ) {
		return getDefaults().get(key);
	}

	public static ComponentUI  getUI (Component target ){

		return getDefaults().getUI(target);
	}

	public static boolean  getBoolean (String key ){
		return getDefaults().getBoolean(key);
	}

	public static double  getNumber (String key ){
		return getDefaults().getNumber(key);
	}

	public static int  getInt (String key ){
		return getDefaults().getInt(key);
	}

	public static int  getUint (String key ){
		return getDefaults().getUint(key);
	}

	public static String  getString (String key ){
		return getDefaults().getString(key);
	}

	public static Border  getBorder (String key ){
		return getDefaults().getBorder(key);
	}

	public static GroundDecorator  getGroundDecorator (String key ){
		return getDefaults().getGroundDecorator(key);
	}

	public static ASColor  getColor (String key ){
		return getDefaults().getColor(key);
	}

	public static ASFont  getFont (String key ){
		return getDefaults().getFont(key);
	}

	public static Icon  getIcon (String key ){
		return getDefaults().getIcon(key);
	}

	public static Insets  getInsets (String key ){
		return getDefaults().getInsets(key);
	}

	public static Object getInstance (String key ) {
		return getDefaults().getInstance(key);
	}

	public static Class  getClass (String key ){
		return getDefaults().getConstructor(key);
	}

}


