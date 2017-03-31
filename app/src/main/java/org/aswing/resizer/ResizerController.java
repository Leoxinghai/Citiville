/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.resizer;

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


import org.aswing.Component;
import org.aswing.resizer.DefaultResizer;
import org.aswing.resizer.Resizer;
import org.aswing.util.HashMap;

/**
 * ResizerController make manage resizer feature easily.
 * 
 * <pre>
 *JButton button =new JButton("click");
 *  button.setSize( 100,25 );
 * 	button.setLocation( 100, 100 );
 * 	
 *JLabel label1 =new JLabel("avoid resizing");
 * 	label1.setSize(100, 25);
 * 	label1.setLocation( 200, 50 );
 * 	
 *JLabel label2 =new JLabel("ok for resizing");
 * 	label2.setSize(100, 25);
 * 	label2.setLocation( 50, 50 );
 * 	label2.setOpaque( true );
 * 
 * 	try
 * 	{
 * 		// Direct affectation
 *ResizerController resizer =ResizerController.init(button );
 * 		
 * 		// Simple call to #init method
 * 		ResizerController.init( label1 );
 * 		ResizerController.init( label2 );
 * 	}
 * 	catch( e : Error )
 * 	{
 * 		// log error message
 * 	}
 * 	
 * 	//We can retreive ResizerController for a specific component like :
 *ResizerController labelResizer =ResizerController.getController(label1 );
 * 	labelResizer.setResizable( false );
 * 	
 * 	resizer.setResizeDirectly( true );
 * 	
 * 	// content must have a layout set to EmptyLayout to allow correct resizing
 * 	content.append( button );
 * 	content.append( label1 );
 * 	content.append( label2 );
 * </pre>
 * <p>
 * Many thanks Romain Ecarnot, this class is based on his ResizerController, and all resizer implement 
 * are inspired by him.
 * </p>
 * @author Romain Ecarnot
 * @author iiley
 */
public class ResizerController{
	//-------------------------------------------------------------------------
	// Private properties
	//-------------------------------------------------------------------------
	
	private static Class defaultResizerClass ;
	
	private Component _component ;
	
	private Resizer _resizer ;
	private boolean _resizable ;
	private boolean _resizableDirectly ;
	
	
	//-------------------------------------------------------------------------
	// Public API
	//-------------------------------------------------------------------------
	
	/**
	 * Sets the default resizer class.
	 * @param cl the default resizer class.
	 */
	public static void  setDefaultResizerClass (Class cl ){
		defaultResizerClass = cl;
	}
	
	/**
	 * Returns the default resizer class.
	 */
	public static Class  getDefaultResizerClass (){
		return defaultResizerClass;
	}
	
	/**
	 * Create resizing behaviour to passed-in component.
	 * @param comp the component which need to be resizable
	 * @param resizer (optional)the resizer, default there will be a default one instance created.
	 */
	public static ResizerController  create (Component comp ,Resizer resizer =null )
	{
		return new ResizerController( comp , resizer);
	}
		
	/**
	 * Returns reference to the real used component.
	 */
	public Component  getComponent ()
	{
		return _component;
	}
	
	/**
	 * Returns whether this component is resizable by the user.
	 * 
	 * <p>By default, all components are initially resizable. 
	 * 
	 * @see #setResizable()
	 */
	public boolean  isResizable ()
	{
		return _resizable;
	}
	
	/**
	 * Sets whether this component is resizable by the user.
	 * 
	 * @param b {@code true} user can resize the component by 
	 * drag to scale the component, {@code false} user can't.
	 * 
	 * @see #isResizable()
	 */
	public void  setResizable (boolean b )
	{
		if( _resizable != b )
		{
			_resizable = b;  
			_resizer.setEnabled( isResizable() );
		}
	}
	
	/**
	 * Returns the resizer controller defined in {@link #setResizer}.
	 * 
	 * @see #setResizer
	 */
	public Resizer  getResizer ()
	{
		return _resizer;
	}
	
	/**
	 * Sets the {@link Resizer} controller.
	 * 
	 * <p>By default, use {@code Frame.resizer} one.
	 * 
	 * @param r {@link Resizer} instance
	 * 
	 * @see #getResizer
	 */
	public void  setResizer (Resizer r )
	{
		if( r != _resizer )
		{
			_destroyResizer();
			_resizer = r;
			_initResizer();
		}
	}
	
	/**
	 * Return whether need resize widget directly when drag the resizer arrow.
	 * 
	 * @see #setResizeDirectly()
	 */
	public boolean  isResizeDirectly ()
	{
		return _resizableDirectly;
	}
	
	/**
	 * Indicate whether need resize widget directly when drag the resizer arrow.
	 * 
	 * <p>if set to {@code false}, there will be a rectange to represent then size what 
	 * will be resized to.
	 * 
	 * <p>if set to {@code true}, the widget will be resize directly when drag, but this 
	 * is need more cpu counting.<br>
	 * 
	 * <p>Default is {@coe false}.
	 * 
	 * @see org.aswing.Resizer#setResizeDirectly()
	 */
	public void  setResizeDirectly (boolean b )
	{
		_resizableDirectly = b;
		_resizer.setResizeDirectly(b);
	}
	
	
	//-------------------------------------------------------------------------
	// Private implementation
	//-------------------------------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param comp Component where applying resize behaviour.
	 * @param resizer the resizer, default is null means to create a default one.
	 */
	public  ResizerController (Component comp ,Resizer resizer =null )
	{
		if( !comp) 
		{
			throw new Error("illegal component when insert to ResizerContainer");
		}
		else
		{
			_registerComponent( comp , resizer);
		}
	}	
	
	/**
	 * Registers couple component / controller to the hashmap.
	 * 
	 * @param comp Component where applying resize behaviour.
	 * @param resizer the resizer, default is null means to create a default one.
	 */
	private void  _registerComponent (Component comp ,Resizer resizer =null )
	{
		_component = comp;
		 
		_resizable = true;
		_resizableDirectly = false;
		if(resizer == null){
			if(getDefaultResizerClass() == null){
				setDefaultResizerClass(DefaultResizer);
			}
			Class cl =getDefaultResizerClass ();
			resizer = new cl();
			if(resizer == null){
				throw new Error("The defaultResizerClass is set wrong!!");
			}
		}
		setResizer( resizer );
	}
	
	private void  _initResizer (){
		_resizer.setOwner(getComponent());
		_resizer.setEnabled(isResizable());
		_resizer.setResizeDirectly(isResizeDirectly());
	}
	
	private void  _destroyResizer (){
		if(_resizer != null){
			_resizer.setOwner(null);
			_resizer = null;
		}
	}
	
	/**
	 * Destroy this resizercontroller, set the resizer to null.
	 */
	public void  destroy ()
	{   
		_destroyResizer();
		_component = null;
	}
	
}


