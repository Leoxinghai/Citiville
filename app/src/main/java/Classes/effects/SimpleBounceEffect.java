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
import com.greensock.easing.*;

    public class SimpleBounceEffect extends MapResourceEffect
    {
        private  double SHAKE_WIDTH =5;
        private  double BOUNCE_HEIGHT =75;
        private boolean m_isAnimationComplete =false ;
        private double m_originalX ;
        private double m_originalY ;
        private TimelineLite m_animTimeline ;

        public  SimpleBounceEffect (MapResource param1 )
        {
            super(param1);
            this.animate(1);
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (this.m_isAnimationComplete)
            {
                return false;
            }
            if (isMapResourceLoaded)
            {
                this.startBounce();
                this.m_isAnimationComplete = true;
                return false;
            }
            return true;
        }//end

         public void  cleanUp ()
        {
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
            }
            if (this.m_isAnimationComplete)
            {
                m_mapResource.content.x = this.m_originalX;
                m_mapResource.content.y = this.m_originalY;
            }
            else
            {
                this.m_isAnimationComplete = true;
            }
            m_isCompleted = true;
            return;
        }//end

        private void  startBounce ()
        {
            this.m_originalX = m_mapResource.content.x;
            this.m_originalY = m_mapResource.content.y;
            _loc_1 = this.SHAKE_WIDTH ;
            _loc_2 = this.BOUNCE_HEIGHT ;
            this.m_animTimeline = new TimelineLite({onComplete:this.bounceCompleteHandler});
            this.m_animTimeline.appendMultiple(.get(new TweenLite(m_mapResource.content, 0.25, {y:this.m_originalY - _loc_2, ease:Quad.easeOut}), new TweenLite(m_mapResource.content, 0.25, {y:this.m_originalY, ease:Quad.easeIn}), new TweenLite(m_mapResource.content, 0.12, {y:this.m_originalY - _loc_2 * 0.25, ease:Quad.easeOut}), new TweenLite(m_mapResource.content, 0.12, {y:this.m_originalY, ease:Quad.easeIn})), 0, TweenAlign.SEQUENCE);
            return;
        }//end

        private void  bounceCompleteHandler ()
        {
            this.cleanUp();
            return;
        }//end

    }


