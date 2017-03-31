package Modules.realtime;

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

import Classes.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
//import flash.external.*;

    public class RealtimeManager
    {
        public static RealtimeObserver m_realtimeObserver =null ;

        public  RealtimeManager ()
        {
            return;
        }//end

        public static boolean  inExperiment ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REALTIME );
            if (_loc_1 == ExperimentDefinitions.REALTIME_ENABLED)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  inSendExperiment ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SEND_REALTIME );
            if (_loc_1 == ExperimentDefinitions.SEND_REALTIME_ENABLED)
            {
                return true;
            }
            return false;
        }//end

        public static void  onRTStatusChange (String param1 ,String param2 )
        {
            if (!inExperiment)
            {
                return;
            }
            _loc_3 = RealtimeMethods.getFriendByZid(param1);
            String _loc_4 =null ;
            if (_loc_3)
            {
                StatsManager.count("zoom", "show", "presence", param2);
                switch(param2)
                {
                    case "online":
                    {
                        _loc_3.m_online = true;
                        _loc_4 = _loc_3.m_name + " came online";
                        break;
                    }
                    case "offline":
                    {
                        _loc_3.m_online = false;
                        _loc_4 = _loc_3.m_name + " went offline";
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                if (Global.ui.m_friendBar.scrollingList)
                {
                    Global.ui.m_friendBar.scrollingList.updateFriend(_loc_3);
                    Global.ui.showTickerMessage(_loc_4);
                }
            }
            return;
        }//end

        public static void  callMethodOnOnlineUser (String param1 ,RealtimeMethod param2 )
        {
            zid = param1;
            method = param2;
            if (!inSendExperiment)
            {
                return;
            }
            try
            {
                ExternalInterface.call("sendChat", zid, method.jsonString);
            }
            catch (error:Error)
            {
            }
            return;
        }//end

        public static void  onRTChat (String param1 ,String param2 )
        {
            if (!LoadingManager.worldLoaded)
            {
                return;
            }
            RealtimeMethod _loc_3 =new RealtimeMethod ();
            _loc_3.createFromString(param2);
            _loc_3.zid = param1;
            _loc_3.execute();
            return;
        }//end

        public static void  callMethodOnAllOnlineUsers (RealtimeMethod param1 )
        {
            method = param1;
            if (!inSendExperiment)
            {
                return;
            }
            try
            {
                ExternalInterface.call("broadcastChat", method.jsonString);
            }
            catch (error:Error)
            {
            }
            return;
        }//end

    }



