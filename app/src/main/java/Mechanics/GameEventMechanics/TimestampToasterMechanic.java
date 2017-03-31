package Mechanics.GameEventMechanics;

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

    public class TimestampToasterMechanic extends ToasterMechanic
    {

        public  TimestampToasterMechanic ()
        {
            return;
        }//end

         protected boolean  doesDataStateMatch ()
        {
            double _loc_3 =0;
            double _loc_4 =0;
            boolean _loc_1 =false ;
            _loc_2 = m_config.params.get( "dataToCheck") ? (m_config.params.get("dataToCheck")) : ("");
            if (_loc_2 != "")
            {
                _loc_3 = m_owner.getDataForMechanic(_loc_2);
                _loc_4 = GlobalEngine.getTimer() / 1000;
                if (Math.abs(_loc_3 - _loc_4) < 10)
                {
                    return true;
                }
            }
            return _loc_1;
        }//end

    }



