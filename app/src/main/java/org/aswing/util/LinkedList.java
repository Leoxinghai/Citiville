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

import org.aswing.util.ListNode;
/**
 * Linked list implementation of the List interface.
 * @author Tomato
 * @author iiley
 */
public class LinkedList implements List
	/**
	 * the head node in this list
	 */
	private ListNode head ;
	/**
	 * the tail node in this list
	 */
	private ListNode tail ;

	/**
	 * the number of nodes in this list
	 */
	private int count ;


	//constructor
	public  LinkedList (){
	 	count = 0;
	 	head = null;
	 	tail = null;
	}


	public int  size (){
		return count;
	}

	public ListNode  getHead (){
		return head;
	}

	public ListNode  getTail (){
		return tail;
	}

	/**
	 * @throws RangeError when index out of bounds
	 */
	public void  append (*data ,int index =-1){
		if(index == -1) index = size();
		if(size() == 0 && index == 0){
			setInitalFirstNode(data);
			return;
		}

		if(index < 0 || index > size()){
			throw new RangeError("Error index out of range : " + index + ", size:" + size());
		}
		ListNode newNode ;
		if(index == size()){
			newNode = new ListNode(data , tail , null);
			tail.setNextNode(newNode);
			tail = newNode;
		}else if(index == 0){
			newNode = new ListNode(data , null , head);
			head.setPrevNode(newNode);
			head = newNode;
		}else{
			ListNode preNode =getNodeAt(index -1);
			ListNode nexNode =preNode.getNextNode ();
			newNode = new ListNode(data , preNode , nexNode);
			preNode.setNextNode(newNode);
			nexNode.setPrevNode(newNode);
		}
		count += 1;
	}

	private void  setInitalFirstNode (Object data ){
		ListNode newNode =new ListNode(data ,null ,null );
		head = newNode;
		tail = newNode;
		count = 1;
	}

	public ListNode  getNodeAt (int index ){
		if(index < 0 || index >= size()){
			return null;
		}
		int i ;
		int n ;
		ListNode node ;
		if(index < size()/2){
			n = index;
			node = getHead();
			for(i=0; i<n; i++){
				node = node.getNextNode();
			}
			return node;
		}else{
			n = size() - index - 1;
			node = getTail();
			for(i=0; i<n; i++){
				node = node.getPrevNode();
			}
			return node;
		}
	}


	public Object get(int index ) {
		if(index < 0 || index >= size()){
			return null;
		}else{
			return this.getNodeAt(index).getData();
		}
	}

	/**
	 * @throws RangeError when index out of bounds
	 */
	public void  appendAll (Array arr ,int index =-1){
		if(index == -1) index = size();
		if(arr == null || arr.length == 0){
			return;
		}
		if(index < 0 || index > size()){
			throw new RangeError("Error index out of range : " + index + ", size:" + size());
		}

		LinkedList tempList =new LinkedList ();
		for(int i =0;i <arr.length ;i ++){
			tempList.append(arr.get(i));
		}

		if(size() == 0){
			head = tempList.getHead();
			tail = tempList.getTail();
		}else if(index == size()){
			tail.setNextNode(tempList.getHead());
			tempList.getHead().setPrevNode(tail);
			tail = tempList.getTail();
		}else if(index == 0){
			head.setPrevNode(tempList.getTail());
			tempList.getTail().setNextNode(head);
			head = tempList.getHead();
		}else{
			ListNode preNode =getNodeAt(index -1);
			ListNode nexNode =preNode.getNextNode ();
			preNode.setNextNode(tempList.getHead());
			tempList.getHead().setPrevNode(preNode);
			tempList.getTail().setNextNode(nexNode);
			nexNode.setPrevNode(tempList.getTail());
		}
		count += tempList.size();
	}

	public void  appendList (List list ,int index =-1){
		appendAll(list.toArray(), index);
	}

	public  replaceAt (int index ,*)element *{
		ListNode node =getNodeAt(index );
		oldData = node.getData();
		node.setData(element);
		return oldData;
	}

	public Object removeAt (int index ) {
		if(index < 0 || index >= size()){
			return undefined;
		}
		Object element;
		if(index == 0){
			element = head.getData();
			head = head.getNextNode();
			if(head == null){
				tail = null;
			}else{
				head.setPrevNode(null);
			}
		}else if(index == size() - 1){
			element = tail.getData();
			tail = tail.getPrevNode();
			if(tail == null){
				head = null;
			}else{
				tail.setNextNode(null);
			}
		}else{
			ListNode preNode =getNodeAt(index -1);
			ListNode nexNode =preNode.getNextNode ().getNextNode ();
			element = preNode.getNextNode().getData();
			preNode.setNextNode(nexNode);
			nexNode.setPrevNode(preNode);
		}
		count --;
		return element;
	}

	public  remove (*)element *{
		for(ListNode node =head ;node !=null; node=node.getNextNode()){
			if(node.getData() == element){
				removeNode(node);
				return node.getData();
			}
		}
		return undefined;
	}

	public void  removeNode (ListNode node ){
		if(node == head && node == tail){
			head = tail = null;
		}else if(node == head){
			head = head.getNextNode();
			head.setPrevNode(null);
		}else if(node == tail){
			tail = tail.getPrevNode();
			tail.setNextNode(null);
		}else{
			ListNode preNode =node.getPrevNode ();
			ListNode nexNode =node.getNextNode ();
			preNode.setNextNode(nexNode);
			nexNode.setPrevNode(preNode);
		}
		count --;
	}

	/**
	 * Returns null if out of bounds
	 */
	public Array  removeRange (int fromIndex ,int toIndex ){
		if(fromIndex > toIndex){
			int temp =fromIndex ;
			fromIndex = toIndex;
			toIndex = temp;
		}
		if(fromIndex < 0 || fromIndex >= size()){
			return null;
		}
		if(toIndex < 0 || toIndex >= size()){
			return null;
		}
		ListNode preNode =getNodeAt(fromIndex -1);
		ListNode nexNode =getNodeAt(toIndex +1);
		if(fromIndex == 0 && toIndex == size() - 1){
			Array array =toArray ();
			clear();
			return array;
		}

		ListNode startNode =preNode.getNextNode ();
		ListNode endNode =nexNode.getPrevNode ();
		if(preNode == null){
			startNode = head;
		}
		if(nexNode == null){
			endNode = tail;
		}
		int al =toIndex -fromIndex +1;
		Array arr =new Array(al );
		for(int i =0;i <al ;i ++){
			arr.put(i,  startNode);
			startNode = startNode.getNextNode();
		}

		if(fromIndex == 0){
			head = nexNode;
			head.setPrevNode(null);
		}else if(toIndex == size() - 1){
			tail = preNode;
			tail.setNextNode(null);
		}else{
			preNode.setNextNode(nexNode);
			nexNode.setPrevNode(preNode);
		}

		count -= al;
		return arr;
	}

	public int  indexOf (*)element {
		int index =0;
		for(ListNode node =head ;node !=null; node=node.getNextNode()){
			if(node.getData() == element){
				return index;
			}
			index++;
		}
		return -1;
	}

	public boolean  contains (*)element {
		return (indexOf(element) != -1);
	}

	public Object first () {
		return head.getData();
	}

	public Object last () {
		return tail.getData();
	}

	public Object pop () {
		return removeAt(size() - 1);
	}

	public Object shift () {
		return removeAt(0);
	}

	public void  clear (){
		head = tail = null;
		count = 0;
	}

	public boolean  isEmpty (){
		return count == 0;
	}

	public Array  toArray (){
		Array arr =new Array(size ());
		int index =0;
		for(ListNode node =head ;node !=null; node=node.getNextNode()){
			arr.put(index,  node.getData());
			index++;
		}
		return arr;
	}

	public String  toString (){
		return "LinkedList.get(" + toArray() + ")";
	}

}


