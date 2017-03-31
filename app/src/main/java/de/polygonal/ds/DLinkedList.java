 * Copyright (c) Michael Baczynski 2007
 * http://lab.polygonal.de/ds/
 *
 * This software is distributed under licence. Use of this software
 * implies agreement with all terms and conditions of the accompanying
 * software licence.
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

import de.polygonal.ds.Iterator;
import de.polygonal.ds.Collection;

import de.polygonal.ds.DListNode;
import de.polygonal.ds.DListIterator;

        /**
         * A doubly linked list.
         *
         * <p>A doubly linked list stores a reference to the next
         * and previous node which makes it possible to traverse
         * the list in both directions.</p>
         */
        public class DLinkedList implements Collection
        {
                private int _count ;

                /**
                 * The head node being referenced.
                 */
                public DListNode head ;

                /**
                 * The tail node being referenced.
                 */
                public DListNode tail ;

                /**
                 * Initializes an empty list.
                 */
                public  DLinkedList ()
                {
                        head = tail = null;
                        _count = 0;
                }

                /**
                 * Appends an item to the list.
                 *
                 * @param obj The data.
                 * @return A doubly linked list node wrapping the data.
                 */
                public DListNode  append (*)obj
                {
                        DListNode node =new DListNode(obj );
                        if (head)
                        {
                                tail.insertAfter(node);
                                tail = tail.next;
                        }
                        else
                                head = tail = node;

                        _count++;
                        return node;
                }

                /**
                 * Prepends an item to the list.
                 *
                 * @param obj The data.
                 * @return A doubly linked list node wrapping the data.
                 */
                public DListNode  prepend (*)obj
                {
                        DListNode node =new DListNode(obj );

                        if (head)
                        {
                                head.insertBefore(node);
                                head = head.prev;
                        }
                        else
                                head = tail = node;

                        _count++;
                        return node;
                }

                /**
                 * Inserts an item after a given iterator or appends it
                 * if the iterator is invalid.
                 *
                 * @param itr A doubly linked list iterator.
                 * @param obj The data.
                 * @return A doubly linked list node wrapping the data.
                 */
                public DListNode  insertAfter (DListIterator itr ,*)obj
                {
                        if (itr.list != this) return null;
                        if (itr.node)
                        {
                                DListNode node =new DListNode(obj );
                                itr.node.insertAfter(node);

                                if (itr.node == tail)
                                        tail = itr.node.next;

                                _count++;
                                return node;
                        }
                        else
                                return append(obj);
                }

                /**
                 * Inserts an item before a given iterator or appends it
                 * if the iterator is invalid.
                 *
                 * @param itr A doubly linked list iterator.
                 * @param obj The data.
                 * @return A doubly linked list node wrapping the data.
                 */
                public DListNode  insertBefore (DListIterator itr ,*)obj
                {
                        if (itr.list != this) return null;
                        if (itr.node)
                        {
                                DListNode node =new DListNode(obj );
                                itr.node.insertBefore(node);
                                if (itr.node == head)
                                        head = head.prev;

                                _count++;
                                return node;
                        }
                        else
                                return prepend(obj);
                }

                /**
                * Removes the node the iterator is pointing
                * at and moves the iterator to the next node.
                *
                * @return True if the removal succeeded, otherwise false.
                */
                public boolean  remove (DListIterator itr )
                {
                        if (itr.list != this || !itr.node) return false;

                        DListNode node =itr.node ;

                        if (node == head)
                                head = head.next;
                        else
                        if (node == tail)
                                tail = tail.prev;

                        itr.forth();
                        node.unlink();

                        if (head == null) tail = null;

                        _count--;
                        return true;
                }

                /**
                 * Removes the head of the list.
                 */
                public void  removeHead ()
                {
                        if (head)
                        {
                                head = head.next;

                                if (head)
                                        head.prev = null;
                                else
                                        tail = null

                                _count--;
                        }
                }

                /**
                 * Removes the tail of the list.
                 */
                public void  removeTail ()
                {
                        if (tail)
                        {
                                tail = tail.prev;

                                if (tail)
                                        tail.next = null;
                                else
                                        head = null;

                                _count--;
                        }
                }

                /**
                 * Removes and appends the head node to the tail.
                 */
                public void  shiftUp ()
                {
                        DListNode t =head ;

                        if (head.next == tail)
                        {
                                head = tail;
                                head.prev = null;

                                tail = t;
                                tail.next = null;

                                head.next = tail;
                                tail.prev = head;
                        }
                        else
                        {
                                head = head.next;
                                head.prev = null;

                                tail.next = t;

                                t.next = null;
                                t.prev = tail;

                                tail = t;
                        }
                }

                /**
                 * Removes and prepends the tail node to the head.
                 */
                public void  popDown ()
                {
                        DListNode t =tail ;

                        if (tail.prev == head)
                        {
                                tail = head;
                                tail.next = null;

                                head = t;
                                head.prev = null;

                                head.next = tail;
                                tail.prev = head;
                        }
                        else
                        {
                                tail = tail.prev;
                                tail.next = null;

                                head.prev = t;

                                t.prev = null;
                                t.next = head;

                                head = t;
                        }
                }

                /**
                 * Checks if a given item exists.
                 *
                 * @return True if the item is found, otherwise false.
                 */
                public boolean  contains (*)obj
                {
                        DListNode node =head ;
                        while (node)
                        {
                                if (node.data == obj) return true;
                                node = node.next;
                        }
                        return false;
                }

                /**
                 * Clears the list by unlinking all nodes
                 * from it. This is important to unlock
                 * the nodes for the garbage collector.
                 */
                public void  clear ()
                {
                        DListNode node =head ;
                        head = null;

                        DListNode next ;
                        while (node)
                        {
                                next = node.next;
                                node.next = node.prev = null;
                                node = next;
                        }
                        _count = 0;
                }

                /**
                 * Creates an iterator object pointing
                 * at the first node in the list.
                 *
                 * @returns An iterator object.
                 */
                public Iterator  getIterator ()
                {
                        return new DListIterator(this, head);
                }

                /**
                 * Creates a doubly linked iterator object pointing
                 * at the first node in the list.
                 *
                 * @returns A DListIterator object.
                 */
                public DListIterator  getListIterator ()
                {
                        return new DListIterator(this, head);
                }

                /**
                 * The total number of nodes in the list.
                 */
                public int  size ()
                {
                        return _count;
                }

                /**
                 * Checks if the list is empty.
                 */
                public boolean  isEmpty ()
                {
                        return _count == 0;
                }

                /**
                 * Converts the linked list into an array.
                 *
                 * @return An array.
                 */
                public Array  toArray ()
                {
                        Array a =new Array();
                        DListNode node =head ;
                        while (node)
                        {
                                a.push(node.data);
                                node = node.next;
                        }
                        return a;
                }

                /**
                 * Returns a string representing the current object.
                 */
                public String  toString ()
                {
                        return "[DLinkedList > has " + size + " nodes]";
                }

                /**
                 for(int i0 = 0; i0 < s out all elements in the list (for debug/demo purposes.size(); i0++) 
                 {
                 		for(int i0 = 0; i0 < the list (for debug/demo purposes.get(i0.size(); i0++) 
                 		{
                 				elements = the list (for debug/demo purposes.get(i0.get(i0);
                {
                        if (head == null) return "DLinkedList, empty";

                        String s ="DLinkedList, has "+_count +" node"+(_count ==1? "" : "s") + "\n|< Head\n";

                        DListIterator itr =getListIterator ();
                        for (; itr.valid(); itr.forth())
                                s += "\t" + itr.data + "\n";

                        s += "Tail >|";

                        return s;
                }
        }

