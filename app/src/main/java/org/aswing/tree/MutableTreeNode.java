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


import org.aswing.tree.TreeNode;

/**
 * Defines the requirements for a tree node object that can change --
 * by adding or removing child nodes, or by changing the contents
 * of a user object stored in the node.
 *
 * @author iiley
 * @see org.aswing.tree.DefaultMutableTreeNode
 * @see org.aswing.JTree
 */
public interface MutableTreeNode extends TreeNode{

    /**
     * Adds <code>child</code> to the receiver at <code>index</code>.
     * <code>child</code> will be messaged with <code>setParent</code>.
     */
    void  insert (MutableTreeNode child ,int index );

    /**
     * Removes the child at <code>index</code> from the receiver.
     */
    void  removeAt (int index );

    /**
     * Removes <code>node</code> from the receiver. <code>setParent</code>
     * will be messaged on <code>node</code>.
     */
    void  remove (MutableTreeNode node );

    /**
     * Resets the user object of the receiver to <code>object</code>.
     */
    void  setUserObject (*)object ;

	/**
	 * Returns the user object.
	 */
	Object getUserObject ();

    /**
     * Removes the receiver from its parent.
     */
    void  removeFromParent ();

    /**
     * Sets the parent of the receiver to <code>newParent</code>.
     */
    void  setParent (MutableTreeNode newParent );
}


