package Modules.pickthings.Display;

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
import Modules.pickthings.*;
//import flash.events.*;
//import flash.utils.*;

    public class PickThingsDialog extends GenericDialog
    {
        private double assetCount =0;
        private Object assetInfo ;
        private Dictionary assets ;
        public String m_minigameId ;
        private boolean setupComplete =false ;
        protected Dictionary m_data ;
        protected Dictionary m_config ;

        public  PickThingsDialog (String param1 )
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            String _loc_5 =null ;
            this.assetInfo = {};
            super("");
            m_jpanel = null;
            this.m_minigameId = param1;
            this.m_data = PickThingsManager.instance.getDataForGameBoard(param1);
            this.m_config = Global.gameSettings().getPickThingsConfig(param1);
            this.assets = new Dictionary();
            int _loc_2 =0;
            while (_loc_2 < ((Array)this.m_config.get("rewards")).length())
            {

                _loc_3 = this.m_config.get("rewards").get(_loc_2);
                _loc_4 = Global.gameSettings().getItemByName(_loc_3);
                _loc_5 = Global.gameSettings().getImageByNameRelativeUrl(_loc_3, _loc_4.iconImageName);
                this.assetInfo.put(_loc_5,  _loc_3);
                Global.delayedAssets.get(_loc_5, this.makeAsset);
                this.assetCount++;
                _loc_2++;
            }
            this.loadAsset("Background.png", "bg");
            this.loadAsset("PrizeWindow.png", "window");
            this.loadAsset("PrizeBorder.png", "border");
            this.loadAsset("Unknown.png", "unknown");
            this.loadAsset("Counter.png", "counter");
            this.loadAsset("Bubble.png", "bubble");
            this.loadAsset("Claw.png", "claw");
            this.loadAsset("Tracks.png", "tracks");
            this.loadAsset("Conveyer.png", "conveyer");
            this.loadAsset("CashBurst.png", "cashbubble");
            this.loadAsset("PrizeBurst.png", "prizeburst");
            this.loadAsset("Piece0.png", "p0");
            this.loadAsset("Piece1.png", "p1");
            this.loadAsset("Piece2.png", "p2");
            this.loadAsset("pickState.png", "pickState");
            this.setupComplete = true;
            this.createView();
            return;
        }//end

         protected void  loadAssets ()
        {
            return;
        }//end

        public void  loadAsset (String param1 ,String param2 )
        {
            _loc_3 = this.m_config.get( "assetPrefix") +"/"+param1 ;
            this.assetInfo.put(_loc_3,  param2);
            this.assetCount++;
            Global.delayedAssets.get(_loc_3, this.makeAsset);
            return;
        }//end

        public void  makeAsset (Object param1 ,String param2 )
        {
            if (param2 in this.assets)
            {
                return;
            }
            this.assetCount--;
            this.assets.get(this.assetInfo.put(param2),  param1);
            this.createView();
            return;
        }//end

        public void  createView ()
        {
            if (m_jpanel == null && this.assetCount == 0 && this.setupComplete)
            {
                m_jpanel = new PickThingsDialogView(this.assets, this.m_minigameId, this.m_data, this.m_config);
                m_jpanel.addEventListener(Event.CLOSE, this.closePopup, false, 0, true);
                finalizeAndShow();
                ((PickThingsDialogView)m_jpanel).checkFTUE();
            }
            return;
        }//end

        public PickThingsDialogView  panel ()
        {
            return (PickThingsDialogView)m_jpanel;
        }//end

        public void  closePopup (Event event =null )
        {
            Sounds.play("click1");
            close();
            return;
        }//end

    }



