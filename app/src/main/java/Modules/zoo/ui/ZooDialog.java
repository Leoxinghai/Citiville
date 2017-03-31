package Modules.zoo.ui;

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

    public class ZooDialog extends GenericDialog
    {
        private MechanicMapResource m_spawner ;
        public static Dictionary assetDict =new Dictionary ();

        public  ZooDialog (MechanicMapResource param1 )
        {
            this.m_spawner = param1;
            super("", "", 0, null, "ZooDialog");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.ZOO_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = m_assetDependencies.get(DelayedAssetLoader.ZOO_ASSETS) ;
            assetDict.put("spawner",  this.m_spawner);
            assetDict.put("dialog_bg", (DisplayObject) new _loc_1.dialog_bg());
            assetDict.put("market_bg", (DisplayObject) new _loc_1.market_bg());
            assetDict.put("tab_unselected",  _loc_1.tab_unselected);
            assetDict.put("tab_selected",  _loc_1.tab_selected);
            assetDict.put("left_button_over",  _loc_1.left_button_over);
            assetDict.put("left_button_tab",  _loc_1.left_button_tab);
            assetDict.put("left_button_up",  _loc_1.left_button_up);
            assetDict.put("right_button_tab",  _loc_1.right_arrow_tab);
            assetDict.put("right_button_over",  _loc_1.right_button_over);
            assetDict.put("right_button_up",  _loc_1.right_button_up);
            assetDict.put("speech_bubble",  _loc_1.speech_bubble);
            assetDict.put("enclosure_box",  _loc_1.enclosure_box);
            assetDict.put("enclosure_close_btn_over",  _loc_1.enclosure_close_btn_over);
            assetDict.put("enclosure_close_btn_up",  _loc_1.enclosure_close_btn_up);
            assetDict.put("tab_locked",  _loc_1.tab_locked);
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.MARKET_ASSETS) ;
            assetDict.put("card_available_unselected",  _loc_2.slices_card_inactive);
            assetDict.put("card_available_selected",  _loc_2.slices_card_active);
            assetDict.put("icon_cash",  _loc_2.cash);
            assetDict.put("counter_active",  _loc_2.slices_counter_active);
            assetDict.put("counter_inactive",  _loc_2.slices_counter_inactive);
            return assetDict;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new ZooDialogView(param1, m_message, m_title, m_type, m_callback, this.m_spawner as ZooEnclosure);
        }//end

    }



