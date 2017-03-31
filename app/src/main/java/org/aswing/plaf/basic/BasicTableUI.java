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


import flash.display.Shape;
import flash.events.Event;
import flash.events.KeyboardEvent;
import flash.events.MouseEvent;
import flash.ui.Keyboard;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.table.*;

/**
 * @author iiley
 * @private
 */
public class BasicTableUI extends BaseComponentUI implements TableUI{
	
	protected JTable table ;
	protected Shape gridShape ;
	
	public  BasicTableUI (){
		super();
		focusRow = 0;
		focusColumn = 0;
	}
	
	 public void  installUI (Component c ){
		table = JTable(c);
		installDefaults();
		installListeners();
	}
	
	protected String  getPropertyPrefix (){
		return "Table.";
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(table, pp);
		LookAndFeel.installBorderAndBFDecorators(table, pp);
		LookAndFeel.installBasicProperties(table, pp);
		
		ASColor sbg =table.getSelectionBackground ();
		if (sbg == null || sbg is UIResource) {
			table.setSelectionBackground(getColor(pp+"selectionBackground"));
		}

		ASColor sfg =table.getSelectionForeground ();
		if (sfg == null || sfg is UIResource) {
			table.setSelectionForeground(getColor(pp+"selectionForeground"));
		}

		ASColor gridColor =table.getGridColor ();
		if (gridColor == null || gridColor is UIResource) {
			table.setGridColor(getColor(pp+"gridColor"));
		}
	}
	
