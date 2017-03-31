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
import flash.text.*;

import org.aswing.event.InteractiveEvent;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntPoint;
import org.aswing.geom.IntRectangle;
import org.aswing.plaf.basic.BasicTextAreaUI;

/**
 * Dispatched when the viewport's state changed. the state is all about:
 * <ul>
 * <li>view position</li>
 * <li>verticalUnitIncrement</li>
 * <li>verticalBlockIncrement</li>
 * <li>horizontalUnitIncrement</li>
 * <li>horizontalBlockIncrement</li>
 * </ul>
 * </p>
 * 
 * @eventType org.aswing.event.InteractiveEvent.STATE_CHANGED
 */
.get(Event(name="stateChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A JTextArea is a multi-line area that displays text.
 * <p>
 * With JScrollPane, it's easy to be a scrollable text area, for example:
 * <pre>
 *JTextArea ta =new JTextArea ();
 * 
 *JScrollPane sp =new JScrollPane(ta );
 * //or 
 *//JScrollPane sp =new JScrollPane ();
 * //sp.setView(ta);
 * </pre>
 * @author iiley
 * @see org.aswing.JScrollPane
 */
public class JTextArea extends JTextComponent implements Viewportable{
	 	
 	/**
 	 * The default unit/block increment, it means auto count a value.
 	 */
 	public static  int AUTO_INCREMENT =int.MIN_VALUE ;
 	
	private static int defaultMaxChars =0;
 	
	private int columns ;
	private int rows ;
	
	private IntPoint viewPos ;
	private boolean viewportSizeTesting ;
	private int lastMaxScrollV ;
	private int lastMaxScrollH ;
		
	private int verticalUnitIncrement ;
	private int verticalBlockIncrement ;
	private int horizontalUnitIncrement ;
	private int horizontalBlockIncrement ;
	
	public  JTextArea (String text ="",int rows =0,int columns =0){
		super();
		setName("JTextField");
		getTextField().multiline = true;
		getTextField().text = text;
		setMaxChars(defaultMaxChars);
		this.rows = rows;
		this.columns = columns;
		viewPos = new IntPoint();
		viewportSizeTesting = false;			
		lastMaxScrollV = getTextField().maxScrollV;
		lastMaxScrollH = getTextField().maxScrollH;
		
		verticalUnitIncrement = AUTO_INCREMENT;
		verticalBlockIncrement = AUTO_INCREMENT;
		horizontalUnitIncrement = AUTO_INCREMENT;
		horizontalBlockIncrement = AUTO_INCREMENT;
		
		getTextField().addEventListener(Event.CHANGE, __onTextAreaTextChange);
		getTextField().addEventListener(Event.SCROLL, __onTextAreaTextScroll);
		
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicTextAreaUI;
    }
	
	 public String  getUIClassID (){
		return "TextAreaUI";
	}
	
	/**
	 * Sets the maxChars property for default value when <code>JTextArea</code> be created.
	 * By default it is 0, you can change it by this method.
	 * @param n the default maxChars to set
	 */
	public static void  setDefaultMaxChars (int n ){
		defaultMaxChars = n;
	}
	
	/**
	 * Returns the maxChars property for default value when <code>JTextArea</code> be created.
	 * @return the default maxChars value.
	 */
	public static int  getDefaultMaxChars (){
		return defaultMaxChars;
	}	
	
	/**
	 * Sets the number of columns in this JTextArea, if it changed then call parent to do layout. 
	 * 
	 * @param columns the number of columns to use to calculate the preferred width;
	 * 			if columns is set to zero or min than zero, the preferred width will be matched just to view all of the text.
	 */
	public void  setColumns (int columns ){
		if(columns < 0) columns = 0;
		if(this.columns != columns){
			this.columns = columns;
			if(isWordWrap()){
				//invalidateTextFieldAutoSizeToCountPrefferedSize();
			}
			revalidate();
		}
	}
	
	/**
	 * @see #setColumns
	 */
	public int  getColumns (){
		return columns;
	}
	
	/**
	 * Sets the number of rows in this JTextArea, if it changed then call parent to do layout. 
	 * 
	 * @param rows the number of rows to use to calculate the preferred height;
	 * 			if rows is set to zero or min than zero, the preferred height will be matched just to view all of the text.
	 */
	public void  setRows (int rows ){
		if(rows < 0) rows = 0;
		if(this.rows != rows){
			this.rows = rows;
			if(isWordWrap()){
				//invalidateTextFieldAutoSizeToCountPrefferedSize();
			}
			revalidate();
		}
	}
	
	/**
	 * @see #setRows
	 */
	public int  getRows (){
		return rows;
	}
	
	 protected boolean  isAutoSize (){
		return columns == 0 || rows == 0;
	}
	
	 protected IntDimension  countPreferredSize (){
		IntDimension size ;
		if(columns > 0 && rows > 0){
			int width =getColumnWidth ()*columns +getWidthMargin ();
			int height =getRowHeight ()*rows +getHeightMargin ();
			size = new IntDimension(width, height);
		}else if(rows <=0 && columns <=0 ){
			size = getTextFieldAutoSizedSize();
		}else if(rows > 0){ // columns must <= 0
			int forceHeight =getRowHeight ()*rows +getHeightMargin ();
			size = getTextFieldAutoSizedSize(0, forceHeight);
		}else{ //must be columns > 0 and rows <= 0
			int forceWidth =getColumnWidth ()*columns +getWidthMargin ();
			size = getTextFieldAutoSizedSize(forceWidth, 0);
		}
		return getInsets().getOutsideSize(size);
	}	
	
	protected void  fireStateChanged (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}
	
	 protected void  size (){
		super.size();
		applyBoundsToText(getPaintBounds());
	}

	
	private void  __onTextAreaTextChange (Event e ){
    	if(viewportSizeTesting){
    		return;
    	}
		//do not need call revalidate here in fact
		//because if the scroll changed with text change, the 
		//scroll event will be fired see below handler
	}
	
	private void  __onTextAreaTextScroll (Event e ){
    	if(viewportSizeTesting){
    		return;
    	}
    	TextField t =getTextField ();
    	if(focusScrolling){//avoid scroll change when make focus
    		IntPoint vp =getViewPosition ();
    		t.scrollH = vp.x;
    		t.scrollV = vp.y + 1;
    		return;
    	}
		IntPoint newViewPos =new IntPoint(t.scrollH ,t.scrollV -1);
		if(!getViewPosition().equals(newViewPos)){
			viewPos.setLocation(newViewPos);
			//notify scroll bar to syn
			fireStateChanged(true);
		}
		if(lastMaxScrollV != t.maxScrollV || lastMaxScrollH != t.maxScrollH){
			lastMaxScrollV = t.maxScrollV;
			lastMaxScrollH = t.maxScrollH;
			revalidate();
		}
	}
	
	private boolean focusScrolling =false ;
	 public void  makeFocus (){
		if(getFocusTransmit() == null){
			focusScrolling = true;
			super.makeFocus();
			focusScrolling = false;
		}
	}
	
	//------------------------------------------------------------
	//                    Viewportable Imp
	//------------------------------------------------------------
	
	/**
	 * Add a listener to listen the viewpoat state change event.
	 * <p>
	 * When the viewpoat's state changed, the state is all about:
	 * <ul>
	 * <li>viewPosition</li>
	 * <li>verticalUnitIncrement</li>
	 * <li>verticalBlockIncrement</li>
	 * <li>horizontalUnitIncrement</li>
	 * <li>horizontalBlockIncrement</li>
	 * </ul>
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	public void  addStateListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.STATE_CHANGED, listener, false, priority);
	}	
	
	/**
	 * Removes a state listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#STATE_CHANGED
	 */	
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}
	
	/**
	 * Returns the unit value for the Vertical scrolling.
	 */
    public int  getVerticalUnitIncrement (){
    	if(verticalUnitIncrement == AUTO_INCREMENT){
    		return 1;
    	}else{
    		return verticalUnitIncrement;
    	}
    }
    
    /**
     * Return the block value for the Vertical scrolling.
     */
    public int  getVerticalBlockIncrement (){
    	if(verticalBlockIncrement == AUTO_INCREMENT){
    		return 10;
    	}else{
    		return verticalBlockIncrement;
    	}
    }
    
	/**
	 * Returns the unit value for the Horizontal scrolling.
	 */
    public int  getHorizontalUnitIncrement (){
    	if(horizontalUnitIncrement == AUTO_INCREMENT){
    		return getColumnWidth();
    	}else{
    		return horizontalUnitIncrement;
    	}
    }
    
    /**
     * Return the block value for the Horizontal scrolling.
     */
    public int  getHorizontalBlockIncrement (){
    	if(horizontalBlockIncrement == AUTO_INCREMENT){
    		return getColumnWidth()*10;
    	}else{
    		return horizontalBlockIncrement;
    	}
    }
    
	/**
	 * Sets the unit value for the Vertical scrolling.
	 */
    public void  setVerticalUnitIncrement (int increment ){
    	if(verticalUnitIncrement != increment){
    		verticalUnitIncrement = increment;
			fireStateChanged();
    	}
    }
    
    /**
     * Sets the block value for the Vertical scrolling.
     */
    public void  setVerticalBlockIncrement (int increment ){
    	if(verticalBlockIncrement != increment){
    		verticalBlockIncrement = increment;
			fireStateChanged();
    	}
    }
    
	/**
	 * Sets the unit value for the Horizontal scrolling.
	 */
    public void  setHorizontalUnitIncrement (int increment ){
    	if(horizontalUnitIncrement != increment){
    		horizontalUnitIncrement = increment;
			fireStateChanged();
    	}
    }
    
    /**
     * Sets the block value for the Horizontal scrolling.
     */
    public void  setHorizontalBlockIncrement (int increment ){
    	if(horizontalBlockIncrement != increment){
    		horizontalBlockIncrement = increment;
			fireStateChanged();
    	}
    }

	/**
	 * Scrolls to view bottom left content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */
	public void  scrollToBottomLeft (){
		setViewPosition(new IntPoint(0, int.MAX_VALUE));
	}
	/**
	 * Scrolls to view bottom right content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToBottomRight (){
		setViewPosition(new IntPoint(int.MAX_VALUE, int.MAX_VALUE));
	}
	/**
	 * Scrolls to view top left content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToTopLeft (){
		setViewPosition(new IntPoint(0, 0));
	}
	/**
	 * Scrolls to view to right content. 
	 * This will make the scrollbars of <code>JScrollPane</code> scrolled automatically, 
	 * if it is located in a <code>JScrollPane</code>.
	 */	
	public void  scrollToTopRight (){
		setViewPosition(new IntPoint(int.MAX_VALUE, 0));
	}	    
	
	public void  scrollRectToVisible (IntRectangle contentRect ,boolean programmatic =true ){
		setViewPosition(new IntPoint(contentRect.x, contentRect.y), programmatic);
	}
	
	public void  setViewPosition (IntPoint p ,boolean programmatic =true ){
		if(!viewPos.equals(p)){
			restrictionViewPos(p);
			if(viewPos.equals(p)){
				return;
			}
			viewPos.setLocation(p);
			validateScroll();
			fireStateChanged(programmatic);
		}
	}
	
	public void  setViewportTestSize (IntDimension s ){
    	viewportSizeTesting = true;
    	setSize(s);
    	validateScroll();
    	viewportSizeTesting = false;
	}
	
	public IntDimension  getViewSize (){
    	TextField t =getTextField ();
    	int wRange ,hRange int ;
    	if(isWordWrap()){
    		wRange = t.textWidth;
    		t.scrollH = 0;
    	}else{
	    	if(t.maxScrollH > 0){
    			wRange = t.textWidth + t.maxScrollH;
	    	}else{
    			wRange = t.textWidth;
    			t.scrollH = 0;
	    	}
    	}
		int extent =t.bottomScrollV -t.scrollV +1;
		int maxValue =t.maxScrollV +extent ;
		int minValue =1;
    	hRange = maxValue - minValue;
    	return new IntDimension(wRange, hRange);
	}
	
	public IntDimension  getExtentSize (){
    	TextField t =getTextField ();
		int extentVer =t.bottomScrollV -t.scrollV +1;
		int extentHor =t.textWidth ;
    	return new IntDimension(extentHor, extentVer);
	}
	
	public Component  getViewportPane (){
		return this;
	}
	
	public IntPoint  getViewPosition (){
		return viewPos.clone();
	}
	
	/**
	 * Scroll the text with viewpos
	 */
    protected void  validateScroll (){
		int xS =viewPos.x ;
		int yS =viewPos.y +1;
    	TextField t =getTextField ();
		if(t.scrollH != xS){
			t.scrollH = xS;
		}
		if(t.scrollV != yS){
			t.scrollV = yS;
		}
		//t.background = false; //avoid TextField background lose effect bug
    }
	
	protected IntPoint  restrictionViewPos (IntPoint p ){
		IntPoint maxPos =getViewMaxPos ();
		p.x = Math.max(0, Math.min(maxPos.x, p.x));
		p.y = Math.max(0, Math.min(maxPos.y, p.y));
		return p;
	}
	
	private IntPoint  getViewMaxPos (){
		IntDimension showSize =getExtentSize ();
		IntDimension viewSize =getViewSize ();
		IntPoint p =new IntPoint(viewSize.width -showSize.width ,viewSize.height -showSize.height );
		if(p.x < 0) p.x = 0;
		if(p.y < 0) p.y = 0;
		return p;
	}
}


