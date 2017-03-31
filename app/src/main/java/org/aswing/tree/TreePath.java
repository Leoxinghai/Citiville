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


/**
 * TODO make TreePash faster as hashmap key
 * @author iiley
 */
public class TreePath{
	 /**
	  * Path representing the parent, null if lastPathComponent represents
      * the root.
      */
    private TreePath parentPath ;
    /** Last path component. */
    private Object lastPathComponent;

    /**
     * Constructs a path from an array of Objects, uniquely identifying
     * the path from the root of the tree to a specific node, as returned
     * by the tree's data model.
     * <p>
     * The model is free to return an array of any Objects it needs to
     * represent the path. The DefaultTreeModel returns an array of
     * TreeNode objects. The first TreeNode in the path is the root of the
     * tree, the last TreeNode is the node identified by the path.
     * </p>
     * @param path  an array of Objects representing the path to a node
     */
    public  TreePath (Array path ){
        if(path == null || path.length == 0){
        	trace("Error : path in TreePath must be non null and not empty.");
            throw new Error("path in TreePath must be non null and not empty.");
        }
		lastPathComponent = path.get(path.length - 1);
		if(path.length > 1){
			Array pp =path.concat ();
			pp.pop();
		    parentPath = new TreePath(pp);
	    }
    }

    /**
     * Constructs a new TreePath, which is the path identified by
     * <code>parent</code> ending in <code>lastElement</code>.
     */
    public static TreePath  createTreePath (TreePath parent ,*)lastElement {
		if(lastElement == null){
			trace("path in TreePath must be non null.");
			throw new Error("path in TreePath must be non null.");
		}
		TreePath tp =new TreePath(.get(null) );
		tp.parentPath = parent;
		tp.lastPathComponent = lastElement;
		return tp;
    }

    /**
     * Returns an ordered array of Objects containing the components of this
     * TreePath. The first element (index 0) is the root.
     *
     * @return an array of Objects representing the TreePath
     * @see #TreePath(*[])
     */
    public Array  getPath (){
		double i =getPathCount ();
        Array result =new Array(i );
        i--;
        for(TreePath path =this ;path != null; path = path.parentPath) {
            result.put(i,  path.lastPathComponent);
            i--;
        }
		return result;
    }

    /**
     * Returns the last component of this path. For a path returned by
     * DefaultTreeModel this will return an instance of TreeNode.
     *
     * @return the Object at the end of the path
     * @see #TreePath(*[])
     */
    public Object getLastPathComponent () {
		return lastPathComponent;
    }

    /**
     * Returns the number of elements in the path.
     *
     * @return an int giving a count of items the path
     */
    public int  getPathCount (){
        double result =0;
        for(TreePath path =this ;path != null; path = path.parentPath) {
            result++;
        }
		return result;
    }

    /**
     * Returns the path component at the specified index.
     *
     * @param element  an int specifying an element in the path, where
     *                 0 is the first element in the path
     * @return the Object at that index location, undefined if the index is beyond the length of the path
     *
     * @see #TreePath(Object[])
     */
    public Object getPathComponent (int element ) {
        int pathLength =getPathCount ();

        if(element < 0 || element >= pathLength){
            return undefined;
        }

        TreePath path =this ;

        for(int i =pathLength -1;i != element; i--) {
           path = path.parentPath;
        }
		return path.lastPathComponent;
    }

    /**
     * Tests two TreePaths for equality by checking each element of the
     * paths for equality. Two paths are considered equal if they are of
     * the same length, and contain
     * the same elements (<code>.equals</code>).
     *
     * @param o the Object to compare
     */
    public boolean  equals (*)o {
		if(o == this){
		    return true;
		}
        if(o is TreePath) {
            TreePath oTreePath =TreePath(o );
	    	if(getPathCount() != oTreePath.getPathCount()){
				return false;
	    	}
	    	for(TreePath path =this ;path != null; path = path.parentPath) {
				if (path.lastPathComponent != oTreePath.lastPathComponent) {
		    		return false;
				}
				oTreePath = oTreePath.parentPath;
	    	}
	    	return true;
        }
        return false;
    }


    /**
     * Returns true if <code>aTreePath</code> is a
     * descendant of this
     * TreePath. A TreePath P1 is a descendent of a TreePath P2
     * if P1 contains all of the components that make up
     * P2's path.
     * For example, if this object has the path .get(a, b),
     * and <code>aTreePath</code> has the path .get(a, b, c),
     * then <code>aTreePath</code> is a descendant of this object.
     * However, if <code>aTreePath</code> has the path .get(a),
     * then it is not a descendant of this object.
     *
     * @return true if <code>aTreePath</code> is a descendant of this path
     */
    public boolean  isDescendant (TreePath aTreePath ){
		if(aTreePath == this)
	    	return true;

        if(aTreePath != null) {
            double pathLength =getPathCount ();
	    	double oPathLength =aTreePath.getPathCount ();

	    	if(oPathLength < pathLength){
				// Can't be a descendant, has fewer components in the path.
				return false;
	    	}
			while(oPathLength > pathLength){
				aTreePath = aTreePath.getParentPath();
				oPathLength--;
			}
			return equals(aTreePath);
        }
        return false;
    }

    /**
     * Returns a new path containing all the elements of this object
     * plus <code>child</code>. <code>child</code> will be the last element
     * of the newly created TreePath.
     * This will throw a NullPointerException
     * if child is null.
     */
    public TreePath  pathByAddingChild (*)child {
		if(child == null){
			trace("Null child not allowed");
	    	throw new Error("Null child not allowed");
		}

		return createTreePath(this, child);
    }

    /**
     * Returns a path containing all the elements of this object, except
     * the last path component.
     */
    public TreePath  getParentPath (){
		return parentPath;
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * @return a String representation of this object
     */
    public String  toString (){
        return "TreePath.get(" + getPath() + ")";
    }

}


