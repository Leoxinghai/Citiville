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

    public class GiftNotify extends SWFDialog
    {
        private MovieClip m_window ;
        private int m_numGifts ;
        private GenericButton m_accept ;
        private GenericButton m_reject ;

        public  GiftNotify (int param1 ,boolean param2 =true )
        {
            m_dialogAsset = "assets/dialogs/FV_GiftNotify.swf";
            this.m_numGifts = param1;
            super(param2);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            this.m_window =(MovieClip) m_loader.content;
            this.m_window.giftNotify_mc.giftNotify_tf.text = ZLoc.t("Dialogs", "GiftsWaiting", {num:this.m_numGifts});
            this.m_window.giftNotify_mc.lookAtGifts_tf.text = ZLoc.t("Dialogs", "SellGifts");
            _loc_1 =Global.gameSettings().getInt("maxGiftLimit");
            if (this.m_numGifts >= _loc_1)
            {
                this.m_window.giftNotify_mc.maxGifts_tf.text = ZLoc.t("Dialogs", "MaxGifts", {max:_loc_1});
                this.m_window.giftNotify_mc.maxGifts_tf.visible = true;
            }
            else
            {
                this.m_window.giftNotify_mc.maxGifts_tf.visible = false;
            }
            this.m_accept = new GenericButton(this.m_window.giftNotify_mc.accept_bt, this.onAcceptClick);
            this.m_accept.text = ZLoc.t("Dialogs", "Accept");
            this.m_reject = new GenericButton(this.m_window.giftNotify_mc.cancel_bt, this.onCloseClick);
            this.m_reject.text = ZLoc.t("Dialogs", "Cancel");
            StatsManager.count("flash_giftNotify", "automatic", "viewInitial");
            addChild(this.m_window);
            StatsManager.milestone("gifts_waiting_md");
            return;
        }//end

        private void  onAcceptClick (MouseEvent event )
        {
            this.onCloseClick(event);
            UI.displayGiftDialog();
            StatsManager.count("flash_giftNotify", "automatic", "accept");
            StatsManager.milestone("gift_box_md");
            return;
        }//end

        private void  onCloseClick (MouseEvent event )
        {
            event.stopPropagation();
            StatsManager.count("flash_giftNotify", "automatic", "close");
            Global.player.options.noGiftNotify = GlobalEngine.getTimer();
            GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
            close();
            return;
        }//end

    }


