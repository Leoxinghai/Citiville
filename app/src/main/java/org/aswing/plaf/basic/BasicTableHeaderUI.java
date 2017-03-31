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


import flash.display.DisplayObject;
import flash.events.Event;
import flash.events.MouseEvent;

import org.aswing.*;
import org.aswing.event.ReleaseEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.table.*;

/**
 * @author iiley
 * @private
 */
public class BasicTableHeaderUI extends BaseComponentUI{
	
	private JTableHeader header ;
	private Array cells ;
	private int mouseXOffset ;
	private DisplayObject resizeCursor ;
	private boolean resizing ;
	
	public  BasicTableHeaderUI (){
		super();
		mouseXOffset = 0;
		resizing = false;
		resizeCursor = Cursor.createCursor(Cursor.H_MOVE_CURSOR);
	}
	
	protected String  getPropertyPrefix (){
		return "TableHeader.";
	}
	
	 public void  installUI (Component c ){
		header = JTableHeader(c);
		installDefaults();
		installComponents();
		installListeners();
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(header, pp);
		LookAndFeel.installBorderAndBFDecorators(header, pp);
		LookAndFeel.installBasicProperties(header, pp);
		header.setOpaque(true);
	}
	
	protected void  installComponents (){
		cells = new Array();
	}
	
	protected void  installListeners (){
		header.addEventListener(MouseEvent.ROLL_OVER, __onHeaderRollover);
		header.addEventListener(MouseEvent.ROLL_OUT, __onHeaderRollout);
		header.addEventListener(MouseEvent.MOUSE_DOWN, __onHeaderPressed);
		header.addEventListener(ReleaseEvent.RELEASE, __onHeaderReleased);
		header.addEventListener(Event.REMOVED_FROM_STAGE, __headerRemovedFromStage);
	}
	
