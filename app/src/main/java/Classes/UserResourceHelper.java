package Classes;

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
    public class UserResourceHelper
    {
        private static Dictionary s_map ;

        public  UserResourceHelper ()
        {
            return;
        }//end

        public static boolean  deductResource (UserResourceType param1 ,int param2 ,boolean param3 =false )
        {
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            initMap();
            _loc_4 = s_map.get(param1);
            if (.get(_loc_4) != null && (param3 || param1.getValue() >= param2))
            {
                ((Function).get(_loc_4)).call(null, -param2);
                _loc_5 = true;
            }
            return _loc_5;
        }//end

        private static void  initMap ()
        {
            if (!s_map)
            {
                s_map = new Dictionary();
                s_map.put(UserResourceType.get(UserResourceType.COIN),  "goldResource");
                s_map.put(UserResourceType.get(UserResourceType.CASH),  "cashResource");
                s_map.put(UserResourceType.get(UserResourceType.ENERGY),  "energyResource");
            }
            return;
        }//end

        private static void  goldResource (int param1 )
        {
            Global.player.gold = Global.player.gold + param1;
            return;
        }//end

        private static void  cashResource (int param1 )
        {
            Global.player.cash = Global.player.cash + param1;
            return;
        }//end

        private static void  energyResource (int param1 )
        {
            Global.player.updateEnergy(param1, null);
            return;
        }//end

    }



