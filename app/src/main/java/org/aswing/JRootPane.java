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


import flash.display.InteractiveObject;
import flash.events.*;
import flash.text.TextField;
import flash.ui.Keyboard;

import org.aswing.error.ImpMissError;
import org.aswing.util.HashMap;

/**
 * The general AsWing window root container, it is the popup, window and frame's ancestor.
 * It manages the key accelerator and mnemonic for a pane.
 * @see #registerMnemonic()
 * @author iiley
 */
public class JRootPane extends Container{

	protected JButton defaultButton ;
	protected HashMap mnemonics ;
	protected boolean mnemonicJustActed ;
	protected KeyboardManager keyManager ;

	private InteractiveObject triggerProxy ;

	//TODO imp
	//private Object menuBar;

	public  JRootPane (){
		super();
		setName("JRootPane");
		mnemonicJustActed = false;
		layout = new BorderLayout();
		mnemonics = new HashMap();
		keyManager = new KeyboardManager();
		keyManager.init(this);
		triggerProxy = this;//just make below call works
		setMnemonicTriggerProxy(null);
		addEventListener(Event.REMOVED_FROM_STAGE, __removedFromStage);
	}

	public void  setDefaultButton (JButton button ){
		if(defaultButton != button){
			if(defaultButton != null){
				defaultButton.repaint();
			}
			defaultButton = button;
			defaultButton.repaint();
		}
	}

	public JButton  getDefaultButton (){
		return defaultButton;
	}

	/**
	 * Sets the main menuBar of this root pane.(Main menu bar means that
	 * if user press Alt key, the first menu of the menu bar will be actived)
	 * The menuBar must be located in this root pane(or in its child),
	 * otherwise, it will not have the main menu bar ability.
	 * @menuBar the menu bar, or null
	 */
	public void  setMenuBar (*)menuBar {
		//TODO imp
		throw new ImpMissError();
	}

	/**
	 * Returns the key -> action map of this window.
	 * When a window is actived, it's keymap will be in working, or it is out of working.
	 * @see org.aswing.KeyMap
	 * @see org.aswing.KeyboardController
	 */
	public KeyMap  getKeyMap (){
		return keyManager.getKeyMap();
	}

	 public KeyboardManager  getKeyboardManager (){
		return keyManager;
	}

	/**
	 * Sets whether or not the kay map action will be fired.
	 * @param b true to make it work, false not.
	 */
	public void  setKeyMapActived (boolean b ){
		keyManager.setEnabled(b);
	}

	/**
	 * Sets the mnemonic be forced to work or not.
	 * <p>
	 * true, to make the mnemonic be forced to work, it means what ever the root pane and
	 * it children has focused or not, it will listen the key to make mnemonic works.<br>
	 * false, to make the mnemonic works in normal way, it means the mnenonic will only works
	 * when the root pane or its children has focus.
	 * </p>
	 * @param b forced work or not.
	 */
	public void  setMnemonicTriggerProxy (InteractiveObject trigger ){
		if(trigger != triggerProxy){
			if(triggerProxy){
				triggerProxy.removeEventListener(TextEvent.TEXT_INPUT, __textInput, true);
				triggerProxy.removeEventListener(KeyboardEvent.KEY_DOWN, __keyDown, true);
			}
			triggerProxy = trigger;
			if(trigger == null){
				trigger = this;
			}
			trigger.addEventListener(TextEvent.TEXT_INPUT, __textInput, true, 0, true);
			trigger.addEventListener(KeyboardEvent.KEY_DOWN, __keyDown, true, 0, true);
		}
	}

	/**
	 * Register a button with its mnemonic.
	 */
	internal void  registerMnemonic (AbstractButton button ){
		if(button.getMnemonic() >= 0){
			mnemonics.put(button.getMnemonic(), button);
		}
	}

	internal void  unregisterMnemonic (AbstractButton button ){
		if(mnemonics.get(button.getMnemonic()) == button){
			mnemonics.remove(button.getMnemonic());
		}
	}

	//-------------------------------------------------------
	//        Event Handlers
	//-------------------------------------------------------

	private void  __keyDown (KeyboardEvent e ){
		mnemonicJustActed = false;

		int code =e.keyCode ;

		if(code == Keyboard.ENTER){
			AbstractButton dfBtn =getDefaultButton ();
			if(dfBtn != null){
				if(dfBtn.isShowing() && dfBtn.isEnabled()){
					dfBtn.doClick();
					mnemonicJustActed = true;
					return;
				}
			}
		}
		if(stage == null){
			return;
		}
		//try to trigger the mnemonic
		if(stage.focus is TextField){
			if(!keyManager.isMnemonicModifierDown()){
				return;
			}
		}
		AbstractButton mnBtn =mnemonics.getValue(int(code ));
		if(mnBtn != null){
			if(mnBtn.isShowing() && mnBtn.isEnabled()){
				mnBtn.doClick();
				FocusManager fm =FocusManager.getManager(stage );
				if(fm){
					fm.setTraversing(true);
					mnBtn.paintFocusRect();
				}
				mnemonicJustActed = true;
			}
		}
	}

	private void  __textInput (TextEvent e ){
		if(keyManager.isMnemonicModifierDown() || keyManager.isKeyJustActed()){
			e.preventDefault();
		}
	}

	private void  __removedFromStage (Event e ){
		mnemonics.clear();
	}

}


