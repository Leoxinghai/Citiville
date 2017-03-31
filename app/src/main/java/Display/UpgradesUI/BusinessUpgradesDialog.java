package Display.UpgradesUI;

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
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class BusinessUpgradesDialog extends UIGenericDialog
    {
        protected MapResource m_building ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyHandler ;

        public  BusinessUpgradesDialog (MapResource param1 ,Function param2 ,Function param3 ,Function param4 )
        {
            this.m_building = param1;
            this.m_finishCheckCB = param2;
            this.m_finishCB = param3;
            this.m_buyHandler = param4;
            super("", "", 0, null, "BusinessUpgradesUI", "", false);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new BusinessUpgradesDialogView(this.m_building, this.m_finishCheckCB, this.m_finishCB, this.m_buyHandler);
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.BIZ_UPGRADES_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            UpgradesCommonAssets.bizupsAssetDict.put("dialogBG",  new m_comObject.upgrades_bg());
            UpgradesCommonAssets.bizupsAssetDict.put("blankPortrait",  m_comObject.upgrades_portrait_missing);
            UpgradesCommonAssets.bizupsAssetDict.put("currentCard",  m_comObject.upgrades_level_card_current);
            UpgradesCommonAssets.bizupsAssetDict.put("upgradeCard",  m_comObject.upgrades_level_card_upgrade);
            UpgradesCommonAssets.bizupsAssetDict.put("upgradeBarBG",  m_comObject.upgrades_bar_bg);
            UpgradesCommonAssets.bizupsAssetDict.put("upgradeBar",  m_comObject.upgrades_bar);
            UpgradesCommonAssets.bizupsAssetDict.put("checkMark",  m_comObject.upgrades_checkmark);
            UpgradesCommonAssets.bizupsAssetDict.put("arrow",  m_comObject.upgrades_mastery_arrow);
            UpgradesCommonAssets.bizupsAssetDict.put("verticleRule",  m_comObject.upgrades_mastery_verticle_rule);
            UpgradesCommonAssets.bizupsAssetDict.put("star",  m_comObject.upgrades_mastery_star_big);
            UpgradesCommonAssets.bizupsAssetDict.put("divider",  m_comObject.upgrades_divider_gradient);
            return UpgradesCommonAssets.bizupsAssetDict;
        }//end

         protected void  showTween ()
        {
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            centerPopup();
            m_content.alpha = 0;

            m_content.scaleY = 1;
            m_content.scaleX = 1;
            TweenLite.to(m_content, TWEEN_TIME, {alpha:1, onComplete:onShowComplete});
            return;
        }//end

         public void  close ()
        {
            this.m_building.setShowUpgradeArrow(false);
            this.m_building.setState(this.m_building.getState());
            dispatchEvent(new Event(Event.CLOSE));
            onHide();
            return;
        }//end

    }


