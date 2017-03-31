package org.aswing.ext;

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


import flash.events.*;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.util.ArrayList;


/**
 * Dispatched when the list selection changed.
 * @eventType org.aswing.event.SelectionEvent.LIST_SELECTION_CHANGED
 */
.get(Event(name="listSelectionChanged", type="org.aswing.event.SelectionEvent"))

/**
 * Dispatched when the list item be click.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_CLICK
 */
.get(Event(name="itemClick", type="org.aswing.ext.GridListItemEvent"))

/**
 * Dispatched when the list item be double click.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_DOUBLE_CLICK
 */
.get(Event(name="itemDoubleClick", type="org.aswing.ext.GridListItemEvent"))

/**
 * Dispatched when the list item be mouse down.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_MOUSE_DOWN
 */
.get(Event(name="itemMouseDown", type="org.aswing.ext.GridListItemEvent"))

/**
 * Dispatched when the list item be roll over.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_ROLL_OVER
 */
.get(Event(name="itemRollOver", type="org.aswing.ext.GridListItemEvent"))

/**
 * Dispatched when the list item be roll out.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_ROLL_OUT
 */
.get(Event(name="itemRollOut", type="org.aswing.ext.GridListItemEvent"))

/**
 * Dispatched when the list item be released out side.
 * @eventType org.aswing.ext.GridListItemEvent.ITEM_RELEASE_OUT_SIDE
 */
.get(Event(name="itemReleaseOutSide", type="org.aswing.ext.GridListItemEvent"))

/**
 * GridList usage is similar to JList, GridList provide a grid like container,
 * you can put GridList into a JScrollPane.<br/>
 * GridList doesn't share cell instances, it means it will create cells for every cell value,
 * it's not suitable for large data model.<br/>
 * GridList doesn't support key board navigation/selection yet.
 *
 * @author iiley
 */
public class GridList extends JViewport implements ListDataListener{

	/**
	 * Only can select one most item at a time.
	 */
	public static int SINGLE_SELECTION =DefaultListSelectionModel.SINGLE_SELECTION ;
	/**
	 * Can select any item at a time.
	 */
	public static int MULTIPLE_SELECTION =DefaultListSelectionModel.MULTIPLE_SELECTION ;

	protected GridCellHolder tileHolder ;
	protected GridListLayout gridLayout ;

	protected GridListCellFactory cellFactory ;
	protected ListModel model ;
	protected ListSelectionModel selectionModel ;
	protected ArrayList cells ;
	protected boolean selectable =true ;
	protected boolean autoScroll =true ;
	protected int tileWidth =40;
	protected int tileHeight =20;


	/**
	 * Creates a GridList
	 * @param model the data provider model
	 * @param cellFactory the cell factory, null to be a default factory to generate text cell
	 * @param columns if == 0 it will auto (only one of col or row can be == 0)
	 * @param rows if == 0 it will auto (only one of col or row can be == 0)
	 */
	public  GridList (ListModel model =null ,GridListCellFactory cellFactory =null ,int columns =0,int rows =2){
		super(createHolder(columns, rows));
		setHorizontalAlignment(AsWingConstants.LEFT);
		setVerticalAlignment(AsWingConstants.TOP);
		cells = new ArrayList();
		if(model == null) model = new VectorListModel();
		if(cellFactory == null) cellFactory = new GeneralGridListCellFactory(DefaultGridCell);
		this.cellFactory = cellFactory;
		setSelectionModel(new DefaultListSelectionModel());
		setModel(model);
	}

	/**
	 * Sets whether selectable by user interactive.
	 */
	public void  setSelectable (boolean b ){
		selectable = b;
	}

        public ArrayList  getCells ()
        {
            return this.cells;
        }//end

        public boolean  isSelectable (){
		return selectable;
	}

	public void  setTileWidth (int w ){
		if(tileWidth != w){
			tileWidth = w;
			gridLayout.setTileWidth(w);
			tileHolder.revalidate();
		}
	}

	public void  setTileHeight (int h ){
		if(tileHeight != h){
			tileHeight = h;
			gridLayout.setTileHeight(h);
			tileHolder.revalidate();
		}
	}

