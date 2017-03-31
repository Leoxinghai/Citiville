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


import org.aswing.event.CellEditorListener;
import org.aswing.event.InteractiveEvent;
import org.aswing.event.PropertyChangeEvent;
import org.aswing.event.TreeCellEditEvent;
import org.aswing.event.TreeEvent;
import org.aswing.event.TreeModelEvent;
import org.aswing.event.TreeModelListener;
import org.aswing.event.TreeSelectionEvent;
import org.aswing.geom.IntDimension;
import org.aswing.geom.IntPoint;
import org.aswing.geom.IntRectangle;
import org.aswing.plaf.ComponentUI;
import org.aswing.plaf.TreeUI;
import org.aswing.plaf.basic.BasicTreeUI;
import org.aswing.tree.DefaultMutableTreeNode;
import org.aswing.tree.DefaultTreeCell;
import org.aswing.tree.DefaultTreeModel;
import org.aswing.tree.DefaultTreeSelectionModel;
import org.aswing.tree.EmptySelectionModel;
import org.aswing.tree.GeneralTreeCellFactoryUIResource;
import org.aswing.tree.TreeCellEditor;
import org.aswing.tree.TreeCellFactory;
import org.aswing.tree.TreeModel;
import org.aswing.tree.TreePath;
import org.aswing.tree.TreePathMap;
import org.aswing.tree.TreeSelectionModel;
import org.aswing.util.Stack;
import org.aswing.util.ArrayList;

/**
 * Dispatched when a property changed.
 * @eventType org.aswing.event.PropertyChangeEvent.PROPERTY_CHANGE
 */
.get(Event(name="propertyChange", type="org.aswing.event.PropertyChangeEvent"))

/**
 * Dispatched when tree items selection changed.
 * @eventType org.aswing.event.TreeSelectionEvent.TREE_SELECTION_CHANGED
 */
.get(Event(name="treeSelectionChanged", type="org.aswing.event.TreeSelectionEvent"))


/**
 * Dispatched when an item in the tree has been expanded.
 * @eventType org.aswing.event.TreeEvent.TREE_EXPANDED
 */
.get(Event(name="treeExpanded", type="org.aswing.event.TreeEvent"))
/**
 * Dispatched when an item in the tree has been collapsed.
 * @eventType org.aswing.event.TreeEvent.TREE_COLLAPSED
 */
.get(Event(name="treeCollapsed", type="org.aswing.event.TreeEvent"))
/**
 * Dispatched whenever a node in the tree is about to be expanded.<br>
 *
 * You can throw a <code>ExpandVetoException</code> in this event handler to indicate that this
 * action is veto.
 * @eventType org.aswing.event.TreeEvent.TREE_WILL_EXPAND
 */
.get(Event(name="treeWillExpand", type="org.aswing.event.TreeEvent"))
/**
 * Dispatched whenever a node in the tree is about to be expanded.<br>
 *
 * You can throw a <code>ExpandVetoException</code> in this event handler to indicate that this
 * action is veto.
 * @eventType org.aswing.event.TreeEvent.TREE_WILL_COLLAPSE
 */
.get(Event(name="treeWillCollapse", type="org.aswing.event.TreeEvent"))


/**
 * Dispatched when the cell editing started.
 * @eventType org.aswing.event.TreeCellEditEvent.EDITING_STARTED
 */
.get(Event(name="treeCellEditingStarted", type="org.aswing.event.TreeCellEditEvent"))

/**
 * Dispatched when the cell editing canceled.
 * @eventType org.aswing.event.TreeCellEditEvent.EDITING_CANCELED
 */
.get(Event(name="treeCellEditingCanceled", type="org.aswing.event.TreeCellEditEvent"))

/**
 * Dispatched when the cell editing finished.
 * @eventType org.aswing.event.TreeCellEditEvent.EDITING_STOPPED
 */
.get(Event(name="treeCellEditingStopped", type="org.aswing.event.TreeCellEditEvent"))


/**
 * A control that displays a set of hierarchical data as an outline.
 * <a name="jtree_description">
 * A control that displays a set of hierarchical data as an outline.
 * You can find task-oriented documentation and examples of using trees in
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/components/tree.html">How to Use Trees</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * A specific node in a tree can be identified either by a
 * <code>TreePath</code> (an object
 * that encapsulates a node and all of its ancestors), or by its
 * display row, where each row in the display area displays one node.
 * An <i>expanded</i> node is a non-leaf node (as identified by
 * <code>TreeModel.isLeaf(node)</code> returning false) that will displays
 * its children when all its ancestors are <i>expanded</i>.
 * A <i>collapsed</i>
 * node is one which hides them. A <i>hidden</i> node is one which is
 * under a collapsed ancestor. All of a <i>viewable</i> nodes parents
 * are expanded, but may or may not be displayed. A <i>displayed</i> node
 * is both viewable and in the display area, where it can be seen.
 * </p>
 * The following <code>JTree</code> methods use "visible" to mean "displayed":
 * <ul>
 * <li><code>isRootVisible()</code>
 * <li><code>setRootVisible()</code>
 * <li><code>scrollPathToVisible()</code>
 * <li><code>scrollRowToVisible()</code>
 * <li><code>getVisibleRowCount()</code>
 * <li><code>setVisibleRowCount()</code>
 * </ul>
 * </p>
 * The next group of <code>JTree</code> methods use "visible" to mean
 * "viewable" (under an expanded parent):
 * <ul>
 * <li><code>isPathVisible()</code>
 * <li><code>makePathVisible()</code>
 * </ul>
 * <p>
 * If you are interested in knowing when the selection listen the
 * the <code>TreeSelectionEvent.TREE_SELECTION_CHANGED</code> event.
 * </p>
 * If you are interested in detecting either double-click events or when
 * a user clicks on a node, regardless of whether or not it was selected,
 * we recommend you do the following:
 * <listing>
 *     yourTree.addEventListener(MouseEvent.MOUSE_DOWN, __onPressed);
 *     ....
 *
 *public void  __onPressed (Event e ){
 *TreePath selPath =tree.getMousePointedPath ();
 *         ....
 *     }
 * };
 * </listing>
 * <p>
 * To use <code>JTree</code> to display compound nodes
 * (for example, nodes containing both
 * a graphic icon and text), subclass {@link TreeCell} and {@link TreeCellFactory} and use
 * {@link #setCellFactory} to tell the tree to use it. To edit such nodes,
 * subclass {@link TreeCellEditor} and use {@link #setCellEditor}.
 * </p>
 * For a big model tree, there's two way to speed up the performance.
 * <br>
 * call JTree.setFixedCellWidth(width:int) to fix the cell width.
 *
 * @see TreeModel
 * @author iiley
 */
public class JTree extends Container implements Viewportable, TreeModelListener, CellEditorListener{

    /** Selection can only contain one path at a time. */
    public static  int SINGLE_TREE_SELECTION =DefaultTreeSelectionModel.SINGLE_TREE_SELECTION ;

    /** Selection can only be contiguous. This will only be enforced if
     * a RowMapper instance is provided. That is, if no RowMapper is set
     * this behaves the same as DISCONTIGUOUS_TREE_SELECTION. */
    public static  int CONTIGUOUS_TREE_SELECTION =DefaultTreeSelectionModel.CONTIGUOUS_TREE_SELECTION ;

    /** Selection can contain any number of items that are not necessarily
     * contiguous. */
    public static  int DISCONTIGUOUS_TREE_SELECTION =DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION ;

 	/**
 	 * The default unit/block increment, it means auto count a value.
 	 */
 	public static  int AUTO_INCREMENT =int.MIN_VALUE ;

    /**
     * The model that defines the tree displayed by this object.
     */
    private TreeModel treeModel ;

    /**
     * Models the set of selected nodes in this tree.
     */
    private TreeSelectionModel selectionModel ;

    /**
     * True if the root node is displayed, false if its children are
     * the highest visible nodes.
     */
    private boolean rootVisible ;

    /**
     * The cell used to draw nodes. If <code>null</code>, the UI uses a default
     * <code>cellRenderer</code>.
     */
    private TreeCellFactory cellFactory ;

    /**
     * Height to use for each display row. If this is <= 0 the renderer
     * determines the height for each row.
     */
    private int rowHeight ;
    private boolean rowHeightSet ;

    private int fixedCellWidth ;

	private ASColor selectionForeground ;
	private ASColor selectionBackground ;

    /**
     * Maps from <code>TreePath</code> to <code>Boolean</code>
     * indicating whether or not the
     * particular path is expanded. This ONLY indicates whether a
     * given path is expanded, and NOT if it is visible or not. That
     * information must be determined by visiting all the parent
     * paths and seeing if they are visible.
     */
    private TreePathMap expandedState ;

    /**
     * Editor for the entries.  Default is <code>null</code>
     * (tree is not editable).
     */
    private TreeCellEditor cellEditor ;

    /**
     * Is the tree editable? Default is false.
     */
    private boolean editable ;

    /**
     * Number of rows to make visible at one time. This value is used for
     * the <code>Scrollable</code> interface. It determines the preferred
     * size of the display area.
     */
    private int visibleRowCount ;

    /**
     * If true, when editing is to be stopped by way of selection changing,
     * data in tree changing or other means <code>stopCellEditing</code>
     * is invoked, and changes are saved. If false,
     * <code>cancelCellEditing</code> is invoked, and changes
     * are discarded. Default is false.
     */
    private boolean invokesStopCellEditing ;

