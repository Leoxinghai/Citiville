/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic;

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


import org.aswing.plaf.*;
import org.aswing.*;
import org.aswing.event.ListItemEvent;
import org.aswing.event.FocusKeyEvent;
import org.aswing.event.AWEvent;
import org.aswing.event.SelectionEvent;
import flash.events.MouseEvent;
import org.aswing.graphics.*;
import org.aswing.geom.*;
import flash.display.Graphics;
import flash.ui.Keyboard;

/**
 * List UI basic imp.
 * @author  iiley
 * @private
 */
public class BasicListUI extends BaseComponentUI{
	
	protected JList list ;
	
	public  BasicListUI (){
		super();
	}
	
     public void  installUI (Component c ){
        list = JList(c);
        installDefaults();
        installListeners();
    }
    
    protected String  getPropertyPrefix (){
        return "List.";
    }
    
    protected void  installDefaults (){
        String pp =getPropertyPrefix ();
        
        LookAndFeel.installColorsAndFont(list, pp);
        LookAndFeel.installBorderAndBFDecorators(list, pp);
        LookAndFeel.installBasicProperties(list, pp);
        
		ASColor sbg =list.getSelectionBackground ();
		if (sbg == null || sbg is UIResource) {
			list.setSelectionBackground(getColor(pp+"selectionBackground"));
		}

		ASColor sfg =list.getSelectionForeground ();
		if (sfg == null || sfg is UIResource) {
			list.setSelectionForeground(getColor(pp+"selectionForeground"));
		}
    }
    
    protected void  installListeners (){
    	list.addEventListener(ListItemEvent.ITEM_CLICK, __onItemClick);
    	list.addEventListener(ListItemEvent.ITEM_MOUSE_DOWN, __onItemMouseDown);
    	list.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
    	list.addEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
    	list.addEventListener(SelectionEvent.LIST_SELECTION_CHANGED, __onSelectionChanged);
    	list.addEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
    }
	
	 public void  uninstallUI (Component c ){
        uninstallDefaults();
        uninstallListeners();
    }
    
    protected void  uninstallDefaults (){
        LookAndFeel.uninstallBorderAndBFDecorators(list);
    }
    
