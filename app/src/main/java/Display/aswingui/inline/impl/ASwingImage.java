package Display.aswingui.inline.impl;

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

import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class ASwingImage extends ASwingObject implements IASwingImage
    {
        private int m_scaleMode ;
        private Object m_source ;
        private Object m_alternateSource ;
        private int m_priority =0;
        private Component m_component ;

        public  ASwingImage (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  destroy ()
        {
            this.m_component = null;
            super.destroy();
            return;
        }//end

        public IASwingImage  source (Object param1 )
        {
            this.m_source = param1;
            return this;
        }//end

        public IASwingImage  alternateSource (Object param1 )
        {
            this.m_alternateSource = param1;
            return this;
        }//end

        public IASwingImage  priority (int param1 )
        {
            this.m_priority = param1;
            return this;
        }//end

        public IASwingImage  size (int param1 ,int param2 )
        {
            m_width = param1;
            m_height = param2;
            return this;
        }//end

        public IASwingImage  position (int param1 ,int param2 )
        {
            m_x = param1;
            m_y = param2;
            return this;
        }//end

        public IASwingImage  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end

        public IASwingImage  scaleMode (int param1 )
        {
            this.m_scaleMode = param1;
            return this;
        }//end

        public Component  component ()
        {
            AssetPane _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            String _loc_3 =null ;
            if (!this.m_component)
            {
                AssetPane _loc_4 =new AssetPane ();
                _loc_1 = new AssetPane();
                this.m_component = _loc_4;
                if (this.m_source instanceof Class)
                {
                    _loc_2 =(DisplayObject) new this.m_source();
                }
                else if (this.m_source instanceof DisplayObject)
                {
                    _loc_2 =(DisplayObject) this.m_source;
                }
                else if (this.m_source instanceof String)
                {
                    if (this.m_alternateSource)
                    {
                        _loc_2 = this.m_alternateSource instanceof Class ? (new (DisplayObject)this.m_alternateSource()) : (this.m_alternateSource instanceof DisplayObject ? (this.m_alternateSource) : (null));
                    }
                    _loc_3 =(String) this.m_source;
                }
                if (_loc_2)
                {
                    _loc_1.setAsset(this.format(_loc_2));
                    this.initialize(_loc_1);
                }
                if (_loc_3)
                {
                    LoadingManager.loadFromUrl(_loc_3, {completeCallback:this.imageLoadComplete, priority:this.m_priority});
                }
            }
            return this.m_component;
        }//end

        private void  imageLoadComplete (Event event )
        {
            _loc_2 =(AssetPane) this.m_component;
            _loc_3 =(DisplayObject) event.target.content;
            _loc_2.setAsset(this.format(_loc_3));
            this.initialize(_loc_2);
            return;
        }//end

        private DisplayObject  format (DisplayObject param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            if (this.m_scaleMode == ScaleMode.SCALE && (m_width >= 0 || m_height >= 0))
            {
                _loc_2 = param1.width / param1.scaleX;
                _loc_3 = param1.height / param1.scaleY;
                _loc_4 = m_width ? (m_width) : (_loc_2);
                _loc_5 = _loc_4;
                _loc_6 = m_height ? (m_height) : (_loc_3);
                _loc_7 = _loc_6;
                _loc_8 = _loc_2 / _loc_3;
                if (_loc_8 > 1)
                {
                    _loc_7 = _loc_5 / _loc_8;
                }
                else
                {
                    _loc_5 = _loc_7 * _loc_8;
                }
                _loc_9 = _loc_5 / _loc_2;
                _loc_10 = _loc_7 / _loc_3;
                param1.scaleX = _loc_9;
                param1.scaleY = _loc_10;
            }
            return param1;
        }//end

    }


