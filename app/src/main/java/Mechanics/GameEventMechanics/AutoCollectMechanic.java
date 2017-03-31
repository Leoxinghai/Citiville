package Mechanics.GameEventMechanics;

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
import Classes.sim.*;
import Display.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.automation.ui.*;
//import flash.events.*;
//import flash.utils.*;

    public class AutoCollectMechanic implements IActionGameMechanic, IToolTipModifier
    {
        protected MapResource m_owner =null ;

        public  AutoCollectMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            boolean _loc_7 =false ;
            double _loc_8 =0;
            Timer _loc_9 =null ;
            boolean _loc_3 =true ;
            boolean _loc_4 =true ;
            boolean _loc_5 =false ;
            _loc_6 = this.m_owner && !this.m_owner.isNeedingRoad;
            if (!ActionAutomationManager.instance.isEligibleForFeature)
            {
                _loc_6 = false;
            }
            if (_loc_6 && ActionAutomationManager.instance.isEligibleForAtLeastOneAutomator)
            {
                if (!Global.player.checkEnergy(-1, false))
                {
                    this.m_owner.displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                    return new MechanicActionResult(_loc_3, !_loc_4, _loc_5);
                }
                _loc_7 = false;
                if (!UI.m_tabbedCatalog)
                {
                    _loc_7 = true;
                }
                else if (!UI.m_tabbedCatalog.asset instanceof AutomationUI)
                {
                    _loc_7 = true;
                    UI.clearTabbedCatalog();
                }
                if (_loc_7)
                {
                    _loc_8 = 1000;
                    _loc_9 = new Timer(_loc_8, 1);
                    _loc_9.addEventListener(TimerEvent.TIMER_COMPLETE, showAutomationPanel, false, 0, true);
                    _loc_9.start();
                }
            }
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_5);
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MapResource) param1;
            if (this.m_owner)
            {
                this.m_owner.setIsRoadVerifiable();
                this.m_owner.setState(HarvestableResource.STATE_FALLOW);
            }
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            _loc_2 = ActionAutomationManager.instance.isEligibleForFeature;
            if (!_loc_2)
            {
                return "";
            }
            if (this.m_owner && this.m_owner.isNeedingRoad)
            {
                _loc_2 = false;
            }
            if (!_loc_2)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
            }
            else
            {
                _loc_1 = ZLoc.t("Dialogs", "AutoCollectToolTip");
            }
            return _loc_1;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

        private static void  showAutomationPanel (TimerEvent event )
        {
            ActionAutomationManager.instance.openAutomationUI();
            return;
        }//end

    }



