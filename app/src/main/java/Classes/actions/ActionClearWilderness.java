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
import Engine.*;
import Engine.Helpers.*;
import com.xinghai.Debug;

    public class ActionClearWilderness extends ActionPickup
    {
        private  int FADE_LENGTH =1;
        private  String WALK_ANIM ="static";
        private  int MAX_DISTANCE_TO_WALK =10;
        private  double WALK_DURATION =2;

        public  ActionClearWilderness (NPC param1 ,int param2 ,MapResource param3 )
        {
            super(param1, param2, param3);
            return;
        }//end

         protected void  onAppear ()
        {
            super.onAppear();
            m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.alpha = 0;
                return;
            }//end
            ));
            return;
        }//end

         protected void  onTravel ()
        {
            super.onTravel();
            if (getTarget() && !isFirstTarget())
            {
                this.walkOrFade();
            }
            return;
        }//end

        protected void  walkOrFade ()
        {
            _loc_1 = getTarget().getPositionNoClone().subtract(m_npc.getPositionNoClone());
            if (_loc_1.dot(_loc_1) < this.MAX_DISTANCE_TO_WALK)
            {
                this.walkTravel();
            }
            else
            {
                this.fadeTravel();
            }
            return;
        }//end

        protected void  walkTravel ()
        {
            Vector3 posA ;
            Vector3 posB ;
            Vector3 stepOne ;
            boolean posAWalkable ;
            boolean posBWalkable ;
            boolean bothWalkableAndShouldSwitch ;
            diff = getTarget().getPositionNoClone().subtract(m_npc.getPositionNoClone());
            if (diff.x != 0 && diff.y != 0)
            {
                posA = new Vector3(getTarget().positionX, m_npc.positionY);
                posB = new Vector3(m_npc.positionX, getTarget().positionY);
                stepOne = posA;
                posAWalkable = Global.world.getCollisionMap().getObjectsByPosition(posA.x, posA.y).length == 0;
                posBWalkable = Global.world.getCollisionMap().getObjectsByPosition(posB.x, posB.y).length == 0;
                bothWalkableAndShouldSwitch = posAWalkable && posBWalkable && MathUtil.randomIncl(1, 0) == 1;
                if (!posAWalkable && !posBWalkable)
                {
                    this.fadeTravel();
                    return;
                }
                if (bothWalkableAndShouldSwitch || !posAWalkable)
                {
                    stepOne = posB;
                }
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = WALK_ANIM;
                return;
            }//end
            ), new ActionTranslate(m_npc, this.WALK_DURATION, stepOne), new ActionTranslate(m_npc, this.WALK_DURATION, getTarget().getPosition()));
            }
            else
            {
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = WALK_ANIM;
                return;
            }//end
            ), new ActionTranslate(m_npc, this.WALK_DURATION, getTarget().getPosition()));
            }
            return;
        }//end

        protected void  fadeTravel ()
        {
            m_npc.getStateMachine().addActions(new ActionTween(m_npc, ActionTween.TO, this.FADE_LENGTH, {alpha:0}));
            return;
        }//end

         protected void  onPickup ()
        {
            Debug.debug5("ActionClearWilderness.onPickup");
            if (getTarget())
            {
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.setPosition(getTarget().positionX, getTarget().positionY);
                m_npc.conditionallyReattach();
                getTarget().unlock();
                return;
            }//end
            ));
            }
            setNextState(STATE_TRAVEL);
            if (!getTarget())
            {
                return;
            }
            clearTimeInSec = Global.gameSettings().getNumber("actionBarWildernessClear");
            randomDir = MathUtil.randomIncl(Constants.DIRECTION_NW,Constants.DIRECTION_NE);
            m_npc.setDirection(randomDir);
            m_npc.setState(m_npc.getState());
            Sounds.play("lumberjack");
            m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                _loc_1 = (Wilderness)getTarget()
                m_npc.animation = _loc_1.getPickupAnim();
                return;
            }//end
            ));
            if (m_npc.alpha < 1)
            {
                m_npc.getStateMachine().addActions(new ActionAnimationEffect(m_npc, EffectType.POOF));
            }
            target = getTarget();
            if (!target.canClearFromMap())
            {
                target.unlock();
                Global.world.citySim.pickupManager.clearQueue(m_npc.getItemName());
                Global.world.citySim.npcManager.removePickupNPC(m_npc.getItemName());
                return;
            }
            m_npc.getStateMachine().addActions(new ActionProgressBar(m_npc, target, target.getActionText(), clearTimeInSec));
            return;
        }//end

    }



