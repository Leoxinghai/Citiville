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


import org.aswing.event.TableModelEvent;
import org.aswing.event.TableModelListener;
import org.aswing.util.ArrayUtils;
import org.aswing.error.ImpMissError;

/**
 *  This abstract class provides default implementations for most of
 *  the methods in the <code>TableModel</code> interface. It takes care of
 *  the management of listeners and provides some conveniences for generating
 *  <code>TableModelEvents</code> and dispatching them to the listeners.
 *  To create a concrete <code>TableModel</code> as a subclass of
 *  <code>AbstractTableModel</code> you need only provide implementations
 *  for the following three methods:
 *
 *  <pre>
 *public double  getRowCount ();
 *public double  getColumnCount ();
 *public  getValueAt (double row ,double column );
 *  </pre>
 *
 * @author iiley
 */
public class AbstractTableModel implements TableModel{

	/** List of listeners */
	protected Array listenerList ;

	protected Array columnClasses ;

	public  AbstractTableModel (){
		listenerList = new Array();
		columnClasses = new Array();
	}

	/**
	 *Subclass must  this method .
	 */
	public int  getRowCount (){
		throw new ImpMissError();
		return -1;
	}

	/**
	 *Subclass must  this method .
	 */
	public int  getColumnCount (){
		throw new ImpMissError();
		return -1;
	}

	/**
	 *Subclass must  this method .
	 */
	public Object getValueAt (int rowIndex ,int columnIndex ) {
		throw new ImpMissError();
		return null;
	}

	/**
	 *  Returns a default name for the column using spreadsheet conventions:
	 *  A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
	 *  returns an empty string.
	 *
	 * @param column  the column being queried
	 * @return a string containing the default name of <code>column</code>
	 */
	public String  getColumnName (int column ){
		return String.fromCharCode(32+column%26);
	}

