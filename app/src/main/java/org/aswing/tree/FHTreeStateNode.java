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


import org.aswing.tree.DefaultMutableTreeNode;
import org.aswing.tree.FixedHeightLayoutCache;
import org.aswing.tree.MutableTreeNode;
import org.aswing.tree.SearchInfo;
import org.aswing.tree.TreeModel;
import org.aswing.tree.TreePath;

/**
 * FHTreeStateNode is used to track what has been expanded.
 * FHTreeStateNode differs from VariableHeightTreeState.TreeStateNode
 * in that it is highly model intensive. That is almost all queries to a
 * FHTreeStateNode result in the TreeModel being queried. And it
 * obviously does not support variable sized row heights.
 * @author iiley
 */
public class FHTreeStateNode extends DefaultMutableTreeNode {
	

	/** Is this node expanded? */
	private boolean expanded ;

	/** Index of this node from the model. */
	private int childIndex ;

  	/** Child count of the receiver. */
  	private int childCount ;
 	
	/** Row of the receiver. This is only valid if the row is expanded.
	 */
	private int row ;

	/** Path of this node. */
	private TreePath path ;
	
	private FixedHeightLayoutCache layoutCache ;

	public  FHTreeStateNode (FixedHeightLayoutCache layoutCache ,Object userObject ,int childIndex ,int row ){
	    super(userObject);
	    this.childIndex = childIndex;
	    this.row = row;
	    this.layoutCache = layoutCache;
	    childCount = 0;
	    expanded = false;
	}
	
	public void  setPath (TreePath p ){
		path = p;
	}
	
	public void  setRow (int r ){
		row = r;
	}
	
	//
	// Overriden DefaultMutableTreeNode methods
	//

	/**
	 * Messaged when this node is added somewhere, resets the path
	 * and adds a mapping from path to this node.
	 */
	 public void  setParent (MutableTreeNode parent ){
	    super.setParent(parent);
	    if(parent != null) {
			path = (FHTreeStateNode(parent)).getTreePath().pathByAddingChild(getUserObject());
			layoutCache.addMapping(this);
	    }
	}

	/**
	 * Messaged when this node is removed from its parent, this messages
	 * <code>removedFromMapping</code> to remove all the children.
	 */
	 public void  removeAt (int childIndex ){
	    FHTreeStateNode node =FHTreeStateNode(getChildAt(childIndex ));

	    node.removeFromMapping();
	    super.removeAt(childIndex);
	}

	/**
	 * Messaged to set the user object. This resets the path.
	 */
	 public void  setUserObject (*)o {
	    super.setUserObject(o);
	    if(path != null) {
			FHTreeStateNode parent =FHTreeStateNode(getParent ());
	
			if(parent != null)
			    resetChildrenPaths(parent.getTreePath());
			else
		    	resetChildrenPaths(null);
	    }
	}

	//
	//

	/**
	 * Returns the index of the receiver in the model.
	 */
	public int  getChildIndex (){
	    return childIndex;
	}

	/**
	 * Returns the <code>TreePath</code> of the receiver.
	 */
	public TreePath  getTreePath (){
	    return path;
	}

	/**
	 * Returns the child for the passed in model index, this will
	 * return <code>null</code> if the child for <code>index</code>
         * has not yet been created (expanded).
	 */
	public FHTreeStateNode  getChildAtModelIndex (int index ){
	    // PENDING: Make this a binary search!
	    for(int counter =getChildCount ()-1;counter >=0;counter --){
			if((FHTreeStateNode(getChildAt(counter))).childIndex == index)
			    return FHTreeStateNode(getChildAt(counter));
	    }
	    return null;
	}

	/**
	 * Returns true if this node is visible. This is determined by
	 * asking all the parents if they are expanded.
	 */
	public boolean  isVisible (){
	    FHTreeStateNode parent =FHTreeStateNode(getParent ());

	    if(parent == null){
			return true;
	    }
	    return (parent.isExpanded() && parent.isVisible());
	}

	/**
	 * Returns the row of the receiver.
	 */
	public int  getRow (){
	    return row;
	}

	/**
	 * Returns the row of the child with a model index of
	 * <code>index</code>.
	 */
	public int  getRowToModelIndex (int index ){
	    FHTreeStateNode child ;

	    // This too could be a binary search!
	    int counter =0;
	    int maxCounter =getChildCount ();
	    for(; counter < maxCounter; counter++) {
			child = FHTreeStateNode(getChildAt(counter));
			if(child.childIndex >= index) {
			    if(child.childIndex == index)
					return child.row;
			    if(counter == 0)
					return getRow() + 1 + index;
			    return child.row - (child.childIndex - index);
			}
		}
	    // YECK!
	    return getRow() + 1 + getTotalChildCount() - (childCount - index);
	}