	public int  getTileWidth (){
		return tileWidth;
	}

	public int  getTileHeight (){
		return tileHeight;
	}

	/**
	 * Auto scroll to view selection?
	 */
	public void  setAutoScroll (boolean b ){
		autoScroll = b;
	}

	public boolean  isAutoScroll (){
		return autoScroll;
	}

	protected Container  createHolder (int columns ,int rows ){
		tileHolder = new GridCellHolder(this);
		gridLayout = new GridListLayout(rows, columns, 2, 2);
		gridLayout.setTileWidth(getTileWidth());
		gridLayout.setTileHeight(getTileHeight());
		tileHolder.setLayout(gridLayout);
		return tileHolder;
	}

	public void  setHolderLayout (GridListLayout layout ){
		if(layout != gridLayout){
			gridLayout = layout;
			gridLayout.setTileWidth(getTileWidth());
			gridLayout.setTileHeight(getTileHeight());
			tileHolder.setLayout(gridLayout);
		}
	}

	/**
	 * Set the list mode to provide the data to GridList.
	 * @see org.aswing.ListModel
	 */
	public void  setModel (ListModel m ){
		if(m != model){
			if(model != null){
				model.removeListDataListener(this);
			}
			model = m;
			model.addListDataListener(this);
			rebuildListView();
		}
	}

	/**
	 * Set a array to be the list data, a new model will be created and the values is copied to the model.
	 * This is not a good way, its slow. So suggest you to create a ListMode for example VectorListMode to JList,
	 * When you modify ListMode, it will automatic update GridList if necessary.
	 * @see #setModel()
	 * @see org.aswing.ListModel
	 */
	public void  setListData (Array ld ){
		ListModel m =new VectorListModel(ld );
		setModel(m);
	}

	/**
	 * @return the model of this List
	 */
	public ListModel  getModel (){
		return model;
	}

	public void  setSelectionModel (ListSelectionModel m ){
		if(m != selectionModel){
			if(selectionModel != null){
				selectionModel.removeListSelectionListener(__selectionListener);
			}
			selectionModel = m;
			selectionModel.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			selectionModel.addListSelectionListener(__selectionListener);
		}
	}

	public ListSelectionModel  getSelectionModel (){
		return selectionModel;
	}

	/**
	 * Determines whether single-item or multiple-item selections are allowed.
	 * If selection mode changed, will cause clear selection;
	 * @see #SINGLE_SELECTION
	 * @see #MULTIPLE_SELECTION
	 */
	public void  setSelectionMode (int sm ){
		getSelectionModel().setSelectionMode(sm);
	}

