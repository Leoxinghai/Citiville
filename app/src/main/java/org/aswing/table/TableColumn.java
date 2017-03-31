/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table;

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


import org.aswing.Component;
import org.aswing.event.*;
import flash.events.EventDispatcher;
	
/**
 * Dispatched when a property changed.
 * @eventType org.aswing.event.PropertyChangeEvent.PROPERTY_CHANGE
 */
.get(Event(name="propertyChange", type="org.aswing.event.PropertyChangeEvent"))

/**
 *  A <code>TableColumn</code> represents all the attributes of a column in a
 *  <code>JTable</code>, such as width, resizibility, minimum and maximum width.
 *  In addition, the <code>TableColumn</code> provides slots for a renderer and 
 *  an editor that can be used to display and edit the values in this column. 
 *  <p>
 *  It is also possible to specify renderers and editors on a per type basis
 *  rather than a per column basis - see the 
 *  <code>setDefaultRenderer</code> method in the <code>JTable</code> class.
 *  This default mechanism is only used when the renderer (or
 *  editor) in the <code>TableColumn</code> is <code>null</code>.
 * <p>
 *  The <code>TableColumn</code> stores the link between the columns in the
 *  <code>JTable</code> and the columns in the <code>TableModel</code>.
 *  The <code>modelIndex</code> is the column in the
 *  <code>TableModel</code>, which will be queried for the data values for the
 *  cells in this column. As the column moves around in the view this
 *  <code>modelIndex</code> does not change.
 *  <p>
 * <b>Note:</b> Some implementations may assume that all 
 *    <code>TableColumnModel</code>s are unique, therefore we would 
 *    recommend that the same <code>TableColumn</code> instance
 *    not be added more than once to a <code>TableColumnModel</code>.
 *    To show <code>TableColumn</code>s with the same column of
 *    data from the model, create a new instance with the same
 *    <code>modelIndex</code>.
 *  <p>
 * @author iiley
 */
public class TableColumn extends EventDispatcher{
	
	public static  String COLUMN_WIDTH_PROPERTY ="columWidth";
	public static  String HEADER_VALUE_PROPERTY ="headerValue";
	public static  String HEADER_RENDERER_PROPERTY ="headerRenderer";
	public static  String CELL_RENDERER_PROPERTY ="cellRenderer";
	
	private int modelIndex ;
	private Object identifier ;
	private int width ;
	private int minWidth ;
	private int preferredWidth ;
	private int maxWidth ;
	private TableCellFactory headerRenderer ;
	private Object headerValue ;
	private TableCellFactory cellRenderer ;
	private TableCellEditor cellEditor ;
	private boolean isResizable ;
	
	/**
	 * Create a TableColumn.
	 * @param modelIndex modelIndex
	 * @param width the    (optional)width of the column, default to 75
	 * @param cellRenderer (optional)the cell renderer for this column cells
	 * @param cellEditor   (optional)the cell editor for this column cells
	 */
	public  TableColumn (int modelIndex =0,int width =75,TableCellFactory cellRenderer =null ,TableCellEditor cellEditor =null ){
		this.modelIndex = modelIndex;
		this.width = width;
		this.preferredWidth = width;
		this.cellRenderer = cellRenderer;
		this.cellEditor = cellEditor;
		minWidth = 17;
		maxWidth = 100000; //default max width
		isResizable = true;
		//resizedPostingDisableCount = 0;
		headerValue = null;
	}
	
	protected void  firePropertyChangeIfReallyChanged (String propertyName ,*oldValue ,*)newValue {
		if(oldValue != newValue){
			dispatchEvent(new PropertyChangeEvent(propertyName, oldValue, newValue));
		}
	}
	
	public void  setModelIndex (int modelIndex ){
		int old =this.modelIndex ;
		this.modelIndex = modelIndex;
		firePropertyChangeIfReallyChanged("modelIndex", old, modelIndex);
	}
	
	public int  getModelIndex (){
		return modelIndex;
	}
	
	public void  setIdentifier (Object identifier ){
		Object old =this.identifier ;
		this.identifier = identifier;
		firePropertyChangeIfReallyChanged("identifier", old, identifier);
	}
	
	public Object  getIdentifier (){
		return (((identifier != null) ? identifier : getHeaderValue()));
	}
	
	public void  setHeaderValue (Object headerValue ){
		Object old =this.headerValue ;
		this.headerValue = headerValue;
		firePropertyChangeIfReallyChanged("headerValue", old, headerValue);
	}
	
	public Object  getHeaderValue (){
		return headerValue;
	}
	
