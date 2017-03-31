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

import flash.events.Event;
import flash.events.EventDispatcher;

import org.aswing.event.*;
import org.aswing.util.*;

/**
 * A generic implementation of SingleSelectionModel.
 * @author iiley
 */
public class DefaultSingleSelectionModel extends EventDispatcher implements SingleSelectionModel{
	
	private int index ;
	
	public  DefaultSingleSelectionModel (){
		index = -1;
	}
	
	public int  getSelectedIndex (){
		return index;
	}

	public void  setSelectedIndex (int index ,boolean programmatic =true ){
		if(this.index != index){
			this.index = index;
			fireChangeEvent(programmatic);
		}
	}

	public void  clearSelection (boolean programmatic =true ){
		setSelectedIndex(-1, programmatic);
	}

	public boolean  isSelected (){
		return getSelectedIndex() != -1;
	}
	
	public void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, listener, false,  priority, useWeakReference);
	}
	
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}
	
	private void  fireChangeEvent (boolean programmatic ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
}


