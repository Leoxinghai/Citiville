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

import de.polygonal.ds.Collection;
import de.polygonal.ds.Iterator;
import de.polygonal.ds.SListNode;
import de.polygonal.ds.SListIterator;

	/**
	 * A singly linked list.
	 */
	public class SLinkedList implements Collection
	{
		private int _count ;

		/**
		 * The head node being referenced.
		 */
		public SListNode head ;

		/**
		 * The tail node being referenced.
		 */
		public SListNode tail ;

		/**
		 * Initializes an empty list.
		 */
		public  SLinkedList ()
		{
			head = tail = null;
			_count = 0;
		}

		/**
		 * Appends an item to the list.
		 *
		 * @param obj The data.
		 * @return A singly linked list node wrapping the data.
		 */
		public SListNode  append (*)obj
		{
			SListNode node =new SListNode(obj );
			if (head)
			{
				tail.next = node;
				tail = node;
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
		 * @return A singly linked list node wrapping the data.
		 */
		public SListNode  prepend (*)obj
		{
			SListNode node =new SListNode(obj );

			if (head)
			{
				node.next = head;
				head = node;
			}
			else
				head = tail = node;

			_count++;
			return node;
		}

		/**
		 * Inserts data after a given iterator or appends it
		 * if the iterator is invalid.
		 *
		 * @param itr  A singly linked list iterator.
		 * @param obj The data.
		 * @return A singly linked list node wrapping the data.
		 */
		public SListNode  insertAfter (SListIterator itr ,*)obj
		{
			if (itr.list != this) return null;
			if (itr.node)
			{
				SListNode node =new SListNode(obj );
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
		* Removes the node the iterator is pointing
		* to and move the iterator to the next node.
		*
		* @return True if the removal succeeded, otherwise false.
		*/
		public boolean  remove (SListIterator itr )
		{
			if (itr.list != this || !itr.node) return false;

			SListNode node =head ;
			if (itr.node == head)
			{
				itr.forth();
				removeHead();
				return true;
			}

			while (node.next != itr.node) node = node.next;
			itr.forth();
			if (node.next == tail) tail = node;
			node.next = itr.node;

			_count--;
			return true;
		}

		/**
		 * Removes the head of the list.
		 */
		public void  removeHead ()
		{
			if (!head) return;

			if (head == tail)
				head = tail = null;
			else
			{
				SListNode node =head ;

				head = head.next;
				node.next = null;
				if (head == null) tail = null;
			}
			_count--;
		}

		/**
		 * Removes the tail of the list.
		 */
		public void  removeTail ()
		{
			if (!tail) return;

			if (head == tail)
				head = tail = null;
			else
			{
				SListNode node =head ;
				while (node.next != tail)
					node = node.next;

				tail = node;
				node.next = null;
			}
			_count--;
		}

		/**
		 * Removes and appends the head node to the tail.
		 */
		public void  shiftUp ()
		{
			SListNode t =head ;

			if (head.next == tail)
			{
				head = tail;

				tail = t;
				tail.next = null;

				head.next = tail;
			}
			else
			{
				head = head.next;
				tail.next = t;
				t.next = null;
				tail = t;
			}
		}

		/**
		 * Removes and prepends the tail node to the head.
		 */
		public void  popDown ()
		{
			SListNode t =tail ;

			if (head.next == tail)
			{
				tail = head;
				head = t;

				tail.next = null;
				head.next = tail;
			}
			else
			{
				SListNode node =head ;
				while (node.next != tail)
					node = node.next;

				tail = node;
				tail.next = null;

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
			SListNode node =head ;
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
			SListNode node =head ;
			head = null;

			SListNode next ;
			while (node)
			{
				next = node.next;
				node.next = null;
				node = next;
			}
			_count = 0;
		}

		/**
		 * Creates an iterator pointing
		 * to the first node in the list.
		 *
		 * @returns An iterator object.
		 */
		public Iterator  getIterator ()
		{
			return new SListIterator(this, head);
		}

		/**
		 * Creates a list iterator pointing
		 * to the first node in the list.
		 *
		 * @returns A SListIterator object.
		 */
		public SListIterator  getListIterator ()
		{
			return new SListIterator(this, head);
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
			SListNode node =head ;
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
			return "[SlinkedList, size=" + size + "]";
		}

		/**
		 for(int i0 = 0; i0 < s out all elements in the list (for debug/demo purposes.size(); i0++)
		 {
		 		for(int i0 = 0; i0 < the list (for debug/demo purposes.get(i0.size(); i0++)
		 		{
		 				elements = the list (for debug/demo purposes.get(i0.get(i0);
		{
			if (!head)
				return "SLinkedList: (empty)";

			String s ="SLinkedList: (has "+_count +" node"+(_count ==1? ")" : "s") + "\n|< Head\n";

			SListIterator itr =getListIterator ();
			for (; itr.valid(); itr.forth())
				s += "\t" + itr.data + "\n";

			s += "Tail >|";

			return s;
		}

        public SListIterator  nodeOf (Object param1 ,SListIterator param2 )
        {
            if (param2 != null)
            {
                if (param2.list != null)
                {
                    return null;
                }
            }
            _loc_3 = param2==null ? (this.head) : (param2.node);
            while (_loc_3)
            {

                if (_loc_3.data === param1)
                {
                    return new SListIterator(this, _loc_3);
                }
                _loc_3 = _loc_3.next;
            }
            return null;
        }//end


	}

