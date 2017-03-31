/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.util;

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


import flash.display.*;

/**
 * DepthManager to manage the depth of display objects.
 * 
 * @author iiley
 */
public class DepthManager{
	
	/**
	 * Bring the mc to all brother mcs' bottom.
	 * @param mc the mc to be set to bottom
	 * @see #isBottom()
	 */
	public static void  bringToBottom (DisplayObject mc ){
		DisplayObjectContainer parent =mc.parent ;
		if(parent == null){ return; }
		if(parent.getChildIndex(mc) != 0){
			parent.setChildIndex(mc, 0);
		}
	}
	
	/**
	 * Bring the mc to all brother mcs' top.
	 */	
	public static void  bringToTop (DisplayObject mc ){
		DisplayObjectContainer parent =mc.parent ;
		if(parent == null) return;
		int maxIndex =parent.numChildren -1;
		if(parent.getChildIndex(mc) != maxIndex){
			parent.setChildIndex(mc, maxIndex);
		}
	}
	
	/**
	 * Returns is the mc is on the top depths in DepthManager's valid depths.
	 * Valid depths is that depths from MIN_DEPTH to MAX_DEPTH.
	 */
	public static boolean  isTop (DisplayObject mc ){
		DisplayObjectContainer parent =mc.parent ;
		if(parent == null) return true;
		return (parent.numChildren-1) == parent.getChildIndex(mc);
	}
	
	/**
	 * Returns if the mc is at bottom depth.
	 * @param mc the mc to be set to bottom
	 * @return is the mc is at the bottom
	 */
	public static boolean  isBottom (DisplayObject mc ){
		DisplayObjectContainer parent =mc.parent ;
		if(parent == null) return true;
		int depth =parent.getChildIndex(mc );
		if(depth == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Return if mc is just first bebow the aboveMC.
	 * if them don't have the same parent, whatever depth they has just return false.
	 */
	public static boolean  isJustBelow (DisplayObject mc ,DisplayObject aboveMC ){
		DisplayObjectContainer parent =mc.parent ;
		if(parent == null) return false;
		if(aboveMC.parent != parent) return false;
		
		return parent.getChildIndex(mc) == parent.getChildIndex(aboveMC)-1;
	}
	
	/**
	 * Returns if mc is just first above the belowMC.
	 * if them don't have the same parent, whatever depth they has just return false.
	 * @see #isJustBelow
	 */	
	public static boolean  isJustAbove (DisplayObject mc ,DisplayObject belowMC ){
		return isJustBelow(belowMC, mc);
	}
	
}