    /**
     * If true, when a node is expanded, as many of the descendants are
     * scrolled to be visible.
     */
    private boolean scrollsOnExpand ;
    private boolean scrollsOnExpandSet ;

    /**
     * Number of mouse clicks before a node is expanded.
     */
    private int toggleClickCount ;

    /**
     * Used when <code>setExpandedState</code> is invoked,
     * will be a <code>Stack</code> of <code>Stack</code>s.
     */
    private Stack expandedStack ;

    /**
     * Lead selection path, may not be <code>null</code>.
     */
    private TreePath leadPath ;

    /**
     * Anchor path.
     */
    private TreePath anchorPath ;

    /**
     * True if paths in the selection should be expanded.
     */
    private boolean expandsSelectedPaths ;

    /**
     * This is set to true for the life of the <code>setUI</code> call.
     */
    private boolean settingUI ;

	private IntPoint viewPosition ;
	private int verticalUnitIncrement ;
	private int verticalBlockIncrement ;
	private int horizontalUnitIncrement ;
	private int horizontalBlockIncrement ;

    /**
     * Max number of stacks to keep around.
     */
    private static int TEMP_STACK_SIZE =11;

    //
    // Bound property names
    //
    /** Bound property name for <code>cellFactory</code>. */
    public static  String CELL_FACTORY_PROPERTY ="cellFactory";
    /** Bound property name for <code>treeModel</code>. */
    public static  String TREE_MODEL_PROPERTY ="model";
    /** Bound property name for <code>rootVisible</code>. */
    public static  String ROOT_VISIBLE_PROPERTY ="rootVisible";
    /** Bound property name for <code>showsRootHandles</code>. */
    /** Bound property name for <code>rowHeight</code>. */
    public static  String ROW_HEIGHT_PROPERTY ="rowHeight";
    /** Bound property name for <code>cellEditor</code>. */
    public static  String CELL_EDITOR_PROPERTY ="cellEditor";
    /** Bound property name for <code>editable</code>. */
    public static  String EDITABLE_PROPERTY ="editable";
    /** Bound property name for selectionModel. */
    public static  String SELECTION_MODEL_PROPERTY ="selectionModel";
    /** Bound property name for <code>visibleRowCount</code>. */
    public static  String VISIBLE_ROW_COUNT_PROPERTY ="visibleRowCount";
    /** Bound property name for <code>messagesStopCellEditing</code>. */
    public static  String INVOKES_STOP_CELL_EDITING_PROPERTY ="invokesStopCellEditing";
    /** Bound property name for <code>scrollsOnExpand</code>. */
    public static  String SCROLLS_ON_EXPAND_PROPERTY ="scrollsOnExpand";
    /** Bound property name for <code>toggleClickCount</code>. */
    public static  String TOGGLE_CLICK_COUNT_PROPERTY ="toggleClickCount";
    /** Bound property name for <code>leadSelectionPath</code>. */
    public static  String LEAD_SELECTION_PATH_PROPERTY ="leadSelectionPath";
    /** Bound property name for anchor selection path. */
    public static  String ANCHOR_SELECTION_PATH_PROPERTY ="anchorSelectionPath";
    /** Bound property name for expands selected paths property */
    public static  String EXPANDS_SELECTED_PATHS_PROPERTY ="expandsSelectedPaths";
    /** Bound property name for font property  */
    public static  String FONT_PROPERTY ="font";

    /**
     * Creates and returns a sample <code>TreeModel</code>.
     * Used primarily for beanbuilders to show something interesting.
     *
     * @return the default <code>TreeModel</code>
     */
    private static TreeModel  getDefaultTreeModel (){
        DefaultMutableTreeNode root =new DefaultMutableTreeNode("JTree");
		DefaultMutableTreeNode parent ;

		parent = new DefaultMutableTreeNode("colors");
		root.append(parent);
		parent.append(new DefaultMutableTreeNode("blue"));
		parent.append(new DefaultMutableTreeNode("violet"));
		parent.append(new DefaultMutableTreeNode("red"));
		parent.append(new DefaultMutableTreeNode("yellow"));

		parent = new DefaultMutableTreeNode("sports");
		root.append(parent);
		parent.append(new DefaultMutableTreeNode("basketball"));
		parent.append(new DefaultMutableTreeNode("soccer"));
		parent.append(new DefaultMutableTreeNode("football"));
		parent.append(new DefaultMutableTreeNode("hockey"));

		parent = new DefaultMutableTreeNode("food");
		root.append(parent);
		parent.append(new DefaultMutableTreeNode("hot dogs"));
		parent.append(new DefaultMutableTreeNode("pizza"));
		parent.append(new DefaultMutableTreeNode("ravioli"));
		parent.append(new DefaultMutableTreeNode("bananas"));
		root.append(parent);
        return new DefaultTreeModel(root);
    }

    /**
     * JTree(newModel:TreeModel) <br>;
     * JTree() a defaul model will be created<br>
     * Returns an instance of <code>JTree</code> which displays the root node
     * -- the tree is created using the specified data model.
     *
     * @param newModel  (optional)the <code>TreeModel</code> to use as the data model. If miss
     * 					it, a defaul model will be created.
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public  JTree (TreeModel newModel =null ){
        super();
        setName("JTree");

		verticalUnitIncrement = AUTO_INCREMENT;
		verticalBlockIncrement = AUTO_INCREMENT;
		horizontalUnitIncrement = AUTO_INCREMENT;
		horizontalBlockIncrement = AUTO_INCREMENT;

        if(newModel == null) newModel = getDefaultTreeModel();
        viewPosition = new IntPoint();
		expandedStack = new Stack();
		toggleClickCount = 3;
		expandedState = new TreePathMap();
        rowHeight = 16;
        rowHeightSet = false;
        fixedCellWidth = -1;
        visibleRowCount = 16;
        rootVisible = true;
        editable = false;
        setSelectionModel(new DefaultTreeSelectionModel());
        cellFactory = null;
		scrollsOnExpand = true;
		scrollsOnExpandSet = false;
		expandsSelectedPaths = false;
		setCellFactory(new GeneralTreeCellFactoryUIResource(DefaultTreeCell));
        setCellEditor(new DefaultTextFieldCellEditor());
        setModel(newModel);
        updateUI();
    }

    /**
     * Returns the L&F object that renders this component.
     *
     * @return the <code>TreeUI</code> object that renders this component
     */
    public TreeUI  getTreeUI (){
        return (TreeUI)ui;
    }

    /**
     * Sets the L&F object that renders this component.
     *
     * @param ui  the <code>TreeUI</code> L&F object
     * @see UIDefaults#getUI()
     */
     public void  setUI (ComponentUI ui ){
	    settingUI = true;
	    //TODO check if need add expand/collpase listener to first
		super.setUI(ui);
		settingUI = false;
    }