	 public void  uninstallUI (Component c ){
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}
	
	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(header);
	}
	
	protected void  uninstallComponents (){
		removeAllCells();
		cells = null;
	}
	
	protected void  uninstallListeners (){
		header.removeEventListener(MouseEvent.ROLL_OVER, __onHeaderRollover);
		header.removeEventListener(MouseEvent.ROLL_OUT, __onHeaderRollout);
		header.removeEventListener(MouseEvent.MOUSE_DOWN, __onHeaderPressed);
		header.removeEventListener(ReleaseEvent.RELEASE, __onHeaderReleased);
		header.removeEventListener(Event.REMOVED_FROM_STAGE, __headerRemovedFromStage);
	}
	
	//*************************************************
	//			 Event Handlers
	//*************************************************
	
	private void  __headerRemovedFromStage (Event e ){
		header.stage.removeEventListener(MouseEvent.MOUSE_MOVE, 
			__onRollOverMouseMoving);
		header.stage.removeEventListener(MouseEvent.MOUSE_MOVE, 
			__onMouseMoving);
	}
	
	private void  __onHeaderRollover (MouseEvent e ){
		if(!e.buttonDown){
			if(header.stage){
				header.stage.addEventListener(MouseEvent.MOUSE_MOVE, 
					__onRollOverMouseMoving, false, 0, true);
			}
		}
	}
	
	private void  __onHeaderRollout (MouseEvent e ){
		if(e == null || !e.buttonDown){
			CursorManager.getManager(header.stage).hideCustomCursor(resizeCursor);
			if(header.stage){
				header.stage.removeEventListener(MouseEvent.MOUSE_MOVE, 
					__onRollOverMouseMoving);
			}
		}
	}
	
	private void  __onRollOverMouseMoving (Event e ){
		if(resizing){
			return;
		}
		IntPoint p =header.getMousePosition ();
		if(header.getTable().hitTestMouse() && 
			canResize(getResizingColumn(p, header.columnAtPoint(p)))){
			CursorManager.getManager(header.stage).showCustomCursor(resizeCursor, true);
		}else{
			CursorManager.getManager(header.stage).hideCustomCursor(resizeCursor);
		}
	}
	
	private void  __onHeaderPressed (Event e ){
		header.setResizingColumn(null);
		if(header.getTable().getCellEditor() != null){
			header.getTable().getCellEditor().cancelCellEditing();
		}
		
		IntPoint p =header.getMousePosition ();
		//First find which header cell was hit
		int index =header.columnAtPoint(p );
		if(index >= 0){
			//The last 3 pixels + 3 pixels of next column are for resizing
			TableColumn resizingColumn =getResizingColumn(p ,index );
			if (canResize(resizingColumn)) {
				header.setResizingColumn(resizingColumn);
				mouseXOffset = p.x - resizingColumn.getWidth();
				if(header.stage){
					header.stage.addEventListener(MouseEvent.MOUSE_MOVE, 
						__onMouseMoving, false, 0, true);
				}
				resizing = true;
			}
		}
	}
	
	private void  __onHeaderReleased (Event e ){
		if(header.stage){
			header.stage.removeEventListener(MouseEvent.MOUSE_MOVE, 
				__onMouseMoving);
		}
		header.setResizingColumn(null);
		resizing = false;
		__onRollOverMouseMoving(null);
	}
		
	private void  __onMouseMoving (MouseEvent e ){
		int mouseX =header.getMousePosition ().x ;
		TableColumn resizingColumn =header.getResizingColumn ();
		if (resizingColumn != null) {
			int newWidth ;
			newWidth = mouseX - mouseXOffset;
			resizingColumn.setWidth(newWidth);
			e.updateAfterEvent();
		}
	}
	
	private boolean  canResize (TableColumn column ){
		return (column != null) && header.getResizingAllowed()
			&& column.getResizable();
	}
	
	private TableColumn  getResizingColumn (IntPoint p ,int column ){
		if (column < 0) {
			return null;
		}
		IntRectangle r =header.getHeaderRect(column );
		r.grow(-3, 0);
		//if r contains p
		if ((p.x > r.x && p.x < r.x+r.width)) {
			return null;
		}
		int midPoint =r.x +r.width /2;
		int columnIndex ;
		columnIndex = (p.x < midPoint) ? column - 1 : column;
		if (columnIndex == -1) {
			return null;
		}
		return header.getColumnModel().getColumn(columnIndex);
	}
	
	private TableCellFactory  getHeaderRenderer (int columnIndex ){
		TableColumn aColumn =header.getColumnModel ().getColumn(columnIndex );
		TableCellFactory renderer =aColumn.getHeaderCellFactory ();
		if (renderer == null) {
			renderer = header.getDefaultRenderer();
		}
		return renderer;
	}	
	
	 protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
		if(c.isOpaque()){
	 		ASColor bgColor =(c.getBackground ()==null ? ASColor.WHITE : c.getBackground());
	    	BasicGraphicsUtils.drawControlBackground(g, b, bgColor, Math.PI/2);
		}
	}
	
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		if (header.getColumnModel().getColumnCount() <= 0) {
			return;
		}
		synCreateCellInstances();
		
		TableColumnModel cm =header.getColumnModel ();
		double cMin =0;
		double cMax =cm.getColumnCount ()-1;
		double columnWidth ;
		IntRectangle cellRect =header.getHeaderRect(cMin );
		cellRect.x += header.getTable().getColumnModel().getColumnMargin()/2;
		TableColumn aColumn ;
		for(double column =cMin ;column <=cMax ;column ++){
			aColumn = cm.getColumn(column);
			columnWidth = aColumn.getWidth();
			cellRect.width = columnWidth;
			TableCell cell =cells.get(column) ;
			cell.setCellValue(aColumn.getHeaderValue());
			cell.setTableCellStatus(header.getTable(), false, -1, column);
			cell.getCellComponent().setBounds(cellRect);
			cell.getCellComponent().setVisible(true);
			cell.getCellComponent().validate();
			cellRect.x += columnWidth;
		}
	}
	
	private Array lastColumnCellFactories ;
	private void  synCreateCellInstances (){
		int columnCount =header.getColumnModel ().getColumnCount ();
		int i ;
		if(lastColumnCellFactories==null || lastColumnCellFactories.length != columnCount){
			removeAllCells();
		}else{
			for(i=0; i<columnCount; i++){
				if(lastColumnCellFactories.get(i) != getHeaderRenderer(i)){
					removeAllCells();
					break;
				}
			}
		}
		if(cells.length == 0){
			lastColumnCellFactories = new Array(columnCount);
			for(i=0; i<columnCount; i++){
				TableCellFactory factory =getHeaderRenderer(i );
				lastColumnCellFactories.put(i,  factory);
				TableCell cell =factory.createNewCell(false );
				header.append(cell.getCellComponent());
				setCellComponentProperties(cell.getCellComponent());
				cells.push(cell);
			}
		}
	}
	
	private static void  setCellComponentProperties (Component com ){
		com.setFocusable(false);
		if(com is Container){
			Container con =Container(com );
			for(int i =0;i <con.getComponentCount ();i ++){
				setCellComponentProperties(con.getComponent(i));
			}
		}
	}	
	
	private void  removeAllCells (){
		for(int i =0;i <cells.length ;i ++){
			TableCell cell =TableCell(cells.get(i) );
			cell.getCellComponent().removeFromContainer();
		}
		cells = new Array();
	}
	//******************************************************************
	//							 Size Methods
	//******************************************************************
	private IntDimension  createHeaderSize (int width ){
		return header.getInsets().getOutsideSize(new IntDimension(width, header.getRowHeight()));
	}

	/**
	 * Return the minimum size of the table. The minimum height is the
	 * row height times the number of rows.
	 * The minimum width is the sum of the minimum widths of each column.
	 */
	 public IntDimension  getMinimumSize (Component c ){
		int width =0;
//		var enumeration:Array = header.getColumnModel().getColumns();
//		for(var i:int=0; i<enumeration.length; i++){
//			var aColumn:TableColumn = TableColumn(enumeration.get(i));
//			width = width + aColumn.getMinWidth();
//		}
		return createHeaderSize(width);
	}

	/**
	 * Return the preferred size of the table. The preferred height is the
	 * row height times the number of rows.
	 * The preferred width is the sum of the preferred widths of each column.
	 */
	 public IntDimension  getPreferredSize (Component c ){
		int width =0;
		Array enumeration =header.getColumnModel ().getColumns ();
		for(int i =0;i <enumeration.length ;i ++){
			TableColumn aColumn =TableColumn(enumeration.get(i) );
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}

	/**
	 * Return the maximum size of the table. The maximum height is the
	 * row heighttimes the number of rows.
	 * The maximum width is the sum of the maximum widths of each column.
	 */
	 public IntDimension  getMaximumSize (Component c ){
		int width =100000;
//		var enumeration:Array = header.getColumnModel().getColumns();
//		for(var i:int=0; i<enumeration.length; i++){
//			var aColumn:TableColumn = TableColumn(enumeration.get(i));
//			width = width + aColumn.getMaxWidth();
//		}
		return createHeaderSize(width);
	}	
	
	public String  toString (){
		return "BasicTableHeaderUI[]";
	}
}


