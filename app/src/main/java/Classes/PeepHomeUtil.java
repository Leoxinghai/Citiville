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

import Classes.doobers.*;
    public class PeepHomeUtil
    {

        public  PeepHomeUtil ()
        {
            return;
        }//end

        public static double  getRentPayout (String param1 )
        {
            double _loc_2 =0;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 == null)
            {
                return _loc_2;
            }
            _loc_4 = Global.player.GetDooberMinimums(_loc_3,Doober.DOOBER_COIN);
            return Global.player.GetDooberMinimums(_loc_3, Doober.DOOBER_COIN);
        }//end

        public static double  getRentBonus (String param1 )
        {
            double _loc_2 =0;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 == null)
            {
                return _loc_2;
            }
            Item _loc_4 =null ;
            if (_loc_3.hasRemodel() && _loc_3.isRemodelSkin())
            {
                _loc_4 = _loc_3.getRemodelBase();
            }
            _loc_5 = Global.player.GetDooberMinimums(_loc_3,Doober.DOOBER_COIN);
            double _loc_6 =0;
            double _loc_7 =0;
            if (_loc_4)
            {
                _loc_6 = Global.player.GetDooberMinimums(_loc_4, Doober.DOOBER_COIN);
                _loc_5 = Global.player.GetDooberMinimums(_loc_3, Doober.DOOBER_COIN);
                _loc_7 = Math.round(100 * (_loc_5 - _loc_6) / _loc_6);
            }
            return _loc_7;
        }//end

    }



