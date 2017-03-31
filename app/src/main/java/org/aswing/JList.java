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


import flash.events.MouseEvent;

import org.aswing.dnd.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.basic.BasicListUI;
import org.aswing.util.*;


/**
 * Dispatched when the list selection changed.
 * @eventType org.aswing.event.SelectionEvent.LIST_SELECTION_CHANGED
 */
.get(Event(name="listSelectionChanged", type="org.aswing.event.SelectionEvent"))

/**
 * Dispatched when the list item be click.
 * @eventType org.aswing.event.ListItemEvent.ITEM_CLICK
 */
.get(Event(name="itemClick", type="org.aswing.event.ListItemEvent"))

/**
 * Dispatched when the list item be double click.
 * @eventType org.aswing.event.ListItemEvent.ITEM_DOUBLE_CLICK
 */
.get(Event(name="itemDoubleClick", type="org.aswing.event.ListItemEvent"))

/**
 * Dispatched when the list item be mouse down.
 * @eventType org.aswing.event.ListItemEvent.ITEM_MOUSE_DOWN
 */
.get(Event(name="itemMouseDown", type="org.aswing.event.ListItemEvent"))

/**
 * Dispatched when the list item be roll over.
 * @eventType org.aswing.event.ListItemEvent.ITEM_ROLL_OVER
 */
.get(Event(name="itemRollOver", type="org.aswing.event.ListItemEvent"))

/**
 * Dispatched when the list item be roll out.
 * @eventType org.aswing.event.ListItemEvent.ITEM_ROLL_OUT
 */
.get(Event(name="itemRollOut", type="org.aswing.event.ListItemEvent"))

/**
 * Dispatched when the list item be released out side.
 * @eventType org.aswing.event.ListItemEvent.ITEM_RELEASE_OUT_SIDE
 */
.get(Event(name="itemReleaseOutSide", type="org.aswing.event.ListItemEvent"))

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
 * A component that allows the user to select one or more objects from a
 * list.  A separate model, <code>ListModel</code>, represents the contents
 * of the list.  It's easy to display an array objects, using
 * a <code>JList</code> constructor that builds a <code>ListModel</code>
 * instance for you:
 * <pre>
 * // Create a JList that displays the strings in data[]
 *
 *Array data =.get( "one","two","three","four") ;
 *JList dataList =new JList(data );
 *
 * // The value of the JList model property is an object that provides
 * // a read-only view of the data.  It was constructed automatically.
 *
 * for(int i = 0; i < dataList.getModel().getSize(); i++) {
 *     System.out.println(dataList.getModel().getElementAt(i));
 * }
 *
 * // Create a JList that displays the values in a IVector--<code>VectorListModel</code>.
 *
 *VectorListModel vec =new VectorListModel(.get( "one","two","three","four") );
 *JList vecList =new JList(vec );
 *
 * //When you add elements to the vector, the JList will be automatically updated.
 * vec.append("five");
 * </pre>
 * <p>
 * <code>JList</code> doesn't support scrolling directly.
 * To create a scrolling
 * list you make the <code>JList</code> the viewport of a
 * <code>JScrollPane</code>.  For example:
 * <pre>
 * JScrollPane scrollPane = new JScrollPane(dataList);
 * // Or in two steps:
 * JScrollPane scrollPane = new JScrollPane();
 * scrollPane.setView(dataList);
 * </pre>
 * <p>
 * By default the <code>JList</code> selection model is
 * <code>SINGLE_SELECTION</code>.
 * <pre>
 * String[] data = {"one", "two", "three", "four"};
 * JList dataList = new JList(data);
 *
 * dataList.setSelectedIndex(1);  // select "two"
 * dataList.getSelectedValue();   // returns "two"
 * </pre>
 * <p>
 * The contents of a <code>JList</code> can be dynamic,
 * in other words, the list elements can
 * change value and the size of the list can change after the
 * <code>JList</code> has
 * been created.  The <code>JList</code> observes changes in its model with a
 * <code>ListDataListener</code> implementation.  A correct
 * implementation of <code>ListModel</code> notifies
 * it's listeners each time a change occurs.  The changes are
 * characterized by a <code>ListDataEvent</code>, which identifies
 * the range of list indices that have been modified, added, or removed.
 * Simple dynamic-content <code>JList</code> applications can use the
 * <code>VectorListModel</code> class to store list elements.  This class
 * implements the <code>ListModel</code> and <code>IVector</code> interfaces
 * and provides the Vector API.  Applications that need to
 * provide custom <code>ListModel</code> implementations can subclass
 * <code>AbstractListModel</code>, which provides basic
 * <code>ListDataListener</code> support.
 * <p>
 * <code>JList</code> uses a <code>Component</code> provision, provided by
 * a delegate called the
 * <code>ListCell</code>, to paint the visible cells in the list.
 * <p>
 * <code>ListCell</code> created by a <code>ListCellFactory</code>, to custom
 * the item representation of the list, you need a custom <code>ListCellFactory</code>.
 * For example a IconListCellFactory create IconListCells.
 * <p>
 * <code>ListCellFactory</code> is related to the List's performace too, see the doc
 * comments of <code>ListCellFactory</code> for the details.
 * And if you want a horizontal scrollvar visible when item width is bigger than the visible
 * width, you need a not <code>shareCells</code> Factory(and of course the List should located
 * in a JScrollPane first). <code>shareCells</code> Factory
 * can not count the maximum width of list items.
 * @author iiley
 * @see ListCellFactory
 * @see ListCell
 * @see ListModel
 * @see VectorListModel
 */
public class JList extends Container implements LayoutManager, Viewportable, ListDataListener{

 	/**
 	 * The default unit/block increment, it means auto count a value.
 	 */
 	public static  int AUTO_INCREMENT =int.MIN_VALUE ;

	/**
	 * Only can select one most item at a time.
	 */
	public static int SINGLE_SELECTION =DefaultListSelectionModel.SINGLE_SELECTION ;
	/**
	 * Can select any item at a time.
	 */
	public static int MULTIPLE_SELECTION =DefaultListSelectionModel.MULTIPLE_SELECTION ;

	/**
	 * Drag and drop disabled.
	 */
	public static int DND_NONE =DragManager.TYPE_NONE ;

	/**
	 * Drag and drop enabled, and the action of items is move.
	 */
	public static int DND_MOVE =DragManager.TYPE_MOVE ;

