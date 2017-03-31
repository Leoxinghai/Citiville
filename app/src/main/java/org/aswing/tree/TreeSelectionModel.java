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


import org.aswing.tree.RowMapper;
import org.aswing.tree.TreePath;

/**
  * This interface represents the current state of the selection for
  * the tree component.
  * For information and examples of using tree selection models,
  * see <a href="http://java.sun.com/docs/books/tutorial/uiswing/components/tree.html">How to Use Trees</a>
  * in <em>The Java Tutorial.</em>
  *
  * <p>
  * The state of the tree selection is characterized by
  * a set of TreePaths, and optionally a set of integers. The mapping
  * from TreePath to integer is done by way of an instance of RowMapper.
  * It is not necessary for a TreeSelectionModel to have a RowMapper to
  * correctly operate, but without a RowMapper <code>getSelectionRows</code>
  * will return null.
  *
  * <p>
  * 
  * A TreeSelectionModel can be configured to allow only one
  * path (<code>SINGLE_TREE_SELECTION</code>) a number of
  * continguous paths (<code>CONTIGUOUS_TREE_SELECTION</code>) or a number of
  * discontiguous paths (<code>DISCONTIGUOUS_TREE_SELECTION</code>).
  * A <code>RowMapper</code> is used to determine if TreePaths are
  * contiguous.
  * In the absence of a RowMapper <code>CONTIGUOUS_TREE_SELECTION</code> and
  * <code>DISCONTIGUOUS_TREE_SELECTION</code> behave the same, that is they
  * allow any number of paths to be contained in the TreeSelectionModel.
  *
  * <p>
  * 
  * For a selection model of <code>CONTIGUOUS_TREE_SELECTION</code> any
  * time the paths are changed (<code>setSelectionPath</code>,
  * <code>addSelectionPath</code> ...) the TreePaths are again checked to
  * make they are contiguous. A check of the TreePaths can also be forced
  * by invoking <code>resetRowSelection</code>. How a set of discontiguous
  * TreePaths is mapped to a contiguous set is left to implementors of
  * this interface to enforce a particular policy.
  *
  * <p>
  *
  * Implementations should combine duplicate TreePaths that are
  * added to the selection. For example, the following code
  * <pre>
  *Array paths =.get( treePath ,treePath ) ;
  *   treeSelectionModel.setSelectionPaths(paths);
  * </pre>
  * should result in only one path being selected:
  * <code>treePath</code>, and
  * not two copies of <code>treePath</code>.
  *
  * <p>
  *
  * The lead TreePath is the last path that was added (or set). The lead
  * row is then the row that corresponds to the TreePath as determined
  * from the RowMapper.
  * <p>
 * (Fully quoted from java swing's tree doc)
  *
 * @author iiley
 */
public interface TreeSelectionModel{
	 
	 /**
     * Sets the selection model, which must be one of SINGLE_TREE_SELECTION,
     * CONTIGUOUS_TREE_SELECTION or DISCONTIGUOUS_TREE_SELECTION.
     * <p>
     * This may change the selection if the current selection is not valid
     * for the new mode. For example, if three TreePaths are
     * selected when the mode is changed to <code>SINGLE_TREE_SELECTION</code>,
     * only one TreePath will remain selected. It is up to the particular
     * implementation to decide what TreePath remains selected.
     */
    void  setSelectionMode (int mode );

    /**
     * Returns the current selection mode, one of
     * <code>SINGLE_TREE_SELECTION</code>,
     * <code>CONTIGUOUS_TREE_SELECTION</code> or
     * <code>DISCONTIGUOUS_TREE_SELECTION</code>.
     */
    int  getSelectionMode ();

