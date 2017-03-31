package Modules.crew.ui;

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

    public class RollCallIntroDialog extends GenericDialog
    {
        protected MechanicMapResource m_spawner ;
        private int m_duration ;

        public  RollCallIntroDialog (MechanicMapResource param1 ,String param2 ,int param3 ,String param4 ,String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",String param9 ="",boolean param10 =true ,int param11 =0,String param12 ="",Function param13 =null ,String param14 ="")
        {
            this.m_spawner = param1;
            this.m_duration = param3;
            super("dialog", "RollCallIntroDialog_" + param2, GenericDialogView.TYPE_CUSTOM_OK, param7, "RollCallIntroDialog_" + param2, "", false, 0, "", null, param14);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            RollCallIntroDialogView _loc_2 =new RollCallIntroDialogView(param1 ,m_message ,m_dialogTitle ,GenericDialogView.TYPE_CUSTOM_OK ,this.m_callback ,"",0,"",null ,this.m_customOk );
            return _loc_2;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.PAYROLL_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary(true );
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            _loc_1.put("dialog_bg", (DisplayObject) new _loc_2.payroll_kickoffBG());
            _loc_1.put("payroll_dividerGradient",  _loc_2.payroll_dividerGradient);
            _loc_1.put("payroll_icon_clock",  _loc_2.payroll_icon_clock);
            _loc_1.put("payroll_icon_responses",  _loc_2.payroll_icon_responses);
            _loc_1.put("payroll_reward_coin",  _loc_2.payroll_reward_coin);
            _loc_1.put("payroll_reward_energy",  _loc_2.payroll_reward_energy);
            _loc_1.put("payroll_reward_xp",  _loc_2.payroll_reward_xp);
            _loc_1.put("payroll_rewardBG_blue",  _loc_2.payroll_rewardBG_blue);
            _loc_1.put("payroll_sam",  _loc_2.payroll_sam);
            _loc_1.put("payroll_starburst",  _loc_2.payroll_starburst);
            _loc_1.put("payroll_rewardArrow",  _loc_2.payroll_rewardArrow);
            _loc_1.put("duration",  this.m_duration);
            _loc_1.put("spawner",  this.m_spawner);
            return _loc_1;
        }//end

    }



