package org.aswing.flyfish.css;

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
import org.aswing.util.*;

    public class StyleSheetList extends Object
    {
        protected HashMap map ;
        protected Array sortedSheets ;

        public  StyleSheetList ()
        {
            this.map = new HashMap();
            this.sortedSheets = null;
            return;
        }//end

        public void  combine (StyleSheetList param1 )
        {
            IStyleSheet _loc_3 =null ;
            _loc_2 = param1.toArray ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            		_loc_3 = _loc_2.get(i0);

                this.putStyleSheet(_loc_3);
            }
            this.sortedSheets = null;
            return;
        }//end

        public StyleSheetList  clone ()
        {
            IStyleSheet _loc_3 =null ;
            _loc_1 = new StyleSheetList ();
            _loc_2 = this.map.values ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_1.map.put(_loc_3.getSelector().getKey(), _loc_3);
            }
            return _loc_1;
        }//end

        public Array  toArray ()
        {
            if (this.sortedSheets)
            {
                return this.sortedSheets;
            }
            this.sortedSheets = this.map.values();
            this.sortedSheets.sort(this.__compareStyleSheet);
            return this.sortedSheets;
        }//end

        public void  putStyleSheet (IStyleSheet param1 )
        {
            _loc_2 = param1.getSelector ().getKey ();
            _loc_3 = this.map.getValue(_loc_2 );
            if (_loc_3)
            {
                _loc_3.combine(param1);
            }
            else
            {
                this.map.put(_loc_2, param1);
            }
            this.sortedSheets = null;
            return;
        }//end

        private int  __compareStyleSheet (IStyleSheet param1 ,IStyleSheet param2 )
        {
            _loc_3 = param1.getWeight ();
            _loc_4 = param2.getWeight ();
            if (_loc_3 > _loc_4)
            {
                return 1;
            }
            if (_loc_3 < _loc_4)
            {
                return -1;
            }
            return 0;
        }//end

        public LazyLoadRequestList  apply (Component param1 )
        {
            IStyleSheet _loc_4 =null ;
            _loc_2 = new LazyLoadRequestList ();
            _loc_3 = this.toArray ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.combine(_loc_4.apply(param1));
            }
            return _loc_2;
        }//end

    }


