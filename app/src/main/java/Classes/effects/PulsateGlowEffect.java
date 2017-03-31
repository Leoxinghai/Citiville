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

    public class PulsateGlowEffect extends MapResourceEffect
    {
        private boolean m_isActive ;
        private int m_glowColor ;
        private TimelineLite m_glowTimeline ;

        public  PulsateGlowEffect (MapResource param1 ,int param2 =16755200)
        {
            DisplayObject _loc_3 =null ;
            super(param1);
            this.m_isActive = true;
            this.m_glowColor = param2;
            if (m_mapResource.getDisplayObject())
            {
                _loc_3 = m_mapResource.getDisplayObject();
                this.m_glowTimeline = new TimelineLite({onComplete:this.cleanUp});
                this.m_glowTimeline.insert(TweenMax.to(_loc_3, 0.5, {glowFilter:{color:param2, alpha:1, blurX:8, blurY:8, strength:10}, repeat:-1, yoyo:true}));
            }
            return;
        }//end

         public boolean  animate (int param1 )
        {
            DisplayObject _loc_2 =null ;
            if (!this.m_isActive)
            {
                this.m_glowTimeline.clear();
                this.m_glowTimeline.kill();
                _loc_2 = m_mapResource.getDisplayObject();
                if (_loc_2)
                {
                    _loc_2.filters = new Array();
                }
            }
            return this.m_isActive;
        }//end

         public void  cleanUp ()
        {
            this.m_isActive = false;
            return;
        }//end

    }



