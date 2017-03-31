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
 * An ordered collection (also known as a sequence).
 * The user of this interface has precise control over where in the list each element is inserted.
 for(int i0 = 0; i0 <  i0 = 0; i0 < eger index (position in the list.size(); i0++.size(); i0++)
 {
 		position =  i0 = 0; i0 < eger index (position in the list.size(); i0++.get(i0);
 		position = eger index (position in the list.get(i0);
 * @author iiley
 */
public interface List
	/**
	 * Returns the element at the specified position in this list.
	 * @param index index of element to return.
	 * @return the element at the specified position in this list. Undefined will be
	 * returned if the index is out of range (index < 0 || index >= size()).
	 */
	 Object get(int index );

	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent elements to the
	 * right (adds one to their indices).
	 * <p>
	 * If index is omited, the element will be append to the end of the list.
	 * @param element element to be inserted.
	 * @param index (optional)at which the specified element is to be inserted.
	 */
	 void  append (*element ,int index =-1);

	/**
	 * Inserts all the elements in a array at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent elements to the
	 * right (adds one to their indices).
	 * <p>
	 * If index is omited, the elements will be append to the end of the list.
	 * @param arr arr of elements to be inserted.
	 * @param index (optional)at which the elements is to be inserted.
	 */
	 void  appendAll (Array arr ,int index =-1);

	/**
	 * Inserts all the elements in a list at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent elements to the
	 * right (adds one to their indices).
	 * <p>
	 * If index is omited, the elements will be append to the end of the list.
	 * @param arr arr of elements to be inserted.
	 * @param index (optional)at which the elements is to be inserted.
	 */
	 void  appendList (List list ,int index =-1);

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * @param index index of element to replace.
	 * @param element element to be stored at the specified position.
	 * @return the element previously at the specified position.
	 */
	 Object  replaceAt (int index ,Object element);

	/**
	 * Removes the element at the specified position in this list.
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 * Returns the element that was removed from the list.
	 * @param index the index of the element to removed.
	 * @return the element previously at the specified position.
	 */
	 Object removeAt (int index );

	/**
	 * Removes the first occurrence in this list of the specified element.
	 * If this list does not contain the element, it is unchanged.
	 * More formally, removes the element with the lowest index.
	 * @param element element to be removed from this list, if present.
	 * @return the element removed or undefined there is not such element in the list.
	 */
	 Object remove (Object element);

	/**
	 * Removes the elements at the specified index range.
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 * Returns the elements in a Array that were removed from the list.
	 * @param fromIndex the beginning index to be removed(include).
	 * @param fromIndex the ending index to be removed(include).
	 * @return the elements were removed.
	 */
	 Array  removeRange (int fromIndex ,int toIndex );

	/**
	 * Returns the index in this list of the first occurrence of the specified element,
	 * or -1 if this list does not contain this element.
	 * More formally, returns the lowest index.
	 * @param element element to search for.
	 * @return the index in this list of the first occurrence of the specified element,
	 * or -1 if this list does not contain this element
	 */
	 int  indexOf (*)element ;

	/**
	 * Returns true if this list contains the specified element.
	 * @param element element whose presence in this list is to be tested.
	 * @return true if this list contains the specified element.
	 */
	 boolean  contains (*)element ;

	/**
	 * Returns the first element in the list.
	 * Undefined will be return if there is not any elements in the list.
	 * @return the first element in the list.
	 */
	 Object first ();

	/**
	 * Returns the last element in the list.
	 * Undefined will be return if there is not any elements in the list.
	 * @return the last element in the list.
	 */
	 Object last ();

	/**
	 * Removes and return the last element in the list.
	 * Undefined will be return if there is not any elements in the list.
	 * @return the last element in the list.
	 */
	 Object pop ();

	/**
	 * Removes and return the first element in the list.
	 * Undefined will be return if there is not any elements in the list.
	 * @return the first element in the list.
	 */
	 Object shift ();

	/**
	 * Returns the number of elements in this list.
	 * @return the number of elements in this list.
	 */
	 int  size ();

	/**
	 * Removes all of the elements from this list.
	 * This list will be empty after this call returns.
	 */
	 void  clear ();

	/**
	 * Returns true if this list contains no elements.
	 * @return true if this list contains no elements.
	 */
	 boolean  isEmpty ();

	/**
	 * Returns an array containing all of the elements in this list in proper sequence.
	 * @return an array containing all of the elements in the list.
	 */
	 Array  toArray ();
}


