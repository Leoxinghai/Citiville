package root;

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

import Engine.Classes.*;
import Engine.Init.*;
import Engine.Interfaces.*;
import Engine.Managers.*;
import com.adobe.utils.*;
//import flash.debugger.*;
//import flash.display.*;
//import flash.external.*;
//import flash.media.*;
//import flash.utils.*;

import com.xinghai.Debug;

import java.util.Timer;

public class GlobalEngine
    {
        public static Stage stage =null ;
        public static Viewport viewport ;
        public static SocialNetwork socialNetwork ;
        public static Function onFeedCloseCallback =null ;
        public static Timer feedCallbackTimer =null ;
        public static ZEngineOptions engineOptions =null ;
        public static InitializationManager initializationManager ;
        public static ZaspManager zaspManager ;
        public static int lastInputTick =0;
public static int m_internalServerTime =0;
public static int m_serverTime ;
public static int m_syncTime =0;
public static Object m_flashVars =null ;
public static double m_startingWaitTime =0;
public static Object m_zyParams =null ;
        private static ILocalizer m_localizer =null ;
        private static IFontMapper m_fontMapper =new DefaultFontMapper ();
        public static int currentTime ;
        private static Object stc_stackCache ={};
        public static  int LEVEL_ERROR =1;
        public static  int LEVEL_WARNING =2;
        public static  int LEVEL_INFO =3;
        public static  int LEVEL_ALL =3;
        public static  int LEVEL_NONE =-1;

        public  GlobalEngine ()
        {
            return;
        }//end

        public static void  updateTimers ()
        {
            m_serverTime = getTimer();
            currentTime = getTimer();
            return;
        }//end

        public static void  setInternalServerTime (double param1 )
        {
            m_internalServerTime = param1;
            m_syncTime = getTimer();
            return;
        }//end

        public static double  serverTime ()
        {
            return m_serverTime;
        }//end

        public static double  startingWaitTime ()
        {
            return m_startingWaitTime;
        }//end

        public static void  setStartingWaitTime ()
        {
            m_startingWaitTime = getTimer() / 1000;
            return;
        }//end

        public static boolean  isServerTimeValid ()
        {
            return m_internalServerTime != 0;
        }//end

        public static void  localizer (ILocalizer param1 )
        {
            m_localizer = param1;
            return;
        }//end

        public static ILocalizer  localizer ()
        {
            return m_localizer;
        }//end

        public static void  fontMapper (IFontMapper param1 )
        {
            m_fontMapper = param1;
            return;
        }//end

        public static IFontMapper  fontMapper ()
        {
            return m_fontMapper;
        }//end

        public static String  quickLocalize (String param1 ,String param2 ,Array param3 =null ,boolean param4 =true )
        {
            String[] _loc_6 =null ;
            if (param2 == null)
            {
                _loc_6 = param1.split(":");
                if (_loc_6.length == 2)
                {
                    param1 = _loc_6.get(0);
                    param2 = _loc_6.get(1);
                }
            }
            LocalizationManager _loc_5 = quickLocalize(param1 ,param2 ,param3 );
            if (param4)
            {
                if (_loc_5 == null)
                {
                    _loc_5 = "";
                }
                if (_loc_5 == "")
                {
                    _loc_5 = param1 + ".props: " + param2 + "=??";
                }
                if (_loc_5 == "null")
                {
                    _loc_5 = "";
                }
            }
            return _loc_5;
        }//end

        public static int getTimer ()
        {
            return System.currentTimeMillis() - m_syncTime + m_internalServerTime * 1000;
        }//end

        public static double  getCurrentTime ()
        {
            return  System.currentTimeMillis() + m_syncTime + m_internalServerTime * 1000;
        }//end

        public static double  getTimerInSeconds ()
        {
            return Math.floor((getTimer() - m_syncTime) / 1000) + m_serverTime;
        }//end

        public static Object  zyParams ()
        {
            Object _loc_1 =null ;
            String _loc_2 =null ;
            if (GlobalEngine.m_zyParams == null)
            {
                _loc_1 = {};
                for(int i0 = 0; i0 < m_flashVars.size(); i0++)
                {
                		_loc_2 = m_flashVars.get(i0);

                    if (_loc_2.substr(0, 2) == "zy")
                    {
                        _loc_1.put(_loc_2,  m_flashVars.get(_loc_2));
                    }
                }
                GlobalEngine.m_zyParams = _loc_1;
            }
            return GlobalEngine.m_zyParams;
        }//end

        public static void  zyParams (Object param1 )
        {
            GlobalEngine.m_zyParams = param1;
            TransactionManager.additionalSignedParams = param1;
            return;
        }//end

        public static Object  getFlashVars ()
        {
            return m_flashVars;
        }//end

        public static void  parseFlashVars (Object param1 )
        {
            m_flashVars = param1;
            if (param1.noSound == 1)
            {
                SoundMixer.soundTransform = new SoundTransform(0);
            }
            ExternalInterface.call("zaspActivity","parseFlashVars"+param1.app_url);
            if (param1.app_url != null)
            {
                Config.BASE_PATH = param1.app_url;



                Config.SERVICES_GATEWAY_PATH = param1.app_url + "/editgrid/games/citiville/gateway.jsp";
                Config.RECORD_STATS_PATH = param1.app_url + "/editgrid/games/citiville/record_stats.jsp";
                Config.WEIBO_PATH = param1.app_url + "/editgrid/games/citiville/maingame.jsp";
                Config.SERVICES_CITYCHANGE_PATH = param1.app_url + "/editgrid/games/citiville/citychange.jsp";
/*
                Config.SERVICES_GATEWAY_PATH = param1.app_url + "/editgrid/gateway.jsp";
                Config.RECORD_STATS_PATH = param1.app_url + "/editgrid/record_stats.jsp";
                Config.WEIBO_PATH = param1.app_url + "/editgrid/maingame.jsp";
                Config.SERVICES_CITYCHANGE_PATH = param1.app_url + "/editgrid/citychange.jsp";
*/
            }
            if (param1.userid != null)
            {
            	Config.userid = param1.userid;
            }

            if (param1.sn_app_url != null)
            {
                Config.SN_APP_URL = param1.sn_app_url;
            }
            if (param1.additional_asset_urls != null || param1.asset_url != null)
            {
                setAssetUrls(param1.asset_url, param1.additional_asset_urls);
            }
            if (param1.serverTime != null)
            {
                GlobalEngine.setInternalServerTime(param1.serverTime);
            }
            if (param1.pollTimeSeconds != null)
            {
                TransactionManager.amfMaxWait = param1.pollTimeSeconds * 1000;
            }
            if (param1.debugMode)
            {
                Config.DEBUG_MODE = true;
            }
            return;
        }//end

        public static void  setFlashVar (String param1 ,Object param2 )
        {
            m_flashVars.put(param1,  param2);
            return;
        }//end

        public static Object  getFlashVar (String param1 )
        {
            return m_flashVars.get(param1);
        }//end

        public static void  error (String param1 ,String param2 )
        {
            logByLevel(LEVEL_ERROR, param1, param2);
            return;
        }//end

        public static void  warning (String param1 ,String param2 )
        {
            logByLevel(LEVEL_WARNING, param1, param2);
            return;
        }//end

        public static void  log (String param1 ,String param2 )
        {
            logByLevel(LEVEL_INFO, param1, param2);
            return;
        }//end

        public static void  info (String param1 ,String param2 )
        {
            logByLevel(LEVEL_INFO, param1, param2);
            return;
        }//end

        public static void  msg (String... args )
        {
            logByLevel(LEVEL_INFO, null, args.join(" "));
            return;
        }//end

        private static void  logByLevel (int param1 ,String param2 ,String param3 )
        {
            Object _loc_4 =null ;
            int _loc_5 =0;
            String _loc_6 =null ;
            if (Config.verboseLogging)
            {
                if (param2 == null || param1 <= LEVEL_WARNING)
                {
                    _loc_4 = computeSectionFromStack();
                }
                if (param2 == null)
                {
                    param2 = _loc_4.sectionName;
                }
                _loc_5 = Config.TRACE_DEFAULT_LEVEL;
                if (Config.TRACE_SECTIONS != null && param1 >= LEVEL_INFO)
                {
                    if (Config.TRACE_SECTIONS.hasOwnProperty(param2))
                    {
                        _loc_5 = Config.TRACE_SECTIONS.get(param2);
                    }
                }
                if (param1 <= _loc_5)
                {
                    _loc_6 = "" + dateToShortTime(new Date()) + " " + param2 + ": ";
                    if (LEVEL_ERROR == param1)
                    {
                        _loc_6 = _loc_6 + ("**ERROR** " + param3 + "\r\n" + _loc_4.stackTrace);
                    }
                    else if (LEVEL_WARNING == param1)
                    {
                        _loc_6 = _loc_6 + ("**WARNING** " + param3);
                    }
                    else
                    {
                        _loc_6 = _loc_6 + param3;
                    }
                    trace(_loc_6);
                }
            }
            return;
        }//end

        private static Object  computeSectionFromStack ()
        {
            Object _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            int _loc_10 =0;
            RegExp _loc_11 =null ;
            Object _loc_12 =null ;
            String _loc_1 =null ;
            Object _loc_2 ={stackTrace rawLine "",""packageName ,"",className "","<unknown>","",0};
            Error _loc_3 =new Error ();
            int _loc_4 =5;
            _loc_1 = _loc_3.getStackTrace();
            if (_loc_1 != null && _loc_1 != "")
            {
                _loc_5 = stc_stackCache.get(_loc_1);
                if (_loc_5 == null)
                {
                    _loc_6 = _loc_1.split("\n");
                    if (_loc_6.length >= _loc_4)
                    {
                        _loc_2.stackTrace = _loc_6.slice((_loc_4 - 1)).join("\r\n");
                        _loc_7 = String(_loc_6.get((_loc_4 - 1)));
                        _loc_2.rawLine = StringUtil.ltrim(_loc_7);
                        _loc_8 = _loc_7.substring(4, _loc_7.indexOf("()", 4));
                        _loc_10 = _loc_8.indexOf("::");
                        if (_loc_10 != -1)
                        {
                            _loc_2.packageName = _loc_8.substr(0, _loc_10);
                            _loc_8 = _loc_8.substr(_loc_10 + 2);
                        }
                        _loc_11 = /\[(.*):(\d+)\]""\[(.*):(\d+)\]/i;
                        _loc_12 = _loc_11.exec(_loc_7);
                        if (_loc_12)
                        {
                            _loc_2.fileName = _loc_12.get(1);
                            _loc_2.lineNumber = _loc_12.get(2);
                        }
                        if (_loc_8.indexOf("$/") != -1)
                        {
                            _loc_9 = _loc_8.split("$/", 2);
                            _loc_2.className = _loc_9.get(0);
                            _loc_2.methodName = _loc_9.get(1);
                        }
                        else
                        {
                            _loc_10 = _loc_8.indexOf("/");
                            if (_loc_10 != -1)
                            {
                                _loc_9 = _loc_8.split("/", 2);
                                _loc_2.className = _loc_9.get(0);
                                _loc_2.methodName = _loc_9.get(1);
                            }
                            else
                            {
                                _loc_2.className = _loc_8;
                                _loc_2.methodName = "ctor";
                            }
                        }
                    }
                    if (_loc_2.className == "global")
                    {
                        _loc_2.sectionName = _loc_2.methodName;
                    }
                    else
                    {
                        _loc_2.sectionName = _loc_2.className;
                    }
                    stc_stackCache.put(_loc_1,  _loc_2);
                }
                else
                {
                    _loc_2 = _loc_5;
                }
            }
            return _loc_2;
        }//end

        public static String  dateToShortTime (Date param1 )
        {
            _loc_2 = param1.getHours ();
            _loc_3 = param1.getMinutes ();
            _loc_4 = param1.getSeconds ();
            String _loc_5 =new String ();
            if (_loc_2 < 10)
            {
                _loc_5 = _loc_5 + "0";
            }
            _loc_5 = _loc_5 + _loc_2;
            _loc_5 = _loc_5 + ":";
            if (_loc_3 < 10)
            {
                _loc_5 = _loc_5 + "0";
            }
            _loc_5 = _loc_5 + _loc_3;
            _loc_5 = _loc_5 + ":";
            if (_loc_4 < 10)
            {
                _loc_5 = _loc_5 + "0";
            }
            _loc_5 = _loc_5 + _loc_4;
            return _loc_5;
        }//end

        public static void  addTraceLevel (String param1 ,int param2 )
        {
            if (Config.TRACE_SECTIONS == null)
            {
                Config.TRACE_SECTIONS = {};
            }
            Config.TRACE_SECTIONS.put(param1,  param2);
            return;
        }//end

        public static String  getAssetUrl (String param1 )
        {
            int _loc_3 =0;
            String _loc_2 ="";

            if (Config.ASSET_PATHS && Config.ASSET_PATHS.length > 0)
            {
                _loc_3 = param1.length % Config.ASSET_PATHS.length;
                _loc_2 = Config.ASSET_PATHS.get(_loc_3) + param1;
            }
            else
            {
                _loc_2 = param1;
                GlobalEngine.log("Config", "WARNING: no asset path specified!");
            }
            return _loc_2;
        }//end

        public static void  setAssetUrls (String param1 ,String param2 )
        {
            if (param2 && param2.length > 1)
            {
                Config.ASSET_PATHS = param2.split(",");
            }
            if (param1 && param1.length > 1)
            {
                Config.ASSET_PATHS.push(param1);
            }
            return;
        }//end

        public static void  onGrantXP (int param1 )
        {
            delta = param1;
            try
            {
                if (ExternalInterface.available && delta > 0)
                {
                    ExternalInterface.call("onGrantXP", delta);
                }
            }
            catch (e:Error)
            {
                GlobalEngine.log("zPoints", "grantXP callback failed");
            }
            return;
        }//end

        public static void  assert (boolean param1 ,String param2 )
        {
            if (!param1)
            {
                error("Assert", "ASSERTION FAILED - " + param2);
                enterDebugger();
            }
            return;
        }//end

    }



