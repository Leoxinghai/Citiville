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

import Display.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.Transactions.*;
import Modules.attractions.*;
//import flash.events.*;
//import flash.utils.*;

    public class BaseMaintenanceMechanic extends DialogGenerationMechanic
    {
        protected String m_gameEvent ;

        public  BaseMaintenanceMechanic ()
        {
            return;
        }//end

         public boolean  canPopDialog ()
        {
            return this.isSleeping();
        }//end

        public double  startTS ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            return _loc_1.get("startTS");
        }//end

        public double  sleepTimeLeft ()
        {
            _loc_1 = GlobalEngine.getTimer ()/1000;
            _loc_2 = m_owner.getItem().harvestLoopConfig;
            _loc_3 = this.startTS +Number(_loc_2.get( "timerDuration") )+Number(_loc_2.get( "sleepDuration") );
            return Math.max(0, _loc_3 - _loc_1);
        }//end

        public boolean  isSleeping ()
        {
            _loc_1 = GlobalEngine.getTimer ()/1000;
            _loc_2 = m_owner.getItem().harvestLoopConfig;
            _loc_3 = this.startTS ;
            _loc_4 = _loc_3+Number(_loc_2.get( "timerDuration") );
            _loc_5 = _loc_3+Number(_loc_2.get( "timerDuration") )+Number(_loc_2.get( "sleepDuration") );
            return _loc_1 > _loc_4 && _loc_1 < _loc_5;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            this.m_gameEvent = param1;
            boolean _loc_3 =false ;
            boolean _loc_4 =true ;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            this.createMaintenanceDialog();
            return new MechanicActionResult(_loc_3, _loc_4, _loc_5, _loc_6);
        }//end

        public void  createMaintenanceDialog ()
        {
            int numRequired ;
            int finishWithCash ;
            consumableName = this.getConsumableName();
            numRequired = this.getNumReqired();
            finishWithCash = this.getFinishWithCash();
            onAskFriends = function(eventEvent)
            {
                return;
            }//end
            ;
            onUseConsumables = function(eventEvent)
            {
                _loc_2 = m_owner.getDataForMechanic(m_config.type ).get( "consumables") ;
                if (_loc_2 >= numRequired)
                {
                    onFinishMechanic();
                    TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, m_gameEvent, {operation:"useConsumables"}));
                }
                return;
            }//end
            ;
            onFinishWithCash = function(eventEvent)
            {
                if (Global.player.canBuyCash(finishWithCash))
                {
                    Global.player.cash = Global.player.cash - finishWithCash;
                    onFinishMechanic();
                    TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, m_gameEvent, {operation:"finishWithCash"}));
                }
                return;
            }//end
            ;
            MaintenanceDialog maintenanceDialog =new MaintenanceDialog(m_owner ,consumableName ,numRequired ,finishWithCash ,onAskFriends ,onUseConsumables ,onFinishWithCash ,this.getMessage (),this.getRewardIcon (),this.getRewardCaption ());
            UI.displayPopup(maintenanceDialog);
            return;
        }//end

        protected void  onFinishMechanic ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            _loc_1.put("startTS",  0);
            _loc_1.put("consumables",  0);
            _loc_1.put("maintenance",  1);
            m_owner.setDataForMechanic(m_config.type, _loc_1, this.m_gameEvent);
            return;
        }//end

        protected String  getConsumableName ()
        {
            String _loc_1 =null ;
            if (m_config.params.hasOwnProperty("consumableName"))
            {
                _loc_1 = m_config.params.get("consumableName");
            }
            return _loc_1;
        }//end

        protected int  getNumReqired ()
        {
            int _loc_1 =0;
            if (m_config.params.hasOwnProperty("numRequired"))
            {
                _loc_1 = int(m_config.params.get("numRequired"));
            }
            return _loc_1;
        }//end

        protected int  getFinishWithCash ()
        {
            int _loc_1 =0;
            if (m_config.params.hasOwnProperty("finishWithCash"))
            {
                _loc_1 = int(m_config.params.get("finishWithCash"));
            }
            return _loc_1;
        }//end

        protected String  getMessage ()
        {
            String _loc_1 ="";
            if (m_config.params.hasOwnProperty("message"))
            {
                _loc_1 = String(m_config.params.get("message"));
            }
            return _loc_1;
        }//end

        protected String  getRewardIcon ()
        {
            String _loc_1 ="";
            if (m_config.params.hasOwnProperty("rewardIcon"))
            {
                _loc_1 = String(m_config.params.get("rewardIcon"));
            }
            return _loc_1;
        }//end

        protected String  getRewardCaption ()
        {
            String _loc_1 ="";
            if (m_config.params.hasOwnProperty("rewardCaption"))
            {
                _loc_1 = String(m_config.params.get("rewardCaption"));
            }
            return _loc_1;
        }//end

        protected String  getCustomNpcUrl ()
        {
            String _loc_1 ="";
            if (m_config.params.hasOwnProperty("npc"))
            {
                _loc_1 = m_config.params.get("npc");
            }
            return _loc_1;
        }//end

    }



