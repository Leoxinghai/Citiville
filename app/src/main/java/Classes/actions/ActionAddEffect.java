package Classes.actions;

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
import Classes.effects.*;
import Engine.Classes.*;

    public class ActionAddEffect extends BaseAction
    {
        protected boolean m_actionStarted =false ;
        protected EffectType m_effectType ;
        protected MapResourceEffect m_mapResourceEffect ;

        public  ActionAddEffect (MapResource param1 ,EffectType param2 )
        {
            super(param1);
            this.m_effectType = param2;
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.m_actionStarted = true;
            this.m_mapResourceEffect = MapResourceEffectFactory.createEffect(m_mapResource, this.m_effectType);
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.m_actionStarted = true;
            this.m_mapResourceEffect = MapResourceEffectFactory.createEffect(m_mapResource, this.m_effectType);
            return;
        }//end

         public void  removed ()
        {
            this.m_actionStarted = false;
            this.m_mapResourceEffect.cleanUp();
            super.removed();
            return;
        }//end

         public int  getInterrupt ()
        {
            return this.m_actionStarted ? (State.NO_INTERRUPT) : (State.NORMAL_INTERRUPT);
        }//end

         public void  update (double param1 )
        {
            AnimationEffect _loc_2 =null ;
            if (this.m_actionStarted)
            {
                if (!this.m_mapResourceEffect.isCompleted)
                {
                    if (this.m_mapResourceEffect instanceof AnimationEffect)
                    {
                        _loc_2 =(AnimationEffect) this.m_mapResourceEffect;
                        if (!_loc_2.isAutoUpdating)
                        {
                            _loc_2.updateAnimatedBitmap(param1);
                        }
                    }
                }
                else
                {
                    m_mapResource.actionQueue.removeState(this);
                }
            }
            return;
        }//end

    }



