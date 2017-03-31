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
 * The focus traversal policy for windows, the only different from 
 * <code>ContainerOrderFocusTraversalPolicy</code> is that it choose the first 
 * content pane component to be the default component.
 */
public class WindowOrderFocusTraversalPolicy extends ContainerOrderFocusTraversalPolicy{
	
	public  WindowOrderFocusTraversalPolicy (){
		super();
	}
	
	/**
	 * This will return the first focusable component in the container.
	 * @return the default component to be focused.
	 */
	 public Component  getDefaultComponent (Container container ){
		if(container is JWindow){
			JWindow window =(JWindow)container;
			Container content =window.getContentPane ();
			if(content.isShowing() && content.isVisible() && content.isFocusable()){
				return content;
			}
			Component dc =getFirstComponent(content );
			if(dc == null){
				return super.getDefaultComponent(container);
			}else{
				return dc;
			}
		}else{
			return super.getDefaultComponent(container);
		}
	}
}


