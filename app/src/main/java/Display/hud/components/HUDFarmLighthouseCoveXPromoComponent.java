package Display.hud.components;

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

import Engine.Managers.*;
//import flash.events.*;
import Classes.sim.*;
    
    public class HUDFarmLighthouseCoveXPromoComponent extends HUDXPromoComponent
    {

        public  HUDFarmLighthouseCoveXPromoComponent ()
        {
            return;
        }//end  

         protected boolean  lockedCheck ()
        {
            return super.lockedCheck() || Global.player.getSeenFlag("farm_lighthouse_cove_xpromo");
        }//end  

         protected void  trackMouseClick ()
        {
            StatsManager.count("hud", "xpromo", "icon_click", "63");
            return;
        }//end  

         protected void  onMouseClick (Event event )
        {
            super.onMouseClick(event);
            Global.player.setSeenFlag("farm_lighthouse_cove_xpromo");
            return;
        }//end  

    }


