package de.polygonal.ds;

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

import de.polygonal.ds.DListNode;
import de.polygonal.ds.DLinkedList;

        /**
         * A list iterator.
         */
        public class DListIterator implements Iterator
        {
                /**
                 * The node the iterator is pointing to.
                 */
                public DListNode node ;

                /**
                 * The list of the iterator is referenced.
                 */
                public DLinkedList list ;

                /**
                 * Initializes a new DListIterator instance pointing to a given node.
                 * Usually created by invoking SLinkedList.getIterator().
                 *
                 * @param list The linked list the iterator should use.
                 * @param node The iterator's initial node.
                 */
                public  DListIterator (DLinkedList list ,DListNode node =null )
                {
                        this.list = list;
                        this.node = node;
                }

                /**
                 * Moves the iterator to the start of the list.
                 */
                public void  start ()
                {
                        node = list.head;
                }

                /**
                 * Returns the current node's data while
                 * moving the iterator forward by one position.
                 */
                public Object next ()
                {
                        if (hasNext())
                        {
                                obj = node.data;
                                node = node.next;
                                return obj;
                        }
                        return null;
                }

                /**
                 * Checks if the next node exists.
                 */
                public boolean  hasNext ()
                {
                        return Boolean(node);
                }

                /**
                 * Read/writes the current node's data.
                 *
                 * @return The data.
                 */
                public Object data ()
                {
                        if (node) return node.data;
                        return null;
                }

                public void  data (*)obj
                {
                        node.data = obj;
                }

                /**
                 * Moves the iterator to the end of the list.
                 */
                public void  end ()
                {
                        node = list.tail;
                }

                /**
                 * Moves the iterator to the next node.
                 */
                public void  forth ()
                {
                        if (node) node = node.next;
                }

                /**
                 * Moves the iterator to the previous node.
                 */
                public void  back ()
                {
                        if (node) node = node.prev;
                }

                /**
                 * Checks if the current referenced node is valid.
                 *
                 * @return True if the node exists, otherwise false.
                 */
                public boolean  valid ()
                {
                        return Boolean(node);
                }

                /**
                 * Removes the node the iterator is
                 * pointing to.
                 */
                public void  remove ()
                {
                        list.remove(this);
                }

                /**
                 * Returns a string representing the current object.
                 */
                public String  toString ()
                {
                        return "{DListIterator, data=" + (node ? node.data : "null") + "}";
                }
        }


