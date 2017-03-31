/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table;

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


import org.aswing.JTable;
import org.aswing.UIManager;

/**
 * Default table header cell to render text
 * @author iiley
 */
public class DefaultTextHeaderCell extends DefaultTextCell{
	
	public  DefaultTextHeaderCell (){
		super();
		setHorizontalAlignment(CENTER);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setBackgroundDecorator(UIManager.getGroundDecorator("TableHeader.cellBackground"));
		setOpaque(false);
	}
	
	 public void  setTableCellStatus (JTable table ,boolean isSelected ,int row ,int column ){
		JTableHeader header =table.getTableHeader ();
		if(header != null){
			setBackground(header.getBackground());
			setForeground(header.getForeground());
			setFont(header.getFont());
		}
	}
}


