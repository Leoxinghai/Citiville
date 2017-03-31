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

	
import flash.display.DisplayObjectContainer;
import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.KeyboardEvent;
import flash.ui.Keyboard;

import org.aswing.util.*;

/**
 * Dispatched when key is down.
 * @eventType flash.events.KeyboardEvent.KEY_DOWN
 */
.get(Event(name="keyDown", type="flash.events.KeyboardEvent"))

/**
 * Dispatched when key is up.
 * @eventType flash.events.KeyboardEvent.KEY_UP
 */
.get(Event(name="keyUp", type="flash.events.KeyboardEvent"))

/**
 * KeyboardController controlls the key map for the action firing.
 * <p>
 * Thanks Romain for his Fever{@link http://fever.riaforge.org} accelerator framworks implementation, 
 * this is a simpler implementation study from his.
 * 
 * @see org.aswing.KeyMap
 * @see org.aswing.KeyType
 * @author iiley
 */
public class KeyboardManager extends EventDispatcher{
	
	protected static Array defaultMnemonicModifier =.get(Keyboard.CONTROL ,Keyboard.SHIFT) ;
	
	protected ArrayList keySequence ;
	protected KeyMap keymap ;
	protected boolean inited ;
	protected Array mnemonicModifier ;
	protected boolean keyJustActed ;
	protected boolean enabled ;
	
	
	/**
	 * Singleton class, 
	 * Don't create instance directly, in stead you should call <code>getInstance()</code>.
	 */
	public  KeyboardManager (){
		enabled = true;
		inited = false;
		keyJustActed = false;
		keySequence = new ArrayList();
		keymap = new KeyMap();
		mnemonicModifier = null;
	}
	
	/**
	 * Init the keyboad manager, it will only start works when it is inited.
	 * @param root the key trigger root of this keyboard manager.
	 * @throws Error if it is already inited.
	 */
	public void  init (DisplayObjectContainer root ){
		if(!inited){
			inited = true;
			root.addEventListener(KeyboardEvent.KEY_DOWN, __onKeyDown, false, 0, true);
			root.addEventListener(KeyboardEvent.KEY_UP, __onKeyUp, false, 0, true);
			root.addEventListener(Event.DEACTIVATE, __deactived, false, 0, true);
			
			//root.addEventListener(KeyboardEvent.KEY_DOWN, __onKeyDownCap, true);
			//root.addEventListener(KeyboardEvent.KEY_UP, __onKeyUpCap, true);
		}else{
			throw new Error("This KeyboardManager was already inited!");
		}
	}
		
	/**
	 * Registers a key action to the default key map of this controller.
	 * @param key the key type
	 * @param action the action
	 * @see KeyMap#registerKeyAction()
	 */
	public void  registerKeyAction (KeyType key ,Function action ){
		keymap.registerKeyAction(key, action);
	}
	
	/**
	 * Unregisters a key action to the default key map of this controller.
	 * @param key the key type
	 * @see KeyMap#unregisterKeyAction()
	 */
	public void  unregisterKeyAction (KeyType key ){
		keymap.unregisterKeyAction(key);
	}
	
	public KeyMap  getKeyMap (){
		return keymap;
	}
	
	/**
	 * Returns whether or not the key is down.
	 * @param the key code
	 * @return true if the specified key is down, false if not.
	 */
	public boolean  isKeyDown (int keyCode ){
		return keySequence.contains(keyCode);
	}
	
	/**
	 * Sets the mnemonic modifier key codes, the default is .get(Ctrl, Shift), however 
	 * for normal UI frameworks, it is .get(Alt), but because the flashplayer or explorer will 
	 * eat .get(Alt) for thier own mnemonic modifier, so we set our default to .get(Ctrl, Shift).
	 * <p>
	 * Sets null to make it allways keep same to <code>getDefaultMnemonicModifier</code>
	 * </p>
	 * @param keyCodes the array of key codes to be the mnemoic modifier.
	 */
	public void  setMnemonicModifier (Array keyCodes ){
		if(keyCodes == null){
			keyCodes = null;
		}else{
			mnemonicModifier = keyCodes.concat();
		}
	}
	
	public static Array  getDefaultMnemonicModifier (){
		return defaultMnemonicModifier.concat();
	}
	
	public static void  setDefaultMnemonicModifier (Array keyCodes ){
		defaultMnemonicModifier = keyCodes.concat();
	}
	
	public void  setEnabled (boolean b ){
		enabled = b;
		if(!b){
			keySequence.clear();
		}
	}
	
	public boolean  isEnabled (){
		return enabled;
	}
	
	public Array  getMnemonicModifier (){
		if(mnemonicModifier == null){
			return getDefaultMnemonicModifier();
		}else{
			return mnemonicModifier.concat();
		}
	}
	
	/**
	 * Returns whether or not the mnemonic modifier keys is down.
	 * @return whether or not the mnemonic modifier keys is down.
	 */
	public boolean  isMnemonicModifierDown (){
		Array mm =getMnemonicModifier ();
		for(int i =0;i <mm.length ;i ++){
			if(!isKeyDown(mm.get(i))){
				return false;
			}
		}
		return mm.length > 0;
	}
	
	/**
	 * Returns whether or not just a key action acted when the last key down.
	 * @return true if there's key actions acted at last key down, false not.
	 */
	public boolean  isKeyJustActed (){
		return keyJustActed;
	}
		
	private void  __onKeyDown (KeyboardEvent e ){
		if(!enabled){
			return;
		}
		dispatchEvent(e);
		int code =e.keyCode ;
		if(!keySequence.contains(code)){
			keySequence.append(code);
		}
		keyJustActed = false;
		if(keymap.fireKeyAction(keySequence.toArray())){
			keyJustActed = true;
		}
	}

	private void  __onKeyUp (KeyboardEvent e ){
		if(!enabled){
			return;
		}
		dispatchEvent(e);
		int code =e.keyCode ;
		keySequence.remove(code);
		//avoid IME bug that can't trigger keyup event when active IME and key up
		if(!e.ctrlKey){
			keySequence.remove(Keyboard.CONTROL);
		}
		if(!e.shiftKey){
			keySequence.remove(Keyboard.SHIFT);
		}
	}
	
	private void  __deactived (Event e ){
		keySequence.clear();
	}

}


