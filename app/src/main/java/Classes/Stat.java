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

    public class Stat
    {
        private double m_value ;
        public static String OFFENSE_LEVEL ;
        public static String OFFENSE_XP ;
        public static String OFFENSETONEXTLEVEL ;
        public static String DEFENSE_LEVEL ;
        public static String DEFENSE_XP ;
        public static String DEFENSETONEXTLEVEL ;
        public static String ELORANK ;
        public static String FANS ;
        public static String STAMINA ;
        public static String WINS ;
        public static String LOSSES ;
        public static String DRAWS ;
        public static String NAME ;
        private static boolean m_constantsDefined ;

        public  Stat (double param1 )
        {
            if (m_constantsDefined == false)
            {
                this.defineConstants();
            }
            this.m_value = param1;
            return;
        }//end

        public double  value ()
        {
            return this.m_value;
        }//end

        public void  value (double param1 )
        {
            this.m_value = param1;
            return;
        }//end

        private void  defineConstants ()
        {
            _loc_1 =Global.gameSettings().getXML ();
            OFFENSE_LEVEL = _loc_1.stadium.@Stat_OFFENSE_LEVEL;
            OFFENSE_XP = _loc_1.stadium.@Stat_OFFENSE_XP;
            OFFENSETONEXTLEVEL = _loc_1.stadium.@Stat_OFFENSETONEXTLEVEL;
            DEFENSE_LEVEL = _loc_1.stadium.@Stat_DEFENSE_LEVEL;
            DEFENSE_XP = _loc_1.stadium.@Stat_DEFENSE_XP;
            DEFENSETONEXTLEVEL = _loc_1.stadium.@Stat_DEFENSETONEXTLEVEL;
            ELORANK = _loc_1.stadium.@Stat_ELORANK;
            FANS = _loc_1.stadium.@Stat_FANS;
            STAMINA = _loc_1.stadium.@Stat_STAMINA;
            WINS = _loc_1.stadium.@Stat_WINS;
            LOSSES = _loc_1.stadium.@Stat_LOSSES;
            DRAWS = _loc_1.stadium.@Stat_DRAWS;
            NAME = _loc_1.stadium.@Stat_NAME;
            return;
        }//end

    }


