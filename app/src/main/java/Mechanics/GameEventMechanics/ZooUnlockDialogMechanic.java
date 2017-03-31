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

import Classes.*;
import Mechanics.*;

    public class ZooUnlockDialogMechanic extends DialogGenerationMechanic
    {

        public  ZooUnlockDialogMechanic ()
        {
            return;
        }//end

         public boolean  canPopDialog ()
        {
            _loc_1 = (ZooEnclosure)m_owner
            boolean _loc_2 =false ;
            if (_loc_1)
            {
                _loc_2 = super.canPopDialog();
                if (_loc_2)
                {
                    if (m_config.params.get("skipAnimals"))
                    {
                        _loc_2 = _loc_1.readyToUnlockMinusAnimals();
                    }
                    else
                    {
                        _loc_2 = _loc_1.readyToUnlock();
                    }
                }
            }
            return _loc_2;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            _loc_3 =             !this.canPopDialog();
            _loc_4 = (ZooEnclosure)m_owner
            _loc_5 = super.executeOverrideForGameEvent(param1,param2);
            if (!_loc_3)
            {
                if (m_config.params.get("skipAnimals"))
                {
                    _loc_4.unlockNextEnclosure("readyToUnlockMinusAnimals");
                }
                else
                {
                    _loc_4.unlockNextEnclosure();
                }
            }
            return new MechanicActionResult(_loc_5.actionSuccess, _loc_3, _loc_5.sendTransaction, _loc_5.transactionParams);
        }//end

    }



