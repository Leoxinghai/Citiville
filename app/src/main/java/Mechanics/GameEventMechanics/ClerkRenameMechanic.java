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

    public class ClerkRenameMechanic extends DialogGenerationMechanic implements IToolTipModifier
    {

        public  ClerkRenameMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            if (!canPopDialog())
            {
                return false;
            }
            return param1 == "GMPlay";
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Dialogs","RenameToolTip");
            return _loc_1;
        }//end

    }



