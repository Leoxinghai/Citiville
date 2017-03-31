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

    public class FocusGlowEffect extends MapResourceEffect
    {
        private boolean m_isActive ;
        private boolean m_isCleaningUp ;
        private int m_glowColor ;
        private int m_duration ;
        private double m_size ;
        private TimelineLite m_glowStartTimeline ;
        private TimelineLite m_glowEndTimeline ;

        public  FocusGlowEffect (MapResource param1 ,int param2 =0,int param3 =16755200,double param4 =2.5)
        {
            DisplayObject _loc_5 =null ;
            super(param1);
            this.m_isActive = true;
            this.m_isCleaningUp = false;
            this.m_glowColor = param3;
            this.m_duration = param2;
            this.m_size = param4 < 0 ? (0) : (param4);
            if (m_mapResource.getDisplayObject())
            {
                _loc_5 = m_mapResource.getDisplayObject();
                this.m_glowStartTimeline = new TimelineLite({onComplete:this.cleanUpEmpty});
                this.m_glowStartTimeline.appendMultiple(.get(new TweenLite(_loc_5, 0.2, {glowFilter:{color:this.m_glowColor, strength:100, alpha:1, blurX:this.m_size, blurY:this.m_size}})), 0, TweenAlign.SEQUENCE, this.m_duration);
            }
            return;
        }//end  

         public boolean  animate (int param1 )
        {
            DisplayObject _loc_2 =null ;
            if (this.m_isCleaningUp == false && Global.world.citySim.resortManager.getFocusNPC() == null)
            {
                _loc_2 = m_mapResource.getDisplayObject();
                this.m_isCleaningUp = true;
                this.m_glowEndTimeline = new TimelineLite({onComplete:this.cleanUp});
                this.m_glowEndTimeline.appendMultiple(.get(new TweenLite(_loc_2, 0.2, {glowFilter:{color:this.m_glowColor, strength:100, alpha:0, blurX:this.m_size, blurY:this.m_size}})), 0, TweenAlign.SEQUENCE, 0);
            }
            return this.m_isActive;
        }//end  

         public void  cleanUp ()
        {
            this.m_isActive = false;
            return;
        }//end  

        public void  cleanUpEmpty ()
        {
            return;
        }//end  

    }



