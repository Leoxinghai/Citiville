package Display.aswingui;

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

import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class GridListHorizontalLayout extends GridListLayout
    {

        public  GridListHorizontalLayout (int param1 =1,int param2 =1,int param3 =2,int param4 =2)
        {
            super(param1, param2, param3, param4);
            if (param1 == 0 || param2 == 0)
            {
                throw new ArgumentError("neither rows nor columns can be zero");
            }
            return;
        }//end  

         public IntDimension  preferredLayoutSize (Container param1 )
        {
            _loc_2 = getColumns();
            _loc_3 = getRows();
            _loc_4 = getHgap();
            _loc_5 = getVgap();
            if (_loc_2 == 0 || _loc_3 == 0)
            {
                throw new Error("neither rows nor columns can be zero");
            }
            _loc_6 = param1.getInsets();
            _loc_7 = param1.getComponentCount();
            _loc_8 = Math.ceil(_loc_7/(_loc_3*_loc_2));
            _loc_9 = getTileWidth();
            _loc_10 = getTileHeight();
            _loc_11 = _loc_6.left+_loc_6.right+_loc_8*_loc_2*_loc_9+(_loc_8*_loc_2-1)*_loc_4;
            _loc_12 = _loc_6.top+_loc_6.bottom+_loc_3*_loc_10+(_loc_3-1)*_loc_5;
            return new IntDimension(_loc_11, _loc_12);
        }//end  

         public IntDimension  getViewSize (GridCellHolder param1 )
        {
            return this.preferredLayoutSize(param1);
        }//end  

         public void  layoutContainer (Container param1 )
        {
            int _loc_15 =0;
            int _loc_16 =0;
            int _loc_17 =0;
            _loc_2 = getColumns();
            _loc_3 = getRows();
            _loc_4 = getHgap();
            _loc_5 = getVgap();
            if (_loc_2 == 0 || _loc_3 == 0)
            {
                throw new Error("neither rows nor columns can be zero");
            }
            _loc_6 = param1.getInsets();
            _loc_7 = param1.getComponentCount();
            if (param1.getComponentCount() == 0)
            {
                return;
            }
            _loc_8 = GridCellHolder(param1).getList();
            _loc_9 = Math.ceil(_loc_7/(_loc_3*_loc_2));
            _loc_10 = getTileWidth();
            _loc_11 = getTileHeight();
            _loc_12 = _loc_6.left;
            _loc_13 = _loc_6.top;
            int _loc_14 =0;
            while (_loc_14 < _loc_9)
            {
                
                _loc_13 = _loc_6.top;
                _loc_15 = 0;
                while (_loc_15 < _loc_3)
                {
                    
                    _loc_12 = _loc_6.left + _loc_14 * _loc_2 * (_loc_10 + _loc_4);
                    _loc_16 = 0;
                    while (_loc_16 < _loc_2)
                    {
                        
                        _loc_17 = _loc_14 * _loc_3 * _loc_2 + _loc_15 * _loc_2 + _loc_16;
                        if (_loc_17 < _loc_7)
                        {
                            param1.getComponent(_loc_17).setBounds(new IntRectangle(_loc_12, _loc_13, _loc_10, _loc_11));
                            _loc_8.getCellByIndex(_loc_17).setGridListCellStatus(_loc_8, _loc_8.isSelectedIndex(_loc_17), _loc_17);
                        }
                        else
                        {
                            return;
                        }
                        _loc_12 = _loc_12 + (_loc_10 + _loc_4);
                        _loc_16++;
                    }
                    _loc_13 = _loc_13 + (_loc_11 + _loc_5);
                    _loc_15++;
                }
                _loc_14++;
            }
            return;
        }//end  

    }



