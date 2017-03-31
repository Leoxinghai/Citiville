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
import Engine.Helpers.*;
import com.zynga.skelly.util.*;

    public class ActionNavigateWander extends ActionNavigate
    {
        protected double m_elapsed =0;
        protected double m_timeout =-1;
        protected double m_idleTime =0;
        protected MapResource m_previousTarget ;
        protected int m_numRerolls =3;
        protected boolean m_continueAfterFail =true ;
public static  double FAIL_PAUSE_TIME =3;

        public  ActionNavigateWander (NPC param1 ,MapResource param2 ,double param3 =0)
        {
            this.m_previousTarget = param2;
            this.m_elapsed = param3;
            super(param1, null, this.m_previousTarget);
            return;
        }//end

        public void  cleanUp ()
        {
            if (m_npc)
            {
                m_npc.getStateMachine().removeState(this);
            }
            return;
        }//end

        public ActionNavigateWander  setIdleTime (double param1 )
        {
            this.m_idleTime = param1;
            return this;
        }//end

        public ActionNavigateWander  setTimeout (double param1 )
        {
            this.m_timeout = param1;
            return this;
        }//end

        public ActionNavigateWander  setTarget (MapResource param1 )
        {
            m_target = param1;
            return this;
        }//end

        public ActionNavigateWander  setFailureCorrection (boolean param1 )
        {
            this.m_continueAfterFail = param1;
            return this;
        }//end

        public boolean  expired ()
        {
            return this.m_timeout >= 0 && this.m_elapsed > this.m_timeout;
        }//end

         public void  enter ()
        {
            this.onStart();
            super.enter();
            return;
        }//end

         public void  reenter ()
        {
            this.m_elapsed = 0;
            this.onStart();
            super.reenter();
            return;
        }//end

        protected void  onStart ()
        {
            if (m_npc instanceof Vehicle)
            {
                GlobalEngine.warning("ActionNavigateWander", "This action was not designed for vehicles.");
                this.cleanUp();
                return;
            }
            if (!m_target)
            {
                m_target = Global.world.citySim.roadManager.findRandomWalkableTileNearEdges();
            }
            return;
        }//end

         protected void  updatePath (boolean param1 )
        {
            super.updatePath(param1);
            if (m_target instanceof Sidewalk)
            {
                m_path.pop();
            }
            return;
        }//end

         public void  update (double param1 )
        {
            Array newStates ;
            dt = param1;
            this.m_elapsed = this.m_elapsed + dt;
            if (!this.expired)
            {
                if (m_npc)
                {
                    if ((!m_path || m_path.length == 0) && !this.m_navigationFailed)
                    {
                        newStates;
                        if (this.m_idleTime > 0)
                        {
                            newStates .unshift (new ActionFn (m_npc ,Curry .curry (void  (NPC param11 )
            {
                param11.animation = "idle";
                return;
            }//end
            , m_npc)), new ActionPause(m_npc, this.m_idleTime));
                        }
                        m_npc.getStateMachine().insertActionsArray(Math.max(0, (m_npc.getStateMachine().getStates().length - 1)), newStates);
                    }
                }
            }
            super.update(dt);
            if (this.expired)
            {
                this.cleanUp();
            }
            return;
        }//end

         protected void  doNavigationFailed ()
        {
            MapResource collider ;
            Box3D box ;
            super.doNavigationFailed();
            if (!this.m_continueAfterFail)
            {
                return;
            }
            m_npc.clearStates();
            int rolls ;
            colliders = Global.world.getObjectsByClassAt(MapResource,m_npc.getPositionNoClone());
            closest = Global.world.citySim.roadManager.findClosestWalkableTile(m_npc.getPositionNoClone());
            MapResource standing ;
            if (colliders.length == 0)
            {
                box = new Box3D(new Vector3(m_npc.positionX - 0.5, m_npc.positionY - 0.5), new Vector3(1.7, 1.7));
                colliders = Global.world.getCollisionMap().getIntersectingObjects(box);
            }
            int _loc_2 =0;
            _loc_3 = colliders;
            for(int i0 = 0; i0 < colliders.size(); i0++)
            {
            		collider = colliders.get(i0);


                if (collider instanceof Sidewalk || collider instanceof Road)
                {
                    standing = closest;
                    while (standing == closest && rolls < this.m_numRerolls)
                    {

                        standing = Global.world.citySim.roadManager.findRandomWalkableTile(closest);
                        rolls = (rolls + 1);
                    }
                    break;
                }
            }
            m_npc.animation = "static";
            if (standing != closest && standing != null)
            {
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = "static";
                return;
            }//end
            ), new ActionNavigateWander(m_npc, closest, this.m_elapsed).setTarget(standing).setIdleTime(this.m_idleTime).setTimeout(this.m_timeout));
                return;
            }
            if (standing == null && closest != null)
            {
                m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = "static";
                return;
            }//end
            ), new ActionNavigateBeeline(m_npc, closest instanceof Road ? (closest.getHotspot()) : (closest.getPositionNoClone().add(this.pseudoSidwalkSize)), null));
            }
            m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = "idle";
                return;
            }//end
            ), new ActionPause(m_npc, this.m_idleTime > 0 ? (this.m_idleTime) : (ActionNavigateWander.FAIL_PAUSE_TIME)));
            m_npc .getStateMachine ().addActions (new ActionFn (m_npc ,void  ()
            {
                m_npc.animation = "static";
                return;
            }//end
            ), new ActionNavigateWander(m_npc, closest, this.m_elapsed).setIdleTime(this.m_idleTime).setTimeout(this.m_timeout));
            return;
        }//end

        protected Vector3  pseudoSidwalkSize ()
        {
            return new Vector3(0.5, 0.5);
        }//end

    }