    /**
     * Notification from the <code>UIManager</code> that the L&F has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * @see JComponent#updateUI
     */
     public void  updateUI (){
        setUI(UIManager.getUI(this));
        invalidate();
    }

     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicTreeUI;
    }


    /**
     * Returns the name of the L&F class that renders this component.
     *
     * @return the string "TreeUI"
     * @see org.aswing.Component#getUIClassID()
     * @see org.aswing.UIDefaults#getUI()
     */
     public String  getUIClassID (){
        return "TreeUI";
    }

	/**
	 * You can not set layout to JTree, it's handled by TreeUI.
	 * @throws Error when set any layout.
	 */
	 public void  setLayout (LayoutManager layout ){
		throw new Error("You can not set layout to JTree, it's handled by TreeUI");
	}

    /**
     * Returns the current <code>TreeCellFactory</code>
     *  that is rendering each cell.
     *
     * @return the <code>TreeCellFactory</code> that is rendering each cell
     */
    public TreeCellFactory  getCellFactory (){
        return cellFactory;
    }

    /**
     * Sets the <code>TreeCellFactory</code> that will be used to
     * draw each cell.
     *
     * @param x  the <code>TreeCellFactory</code> that is to render each cell
     */
    public void  setCellFactory (TreeCellFactory x ){
        TreeCellFactory oldValue =cellFactory ;
		if(cellFactory != x){
	        cellFactory = x;
	        firePropertyChange(CELL_FACTORY_PROPERTY, oldValue, cellFactory);
	        invalidate();
		}
    }

     public void  setFont (ASFont f ){
    	ASFont old =getFont ();
    	if(old != f){
	    	super.setFont(f);
	    	firePropertyChange(FONT_PROPERTY, old, getFont());
    	}
    }

    /**
      * Determines whether the tree is editable. Fires a property
      * change event if the new setting is different from the existing
      * setting.
      *
      * @param flag  a boolean value, true if the tree is editable
      */
    public void  setEditable (boolean flag ){
        boolean oldValue =editable ;
		if(editable != flag){
	        editable = flag;
	        firePropertyChange(EDITABLE_PROPERTY, oldValue, flag);
		}
    }

    /**
     * Returns true if the tree is editable.
     *
     * @return true if the tree is editable
     */
    public boolean  isEditable (){
        return editable;
    }

    /**
     * Sets the cell editor.  A <code>null</code> value implies that the
     * tree cannot be edited.  If this represents a change in the
     * <code>cellEditor</code>, the <code>propertyChange</code>
     * method is invoked on all listeners.
     *
     * @param cellEditor the <code>TreeCellEditor</code> to use
     */
    public void  setCellEditor (TreeCellEditor cellEditor ){
        TreeCellEditor oldEditor =this.cellEditor ;
		if(oldEditor != cellEditor){
			if(oldEditor != null){
				oldEditor.removeCellEditorListener(this);
			}
	        this.cellEditor = cellEditor;
	        cellEditor.addCellEditorListener(this);
	        firePropertyChange(CELL_EDITOR_PROPERTY, oldEditor, cellEditor);
	        invalidate();
		}
    }

    /**
     * Returns the editor used to edit entries in the tree.
     *
     * @return the <code>TreeCellEditor</code> in use,
     *		or <code>null</code> if the tree cannot be edited
     */
    public TreeCellEditor  getCellEditor (){
        return cellEditor;
    }

    /**
     * Returns the <code>TreeModel</code> that is providing the data.
     *
     * @return the <code>TreeModel</code> that is providing the data
     */
    public TreeModel  getModel (){
        return treeModel;
    }

    /**
     * Sets the <code>TreeModel</code> that will provide the data.
     *
     * @param newModel the <code>TreeModel</code> that is to provide the data
     */
    public void  setModel (TreeModel newModel ){
    	if(treeModel == newModel){
    		return;
    	}
        clearSelection();

        TreeModel oldModel =treeModel ;

		if(treeModel != null)
		    treeModel.removeTreeModelListener(this);

        treeModel = newModel;
		clearToggledPaths();
		if(treeModel != null) {
			treeModel.addTreeModelListener(this);
		    // Mark the root as expanded, if it isn't a leaf.
		    if(treeModel.getRoot() != null && !treeModel.isLeaf(treeModel.getRoot())) {
				expandedState.put(new TreePath(.get(treeModel.getRoot())), true);
	        }
		}
        firePropertyChange(TREE_MODEL_PROPERTY, oldModel, treeModel);
        invalidate();
    }

    /**
     * Returns true if the root node of the tree is displayed.
     *
     * @return true if the root node of the tree is displayed
     */
    public boolean  isRootVisible (){
        return rootVisible;
    }

    /**
     * Determines whether or not the root node from
     * the <code>TreeModel</code> is visible.
     *
     * @param rootVisible true if the root node of the tree is to be displayed
     */
    public void  setRootVisible (boolean rootVisible ){
        boolean oldValue =this.rootVisible ;
		if(oldValue != rootVisible){
	        this.rootVisible = rootVisible;
	        firePropertyChange(ROOT_VISIBLE_PROPERTY, oldValue, this.rootVisible);
		}
    }

    /**
     * Sets the height of each cell, in pixels.  If the specified value
     * is less than or equal to zero nothing will changed.(the current cell height will not change.)
     * @param rowHeight the height of each cell, in pixels
     */
    public void  setRowHeight (int rowHeight ){
    	if(rowHeight <= 0 || this.rowHeight == rowHeight){
    		return;
    	}
        int oldValue =this.rowHeight ;

        this.rowHeight = rowHeight;
		rowHeightSet = true;
        firePropertyChange(ROW_HEIGHT_PROPERTY, oldValue, this.rowHeight);
        invalidate();
    }

    /**
     * Returns the height of each row. Default is 16.
     *
     */
    public int  getRowHeight (){
        return rowHeight;
    }

    /**
     * Sets whether or not row height is set by user.
     * If it is set by user, the LAF will not change the row height value.
     * @param b set or not
     */
	public void  setRowHeightSet (boolean b ){
		this.rowHeightSet = b;
	}

	/**
	 * Returns whether or not row height is set by user.
     * If it is set by user, the LAF will not change the row height value.
	 * @return set or not
	 */
	public boolean  isRowHeightSet (){
		return rowHeightSet;
	}

    /**
     * Returns the fixed cell width value -- the value specified by setting
     * the <code>fixedCellWidth</code> property, rather than that calculated
     * from the cell elements.
     *
     * @return the fixed cell width
     * @see #setFixedCellWidth()
     * @see #getRowHeight()
     */
    public int  getFixedCellWidth (){
        return fixedCellWidth;
    }

    /**
     * Sets the width of every cell in the list.  If <code>width</code> is -1,
     * cell widths are computed by applying <code>getPreferredSize</code>
     * to the <code>TreeCell</code> component for each list element.
     * <p>
     * The default value of this property is -1.<br>
     * If your tree model is very large or all of your tree item has same width,
     * i recommend you set a fixed cell with it will speed up the performance.
     * <p>
     *
     * @param width   the width, in pixels, for all cells in this list
     * @see #setFixedCellWidth()
     * @see #setRowHeight()
     */
    public void  setFixedCellWidth (int width ){
    	if(width != fixedCellWidth){
	        fixedCellWidth = width;
	        treeDidChange();
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
		if (! selectionBackground.equals(old)){
			repaint();
			revalidate();
		}
	}

    /**
     * Determines what happens when editing is interrupted by selecting
     * another node in the tree, a change in the tree's data, or by some
     * other means. Setting this property to <code>true</code> causes the
     * changes to be automatically saved when editing is interrupted.
     * <p>
     * Fires a property change for the INVOKES_STOP_CELL_EDITING_PROPERTY.
     *
     * @param newValue true means that <code>stopCellEditing</code> is invoked
     *        when editing is interrupted, and data is saved; false means that
     *        <code>cancelCellEditing</code> is invoked, and changes are lost
     */
    public void  setInvokesStopCellEditing (boolean newValue ){
        boolean oldValue =invokesStopCellEditing ;
		if(oldValue != newValue){
	        invokesStopCellEditing = newValue;
	        firePropertyChange(INVOKES_STOP_CELL_EDITING_PROPERTY, oldValue, newValue);
		}
    }

    /**
     * Returns the indicator that tells what happens when editing is
     * interrupted.
     *
     * @return the indicator that tells what happens when editing is
     *         interrupted
     * @see #setInvokesStopCellEditing()
     */
    public boolean  isInvokesStopCellEditing (){
        return invokesStopCellEditing;
    }

    /**
     * Sets the <code>scrollsOnExpand</code> property,
     * which determines whether the
     * tree might scroll to show previously hidden children.
     * If this property is <code>true</code> (the default),
     * when a node expands
     * the tree can use scrolling to make
     * the maximum possible number of the node's descendants visible.
     * In some look and feels, trees might not need to scroll when expanded;
     * those look and feels will ignore this property.
     *
     * @param newValue <code>false</code> to disable scrolling on expansion;
     *                 <code>true</code> to enable it
     * @see #getScrollsOnExpand()
     */
    public void  setScrollsOnExpand (boolean newValue ){
		boolean oldValue =scrollsOnExpand ;
		if(oldValue != newValue){
			scrollsOnExpand = newValue;
			scrollsOnExpandSet = true;
	        firePropertyChange(SCROLLS_ON_EXPAND_PROPERTY, oldValue, newValue);
		}
    }

    /**
     * Returns the value of the <code>scrollsOnExpand</code> property.
     *
     * @return the value of the <code>scrollsOnExpand</code> property
     */
    public boolean  isScrollsOnExpand (){
		return scrollsOnExpand;
    }

    /**
     * Sets whether or not scrolls on expand is set by user.
     * If it is set by user, the LAF will not change the scrolls on expand value.
     * @param b set or not
     */
    public void  setScrollsOnExpandSet (boolean b ){
    	scrollsOnExpandSet = b;
    }

    /**
     * Returns whether or not scrolls on expand is set by user.
     * If it is set by user, the LAF will not change the scrolls on expand value.
     * @return set or not
     */
    public boolean  isScrollsOnExpandSet (){
    	return scrollsOnExpandSet;
    }

    /**
     * Sets the number of mouse clicks before a node will expand or close.
     * The default is 3.
     */
    public void  setToggleClickCount (int clickCount ){
		int oldCount =toggleClickCount ;
		if(oldCount != clickCount){
			toggleClickCount = clickCount;
			firePropertyChange(TOGGLE_CLICK_COUNT_PROPERTY, oldCount, clickCount);
		}
    }

    /**
     * Returns the number of mouse clicks needed to expand or close a node.
     *
     * @return number of mouse clicks before node is expanded
     */
    public int  getToggleClickCount (){
		return toggleClickCount;
    }

    /**
     * Configures the <code>expandsSelectedPaths</code> property. If
     * true, any time the selection is changed, either via the
     * <code>TreeSelectionModel</code>, or the cover methods provided by
     * <code>JTree</code>, the <code>TreePath</code>s parents will be
     * expanded to make them visible (visible meaning the parent path is
     * expanded, not necessarily in the visible rectangle of the
     * <code>JTree</code>). If false, when the selection
     * changes the nodes parent is not made visible (all its parents expanded).
     * This is useful if you wish to have your selection model maintain paths
     * that are not always visible (all parents expanded).
     *
     * @param newValue the new value for <code>expandsSelectedPaths</code>
     *               the parent of the path visible.
     */
    public void  setExpandsSelectedPaths (boolean newValue ){
		boolean oldValue =expandsSelectedPaths ;
		if(oldValue != newValue){
			expandsSelectedPaths = newValue;
			firePropertyChange(EXPANDS_SELECTED_PATHS_PROPERTY, oldValue, newValue);
		}
    }

    /**
     * Returns the <code>expandsSelectedPaths</code> property.
     * @return true if selection changes result in the parent path being
     *         expanded
     * @see #setExpandsSelectedPaths()
     */
    public boolean  isExpandsSelectedPaths (){
		return expandsSelectedPaths;
    }

    /**
     * Returns <code>isEditable</code>. This is invoked from the UI before
     * editing begins to insure that the given path can be edited. This
     * is provided as an entry point for subclassers to add filtered
     * editing without having to resort to creating a new editor.
     *
     * @return true if every parent node and the node itself is editable
     * @see #isEditable()
     */
    public boolean  isPathEditable (TreePath path ){
        return isEditable();
    }

    //
    // The following are convenience methods that get forwarded to the
    // current TreeUI.
    //

    /**
     * Returns the number of rows that are currently being displayed.
     *
     * @return the number of rows that are being displayed
     */
    public int  getRowCount (){
        TreeUI tree =getTreeUI ();
        if(tree != null)
            return tree.getRowCount(this);
        return 0;
    }

    /**
     * Selects the node identified by the specified path. If any
     * component of the path is hidden (under a collapsed node), and
     * <code>getExpandsSelectedPaths</code> is true it is
     * exposed (made viewable).
     *
     * @param path the <code>TreePath</code> specifying the node to select
     * @param programmatic indicate if this is a programmatic change
     */
    public void  setSelectionPath (TreePath path ,boolean programmatic =true ){
        getSelectionModel().setSelectionPath(path, programmatic);
    }

    /**
     * Selects the nodes identified by the specified array of paths.
     * If any component in any of the paths is hidden (under a collapsed
     * node), and <code>getExpandsSelectedPaths</code> is true
     * it is exposed (made viewable).
     *
     * @param paths an array of <code>TreePath</code> objects that specifies
     *		the nodes to select
     * @param programmatic indicate if this is a programmatic change
     */
    public void  setSelectionPaths (Array paths ,boolean programmatic =true ){
        getSelectionModel().setSelectionPaths(paths, programmatic);
    }

    /**
     * Sets the path identifies as the lead. The lead may not be selected.
     * The lead is not maintained by <code>JTree</code>,
     * rather the UI will update it.
     *
     * @param newPath  the new lead path
     */
    public void  setLeadSelectionPath (TreePath newPath ){
		TreePath oldValue =leadPath ;
		if((oldValue==null && newPath !=null) || (oldValue!=null && !oldValue.equals(newPath))){
			leadPath = newPath;
			firePropertyChange(LEAD_SELECTION_PATH_PROPERTY, oldValue, newPath);
		}
    }

    /**
     * Sets the path identified as the anchor.
     * The anchor is not maintained by <code>JTree</code>, rather the UI will
     * update it.
     *
     * @param newPath  the new anchor path
     */
    public void  setAnchorSelectionPath (TreePath newPath ){
		TreePath oldValue =anchorPath ;
		if((oldValue==null && newPath !=null) || (oldValue!=null && !oldValue.equals(newPath))){
			anchorPath = newPath;
			firePropertyChange(ANCHOR_SELECTION_PATH_PROPERTY, oldValue, newPath);
		}
    }

    /**
     * Selects the node at the specified row in the display.
     *
     * @param row  the row to select, where 0 is the first row in
     *             the display
     * @param programmatic indicate if this is a programmatic change
     */
    public void  setSelectionRow (int row ,boolean programmatic =true ){
        setSelectionRows(.get(row), programmatic);
    }

    /**
     * Selects the nodes corresponding to each of the specified rows
     * in the display. If a particular element of <code>rows</code> is
     * < 0 or >= <code>getRowCount</code>, it will be ignored.
     * If none of the elements
     * in <code>rows</code> are valid rows, the selection will
     * be cleared. That is it will be as if <code>clearSelection</code>
     * was invoked.
     *
     * @param rows  an array of ints specifying the rows to select,
     *              where 0 indicates the first row in the display
     * @param programmatic indicate if this is a programmatic change
     */
    public void  setSelectionRows (Array rows ,boolean programmatic =true ){
        TreeUI ui =getTreeUI ();

        if(ui != null && rows != null) {
            int numRows =rows.length ;
            Array paths =new Array(numRows );

            for(int counter =0;counter <numRows ;counter ++){
                paths.put(counter,  ui.getPathForRow(this, rows.get(counter)));
	    	}
            setSelectionPaths(paths, programmatic);
        }
    }

    /**
     * Adds the node identified by the specified <code>TreePath</code>
     * to the current selection. If any component of the path isn't
     * viewable, and <code>getExpandsSelectedPaths</code> is true it is
     * made viewable.
     * <p>
     * Note that <code>JTree</code> does not allow duplicate nodes to
     * exist as children under the same parent -- each sibling must be
     * a unique object.
     *
     * @param path the <code>TreePath</code> to add
     * @param programmatic indicate if this is a programmatic change
     */
    public void  addSelectionPath (TreePath path ,boolean programmatic =true ){
        getSelectionModel().addSelectionPath(path, programmatic);
    }

    /**
     * Adds each path in the array of paths to the current selection. If
     * any component of any of the paths isn't viewable and
     * <code>getExpandsSelectedPaths</code> is true, it is
     * made viewable.
     * <p>
     * Note that <code>JTree</code> does not allow duplicate nodes to
     * exist as children under the same parent -- each sibling must be
     * a unique object.
     *
     * @param paths an array of <code>TreePath</code> objects that specifies
     *		the nodes to add
     * @param programmatic indicate if this is a programmatic change
     */
    public void  addSelectionPaths (Array paths ,boolean programmatic =true ){
		getSelectionModel().addSelectionPaths(paths, programmatic);
    }

    /**
     * Adds the path at the specified row to the current selection.
     *
     * @param row  an integer specifying the row of the node to add,
     *             where 0 is the first row in the display
     * @param programmatic indicate if this is a programmatic change
     */
    public void  addSelectionRow (int row ,boolean programmatic =true ){
        addSelectionRows(.get(row), programmatic);
    }

    /**
     * Adds the paths at each of the specified rows to the current selection.
     *
     * @param rows  an array of ints specifying the rows to add,
     *              where 0 indicates the first row in the display
     * @param programmatic indicate if this is a programmatic change
     */
    public void  addSelectionRows (Array rows ,boolean programmatic =true ){
        TreeUI ui =getTreeUI ();

        if(ui != null && rows != null) {
            int numRows =rows.length ;
            Array paths =new Array(numRows );

            for(int counter =0;counter <numRows ;counter ++)
                paths.put(counter,  ui.getPathForRow(this, rows.get(counter)));
            addSelectionPaths(paths, programmatic);
        }
    }

    /**
     * Returns the last path component in the first node of the current
     * selection.
     *
     * @return the last <code>Object</code> in the first selected node's
     *		<code>TreePath</code>,
     *		or <code>null</code> if nothing is selected
     * @see TreePath#getLastPathComponent
     */
    public Object  getLastSelectedPathComponent (){
        TreePath selPath =getSelectionModel ().getSelectionPath ();

        if(selPath != null)
            return selPath.getLastPathComponent();
        return null;
    }

    /**
     * Returns the path identified as the lead.
     * @return path identified as the lead
     */
    public TreePath  getLeadSelectionPath (){
		return leadPath;
    }

    /**
     * Returns the path identified as the anchor.
     * @return path identified as the anchor
     */
    public TreePath  getAnchorSelectionPath (){
		return anchorPath;
    }

    /**
     * Returns the path to the first selected node.
     *
     * @return the <code>TreePath</code> for the first selected node,
     *		or <code>null</code> if nothing is currently selected
     */
    public TreePath  getSelectionPath (){
        return getSelectionModel().getSelectionPath();
    }

    /**
     * Returns the paths of all selected values.
     *
     * @return an array of <code>TreePath</code> objects indicating the selected
     *         nodes, or <code>null</code> if nothing is currently selected
     */
    public Array  getSelectionPaths (){
        return getSelectionModel().getSelectionPaths();
    }

    /**
     * Returns all of the currently selected rows. This method is simply
     * forwarded to the <code>TreeSelectionModel</code>.
     * If nothing is selected <code>null</code> or an empty array will
     * be returned, based on the <code>TreeSelectionModel</code>
     * implementation.
     *
     * @return an array of integers that identifies all currently selected rows
     *         where 0 is the first row in the display
     */
    public Array  getSelectionRows (){
        return getSelectionModel().getSelectionRows();
    }


    /**
     * Returns the first selected row. If nothing is selected -1 will be returned.
     *
     * @return the first selected row. If nothing is selected -1 will be returned.
     */
    public int  getSelectionRow (){
    	Array rows =getSelectionModel ().getSelectionRows ();
    	if(rows == null || rows.length == 0){
    		return -1;
    	}else{
    		return rows.get(0);
    	}
    }

    /**
     * Returns the number of nodes selected.
     *
     * @return the number of nodes selected
     */
    public int  getSelectionCount (){
        return selectionModel.getSelectionCount();
    }

    /**
     * Gets the first selected row.
     *
     * @return an integer designating the first selected row, where 0 is the
     *         first row in the display
     */
    public int  getMinSelectionRow (){
        return getSelectionModel().getMinSelectionRow();
    }

    /**
     * Returns the last selected row.
     *
     * @return an integer designating the last selected row, where 0 is the
     *         first row in the display
     */
    public int  getMaxSelectionRow (){
        return getSelectionModel().getMaxSelectionRow();
    }

    /**
     * Returns the row index corresponding to the lead path.
     *
     * @return an integer giving the row index of the lead path,
     *		where 0 is the first row in the display; or -1
     *		if <code>leadPath</code> is <code>null</code>
     */
    public int  getLeadSelectionRow (){
		TreePath leadPath =getLeadSelectionPath ();

		if (leadPath != null) {
		    return getRowForPath(leadPath);
		}
        return -1;
    }

    /**
     * Returns true if the item identified by the path is currently selected.
     *
     * @param path a <code>TreePath</code> identifying a node
     * @return true if the node is selected
     */
    public boolean  isPathSelected (TreePath path ){
        return getSelectionModel().isPathSelected(path);
    }

    /**
     * Returns true if the node identified by row is selected.
     *
     * @param row  an integer specifying a display row, where 0 is the first
     *             row in the display
     * @return true if the node is selected
     */
    public boolean  isRowSelected (int row ){
        return getSelectionModel().isRowSelected(row);
    }

    /**
     * Returns an <code>Array</code> of the descendants of the
     * path <code>parent</code> that
     * are currently expanded. If <code>parent</code> is not currently
     * expanded, this will return <code>null</code>.
     * If you expand/collapse nodes while
     * iterating over the returned <code>Array</code>
     * this may not return all
     * the expanded paths, or may return paths that are no longer expanded.
     *
     * @param parent  the path which is to be examined
     * @return an <code>Array</code> of the descendents of
     *		<code>parent</code>, or <code>null</code> if
     *		<code>parent</code> is not currently expanded
     */
    public Array  getExpandedDescendants (TreePath parent ){
		if(!isExpanded(parent)){
		    return null;
		}

		Array toggledPaths =expandedState.keys ();
		ArrayList elements =null ;
		TreePath path ;
		boolean value ;

		if(toggledPaths != null) {
			for(int i =0;i <toggledPaths.length ;i ++){
				path = TreePath(toggledPaths.get(i));
				value = expandedState.get(path);
				// Add the path if it is expanded, a descendant of parent,
				// and it is visible (all parents expanded). This is rather
				// expensive!
				if(path != parent && value==true &&
				   parent.isDescendant(path) && isPathVisible(path)) {
				    if (elements == null) {
						elements = new ArrayList();
				    }
				    elements.append(path);
				}
		    }
		}
		if (elements == null) {
		    return new Array();
		}
		return elements.toArray();
    }

    /**
     * Returns true if the node identified by the path has ever been
     * expanded.
     * @return true if the <code>path</code> has ever been expanded
     */
    public boolean  hasBeenExpanded (TreePath path ){
		return (path != null && expandedState.get(path) != null);
    }

    /**
     * Returns true if the node identified by the path is currently expanded,
     *
     * @param path  the <code>TreePath</code> specifying the node to check
     * @return false if any of the nodes in the node's path are collapsed,
     *               true if all nodes in the path are expanded
     */
    public boolean  isExpanded (TreePath path ){
		if(path == null)
		    return false;

		// Is this node expanded?
		boolean value =expandedState.get(path );

		if(value != true){
	    	return false;
		}

		// It is, make sure its parent is also expanded.
		TreePath parentPath =path.getParentPath ();

		if(parentPath != null){
		    return isExpanded(parentPath);
		}
	    return true;
    }

    /**
     * Returns true if the node at the specified display row is currently
     * expanded.
     *
     * @param row  the row to check, where 0 is the first row in the
     *             display
     * @return true if the node is currently expanded, otherwise false
     */
    public boolean  isExpandedOfRow (int row ){
        TreeUI tree =getTreeUI ();

        if(tree != null) {
	    	TreePath path =tree.getPathForRow(this ,row );

	    	if(path != null) {
                boolean value =expandedState.get(path );
                return (value == true);
            }
		}
        return false;
    }

    /**
     * Returns true if the value identified by path is currently collapsed,
     * this will return false if any of the values in path are currently
     * not being displayed.
     *
     * @param path  the <code>TreePath</code> to check
     * @return true if any of the nodes in the node's path are collapsed,
     *               false if all nodes in the path are expanded
     */
    public boolean  isCollapsed (TreePath path ){
		return !isExpanded(path);
    }

    /**
     * Returns true if the node at the specified display row is collapsed.
     *
     * @param row  the row to check, where 0 is the first row in the
     *             display
     * @return true if the node is currently collapsed, otherwise false
     */
    public boolean  isCollapsedOfRow (int row ){
		return !isExpandedOfRow(row);
    }

    /**
     * Ensures that the node identified by path is currently viewable.
     *
     * @param path  the <code>TreePath</code> to make visible
     */
    public void  makePathVisible (TreePath path ){
        if(path != null) {
		    TreePath parentPath =path.getParentPath ();

		    if(parentPath != null) {
				expandPath(parentPath);
		    }
        }
    }

    /**
     * Returns true if the value identified by path is currently viewable,
     * which means it is either the root or all of its parents are expanded.
     * Otherwise, this method returns false.
     *
     * @return true if the node is viewable, otherwise false
     */
    public boolean  isPathVisible (TreePath path ){
        if(path != null) {
		    TreePath parentPath =path.getParentPath ();

		    if(parentPath != null){
				return isExpanded(parentPath);
		    }
		    // Root.
		    return true;
		}
        return false;
    }

    /**
     * Returns the <code>IntRectangle</code> that the specified node will be drawn
     * into. Returns <code>null</code> if any component in the path is hidden
     * (under a collapsed parent).
     * <p>
     * Note:<br>
     * This method returns a valid rectangle, even if the specified
     * node is not currently displayed.
     *
     * @param path the <code>TreePath</code> identifying the node
     * @return the <code>IntRectangle</code> the node is drawn in,
     *		or <code>null</code>
     */
    public IntRectangle  getPathBounds (TreePath path ){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getPathBounds(this, path);
        return null;
    }

    /**
     * Returns the <code>IntRectangle</code> that the node at the specified row is
     * drawn in.
     *
     * @param row  the row to be drawn, where 0 is the first row in the
     *             display
     * @return the <code>IntRectangle</code> the node is drawn in
     */
    public IntRectangle  getRowBounds (int row ){
		return getPathBounds(getPathForRow(row));
    }
	/**
	 * Returns the location in the JTree view area of the logic location.
	 */
	public IntPoint  getPixelLocationFromLogicLocation (IntPoint p ){
		IntPoint pp =p.clone ();
		IntPoint startP =getViewStartPoint ();
		pp.move(startP.x, startP.y);
		return pp;
	}

	/**
	 * Returns the location in the JTree view area of the logic location.
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
		int startY =insetsY -viewPos.y ;
		return new IntPoint(startX, startY);
	}

    /**
     * Makes sure all the path components in path are expanded (except
     * for the last path component) and scrolls so that the
     * node identified by the path is displayed. Only works when this
     * <code>JTree</code> is contained in a <code>JScrollPane</code>.
     *
     * @param path  the <code>TreePath</code> identifying the node to
     * 		bring into view
     */
    public void  scrollPathToVisible (TreePath path ){
		if(path != null) {
		    makePathVisible(path);

		    IntRectangle bounds =getPathBounds(path );

		    if(bounds != null) {
				scrollRectToVisible(bounds);
		    }
		}
    }

    /**
     * Scrolls the item identified by row until it is displayed. The minimum
     * of amount of scrolling necessary to bring the row into view
     * is performed. Only works when this <code>JTree</code> is contained in a
     * <code>JScrollPane</code>.
     *
     * @param row  an integer specifying the row to scroll, where 0 is the
     *             first row in the display
     */
    public void  scrollRowToVisible (int row ){
		scrollPathToVisible(getPathForRow(row));
    }

    /**
     * Returns the treePath that the user mouse pointed, null if no path was pointed.
     * @return the path mouse pointed, null if not pointed any one.
     */
    public TreePath  getMousePointedPath (){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getMousePointedPath();
        return null;
    }

    /**
     * Returns the path for the specified row.  If <code>row</code> is
     * not visible, <code>null</code> is returned.
     *
     * @param row  an integer specifying a row
     * @return the <code>TreePath</code> to the specified node,
     *		<code>null</code> if <code>row < 0</code>
     *		or <code>row > getRowCount()</code>
     */
    public TreePath  getPathForRow (int row ){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getPathForRow(this, row);
        return null;
    }

    /**
     * Returns the row that displays the node identified by the specified
     * path.
     *
     * @param path  the <code>TreePath</code> identifying a node
     * @return an integer specifying the display row, where 0 is the first
     *         row in the display, or -1 if any of the elements in path
     *         are hidden under a collapsed parent.
     */
    public int  getRowForPath (TreePath path ){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getRowForPath(this, path);
        return -1;
    }

    /**
     * Ensures that the node identified by the specified path is
     * expanded and viewable. If the last item in the path is a
     * leaf, this will have no effect.
     *
     * @param path  the <code>TreePath</code> identifying a node
     */
    public void  expandPath (TreePath path ){
		// Only expand if not leaf!
		TreeModel model =getModel ();

		if(path != null && model != null && !model.isLeaf(path.getLastPathComponent())) {
		    setExpandedState(path, true);
		}
    }

    /**
     * Ensures that the node in the specified row is expanded and
     * viewable.
     * <p>
     * If <code>row</code> is < 0 or >= <code>getRowCount</code> this
     * will have no effect.
     *
     * @param row  an integer specifying a display row, where 0 is the
     *             first row in the display
     */
    public void  expandRow (int row ){
		expandPath(getPathForRow(row));
    }

    /**
     * Ensures that the node identified by the specified path is
     * collapsed and viewable.
     *
     * @param path  the <code>TreePath</code> identifying a node
      */
    public void  collapsePath (TreePath path ){
		setExpandedState(path, false);
    }

    /**
     * Ensures that the node in the specified row is collapsed.
     * <p>
     * If <code>row</code> is < 0 or >= <code>getRowCount</code> this
     * will have no effect.
     *
     * @param row  an integer specifying a display row, where 0 is the
     *             first row in the display
      */
    public void  collapseRow (int row ){
		collapsePath(getPathForRow(row));
    }

    /**
     * Returns the path for the node at the specified location.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return  the <code>TreePath</code> for the node at that location
     */
    public TreePath  getPathForLocation (int x ,int y ){
        TreePath closestPath =getClosestPathForLocation(x ,y );

        if(closestPath != null) {
            IntRectangle pathBounds =getPathBounds(closestPath );

            if(pathBounds != null &&
               x >= pathBounds.x && x < (pathBounds.x + pathBounds.width) &&
               y >= pathBounds.y && y < (pathBounds.y + pathBounds.height))
                return closestPath;
        }
        return null;
    }

    /**
     * Returns the row for the specified location.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return the row corresponding to the location, or -1 if the
     *         location is not within the bounds of a displayed cell
     * @see #getClosestRowForLocation
     */
    public int  getRowForLocation (int x ,int y ){
		return getRowForPath(getPathForLocation(x, y));
    }

    /**
     * Returns the path to the node that is closest to x,y.  If
     * no nodes are currently viewable, or there is no model, returns
     * <code>null</code>, otherwise it always returns a valid path.  To test if
     * the node is exactly at x, y, get the node's bounds and
     * test x, y against that.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return  the <code>TreePath</code> for the node closest to that location,
     *          <code>null</code> if nothing is viewable or there is no model
     *
     * @see #getPathForLocation
     * @see #getPathBounds
     */
    public TreePath  getClosestPathForLocation (int x ,int y ){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getClosestPathForLocation(this, x, y);
        return null;
    }

    /**
     * Returns the row to the node that is closest to x,y.  If no nodes
     * are viewable or there is no model, returns -1. Otherwise,
     * it always returns a valid row.  To test if the returned object is
     * exactly at x, y, get the bounds for the node at the returned
     * row and test x, y against that.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return the row closest to the location, -1 if nothing is
     *         viewable or there is no model
     *
     * @see #getRowForLocation
     * @see #getRowBounds
     */
    public int  getClosestRowForLocation (int x ,int y ){
		return getRowForPath(getClosestPathForLocation(x, y));
    }

    /**
     * Returns true if the tree is being edited. The item that is being
     * edited can be obtained using <code>getSelectionPath</code>.
     *
     * @return true if the user is currently editing a node
     * @see #getSelectionPath
     */
    public boolean  isEditing (){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.isEditing(this);
        return false;
    }

    /**
     * Ends the current editing session.
     * (The <code>DefaultTreeCellEditor</code>
     * object saves any edits that are currently in progress on a cell.
     * Other implementations may operate differently.)
     * Has no effect if the tree isn't being edited.
     * <blockquote>
     * <b>Note:</b><br>
     * To make edit-saves automatic whenever the user changes
     * their position in the tree, use {@link #setInvokesStopCellEditing}.
     * </blockquote>
     *
     * @return true if editing was in progress and is now stopped,
     *              false if editing was not in progress
     */
    public boolean  stopEditing (){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.stopEditing(this);
        return false;
    }

    /**
     * Cancels the current editing session. Has no effect if the
     * tree isn't being edited.
     */
    public void  cancelEditing (){
        TreeUI tree =getTreeUI ();

        if(tree != null)
	    	tree.cancelEditing(this);
    }

    /**
     * Selects the node identified by the specified path and initiates
     * editing.  The edit-attempt fails if the <code>CellEditor</code>
     * does not allow
     * editing for the specified item.
     *
     * @param path  the <code>TreePath</code> identifying a node
     */
    public void  startEditingAtPath (TreePath path ){
        TreeUI tree =getTreeUI ();

        if(tree != null){
            if(tree.startEditingAtPath(this, path)){
            	this.dispatchEvent(new TreeCellEditEvent(TreeCellEditEvent.EDITING_STARTED, path));
            }
        }
    }

    /**
     * Returns the path to the element that is currently being edited.
     *
     * @return  the <code>TreePath</code> for the node being edited
     */
    public TreePath  getEditingPath (){
        TreeUI tree =getTreeUI ();

        if(tree != null)
            return tree.getEditingPath(this);
        return null;
    }

    //
    // Following are primarily convenience methods for mapping from
    // row based selections to path selections.  Sometimes it is
    // easier to deal with these than paths (mouse downs, key downs
    // usually just deal with index based selections).
    // Since row based selections require a UI many of these won't work
    // without one.
    //

    /**
     * Sets the tree's selection model. When a <code>null</code> value is
     * specified an emtpy
     * <code>selectionModel</code> is used, which does not allow selections.
     *
     * @param selectionModel the <code>TreeSelectionModel</code> to use,
     *		or <code>null</code> to disable selections
     * @see TreeSelectionModel
     */
    public void  setSelectionModel (TreeSelectionModel selectionModel ){
    	if(this.selectionModel == selectionModel){
    		return;
    	}
        if(selectionModel == null){
            selectionModel = EmptySelectionModel.sharedInstance();
        }
        TreeSelectionModel oldValue =this.selectionModel ;

		if (this.selectionModel != null) {
			this.selectionModel.removeTreeSelectionListener(__valueChangedTreeSelectionRedirector);
		}

        this.selectionModel = selectionModel;
	    this.selectionModel.addTreeSelectionListener(__valueChangedTreeSelectionRedirector);
        firePropertyChange(SELECTION_MODEL_PROPERTY, oldValue, this.selectionModel);
    }

    /**
     * Returns the model for selections. This should always return a
     * non-<code>null</code> value. If you don't want to allow anything
     * to be selected
     * set the selection model to <code>null</code>, which forces an empty
     * selection model to be used.
     *
     * @see #setSelectionModel()
     */
    public TreeSelectionModel  getSelectionModel (){
        return selectionModel;
    }

	/**
	 * Proxy method which passes specified selection mode to the currently
	 * configured selection model.
	 * @param mode the selection mode identifier
	 */
	public void  setSelectionMode (int mode ){
		selectionModel.setSelectionMode(mode);
	}

    /**
     * Returns <code>JTreePath</code> instances representing the path
     * between index0 and index1 (including index1).
     * Returns <code>null</code> if there is no tree.
     *
     * @param index0  an integer specifying a display row, where 0 is the
     *                first row in the display
     * @param index1  an integer specifying a second display row
     * @return an array of <code>TreePath</code> objects, one for each
     *		node between index0 and index1, inclusive; or <code>null</code>
     *		if there is no tree
     */
    private Array  getPathBetweenRows (int index0 ,int index1 ){
        int newMinIndex ,newMaxIndex int ;
        TreeUI tree =getTreeUI ();

        newMinIndex = Math.min(index0, index1);
        newMaxIndex = Math.max(index0, index1);

        if(tree != null) {
            Array selection =new Array(newMaxIndex -newMinIndex +1);

            for(int counter =newMinIndex ;counter <=newMaxIndex ;counter ++){
                selection.put(counter - newMinIndex,  tree.getPathForRow(this, counter));
            }
            return selection;
        }
        return null;
    }

    /**
     * Selects the nodes between index0 and index1, inclusive.
     *
     * @param index0  an integer specifying a display row, where 0 is the
     *                first row in the display
     * @param index1  an integer specifying a second display row
     * @param programmatic indicate if this is a programmatic change
    */
    public void  setSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
        Array paths =getPathBetweenRows(index0 ,index1 );
        getSelectionModel().setSelectionPaths(paths, programmatic);
    }

    /**
     * Adds the paths between index0 and index1, inclusive, to the
     * selection.
     *
     * @param index0  an integer specifying a display row, where 0 is the
     *                first row in the display
     * @param index1  an integer specifying a second display row
     * @param programmatic indicate if this is a programmatic change
     */
    public void  addSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
        Array paths =getPathBetweenRows(index0 ,index1 );

        getSelectionModel().addSelectionPaths(paths, programmatic);
    }

    /**
     * Removes the nodes between index0 and index1, inclusive, from the
     * selection.
     *
     * @param index0  an integer specifying a display row, where 0 is the
     *                first row in the display
     * @param index1  an integer specifying a second display row
     * @param programmatic indicate if this is a programmatic change
     */
    public void  removeSelectionInterval (int index0 ,int index1 ,boolean programmatic =true ){
        Array paths =getPathBetweenRows(index0 ,index1 );

        this.getSelectionModel().removeSelectionPaths(paths, programmatic);
    }

    /**
     * Removes the node identified by the specified path from the current
     * selection.
     *
     * @param path  the <code>TreePath</code> identifying a node
     * @param programmatic indicate if this is a programmatic change
     */
    public void  removeSelectionPath (TreePath path ,boolean programmatic =true ){
        getSelectionModel().removeSelectionPath(path, programmatic);
    }

    /**
     * Removes the nodes identified by the specified paths from the
     * current selection.
     *
     * @param paths an array of <code>TreePath</code> objects that
     *              specifies the nodes to remove
     * @param programmatic indicate if this is a programmatic change
     */
    public void  removeSelectionPaths (Array paths ,boolean programmatic =true ){
        getSelectionModel().removeSelectionPaths(paths, programmatic);
    }

    /**
     * Removes the row at the index <code>row</code> from the current
     * selection.
     *
     * @param row  the row to remove
     * @param programmatic indicate if this is a programmatic change
     */
    public void  removeSelectionRow (int row ,boolean programmatic =true ){
        removeSelectionRows(.get(row), programmatic);
    }

    /**
     * Removes the rows that are selected at each of the specified
     * rows.
     *
     * @param rows  an array of ints specifying display rows, where 0 is
     *             the first row in the display
     * @param programmatic indicate if this is a programmatic change
     */
    public void  removeSelectionRows (Array rows ,boolean programmatic =true ){
        TreeUI tree =getTreeUI ();

        if(tree != null && rows != null) {
            int numRows =rows.length ;
            Array paths =new Array(numRows );

            for(int counter =0;counter <numRows ;counter ++){
                paths.put(counter,  tree.getPathForRow(this, rows.get(counter)));
            }
            removeSelectionPaths(paths, programmatic);
        }
    }

    /**
     * Clears the selection.
     * @param programmatic indicate if this is a programmatic change
     */
    public void  clearSelection (boolean programmatic =true ){
        getSelectionModel().clearSelection(programmatic);
    }

    /**
     * Returns true if the selection is currently empty.
     *
     * @return true if the selection is currently empty
     */
    public boolean  isSelectionEmpty (){
        return getSelectionModel().isSelectionEmpty();
    }

    protected void  firePropertyChange (String pn ,*oldValue ,*)newValue {
		dispatchEvent(new PropertyChangeEvent(pn, oldValue, newValue));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>path</code> parameter.
     *
     * @param path the <code>TreePath</code> indicating the node that was
     *		expanded
     * @see EventListenerList
     */
    public void  fireTreeExpanded (TreePath path ){
    	dispatchEvent(new TreeEvent(TreeEvent.TREE_EXPANDED, path));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>path</code> parameter.
     *
     * @param path the <code>TreePath</code> indicating the node that was
     *		collapsed
     * @see EventListenerList
     */
    public void  fireTreeCollapsed (TreePath path ){
    	dispatchEvent(new TreeEvent(TreeEvent.TREE_COLLAPSED, path));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>path</code> parameter.
     *
     * @param path the <code>TreePath</code> indicating the node that was
     *		expanded
     * @see EventListenerList
     */
     public void  fireTreeWillExpand (TreePath path ){
    	dispatchEvent(new TreeEvent(TreeEvent.TREE_WILL_EXPAND, path));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>path</code> parameter.
     *
     * @param path the <code>TreePath</code> indicating the node that was
     *		expanded
     * @see EventListenerList
     */
     public void  fireTreeWillCollapse (TreePath path ){
    	dispatchEvent(new TreeEvent(TreeEvent.TREE_WILL_COLLAPSE, path));
    }

    /**
     * Adds a listener for tree selection events.
     * the listenerthat will be notified when a node is selected or
     * deselected (a "negative selection")
     *
     * @param listener the new listener to be added
     * @param priority the priority
     * @param useWeakReference Determines whether the reference to the listener is strong or weak.
     */
    public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
    	addEventListener(TreeSelectionEvent.TREE_SELECTION_CHANGED, listener, false, priority, useWeakReference);
    }

	public void  removeSelectionListener (Function listener ){
		removeEventListener(TreeSelectionEvent.TREE_SELECTION_CHANGED, listener);
	}

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * @param e the <code>TreeSelectionEvent</code> to be fired;
     *          generated by the
     *		<code>TreeSelectionModel</code>
     *          when a node is selected or deselected
     * @see EventListenerList
     */
    /*protected void  fireValueChanged (TreeSelectionEvent e ){
    	//TODO check if need
    	dispatchEvent(e.cloneWithSource(this));
    }*/

    /**
     * Sent when the tree has changed enough that we need to resize
     * the bounds, but not enough that we need to remove the
     * expanded node set (e.g nodes were expanded or collapsed, or
     * nodes were inserted into the tree). You should never have to
     * invoke this, the UI will invoke this as it needs to.
     */
    public void  treeDidChange (){
    	setViewPosition(restrictionViewPos(getViewPosition()));
        revalidate();
        repaint();
    }

    /**
     * Sets the number of rows that are to be displayed.
     * This will only work if the tree is contained in a
     * <code>JScrollPane</code>,
     * and will adjust the preferred size and size of that scrollpane.
     *
     * @param newCount the number of rows to display
     */
    public void  setVisibleRowCount (int newCount ){
        int oldCount =visibleRowCount ;
		if(oldCount != newCount){
	        visibleRowCount = newCount;
	        firePropertyChange(VISIBLE_ROW_COUNT_PROPERTY, oldCount,
	                           visibleRowCount);
	        invalidate();
		}
    }

    /**
     * Returns the number of rows that are displayed in the display area.
     *
     * @return the number of rows displayed
     */
    public int  getVisibleRowCount (){
        return visibleRowCount;
    }

    /**
     * Expands the root path, assuming the current TreeModel has been set.
     */
    protected void  expandRoot (){
			TreeModel model =getModel ();

		if(model != null && model.getRoot() != null) {
		    expandPath(new TreePath(.get(model.getRoot())));
		}
    }

    /**
     * Returns an array of integers specifying the indexs of the
     * components in the <code>path</code>. If <code>path</code> is
     * the root, this will return an empty array.  If <code>path</code>
     * is <code>null</code>, <code>null</code> will be returned.
     */
    protected Array  getModelIndexsForPath (TreePath path ){
		if(path != null) {
		    TreeModel model =getModel ();
		    int count =path.getPathCount ();
		    Array indexs =new int.get(count -1) ;
		    Object parent =model.getRoot ();

		    for(int counter =1;counter <count ;counter ++){
				indexs.put(counter - 1,  model.getIndexOfChild(parent, path.getPathComponent(counter)));
				parent = path.getPathComponent(counter);
				if(indexs.get(counter - 1) < 0){
				    return null;
				}
			}
			return indexs;
		}
		return null;
    }

    /**
     * Returns a <code>TreePath</code> created by obtaining the children
     * for each of the indices in <code>indexs</code>. If <code>indexs</code>
     * or the <code>TreeModel</code> is <code>null</code>, it will return
     * <code>null</code>.
     */
    protected TreePath  getPathForIndexs (Array indexs ){
		if(indexs == null)
		    return null;

		TreeModel model =getModel ();

		if(model == null)
		    return null;

		int count =indexs.length ;
		Object parent =model.getRoot ();
		TreePath parentPath =new TreePath(.get(parent) );

		for(int counter =0;counter <count ;counter ++){
		    parent = model.getChild(parent, indexs.get(counter));
		    if(parent == null){
				return null;
		    }
		    parentPath = parentPath.pathByAddingChild(parent);
		}
		return parentPath;
    }

    /**
     * Sets the expanded state of this <code>JTree</code>.
     * If <code>state</code> is
     * true, all parents of <code>path</code> and path are marked as
     * expanded. If <code>state</code> is false, all parents of
     * <code>path</code> are marked EXPANDED, but <code>path</code> itself
     * is marked collapsed.<p>
     * This will fail if a <code>TreeWillExpandListener</code> vetos it.
     */
    protected void  setExpandedState (TreePath path ,boolean state ){
		if(path != null) {
		    // Make sure all parents of path are expanded.
		    Stack stack ;
		    TreePath parentPath =path.getParentPath ();

		    if (expandedStack.size() == 0) {
				stack = new Stack();
		    }else {
				stack = Stack(expandedStack.pop());
		    }

		    try {
				while(parentPath != null) {
				    if(isExpanded(parentPath)) {
						parentPath = null;
				    }else {
						stack.push(parentPath);
						parentPath = parentPath.getParentPath();
				    }
				}
				for(int counter =stack.size ()-1;counter >=0;counter --){
				    parentPath = TreePath(stack.pop());
				    if(!isExpanded(parentPath)) {
						try {
						    fireTreeWillExpand(parentPath);
						} catch (eve1:Error) {
						    // Expand vetoed!
						    return;
						}
						expandedState.put(parentPath, true);
						fireTreeExpanded(parentPath);
				    }
				}
		    }catch(e:Error) {
				if (expandedStack.size() < TEMP_STACK_SIZE) {
				    stack.clear();
				    expandedStack.push(stack);
				}
		    }
		    Object cValue ;
		    if(!state) {
				// collapse last path.
				cValue = expandedState.get(path);

				if(cValue != null && cValue==true) {
				    try {
						fireTreeWillCollapse(path);
				    }catch (eve2:Error) {
						return;
				    }
				    expandedState.put(path, false);
				    fireTreeCollapsed(path);
				    if (removeDescendantSelectedPaths(path, false) && !isPathSelected(path)) {
						// A descendant was selected, select the parent.
						addSelectionPath(path);
				    }
				}
		    }else {
				// Expand last path.
				cValue = expandedState.get(path);

				if(cValue == null || !(cValue==true)) {
				    try {
						fireTreeWillExpand(path);
				    }catch (eve3:Error) {
						return;
				    }
				    expandedState.put(path, true);
				    fireTreeExpanded(path);
				}
		    }
		}
    }

    /**
     * Returns an <code>Enumeration</code> of <code>TreePaths</code>
     * that have been expanded that
     * are descendants of <code>parent</code>.
     */
    private Array  getDescendantToggledPaths (TreePath parent ){
		if(parent == null)
		    return null;

		Array descendants =new Array ();
		Array nodes =expandedState.keys ();
		TreePath path ;

		for(int i =0;i <nodes.length ;i ++){
		    path = TreePath(nodes.get(i));
		    if(parent.isDescendant(path)){
				descendants.push(path);
		    }
		}
		return descendants;
    }

    /**
     * Removes any descendants of the <code>TreePaths</code> in
     * <code>toRemove</code>
     * that have been expanded.
     */
	private void  removeDescendantToggledPaths (Array toRemove ){
		if(toRemove != null) {
			for(int i =0;i <toRemove.length ;i ++){
				Array descendants =getDescendantToggledPaths(TreePath(toRemove.get(i) ));

				if(descendants != null) {
					for(int j =0;j <descendants.length ;j ++){
						expandedState.remove(descendants.get(j));
					}
				}
			}
		}
	}

     /**
      * Clears the cache of toggled tree paths. This does NOT send out
      * any <code>TreeExpansionListener</code> events.
      */
     private void  clearToggledPaths (){
	 	expandedState.clear();
     }

    /**
     * Removes any paths in the selection that are descendants of
     * <code>path</code>. If <code>includePath</code> is true and
     * <code>path</code> is selected, it will be removed from the selection.
     *
     * @return true if a descendant was selected
     */
    private boolean  removeDescendantSelectedPaths (TreePath path ,boolean includePath ){
		Array toRemove =getDescendantSelectedPaths(path ,includePath );

		if (toRemove != null) {
		    getSelectionModel().removeSelectionPaths(toRemove);
		    return true;
		}
		return false;
    }

    /**
     * Returns an array of paths in the selection that are descendants of
     * <code>path</code>. The returned array may contain <code>null</code>s.
     */
    private Array  getDescendantSelectedPaths (TreePath path ,boolean includePath ){
		TreeSelectionModel sm =getSelectionModel ();
		Array selPaths =(sm != null) ? sm.getSelectionPaths() : null;

		if(selPaths != null) {
		    boolean shouldRemove =false ;

		    for(int counter =selPaths.length -1;counter >=0;counter --){
				if(selPaths.get(counter) != null &&
				   path.isDescendant(selPaths.get(counter)) &&
				   (!path.equals(selPaths.get(counter)) || includePath))
				    shouldRemove = true;
				else
				    selPaths.put(counter,  null);
		    }
		    if(!shouldRemove) {
				selPaths = null;
		    }
		    return selPaths;
		}
		return null;
    }

    /**
     * Removes any paths from the selection model that are descendants of
     * the nodes identified by in <code>e</code>.
     */
    public void  removeDescendantSelectedPathsWithEvent (TreeModelEvent e ){
		TreePath pPath =e.getTreePath ();
		Array oldChildren =e.getChildren ();
		TreeSelectionModel sm =getSelectionModel ();

		if (sm != null && pPath != null && oldChildren != null && oldChildren.length > 0) {
		    for(int counter =oldChildren.length -1;counter >=0;counter --){
			// Might be better to call getDescendantSelectedPaths
			// numerous times, then push to the model.
			removeDescendantSelectedPaths(pPath.pathByAddingChild(oldChildren.get(counter)), true);
		    }
		}
    }

	//*******************************************************************
	//                      Model Selection Listener
	//*******************************************************************

    /**
     * Invoked by the <code>TreeSelectionModel</code> when the
     * selection changes.
     *
     * @param source the <code>TreeSelectionModel</code>
     * @param e the <code>TreeSelectionEvent</code> generated by the
     *		<code>TreeSelectionModel</code></code> generated by the
     *		<code>TreeSelectionModel</code>
     */
    private void  __valueChangedTreeSelectionRedirector (TreeSelectionEvent e ){
		dispatchEvent(e.cloneWithSource(this));
    }

	//*******************************************************************
	//                         TreeModelListener
	//*******************************************************************

	public void  treeNodesChanged (TreeModelEvent e ){
	}

	public void  treeNodesInserted (TreeModelEvent e ){
	}

	public void  treeNodesRemoved (TreeModelEvent e ){
	    if(e == null){
			return;
	    }

	    TreePath parent =e.getTreePath ();
	    Array children =e.getChildren ();

	    if(children == null){
			return;
	    }

	    TreePath rPath ;
	    Array toRemove =new Array ();

	    for(int counter =children.length -1;counter >=0;counter --){
			rPath = parent.pathByAddingChild(children.get(counter));
			if(expandedState.get(rPath) != null)
			    toRemove.push(rPath);
	    }
	    if(toRemove.length > 0){
			removeDescendantToggledPaths(toRemove);
	    }

	    TreeModel model =getModel ();

	    if(model == null || model.isLeaf(parent.getLastPathComponent())){
			expandedState.remove(parent);
	    }

	    removeDescendantSelectedPathsWithEvent(e);
	}

	public void  treeStructureChanged (TreeModelEvent e ){
	    if(e == null){
			return;
	    }

	    // NOTE: If I change this to NOT remove the descendants
	    // and update BasicTreeUIs treeStructureChanged method
	    // to update descendants in response to a treeStructureChanged
	    // event, all the children of the event won't collapse!
	    TreePath parent =e.getTreePath ();

	    if(parent == null){
			return;
	    }

	    if (parent.getPathCount() == 1) {
			// New root, remove everything!
			clearToggledPaths();
			if(treeModel.getRoot() != null && !treeModel.isLeaf(treeModel.getRoot())) {
			    // Mark the root as expanded, if it isn't a leaf.
			    expandedState.put(parent, true);
			}
	    }else if(expandedState.get(parent) != null) {
			Array toRemove =new Array ();
			boolean isExpanded =isExpanded(parent );

			toRemove.push(parent);
			removeDescendantToggledPaths(toRemove);
			if(isExpanded) {
			    TreeModel model =getModel ();

			    if(model == null || model.isLeaf(parent.getLastPathComponent()))
					collapsePath(parent);
			    else
					expandedState.put(parent, true);
			}
	    }
	    removeDescendantSelectedPaths(parent, false);
	}

	//*******************************************************************
	//                        CellEditorListener
	//*******************************************************************

	public void  editingStopped (CellEditor source ){
		TreeUI tree =getTreeUI ();
		if(tree != null){
			TreePath path =tree.getEditingPath(this );
			if(path != null){
				getModel().valueForPathChanged(tree.getEditingPath(this), source.getCellEditorValue());
				dispatchEvent(new TreeCellEditEvent(TreeCellEditEvent.EDITING_STOPPED,
					path, path.getLastPathComponent(), source.getCellEditorValue()));
			}
		}
		requestFocus();
	}

	public void  editingCanceled (CellEditor source ){
		dispatchEvent(new TreeCellEditEvent(TreeCellEditEvent.EDITING_CANCELED, getTreeUI().getEditingPath(this)));
		requestFocus();
	}

	//*******************************************************************
	//                        Viewportable
	//*******************************************************************

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
			return Math.max(getRowHeight(), getExtentSize().height - getRowHeight());
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
			return Math.max(1, getExtentSize().width - 1);
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
		return getTreeUI().getViewSize(this);
	}

	public IntPoint  getViewPosition (){
		return new IntPoint(viewPosition.x, viewPosition.y);
	}

	public void  setViewPosition (IntPoint p ,boolean programmatic =true ){
		restrictionViewPos(p);
		if(!viewPosition.equals(p)){
			viewPosition.setLocation(p);
			fireStateChanged(programmatic);
			//TODO r
			//repaint();
		}
	}

	public void  scrollRectToVisible (IntRectangle contentRect ,boolean programmatic =true ){
		IntRectangle visibleRect =getVisibleRect ();
		IntPoint ltPoint =contentRect.leftTop ();
		IntPoint rbPoint =contentRect.rightBottom ();
		boolean ltIn =visibleRect.containsPoint(ltPoint );
		boolean rbIn =visibleRect.containsPoint(rbPoint );
		if(ltIn && rbIn){
			return;
		}else{
			if(!ltIn){
				setViewPosition(new IntPoint(ltPoint.x, ltPoint.y), programmatic);
			}else{
				IntDimension extendSize =getExtentSize ();
				setViewPosition(
					new IntPoint(rbPoint.x-extendSize.width, rbPoint.y-extendSize.height),
					programmatic);
			}
		}
	}

	public IntRectangle  getVisibleRect (){
		IntDimension es =getExtentSize ();
		return new IntRectangle(viewPosition.x, viewPosition.y, es.width, es.height);
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
}


