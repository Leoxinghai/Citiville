/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.tree;

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


import flash.events.EventDispatcher;
import org.aswing.DefaultListSelectionModel;
import org.aswing.event.*;
import org.aswing.tree.*;
import org.aswing.util.*;

/**
 * Default implementation of TreeSelectionModel.  Listeners are notified
 * whenever
 * the paths in the selection change, not the rows. In order
 * to be able to track row changes you may wish to become a listener
 * for expansion events on the tree and test for changes from there.
 * <p>resetRowSelection is called from any of the methods that update
 * the selected paths. If you subclass any of these methods to
 * filter what is allowed to be selected, be sure and message
 * <code>resetRowSelection</code> if you do not message super.
 *
 * @author iiley
 */
public class DefaultTreeSelectionModel extends EventDispatcher implements TreeSelectionModel{

    /** Selection can only contain one path at a time. */
    public static  int SINGLE_TREE_SELECTION =1;

    /** Selection can only be contiguous. This will only be enforced if
     * a RowMapper instance is provided. That is, if no RowMapper is set
     * this behaves the same as DISCONTIGUOUS_TREE_SELECTION. */
    public static  int CONTIGUOUS_TREE_SELECTION =2;

    /** Selection can contain any number of items that are not necessarily
     * contiguous. */
    public static  int DISCONTIGUOUS_TREE_SELECTION =4;

    /** Property name for selectionMode. */
    public static  String SELECTION_MODE_PROPERTY ="selectionMode";

    /**
     * onPropertyChanged(source:DefaultTreeSelectionModel, name:String, oldValue, newValue);
	 */
    public static  String ON_PROPERTY_CHANGED ="onPropertyChanged";

    /**
     * onSelectionChanged(source:DefaultTreeSelectionModel, e:TreeSelectionEvent);
     */
    public static  String ON_SELECTION_CHANGED ="onSelectionChanged";

    /** Paths that are currently selected.  Will be null if nothing is
      * currently selected. */
    private Array selection ;//TreePath[]

    /** Provides a row for a given path. */
    private RowMapper rowMapper ;

    /** Handles maintaining the list selection model. The RowMapper is used
     * to map from a TreePath to a row, and the value is then placed here. */
    private DefaultListSelectionModel listSelectionModel ;

    /** Mode for the selection, will be either SINGLE_TREE_SELECTION,
     * CONTIGUOUS_TREE_SELECTION or DISCONTIGUOUS_TREE_SELECTION.
     */
    private int selectionMode ;

    /** Last path that was added. */
    private TreePath leadPath ;
    /** Index of the lead path in selection. */
    private int leadIndex ;
    /** Lead row. */
    private int leadRow ;

    /** Used to make sure the paths are unique, will contain all the paths
     * in <code>selection</code>.
     */
    private TreePathMap uniquePaths ;
    private TreePathMap lastPaths ;
    private Array tempPaths ;//TreePath[]


    /**
     * Creates a new instance of DefaultTreeSelectionModel that is
     * empty, with a selection mode of DISCONTIGUOUS_TREE_SELECTION.
     */
    public  DefaultTreeSelectionModel (){
		listSelectionModel = new DefaultListSelectionModel();
		selectionMode = DISCONTIGUOUS_TREE_SELECTION;
		leadIndex = leadRow = -1;
		uniquePaths = new TreePathMap();
		lastPaths = new TreePathMap();
		tempPaths = new Array(1);
    }

    /**
     * Sets the RowMapper instance. This instance is used to determine
     * the row for a particular TreePath.
     */
    public void  setRowMapper (RowMapper newMapper ){
		rowMapper = newMapper;
		resetRowSelection();
    }

    /**
     * Returns the RowMapper instance that is able to map a TreePath to a
     * row.
     */
    public RowMapper  getRowMapper (){
		return rowMapper;
    }

