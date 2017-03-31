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

public class ListNode
	/**
	 * the data stored in this node
	 */
	private Object data;
	/**
	 * the node directly behind this node in a list
	 */
	private ListNode nextNode ;
	/**
	 * the node directly before this node in a list
	 */
	private ListNode preNode ;

	public  ListNode (*_data ,ListNode _preNode ,ListNode _nextNode ){
		this.data = _data;
		this.nextNode = _nextNode;
		this.preNode = _preNode;
	}

	//setter and getter methiods
	public void  setData (*)_data {
		this.data = _data;
	}

	public Object getData () {
		return this.data;
	}

	public void  setPrevNode (ListNode _preNode ){
		this.preNode = _preNode;
	}

	public ListNode  getPrevNode (){
		return this.preNode;
	}

	public void  setNextNode (ListNode _nextNode ){
		this.nextNode = _nextNode;
	}

	public ListNode  getNextNode (){
		return this.nextNode;
	}

	public String  toString (){
		return "ListNode.get(data:" + data + ")";
	}
}


