
package org.aswing.event;

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


import org.aswing.table.TableModel;

/**
 * TableModelEvent is used to notify listeners that a table model
 * has changed. The model event describes changes to a TableModel 
 * and all references to rows and columns are in the co-ordinate 
 * system of the model. 
 * Depending on the parameters used in the constructors, the TableModelevent
 * can be used to specify the following types of changes: <p>
 *
 * <pre>
 * TableModelEvent(source);              //  The data, ie. all rows changed 
 * TableModelEvent(source, HEADER_ROW);  //  Structure change, reallocate TableColumns
 * TableModelEvent(source, 1);           //  Row 1 changed
 * TableModelEvent(source, 3, 6);        //  Rows 3 to 6 inclusive changed
 * TableModelEvent(source, 2, 2, 6);     //  Cell at (2, 6) changed
 * TableModelEvent(source, 3, 6, ALL_COLUMNS, INSERT); // Rows (3, 6) were inserted
 * TableModelEvent(source, 3, 6, ALL_COLUMNS, DELETE); // Rows (3, 6) were deleted
 * </pre>
 *
 * It is possible to use other combinations of the parameters, not all of them 
 * are meaningful. By subclassing, you can add other information, for example: 
 * whether the event WILL happen or DID happen. This makes the specification 
 * of rows in DELETE events more useful but has not been included in 
*theswingpackage astheJTableonlyneedspost-eventnotification.;

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

 * <p>
 * 
 * @author iiley
 */
public class TableModelEvent extends ModelEvent {
	
    /** Identifies the addtion of new rows or columns. */
    public static  String INSERT ="insert";
    /** Identifies a change to existing data. */
    public static  String UPDATE ="update";
    /** Identifies the removal of rows or columns. */
    public static  String DELETE ="delete";
    
    /** Identifies the header row. */
    public static  int HEADER_ROW =-1;

    /** Specifies all columns in a row or rows. */
    public static  int ALL_COLUMNS =-1;
    
    private String type ;
    private int firstRow ;
    private int lastRow ;
    private int column ;
    
    /**
     * <pre>
     * TableModelEvent(source:TableModel, firstRow:int, lastRow:int, column:int, type:String)
     * TableModelEvent(source:TableModel, firstRow:int, lastRow:int, column:int)
     * TableModelEvent(source:TableModel, firstRow:int, lastRow:int)
     * TableModelEvent(source:TableModel, row:int)
     * TableModelEvent(source:TableModel)
     * </pre>
     * <p>
     * <ul>
     * <li>TableModelEvent(source:TableModel, firstRow:int, lastRow:int, column:int, type:String)<br>
     *  The cells from (firstRow, column) to (lastRow, column) have been changed. 
     *  The <I>column</I> refers to the column index of the cell in the model's 
     *  co-ordinate system. When <I>column</I> is ALL_COLUMNS, all cells in the 
     *  specified range of rows are considered changed. <br>
     *  The <I>type</I> should be one of: INSERT, UPDATE and DELETE.
     *  
     *  <li>TableModelEvent(source:TableModel, firstRow:int, lastRow:int, column:int)<br>
     *  The cells in column <I>column</I> in the range 
     *  .get(<I>firstRow</I>, <I>lastRow</I>) have been updated. 
     *  
     *  <li>TableModelEvent(source:TableModel, firstRow:int, lastRow:int)<br>
     *  The data in rows .get(<I>firstRow</I>, <I>lastRow</I>) have been updated.
     *   
     *  <li>TableModelEvent(source:TableModel, row:int)<br>
     *  This row of data has been updated. 
     *  To denote the arrival of a completely new table with a different structure 
     *  use <code>HEADER_ROW</code> as the value for the <code>row</code>. 
     *  When the <code>JTable</code> receives this event and its
     *  <code>autoCreateColumnsFromModel</code> 
     *  flag is set it discards any TableColumns that it had and reallocates 
     *  default ones in the order they appear in the model. This is the 
     *  same as calling <code>setModel(TableModel)</code> on the <code>JTable</code>.
     *   
     *  <li>TableModelEvent(source:TableModel)<br>
     *  All row data in the table has changed, listeners should discard any state 
     *  that was based on the rows and requery the <code>TableModel</code>
     *  to get the new row count and all the appropriate values. 
     *  The <code>JTable</code> will repaint the entire visible region on
     *  receiving this event, querying the model for the cell values that are visible. 
     *  The structure of the table ie, the column names, types and order 
     *  have not changed.  
     * </ul>
     */
    public  TableModelEvent (TableModel source ,int firstRow =-2,int lastRow =-2,int column =ALL_COLUMNS ,String type =UPDATE ){
    	super(source);
    	if(firstRow == -2){
	        // Use int.MAX_VALUE instead of getRowCount() in case rows were deleted. 
			init(0, int.MAX_VALUE, column, type);
    	}else if(lastRow == -2){
        	init(firstRow, firstRow, column, type);
    	}else{
			init(firstRow, lastRow, column, type);
    	}
    }
	
	private void  init (int firstRow ,int lastRow ,int column ,String type ){
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.column = column;
		this.type = type;
	}
	
	//**********************
	// Querying Methods
	//**********************
	
	public String  getType (){
		return type;
	}
	
    /** Returns the first row that changed.  HEADER_ROW means the meta data, 
     * ie. names, types and order of the columns. 
     */
    public firstRow return int  getFirstRow (){;};

    /** Returns the last row that changed. */
    public lastRow return int  getLastRow (){;};
    
    /**
     *  Returns the column for the event.  If the return
     *  value is ALL_COLUMNS; it means every column in the specified
     *  rows changed.
     */
    public column return int  getColumn (){;};
}


