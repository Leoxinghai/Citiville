package Modules.ships;

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
import Display.DialogUI.*;
//import flash.display.*;
//import flash.utils.*;

    public class PierUpgradesDialog extends GenericDialog
    {
        protected MechanicMapResource m_owner ;

        public  PierUpgradesDialog (MechanicMapResource param1 ,String param2 ="",Function param3 =null )
        {
            this.m_owner = param1;
            super("", "PierUpgradesDialog", 0, param3, "PierUpgradesDialog");
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            param1.put("spawner",  this.m_owner);
            PierUpgradesView _loc_2 =new PierUpgradesView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback ,m_customOk ,m_relativeIcon );
            return _loc_2;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.PIER_UPGRADE_ASSETS) ;
            _loc_1.put("hotels_goalsBackground",  _loc_2.hotels_goalsBackground);
            _loc_1.put("hotels_upgradeBackground",  _loc_2.hotels_upgradeBackground);
            _loc_1.put("shippingUpgrades_bg", (DisplayObject) new _loc_2.shippingUpgrades_bg());
            _loc_1.put("shippingUpgrades_bgCurrentLevel",  _loc_2.shippingUpgrades_bgCurrentLevel);
            _loc_1.put("shippingUpgrades_bgQuest",  _loc_2.shippingUpgrades_bgQuest);
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            _loc_1.put("payroll_checkin_confirmCheckmark",  _loc_3.payroll_checkin_confirmCheckmark);
            m_assetBG = _loc_1.get("shippingUpgrades_bg");
            return _loc_1;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =.get(DelayedAssetLoader.PIER_UPGRADE_ASSETS ,DelayedAssetLoader.PAYROLL_ASSETS) ;
            return _loc_1;
        }//end

    }



