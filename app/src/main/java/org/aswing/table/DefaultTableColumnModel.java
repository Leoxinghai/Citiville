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


import org.aswing.DefaultListSelectionModel;
import org.aswing.ListSelectionModel;
import org.aswing.util.ArrayUtils;
import org.aswing.event.PropertyChangeEvent;
import org.aswing.event.SelectionEvent;

/**
 * The standard column-handler for a <code>JTable</code>.
 * <p>
 * @author iiley
 */
public class DefaultTableColumnModel implements TableColumnModel{
	
	/** Array of TableColumn objects in this model */
	private Array tableColumns ;
	/** Model for keeping track of column selections */
	private ListSelectionModel selectionModel ;
	/** Width margin between each column */
	private int columnMargin ;
	/** List of TableColumnModelListener */
	private Array listenerList ;
	/** Column selection allowed in this column model */
	private boolean columnSelectionAllowed ;
	/** A local cache of the combined width of all columns */
	private int totalColumnWidth ;
		
	/**
	 * Creates a default table column model.
	 */	
	public  DefaultTableColumnModel (){
		tableColumns = new Array();
		listenerList = new Array();
		
		setSelectionModel(createSelectionModel());
		setColumnMargin(1);
		invalidateWidthCache();
		setColumnSelectionAllowed(false);
	}
	
	/**
	 *  Appends <code>aColumn</code> to the end of the
	 *  <code>tableColumns</code> array.
	 *  This method also posts the <code>columnAdded</code>
	 *  event to its listeners.
	 *
	 * @param aColumn the <code>TableColumn</code> to be added
	 * @see	#removeColumn()
	 */	
	public void  addColumn (TableColumn aColumn ){
		if (aColumn == null){
			trace("Adding null column ignored");
			return;
		}
		tableColumns.push(aColumn);
		aColumn.addPropertyChangeListener(__propertyChanged);
		invalidateWidthCache();
		checkLeadAnchor();
		fireColumnAdded(new TableColumnModelEvent(this, 0, (getColumnCount() - 1)));
	}
	
	/**
	 *  Deletes the <code>column</code> from the
	 *  <code>tableColumns</code> array.  This method will do nothing if
	 *  <code>column</code> is not in the table's columns list.
	 *  <code>tile</code> is called
	 *  to resize both the header and table views.
	 *  This method also posts a <code>columnRemoved</code>
	 *  event to its listeners.
	 *
	 * @param column the <code>TableColumn</code> to be removed
	 * @see	#addColumn()
	 */	
	public void  removeColumn (TableColumn column ){
		int columnIndex =ArrayUtils.indexInArray(tableColumns ,column );
		if (columnIndex != (- 1)){
			if (selectionModel != null){
				selectionModel.removeIndexInterval(columnIndex, columnIndex);
			}
			checkLeadAnchor();
			column.removePropertyChangeListener(__propertyChanged);
			tableColumns.splice(columnIndex, 1);
			invalidateWidthCache();
			fireColumnRemoved(new TableColumnModelEvent(this, columnIndex, 0));
		}
	}
	
	/**
	 * Moves the column and heading at <code>columnIndex</code> to
	 * <code>newIndex</code>.  The old column at <code>columnIndex</code>
	 * will now be found at <code>newIndex</code>.  The column
	 * that used to be at <code>newIndex</code> is shifted
	 * left or right to make room.  This will not move any columns if
	 * <code>columnIndex</code> equals <code>newIndex</code>.  This method
	 * also posts a <code>columnMoved</code> event to its listeners.
	 *
	 * @param columnIndex the index of column to be moved
	 * @param newIndex	  new index to move the column
	 * @exception Error	if <code>column</code> or
	 * 						<code>newIndex</code>
	 *						are not in the valid range
	 */	
	public void  moveColumn (int columnIndex ,int newIndex ){
		if ((((columnIndex < 0) || (columnIndex >= getColumnCount())) || (newIndex < 0)) || (newIndex >= getColumnCount())){
			trace("Error : moveColumn() - Index out of range");
			throw new Error("moveColumn() - Index out of range");
			return;
		}
		TableColumn aColumn ;
		if (columnIndex == newIndex){
			fireColumnMoved(new TableColumnModelEvent(this, columnIndex, newIndex));
			return ;
		}
		aColumn = TableColumn(tableColumns.get(columnIndex));
		tableColumns.splice(columnIndex, 1);
		boolean selected =selectionModel.isSelectedIndex(columnIndex );
		selectionModel.removeIndexInterval(columnIndex, columnIndex);
		tableColumns.splice(newIndex, 0, aColumn);
		selectionModel.insertIndexInterval(newIndex, 1, true);
		if (selected){
			selectionModel.addSelectionInterval(newIndex, newIndex);
		}else{
			selectionModel.removeSelectionInterval(newIndex, newIndex);
		}
		fireColumnMoved(new TableColumnModelEvent(this, columnIndex, newIndex));
	}
	
