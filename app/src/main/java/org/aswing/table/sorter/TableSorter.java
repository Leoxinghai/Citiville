/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table.sorter;

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


import flash.events.MouseEvent;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.table.AbstractTableModel;
import org.aswing.table.JTableHeader;
import org.aswing.table.TableCellFactory;
import org.aswing.table.TableColumnModel;
import org.aswing.table.TableModel;
import org.aswing.util.ArrayUtils;
import org.aswing.util.HashMap;

/**
 * A class that make your JTable sortable. Usage:
 * <pre>
 *TableSorter sorter =new TableSorter(yourTableModel );
 * sorter.setTableHeader(yourTable.getTableHeader());
 * yourTable.setModel(sorter);
 * </pre>
 * @author iiley
 */
public class TableSorter extends AbstractTableModel implements TableModelListener{

    public static int DESCENDING =-1;
    public static int NOT_SORTED =0;
    public static int ASCENDING =1;

    private static Directive EMPTY_DIRECTIVE ;
    public static Function NUMBER_COMAPRATOR ;
    public static Function LEXICAL_COMPARATOR ;

    private static boolean inited =false ;

    private TableModel tableModel ;
    private Array viewToModel ;//Row[]
    private Array modelToView ;//int[]
    private Array columnSortables ;

    private JTableHeader tableHeader ;
    private TableModelListener tableModelListener ;
    private HashMap columnComparators ;
    private Array sortingColumns ;

	/**
	 * TableSorter(tableModel:TableModel, tableHeader:JTableHeader)<br>
	 * TableSorter(tableModel:TableModel)<br>
	 * TableSorter()<br>
	 */
    public  TableSorter (TableModel tableModel ,JTableHeader tableHeader =null ){
        super();
        initStatics();
        columnComparators  = new HashMap();
        sortingColumns     = new Array();
        columnSortables    = new Array();
        tableModelListener = this;
        setTableHeader(tableHeader);
        setTableModel(tableModel);
    }

