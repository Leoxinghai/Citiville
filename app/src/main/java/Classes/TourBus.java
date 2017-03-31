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

import com.greensock.*;
//import flash.display.*;

    public class TourBus extends Vehicle
    {
        protected boolean m_busFull =true ;
        protected DisplayObject m_fullImage =null ;

        public  TourBus (String param1 ,boolean param2 )
        {
            super(param1, param2);
            return;
        }//end  

        public void  full (boolean param1 )
        {
            if (this.m_fullImage && !param1)
            {
                TweenLite.to(this.m_fullImage, 0.5, {alpha:0});
            }
            this.m_busFull = param1;
            return;
        }//end  

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = animation;
            _loc_2 = m_item.getCachedImage("bus_full",this,m_direction);
            _loc_3 = m_item.getCachedImage(_loc_1,this,m_direction);
            return _loc_3;
        }//end  

         public void  replaceContent (DisplayObject param1 )
        {
            DisplayObject _loc_4 =null ;
            Sprite _loc_5 =null ;
            super.replaceContent(param1);
            double _loc_2 =1;
            if (this.m_fullImage)
            {
                _loc_2 = this.m_fullImage.alpha;
            }
            this.m_fullImage = null;
            _loc_3 = m_item.getCachedImage("bus_full",this,m_direction);
            if (_loc_3 && this.m_busFull)
            {
                _loc_4 =(DisplayObject) _loc_3.image;
                if (_loc_4)
                {
                    _loc_5 =(Sprite) m_displayObject;
                    _loc_5.addChild(_loc_4);
                    this.m_fullImage = _loc_4;
                    this.m_fullImage.alpha = alpha;
                }
                if (!this.m_busFull && alpha > 0)
                {
                    this.m_busFull = true;
                    this.full = false;
                }
            }
            return;
        }//end  

    }



