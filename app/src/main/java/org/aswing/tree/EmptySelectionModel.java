package org.aswing.tree;

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

/*
 Copyright aswing.org, see the LICENCE.txt.
*/

import org.aswing.tree.DefaultTreeSelectionModel;

/**
 * <code>EmptySelectionModel</code> is a <code>TreeSelectionModel</code>
 * that does not allow anything to be selected.
 * @author iiley
 */
public class EmptySelectionModel extends DefaultTreeSelectionModel {
	
	public  EmptySelectionModel (){
		super();
	}

    /** Unique shared instance. */
    private static EmptySelectionModel _sharedInstance ;

    /** Returns a shared instance of an empty selection model. */
    public static EmptySelectionModel  sharedInstance (){
    	if(_sharedInstance == null){
    		_sharedInstance = new EmptySelectionModel();
    	}
        return _sharedInstance;
    }

    /** A <code>null</code> implementation that selects nothing. */
     public void  setSelectionPaths (Array pPaths ,boolean programmatic =true ){}
    /** A <code>null</code> implementation that adds nothing. */
     public void  addSelectionPaths (Array pPaths ,boolean programmatic =true ){}
    /** A <code>null</code> implementation that removes nothing. */
     public void  removeSelectionPaths (Array pPaths ,boolean programmatic =true ){}
}


