package Classes;

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

    public class TimestampEvents
    {
        public static  int TIME_NONE =0;
        public static  int TIME_CHRISTMAS2010 =1;
        public static  int TIME_CHRISTMAS2010_WARNING =2;
        public static  int TIME_CHRISTMAS2010_END =3;
        public static  int TIME_COUNT =4;
        private static  int m_TimestampData_ID =0;
        private static  int m_TimestampData_TEXTID =1;
        private static  int m_TimestampData_TIMESTAMPDATA =2;
        private static Array m_TimestampData =new Array(new Array(TIME_NONE ,"none","2010:12:01:00:00:00"),new Array(TIME_CHRISTMAS2010 ,"christmas2010","2010:12:24:18:00:00"),new Array(TIME_CHRISTMAS2010_WARNING ,"christmas2010_warning","2011:01:03:00:00:00"),new Array(TIME_CHRISTMAS2010_END ,"christmas2010_end","2011:01:08:00:00:00"));

        public  TimestampEvents ()
        {
            return;
        }//end

        private static String  getTimestmpDataFromID (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < TIME_COUNT)
            {

                if (param1 == m_TimestampData.get(_loc_2).get(m_TimestampData_TEXTID))
                {
                    return m_TimestampData.get(_loc_2).get(m_TimestampData_TIMESTAMPDATA);
                }
                _loc_2++;
            }
            return "";
        }//end

        private static Date  parseDateFromTimestampID (String param1 )
        {
            Date _loc_2 =new Date(0,0,0,0,0,0,0);
            _loc_3 = getTimestmpDataFromID(param1).split(":");
            if (_loc_3.length == 6)
            {
                _loc_2 = new Date(parseInt(_loc_3.get(0)), (parseInt(_loc_3.get(1)) - 1), parseInt(_loc_3.get(2)), parseInt(_loc_3.get(3)), parseInt(_loc_3.get(4)), parseInt(_loc_3.get(5)), 0);
            }
            return _loc_2;
        }//end

        public static Date  parseDateFromTimestampString (String param1 )
        {
            Date _loc_2 =new Date(0,0,0,0,0,0,0);
            _loc_3 = param1.split(":");
            if (_loc_3.length == 6)
            {
                _loc_2 = new Date(parseInt(_loc_3.get(0)), (parseInt(_loc_3.get(1)) - 1), parseInt(_loc_3.get(2)), parseInt(_loc_3.get(3)), parseInt(_loc_3.get(4)), parseInt(_loc_3.get(5)), 0);
            }
            return _loc_2;
        }//end

        public static boolean  checkCurrentTimeBeforeTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.currentTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 < _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeBeforeOrDuringTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.currentTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 <= _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkLastTimeAfterOrDuringTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.previousTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 >= _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeAfterOrDuringTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.currentTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 >= _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkLastTimeAfterTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.previousTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 > _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeAfterTime (int param1 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            _loc_2 = parseDateFromTimestampID(Global.player.currentTimestampEventID);
            _loc_3 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA) );
            if (_loc_2 > _loc_3)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkLastTimeBetweenRange (int param1 ,int param2 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            if (param2 < TIME_NONE && param2 >= TIME_COUNT)
            {
                return false;
            }
            _loc_3 = parseDateFromTimestampID(Global.player.previousTimestampEventID);
            _loc_4 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA));
            _loc_5 = parseDateFromTimestampString(m_TimestampData.get(param2).get(m_TimestampData_TIMESTAMPDATA));
            if (_loc_3 >= _loc_4 && _loc_3 < _loc_5)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkTimestampAfterUnixTime (String param1 ,double param2 )
        {
            _loc_3 = parseDateFromTimestampString(param1 );
            Date _loc_4 =new Date(param2 );
            if (_loc_3.time <= 0 || _loc_4.time <= 0)
            {
                return false;
            }
            return _loc_3.time > _loc_4.time;
        }//end

        public static boolean  checkTimestampBeforeUnixTime (String param1 ,double param2 )
        {
            _loc_3 = parseDateFromTimestampString(param1 );
            Date _loc_4 =new Date(param2 );
            if (_loc_3.time <= 0 || _loc_4.time <= 0)
            {
                return false;
            }
            return _loc_3.time < _loc_4.time;
        }//end

        public static boolean  checkCurrentTimeBetweenRange (int param1 ,int param2 )
        {
            if (param1 < TIME_NONE && param1 >= TIME_COUNT)
            {
                return false;
            }
            if (param2 < TIME_NONE && param2 >= TIME_COUNT)
            {
                return false;
            }
            _loc_3 = parseDateFromTimestampID(Global.player.currentTimestampEventID);
            _loc_4 = parseDateFromTimestampString(m_TimestampData.get(param1).get(m_TimestampData_TIMESTAMPDATA));
            _loc_5 = parseDateFromTimestampString(m_TimestampData.get(param2).get(m_TimestampData_TIMESTAMPDATA));
            if (_loc_3 >= _loc_4 && _loc_3 < _loc_5)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeAfterOrDuringTime_FirstTimeOnly (int param1 )
        {
            if (checkCurrentTimeAfterOrDuringTime(param1) && !checkLastTimeAfterOrDuringTime(param1))
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeAfterTime_FirstTimeOnly (int param1 )
        {
            if (checkCurrentTimeAfterTime(param1) && !checkLastTimeAfterTime(param1))
            {
                return true;
            }
            return false;
        }//end

        public static boolean  checkCurrentTimeBetweenRange_FirstTimeOnly (int param1 ,int param2 )
        {
            if (checkCurrentTimeBetweenRange(param1, param2) && !checkLastTimeBetweenRange(param1, param2))
            {
                return true;
            }
            return false;
        }//end

        public static boolean  isCurrentTimeBetweeenRange (String param1 ,String param2 )
        {
            Date _loc_3 =null ;
            Date _loc_4 =null ;
            if (param1 !=null)
            {
                _loc_3 = parseDateFromTimestampString(param1);
            }
            if (param2)
            {
                _loc_4 = parseDateFromTimestampString(param2);
            }
            Date _loc_5 =new Date ();
            if (_loc_3 && _loc_4)
            {
                return _loc_5.time >= _loc_3.time && _loc_5.time <= _loc_4.time;
            }
            if (_loc_4)
            {
                return _loc_5.time <= _loc_4.time;
            }
            if (_loc_3)
            {
                return _loc_5.time >= _loc_3.time;
            }
            return false;
        }//end

        public static boolean  hasTimestampEventChanged ()
        {
            return Global.player.currentTimestampEventID != Global.player.previousTimestampEventID;
        }//end

    }



