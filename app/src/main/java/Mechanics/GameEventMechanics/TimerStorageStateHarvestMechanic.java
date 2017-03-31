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

    public class TimerStorageStateHarvestMechanic extends StorageStateHarvestMechanic
    {

        public  TimerStorageStateHarvestMechanic ()
        {
            return;
        }//end  

         public boolean  isHarvestReady ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            return super.isHarvestReady() && _loc_1 && _loc_1.hasOwnProperty("endTS") && GlobalEngine.getTimer() / 1000 >= m_owner.getDataForMechanic("endTS");
        }//end  

    }