	/**
	 * Sets the column margin to <code>newMargin</code>.  This method
	 * also posts a <code>columnMarginChanged</code> event to its
	 * listeners.
	 *
	 * @param newMargin the new margin width, in pixels
	 * @see	#getColumnMargin()
	 * @see	#getTotalColumnWidth()
	 */	
	public void  setColumnMargin (int newMargin ){
		if (newMargin != columnMargin){
			columnMargin = newMargin;
			fireColumnMarginChanged();
		}
	}
	
	/**
	 * Returns the number of columns in the <code>tableColumns</code> array.
	 *
	 * @return	the number of columns in the <code>tableColumns</code> array
	 * @see	#getColumns()
	 */	
	public int  getColumnCount (){
		return tableColumns.length;
	}
	
	/**
	 * Returns an <code>Array</code> of all the columns in the model.
	 * @return an <code>Array</code> of the columns in the model
	 */	
	public Array  getColumns (){
		return tableColumns.concat();
	}
	
	/**
	 * Returns the index of the first column in the <code>tableColumns</code>
	 * array whose identifier is equal to <code>identifier</code>,
	 * when compared using <code>equals</code>.
	 *
	 * @param identifier the identifier object
	 * @return the index of the first column in the 
	 *			<code>tableColumns</code> array whose identifier
	 *			is equal to <code>identifier</code>
	 * @exception Error  if <code>identifier</code>
	 *				is <code>null</code>, or if no
	 *				<code>TableColumn</code> has this
	 *				<code>identifier</code>
	 * @see #getColumn()
	 */	
	public int  getColumnIndex (Object identifier ){
		if (identifier == null){
			trace("Error : Identifier is null");
			throw new Error("Identifier is null");
		}
		Array enumeration =getColumns ();
		TableColumn aColumn ;
		int index =0;
		for(int i =0;i <enumeration.length ;i ++){
			aColumn = TableColumn(enumeration.get(i));
			if (identifier == aColumn.getIdentifier()){
				return index;
			}
			index++;
		}
		trace("Error : Identifier is null");
		throw new Error("Identifier not found");
	}
	
	/**
	 * Returns the <code>TableColumn</code> object for the column
	 * at <code>columnIndex</code>.
	 *
	 * @param	columnIndex	the index of the column desired
	 * @return	the <code>TableColumn</code> object for the column
	 *				at <code>columnIndex</code>
	 */	
	public TableColumn  getColumn (int columnIndex ){
		return TableColumn(tableColumns.get(columnIndex));
	}
	
	/**
	 * Returns the width margin for <code>TableColumn</code>.
	 * The default <code>columnMargin</code> is 1.
	 *
	 * @return the maximum width for the <code>TableColumn</code>
	 * @see	#setColumnMargin()
	 */	
	public int  getColumnMargin (){
		return columnMargin;
	}
	
