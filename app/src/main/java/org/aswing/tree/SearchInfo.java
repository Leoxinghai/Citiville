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
import org.aswing.tree.AbstractLayoutCache;
import org.aswing.tree.FHTreeStateNode;
import org.aswing.tree.TreePath;

/**
 * @author iiley
 */
public class SearchInfo {
	public FHTreeStateNode node ;
	public boolean isNodeParentNode ;
	public double childIndex ;
	private AbstractLayoutCache layoutCatch ;
	
	public  SearchInfo (AbstractLayoutCache layoutCatch ){
		this.layoutCatch = layoutCatch;
	}

	public TreePath  getPath (){
	    if(node == null){
			return null;
	    }

	    if(isNodeParentNode){
			return node.getTreePath().pathByAddingChild(layoutCatch.getModel().getChild(node.getUserObject(),
						     childIndex));
	    }
	    return node.getTreePath();
	}	
}


