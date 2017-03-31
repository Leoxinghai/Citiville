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
 * Defines the requirements for an object that can be used as a
 * tree node in a JTree.
 * 
 * @author iiley
 * @see org.aswing.tree.MutableTreeNode
 * @see org.aswing.tree.DefaultMutableTreeNode
 * @see org.aswing.JTree
 */
public interface TreeNode {
	
    /**
     * Returns the child <code>TreeNode</code> at index 
     * <code>childIndex</code>.
     */
    TreeNode  getChildAt (int childIndex );

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    int  getChildCount ();

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     */
    TreeNode  getParent ();

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    int  getIndex (TreeNode node );

    /**
     * Returns true if the receiver allows children.
     */
    boolean  getAllowsChildren ();

    /**
     * Returns true if the receiver is a leaf.
     */
    boolean  isLeaf ();

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     */
    Array  children ();
}


