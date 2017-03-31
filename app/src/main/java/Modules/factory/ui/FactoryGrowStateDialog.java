package Modules.factory.ui;

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
import Classes.Managers.*;
import Classes.inventory.*;
import Classes.util.*;
import Classes.virals.*;
import Display.DialogUI.*;
import Display.GateUI.*;
import Engine.Events.*;
import Events.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.geom.*;

    public class FactoryGrowStateDialog extends GenericDialog
    {
        protected int numAssets =1;
        protected Factory m_factory ;
        protected Function m_finishCheckCallback ;
        protected Function m_finishCallback ;
        protected Function m_buyAllCallback ;
        protected ItemImageInstance m_contractPic ;
public static  Class GRIDCELL_FACTORY =KeyCellCrewFactory ;

        public  FactoryGrowStateDialog (Factory param1 ,String param2 ,String param3 ,Function param4 ,Function param5 ,Function param6 )
        {
            this.m_factory = param1;
            this.m_finishCheckCallback = param4;
            this.m_finishCallback = param5;
            this.m_buyAllCallback = param6;
            super(param3, "", 0, null, param2);
            return;
        }//end

        public void  refresh ()
        {
            _loc_1 = (FactoryGrowStateDialogView)m_jpanel
            if (_loc_1)
            {
                _loc_1.update(this.m_factory);
            }
            return;
        }//end

         protected void  loadAssets ()
        {
            this.m_contractPic = this.m_factory.harvestingDefinition.getCachedImage("growStateDialog");
            if (!this.m_contractPic)
            {
                this.numAssets++;
                this.m_factory.harvestingDefinition.addEventListener(LoaderEvent.LOADED, this.onContractPicLoaded, false, 0, true);
            }
            Global.delayedAssets.get(DelayedAssetLoader.BUILDABLE_ASSETS, this.onLoadBuildableAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg", (DisplayObject) new m_comObject.factory_hireWorkers_bg());
            _loc_1.put("cell_bg",  m_comObject.crew_position_bg);
            _loc_1.put("cell_bg_alt",  m_comObject.crew_position_bg_alternate);
            _loc_1.put("cell_goods",  m_comObject.factory_hireWorkers_goods_icon_small);
            _loc_1.put("pic_timer", (DisplayObject) new m_comObject.factory_hireWorkers_timer_icon());
            _loc_1.put("pic_goods", (DisplayObject) new m_comObject.factory_hireWorkers_goods_icon());
            _loc_1.put("pic_contract", (DisplayObject) this.m_contractPic.image);
            _loc_1.put("pic_cashBtn", (DisplayObject) new EmbeddedArt.icon_cash_big());
            _loc_1.put("stringAssets",  {dialogTitle:ZLoc.t("Dialogs", m_title + "_title"), jobTitle:this.m_factory.harvestingDefinition.localizedName, jobDescription:this.getJobDescription(), workerBonusText:"+" + Global.player.GetDooberMinimums(this.m_factory.harvestingDefinition, Commodities.PREMIUM_GOODS_COMMODITY), hireFriendsButton:ZLoc.t("Dialogs", "HireFriendsButtonLabel"), buyWorkerButton:ZLoc.t("Dialogs", "BuyCrewButtonLabel", {amount:this.m_factory.harvestingDefinition.workers.cashCost.toString()}), completeButton:ZLoc.t("Dialogs", "OkButton"), unlockWorkerQuestion:ZLoc.t("Dialogs", "FactoryWorkerDialog_purchaseQuestion", {amount:this.m_factory.harvestingDefinition.workers.cashCost.toString()}), unlockWorkerNote:ZLoc.t("Dialogs", "FactoryWorkerDialog_purchaseNote")});
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            _loc_2 = this.m_factory.getWorkerData ();
            WorkerCellFactory _loc_3 =new WorkerCellFactory(param1 ,new IntDimension(680/2,160/3));
            FactoryGrowStateDialogView _loc_4 =new FactoryGrowStateDialogView(_loc_2 ,_loc_3 ,param1 ,m_message ,m_title ,GenericDialogView.TYPE_CUSTOM_OK );
            _loc_4.addEventListener(UIEvent.ASK_FOR_CREW, this.onHireFriends, false, 0, true);
            _loc_4.addEventListener(UIEvent.BUY_CREW, this.onBuyWorker, false, 0, true);
            return _loc_4;
        }//end

        protected void  onContractPicLoaded (Event event )
        {
            event.stopImmediatePropagation();
            event.target.removeEventListener(LoaderEvent.LOADED, this.onContractPicLoaded);
            this.m_contractPic = this.m_factory.harvestingDefinition.getCachedImage("growStateDialog");
             this.numAssets--;
            if (--this.numAssets == 0)
            {
                onAssetsLoaded();
            }
            return;
        }//end

        protected void  onLoadBuildableAssets (DisplayObject param1 ,String param2 )
        {
            m_comObject = param1;
             this.numAssets--;
            if (--this.numAssets == 0)
            {
                onAssetsLoaded();
            }
            return;
        }//end

        protected void  onHireFriends (Event event =null )
        {
            FlashMFSListManager _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            Object _loc_10 =null ;
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS );
            _loc_3 = this"factoryWorker.php?wid="+.m_factory.getId ().toString ();
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FLASH_MFS_FACTORY) == 3)
            {
                _loc_4 =(FlashMFSListManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_FLASH_MFS_LIST);
                if (_loc_4)
                {
                    this.close();
                    _loc_5 = ZLoc.t("Dialogs", "mfs_factory_title");
                    _loc_6 = this.m_factory.getHarvestingDefinition().localizedName;
                    _loc_7 = ZLoc.t("Dialogs", "mfs_factory_body", {contractName:this.m_factory.getHarvestingDefinition().localizedName});
                    _loc_8 = this.m_factory.getHarvestingDefinition().getRelativeImageByName("icon");
                    _loc_9 = ZLoc.t("Dialogs", "SendRequest");
                    _loc_10 = {type:RequestType.WORKER_REQUEST, title:_loc_5, subTitle:_loc_6, message:_loc_7, iconUrl:_loc_8, sendLabel:_loc_9, data:{worldObjectId:this.m_factory.getId(), message:"", featureName:"in_game_flash_mfs"}};
                    _loc_4.initMFS(_loc_10);
                    _loc_4.displayMFS();
                    return;
                }
            }
            if (_loc_2 == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                FrameManager.showTray(_loc_3);
                this.close();
            }
            else
            {
                FrameManager.navigateTo(_loc_3);
            }
            return;
        }//end

        protected boolean  onBuyWorker (Event event =null )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.m_factory.harvestingDefinition.workers.cashCost ;
            if (Global.player.canBuyCash(_loc_3, true))
            {
                _loc_2 = true;
                if (this.canStillBuyWorkers() == true)
                {
                    this.m_factory.purchaseWorker();
                    this.refresh();
                }
            }
            return _loc_2;
        }//end

        protected boolean  canStillBuyWorkers ()
        {
            _loc_1 =Global.factoryWorkerManager.getWorkers(this.m_factory.getWorkerBucket ()).getRemainingSpots ();
            return _loc_1 > 0;
        }//end

        protected String  getJobDescription ()
        {
            _loc_1 =Global.factoryWorkerManager.getWorkers(this.m_factory.getWorkerBucket ()).getRemainingSpots ();
            if (_loc_1 > 0)
            {
                return ZLoc.t("Items", this.m_factory.harvestingDefinition.name + "_description", {workersLeft:_loc_1});
            }
            return ZLoc.t("Items", this.m_factory.harvestingDefinition.name + "_complete");
        }//end

    }



