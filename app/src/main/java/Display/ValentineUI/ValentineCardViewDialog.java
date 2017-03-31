package Display.ValentineUI;

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
import Classes.orders.Valentines2011.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class ValentineCardViewDialog extends GenericDialog
    {
        public Array m_cardData ;
        public Admirer m_senderID ;
        protected JWindow m_secondWindow ;
        protected JPanel m_secondPanel ;
        protected Sprite m_firstHolder ;
        protected Sprite m_secondHolder ;
        public static Dictionary assetDict ;

        public  ValentineCardViewDialog (Array param1 ,Admirer param2 )
        {
            this.m_cardData = param1;
            this.m_senderID = param2;
            m_lightbox = true;
            super("", "", GenericDialogView.TYPE_CUSTOM_OK, this.shareThanks, "ValUIView", "", true, 0, "", null, ZLoc.t("Dialogs", "ValUIView_btn"));
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.VALENTINES_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            assetDict = new Dictionary();
            assetDict.put("cardData",  this.m_cardData);
            assetDict.put("admirer",  this.m_senderID);
            assetDict.put("market_next_down",  new m_comObject.market_next_down());
            assetDict.put("market_next_over",  new m_comObject.market_next_over());
            assetDict.put("market_next_up",  new m_comObject.market_next_up());
            assetDict.put("market_prev_down",  new m_comObject.market_prev_down());
            assetDict.put("market_prev_over",  new m_comObject.market_prev_over());
            assetDict.put("market_prev_up",  new m_comObject.market_prev_up());
            assetDict.put("noportrait",  m_comObject.valentinesDay_admirerCard_blank);
            assetDict.put("valentinesDay_achievementStamp",  m_comObject.valentinesDay_achievementStamp);
            assetDict.put("dialog_bg",  new m_comObject.valentinesDay_viewerBG());
            return assetDict;
        }//end

        protected void  shareThanks (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                if (this.m_senderID.uid != Player.FAKE_USER_ID_STRING)
                {
                    Global.world.viralMgr.vdaySendCardThankYou(this.m_senderID.uid);
                }
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDVIEWER, "share");
            return;
        }//end

         protected void  handleShowLightbox ()
        {
            if (m_lightbox)
            {
                Global.ui.displayLightbox(true, UI.MASK_ALL_UI);
            }
            return;
        }//end

        protected JPanel  createSecondPanel (int param1 =0)
        {
            JPanel jp ;
            DisplayObject bgAsset ;
            Loader icon ;
            ind = param1;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            bgAsset =(DisplayObject) new assetDict.get("valentinesDay_achievementStamp");
            curCard = this.m_cardData.get(ind);
            icons = curCard.findMatchingAchievementIcons();
            if (icons.length == 0)
            {
                return jp;
            }
            iconUrl = .get((icons.length-1));
            icon =LoadingManager .load (Global .getAssetURL (iconUrl ),void  (Event event )
            {
                jp.removeAll();
                AssetPane _loc_2 =new AssetPane(icon.content );
                ASwingHelper.setEasyBorder(_loc_2, (bgAsset.height - icon.content.height) / 2);
                jp.append(_loc_2);
                ASwingHelper.prepare(jp.parent);
                return;
            }//end
            );
            ASwingHelper.setSizedBackground(jp, bgAsset);
            return jp;
        }//end

        protected void  changeAchievement (GenericObjectEvent event )
        {
            this.m_secondWindow.hide();
            this.m_secondWindow.removeAll();
            this.m_secondPanel = this.createSecondPanel(event.obj.index);
            this.m_secondWindow.setContentPane(this.m_secondPanel);
            ASwingHelper.prepare(this.m_secondWindow);
            this.m_secondWindow.show();
            return;
        }//end

         protected void  init ()
        {
            m_holder = new Sprite();
            this.addChild(m_holder);
            Sprite _loc_1 =new Sprite ();
            m_holder.addChild(_loc_1);
            m_jwindow = new JWindow(_loc_1);
            m_content = m_holder;
            m_content.addEventListener(GenericObjectEvent.REFRESH_ACHIEVEMENT, this.changeAchievement, false, 0, true);
            m_content.addEventListener(GenericPopupEvent.SELECTED, processSelection, false, 0, true);
            m_content.addEventListener(UIEvent.REFRESH_DIALOG, onRefreshDialog, false, 0, true);
            m_content.addEventListener("close", closeMe, false, 0, true);
            return;
        }//end

         protected void  finalizeAndShow ()
        {
            ASwingHelper.prepare(m_jpanel);
            m_jwindow.setContentPane(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            if (m_assetBG && m_title == "" && m_assetBG.height > m_jwindow.getHeight())
            {
                m_jwindow.setPreferredHeight((m_assetBG.height + 1));
                ASwingHelper.prepare(m_jwindow);
            }
            _loc_1 = m_jwindow.getWidth();
            _loc_2 = m_jwindow.getHeight();
            m_jwindow.show();
            this.m_secondHolder = new Sprite();
            m_holder.addChild(this.m_secondHolder);
            this.m_secondHolder.x = 425;
            this.m_secondHolder.y = 320;
            this.m_secondPanel = this.createSecondPanel((assetDict.get("cardData").length - 1));
            this.m_secondWindow = new JWindow(this.m_secondHolder);
            ASwingHelper.prepare(this.m_secondPanel);
            this.m_secondWindow.setContentPane(this.m_secondPanel);
            ASwingHelper.prepare(this.m_secondWindow);
            this.m_secondWindow.show();
            m_holder.width = _loc_1;
            m_holder.height = _loc_2;
            onDialogInitialized();
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new ValentineCardViewDialogView(param1, m_message, m_title, m_type, m_callback, "", 0, "", null, ZLoc.t("Dialogs", "ValUIView_btn"));
        }//end

    }