	/**
	 * Returns the number of children in the receiver by descending all
	 * expanded nodes and messaging them with getTotalChildCount.
	 */
	public int  getTotalChildCount (){
	    if(isExpanded()) {
			FHTreeStateNode parent =FHTreeStateNode(getParent ());
			int pIndex ;
	
			if(parent != null && (pIndex = parent.getIndex(this)) + 1 < parent.getChildCount()) {
			    // This node has a created sibling, to calc total
			    // child count directly from that!
			    FHTreeStateNode nextSibling =FHTreeStateNode(parent.getChildAt(pIndex +1));
	
			    return nextSibling.row - row - (nextSibling.childIndex - childIndex);
			}else{
	 		    int retCount =childCount ;
	
			    for(int counter =getChildCount ()-1;counter >=0;counter --){
					retCount += (FHTreeStateNode(getChildAt(counter))).getTotalChildCount();
			    }
			    return retCount;
			}
	    }
	    return 0;
	}

	/**
	 * Returns true if this node is expanded.
	 */
	public boolean  isExpanded (){
	    return expanded;
	}

	/**
	 * The highest visible nodes have a depth of 0.
	 */
	public int  getVisibleLevel (){
	    if (layoutCache.isRootVisible()) {
			return getLevel();
	    } else {
			return getLevel() - 1;
	    }
	}

	/**
	 * Recreates the receivers path, and all its childrens paths.
	 */
	private void  resetChildrenPaths (TreePath parentPath ){
	    layoutCache.removeMapping(this);
	    if(parentPath == null)
			path = new TreePath(.get(getUserObject()));
	    else
			path = parentPath.pathByAddingChild(getUserObject());
	    layoutCache.addMapping(this);
	    for(int counter =getChildCount ()-1;counter >=0;counter --){
			(FHTreeStateNode(getChildAt(counter))).resetChildrenPaths(path);
	    }
	}

	/**
	 * Removes the receiver, and all its children, from the mapping
	 * table.
	 */
	private void  removeFromMapping (){
	    if(path != null) {
			layoutCache.removeMapping(this);
			for(int counter =getChildCount ()-1;counter >=0;counter --){
			    (FHTreeStateNode(getChildAt(counter))).removeFromMapping();
			}
	    }
	}

	/**
	 * Creates a new node to represent <code>userObject</code>.
	 * This does NOT check to ensure there isn't already a child node
	 * to manage <code>userObject</code>.
	 */
	public FHTreeStateNode  createChildFor (Object userObject ){
	    int newChildIndex =layoutCache.getModel ().getIndexOfChild(getUserObject (),userObject );

	    if(newChildIndex < 0)
			return null;

	    FHTreeStateNode aNode ;
	    FHTreeStateNode child =layoutCache.createNodeForValue(userObject ,newChildIndex );
	    int childRow ;

	    if(isVisible()) {
			childRow = getRowToModelIndex(newChildIndex);
	    }else {
			childRow = -1;
	    }
	    child.row = childRow;
	    int counter =0;
	    int maxCounter =getChildCount ();
	    for(; counter < maxCounter; counter++) {
			aNode = FHTreeStateNode(getChildAt(counter));
			if(aNode.childIndex > newChildIndex) {
			    insert(child, counter);
			    return child;
			}
	    }
	    append(child);
	    return child;
	}

	/**
	 * adjustRowBy(amount:int, startIndex:int)<br>
	 * Adjusts this node, its child, and its parent starting at
	 * an index of <code>index</code> index is the index of the child
	 * to start adjusting from, which is not necessarily the model
	 * index.
	 * <p>
	 * adjustRowBy(amount:int)<br>
	 * or adjustRowBy(amount:int, -2)<br>
	 * Adjusts the receiver, and all its children rows by
	 * <code>amount</code>.
	 */
	public void  adjustRowBy (int amount ,int startIndex =-2){
		int counter ;
		if(startIndex == -2){
		    row += amount;
		    if(expanded) {
				for(counter = getChildCount() - 1; counter >= 0; counter--){
				    (FHTreeStateNode(getChildAt(counter))).adjustRowBy(amount);
				}
		    }
		}else{
		    // Could check isVisible, but probably isn't worth it.
		    if(expanded) {
				// children following startIndex.
				for(counter = getChildCount() - 1; counter >= startIndex; counter--)
				    (FHTreeStateNode(getChildAt(counter))).adjustRowBy(amount);
		    }
		    // Parent
		    FHTreeStateNode parent =FHTreeStateNode(getParent ());
	
		    if(parent != null) {
				parent.adjustRowBy(amount, parent.getIndex(this) + 1);
		    }
		}
	}

