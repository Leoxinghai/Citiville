
package org.aswing.dnd;

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
import org.aswing.geom.IntPoint;
import org.aswing.event.DragAndDropEvent;

/**
 * Drag and Drop listener.
 * 
 * @author iiley
 */
public interface DragListener{

	/**
	 * When a drag action started.
	 * @param e the event.
	 * @see Component#isDragEnabled()
	 */
	void  onDragStart (DragAndDropEvent e );
	
	/**
	 * Called while a drag operation is ongoing, when the mouse pointer enters a 
	 * drop trigger component area.
	 * @param e the event.
	 * @see Component#isDropTrigger()
	 */
	void  onDragEnter (DragAndDropEvent e );
	
	/**
	 * Called when a drag operation is ongoing(mouse is moving), while the mouse 
	 * pointer is still over the entered component area.
	 * @param e the event.
	 * @see Component#isDropTrigger()
	 */
	void  onDragOverring (DragAndDropEvent e );
	
	/**
	 * Called while a drag operation is ongoing, when the mouse pointer has exited 
	 * the entered a drop trigger component. 
	 * @param e the event.
	 * @see Component#isDropTrigger()
	 */
	void  onDragExit (DragAndDropEvent e );
	
	/**
	 * Called when drag operation finished.
	 * <p>
	 * Generally if you want to do a custom motion of the dragging movie clip when 
	 * dropped, you may call the DragManager.setDropMotion() method to achieve.
	 * </p>
	 * @param e the event.
	 * @see Component#isDropTrigger()
	 * @see org.aswing.dnd.DragManager#setDropMotion()
	 */
	void  onDragDrop (DragAndDropEvent e );
	
}


