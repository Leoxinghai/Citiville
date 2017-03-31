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

import Mechanics.GameMechanicInterfaces.*;

    public class NullStateDialogMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {

        public  NullStateDialogMechanic ()
        {
            return;
        }//end

         public boolean  canPopDialog ()
        {
            _loc_1 = m_config.params.get( "nullKey") ;
            _loc_2 = m_owner.getDataForMechanic(_loc_1);
            return _loc_2 == null;
        }//end

    }