	/**
	 * Messaged when the node has expanded. This updates all of
	 * the receivers children rows, as well as the total row count.
	 */
	private void  didExpand (){
	    int nextRow =setRowAndChildren(row );
	    FHTreeStateNode parent =FHTreeStateNode(getParent ());
	    int childRowCount =nextRow -row -1;

	    if(parent != null) {
			parent.adjustRowBy(childRowCount, parent.getIndex(this) + 1);
	    }
	    layoutCache.adjustRowCountBy(childRowCount);
	}

	/**
	 * Sets the receivers row to <code>nextRow</code> and recursively
	 * updates all the children of the receivers rows. The index the
	 * next row is to be placed as is returned.
	 */
	private int  setRowAndChildren (int nextRow ){
	    row = nextRow;

	    if(!isExpanded())
			return row + 1;

	    int lastRow =row +1;
	    int lastModelIndex =0;
	    FHTreeStateNode child ;
	    int maxCounter =getChildCount ();

	    for(int counter =0;counter <maxCounter ;counter ++){
			child = FHTreeStateNode(getChildAt(counter));
			lastRow += (child.childIndex - lastModelIndex);
			lastModelIndex = child.childIndex + 1;
			if(child.expanded) {
			    lastRow = child.setRowAndChildren(lastRow);
			}else {
			    child.row = lastRow++;
			}
	    }
	    return lastRow + childCount - lastModelIndex;
	}

	/**
	 * Resets the receivers childrens rows. Starting with the child
	 * at <code>childIndex</code> (and <code>modelIndex</code>) to
	 * <code>newRow</code>. This uses <code>setRowAndChildren</code>
	 * to recursively descend children, and uses
	 * <code>resetRowSelection</code> to ascend parents.
	 */
	// This can be rather expensive, but is needed for the collapse
	// case this is resulting from a remove (although I could fix
	// that by having instances of FHTreeStateNode hold a ref to
	// the number of children). I prefer this though, making determing
	// the row of a particular node fast is very nice!
	public  resetChildrenRowsFrom (int newRow ,int childIndex ,
					    modelIndex:int):void {
	    int lastRow =newRow ;
	    int lastModelIndex =modelIndex ;
	    FHTreeStateNode node ;
	    int maxCounter =getChildCount ();

	    for(int counter =childIndex ;counter <maxCounter ;counter ++){
			node = FHTreeStateNode(getChildAt(counter));
			lastRow += (node.childIndex - lastModelIndex);
			lastModelIndex = node.childIndex + 1;
			if(node.expanded) {
			    lastRow = node.setRowAndChildren(lastRow);
			}
			else {
			    node.row = lastRow++;
			}
	    }
	    lastRow += childCount - lastModelIndex;
	    node = FHTreeStateNode(getParent());
	    if(node != null) {
			node.resetChildrenRowsFrom(lastRow, node.getIndex(this) + 1, this.childIndex + 1);
	    }else { // This is the root, reset total ROWCOUNT!
			layoutCache.setRowCount(lastRow);
	    }
	}

	/**
	 * Makes the receiver visible, but invoking
	 * <code>expandParentAndReceiver</code> on the superclass.
	 */
	public void  makeVisible (){
	    FHTreeStateNode parent =FHTreeStateNode(getParent ());

	    if(parent != null)
			parent.expandParentAndReceiver();
	}

	/**
	 * Invokes <code>expandParentAndReceiver</code> on the parent,
	 * and expands the receiver.
	 */
	private void  expandParentAndReceiver (){
	    FHTreeStateNode parent =FHTreeStateNode(getParent ());

	    if(parent != null)
			parent.expandParentAndReceiver();
	    expand();
	}

	/**
	 * Expands the receiver.
	 */
	public void  expand (){
		//trace("try expand : " + getUserObject());
	    if(!expanded && !isLeaf()) {
			//trace("expanding ... ");
			boolean visible =isVisible ();
	
			expanded = true;
			childCount = layoutCache.getModel().getChildCount(getUserObject());
	
			if(visible) {
			    didExpand();
			}
	
			// Update the selection model.
			if(visible && layoutCache.getSelectionModel() != null) {
			    layoutCache.getSelectionModel().resetRowSelection();
			}
	    }
	}