	/**
	 * Returns the index of the column that lies at position <code>x</code>,
	 * or -1 if no column covers this point.
	 * <p>
	 * In keeping with Swing's separable model architecture, a
	 * TableColumnModel does not know how the table columns actually appear on
	 * screen.  The visual presentation of the columns is the responsibility
	 * of the view/controller object using this model (typically JTable).  The
	 * view/controller need not display the columns sequentially from left to
	 * right.  For example, columns could be displayed from right to left to
	 * accomodate a locale preference or some columns might be hidden at the
	 * request of the user.  Because the model does not know how the columns
	 * are laid out on screen, the given <code>xPosition</code> should not be
	 * considered to be a coordinate in 2D graphics space.  Instead, it should
	 * be considered to be a width from the start of the first column in the
	 * model.  If the column index for a given X coordinate in 2D space is
	 * required, <code>JTable.columnAtPoint</code> can be used instead.
	 *
	 * @param x  the horizontal location of interest
	 * @return the index of the column or -1 if no column is found
	 * @see org.aswing.JTable#columnAtPoint()
	 */	
	public int  getColumnIndexAtX (int x ){
		if (x < 0){
			return -1;
		}
		int cc =getColumnCount ();
		for(int column =0;column <cc ;column ++){
			x = (x - getColumn(column).getWidth());
			if (x < 0){
				return column;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the total combined width of all columns.
	 * @return the <code>totalColumnWidth</code> property
	 */	
	public int  getTotalColumnWidth (){
		if (totalColumnWidth == (-1)){
			recalcWidthCache();
		}
		return totalColumnWidth;
	}
	
	/**
	 *  Sets the selection model for this <code>TableColumnModel</code>
	 *  to <code>newModel</code>
	 *  and registers for listener notifications from the new selection
	 *  model.  If <code>newModel</code> is <code>null</code>,
	 *  nothing will be changed. 
	 *
	 * @param newModel the new selection model
	 * @see	#getSelectionModel()
	 */	
	public void  setSelectionModel (ListSelectionModel newModel ){
		if (newModel == null){
			trace("Setting null ListSelectionModel ignored");
			return;
		}
		ListSelectionModel oldModel =selectionModel ;
		if (newModel != oldModel){
			if (oldModel != null){
				oldModel.removeListSelectionListener(__selectionChanged);
			}
			selectionModel = newModel;
			newModel.addListSelectionListener(__selectionChanged);
			checkLeadAnchor();
		}
	}
	
	/**
	 * Returns the <code>ListSelectionModel</code> that is used to
	 * maintain column selection state.
	 *
	 * @return	the object that provides column selection state.  Or
	 *		<code>null</code> if row selection is not allowed.
	 * @see	#setSelectionModel()
	 */	
	public ListSelectionModel  getSelectionModel (){
		return selectionModel;
	}
	
	/**
	 * Initialize the lead and anchor of the selection model
	 * based on what the column model contains.
	 */	
	private void  checkLeadAnchor (){
		int lead =selectionModel.getLeadSelectionIndex ();
		int count =tableColumns.length ;
		if (count == 0){
			if (lead != (- 1)){
				//TODO check if this is needed to add
				//selectionModel.setValueIsAdjusting(true);
				selectionModel.setAnchorSelectionIndex(- 1);
				selectionModel.setLeadSelectionIndex(- 1);
				//selectionModel.setValueIsAdjusting(false);
			}
		}else{
			if (lead == (- 1)){
				if (selectionModel.isSelectedIndex(0)){
					selectionModel.addSelectionInterval(0, 0);
				}else{
					selectionModel.removeSelectionInterval(0, 0);
				}
			}
		}
	}
	
	/**
	 * Sets whether column selection is allowed.  The default is false.
	 * @param  flag true if column selection will be allowed, false otherwise
	 */	
	public void  setColumnSelectionAllowed (boolean flag ){
		columnSelectionAllowed = flag;
	}
	/**
	 * Returns true if column selection is allowed, otherwise false.
	 * The default is false.
	 * @return the <code>columnSelectionAllowed</code> property
	 */	
	public boolean  getColumnSelectionAllowed (){
		return columnSelectionAllowed;
	}
	/**
	 * Returns an array of selected columns.  If <code>selectionModel</code>
	 * is <code>null</code>, returns an empty array.
	 * @return an array of selected columns or an empty array if nothing
	 *			is selected or the <code>selectionModel</code> is
	 *			<code>null</code>
	 */	
	public Array  getSelectedColumns (){
		if (selectionModel != null){
			int iMin =selectionModel.getMinSelectionIndex ();
			int iMax =selectionModel.getMaxSelectionIndex ();
			if ((iMin == (- 1)) || (iMax == (- 1))){
				return new Array();
			}
			Array rv =new Array ();
			for(int i =iMin ;i <=iMax ;i ++){
				if (selectionModel.isSelectedIndex(i)){
					rv.push(i);
				}
			}
			return rv;
		}
		return new Array();
	}
	
	/**
	 * Returns the number of columns selected.
	 * @return the number of columns selected
	 */	
	public int  getSelectedColumnCount (){
		if (selectionModel != null){
			int iMin =selectionModel.getMinSelectionIndex ();
			int iMax =selectionModel.getMaxSelectionIndex ();
			int count =0;
			for(int i =iMin ;i <=iMax ;i ++){
				if (selectionModel.isSelectedIndex(i)){
					count++;
				}
			}
			return count;
		}
		return 0;
	}
	
	public void  addColumnModelListener (TableColumnModelListener x ){
		listenerList.push(x);
	}
	
	public void  removeColumnModelListener (TableColumnModelListener x ){
		ArrayUtils.removeFromArray(listenerList, x);
	}
	
	public Array  getColumnModelListeners (){
		return listenerList.concat();
	}
	
	private void  fireColumnAdded (TableColumnModelEvent e ){
		Array listeners =listenerList ;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =(listeners.length -1);i >=0;i --){
			TableColumnModelListener lis =TableColumnModelListener(listeners.get(i) );
			lis.columnAdded(e);
		}
	}
	
	private void  fireColumnRemoved (TableColumnModelEvent e ){
		Array listeners =listenerList ;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =(listeners.length -1);i >=0;i --){
			TableColumnModelListener lis =TableColumnModelListener(listeners.get(i) );
			lis.columnRemoved(e);
		}
	}
	
	private void  fireColumnMoved (TableColumnModelEvent e ){
		Array listeners =listenerList ;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =(listeners.length -1);i >=0;i --){
			TableColumnModelListener lis =TableColumnModelListener(listeners.get(i) );
			lis.columnMoved(e);
		}
	}
	