    private void  initStatics (){
        if(!inited){
			EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);

			NUMBER_COMAPRATOR =int  (*o1 ,*)o2 {
				o1 = Number(o1);
				o2 = Number(o2);
				return o1 == o2 ? 0 : (o1 > o2 ? 1 : -1);
		    };

			LEXICAL_COMPARATOR =int  (*o1 ,*)o2 {
		    	o1 = o1.toString();
		    	o2 = o2.toString();
				return o1 == o2 ? 0 : (o1 > o2 ? 1 : -1);
		    };
        	inited = true;
        }
    }

    private void  clearSortingState (){
        viewToModel = null;
        modelToView = null;
    }

    public TableModel  getTableModel (){
        return tableModel;
    }

	/**
	 * Sets the tableModel
	 * @param tableModel the tableModel
	 */
    public void  setTableModel (TableModel tableModel ){
        if (this.tableModel != null) {
            this.tableModel.removeTableModelListener(tableModelListener);
        }

        this.tableModel = tableModel;
        if (this.tableModel != null) {
            this.tableModel.addTableModelListener(tableModelListener);
        }

        clearSortingState();
        fireTableStructureChanged();
    }

    public JTableHeader  getTableHeader (){
        return tableHeader;
    }

	/**
	 * Sets the table header
	 * @param tableHeader the table header
	 */
    public void  setTableHeader (JTableHeader tableHeader ){
        if (this.tableHeader != null) {
            this.tableHeader.removeEventListener(MouseEvent.MOUSE_DOWN, __mousePress);
            this.tableHeader.removeEventListener(ReleaseEvent.RELEASE, __mouseRelease);
            TableCellFactory defaultRenderer =this.tableHeader.getDefaultRenderer ();
            if (defaultRenderer is SortableHeaderRenderer) {
                this.tableHeader.setDefaultRenderer((SortableHeaderRenderer(defaultRenderer)).getTableCellFactory());
            }
        }
        this.tableHeader = tableHeader;
        if (this.tableHeader != null) {
            this.tableHeader.addEventListener(MouseEvent.MOUSE_DOWN, __mousePress);
            this.tableHeader.addEventListener(ReleaseEvent.RELEASE, __mouseRelease);
            this.tableHeader.setDefaultRenderer(
                    new SortableHeaderRenderer(this.tableHeader.getDefaultRenderer(), this));
        }
    }

    public boolean  isSorting (){
        return sortingColumns.length != 0;
    }

    public Array  getSortingColumns (){
    	return sortingColumns;
    }

    /**
     * Sets specified column sortable, default is true.
     * @param column   column
     * @param sortable true to set the column sortable, false to not
     */
    public void  setColumnSortable (int column ,boolean sortable ){
    	if(isColumnSortable(column) != sortable){
    		columnSortables.put(column,  sortable);
    		if(!sortable && getSortingStatus(column) != NOT_SORTED){
    			setSortingStatus(column, NOT_SORTED);
    		}
    	}
    }

    /**
     * Returns specified column sortable, default is true.
     * @return true if the column is sortable, false otherwish
     */
    public boolean  isColumnSortable (int column ){
    	return columnSortables.get(column) != false;
    }

    private Directive  getDirective (int column ){
        for(int i =0;i <sortingColumns.length ;i ++){
            Directive directive =Directive(sortingColumns.get(i) );
            if (directive.column == column) {
                return directive;
            }
        }
        return EMPTY_DIRECTIVE;
    }

    public int  getSortingStatus (int column ){
        return getDirective(column).direction;
    }

    private void  sortingStatusChanged (){
        clearSortingState();
        fireTableDataChanged();
        if (tableHeader != null) {
            tableHeader.repaint();
        }
    }

	/**
	 * Sets specified column to be sort as specified direction.
	 * @param column the column to be sort
	 * @param status sort direction, should be one of these values:
	 * <ul>
	 * <li> DESCENDING : descending sort
	 * <li> NOT_SORTED : not sort
	 * <li> ASCENDING  : ascending sort
	 * </ul>
	 */
    public void  setSortingStatus (int column ,int status ){
        Directive directive =getDirective(column );
        if (directive != EMPTY_DIRECTIVE) {
        	ArrayUtils.removeFromArray(sortingColumns, directive);
        }
        if (status != NOT_SORTED) {
        	sortingColumns.push(new Directive(column, status));
        }
        sortingStatusChanged();
    }

    public Icon  getHeaderRendererIcon (int column ,int size ){
        Directive directive =getDirective(column );
        if (directive == EMPTY_DIRECTIVE) {
            return null;
        }
        return new Arrow(directive.direction == DESCENDING, size);//, sortingColumns.indexOf(directive));
    }

	/**
	 * Cancels all sorting column to be NOT_SORTED.
	 */
    public void  cancelSorting (){
        sortingColumns.splice(0);
        sortingStatusChanged();
    }

	/**
	 * Sets a comparator the specified columnClass. For example:
	 * <pre>
	 * setColumnComparator("Number", aNumberComparFunction);
	 * </pre>
	 * @param columnClass the column class name
	 * @param comparator the comparator function should be this spec:
	 *or or return should it int  (o1 ,o2 ),-101.
	 * @see org.aswing.table.TableModel#getColumnClass()
	 */
    public void  setColumnComparator (String columnClass ,Function comparator ){
        if (comparator == null) {
            columnComparators.remove(columnClass);
        } else {
            columnComparators.put(columnClass, comparator);
        }
    }

	/**
	 *Returns the comparator  for given column .
	 * @return the comparator function for given column.
	 * @see #setColumnComparator()
	 */
    public Function  getComparator (int column ){
        String columnType =tableModel.getColumnClass(column );
        Function comparator =columnComparators.get(columnType )as Function ;
        if (comparator != null) {
            return comparator;
        }
        if(columnType == "Number"){
			return NUMBER_COMAPRATOR;
        }else{
        	return LEXICAL_COMPARATOR;
        }
    }

    private Array  getViewToModel (){
        if (viewToModel == null) {
            int tableModelRowCount =tableModel.getRowCount ();
            viewToModel = new Array(tableModelRowCount);
            for(int row =0;row <tableModelRowCount ;row ++){
                viewToModel.put(row,  new Row(this, row));
            }

            if (isSorting()) {
                viewToModel.sort(sortImp);
            }
        }
        return viewToModel;
    }

    private int  sortImp (Row row1 ,Row row2 ){
    	return row1.compareTo(row2);
    }

	/**
	 * Calculates the model index from the sorted index.
	 * @return the index in model from the sorter model index
	 */
    public int  modelIndex (int viewIndex ){
        return getViewToModel().get(viewIndex).getModelIndex();
    }

    private int[] Array  getModelToView (){//
        if (modelToView == null) {
            int n =getViewToModel ().length ;
            modelToView = new Array(n);
            for(int i =0;i <n ;i ++){
                modelToView.put(modelIndex(i),  i);
            }
        }
        return modelToView;
    }

    // TableModel interface methods

     public int  getRowCount (){
        return (tableModel == null) ? 0 : tableModel.getRowCount();
    }

     public int  getColumnCount (){
        return (tableModel == null) ? 0 : tableModel.getColumnCount();
    }

     public String  getColumnName (int column ){
        return tableModel.getColumnName(column);
    }

     public String  getColumnClass (int column ){
        return tableModel.getColumnClass(column);
    }

     public boolean  isCellEditable (int row ,int column ){
        return tableModel.isCellEditable(modelIndex(row), column);
    }

     public Object getValueAt (int row ,int column ) {
        return tableModel.getValueAt(modelIndex(row), column);
    }

     public void  setValueAt (*aValue ,int row ,int column ){
        tableModel.setValueAt(aValue, modelIndex(row), column);
    }

    public void  tableChanged (TableModelEvent e ){
        // If we're not sorting by anything, just pass the event along.
        if (!isSorting()) {
            clearSortingState();
            fireTableChanged(e);
            return;
        }

        // If the table structure has changed, cancel the sorting; the
        // sorting columns may have been either moved or deleted from
        // the model.
        if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
            cancelSorting();
            fireTableChanged(e);
            return;
        }

        // We can map a cell event through to the view without widening
        // when the following conditions apply:
        //
        // a) all the changes are on one row (e.getFirstRow() == e.getLastRow()) and,
        // b) all the changes are in one column (column != TableModelEvent.ALL_COLUMNS) and,
        // c) we are not sorting on that column (getSortingStatus(column) == NOT_SORTED) and,
        // d) a reverse lookup will not trigger a sort (modelToView != null)
        //
        // Note: INSERT and DELETE events fail this test as they have column == ALL_COLUMNS.
        //
        // The last check, for (modelToView != null) is to see if modelToView
        // is already allocated. If we don't do this check; sorting can become
        // a performance bottleneck for applications where cells
        // change rapidly in different parts of the table. If cells
        // change alternately in the sorting column and then outside of
        // it this class can end up re-sorting on alternate cell updates -
        // which can be a performance problem for large tables. The last
        // clause avoids this problem.
        int column =e.getColumn ();
        if (e.getFirstRow() == e.getLastRow()
                && column != TableModelEvent.ALL_COLUMNS
                && getSortingStatus(column) == NOT_SORTED
                && modelToView != null) {
            int viewIndex =getModelToView ().get(e.getFirstRow ()) ;
            fireTableChanged(new TableModelEvent(this,
                                                 viewIndex, viewIndex,
                                                 column, e.getType()));
            return;
        }

        // Something has happened to the data that may have invalidated the row order.
        clearSortingState();
        fireTableDataChanged();
        return;
    }

    private IntPoint pressedPoint ;
    private void  __mousePress (MouseEvent e ){
    	JTableHeader header =(JTableHeader)e.currentTarget;
    	pressedPoint = header.getMousePosition();
    }

    private void  __mouseRelease (ReleaseEvent e ){
    	if(e.isReleasedOutSide()){
    		return;
    	}
        JTableHeader h =(JTableHeader)e.currentTarget;
        IntPoint point =h.getMousePosition ();
        //if user are dragging the header, not sort
        if(!point.equals(pressedPoint)){
        	return;
        }
        TableColumnModel columnModel =h.getColumnModel ();
        int viewColumn =columnModel.getColumnIndexAtX(h.getMousePosition ().x );
        if(viewColumn == -1){
        	return;
        }
        int column =columnModel.getColumn(viewColumn ).getModelIndex ();
        if (column != -1 && isColumnSortable(column)) {
            int status =getSortingStatus(column );
            if (!e.ctrlKey) {
                cancelSorting();
            }
            status = nextSortingStatus(status, e.shiftKey);
            setSortingStatus(column, status);
        }
    }

    // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or
    // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed.
    //You can  this method to change the arithmetic
    protected int  nextSortingStatus (int curStatus ,boolean shiftKey ){
    	int status =curStatus ;
	    status = status + (shiftKey ? -1 : 1);
        status = (status + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
        return status;
    }
}


