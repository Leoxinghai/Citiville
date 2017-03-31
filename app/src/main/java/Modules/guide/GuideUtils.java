package Modules.guide;

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
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.display.*;

    public class GuideUtils
    {
        public static  String FIRST_STEP ="NewUser";
        public static  String TUTORIAL_ASSETS ="assets/dialogs/TutorialAssets.swf";
        public static  String CITY_SAM_1 ="assets/dialogs/citysam_suit01b.png";
        public static  String CITY_SAM_2 ="assets/dialogs/citysam_suit01c.png";
        public static  String CITY_SAM_3 ="assets/dialogs/citysam_suit01d.png";
        public static  String CITY_SAM_4 ="assets/dialogs/citysam_thumbsup_02.png";
        public static  Array GUIDE_ASSETS_FIRST_STEP =.get(TUTORIAL_ASSETS ,CITY_SAM_1) ;
        public static  Array GUIDE_ASSETS_LATER_STEPS =[CITY_SAM_2 ,CITY_SAM_3 ,CITY_SAM_4 ,DelayedAssetLoader.MARKET_ASSETS ,"assets/NPC/Car_yehaul/car_haultruck_N.png","assets/NPC/Car_yehaul/car_haultruck_NNE.png","assets/NPC/Car_yehaul/car_haultruck_NE.png","assets/NPC/Car_yehaul/car_haultruck_NEE.png","assets/NPC/Car_yehaul/car_haultruck_E.png","assets/NPC/Car_yehaul/car_haultruck_SEE.png","assets/NPC/Car_yehaul/car_haultruck_SE.png","assets/NPC/Car_yehaul/car_haultruck_SSE.png","assets/NPC/Car_yehaul/car_haultruck_S.png","assets/NPC/Car_yehaul/car_haultruck_SSW.png","assets/NPC/Car_yehaul/car_haultruck_SW.png","assets/NPC/Car_yehaul/car_haultruck_SWW.png","assets/NPC/Car_yehaul/car_haultruck_W.png","assets/NPC/Car_yehaul/car_haultruck_NWW.png","assets/NPC/Car_yehaul/car_haultruck_NW.png","assets/NPC/Car_yehaul/car_haultruck_NNW.png","assets/doobers/star_doober.png","assets/doobers/star2_doober.png","assets/doobers/coin_doober.png","assets/doobers/coin2_doober.png","assets/doobers/bolt_doober.png","assets/doobers/cratefood_doober.png","assets/doobers/goods_doober.png","assets/doobers/goods2_doober.png","assets/doobers/heart_doober.png"] ;

        public  GuideUtils ()
        {
            return;
        }//end

        public static DisplayObject  scanForChild (DisplayObjectContainer param1 ,String param2 )
        {
            if (param1 == null || param2 == null || param2.length < 1)
            {
                return null;
            }
            DisplayObject _loc_3 =null ;
            _loc_3 = GuideUtils.findChild(param1, param2);
            if (_loc_3 != null)
            {
                return _loc_3;
            }
            int _loc_4 =0;
            while (_loc_4 < param1.numChildren)
            {

                _loc_3 = param1.getChildAt(_loc_4);
                if (_loc_3 != null)
                {
                    if (_loc_3.name == param2)
                    {
                        return _loc_3;
                    }
                    if (_loc_3.hasOwnProperty("numChildren"))
                    {
                        scanForChild((DisplayObjectContainer)_loc_3, param2);
                    }
                }
                _loc_4 = _loc_4 + 1;
            }
            return null;
        }//end

        public static DisplayObject  findChild (DisplayObjectContainer param1 ,String param2 )
        {
            if (param1 == null || param2 == null || param2.length < 1)
            {
                return null;
            }
            DisplayObject _loc_3 =null ;
            int _loc_4 =0;
            while (_loc_4 < param1.numChildren)
            {

                _loc_3 = param1.getChildAt(_loc_4);
                if (_loc_3 != null && _loc_3.name == param2)
                {
                    return _loc_3;
                }
                _loc_4 = _loc_4 + 1;
            }
            return null;
        }//end

        public static GameObject  getGameObjectByClassName (Class param1 )
        {
            _loc_2 =Global.world.getObjectsByClass(param1 );
            if (_loc_2.length > 0)
            {
                return _loc_2.get(0);
            }
            return null;
        }//end

        public static GameObject  getGameObjectByTypeName (String param1 )
        {
            _loc_2 =Global.world.getObjectsByNames(.get(param1) );
            return _loc_2.length ? (_loc_2.get(0)) : (null);
        }//end

        public static GameObject  getGameObjectInWorldByRootName (String param1 )
        {
            _loc_2 =Global.gameSettings().getOrderedUpgradeChainByRoot(param1 );
            _loc_3 =Global.world.getObjectsByNames(_loc_2 );
            return _loc_3.length ? (_loc_3.get(0)) : (null);
        }//end

        public static boolean  isUpgradeCountable (GameObject param1 )
        {
            if (param1 instanceof MapResource)
            {
                return ((MapResource)param1).canCountUpgradeActions();
            }
            return false;
        }//end

        public static GameObject  getActiveRollCallByTypeNames (Array param1 )
        {
            GameObject _loc_3 =null ;
            RollCallDataMechanic _loc_4 =null ;
            _loc_2 =Global.world.getObjectsByNames(param1 );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 && _loc_3 instanceof IMechanicUser)
                {
                    _loc_4 =(IMechanicUser, "rollCall", "all") as RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_3;
                    if (_loc_4 && _loc_4.isActiveObject())
                    {
                        return _loc_3;
                    }
                }
            }
            return null;
        }//end

    }



