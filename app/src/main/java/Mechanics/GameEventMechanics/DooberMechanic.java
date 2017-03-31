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
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
//import flash.utils.*;
import validation.*;

    public class DooberMechanic implements IDooberMechanic, IValidationMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected GenericValidationScript m_validator ;
        protected boolean m_sendTransactions ;

        public  DooberMechanic ()
        {
            this.m_sendTransactions = true;
            return;
        }//end

        public boolean  canGiveReward ()
        {
            return this.isValid();
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

        public boolean  isValid ()
        {
            if (this.m_validator)
            {
                return this.m_validator.validate(this.m_owner);
            }
            return true;
        }//end

        public String  getRandomModifiersName ()
        {
            return this.m_config.params.get("randomModifiers");
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Array _loc_4 =null ;
            boolean _loc_3 =false ;
            if (this.canGiveReward())
            {
                _loc_4 = new Array();
                this.m_owner.doobersArray = Global.player.processRandomModifiers(this.m_owner.getItem(), this.m_owner, true, _loc_4, "default", this.getRandomModifiersName());
                this.m_owner.secureRandsArray = _loc_4;
                this.m_owner.spawnDoobers();
                GameTransactionManager.addTransaction(new TDooberMechanicAction(this.m_owner, this.m_config.type));
                _loc_3 = true;
            }
            return new MechanicActionResult(_loc_3, false, false);
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            if (param1 instanceof ItemInstance)
            {
                this.m_validator = ((ItemInstance)param1).getItem().getValidation(param2.params.get("validate"));
            }
            return;
        }//end

        public Dictionary  getRewardInfo ()
        {
            return Global.gameSettings().parseDisplayedStats(this.m_owner.getItemName(), this.m_config.rawXMLConfig.displayedStats, this.getRandomModifiersName());
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



