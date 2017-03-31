package org.aswing.flyfish.css.property.filter;

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

//import flash.filters.*;
import org.aswing.flyfish.*;
import org.aswing.util.*;

    public class FilterDef extends Object
    {
        private Class clazz ;
        private HashMap proMap ;

        public  FilterDef (Class param1 ,...args )
        {
            argsvalue = null;
            this.clazz = param1;
            this.proMap = new HashMap();
            for(int i0 = 0; i0 < args.size(); i0++) 
            {
            		argsvalue = args.get(i0);

                this.proMap.put(argsvalue.getName(), args);
            }
            return;
        }//end

        public BitmapFilter  decode (Array param1 )
        {
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            FilterProperty _loc_7 =null ;
            _loc_2 = new this.clazz ();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_4 = _loc_3.split("=");
                if (_loc_4.length == 2)
                {
                    _loc_5 = StringUtils.trim(_loc_4.get(0));
                    _loc_6 = StringUtils.trim(_loc_4.get(1));
                    _loc_7 = this.proMap.getValue(_loc_5);
                    if (_loc_7)
                    {
                        _loc_2.put(_loc_5,  _loc_7.decode(_loc_6));
                    }
                    else
                    {
                        AGLog.warn("Unknown filter property \'" + _loc_5 + "\' of filter " + this.clazz);
                    }
                    continue;
                }
                AGLog.warn("Wrong filter property syntax\'" + _loc_3 + "\' of filter " + this.clazz);
            }
            return _loc_2;
        }//end

    }


