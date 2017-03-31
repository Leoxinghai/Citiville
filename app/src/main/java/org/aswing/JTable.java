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


import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.util.*;
import org.aswing.plaf.*;
import org.aswing.table.*;
import org.aswing.plaf.basic.BasicTableUI;
import flash.utils.getTimer;

/**
 * Dispatched when the row selection changed.
 * @eventType org.aswing.event.SelectionEvent.ROW_SELECTION_CHANGED
 * @see #addSelectionListener()
 */
.get(Event(name="rowSelectionChanged", type="org.aswing.event.SelectionEvent"))

/**
 * Dispatched when the column selection changed.
 * @eventType org.aswing.event.SelectionEvent.COLUMN_SELECTION_CHANGED
 */
.get(Event(name="columnSelectionChanged", type="org.aswing.event.SelectionEvent"))

/**
 * Dispatched when the cell editing started.
 * @eventType org.aswing.event.TableCellEditEvent.EDITING_STARTED
 */
.get(Event(name="tableCellEditingStarted", type="org.aswing.event.TableCellEditEvent"))

/**
 * Dispatched when the cell editing canceled.
 * @eventType org.aswing.event.TableCellEditEvent.EDITING_CANCELED
 */
.get(Event(name="tableCellEditingCanceled", type="org.aswing.event.TableCellEditEvent"))

/**
 * Dispatched when the cell editing finished.
 * @eventType org.aswing.event.TableCellEditEvent.EDITING_STOPPED
 */
.get(Event(name="tableCellEditingStopped", type="org.aswing.event.TableCellEditEvent"))

/**
 * The <code>JTable</code> is used to display and edit regular two-dimensional tables
 * of cells.
 * <p>
 * The <code>JTable</code> has many
 * facilities that make it possible to customize its rendering and editing
 * but provides defaults for these features so that simple tables can be
 * set up easily.  For example, to set up a table with 10 rows and 10
 * columns of numbers:
 * <p>
 * <pre>
 *      class MyTableModel extends AbstractTableModel{
 *public return int  getColumnCount (){10;}
 *public return int  getRowCount (){10;}
 *          public getValueAt(row:int, col:int) { return row*col; }
 *      };
 *MyTableModel dataModel =new MyTableModel ();
 *JTable table =new JTable(dataModel );
 *JScrollPane scrollpane =new JScrollPane(table );
 * </pre>
 * <p>
 * Note that if you wish to use a <code>JTable</code> in a standalone
 * view (outside of a <code>JScrollPane</code>) and want the header
 * displayed, you can get it using {@link #getTableHeader} and
 * display it separately.
 * <p>
 * When designing applications that use the <code>JTable</code> it is worth paying
 * close attention to the data structures that will represent the table's data.
 * The <code>DefaultTableModel</code> is a model implementation that
 * uses a <code>Vector</code> of <code>Vector</code>s of <code>Object</code>s to
 * store the cell values. As well as copying the data from an
 * application into the <code>DefaultTableModel</code>,
 * it is also possible to wrap the data in the methods of the
 * <code>TableModel</code> interface so that the data can be passed to the
 * <code>JTable</code> directly, as in the example above. This often results
 * in more efficient applications because the model is free to choose the
 * internal representation that best suits the data.
 * A good rule of thumb for deciding whether to use the <code>AbstractTableModel</code>
 * or the <code>DefaultTableModel</code> is to use the <code>AbstractTableModel</code>
 * as the base class for creating subclasses and the <code>DefaultTableModel</code>
 * when subclassing is not required.
 * <p>
 * The <code>JTable</code> uses integers exclusively to refer to both the rows and the columns
 * of the model that it displays. The <code>JTable</code> simply takes a tabular range of cells
 * and uses <code>getValueAt(int, int)</code> to retrieve the
 * values from the model during painting.
 * <p>
 * By default, columns may be rearranged in the <code>JTable</code> so that the
 * view's columns appear in a different order to the columns in the model.
 * This does not affect the implementation of the model at all: when the
 * columns are reordered, the <code>JTable</code> maintains the new order of the columns
 * internally and converts its column indices before querying the model.
 * <p>
 * To listen table row selection change event, by <code>addSelectionListener()</code>
 * To listen column selection see {@link org.aswing.table.TableColumnModel#addColumnModelListener} <br>
 * To listen other Table events see {@link org.aswing.table.TableModel#addTableModelListener}
 * @author iiley
 */
public class JTable extends Container implements Viewportable, TableModelListener, TableColumnModelListener, CellEditorListener, LayoutManager{

 	/**
 	 * The default unit/block increment, it means auto count a value.
 	 */
 	public static  int AUTO_INCREMENT =int.MIN_VALUE ;

	/**
	 * Do not adjust column widths automatically; use a scrollbar.
	 */
	public static  int AUTO_RESIZE_OFF =0;
	/**
	 * When a column is adjusted in the UI, adjust the next column the opposite way.
	 */
	public static  int AUTO_RESIZE_NEXT_COLUMN =1;
	/**
	 * During UI adjustment, change subsequent columns to preserve the total width;
	 * this is the default behavior.
	 */
	public static  int AUTO_RESIZE_SUBSEQUENT_COLUMNS =2;
	/**
	 * During all resize operations, apply adjustments to the last column only.
	 */
	public static  int AUTO_RESIZE_LAST_COLUMN =3;
	/**
	 * During all resize operations, proportionately resize all columns.
	 */
	public static  int AUTO_RESIZE_ALL_COLUMNS =4;

	/**
	 * Only can select one most item at a time.
	 */
	public static  int SINGLE_SELECTION =0;
	/**
	 * Can select any item at a time.
	 */
	public static  int MULTIPLE_SELECTION =1;

	private TableModel dataModel ;

	private TableColumnModel columnModel ;
	private ListSelectionModel selectionModel ;

	protected Container cellPane ;
	protected Container headerPane ;
	private JTableHeader tableHeader ;
	private Array rowCells ;
	private int rowHeight ;
	private int rowMargin ;
	private ASColor gridColor ;
	private boolean showHorizontalLines ;
	private boolean showVerticalLines ;
	private int autoResizeMode ;
	private boolean autoCreateColumnsFromModel ;
	private IntDimension preferredViewportSize ;
	private boolean rowSelectionAllowed ;
	private boolean cellSelectionEnabled ;
	private TableCellEditor cellEditor ;
	private int editingColumn ;
	private int editingRow ;
	private HashMap defaultRenderersByColumnClass ;
	private HashMap defaultEditorsByColumnClass ;
	private ASColor selectionForeground ;
	private ASColor selectionBackground ;
	//private boolean surrendersFocusOnKeystroke ;
	private boolean columnSelectionAdjusting ;
	private boolean rowSelectionAdjusting ;

	//viewport
	private IntPoint viewPosition ;
	private int verticalUnitIncrement ;
	private int verticalBlockIncrement ;
	private int horizontalUnitIncrement ;
	private int horizontalBlockIncrement ;
	/** Stored cell value before any edition. */
	private Object _storedValue;

	/**
	 * Constructs a default <code>JTable</code>.
	 *
	 * @see #initWithEmptyParam()
	 * @see #initWithDataNames()
	 * @see #initWithRowColumn()
	 * @see #initWithModels()
	 */
	public  JTable (TableModel dm =null ){
		super();
		setName("JTable");

		verticalUnitIncrement = AUTO_INCREMENT;
		verticalBlockIncrement = AUTO_INCREMENT;
		horizontalUnitIncrement = AUTO_INCREMENT;
		horizontalBlockIncrement = AUTO_INCREMENT;

		initWithModels(dm);
	}

	/**
	 * Initializes the <code>JTable</code> with
	 * <code>dm</code> as the data model, <code>cm</code> as the
	 * column model, and <code>sm</code> as the selection model.
	 * If any of the parameters are <code>null</code> this method
	 * will initialize the table with the corresponding default model.
	 * The <code>autoCreateColumnsFromModel</code> flag is set to false
	 * if <code>cm</code> is non-null, otherwise it is set to true
	 * and the column model is populated with suitable
	 * <code>TableColumns</code> for the columns in <code>dm</code>.
	 *
	 * @param dm		the data model for the table
	 * @param cm		the column model for the table
	 * @param sm		the row selection model for the table
	 * @see #createDefaultDataModel()
	 * @see #createDefaultColumnModel()
	 * @see #createDefaultSelectionModel()
	 */
	private void  initWithModels (TableModel dm =null ,TableColumnModel cm =null ,ListSelectionModel sm =null ){
		setLayout(this);

		cellPane = new CellPane();
		cellPane.setEnabled(false);
		append(cellPane);

		headerPane = new CellPane();
		headerPane.setEnabled(false);
		append(headerPane);

		rowCells = new Array();
		viewPosition = new IntPoint();

		if (cm == null){
			cm = createDefaultColumnModel();
			autoCreateColumnsFromModel = true;
		}
		//trace("cm = " + cm);
		setColumnModel(cm);
		if (sm == null){
			sm = createDefaultSelectionModel();
		}
		//trace("sm = " + sm);
		setSelectionModel(sm);
		if (dm == null){
			dm = createDefaultDataModel();
		}
		//trace("dm = " + dm);
		setModel(dm);
		initializeLocalVars();
		updateUI();
	}

	//****************************************
	//           Managing TableUI
	//****************************************

	/**
	 * Sets the L&F object that renders this component and repaints.
	 *
	 * @param ui  the TableUI L&F object
	 * @throws ArgumentError when the newUI is not an <code>TableUI</code> instance.
	 */
	 public void  setUI (ComponentUI newUI ){
		if (newUI is TableUI){
			super.setUI(newUI);
		    repaint();
		}else{
			throw new ArgumentError("JTable ui should implemented TableUI interface!");
		}
	}

	public TableUI  getTableUI (){
		return getUI() as TableUI;
	}

	protected void  updateSubComponentUI (*)componentShell {
		if (componentShell == null){
			return;
		}
		Component component =null ;
		if (componentShell is Component) {
			component =(Component) componentShell;
			component.updateUI();
		}else if (componentShell is CellEditor){
			CellEditor ed =(CellEditor)componentShell;
			ed.updateUI();
		}
	}