    /**
     * Sets the selection model, which must be one of SINGLE_TREE_SELECTION,
     * CONTIGUOUS_TREE_SELECTION or DISCONTIGUOUS_TREE_SELECTION. If mode
     * is not one of the defined value,
     * <code>DISCONTIGUOUS_TREE_SELECTION</code> is assumed.
     * <p>This may change the selection if the current selection is not valid
     * for the new mode. For example, if three TreePaths are
     * selected when the mode is changed to <code>SINGLE_TREE_SELECTION</code>,
     * only one TreePath will remain selected. It is up to the particular
     * implementation to decide what TreePath remains selected.
     * <p>
     * Setting the mode to something other than the defined types will
     * result in the mode becoming <code>DISCONTIGUOUS_TREE_SELECTION</code>.
     */
    public void  setSelectionMode (int mode ){
		int oldMode =selectionMode ;

		selectionMode = mode;
		if(selectionMode != SINGLE_TREE_SELECTION &&
		   selectionMode != CONTIGUOUS_TREE_SELECTION &&
		   selectionMode != DISCONTIGUOUS_TREE_SELECTION){
		    selectionMode = DISCONTIGUOUS_TREE_SELECTION;
		}
		if(oldMode != selectionMode){
		    firePropertyChange(SELECTION_MODE_PROPERTY, oldMode, selectionMode);
		}
    }

    protected void  firePropertyChange (String name ,*oldValue ,*)newValue {
    	dispatchEvent(new PropertyChangeEvent(name, oldValue, newValue));
    }

    /**
     * Returns the selection mode, one of <code>SINGLE_TREE_SELECTION</code>,
     * <code>DISCONTIGUOUS_TREE_SELECTION</code> or
     * <code>CONTIGUOUS_TREE_SELECTION</code>.
     */
    public int  getSelectionMode (){
		return selectionMode;
    }

