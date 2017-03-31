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
import Classes.util.*;

    public class ActionAnimationEffect extends NPCAction
    {
        protected AnimationEffect m_effect ;
        protected EffectType m_effectType ;

        public  ActionAnimationEffect (NPC param1 ,EffectType param2 )
        {
            super(param1);
            this.m_effect = null;
            this.m_effectType = param2;
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.startEffect();
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.startEffect();
            return;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            if (!m_npc.isVisible())
            {
                this.m_effect.cleanUp();
                m_npc.alpha = 1;
                m_npc.getStateMachine().removeState(this);
                return;
            }
            if (!this.m_effect || this.m_effect.isComplete())
            {
                m_npc.getStateMachine().removeState(this);
            }
            return;
        }//end

        protected void  startEffect ()
        {
            _loc_1 = m_npc.addAnimatedEffect(this.m_effectType);
            if (_loc_1 instanceof AnimationEffect)
            {
                this.m_effect =(AnimationEffect) _loc_1;
                if (this.m_effectType == EffectType.VEHICLE_POOF || this.m_effectType == EffectType.POOF)
                {
                    Sounds.play("poof");
                }
            }
            return;
        }//end

    }



