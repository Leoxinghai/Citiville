package org.aswing.flyfish.css;

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
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.css.property.*;
import org.aswing.flyfish.event.*;

    public class LazyLoadRequest extends EventDispatcher
    {
        private String file ;
        private String linkage ;
        private Function valueCreator ;
        private Component target ;
        private ValueInjector injector ;
        private Array params ;
        private boolean finished ;

        public  LazyLoadRequest (String param1 ,String param2 ,Function param3 ,Array param4 )
        {
            this.file = param1;
            this.linkage = param2;
            this.valueCreator = param3;
            this.params = param4;
            this.finished = false;
            return;
        }//end

        public boolean  isImage ()
        {
            return this.linkage == null;
        }//end

        public void  setTarget (Component param1 ,ValueInjector param2 )
        {
            this.target = param1;
            this.injector = param2;
            return;
        }//end

        public Component  getTarget ()
        {
            return this.target;
        }//end

        public void  startLoad (ILazyLoadManager param1 )
        {
            this.finished = false;
            if (this.isImage())
            {
                param1.loadImage(this.file, this.__imageLoaded);
            }
            else
            {
                param1.loadSwf(this.file, this.__swfLoaded);
            }
            return;
        }//end

        protected void  doInject (LazyLoadData param1 )
        {
            this.injector.inject(this.target, this.valueCreator(param1));
            this.target.revalidate();
            return;
        }//end

        public String  getFile ()
        {
            return this.file;
        }//end

        public String  getLinkage ()
        {
            return this.linkage;
        }//end

        public boolean  isFinished ()
        {
            return this.finished;
        }//end

        private void  __imageLoaded (DisplayObject param1 )
        {
            this.finished = true;
            if (param1 == null)
            {
                dispatchEvent(new LazyLoadEvent(LazyLoadEvent.LOAD_ERROR, this));
                return;
            }
            _loc_2 = new LazyLoadData ();
            _loc_2.content = param1;
            _loc_2.params = this.params;
            this.doInject(_loc_2);
            dispatchEvent(new LazyLoadEvent(LazyLoadEvent.LOAD_COMPLETE, this));
            return;
        }//end

        private void  __swfLoaded (ApplicationDomain param1 )
        {
            this.finished = true;
            if (param1 == null)
            {
                dispatchEvent(new LazyLoadEvent(LazyLoadEvent.LOAD_ERROR, this));
                return;
            }
            _loc_2 = new LazyLoadData ();
            _loc_2.domain = param1;
            _loc_2.linkage = this.linkage;
            _loc_2.params = this.params;
            this.doInject(_loc_2);
            dispatchEvent(new LazyLoadEvent(LazyLoadEvent.LOAD_COMPLETE, this));
            return;
        }//end

        public static LazyLoadRequest  checkLazy (String param1 ,Function param2 ,Array param3 )
        {
            Array _loc_4 =null ;
            String _loc_5 =null ;
            LazyLoadRequest _loc_6 =null ;
            if (param1.indexOf("#") > 0)
            {
                _loc_4 = param1.split("#");
                _loc_5 = _loc_4.get(0);
                param1 = _loc_4.get(1);
                _loc_6 = new LazyLoadRequest(_loc_5, param1, param2, param3);
                return _loc_6;
            }
            return null;
        }//end

    }