	public void  setHeaderCellFactory (TableCellFactory headerRenderer ){
		TableCellFactory old =this.headerRenderer ;
		this.headerRenderer = headerRenderer;
		firePropertyChangeIfReallyChanged("headerRenderer", old, headerRenderer);
	}
	
	public TableCellFactory  getHeaderCellFactory (){
		return headerRenderer;
	}
	
	public void  setCellFactory (TableCellFactory cellRenderer ){
		TableCellFactory old =this.cellRenderer ;
		this.cellRenderer = cellRenderer;
		firePropertyChangeIfReallyChanged("cellRenderer", old, cellRenderer);
	}
	
	public TableCellFactory  getCellFactory (){
		return cellRenderer;
	}
	
	public void  setCellEditor (TableCellEditor cellEditor ){
		TableCellEditor old =this.cellEditor ;
		this.cellEditor = cellEditor;
		firePropertyChangeIfReallyChanged("cellEditor", old, cellEditor);
	}
	
	public TableCellEditor  getCellEditor (){
		return cellEditor;
	}
	
	public void  setWidth (int width ){
		int old =this.width ;
		this.width = Math.min(Math.max(width, minWidth), maxWidth);
		firePropertyChangeIfReallyChanged("width", old, this.width);
	}
	
	public int  getWidth (){
		return width;
	}
	
	public void  setPreferredWidth (int preferredWidth ){
		int old =this.preferredWidth ;
		this.preferredWidth = Math.min(Math.max(preferredWidth, minWidth), maxWidth);
		firePropertyChangeIfReallyChanged("preferredWidth", old, this.preferredWidth);
	}
	
	public int  getPreferredWidth (){
		return preferredWidth;
	}
	
	public void  setMinWidth (int minWidth ){
		int old =this.minWidth ;
		this.minWidth = Math.max(minWidth, 0);
		if (width < minWidth){
			setWidth(minWidth);
		}
		if (preferredWidth < minWidth){
			setPreferredWidth(minWidth);
		}
		firePropertyChangeIfReallyChanged("minWidth", old, this.minWidth);
	}
	
	public int  getMinWidth (){
		return minWidth;
	}
	
	public void  setMaxWidth (int maxWidth ){
		int old =this.maxWidth ;
		this.maxWidth = Math.max(minWidth, maxWidth);
		if (width > maxWidth){
			setWidth(maxWidth);
		}
		if (preferredWidth > maxWidth){
			setPreferredWidth(maxWidth);
		}
		firePropertyChangeIfReallyChanged("maxWidth", old, this.maxWidth);
	}
	
	public int  getMaxWidth (){
		return maxWidth;
	}
	
	public void  setResizable (boolean isResizable ){
		boolean old =this.isResizable ;
		this.isResizable = isResizable;
		firePropertyChangeIfReallyChanged("isResizable", old, this.isResizable);
	}
	
	public boolean  getResizable (){
		return isResizable;
	}
	
    /**
     * Resizes the <code>TableColumn</code> to fit the width of its header cell.
     * This method does nothing if the header renderer is <code>null</code>
     * (the default case). Otherwise, it sets the minimum, maximum and preferred 
     * widths of this column to the widths of the minimum, maximum and preferred 
     * sizes of the Component delivered by the header renderer. 
     * The transient "width" property of this TableColumn is also set to the 
     * preferred width. Note this method is not used internally by the table 
*package .;

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

     *
     * @see	#setPreferredWidth()
     */	
	public void  sizeWidthToFit (){
		if (headerRenderer == null){
			return;
		}
		TableCell cell =headerRenderer.createNewCell(true );
		cell.setCellValue(getHeaderValue());
		Component c =cell.getCellComponent ();
		setMinWidth(c.getMinimumSize().width);
		setMaxWidth(c.getMaximumSize().width);
		setPreferredWidth(c.getPreferredSize().width);
		setWidth(getPreferredWidth());
	}
	
    /**
     * Adds a property change listener.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.PropertyChangeEvent#PROPERTY_CHANGE
     */
    public void  addPropertyChangeListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, listener, false, priority, useWeakReference);
    }
	/**
	 * Removes a property change listener.
	 * @param listener the listener to be removed.
	 */
	public void  removePropertyChangeListener (Function listener ){
		removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, listener);
	}
	
	public TableCellFactory  createDefaultHeaderRenderer (){
		TableCellFactory factory =new GeneralTableCellFactoryUIResource(DefaultTextCell );
		//TODO header cell
		return factory;
	}
}


