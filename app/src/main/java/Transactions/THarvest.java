package Transactions;

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
import Engine.Managers.*;
import Modules.realtime.*;

    public class THarvest extends TWorldState
    {
        protected HarvestableResource m_resource ;
        protected int m_coinYield ;
        protected int m_newResPop ;
        protected int m_newWorldPop ;

        public  THarvest (HarvestableResource param1 )
        {
            this.m_resource = param1;
            this.m_coinYield = param1.getCoinYield();
            this.m_newResPop = param1.getPopulationYield();
            this.m_newWorldPop = Global.world.citySim.getPopulation();
            super(param1);
            return;
        }//end

         public void  perform ()
        {
            signedWorldAction("harvest", this.m_coinYield, this.m_newResPop, this.m_newWorldPop);
            return;
        }//end

         protected void  onWorldActionComplete (Object param1 )
        {
            _loc_2 = param1.retCoinYield ;
            if (this.m_coinYield != _loc_2)
            {
                ErrorManager.addError("Coin yield didn\'t match on harvest for " + this.m_resource.getItem().name + ", expected " + this.m_coinYield + ", received " + _loc_2, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
            }
            this.m_resource.onHarvestComplete(param1);
            if (Config.DEBUG_MODE)
            {
                if (this.m_newResPop != param1.objectPopulation)
                {
                    ErrorManager.addError("Error Baby Doobers: New population did not match for " + this.m_resource.getItem().name + ", expected " + this.m_newResPop + ", received " + param1.objectPopulation, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
                else if (this.m_newWorldPop != param1.worldPopulation)
                {
                    ErrorManager.addError("Error Baby Doobers: New world population did not match for " + this.m_resource.getItem().name + ", expected " + this.m_newWorldPop + ", received " + param1.worldPopulation, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
            }
            return;
        }//end

         public RealtimeMethod  getRealtimeMethod ()
        {
            return new RealtimeMethod("showAction", "harvested", this.m_resource.getHarvestingDefinition().name);
        }//end

    }



