package Engine.Classes;

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

import Engine.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class Asset implements IEventDispatcher
    {
        protected int m_referenceCount =0;
        protected EventDispatcher m_dispatcher ;
        protected String m_imageFullPath ="";
        protected double m_id =0;
        protected BitmapData m_thumbnailBitmapData ;
        public static  int THUMBNAIL_DEFAULT_WIDTH =64;
        public static  int THUMBNAIL_DEFAULT_HEIGHT =64;
        public static  int THUMBNAIL_DEFAULT_BITMAP_WIDTH =50;
        public static  int THUMBNAIL_DEFAULT_BITMAP_HEIGHT =50;

        public  Asset ()
        {
            this.generateDefaultThumbnailBitmapData();
            this.m_dispatcher = new EventDispatcher(this);
            return;
        }//end

        public void  dispose ()
        {
            if (this.m_thumbnailBitmapData != null)
            {
                this.m_thumbnailBitmapData.dispose();
            }
            return;
        }//end

        protected void  generateDefaultThumbnailBitmapData ()
        {
            this.m_thumbnailBitmapData = new BitmapData(THUMBNAIL_DEFAULT_BITMAP_WIDTH, THUMBNAIL_DEFAULT_BITMAP_HEIGHT, true, 0);
            return;
        }//end

        public String  getType ()
        {
            return Constants.ASSET_NONE;
        }//end

        public void  parseData (Object param1 )
        {
            this.m_imageFullPath = param1.imageFullPath;
            this.m_id = param1.id;
            return;
        }//end

        public void  loadThumbnail ()
        {
            return;
        }//end

        public boolean  isFullyLoaded ()
        {
            return this.m_id > 0;
        }//end

        public String  getThumbnailURL ()
        {
            return null;
        }//end

        public DisplayObject  getThumbnail ()
        {
            return null;
        }//end

        public BitmapData  getThumbnailBitmapData ()
        {
            return this.m_thumbnailBitmapData;
        }//end

        protected void  drawThumbnailBitmapData (DisplayObject param1 )
        {
            Matrix _loc_2 =new Matrix ();
            _loc_2.scale(THUMBNAIL_DEFAULT_BITMAP_WIDTH / param1.width, THUMBNAIL_DEFAULT_BITMAP_HEIGHT / param1.height);
            this.m_thumbnailBitmapData.draw(param1, _loc_2, null, null, null, true);
            return;
        }//end

        public String  getImageFullPath ()
        {
            return this.m_imageFullPath;
        }//end

        public int  getInstanceCount ()
        {
            return 0;
        }//end

        public boolean  equals (Asset param1 )
        {
            return this.m_id == param1.m_id;
        }//end

        public double  getId ()
        {
            return this.m_id;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,int param4 =0,boolean param5 =false )
        {
            this.m_dispatcher.addEventListener(param1, param2, param3, param4);
            return;
        }//end

        public boolean  dispatchEvent (Event event )
        {
            return this.m_dispatcher.dispatchEvent(event);
        }//end

        public boolean  hasEventListener (String param1 )
        {
            return this.m_dispatcher.hasEventListener(param1);
        }//end

        public void  removeEventListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            this.m_dispatcher.removeEventListener(param1, param2, param3);
            return;
        }//end

        public boolean  willTrigger (String param1 )
        {
            return this.m_dispatcher.willTrigger(param1);
        }//end

    }



