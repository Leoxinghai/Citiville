 * Copyright (C) 2006 Claus Wahlers and Max Herkender
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package deng.fzip;

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

//import flash.events.Event;

	/**
	 * FZip dispatches FZipErrorEvent objects when it encounters 
	 * errors while parsing the ZIP archive. There is only one type 
	 * of FZipErrorEvent: FZipErrorEvent.PARSE_ERROR
	 */		
	public class FZipErrorEvent extends Event
	{
		/**
		* A human readable description of the kind of parse error.
		*/		
		public String text ;

		/**
		* Defines the value of the type property of a FZipErrorEvent object.
		*/		
		public static  String PARSE_ERROR ="parseError";

		/**
		 * Constructor
		 * 
		 * @param type The type of the event. Event listeners can 
		 * access this information through the inherited type property. 
		 * There is only one type of FZipErrorEvent: 
		 * FZipErrorEvent.PARSE_ERROR.
		 * 
		 * @param text A human readable description of the kind of parse 
		 * error.
		 * 
		 * @param bubbles Determines whether the Event object participates 
		 * in the bubbling stage of the event flow. Event listeners can 
		 * access this information through the inherited bubbles property.
		 * 
		 * @param cancelable Determines whether the Event object can be 
		 * canceled. Event listeners can access this information through 
		 * the inherited cancelable property.
		 */		
		public  FZipErrorEvent (String type ,String text ="",boolean bubbles =false ,boolean cancelable =false ){
			this.text = text;
			super(type, bubbles, cancelable);
		}
		
		/**
		 * Creates a copy of the FZipErrorEvent object and sets the value 
		 * of each property to match that of the original.
		 * 
		 * @return A new FZipErrorEvent object with property values that 
		 * match those of the original.
		 */		
		 public Event  clone (){
			return new FZipErrorEvent(type, text, bubbles, cancelable);
		}
	}