    /**
      * Sets the selection to path. If this represents a change, then
      * the TreeSelectionListeners are notified. If <code>path</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      *
      * @param path new path to select.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  setSelectionPath (TreePath path ,boolean programmatic =true );

    /**
      * Sets the selection to path(TreePath[]) . If this represents a change, then
      * the TreeSelectionListeners are notified. If <code>paths</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      *
      * @param paths new selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  setSelectionPaths (Array paths ,boolean programmatic =true );

    /**
      * Adds path to the current selection. If path is not currently
      * in the selection the TreeSelectionListeners are notified. This has
      * no effect if <code>path</code> is null.
      *
      * @param path the new path to add to the current selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  addSelectionPath (TreePath path ,boolean programmatic =true );

    /**
      * Adds paths(TreePath[]) to the current selection.  If any of the paths in
      * paths are not currently in the selection the TreeSelectionListeners
      * are notified. This has
      * no effect if <code>paths</code> is null.
      *
      * @param paths the new paths to add to the current selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  addSelectionPaths (Array paths ,boolean programmatic =true );

    /**
      * Removes path from the selection. If path is in the selection
      * The TreeSelectionListeners are notified. This has no effect if
      * <code>path</code> is null.
      *
      * @param path the path to remove from the selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  removeSelectionPath (TreePath path ,boolean programmatic =true );

    /**
      * Removes paths(TreePath[]) from the selection.  If any of the paths in 
      * <code>paths</code>
      * are in the selection, the TreeSelectionListeners are notified.
      * This method has no effect if <code>paths</code> is null.
      *
      * @param paths the path to remove from the selection.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  removeSelectionPaths (Array paths ,boolean programmatic =true );

    /**
      * Returns the first path in the selection. How first is defined is
      * up to implementors, and may not necessarily be the TreePath with
      * the smallest integer value as determined from the
      * <code>RowMapper</code>.
      */
    TreePath  getSelectionPath ();

    /**
      * Returns the paths(TreePath[]) in the selection. This will return null (or an
      * empty array) if nothing is currently selected.
      */
    Array  getSelectionPaths ();

    /**
     * Returns the number of paths that are selected.
     */
    int  getSelectionCount ();

    /**
      * Returns true if the path, <code>path</code>, is in the current
      * selection.
      */
    boolean  isPathSelected (TreePath path );

    /**
      * Returns true if the selection is currently empty.
      */
    boolean  isSelectionEmpty ();

    /**
      * Empties the current selection.  If this represents a change in the
      * current selection, the selection listeners are notified.
      * @param programmatic indicate if this is a programmatic change.
      */
    void  clearSelection (boolean programmatic =true );

    /**
     * Sets the RowMapper instance. This instance is used to determine
     * the row for a particular TreePath.
     */
    void  setRowMapper (RowMapper newMapper );

    /**
     * Returns the RowMapper instance that is able to map a TreePath to a
     * row.
     */
    RowMapper  getRowMapper ();

    /**
      * Returns all of the currently selected rows. This will return
      * null (or an empty array) if there are no selected TreePaths or
      * a RowMapper has not been set.
      */
    Array  getSelectionRows ();

    /**
     * Returns the smallest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
      */
    int  getMinSelectionRow ();

    /**
     * Returns the largest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
      */
    int  getMaxSelectionRow ();

    /**
      * Returns true if the row identified by <code>row</code> is selected.
      */
    boolean  isRowSelected (int row );

    /**
     * Updates this object's mapping from TreePaths to rows. This should
     * be invoked when the mapping from TreePaths to integers has changed
     * (for example, a node has been expanded).
     * <p>
     * You do not normally have to call this; JTree and its associated
     * listeners will invoke this for you. If you are implementing your own
     * view class, then you will have to invoke this.
     */
    void  resetRowSelection ();

    /**
     * Returns the lead selection index. That is the last index that was
     * added.
     */
    int  getLeadSelectionRow ();

    /**
     * Returns the last path that was added. This may differ from the 
     * leadSelectionPath property maintained by the JTree.
     */
    TreePath  getLeadSelectionPath ();

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
    void  addPropertyChangeListener (Function listener ,int priority =0,boolean useWeakReference =false );
	
	/**
	 * Removed a propertyChangeListener.
	 * @param listener the listener to be removed.
	 */
	void  removePropertyChangeListener (Function listener );
	
    /**
      * Adds x to the list of listeners that are notified each time the
      * set of selected TreePaths changes.
      *
      * @param listener the new listener to be added
	  * @param priority the priority
	  * @param useWeakReference Determines whether the reference to the listener is strong or weak.
      * @see org.aswing.JTree#ON_SELECTION_CHANGED
      */
    void  addTreeSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false );
    
	/**
	 * Removed a treeSelectionListener.
	 * @param listener the listener to be removed.
	 */    
    void  removeTreeSelectionListener (Function listener );
}