	/**
	 * Collapses the receiver. If <code>adjustRows</code> is true,
	 * the rows of nodes after the receiver are adjusted.
	 */
	public void  collapse (boolean adjustRows ){
	    if(expanded) {
			if(isVisible() && adjustRows) {
			    int childCount =getTotalChildCount ();
	
			    expanded = false;
			    layoutCache.adjustRowCountBy(-childCount);
			    // We can do this because adjustRowBy won't descend
			    // the children.
			    adjustRowBy(-childCount, 0);
			}else{
			    expanded = false;
			}
			if(adjustRows && isVisible() && layoutCache.getSelectionModel() != null){
			    layoutCache.getSelectionModel().resetRowSelection();
			}
	    }
	}

	/**
	 * Returns true if the receiver is a leaf.
	 */
	 public boolean  isLeaf (){
        TreeModel model =layoutCache.getModel ();
        return (model != null) ? model.isLeaf(getUserObject()) : true;
	}

	/**
	 * Adds newChild to this nodes children at the appropriate location.
	 * The location is determined from the childIndex of newChild.
	 */
	/*private void  addNode (FHTreeStateNode newChild ){
	    boolean added =false ;
	    int childIndex =newChild.getChildIndex ();

	    int counter =0;
	    int maxCounter =getChildCount ();
	    for(; counter < maxCounter; counter++) {
			if((FHTreeStateNode(getChildAt(counter))).getChildIndex() > childIndex) {
			    added = true;
			    insert(newChild, counter);
			    counter = maxCounter;
			}
	    }
	    if(!added)
			append(newChild);
	}*/

	/**
	 * Removes the child at <code>modelIndex</code>.
	 * <code>isChildVisible</code> should be true if the receiver
	 * is visible and expanded.
	 */
	public void  removeChildAtModelIndex (int modelIndex ,boolean isChildVisible ){
	    FHTreeStateNode childNode =getChildAtModelIndex(modelIndex );

	    if(childNode != null) {
			int row =childNode.getRow ();
			int index =getIndex(childNode );
	
			childNode.collapse(false);
			removeAt(index);
			adjustChildIndexs(index, -1);
			childCount--;
			if(isChildVisible) {
			    // Adjust the rows.
			    resetChildrenRowsFrom(row, index, modelIndex);
			}
	    }else {
			int maxCounter =getChildCount ();
			FHTreeStateNode aChild ;
	
			for(int counter =0;counter <maxCounter ;counter ++){
			    aChild = FHTreeStateNode(getChildAt(counter));
			    if(aChild.childIndex >= modelIndex) {
					if(isChildVisible) {
					    adjustRowBy(-1, counter);
					    layoutCache.adjustRowCountBy(-1);
					}
					// Since matched and children are always sorted by
					// index, no need to continue testing with the
					// above.
					for(; counter < maxCounter; counter++){
					    (FHTreeStateNode(getChildAt(counter))).childIndex--;
					}
					childCount--;
					return;
			    }
			}
			// No children to adjust, but it was a child, so we still need
			// to adjust nodes after this one.
			if(isChildVisible) {
			    adjustRowBy(-1, maxCounter);
			    layoutCache.adjustRowCountBy(-1);
			}
			childCount--;
	    }
	}

	/**
	 * Adjusts the child indexs of the receivers children by
	 * <code>amount</code>, starting at <code>index</code>.
	 */
	private void  adjustChildIndexs (int index ,int amount ){
		int counter =index ;
		int maxCounter =getChildCount ();
	    for(; counter < maxCounter; counter++) {
			(FHTreeStateNode(getChildAt(counter))).childIndex += amount;
	    }
	}

	/**
	 * Messaged when a child has been inserted at index. For all the
	 * children that have a childIndex >= index their index is incremented
	 * by one.
	 */
	public void  childInsertedAtModelIndex (int index ,boolean isExpandedAndVisible ){
	    FHTreeStateNode aChild ;
	    int maxCounter =getChildCount ();

	    for(int counter =0;counter <maxCounter ;counter ++){
			aChild = FHTreeStateNode(getChildAt(counter));
			if(aChild.childIndex >= index) {
			    if(isExpandedAndVisible) {
					adjustRowBy(1, counter);
					layoutCache.adjustRowCountBy(1);
			    }
			    /* Since matched and children are always sorted by
			       index, no need to continue testing with the above. */
			    for(; counter < maxCounter; counter++){
					(FHTreeStateNode(getChildAt(counter))).childIndex++;
			    }
			    childCount++;
			    return;
			}
	    }
	    // No children to adjust, but it was a child, so we still need
	    // to adjust nodes after this one.
	    if(isExpandedAndVisible) {
			adjustRowBy(1, maxCounter);
			layoutCache.adjustRowCountBy(1);
	    }
	    childCount++;
	}

