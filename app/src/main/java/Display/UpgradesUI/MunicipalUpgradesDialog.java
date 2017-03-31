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

    public class MunicipalUpgradesDialog extends UIGenericDialog
    {
        protected String m_buildingName ;
        protected Array m_crewData ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyAllHandler ;
        protected double m_targetObjectId ;

        public  MunicipalUpgradesDialog (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 )
        {
            this.m_buildingName = param1;
            this.m_crewData = param2;
            this.m_finishCheckCB = param3;
            this.m_finishCB = param4;
            this.m_buyAllHandler = param5;
            this.m_targetObjectId = param6;
            super("", "", 0, null, "UpgradesUI", "", false);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new MunicipalUpgradesDialogView(this.m_buildingName, this.m_crewData, this.m_finishCheckCB, this.m_finishCB, this.m_buyAllHandler, this.m_targetObjectId);
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.UPGRADES_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            UpgradesCommonAssets.assetDict.put("dialogBG",  new m_comObject.upgrades_bg());
            UpgradesCommonAssets.assetDict.put("checkMark",  m_comObject.upgrades_checkmark);
            UpgradesCommonAssets.assetDict.put("crewIcon",  m_comObject.upgrades_crewIcon);
            UpgradesCommonAssets.assetDict.put("blankPortrait",  m_comObject.upgrades_portrait_missing);
            UpgradesCommonAssets.assetDict.put("scrollBorder",  m_comObject.upgrades_scrollBorder);
            UpgradesCommonAssets.assetDict.put("scrollCircle",  m_comObject.upgrades_scrollCircle);
            UpgradesCommonAssets.assetDict.put("upgradeArrow",  m_comObject.upgrades_upgradeArrow);
            UpgradesCommonAssets.assetDict.put("manilaCard",  m_comObject.upgrades_manilaCard);
            UpgradesCommonAssets.assetDict.put("up_disabled",  m_comObject.upgrades_up_disabled);
            UpgradesCommonAssets.assetDict.put("up_over",  m_comObject.upgrades_up_over);
            UpgradesCommonAssets.assetDict.put("up_up",  m_comObject.upgrades_up_up);
            UpgradesCommonAssets.assetDict.put("down_disabled",  m_comObject.upgrades_down_disabled);
            UpgradesCommonAssets.assetDict.put("down_over",  m_comObject.upgrades_down_over);
            UpgradesCommonAssets.assetDict.put("down_up",  m_comObject.upgrades_down_up);
            return UpgradesCommonAssets.assetDict;
        }//end

        public void  activateFinishButton ()
        {
            ((MunicipalUpgradesDialogView)m_jpanel).rebuildButtons();
            return;
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
            _loc_1 =Global.world.getObjectById(this.m_targetObjectId )as MapResource ;
            if (_loc_1)
            {
                _loc_1.setShowUpgradeArrow(false);
                _loc_1.setState(_loc_1.getState());
            }
            dispatchEvent(new Event(Event.CLOSE));
            onHide();
            return;
        }//end

    }



