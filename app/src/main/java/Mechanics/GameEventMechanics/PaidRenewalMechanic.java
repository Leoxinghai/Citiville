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
import Display.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.Transactions.*;

    public class PaidRenewalMechanic extends BaseGameMechanic
    {
        protected String m_gameEvent ;

        public  PaidRenewalMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            _loc_2 =(double)(m_config.params.get( "renewDuration") );
            _loc_3 = GlobalEngine.getTimer ()/1000;
            _loc_4 = m_owner.getDataForMechanic(m_config.type);
            return m_owner.getDataForMechanic(m_config.type) + _loc_2 < _loc_3;
        }//end

        public MechanicActionResult  onGMPlay (Object param1 ,Array param2 )
        {
            String _loc_9 =null ;
            this.m_gameEvent = "GMPlay";
            boolean _loc_3 =false ;
            _loc_4 =(double)(m_config.params.get( "renewDuration") );
            _loc_5 =(String) m_config.params.get( "costType");
            _loc_6 = this.getCost ();
            String _loc_7 ="";
            if (m_config.params.get("icon") != null)
            {
                _loc_7 = String(m_config.params.get("icon"));
            }
            Object _loc_8 ={price _loc_6 ,currency ,item(as MapResource ).getItemFriendlyName (),duration /3600};
            if (Global.player.canBuyCash(_loc_6))
            {
                _loc_9 = ZLoc.t("Dialogs", m_config.params.get("dialogLocKey"), _loc_8);
                UI.displayMessage(_loc_9, GenericPopup.TYPE_YESNO, this.onConfirm, "pay", true, _loc_7, "RenewHeader");
            }
            return new MechanicActionResult(_loc_3, m_blocksOthers, false);
        }//end

        public void  onConfirm (GenericPopupEvent event )
        {
            double _loc_2 =0;
            if (event.button == GenericPopup.ACCEPT)
            {
                Global.player.cash = Global.player.cash - this.getCost();
                _loc_2 = GlobalEngine.getTimer() / 1000 + Number(m_config.params.get("renewDuration"));
                m_owner.setDataForMechanic(m_config.type, _loc_2, this.m_gameEvent);
                TransactionManager.addTransaction(new TMechanicAction((MechanicMapResource)m_owner, m_config.type, this.m_gameEvent, {operation:"renew"}), true);
            }
            return;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            Object _loc_2 ={price this.getCost (),currency.params.get( "costType") };
            _loc_3 = m_config.params.get(TOOLTIP_FILE) ? (m_config.params.get(TOOLTIP_FILE)) : ("Main");
            _loc_1 = ZLoc.t(_loc_3, m_config.params.get(TOOLTIP_ATTR), _loc_2);
            return _loc_1;
        }//end

        public double  getCost ()
        {
            double _loc_1 =0;
            _loc_2 = m_config.priceTestCost;
            _loc_1 = _loc_2 > 0 ? (_loc_2) : (int(m_config.params.get("cost")));
            return _loc_1;
        }//end

    }