    protected void  uninstallListeners (){
    	list.removeEventListener(ListItemEvent.ITEM_CLICK, __onItemClick);
    	list.removeEventListener(ListItemEvent.ITEM_MOUSE_DOWN, __onItemMouseDown);
    	list.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);
    	list.removeEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
    	list.removeEventListener(SelectionEvent.LIST_SELECTION_CHANGED, __onSelectionChanged);
    	list.removeEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
    }
    
    protected int paintFocusedIndex =-1;
    protected ListCell paintFocusedCell ;
    protected Graphics2D focusGraphics ;
    protected IntRectangle focusRectangle ;
    protected Graphics focusGraphicsOwner ;
    
	 public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
    	FocusManager fm =FocusManager.getManager(list.stage );
    	if(fm){
	    	focusGraphics = g;
	    	focusRectangle = b;
	    	focusGraphicsOwner = fm.moveFocusRectUpperTo(list).graphics;
	    	paintCurrentCellFocus();
    	}
    }
        
    private void  paintCurrentCellFocus (){
    	if(paintFocusedCell != null){
    		paintCellFocus(paintFocusedCell.getCellComponent());
    	}else{
    		super.paintFocus(list, focusGraphics, focusRectangle);
    	}
    }
    
    private void  paintCellFocusWithIndex (int index ){
    	if(index < 0 || index >= list.getModel().getSize()){
    		return;
    	}
		paintFocusedCell = list.getCellByIndex(index);
		paintFocusedIndex = index;
		if(paintFocusedCell){
			paintCellFocus(paintFocusedCell.getCellComponent());
		}
    }
    
    protected void  paintCellFocus (Component cellComponent ){
    	if(focusGraphicsOwner)
    		focusGraphicsOwner.clear();
    	super.paintFocus(list, focusGraphics, focusRectangle);
    	super.paintFocus(list, focusGraphics, paintFocusedCell.getCellComponent().getComBounds());
    }
    
    //----------
    private void  __onMouseWheel (MouseEvent e ){
		if(!list.isEnabled()){
			return;
		}
    	IntPoint viewPos =list.getViewPosition ();
    	if(e.shiftKey){
    		viewPos.x -= e.delta*list.getHorizontalUnitIncrement();
    	}else{
    		viewPos.y -= e.delta*list.getVerticalUnitIncrement();
    	}
    	list.setViewPosition(viewPos);
    }
    
    private void  __onFocusLost (AWEvent e ){
    	if(focusGraphicsOwner)
    		focusGraphicsOwner.clear();
    }
    
    private void  __onKeyDown (FocusKeyEvent e ){
		if(!list.isEnabled()){
			return;
		}
    	int code =e.keyCode ;
    	double dir =0;
    	if(code == Keyboard.UP || code == Keyboard.DOWN || code == Keyboard.SPACE){
    		FocusManager fm =FocusManager.getManager(list.stage );
	    	if(fm) fm.setTraversing(true);
    	}
    	if(code == Keyboard.UP){
    		dir = -1;
    	}else if(code == Keyboard.DOWN){
    		dir = 1;
    	}
    	
    	if(paintFocusedIndex == -1){
    		paintFocusedIndex = list.getSelectedIndex();
    	}
    	if(paintFocusedIndex < -1){
    		paintFocusedIndex = -1;
    	}else if(paintFocusedIndex > list.getModel().getSize()){
    		paintFocusedIndex = list.getModel().getSize();
    	}
    	int index =paintFocusedIndex +dir ;
    	if(code == Keyboard.HOME){
    		index = 0;
    	}else if(code == Keyboard.END){
    		index = list.getModel().getSize() - 1;
    	}
    	if(index < 0 || index >= list.getModel().getSize()){
    		return;
    	}
    	if(dir != 0 || (code == Keyboard.HOME || code == Keyboard.END)){
		    list.ensureIndexIsVisible(index);
		    list.validate();
    		if(e.shiftKey){
				int archor =list.getAnchorSelectionIndex ();
				if(archor < 0){
					archor = index;
				}
				list.setSelectionInterval(archor, index, false);
    		}else if(e.ctrlKey){
    		}else{
		    	list.setSelectionInterval(index, index, false);
    		}
    		//this make sure paintFocusedCell rememberd
    		paintCellFocusWithIndex(index);
    	}else{
    		if(code == Keyboard.SPACE){
		    	list.addSelectionInterval(index, index, false);
    			//this make sure paintFocusedCell rememberd
    			paintCellFocusWithIndex(index);
		    	list.ensureIndexIsVisible(index);
    		}
    	}
    }
    private void  __onSelectionChanged (SelectionEvent e ){
    	FocusManager fm =FocusManager.getManager(list.stage );
    	if(fm != null && fm.isTraversing() && list.isFocusOwner()){
    		if(focusGraphics == null){
    			list.paintFocusRect(true);
    		}
    		paintCellFocusWithIndex(list.getLeadSelectionIndex());
    	}
    }
    
	//------------------------------------------------------------------------
	//                 ---------  Selection ---------
	//------------------------------------------------------------------------
    
    private double pressedIndex ;
    private boolean pressedCtrl ;
    private boolean pressedShift ;
    private boolean doSelectionWhenRelease ;
    
    private void  __onItemMouseDown (ListItemEvent e ){
		int index =list.getItemIndexByCell(e.getCell ());
		pressedIndex = index;
		pressedCtrl = e.ctrlKey;
		pressedShift = e.shiftKey;
		doSelectionWhenRelease = false;
		
		if(list.getSelectionMode() == JList.MULTIPLE_SELECTION){
			if(list.isSelectedIndex(index)){
				doSelectionWhenRelease = true;
			}else{
				doSelection();
			}
		}else{
			list.setSelectionInterval(index, index, false);
		}
    }
    
    private void  doSelection (){
    	int index =pressedIndex ;
		if(pressedShift){
			int archor =list.getAnchorSelectionIndex ();
			if(archor < 0){
				archor = index;
			}
			list.setSelectionInterval(archor, index, false);
		}else if(pressedCtrl){
			if(!list.isSelectedIndex(index)){
				list.addSelectionInterval(index, index, false);
			}else{
				list.removeSelectionInterval(index, index, false);
			}
		}else{
			list.setSelectionInterval(index, index, false);
		}
    }
    
    private void  __onItemClick (ListItemEvent e ){
    	if(doSelectionWhenRelease){
    		doSelection();
    		doSelectionWhenRelease = false;
    	}
    }
    	
	
}


