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

import Classes.*;
import Classes.util.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class UnwitherDialog extends GenericDialog
    {
        protected Object m_marketComObj ;
        protected int m_numLoaded ;
        private HarvestableResource m_harvestable ;
        public static  String TYPE_GOODS_STORAGE ="GoodsStorage";
        public static  String TYPE_NEED_MORE_STORAGE ="NeedMoreStorage";
        public static  String SHIPS ="ships";
        public static  String CROPS ="crops";
        public static  int NUM_ASSETS_TO_LOAD =2;

        public  UnwitherDialog (HarvestableResource param1 )
        {
            this.m_harvestable = param1;
            super("instant_unwither_dialog", param1.getTypeName(), 0, null, "instant_unwither_dialog", param1.getTypeName(), true, 0);
            return;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            String _loc_2 =null ;
            _loc_2 = "unwither_" + this.m_harvestable.getTypeName();
            StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "viewed");
            return new UnwitherDialogView(param1, this.m_harvestable);
        }//end  

         protected void  loadAssets ()
        {
            super.loadAssets();
            Global.delayedAssets.get(DelayedAssetLoader.MARKET_ASSETS, this.makeAssets);
            return;
        }//end  

         protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            if (param2 == DelayedAssetLoader.MARKET_ASSETS)
            {
                this.m_marketComObj = param1;
                this.onAssetsLoaded();
            }
            else
            {
                super.makeAssets(param1, param2);
            }
            return;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_numLoaded++;
            if (this.m_numLoaded == NUM_ASSETS_TO_LOAD)
            {
                super.onAssetsLoaded();
            }
            return;
        }//end  

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = super.createAssetDict();
            _loc_1.put("card_available_unselected", this.m_marketComObj.marketItem);
            _loc_1.put("card_available_selected", this.m_marketComObj.marketItem);
            _loc_1.put("icon_cash", this.m_marketComObj.cash);
            _loc_1.put("icon_coins", this.m_marketComObj.coin);
            _loc_1.put("pop_lock", this.m_marketComObj.lockedArea);
            return _loc_1;
        }//end  

    }



