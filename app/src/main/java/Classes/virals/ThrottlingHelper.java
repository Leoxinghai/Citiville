package Classes.virals;

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

import Classes.util.*;
    public class ThrottlingHelper
    {
        protected int m_throttle ;
        protected Object m_data ;

        public  ThrottlingHelper (int param1 ,Object param2 )
        {
            this.m_throttle = param1;
            this.m_data = param2;
            this.purge();
            return;
        }//end

        public void  add (String param1 )
        {
            if (!this.m_data)
            {
                this.m_data = {};
            }
            this.m_data.put(param1,  DateUtil.getUnixTime());
            return;
        }//end

        public boolean  isThrottled (String param1 )
        {
            int _loc_4 =0;
            boolean _loc_2 =false ;
            _loc_3 = DateUtil.getUnixTime();
            if (this.m_data && this.m_data.get(param1))
            {
                _loc_4 = _loc_3 - this.m_data.get(param1);
                if (_loc_4 < this.m_throttle)
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public double  throttleEndTime ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 =(double).MAX_VALUE ;
            if (this.m_data)
            {
                for(int i0 = 0; i0 < this.m_data.size(); i0++) 
                {
                	_loc_2 = this.m_data.get(i0);

                    _loc_3 = this.m_data.get(_loc_2);
                    if (_loc_3 < _loc_1)
                    {
                        _loc_1 = _loc_3;
                    }
                }
                _loc_1 = _loc_1 + this.m_throttle;
            }
            else
            {
                _loc_1 = 0;
            }
            return _loc_1 * 1000;
        }//end

        public void  purge ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = DateUtil.getUnixTime();
            if (this.m_data)
            {
                for(int i0 = 0; i0 < this.m_data.size(); i0++) 
                {
                	_loc_2 = this.m_data.get(i0);

                    _loc_3 = _loc_1 - this.m_data.get(_loc_2);
                    if (_loc_3 >= this.m_throttle)
                    {
                        delete this.m_data.get(_loc_2);
                    }
                }
            }
            return;
        }//end

    }