	/**
	 * Drag and drop enabled, and the action of items is copy.
	 */
	public static int DND_COPY =DragManager.TYPE_COPY ;

	//---------------------caches------------------
	private int viewHeight ;
	private int viewWidth ;
	private ListCell maxWidthCell ;
	private HashMap cellPrefferSizes ;//use for catche sizes when not all cells same height
	private HashMap comToCellMap ;
	private int visibleRowCount ;
	private int visibleCellWidth ;
	//--

	private int preferredWidthWhenNoCount ;

	private boolean tracksWidth ;
	private int verticalUnitIncrement ;
	private int verticalBlockIncrement ;
	private int horizontalUnitIncrement ;
	private int horizontalBlockIncrement ;

	private IntPoint viewPosition ;
	private ASColor selectionForeground ;
	private ASColor selectionBackground ;

	protected CellPane cellPane ;
	private ListCellFactory cellFactory ;
	private ListModel model ;
	private ListSelectionModel selectionModel ;
	private ArrayList cells ;

	private int firstVisibleIndex ;
	private int lastVisibleIndex ;
	private 	int firstVisibleIndexOffset =0;
	private 	int lastVisibleIndexOffset =0;

	private int autoDragAndDropType ;

	/**
	 * Create a list.
	 * @param listData (optional)a ListModel or a Array.
	 * @param cellFactory (optional)the cellFactory for this List.
	 */
	( = JListlistDatanull,ListCellFactorycellFactory=null){
		super();

		setName("JList");
		layout = this;
		cellPane = new CellPane();
		append(cellPane);
		viewPosition = new IntPoint(0, 0);
		setSelectionModel(new DefaultListSelectionModel());
		firstVisibleIndex = 0;
		lastVisibleIndex = -1;
		firstVisibleIndexOffset = 0;
		lastVisibleIndexOffset = 0;
		visibleRowCount = -1;
		visibleCellWidth = -1;
		preferredWidthWhenNoCount = 20; //Default 20

		verticalUnitIncrement = AUTO_INCREMENT;
		verticalBlockIncrement = AUTO_INCREMENT;
		horizontalUnitIncrement = AUTO_INCREMENT;
		horizontalBlockIncrement = AUTO_INCREMENT;

		tracksWidth = false;
		viewWidth = 0;
		viewHeight = 0;
		maxWidthCell = null;
		cellPrefferSizes = new HashMap();
		comToCellMap = new HashMap();
		cells = new ArrayList();
		model = null;
		autoDragAndDropType = DND_NONE;

		if(cellFactory == null){
			cellFactory = new DefaultListCellFactory(true);
		}
		this.cellFactory = cellFactory;

		if(listData == null){
			setModel(new VectorListModel());
		}else if(listData is ListModel){
			setModel((ListModel)listData);
		}else{
			setListData((Array)listData);
		}

		updateUI();
	}

