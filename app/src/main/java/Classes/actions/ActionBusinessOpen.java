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
//import flash.geom.*;

    public class ActionBusinessOpen extends BaseResourceAction
    {
        protected Business m_business ;

        public  ActionBusinessOpen (NPC param1 ,HarvestableResource param2 )
        {
            super(param1, param2);
            if (param2 && param2 instanceof Business)
            {
                this.m_business =(Business) param2;
            }
            else
            {
                this.m_business = null;
            }
            return;
        }//end

         public void  update (double param1 )
        {
            double _loc_2 =0;
            String _loc_3 =null ;
            if (m_actionStarted)
            {
                if (this.m_business && !this.m_business.isOpen())
                {
                    m_actionTime = m_actionTime + param1;
                    _loc_2 = m_actionTime / this.m_business.getOpenTime();
                    if (_loc_2 > 1)
                    {
                        if (Global.player.checkEnergy(-this.m_business.harvestingDefinition.openEnergyCost))
                        {
                            this.m_business.openBusiness();
                        }
                        else
                        {
                            this.m_business.displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                        }
                        m_npc.getStateMachine().removeState(this);
                    }
                    else if (this.m_business.showActionBar())
                    {
                        _loc_3 = this.m_business.getOpeningText();
                        this.m_business.setActionBarOffset(0, this.m_business.content.height >> 1);
                        this.m_business.setActionProgress(true, _loc_3, _loc_2, false);
                    }
                }
                else
                {
                    m_npc.getStateMachine().removeState(this);
                }
            }
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            if (this.m_actionStarted)
            {
                m_npc.hideFeedbackBubble();
            }
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            return;
        }//end

         public void  exit ()
        {
            this.m_business.setActionProgress(false);
            this.m_business.setActionBarOffset(0, 0);
            super.exit();
            return;
        }//end

         protected Point  getActionPosition ()
        {
            return null;
        }//end

    }