	/**
	 * Returns true if there is a row for <code>row</code>.
	 * <code>nextRow</code> gives the bounds of the receiver.
	 * Information about the found row is returned in <code>info</code>.
	 * This should be invoked on root with <code>nextRow</code> set
	 * to <code>getRowCount</code>().
	 */
	public boolean  getPathForRow (int row ,int nextRow ,SearchInfo info ){
	    if(this.row == row) {
			info.node = this;
			info.isNodeParentNode = false;
			info.childIndex = childIndex;
			return true;
	    }

	    FHTreeStateNode child ;
	    FHTreeStateNode lastChild =null ;
		int counter =0;
		int maxCounter =getChildCount ();
		int lastChildEndRow ;
	    for(;counter < maxCounter; counter++) {
			child = FHTreeStateNode(getChildAt(counter));
			if(child.row > row) {
			    if(counter == 0) {
					// No node exists for it, and is first.
					info.node = this;
					info.isNodeParentNode = true;
					info.childIndex = row - this.row - 1;
					return true;
			    }else {
					// May have been in last childs bounds.
					lastChildEndRow = 1 + child.row - (child.childIndex - lastChild.childIndex);
		
					if(row < lastChildEndRow) {
					    return lastChild.getPathForRow(row, lastChildEndRow, info);
					}
					// Between last child and child, but not in last child
					info.node = this;
					info.isNodeParentNode = true;
					info.childIndex = row - lastChildEndRow +
					                        lastChild.childIndex + 1;
					return true;
			    }
			}
			lastChild = child;
	    }

	    // Not in children, but we should have it, offset from
	    // nextRow.
	    if(lastChild != null) {
			lastChildEndRow = nextRow - (childCount - lastChild.childIndex) + 1;
	
			if(row < lastChildEndRow) {
			    return lastChild.getPathForRow(row, lastChildEndRow, info);
			}
			// Between last child and child, but not in last child
			info.node = this;
			info.isNodeParentNode = true;
			info.childIndex = row - lastChildEndRow + lastChild.childIndex + 1;
			return true;
	    } else {
			// No children.
			int retChildIndex =row -this.row -1;
	
			if(retChildIndex >= childCount) {
			    return false;
			}
			info.node = this;
			info.isNodeParentNode = true;
			info.childIndex = retChildIndex;
			return true;
	    }
	}

	/**
	 * Asks all the children of the receiver for their totalChildCount
	 * and returns this value (plus stopIndex).
	 */
	private int  getCountTo (int stopIndex ){
	    FHTreeStateNode aChild ;
	    int retCount =stopIndex +1;
		int counter =0;
		int maxCounter =getChildCount ();
	    for(; counter < maxCounter; counter++) {
			aChild = FHTreeStateNode(getChildAt(counter));
			if(aChild.childIndex >= stopIndex)
			    counter = maxCounter;
			else
			    retCount += aChild.getTotalChildCount();
	    }
	    if(parent != null){
			return retCount + (FHTreeStateNode(getParent())).getCountTo(childIndex);
	    }
	    if(!layoutCache.isRootVisible()){
			return (retCount - 1);
	    }
	    return retCount;
	}

	/**
	 * Returns the number of children that are expanded to
	 * <code>stopIndex</code>. This does not include the number
	 * of children that the child at <code>stopIndex</code> might
	 * have.
	 */
	/*private int  getNumExpandedChildrenTo (int stopIndex ){
	    FHTreeStateNode aChild ;
	    int retCount =stopIndex +1;
		int counter =0;
		int maxCounter =getChildCount ();

	    for(; counter < maxCounter; counter++) {
			aChild = FHTreeStateNode(getChildAt(counter));
			if(aChild.childIndex >= stopIndex)
			    return retCount;
			else {
			    retCount += aChild.getTotalChildCount();
			}
	    }
	    return retCount;
	}*/

	/**
	 * Messaged when this node either expands or collapses.
	 */
	//private void  didAdjustTree (){
	//}
}