     public void  updateUI (){
    	//update cells ui
    	for(int i =cells.size ()-1;i >=0;i --){
    		ListCell cell =ListCell(cells.get(i ));
    		cell.getCellComponent().updateUI();
    	}

    	setUI(UIManager.getUI(this));
    }

     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicListUI;
    }

	 public String  getUIClassID (){
		return "ListUI";
	}


	/**
	 * Can not set layout to JList, its layout is itself.
	 * @throws ArgumentError when set any layout.
	 */
	 public void  setLayout (LayoutManager layout ){
		throw new ArgumentError("Can not set layout to JList, its layout is itself!");
	}

	/**
	 * Set a array to be the list data, a new model will be created and the values is copied to the model.
	 * This is not a good way, its slow. So suggest you to create a ListMode for example VectorListMode to JList,
	 * When you modify ListMode, it will automatic update JList if necessary.
	 * @see #setModel()
	 * @see org.aswing.ListModel
	 */
	public void  setListData (Array ld ){
		ListModel m =new VectorListModel(ld );
		setModel(m);
	}

	/**
	 * Set the list mode to provide the data to JList.
	 * @see org.aswing.ListModel
	 */
	public void  setModel (ListModel m ){
		if(m != model){
			if(model != null){
				model.removeListDataListener(this);
			}
			model = m;
			model.addListDataListener(this);
			updateListView();
		}
	}

	/**
	 * @return the model of this List
	 */
	public ListModel  getModel (){
		return model;
	}

    /**
     * Sets the <code>selectionModel</code> for the list to a
     * non-<code>null</code> <code>ListSelectionModel</code>
     * implementation. The selection model handles the task of making single
     * selections, multiple selections.
     * <p>
     * @param selectionModel  the <code>ListSelectionModel</code> that
     *				implements the selections, if it is null, nothing will be done.
     * @see #getSelectionModel()
     */
	public void  setSelectionModel (ListSelectionModel m ){
		if(m != selectionModel){
			if(selectionModel != null){
				selectionModel.removeListSelectionListener(__selectionListener);
			}
			selectionModel = m;
			selectionModel.addListSelectionListener(__selectionListener);
		}
	}

    /**
     * Returns the value of the current selection model. The selection
     * model handles the task of making single selections, multiple selections.
     *
     * @return the <code>ListSelectionModel</code> that implements
     *					list selections
     * @see #setSelectionModel()
     * @see ListSelectionModel
     */
	public ListSelectionModel  getSelectionModel (){
		return selectionModel;
	}

	/**
	 * @return the cellFactory of this List
	 */
	public ListCellFactory  getCellFactory (){
		return cellFactory;
	}

	/**
	 * This will cause all cells recreating by new factory.
	 * @param newFactory the new cell factory for this List
	 */
	public void  setCellFactory (ListCellFactory newFactory ){
		cellFactory = newFactory;
		removeAllCells();
		updateListView();
	}

	/**
	 * Adds a listener to list selection changed.
	 * @param listener the listener to be add.
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.SelectionEvent
	 */
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(SelectionEvent.LIST_SELECTION_CHANGED, listener, false, priority, useWeakReference);
	}

	/**
	 * Removes a listener from list selection changed listeners.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.SelectionEvent
	 */
	public void  removeSelectionListener (Function listener ){
		removeEventListener(SelectionEvent.LIST_SELECTION_CHANGED, listener);
	}

	/**
	 * @see #setPreferredWidthWhenNoCount()
	 * @return the default preferred with of the List when <code>shareCelles</code>.
	 */
	public int  getPreferredCellWidthWhenNoCount (){
		return preferredWidthWhenNoCount;
	}

	/**
	 * The preferred with of the List, it is only used when List have no counting for its prefferredWidth.
	 * <p>
	 * When <code>ListCellFactory</code> is <code>shareCelles</code>, List will not count prefferred width.
	 * @param preferredWidthWhenNoCount the preferred with of the List.
	 */
	public void  setPreferredCellWidthWhenNoCount (int preferredWidthWhenNoCount ){
		this.preferredWidthWhenNoCount = preferredWidthWhenNoCount;
	}

	/**
	 * When your list data changed, and you want to update list view by hand.
	 * call this method.
	 * <p>This method is called automatically when setModel called with a different model to set.
	 */
	public void  updateListView (){
		createCells();
		validateCells();
	}

	/**
	 * Clears the selection - after calling this method isSelectionEmpty will return true.
	 * This is a convenience method that just delegates to the selectionModel.
     * @param programmatic indicate if this is a programmatic change.
	 */
	public void  clearSelection (boolean programmatic =true ){
		getSelectionModel().clearSelection(programmatic);
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
		if (!selectionForeground.equals(old)){
			repaint();
			validateCells();
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
		if (!selectionBackground.equals(old)){
			repaint();
			validateCells();
		}
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
	 * @return true if the index is selected, otherwise false.
	 */
	public boolean  isSelectedIndex (int index ){
		return getSelectionModel().isSelectedIndex(index);
	}

	/**
	 * Returns the first selected value, or null if the selection is empty.
	 * @return the first selected value
	 */
	public Object getSelectedValue () {
		int i =getSelectedIndex ();
		if(i < 0){
			return null;
		}else{
			return model.getElementAt(i);
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
     * Selects a single cell.
     * @param index the index to be seleted.
     * @param programmatic indicate if this is a programmatic change.
     * @see ListSelectionModel#setSelectionInterval
     * @see #isSelectedIndex()
     * @see #addSelectionListener()
	 * @see #ensureIndexIsVisible()
	 */
	public void  setSelectedIndex (int index ,boolean programmatic =true ){
		if(index >= getModel().getSize()){
			return;
		}
		getSelectionModel().setSelectionInterval(index, index, programmatic);
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
	 * Selects the specified object from the list. This will not cause a scroll, if you want to
	 * scroll to visible the selected value, call ensureIndexIsVisible().
	 * @param value the value to be selected.
     * @param programmatic indicate if this is a programmatic change.
	 * @see #setSelectedIndex()
	 * @see #ensureIndexIsVisible()
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
	 * Scrolls the JList to make the specified cell completely visible.
	 * @see #setFirstVisibleIndex()
	 */
	public void  ensureIndexIsVisible (int index ){
		if(index<=getFirstVisibleIndex()){
			setFirstVisibleIndex(index);
		}else if(index>=getLastVisibleIndex()){
			setLastVisibleIndex(index);
		}
	}

	public int  getFirstVisibleIndex (){
		return firstVisibleIndex;
	}

	/**
	 * scroll the list to view the specified index as first visible.
	 * If the list data elements is too short can not move the specified
	 * index to be first, just scroll as top as can.
	 * @see #ensureIndexIsVisible()
	 * @see #setLastVisibleIndex()
	 */
	public void  setFirstVisibleIndex (int index ){
    	ListCellFactory factory =getCellFactory ();
		IntPoint p =getViewPosition ();
		if(factory.isAllCellHasSameHeight() || factory.isShareCells()){
			p.y = index * factory.getCellHeight();
		}else{
			int num =Math.min(cells.getSize ()-1,index );
			int y =0;
			for(int i =0;i <num ;i ++){
				ListCell cell =ListCell(cells.get(i ));
				IntDimension s =getCachedCellPreferSize(cell );
				if(s == null){
					s = cell.getCellComponent().getPreferredSize();
					trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
				}
				y += s.height;
			}
			p.y = y;
		}
		p.y = Math.max(0, Math.min(getViewMaxPos().y, p.y));
		setViewPosition(p);
	}

	public int  getLastVisibleIndex (){
		return lastVisibleIndex;
	}

	/**
	 * scroll the list to view the specified index as last visible
	 * If the list data elements is too short can not move the specified
	 * index to be last, just scroll as bottom as can.
	 * @see ensureIndexIsVisible()
	 * @see setFirstVisibleIndex()
	 */
	public void  setLastVisibleIndex (int index ){
    	ListCellFactory factory =getCellFactory ();
		IntPoint p =getViewPosition ();
		if(factory.isAllCellHasSameHeight() || factory.isShareCells()){
			p.y = (index + 1) * factory.getCellHeight() - getExtentSize().height;
		}else{
			int num =Math.min(cells.getSize (),index +2);
			int y =0;
			for(int i =0;i <num ;i ++){
				ListCell cell =ListCell(cells.get(i ));
				IntDimension s =getCachedCellPreferSize(cell );
				if(s == null){
					s = cell.getCellComponent().getPreferredSize();
					trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
				}
				y += s.height;
			}
			p.y = y - getExtentSize().height;
		}
		p.y = Math.max(0, Math.min(getViewMaxPos().y, p.y));
		setViewPosition(p);
	}

    /**
     * Returns the prefferred number of visible rows.
     *
     * @return an integer indicating the preferred number of rows to display
     *         without using a scroll bar, -1 means perffered number is <code>model.getSize()</code>
     * @see #setVisibleRowCount()
     */
	public int  getVisibleRowCount (){
		return visibleRowCount;
	}

    /**
     * Sets the preferred number of rows in the list that can be displayed.
     * -1 means prefer to display all rows.
     * <p>
     * The default value of this property is -1.
     * The rowHeight will be counted as 20 if the cell factory produces not same height cells.
     * <p>
     *
     * @param visibleRowCount  an integer specifying the preferred number of
     *                         visible rows
     * @see #setVisibleCellWidth()
     * @see #getVisibleRowCount()
     */
	public void  setVisibleRowCount (int c ){
		if(c != visibleRowCount){
			visibleRowCount = c;
			revalidate();
		}
	}

    /**
     * Returns the preferred width of visible list pane. -1 means return the view width.
     *
     * @return an integer indicating the preferred width to display.
     * @see #setVisibleCellWidth()
     */
	public int  getVisibleCellWidth (){
		return visibleCellWidth;
	}

    /**
     * Sets the preferred width the list that can be displayed.
     * <p>
     * The default value of this property is -1.
     * -1 means the width that can display all content.
     * <p>
     *
     * @param visibleRowCount  an integer specifying the preferred width.
     * @see #setVisibleRowCount()
     * @see #getVisibleCellWidth()
     * @see #setPreferredCellWidthWhenNoCount()
     */
	public void  setVisibleCellWidth (int w ){
		if(w != visibleCellWidth){
			visibleCellWidth = w;
			revalidate();
		}
	}

	/**
	 * Sets true to make the cell always have same width with the List container,
	 * and the herizontal scrollbar will not shown if the list is in a <code>JScrollPane</code>;
	 * false to make it as same as its preffered width.
	 * @param b tracks width, default value is false
	 */
	public void  setTracksWidth (boolean b ){
		if(b != tracksWidth){
			tracksWidth = b;
		}
	}

	/**
	 * Returns tracks width value.
	 * @return tracks width
	 * @see #setTracksWidth()
	 */
	public boolean  isTracksWidth (){
		return tracksWidth;
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

	/**
     * Enables the list so that items can be selected.
     */
	 public void  setEnabled (boolean b ){
		super.setEnabled(b);
		mouseChildren = b;
	}

	/**
	 * Sets auto drag and drop type.
	 * @see #DND_NONE
	 * @see #DND_MOVE
	 * @see #DND_COPY
	 */
	/*public void  setAutoDragAndDropType (int type ){
		autoDragAndDropType = type;
		if(dndListener == null){
			dndListener = new Object();
			dndListener.put(ON_DRAG_RECOGNIZED,  Delegate.create(this, ____onDragRecognized));
			dndListener.put(ON_DRAG_ENTER,  Delegate.create(this, ____onDragEnter));
			dndListener.put(ON_DRAG_OVERRING,  Delegate.create(this, ____onDragOverring));
			dndListener.put(ON_DRAG_EXIT,  Delegate.create(this, ____onDragExit));
			dndListener.put(ON_DRAG_DROP,  Delegate.create(this, ____onDragDrop));
		}
		removeEventListener(dndListener);
		if(isAutoDragAndDropAllown()){
			setDropTrigger(true);
			setDragEnabled(true);
			addEventListener(dndListener);
		}else{
			setDropTrigger(false);
			setDragEnabled(false);
		}
	}*/

	/**
	 * Returns the auto drag and drop type.
	 * @see #DND_NONE
	 * @see #DND_MOVE
	 * @see #DND_COPY
	 */
	public int  getAutoDragAndDropType (){
		return autoDragAndDropType;
	}

	private boolean  isAutoDragAndDropAllown (){
		return autoDragAndDropType == DND_MOVE || autoDragAndDropType == DND_COPY;
	}

	/**
	 * Returns is this list allown to automatically be as an drag and drop initiator.
	 * @see #org.aswing.MutableListModel
	 * @see #DND_NONE
	 * @see #DND_MOVE
	 * @see #DND_COPY
	 */
	public boolean  isAutoDnDInitiatorAllown (){
		if(!isAutoDragAndDropAllown()){
			return false;
		}
		if(!isMutableModel()){
			return autoDragAndDropType == DND_COPY;
		}else{
			return true;
		}
	}

	/**
	 * Returns is this list allown to automatically be as an drag and drop target.
	 * @see #org.aswing.MutableListModel
	 * @see #DND_NONE
	 * @see #DND_MOVE
	 * @see #DND_COPY
	 */
	public boolean  isAutoDnDDropTargetAllown (){
		return isAutoDragAndDropAllown() && isMutableModel();
	}

	//----------------------privates-------------------------

	protected void  addCellToContainer (ListCell cell ){
		cell.getCellComponent().setFocusable(false);
		cellPane.append(cell.getCellComponent());
		comToCellMap.put(cell.getCellComponent(), cell);
		addHandlersToCell(cell.getCellComponent());
	}

	protected void  removeCellFromeContainer (ListCell cell ){
		cell.getCellComponent().removeFromContainer();
		comToCellMap.remove(cell.getCellComponent());
		removeHandlersFromCell(cell.getCellComponent());
	}

	private void  checkCreateCellsWhenShareCells (){
		createCellsWhenShareCells();
	}

	private void  createCellsWhenShareCells (){
		int ih =getCellFactory ().getCellHeight ();
		int needNum =Math.floor(getExtentSize ().height /ih )+2;

		viewWidth = getPreferredCellWidthWhenNoCount();
		viewHeight = getModel().getSize()*ih;

		if(cells.getSize() == needNum/* || !displayable*/){
			return;
		}

		int i ;
		ListCell cell ;
		//create needed
		if(cells.getSize() < needNum){
			int addNum =needNum -cells.getSize ();
			for(i=0; i<addNum; i++){
				cell = createNewCell();
				addCellToContainer(cell);
				cells.append(cell);
			}
		}else if(cells.getSize() > needNum){ //remove mored
			int removeIndex =needNum ;
			Array removed =cells.removeRange(removeIndex ,cells.getSize ()-1);
			for(i=0; i<removed.length; i++){
				cell = ListCell(removed.get(i));
				removeCellFromeContainer(cell);
			}
		}
	}

	private void  createCellsWhenNotShareCells (){
		ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();

		int w =0;
		int h =0;
		boolean sameHeight =factory.isAllCellHasSameHeight ();

		int mSize =m.getSize ();
		int cSize =cells.getSize ();

		cellPrefferSizes.clear();

		int n =Math.min(mSize ,cSize );
		int i ;
		ListCell cell ;
		IntDimension s ;
		//reuse created cells
		for(i=0; i<n; i++){
			cell = ListCell(cells.get(i));
			cell.setCellValue(m.getElementAt(i));
			s = cell.getCellComponent().getPreferredSize();
			cellPrefferSizes.put(cell.getCellComponent(), s);
			if(s.width > w){
				w = s.width;
				maxWidthCell = cell;
			}
			if(!sameHeight){
				h += s.height;
			}
		}

		//create lest needed cells
		if(mSize > cSize){
			for(i = cSize; i<mSize; i++){
				cell = createNewCell();
				cells.append(cell);
				cell.setCellValue(m.getElementAt(i));
				addCellToContainer(cell);
				s = cell.getCellComponent().getPreferredSize();
				cellPrefferSizes.put(cell.getCellComponent(), s);
				if(s.width > w){
					w = s.width;
					maxWidthCell = cell;
				}
				if(!sameHeight){
					h += s.height;
				}
			}
		}else if(mSize < cSize){ //remove unwanted cells
			Array removed =cells.removeRange(mSize ,cSize -1);
			for(i=0; i<removed.length; i++){
				cell = ListCell(removed.get(i));
				removeCellFromeContainer(cell);
				cellPrefferSizes.remove(cell.getCellComponent());
			}
		}

		if(sameHeight){
			h = m.getSize()*factory.getCellHeight();
		}

		viewWidth = w;
		viewHeight = h;
	}

	private ListCell  createNewCell (){
		return getCellFactory().createNewCell();
	}

	private void  createCells (){
		if(getCellFactory().isShareCells()){
			createCellsWhenShareCells();
		}else{
			createCellsWhenNotShareCells();
		}
	}

	private void  removeAllCells (){
		for(int i =0;i <cells.getSize ();i ++){
			ListCell cell =cells.get(i );
			cell.getCellComponent().removeFromContainer();
		}
		cells.clear();
	}

	private void  validateCells (){
		revalidate();
	}

	//--------------------------------------------------------

	protected void  fireStateChanged (boolean programmatic =true ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.STATE_CHANGED, programmatic));
	}

	public int  getVerticalUnitIncrement (){
		if(verticalUnitIncrement != AUTO_INCREMENT){
			return verticalUnitIncrement;
		}else if(getCellFactory().isAllCellHasSameHeight()){
			return getCellFactory().getCellHeight();
		}else{
			return 18;
		}
	}

	public int  getVerticalBlockIncrement (){
		if(verticalBlockIncrement != AUTO_INCREMENT){
			return verticalBlockIncrement;
		}else if(getCellFactory().isAllCellHasSameHeight()){
			return getExtentSize().height - getCellFactory().getCellHeight();
		}else{
			return getExtentSize().height - 10;
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
			return getExtentSize().width - 1;
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

    public void  setViewportTestSize (IntDimension s ){
    	setSize(s);
    }

	public IntDimension  getExtentSize (){
    	return getInsets().getInsideSize(getSize());
	}

	public IntDimension  getViewSize (){
		int w =isTracksWidth ()? getExtentSize().width : viewWidth;
		return new IntDimension(w, viewHeight);
	}

	public IntPoint  getViewPosition (){
		return new IntPoint(viewPosition.x, viewPosition.y);
	}

	public void  setViewPosition (IntPoint p ,boolean programmatic =true ){
		restrictionViewPos(p);
		if(!viewPosition.equals(p)){
			viewPosition.setLocation(p);
			fireStateChanged(programmatic);
			//revalidate();
			valid = false;
			RepaintManager.getInstance().addInvalidRootComponent(this);
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

	public Component  getViewportPane (){
		return this;
	}
	//------------------------Layout implementation---------------------


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
    	IntDimension viewSize =getViewSize ();
    	int rowCount =getVisibleRowCount ();
    	if(rowCount > 0){
	    	int rowHeight =20;
	    	if(getCellFactory().isAllCellHasSameHeight()){
	    		rowHeight = getCellFactory().getCellHeight();
	    	}
    		viewSize.height = rowCount * rowHeight;
    	}
    	int cellWidth =getVisibleCellWidth ();
    	if(cellWidth > 0){
    		viewSize.width = cellWidth;
    	}
    	return getInsets().getOutsideSize(viewSize);
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
    	ListCellFactory factory =getCellFactory ();

		IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
    	cellPane.setComBounds(ir);

    	if(factory.isShareCells()){
    		layoutWhenShareCells();
    	}else{
    		if(factory.isAllCellHasSameHeight()){
    			layoutWhenNotShareCellsAndSameHeight();
    		}else{
    			layoutWhenNotShareCellsAndNotSameHeight();
    		}
    	}
    }

    private void  layoutWhenShareCells (){
    	checkCreateCellsWhenShareCells();

    	ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();
		IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
    	int cellWidth =ir.width ;
    	ir.x = ir.y = 0; //this is because the cells is in cellPane, not in JList

    	restrictionViewPos(viewPosition);
		int x =viewPosition.x ;
		int y =viewPosition.y ;
		int ih =factory.getCellHeight ();
		int startIndex =Math.floor(y /ih );
		int startY =startIndex *ih -y ;
		int listSize =m.getSize ();
		int cx =ir.x -x ;
		int cy =ir.y +startY ;
		int maxY =ir.y +ir.height ;
		int cellsSize =cells.getSize ();
		if(listSize < 0){
			lastVisibleIndex = -1;
		}
		for(int i =0;i <cellsSize ;i ++){
			ListCell cell =cells.get(i );
			int ldIndex =startIndex +i ;
			Component cellCom =cell.getCellComponent ();
			if(ldIndex < listSize){
				cell.setCellValue(m.getElementAt(ldIndex));
				cellCom.setVisible(true);
				cellCom.setComBoundsXYWH(cx, cy, cellWidth, ih);
				if(cy < maxY){
					lastVisibleIndex = ldIndex;
				}
				cy += ih;
				cell.setListCellStatus(this, isSelectedIndex(ldIndex), ldIndex);
			}else{
				cellCom.setVisible(false);
			}
			cellCom.validate();
		}
		firstVisibleIndex = startIndex;
    }

    private void  layoutWhenNotShareCellsAndSameHeight (){
    	ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();
		IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
    	int cellWidth =Math.max(ir.width ,viewWidth );
    	ir.x = ir.y = 0; //this is because the cells is in cellPane, not in JList

    	restrictionViewPos(viewPosition);
		int x =viewPosition.x ;
		int y =viewPosition.y ;
		int ih =factory.getCellHeight ();
		int startIndex =Math.floor(y /ih );
		int listSize =m.getSize ();
		int startY =startIndex *ih -y ;

		int endIndex =startIndex +Math.ceil ((ir.height -(ih +startY ))/ih );
		if(endIndex >= listSize){
			endIndex = listSize - 1;
		}

		int cx =ir.x -x ;
		int cy =ir.y +startY ;
		int maxY =ir.y +ir.height ;
		int i ;
		Component cellCom ;
		//invisible last viewed
		for(i=Math.max(0, firstVisibleIndex+firstVisibleIndexOffset); i<startIndex; i++){
			cellCom = ListCell(cells.get(i)).getCellComponent();
			cellCom.setVisible(false);
			cellCom.validate();
		}
		int rlvi =Math.min(lastVisibleIndex +lastVisibleIndexOffset ,listSize -1);
		for(i=endIndex+1; i<=rlvi; i++){
			cellCom = ListCell(cells.get(i)).getCellComponent();
			cellCom.setVisible(false);
			cellCom.validate();
		}
		if(endIndex < 0 || startIndex > endIndex){
			lastVisibleIndex = -1;
		}
		//visible current needed
		for(i=startIndex; i<=endIndex; i++){
			ListCell cell =ListCell(cells.get(i ));
			cellCom = cell.getCellComponent();
			cellCom.setVisible(true);
			IntDimension s =getCachedCellPreferSize(cell );
			if(s == null){
				s = cell.getCellComponent().getPreferredSize();
				trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
			}
			int finalWidth =isTracksWidth ()? ir.width : Math.max(cellWidth, s.width);
			cellCom.setComBoundsXYWH(cx, cy, finalWidth, ih);
			if(cy < maxY){
				lastVisibleIndex = i;
			}
			cy += ih;
			cell.setListCellStatus(this, isSelectedIndex(i), i);
			cellCom.validate();
		}
		firstVisibleIndex = startIndex;
		firstVisibleIndexOffset = lastVisibleIndexOffset = 0;
    }

    private IntDimension  getCachedCellPreferSize (ListCell cell ){
    	return IntDimension(cellPrefferSizes.get(cell.getCellComponent()));
    }

    private void  layoutWhenNotShareCellsAndNotSameHeight (){
		ListModel m =getModel ();
		IntRectangle ir =getInsets ().getInsideBounds(getSize ().getBounds ());
    	int cellWidth =Math.max(ir.width ,viewWidth );
    	ir.x = ir.y = 0; //this is because the cells is in cellPane, not in JList

    	restrictionViewPos(viewPosition);
		int x =viewPosition.x ;
		int y =viewPosition.y ;
		int startIndex =0;
		int cellsCount =cells.getSize ();

		int tryY =0;
		int startY =0;
		int i ;
		IntDimension s ;
		ListCell cell ;

		for(i=0; i<cellsCount; i++){
			cell = ListCell(cells.get(i));
			s = getCachedCellPreferSize(cell);
			if(s == null){
				s = cell.getCellComponent().getPreferredSize();
				trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
			}
			tryY += s.height;
			if(tryY > y){
				startIndex = i;
				startY = -(s.height - (tryY - y));
				break;
			}
		}

		int listSize =m.getSize ();
		int cx =ir.x -x ;
		int cy =ir.y +startY ;
		int maxY =ir.y +ir.height ;
		int tempLastVisibleIndex =-1;
		Component cellCom ;
		//visible current needed
		int endIndex =startIndex ;
		for(i=startIndex; i<cellsCount; i++){
			cell = ListCell(cells.get(i));
			cellCom = cell.getCellComponent();
			s = getCachedCellPreferSize(cell);
			if(s == null){
				s = cell.getCellComponent().getPreferredSize();
				trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
			}
			cell.setListCellStatus(this, isSelectedIndex(i), i);
			cellCom.setVisible(true);
			int finalWidth =isTracksWidth ()? ir.width : Math.max(cellWidth, s.width);
			cellCom.setComBoundsXYWH(cx, cy, finalWidth, s.height);
			cellCom.validate();
			if(cy < maxY){
				tempLastVisibleIndex = i;
			}
			cy += s.height;
			endIndex = i;
			if(cy >= maxY){
				break;
			}
		}

		//invisible last viewed
		for(i=Math.max(0, firstVisibleIndex+firstVisibleIndexOffset); i<startIndex; i++){
			cellCom = ListCell(cells.get(i)).getCellComponent();
			cellCom.setVisible(false);
			cellCom.validate();
		}
		int rlvi =Math.min(lastVisibleIndex +lastVisibleIndexOffset ,listSize -1);
		for(i=endIndex+1; i<=rlvi; i++){
			cellCom = ListCell(cells.get(i)).getCellComponent();
			cellCom.setVisible(false);
			cellCom.validate();
		}
		lastVisibleIndex = tempLastVisibleIndex;
		firstVisibleIndex = startIndex;
		firstVisibleIndexOffset = lastVisibleIndexOffset = 0;
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

	//------------------------ListMode Listener Methods-----------------

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  intervalAdded (ListDataEvent e ){
    	ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();

		int w =viewWidth ;
		int h =viewHeight ;
		boolean sameHeight =factory.isAllCellHasSameHeight ();

		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());

		if(factory.isShareCells()){
			w = getPreferredCellWidthWhenNoCount();
			h = m.getSize()*factory.getCellHeight();
		}else{
			for(int i =i0 ;i <=i1 ;i ++){
				ListCell cell =createNewCell ();
				cells.append(cell, i);
				cell.setCellValue(m.getElementAt(i));
				addCellToContainer(cell);
				IntDimension s =cell.getCellComponent ().getPreferredSize ();
				cell.getCellComponent().setVisible(false);
				cellPrefferSizes.put(cell.getCellComponent(), s);
				if(s.width > w){
					w = s.width;
					maxWidthCell = cell;
				}
				w = Math.max(w, s.width);
				if(!sameHeight){
					h += s.height;
				}
			}
			if(sameHeight){
				h = m.getSize()*factory.getCellHeight();
			}

			if(i0 > lastVisibleIndex + lastVisibleIndexOffset){
				//nothing needed
			}else if(i0 >= firstVisibleIndex + firstVisibleIndexOffset){
				lastVisibleIndexOffset += (i1 - i0 + 1);
			}else if(i0 < firstVisibleIndex + firstVisibleIndexOffset){
				firstVisibleIndexOffset += (i1 - i0 + 1);
				lastVisibleIndexOffset += (i1 - i0 + 1);
			}
		}

		viewWidth = w;
		viewHeight = h;
		getSelectionModel().insertIndexInterval(i0, i1-i0+1, true);
		revalidate();
    }

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  intervalRemoved (ListDataEvent e ){
    	ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();

		int w =viewWidth ;
		int h =viewHeight ;
		boolean sameHeight =factory.isAllCellHasSameHeight ();

		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());

		int i ;
		IntDimension s ;
		ListCell cell ;

		if(factory.isShareCells()){
			w = getPreferredCellWidthWhenNoCount();
			h = m.getSize()*factory.getCellHeight();
		}else{
			boolean needRecountWidth =false ;
			for(i=i0; i<=i1; i++){
				cell = ListCell(cells.get(i));
				if(cell == maxWidthCell){
					needRecountWidth = true;
				}
				if(!sameHeight){
					s = getCachedCellPreferSize(cell);
					if(s == null){
						s = cell.getCellComponent().getPreferredSize();
						trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
					}
					h -= s.height;
				}
				removeCellFromeContainer(cell);
				cellPrefferSizes.remove(cell.getCellComponent());
			}
			cells.removeRange(i0, i1);
			if(sameHeight){
				h = m.getSize()*factory.getCellHeight();
			}
			if(needRecountWidth){
				w = 0;
				for(i=cells.getSize()-1; i>=0; i--){
					cell = ListCell(cells.get(i));
					s = getCachedCellPreferSize(cell);
					if(s == null){
						s = cell.getCellComponent().getPreferredSize();
						trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
					}
					if(s.width > w){
						w = s.width;
						maxWidthCell = cell;
					}
				}
			}
			if(i0 > lastVisibleIndex + lastVisibleIndexOffset){
				//nothing needed
			}else if(i0 >= firstVisibleIndex + firstVisibleIndexOffset){
				lastVisibleIndexOffset -= (i1 - i0 + 1);
			}else if(i0 < firstVisibleIndex + firstVisibleIndexOffset){
				firstVisibleIndexOffset -= (i1 - i0 + 1);
				lastVisibleIndexOffset -= (i1 - i0 + 1);
			}
		}

		viewWidth = w;
		viewHeight = h;
		getSelectionModel().removeIndexInterval(i0, i1);
		revalidate();
    }

	/**
	 * data in list has changed, update JList if needed.
	 */
    public void  contentsChanged (ListDataEvent e ){
    	ListCellFactory factory =getCellFactory ();
		ListModel m =getModel ();

		int w =viewWidth ;
		int h =viewHeight ;
		boolean sameHeight =factory.isAllCellHasSameHeight ();

		int i0 =Math.min(e.getIndex0 (),e.getIndex1 ());
		int i1 =Math.max(e.getIndex0 (),e.getIndex1 ());
		int i ;
		IntDimension s ;
		ListCell cell ;
		IntDimension ns ;

		if(factory.isShareCells()){
			w = getPreferredCellWidthWhenNoCount();
			h = m.getSize()*factory.getCellHeight();
		}else{
			boolean needRecountWidth =false ;
			for(i=i0; i<=i1; i++){
				Object newValue =m.getElementAt(i );
				cell = ListCell(cells.get(i));
				s = getCachedCellPreferSize(cell);
				if(s == null){
					s = cell.getCellComponent().getPreferredSize();
					trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
				}
				if(cell == maxWidthCell){
					h -= s.height;
					cell.setCellValue(newValue);
					ns = cell.getCellComponent().getPreferredSize();
					cellPrefferSizes.put(cell.getCellComponent(), ns);
					if(ns.width < s.width){
						needRecountWidth = true;
					}else{
						w = ns.width;
					}
					h += ns.height;
				}else{
					h -= s.height;
					cell.setCellValue(newValue);
					ns = cell.getCellComponent().getPreferredSize();
					cellPrefferSizes.put(cell.getCellComponent(), ns);
					h += ns.height;
					if(!needRecountWidth){
						if(ns.width > w){
							maxWidthCell = cell;
							w = ns.width;
						}
					}
				}
			}
			if(sameHeight){
				h = m.getSize()*factory.getCellHeight();
			}
			if(needRecountWidth || maxWidthCell == null){
				w = 0;
				for(i=cells.getSize()-1; i>=0; i--){
					cell = cells.get(i);
					s = getCachedCellPreferSize(cell);
					if(s == null){
						s = cell.getCellComponent().getPreferredSize();
						trace("Warnning : cell size not cached index = " + i + ", value = " + cell.getCellValue());
					}
					if(s.width > w){
						w = s.width;
						maxWidthCell = cell;
					}
				}
			}
		}

		viewWidth = w;
		viewHeight = h;

		revalidate();
    }

    private void  __selectionListener (SelectionEvent e ){
    	dispatchEvent(new SelectionEvent(SelectionEvent.LIST_SELECTION_CHANGED, e.getFirstIndex(), e.getLastIndex(), e.isProgrammatic()));
    	revalidate();
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

	protected ListItemEvent  createItemEventObj (*cellCom ,String type ,MouseEvent e ){
		ListCell cell =getCellByCellComponent(Component(cellCom ));
		ListItemEvent event =new ListItemEvent(type ,cell.getCellValue (),cell ,e );
		return event;
	}

	protected int  getItemIndexByCellComponent (Component item ){
		ListCell cell =comToCellMap.get(item );
		return getItemIndexByCell(cell);
	}

	/**
	 * Returns the index of the cell.
	 */
	public int  getItemIndexByCell (ListCell cell ){
		if(getCellFactory().isShareCells()){
			return firstVisibleIndex + cells.indexOf(cell);
		}else{
			return cells.indexOf(cell);
		}
	}

	private ListCell  getCellByCellComponent (Component item ){
		return comToCellMap.get(item);
	}

	/**
	 * Returns the cell of the specified index
	 */
	public ListCell  getCellByIndex (int index ){
		if(getCellFactory().isShareCells()){
			return ListCell(cells.get(index - firstVisibleIndex));
		}else{
			return ListCell(cells.get(index));
		}
	}


    /**
     * Event Listener For All Items
     */
	private void  __onItemMouseDown (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_MOUSE_DOWN, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemClick (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_CLICK, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemReleaseOutSide (ReleaseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_RELEASE_OUT_SIDE, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemRollOver (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_ROLL_OVER, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemRollOut (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_ROLL_OUT, e));
	}

    /**
     * Event Listener For All Items
     */
	private void  __onItemDoubleClick (MouseEvent e ){
		dispatchEvent(createItemEventObj(e.currentTarget, ListItemEvent.ITEM_DOUBLE_CLICK, e));
	}

	//-------------------------------Drag and Drop---------------------------------

	/*private Timer dndAutoScrollTimer ;
	private MovieClip dnd_line_mc ;

	private void  __onDragRecognized (Component dragInitiator ,Component touchedChild ){
		if(isAutoDnDInitiatorAllown()){
			Array data =getSelectedIndices ();
			ListSourceData sourceData =new ListSourceData("ListSourceData",data );

			int firstIndex =getFirstVisibleIndex ();
			int lastIndex =getLastVisibleIndex ();
			IntPoint mp =getMousePosition ();
			Rectangle ib =new Rectangle ();
			int offsetY =mp.y ;
			for(int i =firstIndex ;i <=lastIndex ;i ++){
				ib = getCellByIndex(i).getCellComponent().getBounds(ib);
				if(mp.y < ib.y + ib.height){
					offsetY = ib.y;
					break;
				}
			}

			DragManager.startDrag(this, sourceData, new ListDragImage(this, offsetY));
		}
	}
	private void  __onDragEnter (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		dndInsertPosition = -1;
		if(!(isAcceptableListSourceData(dragInitiator, sourceData) && isAutoDnDDropTargetAllown())){
			DragManager.getCurrentDragImage().switchToRejectImage();
		}else{
			DragManager.getCurrentDragImage().switchToAcceptImage();
			checkStartDnDAutoScroll();
		}
	}
	private void  __onDragOverring (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		if(isAcceptableListSourceData(dragInitiator, sourceData) && isAutoDnDDropTargetAllown()){
			checkStartDnDAutoScroll();
			drawInsertLine();
		}
	}
	private void  __onDragExit (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		checkStopDnDAutoScroll();
	}
	private void  __onDragDrop (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		checkStopDnDAutoScroll();
		if(isAcceptableListSourceData(dragInitiator, sourceData) && isAutoDnDDropTargetAllown()){
			if(dndInsertPosition >= 0){
				Array indices =(ListSourceData(sourceData )).getItemIndices ();
				if(indices == null || indices.length == null || indices.length <= 0){
					return;
				}
				JList initiator =JList(dragInitiator );
				Array items =new Array(indices.length );
				for(int i =0;i <indices.length ;i ++){
					items.put(i,  initiator.getModel().getElementAt(indices.get(i)));
				}
				int insertOffset =0;
				if(initiator.getAutoDragAndDropType() == DND_MOVE){
					MutableListModel imm =MutableListModel(initiator.getModel ());
					boolean sameModel =(imm ==getModel ());
					for(int i =0;i <indices.length ;i ++){
						int rindex =indices.get(i) ;
						imm.removeElementAt(rindex-i);
						if(sameModel && rindex<dndInsertPosition){
							insertOffset ++;
						}
					}
				}
				int index =dndInsertPosition -insertOffset ;
				MutableListModel mm =MutableListModel(getModel ());
				for(int i =0;i <items.length ;i ++){
					mm.insertElementAt(items.get(i), index);
					index++;
				}
				return;
			}
		}
		DragManager.setDropMotion(DragManager.DEFAULT_REJECT_DROP_MOTION);
	}
	*/
	/**
	 * Returns is the source data is acceptale to drop in this list as build-in support
	 */
	/*public boolean  isAcceptableListSourceData (Component dragInitiator ,SourceData sd ){
		return (sd is ListSourceData) && isDragAcceptableInitiator(dragInitiator);
	}*/

	/**
	 * Returns is the model is mutable
	 */
	public boolean  isMutableModel (){
		return getModel() is MutableListModel;
	}

	/*private void  checkStartDnDAutoScroll (){
		if(dndAutoScrollTimer == null){
			dndAutoScrollTimer = new Timer(200);
			dndAutoScrollTimer.addActionListener(__dndAutoScroll, this);
		}
		if(!dndAutoScrollTimer.isRunning()){
			dndAutoScrollTimer.start();
		}
		if(!MCUtils.isMovieClipExist(dnd_line_mc)){
			dnd_line_mc = createMovieClip("line_mc");
		}
	}
	private void  checkStopDnDAutoScroll (){
		if(dndAutoScrollTimer != null){
			dndAutoScrollTimer.stop();
		}
		if(dnd_line_mc != null){
			dnd_line_mc.removeMovieClip();
			dnd_line_mc = null;
		}
	}

	private void  __dndAutoScroll (){
		Rectangle lastCellBounds =getCellByIndex(getLastVisibleIndex ()).getCellComponent ().getBounds ();
		Rectangle firstCellBounds =getCellByIndex(getFirstVisibleIndex ()).getCellComponent ().getBounds ();
		IntPoint vp =getViewPosition ();
		IntPoint mp =getMousePosition ();
		Insets ins =getInsets ();

		if(mp.y < ins.top + firstCellBounds.height/2){
			vp.y -= firstCellBounds.height;
			setViewPosition(vp);
			drawInsertLine();
		}else if(mp.y > getHeight() - ins.bottom - lastCellBounds.height/2){
			vp.y += lastCellBounds.height;
			setViewPosition(vp);
			drawInsertLine();
		}
	}

	private int dndInsertPosition ;
	private void  drawInsertLine (){
		int firstIndex =getFirstVisibleIndex ();
		int lastIndex =getLastVisibleIndex ();

		IntPoint mp =getMousePosition ();
		Rectangle ib =new Rectangle ();
		int insertIndex =-1;
		int insertY ;
		for(int i =firstIndex ;i <=lastIndex ;i ++){
			ib = getCellByIndex(i).getCellComponent().getBounds(ib);
			if(mp.y < ib.y + ib.height/2){
				insertIndex = i;
				insertY = ib.y;
				break;
			}
		}
		if(insertIndex < 0){
			ib = getCellByIndex(lastIndex).getCellComponent().getBounds(ib);
			insertIndex = lastIndex+1;
			insertY = ib.y + ib.height;
		}
		dndInsertPosition = insertIndex;

		dnd_line_mc.clear();
		Graphics g =new Graphics(dnd_line_mc );
		Pen pen =new Pen(0,2,70);
		Insets ins =this.getInsets ();

		g.drawLine(pen, ins.left+1, insertY, getWidth()-ins.right-1, insertY);
	}


	private void  ____onDragRecognized (Component dragInitiator ,Component touchedChild ){
		__onDragRecognized(dragInitiator, touchedChild);
	}
	private void  ____onDragEnter (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		__onDragEnter(source, dragInitiator, sourceData, mousePos);
	}
	private void  ____onDragOverring (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		__onDragOverring(source, dragInitiator, sourceData, mousePos);
	}
	private void  ____onDragExit (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		__onDragExit(source, dragInitiator, sourceData, mousePos);
	}
	private void  ____onDragDrop (Component source ,Component dragInitiator ,SourceData sourceData ,IntPoint mousePos ){
		__onDragDrop(source, dragInitiator, sourceData, mousePos);
	}
	*/
}