    /**
      * Sets the selection to path. If this represents a change, then
      * the TreeSelectionListeners are notified. If <code>path</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      *
      * @param path new path to select.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  setSelectionPath (TreePath path ,boolean programmatic =true ){
		if(path == null){
		    setSelectionPaths(null, programmatic);
		}else {
		    setSelectionPaths(.get(path), programmatic);
		}
    }

    /**
      * Sets the selection to the paths in paths.  If this represents a
      * change the TreeSelectionListeners are notified.  Potentially
      * paths will be held by this object; in other words don't change
      * any of the objects in the array once passed in.
      * <p>If <code>paths</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      * <p>The lead path is set to the last path in <code>pPaths</code>.
      * <p>If the selection mode is <code>CONTIGUOUS_TREE_SELECTION</code>,
      * and adding the new paths would make the selection discontiguous,
      * the selection is reset to the first TreePath in <code>paths</code>.
      *
      * @param pPaths new selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  setSelectionPaths (Array pPaths ,boolean programmatic =true ){
		int newCount ,newCounter int ,oldCount ,oldCounter ;
		Array paths =pPaths ;

		if(paths == null)
		    newCount = 0;
		else
		    newCount = paths.length;
		if(selection == null)
		    oldCount = 0;
		else
		    oldCount = selection.length;
		if((newCount + oldCount) != 0) {
		    if(selectionMode == SINGLE_TREE_SELECTION) {
				/* If single selection and more than one path, only allow
				   first. */
				if(newCount > 1) {
				    paths = .get(pPaths.get(0));
				    newCount = 1;
				}
		    }else if(selectionMode == CONTIGUOUS_TREE_SELECTION) {
				/* If contiguous selection and paths aren't contiguous,
				   only select the first path item. */
				if(newCount > 0 && !arePathsContiguous(paths)) {
				    paths = .get(pPaths.get(0));
				    newCount = 1;
				}
		    }

		    int validCount =0;
		    TreePath beginLeadPath =leadPath ;
		    ArrayList cPaths =new ArrayList ();
		    TreePath path ;

		    lastPaths.clear();
		    leadPath = null;
		    /* Find the paths that are new. */
		    for(newCounter = 0; newCounter < newCount; newCounter++) {
		    	path = paths.get(newCounter);
				if(path != null && lastPaths.get(path) == null) {
				    validCount++;
				    lastPaths.put(path, true);
				    if (uniquePaths.get(path) == null) {
						cPaths.append(new PathPlaceHolder(path, true));
				    }
				    leadPath = path;
				}
		    }

		    /* If the validCount isn't equal to newCount it means there
		       are some null in paths, remove them and set selection to
		       the new path. */
		    Array newSelection ;

		    if(validCount == 0) {
				newSelection = null;
		    } else if (validCount != newCount) {
				Array keys =lastPaths.keys ();

				newSelection = new Array(validCount);
				validCount = 0;
				for(int i =0;i <keys.length ;i ++){
					newSelection.put(validCount++,  keys.get(i));
				}
		    }else {
				newSelection = paths.concat();
		    }

		    /* Get the paths that were selected but no longer selected. */
		    for(oldCounter = 0; oldCounter < oldCount; oldCounter++){
		    	path = selection.get(oldCounter);
				if(path != null &&  lastPaths.get(path) == null){
			    	cPaths.append(new PathPlaceHolder(path, false));
				}
		    }
		    selection = newSelection;

		    TreePathMap tempHT =uniquePaths ;

		    uniquePaths = lastPaths;
		    lastPaths = tempHT;
		    lastPaths.clear();

		    // No reason to do this now, but will still call it.
		    if(selection != null){
				insureUniqueness();
		    }

		    updateLeadIndex();

		    resetRowSelection();
		    /* Notify of the change. */
		    if(cPaths.size() > 0){
				notifyPathChange(cPaths, beginLeadPath, programmatic);
		    }
		}
    }

    /**
      * Adds path to the current selection. If path is not currently
      * in the selection the TreeSelectionListeners are notified. This has
      * no effect if <code>path</code> is null.
      *
      * @param path the new path to add to the current selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  addSelectionPath (TreePath path ,boolean programmatic =true ){
		if(path != null) {
		    addSelectionPaths(.get(path), programmatic);
		}
    }

    /**
      * Adds paths(TreePath[]) to the current selection. If any of the paths in
      * paths are not currently in the selection the TreeSelectionListeners
      * are notified. This has
      * no effect if <code>paths</code> is null.
      * <p>The lead path is set to the last element in <code>paths</code>.
      * <p>If the selection mode is <code>CONTIGUOUS_TREE_SELECTION</code>,
      * and adding the new paths would make the selection discontiguous.
      * Then two things can result: if the TreePaths in <code>paths</code>
      * are contiguous, then the selection becomes these TreePaths,
      * otherwise the TreePaths aren't contiguous and the selection becomes
      * the first TreePath in <code>paths</code>.
      *
      * @param paths the new path to add to the current selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  addSelectionPaths (Array paths ,boolean programmatic =true ){
		int newPathLength =((paths ==null )? 0 : paths.length());
		if(newPathLength <= 0){
			return;
		}
	    if(selectionMode == SINGLE_TREE_SELECTION) {
			setSelectionPaths(paths);
	    }else if(selectionMode == CONTIGUOUS_TREE_SELECTION && !canPathsBeAdded(paths)) {
			if(arePathsContiguous(paths)) {
			    setSelectionPaths(paths);
			}else {
			    setSelectionPaths(.get(paths.get(0)));
			}
	    } else {
			int counter ,validCount int ,oldCount ;
			TreePath beginLeadPath =leadPath ;
			ArrayList cPaths =null ;

			if(selection == null)
			    oldCount = 0;
			else
			    oldCount = selection.length;
			/* Determine the paths that aren't currently in the
			   selection. */
			lastPaths.clear();
			counter = 0;
			validCount = 0;
			for(; counter < newPathLength; counter++) {
				TreePath path =paths.get(counter) ;
			    if(path != null) {
					if (uniquePaths.get(path) == null) {
					    validCount++;
					    if(cPaths == null){
							cPaths = new ArrayList();
					    }
					    cPaths.append(new PathPlaceHolder(path, true));
					    uniquePaths.put(path, true);
					    lastPaths.put(path, true);
					}
					leadPath = path;
			    }
			}

			if(leadPath == null) {
			    leadPath = beginLeadPath;
			}

			if(validCount > 0) {
			    Array newSelection =new Array ();

			    /* And build the new selection. */
			    if(oldCount > 0){
					newSelection = selection.concat();
			    }
			    if(validCount != paths.length()) {
					/* Some of the paths in paths are already in
					   the selection. */
					Array newPaths =lastPaths.keys ();
					newSelection = newSelection.concat(newPaths);
			    } else {
			    	newSelection = newSelection.concat(paths);
			    }

			    selection = newSelection;

			    insureUniqueness();

			    updateLeadIndex();

			    resetRowSelection();

			    notifyPathChange(cPaths, beginLeadPath, programmatic);
			}else{
			    leadPath = beginLeadPath;
			}
			lastPaths.clear();
	    }
    }

    /**
      * Removes path from the selection. If path is in the selection
      * The TreeSelectionListeners are notified. This has no effect if
      * <code>path</code> is null.
      *
      * @param path the path to remove from the selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  removeSelectionPath (TreePath path ,boolean programmatic =true ){
		if(path != null) {
		    removeSelectionPaths(.get(path), programmatic);
		}
    }

    /**
      * Removes paths from the selection.  If any of the paths in paths
      * are in the selection the TreeSelectionListeners are notified.
      * This has no effect if <code>paths</code> is null.
      *
      * @param paths the paths to remove from the selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  removeSelectionPaths (Array paths ,boolean programmatic =true ){
		if (paths != null && selection != null && paths.length > 0) {
		    if(!canPathsBeRemoved(paths)) {
				/* Could probably do something more interesting here!
				 * Yes, maybe:) */
				clearSelection();
		    }else{
				ArrayList pathsToRemove =null ;

				/* Find the paths that can be removed. */
				for(int removeCounter =paths.length -1;removeCounter >=0;removeCounter --){
					TreePath path =paths.get(removeCounter) ;
				    if(path != null) {
						if (uniquePaths.get(path) != null) {
						    if(pathsToRemove == null){
								pathsToRemove = new ArrayList();
						    }
						    uniquePaths.remove(path);
						    pathsToRemove.append(new PathPlaceHolder(path, false));
						}
				    }
				}
				if(pathsToRemove != null) {
				    int removeCount =pathsToRemove.size ();
				    TreePath beginLeadPath =leadPath ;

				    if(removeCount == selection.length()) {
						selection = null;
				    }else {
						Array pEnum =uniquePaths.keys ();
						int validCount =0;

						selection = new Array(selection.length - removeCount);
						for(int i =0;i <pEnum.length ;i ++){
							selection.put(validCount++,  pEnum.get(i));
						}
				    }
				    if (leadPath != null && uniquePaths.get(leadPath) == null) {
						if (selection != null) {
						    leadPath = selection.get(selection.length - 1);
						}else {
						    leadPath = null;
						}
				    }else if (selection != null) {
						leadPath = selection.get(selection.length - 1);
				    }else {
						leadPath = null;
				    }
				    updateLeadIndex();

				    resetRowSelection();

				    notifyPathChange(pathsToRemove, beginLeadPath, programmatic);
				}
		    }
		}
    }

    /**
      * Returns the first path in the selection. This is useful if there
      * if only one item currently selected.
      */
    public TreePath  getSelectionPath (){
		if(selection != null){
		    return selection.get(0);
		}else{
			return null;
		}
    }

    /**
      * Returns the paths(TreePath[]) in the selection. This will return null (or an
      * empty array) if nothing is currently selected.
      */
    public Array  getSelectionPaths (){
		if(selection != null) {
		    return selection.concat();
		}
		return null;
    }

    /**
     * Returns the number of paths that are selected.
     */
    public int  getSelectionCount (){
		return (selection == null) ? 0 : selection.length;
    }

    /**
      * Returns true if the path, <code>path</code>,
      * is in the current selection.
      */
    public boolean  isPathSelected (TreePath path ){
		return (path != null) ? (uniquePaths.get(path) != null) : false;
    }

    /**
      * Returns true if the selection is currently empty.
      */
    public boolean  isSelectionEmpty (){
		return (selection == null);
    }

    /**
      * Empties the current selection.  If this represents a change in the
      * current selection, the selection listeners are notified.
      * @param programmatic indicate if this is a programmatic change.
      */
    public void  clearSelection (boolean programmatic =true ){
		if(selection != null) {
		    int selSize =selection.length ;
		    Array newness =new Array(selSize );//boolean[]

		    for(int counter =0;counter <selSize ;counter ++){
				newness.put(counter,  false);
		    }

		    	TreeSelectionEvent event =new TreeSelectionEvent(this ,programmatic ,selection ,newness ,leadPath ,null );

		    leadPath = null;
		    leadIndex = leadRow = -1;
		    uniquePaths.clear();
		    selection = null;
		    resetRowSelection();
		    fireValueChanged(event);
		}
    }

    /**
     * Notifies all listeners that are registered for
     * tree selection events on this object.
     * @see #addTreeSelectionListener
     * @see EventListenerList
     */
    protected void  fireValueChanged (TreeSelectionEvent e ){
    	dispatchEvent(e);
    }

    /**
      * Returns all of the currently selected rows(int[]). This will return
      * null (or an empty array) if there are no selected TreePaths or
      * a RowMapper has not been set.
      * This may return an array of length less that than of the selected
      * TreePaths if some of the rows are not visible (that is the
      * RowMapper returned -1 for the row corresponding to the TreePath).
      */
    public Array  getSelectionRows (){
		// This is currently rather expensive.  Needs
		// to be better support from ListSelectionModel to speed this up.
		if(rowMapper != null && selection != null) {
			int counter ;
		    Array rows =rowMapper.getRowsForPaths(selection );

		    if (rows != null) {
				int invisCount =0;

				for (counter = rows.length - 1; counter >= 0; counter--) {
				    if (rows.get(counter) == -1) {
						invisCount++;
				    }
				}
				if (invisCount > 0) {
				    if (invisCount == rows.length()) {
						rows = null;
				    }else {
						Array tempRows =new Array(rows.length -invisCount );
						counter = rows.length - 1;
						int visCounter =0;
						for (; counter >= 0; counter--) {
						    if (rows.get(counter) != -1) {
								tempRows.put(visCounter++,  rows.get(counter));
						    }
						}
						rows = tempRows;
				    }
				}
		    }
		    return rows;
		}
		return null;
    }

    /**
     * Returns the smallest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
      */
    public int  getMinSelectionRow (){
		return listSelectionModel.getMinSelectionIndex();
    }

    /**
     * Returns the largest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
      */
    public int  getMaxSelectionRow (){
		return listSelectionModel.getMaxSelectionIndex();
    }

    /**
      * Returns true if the row identified by <code>row</code> is selected.
      */
    public boolean  isRowSelected (int row ){
		return listSelectionModel.isSelectedIndex(row);
    }

    /**
     * Updates this object's mapping from TreePath to rows. This should
     * be invoked when the mapping from TreePaths to integers has changed
     * (for example, a node has been expanded).
     * <p>You do not normally have to call this, JTree and its associated
     * Listeners will invoke this for you. If you are implementing your own
     * View class, then you will have to invoke this.
     * <p>This will invoke <code>insureRowContinuity</code> to make sure
     * the currently selected TreePaths are still valid based on the
     * selection mode.
     */
    public void  resetRowSelection (){
		listSelectionModel.clearSelection();
		if(selection != null && rowMapper != null) {
		    int aRow ;
		    Array rows =rowMapper.getRowsForPaths(selection );
			int counter =0;
			int maxCounter =selection.length ;
		    for(; counter < maxCounter; counter++) {
				aRow = rows.get(counter);
				if(aRow != -1) {
				    listSelectionModel.addSelectionInterval(aRow, aRow);
				}
		    }
		    if(leadIndex != -1 && rows != null) {
				leadRow = rows.get(leadIndex);
		    }else if (leadPath != null) {
				// Lead selection path doesn't have to be in the selection.
				tempPaths.put(0,  leadPath);
				rows = rowMapper.getRowsForPaths(tempPaths);
				leadRow = (rows != null) ? rows.get(0) : -1;
		    }else {
				leadRow = -1;
		    }
		    insureRowContinuity();
		}else{
		    leadRow = -1;
		}
    }

    /**
     * Returns the lead selection index. That is the last index that was
     * added.
     */
    public int  getLeadSelectionRow (){
		return leadRow;
    }

    /**
     * Returns the last path that was added. This may differ from the
     * leadSelectionPath property maintained by the JTree.
     */
    public TreePath  getLeadSelectionPath (){
		return leadPath;
    }


    /**
     * Adds a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A PropertyChangeEvent will get fired when the selection mode
     * changes.
     *
     * @param listener the propertyChangeListener to be added
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
     * @see org.aswing.JTree#ON_PROPERTY_CHANGED
     */
    public void  addPropertyChangeListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, listener, false, priority, useWeakReference);
    }

	/**
	 * Removed a propertyChangeListener.
	 * @param listener the listener to be removed.
	 */
	public void  removePropertyChangeListener (Function listener ){
		removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, listener);
	}

    /**
      * Adds x to the list of listeners that are notified each time the
      * set of selected TreePaths changes.
      *
      * @param listener the new listener to be added
	  * @param priority the priority
	  * @param useWeakReference Determines whether the reference to the listener is strong or weak.
      * @see org.aswing.JTree#ON_SELECTION_CHANGED
      */
    public void  addTreeSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(TreeSelectionEvent.TREE_SELECTION_CHANGED, listener,
    		false, priority, useWeakReference);
    }

	/**
	 * Removed a treeSelectionListener.
	 * @param listener the listener to be removed.
	 */
    public void  removeTreeSelectionListener (Function listener ){
    	removeEventListener(TreeSelectionEvent.TREE_SELECTION_CHANGED, listener);
    }

    /**
     * Makes sure the currently selected <code>TreePath</code>s are valid
     * for the current selection mode.
     * If the selection mode is <code>CONTIGUOUS_TREE_SELECTION</code>
     * and a <code>RowMapper</code> exists, this will make sure all
     * the rows are contiguous, that is, when sorted all the rows are
     * in order with no gaps.
     * If the selection isn't contiguous, the selection is
     * reset to contain the first set, when sorted, of contiguous rows.
     * <p>
     * If the selection mode is <code>SINGLE_TREE_SELECTION</code> and
     * more than one TreePath is selected, the selection is reset to
     * contain the first path currently selected.
     */
    private void  insureRowContinuity (){
		if(selectionMode == CONTIGUOUS_TREE_SELECTION && selection != null && rowMapper != null) {
		    DefaultListSelectionModel lModel =listSelectionModel ;
		    int min =lModel.getMinSelectionIndex ();

		    if(min != -1) {
		    	int counter =min ;
		    	int maxCounter =lModel.getMaxSelectionIndex ();
				for(; counter <= maxCounter; counter++) {
				    if(!lModel.isSelectedIndex(counter)) {
						if(counter == min) {
						    clearSelection();
						}else {
						    Array newSel =new Array(counter -min );
						    Array selectionIndex =rowMapper.getRowsForPaths(selection );
						    // find the actual selection pathes corresponded to the
						    // rows of the new selection
						    for(int i =0;i <selectionIndex.length ;i ++){
								if (selectionIndex.get(i)<counter) {
								    newSel.get(selectionIndex.put(i)-min,  selection.get(i));
								}
						    }
						    setSelectionPaths(newSel);
						    break;
						}
				    }
				}
		    }
		}else if(selectionMode == SINGLE_TREE_SELECTION &&
			selection != null && selection.length > 1) {
		    setSelectionPath(selection.get(0));
		}
    }

    /**
     * Returns true if the paths() are contiguous,
     * or this object has no RowMapper.
     */
    private boolean  arePathsContiguous (Array paths ){
		if(rowMapper == null || paths.length < 2){
		    return true;
		}else {
		    Array bitSet =new Array(32);
		    int anIndex ,counter int ,min ;
		    int pathCount =paths.length ;
		    int validCount =0;
		    Array tempPath =.get(paths.get(0)) ;

		    min = rowMapper.getRowsForPaths(tempPath).get(0);
		    for(counter = 0; counter < pathCount; counter++) {
				if(paths.get(counter) != null) {
				    tempPath.put(0,  paths.get(counter));
				    Array rows =rowMapper.getRowsForPaths(tempPath );
				    if (rows == null) {
						return false;
				    }
				    anIndex = rows.get(0);
				    if(anIndex == -1 || anIndex < (min - pathCount) || anIndex > (min + pathCount)){
						return false;
				    }
				    if(anIndex < min){
						min = anIndex;
				    }
				    if(!(bitSet.get(anIndex) == true)) {
						bitSet.put(anIndex,  true);
						validCount++;
				    }
				}
		    }
		    int maxCounter =validCount +min ;

		    for(counter = min; counter < maxCounter; counter++){
				if(!(bitSet.get(counter) == true)){
				    return false;
				}
		    }
		}
		return true;
    }

    /**
     * Used to test if a particular set of <code>TreePath</code>s can
     * be added. This will return true if <code>paths</code> is null (or
     * empty), or this object has no RowMapper, or nothing is currently selected,
     * or the selection mode is <code>DISCONTIGUOUS_TREE_SELECTION</code>, or
     * adding the paths to the current selection still results in a
     * contiguous set of <code>TreePath</code>s.
     */
    private boolean  canPathsBeAdded (Array paths ){
		if(paths == null || paths.length == 0 || rowMapper == null ||
		   selection == null || selectionMode == DISCONTIGUOUS_TREE_SELECTION){
		    return true;
		}else {
		    Array bitSet =new Array(32);
		    DefaultListSelectionModel lModel =listSelectionModel ;
		    int anIndex ;
		    int counter ;
		    int min =lModel.getMinSelectionIndex ();
		    int max =lModel.getMaxSelectionIndex ();
		    Array tempPath =new Array(1);

		    if(min != -1) {
				for(counter = min; counter <= max; counter++) {
				    if(lModel.isSelectedIndex(counter)){
						bitSet.put(counter,  true);
				    }
				}
		    }else {
				tempPath.put(0,  paths.get(0));
				min = max = rowMapper.getRowsForPaths(tempPath).get(0);
		    }
		    for(counter = paths.length - 1; counter >= 0; counter--) {
				if(paths.get(counter) != null) {
				    tempPath.put(0,  paths.get(counter));
				    Array rows =rowMapper.getRowsForPaths(tempPath );
				    if (rows == null) {
						return false;
				    }
				    anIndex = rows.get(0);
				    min = Math.min(anIndex, min);
				    max = Math.max(anIndex, max);
				    if(anIndex == -1){
						return false;
				    }
				    bitSet.put(anIndex,  true);
				}
		    }
		    for(counter = min; counter <= max; counter++){
				if(!(bitSet.get(counter) == true)){
				    return false;
				}
		    }
		}
		return true;
    }

    /**
     * Returns true if the paths can be removed without breaking the
     * continuity of the model.
     * This is rather expensive.
     */
    private boolean  canPathsBeRemoved (Array paths ){
		if(rowMapper == null || selection == null || selectionMode == DISCONTIGUOUS_TREE_SELECTION){
		    return true;
		}else {
		    Array bitSet =new Array ();
		    int counter ;
		    int pathCount =paths.length ;
		    int min =-1;
		    int validCount =0;
		    Array tempPath =new Array(1);
		    Array rows ;

		    /* Determine the rows for the removed entries. */
		    lastPaths.clear();
		    for (counter = 0; counter < pathCount; counter++) {
				if (paths.get(counter) != null) {
				    lastPaths.put(paths.get(counter), true);
				}
		    }
		    for(counter = selection.length - 1; counter >= 0; counter--) {
				if(lastPaths.get(selection.get(counter)) == null) {
				    tempPath.put(0,  selection.get(counter));
				    rows = rowMapper.getRowsForPaths(tempPath);
				    if(rows != null && rows[0] != -1 && !(bitSet.get(rows.get(0)) == true)) {
						validCount++;
						if(min == -1)
						    min = rows.get(0);
						else
						    min = Math.min(min, rows.get(0));
						bitSet.get(rows.put(0),  true);
				    }
				}
		    }
		    lastPaths.clear();
		    /* Make sure they are contiguous. */
		    if(validCount > 1) {
				for(counter = min + validCount - 1; counter >= min; counter--){
				    if(!(bitSet.get(counter) == true)){
						return false;
				    }
				}
		    }
		}
		return true;
    }

    /**
      * Notifies listeners of a change in path. changePaths should contain
      * instances of PathPlaceHolder.
      */
    private void  notifyPathChange (ArrayList changedPaths ,TreePath oldLeadSelection ,boolean programmatic ){
		int cPathCount =changedPaths.size ();
		Array newness =new Array(cPathCount );//boolean[]
		Array paths =new Array(cPathCount );//TreePath[]
		PathPlaceHolder placeholder ;

		for(int counter =0;counter <cPathCount ;counter ++){
		    placeholder = PathPlaceHolder(changedPaths.get(counter));
		    newness.put(counter,  placeholder.isNew);
		    paths.put(counter,  placeholder.path);
		}

		TreeSelectionEvent event =new TreeSelectionEvent(this ,programmatic ,paths ,newness ,oldLeadSelection ,leadPath );

		fireValueChanged(event);
    }

    /**
     * Updates the leadIndex instance variable.
     */
    private void  updateLeadIndex (){
		if(leadPath != null) {
		    if(selection == null) {
				leadPath = null;
				leadIndex = leadRow = -1;
		    } else {
				leadRow = leadIndex = -1;
				for(int counter =selection.length -1;counter >=0;counter --){
				    // Can use == here since we know leadPath came from
				    // selection
				    if(selection.get(counter) == leadPath) {
						leadIndex = counter;
						break;
				    }
				}
		    }
		}else {
		    leadIndex = -1;
		}
    }

    /**
     * This method is obsolete and its implementation is now a noop.  It's
     * still called by setSelectionPaths and addSelectionPaths, but only
     * for backwards compatability.
     */
    private void  insureUniqueness (){
    }


    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * @return a String representation of this object
     */
     public String  toString (){
		return "DefaultTreeSelectionModel.get(" + getSelectionPaths() + ")";
    }
}


