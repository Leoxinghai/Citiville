package Classes.xgamegifting;

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
import Display.DialogUI.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class XGameGiftingDialog extends GenericDialog
    {
        private int m_gameId ;
        public static Dictionary assetDict ;

        public  XGameGiftingDialog (int param1 )
        {
            this.m_gameId = param1;
            super("", "xgamegifting", 0, this.nukeRecievedItems, "XGameGifting_" + param1);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.INVENTORY_ASSETS, DelayedAssetLoader.XGAMEGIFTING_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.PAYROLL_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            assetDict = super.createAssetDict();
            _loc_1 = m_assetDependencies.get(DelayedAssetLoader.XGAMEGIFTING_ASSETS) ;
            assetDict.put("tab",  _loc_1.get("tab"));
            assetDict.put("tab_current",  _loc_1.get("tab_current"));
            assetDict.put("speech_bubble",  _loc_1.get("speech_bubble"));
            assetDict.put("item_counter",  _loc_1.get("item_counter"));
            assetDict.put("item_counter_disabled",  _loc_1.get("item_counter_disabled"));
            assetDict.put("item_card",  _loc_1.get("item_card"));
            assetDict.put("item_card_disabled",  _loc_1.get("item_card_disabled"));
            assetDict.put("logo_farmville",  _loc_1.get("logo_farmville"));
            assetDict.put("dialog_bg", (DisplayObject) new _loc_1.get("bg"));
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            assetDict.put("payroll_icon_clock",  _loc_2.get("payroll_icon_clock"));
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.INVENTORY_ASSETS) ;
            assetDict.put("countBG",  _loc_3.get("countBG"));
            return assetDict;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new XGameGiftingDialogView(param1, this.m_gameId);
        }//end

        private void  nukeRecievedItems (Event event )
        {
            XGameGiftingManager.instance.invalidateNotice(this.m_gameId);
            return;
        }//end

    }


