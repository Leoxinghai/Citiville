package Display.DialogUI;

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

import Classes.util.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class DailyBonusDialog extends GenericDialog
    {
        private DisplayObject m_arrow ;
        protected Sprite m_arrowSprite ;
        protected boolean m_isBonusAvailable ;
        protected boolean m_isRecoveryNeeded ;

        public  DailyBonusDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true )
        {
            this.m_isBonusAvailable = Global.player.dailyBonusManager.isBonusAvailable;
            this.m_isRecoveryNeeded = Global.player.dailyBonusManager.isRecoveryNeeded;
            super(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end  

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.DAILY_BONUS_ASSETS, makeAssets);
            return;
        }//end  

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dailyBonus_bg", m_comObject.dailyBonus_bg);
            _loc_1.put("dailyBonus_check", m_comObject.dailyBonus_check);
            _loc_1.put("doober_coin", m_comObject.doober_coin);
            _loc_1.put("doober_xp", m_comObject.doober_star);
            _loc_1.put("doober_energy", m_comObject.doober_bolt);
            _loc_1.put("doober_goods", m_comObject.doober_goods);
            _loc_1.put("dailyBonus_present_big", m_comObject.dailyBonus_present_big);
            _loc_1.put("dailyBonus_hr", m_comObject.dailyBonus_hr);
            _loc_1.put("dailyBonus_tab", m_comObject.dailyBonus_tab);
            _loc_1.put("dailyBonus_tab_left_regular", m_comObject.dailyBonus_tab_left_regular);
            _loc_1.put("dailyBonus_tab_left_green", m_comObject.dailyBonus_tab_left_green);
            _loc_1.put("dailyBonus_tab_left_green_selected", m_comObject.dailyBonus_tab_left_green_selected);
            _loc_1.put("dailyBonus_tab_left_red", m_comObject.dailyBonus_tab_left_red);
            _loc_1.put("dailyBonus_tab_left_red_selected", m_comObject.dailyBonus_tab_left_red_selected);
            _loc_1.put("dailyBonus_tab_mid_regular", m_comObject.dailyBonus_tab_mid_regular);
            _loc_1.put("dailyBonus_tab_mid_green", m_comObject.dailyBonus_tab_mid_green);
            _loc_1.put("dailyBonus_tab_mid_green_selected", m_comObject.dailyBonus_tab_mid_green_selected);
            _loc_1.put("dailyBonus_tab_mid_red", m_comObject.dailyBonus_tab_mid_red);
            _loc_1.put("dailyBonus_tab_mid_red_selected", m_comObject.dailyBonus_tab_mid_red_selected);
            _loc_1.put("dailyBonus_tab_right_regular", m_comObject.dailyBonus_tab_right_regular);
            _loc_1.put("dailyBonus_tab_right_green", m_comObject.dailyBonus_tab_right_green);
            _loc_1.put("dailyBonus_tab_right_green_selected", m_comObject.dailyBonus_tab_right_green_selected);
            _loc_1.put("dailyBonus_tab_right_red", m_comObject.dailyBonus_tab_right_red);
            _loc_1.put("dailyBonus_tab_right_red_selected", m_comObject.dailyBonus_tab_right_red_selected);
            _loc_1.put("dailyBonus_x", m_comObject.dailyBonus_x);
            this.m_arrow =(DisplayObject) new m_comObject.dailyBonus_nextDayArrow();
            return _loc_1;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            DailyBonusDialogView _loc_2 =new DailyBonusDialogView(param1 ,this.m_isBonusAvailable ,this.m_isRecoveryNeeded ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon );
            return _loc_2;
        }//end  

         protected void  init ()
        {
            m_holder = new Sprite();
            this.addChild(m_holder);
            m_jwindow = new JWindow(m_holder);
            this.m_arrowSprite = new Sprite();
            m_holder.addChild(this.m_arrowSprite);
            m_content = m_holder;
            m_content.addEventListener(GenericPopupEvent.SELECTED, processSelection, false, 0, true);
            m_content.addEventListener(UIEvent.REFRESH_DIALOG, onRefreshDialog, false, 0, true);
            m_content.addEventListener("close", closeMe, false, 0, true);
            return;
        }//end  

         protected void  finalizeAndShow ()
        {
            ASwingHelper.prepare(m_jpanel);
            m_jwindow.setContentPane(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            _loc_1 = m_jwindow.getWidth();
            _loc_2 = m_jwindow.getHeight();
            m_jwindow.show();
            m_holder.width = _loc_1;
            m_holder.height = _loc_2;
            setupDialogStatsTracking();
            onDialogInitialized();
            return;
        }//end  

    }



