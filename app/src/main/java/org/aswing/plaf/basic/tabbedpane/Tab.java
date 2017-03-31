/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.tabbedpane;

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

/**
 * TabbedPane Tab
 * @author iiley
 * @private
 */
public interface Tab{
	
	/**
	 * Inits the tab
	 * @param owner the tab owner component
	 */
	void  initTab (Component owner );
	
	/**
	 * Sets text and icon to the header
	 */
	void  setTextAndIcon (String text ,Icon icon );
	
	/**
	 * Sets the font of the tab text
	 */
	void  setFont (ASFont font );
	
	/**
	 * Sets the text color of the tab
	 */
	void  setForeground (ASColor color );
	
	/**
	 * Sets whether it is selected
	 */
	void  setSelected (boolean b );
	
    /**
     * Sets the vertical alignment of the icon and text.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    void  setVerticalAlignment (int alignment );
    
    /**
     * Sets the horizontal alignment of the icon and text.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    void  setHorizontalAlignment (int alignment );
    
    /**
     * Sets the vertical position of the text relative to the icon.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)</li>
     * <li>AsWingConstants.TOP</li>
     * <li>AsWingConstants.BOTTOM</li>
     * </ul>
     */
    void  setVerticalTextPosition (int textPosition );
    
    /**
     * Sets the horizontal position of the text relative to the icon.
     * @param textPosition one of the following values:
     * <ul>
     * <li>AsWingConstants.RIGHT (the default)</li>
     * <li>AsWingConstants.LEFT</li>
     * <li>AsWingConstants.CENTER</li>
     * </ul>
     */
    void  setHorizontalTextPosition (int textPosition );
    
    /**
     * If both the icon and text properties are set, this property
     * defines the space between them.  
     * <p>
     * The default value of this property is 4 pixels.
     * 
     * @see #getIconTextGap()
     */
    void  setIconTextGap (int iconTextGap );
	
	/**
	 * Sets space for margin between the border and
     * the content.
     */
	void  setMargin (Insets m );
	
	/**
	 * The component represent the header and can fire the selection event 
	 * through <code>MouseEvent.CLICK</code> event.
	 */
	Component  getTabComponent ();
	
}


