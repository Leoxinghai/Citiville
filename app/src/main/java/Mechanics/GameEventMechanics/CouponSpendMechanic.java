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
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class CouponSpendMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gameEvent ="all";

        public  CouponSpendMechanic ()
        {
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            this.m_gameEvent = param1;
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            _loc_4 = this.m_config.rawXMLConfig.@consumable;
            if (Global.player.hasCoupon(_loc_4))
            {
                Global.player.useCoupon(_loc_4);
                TransactionManager.addTransaction(new TMechanicAction(this.m_owner, this.m_config.type, this.m_gameEvent, {operation:"useCoupon"}));
                _loc_3 = true;
            }
            return new MechanicActionResult(_loc_3, true, false, null);
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



