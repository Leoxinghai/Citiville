package Classes.effects;

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

import Classes.*;
import Engine.Classes.*;
import Engine.Events.*;
import com.greensock.*;
//import flash.display.*;

    public class PoofEffect extends AnimationEffect
    {
        private int m_appearFrame ;
public static  int ACTION_BAR_OFFSET =8;

        public  PoofEffect (MapResource param1 ,String param2 ,int param3 =17)
        {
            super(param1, param2);
            this.m_appearFrame = param3;
            this.hideBaseImage();
            param1.alpha = 0;
            return;
        }//end  

         public boolean  animate (int param1 )
        {
            AnimatedBitmap _loc_3 =null ;
            _loc_2 = super.animate(param1);
            if (m_effectImage)
            {
                _loc_3 =(AnimatedBitmap) m_effectImage;
                if (_loc_3.isPlaying && _loc_3.currentFrame >= this.m_appearFrame)
                {
                    this.showBaseImage();
                }
            }
            return _loc_2;
        }//end  

         public void  cleanUp ()
        {
            super.cleanUp();
            this.showBaseImage();
            m_mapResource.setActionBarOverrideY(0);
            return;
        }//end  

         protected void  onItemImageLoaded (LoaderEvent event )
        {
            super.onItemImageLoaded(event);
            this.hideBaseImage();
            if (m_mapResource.getItemName() == "NPC_lumberjack")
            {
                m_mapResource.setActionBarOverrideY(ACTION_BAR_OFFSET);
            }
            return;
        }//end  

        protected void  hideBaseImage ()
        {
            DisplayObject _loc_1 =null ;
            if (m_mapResource.content)
            {
                _loc_1 =(DisplayObject) m_mapResource.content;
                m_mapResource.alpha = 1;
                _loc_1.alpha = 0;
            }
            return;
        }//end  

        protected void  showBaseImage ()
        {
            DisplayObject _loc_1 =null ;
            if (m_mapResource && m_mapResource.content)
            {
                _loc_1 =(DisplayObject) m_mapResource.content;
                TweenLite.to(_loc_1, 0.5, {alpha:1});
            }
            return;
        }//end  

    }



