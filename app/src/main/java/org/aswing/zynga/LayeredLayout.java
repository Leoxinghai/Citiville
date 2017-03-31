package org.aswing.zynga;

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
import org.aswing.geom.*;

    public class LayeredLayout extends EmptyLayout
    {

        public  LayeredLayout ()
        {
            return;
        }//end

         public IntDimension  preferredLayoutSize (Container param1 )
        {
            Component _loc_5 =null ;
            IntDimension _loc_6 =null ;
            _loc_2 = new IntDimension(0,0);
            _loc_3 = param1.getComponentCount ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = param1.getComponent(_loc_4);
                _loc_6 = _loc_5.getPreferredSize();
                if (_loc_6.width > _loc_2.width)
                {
                    _loc_2.width = _loc_6.width;
                }
                if (_loc_6.height > _loc_2.height)
                {
                    _loc_2.height = _loc_6.height;
                }
                _loc_4++;
            }
            return param1.getInsets().getOutsideSize(_loc_2);
        }//end

         public IntDimension  minimumLayoutSize (Container param1 )
        {
            Component _loc_5 =null ;
            IntDimension _loc_6 =null ;
            _loc_2 = new IntDimension(0,0);
            _loc_3 = param1.getComponentCount ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = param1.getComponent(_loc_4);
                _loc_6 = _loc_5.getMinimumSize();
                if (_loc_6.width > _loc_2.width)
                {
                    _loc_2.width = _loc_6.width;
                }
                if (_loc_6.height > _loc_2.height)
                {
                    _loc_2.height = _loc_6.height;
                }
                _loc_4++;
            }
            return param1.getInsets().getOutsideSize(_loc_2);
        }//end

         public IntDimension  maximumLayoutSize (Container param1 )
        {
            Component _loc_5 =null ;
            IntDimension _loc_6 =null ;
            _loc_2 = new IntDimension(0,0);
            _loc_3 = param1.getComponentCount ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = param1.getComponent(_loc_4);
                _loc_6 = _loc_5.getMaximumSize();
                if (_loc_6.width > _loc_2.width)
                {
                    _loc_2.width = _loc_6.width;
                }
                if (_loc_6.height > _loc_2.height)
                {
                    _loc_2.height = _loc_6.height;
                }
                _loc_4++;
            }
            return param1.getInsets().getOutsideSize(_loc_2);
        }//end

         public void  layoutContainer (Container param1 )
        {
            Component _loc_7 =null ;
            _loc_2 = param1.getComponentCount ();
            _loc_3 = param1.getSize ();
            _loc_4 = param1.getInsets ();
            _loc_5 = param1.getInsets ().getInsideBounds(_loc_3.getBounds ());
            int _loc_6 =0;
            while (_loc_6 < _loc_2)
            {

                _loc_7 = param1.getComponent(_loc_6);
                _loc_7.setComBounds(_loc_5);
                _loc_6++;
            }
            return;
        }//end

    }


