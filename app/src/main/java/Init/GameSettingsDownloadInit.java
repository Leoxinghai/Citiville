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
import com.xiyu.util.Event;

import Engine.*;
import Engine.Init.*;
import root.GlobalEngine;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class GameSettingsDownloadInit extends InitializationAction
    {
        private String m_settingsUrl ;
        private ByteArray m_gsArray =null ;
        private ByteArray m_rawData =null ;
        private String m_uncompressedData =null ;
        public static  String INIT_ID ="GameSettingsDownloadInit";

        public  GameSettingsDownloadInit (String param1 ,ByteArray param2 )
        {
            super(INIT_ID);
            this.m_settingsUrl = param1;
            this.m_gsArray = param2;
            GlobalEngine.zaspManager.trackTimingStart("GAME_SETTINGS_DOWNLOAD_INIT");
            return;
        }//end  

         public void  execute ()
        {
            URLLoader _loc_1 =null ;
            if (this.m_gsArray !== null)
            {
                this.onConfigXmlLoaded(null);
            }
            else
            {
                _loc_1 = new URLLoader(new URLRequest(this.m_settingsUrl));
                _loc_1.dataFormat = URLLoaderDataFormat.BINARY;
                _loc_1.addEventListener(Event.COMPLETE, this.onConfigXmlLoaded);
            }
            return;
        }//end  

        private void  onConfigXmlLoaded (Event event )
        {
            if (event != null)
            {
                if (this.m_settingsUrl.indexOf(".xml.z") > 0)
                {
                    this.m_rawData = event.target.data;
                }
                else
                {
                    this.m_uncompressedData = event.target.data;
                }
            }
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("GAME_SETTINGS_DOWNLOAD_INIT");
            return;
        }//end  

        public String  data ()
        {
            if (this.m_uncompressedData == null)
            {
                if (this.m_gsArray != null)
                {
                    this.m_uncompressedData = this.m_gsArray.toString();
                }
                else if (this.m_rawData != null)
                {
                    this.m_uncompressedData = Utilities.uncompress(this.m_rawData);
                }
                this.m_gsArray = null;
                this.m_rawData = null;
            }
            return this.m_uncompressedData;
        }//end  

        public void  data (String param1 )
        {
            this.m_uncompressedData = param1;
            return;
        }//end  

    }



