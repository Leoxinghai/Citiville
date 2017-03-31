package Display;

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
import Engine.Managers.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class MOTD extends SWFDialog
    {
        private MovieClip m_window ;
        private Loader m_iconLoader ;
        private GenericButton m_accept ;
        private boolean m_popGiftDialog =false ;

        public  MOTD ()
        {
            m_dialogAsset = "assets/dialogs/TI_GenericIconPopUp.swf";
            super(true);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            this.m_window =(MovieClip) m_loader.content;
            this.m_window.callOutBox_mc.bouquets_mc.visible = false;
            this.m_window.callOutBox_mc.share_bt.visible = false;
            this.m_window.callOutBox_mc.cancel_bt.visible = false;
            this.m_window.callOutBox_mc.callOut_tf.text = ZLoc.t(Global.gameSettings().getString("motdText"), null);
            this.m_accept = new GenericButton(this.m_window.callOutBox_mc.okay_bt, this.onOkClick);
            this.m_accept.text = ZLoc.t("Dialogs", "OkButton");
            this.m_window.callOutBox_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onClose);
            this.m_iconLoader = LoadingManager.load(Global.getAssetURL(Global.gameSettings().getString("motdIcon")), this.onIconLoaded);
            addChild(this.m_window);
            StatsManager.milestone("decoration_md");
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            _loc_2 = this.m_window.callOutBox_mc.icon_mc ;
            int _loc_4 =1;
            _loc_2.scaleY = 1;
            _loc_2.scaleX = _loc_4;
            while (_loc_2.numChildren)
            {

                _loc_2.removeChildAt(0);
            }
            _loc_3 = this.m_iconLoader.content ;
            _loc_3.x = (-_loc_3.width) / 2;
            _loc_3.y = (-_loc_3.height) / 2;
            _loc_2.addChild(_loc_3);
            return;
        }//end

        private void  onOkClick (MouseEvent event )
        {
            _loc_2 =Global.gameSettings().getString("motdRedirect");
            if (_loc_2 == "miniMarket")
            {
                UI.displayMiniMarketDialog(.get("flowershed", "flowershed_cash"), "motd", "building");
            }
            else if (_loc_2 == "giftbox")
            {
                this.m_popGiftDialog = true;
            }
            else if (_loc_2 != "")
            {
                UI.displayMarketDialog("motd", _loc_2);
            }
            this.onClose(event);
            return;
        }//end

        protected void  onClose (MouseEvent event )
        {
            this.m_window.callOutBox_mc.okay_bt.removeEventListener(MouseEvent.CLICK, this.onOkClick);
            this.m_window.callOutBox_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onClose);
            GameTransactionManager.addTransaction(new TSawMOTD());
            if (this.m_popGiftDialog)
            {
                UI.displayGiftDialog();
            }
            event.stopPropagation();
            close();
            StatsManager.milestone("invite_complete");
            return;
        }//end

    }



