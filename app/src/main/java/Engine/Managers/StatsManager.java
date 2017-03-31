package Engine.Managers;

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

import com.adobe.crypto.*;
import com.adobe.serialization.json.*;

import root.GlobalEngine;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class StatsManager
    {
        public static  int ONE_LINE_FEED =1;
        public static  int SHORT_FEED =2;
        public static  int APP_TO_USER_NOTIF =3;
        public static  int USER_TO_USER_NOTIF =4;
        public static  int STATUS =5;
        public static  int APP_EMAIL =6;
        public static  int REQUEST =7;
        public static  int FEED =8;
        public static  int MAX_CHARS =24;
        private static  int REPORT_DELAY =10000;
        private static  boolean IDLE_TIMER_ENABLED =true ;
        private static  boolean HEARTBEAT_STATS_ENABLED =false ;
        public static Array m_stats =new Array ();
        public static Timer m_timer =null ;
        public static boolean m_idle =false ;
        private static boolean m_enabled =true ;

        public  StatsManager ()
        {
            return;
        }//end

        public static void  addStatDataToBatch (String param1 , String ...args )
        {
            if (m_timer == null)
            {
                m_timer = new Timer(REPORT_DELAY);
                m_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);
                m_timer.start();
            }
            m_stats.push({statfunction:param1, data:args});
            return;
        }//end

        public static void  count (String param1 ,String param2,String param3,String param4,String param5,String param6,int param7)
        {
            GlobalEngine.msg("StatsManager.count", param1, param2, param3, param4, param5, param6);//, param7);
            addStatDataToBatch("count", param1, param2, param3, param4, param5, param6);//, param7);
            return;
        }//end

        public static void  economy (int param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",int param8 =1)
        {
            addStatDataToBatch("economy", param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

        public static void  trackMessage (int param1 ,String param2 ,int param3 ,String param4 ="",String param5 ="",String param6 ="",boolean param7 =true )
        {
            addStatDataToBatch("trackMessage", param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public static void  sample (int param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",int param8 =1)
        {
            if (willSampleGetRecorded(param1))
            {
                addStatDataToBatch("sample", param1, param2, param3, param4, param5, param6, param7, param8);
            }
            return;
        }//end

        private static boolean  willSampleGetRecorded (int param1 ,boolean param2 =true )
        {
            if (Config.DEBUG_MODE || GlobalEngine.getFlashVar("sampleWhitelist"))
            {
                return true;
            }
            if (param2)
            {
                if (Number(GlobalEngine.getFlashVar("snuid")) % param1 == 1)
                {
                    return true;
                }
                return false;
            }
            else
            {
                return true;
            }
        }//end

        public static void  perf (String param1 ="",String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="")
        {
            addStatDataToBatch("perf", param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public static void  perfSample (int param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",String param8 ="")
        {
            if (willSampleGetRecorded(param1))
            {
                addStatDataToBatch("perfSample", param1, param2, param3, param4, param5, param6, param7, param8);
            }
            return;
        }//end

        public static void  social (String param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",int param7 =0,String param8 ="")
        {
            GlobalEngine.msg("StatsManager.social", param1, param2, param3, param4, param5, param6, param7, param8);
            addStatDataToBatch("social", param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

        public static int  startExperiment (String param1 ,String param2 ,int param3 )
        {
            int _loc_4 =0;
            int _loc_5 =7;
            _loc_6 = MD5.hash(param2+param1);
            _loc_7 = parseInt("0x"+_loc_6.substr(-_loc_5));
            _loc_4 = parseInt("0x" + _loc_6.substr(-_loc_5)) % (param3 + 1);
            addStatDataToBatch("setUserExperimentVariant", param1, param2, _loc_4);
            return _loc_4;
        }//end

        public static void  experimentGoal (String param1 ,String param2 ,int param3 =0)
        {
            addStatDataToBatch("experimentGoal", param1, param2, param3);
            return;
        }//end

        public static void  notIdle ()
        {
            m_idle = false;
            return;
        }//end

        public static void  sendStats (boolean param1 =false )
        {
            m_stats = new Array();
            return;

            Object _loc_2 =null ;
            Object _loc_3 =null ;
            URLRequest _loc_4 =null ;
            URLLoader _loc_5 =null ;
            if (m_stats.length && m_enabled == true)
            {
                _loc_2 = new Object();
                _loc_2.signedParams = TransactionManager.additionalSignedParams;
                _loc_2.stats = m_stats;
                _loc_3 = com.adobe.serialization.json.JSON.encode(_loc_2);
                _loc_4 = new URLRequest(Config.RECORD_STATS_PATH);
                _loc_4.method = URLRequestMethod.POST;
                _loc_4.contentType = "application/json";
                _loc_4.data = _loc_3;
                _loc_5 = new URLLoader();
                _loc_5.dataFormat = URLLoaderDataFormat.BINARY;
                _loc_5.load(_loc_4);
                m_stats = new Array();
            }
            return;
        }//end

        private static void  onTimer (TimerEvent event )
        {
            if (HEARTBEAT_STATS_ENABLED)
            {
                if (m_idle && IDLE_TIMER_ENABLED)
                {
                    count("timer", "idle");
                }
                else
                {
                    count("timer", "active");
                }
            }
            sendStats();
            m_idle = true;
            return;
        }//end

        public static void  milestone (String param1 ,double param2)
        {
            addStatDataToBatch("milestone", param1, param2);
            return;
        }//end

        public static void  disable ()
        {
            m_enabled = false;
            return;
        }//end

        public static void  enable ()
        {
            m_enabled = true;
            return;
        }//end

    }



