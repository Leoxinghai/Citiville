package Display.TrainUI;

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
//import flash.utils.*;

    public class TrainStationDialog extends GenericDialog
    {
        private TrainStationDialogView m_view ;
        public static Dictionary assetDict ;

        public  TrainStationDialog ()
        {
            super("", "", 0, null, "TrainUI_TrainStation");
            return;
        }//end

        public boolean  hasOverlay ()
        {
            return this.m_view.hasOverlay;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.TRAIN_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new m_comObject.trainDialog_bg());
            _loc_1.put("portrait_card",  m_comObject.portrait_card);
            _loc_1.put("portrait_empty",  m_comObject.portrait_empty);
            _loc_1.put("checkmark",  m_comObject.checkmark);
            _loc_1.put("clock",  new m_comObject.clock());
            _loc_1.put("crate_big",  new m_comObject.crate_big());
            _loc_1.put("crate_small",  m_comObject.crate_small);
            _loc_1.put("coin_big",  new m_comObject.coin_big());
            _loc_1.put("coin_small",  m_comObject.coin_small);
            _loc_1.put("btn_prev_disabled",  m_comObject.btn_prev_disabled);
            _loc_1.put("btn_prev_up",  m_comObject.btn_prev_up);
            _loc_1.put("btn_prev_over",  m_comObject.btn_prev_over);
            _loc_1.put("btn_prev_down",  m_comObject.btn_prev_down);
            _loc_1.put("btn_next_disabled",  m_comObject.btn_next_up);
            _loc_1.put("btn_next_up",  m_comObject.btn_next_up);
            _loc_1.put("btn_next_over",  m_comObject.btn_next_over);
            _loc_1.put("btn_next_down",  m_comObject.btn_next_down);
            assetDict = _loc_1;
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.m_view = new TrainStationDialogView(param1, m_message, m_title, m_type, m_callback);
            return this.m_view;
        }//end

    }



