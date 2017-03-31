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


import org.aswing.util.HashMap;

/**
 * KeyMap is a key definition -> action map.
 * @see org.aswing.KeyDefinition
 * @author iiley
 */
public class KeyMap{
	
	private HashMap map ;
	
	/**
	 * Creates a key map.
	 */
	public  KeyMap (){
		map = new HashMap();
	}
	
	/**
	 * Registers a key definition -> action pair to the map. If same key definition is already 
	 * in the map, it will be replaced with the new one.
	 * @param key the key definition.
	 * @param action the aciton function
	 */
	public void  registerKeyAction (KeyType key ,Function action ){
		map.put(getCodec(key), new KeyAction(key, action));
	}
	
	/**
	 * Unregisters a key and its action value.
	 * @param key the key and its value to be unrigesterd.
	 */
	public void  unregisterKeyAction (KeyType key ){
		map.remove(getCodec(key));
	}
	
	/**
	 * Returns the action from the key defintion.
	 * @param key the key definition
	 * @return the action.
	 * @see #getCodec()
	 */
	public Function  getKeyAction (KeyType key ){
		return getKeyActionWithCodec(getCodec(key));
	}
	
	private Function  getKeyActionWithCodec (String codec ){
		KeyAction ka =map.get(codec );
		if(ka != null){
			return ka.action;
		}
		return null;
	}
	
	/**
	 * Fires a key action with key sequence.
	 * @return whether or not a key action fired with this key sequence.
	 */
	public boolean  fireKeyAction (Array keySequence ){
		String codec =getCodecWithKeySequence(keySequence );
		Function action =getKeyActionWithCodec(codec );
		if(action != null){
			action();
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the key definition is already registered.
	 * @param key the key definition
	 */
	public boolean  containsKey (KeyType key ){
		return map.containsKey(getCodec(key));
	}
	
	/**
	 * Returns the codec of a key definition, same codec means same key definitions.
	 * @param key the key definition
	 * @return the codec of specified key definition
	 */
	public static String  getCodec (KeyType key ){
		return getCodecWithKeySequence(key.getCodeSequence());
	}
	
	/**
	 * Returns the codec of a key sequence.
	 * @param keySequence the key sequence
	 * @return the codec of specified key sequence
	 */
	public static String  getCodecWithKeySequence (Array keySequence ){
		return keySequence.join("|");
	}

}

import org.aswing.KeyType;

class KeyAction{
	internal KeyType key ;
	internal Function action ;
	
	public  KeyAction (KeyType key ,Function action ){
		this.key = key;
		this.action = action;
	}
}


