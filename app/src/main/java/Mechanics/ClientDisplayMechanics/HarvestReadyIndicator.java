package Mechanics.ClientDisplayMechanics;

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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.geom.*;

    public class HarvestReadyIndicator implements IClientGameMechanic, IEdgeModifier
    {
        protected HarvestableResource m_owner ;
        protected String m_type ;
        protected String m_previousState ;
        protected StagePickEffect m_stagePickEffect ;

        public  HarvestReadyIndicator ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            if (this.m_previousState != this.m_owner.state)
            {
                if (this.m_owner.state == "grown")
                {
                    if (!this.m_stagePickEffect)
                    {
                        this.m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this.m_owner, EffectType.STAGE_PICK);
                        this.m_stagePickEffect.setPickType(StagePickEffect.PICK_1);
                        this.m_stagePickEffect.queuedFloat();
                    }
                    else
                    {
                        this.m_stagePickEffect.setPickType(StagePickEffect.PICK_1);
                        this.m_stagePickEffect.reattach();
                        this.m_stagePickEffect.queuedFloat();
                    }
                }
                else if (this.m_stagePickEffect)
                {
                    this.m_stagePickEffect.stopFloat();
                    this.m_stagePickEffect.cleanUp();
                    this.m_stagePickEffect = null;
                }
            }
            this.m_previousState = this.m_owner.state;
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(HarvestableResource) param1;
            this.m_type = param2.type;
            return;
        }//end

        public void  detachDisplayObject ()
        {
            if (this.m_stagePickEffect)
            {
                this.m_stagePickEffect.stopFloat();
                this.m_stagePickEffect.cleanUp();
                this.m_stagePickEffect = null;
            }
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  isPixelInside (Point param1 )
        {
            return false;
        }//end

        public int  getFloatOffset ()
        {
            return 0;
        }//end

    }




