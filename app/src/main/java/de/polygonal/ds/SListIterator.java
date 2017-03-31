 * DATA STRUCTURES FOR GAME PROGRAMMERS
 * Copyright (c) 2007 Michael Baczynski, http://www.polygonal.de
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

        /**
         * A singly linked list iterator.
         *
         * Provides some additional methods useful when dealing with linked lists.
         * Be careful when using them together with the methods implemented through
         * the Iterator interface since they both work differently.
         */
        public class SListIterator implements Iterator
        {
                /**
                 * The node the iterator is pointing to.
                 */
                public SListNode node ;

                /**
                 * The list this iterator uses.
                 */
                public SLinkedList list ;

                /**
                 * Creates a new SListIterator instance pointing to a given node.
                 * Usually created by invoking SLinkedList.getIterator().
                 *
                 * @param list The linked list the iterator should handle.
                 * @param node The iterator's starting node.
                 */
                public  SListIterator (SLinkedList list =null ,SListNode node =null )
                {
                        this.list = list;
                        this.node = node;
                }

                /**
                 * @inheritDoc
                 */
                public void  start ()
                {
                        if (list) node = list.head;
                }

                /**
                 * @inheritDoc
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
                 * @inheritDoc
                 */
                public boolean  hasNext ()
                {
                        return Boolean(node);
                }

                /**
                 * @inheritDoc
                 */
                public Object data ()
                {
                        if (node) return node.data;
                        return null;
                }

                /**
                 * @private
                 */
                public void  data (*)obj
                {
                        node.data = obj;
                }

                /**
                 * Moves the iterator to the tail node.
                 */
                public void  end ()
                {
                        if (list) node = list.tail;
                }

                /**
                 * Moves the iterator to the next node.
                 */
                public void  forth ()
                {
                        if (node) node = node.next;
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
                 * Removes the node the iterator is pointing to.
                 *
                 * @return True if the removal succeeded, otherwise false.
                 */
                public boolean  remove ()
                {
                        return list.remove(this);
                }

                /**
                 * Prints out a string representing the current object.
                 *
                 * @return A string representing the current object.
                 */
                public String  toString ()
                {
                        return "{SListIterator: data=" + node.data + "}";
                }
        }