	/**
	 * Returns a column given its name.
	 * Implementation is naive so this should be overridden if
	 * this method is to be called often. This method is not
	 * in the <code>TableModel</code> interface and is not used by the
	 * <code>JTable</code>.
	 *
	 * @param columnName string containing name of column to be located
	 * @return the column with <code>columnName</code>, or -1 if not found
	 */
	public int  findColumn (String columnName ){
		for(int i =0;i <getColumnCount ();i ++){
			if (columnName == getColumnName(i)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns class name regardless of <code>columnIndex</code>.
	 *
	 * @param columnIndex  the column being queried
	 * @return the class name, default is "Object"
	 */
	public String  getColumnClass (int columnIndex ){
		if(columnClasses.get(columnIndex) == undefined){
			return "Object";
		}else{
			return columnClasses.get(columnIndex);
		}
	}

	/**
	 * Sets class name regardless of <code>columnIndex</code>.
	 *
	 * @param columnIndex  the column being queried
	 * @param className the class name
	 */
	public void  setColumnClass (int columnIndex ,String className ){
		columnClasses.put(columnIndex,  className);
	}

	/**
	 *  Returns false.  This is the default implementation for all cells.
	 *
	 *  @param  rowIndex  the row being queried
	 *  @param  columnIndex the column being queried
	 *  @return false
	 */
	public boolean  isCellEditable (int rowIndex ,int columnIndex ){
		return false;
	}

	/**
	 *  This empty implementation is provided so users don't have to implement
	 *  this method if their data model is not editable.
	 *
	 *  @param  aValue   value to assign to cell
	 *  @param  rowIndex   row of cell
	 *  @param  columnIndex  column of cell
	 */
	public void  setValueAt (*aValue ,int rowIndex ,int columnIndex ){
	}


//
//  Managing Listeners
//

	/**
	 * Adds a listener to the list that's notified each time a change
	 * to the data model occurs.
	 *
	 * @param	l		the TableModelListener
	 */
	public void  addTableModelListener (TableModelListener l ){
		listenerList.push(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a
	 * change to the data model occurs.
	 *
	 * @param	l		the TableModelListener
	 */
	public void  removeTableModelListener (TableModelListener l ){
		ArrayUtils.removeFromArray(listenerList, l);
	}

	/**
	 * Returns an array of all the table model listeners
	 * registered on this model.
	 *
	 * @return all of this model's <code>TableModelListener</code>s
	 *		 or an empty
	 *		 array if no table model listeners are currently registered
	 *
	 * @see #addTableModelListener
	 * @see #removeTableModelListener
	 */
	public Array  getTableModelListeners (){
		return listenerList.concat();
	}

	//**********************************************************************
	//						  Fire methods
	//**********************************************************************

	/**
	 * Notifies all listeners that all cell values in the table's
	 * rows may have changed. The number of rows may also have changed
	 * and the <code>JTable</code> should redraw the
	 * table from scratch. The structure of the table (as in the order of the
	 * columns) is assumed to be the same.
	 *
	 * @see TableModelEvent
	 * @see EventListenerList
	 * @see javax.swing.JTable#tableChanged(TableModelEvent)
	 */
	protected void  fireTableDataChanged (){
		fireTableChanged(new TableModelEvent(this));
	}

	/**
	 * Notifies all listeners that the table's structure has changed.
	 * The number of columns in the table, and the names and types of
	 * the new columns may be different from the previous state.
	 * If the <code>JTable</code> receives this event and its
	 * <code>autoCreateColumnsFromModel</code>
	 * flag is set it discards any table columns that it had and reallocates
	 * default columns in the order they appear in the model. This is the
	 * same as calling <code>setModel(TableModel)</code> on the
	 * <code>JTable</code>.
	 *
	 * @see TableModelEvent
	 * @see EventListenerList
	 */
	protected void  fireTableStructureChanged (){
		fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
	}

	/**
	 * Notifies all listeners that rows in the range
	 * <code>.get(firstRow, lastRow)</code>, inclusive, have been inserted.
	 *
	 * @param  firstRow  the first row
	 * @param  lastRow   the last row
	 *
	 * @see TableModelEvent
	 * @see EventListenerList
	 *
	 */
	protected void  fireTableRowsInserted (int firstRow ,int lastRow ){
		fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	/**
	 * Notifies all listeners that rows in the range
	 * <code>.get(firstRow, lastRow)</code>, inclusive, have been updated.
	 *
	 * @param firstRow  the first row
	 * @param lastRow   the last row
	 *
	 * @see TableModelEvent
	 * @see EventListenerList
	 */
	protected void  fireTableRowsUpdated (int firstRow ,int lastRow ){
		fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
	}

	/**
	 * Notifies all listeners that rows in the range
	 * <code>.get(firstRow, lastRow)</code>, inclusive, have been deleted.
	 *
	 * @param firstRow  the first row
	 * @param lastRow   the last row
	 *
	 * @see TableModelEvent
	 * @see EventListenerList
	 */
	protected void  fireTableRowsDeleted (int firstRow ,int lastRow ){
		fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
	}

	/**
	 * Notifies all listeners that the value of the cell at
	 * <code>.get(row, column)</code> has been updated.
	 *
	 * @param row  row of cell which has been updated
	 * @param column  column of cell which has been updated
	 * @see TableModelEvent
	 * @see EventListenerList
	 */
	protected void  fireTableCellUpdated (int row ,int column ){
		fireTableChanged(new TableModelEvent(this, row, row, column));
	}

	/**
	 * Forwards the given notification event to all
	 * <code>TableModelListeners</code> that registered
	 * themselves as listeners for this table model.
	 *
	 * @param e  the event to be forwarded
	 *
	 * @see #addTableModelListener
	 * @see TableModelEvent
	 * @see EventListenerList
	 */
	protected void  fireTableChanged (TableModelEvent e ){
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =listenerList.length -1;i >=0;i --){
			TableModelListener lis =TableModelListener(listenerList.get(i) );
			lis.tableChanged(e);
		}
	}

	public String  toString (){
		return "AbstractTableModel[]";
	}
}


