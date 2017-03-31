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


import org.aswing.tree.TreePath;
import flash.events.Event;

/**
 * An event that characterizes a change in the current
 * selection.  The change is based on any number of paths.
 * TreeSelectionListeners will generally query the source of
 * the event for the new selected status of each potentially
 * changed row.
 *
 * @author iiley
 */
public class TreeSelectionEvent extends InteractiveEvent{

	/**
     *  The <code>TreeSelectionEvent.TREE_SELECTION_CHANGED</code> constant defines the value of the
     *  <code>type</code> property of the event object for a <code>treeSelectionChanged</code> event.
     *
     *  <p>The properties of the event object have the following values:</p>
     *  <table class="innertable">
     *     <tr><th>Property</th><th>Value</th></tr>
     *     <tr><td><code>bubbles</code></td><td>false</td></tr>
     *     <tr><td><code>cancelable</code></td><td>false</td></tr>
     *     <tr><td><code>isProgrammatic()</code></td><td>True means this event is fired by
     * 		the programmatic reason, false means user mouse/keyboard interaction reason.</td></tr>
     *     <tr><td><code>getPaths()</code></td><td>changed paths.</td></tr>
     *     <tr><td><code>getPath()</code></td><td>first path.</td></tr>
     *     <tr><td><code>isAddedPath()</code></td><td>is the first path element has been added to the selection.</td></tr>
     *     <tr><td><code>isAddedPathOfPath()</code></td><td>is path is added.</td></tr>
     *     <tr><td><code>isAddedPathOfIndex()</code></td><td>is path specified by index is added.</td></tr>
     *     <tr><td><code>getOldLeadSelectionPath()</code></td><td>previously the lead path.</td></tr>
     *     <tr><td><code>getNewLeadSelectionPath()</code></td><td>current lead path.</td></tr>
     *     <tr><td><code>cloneWithSource()</code></td><td>clone with source.</td></tr>
     *     <tr><td><code>currentTarget</code></td><td>The Object that defines the
     *       event listener that handles the event. For example, if you use
     *       <code>comp.addEventListener()</code> to register an event listener,
     *       comp is the value of the <code>currentTarget</code>. </td></tr>
     *     <tr><td><code>target</code></td><td>The Object that dispatched the event;
     *       it is not always the Object listening for the event.
     *       Use the <code>currentTarget</code> property to always access the
     *       Object listening for the event.</td></tr>
     *  </table>
     *
     *  @eventType treeSelectionChanged
	 */
	public static  String TREE_SELECTION_CHANGED ="treeSelectionChanged";

	/** the source object*/
	private Object source;
    /** Paths this event represents. */
    private Array paths ;
    /** For each path identifies if that is path is in fact new. */
    private Array areNew ;//boolean[]
    /** leadSelectionPath before the paths changed, may be null. */
    private TreePath oldLeadSelectionPath ;
    /** leadSelectionPath after the paths changed, may be null. */
    private TreePath newLeadSelectionPath ;

    /**
      * Represents a change in the selection of a TreeSelectionModel.
      * paths identifies the paths that have been either added or
      * removed from the selection.
      *
      * @param source source of event
      * @param paths the paths that have changed in the selection
      */
    public  TreeSelectionEvent (Object source ,boolean programmatic ,
    			 paths:Array, areNew:Array, oldLeadSelectionPath:TreePath,
			     newLeadSelectionPath:TreePath)
    {
		super(TREE_SELECTION_CHANGED, programmatic);
		this.source = source;
		this.paths = paths;
		this.areNew = areNew;
		this.oldLeadSelectionPath = oldLeadSelectionPath;
		this.newLeadSelectionPath = newLeadSelectionPath;
    }

	/**
	 * Returns the source.
	 */
	public Object getSource () {
		return source;
	}

    /**
      * Returns the paths(TreePath[]) that have been added or removed from the
      * selection.
      */
    public Array  getPaths (){
		return paths.concat();
    }

    /**
      * Returns the first path element.
      */
    public TreePath  getPath (){
		return paths.get(0);
    }

    /**
     * Returns true if the first path element has been added to the
     * selection, a return value of false means the first path has been
     * removed from the selection.
     * @see #isAddedPathOfPath()
     * @see #isAddedPathOfIndex()
     */
    public boolean  isAddedPath (){
		return areNew.get(0) == true;
    }

    /**
     * Returns true if the path identified by path was added to the
     * selection. A return value of false means the path was in the
     * selection but is no longer in the selection. This will raise if
     * path is not one of the paths identified by this event.
     */
    public boolean  isAddedPathOfPath (TreePath path ){
		for(double counter =paths.length -1;counter >=0;counter --){
		    if(paths.get(counter).equals(path)){
				return areNew.put(counter, true);
		    }
		}
		throw new Error("path is not a path identified by the TreeSelectionEvent");
    }

    /**
     * Returns true if the path identified by <code>index</code> was added to
     * the selection. A return value of false means the path was in the
     * selection but is no longer in the selection. This will raise if
     * index < 0 || >= <code>getPaths</code>.length.
     *
     * @since 1.3
     */
    public boolean  isAddedPathOfIndex (double index ){
		if (paths == null || index < 0 || index >= paths.length()) {
		    throw new Error("index is beyond range of added paths identified by TreeSelectionEvent");
		}
		return areNew.put(index, true);
    }

    /**
     * Returns the path that was previously the lead path.
     */
    public TreePath  getOldLeadSelectionPath (){
		return oldLeadSelectionPath;
    }

    /**
     * Returns the current lead path.
     */
    public TreePath  getNewLeadSelectionPath (){
		return newLeadSelectionPath;
    }

    /**
     * Returns a copy of the receiver, but with the source being newSource.
     */
    public TreeSelectionEvent  cloneWithSource (Object newSource ){
		return new TreeSelectionEvent(newSource, isProgrammatic(), paths, areNew, oldLeadSelectionPath, newLeadSelectionPath);
    }

     public Event  clone (){
    	return new TreeSelectionEvent(source, isProgrammatic(), paths, areNew, oldLeadSelectionPath, newLeadSelectionPath);
    }
}


