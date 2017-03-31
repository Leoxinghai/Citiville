package Classes.sim;

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
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Classes.*;
import com.greensock.*;
//import flash.events.*;

    public class FranchiseShockwave extends ConsumerShockwave
    {

        public  FranchiseShockwave (MapResource param1 ,double param2 ,double param3 ,Function param4 =null ,Function param5 =null ,boolean param6 =false ,int param7 =16755200)
        {
            super(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

         protected void  onEnterFrame (Event event )
        {
            NPC candidate ;
            ConsumerShockwave shockWave ;
            double fadeout ;
            event = event;
            if (m_origin == null)
            {
                stop();
                return;
            }
            updateVisuals();
            checker = function(param1WorldObject)
            {
                if (!(param1 instanceof NPC) || (Business)m_origin == null)
                {
                    return false;
                }
                _loc_2 = param1as NPC ;
                return _loc_2 != null && m_seenNpcs != null && m_seenNpcs.get(_loc_2) == null && _loc_2.getPositionNoClone().subtract(m_center).length() <= m_tileRadius && !(_loc_2 instanceof DummyNPC);
            }//end
            ;
            freshmeat = Global.world.getObjectsByPredicate(checker);
            if (freshmeat.length > 0 && m_npcConsumer != null)
            {
                int _loc_3 =0;
                _loc_4 = freshmeat;
                for(int i0 = 0; i0 < freshmeat.size(); i0++)
                {
                	candidate = freshmeat.get(i0);


                    m_seenNpcs.put(candidate,  true);
                    shockWave = new ConsumerShockwave(candidate, m_velocity / NPC_SHOCKWAVE_VELOCITY_FACTOR, NPC_SHOCKWAVE_RADIUS, null, null, true);
                    candidate.addAnimatedEffect(EffectType.SHORT_GLOW);
                    TimerUtil .callLater (void  ()
            {
                candidate.addAnimatedEffect(EffectType.COIN);
                return;
            }//end
            , 500);
                    if (candidate instanceof Peep && (candidate as Peep).isFranchiseFreebie)
                    {
                        TimerUtil .callLater (void  ()
            {
                candidate.getStateMachine().removeAllStates();
                candidate.getStateMachine().addActions(new ActionDie(candidate));
                return;
            }//end
            , 800);
                    }
                    shockWave.start();
                }
                m_npcConsumer(freshmeat);
            }
            if (m_fadeHalfway && m_tileRadius >= m_maxRadius / 2)
            {
                fadeout;
                TweenLite.to(m_animation, fadeout, {alpha:0});
                m_fadeHalfway = false;
            }
            if (m_tileRadius >= m_maxRadius)
            {
                if (m_endCallback != null)
                {
                    m_endCallback();
                }
                else
                {
                    stop();
                }
            }
            return;
        }//end

    }



