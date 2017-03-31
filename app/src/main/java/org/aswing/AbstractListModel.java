/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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


import org.aswing.event.*;
import org.aswing.util.*;

/**
 * Abstract list model that provide the list model events base.
 * @author iiley
 */
public class AbstractListModel{
	
    private Array listeners ;
    
    public  AbstractListModel (){
    	listeners = new Array();
    }

    public void  addListDataListener (ListDataListener l ){
		listeners.push(l);
    }

    public void  removeListDataListener (ListDataListener l ){
    	ArrayUtils.removeFromArray(listeners, l);
    }

    protected void  fireContentsChanged (Object target ,int index0 ,int index1 ,Array removedItems ){
		ListDataEvent e =new ListDataEvent(target ,index0 ,index1 ,removedItems );
	
		for(int i =listeners.length -1;i >=0;i --){
			ListDataListener lis =ListDataListener(listeners.get(i) );
			lis.contentsChanged(e);
		}
    }

    protected void  fireIntervalAdded (Object target ,int index0 ,int index1 ){
		ListDataEvent e =new ListDataEvent(target ,index0 ,index1 ,[] );
	
		for(int i =listeners.length -1;i >=0;i --){
			ListDataListener lis =ListDataListener(listeners.get(i) );
			lis.intervalAdded(e);     
		}
    }

    protected void  fireIntervalRemoved (Object target ,int index0 ,int index1 ,Array removedItems ){
		ListDataEvent e =new ListDataEvent(target ,index0 ,index1 ,removedItems );
	
		for(int i =listeners.length -1;i >=0;i --){
			ListDataListener lis =ListDataListener(listeners.get(i) );
			lis.intervalRemoved(e);
		}		
    }
}