	/**
	 * Return whether single-item or multiple-item selections are allowed.
	 * @see #SINGLE_SELECTION
	 * @see #MULTIPLE_SELECTION
	 */
	public int  getSelectionMode (){
		return getSelectionModel().getSelectionMode();
	}

	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(SelectionEvent.LIST_SELECTION_CHANGED, listener, false, priority, useWeakReference);
	}

	/**
	 * Clears the selection - after calling this method isSelectionEmpty will return true.
	 * This is a convenience method that just delegates to the selectionModel.
     * @param programmatic indicate if this is a programmatic change.
	 */
	public void  clearSelection (boolean programmatic =true ){
		selectionModel.clearSelection(programmatic);
	}

	public void  setColsRows (int cols ,int rows ){
		gridLayout.setColumns(cols);
		gridLayout.setRows(rows);
    	tileHolder.revalidate();
    	revalidate();
	}

	public void  setColumns (int cols ){
		gridLayout.setColumns(cols);
    	tileHolder.revalidate();
    	revalidate();
	}

	public void  setRows (int rows ){
		gridLayout.setRows(rows);
    	tileHolder.revalidate();
    	revalidate();
	}

	public int  getColumns (){
		return gridLayout.getColumns();
	}

	public int  getRows (){
		return gridLayout.getRows();
	}

	public void  setHGap (int g ){
		gridLayout.setHgap(g);
    	tileHolder.revalidate();
    	revalidate();
	}

	public void  setVGap (int g ){
		gridLayout.setVgap(g);
    	tileHolder.revalidate();
    	revalidate();
	}

	public int  getHGap (){
		return gridLayout.getHgap();
	}

	public int  getVGap (){
		return gridLayout.getVgap();
	}

    /**
     * Returns the first index argument from the most recent
     * <code>addSelectionModel</code> or <code>setSelectionInterval</code> call.
     * This is a convenience method that just delegates to the
     * <code>selectionModel</code>.
     *
     * @return the index that most recently anchored an interval selection
     * @see ListSelectionModel#getAnchorSelectionIndex
     * @see #addSelectionInterval()
     * @see #setSelectionInterval()
     * @see #addSelectionListener()
     */
    public int  getAnchorSelectionIndex (){
        return getSelectionModel().getAnchorSelectionIndex();
    }

    /**
     * Returns the second index argument from the most recent
     * <code>addSelectionInterval</code> or <code>setSelectionInterval</code>
     * call.
     * This is a convenience method that just  delegates to the
     * <code>selectionModel</code>.
     *
     * @return the index that most recently ended a interval selection
     * @see ListSelectionModel#getLeadSelectionIndex
     * @see #addSelectionInterval()
     * @see #setSelectionInterval()
     * @see #addSelectionListener()
     */
    public int  getLeadSelectionIndex (){
        return getSelectionModel().getLeadSelectionIndex();
    }

    /**
     * @param index0 index0.
     * @param index1 index1.
     * @param programmatic indicate if this is a programmatic change.
     * @see ListSelectionModel#setSelectionInterval
     * @see #removeSelectionInterval()
     */
	public void  setSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		getSelectionModel().setSelectionInterval(index0, index1, programmatic);
	}

    /**
     * @param index0 index0.
     * @param index1 index1.
     * @param programmatic indicate if this is a programmatic change.
     * @see ListSelectionModel#addSelectionInterval()
     * @see #removeSelectionInterval()
     */
	public void  addSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		getSelectionModel().addSelectionInterval(index0, index1, programmatic);
	}

    /**
     * @param index0 index0.
     * @param index1 index1.
     * @param programmatic indicate if this is a programmatic change.
     * @see ListSelectionModel#removeSelectionInterval()
     */
	public void  removeSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
		getSelectionModel().removeSelectionInterval(index0, index1, programmatic);
	}

	/**
	 * Selects all elements in the list.
	 *
     * @param programmatic indicate if this is a programmatic change.
	 * @see #setSelectionInterval
	 */
	public void  selectAll (boolean programmatic =true ){
		setSelectionInterval(0, getModel().getSize()-1, programmatic);
	}

	/**
     * Selects a single cell.
     * @param index the index to be seleted.
     * @param programmatic indicate if this is a programmatic change.
     * @see ListSelectionModel#setSelectionInterval
     * @see #isSelectedIndex()
	 */
	public void  setSelectedIndex (int index ,boolean programmatic =true ){
		if(index >= getModel().getSize()){
			return;
		}
		getSelectionModel().setSelectionInterval(index, index, programmatic);
	}

	/**
	 * Return the selected index, if selection multiple, return the first.
	 * if not selected any, return -1.
	 * @return the selected index
	 */
	public int  getSelectedIndex (){
		return getSelectionModel().getMinSelectionIndex();
	}

	/**
	 * Returns true if nothing is selected.
	 * @return true if nothing is selected, false otherwise.
	 */
	public boolean  isSelectionEmpty (){
		return getSelectionModel().isSelectionEmpty();
	}

	/**
	 * @return true if the index is selected, otherwise false.
	 */
	public boolean  isSelectedIndex (int index ){
		return getSelectionModel().isSelectedIndex(index);
	}

	/**
	 * Selects the specified object from the list. This will not cause a scroll, if you want to
	 * scroll to visible the selected value, call ensureIndexIsVisible().
	 * @param value the value to be selected.
     * @param programmatic indicate if this is a programmatic change.
	 * @see #setSelectedIndex()
	 */
	public void  setSelectedValue (*value ,boolean programmatic =true ){
		int n =model.getSize ();
		for(int i =0;i <n ;i ++){
			if(model.getElementAt(i) == value){
				setSelectedIndex(i, programmatic);
				return;
			}
		}
		setSelectedIndex(-1, programmatic); //there is not this value
	}

	/**
	 * Returns the first selected value, or null if the selection is empty.
	 * @return the first selected value
	 */
	public Object getSelectedValue () {
		if(isSelectionEmpty()){
			return null;
		}else{
			return this.getModel().getElementAt(getSelectedIndex());
		}
	}


	/**
	 * Returns an array of the values for the selected cells.
     * The returned values are sorted in increasing index order.
     * @return the selected values or an empty list if nothing is selected
	 */
	public Array  getSelectedValues (){
		Array values =new Array ();
		ListSelectionModel sm =getSelectionModel ();
		int min =sm.getMinSelectionIndex ();
		int max =sm.getMaxSelectionIndex ();
		if(min < 0 || max < 0 || isSelectionEmpty()){
			return values;
		}
		ListModel vm =getModel ();
		for(int i =min ;i <=max ;i ++){
			if(sm.isSelectedIndex(i)){
				values.push(vm.getElementAt(i));
			}
		}
		return values;
	}

	/**
	 * Returns an array of all of the selected indices in increasing order.
	 * @return a array contains all selected indices
	 */
	public Array  getSelectedIndices (){
		Array indices =new Array ();
		ListSelectionModel sm =getSelectionModel ();
		int min =sm.getMinSelectionIndex ();
		int max =sm.getMaxSelectionIndex ();
		if(min < 0 || max < 0 || isSelectionEmpty()){
			return indices;
		}
		for(int i =min ;i <=max ;i ++){
			if(sm.isSelectedIndex(i)){
				indices.push(i);
			}
		}
		return indices;
	}

	/**
	 * Selects a set of cells.
	 * <p> This will not cause a scroll, if you want to
	 * scroll to visible the selected value, call ensureIndexIsVisible().
	 * @param indices an array of the indices of the cells to select.
     * @param programmatic indicate if this is a programmatic change.
     * @see #isSelectedIndex()
     * @see #addSelectionListener()
	 * @see #ensureIndexIsVisible()
	 */
	public void  setSelectedIndices (Array indices ,boolean programmatic =true ){
        ListSelectionModel sm =getSelectionModel ();
        sm.clearSelection();
		int size =getModel ().getSize ();
        for(int i =0;i <indices.length ;i ++){
	    	if (indices.get(i) < size) {
				sm.addSelectionInterval(indices.get(i), indices.get(i), programmatic);
	    	}
        }
	}

	/**
	 * Selects a set of cells.
	 * <p> This will not cause a scroll, if you want to
	 * scroll to visible the selected value, call ensureIndexIsVisible().
	 * @param values an array of the values to select.
     * @param programmatic indicate if this is a programmatic change.
     * @see #isSelectedIndex()
     * @see #addSelectionListener()
	 * @see #ensureIndexIsVisible()
	 */
	public void  setSelectedValues (Array values ,boolean programmatic =true ){
        ListSelectionModel sm =getSelectionModel ();
        sm.clearSelection();
		int size =getModel ().getSize ();
        for(int i =0;i <values.length ;i ++){
        	for(int j =0;j <size ;j ++){
        		if(values.get(i) == getModel().getElementAt(j)){
					sm.addSelectionInterval(j, j, programmatic);
					break;
        		}
        	}
        }
	}

	/**
	 * Returns the cell by index.
	 */
	public GridListCell  getCellByIndex (int index ){
		return cells.elementAt(index);
	}

	public void  scrollToView (Object value){
		int n =model.getSize ();
		for(int i =0;i <n ;i ++){
			if(model.getElementAt(i) == value){
				scrollToViewIndex(i);
				return;
			}
		}
	}

	public void  scrollToViewIndex (int index ){
		if(index >=0 && index<model.getSize()){
			Component c =tileHolder.getComponent(index );
			IntPoint p =c.getLocation ();
			IntPoint vp =getViewPosition ();
			IntDimension range =getExtentSize ();
			if(p.x + gridLayout.getTileWidth() > vp.x + range.width){
				vp.x = p.x + gridLayout.getTileWidth() - range.width;
			}
			if(p.y + gridLayout.getTileHeight() > vp.y + range.height){
				vp.y = p.y + gridLayout.getTileHeight() - range.height;
			}
			if(p.x < vp.x){
				vp.x = p.x;
			}
			if(p.y < vp.y){
				vp.y = p.y;
			}
			setViewPosition(vp);
		}
	}

	 public int  getVerticalUnitIncrement (){
		return gridLayout.getTileHeight() + gridLayout.getVgap();
	}

	 public int  getVerticalBlockIncrement (){
		return getInsets().getInsideSize(getSize()).height - gridLayout.getVgap();
	}

	 public int  getHorizontalUnitIncrement (){
		return gridLayout.getTileWidth() + gridLayout.getHgap();
	}

	 public int  getHorizontalBlockIncrement (){
		return getInsets().getInsideSize(getSize()).width - gridLayout.getHgap();
	}

    private void  rebuildListView (){
    	clearSelection();
    	GridListCell cell ;
    	for(int i =0;i <cells.size ();i ++){
    		cell = cells.get(i);
    		removeHandlersFromCell(cell.getCellComponent());
    	}
    	tileHolder.removeAll();
    	cells.clear();
    	ListModel model =getModel ();
    	for(i=0; i<model.getSize(); i++){
			cell = createNewCell();
			cells.append(cell, i);
			cell.setCellValue(model.getElementAt(i));
			cell.setGridListCellStatus(this, false, i);
			addCellToContainer(cell, i);
    	}
    	tileHolder.revalidate();
    	revalidate();
    }

    private void  __selectionListener (SelectionEvent e ){
    	int n =cells.size ();
    	for(int i =0;i <n ;i ++){
    		GridListCell cell =cells.elementAt(i );
    		cell.setGridListCellStatus(this, getSelectionModel().isSelectedIndex(i), i);
    	}
    	dispatchEvent(new SelectionEvent(SelectionEvent.LIST_SELECTION_CHANGED, e.getFirstIndex(), e.getLastIndex(), e.isProgrammatic()));

    	if(!(getSelectionModel().isSelectionEmpty()) && isAutoScroll()){
    		scrollToViewIndex(e.getLastIndex());
    	}
    }

     public IntDimension  getViewSize (){
		if(getView() == null){
			return new IntDimension();
		}else{
			if(isTracksWidth() && isTracksHeight()){
				return getExtentSize();
			}else{
				return gridLayout.getViewSize(tileHolder);
			}
		}
    }

	//------------------------ListMode Listener Methods-----------------

	protected GridListCell  createNewCell (){
		return cellFactory.createNewGridListCell();
	}

	protected void  addCellToContainer (GridListCell cell ,int index ){
		tileHolder.insert(index, cell.getCellComponent());
		addHandlersToCell(cell.getCellComponent());
	}

	protected void  removeCellFromeContainer (GridListCell cell ){
		tileHolder.remove(cell.getCellComponent());
		removeHandlersFromCell(cell.getCellComponent());
	}

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  intervalAdded (ListDataEvent e ){
		ListModel m =getModel ();

		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());

		for(int i =i0 ;i <=i1 ;i ++){
			GridListCell cell =createNewCell ();
			cells.append(cell, i);
			cell.setCellValue(m.getElementAt(i));
			cell.setGridListCellStatus(this, false, i);
			addCellToContainer(cell, i);
		}

		selectionModel.insertIndexInterval(i0, i1-i0+1, true);
    	tileHolder.revalidate();
    	revalidate();
    }

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  intervalRemoved (ListDataEvent e ){
		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());

		int i ;
		GridListCell cell ;

		for(i=i0; i<=i1; i++){
			cell = GridListCell(cells.get(i));
			removeCellFromeContainer(cell);
		}
		cells.removeRange(i0, i1);
		selectionModel.removeIndexInterval(i0, i1);
    	tileHolder.revalidate();
    	revalidate();
    }

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  contentsChanged (ListDataEvent e ){
		ListModel m =getModel ();

		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());

		for(int i =i0 ;i <=i1 ;i ++){
			GridListCell cell =cells.get(i );
			cell.setCellValue(m.getElementAt(i));
		}
    	//tileHolder.revalidate();
    }

    //-------------------------------Event Listener For All Items----------------

    protected void  addHandlersToCell (Component cellCom ){
    	cellCom.addEventListener(MouseEvent.CLICK, __onItemClick);
    	cellCom.addEventListener(MouseEvent.DOUBLE_CLICK, __onItemDoubleClick);
    	cellCom.addEventListener(MouseEvent.MOUSE_DOWN, __onItemMouseDown);
    	cellCom.addEventListener(MouseEvent.ROLL_OVER, __onItemRollOver);
    	cellCom.addEventListener(MouseEvent.ROLL_OUT, __onItemRollOut);
    	cellCom.addEventListener(ReleaseEvent.RELEASE_OUT_SIDE, __onItemReleaseOutSide);
    }

    protected void  removeHandlersFromCell (Component cellCom ){
    	cellCom.removeEventListener(MouseEvent.CLICK, __onItemClick);
    	cellCom.removeEventListener(MouseEvent.DOUBLE_CLICK, __onItemDoubleClick);
    	cellCom.removeEventListener(MouseEvent.MOUSE_DOWN, __onItemMouseDown);
    	cellCom.removeEventListener(MouseEvent.ROLL_OVER, __onItemRollOver);
    	cellCom.removeEventListener(MouseEvent.ROLL_OUT, __onItemRollOut);
    	cellCom.removeEventListener(ReleaseEvent.RELEASE_OUT_SIDE, __onItemReleaseOutSide);
    }

    private double pressedIndex ;
    private boolean pressedCtrl ;
    private boolean pressedShift ;
    private boolean doSelectionWhenRelease ;

    private void  __onItemMouseDownSelection (GridListItemEvent e ){
		int index =cells.indexOf(e.getCell ());
		pressedIndex = index;
		pressedCtrl = e.ctrlKey;
		pressedShift = e.shiftKey;
		doSelectionWhenRelease = false;

		if(getSelectionMode() == MULTIPLE_SELECTION){
			if(getSelectionModel().isSelectedIndex(index)){
				doSelectionWhenRelease = true;
			}else{
				doSelection();
			}
		}else{
			getSelectionModel().setSelectionInterval(index, index, false);
		}
    }

    private void  doSelection (){
    	int index =pressedIndex ;
		if(pressedShift){
			int archor =getSelectionModel ().getAnchorSelectionIndex ();
			if(archor < 0){
				archor = index;
			}
			getSelectionModel().setSelectionInterval(archor, index, false);
		}else if(pressedCtrl){
			if(!isSelectedIndex(index)){
				getSelectionModel().addSelectionInterval(index, index, false);
			}else{
				getSelectionModel().removeSelectionInterval(index, index, false);
			}
		}else{
			getSelectionModel().setSelectionInterval(index, index, false);
		}
    }

    private void  __onItemClickSelection (GridListItemEvent e ){
    	if(doSelectionWhenRelease){
    		doSelection();
    		doSelectionWhenRelease = false;
    	}
    }

	protected GridListItemEvent  createItemEventObj (*cellCom ,String type ,MouseEvent me ){
		int index =tileHolder.getIndex(cellCom );
		GridListCell cell =cells.get(index );
		GridListItemEvent event =new GridListItemEvent(type ,getModel ().getElementAt(index ),index ,cell ,me );
		return event;
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemMouseDown (MouseEvent e ){
		GridListItemEvent event =createItemEventObj(e.currentTarget ,GridListItemEvent.ITEM_MOUSE_DOWN ,e );
		if(isSelectable()){
			__onItemMouseDownSelection(event);
		}
		dispatchEvent(event);
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemClick (MouseEvent e ){
		GridListItemEvent event =createItemEventObj(e.currentTarget ,GridListItemEvent.ITEM_CLICK ,e );
		if(isSelectable()){
			__onItemClickSelection(event);
		}
		dispatchEvent(event);
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemReleaseOutSide (ReleaseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, GridListItemEvent.ITEM_RELEASE_OUT_SIDE, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemRollOver (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, GridListItemEvent.ITEM_ROLL_OVER, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemRollOut (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, GridListItemEvent.ITEM_ROLL_OUT, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemDoubleClick (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, GridListItemEvent.ITEM_DOUBLE_CLICK, e));
	}
}


