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

	
/**
 * A collection of constants generally used for positioning and orienting
 * components on the screen.
 * 
 * @author iiley
 */
public class AsWingConstants{
		
		
		public static  int NONE =-1;

        /** 
         * The central position in an area. Used for
         * both compass-direction constants (NORTH, etc.)
         * and box-orientation constants (TOP, etc.).
         */
        public static  int CENTER =0;

        // 
        // Box-orientation constant used to specify locations in a box.
        //
        /** 
         * Box-orientation constant used to specify the top of a box.
         */
        public static  int TOP =1;
        /** 
         * Box-orientation constant used to specify the left side of a box.
         */
        public static  int LEFT =2;
        /** 
         * Box-orientation constant used to specify the bottom of a box.
         */
        public static  int BOTTOM =3;
        /** 
         * Box-orientation constant used to specify the right side of a box.
         */
        public static  int RIGHT =4;

        // 
        // Compass-direction constants used to specify a position.
        //
        /** 
         * Compass-direction North (up).
         */
        public static  int NORTH =1;
        /** 
         * Compass-direction north-east (upper right).
         */
        public static  int NORTH_EAST =2;
        /** 
         * Compass-direction east (right).
         */
        public static  int EAST =3;
        /** 
         * Compass-direction south-east (lower right).
         */
        public static  int SOUTH_EAST =4;
        /** 
         * Compass-direction south (down).
         */
        public static  int SOUTH =5;
        /** 
         * Compass-direction south-west (lower left).
         */
        public static  int SOUTH_WEST =6;
        /** 
         * Compass-direction west (left).
         */
        public static  int WEST =7;
        /** 
         * Compass-direction north west (upper left).
         */
        public static  int NORTH_WEST =8;

        //
        // These constants specify a horizontal or 
        // vertical orientation. For example, they are
        // used by scrollbars and sliders.
        //
        /** 
         * Horizontal orientation. Used for scrollbars and sliders.
         */
        public static  int HORIZONTAL =0;
        /** 
         * Vertical orientation. Used for scrollbars and sliders.
         */
        public static  int VERTICAL =1;
}


