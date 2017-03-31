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
import Display.DialogUI.*;
import Mechanics.*;
//import flash.display.*;
//import flash.utils.*;

    public class PurchaseToInventoryMechanic extends BaseGameMechanic
    {

        public  PurchaseToInventoryMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            return (m_owner.actionMechanics[param1] as Dictionary).get(m_config.className) != null;
        }//end

        public MechanicActionResult  onGMVisitBuy (Object param1 ,Array param2 )
        {
            return this.buyHelper("GMVisitBuy", param2);
        }//end

        private MechanicActionResult  buyHelper (String param1 ,Array param2 )
        {
            DisplayObject _loc_3 =null ;
            if (this.hasOverrideForGameAction(param1))
            {
                _loc_3 = new VisitBuyDialog(((MechanicMapResource)m_owner).getItem());
                UI.displayPopup(_loc_3, true, "visit_buy_window", false);
            }
            return new MechanicActionResult(true, false, false, null);
        }//end

    }



