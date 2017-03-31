package Modules.streetFair;

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
import Display.DialogUI.*;
import Mechanics.*;
//import flash.display.*;
//import flash.utils.*;

    public class StreetFairDialog extends GenericDialog
    {
        private MechanicMapResource m_spawner ;
        protected StreetFairDialogView dialogView ;
        protected String m_ticketTheme ;
        protected String m_swfToPull ;

        public  StreetFairDialog (MechanicMapResource param1 ,String param2 ,String param3 =null )
        {
            MechanicConfigData _loc_4 =null ;
            if (param1 !=null)
            {
                _loc_4 = param1.getMechanicConfig("dialogGenerator", MechanicManager.PLAY);
            }
            this.m_swfToPull = param2 ? (param2) : (_loc_4.params.get("swfToPullFrom"));
            this.m_ticketTheme = param3 ? (param3) : (_loc_4.params.get("ticketTheme"));
            this.m_spawner = param1;
            super("", "Dialog1", 0, null, "ticket_booth_street_" + (param3 ? (param3) : (_loc_4.params.get("ticketTheme"))));
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(this.m_swfToPull);
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.dialogView = new StreetFairDialogView(this.m_ticketTheme, param1, m_message, m_dialogTitle, m_type, m_callback, m_icon, m_iconPos, m_feedShareViralType, m_SkipCallback, m_customOk, m_relativeIcon);
            return this.dialogView;
        }//end

         public void  onShowComplete ()
        {
            super.onShowComplete();
            this.dialogView.checkFeeds();
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            m_assetDependencies.get(_loc_2 = this.m_swfToPull) ;
            _loc_1.put("streetFair_bg", (DisplayObject) new _loc_2.streetFair_bg());
            _loc_1.put("streetFair_checkbox",  _loc_2.streetFair_checkbox);
            _loc_1.put("streetFair_checkmark",  _loc_2.streetFair_checkmark);
            _loc_1.put("streetFair_hRule-09",  _loc_2.streetFair_hRule09);
            _loc_1.put("streetFair_meter",  _loc_2.streetFair_meter);
            _loc_1.put("streetFair_npcSpeechBubble",  _loc_2.streetFair_npcSpeechBubble);
            _loc_1.put("streetFair_subBox",  _loc_2.streetFair_subBox);
            _loc_1.put("streetFair_ticket_big",  _loc_2.streetFair_ticket_big);
            _loc_1.put("streetFair_ticket_button",  _loc_2.streetFair_ticket_button);
            _loc_1.put("streetFair_ticket_meter",  _loc_2.streetFair_ticket_meter);
            _loc_1.put("itemCard",  _loc_2.itemCard);
            _loc_1.put("streetFair_card_ticket",  _loc_2.streetFair_card_ticket);
            return _loc_1;
        }//end

    }



