package Display.RenameUI;

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
import Classes.MiniQuest.*;
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.franchise.*;
import Modules.franchise.data.*;
import Modules.franchise.transactions.*;
import Modules.stats.experiments.*;
import Transactions.*;
import root.Global;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;

    public class RenameDialog extends GenericDialog
    {
        protected String m_editingName ;
        protected String m_editingType ;
        protected boolean m_signatureRequired =true ;

        public  RenameDialog (MechanicMapResource param1)
        {
            _loc_2 = ZLoc.t("Dialogs","RenameUI_message");
            String _loc_3 ="RenameUI";
            _loc_4 = GenericDialogView.TYPE_OK;
            Function _loc_5 =null ;
            String _loc_6 ="RenameUI";
            boolean _loc_7 =true ;
            String _loc_8 ="";
            int _loc_9 =0;
            String _loc_10 ="";
            Function _loc_11 =null ;
            String _loc_12 ="";
            this.m_editingName = null;
            this.m_editingType = null;
            this.m_signatureRequired = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_UPGRADE_CLERKOFFICE) != ExperimentDefinitions.CLERK_UPGRADE_ENABLED;
            if (!this.m_signatureRequired)
            {
                _loc_2 = "";
            }
            super(_loc_2, _loc_3, _loc_4, _loc_5, _loc_6, _loc_8, _loc_7, _loc_9, _loc_10, _loc_11, _loc_12);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary(true );
            _loc_1.put("dialog_bg",  new m_comObject.dialog_rename_bg());
            _loc_1.put("dialog_div", m_comObject.dialog_rename_divider);
            _loc_1.put("btn_up_normal",  m_comObject.gridlist_nav_up_normal);
            _loc_1.put("btn_up_over",  m_comObject.gridlist_nav_up_over);
            _loc_1.put("btn_up_down",  m_comObject.gridlist_nav_up_down);
            _loc_1.put("btn_down_normal",  m_comObject.gridlist_nav_down_normal);
            _loc_1.put("btn_down_over",  m_comObject.gridlist_nav_down_over);
            _loc_1.put("btn_down_down",  m_comObject.gridlist_nav_down_down);
            _loc_1.put("cell_bg",  m_comObject.cell_bg);
            _loc_1.put("cell_bg_alt",  m_comObject.cell_bg_alt);
            _loc_1.put("pic_cashBtn",  EmbeddedArt.icon_cash_big);
            _loc_1.put("pic_checkGreen",  m_comObject.checkmark_green);
            _loc_1.put("pic_dot",  m_comObject.gridList_page_dot);
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.MARKET_ASSETS) ;
            _loc_1.put("card_available_unselected",  _loc_2.marketItem);
            _loc_1.put("card_available_selected",  _loc_2.marketItem);
            _loc_1.put("icon_cash",  _loc_2.cash);
            _loc_1.put("icon_coins",  _loc_2.coin);
            _loc_1.put("pop_lock",  _loc_2.lockedArea);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            _loc_2 = this.createCityModel ();
            _loc_3 = this.createBusinessModel ();
            RenameDialogView _loc_4 =new RenameDialogView(param1 ,_loc_2 ,_loc_3 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback ,m_customOk );
            this.refresh(_loc_4);
            _loc_4.addEventListener(RenameEvent.FINISH_CLICK, this.onFinishClick);
            _loc_4.addEventListener(RenameEvent.PROPOSE_CLICK, this.onProposeClick);
            _loc_4.addEventListener(RenameEvent.PURCHASE_CLICK, this.onPurchaseClick);
            _loc_4.addEventListener(RenameEvent.REQUEST_CLICK, this.onRequestClick);
            return _loc_4;
        }//end

        protected void  refresh (RenameDialogView param1 )
        {
            param1.refreshCityModel(this.createCityModel());
            param1.refreshBusinessModel(this.createBusinessModel());
            ASwingHelper.prepare(m_jpanel);
            ASwingHelper.prepare(m_jwindow);
            return;
        }//end

        protected Array  createBusinessModel ()
        {
            OwnedFranchiseData _loc_3 =null ;
            Item _loc_4 =null ;
            Object _loc_5 =null ;
            Array _loc_1 =new Array ();
            _loc_2 =Global.gameSettings().getItemByName(RequestItemType.SIGNATURE );
            for(int i0 =0; i0< Global.franchiseManager.model.getOwnedFranchises().size();i++)
            {

                _loc_4 = Global.gameSettings().getItemByName(_loc_3.franchiseType);
                if (_loc_4 == null || !_loc_4.isRenameable)
                {
                    continue;
                }
                _loc_5 = new Object();
                _loc_5.item = _loc_3.franchiseType;
                _loc_5.itemName = Global.franchiseManager.getFranchiseName(_loc_3.franchiseType, Global.player.uid);
                _loc_5.pendingName = Global.franchiseManager.getPendingName(_loc_3.franchiseType, Global.player.uid);
                _loc_5.requiredCount = Global.franchiseManager.getSignatureCount(_loc_3.franchiseType);
                _loc_5.requiredMax = Global.franchiseManager.getRequiredSignatures(_loc_3.franchiseType);
                _loc_5.requiredCost = _loc_2.cash;
                _loc_5.state = this.computeState(_loc_5);
                _loc_5.itemName = _loc_5.itemName ? (_loc_5.itemName) : ("");
                _loc_5.pendingName = _loc_5.pendingName ? (_loc_5.pendingName) : ("");
                _loc_1.push(_loc_5);
            }
            return _loc_1;
        }//end

        protected Array  createCityModel ()
        {
            Object _loc_1 =new Object ();
            _loc_2 =Global.gameSettings().getItemByName(RequestItemType.SIGNATURE );
            _loc_1.item = FranchiseManager.CITY_ITEM_NAME;
            _loc_1.itemName = Global.player.cityName;
            _loc_1.pendingName = Global.franchiseManager.getPendingName(FranchiseManager.CITY_ITEM_NAME, Global.player.uid);
            _loc_1.requiredCount = Global.franchiseManager.getSignatureCount(FranchiseManager.CITY_ITEM_NAME);
            _loc_1.requiredMax = Global.franchiseManager.getRequiredSignatures(FranchiseManager.CITY_ITEM_NAME);
            _loc_1.requiredCost = _loc_2.cash;
            _loc_1.state = this.computeState(_loc_1);
            _loc_1.itemName = _loc_1.itemName ? (_loc_1.itemName) : ("");
            _loc_1.pendingName = _loc_1.pendingName ? (_loc_1.pendingName) : ("");
            return .get(_loc_1);
        }//end

        protected int  computeState (Object param1 )
        {
            int _loc_2 ;
            if (!param1.pendingName)
            {
                return RenameCell.STATE_PROPOSE;
            }
            if (param1.requiredCount < param1.requiredMax && this.m_signatureRequired)
            {
                _loc_2 = RenameCell.STATE_REQUEST;
                param1.state = RenameCell.STATE_REQUEST;
                return _loc_2;
            }
            _loc_2 = RenameCell.STATE_FINISH;
            param1.state = RenameCell.STATE_FINISH;
            return _loc_2;
        }//end

        protected void  onRequestClick (RenameEvent event )
        {
            String _loc_2 =null ;
            _loc_3 = ZLoc.t("Items",event.itemName +"_friendlyName");
            if (event.itemName == FranchiseManager.CITY_ITEM_NAME)
            {
                _loc_2 = Global.player.pendingCityName;
            }
            else
            {
                _loc_2 = Global.franchiseManager.getPendingName(event.itemName, Global.player.uid);
            }
            Global.world.viralMgr.sendRequestSignatureFeed(_loc_3, _loc_2, event.itemName);
            this.refresh((RenameDialogView)m_jpanel);
            return;
        }//end

        protected void  onProposeClick (RenameEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            if (event.itemName == FranchiseManager.CITY_ITEM_NAME)
            {
                _loc_2 = "clerk_rename_city";
                _loc_3 = ZLoc.t("Dialogs", "clerk_rename_city_message", {item:ZLoc.t("Items", event.itemName + "_friendlyName")});
                _loc_4 = ZLoc.t("Dialogs", "clerk_rename_city_inputLabel", {item:ZLoc.t("Items", event.itemName + "_friendlyName")});
                _loc_5 = Global.player.cityName;
                _loc_6 = Global.gameSettings().getInt("maxCityNameLength", 22);
            }
            else
            {
                _loc_2 = "clerk_rename_business";
                _loc_3 = ZLoc.t("Dialogs", "clerk_rename_business_message", {item:ZLoc.t("Items", event.itemName + "_friendlyName")});
                _loc_4 = ZLoc.t("Dialogs", "clerk_rename_business_inputLabel", {item:ZLoc.t("Items", event.itemName + "_friendlyName")});
                _loc_5 = Global.franchiseManager.model.getFranchiseName(event.itemName);
                _loc_6 = Global.gameSettings().getInt("maxBusinessNameLength", 30);
            }
            if (Global.localizer.langCode == "ja")
            {
                _loc_6 = int(_loc_6 / 2);
            }
            this.m_editingType = event.itemName;
            InputTextDialog _loc_7 =new InputTextDialog(_loc_3 ,_loc_2 ,_loc_4 ,_loc_5 ,_loc_6 ,0,this.onNameDialogClosed );
            _loc_7.textField.addEventListener(Event.CHANGE, this.onNameDialogChange);
            UI.displayPopup(_loc_7, false, "renameInputDialog");
            return;
        }//end

        protected void  onFinishClick (RenameEvent event )
        {
            if (event.itemName == FranchiseManager.CITY_ITEM_NAME)
            {
                GameTransactionManager.addTransaction(new TFinalizePendingCityName());
            }
            else
            {
                GameTransactionManager.addTransaction(new TFinalizeFranchisePendingName(event.itemName));
            }
            this.refresh((RenameDialogView)m_jpanel);
            if (Global.franchiseManager.getCompletedSignatureCount() < 1)
            {
                Global.world.citySim.miniQuestManager.endMiniQuest(RequestItemMQ.QUEST_NAME);
            }
            closeMe(null);
            return;
        }//end

        protected void  onPurchaseClick (RenameEvent event )
        {
            _loc_2 =Global.gameSettings().getItemByName(RequestItemType.SIGNATURE );
            if (_loc_2 && Global.player.cash >= _loc_2.cash)
            {
                GameTransactionManager.addTransaction(new TBuyRequestItem(RequestItemType.SIGNATURE, event.itemName));
                Global.player.cash = Global.player.cash - _loc_2.cash;
            }
            this.refresh((RenameDialogView)m_jpanel);
            return;
        }//end

        protected void  onNameDialogChange (Event event )
        {
            _loc_2 =(TextField) event.target;
            if (_loc_2)
            {
                this.m_editingName = _loc_2.text;
            }
            return;
        }//end

        protected void  onNameDialogClosed (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                if (this.m_editingName != null)
                {
                    if (this.m_editingType == FranchiseManager.CITY_ITEM_NAME)
                    {
                        GameTransactionManager.addTransaction(new TUpdatePendingCityName(this.m_editingName));
                    }
                    else
                    {
                        GameTransactionManager.addTransaction(new TUpdateFranchisePendingName(this.m_editingType, this.m_editingName));
                    }
                    if (!this.m_signatureRequired)
                    {
                        this.refresh((RenameDialogView)m_jpanel);
                        if (this.m_editingType == FranchiseManager.CITY_ITEM_NAME)
                        {
                            GameTransactionManager.addTransaction(new TFinalizePendingCityName());
                        }
                        else
                        {
                            GameTransactionManager.addTransaction(new TFinalizeFranchisePendingName(this.m_editingType));
                        }
                    }
                }
                this.m_editingName = null;
                this.m_editingType = null;
                this.refresh((RenameDialogView)m_jpanel);
            }
            return;
        }//end

    }



