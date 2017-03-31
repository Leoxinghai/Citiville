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
 * The editor component used for JComboBox components.
 * @author iiley
 */
public interface ComboBoxEditor{

	/**
	 * Return the component that performance the editing asset.
	 * @return the editor component
	 */
	Component  getEditorComponent ();

	/**
	 * Sets whether the editor is editable now.
	 */
	void  setEditable (boolean b );

	/**
	 * Returns whether the editor is editable now.
	 */
	boolean  isEditable ();

	/**
	 * Adds a listener to listen the editor event when the edited item changes.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false );

	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	void  removeActionListener (Function listener );

	/**
	 * Set the item that should be edited. Cancel any editing if necessary.
	 */
	void  setValue (Object value);

	/**
	 * Return the edited item.
	 */
	Object getValue ();

	/**
	 * Ask the editor to start editing and to select everything in the editor.
	 */
	void  selectAll ();
}


