package Display.hud;

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
//import flash.display.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class HUDThemeManager
    {
        public static  String COIN_ASSET ="coinAsset";
        public static  String CASH_ASSET ="cashMeter";
        public static  String ENERGY_BAR ="energyMeter";
        public static  String GOODS_BAR ="goodsMeter";
        public static  String PREMIUM_GOODS_BAR ="premiumGoodsMeter";
        public static  String XP_BAR ="xpMeter";
        public static  String REPUTATION_BAR ="repMeter";
        public static  String NEIGHBOR_BAR_BG ="neighborBarBg";
        public static  String CITY_NAME_BG ="cityNameBg";
        public static  String HAPPY_ICON ="happyIcon";
        public static  String POP_GOOD ="popGood";
        public static  String POP_NEUTRAL ="popNeutral";
        public static  String POP_BAD ="popBad";

        public static  String KDBOMB_ASSET ="kdbombAsset";
        public static  String LRBOMB_ASSET ="lrbombAsset";

        public static  String CITYLEVEL_ASSET ="cityLevelAsset";

        private static DelayedAssetLoader m_loader ;
        private static DisplayObject m_themePack =null ;
        private static XML m_chosenTheme =Global.gameSettings().uiTheme ().hud.get(0) ;

        public  HUDThemeManager ()
        {
            return;
        }//end

        public static void  loadAssets (Function param1 )
        {
	    Debug.debug7("HUDThemeManager.loadAssets." + m_chosenTheme.@assetPack);
	    callback = param1;
            DelayedAssetLoader loader =new DelayedAssetLoader ();

            loader.get(m_chosenTheme.@assetPack, function (param11:DisplayObject, param2:String) : void
            {
                m_themePack = param11;
                callback();
                return;
            }//end
            );
            return;
        }//end

        public static DisplayObject  getAsset (String param1 )
        {
	    Debug.debug7("HUDThemeManager.getAsset."+param1);

            if (isReady)
            {
                return getFromThemePack(param1);
            }
            throw new Error("Could not get HUD asset " + param1 + ", since it has not yet been loaded.");
        }//end

        public static XML  getComponentConfig (String param1 )
        {
            return m_chosenTheme.get(param1).get(0);
        }//end

        public static IntPoint  getOffset (String param1 ,String param2)
        {
            _loc_3 = getComponentConfig(param1);
            _loc_4 = _loc_3.get("@xOffset"+param2) ;
            _loc_5 = _loc_3.get("@yOffset"+param2) ;
            return new IntPoint(_loc_4, _loc_5);
        }//end

        public static IntPoint  getOffsetForCityNameBg ()
        {
            _loc_1 = getOffset(CITY_NAME_BG);
            _loc_2 = Global.gameSettings().getPopulationsForDisplay();
            _loc_3 = _loc_2!= null ? (_loc_2.length()) : (0);
            if (_loc_2 && _loc_3)
            {
                _loc_1.y = _loc_1.y + -1 * _loc_3 * 12;
            }
            return _loc_1;
        }//end

        public static double  getColor (String param1 ,String param2 )
        {
            _loc_3 = getComponentConfig(param1);
            if (_loc_3.get("@" + param2) != "")
            {
                return parseInt(_loc_3.get("@" + param2), 16);
            }
            return NaN;
        }//end

        private static boolean  isReady ()
        {
            return m_themePack != null;
        }//end

        private static DisplayObject  getFromThemePack (String param1 )
        {
	    Debug.debug7("HUDThemeManager.getFromThemPack." + param1);
	    Class _loc_1 =m_themePack.get(param1) ;
	    _loc_2 = new_loc_1();
	    if(_loc_2 instanceof BitmapData) {
	        return  (DisplayObject)new Bitmap(_loc_2);
	    } else {
	    	return (DisplayObject)_loc_2;
	    }

            //return new (m_themePack.get(param1) as Class)();
        }//end

    }




