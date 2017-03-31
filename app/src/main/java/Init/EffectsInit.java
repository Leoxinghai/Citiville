package Init;

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
//import flash.events.*;
//import flash.net.*;

    public class EffectsInit extends InitializationAction
    {
        private String m_settingsUrl ;
        public static  String INIT_ID ="EffectsInit";

        public  EffectsInit (String param1 )
        {
            super(INIT_ID);
            addDependency(GameSettingsInit.INIT_ID);
            this.m_settingsUrl = param1;
            return;
        }//end

         public void  execute ()
        {
            URLLoader _loc_1 =new URLLoader(new URLRequest(this.m_settingsUrl ));
            _loc_1.addEventListener(Event.COMPLETE, this.loadComplete);
            return;
        }//end

        protected void  loadComplete (Event event )
        {
            Global.gameSettings().m_effectsConfigXml = new XML(event.target.data);
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



