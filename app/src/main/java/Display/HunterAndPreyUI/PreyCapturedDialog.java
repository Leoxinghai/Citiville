package Display.HunterAndPreyUI;

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
import Engine.Managers.*;
import Modules.bandits.*;
import Modules.workers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class PreyCapturedDialog extends GenericDialog
    {
        protected PreyData m_bandit ;
        protected Player m_cop ;
        protected Loader m_iconLoader ;
        protected Loader m_rewardLoader ;
        protected Object m_comTabObject ;
        protected int m_numAssetLoads =3;
        protected int m_imagesLoaded =0;
        protected boolean m_itemOnly ;
        protected String m_groupId ="";

        public  PreyCapturedDialog (String param1 ,int param2 ,HunterData param3 ,boolean param4 =false ,boolean param5 =false ,Function param6 =null ,boolean param7 =true ,Function param8 =null )
        {
            String _loc_18 =null ;
            this.m_groupId = param1;
            Object _loc_9 ={id param2 };
            this.m_bandit = PreyManager.getPreyData(this.m_groupId, _loc_9);
            String _loc_10 ="";
            if (PreyManager.isUsingResource(param1))
            {
                _loc_18 = "-1";
                if (param3)
                {
                    _loc_18 = param3.getZID();
                }
                if (_loc_18.indexOf("i") >= 0)
                {
                    _loc_18 = _loc_18.substr((_loc_18.indexOf("i") + 1));
                }
                if (_loc_18.indexOf("-") >= 0)
                {
                    _loc_18 = "-1";
                }
                this.m_cop = Global.player.findFriendById(_loc_18);
            }
            else
            {
                _loc_10 = "_superhero";
            }
            this.m_itemOnly = param5;
            _loc_11 = ZLoc.t("Dialogs",this.m_groupId+"_Captured_buttonText");
            _loc_12 = this.m_groupId +"_Captured";
            String _loc_13 ="";
            _loc_14 = PreyManager.getNumPreyUntilUpgrade(this.m_groupId);
            _loc_15 = this.m_cop ? (this.m_cop.firstName) : (ZLoc.t("Main", "FakeFriendName"));
            if (_loc_14 > 0 && !param5)
            {
                _loc_13 = ZLoc.t("Dialogs", this.m_groupId + "_Captured_message" + _loc_10, {friendName:_loc_15, banditName:this.m_bandit.name, numBandits:_loc_14});
            }
            else if (_loc_14 == 0 && !param5)
            {
                _loc_13 = ZLoc.t("Dialogs", this.m_groupId + "_Captured_message_readyForUpgrade" + _loc_10, {friendName:_loc_15, banditName:this.m_bandit.name});
            }
            else if (_loc_14 == -1 && !param5)
            {
                _loc_13 = ZLoc.t("Dialogs", this.m_groupId + "_Captured_message_noMoreUpgrades" + _loc_10, {friendName:_loc_15, banditName:this.m_bandit.name});
            }
            else if (param5)
            {
                _loc_13 = ZLoc.t("Dialogs", this.m_groupId + "_Captured_item_message" + _loc_10, {friendName:_loc_15, banditName:this.m_bandit.name});
            }
            if (param4 && !param5)
            {
                _loc_13 = _loc_13 + (" " + ZLoc.t("Dialogs", this.m_groupId + "_Captured_item_append_message"));
            }
            _loc_16 = GenericDialogView.TYPE_CUSTOM_OK;
            _loc_17 = param6;
            if (param5)
            {
                _loc_16 = GenericDialogView.TYPE_OK;
                _loc_17 = null;
            }
            super(_loc_13, _loc_12, _loc_16, _loc_17, _loc_12, "", param7, 0, "", param8, _loc_11);
            return;
        }//end

         protected void  loadAssets ()
        {
            if (this.m_bandit.getRewardItems().length > 0)
            {
                this.m_numAssetLoads++;
            }
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.TABBED_ASSETS, this.makeTabAssets);
            if (this.m_bandit.portraitUrl)
            {
                this.m_iconLoader = LoadingManager.load(Global.getAssetURL(this.m_bandit.caughtUrl), this.onAssetsLoaded, LoadingManager.PRIORITY_HIGH);
            }
            if (this.m_bandit.getRewardItems().length > 0)
            {
                this.m_rewardLoader = LoadingManager.load(Global.gameSettings().getImageByName(this.m_bandit.getRewardItems().get(0), "icon"), this.onAssetsLoaded, LoadingManager.PRIORITY_HIGH);
            }
            return;
        }//end

        protected void  makeTabAssets (DisplayObject param1 ,String param2 )
        {
            this.m_comTabObject = param1;
            this.onAssetsLoaded();
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_numAssetLoads)
            {
                return;
            }
            super.onAssetsLoaded(event);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg", (DisplayObject) new m_comObject.dialog_bg());
            m_assetBG = _loc_1.get("dialog_bg");
            if (this.m_iconLoader)
            {
                _loc_1.put("banditIcon",  this.m_iconLoader.content);
            }
            _loc_1.put("rewardCard_unlocked",  this.m_comTabObject.police_rewardCard_unlocked);
            _loc_1.put("police_slot_unfilled",  this.m_comTabObject.police_slot_unfilled);
            if (this.m_rewardLoader)
            {
                _loc_1.put("itemReward",  this.m_rewardLoader.content);
            }
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PreyCapturedDialogView _loc_2 =new PreyCapturedDialogView(param1 ,this.m_bandit ,this.m_cop ,this.m_itemOnly ,m_message ,m_dialogTitle ,m_callback ,m_SkipCallback ,m_customOk );
            return _loc_2;
        }//end

    }