	/**
	 * Notification from the <code>UIManager</code> that the L&F has changed.
	 * Replaces the current UI object with the latest version from the
	 * <code>UIManager</code>.
	 *
	 * @see org.aswing.Component#updateUI
	 */
	 public void  updateUI (){
		// Update the UIs of the cell editors
		TableColumnModel cm =getColumnModel ();
		for(int column =0;column <cm.getColumnCount ();column ++){
			TableColumn aColumn =cm.getColumn(column );
			updateSubComponentUI(aColumn.getCellEditor());
		}
		//Update the cells ui
		int i ;
		for(i=0; i<rowCells.length; i++){
			for(int j =0;j <rowCells.get(i).length ;j ++){
				TableCell cell =rowCells.get(i).get(j) ;
				cell.getCellComponent().updateUI();
			}
		}

		// Update the UIs of all the default editors.
		Array defaultEditors =defaultEditorsByColumnClass.values ();
		for(i=0; i<defaultEditors.length; i++){
			updateSubComponentUI(defaultEditors.get(i));
		}

		// Update the UI of the table header
		if (tableHeader != null && tableHeader.getParent() == null){
			tableHeader.updateUI();
		}

		setUI(UIManager.getUI(this));
		//resizeAndRepaint();
	}

     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicTableUI;
    }

	/**
	 * Returns the suffix used to construct the name of the L&F class used to
	 * render this component.
	 *
	 * @return the string "TableUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	 public String  getUIClassID (){
		return "TableUI";
	}


	//*****************************************************
	//		   Table Attributes
	//*****************************************************

	/**
	 * Sets the <code>tableHeader</code> working with this <code>JTable</code> to <code>newHeader</code>.
	 * It is legal to have a <code>null</code> <code>tableHeader</code>.
	 *
	 * @param   tableHeader  new tableHeader
	 * @see	 #getTableHeader()
	 */
	public void  setTableHeader (JTableHeader tableHeader ){
		if (this.tableHeader != tableHeader){
			JTableHeader old =this.tableHeader ;
			if (old != null){
				old.setTable(null);
				old.removeFromContainer();
			}
			this.tableHeader = tableHeader;
			if (tableHeader != null){
				tableHeader.setTable(this);
				headerPane.append(tableHeader);
			}
			//firePropertyChange("tableHeader", old, tableHeader);
		}
	}

	/**
	 * Returns the <code>tableHeader</code> used by this <code>JTable</code>.
	 *
	 * @return  the <code>tableHeader</code> used by this table
	 * @see	 #setTableHeader()
	 */
	public JTableHeader  getTableHeader (){
		return tableHeader;
	}

	/**
	 * Returns the container that holds the cells.
	 * @return the container that holds the cells.
	 */
	public Container  getCellPane (){
		return cellPane;
	}

	/**
	 * Sets the height, in pixels, of all cells to <code>rowHeight</code>,
	 * revalidates, and repaints.
	 * The height of the cells will be equal to the row height minus
	 * the row margin.
	 *
	 * @param   rowHeight new row height
	 * @throws  RangeError if <code>rowHeight</code> is less than 1
     * @see     #getAllRowHeight()
     */
	public void  setRowHeight (int rowHeight ){
		if (rowHeight < 1){
			throw new RangeError("New row height less than 1");
		}
		//int old =this.rowHeight ;
		this.rowHeight = rowHeight;
		resizeAndRepaint();
		//firePropertyChange("rowHeight", old, rowHeight);
	}

	/**
	 * Returns the height of a table row, in pixels.
	 * The default row height is 20.0.
	 *
	 * @return  the height in pixels of a table row
	 * @see #setAllRowHeight()
	 */
	public int  getRowHeight (){
		return rowHeight;
	}

	/**
	 * Sets the amount of empty space between cells in adjacent rows.
	 *
	 * @param  rowMargin  the number of pixels between cells in a row
	 * @see	 #getRowMargin()
	 */
	public void  setRowMargin (int rowMargin ){
		//int old =this.rowMargin ;
		this.rowMargin = rowMargin;
		resizeAndRepaint();
		//firePropertyChange("rowMargin", old, rowMargin);
	}

	/**
	 * Gets the amount of empty space, in pixels, between cells. Equivalent to:
	 * <code>getIntercellSpacing().height</code>.
	 * @return the number of pixels between cells in a row
	 *
	 * @see	 #setRowMargin()
	 */
	public int  getRowMargin (){
		return rowMargin;
	}

	/**
	 * Sets the <code>rowMargin</code> and the <code>columnMargin</code> --
	 * the height and width of the space between cells -- to
	 * <code>intercellSpacing</code>.
	 *
	 * @param   intercellSpacing		a <code>IntDimension</code>
	 *					specifying the new width
	 *					and height between cells
	 * @see	 #getIntercellSpacing()
	 */
	public void  setIntercellSpacing (IntDimension intercellSpacing ){
		setRowMargin(intercellSpacing.height);
		getColumnModel().setColumnMargin(intercellSpacing.width);
		resizeAndRepaint();
	}

	/**
	 * Returns the horizontal and vertical space between cells.
	 * The default spacing is (1, 1), which provides room to draw the grid.
	 *
	 * @return  the horizontal and vertical spacing between cells
	 * @see	 #setIntercellSpacing()
	 */
	public IntDimension  getIntercellSpacing (){
		return new IntDimension(getColumnModel().getColumnMargin(), rowMargin);
	}

	/**
	 * Sets the color used to draw grid lines to <code>gridColor</code> and redisplays.
	 * The default color is look and feel dependent.
	 *
	 * @param  gridColor the new color of the grid lines
	 * @see #getGridColor()
	 */
	public void  setGridColor (ASColor gridColor ){
		if (gridColor == null){
			trace("New color is null, Ignored");
			return;
		}
		//ASColor old =this.gridColor ;
		this.gridColor = gridColor;
		//firePropertyChange("gridColor", old, gridColor);
		repaint();
	}

	/**
	 * Returns the color used to draw grid lines.
	 * The default color is look and feel dependent.
	 *
	 * @return  the color used to draw grid lines
	 * @see	 #setGridColor()
	 */
	public ASColor  getGridColor (){
		return gridColor;
	}

	/**
	 *  Sets whether the table draws grid lines around cells.
	 *  If <code>showGrid</code> is true it does; if it is false it doesn't.
	 *  There is no <code>getShowGrid</code> method as this state is held
	 *  in two variables -- <code>showHorizontalLines</code> and <code>showVerticalLines</code> --
	 *  each of which can be queried independently.
	 *  <br/>
	 *  You may also need to call setIntercellSpacing() to set a proper gap of
	 *  columns and rows to avoid lines effect caused by different bgs between table and cell.
	 * @param   showGrid true if table view should draw grid lines
	 *
	 * @see #setShowVerticalLines()
	 * @see #setShowHorizontalLines()
	 * @see #setIntercellSpacing()
	 */
	public void  setShowGrid (boolean showGrid ){
		setShowHorizontalLines(showGrid);
		setShowVerticalLines(showGrid);
		repaint();
	}

	/**
	 *  Sets whether the table draws horizontal lines between cells.
	 *  If <code>showHorizontalLines</code> is true it does; if it is false it doesn't.
	 *  <br/>
	 *  You may also need to call setIntercellSpacing() to set a proper gap of
	 *  columns and rows to avoid lines effect caused by different bgs between table and cell.
	 *
	 * @param   showHorizontalLines	  true if table view should draw horizontal lines
	 * @see	 #getShowHorizontalLines()
	 * @see	 #setShowGrid()
	 * @see	 #setShowVerticalLines()
	 * @see  #setIntercellSpacing()
	 */
	public void  setShowHorizontalLines (boolean showHorizontalLines ){
		boolean old =this.showHorizontalLines ;
		this.showHorizontalLines = showHorizontalLines;
		//firePropertyChange("showHorizontalLines", old, showHorizontalLines);
		if(old != showHorizontalLines){
			repaint();
		}
	}

	/**
	 * Sets whether the table draws vertical lines between cells.
	 * If <code>showVerticalLines</code> is true it does; if it is false it doesn't.
	 *  <br/>
	 *  You may also need to call setIntercellSpacing() to set a proper gap of
	 *  columns and rows to avoid lines effect caused by different bgs between table and cell.
	 *
	 * @param   showVerticalLines true if table view should draw vertical lines
	 * @see     #getShowVerticalLines()
	 * @see     #setShowGrid()
	 * @see     #setShowHorizontalLines()
	 * @see 	#setIntercellSpacing()
	 */
	public void  setShowVerticalLines (boolean showVerticalLines ){
		boolean old =this.showVerticalLines ;
		this.showVerticalLines = showVerticalLines;
		//firePropertyChange("showVerticalLines", old, showVerticalLines);
		if(old != showVerticalLines){
			repaint();
		}
	}

	/**
	 * Returns true if the table draws horizontal lines between cells, false if it
	 * doesn't. The default is true.
	 *
	 * @return  true if the table draws horizontal lines between cells, false if it
	 *          doesn't
	 * @see	 #setShowHorizontalLines()
	 */
	public boolean  getShowHorizontalLines (){
		return showHorizontalLines;
	}

	/**
	 * Returns true if the table draws vertical lines between cells, false if it
	 * doesn't. The default is true.
	 *
	 * @return  true if the table draws vertical lines between cells, false if it
	 *          doesn't
	 * @see	 #setShowVerticalLines()
	 */
	public boolean  getShowVerticalLines (){
		return showVerticalLines;
	}

	/**
	 * Sets the table's auto resize mode when the table is resized.
	 *
	 * @param   mode One of 5 legal values:
	 *                   AUTO_RESIZE_OFF,
	 *                   AUTO_RESIZE_NEXT_COLUMN,
	 *                   AUTO_RESIZE_SUBSEQUENT_COLUMNS,
	 *                   AUTO_RESIZE_LAST_COLUMN,
	 *                   AUTO_RESIZE_ALL_COLUMNS
	 *
	 * @see     #getAutoResizeMode()
	 * @see     #doLayout()
	 */
	public void  setAutoResizeMode (int mode ){
		if (mode == AUTO_RESIZE_OFF
			|| mode == AUTO_RESIZE_NEXT_COLUMN
			|| mode == AUTO_RESIZE_SUBSEQUENT_COLUMNS
			|| mode == AUTO_RESIZE_LAST_COLUMN
			|| mode == AUTO_RESIZE_ALL_COLUMNS)
		{
			if(mode != autoResizeMode){
				//int old =autoResizeMode ;
				autoResizeMode = mode;
				resizeAndRepaint();
				if (tableHeader != null){
					tableHeader.resizeAndRepaint();
				}
				//firePropertyChange("autoResizeMode", old, autoResizeMode);
			}
		}
	}

	/**
	 * Returns the auto resize mode of the table.  The default mode
	 * is AUTO_RESIZE_SUBSEQUENT_COLUMNS.
	 *
	 * @return  the autoResizeMode of the table
	 *
	 * @see	 #setAutoResizeMode()
	 * @see	 #doLayout()
	 */
	public int  getAutoResizeMode (){
		return autoResizeMode;
	}

	/**
	 * Sets this table's <code>autoCreateColumnsFromModel</code> flag.
	 * This method calls <code>createDefaultColumnsFromModel</code> if
	 * <code>autoCreateColumnsFromModel</code> changes from false to true.
	 *
	 * @param   autoCreateColumnsFromModel   true if <code>JTable</code> should automatically create columns
	 * @see #getAutoCreateColumnsFromModel()
	 * @see #createDefaultColumnsFromModel()
	 */
	public void  setAutoCreateColumnsFromModel (boolean autoCreateColumnsFromModel ){
		if (this.autoCreateColumnsFromModel != autoCreateColumnsFromModel){
			//boolean old =this.autoCreateColumnsFromModel ;
			this.autoCreateColumnsFromModel = autoCreateColumnsFromModel;
			if (autoCreateColumnsFromModel){
				createDefaultColumnsFromModel();
			}
			//firePropertyChange("autoCreateColumnsFromModel", old, autoCreateColumnsFromModel);
		}
	}

	/**
	 * Determines whether the table will create default columns from the model.
	 * If true, <code>setModel</code> will clear any existing columns and
	 * create new columns from the new model.  Also, if the event in
	 * the <code>tableChanged</code> notification specifies that the
	 * entire table changed, then the columns will be rebuilt.
	 * The default is true.
	 *
	 * @return  the autoCreateColumnsFromModel of the table
	 * @see	 #setAutoCreateColumnsFromModel()
	 * @see	 #createDefaultColumnsFromModel()
	 */
	public boolean  getAutoCreateColumnsFromModel (){
		return autoCreateColumnsFromModel;
	}

	/**
	 * Creates default columns for the table from
	 * the data model using the <code>getColumnCount</code> method
	 * defined in the <code>TableModel</code> interface.
	 * <p>
	 * Clears any existing columns before creating the
	 * new columns based on information from the model.
	 *
	 * @see #getAutoCreateColumnsFromModel()
	 */
	public void  createDefaultColumnsFromModel (){
		TableModel m =getModel ();
		if (m != null){
			TableColumnModel cm =getColumnModel ();
			while(cm.getColumnCount() > 0){
				cm.removeColumn(cm.getColumn(0));
			}
			for(int i =0;i <m.getColumnCount ();i ++){
				TableColumn newColumn =new TableColumn(i );
				addColumn(newColumn);
			}
		}
	}

	/**
	 * Sets a default cell factory to be used if no renderer has been set in
	 * a <code>TableColumn</code>. If renderer is <code>null</code>,
	 * removes the default factory for this column class.
	 *
	 * @param  columnClass     set the default cell factory for this columnClass
	 * @param  renderer        default cell factory to be used for this
	 *			       columnClass
	 * @see     #getDefaultRenderer()
	 * @see     #setDefaultEditor()
	 */
	public void  setDefaultCellFactory (String columnClass ,TableCellFactory renderer ){
		if (renderer != null){
			defaultRenderersByColumnClass.put(columnClass, renderer);
		}else{
			defaultRenderersByColumnClass.remove(columnClass);
		}
	}

	/**
	 * Returns the cell factory to be used when no factory has been set in
	 * a <code>TableColumn</code>. During the rendering of cells the factory is fetched from
	 * a <code>Hashtable</code> of entries according to the class of the cells in the column. If
	 * there is no entry for this <code>columnClass</code> the method returns
	 * the entry for the most specific superclass. The <code>JTable</code> installs entries
	 * for <code>"Object"</code>, <code>"Number"</code>, and <code>"Boolean"</code>, all of which can be modified
	 * or replaced.
	 *
	 * @param   columnClass   return the default cell factory
	 *			      for this columnClass
	 * @return  the factory for this columnClass
	 * @see     #setDefaultRenderer()
	 * @see     #getColumnClass()
	 */
	public TableCellFactory  getDefaultCellFactory (String columnClass ){
		//trace("getDefaultRenderer of " + columnClass);
		if (columnClass == null){
			return null;
		}else{
			Object renderer =defaultRenderersByColumnClass.get(columnClass );
			//trace("defaultRenderersByColumnClass " + renderer);
			if (renderer != null){
				return TableCellFactory(renderer);
			}else{
				return getDefaultCellFactory("Object");
			}
		}
	}

	/**
	 * Sets a default cell editor to be used if no editor has been set in
	 * a <code>TableColumn</code>. If no editing is required in a table, or a
	 * particular column in a table, uses the <code>isCellEditable</code>
	 * method in the <code>TableModel</code> interface to ensure that this
	 * <code>JTable</code> will not start an editor in these columns.
	 * If editor is <code>null</code>, removes the default editor for this
	 * column class.
	 *
	 * @param  columnClass  set the default cell editor for this columnClass
	 * @param  editor   default cell editor to be used for this columnClass
	 * @see     TableModel#isCellEditable()
	 * @see     #getDefaultEditor()
	 * @see     #setDefaultRenderer()
	 */
	public void  setDefaultEditor (String columnClass ,TableCellEditor editor ){
		if (editor != null){
			defaultEditorsByColumnClass.put(columnClass, editor);
		}else{
			defaultEditorsByColumnClass.remove(columnClass);
		}
	}

	/**
	 * Returns the editor to be used when no editor has been set in
	 * a <code>TableColumn</code>. During the editing of cells the editor is fetched from
	 * a <code>Hashtable</code> of entries according to the class of the cells in the column. If
	 * there is no entry for this <code>columnClass</code> the method returns
	 * the entry for the most specific superclass. The <code>JTable</code> installs entries
	 * for <code>"Object"</code>, <code>"Number"</code>, and <code>"Boolean"</code>, all of which can be modified
	 * or replaced.
	 *
	 * @param   columnClass  return the default cell editor for this columnClass
	 * @return the default cell editor to be used for this columnClass
	 * @see     #setDefaultEditor()
	 * @see     #getColumnClass()
	 */
	public TableCellEditor  getDefaultEditor (String columnClass ){
		if (columnClass == null){
			return null;
		}else{
			Object editor =defaultEditorsByColumnClass.get(columnClass );
			if (editor != null){
				return TableCellEditor(editor);
			}else{
				return getDefaultEditor("Object");
			}
		}
	}

	/**
	 * Sets the table's selection mode to allow only single selections, a single
	 * contiguous interval, or multiple intervals.
	 * <P>
	 * <bold>Note:</bold>
	 * <code>JTable</code> provides all the methods for handling
	 * column and row selection.  When setting states,
	 * such as <code>setSelectionMode</code>, it not only
	 * updates the mode for the row selection model but also sets similar
	 * values in the selection model of the <code>columnModel</code>.
	 * If you want to have the row and column selection models operating
	 * in different modes, set them both directly.
	 * <p>
	 * Both the row and column selection models for <code>JTable</code>
	 * default to using a <code>DefaultListSelectionModel</code>
	 * so that <code>JTable</code> works the same way as the
	 * <code>JList</code>. See the <code>setSelectionMode</code> method
	 * in <code>JList</code> for details about the modes.
	 *
	 * @see JList#setSelectionMode()
	 */
	public void  setSelectionMode (int selectionMode ){
		clearSelection();
		getSelectionModel().setSelectionMode(selectionMode);
		getColumnModel().getSelectionModel().setSelectionMode(selectionMode);
	}

	/**
	 * Sets whether the rows in this model can be selected.
	 *
	 * @param rowSelectionAllowed   true if this model will allow row selection
	 * @see #getRowSelectionAllowed()
	 */
	public void  setRowSelectionAllowed (boolean rowSelectionAllowed ){
		boolean old =this.rowSelectionAllowed ;
		this.rowSelectionAllowed = rowSelectionAllowed;
		if (old != rowSelectionAllowed){
			repaint();
		}
		//firePropertyChange("rowSelectionAllowed", old, rowSelectionAllowed);
	}

	/**
	 * Returns true if rows can be selected.
	 *
	 * @return true if rows can be selected, otherwise false
	 * @see #setRowSelectionAllowed()
	 */
	public boolean  getRowSelectionAllowed (){
		return rowSelectionAllowed;
	}

	/**
	 * Sets whether the columns in this model can be selected.
	 *
	 * @param columnSelectionAllowed   true if this model will allow column selection
	 * @see #getColumnSelectionAllowed()
	 */
	public void  setColumnSelectionAllowed (boolean columnSelectionAllowed ){
		boolean old =columnModel.getColumnSelectionAllowed ();
		columnModel.setColumnSelectionAllowed(columnSelectionAllowed);
		if (old != columnSelectionAllowed){
			repaint();
		}
		//firePropertyChange("columnSelectionAllowed", old, columnSelectionAllowed);
	}

	/**
	 * Returns true if columns can be selected.
	 *
	 * @return true if columns can be selected, otherwise false
	 * @see #setColumnSelectionAllowed()
	 */
	public boolean  getColumnSelectionAllowed (){
		return columnModel.getColumnSelectionAllowed();
	}

	/**
	 * Sets whether this table allows both a column selection and a
	 * row selection to exist simultaneously. When set,
	 * the table treats the intersection of the row and column selection
	 * models as the selected cells. Override <code>isCellSelected</code> to
	 * change this default behavior. This method is equivalent to setting
	 * both the <code>rowSelectionAllowed</code> property and
	 * <code>columnSelectionAllowed</code> property of the
	 * <code>columnModel</code> to the supplied value.
	 *
	 * @param  cellSelectionEnabled   	true if simultaneous row and column
	 *					selection is allowed
	 * @see #getCellSelectionEnabled()
	 * @see #isCellSelected()
	 */
	public void  setCellSelectionEnabled (boolean cellSelectionEnabled ){
		setRowSelectionAllowed(cellSelectionEnabled);
		setColumnSelectionAllowed(cellSelectionEnabled);
		//boolean old =this.cellSelectionEnabled ;
		this.cellSelectionEnabled = cellSelectionEnabled;
		//firePropertyChange("cellSelectionEnabled", old, cellSelectionEnabled);
	}

	/**
	 * Returns true if both row and column selection models are enabled.
	 * Equivalent to <code>getRowSelectionAllowed() &&
	 * getColumnSelectionAllowed()</code>.
	 *
	 * @return true if both row and column selection models are enabled
	 *
	 * @see #setCellSelectionEnabled()
	 */
	public boolean  getCellSelectionEnabled (){
		return (getRowSelectionAllowed() && getColumnSelectionAllowed());
	}

	/**
	 *  Selects all rows, columns, and cells in the table.
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  selectAll (boolean programmatic =true ){
		if (isEditing()){
			removeEditor();
		}
		if ((getRowCount() > 0) && (getColumnCount() > 0)){
			int oldLead ;
			int oldAnchor ;
			ListSelectionModel selModel ;
			selModel = selectionModel;
			//TODO adjusting needed?
			//selModel.setValueIsAdjusting(true);
			oldLead = selModel.getLeadSelectionIndex();
			oldAnchor = selModel.getAnchorSelectionIndex();
			setRowSelectionInterval(0, getRowCount() - 1, programmatic);
			selModel.addSelectionInterval(oldAnchor, oldLead, programmatic);
			//selModel.setValueIsAdjusting(false);
			selModel = columnModel.getSelectionModel();
			//selModel.setValueIsAdjusting(true);
			oldLead = selModel.getLeadSelectionIndex();
			oldAnchor = selModel.getAnchorSelectionIndex();
			setColumnSelectionInterval(0, getColumnCount() - 1, programmatic);
			selModel.addSelectionInterval(oldAnchor, oldLead, programmatic);
			//selModel.setValueIsAdjusting(false);
		}
	}

	/**
	 * Deselects all selected columns and rows.
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  clearSelection (boolean programmatic =true ){
		selectionModel.clearSelection(programmatic);
		columnModel.getSelectionModel().clearSelection(programmatic);
	}

	private int  boundRow (int row ){
		if ((row < 0) || (row >= getRowCount())){
			throw new RangeError("Row index out of range");
		}
		return row;
	}

	private int  boundColumn (int col ){
		if ((col < 0) || (col >= getColumnCount())){
			throw new RangeError("Column index out of range");
		}
		return col;
	}

	/**
	 * Selects the rows from <code>index0</code> to <code>index1</code>,
	 * inclusive.
	 *
	 * @exception Error      if <code>index0</code> or
	 *						<code>index1</code> lie outside
	 *                                          .get(0, <code>getRowCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  setRowSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		selectionModel.setSelectionInterval(boundRow(index0), boundRow(index1), programmatic);
	}

	/**
	 * Selects the columns from <code>index0</code> to <code>index1</code>,
	 * inclusive.
	 *
	 * @exception Error      if <code>index0</code> or
	 *						<code>index1</code> lie outside
	 *                                          .get(0, <code>getColumnCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  setColumnSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		columnModel.getSelectionModel().setSelectionInterval(boundColumn(index0), boundColumn(index1), programmatic);
	}

	/**
	 * Adds the rows from <code>index0</code> to <code>index1</code>, inclusive, to
	 * the current selection.
	 *
	 * @exception Error      if <code>index0</code> or <code>index1</code>
	 *                                          lie outside .get(0, <code>getRowCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  addRowSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		selectionModel.addSelectionInterval(boundRow(index0), boundRow(index1), programmatic);
	}

	/**
	 * Adds the columns from <code>index0</code> to <code>index1</code>,
	 * inclusive, to the current selection.
	 *
	 * @exception Error      if <code>index0</code> or
	 *						<code>index1</code> lie outside
	 *                                          .get(0, <code>getColumnCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  addColumnSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		columnModel.getSelectionModel().addSelectionInterval(boundColumn(index0), boundColumn(index1), programmatic);
	}

	/**
	 * Deselects the rows from <code>index0</code> to <code>index1</code>, inclusive.
	 *
	 * @exception Error      if <code>index0</code> or
	 *						<code>index1</code> lie outside
	 *                                          .get(0, <code>getRowCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  removeRowSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		selectionModel.removeSelectionInterval(boundRow(index0), boundRow(index1), programmatic);
	}

	/**
	 * Deselects the columns from <code>index0</code> to <code>index1</code>, inclusive.
	 *
	 * @exception Error      if <code>index0</code> or
	 *						<code>index1</code> lie outside
	 *                                          .get(0, <code>getColumnCount()</code>-1)
	 * @param   index0 one end of the interval
	 * @param   index1 the other end of the interval
     * @param programmatic indicate if this is a programmatic change
	 */
	public void  removeColumnSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		columnModel.getSelectionModel().removeSelectionInterval(boundColumn(index0), boundColumn(index1), programmatic);
	}

	/**
	 * Returns the index of the first selected row, -1 if no row is selected.
	 * @return the index of the first selected row
	 */
	public int  getSelectedRow (){
		return selectionModel.getMinSelectionIndex();
	}

	/**
	 * Returns the index of the first selected column,
	 * -1 if no column is selected.
	 * @return the index of the first selected column
	 */
	public int  getSelectedColumn (){
		return columnModel.getSelectionModel().getMinSelectionIndex();
	}

	/**
	 * Returns the indices of all selected rows.
	 *
	 * @return an array of integers containing the indices of all selected rows,
	 *         or an empty array if no row is selected
	 * @see #getSelectedRow
	 */
	public Array  getSelectedRows (){
		int iMin =selectionModel.getMinSelectionIndex ();
		int iMax =selectionModel.getMaxSelectionIndex ();
		if ((iMin == (- 1)) || (iMax == (- 1))){
			return new Array();
		}

		Array rvTmp =new Array ();
		for(int i =iMin ;i <=iMax ;i ++){
			if (selectionModel.isSelectedIndex(i)){
				rvTmp.push(i);
			}
		}
		return rvTmp;
	}

	/**
	 * Returns the indices of all selected columns.
	 *
	 * @return an array of integers containing the indices of all selected columns,
	 *         or an empty array if no column is selected
	 * @see #getSelectedColumn
	 */
	public Array  getSelectedColumns (){
		return columnModel.getSelectedColumns();
	}

	/**
	 * Returns the number of selected rows.
	 *
	 * @return the number of selected rows, 0 if no rows are selected
	 */
	public int  getSelectedRowCount (){
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

	/**
	 * Returns the number of selected columns.
	 *
	 * @return the number of selected columns, 0 if no columns are selected
	 */
	public int  getSelectedColumnCount (){
		return columnModel.getSelectedColumnCount();
	}

	/**
	 * Returns true if the specified index is in the valid range of rows,
	 * and the row at that index is selected.
	 *
	 * @return true if <code>row</code> is a valid index and the row at
	 *              that index is selected (where 0 is the first row)
	 */
	public boolean  isRowSelected (int row ){
		return selectionModel.isSelectedIndex(row);
	}

	/**
	 * Returns true if the specified index is in the valid range of columns,
	 * and the column at that index is selected.
	 *
	 * @param  column   the column in the column model
	 * @return true if <code>column</code> is a valid index and the column at
	 *              that index is selected (where 0 is the first column)
	 */
	public boolean  isColumnSelected (int column ){
		return columnModel.getSelectionModel().isSelectedIndex(column);
	}

	/**
	 * Returns true if the specified indices are in the valid range of rows
	 * and columns and the cell at the specified position is selected.
	 * @param row   the row being queried
	 * @param column  the column being queried
	 *
	 * @return true if <code>row</code> and <code>column</code> are valid indices
	 *              and the cell at index <code>(row, column)</code> is selected,
	 *              where the first row and first column are at index 0
	 */
	public boolean  isCellSelected (int row ,int column ){
		if ((! getRowSelectionAllowed()) && (! getColumnSelectionAllowed())){
			return false;
		}
		return ((!getRowSelectionAllowed()) || isRowSelected(row))
				&& ((!getColumnSelectionAllowed()) || isColumnSelected(column));
	}

	/**
	 * Scrolls the JTable to make the specified cell completely visible.
	 * @param row the row index
	 * @param column the column index
	 */
	public void  ensureCellIsVisible (int row ,int column ){
		IntRectangle rect =getCellRect(row ,column ,false );
		rect.setLocation(getPixelLocationFromLogicLocation(rect.getLocation()));

		Insets insets =getInsets ();
		int insetsX =insets.left ;
		int insetsY =insets.top ;
		int startX =insetsX ;
		int startY =insetsY +getHeaderHeight ();
		int endX =getWidth ()-insets.right ;
		int endY =getHeight ()-insets.bottom ;
		int moveX =0;
		int moveY =0;
		if(rect.x < startX){
			moveX = rect.x - startX;
		}else if(rect.x + rect.width > endX){
			moveX = rect.x + rect.width - endX;
		}
		if(rect.y < startY){
			moveY = rect.y - startY;
		}else if(rect.y + rect.height > endY){
			moveY = rect.y + rect.height - endY;
		}
		setViewPosition(getViewPosition().move(moveX, moveY));
	}

	protected  changeSelectionModel (ListSelectionModel sm ,int index ,
		toggle:Boolean, extend:Boolean, selected:Boolean, programmatic:Boolean=true):void{
		if (extend){
			if (toggle){
				sm.setAnchorSelectionIndex(index);
			}else{
				sm.setSelectionInterval(sm.getAnchorSelectionIndex(), index, programmatic);
			}
		}else{
			if (toggle){
				if (selected){
					sm.removeSelectionInterval(index, index, programmatic);
				}else{
					sm.addSelectionInterval(index, index, programmatic);
				}
			}else{
				sm.setSelectionInterval(index, index, programmatic);
			}
		}
	}

	/**
	 * Updates the selection models of the table, depending on the state of the
	 * two flags: <code>toggle</code> and <code>extend</code>. Most changes
	 * to the selection that are the result of keyboard or mouse events received
	 * by the UI are channeled through this method so that the behavior may be
	 * overridden by a subclass. Some UIs may need more functionality than
	 * this method provides, such as when manipulating the lead for discontiguous
	 * selection, and may not call into this method for some selection changes.
	 * <p>
	 * This implementation uses the following conventions:
	 * <ul>
	 * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>: <em>false</em>.
	 *      Clear the previous selection and ensure the new cell is selected.
	 * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>: <em>true</em>.
	 *      Extend the previous selection from the anchor to the specified cell,
	 *      clearing all other selections.
	 * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>: <em>false</em>.
	 *      If the specified cell is selected, deselect it. If it is not selected, select it.
	 * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>: <em>true</em>.
	 *      Leave the selection state as it is, but move the anchor index to the specified location.
	 * </ul>
	 * @param  rowIndex   affects the selection at <code>row</code>
	 * @param  columnIndex  affects the selection at <code>column</code>
	 * @param  toggle  see description above
	 * @param  extend  if true, extend the current selection
     * @param programmatic indicate if this is a programmatic change
	 */
	public  changeSelection (int rowIndex ,int columnIndex ,boolean toggle ,
		extend:Boolean, programmatic:Boolean=true):void{
		ListSelectionModel rsm =getSelectionModel ();
		ListSelectionModel csm =getColumnModel ().getSelectionModel ();
		// Check the selection here rather than in each selection model.
		// This is significant in cell selection mode if we are supposed
		// to be toggling the selection. In this case it is better to
		// ensure that the cell's selection state will indeed be changed.
		// If this were done in the code for the selection model it
		// might leave a cell in selection state if the row was
		// selected but the column was not - as it would toggle them both.
		boolean selected =isCellSelected(rowIndex ,columnIndex );
		changeSelectionModel(csm, columnIndex, toggle, extend, selected, programmatic);
		changeSelectionModel(rsm, rowIndex, toggle, extend, selected, programmatic);

    	// Scroll after changing the selection as blit scrolling is immediate,
    	// so that if we cause the repaint after the scroll we end up painting
    	// everything!
		if (false){//getAutoscrolls()){
			IntRectangle cellRect =getCellRect(rowIndex ,columnIndex ,false );
			if(cellRect){
				scrollRectToVisible(cellRect);
			}
		}
	}

	/**
	 * Returns the foreground color for selected cells.
	 *
	 * @return the <code>Color</code> object for the foreground property
	 * @see #setSelectionForeground()
	 * @see #setSelectionBackground()
	 */
	public ASColor  getSelectionForeground (){
		return selectionForeground;
	}

	/**
	 * Sets the foreground color for selected cells.  Cell renderers
	 * can use this color to render text and graphics for selected
	 * cells.
	 * <p>
	 * The default value of this property is defined by the look
	 * and feel implementation.
	 *
	 * @param selectionForeground  the <code>Color</code> to use in the foreground
	 *                             for selected list items
	 * @see #getSelectionForeground()
	 * @see #setSelectionBackground()
	 * @see #setForeground()
	 * @see #setBackground()
	 * @see #setFont()
	 */
	public void  setSelectionForeground (ASColor selectionForeground ){
		ASColor old =this.selectionForeground ;
		this.selectionForeground = selectionForeground;
		//firePropertyChange("selectionForeground", old, selectionForeground);
		if (! selectionForeground.equals(old)){
			repaint();
			revalidate();
		}
	}

	/**
	 * Returns the background color for selected cells.
	 *
	 * @return the <code>Color</code> used for the background of selected list items
	 * @see #setSelectionBackground()
	 * @see #setSelectionForeground()
	 */
	public ASColor  getSelectionBackground (){
		return selectionBackground;
	}

	/**
	 * Sets the background color for selected cells.  Cell renderers
	 * can use this color to the fill selected cells.
	 * <p>
	 * The default value of this property is defined by the look
	 * and feel implementation.
	 * @param selectionBackground  the <code>Color</code> to use for the background
	 *                             of selected cells
	 * @see #getSelectionBackground()
	 * @see #setSelectionForeground()
	 * @see #setForeground()
	 * @see #setBackground()
	 * @see #setFont()
	 */
	public void  setSelectionBackground (ASColor selectionBackground ){
		ASColor old =this.selectionBackground ;
		this.selectionBackground = selectionBackground;
		//firePropertyChange("selectionBackground", old, selectionBackground);
		if (! selectionBackground.equals(old)){
			repaint();
			revalidate();
		}
	}

	/**
	 * Returns the <code>TableColumn</code> object for the column in the table
	 * whose identifier is equal to <code>identifier</code>, when compared using
	 * <code>equals</code>.
	 *
	 * @return  the <code>TableColumn</code> object that matches the identifier
	 * @param   identifier                      the identifier object
	 */
	public TableColumn  getColumn (Object identifier ){
		TableColumnModel cm =getColumnModel ();
		int columnIndex =cm.getColumnIndex(identifier );
		return cm.getColumn(columnIndex);
	}

	/**
	 * Returns the <code>TableColumn</code> object for the column at
	 * <code>columnIndex</code>.
	 *
	 * @param   columnIndex     the index of the desired column
	 * @return  the <code>TableColumn</code> object for
	 *				the column at <code>columnIndex</code>
	 */
	public TableColumn  getColumnAt (int columnIndex ){
		return getColumnModel().getColumn(columnIndex);
	}

	//*****************************************************
	// Informally implement the TableModel interface.
	//*****************************************************

	/**
	 * Maps the index of the column in the view at
	 * <code>viewColumnIndex</code> to the index of the column
	 * in the table model.  Returns the index of the corresponding
	 * column in the model.  If <code>viewColumnIndex</code>
	 * is less than zero, returns <code>viewColumnIndex</code>.
	 *
	 * @param   viewColumnIndex     the index of the column in the view
	 * @return  the index of the corresponding column in the model
	 *
	 * @see #convertColumnIndexToView
	 */
	public int  convertColumnIndexToModel (int viewColumnIndex ){
		if (viewColumnIndex < 0){
			return viewColumnIndex;
		}
		return getColumnModel().getColumn(viewColumnIndex).getModelIndex();
	}

	/**
	 * Maps the index of the column in the table model at
	 * <code>modelColumnIndex</code> to the index of the column
	 * in the view.  Returns the index of the
	 * corresponding column in the view; returns -1 if this column is not
	 * being displayed.  If <code>modelColumnIndex</code> is less than zero,
	 * returns <code>modelColumnIndex</code>.
	 *
	 * @param   modelColumnIndex     the index of the column in the model
	 * @return  the index of the corresponding column in the view
	 *
	 * @see #convertColumnIndexToModel()
	 */
	public int  convertColumnIndexToView (int modelColumnIndex ){
		if (modelColumnIndex < 0){
			return modelColumnIndex;
		}
		TableColumnModel cm =getColumnModel ();
		for(int column =0;column <getColumnCount ();column ++){
			if (cm.getColumn(column).getModelIndex() == modelColumnIndex){
				return column;
			}
		}
		return -1;
	}

	/**
	 * Returns the number of rows in this table's model.
	 * @return the number of rows in this table's model
	 *
	 * @see #getColumnCount()
	 */
	public int  getRowCount (){
		return getModel().getRowCount();
	}

	/**
	 * Returns the number of columns in the column model. Note that this may
	 * be different from the number of columns in the table model.
	 *
	 * @return  the number of columns in the table
	 * @see #getRowCount()
	 * @see #removeColumn()
	 */
	public int  getColumnCount (){
		return getColumnModel().getColumnCount();
	}

	/**
	 * Returns the name of the column appearing in the view at
	 * column position <code>column</code>.
	 *
	 * @param  column    the column in the view being queried
	 * @return the name of the column at position <code>column</code>
	 *		in the view where the first column is column 0
	 */
	public String  getColumnName (int column ){
		return getModel().getColumnName(convertColumnIndexToModel(column));
	}

	/**
	 * Returns the type of the column appearing in the view at
	 * column position <code>column</code>.
	 *
	 * @param   column   the column in the view being queried
	 * @return the type of the column at position <code>column</code>
	 * 		in the view where the first column is column 0
	 */
	public String  getColumnClass (int column ){
		return getModel().getColumnClass(convertColumnIndexToModel(column));
	}

	/**
	 * Returns the cell value at <code>row</code> and <code>column</code>.
	 * <p>
	 * <b>Note</b>: The column is specified in the table view's display
	 *              order, and not in the <code>TableModel</code>'s column
	 *		    order.  This is an important distinction because as the
	 *		    user rearranges the columns in the table,
	 *		    the column at a given index in the view will change.
	 *              Meanwhile the user's actions never affect the model's
	 *              column ordering.
	 *
	 * @param   row             the row whose value is to be queried
	 * @param   column          the column whose value is to be queried
	 * @return  the Object at the specified cell
	 */
	public Object  getValueAt (int row ,int column ){
		return getModel().getValueAt(row, convertColumnIndexToModel(column));
	}

	/**
	 * Sets the value for the cell in the table model at <code>row</code>
	 * and <code>column</code>.
	 * <p>
	 * <b>Note</b>: The column is specified in the table view's display
	 *              order, and not in the <code>TableModel</code>'s column
	 *		    order.  This is an important distinction because as the
	 *		    user rearranges the columns in the table,
	 *		    the column at a given index in the view will change.
	 *              Meanwhile the user's actions never affect the model's
	 *              column ordering.
	 *
	 * <code>aValue</code> is the new value.
	 *
	 * @param   aValue          the new value
	 * @param   row             the row of the cell to be changed
	 * @param   column          the column of the cell to be changed
	 * @see #getValueAt()
	 */
	public void  setValueAt (Object aValue ,int row ,int column ){
		getModel().setValueAt(aValue, row, convertColumnIndexToModel(column));
	}

	/**
	 * Returns true if the cell at <code>row</code> and <code>column</code>
	 * is editable.  Otherwise, invoking <code>setValueAt</code> on the cell
	 * will have no effect.
	 * <p>
	 * <b>Note</b>: The column is specified in the table view's display
	 *              order, and not in the <code>TableModel</code>'s column
	 *		    order.  This is an important distinction because as the
	 *		    user rearranges the columns in the table,
	 *		    the column at a given index in the view will change.
	 *              Meanwhile the user's actions never affect the model's
	 *              column ordering.
	 *
	 *
	 * @param   row      the row whose value is to be queried
	 * @param   column   the column whose value is to be queried
	 * @return  true if the cell is editable
	 * @see #setValueAt()
	 */
	public boolean  isCellEditable (int row ,int column ){
		return getModel().isCellEditable(row, convertColumnIndexToModel(column));
	}

	/**
	 *  Appends <code>aColumn</code> to the end of the array of columns held by
	 *  this <code>JTable</code>'s column model.
	 *  If the column name of <code>aColumn</code> is <code>null</code>,
	 *  sets the column name of <code>aColumn</code> to the name
	 *  returned by <code>getModel().getColumnName()</code>.
	 *  <p>
	 *  To add a column to this <code>JTable</code> to display the
	 *  <code>modelColumn</code>'th column of data in the model with a
	 *  given <code>width</code>, <code>cellRenderer</code>,
	 *  and <code>cellEditor</code> you can use:
	 *  <pre>
	 *
	 *      addColumn(new TableColumn(modelColumn, width, cellRenderer, cellEditor));
	 *
	 *  </pre>
	 *  [Any of the <code>TableColumn</code> constructors can be used
	 *  instead of this one.]
	 *  The model column number is stored inside the <code>TableColumn</code>
	 *  and is used during rendering and editing to locate the appropriates
	 *  data values in the model. The model column number does not change
	 *  when columns are reordered in the view.
	 *
	 *  @param  aColumn         the <code>TableColumn</code> to be added
	 *  @see    #removeColumn
	 */
	public void  addColumn (TableColumn aColumn ){
		if (aColumn.getHeaderValue() == null){
			int modelColumn =aColumn.getModelIndex ();
			String columnName =getModel ().getColumnName(modelColumn );
			aColumn.setHeaderValue(columnName);
		}
		getColumnModel().addColumn(aColumn);
	}

	/**
	 *  Removes <code>aColumn</code> from this <code>JTable</code>'s
	 *  array of columns.  Note: this method does not remove the column
	 *  of data from the model; it just removes the <code>TableColumn</code>
	 *  that was responsible for displaying it.
	 *
	 *  @param  aColumn         the <code>TableColumn</code> to be removed
	 *  @see    #addColumn
	 */
	public void  removeColumn (TableColumn aColumn ){
		getColumnModel().removeColumn(aColumn);
	}

	/**
	 * Moves the column <code>column</code> to the position currently
	 * occupied by the column <code>targetColumn</code> in the view.
	 * The old column at <code>targetColumn</code> is
	 * shifted left or right to make room.
	 *
	 * @param   column                  the index of column to be moved
	 * @param   targetColumn            the new index of the column
	 */
	public void  moveColumn (int column ,int targetColumn ){
		getColumnModel().moveColumn(column, targetColumn);
	}

	/**
	 * Returns the index of the column that <code>point</code> lies in,
	 * or -1 if the result is not in the range
	 * .get(0, <code>getColumnCount()</code>-1).
	 *
	 * @param   point   the location of interest
	 * @return  the index of the column that <code>point</code> lies in,
	 *		or -1 if the result is not in the range
	 *		.get(0, <code>getColumnCount()</code>-1)
	 * @see     #rowAtPoint
	 */
	public int  columnAtPoint (IntPoint point ){
		int x =point.x ;
		return getColumnModel().getColumnIndexAtX(x);
	}

	/**
	 * Returns the index of the row that <code>point</code> lies in,
	 * or -1 if the result is not in the range
	 * .get(0, <code>getRowCount()</code>-1).
	 *
	 * @param   point   the location of interest
	 * @return  the index of the row that <code>point</code> lies in,
	 *          or -1 if the result is not in the range
	 *          .get(0, <code>getRowCount()</code>-1)
	 * @see     #columnAtPoint
	 */
	public int  rowAtPoint (IntPoint point ){
		int y =point.y ;
		int result =Math.floor(y /getRowHeight ());
		if (result < 0){
			return (-1);
		}else if (result >= getRowCount()){
			return (-1);
		}else{
			return result;
		}
	}

	/**
	 * Returns a rectangle for the cell that lies at the intersection of
	 * <code>row</code> and <code>column</code>.
	 * If <code>includeSpacing</code> is true then the value returned
	 * has the full height and width of the row and column
	 * specified. If it is false, the returned rectangle is inset by the
	 * intercell spacing to return the true bounds of the rendering or
	 * editing component as it will be set during rendering.
	 * <p>
	 * If the column index is valid but the row index is less
	 * than zero the method returns a rectangle with the
	 * <code>y</code> and <code>height</code> values set appropriately
	 * and the <code>x</code> and <code>width</code> values both set
	 * to zero. In general, when either the row or column indices indicate a
	 * cell outside the appropriate range, the method returns a rectangle
	 * depicting the closest edge of the closest cell that is within
	 * the table's range. When both row and column indices are out
	 * of range the returned rectangle covers the closest
	 * point of the closest cell.
	 * <p>
	 * In all cases, calculations that use this method to calculate
	 * results along one axis will not fail because of anomalies in
	 * calculations along the other axis. When the cell is not valid
	 * the <code>includeSpacing</code> parameter is ignored.
	 *
	 * @param   row                   the row index where the desired cell
	 *                                is located
	 * @param   column                the column index where the desired cell
	 *                                is located in the display; this is not
	 *                                necessarily the same as the column index
	 *                                in the data model for the table; the
	 *                                {@link #convertColumnIndexToView(int)}
	 *                                method may be used to convert a data
	 *                                model column index to a display
	 *                                column index
	 * @param   includeSpacing        if false, return the true cell bounds -
	 *                                computed by subtracting the intercell
	 *				      spacing from the height and widths of
	 *				      the column and row models
	 *
	 * @return  the rectangle containing the cell at location
	 *          <code>row</code>,<code>column</code>
	 */
	public IntRectangle  getCellRect (int row ,int column ,boolean includeSpacing ){
		IntRectangle r =new IntRectangle ();
		Insets insets =getInsets ();
		boolean valid =true ;
		if (row < 0){
			valid = false;
		}else if (row >= getRowCount()){
			r.y = getViewSize().height - insets.getMarginHeight();
			valid = false;
		}else{
			r.height = getRowHeight();
			r.y = row * r.height;
		}
		if (column < 0){
			valid = false;
		}else if (column >= getColumnCount()){
			r.x = getLastTotalColumnWidth() - insets.getMarginWidth();
			valid = false;
		}else{
			TableColumnModel cm =getColumnModel ();
			for(int i =0;i <column ;i ++)
			{
				r.x += cm.getColumn(i).getWidth();
			}
			r.width = cm.getColumn(column).getWidth();
		}
		if (valid && (!includeSpacing))
		{
			int rmi =getRowMargin ();
			int cmi =getColumnModel ().getColumnMargin ();
			r.setRectXYWH(r.x+cmi, r.y+rmi, r.width-cmi, r.height-rmi);
		}
		return r;
	}

	/**
	 * Returns the location in the JTable view area of the logic location.
	 */
	public IntPoint  getPixelLocationFromLogicLocation (IntPoint p ){
		IntPoint pp =p.clone ();
		IntPoint startP =getViewStartPoint ();
		pp.move(startP.x, startP.y);
		return pp;
	}

	/**
	 * Returns the logic location in the JTable view area of the pixel location.
	 */
	public IntPoint  getLogicLocationFromPixelLocation (IntPoint p ){
		IntPoint pp =p.clone ();
		IntPoint startP =getViewStartPoint ();
		pp.move(-startP.x, -startP.y);
		return pp;
	}

	private IntPoint  getViewStartPoint (){
		IntPoint viewPos =getViewPosition ();
		Insets insets =getInsets ();
		int insetsX =insets.left ;
		int insetsY =insets.top ;
		int startX =insetsX -viewPos.x ;
		int startY =insetsY +getHeaderHeight ()-viewPos.y ;
		return new IntPoint(startX, startY);
	}

	/**
	 * Returns the header height.
	 */
	public int  getHeaderHeight (){
		if(getTableHeader() == null){
			return 0;
		}else{
			return getTableHeader().getHeight();
		}
	}

	private int  viewIndexForColumn (TableColumn aColumn ){
		TableColumnModel cm =getColumnModel ();
		for(int column =0;column <cm.getColumnCount ();column ++)
		{
			if (cm.getColumn(column) == aColumn)
			{
				return column;
			}
		}
		return -1;
	}

	private TableColumn  getResizingColumn (){
		return (((tableHeader == null) ? null : tableHeader.getResizingColumn()));
	}

	private void  setWidthsFromPreferredWidths (boolean inverse ){
		Insets insets =getInsets ();
		int totalWidth =(autoResizeMode ==AUTO_RESIZE_OFF ? getLastTotalColumnWidth() : (getWidth() - insets.getMarginWidth()));
		int totalPreferred =getPreferredSize ().width -insets.getMarginWidth ();
		int target =((!inverse) ? totalWidth : totalPreferred);

		TableColumnModel cm =columnModel ;
		Resizable3 r =new Resizable3Imp1(cm ,inverse );
		adjustSizes3(target, r, inverse);
	}

	// Distribute delta over columns, as indicated by the autoresize mode.
	private void  accommodateDelta (int resizingColumnIndex ,int delta ){
		int columnCount =getColumnCount ();
		int from =resizingColumnIndex ;
		int _to =columnCount ;
		// Use the mode to determine how to absorb the changes.
		switch (autoResizeMode) {
			case AUTO_RESIZE_NEXT_COLUMN:
				from = (from + 1);
				_to = Math.min(from + 1, columnCount);
				break;
			case AUTO_RESIZE_SUBSEQUENT_COLUMNS:
				from = (from + 1);
				_to = columnCount;
				break;
			case AUTO_RESIZE_LAST_COLUMN:
				from = (columnCount - 1);
				_to = (from + 1);
				break;
			case AUTO_RESIZE_ALL_COLUMNS:
				from = 0;
				_to = columnCount;
				break;
			default:
				return ;
		}
		int start =from ;
		int end =_to ;
		TableColumnModel cm =columnModel ;
		Resizable3 r =new Resizable3Imp2(cm ,start ,end );
		int totalWidth =0;
		for(int i =from ;i <_to ;i ++)
		{
			TableColumn aColumn =columnModel.getColumn(i );
			int input =aColumn.getWidth ();
			totalWidth = (totalWidth + input);
		}
		adjustSizes3(totalWidth + delta, r, false);
		return ;
	}

	private void  adjustSizes3 (int target ,Resizable3 r ,boolean inverse ){
		int N =r.getElementCount ();
		int totalPreferred =0;
		for(int i =0;i <N ;i ++){
			totalPreferred += r.getMidPointAt(i);
		}
		Resizable2 s =new Resizable2Imp1(r ,(target <totalPreferred )==(!inverse));
		adjustSizes2(target, s, !inverse);
	}

	private void  adjustSizes2 (int target ,Resizable2 r ,boolean limitToRange ){
		int totalLowerBound =0;
		int totalUpperBound =0;
		int N =r.getElementCount ();
		int i ;
		for (i = 0; i < N; i++){
			totalLowerBound += r.getLowerBoundAt(i);
			totalUpperBound += r.getUpperBoundAt(i);
		}
		if (limitToRange){
			target = Math.min(Math.max(totalLowerBound, target), totalUpperBound);
		}
		for (i = 0; i < N; i++){
			int lowerBound =r.getLowerBoundAt(i );
			int upperBound =r.getUpperBoundAt(i );

			// Check for zero. This happens when the distribution of the delta
			// finishes early due to a series of "fixed" entries at the end.
			// In this case, lowerBound == upperBound, for all subsequent terms.
			int newSize ;
			if (totalLowerBound == totalUpperBound){
				newSize = lowerBound;
			}else{
				double f =(target -totalLowerBound )/(totalUpperBound -totalLowerBound );
				newSize = Math.round(lowerBound + (f * (upperBound - lowerBound)));
			}
			r.setSizeAt(newSize, i);
			target -= newSize;
			totalLowerBound -= lowerBound;
			totalUpperBound -= upperBound;
		}
	}

	/**
	 * editCellAt(row:int, column:int, clickCount:int):Boolean<br>
	 * editCellAt(row:int, column:int):Boolean
	 * <p>
	 * Programmatically starts editing the cell at <code>row</code> and
	 * <code>column</code>, if those indices are in the valid range, and
	 * the cell at those indices is editable.
	 *
	 * @param   row                             the row to be edited
	 * @param   column                          the column to be edited
	 * @param	clickCount						the click count, if force to edite, pass -1 to this param.
	 * @return  false if for any reason the cell cannot be edited,
	 *                or if the indices are invalid
	 */
	public boolean  editCellAt (int row ,int column ,int clickCount =-1){
		if ((cellEditor != null) && (! cellEditor.stopCellEditing())){
			return false;
		}
		if ((((row < 0) || (row >= getRowCount())) || (column < 0)) || (column >= getColumnCount())){
			return false;
		}
		if (!isCellEditable(row, column)){
			return false;
		}
		if(cellEditor != null){
			removeEditor();
		}
		TableCellEditor editor =getCellEditorOfRowColumn(row ,column );
		if ((editor != null) && (clickCount == -1 || editor.isCellEditable(clickCount))){
			IntRectangle cb =getCellRect(row ,column ,true );
			cb.setLocation(getPixelLocationFromLogicLocation(cb.getLocation()));
			editor.startCellEditing(this, getValueAt(row, column), cb);
			setCellEditor(editor);
			setEditingRow(row);
			setEditingColumn(column);
			editor.removeCellEditorListener(this);
			editor.addCellEditorListener(this);

			_storedValue = getValueAt(row, column);
			dispatchEvent(new TableCellEditEvent(TableCellEditEvent.EDITING_STARTED, row, column, _storedValue));
			return true;
		}
		return false;
	}

	/**
	 * Returns true if a cell is being edited.
	 *
	 * @return  true if the table is editing a cell
	 * @see     #getEditingColumn()
	 * @see     #getEditingRow()
	 */
	public boolean  isEditing (){
		return (((cellEditor == null) ? false : true));
	}

	/**
	 * Returns the index of the column that contains the cell currently
	 * being edited.  If nothing is being edited, returns -1.
	 *
	 * @return  the index of the column that contains the cell currently
	 *		being edited; returns -1 if nothing being edited
	 * @see #getEditingRow()
	 */
	public int  getEditingColumn (){
		return editingColumn;
	}

	/**
	 * Returns the index of the row that contains the cell currently
	 * being edited.  If nothing is being edited, returns -1.
	 *
	 * @return  the index of the row that contains the cell currently
	 *		being edited; returns -1 if nothing being edited
	 * @see #getEditingColumn()
	 */
	public int  getEditingRow (){
		return editingRow;
	}

	//*****************************************
	//          Managing models
	//*****************************************

	/**
	 * Sets the data model for this table to <code>newModel</code> and registers
	 * with it for listener notifications from the new data model.
	 *
	 * @param   dataModel        the new data source for this table
	 * @see     #getModel()
	 */
	public void  setModel (TableModel dataModel ){
		if (dataModel == null){
			trace("Can't set null TableModel to JTable, Ignored");
			return;
		}
		if (this.dataModel != dataModel){
			TableModel old =this.dataModel ;
			if (old != null)
			{
				old.removeTableModelListener(this);
			}
			this.dataModel = dataModel;
			dataModel.addTableModelListener(this);
			tableChanged(new TableModelEvent(dataModel, TableModelEvent.HEADER_ROW));
			//firePropertyChange("model", old, dataModel);
		}
	}

	/**
	 * Returns the <code>TableModel</code> that provides the data displayed by this
	 * <code>JTable</code>.
	 *
	 * @return  the <code>TableModel</code> that provides the data displayed by this <code>JTable</code>
	 * @see     #setModel()
	 */
	public TableModel  getModel (){
		return dataModel;
	}

	/**
	 * Sets the column model for this table to <code>newModel</code> and registers
	 * for listener notifications from the new column model. Also sets
	 * the column model of the <code>JTableHeader</code> to <code>columnModel</code>.
	 *
	 * @param   columnModel        the new data source for this table
	 * @see     #getColumnModel()
	 */
	public void  setColumnModel (TableColumnModel columnModel ){
		if (columnModel == null){
			trace("Cannot set a null ColumnModel to JTable, Ignored");
			return;
		}
		TableColumnModel old =this.columnModel ;
		if (columnModel != old){
			if (old != null)
			{
				old.removeColumnModelListener(this);
			}
			this.columnModel = columnModel;
			columnModel.addColumnModelListener(this);
			if (tableHeader != null)
			{
				tableHeader.setColumnModel(columnModel);
			}
			//firePropertyChange("columnModel", old, columnModel);
			resizeAndRepaint();
		}
	}

	/**
	 * Returns the <code>TableColumnModel</code> that contains all column information
	 * of this table.
	 *
	 * @return  the object that provides the column state of the table
	 * @see     #setColumnModel()
	 */
	public TableColumnModel  getColumnModel (){
		return columnModel;
	}

	/**
	 * Sets the row selection model for this table to <code>newModel</code>
	 * and registers for listener notifications from the new selection model.
	 *
	 * @param   newModel        the new selection model, if it is null, nothing change.
	 * @see     #getSelectionModel()
	 */
	public void  setSelectionModel (ListSelectionModel newModel ){
		if (newModel == null){
			trace("Cannot set a null SelectionModel to JTable, Ignored");
			return;
		}
		ListSelectionModel oldModel =selectionModel ;
		if (newModel != oldModel){
			if (oldModel != null){
				oldModel.removeListSelectionListener(__listSelectionChanged);
			}
			selectionModel = newModel;
			newModel.addListSelectionListener(__listSelectionChanged);
			//firePropertyChange("selectionModel", oldModel, newModel);
			repaint();
			checkLeadAnchor();
		}
	}

	/**
	 * Returns the <code>ListSelectionModel</code> that is used to maintain row
	 * selection state.
	 *
	 * @return  the object that provides row selection state, <code>null</code>
	 *          if row selection is not allowed
	 * @see     #setSelectionModel()
	 */
	public ListSelectionModel  getSelectionModel (){
		return selectionModel;
	}

	private void  checkLeadAnchor (){
		TableModel model =getModel ();
		if (model == null){
			return ;
		}
		int lead =selectionModel.getLeadSelectionIndex ();
		int count =model.getRowCount ();
		if (count == 0){
			if (lead != (- 1)){
				//TODO adjusting
				//selectionModel.setValueIsAdjusting(true);
				selectionModel.setAnchorSelectionIndex(- 1);
				selectionModel.setLeadSelectionIndex(- 1);
				//selectionModel.setValueIsAdjusting(false);
			}
		}else{
			if (lead == (- 1)){
				if (selectionModel.isSelectedIndex(0)){
					selectionModel.addSelectionInterval(0, 0, true);
				}else{
					selectionModel.removeSelectionInterval(0, 0, true);
				}
			}
		}
	}

	//*********************************************
	// Implementing TableModelListener interface
	//*********************************************

	/**
	 * Invoked when this table's <code>TableModel</code> generates
	 * a <code>TableModelEvent</code>.
	 * The <code>TableModelEvent</code> should be constructed in the
	 * coordinate system of the model; the appropriate mapping to the
	 * view coordinate system is performed by this <code>JTable</code>
	 * when it receives the event.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by <code>JTable</code>.
	 */
	public void  tableChanged (TableModelEvent e ){
		if ((e == null) || (e.getFirstRow() == TableModelEvent.HEADER_ROW)){
			clearSelection();
			checkLeadAnchor();
			if (getAutoCreateColumnsFromModel()){
				createDefaultColumnsFromModel();
				return ;
			}
			resizeAndRepaint();
			return ;
		}
		if (e.getType() == TableModelEvent.INSERT){
			tableRowsInserted(e);
			return ;
		}
		if (e.getType() == TableModelEvent.DELETE){
			tableRowsDeleted(e);
			return ;
		}
		int end =e.getLastRow ();
		if (end != int.MAX_VALUE){
			revalidate();
		}else{
			clearSelection();
			resizeAndRepaint();
		}
	}
	private void  tableRowsInserted (TableModelEvent e ){
		int start =e.getFirstRow ();
		int end =e.getLastRow ();
		if (start < 0){
			start = 0;
		}
		if (end < 0){
			end = (getRowCount() - 1);
		}
		int length =((end -start )+1);
		selectionModel.insertIndexInterval(start, length, true);
		checkLeadAnchor();
		resizeAndRepaint();
	}
	private void  tableRowsDeleted (TableModelEvent e ){
		int start =e.getFirstRow ();
		int end =e.getLastRow ();
		if (start < 0){
			start = 0;
		}
		if (end < 0){
			end = (getRowCount() - 1);
		}
		//int deletedCount =((end -start )+1);
		//int previousRowCount =(getRowCount ()+deletedCount );
		selectionModel.removeIndexInterval(start, end);
		checkLeadAnchor();
		//int rh =getAllRowHeight ();
		//IntRectangle drawRect =new IntRectangle(0,(start *rh ),getColumnModel ().getTotalColumnWidth (),((previousRowCount -start )*rh ));
		resizeAndRepaint();
	}

	//**************************************************
	// Implementing TableColumnModelListener interface
	//**************************************************
	/**
	 * Invoked when a column is added to the table column model.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @see TableColumnModelListener
	 */
	public void  columnAdded (TableColumnModelEvent e ){
		if (isEditing()){
			removeEditor();
		}
		resizeAndRepaint();
	}
	/**
	 * Invoked when a column is removed from the table column model.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @see TableColumnModelListener
	 */
	public void  columnRemoved (TableColumnModelEvent e ){
		if (isEditing()){
			removeEditor();
		}
		resizeAndRepaint();
	}
	/**
	 * Invoked when a column is repositioned. If a cell is being
	 * edited, then editing is stopped and the cell is redrawn.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param e   the event received
	 * @see TableColumnModelListener
	 */
	public void  columnMoved (TableColumnModelEvent e ){
		if (isEditing()){
			removeEditor();
		}
		resizeAndRepaint();
	}
	/**
	 * Invoked when a column is moved due to a margin change.
	 * If a cell is being edited, then editing is stopped and the cell
	 * is redrawn.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param  e    the event received
	 * @see TableColumnModelListener
	 */
	public void  columnMarginChanged (TableColumnModel source ){
		if (isEditing()){
			removeEditor();
		}
		TableColumn resizingColumn =getResizingColumn ();
		if ((resizingColumn != null) && (autoResizeMode == AUTO_RESIZE_OFF)){
			resizingColumn.setPreferredWidth(resizingColumn.getWidth());
		}
		resizeAndRepaint();
	}

	/**
	 * Invoked when the selection model of the <code>TableColumnModel</code>
	 * is changed.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param  e  the event received
	 * @see TableColumnModelListener
	 */
	public void  columnSelectionChanged (TableColumnModel source ,int firstIndex ,int lastIndex ,boolean programmatic ){
		dispatchEvent(new SelectionEvent(SelectionEvent.COLUMN_SELECTION_CHANGED, firstIndex, lastIndex, programmatic));

		boolean isAdjusting =false ;//e.getValueIsAdjusting ();
		if (columnSelectionAdjusting && (! isAdjusting)){
			columnSelectionAdjusting = false;
			return ;
		}
		columnSelectionAdjusting = isAdjusting;
		if ((getRowCount() <= 0) || (getColumnCount() <= 0)){
			return ;
		}
		resizeAndRepaint();
	}

	//************************************************
	// Implementing ListSelectionListener interface
	//************************************************

	/**
	 * Invoked when the row selection changes -- repaints to show the new
	 * selection.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param e   the event received
	 */
	public void  __listSelectionChanged (SelectionEvent e ){
		boolean isAdjusting =false ;//e.getValueIsAdjusting ();
		if (rowSelectionAdjusting && (!isAdjusting))
		{
			rowSelectionAdjusting = false;
			return ;
		}
		rowSelectionAdjusting = isAdjusting;
		dispatchEvent(new SelectionEvent(SelectionEvent.ROW_SELECTION_CHANGED,
			e.getFirstIndex(), e.getLastIndex(), e.isProgrammatic()));
		resizeAndRepaint();
	}

	//************************************************
	// Implementing CellEditorListener interface
	//************************************************

	/**
	 * Invoked when editing is finished. The changes are saved and the
	 * editor is discarded.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param  e  the event received
	 * @see CellEditorListener
	 */
	public void  editingStopped (CellEditor source ){
		TableCellEditor editor =getCellEditor ();
		if (editor != null){
			Object value =editor.getCellEditorValue ();
			setValueAt(value, editingRow, editingColumn);
			dispatchEvent(new TableCellEditEvent(TableCellEditEvent.EDITING_STOPPED, editingRow, editingColumn, _storedValue, value));
			removeEditor();
		}
	}

	/**
	 * Invoked when editing is canceled. The editor object is discarded
	 * and the cell is rendered once again.
	 * <p>
	 * Application code will not use these methods explicitly, they
	 * are used internally by JTable.
	 *
	 * @param  e  the event received
	 * @see CellEditorListener
	 */
	public void  editingCanceled (CellEditor source ){
		dispatchEvent(new TableCellEditEvent(TableCellEditEvent.EDITING_CANCELED, editingRow, editingColumn));
		removeEditor();
	}

	/**
	 * Sets the preferred size of the viewport for this table.
	 *
	 * @param size  a <code>IntDimension</code> object specifying the <code>preferredSize</code> of a
	 *              <code>JViewport</code> whose view is this table
	 */
	public void  setPreferredScrollableViewportSize (IntDimension size ){
		preferredViewportSize = size.clone();
	}

	/**
	 * Returns the preferred size of the viewport for this table.
	 *
	 * @return a <code>IntDimension</code> object containing the <code>preferredSize</code> of the <code>JViewport</code>
	 *         which displays this table
	 */
	public IntDimension  getPreferredScrollableViewportSize (){
		return preferredViewportSize;
	}

	//*********************************************************
	//         Initialize Defaults
	//*********************************************************

	private void  initializeLocalVars (){
		createDefaultCellFactories();
		createDefaultEditors();
		setTableHeader(createDefaultTableHeader());
		setShowGrid(true);
		setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		setRowHeight(20);
		setRowMargin(1);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setCellEditor(null);
		setEditingColumn(-1);
		setEditingRow(-1);
		setPreferredScrollableViewportSize(new IntDimension(450, 400));
		//setAutoscrolls(true);
	}
	public TableModel  createDefaultDataModel (){
		return (new DefaultTableModel()).initWithRowcountColumncount(0, 0);
	}
	public TableColumnModel  createDefaultColumnModel (){
		return new DefaultTableColumnModel();
	}
	public ListSelectionModel  createDefaultSelectionModel (){
		return new DefaultListSelectionModel();
	}
	public JTableHeader  createDefaultTableHeader (){
		return new JTableHeader(columnModel);
	}
	public void  createDefaultCellFactories (){
		defaultRenderersByColumnClass = new HashMap();
		defaultRenderersByColumnClass.put("Object", new GeneralTableCellFactoryUIResource(PoorTextCell));
	}
	public void  createDefaultEditors (){
		defaultEditorsByColumnClass = new HashMap();
		defaultEditorsByColumnClass.put("Number", new DefaultNumberTextFieldCellEditor());
		defaultEditorsByColumnClass.put("Boolean", new DefaultCheckBoxCellEditor());
		defaultEditorsByColumnClass.put("Object", new DefaultTextFieldCellEditor());
	}

	public void  resizeAndRepaint (){
		revalidate();
		repaint();
	}

	public TableCellEditor  getCellEditor (){
		return cellEditor;
	}

	public void  setCellEditor (TableCellEditor anEditor ){
		//TableCellEditor oldEditor =cellEditor ;
		cellEditor = anEditor;
		//firePropertyChange("tableCellEditor", oldEditor, anEditor);
	}

	public void  setEditingColumn (int aColumn ){
		editingColumn = aColumn;
	}

	public void  setEditingRow (int aRow ){
		editingRow = aRow;
	}

	public TableCellFactory  getCellFactory (int row ,int column ){
		//trace("getCellFactory...");
		TableColumn tableColumn =getColumnModel ().getColumn(column );
		//trace("tableColumn = " + tableColumn);
		TableCellFactory renderer =tableColumn.getCellFactory ();
		//trace("renderer = " + renderer);
		if (renderer == null)
		{
			renderer = getDefaultCellFactory(getColumnClass(column));
			//trace("renderer = " + renderer);
		}
		return renderer;
	}

	public TableCellEditor  getCellEditorOfRowColumn (int row ,int column ){
		TableColumn tableColumn =getColumnModel ().getColumn(column );
		TableCellEditor editor =tableColumn.getCellEditor ();
		if (editor == null){
			editor = getDefaultEditor(getColumnClass(column));
		}
		return editor;
	}

	public void  removeEditor (){
//		KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("permanentFocusOwner", editorRemover);
//		editorRemover = null;
		TableCellEditor editor =getCellEditor ();
		if (editor != null){
			editor.removeCellEditorListener(this);
			setCellEditor(null);
			setEditingColumn(- 1);
			setEditingRow(- 1);
			requestFocus();
		}
	}

	//*****************************************************
	//           Layout Implementation
	//*****************************************************

	/**
	 * Causes this table to lay out its rows and columns.  Overridden so
	 * that columns can be resized to accomodate a change in the size of
	 * a containing parent.
	 * Resizes one or more of the columns in the table
	 * so that the total width of all of this <code>JTable</code>'s
	 * columns is equal to the width of the table.
	 * <p>
	 * Before the layout begins the method gets the
	 * <code>resizingColumn</code> of the <code>tableHeader</code>.
	 * When the method is called as a result of the resizing of an enclosing window,
	 * the <code>resizingColumn</code> is <code>null</code>. This means that resizing
	 * has taken place "outside" the <code>JTable</code> and the change -
	 * or "delta" - should be distributed to all of the columns regardless
	 * of this <code>JTable</code>'s automatic resize mode.
	 * <p>
	 * If the <code>resizingColumn</code> is not <code>null</code>, it is one of
	 * the columns in the table that has changed size rather than
	 * the table itself. In this case the auto-resize modes govern
	 * the way the extra (or deficit) space is distributed
	 * amongst the available columns.
	 * <p>
	 * The modes are:
	 * <ul>
	 * <li>  AUTO_RESIZE_OFF: Don't automatically adjust the column's
	 * widths at all. Use a horizontal scrollbar to accomodate the
	 * columns when their sum exceeds the width of the
	 * <code>Viewport</code>.  If the <code>JTable</code> is not
	 * enclosed in a <code>JScrollPane</code> this may
	 * leave parts of the table invisible.
	 * <li>  AUTO_RESIZE_NEXT_COLUMN: Use just the column after the
	 * resizing column. This results in the "boundary" or divider
	 * between adjacent cells being independently adjustable.
	 * <li>  AUTO_RESIZE_SUBSEQUENT_COLUMNS: Use all columns after the
	 * one being adjusted to absorb the changes.  This is the
	 * default behavior.
	 * <li>  AUTO_RESIZE_LAST_COLUMN: Automatically adjust the
	 * size of the last column only. If the bounds of the last column
	 * prevent the desired size from being allocated, set the
	 * width of the last column to the appropriate limit and make
	 * no further adjustments.
	 * <li>  AUTO_RESIZE_ALL_COLUMNS: Spread the delta amongst all the columns
	 * in the <code>JTable</code>, including the one that is being
	 * adjusted.
	 * </ul>
	 * <p>
	 * <bold>Note:</bold> When a <code>JTable</code> makes adjustments
	 *   to the widths of the columns it respects their minimum and
	 *   maximum values absolutely.  It is therefore possible that,
	 *   even after this method is called, the total width of the columns
	 *   is still not equal to the width of the table. When this happens
	 *   the <code>JTable</code> does not put itself
	 *   in AUTO_RESIZE_OFF mode to bring up a scroll bar, or break other
	 *   commitments of its current auto-resize mode -- instead it
	 *   allows its bounds to be set larger (or smaller) than the total of the
	 *   column minimum or maximum, meaning, either that there
	 *   will not be enough room to display all of the columns, or that the
	 *   columns will not fill the <code>JTable</code>'s bounds.
	 *   These respectively, result in the clipping of some columns
	 *   or an area being painted in the <code>JTable</code>'s
	 *   background color during painting.
	 * <p>
	 *   The mechanism for distributing the delta amongst the available
	 *   columns is provided in a private method in the <code>JTable</code>
	 *   class:
	 * <pre>
	 *   adjustSizes(long targetSize, final Resizable3 r, boolean inverse)
	 * </pre>
	 *   an explanation of which is provided in the following section.
	 *   <code>Resizable3</code> is a private
	 *   interface that allows any data structure containing a collection
	 *   of elements with a size, preferred size, maximum size and minimum size
	 *   to have its elements manipulated by the algorithm.
	 * <p>
	 * <H3> Distributing the delta </H3>
	 * <p>
	 * <H4> Overview </H4>
	 * <P>
	 * Call "DELTA" the difference between the target size and the
	 * sum of the preferred sizes of the elements in r. The individual
	 * sizes are calculated by taking the original preferred
	 * sizes and adding a share of the DELTA - that share being based on
	 * how far each preferred size is from its limiting bound (minimum or
	 * maximum).
	 * <p>
	 * <H4>Definition</H4>
	 * <P>
	 * Call the individual constraints min.get(i), max.get(i), and pref.get(i).
	 * <p>
	 * Call their respective sums: MIN, MAX, and PREF.
	 * <p>
	 * Each new size will be calculated using:
	 * <p>
	 * <pre>
	 *          size.put(i,  pref.get(i) + delta.get(i);
	 * </pre>
	 * where each individual delta.get(i) is calculated according to:
	 * <p>
	 * If (DELTA < 0) we are in shrink mode where:
	 * <p>
	 * <PRE>
	 *                        DELTA
	 *          delta.put(i,  ------------ * (pref.get(i) - min.get(i));
	 *                     (PREF - MIN)
	 * </PRE>
	 * If (DELTA > 0) we are in expand mode where:
	 * <p>
	 * <PRE>
	 *                        DELTA
	 *          delta.put(i,  ------------ * (max.get(i) - pref.get(i));
	 *                      (MAX - PREF)
	 * </PRE>
	 * <P>
	 * The overall effect is that the total size moves that same percentage,
	 * k, towards the total minimum or maximum and that percentage guarantees
	 * accomodation of the required space, DELTA.
	 *
	 * <H4>Details</H4>
	 * <P>
	 * Naive evaluation of the formulae presented here would be subject to
	 * the aggregated rounding errors caused by doing this operation in finite
	 * precision (using ints). To deal with this, the multiplying factor above,
	 * is constantly recalculated and this takes account of the rounding
	 * errors in the previous iterations. The result is an algorithm that
	 * produces a set of integers whose values exactly sum to the supplied
	 * <code>targetSize</code>, and does so by spreading the rounding
	 * errors evenly over the given elements.
	 *
	 * <H4>When the MAX and MIN bounds are hit</H4>
	 * <P>
	 * When <code>targetSize</code> is outside the .get(MIN, MAX) range,
	 * the algorithm sets all sizes to their appropriate limiting value
	 * (maximum or minimum).
	 *
	 */
	 public void  doLayout (){
		//trace("doLayout");
		TableColumn resizingColumn =getResizingColumn ();
		if (resizingColumn == null){
			setWidthsFromPreferredWidths(false);
		}else{
			int w =getLastTotalColumnWidth ();
			int columnIndex =viewIndexForColumn(resizingColumn );
			int delta =(w -getColumnModel ().getTotalColumnWidth ());
			accommodateDelta(columnIndex, delta);
			delta = (w - getColumnModel().getTotalColumnWidth());
        	// If the delta cannot be completely accomodated, then the
        	// resizing column will have to take any remainder. This means
        	// that the column is not being allowed to take the requested
        	// width. This happens under many circumstances: For example,
        	// AUTO_RESIZE_NEXT_COLUMN specifies that any delta be distributed
        	// to the column after the resizing column. If one were to attempt
        	// to resize the last column of the table, there would be no
        	// columns after it, and hence nowhere to distribute the delta.
        	// It would then be given entirely back to the resizing column,
        	// preventing it from changing size.
			if (delta != 0 && (autoResizeMode != AUTO_RESIZE_OFF)){
				resizingColumn.setWidth(resizingColumn.getWidth() + delta);
			}
			setWidthsFromPreferredWidths(true);
		}
		lastTotalColumnWidth = getColumnModel().getTotalColumnWidth();
		super.doLayout();
	}

	private int  getLastTotalColumnWidth (){
		if(-1 == lastTotalColumnWidth){
			if(autoResizeMode == AUTO_RESIZE_OFF){
				lastTotalColumnWidth = getPreferredSize().width - getInsets().getMarginWidth();
			}else{
				lastTotalColumnWidth = getWidth() - getInsets().getMarginWidth();
			}
		}
		return lastTotalColumnWidth;
	}

	private int lastTotalColumnWidth =-1;

	private void  layoutCells (){
		Insets insets =getInsets ();
		int insetsX =insets.left ;
		int insetsY =insets.top ;

		int headerHeight =getTableHeader ().getPreferredHeight ();

		headerPane.setComBoundsXYWH(
			insetsX, insetsY,
			getWidth() - insets.getMarginWidth(),
			headerHeight
		);

		//layout table header
		getTableHeader().setLocationXY(- viewPosition.x, 0);
		getTableHeader().setSizeWH(
			getLastTotalColumnWidth(),
			headerHeight);
		getTableHeader().validate();
		getTableHeader().paintImmediately();

		IntRectangle b =new IntRectangle ();
		b.setSize(getExtentSize());
		b.setLocation(viewPosition);

		IntRectangle cellPaneBounds =new IntRectangle ();
		cellPaneBounds.setSize(b.getSize());
		cellPaneBounds.setLocation(new IntPoint(insetsX, insetsY+getTableHeader().getHeight()));
		cellPane.setComBounds(cellPaneBounds);

		if (getRowCount() <= 0 || getColumnCount() <= 0) {
			return;
		}

		IntPoint upperLeft =b.getLocation ();
		IntPoint lowerRight =b.rightBottom ();
		int rMin =rowAtPoint(upperLeft );
		int rMax =rowAtPoint(lowerRight );
		int columnCount =getColumnCount ();
		// This should never happen (as long as our bounds intersect the clip,
		// which is why we bail above if that is the case).
		if (rMin == -1) {
			rMin = 0;
		}
		// If the table does not have enough rows to fill the view we'll get -1.
		// (We could also get -1 if our bounds don't intersect the clip,
		// which is why we bail above if that is the case).
		// Replace this with the index of the last row.
		if (rMax == -1) {
			rMax = getRowCount() - 1;
		}
		int cMin =columnAtPoint(upperLeft );
		int cMax =columnAtPoint(lowerRight );
		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}
		// If the table does not have enough columns to fill the view we'll get -1.
		// Replace this with the index of the last column.
		if (cMax == -1) {
			cMax = columnCount - 1;
		}

		//layout each eyeable cells

		TableColumnModel cm =getColumnModel ();
		int columnMargin =cm.getColumnMargin ();
		int rowMargin =getRowMargin ();

		IntRectangle cellRect ;
		IntRectangle tempRect =new IntRectangle ();
		TableColumn aColumn ;
		int columnWidth ;
		int cr =0;//row in visible cell table
		int cc =0;//column in visible cell table

		int startX =-viewPosition.x ;
		int startY =-viewPosition.y ;

		int row =rMin -1;
		boolean showHL =getShowHorizontalLines ();
		boolean showVL =getShowVerticalLines ();
		while((++row) <= rMax){
			if(cr >= rowCells.length()){
				break;
			}
			cellRect = getCellRect(row, cMin, false);
			if(showHL && row == getRowCount() - 1){
				cellRect.height -= rowMargin;
			}
			int column =cMin -1;
			while((++column) <= cMax){
				cc = column;
				if(cc >= rowCells.get(cr).length()){
					break;
				}
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;
				tempRect.setRectXYWH(cellRect.x + startX, cellRect.y + startY, cellRect.width, cellRect.height);
				if(showVL && column == (columnCount - 1)){
					tempRect.width -= columnMargin;
				}
				layoutCell(row, column, tempRect, cr, cc);
				cellRect.x += columnWidth;
			}
			//invisible others columns
			TableCell cell ;
			int cellColumnCount =rowCells.get(0).length ;
			for(cc=0; cc<cMin; cc++){
				cell = rowCells.get(cr).get(cc);
				cell.getCellComponent().setVisible(false);
			}
			for(cc=cMax+1; cc < cellColumnCount; cc++){
				cell = rowCells.get(cr).get(cc);
				cell.getCellComponent().setVisible(false);
			}
			cr++;
		}
		//invisible others rows
		for(int i =cr ;i <rowCells.length ;i ++){
			for(int c =columnCount -1;c >=0;c --){
				cell = rowCells.get(i).get(c);
				cell.getCellComponent().setVisible(false);
			}
		}
	}

	private void  layoutCell (int row ,int column ,IntRectangle cellRect ,int cr ,int cc ){
         value = getValueAt(row ,column );

//        var isSelected:Boolean = false;
//        var hasFocus:Boolean = false;
//
//        isSelected = isCellSelected(row, column);
//
//        var rowIsLead:Boolean = (selectionModel.getLeadSelectionIndex() == row);
//        var colIsLead:Boolean = (columnModel.getSelectionModel().getLeadSelectionIndex() == column);
//
//        hasFocus = (rowIsLead && colIsLead) && isFocusOwner();

        TableCell cell =rowCells.get(cr).get(cc) ;
        if(cell == null){
        	trace("Logic Error : rowCells.get(" + cr + ").put(" + cc + ",  undefined"));
        	trace("rowCells.length = " + rowCells.length());
        }
        cell.setCellValue(value);
        cell.setTableCellStatus(this, isCellSelected(row, column), row, column);
        cell.getCellComponent().setComBounds(cellRect);
        cell.getCellComponent().setVisible(true);
        cell.getCellComponent().validate();
    	cell.getCellComponent().paintImmediately();
	}

	private Array lastColumnCellFactories ;
	//track if the cell factory changed, if changed, recreate all cells
	private void  synCellClasses (){
		if(lastColumnCellFactories==null || lastColumnCellFactories.length != getColumnCount()){
			clearCells();
			return;
		}
		for(int i =lastColumnCellFactories.length -1;i >=0;i --){
			if(lastColumnCellFactories.get(i) != this.getCellFactory(0, i)){
				clearCells();
				return;
			}
		}
	}

	private void  clearCells (){
		//trace("Clear cells!!!!!!!!!!!!");
		removeCells(rowCells);
		rowCells = new Array();
	}

	private void  synCreateCellInstances (){
		synCellClasses();
		int ih =getRowHeight ();
		int needNum =Math.floor(getExtentSize ().height /ih )+2;

		if(rowCells.length == needNum/* || !displayable*/){
			return;
		}
		int columnCount =getColumnCount ();
		int i ;
		lastColumnCellFactories = new Array();
		for(i=0; i<columnCount; i++){
			lastColumnCellFactories.push(getCellFactory(0, i));
		}

		//create needed
		if(rowCells.length < needNum){
			int addNum =needNum -rowCells.length ;
			//trace("----create need rows-----" + needNum);
			//trace("----current rows-----" + rowCells.length());
			for(i=0; i<addNum; i++){
				Array columnCells =new Array ();
				for(int c =0;c <columnCount ;c ++){
					TableCell cell =TableCellFactory(lastColumnCellFactories.get(c) ).createNewCell(false );
					columnCells.push(cell);
					addCellToContainer(cell);
				}
				rowCells.push(columnCells);
			}
		}else if(rowCells.length > needNum){ //remove mored
			int removeIndex =needNum ;
			Array removed =rowCells.splice(removeIndex ,rowCells.length -1);
			removeCells(removed);
		}
	}

	private void  removeCells (Array removed ){
		for(int i =0;i <removed.length ;i ++){
			Array columnCells =removed.get(i) ;
			for(int c =0;c <columnCells.length ;c ++){
				TableCell cell =TableCell(columnCells.get(c) );
				removeCellFromeContainer(cell);
			}
		}
	}

	private void  addCellToContainer (TableCell cell ){
		Component cellCom =cell.getCellComponent ();
		setCellComponentProperties(cellCom);
		cellPane.append(cellCom);
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

	private void  removeCellFromeContainer (TableCell cell ){
		cellPane.remove(cell.getCellComponent());
	}

	//*****************************************************
	//                  Events Handling
	//*****************************************************


	//*****************************************************
	//           Viewportable Implementation
	//*****************************************************
	 public void  setSize (IntDimension newSize ){
		super.setSize(newSize);
		if(!testingSize){
			setViewPosition(getViewPosition());
		}
	}

	protected void  fireStateChanged (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}

    public int  getVerticalUnitIncrement (){
    	if(verticalUnitIncrement == AUTO_INCREMENT){
    		return getRowHeight();
    	}else{
    		return verticalUnitIncrement;
    	}
    }

    public int  getVerticalBlockIncrement (){
    	if(verticalBlockIncrement == AUTO_INCREMENT){
    		return getRowHeight()*5;
    	}else{
    		return verticalBlockIncrement;
    	}
    }

    public int  getHorizontalUnitIncrement (){
    	if(horizontalUnitIncrement == AUTO_INCREMENT){
    		return 1;
    	}else{
    		return horizontalUnitIncrement;
    	}
    }

    public int  getHorizontalBlockIncrement (){
    	if(horizontalBlockIncrement == AUTO_INCREMENT){
    		return 10;
    	}else{
    		return horizontalBlockIncrement;
    	}
    }

    public void  setVerticalUnitIncrement (int increment ){
    	if(verticalUnitIncrement != increment){
    		verticalUnitIncrement = increment;
			fireStateChanged();
    	}
    }

    public void  setVerticalBlockIncrement (int increment ){
    	if(verticalBlockIncrement != increment){
    		verticalBlockIncrement = increment;
			fireStateChanged();
    	}
    }

    public void  setHorizontalUnitIncrement (int increment ){
    	if(horizontalUnitIncrement != increment){
    		horizontalUnitIncrement = increment;
			fireStateChanged();
    	}
    }

    public void  setHorizontalBlockIncrement (int increment ){
    	if(horizontalBlockIncrement != increment){
    		horizontalBlockIncrement = increment;
			fireStateChanged();
    	}
    }

    private boolean testingSize ;
    public void  setViewportTestSize (IntDimension s ){
    	testingSize = true;
    	setSize(s);
    	testingSize = false;
    }

    public IntDimension  getExtentSize (){
    	IntDimension d =getInsets ().getInsideSize(getSize ());
    	d.height -= getTableHeader().getHeight();
    	return d;
    }

    public IntDimension  getViewSize (){
    	if(getUI() == null){
    		return getInsets().getOutsideSize();
    	}
    	return getTableUI().getViewSize(this);
    }

    public IntPoint  getViewPosition (){
		return new IntPoint(viewPosition.x, viewPosition.y);
    }

    public void  setViewPosition (IntPoint p ,boolean programmatic =true ){
		if(!viewPosition.equals(p)){
			restrictionViewPos(p);
			if(viewPosition.equals(p)){
				return;
			}
			viewPosition.setLocation(p);
			fireStateChanged(programmatic);
			//revalidate();
			valid = false;
			RepaintManager.getInstance().addInvalidRootComponent(this);
			repaint();
		}
    }

	public void  scrollRectToVisible (IntRectangle contentRect ,boolean programmatic =true ){
		setViewPosition(new IntPoint(contentRect.x, contentRect.y), programmatic);
	}

	private IntPoint  restrictionViewPos (IntPoint p ){
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

	/**
	 * Adds a listener to row selection changed.
	 * @param listener the listener to be add.
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.SelectionEvent
	 */
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(SelectionEvent.ROW_SELECTION_CHANGED, listener, false, priority, useWeakReference);
	}

	/**
	 * Removes a listener from row selection changed listeners.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.SelectionEvent
	 */
	public void  removeSelectionListener (Function listener ){
		removeEventListener(SelectionEvent.ROW_SELECTION_CHANGED, listener);
	}

	/**
	 * Adds a listener to column selection changed.
	 * @param listener the listener to be add.
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.SelectionEvent
	 * @see #addSelectionListener()
	 */
	public void  addColumnSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(SelectionEvent.COLUMN_SELECTION_CHANGED, listener, false, priority, useWeakReference);
	}

	/**
	 * Removes a listener from column selection changed listeners.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.SelectionEvent
	 * @see #removeSelectionListener()
	 */
	public void  removeColumnSelectionListener (Function listener ){
		removeEventListener(SelectionEvent.COLUMN_SELECTION_CHANGED, listener);
	}

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
	 * @see org.aswing.event.InteractiveEvent#STATE_CHANGED
	 */
	public void  removeStateListener (Function listener ){
		removeEventListener(InteractiveEvent.STATE_CHANGED, listener);
	}

    public Component  getViewportPane (){
    	return this;
    }

	//******************************************************************
	//------------------------Layout implementation---------------------
	//******************************************************************

	/**
	 * do nothing
	 */
    public void  addLayoutComponent (Component comp ,Object constraints ){
    }
	/**
	 * do nothing
	 */
    public void  removeLayoutComponent (Component comp ){
    }

    public IntDimension  preferredLayoutSize (Container target ){
    	return getViewSize();
    }

    public IntDimension  minimumLayoutSize (Container target ){
    	return getInsets().getOutsideSize();
    }

    public IntDimension  maximumLayoutSize (Container target ){
    	return IntDimension.createBigDimension();
    }

	/**
	 * position and fill cells here
	 */
    public void  layoutContainer (Container target ){
		synCreateCellInstances();
		layoutCells();
    }

	/**
	 * return 0
	 */
    public double  getLayoutAlignmentX (Container target ){
    	return 0;
    }

	/**
	 * return 0
	 */
    public double  getLayoutAlignmentY (Container target ){
    	return 0;
    }

    public void  invalidateLayout (Container target ){
    }
}


