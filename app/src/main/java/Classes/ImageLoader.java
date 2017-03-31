package Classes;

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
//import flash.display.*;
//import flash.events.*;

    public class ImageLoader
    {
        private String m_url =null ;
        private DisplayObject m_content ;
        private Array m_callbacks ;
        private boolean m_isLoading ;
        private Loader m_loader ;

        public  ImageLoader (String param1 )
        {
            this.m_url = param1;
            this.m_callbacks = new Array();
            return;
        }//end

        public void  getContent (Function param1 )
        {
            if (this.m_content)
            {
                this.clone(this.m_content, param1);
            }
            else
            {
                this.m_callbacks.push(param1);
                if (!this.m_isLoading)
                {
                    this.m_loader = LoadingManager.load(this.m_url, this.onLoad);
                    this.m_isLoading = true;
                }
            }
            return;
        }//end

        public void  onLoad (Event event )
        {
            int _loc_2 =0;
            this.m_loader =(Loader) event.target.loader;
            this.m_loader.removeEventListener(Event.COMPLETE, this.onLoad);
            if (this.m_loader.content)
            {
                this.m_content = this.m_loader.content;
            }
            if (this.m_content)
            {
                _loc_2 = 0;
                while (_loc_2 < this.m_callbacks.length())
                {

                    this.clone(this.m_content, (Function)this.m_callbacks.get(_loc_2));
                    _loc_2++;
                }
            }
            return;
        }//end

        public void  clone (DisplayObject param1 ,Function param2 )
        {
            LoaderWithInfo _loc_3 =null ;
            if (param1 instanceof MovieClip)
            {
                _loc_3 = new LoaderWithInfo();
                _loc_3.info.callbackFunction =(Function) param2;
                _loc_3.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onClone);
                _loc_3.loadBytes(this.m_content.loaderInfo.bytes);
            }
            else if (param1 instanceof Bitmap)
            {
                param2(new Bitmap(((Bitmap)this.m_content).bitmapData));
            }
            return;
        }//end

        public void  onClone (Event event )
        {
            _loc_2 =(LoaderWithInfo) event.target.loader;
            _loc_2.info.callbackFunction(event.target.loader.content);
            return;
        }//end

    }


