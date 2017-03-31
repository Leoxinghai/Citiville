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

import Mechanics.*;
    public class TutorialMechanic extends AbstractCountableMechanic
    {

        public  TutorialMechanic ()
        {
            return;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =null ;
            String _loc_4 =null ;
            if (!m_config.params.get("guide"))
            {
                _loc_3 = new MechanicActionResult(false, true, false);
            }
            if (!shouldSkip)
            {
                _loc_4 = m_config.params.get("guide");
                Global.guide.notify(_loc_4);
                _loc_3 = super.executeOverrideForGameEvent(param1, param2);
            }
            else
            {
                _loc_3 = super.executeOverrideForGameEvent(param1, param2);
            }
            return _loc_3;
        }//end

    }



