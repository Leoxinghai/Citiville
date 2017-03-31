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
import flash.events.MouseEvent;
import flash.ui.Keyboard;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.tree.ExpandControl;
import org.aswing.tree.*;
import org.aswing.util.ArrayList;

/**
 * @author iiley
 * @private
 */
public class BasicTreeUI extends BaseComponentUI implements TreeUI, NodeDimensions, TreeModelListener{

	private static Insets EMPTY_INSETS ;

	/** Object responsible for handling sizing and expanded issues. */
	protected AbstractLayoutCache treeState ;

	protected CellPane rendererPane ;
	/** Total distance that will be indented.  The sum of leftChildIndent
	  * and rightChildIndent.
	  */
	protected int totalChildIndent ;
	/** How much the depth should be offset to properly calculate
	 * x locations. This is based on whether or not the root is visible,
	 * and if the root handles are visible.
	 */
	protected int depthOffset ;
	/** Distance between left margin and where vertical dashes will be
	  * drawn. */
	protected int leftChildIndent ;
	/** Distance to add to leftChildIndent to determine where cell
	  * contents will be drawn. */
	protected int rightChildIndent ;
	/** If true, the property change event for LEAD_SELECTION_PATH_PROPERTY,
	 * or ANCHOR_SELECTION_PATH_PROPERTY will not generate a repaint. */
	protected boolean ignoreLAChange ;

	protected JTree tree ;
	protected TreeModel treeModel ;
	protected TreeCellEditor editor ;
	protected TreeSelectionModel selectionModel ;
	protected ArrayList cells ;
	protected boolean validCachedViewSize ;
	protected IntDimension viewSize ;
	protected IntPoint lastViewPosition ;

	protected ExpandControl expandControl ;

	public  BasicTreeUI (){
		super();
		if(EMPTY_INSETS == null){
			EMPTY_INSETS = new Insets(0, 0, 0, 0);
		}
		totalChildIndent = 0;
		depthOffset = 0;
		leftChildIndent = 0;
		rightChildIndent = 0;

		paintFocusedIndex = -1;
		cells = new ArrayList();
		lastViewPosition = new IntPoint();
		viewSize = new IntDimension();
		validCachedViewSize = false;
	}

	 public void  installUI (Component c ){
		tree = JTree(c);
		installDefaults();
		installComponents();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}

	protected String  getPropertyPrefix (){
		return "Tree.";
	}

	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(tree, pp);
		LookAndFeel.installBorderAndBFDecorators(tree, pp);
		LookAndFeel.installBasicProperties(tree, pp);

		ASColor sbg =tree.getSelectionBackground ();
		if (sbg == null || sbg is UIResource) {
			tree.setSelectionBackground(getColor(pp+"selectionBackground"));
		}

		ASColor sfg =tree.getSelectionForeground ();
		if (sfg == null || sfg is UIResource) {
			tree.setSelectionForeground(getColor(pp+"selectionForeground"));
		}
		tree.setRowHeight(getInt(pp+"rowHeight"));
		tree.setRowHeightSet(false);
		setLeftChildIndent(getInt(pp+"leftChildIndent"));
		setRightChildIndent(getInt(pp+"rightChildIndent"));
		updateDepthOffset();
		treeState = new FixedHeightLayoutCache();
		treeState.setModel(tree.getModel());
		treeState.setSelectionModel(tree.getSelectionModel());
		treeState.setNodeDimensions(this);
		treeState.setRowHeight(tree.getRowHeight());
		editor = tree.getCellEditor();
		setRootVisible(tree.isRootVisible());

