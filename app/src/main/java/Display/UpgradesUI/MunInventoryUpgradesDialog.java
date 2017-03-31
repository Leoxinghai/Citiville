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
import Classes.gates.*;
import Classes.util.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class MunInventoryUpgradesDialog extends UIGenericDialog
    {
        protected String m_buildingName ;
        protected Array m_inventoryData ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyAllHandler ;
        protected double m_targetObjectId ;
        protected AbstractGate m_gate ;

        public  MunInventoryUpgradesDialog (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 ,AbstractGate param7 =null )
        {
            this.m_gate = param7;
            this.m_buildingName = param1;
            this.m_inventoryData = param2;
            this.m_finishCheckCB = param3;
            this.m_finishCB = param4;
            this.m_buyAllHandler = param5;
            this.m_targetObjectId = param6;
            super("", "", 0, null, "UpgradesUI", "", false);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new MunInventoryUpgradesDialogView(this.m_buildingName, this.m_inventoryData, this.m_finishCheckCB, this.m_finishCB, this.m_buyAllHandler, this.m_targetObjectId, this.m_gate);
        }//end

         protected Array  getAssetDependencies ()
        {
            Item _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_1 =.get(DelayedAssetLoader.UPGRADES_ASSETS ,DelayedAssetLoader.BUILDABLE_ASSETS) ;
            int _loc_2 =0;
            while (_loc_2 < this.m_inventoryData.length())
            {

                if (this.m_inventoryData.get(_loc_2).amountNeeded)
                {
                    _loc_3 =(Item) this.m_inventoryData.get(_loc_2).item;
                    _loc_4 = _loc_3.icon;
                    _loc_1 = _loc_1.concat(_loc_4);
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

         protected void  loadAssets ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.getAssetDependencies().size(); i0++)
            {
            	_loc_1 = this.getAssetDependencies().get(i0);

                if (_loc_1.indexOf("http:") == 0 || _loc_1.indexOf("file:") == 0)
                {
                    LoadingManager.load(_loc_1, this.onIconLoad, LoadingManager.PRIORITY_HIGH, this.onIconFail);
                    continue;
                }
                Global.delayedAssets.get(_loc_1, makeAssets);
            }
            return;
        }//end

        protected void  onIconLoad (Event event )
        {
            _loc_2 =(LoaderInfo) event.target;
            if (_loc_2)
            {
                makeAssets(_loc_2.content, _loc_2.loaderURL);
            }
            return;
        }//end

        protected void  onIconFail (Event event )
        {
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            m_comObject = m_assetDependencies.get(DelayedAssetLoader.UPGRADES_ASSETS);
            UpgradesCommonAssets.assetDict.put("dialogBG",  new m_comObject.upgrades_bg());
            UpgradesCommonAssets.assetDict.put("checkMark",  m_comObject.upgrades_checkmark);
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
            UpgradesCommonAssets.assetDict.put("blue_btn",  m_comObject.blue_btn);
            _loc_1 = DelayedAssetLoader.BUILDABLE_ASSETS,.BUILDABLE_ASSETS]);
            UpgradesCommonAssets.assetDict.put("buildables_item",  _loc_1.buildables_item);
            UpgradesCommonAssets.assetDict.put("buildables_bg",  _loc_1.buildables_bg);
            UpgradesCommonAssets.assetDict.put("buildables_check",  _loc_1.buildables_check);
            UpgradesCommonAssets.assetDict.put("buildables_item",  _loc_1.buildables_item);
            UpgradesCommonAssets.assetDict.put("buildables_wishlistIcon",  _loc_1.buildables_wishlistIcon);
            return UpgradesCommonAssets.assetDict;
        }//end

        public void  activateFinishButton ()
        {
            _loc_1 = (MunInventoryUpgradesDialogView)m_jpanel
            if (_loc_1)
            {
                _loc_1.rebuildButtons();
            }
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



