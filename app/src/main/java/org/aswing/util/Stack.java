/*
 Copyright aswing.org, see the LICENCE.txt.
*/
package org.aswing.util;

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
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects. It extends class <tt>Vector</tt> with five
 * operations that allow a vector to be treated as a stack. The usual
 * <tt>push</tt> and <tt>pop</tt> operations are provided, as well as a
 * method to <tt>peek</tt> at the top item on the stack, a method to test
 * for whether the stack is <tt>empty</tt>, and a method to <tt>search</tt>
 * the stack for an item and discover how far it is from the top.
 * <p>
 * When a stack is first created, it contains no items.
 * @author iiley
 */
public class Stack extends ArrayList
	/**
     * Creates an empty Stack.
     */
	public  Stack (){
		super();
	}

    /**
     * Tests if this stack is empty.
     *
     * @return  <code>true</code> if and only if this stack contains
     *          no items; <code>false</code> otherwise.
     */
	public boolean  empty (){
		return _elements.length == 0;
	}

	/**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return     the object at the top of this stack (the last item
     *             of the <tt>Vector</tt> object). undefined is there is no items.
	 */
	public Object peek () {
		return _elements.get(_elements.length-1);
	}

	/**
     * Removes the object at the top of this stack and returns that
     *object as the value of this  .
     *
     * @return     The object at the top of this stack (the last item
     *             of the <tt>Vector</tt> object). undefined if there is no items.
     */
	 public Object pop () {
		return _elements.pop();
	}

	/**
     * Pushes an item onto the top of this stack. This has exactly
     * the same effect as:
     * <blockquote><pre>
     * append(item)</pre></blockquote>
     *
     * @param   item   the item to be pushed onto this stack.
     * @return  the <code>item</code> argument.
	 */
	public  push (*)item *{
		_elements.push(item);
		return item;
	}


    /**
     * Returns the 1-based position where an object is on this stack.
     * If the object <tt>o</tt> occurs as an item in this stack, this
     * method returns the distance from the top of the stack of the
     * occurrence nearest the top of the stack; the topmost item on the
     * stack is considered to be at distance <tt>1</tt>. The <tt>equals</tt>
     * method is used to compare <tt>o</tt> to the
     * items in this stack.
     *
     * @param   o   the desired object.
     * @return  the 1-based position from the top of the stack where
     *          the object is located; the return value <code>-1</code>
     *          indicates that the object is not on the stack.
     */
	public int  search (*)o {
		int i =lastIndexOf(o );
		if (i >= 0) {
		    return size() - i;
		}
		return -1;
	}
}


