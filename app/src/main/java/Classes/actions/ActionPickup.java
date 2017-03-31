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
import Classes.util.*;
import com.greensock.*;
import com.xinghai.Debug;

    public class ActionPickup extends NPCAction
    {
        protected int m_state ;
        protected int m_nextState ;
        protected MapResource m_target ;
        protected boolean m_firstTarget ;
        public static  int STATE_APPEAR =0;
        public static  int STATE_TRAVEL =1;
        public static  int STATE_PICKUP =2;
        public static  int STATE_DISAPPEAR =3;
        public static  int NUM_STATES =4;

        public  ActionPickup (NPC param1 ,int param2 ,MapResource param3 )
        {
            super(param1);
            Debug.debug4("ActionPickup."+param3);
            this.m_target = param3;
            this.m_state = param2;
            this.m_nextState = (param2 + 1) % NUM_STATES;
            this.m_firstTarget = param3 == null;
            return;
        }//end  

         public void  update (double param1 )
        {
            super.update(param1);
            this.branchToState(this.m_state);
            m_npc.getStateMachine().removeState(this);
            if (this.m_state != STATE_DISAPPEAR)
            {
                Global.world.citySim.pickupManager.addPickupAction(m_npc, this.m_nextState, this.m_target);
            }
            return;
        }//end  

        protected int  getNextState ()
        {
            return this.m_nextState;
        }//end  

        protected void  setNextState (int param1 )
        {
            this.m_nextState = param1;
            return;
        }//end  

        protected MapResource  getTarget ()
        {
            return this.m_target;
        }//end  

        protected boolean  isFirstTarget ()
        {
            return this.m_firstTarget;
        }//end  

        protected void  branchToState (int param1 )
        {
            switch(param1)
            {
                case STATE_APPEAR:
                {
                    this.onAppear();
                    break;
                }
                case STATE_TRAVEL:
                {
                    this.onTravel();
                    break;
                }
                case STATE_PICKUP:
                {
                    this.onPickup();
                    break;
                }
                case STATE_DISAPPEAR:
                {
                    this.onDisappear();
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return;
        }//end  

        protected void  onAppear ()
        {
            this.setNextState(STATE_TRAVEL);
            return;
        }//end  

        protected void  onTravel ()
        {
            this.m_target = Global.world.citySim.pickupManager.dequeue(m_npc.getItemName());
            if (!this.m_target)
            {
                this.setNextState(STATE_DISAPPEAR);
                return;
            }
            this.setNextState(STATE_PICKUP);
            return;
        }//end  

        protected void  onPickup ()
        {
            Debug.debug5("ActionPickup."+"onPickup");
            if (this.getTarget())
            {
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.setPosition(getTarget().getHotspot().x, getTarget().getHotspot().y);
                m_npc.conditionallyReattach();
                getTarget().unlock();
                return;
            }//end  
            ));
            }
            this.setNextState(STATE_TRAVEL);
            return;
        }//end  

        protected void  onDisappear ()
        {
            TweenLite.to(m_npc, 0.3, {alpha:0});
            TimerUtil .callLater (void  ()
            {
                if (Global.world.citySim.pickupManager.peek(m_npc.getItemName()))
                {
                    Global.world.citySim.pickupManager.addPickupAction(m_npc, STATE_APPEAR, null, false);
                }
                else
                {
                    Global.world.citySim.npcManager.removePickupNPC(m_npc.getItemName());
                }
                return;
            }//end  
            , 300);
            return;
        }//end  

        public boolean  isDisappearing ()
        {
            return this.m_state == STATE_DISAPPEAR;
        }//end  

    }



