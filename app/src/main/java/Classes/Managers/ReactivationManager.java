package Classes.Managers;

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

//import flash.utils.*;
    public class ReactivationManager
    {
public static Dictionary m_allowedActionCache =new Dictionary ();
public static int m_isPlayerReactive =-1;

        public  ReactivationManager ()
        {
            return;
        }//end

        public static String  getLapsedGroup (int param1 )
        {
            XML _loc_4 =null ;
            int _loc_5 =0;
            String _loc_2 =null ;
            _loc_3 =Global.gameSettings().getReactivateGroups ();
            if (_loc_3)
            {
                for(int i0 = 0; i0 < _loc_3.group.size(); i0++)
                {
                		_loc_4 = _loc_3.group.get(i0);

                    _loc_5 = int(_loc_4.@age);
                    if (param1 >= _loc_5)
                    {
                        _loc_2 = _loc_4.@name;
                    }
                }
            }
            return _loc_2;
        }//end

        public static String  getPlayerLapsedGroup ()
        {
            _loc_1 = Global.player.lastLapsedOfflineTime;
            _loc_2 = ReactivationManager.getLapsedGroup(_loc_1);
            return _loc_2;
        }//end

        public static int  getPlayerLapsedPeriod ()
        {
            _loc_1 =Global.player.lastLapsedOfflineTime ;
            return _loc_1;
        }//end

        public static boolean  isPlayerFirstTime ()
        {
            return Global.player.isNewPlayer;
        }//end

        public static boolean  isPlayerReactive ()
        {
            XMLList _loc_2 =null ;
            int _loc_3 =0;
            XML _loc_4 =null ;
            boolean _loc_1 =false ;
            if (m_isPlayerReactive != -1)
            {
                _loc_1 = Boolean(m_isPlayerReactive);
            }
            else
            {
                _loc_2 = Global.gameSettings().getReactivateAllowedActions();
                if (_loc_2)
                {
                    _loc_3 = Global.player.lastLapsedOfflineTime;
                    for(int i0 = 0; i0 < _loc_2.action.size(); i0++)
                    {
                    		_loc_4 = _loc_2.action.get(i0);

                        _loc_1 = true;
                        if (_loc_4.hasOwnProperty("@age"))
                        {
                            _loc_1 = _loc_3 >= _loc_4.@age;
                        }
                        if (_loc_1)
                        {
                            break;
                        }
                    }
                    m_isPlayerReactive = int(_loc_1);
                }
            }
            return _loc_1;
        }//end

        public static boolean  isActionAllowed (String param1 )
        {
            XMLList _loc_3 =null ;
            int _loc_4 =0;
            XML _loc_5 =null ;
            boolean _loc_2 =false ;
            if (m_allowedActionCache.hasOwnProperty(param1))
            {
                _loc_2 = m_allowedActionCache.get(param1);
            }
            else
            {
                _loc_3 = Global.gameSettings().getReactivateAllowedActions();
                if (_loc_3)
                {
                    _loc_4 = Global.player.lastLapsedOfflineTime;
                    for(int i0 = 0; i0 < _loc_3.action.size(); i0++)
                    {
                    		_loc_5 = _loc_3.action.get(i0);

                        if (_loc_5.@type == param1)
                        {
                            _loc_2 = true;
                            if (_loc_5.hasOwnProperty("@age"))
                            {
                                _loc_2 = _loc_4 >= _loc_5.@age;
                            }
                            if (_loc_2)
                            {
                                break;
                            }
                        }
                    }
                }
                m_allowedActionCache.put(param1,  _loc_2);
            }
            return _loc_2;
        }//end

    }



