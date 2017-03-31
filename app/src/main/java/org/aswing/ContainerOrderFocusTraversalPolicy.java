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


/**
 * A FocusTraversalPolicy that determines traversal order based on the order
 * of child Components in a Container.
 * 
 * @author iiley 
 */
public class ContainerOrderFocusTraversalPolicy implements FocusTraversalPolicy{
		
	public  ContainerOrderFocusTraversalPolicy (){
	}
	
	public Component  getComponentAfter (Component c )
	{
		return getComponentAfterImp(c, true);
	}
	
	protected Component  getComponentAfterImp (Component c ,boolean deepIn =true ){
		if(c == null){
			return null;
		}
		if((c is Container) && deepIn){
			Component fc =getFirstComponent(c as Container );
			if(fc != null){
				return fc;
			}
		}
		Container container =c.getParent ();
		if(container == null){
			return getFirstComponent(c as Container);
		}
		int index =container.getIndex(c );
		int n =container.getComponentCount ();
		if(index >= 0){
			while((++index) < n){
				Component nc =getFocusableComponent(container.getComponent(index ));
				if(nc != null){
					return nc;
				}
			}
		}
		//up circle
		return getComponentAfterImp(container, false);
	}
	
	public Component  getComponentBefore (Component c )
	{
		return getComponentBeforeImp(c);
	}
	
	protected Component  getComponentBeforeImp (Component c ){
		if(c == null){
			return null;
		}
		Container container =c.getParent ();
		if(container == null){
			return getLastComponent(c as Container);
		}
		int index =container.getIndex(c );
		while((--index) >= 0){
			Component nc =getLastComponent(container.getComponent(index ));
			if(nc != null){
				return nc;
			}
		}
		if(accept(container)){
			return container;
		}
		//up circle
		return getComponentBeforeImp(container);
	}
	
	/**
	 * This will return the first focusable component in the container.
	 * @return the default component to be focused.
	 */
	public Component  getDefaultComponent (Container container )
	{
		return getFirstComponent(container);
	}
	
	/**
	 * Returns the first focusable component in the container.
	 */
	protected Component  getFirstComponent (Container container ){
		if(container == null){
			return null;
		}
		int index =-1;
		int n =container.getComponentCount ();
		while((++index) < n){
			Component nc =getFocusableComponent(container.getComponent(index ));
			if(nc != null){
				return nc;
			}
		}
		//do not up cirle here
		return null;
	}
	
	/**
	 * Returns the last focusable component in the component, if it is a container 
	 * deep into it to find the last.
	 */
	protected Component  getLastComponent (Component c ){
		Container container =(Container)c;
		if(container == null){
			if(accept(c)){
				return c;
			}else{
				return null;
			}
		}
		int index =container.getComponentCount ();
		while((--index) >= 0){
			Component theC =container.getComponent(index );
			if(isLeaf(theC)){
				if(accept(theC)){
					return theC;
				}
			}
			Component nc =getLastComponent((Container)theC );
			if(nc != null){
				return nc;
			}
		}
		if(accept(container)){
			return container;
		}
		//do not up cirle here
		return null;
	}
	
	private boolean  isLeaf (Component c ){
		if(c is Container){
			Container con =(Container)c;
			return con.getComponentCount() == 0;
		}
		return true;
	}
	
	private boolean  accept (Component c ){
		return c != null && c.isShowing() && c.isFocusable() && c.isEnabled();
	}
	
	private Component  getFocusableComponent (Component c ){
		if(c.isShowing() && c.isEnabled()){
			if(c.isFocusable()){
				return c;
			}else if(c is Container){//down circle
				Container con =(Container)c;
				Component conDefault =con.getFocusTraversalPolicy ().getDefaultComponent(con );
				if(conDefault != null){
					return conDefault;
				}
			}
		}
		return null;
	}
	
}


