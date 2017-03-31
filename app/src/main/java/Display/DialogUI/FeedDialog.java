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
import Classes.virals.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;

    public class FeedDialog extends GenericDialog
    {
        protected String m_action ;
        protected String m_caption ;
        protected Loader m_iconLoader ;
        protected DisplayObject m_viralIcon ;
        protected String m_viralType ;
        protected Object m_viralData ;
        protected FeedDialogView m_dialogView ;
        protected Function m_successCallback ;
        protected String m_targetId ;

        public  FeedDialog (String param1 ,Object param2 ,String param3 ,Function param4 =null ,Function param5 =null )
        {
            if (Utilities.isFullScreen())
            {
                Utilities.toggleFullScreen();
            }
            this.m_viralType = param1;
            if (this.m_viralType == ViralType.QUEST_COMPLETE)
            {
                this.m_viralType = param2.quest;
            }
            this.m_viralData = param2;
            this.m_targetId = param3;
            m_viralData.put("user", ZLoc.tn(Global.player.firstName,Global.player.gender));
            this.m_successCallback = param4;
            String _loc_6 ="PostProfile";
            if (param3 && Global.player.uid != param3)
            {
                _loc_6 = "PostFriendsProfile";
            }
            super("", "feed_dialog", 0, param5, _loc_6);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Bitmap _loc_2 =null ;
            AnimatedBitmap _loc_3 =null ;
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg_feed());
            _loc_1.put("dialog_icon", this.m_viralIcon);
            if (_loc_1.get("dialog_icon") == null)
            {
                _loc_2 =(Bitmap) new m_comObject.loader_90x90();
                _loc_3 = new AutoAnimatedBitmap(_loc_2.bitmapData, 12, 90, 90, 24);
                _loc_1.put("dialog_icon", _loc_3);
            }
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.m_dialogView = new FeedDialogView(param1, this.m_viralType, this.m_action, m_message, this.m_caption, m_title, ZLoc.t("Dialogs", "FeedComment"), this.m_targetId, this.shareMessage, GenericDialogView.TYPE_SHARE, m_callback);
            return this.m_dialogView;
        }//end

         protected void  loadAssets ()
        {
            super.loadAssets();
            Global.creatives.registerOnLoadCallback(this.onCreativeLoaded);
            return;
        }//end

        private void  onCreativeLoaded ()
        {
            _loc_1 = Global.creatives;
            _loc_2 = _loc_1.getFeedName(this.m_viralType,this.m_viralData);
            _loc_3 = _loc_1.getFeed(_loc_2,this.m_viralData);
            this.m_action = _loc_3.attachmentName;
            m_message = _loc_3.attachmentDescription;
            this.m_caption = _loc_3.attachmentCaption;
            this.m_iconLoader = LoadingManager.load(_loc_3.mediaURL, this.onIconLoad);
            this.onAssetsLoaded();
            return;
        }//end

        private void  onIconLoad (Event event =null )
        {
            this.m_viralIcon = this.m_iconLoader.content;
            _loc_2 = (FeedDialogView)m_jpanel
            _loc_2.setIcon(this.m_viralIcon);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            if (m_message == null || m_comObject == null || this.m_action == null)
            {
                return;
            }
            _loc_2 = this.createAssetDict ();
            m_jpanel = this.createDialogView(_loc_2);
            finalizeAndShow();
            this.setFocus();
            return;
        }//end

        private void  shareMessage (String param1 )
        {
            this.m_successCallback(param1);
            return;
        }//end

        protected void  setFocus ()
        {
            _loc_1 = (FeedDialogView)m_jpanel
            _loc_2 = _loc_1.m_textField.getTextField();
            Global.stage.focus = _loc_2;
            setTimeout(_loc_2.setSelection, 250, 0, _loc_2.text.length());
            ASwingHelper.prepare(m_jwindow);
            return;
        }//end

         protected String  getDialogStatsTrackingString ()
        {
            if (this.m_targetId == Global.player.uid)
            {
                return StatsKingdomType.FEED;
            }
            return StatsKingdomType.WALL_TO_WALL;
        }//end

         public void  setupDialogStatsTracking ()
        {
            super.setupDialogStatsTracking();
            countDialogAction(StatsPhylumType.FEED_DIALOG_OPEN, 1, this.m_viralType);
            return;
        }//end

    }




