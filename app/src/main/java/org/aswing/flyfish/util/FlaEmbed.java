package org.aswing.flyfish.util;

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
//import flash.system.*;
//import flash.utils.*;
import org.aswing.flyfish.*;

    public class FlaEmbed extends Object
    {
        private Loader loader ;
        private EventDispatcher _eventDispatcher ;
        private boolean calledInit =false ;
        private Function initHandler ;
        private boolean inited =false ;
        private ApplicationDomain domain ;
        private int loadIndex ;
        private Array classes ;

        public  FlaEmbed ()
        {
            return;
        }//end

        public void  init (Array param1 ,Function param2 ,ApplicationDomain param3 =null )
        {
            if (this.loader && !this.inited)
            {
                throw new Error("FlaEmbed.init can\'t be called again before its previous initing completed");
            }
            this.inited = false;
            this.classes = param1;
            if (param3 == null)
            {
                param3 = new ApplicationDomain(ApplicationDomain.currentDomain);
            }
            this.domain = param3;
            this.initHandler = param2;
            if (this.loader)
            {
                this.loader.unload();
            }
            this.loader = new Loader();
            this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, this.handleLoaderInit);
            this.loadIndex = -1;
            this.loadNext();
            return;
        }//end

        public ApplicationDomain  getDomain ()
        {
            return this.domain;
        }//end

        private void  loadNext ()
        {
            MovieClip _loc_3 =null ;

            _loc_5 = this.loadIndex ++;

            if (this.loadIndex >= this.classes.length())
            {
                this.inited = true;
                this.loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, this.handleLoaderInit);
                this.initHandler();
                return;
            }
            _loc_1 = new LoaderContext(false ,this.domain );
            if (_loc_1.hasOwnProperty("allowLoadBytesCodeExecution"))
            {
                _loc_1.put("allowLoadBytesCodeExecution",  true);
            }
            _loc_2 = this.classes.get(this.loadIndex) ;
            if (_loc_2 is ByteArray)
            {
                this.loader.loadBytes(_loc_2, _loc_1);
            }
            else
            {
                _loc_3 =(MovieClip) _loc_2;
                this.loader.loadBytes(new _loc_2, _loc_1);
            }
            return;
        }//end

        private void  handleLoaderInit (Event event )
        {
            this.loadNext();
            return;
        }//end

        public Object getInstance (String param1 )
        {
            _loc_2 = this.getDefinition(param1 );
            if (_loc_2 != null)
            {
                return new _loc_2;
            }
            return null;
        }//end

        public Class  getDefinition (String param1 )
        {
            className = param1;
            if (this.inited)
            {
                try
                {
                    return this.domain.getDefinition(className) as Class;
                }
                catch (e:Error)
                {
                    AGLog.warn("can not find definition: " + className);
                    return null;
                }
            }
            return null;
        }//end

        public Object reflect (String param1 )
        {
            return this.getInstance(param1);
        }//end

        public Object reflectBitmapData (String param1 ,int param2 ,int param3 )
        {
            _loc_4 = this.getDefinition(param1 );
            if (_loc_4 != null)
            {
                return new _loc_4(param2, param3);
            }
            return null;
        }//end

    }


