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
import flash.events.FocusEvent;
import flash.events.KeyboardEvent;
import flash.ui.Keyboard;

import org.aswing.event.AWEvent;

public class DefaultComboBoxEditor extends EventDispatcher implements ComboBoxEditor{

    private JTextField textField ;
    private boolean lostingFocus ;
    protected Object value;
    protected String valueText ;

	public  DefaultComboBoxEditor (){
		lostingFocus = false;
	}

	public void  selectAll (){
		if(getTextField().isEditable() && !lostingFocus){
			getTextField().selectAll();
		}
		//getTextField().makeFocus();
	}

	public void  setValue (Object value){
		this.value = value;
		if(value == null){
			getTextField().setText("");
		}else{
			getTextField().setText(value+"");
		}
		valueText = getTextField().getText();
	}

	public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(AWEvent.ACT, listener, false,  priority, useWeakReference);
	}

	public Object getValue () {
		return value;
	}

	public void  removeActionListener (Function listener ){
		removeEventListener(AWEvent.ACT, listener, false);
	}

	public void  setEditable (boolean b ){
        getTextField().setEditable(b);
        getTextField().setEnabled(b);
	}

	public Component  getEditorComponent (){
		return getTextField();
	}

	public boolean  isEditable (){
		return getTextField().isEditable();
	}

     public String  toString (){
        return "DefaultComboBoxEditor[]";
    }

    //------------------------------------------------------

	protected JTextField  createTextField (){
		JTextField tf =new JTextField("",1);//set rows 1to ensure the JTextField has a perfer height when empty
		tf.setBorder(null);
        tf.setOpaque(false);
        tf.setFocusable(false);
        tf.setBackgroundDecorator(null);
        return tf;
	}

    protected JTextField  getTextField (){
        if(textField == null){
        	textField = createTextField();
            initHandler();
        }
        return textField;
    }

    private void  initHandler (){
        getTextField().getTextField().addEventListener(KeyboardEvent.KEY_DOWN, __textKeyDown);
        getTextField().getTextField().addEventListener(FocusEvent.FOCUS_OUT, __grapValueFormText);
    }

    private void  __grapValueFormText (Event e ){
    	if(grapValueFormText()){
    		lostingFocus = true;
	        dispatchEvent(new AWEvent(AWEvent.ACT));
	        lostingFocus = false;
     	}
    }

    private boolean  grapValueFormText (){
    	if(getTextField().isEditable() && valueText != getTextField().getText()){
    		value = getTextField().getText();
    		return true;
    	}
    	return false;
    }

    private void  __textKeyDown (KeyboardEvent e ){
    	if(getTextField().isEditable() && e.keyCode == Keyboard.ENTER){
	        grapValueFormText();
	        dispatchEvent(new AWEvent(AWEvent.ACT));
     	}
    }
}


