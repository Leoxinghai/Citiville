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
import Classes.util.*;
import Engine.Helpers.*;
import Modules.bandits.*;

    public class SuperHeroActionSelection extends CopHelicopterActionSelection
    {
        protected  double FADE_LENGTH =1.5;

        public  SuperHeroActionSelection (NPC param1 )
        {
            super(param1);
            return;
        }//end  

        public NPC  hero ()
        {
            return npc;
        }//end  

         protected double  getLiftOffHeight ()
        {
            _loc_1 = PreyManager.COPTER_FLY_HEIGHT*0.8;
            if (npc)
            {
                _loc_1 = _loc_1 + npc.positionZ;
            }
            return _loc_1;
        }//end  

         protected Array  getIntroActions ()
        {
            if (npc.alpha != 0)
            {
                return .get(new ActionTween(npc, ActionTween.TO, this.FADE_LENGTH, {alpha:0}));
            }
            return [new ActionFn (npc ,void  ()
            {
                return;
            }//end  
            )];
        }//end  

         protected BaseAction  getIntroInsertAction ()
        {
            return new ActionFn (npc ,void  ()
            {
                return;
            }//end  
            );
        }//end  

         protected SelectionResult  goToTarget ()
        {
            Vector3 pos ;
            if (m_scenes.length == 0 || !npc)
            {
                return SelectionResult.FAIL;
            }
            pos = m_scenes.get(0);
            introActions = this.getIntroActions();
            return new SelectionResult (npc ,introActions .concat ([new ActionFn (npc ,void  ()
            {
                npc.animation = "static";
                if (soundLoop && !m_soundLoop.isPlaying())
                {
                    m_soundLoop.unpause();
                }
                npc.alpha = 0;
                npc.setPosition(pos.x, pos.y, PreyManager.COPTER_FLY_HEIGHT);
                npc.conditionallyReattach();
                return;
            }//end  
            ),new ActionTween (npc ,ActionTween .TO ,this .FADE_LENGTH /2,{1alpha }),new ActionElevate (npc ,0).setVelocityScale (1.2).setEase (ActionNavigateBeeline .EASE_QUAD_OUT ),new ActionPause (npc ,PAUSE_LENGTH *0.8),new ActionElevate (npc ,PreyManager .COPTER_FLY_HEIGHT *0.4).setDirection (Item .DIRECTION_S ).setVelocityScale (1.2).setEase (ActionNavigateBeeline .EASE_QUAD_IN ),new ActionTween (npc ,ActionTween .TO ,this .FADE_LENGTH /2,{0alpha }),new ActionFn (npc ,void  ()
            {
                m_scenes.shift();
                return;
            }//end  
            )]));
        }//end  

         protected SelectionResult  goToHelipad ()
        {
            if (!this.hero)
            {
                return SelectionResult.FAIL;
            }
            return new SelectionResult (npc ,[new ActionFn (npc ,void  ()
            {
                if (soundLoop)
                {
                    Sounds.stop(m_soundLoop);
                    m_soundLoop = null;
                }
                PreyManager.cleanUpSpecialNPC(groupId, hero);
                return;
            }//end  
            ), new ActionDie(this.hero)]);
        }//end  

    }



