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
    public class UserResourceType
    {
        private String m_type ;
        private static Dictionary s_map ;
        public static  String CASH ="cash";
        public static  String COIN ="gold";
        public static  String ENERGY ="energy";

        public  UserResourceType (String param1 )
        {
            this.m_type = param1;
            return;
        }//end

        public int  getValue ()
        {
            return Global.player.get(this.m_type);
        }//end

        public String  toString ()
        {
            return this.m_type;
        }//end

        public static UserResourceType  (String param1 )
        {
            initMap();
            return s_map.get(param1);
        }//end

        private static void  initMap ()
        {
            if (!s_map)
            {
                s_map = new Dictionary();
                s_map.put(COIN,  new UserResourceType(COIN));
                s_map.put(CASH,  new UserResourceType(CASH));
                s_map.put(ENERGY,  new UserResourceType(ENERGY));
            }
            return;
        }//end

    }



