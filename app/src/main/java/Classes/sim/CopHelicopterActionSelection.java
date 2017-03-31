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
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.bandits.*;

    public class CopHelicopterActionSelection implements IActionSelection
    {
        protected NPC m_npc ;
        protected Array m_scenes ;
        protected SoundObject m_soundLoop ;
        protected String m_soundLoopName ;
        protected MapResource m_helipad ;
        protected boolean m_forceSunset =false ;
        protected String m_idleHotspot ="helipad";
        protected boolean m_isIdle =true ;
        protected String m_groupId =null ;
public static  double PAUSE_LENGTH =1;

        public  CopHelicopterActionSelection (NPC param1 )
        {
            this.m_scenes = new Array();
            this.m_npc = param1;
            if (this.soundLoop)
            {
                this.soundLoop.pause();
            }
            return;
        }//end

        public void  addTargetScene (Vector3 param1 )
        {
            if (param1 !=null)
            {
                this.m_scenes.push(param1);
                if (this.m_scenes.length == 1 && !(this.npc.getStateMachine().getState() instanceof ActionElevate))
                {
                    this.npc.clearStates();
                }
            }
            return;
        }//end

        public void  forceSunset ()
        {
            if (!this.m_forceSunset)
            {
                this.m_forceSunset = true;
            }
            return;
        }//end

        public NPC  npc ()
        {
            return this.m_npc;
        }//end

        public MapResource  helipad ()
        {
            return this.m_helipad;
        }//end

        public String  idleHotspot ()
        {
            return this.m_idleHotspot;
        }//end

        public void  idleHotspot (String param1 )
        {
            this.m_idleHotspot = param1;
            return;
        }//end

        public String  soundLoopName ()
        {
            return this.m_soundLoopName;
        }//end

        public void  soundLoopName (String param1 )
        {
            this.m_soundLoopName = param1;
            return;
        }//end

        public String  groupId ()
        {
            return this.m_groupId;
        }//end

        public void  groupId (String param1 )
        {
            this.m_groupId = param1;
            return;
        }//end

        public boolean  isIdle ()
        {
            return this.m_isIdle;
        }//end

        public Array  getNextActions ()
        {
            Vector3 _loc_2 =null ;
            _loc_1 = SelectionResult.FAIL;
            if (this.npc)
            {
                if (!this.m_forceSunset)
                {
                    _loc_1 = this.goToTarget();
                }
                if (_loc_1 == SelectionResult.FAIL && !this.m_forceSunset)
                {
                    _loc_1 = this.goToHelipad();
                }
                else
                {
                    this.m_helipad = null;
                }
                if (_loc_1 == SelectionResult.FAIL || this.m_forceSunset)
                {
                    _loc_1 = this.goToSunset();
                }
                if (_loc_1.actions.length == 0)
                {
                    if (this.m_helipad)
                    {
                        this.m_isIdle = true;
                        _loc_2 = this.convertHotspotToNavigation(this.m_helipad.getHotspots(this.m_idleHotspot).get(0));
                        this.npc.setPosition(_loc_2.x, _loc_2.y, _loc_2.z);
                        this.npc.conditionallyReattach(true);
                    }
                }
                else
                {
                    this.m_isIdle = false;
                }
            }
            return _loc_1.actions;
        }//end

        protected SoundObject  soundLoop ()
        {
            if (!this.m_soundLoop && this.m_soundLoopName)
            {
                this.m_soundLoop = Sounds.play(this.m_soundLoopName, 0, Sounds.LOOPING);
            }
            return this.m_soundLoop;
        }//end

        protected double  getLiftOffHeight ()
        {
            return PreyManager.COPTER_FLY_HEIGHT * 0.8;
        }//end

        protected Array  getIntroActions ()
        {
            return [new ActionFn (this .npc ,void  ()
            {
                return;
            }//end
            )];
        }//end

        protected BaseAction  getIntroInsertAction ()
        {
            BaseAction insert ;
            if (this.m_helipad)
            {
                insert = new ActionElevate(this.npc, this.getLiftOffHeight());
            }
            else
            {
                insert =new ActionFn (this .npc ,void  ()
            {
                return;
            }//end
            );
            }
            return insert;
        }//end

        protected SelectionResult  goToTarget ()
        {
            if (this.m_scenes.length == 0 || !this.npc)
            {
                return SelectionResult.FAIL;
            }
            pos = this.m_scenes.get(0);
            insert = this.getIntroInsertAction();
            introActions = this.getIntroActions();
            return new SelectionResult (this .npc ,introActions .concat ([new ActionFn (this .npc ,void  ()
            {
                if (npc instanceof Helicopter)
                {
                    ((Helicopter)npc).showShadow = true;
                }
                npc.animation = "static";
                if (soundLoop && !m_soundLoop.isPlaying())
                {
                    m_soundLoop.unpause();
                }
                return;
            }//end
            ),insert ,new ActionFn (this .npc ,void  ()
            {
                if (npc instanceof Helicopter)
                {
                    ((Helicopter)npc).depthSwapObject = null;
                }
                return;
            }//end
            ),new ActionNavigateBeeline (this .npc ,pos .add (new Vector3 (0,0,PreyManager .COPTER_FLY_HEIGHT *0.8))),new ActionElevate (this .npc ,0).setVelocityScale (0.9).setEase (ActionNavigateBeeline .EASE_QUAD_OUT ),new ActionPause (this .npc ,PAUSE_LENGTH ),new ActionElevate (this .npc ,PreyManager .COPTER_FLY_HEIGHT *0.4).setDirection (Item .DIRECTION_S ).setVelocityScale (0.8).setEase (ActionNavigateBeeline .EASE_QUAD_IN ),new ActionFn (this .npc ,void  ()
            {
                m_scenes.shift();
                return;
            }//end
            )]));
        }//end

        protected SelectionResult  goToHelipad ()
        {
            if (!this.npc)
            {
                return SelectionResult.FAIL;
            }
            if (this.m_helipad && Global.world.getObjectById(this.m_helipad.getId()))
            {
                return new SelectionResult(this.npc, []);
            }
            this.m_helipad = PreyUtil.findClosestHubOfLevel("copsNBandits", this.npc.getPosition(), PreyManager.HELICOPTER_LEVEL);
            if (!this.m_helipad || !PreyManager.isActiveSpecialNPC(this.groupId, this.npc))
            {
                return SelectionResult.FAIL;
            }
            pos = this.convertHotspotToNavigation(this.m_helipad.getHotspots(this.m_idleHotspot).get(0));
            return new SelectionResult (this .npc ,[new ActionFn (this .npc ,void  ()
            {
                npc.animation = "static";
                if (soundLoop && !m_soundLoop.isPlaying())
                {
                    m_soundLoop.unpause();
                }
                return;
            }//end
            ),new ActionNavigateBeeline (this .npc ,pos .add (new Vector3 (0,0,PreyManager .COPTER_FLY_HEIGHT *0.8))),new ActionFn (this .npc ,void  ()
            {
                if (npc instanceof Helicopter)
                {
                    ((Helicopter)npc).showShadow = false;
                    ((Helicopter)npc).depthSwapObject = m_helipad;
                }
                return;
            }//end
            ),new ActionElevate (this .npc ,pos .z ).setDirection (Item .DIRECTION_SW ).setVelocityScale (0.8).setEase (ActionNavigateBeeline .EASE_QUAD_OUT ),new ActionFn (this .npc ,void  ()
            {
                npc.animation = "idle";
                if (soundLoop)
                {
                    Sounds.stop(m_soundLoop);
                    m_soundLoop = null;
                }
                return;
            }//end
            )]);
        }//end

        protected SelectionResult  goToSunset ()
        {
            view_y = Global.stage.y;
            view_height = Global.ui.screenHeight/2;
            view_x = Global.stage.x;
            view_width = Global.ui.screenWidth;
            startX = view_width+view_x+20;
            startY = view_y+view_height;
            endPos = IsoMath.screenPosToTilePos(startX+200,startY+MathUtil.randomIncl(200,-200));
            return new SelectionResult (this .npc ,[new ActionFn (this .npc ,void  ()
            {
                if (npc.animation != "static")
                {
                    npc.animation = "static";
                }
                if (soundLoop && !m_soundLoop.isPlaying())
                {
                    m_soundLoop.unpause();
                }
                return;
            }//end
            ),new ActionNavigateBeeline (this .npc ,new Vector3 (endPos .x ,endPos .y ,PreyManager .COPTER_FLY_HEIGHT )),new ActionFn (this .npc ,void  ()
            {
                Sounds.stop(m_soundLoop);
                PreyManager.cleanUpSpecialNPC(groupId, npc);
                return;
            }//end
            ), new ActionDie(this.npc)]);
        }//end

        public void  putNPCOnStation (MapResource param1 )
        {
            if (param1 == null)
            {
                return;
            }
            this.m_helipad = param1;
            _loc_2 = this.convertHotspotToNavigation(this.m_helipad.getHotspots(this.m_idleHotspot ).get(0) );
            this.npc.setPosition(_loc_2.x, _loc_2.y, _loc_2.z);
            this.npc.conditionallyReattach();
            return;
        }//end

        protected Vector3  convertHotspotToNavigation (Vector3 param1 )
        {
            _loc_2 = param1.clone ();
            _loc_3 = _loc_2.subtract(this.m_helipad.getPositionNoClone ());
            _loc_4 = Math.max(_loc_3.x ,_loc_3.y );
            _loc_5 = IsoMath.getPixelDeltaFromTileDelta(_loc_4 ,_loc_4 );
            _loc_6 = IsoMath.getPixelDeltaFromTileDelta(_loc_4 ,_loc_4 ).y /Constants.TILE_HEIGHT ;
            _loc_7 = _loc_2.add(new Vector3 (-_loc_4 ,-_loc_4 ));
            _loc_2.add(new Vector3(-_loc_4, -_loc_4)).z = Math.abs(_loc_6);
            return _loc_7;
        }//end

    }