	protected void  installListeners (){
		table.addEventListener(MouseEvent.MOUSE_DOWN, __onTablePress);
		table.addEventListener(ReleaseEvent.RELEASE, __onTableRelease);
		table.addEventListener(ClickCountEvent.CLICK_COUNT, __onTableClicked);
		table.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onTableKeyDown);
		table.addEventListener(MouseEvent.MOUSE_WHEEL, __onTableMouseWheel);
	}
	
	 public void  uninstallUI (Component c ){
		uninstallDefaults();
		uninstallListeners();
	}
	
	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(table);
	}
	
	protected void  uninstallListeners (){
		table.removeEventListener(MouseEvent.MOUSE_DOWN, __onTablePress);
		table.removeEventListener(ReleaseEvent.RELEASE, __onTableRelease);
		table.removeEventListener(ClickCountEvent.CLICK_COUNT, __onTableClicked);
		table.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onTableKeyDown);
		table.removeEventListener(MouseEvent.MOUSE_WHEEL, __onTableMouseWheel);
		table.removeEventListener(MouseEvent.MOUSE_MOVE, __onTableMouseMove);
	}
	
	protected void  __onTablePress (MouseEvent e ){
		if(table.getTableHeader().hitTestMouse()){
			return;
		}
		selectMousePointed(e);
		table.addEventListener(MouseEvent.MOUSE_MOVE, __onTableMouseMove);
		TableCellEditor editor =table.getCellEditor ();
		if(editor != null && editor.isCellEditing()){
			table.getCellEditor().stopCellEditing();
		}
	}
	
	private void  __onTableClicked (ClickCountEvent e ){
		if(table.getTableHeader().hitTestMouse()){
			return;
		}
		IntPoint p =getMousePosOnTable ();
		int row =table.rowAtPoint(p );
		int column =table.columnAtPoint(p );
		if(table.editCellAt(row, column, e.getCount())){
		}
	}
	
	private void  __onTableRelease (Event e ){
		table.removeEventListener(MouseEvent.MOUSE_MOVE, __onTableMouseMove);
	}
	
	private void  __onTableMouseMove (Event e ){
		addSelectMousePointed();
	}
	
	private void  __onTableMouseWheel (MouseEvent e ){
		if(!table.isEnabled()){
			return;
		}
		if(table.getTableHeader().hitTestMouse()){
			return;
		}
		IntPoint viewPos =table.getViewPosition ();
		viewPos.y -= e.delta*table.getVerticalUnitIncrement();
		table.setViewPosition(viewPos);
	}
	
	private void  selectMousePointed (MouseEvent e ){
		IntPoint p =getMousePosOnTable ();
		int row =table.rowAtPoint(p );
		int column =table.columnAtPoint(p );
		if ((column == -1) || (row == -1)) {
			return;
		}
		makeSelectionChange(row, column, e);
	}
	
	private void  addSelectMousePointed (){
		IntPoint p =getMousePosOnTable ();
		int row =table.rowAtPoint(p );
		int column =table.columnAtPoint(p );
		if ((column == -1) || (row == -1)) {
			return;
		}
		changeSelection(row, column, false, true);
	}
	
	private void  makeSelectionChange (int row ,int column ,MouseEvent e ){
		recordFocusIndecis(row, column);
		boolean ctrl =e.ctrlKey ;
		boolean shift =e.shiftKey ;

		// Apply the selection state of the anchor to all cells between it and the
		// current cell, and then select the current cell.
		// For mustang, where API changes are allowed, this logic will moved to
		// JTable.changeSelection()
		if (ctrl && shift) {
			ListSelectionModel rm =table.getSelectionModel ();
			ListSelectionModel cm =table.getColumnModel ().getSelectionModel ();
			int anchorRow =rm.getAnchorSelectionIndex ();
			int anchorCol =cm.getAnchorSelectionIndex ();

			if (table.isCellSelected(anchorRow, anchorCol)) {
				rm.addSelectionInterval(anchorRow, row, false);
				cm.addSelectionInterval(anchorCol, column, false);
			} else {
				rm.removeSelectionInterval(anchorRow, row, false);
				rm.addSelectionInterval(row, row, false);
				rm.setAnchorSelectionIndex(anchorRow);
				cm.removeSelectionInterval(anchorCol, column, false);
				cm.addSelectionInterval(column, column, false);
				cm.setAnchorSelectionIndex(anchorCol);
			}
		} else {
			changeSelection(row, column, ctrl, !ctrl && shift);
		}
	}	
	
	private void  changeSelection (int rowIndex ,int columnIndex ,boolean toggle ,boolean extend ){
		recordFocusIndecis(rowIndex, columnIndex);
		table.changeSelection(rowIndex, columnIndex, toggle, extend, false);
	}
	
	private IntPoint  getMousePosOnTable (){
		IntPoint p =table.getMousePosition ();
		return table.getLogicLocationFromPixelLocation(p);
	}
	
	private int  getEditionKey (){
		return Keyboard.ENTER;
	}
	private int  getSelectionKey (){
		return Keyboard.SPACE;
	}
	
	protected Graphics2D  createGridGraphics (){
		if(gridShape == null){
			gridShape = new Shape();
			table.getCellPane().addChild(gridShape);
		}
		gridShape.graphics.clear();
		return new Graphics2D(gridShape.graphics);
	}
		
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		g = createGridGraphics();
		int rowCount =table.getRowCount ();
		int columnCount =table.getColumnCount ();
		if (rowCount <= 0 || columnCount <= 0) {
			return;
		}
		IntDimension extentSize =table.getExtentSize ();
		IntPoint viewPos =table.getViewPosition ();
		int startX =-viewPos.x ;
		int startY =-viewPos.y ;
		
		IntRectangle vb =new IntRectangle ();
		vb.setSize(extentSize);
		vb.setLocation(viewPos);
		IntPoint upperLeft =vb.getLocation ();
		IntPoint lowerRight =vb.rightBottom ();
		int rMin =table.rowAtPoint(upperLeft );
		int rMax =table.rowAtPoint(lowerRight );
		if (rMin == -1) {
			rMin = 0;
		}
		if (rMax == -1) {
			rMax = rowCount - 1;
		}
		int cMin =table.columnAtPoint(upperLeft );
		int cMax =table.columnAtPoint(lowerRight );
		if (cMin == -1) {
			cMin = 0;
		}
		if (cMax == -1) {
			cMax = columnCount - 1;
		}
		
		IntRectangle minCell =table.getCellRect(rMin ,cMin ,true );
		IntRectangle maxCell =table.getCellRect(rMax ,cMax ,true );
		IntRectangle damagedArea =minCell.union(maxCell );
		damagedArea.setLocation(damagedArea.getLocation().move(startX, startY));
		
		Pen pen =new Pen(table.getGridColor (),1);
		if (table.getShowHorizontalLines()) {
			double x1 =damagedArea.x +0.5;
			double x2 =damagedArea.x +damagedArea.width -1;
			double y =damagedArea.y +0.5;
			int rh =table.getRowHeight ();
			for(int row =rMin ;row <=rMax +1;row ++){
				if(row == rowCount){
					y -= 1;
				}
				g.drawLine(pen, x1, y, x2, y);
				y += rh;
			}
		}
		if (table.getShowVerticalLines()) {
			TableColumnModel cm =table.getColumnModel ();
			double x =damagedArea.x +0.5;
			double y1 =damagedArea.y +0.5;
			double y2 =y1 +damagedArea.height -1;
			for(int column =cMin ;column <=cMax +1;column ++){
				if(column == columnCount){
					x -= 1;
				}
				g.drawLine(pen, x, y1, x, y2);
				if(column < columnCount){
					x += cm.getColumn(column).getWidth();
				}
			}
		}		
	}	
	//******************************************************************
	//						Focus and Keyboard control
	//******************************************************************
	private void  __onTableKeyDown (FocusKeyEvent e ){
		if(!table.isEnabled()){
			return;
		}
		double rDir =0;
		double cDir =0;
		int code =e.keyCode ;
		if(code == Keyboard.LEFT){
			cDir = -1;
		}else if(code == Keyboard.RIGHT){
			cDir = 1;
		}else if(code == Keyboard.UP){
			rDir = -1;
		}else if(code == Keyboard.DOWN){
			rDir = 1;
		}
		if(cDir != 0 || rDir != 0){
			moveFocus(rDir, cDir, e);
    		FocusManager fm =FocusManager.getManager(table.stage );
			if(fm) fm.setTraversing(true);
			table.paintFocusRect();
			return;
		}
		if(code == getSelectionKey()){
			table.changeSelection(focusRow, focusColumn, true, false);
		}else if(code == getEditionKey()){
			table.editCellAt(focusRow, focusColumn, -1);
		}
	}
	
	private void  recordFocusIndecis (int row ,int column ){
		focusRow = row;
		focusColumn = column;
	}
	
	private int  restrictRow (int row ){
		return Math.max(0, Math.min(table.getRowCount()-1, row));;
	}
	
	private int  restrictColumn (int column ){
		return Math.max(0, Math.min(table.getColumnCount()-1, column));
	}
	
	private void  moveFocus (double rDir ,double cDir ,KeyboardEvent e ){
		boolean ctrl =e.ctrlKey ;
		boolean shift =e.shiftKey ;
		focusRow += rDir;
		focusRow = restrictRow(focusRow);
		focusColumn += cDir;
		focusColumn = restrictColumn(focusColumn);
		
		if(!ctrl){
			changeSelection(focusRow, focusColumn, ctrl, !ctrl && shift);
		}
		table.ensureCellIsVisible(focusRow, focusColumn);
	}
	
	private int focusRow ;
	private int focusColumn ;

	 public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
		paintCurrentCellFocus(g);
	}
	
	private void  paintCurrentCellFocus (Graphics2D g ){
		paintCellFocusWithRowColumn(g, focusRow, focusColumn);
	}
	
	protected void  paintCellFocusWithRowColumn (Graphics2D g ,int row ,int column ){
		IntRectangle rect =table.getCellRect(row ,column ,true );
		rect.setLocation(table.getPixelLocationFromLogicLocation(rect.getLocation()));
		g.drawRectangle(new Pen(getDefaultFocusColorOutter(), 2), rect.x, rect.y, rect.width, rect.height);
	}

	//******************************************************************
	//							 Size Methods
	//******************************************************************

	protected IntDimension  createTableSize (int width ){
		int height =0;
		int rowCount =table.getRowCount ();
		if (rowCount > 0 && table.getColumnCount() > 0) {
			IntRectangle r =table.getCellRect(rowCount -1,0,true );
			height = r.y + r.height;
		}
		height += table.getTableHeader().getPreferredHeight();
		return new IntDimension(width, height);
	}
		
	/**
	 * Returns the view size.
	 */	
	public IntDimension  getViewSize (JTable table ){
		int width =0;
		Array enumeration =table.getColumnModel ().getColumns ();
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn aColumn =enumeration.get(i) ;
			width += aColumn.getPreferredWidth();
		}
		
		IntDimension d =createTableSize(width );
		if(table.getAutoResizeMode() != JTable.AUTO_RESIZE_OFF){
			d.width = table.getExtentSize().width;
		}else{
			d.width = table.getColumnModel().getTotalColumnWidth();
		}
		d.height -= table.getTableHeader().getHeight();
				
		return d;
	}

	/**
	 * Return the minimum size of the table. The minimum height is the
	 * row height times the number of rows.
	 * The minimum width is the sum of the minimum widths of each column.
	 */
	 public IntDimension  getMinimumSize (Component c ){
		int width =0;
		Array enumeration =table.getColumnModel ().getColumns ();
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn aColumn =enumeration.get(i) ;
			width += aColumn.getMinWidth();
		}
		return table.getInsets().getOutsideSize(new IntDimension(width, 0));
	}

	/**
	 * Return the preferred size of the table. The preferred height is the
	 * row height times the number of rows.
	 * The preferred width is the sum of the preferred widths of each column.
	 */
	 public IntDimension  getPreferredSize (Component c ){
		int width =0;
		Array enumeration =table.getColumnModel ().getColumns ();
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn aColumn =enumeration.get(i) ;
			width += aColumn.getPreferredWidth();
		}
		return table.getInsets().getOutsideSize(createTableSize(width));
		//return table.getInsets().getOutsideSize(getViewSize(JTable(c)));
	}

	/**
	 * Return the maximum size of the table. The maximum height is the
	 * row heighttimes the number of rows.
	 * The maximum width is the sum of the maximum widths of each column.
	 */
	 public IntDimension  getMaximumSize (Component c ){
		int width =0;
		Array enumeration =table.getColumnModel ().getColumns ();
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn aColumn =enumeration.get(i) ;
			width += aColumn.getMaxWidth();
		}
		return table.getInsets().getOutsideSize(createTableSize(width));
	}	
	
	public String  toString (){
		return "BasicTableUI[]";
	}

}


