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
//import flash.geom.*;

    public class ActionHarvest extends BaseResourceAction
    {
        protected HarvestableResource m_harvestResource ;
        private boolean m_playOnReenter =true ;

        public  ActionHarvest (NPC param1 ,HarvestableResource param2 )
        {
            super(param1, param2);
            this.m_harvestResource = param2;
            return;
        }//end

         public void  update (double param1 )
        {
            double _loc_2 =0;
            String _loc_3 =null ;
            if (m_actionStarted)
            {
                m_actionTime = m_actionTime + param1;
                _loc_2 = m_actionTime / this.m_harvestResource.getHarvestTime();
                if (_loc_2 > 1)
                {
                    if (this.m_harvestResource.isHarvestable())
                    {
                        this.finalizeHarvest();
                    }
                    else if (Global.isVisiting())
                    {
                        switch(this.m_harvestResource.getState())
                        {
                            case HarvestableResource.STATE_PLANTED:
                            {
                                this.finalizeWater();
                                break;
                            }
                            case HarvestableResource.STATE_WITHERED:
                            {
                                this.finalizeRevive();
                                break;
                            }
                            default:
                            {
                                break;
                            }
                        }
                    }
                    m_npc.getStateMachine().removeState(this);
                }
                else if (this.m_harvestResource.showActionBar())
                {
                    _loc_3 = this.m_harvestResource.getActionText();
                    switch(this.m_harvestResource.getState())
                    {
                        case HarvestableResource.STATE_PLANTED:
                        {
                            _loc_3 = this.m_harvestResource.getWateringText();
                            break;
                        }
                        case HarvestableResource.STATE_WITHERED:
                        {
                            _loc_3 = this.m_harvestResource.getRevivingText();
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    this.m_harvestResource.setActionBarOffset(0, this.m_harvestResource.content.height >> 1);
                    this.m_harvestResource.setActionProgress(true, _loc_3, _loc_2, false);
                }
            }
            return;
        }//end

        protected void  finalizeHarvest ()
        {
            _loc_1 = this.m_harvestResource.harvest ();
            if (_loc_1)
            {
                this.m_harvestResource.doHarvestDropOff();
            }
            return;
        }//end

        protected void  finalizeWater ()
        {
            _loc_1 = this.m_harvestResource.visitBoost ();
            return;
        }//end

        protected void  finalizeRevive ()
        {
            _loc_1 = this.m_harvestResource.revive ();
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            if (this.m_actionStarted)
            {
                this.m_playOnReenter = false;
                Sounds.playFromSet(Sounds.SET_HARVEST, this.m_harvestResource);
                m_npc.hideFeedbackBubble();
            }
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            if (this.m_playOnReenter && m_actionStarted)
            {
                Sounds.playFromSet(Sounds.SET_HARVEST, this.m_harvestResource);
            }
            return;
        }//end

         public void  exit ()
        {
            this.m_harvestResource.setActionProgress(false);
            this.m_harvestResource.setActionBarOffset(0, 0);
            super.exit();
            return;
        }//end

         protected Point  getActionPosition ()
        {
            return null;
        }//end

    }



