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


import org.aswing.CellEditor;
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.table.TableCellEditor;
import org.aswing.tree.TreeCellEditor;
import org.aswing.util.ArrayUtils;
import org.aswing.error.ImpMissError;
import flash.events.Event;
import flash.ui.Keyboard;

/**
 * @author iiley
 */
public class AbstractCellEditor implements CellEditor, TableCellEditor, TreeCellEditor{

	private Array listeners ;
	private int clickCountToStart ;

	protected JPopup popup ;

	public  AbstractCellEditor (){
		listeners = new Array();
		clickCountToStart = 0;
		popup = new JPopup();
		popup.setLayout(new EmptyLayout());
	}

    /**
     * Specifies the number of clicks needed to start editing.
     * Default is 0.(mean start after pressed)
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart()
     */
    public void  setClickCountToStart (double count ){
		clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * @return the number of clicks needed to start editing
     */
    public double  getClickCountToStart (){
		return clickCountToStart;
    }

    /**
     * Calls the editor's component to update UI.
     */
    public void  updateUI (){
    	getEditorComponent().updateUI();
    }

    public Component  getEditorComponent (){
		throw new ImpMissError();
		return null;
    }

	public Object getCellEditorValue () {
		throw new ImpMissError();
	}

   /**
    *Sets the value of this cell .Subclass must  this method to
    * make editor display this value.
    * @param value the new value of this cell
    */
	protected void  setCellEditorValue (Object value){
		throw new ImpMissError();
	}

	public boolean  isCellEditable (int clickCount ){
		return clickCount == clickCountToStart;
	}

	public void  startCellEditing (Container owner ,*value ,IntRectangle bounds ){
		popup.changeOwner(AsWingUtils.getOwnerAncestor(owner));
		IntPoint gp =owner.getGlobalLocation ().move(bounds.x ,bounds.y );
		popup.setSizeWH(bounds.width, bounds.height);
		popup.show();
		popup.setGlobalLocation(gp);
		popup.validate();
		popup.toFront();

		Component com =getEditorComponent ();
		com.removeEventListener(AWEvent.ACT, __editorComponentAct);
		com.removeEventListener(AWEvent.FOCUS_LOST, __editorComponentFocusLost);
		com.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __editorComponentKeyDown);
		com.setSizeWH(bounds.width, bounds.height);
		popup.append(com);
		setCellEditorValue(value);
		com.requestFocus();
		//if com is a container and can't has focus, then focus its first sub child.
		if(com is Container && !com.isFocusOwner()){
			Container con =Container(com );
			Component sub ;
			sub = con.getFocusTraversalPolicy().getDefaultComponent(con);
			if(sub != null) sub.requestFocus();
			if(sub == null || !sub.isFocusOwner()){
				for(int i =0;i <con.getComponentCount ();i ++){
					sub = con.getComponent(i);
					sub.requestFocus();
					if(sub.isFocusOwner()){
						break;
					}
				}
			}
		}
		com.addEventListener(AWEvent.ACT, __editorComponentAct);
		com.addEventListener(AWEvent.FOCUS_LOST, __editorComponentFocusLost);
		com.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __editorComponentKeyDown);
		com.validate();
	}

	private void  __editorComponentFocusLost (Event e ){
		trace("__editorComponentFocusLost");
		cancelCellEditing();
	}

	private void  __editorComponentAct (Event e ){
		stopCellEditing();
	}

	private void  __editorComponentKeyDown (FocusKeyEvent e ){
		if(e.keyCode == Keyboard.ESCAPE){
			cancelCellEditing();
		}
	}

	public boolean  stopCellEditing (){
		removeEditorComponent();
		fireEditingStopped();
		return true;
	}

	public void  cancelCellEditing (){
		removeEditorComponent();
		fireEditingCanceled();
	}

	public boolean  isCellEditing (){
		Component editorCom =getEditorComponent ();
		return editorCom != null && editorCom.isShowing();
	}

	public void  addCellEditorListener (CellEditorListener l ){
		listeners.push(l);
	}

	public void  removeCellEditorListener (CellEditorListener l ){
		ArrayUtils.removeFromArray(listeners, l);
	}

	protected void  fireEditingStopped (){
		for(double i =listeners.length -1;i >=0;i --){
			CellEditorListener l =CellEditorListener(listeners.get(i) );
			l.editingStopped(this);
		}
	}
	protected void  fireEditingCanceled (){
		for(double i =listeners.length -1;i >=0;i --){
			CellEditorListener l =CellEditorListener(listeners.get(i) );
			l.editingCanceled(this);
		}
	}

	protected void  removeEditorComponent (){
		getEditorComponent().removeFromContainer();
		popup.dispose();
	}
}


