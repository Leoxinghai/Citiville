package Classes.util;

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

    public class DateUtil
    {
        public static int SECONDS_PER_DAY =86400;
        public static int SECONDS_PER_HOUR =3600;
        public static int MILLIS_PER_DAY =86400000;
        public static int MILLIS_PER_HOUR =3600000;
        public static int MILLIS_PER_MINUTE =60000;
        public static int MILLIS_PER_SECOND =1000;

        public  DateUtil ()
        {
            return;
        }//end

        public static Object  calculateTimeDifference (double param1 )
        {
            _loc_2 = param1/MILLIS_PER_DAY ;
            _loc_3 = param1-_loc_2 *MILLIS_PER_DAY ;
            _loc_4 = _loc_3/MILLIS_PER_HOUR ;
            _loc_3 = _loc_3 - _loc_4 * MILLIS_PER_HOUR;
            _loc_5 = _loc_3/MILLIS_PER_MINUTE ;
            _loc_3 = _loc_3 - _loc_5 * MILLIS_PER_MINUTE;
            _loc_6 = _loc_3/MILLIS_PER_SECOND ;
            return {days:_loc_2, hours:_loc_4, minutes:_loc_5, seconds:_loc_6};
        }//end

        public static String  getCounterNumberFormat (int param1 =-1,int param2 =-1,int param3 =-1,int param4 =-1)
        {
            int _loc_8 =0;
            Array _loc_5 =.get(param1 ,param2 ,param3 ,param4) ;
            String _loc_6 ="";
            int _loc_7 =0;
            while (_loc_7 < _loc_5.length())
            {

                _loc_8 = _loc_5.get(_loc_7);
                if (_loc_8 >= 0)
                {
                    if (_loc_8 > 9)
                    {
                        _loc_6 = _loc_6 + ("" + _loc_8);
                    }
                    else
                    {
                        _loc_6 = _loc_6 + ("0" + _loc_8);
                    }
                    if (_loc_7 < (_loc_5.length - 1))
                    {
                        _loc_6 = _loc_6 + ":";
                    }
                }
                _loc_7++;
            }
            return _loc_6;
        }//end

        public static int  convertTimeStrToSeconds (String param1 )
        {
            _loc_2 = param1.split(":");
            _loc_3 = parseInt(_loc_2.get(0) )*3600+parseInt(_loc_2.get(1) )*60+parseInt(_loc_2.get(2) );
            return _loc_3;
        }//end

        public static String  getFormattedDayCounter (int param1 ,int param2 =3)
        {
            double _loc_5 =0;
            _loc_3 = DateUtil.calculateTimeDifference(param1*1000);
            String _loc_4 ="";
            if (param1 > SECONDS_PER_DAY * param2)
            {
                _loc_5 = Math.ceil(Number(param1) / Number(SECONDS_PER_DAY));
                _loc_4 = String(_loc_5);
            }
            else
            {
                _loc_4 = DateUtil.getCounterNumberFormat(-1, _loc_3.hours + _loc_3.days * 24, _loc_3.minutes, _loc_3.seconds);
            }
            return _loc_4;
        }//end

        public static int  getUnixTime ()
        {
            return uint(GlobalEngine.getTimer() / 1000);
        }//end

    }



