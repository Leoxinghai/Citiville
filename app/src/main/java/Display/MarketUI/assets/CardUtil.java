package Display.MarketUI.assets;

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
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;

    public class CardUtil
    {

        public  CardUtil ()
        {
            return;
        }//end

        public static double  scaleToFit (DisplayObject param1 ,DisplayObject param2 )
        {
            double _loc_3 =1;
            double _loc_4 =1;
            if (param1.width > param2.width)
            {
                _loc_3 = param2.width / param1.width;
            }
            if (param1.height > param2.height)
            {
                _loc_4 = param2.height / param1.height;
            }
            return Math.min(_loc_3, _loc_4);
        }//end


        public static void  loadIcon (Item param1 ,DisplayObject param2 )
        {
            Loader loader ;
            data = ;
            asset = param2;
            url = Global.gameSettings().getImageByName(data.name ,"icon");
            loader =LoadingManager .load (url ,void  (Event event )
            {
                _loc_2 = null;
                _loc_3 = null;
                if (loader && loader.content)
                {
                    _loc_2 =(MovieClip) asset.get("icon");
                    _loc_3 = CardUtil.scaleToFit(loader.content, _loc_2);
                    _loc_4 = _loc_3;
                    loader.content.scaleY = _loc_3;
                    loader.content.scaleX = _loc_4;
                    Utilities.removeAllChildren(_loc_2);
                    _loc_2.addChild(loader.content);
                }
                return;
            }//end
            );
            return;
        }//end

        public static void  populatePriceButton (Item param1 ,DisplayObject param2 )
        {
            _loc_3 = param2.get("priceButton") ;
            if (param1.cash > 0)
            {
                _loc_3.coinsSymbol.visible = false;
            }
            else
            {
                _loc_3.cashSymbol.visible = false;
            }
            _loc_4 = param1.cash >0? (param1.cash) : (param1.cost);
            if ((param1.cash > 0 ? (param1.cash) : (param1.cost)) > 0)
            {
                boolean _loc_5 =false ;
                _loc_3.mouseChildren = false;
                _loc_3.mouseEnabled = _loc_5;
                _loc_3.priceTextField.text = String(_loc_4);
                EmbeddedArt.updateFont(_loc_3.priceTextField);
            }
            else
            {
                _loc_3.visible = false;
            }
            return;
        }//end

        public static int  indexOfByCardName (Array param1 ,String param2 )
        {
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                if (param1.get(_loc_3).name == param2)
                {
                    return _loc_3;
                }
                _loc_3++;
            }
            return -1;
        }//end

        public static void  populateFields (Object param1 ,DisplayObject param2 )
        {
            String _loc_3 =null ;
            TextField _loc_4 =null ;
            for (_loc_3 in param1)
            {

                if (param2.hasOwnProperty(_loc_3))
                {
                    if (param2.get(_loc_3) instanceof TextField)
                    {
                        _loc_4 = param2.get(_loc_3);
                        _loc_4.text = param1.get(_loc_3);
                        _loc_4.mouseEnabled = false;
                        EmbeddedArt.updateFont(_loc_4);
                    }
                }
            }
            return;
        }//end

        public static String  localizeBuildTime (double param1 )
        {
            return ZLoc.t("Dialogs", "CatalogBuildTime", {time:localizeTimeLeft(param1)});
        }//end

        private static String  formatNumber (double param1 )
        {
            _loc_2 = Math.abs(param1 -Math.round(param1 ));
            if (_loc_2 < 0.05)
            {
                return Math.round(param1).toFixed(0);
            }
            return param1.toFixed(1);
        }//end

        public static String  formatTimeHours (double param1 )
        {
            double _loc_2 =0;
            _loc_3 = param1/(60*60);
            _loc_2 = _loc_3 - Math.floor(_loc_3);
            _loc_3 = Math.floor(_loc_3);
            _loc_4 = _loc_2*60;
            _loc_2 = _loc_2 * 60 - Math.floor(_loc_4);
            _loc_4 = Math.floor(_loc_4);
            _loc_5 = _loc_2*60;
            _loc_2 = _loc_2 * 60 - Math.floor(_loc_5);
            _loc_5 = Math.floor(_loc_5);
            _loc_6 = _loc_3<10? ("0" + _loc_3) : ("" + _loc_3);
            _loc_7 = _loc_4<10? ("0" + _loc_4) : ("" + _loc_4);
            _loc_8 = _loc_5<10? ("0" + _loc_5) : ("" + _loc_5);
            if (param1 < 0 || isNaN(param1))
            {
                return "00:00";
            }
            if (_loc_3 > 0)
            {
                return ZLoc.t("Dialogs", "TT_formatTimeHours", {hour:_loc_6, min:_loc_7, sec:_loc_8});
            }
            return _loc_7 + ":" + _loc_8;
        }//end

        public static String  formatTime (double param1 )
        {
            double _loc_2 =0;
            _loc_3 = param1/(60*60*24);
            _loc_2 = _loc_3 - Math.floor(_loc_3);
            _loc_3 = Math.floor(_loc_3);
            _loc_4 = _loc_2*24;
            _loc_2 = _loc_2 * 24 - Math.floor(_loc_4);
            _loc_4 = Math.floor(_loc_4);
            _loc_5 = _loc_2*60;
            _loc_2 = _loc_2 * 60 - Math.floor(_loc_5);
            _loc_5 = Math.floor(_loc_5);
            _loc_6 = param1% 60;
            _loc_7 = _loc_3<10? ("0" + _loc_3) : ("" + _loc_3);
            _loc_8 = _loc_4<10? ("0" + _loc_4) : ("" + _loc_4);
            _loc_9 = _loc_5<10? ("0" + _loc_5) : ("" + _loc_5);
            _loc_10 = _loc_6<10? ("0" + _loc_6) : ("" + _loc_6);
            if (param1 < 0 || isNaN(param1))
            {
                return "00:00";
            }
            if (_loc_4 > 0)
            {
                return ZLoc.t("Dialogs", "TT_formatTimeDayHours", {day:_loc_7, hour:_loc_8, min:_loc_9, sec:_loc_10});
            }
            return _loc_9 + ":" + _loc_10;
        }//end

        public static String  localizeTimeLeftLanguage (double param1 )
        {
            if (param1 > 1.083)
            {
                return ZLoc.t("Dialogs", "TT_CatalogTimeLeftDays", {time:formatNumber(param1)});
            }
            _loc_2 = (int)(param1 *24);
            if (_loc_2 >= 23 && _loc_2 <= 25)
            {
                return ZLoc.t("Dialogs", "TT_CatalogTimeLeftOneDay");
            }
            if (_loc_2 > 1)
            {
                return ZLoc.t("Dialogs", "TT_CatalogTimeLeftHours", {time:Math.round(_loc_2).toFixed(0)});
            }
            _loc_3 = (Int)(param1 *24*60);
            if (_loc_3 >= 58)
            {
                return ZLoc.t("Dialogs", "TT_CatalogTimeLeftOneHour");
            }
            return ZLoc.t("Dialogs", "TT_CatalogTimeLeftMinutes", {time:_loc_3});
        }//end

        public static String  localizeTimeLeft (double param1 )
        {
            if (param1 > 1.083)
            {
                return ZLoc.t("Dialogs", "CatalogTimeLeftDays", {time:formatNumber(param1)});
            }
            _loc_2 = (int)(param1 *24);
            if (_loc_2 >= 23 && _loc_2 <= 25)
            {
                return ZLoc.t("Dialogs", "CatalogTimeLeftOneDay");
            }
            if (_loc_2 > 1)
            {
                return ZLoc.t("Dialogs", "CatalogTimeLeftHours", {time:Math.round(_loc_2).toFixed(0)});
            }
            _loc_3 = (Int)(param1 *24*60);
            if (_loc_3 >= 58)
            {
                return ZLoc.t("Dialogs", "CatalogTimeLeftOneHour");
            }
            return ZLoc.t("Dialogs", "CatalogTimeLeftMinutes", {time:_loc_3});
        }//end

        public static String  localizeTimeRemainingFloored (double param1 )
        {
            if (param1 > 1.083)
            {
                return ZLoc.t("Dialogs", "RemainingTimeLeftDays", {time:Math.floor(param1).toFixed(0)});
            }
            _loc_2 = (int)(param1 *24);
            if (_loc_2 >= 23 && _loc_2 <= 25)
            {
                return ZLoc.t("Dialogs", "RemainingTimeLeftOneDay");
            }
            if (_loc_2 > 1)
            {
                return ZLoc.t("Dialogs", "RemainingTimeLeftHours", {time:Math.floor(_loc_2).toFixed(0)});
            }
            _loc_3 = (Int)(param1 *24*60);
            if (_loc_3 >= 58)
            {
                return ZLoc.t("Dialogs", "RemainingTimeLeftOneHour");
            }
            if (_loc_3 >= 5)
            {
                return ZLoc.t("Dialogs", "RemainingTimeLeftMinutes", {time:Math.floor(_loc_3).toFixed(0)});
            }
            return formatTimeHours(_loc_3 * 60);
        }//end

    }


