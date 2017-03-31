package Modules.stats.trackers;

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
import Classes.util.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.external.*;
//import flash.net.*;
//import flash.utils.*;

    public class StartupSessionTracker
    {

        public  StartupSessionTracker ()
        {
            return;
        }//end

        private static int  perfSampleRate ()
        {
            int _loc_1 =10;
            if (GlobalEngine.getFlashVar("perfSampleRate") != null)
            {
                _loc_1 = parseInt((String)GlobalEngine.getFlashVar("perfSampleRate"));
            }
            return _loc_1;
        }//end

        private static int  streamingLoadTimeThreshold ()
        {
            return RuntimeVariableManager.getInt("LOADTIME_THRESHOLD", 70000);
        }//end

        public static void  perf (String param1 )
        {
            StatsManager.perfSample(perfSampleRate, "timing", param1);
            StatsManager.sendStats(true);
            if (GlobalEngine.getFlashVar("profileType") != null)
            {
                switch(param1)
                {
                    case StatsKingdomType.LOADAPP:
                    {
                        if (GlobalEngine.getFlashVar("profileType") != ProfilerConstants.PROFILE_USER)
                        {
                            ZProfiler.startProfile();
                        }
                        break;
                    }
                    case StatsKingdomType.INTERACTIVE:
                    {
                        if (GlobalEngine.getFlashVar("profileType") == ProfilerConstants.PROFILE_T5)
                        {
                            setTimeout(ZProfiler.stopProfile, 0);
                        }
                        break;
                    }
                    case StatsKingdomType.LOADCOMPLETE:
                    {
                        if (GlobalEngine.getFlashVar("profileType") == ProfilerConstants.PROFILE_T6)
                        {
                            setTimeout(ZProfiler.stopProfile, 0);
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public static void  interactive ()
        {
            URLRequest _loc_2 =null ;
            Object _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            URLLoader _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            if (GlobalEngine.zaspManager)
            {
                GlobalEngine.zaspManager.zaspInteractive();
            }
            perf(StatsKingdomType.INTERACTIVE);
            if (!Config.DEBUG_MODE)
            {
                _loc_2 = new URLRequest(Config.BASE_PATH + "v" + GlobalEngine.getFlashVar("flashRevision") + "/keynote.php");
                _loc_3 = TransactionManager.additionalSignedParams;
                _loc_4 = "";
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_5 = _loc_3.get(i0);

                    _loc_4 = _loc_4 + (_loc_5 + "=" + _loc_3.get(_loc_5) + "&");
                }
                _loc_2.data = _loc_4;
                _loc_6 = new URLLoader(_loc_2);
            }
            GameTransactionManager.addTransaction(new TOnInteractive(), true);
            _loc_1 = getTimer();
            if (_loc_1 < streamingLoadTimeThreshold)
            {
                StatsManager.perfSample(perfSampleRate, "timing", "lt_flash", "", "", "", "", _loc_1.toString());
                _loc_7 = GlobalEngine.initializationManager.m_startTime + GlobalEngine.initializationManager.m_gameSettingsLoadTime;
                _loc_8 = _loc_1 - _loc_7;
                StatsManager.perfSample(perfSampleRate, "timing", "lt_normalized", "", "", "", "", _loc_8.toString());
            }
            ExternalInterface.call("onInteractive");
            return;
        }//end

        public static void  initUser ()
        {
            _loc_1 = getTimer();
            if (_loc_1 < streamingLoadTimeThreshold)
            {
                StatsManager.perfSample(perfSampleRate, "timing", "lt_inituser", "", "", "", "", _loc_1.toString());
            }
            return;
        }//end

    }



