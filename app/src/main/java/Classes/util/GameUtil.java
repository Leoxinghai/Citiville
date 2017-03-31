package Classes.util;

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

import Engine.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;

import com.adobe.crypto.*;
//import flash.external.*;
//import flash.system.*;

    public class GameUtil extends Utilities
    {
        private static  String SERVER_UID_PREFIX ="i";
public static double seed =0;

        public  GameUtil ()
        {
            return;
        }//end

        public static double  synchronizedDateToNumber (String param1 )
        {
            _loc_2 =Global.gameSettings().getInt("globalServerUTCOffsetHours")*3600*1000;
            return Utilities.dateToNumber(param1, true) + _loc_2;
        }//end

        public static String  formatMinutesSeconds (double param1 ,boolean param2 =false )
        {
            _loc_3 = (Int)(param1 % 60);
            _loc_4 = int((param1-_loc_3)/60);
            _loc_5 = int(_loc_4% 60);
            _loc_6 = int((_loc_4-_loc_5)/60);
            _loc_7 = int((_loc_4-_loc_5)/60)>0|| param2 ? ((_loc_6 >= 10 ? ("") : ("0")) + _loc_6 + ":") : ("");
            _loc_7 = (int((_loc_4 - _loc_5) / 60) > 0 || param2 ? ((_loc_6 >= 10 ? ("") : ("0")) + _loc_6 + ":") : ("")) + ((_loc_5 >= 10 ? ("") : ("0")) + _loc_5 + ":");
            _loc_7 = _loc_7 + ((_loc_3 >= 10 ? ("") : ("0")) + _loc_3);
            return _loc_7;
        }//end

        public static void  redirectHomeVersionMismatch (GenericPopupEvent event =null )
        {
            if (Utilities.isFullScreen())
            {
                Utilities.setFullScreen(false);
            }
            reloadApp(true, "enhance");
            return;
        }//end

        public static void  redirectHome (GenericPopupEvent event =null )
        {
            refreshUserState(null, true);
            return;
        }//end

        public static void  refreshUserState (GenericPopupEvent event =null ,boolean param2 =false )
        {
            TRefreshUser _loc_3 =null ;
            if (TRefreshUser.canCall(param2))
            {
                _loc_3 = new TRefreshUser(GlobalEngine.socialNetwork.getLoggedInUser().name, param2);
                GameTransactionManager.addTransaction(_loc_3, true, true);
            }
            else
            {
                reloadApp(param2, "error");
            }
            return;
        }//end

        public static void  reloadApp (boolean param1 =false ,String param2)
        {
            if (param1 !=null)
            {
                StatsManager.count("refresh", "app", param2);
                StatsManager.sendStats(true);
            }
            ExternalInterface.call("ZYFrameManager.reloadApp");
            return;
        }//end

        public static String  formatServerUid (String param1 )
        {
            _loc_2 = param1;
            if (param1 && param1.length && param1.charAt(0) == SERVER_UID_PREFIX)
            {
                _loc_2 = param1.slice(1);
            }
            return _loc_2;
        }//end

        public static Array  vectorToArray (Object param1 )
        {
            Object _loc_4 =null ;
            _loc_2 = GameUtil.Vector<Object>(param1);
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                _loc_3.push(_loc_4);
            }
            return _loc_3;
        }//end

        public static void  srand (double param1 )
        {
            GameUtil.seed = param1;
            return;
        }//end

        public static int  rand (int param1 ,int param2 )
        {
            _loc_3 =Global.player.uid ;
            _loc_4 = _loc_3"FOR_GR8_JUSTICE"+"::"++"::"+GameUtil.seed ;
            _loc_5 = param2-param1 +1;
            _loc_6 = MD5"0x"+.hash(_loc_4).substring(0,8);
            _loc_7 = double(_loc_6);
            _loc_8 = double(_loc_6)% _loc_5;
            _loc_9 = double(_loc_6)% _loc_5 + param1;
            (GameUtil.seed + 1);
            return _loc_9;
        }//end

        public static String  replaceCharacterStrings (String param1 ,String param2 ,String param3 ="")
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_4 = param1;
            int _loc_5 =0;
            if (param2.length == 0)
            {
                return _loc_4;
            }
            if (param2.length > param1.length())
            {
                return _loc_4;
            }
            _loc_5 = _loc_4.indexOf(param2);
            while (_loc_5 > -1)
            {

                _loc_6 = _loc_4.substr(0, _loc_5);
                _loc_7 = _loc_4.substr((_loc_5 + 1), _loc_4.length - _loc_5);
                _loc_4 = _loc_6 + param3 + _loc_7;
                _loc_5 = _loc_4.indexOf(param2);
            }
            return _loc_4;
        }//end

        public static String  trimBadStatsCharacters (String param1 )
        {
            String _loc_5 =null ;
            _loc_2 =Global.gameSettings().getString("inavlidNameCharacters");
            _loc_3 = param1;
            _loc_3 = replaceCharacterStrings(_loc_3, " ", "_");
            _loc_3 = replaceCharacterStrings(_loc_3, "-", "_");
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                _loc_5 = _loc_2.substr(_loc_4, 1);
                _loc_3 = replaceCharacterStrings(_loc_3, _loc_5);
                _loc_4++;
            }
            _loc_3 = replaceCharacterStrings(_loc_3, "\'");
            _loc_3 = replaceCharacterStrings(_loc_3, "!");
            _loc_3 = replaceCharacterStrings(_loc_3, ",");
            return _loc_3.toLowerCase();
        }//end

        public static Array  logMachineInfo ()
        {
            _loc_1 = Capabilities.version;
            _loc_2 = Capabilities.language;
            _loc_3 = Capabilities.os;
            _loc_4 = Capabilities.screenResolutionX+" x "+Capabilities.screenResolutionY;
            _loc_5 = System(.totalMemory/(1024*1024)).toFixed(3)+" MB ";
            Array _loc_6 =new Array(_loc_1 ,_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 );
            return new Array(_loc_1, _loc_2, _loc_3, _loc_4, _loc_5);
        }//end

        public static int  countObjectLength (Object param1 )
        {
            _loc_3 = null;
            int _loc_2 =0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_2++;
            }
            return _loc_2;
        }//end

    }



