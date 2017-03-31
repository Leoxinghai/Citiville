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
import com.greensock.*;
//import flash.display.*;

    public class ShortGlowEffect extends MapResourceEffect
    {
        private boolean m_isActive ;
        private int m_glowColor ;
        private int m_duration ;
        private double m_size ;
        private boolean m_loop =false ;
        private TimelineLite m_glowTimeline ;

        public  ShortGlowEffect (MapResource param1 ,int param2 =0,int param3 =16755200,double param4 =2.5,boolean param5 =false )
        {
            super(param1);
            this.m_isActive = true;
            this.m_glowColor = param3;
            this.m_duration = param2;
            this.m_size = param4 < 0 ? (0) : (param4);
            this.m_loop = param5;
            this.addGlow();
            return;
        }//end  

        protected void  addGlow ()
        {
            DisplayObject _loc_1 =null ;
            if (this.m_isActive && m_mapResource && m_mapResource.getDisplayObject())
            {
                _loc_1 = m_mapResource.getDisplayObject();
                if (!this.m_loop)
                {
                    this.m_glowTimeline = new TimelineLite({onComplete:this.cleanUp});
                }
                else
                {
                    this.m_glowTimeline = new TimelineLite({onComplete:this.addGlow});
                }
                this.m_glowTimeline.appendMultiple(.get(new TweenLite(_loc_1, 0.2, {glowFilter:{color:this.m_glowColor, strength:100, alpha:1, blurX:this.m_size, blurY:this.m_size}}), new TweenLite(_loc_1, 0.2, {glowFilter:{color:this.m_glowColor, strength:100, alpha:0, blurX:this.m_size, blurY:this.m_size}})), 0, TweenAlign.SEQUENCE, this.m_duration);
            }
            else
            {
                this.m_glowTimeline = null;
            }
            return;
        }//end  

        public void  setLoop (boolean param1 )
        {
            this.m_loop = param1;
            this.addGlow();
            return;
        }//end  

         public boolean  animate (int param1 )
        {
            return this.m_isActive;
        }//end  

         public void  cleanUp ()
        {
            this.m_isActive = false;
            return;
        }//end  

    }