		expandControl =(ExpandControl) getInstance(pp+"expandControl");
	}

	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(tree);
	}

	protected void  installComponents (){
		rendererPane = new CellPane();
		rendererPane.setLayout(new EmptyLayout());
		tree.append(rendererPane);
	}

	protected void  uninstallComponents (){
		tree.remove(rendererPane);
		cells.clear();
		rendererPane = null;
	}

	protected void  installListeners (){
		tree.addEventListener(TreeEvent.TREE_EXPANDED, __treeExpanded);
		tree.addEventListener(TreeEvent.TREE_COLLAPSED, __treeCollapsed);
		tree.addStateListener(__viewportStateChanged);
		tree.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __propertyChanged);
		tree.addEventListener(MouseEvent.MOUSE_DOWN, __onPressed);
		tree.addEventListener(MouseEvent.CLICK, __onReleased);
		tree.addEventListener(ClickCountEvent.CLICK_COUNT, __onClicked);
		tree.addEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
		tree.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);

		setModel(tree.getModel());
		setSelectionModel(tree.getSelectionModel());
	}

	protected void  uninstallListeners (){
		tree.removeEventListener(TreeEvent.TREE_EXPANDED, __treeExpanded);
		tree.removeEventListener(TreeEvent.TREE_COLLAPSED, __treeCollapsed);
		tree.removeStateListener(__viewportStateChanged);
		tree.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, __propertyChanged);
		tree.removeEventListener(MouseEvent.MOUSE_DOWN, __onPressed);
		tree.removeEventListener(MouseEvent.CLICK, __onReleased);
		tree.removeEventListener(ClickCountEvent.CLICK_COUNT, __onClicked);
		tree.removeEventListener(MouseEvent.MOUSE_WHEEL, __onMouseWheel);
		tree.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onKeyDown);

		setModel(null);
		setSelectionModel(null);
	}

	protected void  setModel (TreeModel tm ){
		cancelEditing(tree);
		if(treeModel != null){
			treeModel.removeTreeModelListener(this);
		}
		treeModel = tm;
		if(treeModel != null) {
			treeModel.addTreeModelListener(this);
		}
		if(treeState != null) {
			treeState.setModel(tm);
			updateLayoutCacheExpandedNodes();
			updateSize();
		}
	}

	protected void  setSelectionModel (TreeSelectionModel sm ){
		if(selectionModel != null){
			selectionModel.removePropertyChangeListener(__selectionModelPropertyChanged);
			selectionModel.removeTreeSelectionListener(__selectionChanged);
		}
		selectionModel = sm;
		if(selectionModel != null){
			selectionModel.addPropertyChangeListener(__selectionModelPropertyChanged);
			selectionModel.addTreeSelectionListener(__selectionChanged);
		}
		if(treeState != null){
			treeState.setSelectionModel(selectionModel);
		}
		tree.repaint();
	}

	/**
	 * Sets the root to being visible.
	 */
	protected void  setRootVisible (boolean newValue ){
		cancelEditing(tree);
		updateDepthOffset();
		if(treeState != null) {
			treeState.setRootVisible(newValue);
			treeState.invalidateSizes();
			updateSize();
		}
	}

	/**
	 * Sets the row height, this is forwarded to the treeState.
	 */
	protected void  setRowHeight (int rowHeight ){
		cancelEditing(tree);
		if(treeState != null) {
			treeState.setRowHeight(rowHeight);
			updateSize();
		}
	}

	protected void  setCellEditor (TreeCellEditor editor ){
		cancelEditing(tree);
		this.editor = editor;
	}

	/**
	 * Configures the receiver to allow, or not allow, editing.
	 */
	protected void  setEditable (boolean newValue ){
		cancelEditing(tree);
		if(newValue){
			editor = tree.getCellEditor();
		}else{
			editor = null;
		}
	}

	protected void  repaintPath (TreePath path ){
	}

	protected void  cellFactoryChanged (){
		for(int i =cells.size ()-1;i >=0;i --){
			TreeCell cell =TreeCell(cells.get(i ));
			cell.getCellComponent().removeFromContainer();
		}
		cells.clear();
		treeState.invalidateSizes();
		updateSize();
	}

	/**
	 * Makes all the nodes that are expanded in JTree expanded in LayoutCache.
	 * This invokes updateExpandedDescendants with the root path.
	 */
	protected void  updateLayoutCacheExpandedNodes (){
		if(treeModel != null && treeModel.getRoot() != null){
			updateExpandedDescendants(new TreePath(.get(treeModel.getRoot())));
		}
	}

	/**
	 * Returns true if <code>mouseX</code> and <code>mouseY</code> fall
	 * in the area of row that is used to expand/collapse the node and
	 * the node at <code>row</code> does not represent a leaf.
	 */
	protected boolean  isLocationInExpandControl (TreePath path ,int mouseX ,int mouseY ){
		if(path != null && !treeModel.isLeaf(path.getLastPathComponent())){
			int boxWidth ;
		   //Insets i =tree.getInsets ();
			boxWidth = leftChildIndent;

			int boxLeftX =getRowX(tree.getRowForPath(path ),path.getPathCount ()-1)-boxWidth ;
			//boxLeftX += i.left;
			int boxRightX =boxLeftX +boxWidth ;

			return mouseX >= boxLeftX && mouseX <= boxRightX;
		}
		return false;
	}

	/**
	 * Expands path if it is not expanded, or collapses row if it is expanded.
	 * If expanding a path and JTree scrolls on expand, ensureRowsAreVisible
	 * is invoked to scroll as many of the children to visible as possible
	 * (tries to scroll to last visible descendant of path).
	 */
	protected void  toggleExpandState (TreePath path ){
		if(!tree.isExpanded(path)) {
			int row =getRowForPath(tree ,path );
			tree.expandPath(path);
			updateSize();
			if(row != -1) {
				if(tree.isScrollsOnExpand()){
					ensureRowsAreVisible(row, row + treeState.getVisibleChildCount(path));
				}else{
					ensureRowsAreVisible(row, row);
				}
			}
		}else{
			tree.collapsePath(path);
			updateSize();
		}
	}

	/**
	  * Ensures that the rows identified by beginRow through endRow are
	  * visible.
	  */
	protected void  ensureRowsAreVisible (int beginRow ,int endRow ){
		if(tree != null && beginRow >= 0 && endRow < getRowCount(tree)) {
			tree.scrollRowToVisible(endRow);
			tree.scrollRowToVisible(beginRow);
		}
	}

	/**
	 * Messaged when the user clicks the particular row, this invokes
	 * toggleExpandState.
	 */
	protected void  handleExpandControlClick (TreePath path ,int mouseX ,int mouseY ){
		toggleExpandState(path);
	}

	protected void  selectPathForEvent (TreePath path ,MouseEvent e ){
		doSelectWhenRelease = false;
		pressedPath = path;
		if(tree.isPathSelected(path)){
			doSelectWhenRelease = true;
		}else{
			doSelectPathForEvent(e);
		}
		paintFocusedIndex = tree.getRowForPath(path);
	}

	protected void  doSelectPathForEvent (MouseEvent e ){
		TreePath path =pressedPath ;
		boolean ctrl =false ;
		boolean shift =false ;
		ctrl = e.ctrlKey;
		shift = e.shiftKey;
		if(shift) {
			TreePath anchor =tree.getAnchorSelectionPath ();
			int anchorRow =(anchor ==null )? -1 : getRowForPath(tree, anchor);

			if(anchorRow == -1 || selectionModel.getSelectionMode() == JTree.SINGLE_TREE_SELECTION) {
				tree.setSelectionPath(path, false);
			}else {
				int row =getRowForPath(tree ,path );
				TreePath lastAnchorPath =anchor ;

				if (ctrl) {
					if (tree.isRowSelected(anchorRow)) {
						tree.addSelectionInterval(anchorRow, row, false);
					} else {
						tree.removeSelectionInterval(anchorRow, row, false);
						tree.addSelectionInterval(row, row, false);
					}
				} else if(row < anchorRow) {
					tree.setSelectionInterval(row, anchorRow, false);
				} else {
					tree.setSelectionInterval(anchorRow, row, false);
				}
				ignoreLAChange = true;
				//lastSelectedRow = row;
				tree.setAnchorSelectionPath(lastAnchorPath);
				tree.setLeadSelectionPath(path);
				ignoreLAChange = false;
			}
		}else if(ctrl) {
			// Should this event toggle the selection of this row?
			/* Control toggles just this node. */
			if(tree.isPathSelected(path)){
				tree.removeSelectionPath(path, false);
			}else{
				tree.addSelectionPath(path, false);
			}
			//lastSelectedRow = getRowForPath(tree, path);
			ignoreLAChange = true;
			tree.setAnchorSelectionPath(path);
			tree.setLeadSelectionPath(path);
			ignoreLAChange = false;
		}else{
			tree.setSelectionPath(path, false);
		}
	}

	//------------------------------handlers------------------------
	private void  __selectionModelPropertyChanged (PropertyChangeEvent e ){
		selectionModel.resetRowSelection();
	}
	private void  __selectionChanged (TreeSelectionEvent event ){
		// Stop editing
		stopEditing(tree);
		// Make sure all the paths are visible, if necessary.
		if(tree.isExpandsSelectedPaths() && selectionModel != null) {
			Array paths =selectionModel.getSelectionPaths ();

			if(paths != null) {
				for(int counter =paths.length -1;counter >=0;counter --){
					TreePath path =paths.get(counter).getParentPath ();
					boolean expand =true ;

					while (path != null) {
						// Indicates this path isn't valid anymore,
						// we shouldn't attempt to expand it then.
						if (treeModel.isLeaf(path.getLastPathComponent())){
							expand = false;
							path = null;
						}else {
							path = path.getParentPath();
						}
					}
					if (expand) {
						tree.makePathVisible(paths.get(counter));
					}
				}
			}
		}

		paintFocusedIndex = tree.getMinSelectionRow();
		TreePath lead =tree.getSelectionModel ().getLeadSelectionPath ();
		ignoreLAChange = true;
		tree.setAnchorSelectionPath(lead);
		tree.setLeadSelectionPath(lead);
		ignoreLAChange = false;
		tree.repaint();
	}

	private void  __onClicked (ClickCountEvent e ){
		boolean edit =(tree.isEditable ()&& editor != null && editor.isCellEditable(e.getCount()));
		boolean toggle =(e.getCount ()==tree.getToggleClickCount ());
		if(!(edit || toggle)){
			return;
		}
		IntPoint p =rendererPane.getMousePosition ();
		p.y += tree.getViewPosition().y;
		TreePath path =getClosestPathForLocation(tree ,p.x ,p.y );
		if(path != null){
			IntRectangle bounds =getPathBounds(tree ,path );
			if (p.x > bounds.x && p.x <= (bounds.x + bounds.width)) {
				if(edit){
					tree.startEditingAtPath(path);
				}else{
					toggleExpandState(path);
				}
			}
		}
	}

	private void  __onPressed (MouseEvent e ){
		IntPoint p =rendererPane.getMousePosition ();
		p.y += tree.getViewPosition().y;
		TreePath path =getClosestPathForLocation(tree ,p.x ,p.y );
		if(path != null){
			if(isLocationInExpandControl(path, p.x, p.y)){
				handleExpandControlClick(path, p.x, p.y);
			}
			IntRectangle bounds =getPathBounds(tree ,path );
			if (p.x > bounds.x && p.x <= (bounds.x + bounds.width)) {
			   selectPathForEvent(path, e);
			}
		}
	}

	private boolean doSelectWhenRelease ;
	private TreePath pressedPath ;

	private void  __onReleased (MouseEvent e ){
		if(doSelectWhenRelease){
			doSelectPathForEvent(e);
			doSelectWhenRelease = false;
		}
	}

	private void  __onMouseWheel (MouseEvent e ){
		IntPoint pos =tree.getViewPosition ();
		if(e.shiftKey){
			pos.x -= tree.getHorizontalUnitIncrement()*e.delta;
		}else{
			pos.y -= tree.getVerticalUnitIncrement()*e.delta;
		}
		tree.setViewPosition(pos);
	}

	private int paintFocusedIndex =-1;
	private void  __onKeyDown (FocusKeyEvent e ){
		if(!tree.isEnabled()){
			return;
		}
		int code =e.keyCode ;
		int dir =0;
		if(isControlKey(code)){
    		FocusManager fm =FocusManager.getManager(tree.stage );
			if(fm) fm.setTraversing(true);
		}else{
			return;
		}
		if(code == Keyboard.UP){
			dir = -1;
		}else if(code == Keyboard.DOWN){
			dir = 1;
		}

		if(paintFocusedIndex == -1){
			paintFocusedIndex = tree.getSelectionModel().getMinSelectionRow();
		}
		if(paintFocusedIndex < -1){
			paintFocusedIndex = -1;
		}else if(paintFocusedIndex > tree.getRowCount()){
			paintFocusedIndex = tree.getRowCount();
		}
		int index =paintFocusedIndex +dir ;
		if(code == Keyboard.HOME){
			index = 0;
		}else if(code == Keyboard.END){
			index = tree.getRowCount() - 1;
		}
		if(index < 0 || index >= tree.getRowCount()){
			return;
		}
		TreePath path =tree.getPathForRow(index );
		if(code == Keyboard.LEFT){
			tree.collapseRow(index);
		}else if(code == Keyboard.RIGHT){
			tree.expandRow(index);
		}else if(dir != 0 || (code == Keyboard.HOME || code == Keyboard.END)){
			if(e.shiftKey){
				TreePath anchor =tree.getAnchorSelectionPath ();
				int anchorRow =(anchor ==null )? -1 : getRowForPath(tree, anchor);
				TreePath lastAnchorPath =anchor ;
				if(index < anchorRow) {
					tree.setSelectionInterval(index, anchorRow);
				} else {
					tree.setSelectionInterval(anchorRow, index);
				}
				ignoreLAChange = true;
				tree.setAnchorSelectionPath(lastAnchorPath);
				tree.setLeadSelectionPath(path);
				ignoreLAChange = false;

				paintFocusedIndex = index;
			}else if(e.ctrlKey){
				paintFocusedIndex = index;
			}else{
				tree.setSelectionInterval(index, index);
			}
			tree.scrollRowToVisible(index);
		}else{
			if(code == Keyboard.SPACE){
				tree.addSelectionInterval(index, index);
				tree.scrollRowToVisible(index);
				ignoreLAChange = true;
				tree.setAnchorSelectionPath(path);
				tree.setLeadSelectionPath(path);
				ignoreLAChange = false;
			}else if(code == getEditionKey()){
				boolean edit =(tree.isEditable ()&& editor != null);
				if(edit){
					tree.startEditingAtPath(path);
				}
				return;
			}
		}
		tree.repaint();
	}

	protected boolean  isControlKey (int code ){
		return (code == Keyboard.UP || code == Keyboard.DOWN || code == Keyboard.SPACE
			|| code == Keyboard.LEFT || code == Keyboard.RIGHT || code == Keyboard.HOME
			|| code == Keyboard.END || code == getEditionKey());
	}

	protected int  getEditionKey (){
		return Keyboard.ENTER;
	}

	private void  __viewportStateChanged (InteractiveEvent e ){
		IntPoint viewPosition =tree.getViewPosition ();
		if(!lastViewPosition.equals(viewPosition)){
			if(lastViewPosition.y == viewPosition.y){
				positRendererPaneX(viewPosition.x);
				lastViewPosition.setLocation(viewPosition);
				return;
			}
			tree.repaint();
		}
	}

	private void  __propertyChanged (PropertyChangeEvent e ){
		String changeName =e.getPropertyName ();
		ov = e.getOldValue();
		nv = e.getNewValue();
		if (changeName == JTree.LEAD_SELECTION_PATH_PROPERTY) {
			if (!ignoreLAChange) {
				updateLeadRow();
				repaintPath(TreePath(ov));
				repaintPath(TreePath(nv));
			}
		}else if (changeName == JTree.ANCHOR_SELECTION_PATH_PROPERTY) {
			if (!ignoreLAChange) {
				repaintPath(TreePath(ov));
				repaintPath(TreePath(nv));
			}
		}else if(changeName == JTree.CELL_FACTORY_PROPERTY) {
			cellFactoryChanged();
		}else if(changeName == JTree.TREE_MODEL_PROPERTY) {
			setModel(TreeModel(nv));
		}else if(changeName == JTree.ROOT_VISIBLE_PROPERTY) {
			setRootVisible(nv == true);
		}else if(changeName == JTree.ROW_HEIGHT_PROPERTY) {
			setRowHeight(nv);
		}else if(changeName == JTree.CELL_EDITOR_PROPERTY) {
			setCellEditor(TreeCellEditor(nv));
		}else if(changeName == JTree.EDITABLE_PROPERTY) {
			setEditable(nv == true);
		}else if(changeName == JTree.SELECTION_MODEL_PROPERTY) {
			setSelectionModel(tree.getSelectionModel());
		}else if(changeName == JTree.FONT_PROPERTY) {
			cancelEditing(tree);
			if(treeState != null)
				treeState.invalidateSizes();
			updateSize();
		}
	}

	protected void  positRendererPaneX (int viewX ){
		rendererPane.setX(tree.getInsets().left - viewX);
		rendererPane.validate();
	}

	//---------------------------------------------------
	public void  setLeftChildIndent (int newAmount ){
		leftChildIndent = newAmount;
		totalChildIndent = leftChildIndent + rightChildIndent;
		if(treeState != null)
			treeState.invalidateSizes();
		updateSize();
	}
	public int  getLeftChildIndent (){
		return leftChildIndent;
	}

	public void  setRightChildIndent (int newAmount ){
		rightChildIndent = newAmount;
		totalChildIndent = leftChildIndent + rightChildIndent;
		if(treeState != null)
			treeState.invalidateSizes();
		updateSize();
	}

	public int  getRightChildIndent (){
		return rightChildIndent;
	}

	/**
	 * Updates how much each depth should be offset by.
	 */
	protected void  updateDepthOffset (){
		if(tree.isRootVisible()) {
			depthOffset = 1;
		}else{
			depthOffset = 0;
		}
	}

	/**
	 * Marks the cached size as being invalid, and messages the
	 * tree with <code>treeDidChange</code>.
	 */
	protected void  updateSize (){
		validCachedViewSize = false;
		tree.treeDidChange();
	}

	//**********************************************************************
	//						Paint methods
	//**********************************************************************
	 public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
		IntRectangle ib =treeState.getBounds(tree.getPathForRow(paintFocusedIndex ),null );
		if(ib != null){
			b = ib;
			b.setLocation(tree.getPixelLocationFromLogicLocation(b.getLocation()));
		}

		g.drawRectangle(new Pen(getDefaultFocusColorInner(), 1), b.x+0.5, b.y+0.5, b.width-1, b.height-1);
		g.drawRectangle(new Pen(getDefaultFocusColorOutter(), 1), b.x+1.5, b.y+1.5, b.width-3, b.height-3);
	}

	private Shape rendererShape ;
	protected Graphics2D  createRendererPaneGraphics (){
		if(rendererShape == null){
			rendererShape = new Shape();
			rendererPane.addChild(rendererShape);
		}
		rendererShape.graphics.clear();
		return new Graphics2D(rendererShape.graphics);
	}

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);

		IntDimension viewSize =getViewSize(tree );
		rendererPane.setComBoundsXYWH(0, b.y, viewSize.width, b.height);
		rendererPane.validate();
		checkCreateCells();
		IntPoint viewPosition =tree.getViewPosition ();
		lastViewPosition.setLocation(viewPosition);
		int x =viewPosition.x ;
		int y =viewPosition.y ;
		int ih =tree.getRowHeight ();
		int startIndex =y /ih ;
		int startY =startIndex *ih -y ;
		int rowCount =getRowCount(tree );

		positRendererPaneX(x);

		int cy =startY ;

		IntRectangle showBounds =b.clone ();
		showBounds.y = y;
		showBounds.x = x;
		int showRowCount =Math.min(cells.size (),rowCount );
		TreePath initialPath =getClosestPathForLocation(tree ,0,showBounds.y );
		Array paintingEnumerator =treeState.getVisiblePathsFrom(initialPath ,showRowCount );
		if(paintingEnumerator == null) paintingEnumerator = new Array();
		int row =treeState.getRowContainingYLocation(showBounds.y );

		boolean expanded ;
		boolean leaf ;
		boolean selected ;
		IntRectangle bounds =new IntRectangle ();
		IntRectangle boundsBuffer =new IntRectangle ();
		TreeModel treeModel =tree.getModel ();
		g = createRendererPaneGraphics();
		int n =cells.getSize ();
		int paintingN =paintingEnumerator.length ;
		for(int i =0;i <n ;i ++){
			TreeCell cell =cells.get(i );
			TreePath path =paintingEnumerator.get(i) ;
			Component cellCom =cell.getCellComponent ();
			if(i < paintingN){
				leaf = treeModel.isLeaf(path.getLastPathComponent());
				if(leaf){
					expanded = false;
				}else {
					expanded = treeState.getExpandedState(path);
				}
				selected = tree.getSelectionModel().isPathSelected(path);
				//trace("cell path = " + path);
				bounds = treeState.getBounds(path, bounds);
				//trace("bounds : " + bounds);
				cell.setCellValue(path.getLastPathComponent());
				//trace(path.getLastPathComponent() + " cell value of " + cell);
				cellCom.setVisible(true);
				cell.setTreeCellStatus(tree, selected, expanded, leaf, row);
				boundsBuffer.setRectXYWH(bounds.x, cy, bounds.width, ih);
				cellCom.setBounds(boundsBuffer);
				cellCom.validate();
				cellCom.paintImmediately();
				boundsBuffer.x += b.x;
				boundsBuffer.y += b.y;
				paintExpandControl(g, boundsBuffer, path, row, expanded, leaf);

				cy += ih;
				row++;
			}else{
				cellCom.setVisible(false);
				cellCom.validate();
			}
		}
	}

	protected  paintExpandControl (Graphics2D g ,IntRectangle bounds ,TreePath path ,
					  row:int, expanded:Boolean, leaf:Boolean):void{
		if(expandControl){
			expandControl.paintExpandControl(tree, g, bounds, totalChildIndent, path, row, expanded, leaf);
		}
	}

	protected void  checkCreateCells (){
		int ih =tree.getRowHeight ();
		int needNum =Math.floor(tree.getExtentSize ().height /ih )+2;

		if(cells.getSize() == needNum/* || !displayable*/){
			return;
		}
		int i ;
		TreeCell cell ;
		//create needed
		if(cells.getSize() < needNum){
			int addNum =needNum -cells.getSize ();
			for(i=0; i<addNum; i++){
				cell = tree.getCellFactory().createNewCell();
				rendererPane.append(cell.getCellComponent());
				cells.append(cell);
			}
		}else if(cells.getSize() > needNum){ //remove mored
			int removeIndex =needNum ;
			Array removed =cells.removeRange(removeIndex ,cells.getSize ()-1);
			for(i=0; i<removed.length; i++){
				cell = TreeCell(removed.get(i));
				rendererPane.remove(cell.getCellComponent());
			}
		}
	}

	//---------------------------------------
	protected void  updateLeadRow (){
		paintFocusedIndex = getRowForPath(tree, tree.getLeadSelectionPath());
	}

	/**
	 * Returns the location, along the x-axis, to render a particular row
	 * at. The return value does not include any Insets specified on the JTree.
	 * This does not check for the validity of the row or depth, it is assumed
	 * to be correct and will not throw an Exception if the row or depth
	 * doesn't match that of the tree.
	 *
	 * @param row Row to return x location for
	 * @param depth Depth of the row
	 * @return amount to indent the given row.
	 */
	protected int  getRowX (int row ,int depth ){
		return totalChildIndent * (depth + depthOffset);
	}

	/**
	 * Updates the expanded state of all the descendants of <code>path</code>
	 * by getting the expanded descendants from the tree and forwarding
	 * to the tree state.
	 */
	protected void  updateExpandedDescendants (TreePath path ){
		//completeEditing();
		if(treeState != null) {
			treeState.setExpandedState(path, true);

			Array descendants =tree.getExpandedDescendants(path );

			if(descendants != null) {
				for(int i =0;i <descendants.length ;i ++){
					treeState.setExpandedState(TreePath(descendants.get(i)), true);
				}
			}
			updateLeadRow();
			updateSize();
		}
	}

	//**********************************************************************
	//						NodeDimensions methods
	//**********************************************************************
	protected TreeCell currentCellRenderer ;
	public IntRectangle  countNodeDimensions (*value ,int row ,int depth ,boolean expanded ,IntRectangle size ){
		IntDimension prefSize ;
		if(tree.getFixedCellWidth() >= 0){
			prefSize = new IntDimension(tree.getFixedCellWidth(), tree.getRowHeight());
		}else{
			if(currentCellRenderer == null){
				currentCellRenderer = tree.getCellFactory().createNewCell();
			}
			currentCellRenderer.setCellValue(value);
			//TODO check this selected is needed
			currentCellRenderer.setTreeCellStatus(tree, /*selected*/false, expanded, tree.getModel().isLeaf(value), row);

			prefSize = currentCellRenderer.getCellComponent().getPreferredSize();
		}
		if(size != null) {
			size.x = getRowX(row, depth);
			size.width = prefSize.width;
			size.height = prefSize.height;
		}else {
			size = new IntRectangle(getRowX(row, depth), 0,
					 prefSize.width, prefSize.height);
		}
		return size;
	}
	//**********************************************************************
	//						TreeUI methods
	//**********************************************************************

	/**
	  * Returns the IntRectangle enclosing the label portion that the
	  * last item in path will be drawn into.  Will return null if
	  * any component in path is currently valid.
	  */
	public IntRectangle  getPathBounds (JTree tree ,TreePath path ){
		if(tree != null && treeState != null) {
			Insets i =tree.getInsets ();
			IntRectangle bounds =treeState.getBounds(path ,null );

			if(bounds != null && i != null) {
				bounds.x += i.left;
				bounds.y += i.top;
			}
			return bounds;
		}
		return null;
	}

	/**
	  * Returns the path for passed in row.  If row is not visible
	  * null is returned.
	  */
	public TreePath  getPathForRow (JTree tree ,int row ){
		return (treeState != null) ? treeState.getPathForRow(row) : null;
	}

	/**
	  * Returns the row that the last item identified in path is visible
	  * at.  Will return -1 if any of the elements in path are not
	  * currently visible.
	  */
	public int  getRowForPath (JTree tree ,TreePath path ){
		return (treeState != null) ? treeState.getRowForPath(path) : -1;
	}

	/**
	  * Returns the number of rows that are being displayed.
	  */
	public int  getRowCount (JTree tree ){
		return (treeState != null) ? treeState.getRowCount() : 0;
	}

	/**
	  * Returns the path to the node that is closest to x,y.  If
	  * there is nothing currently visible this will return null, otherwise
	  * it'll always return a valid path.  If you need to test if the
	  * returned object is exactly at x, y you should get the bounds for
	  * the returned path and test x, y against that.
	  */
	public TreePath  getClosestPathForLocation (JTree tree ,int x ,int y ){
		if(tree != null && treeState != null) {
			Insets i =tree.getInsets ();

			if(i == null){
				i = EMPTY_INSETS;
			}

			return treeState.getPathClosestTo(x - i.left, y - i.top);
		}
		return null;
	}

	/**
	 * Returns the treePath that the user mouse pointed, null if no path was pointed.
	 */
	public TreePath  getMousePointedPath (){
		IntPoint p =rendererPane.getMousePosition ();
		p.y += tree.getViewPosition().y;
		TreePath path =getClosestPathForLocation(tree ,p.x ,p.y );
		return path;
	}

	/**
	  * Returns true if the tree is being edited.  The item that is being
	  * edited can be returned by getEditingPath().
	  */
	public boolean  isEditing (JTree tree ){
		return editor.isCellEditing();
	}

	/**
	  * Stops the current editing session.  This has no effect if the
	  * tree isn't being edited.  Returns true if the editor allows the
	  * editing session to stop.
	  */
	public boolean  stopEditing (JTree tree ){
		if(editor != null && editor.isCellEditing()) {
			return editor.stopCellEditing();
		}
		return false;
	}

	/**
	  * Cancels the current editing session.
	  */
	public void  cancelEditing (JTree tree ){
		if(editor != null && editor.isCellEditing()) {
			editor.cancelCellEditing();
		}
	}

	/**
	  * Selects the last item in path and tries to edit it.  Editing will
	  * fail if the CellEditor won't allow it for the selected item.
	  *
	  * @return true is started sucessful, editing fail
	  */
	public boolean  startEditingAtPath (JTree tree ,TreePath path ){
		if(editor == null){
			return false;
		}
		tree.scrollPathToVisible(path);
		if(path != null && tree.isPathVisible(path)){
			TreeCellEditor editor =tree.getCellEditor ();
			if (editor.isCellEditing()){
				if(!editor.stopCellEditing()){
					return false;
				}
			}
			editingPath = path;
			IntRectangle bounds =tree.getPathBounds(path );
			bounds.setLocation(tree.getPixelLocationFromLogicLocation(bounds.getLocation()));
			editor.startCellEditing(tree, path.getLastPathComponent(), bounds);
			return true;
		}
		return false;
	}
	protected TreePath editingPath ;

	/**
	 * Returns the path to the element that is being edited.
	 */
	public TreePath  getEditingPath (JTree tree ){
		return editingPath;
	}

	//******************************************************************
	//							 Size Methods
	//******************************************************************

	protected void  updateCachedViewSize (){
		if(treeState != null) {
			viewSize.width = treeState.getPreferredWidth(null);
			viewSize.height = treeState.getPreferredHeight();
		}
		validCachedViewSize = true;
	}

	 public IntDimension  getMinimumSize (Component c ){
		return c.getInsets().getOutsideSize();
	}

	 public IntDimension  getPreferredSize (Component c ){
		int height =tree.getVisibleRowCount ()*tree.getRowHeight ();
		int width =getViewSize(tree ).width ;
		return c.getInsets().getOutsideSize(new IntDimension(width, height));
	}

	public IntDimension  getViewSize (JTree theTree ){
		if(!validCachedViewSize){
			updateCachedViewSize();
		}
		if(tree != null) {
			return new IntDimension(viewSize.width, viewSize.height);
		}else{
			return new IntDimension(0, 0);
		}
	}

	 public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
	}
		//
		// TreeExpansionListener
		//
	public void  __treeExpanded (TreeEvent e ){
		if(e.getPath() != null) {
			updateExpandedDescendants(e.getPath());
		}
	}

	public void  __treeCollapsed (TreeEvent e ){
		if(e.getPath() != null) {
			//completeEditing();
			if(e.getPath() != null && tree.isPathVisible(e.getPath())) {
				treeState.setExpandedState(e.getPath(), false);
				updateLeadRow();
				updateSize();
			}
		}
	}

	//******************************************************************
	//							 TreeModelListener Methods
	//******************************************************************

	public void  treeNodesChanged (TreeModelEvent e ){
		if(treeState != null && e != null) {
			treeState.treeNodesChanged(e);

			TreePath pPath =e.getTreePath ().getParentPath ();

			if(pPath == null || treeState.isExpanded(pPath)){
				updateSize();
			}
		}
	}

	public void  treeNodesInserted (TreeModelEvent e ){
		if(treeState != null && e != null) {
			treeState.treeNodesInserted(e);

			updateLeadRow();

			TreePath path =e.getTreePath ();

			if(treeState.isExpanded(path)) {
				updateSize();
			}else {
				// PENDING(sky): Need a method in TreeModelEvent
				// that can return the count, getChildIndices allocs
				// a new array!
				Array indices =e.getChildIndices ();
				int childCount =tree.getModel ().getChildCount(path.getLastPathComponent ());

				if(indices != null && (childCount - indices.length()) == 0){
					updateSize();
				}
			}
		}
	}

	public void  treeNodesRemoved (TreeModelEvent e ){
		if(treeState != null && e != null) {
			treeState.treeNodesRemoved(e);

			updateLeadRow();

			TreePath path =e.getTreePath ();

			if(treeState.isExpanded(path) || tree.getModel().getChildCount(path.getLastPathComponent()) == 0){
				updateSize();
			}
		}
	}

	public void  treeStructureChanged (TreeModelEvent e ){
		if(treeState != null && e != null) {
			treeState.treeStructureChanged(e);

			updateLeadRow();

			TreePath pPath =e.getTreePath ();

			if (pPath != null) {
				pPath = pPath.getParentPath();
			}
			if(pPath == null || treeState.isExpanded(pPath)){
				updateSize();
			}
		}
	}

	public String  toString (){
		return "BasicTreeUI[]";
	}
}


