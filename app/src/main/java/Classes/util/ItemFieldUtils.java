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

    public class ItemFieldUtils
    {

        public  ItemFieldUtils ()
        {
            return;
        }//end

        public static int  getCostPricingByItemCount (String param1 ,String param2 ,int param3 ,Object param4 )
        {
            _loc_5 = param3;
            _loc_6 = param4.get("itemFieldMultiplier") ? (int(param4.get("itemFieldMultiplier"))) : (1);
            _loc_7 = param4["useFeatureData"] ? (Boolean(param4.get("useFeatureData") == "true")) : (false);
            int _loc_8 =0;

            //add by xinghai

            if (_loc_7)
            {
                if( Global.itemCountManager == null) return 100;
                _loc_8 = Global.itemCountManager.getItemCount(param1);
            }
            else
            {
                _loc_8 = Global.world.getObjectsByNames(.get(param1)).length;
            }
            _loc_5 = param3 + param3 * _loc_6 * _loc_8;
            return _loc_5;
        }//end

    }



