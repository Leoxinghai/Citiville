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
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class TakePhotoPopup extends SWFDialog
    {
        public MovieClip m_window ;
        private BitmapData m_bmp ;
        private GenericButton m_accept ;

        public  TakePhotoPopup (BitmapData param1 )
        {
            m_dialogAsset = "assets/dialogs/FV_CameraPopUp.swf";
            this.m_bmp = param1;
            StatsManager.count("take_photo_popup", "view");
            return;
        }//end

         protected void  onLoadComplete ()
        {
            Array _loc_1 =new Array();
            this.m_window =(MovieClip) m_loader.content;
            Bitmap _loc_2 =new Bitmap(this.m_bmp );
            _loc_2.width = 198.5;
            _loc_2.height = 197.2;
            this.m_window.popupBox_mc.caption_tf.text = "";
            this.m_window.popupBox_mc.snapshot_mc.addChild(_loc_2);
            this.m_accept = new GenericButton(this.m_window.popupBox_mc.accept_bt, this.onAccept);
            this.m_accept.text = ZLoc.t("Dialogs", "Accept");
            this.m_window.popupBox_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCancel);
            addChild(this.m_window);
            return;
        }//end

        protected void  onAccept (MouseEvent event )
        {
            String _loc_3 =null ;
            _loc_2 = this.m_window.popupBox_mc.caption_tf.text ;
            dispatchEvent(new TakePhotoEvent(TakePhotoEvent.ACCEPT, _loc_2));
            this.removeListeners();
            StatsManager.count("take_photo_popup", "accept_click");
            if (Global.isVisiting() == true)
            {
                _loc_3 = Global.getVisiting();
            }
            else
            {
                _loc_3 = Global.player.uid;
            }
            GameTransactionManager.addTransaction(new TTakePhoto(_loc_3));
            return;
        }//end

        protected void  onCancel (MouseEvent event )
        {
            dispatchEvent(new TakePhotoEvent(TakePhotoEvent.CANCEL, ""));
            this.removeListeners();
            StatsManager.count("take_photo_popup", "cancel_click");
            return;
        }//end

        private void  removeListeners ()
        {
            this.m_window.popupBox_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCancel);
            close();
            StatsManager.count("take_photo_popup", "cancel_click");
            return;
        }//end

    }



