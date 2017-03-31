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
import Engine.Classes.*;
import com.zynga.skelly.util.*;

    public class CopNPCActionSelection implements IActionSelection
    {
        protected NPC m_npc ;
        protected MapResource m_target ;
        protected boolean m_arrived =false ;
public static double WANDER_TIME =10;
public static double PAUSE_TIME =3;

        public  CopNPCActionSelection (NPC param1 ,MapResource param2 )
        {
            this.m_npc = param1;
            this.m_target = param2;
            return;
        }//end

        public MapResource  target ()
        {
            return this.m_target;
        }//end

        public void  target (MapResource param1 )
        {
            this.arrived = param1 != this.m_target ? (false) : (this.arrived);
            this.m_target = param1;
            this.m_npc.clearStates();
            return;
        }//end

        public boolean  arrived ()
        {
            return this.m_arrived;
        }//end

        public void  arrived (boolean param1 )
        {
            this.m_arrived = param1;
            return;
        }//end

        public Array  getNextActions ()
        {
            _loc_1 = SelectionResult.FAIL;
            _loc_1 = this.goToTarget();
            if (_loc_1 == SelectionResult.FAIL)
            {
                _loc_1 = this.patrolTarget();
            }
            if (_loc_1 == SelectionResult.FAIL)
            {
                this.arrived = false;
                this.m_npc.animation = "static";
                return .get(new ActionNavigateWander(this.m_npc, null).setIdleTime(3));
            }
            return _loc_1.actions;
        }//end

        protected SelectionResult  goToTarget ()
        {
            if (!this.target || !Global.world.getObjectById(this.target.getId()) || this.arrived)
            {
                return SelectionResult.FAIL;
            }
            curState = this.m_npc.getStateMachine().getState();
            if (curState instanceof ActionNavigate)
            {
                if (((ActionNavigate)curState).target == this.target)
                {
                    return SelectionResult.FAIL;
                }
            }
            return new SelectionResult (this .target ,[new ActionNavigate (this .m_npc ,this .target ,null ).setFallbackAction (new ActionFn (this .m_npc ,Curry .curry (CopNPCActionSelection .fallBackBeeline ,this .m_npc ,this .target ,this ))),new ActionFn (this .m_npc ,Curry .curry (void  (CopNPCActionSelection param1 )
            {
                param1.arrived = true;
                return;
            }//end
            , this))]);
        }//end

        protected SelectionResult  patrolTarget ()
        {
            if (!this.target || !Global.world.getObjectById(this.target.getId()))
            {
                return SelectionResult.FAIL;
            }
            return new SelectionResult(this.target, [new ActionBusinessPacing(this.m_npc, this.target, false).setCheckBusiness(true)]);
        }//end

        public static void  fallBackBeeline (NPC param1 ,MapResource param2 ,CopNPCActionSelection param3 )
        {
            npc = param1;
            target = param2;
            selection = param3;
            connected = Global.world.citySim.roadManager.getConnectedRoadGraph(target);
            if (!connected)
            {
                npc .getStateMachine ().addActions (new ActionFn (npc ,void  ()
            {
                npc.setPosition(target.getHotspot().x, target.getHotspot().y);
                selection.arrived = true;
                return;
            }//end
            ));
            }
            else
            {
                npc.getStateMachine().addActions(new ActionNavigateBeeline(npc, Global.world.citySim.roadManager.findClosestWalkableTileInGraph(npc.getPosition(), connected).getPosition(), null));
            }
            return;
        }//end

    }



