/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.table.sorter;

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


import org.aswing.table.TableModel;

/**
 * @author iiley
 */
public class Row{
	
	public int modelIndex ;
	public TableSorter tableSorter ;

    public  Row (TableSorter tableSorter ,int index ){
    	this.tableSorter = tableSorter;
        this.modelIndex = index;
    }

    public int  compareTo (*)o {
        int row1 =modelIndex ;
        int row2 =(Row(o )).modelIndex ;
		Array sortingColumns =tableSorter.getSortingColumns ();
		TableModel tableModel =tableSorter.getTableModel ();
        for(int i =0;i <sortingColumns.length ;i ++){
            Directive directive =Directive(sortingColumns.get(i) );
            int column =directive.column ;
            o1 = tableModel.getValueAt(row1,column);
            o2 = tableModel.getValueAt(row2,column);

            int comparison =0;
            // Define null less than everything, except null.
            if (o1 == null && o2 == null) {
                comparison = 0;
            } else if (o1 == null) {
                comparison = -1;
            } else if (o2 == null) {
                comparison = 1;
            } else {
            	Function comparator =tableSorter.getComparator(column );
                comparison = comparator(o1, o2);
            }
            if (comparison != 0) {
                return directive.direction == TableSorter.DESCENDING ? -comparison : comparison;
            }
        }
        return 0;
    }
    
    public int  getModelIndex (){
    	return modelIndex;
    }
}


