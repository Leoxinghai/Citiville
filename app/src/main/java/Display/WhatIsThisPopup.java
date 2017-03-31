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

import Classes.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;

    public class WhatIsThisPopup extends SWFDialog
    {
        private MovieClip m_window ;
        private Loader m_iconLoader ;
        private String m_message ;
        private Function m_closeCallback ;
        private GenericButton m_accept ;
        private GenericButton m_reject ;
        private XML m_item ;
        public static  int YES =1;
        public static  int NO =0;
        public static boolean describeDialogOpen =false ;

        public  WhatIsThisPopup (XML param1 ,Function param2 )
        {
            this.m_item = param1;
            m_dialogAsset = "assets/dialogs/FV_mysteryBox.swf";
            describeDialogOpen = true;
            if (param2 != null)
            {
                this.m_closeCallback = param2;
                addEventListener(GenericPopupEvent.SELECTED, param2);
            }
            return;
        }//end

        protected boolean  canBuy ()
        {
            boolean _loc_1 =false ;
            switch(this.m_item.@unlock.toString())
            {
                case Item.UNLOCK_LEVEL:
                {
                    if (Global.player.checkLevel(this.m_item.requiredLevel))
                    {
                        _loc_1 = true;
                    }
                    break;
                }
                case Item.UNLOCK_NEIGHBOR:
                {
                    if (Global.player.checkNeighbors(this.m_item.minNeighbors))
                    {
                        _loc_1 = true;
                    }
                    break;
                }
                case Item.UNLOCK_PERMITS:
                {
                    if (ExpansionManager.instance.hasEnoughPermits())
                    {
                        _loc_1 = true;
                    }
                    break;
                }
                default:
                {
                    _loc_1 = true;
                    break;
                    break;
                }
            }
            return _loc_1;
        }//end

         protected void  onLoadComplete ()
        {
            Array _loc_1 =new Array();
            this.m_window =(MovieClip) m_loader.content;
            if (this.canBuy())
            {
                this.m_accept = new GenericButton(this.m_window.mysteryBox_mc.buy_bt, this.onAccept);
                this.m_accept.text = ZLoc.t("Dialogs", "Buy");
            }
            else
            {
                this.m_window.mysteryBox_mc.buy_bt.visible = false;
            }
            this.m_reject = new GenericButton(this.m_window.mysteryBox_mc.cancel_bt, this.onCancel);
            this.m_reject.text = ZLoc.t("Dialogs", "Cancel");
            _loc_2 = this.m_item.@name.toString();
            _loc_2 = _loc_2.replace("_cash", "");
            this.m_window.mysteryBox_mc.title_tf.text = ZLoc.t("Dialogs", _loc_2 + "Title");
            this.m_window.mysteryBox_mc.body_tf.text = ZLoc.t("Dialogs", _loc_2 + "Message");
            addChild(this.m_window);
            return;
        }//end

        protected void  onAccept (MouseEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES));
            if (this.m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, this.m_closeCallback);
            }
            this.close();
            return;
        }//end

        protected void  onCancel (MouseEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, NO));
            if (this.m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, this.m_closeCallback);
            }
            this.close();
            return;
        }//end

         public void  close ()
        {
            describeDialogOpen = false;
            super.close();
            return;
        }//end

    }



