package Modules.bandits;

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
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.geom.*;

    public class PreyUtil
    {

        public  PreyUtil ()
        {
            return;
        }//end

        public static Municipal  getHubStationClosestToCenter (String param1 )
        {
            Municipal _loc_5 =null ;
            Point _loc_6 =null ;
            double _loc_7 =0;
            _loc_2 = getHubsByGroup(param1);
            if (_loc_2.length == 0)
            {
                return null;
            }
            Point _loc_3 =new Point(Global.ui.screenWidth /2,Global.ui.screenHeight /2);
            Array _loc_4 =new Array();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_5 = _loc_2.get(i0);

                _loc_6 = IsoMath.tilePosToPixelPos(_loc_5.positionX, _loc_5.positionY);
                _loc_6 = IsoMath.viewportToStage(_loc_6);
                _loc_7 = _loc_6.subtract(_loc_3).length;
                _loc_4.push({distance:_loc_7, station:_loc_5});
            }
            _loc_4.sortOn("distance", Array.NUMERIC);
            return _loc_4.get(0).station;
        }//end

        public static void  scrollToCenterHub (String param1 )
        {
            _loc_2 = (WorldObject)getHubStationClosestToCenter(param1)
            Global.world.centerOnObject(_loc_2);
            return;
        }//end

        public static Array  getHubsByGroup (String param1 )
        {
            _loc_2 =Global.gameSettings().getHubNames(param1 );
            return Global.world.getObjectsByNames(_loc_2);
        }//end

        public static Array  getHubsByLevel (String param1 ,int param2 )
        {
            WorldObject _loc_5 =null ;
            int _loc_6 =0;
            Array _loc_3 =new Array();
            _loc_4 = getHubsByGroup(param1);
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = Global.gameSettings().getHubLevel(param1, ((Municipal)_loc_5).getItem().name);
                if (_loc_6 == param2)
                {
                    _loc_3.push(_loc_5);
                }
            }
            return _loc_3;
        }//end

        public static int  getHubLevel (String param1 )
        {
            Municipal _loc_4 =null ;
            int _loc_5 =0;
            _loc_2 = getHubsByGroup(param1);
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_5 = Global.gameSettings().getHubLevel(param1, _loc_4.getItem().name);
                if (_loc_5 > _loc_3)
                {
                    _loc_3 = _loc_5;
                }
            }
            return _loc_3;
        }//end

        public static MapResource  findClosestHubOfLevel (String param1 ,Vector3 param2 ,int param3 )
        {
            MapResource _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            if (!param2)
            {
                return null;
            }
            _loc_4 = getHubsByGroup(param1);
            MapResource _loc_5 =null ;
            double _loc_6 =-1;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_7 = _loc_4.get(i0);

                if (Global.gameSettings().getHubLevel(param1, _loc_7.getItem().name) < param3)
                {
                    continue;
                }
                _loc_8 = _loc_7.positionX - param2.x;
                _loc_9 = _loc_7.positionY - param2.y;
                _loc_10 = _loc_8 * _loc_8 + _loc_9 * _loc_9;
                if (!_loc_5 || _loc_10 < _loc_6)
                {
                    _loc_5 = _loc_7;
                    _loc_6 = _loc_10;
                }
            }
            return _loc_5;
        }//end

        public static int  getHubInstanceLevel (String param1 ,ItemInstance param2 )
        {
            return Global.gameSettings().getHubLevel(param1, param2.getItem().name);
        }//end

        public static boolean  isHub (String param1 ,WorldObject param2 )
        {
            Item _loc_4 =null ;
            _loc_3 =Global.gameSettings().getHubNames(param1 );
            if (param2 instanceof MapResource)
            {
                _loc_4 = ((MapResource)param2).getItem();
                if (_loc_4 != null && _loc_4.type == "municipal" && _loc_4.behavior == "hub")
                {
                    return _loc_3.indexOf(_loc_4.name) >= 0;
                }
            }
            return false;
        }//end

        public static void  refreshAllHubAppearances ()
        {
            String _loc_2 =null ;
            _loc_1 =Global.gameSettings().getAllHubGroupIds ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                refreshHubAppearance(_loc_2);
            }
            return;
        }//end

        public static void  refreshHubAppearance (String param1 )
        {
            Municipal _loc_3 =null ;
            _loc_2 = getHubsByGroup(param1);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_3.updateStagePickEffect();
            }
            return;
        }//end

        public static void  logDialogStats (String param1 ,String param2 ,String param3 ,PreyData param4 =null ,int param5 =1)
        {
            _loc_6 = param4==null? (null) : (String(param4.id));
            StatsManager.sample(100, StatsCounterType.DIALOG, "active_building", param2, param1, param3, _loc_6, param5);
            return;
        }//end

        public static void  logGameActionStats (String param1 ,String param2 ,String param3 ,PreyData param4 =null )
        {
            _loc_5 = param4==null? (null) : (String(param4.id));
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, param1, param2, param3, _loc_5);
            return;
        }//end

    }



