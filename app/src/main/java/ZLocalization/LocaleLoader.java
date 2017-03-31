package ZLocalization;

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

//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;
//import Engine.Utilities;

    public class LocaleLoader
    {
        protected Loader m_Loader ;
        protected URLLoader m_urlLoader ;
        protected Localizer m_localizer ;
        protected Function m_onComplete ;
        protected boolean m_isCompressed ;
        protected boolean m_loadFailed ;
        private static  String FONT_MAPPER_DEFINITION ="FontMapper";

        public  LocaleLoader (String param1 ,ByteArray param2 ,Function param3 ,boolean param4 =false ,boolean param5 =true )
        {
            if (param2)
            {
                if (param4)
                {
                    param2.uncompress();
                }
                this.m_localizer = new LocalizerXML(new XML(param2.toString()));
                if (param3 != null)
                {
                    param3();
                }
                return;
            }
            this.m_onComplete = param3;
            if (param5)
            {

                this.m_urlLoader = new URLLoader();
                this.m_isCompressed = false;//param4;
                if (param4)
                {
                    this.m_urlLoader.dataFormat = URLLoaderDataFormat.BINARY;
                }
                this.m_urlLoader.addEventListener(Event.COMPLETE, this.onLoadXMLComplete);
                this.m_urlLoader.addEventListener(IOErrorEvent.IO_ERROR, this.onLoadXMLComplete);
                this.m_urlLoader.load(new URLRequest(param1));
            }
            else
            {
                this.m_Loader = new Loader();
                this.m_isCompressed = param4;
                if (param4)
                {
                }
                this.m_Loader.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onLoadSWFComplete);
                this.m_Loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, this.onLoadXMLComplete);
                this.m_Loader.load(new URLRequest(param1));
            }
            return;
        }//end

        public Localizer  localizer ()
        {
            return this.m_localizer;
        }//end

        public boolean  loadFailed ()
        {
            return this.m_loadFailed;
        }//end

        protected void  onLoadXMLComplete (Event event )
        {
            this.m_urlLoader.removeEventListener(Event.COMPLETE, this.onLoadXMLComplete);
            this.m_urlLoader.removeEventListener(IOErrorEvent.IO_ERROR, this.onLoadXMLComplete);

			Object m_uncompressedData;
            if (!(event instanceof IOErrorEvent))
            {
                if (this.m_isCompressed)
                {
	            ByteArray m_compressedData =this.m_urlLoader.data ;
                    m_uncompressedData = Utilities.uncompress(m_compressedData);

                    //this.m_urlLoader.data.uncompress();
                } else {
                	m_uncompressedData = this.m_urlLoader.data;
                }

                this.m_localizer = new LocalizerXML(new XML(m_uncompressedData.toString()));
            }
            else
            {
                this.m_loadFailed = true;
            }
            if (this.m_onComplete != null)
            {
                this.m_onComplete();
            }
            this.m_onComplete = null;
            return;
        }//end

        protected void  onLoadSWFComplete (Event event )
        {
            LocalizerSWF _loc_2 =null ;
            ApplicationDomain _loc_3 =null ;
            Class _loc_4 =null ;
            this.m_Loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, this.onLoadSWFComplete);
            this.m_Loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, this.onLoadSWFComplete);
            if (!(event instanceof IOErrorEvent))
            {
                _loc_2 = new LocalizerSWF(event.target.content);
                this.m_localizer = _loc_2;
                _loc_3 = this.m_Loader.contentLoaderInfo.applicationDomain;
                if (_loc_3.hasDefinition(FONT_MAPPER_DEFINITION))
                {
                    _loc_4 =(Class) _loc_3.getDefinition(FONT_MAPPER_DEFINITION);
                    if (_loc_4)
                    {
                        this.localizer.m_fontMapper = new _loc_4;
                    }
                }
            }
            else
            {
                this.m_loadFailed = true;
            }
            if (this.m_onComplete != null)
            {
                this.m_onComplete();
            }
            this.m_onComplete = null;
            return;
        }//end

    }