	private void  fireColumnSelectionChanged (int firstIndex ,int lastIndex ,boolean programmatic ){
		Array listeners =listenerList ;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =(listeners.length -1);i >=0;i --){
			TableColumnModelListener lis =TableColumnModelListener(listeners.get(i) );
			lis.columnSelectionChanged(this, firstIndex, lastIndex, programmatic);
		}
	}
	
	private void  fireColumnMarginChanged (){
		Array listeners =listenerList ;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i =(listeners.length -1);i >=0;i --){
			TableColumnModelListener lis =TableColumnModelListener(listeners.get(i) );
			lis.columnMarginChanged(this);
		}
	}
	
	public Array  getListeners (){
		return listenerList.concat();
	}
	
	private void  __propertyChanged (PropertyChangeEvent e ){
		if ((e.getPropertyName() == "width") || (e.getPropertyName() == "preferredWidth")){
			invalidateWidthCache();
			// This is a misnomer, we're using this method 
			// simply to cause a relayout. 
			fireColumnMarginChanged();
		}
	}
	
	private void  __selectionChanged (SelectionEvent e ){
		fireColumnSelectionChanged(e.getFirstIndex(), e.getLastIndex(), e.isProgrammatic());
	}
	
	private ListSelectionModel  createSelectionModel (){
		return new DefaultListSelectionModel();
	}
	
	/**
	 * Recalculates the total combined width of all columns.  Updates the
	 * <code>totalColumnWidth</code> property.
	 */	
	private void  recalcWidthCache (){
		Array enumeration =tableColumns ;
		totalColumnWidth = 0;
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn c =enumeration.get(i) ;
			totalColumnWidth += c.getWidth();
		}
	}
	
	private void  invalidateWidthCache (){
		totalColumnWidth = -1;
	}
	
	public String  toString (){
		return "DefaultTableColumnModel[]";
	}
}


