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
import Engine.Interfaces.*;
import ZLocalization.*;
//import flash.events.*;
//import flash.utils.*;

    public class LocalizationInit extends InitializationAction
    {
        private String m_localeUrl ="";
        private XML m_xml ;
        private LocaleLoader localeLoader ;
        private ByteArray m_localeArray =null ;
        public static  String INIT_ID ="LocalizationInit";

        public  LocalizationInit (String param1 ,ByteArray param2 )
        {
            super(INIT_ID);
            this.m_localeUrl = param1;
            GlobalEngine.zaspManager.trackTimingStart("LOCALE_INIT");
            this.m_localeArray = param2;
            return;
        }//end  

         public void  execute ()
        {
            boolean _loc_1 =true ;
            if (this.m_localeUrl.indexOf(".xml.z") < 0)
            {
                _loc_1 = false;
            }
            if (this.m_localeArray)
            {
                this.localeLoader = new LocaleLoader(null, this.m_localeArray, null, false);
                this.onConfigXmlLoaded();
            }
            else
            {
                this.localeLoader = new LocaleLoader(this.m_localeUrl, null, this.onConfigXmlLoaded, _loc_1);
            }
            return;
        }//end  

        private void  onConfigXmlLoaded ()
        {
            Global.localizer = this.localeLoader.localizer;
            Global.localizer.setAsGlobalInstance();
            GlobalEngine.localizer = ILocalizer(Global.localizer);
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("LOCALE_INIT");
            return;
        }//end  

    }



