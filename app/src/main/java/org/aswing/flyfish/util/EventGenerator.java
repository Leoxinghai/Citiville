package org.aswing.flyfish.util;

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

import org.aswing.util.*;
    public class EventGenerator extends Object
    {
        private HashMap map ;
        private HashMap onceMap ;

        public  EventGenerator ()
        {
            this.map = new HashMap();
            this.onceMap = new HashMap();
            return;
        }//end

        public boolean  addListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            Array _loc_5 =null ;
            _loc_4 = this.map.getValue(param1 );
            if (_loc_4 == null)
            {
                _loc_4 = new Array();
                this.map.put(param1, _loc_4);
            }
            if (ArrayUtils.indexInArray(_loc_4, param2) < 0)
            {
                _loc_4.push(param2);
                if (param3)
                {
                    _loc_5 = this.onceMap.getValue(param1);
                    if (_loc_5 == null)
                    {
                        _loc_5 = new Array();
                        this.onceMap.put(param1, _loc_5);
                    }
                    _loc_5.push(param2);
                }
                return true;
            }
            return false;
        }//end

        public boolean  removeListener (String param1 ,Function param2 )
        {
            _loc_3 = this.map.getValue(param1 );
            if (_loc_3)
            {
                return ArrayUtils.removeFromArray(_loc_3, param2) >= 0;
            }
            return false;
        }//end

        public void  dispatchEvent (String param1 ,Array param2 )
        {
            Function _loc_4 =null ;
            Array _loc_5 =null ;
            Function _loc_6 =null ;
            _loc_3 = this.map.getValue(param1 );
            if (_loc_3)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    this.callFunction(_loc_4, param2);
                }
                _loc_5 = this.onceMap.remove(param1);
                if (_loc_5)
                {
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);

                        ArrayUtils.removeFromArray(_loc_3, _loc_6);
                    }
                }
            }
            return;
        }//end

        protected void  callFunction (Function param1 ,Array param2 )
        {
            int _loc_3 =0;
            if (param2)
            {
                _loc_3 = param2.length;
                if (_loc_3 == 0)
                {
                    param1();
                }
                else if (_loc_3 == 1)
                {
                    param1(param2.get(0));
                }
                else if (_loc_3 == 2)
                {
                    param1(param2.get(0), param2.get(1));
                }
                else if (_loc_3 == 3)
                {
                    param1(param2.get(0), param2.get(1), param2.get(2));
                }
                else if (_loc_3 == 4)
                {
                    param1(param2.get(0), param2.get(1), param2.get(2), param2.get(3));
                }
                else if (_loc_3 == 5)
                {
                    param1(param2.get(0), param2.get(1), param2.get(2), param2.get(3), param2.get(4));
                }
                else if (_loc_3 == 6)
                {
                    param1(param2.get(0), param2.get(1), param2.get(2), param2.get(3), param2.get(4), param2.get(5));
                }
                else if (_loc_3 == 7)
                {
                    param1(param2[0], param2.get(1), param2.get(2), param2.get(3), param2.get(4), param2.get(5), param2.get(6));
                }
                else if (_loc_3 == 8)
                {
                    param1(param2[0], param2[1], param2.get(2), param2.get(3), param2.get(4), param2.get(5), param2.get(6), param2.get(7));
                }
                else if (_loc_3 == 9)
                {
                    param1(param2[0], param2[1], param2[2], param2.get(3), param2.get(4), param2.get(5), param2.get(6), param2.get(7), param2.get(8));
                }
                else
                {
                    throw new Error("Can\'t support param count > 9!");
                }
            }
            else
            {
                param1();
            }
            return;
        }//end

    }


