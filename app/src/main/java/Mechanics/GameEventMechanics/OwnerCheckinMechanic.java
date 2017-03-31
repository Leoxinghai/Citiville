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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.display.*;
//import flash.utils.*;

    public class OwnerCheckinMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {
        protected ICheckInHandler m_checkInHandler =null ;

        public  OwnerCheckinMechanic ()
        {
            return;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            this.m_checkInHandler =(ICheckInHandler) m_owner;
            return;
        }//end

         public String  getPick ()
        {
            if (this.m_checkInHandler.isOpenForFriend())
            {
                return "vip";
            }
            if (m_config.params.hasOwnProperty("showWhenHarvestable") && m_config.params.get("showWhenHarvestable") == 1 && (this.m_checkInHandler.isNPCFull() || this.m_checkInHandler.isFriendFull()))
            {
                return "vip";
            }
            return null;
        }//end

         public Array  getPicksToHide ()
        {
            if (this.m_checkInHandler.isOpenForFriend())
            {
                return null;
            }
            if (m_config.params.hasOwnProperty("showWhenHarvestable") && m_config.params.get("showWhenHarvestable") == 1 && (this.m_checkInHandler.isNPCFull() || this.m_checkInHandler.isFriendFull()))
            {
                return null;
            }
            return new Array("vip");
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            if (this.m_checkInHandler.isOpenForFriend())
            {
                return true;
            }
            if (m_config.params.hasOwnProperty("showWhenHarvestable") && m_config.params.get("showWhenHarvestable") == 1 && (this.m_checkInHandler.isNPCFull() || this.m_checkInHandler.isFriendFull()))
            {
                return true;
            }
            return false;
        }//end

         public DisplayObject  instantiateDialog ()
        {
            DisplayObject _loc_2 =null ;
            Global.world.citySim.resortManager.hotelDialogActive();
            _loc_1 = getDefinitionByName(m_config.params.get( "dialogToPop") )as Class ;
            _loc_2 = new _loc_1(m_owner, null, 0, "TEMP TITLE", Global.world.citySim.resortManager.hotelDialogClosed);
            return _loc_2;
        }//end

    }



