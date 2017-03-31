package Display.LandmarkUI;

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
import Events.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class LandmarkBuildDialog extends GenericDialog
    {
        public Class checkBox ;
        private MovieClip m_window ;
        protected Object m_data ;
        protected Array m_rowData ;
        protected int m_imagesLoaded =0;
        private Object m_feedData =null ;
        protected Object m_rewardIcon =null ;
        protected Object m_npcInfo =null ;
        private boolean m_isIntro ;
        private MapResource m_mapResource ;
        private Object m_assetDict ;
        private CompositeGate m_compositeGate ;
        private Array m_gateKeys ;
        private Array m_gates ;
        protected int m_numAssetLoads =6;
        private Item m_targetItem ;
        private String m_experimentName ;
        public static  int OK =1;
        public static  int LATER =0;
        public static  int TYPE_OK =0;
        public static  int TYPE_YESNO =1;

        public  LandmarkBuildDialog (MapResource param1 ,Item param2 )
        {
            String _loc_5 =null ;
            this.m_assetDict = new Object();
            this.m_gates = new Array();
            this.m_mapResource = param1;
            this.m_targetItem = param2;
            this.m_experimentName = ExperimentDefinitions.EXPERIMENT_LANDMARK_SAILBOAT;
            if (this.m_mapResource.getItem().experiments)
            {
                for(int i0 = 0; i0 < this.m_mapResource.getItem().experiments.size(); i0++)
                {
                		_loc_5 = this.m_mapResource.getItem().experiments.get(i0);

                    this.m_experimentName = _loc_5;
                }
            }
            this.m_data = {};
            this.m_data.npcActiveUrl = "assets/missions/citysam02_94.png";
            GateFactory _loc_3 =new GateFactory ();
            this.m_compositeGate =(CompositeGate) _loc_3.loadGateFromXML(param2, this.m_mapResource, "build", this.finishBuilding);
            this.m_gateKeys = this.m_compositeGate.getKeyArray();
            int _loc_4 =0;
            while (_loc_4 < this.m_gateKeys.length())
            {

                this.m_gates.push(_loc_3.loadGateFromXML(param2, this.m_mapResource, this.m_gateKeys.get(_loc_4), null));
                this.m_numAssetLoads++;
                _loc_4++;
            }
            super("");
            return;
        }//end

        private void  finishBuilding ()
        {
            this.close();
            return;
        }//end

         protected void  loadAssets ()
        {
            String _loc_1 =null ;
            AbstractGate _loc_3 =null ;
            this.m_rowData = new Array();
            this.m_npcInfo = new Object();
            this.m_npcInfo.name = this.m_data.npcName;
            _loc_1 = Global.getAssetURL(this.m_data.npcActiveUrl);
            this.m_npcInfo.image = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_1 = Global.getAssetURL((this.m_mapResource as ConstructionSite).targetItem.getRelativeImageByName("icon"));
            this.m_assetDict.itemImage = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_1 = Global.getAssetURL("assets/doobers/diamond1_doober.png");
            this.m_assetDict.pinkDiamondImage = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_1 = Global.getAssetURL("assets/doobers/goods_doober.png");
            this.m_assetDict.goodsImage = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            _loc_1 = Global.getAssetURL(Global.gameSettings().getItemByName(this.m_targetItem.wonderName).getRelativeImageByName("icon"));
            this.m_assetDict.wonderImage = LoadingManager.load(_loc_1, this.onAssetsLoaded);
            this.m_assetDict.gateInfo = new Dictionary();
            double _loc_2 =0;
            while (_loc_2 < this.m_gates.length())
            {

                _loc_3 = this.m_gates.get(_loc_2);
                _loc_1 = Global.getAssetURL(_loc_3.iconURL);
                if (!this.m_assetDict.gateInfo.get(_loc_3))
                {
                    this.m_assetDict.gateInfo.put(_loc_3,  new Object());
                }
                this.m_assetDict.gateInfo.get(_loc_3).icon = LoadingManager.load(_loc_1, this.onAssetsLoaded);
                _loc_2 = _loc_2 + 1;
            }
            Global.delayedAssets.get(DelayedAssetLoader.NEW_QUEST_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.MARKET_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.LANDMARKS_ASSETS, makeAssets);
            return;
        }//end

        private void  loadRewardData (Array param1 ,Array param2 )
        {
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =new Array();
            _loc_1.push(DelayedAssetLoader.INVENTORY_ASSETS, DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS, DelayedAssetLoader.LANDMARKS_ASSETS);
            return _loc_1;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_numAssetLoads)
            {
                return;
            }
            closeLoadingDialog();
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("checkMark",  m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_check);
            _loc_2.put("horizontalRule",  m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_horizontal_rule);
            _loc_2.put("verticalRule", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_vertical_rule());
            _loc_2.put("rewardItemBG", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_item_card());
            _loc_2.put("imageBG_0", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_bg_slim());
            _loc_2.put("imageBG_1", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_bg_single());
            _loc_2.put("imageBG_2", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_bg_half());
            _loc_2.put("imageBG_3", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_bg_full());
            _loc_2.put("tasksBG",  m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_tasks_bg);
            _loc_2.put("speechBG", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_speech_bubble());
            _loc_2.put("speechTail", (DisplayObject) new m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_speech_tail());
            _loc_2.put("checkList",  m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS).quest_check_list);
            _loc_2.put("taskInfo",  this.m_rowData);
            _loc_2.put("pinkDiamondIcon",  this.m_assetDict.pinkDiamondImage.content);
            _loc_2.put("goodsIcon",  this.m_assetDict.goodsImage.content);
            _loc_2.put("burstImage",  m_assetDependencies.get(DelayedAssetLoader.LANDMARKS_ASSETS).wonders_burst_sm);
            _loc_2.put("npcName",  this.m_npcInfo.name);
            _loc_2.put("npcIcon",  this.m_npcInfo.image.content);
            _loc_2.put("itemImage",  this.m_assetDict.itemImage.content);
            _loc_2.put("wonderImage",  this.m_assetDict.wonderImage.content);
            _loc_2.put("gateInfo",  this.m_assetDict.get("gateInfo"));
            if (this.m_rewardIcon)
            {
                _loc_2.put("rewardIcon",  this.m_rewardIcon.image.content);
            }
            m_jpanel = new LandmarkBuildDialogView(_loc_2, this.m_data, this.m_gates, this.m_compositeGate, this.m_mapResource, this.m_experimentName);
            m_jpanel.addEventListener(Event.CLOSE, this.closePopup, false, 0, true);
            finalizeAndShow();
            return;
        }//end

         public Point  getDialogOffset ()
        {
            return new Point(0, 25);
        }//end

        private void  closePopup (Event event =null )
        {
            double sfxVolume ;
            Event e =event ;
            int _loc_4 =0;
            _loc_5 =Global.gameSettings().getXML ().sounds.sound ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == "click1")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            sfxVolume = _loc_3.@volume;
            Sounds.play("click1");
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, 0));
            close();
            return;
        }//end

         protected String  getDialogStatsTrackingString ()
        {
            _loc_1 = this"quest_"+.m_data.name ;
            _loc_1 = _loc_1 + "_info";
            return _loc_1;
        }//end

         public boolean  isLockable ()
        {
            return true;
        }//end

    }



