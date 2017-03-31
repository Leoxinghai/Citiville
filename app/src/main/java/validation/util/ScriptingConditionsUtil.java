package validation.util;

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

import validation.*;

    public class ScriptingConditionsUtil implements IValidationUtilClass
    {

        public  ScriptingConditionsUtil ()
        {
            return;
        }//end

        public Function  getValidationCallback (String param1 )
        {
            switch(param1)
            {
                case "inGeoCountry":
                {
                    return inGeoCountry;
                }
                case "playerRequiredLevel":
                {
                    return playerRequiredLevel;
                }
                default:
                {
                    break;
                }
            }
            throw new Error("unsupported validation: " + param1);
        }//end

        public static boolean  inGeoCountry (Object param1 )
        {
            _loc_2 = param1.countryCode ;
            _loc_3 = _loc_2.split(",");
            _loc_4 =Global.player.getGeoData("country_code");
            _loc_5 = _loc_3.indexOf(_loc_4 )!= -1;
            return _loc_3.indexOf(_loc_4) != -1;
        }//end

        public static boolean  playerRequiredLevel (Object param1 )
        {
            _loc_2 = param1.level ? (param1.level) : (1);
            _loc_3 =Global.player.level ;
            _loc_4 = _loc_3>=_loc_2 ;
            return _loc_3 >= _loc_2;
        }//end

    }



