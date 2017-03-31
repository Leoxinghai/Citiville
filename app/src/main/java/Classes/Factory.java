package Classes;

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

import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Engine.Helpers.*;
import GameMode.*;
import Modules.factory.ui.*;
import Modules.workers.*;
import Modules.workers.transactions.*;

import com.zynga.skelly.util.*;

    public class Factory extends Plot
    {
        protected Function m_updateUI ;

        public  Factory (String param1 ,int param2)
        {
            super(param1, param2);
            m_isRoadVerifiable = true;
            return;
        }//end

         protected Catalog  showCatalogWindow ()
        {
            return UI.displayCatalog(new CatalogParams("factory_contract").setExclusiveCategory(true).setOverrideTitle("factory_contract").setCloseMarket(true), false, true);
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            return getPlotImage();
        }//end

         public boolean  isPlantable (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            return _loc_2.type == "factory_contract";
        }//end

         protected Class  getPlowedCursor ()
        {
            if (isNeedingRoad)
            {
                return EmbeddedArt.hud_act_move;
            }
            return null;
        }//end

         protected Class  getGrownCursor ()
        {
            if (isNeedingRoad)
            {
                return EmbeddedArt.hud_act_move;
            }
            return null;
        }//end

         public boolean  plant (String param1 )
        {
            FactoryWorkers _loc_3 =null ;
            if (isNeedingRoad)
            {
                return false;
            }
            _loc_2 = super.plant(param1);
            if (_loc_2)
            {
                Global.factoryWorkerManager.clearWorkers(getWorkerBucket());
                _loc_3 =(FactoryWorkers) Global.factoryWorkerManager.addAndGetWorkers(getWorkerBucket());
                _loc_3.contractName = harvestingDefinition.name;
                this.showWorkerDialog();
            }
            return _loc_2;
        }//end

        public boolean  canPurchaseWorker ()
        {
            _loc_1 = (FactoryWorkers)Global.factoryWorkerManager.addAndGetWorkers(getWorkerBucket())
            return _loc_1.canAddWorker() && harvestingDefinition.workers && Global.player.canBuyCash(harvestingDefinition.workers.cashCost, true);
        }//end

        public void  purchaseWorker ()
        {
            _loc_1 = (FactoryWorkers)Global.factoryWorkerManager.addAndGetWorkers(getWorkerBucket())
            _loc_1.addWorker(String(-(_loc_1.numPurchasedWorkers + 1)));
            _loc_2 = _loc_1;
            _loc_3 = _loc_1.numPurchasedWorkers+1;
            _loc_2.numPurchasedWorkers = _loc_3;
            Global.player.cash = Global.player.cash - harvestingDefinition.workers.cashCost;
            GameTransactionManager.addTransaction(new TWorkerPurchase("factories", getWorkerBucket()));
            return;
        }//end

         protected void  onStateChanged (String param1 ,String param2 )
        {
            if (param1 == STATE_PLANTED && param2 == STATE_GROWN)
            {
                GameTransactionManager.addTransaction(new TWorkersSync("factories", Global.factoryWorkerManager));
            }
            super.onStateChanged(param1, param2);
            return;
        }//end

         public boolean  harvest ()
        {
            _loc_1 = super.harvest();
            Global.factoryWorkerManager.clearWorkers(getWorkerBucket());
            return _loc_1;
        }//end

         protected void  processPlantedState ()
        {
            this.showWorkerDialog();
            return;
        }//end

        private void  showWorkerDialog ()
        {
            GenericDialog dlg ;
            dlg = new FactoryGrowStateDialog(this, "FactoryWorkerDialog", "", null, null, null);
            UI.displayPopup(dlg);
            this .m_updateUI =Curry .curry (void  ()
            {
                ((FactoryGrowStateDialog)dlg).refresh();
                return;
            }//end
            , dlg);
            return;
        }//end

         public boolean  isVisitorInteractable ()
        {
            return false;
        }//end

         public boolean  isHighlightable ()
        {
            boolean _loc_1 =false ;
            _loc_2 = Global.world.getTopGameMode();
            if (_loc_2 instanceof GMPlay)
            {
                if (isNeedingRoad)
                {
                    _loc_1 = false;
                }
                else
                {
                    _loc_1 = m_isHighlightable;
                }
            }
            else
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

         protected void  updateArrow ()
        {
            if (isNeedingRoad)
            {
                removeStagePickEffect();
                return;
            }
            switch(m_state)
            {
                case STATE_GROWN:
                {
                    this.createStagePickEffect();
                    break;
                }
                case STATE_PLANTED:
                {
                    removeStagePickEffect();
                    break;
                }
                default:
                {
                    this.createStagePickEffect();
                    break;
                    break;
                }
            }
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            _loc_1 = StagePickEffect.PICK_FORKLIFT;
            if (m_state == STATE_GROWN)
            {
                _loc_1 = StagePickEffect.PICK_PREMIUM_GOODS;
            }
            if (!m_stagePickEffect)
            {
                m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.STAGE_PICK);
                m_stagePickEffect.setPickType(_loc_1);
                m_stagePickEffect.queuedFloat();
            }
            else
            {
                m_stagePickEffect.setPickType(_loc_1);
                m_stagePickEffect.reattach();
                m_stagePickEffect.queuedFloat();
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            if (this.m_updateUI != null)
            {
                this.m_updateUI();
            }
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            this.updateEffects();
            this.updateArrow();
            return;
        }//end

        private void  updateEffects ()
        {
            if (!isNeedingRoad && !isUxLockedByQuests)
            {
                this.createStagePickEffect();
            }
            else
            {
                removeAnimatedEffects();
                if (m_arrow)
                {
                    m_arrow = null;
                }
                removeStagePickEffect();
            }
            return;
        }//end

         protected String  getFallowToolTipStatus ()
        {
            return null;
        }//end

         protected String  getPlowedToolTipStatus ()
        {
            return null;
        }//end

         protected String  getGrownToolTipStatus ()
        {
            return null;
        }//end

         protected String  getWitheredToolTipStatus ()
        {
            return null;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = super.getToolTipAction();
            if (isNeedingRoad)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
            }
            _loc_2 = Global.isVisiting()&& !this.isVisitorInteractable();
            if (_loc_2)
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

         public void  onPlayAction ()
        {
            if (isNeedingRoad && !Global.isVisiting())
            {
                super.enterMoveMode();
                return;
            }
            super.onPlayAction();
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            Global.world.citySim.roadManager.updateResource(this);
            if (!isNeedingRoad)
            {
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            return;
        }//end

         protected String  getTimeLeftString (String param1 )
        {
            return ZLoc.t("Main", "PlantedFactoryPlot", {time:param1});
        }//end

         protected String  getPlowedToolTipAction ()
        {
            _loc_1 = super.getPlowedToolTipAction();
            _loc_2 = Global.isVisiting()&& !this.isVisitorInteractable();
            if (isNeedingRoad || _loc_2)
            {
                return null;
            }
            if (_loc_1)
            {
                _loc_1 = ZLoc.t("Main", "PlowedFactoryAction");
            }
            return _loc_1;
        }//end

         protected String  getGrownToolTipAction ()
        {
            _loc_1 = Global.isVisiting()&& !this.isVisitorInteractable();
            if (isNeedingRoad || _loc_1)
            {
                return null;
            }
            return ZLoc.t("Main", "GrownFactoryAction");
        }//end

         protected void  adjustModesAfterPlanting ()
        {
            if (Global.world.getTopGameMode() instanceof GMPlant)
            {
                Global.world.addGameMode(new GMPlay());
            }
            return;
        }//end

        public Array  getWorkerData ()
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Player _loc_7 =null ;
            Array _loc_1 =new Array ();
            _loc_2 = this.harvestingDefinition.workers.members;
            _loc_3 = Global.factoryWorkerManager.addAndGetWorkers(getWorkerBucket()).getWorkerIds();
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                _loc_5 = {};
                if (_loc_3 && _loc_3.length > _loc_4)
                {
                    _loc_6 = this.normalizeUid(_loc_3.get(_loc_4));
                    _loc_7 = Global.player.findFriendById(_loc_6);
                    if (_loc_7)
                    {
                        _loc_5.friendName = _loc_7.name;
                        _loc_5.picUrl = _loc_7.snUser.picture;
                    }
                }
                _loc_5.position = ((WorkerDefinition)_loc_2.get(_loc_4)).name;
                _loc_1.push(_loc_5);
                _loc_4++;
            }
            return _loc_1;
        }//end

        private String  normalizeUid (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = param1;
            if (param1 !=null)
            {
                _loc_3 = parseFloat(param1);
                if (_loc_3 < 0)
                {
                    _loc_3 = -1;
                }
                _loc_2 = _loc_3.toString();
            }
            return _loc_2;
        }//end

         public void  sell ()
        {
            String _loc_1 ="";
            switch(m_state)
            {
                case STATE_GROWN:
                {
                    _loc_1 = ZLoc.t("Main", "SendToInventoryFactoryGrownWarning", {item:getItemFriendlyName(), coins:getSellPrice()});
                    UI.displayMessage(_loc_1, GenericPopup.TYPE_YESNO, sellConfirmationHandler);
                    break;
                }
                case STATE_PLANTED:
                {
                    _loc_1 = ZLoc.t("Main", "SendToInventoryFactoryPlantedWarning", {item:getItemFriendlyName(), coins:getSellPrice()});
                    UI.displayMessage(_loc_1, GenericPopup.TYPE_YESNO, sellConfirmationHandler);
                    break;
                }
                default:
                {
                    super.sell();
                    break;
                    break;
                }
            }
            return;
        }//end

         public boolean  warnForStorage ()
        {
            if (m_state == STATE_PLOWED)
            {
                return false;
            }
            return true;
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            m_state = STATE_PLOWED;
            super.prepareForStorage(param1);
            return;
        }//end

        public static boolean  isFactoryInPlayerWorld ()
        {
            _loc_1 = Global.world.getObjectsByClass(Factory);
            if (_loc_1.length > 0 && !Global.isVisiting())
            {
                return true;
            }
            return false;
        }//end

    }



