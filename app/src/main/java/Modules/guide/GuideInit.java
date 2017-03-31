package Modules.guide;

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

import Engine.Init.*;
import Init.*;
//import flash.events.*;

    public class GuideInit extends InitializationAction
    {
        public static  String INIT_ID ="GuideInit";

        public  GuideInit ()
        {
            super(INIT_ID);
            addDependency(GlobalsInit.INIT_ID);
            addDependency(LocalizationInit.INIT_ID);
            addDependency(GameSettingsInit.INIT_ID);
            addDependency(UIInit.INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            Guide _loc_1 =new Guide ();
            Global.guide = _loc_1;
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



