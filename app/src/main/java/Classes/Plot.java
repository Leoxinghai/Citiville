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

import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Events.*;
import Events.*;
import GameMode.*;
import Modules.franchise.display.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.quest.Helpers.*;
import Modules.quest.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class Plot extends HarvestableResource
    {
        private  String PLOT ="plot";
        private  int WITHERED_HIGHLIGHT_COLOR =11141120;
        private String m_itemImageName ;
        private boolean m_isNewPlot =true ;
        private boolean m_isPlowing =false ;
        private JPanel m_starRatingContainer ;
        private StarRatingComponent m_starRatingComponent ;
        private static Catalog m_catalogWindow ;
        private static Plot marketClickedPlot =null ;

        public  Plot (String param1 ,int param2)
        {
            super(param1);
            m_objectType = WorldObjectTypes.PLOT;
            m_typeName = this.PLOT;
            return;
        }//end

         public Item  harvestingDefinition ()
        {
            return m_contract != null ? (m_contract) : (m_item);
        }//end

         public Object  getSaveObject ()
        {
            this.m_isNewPlot = false;
            _loc_1 = super.getSaveObject();
            if (m_contract)
            {
                _loc_1.contractName = m_contract.name;
                _loc_1.contractType = m_contract.type;
            }
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            this.m_isNewPlot = false;
            m_contract = Global.gameSettings().getItemByName(param1.contractName);
            m_contractGrowTime = m_contract ? (m_contract.growTime) : (0);
            super.loadObject(param1);
            return;
        }//end

        protected ItemImageInstance  getPlotImage ()
        {
            return super.getCurrentImage();
        }//end

        protected ItemImageInstance  getContractImage ()
        {
            _loc_1 = m_contract.getCachedImage(m_state,this,m_direction,m_statePhase);
            if (_loc_1 == null)
            {
                m_contract.addEventListener(LoaderEvent.LOADED, this.onContractImageLoaded, false, 0, true);
            }
            return _loc_1;
        }//end

         public boolean  isWitherOn ()
        {
            if (Global.world.citySim.unwitherManager.isWitheringDisabledGlobally)
            {
                return false;
            }
            return super.isWitherOn;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            if (m_contract == null)
            {
                return this.getPlotImage();
            }
            return this.getContractImage();
        }//end

         protected Class  getPlowedCursor ()
        {
            return EmbeddedArt.hud_act_plant;
        }//end

         protected Class  getGrownCursor ()
        {
            if (!m_isHarvesting && Global.player.checkEnergy(-m_contract.harvestEnergyCost, false))
            {
                return EmbeddedArt.hud_act_harvest;
            }
            return null;
        }//end

         public boolean  isHighlightable ()
        {
            return super.isHighlightable && (m_state != STATE_PLANTED || Global.world.isEditMode);
        }//end

         protected boolean  isWrongPlantContract ()
        {
            _loc_1 = GMPlant(Global.world.getTopGameMode());
            if (_loc_1.m_contractClass == "plot_contract")
            {
                return false;
            }
            return true;
        }//end

         public int  getHighlightColor ()
        {
            if (m_state == STATE_WITHERED)
            {
                return this.WITHERED_HIGHLIGHT_COLOR;
            }
            return super.getHighlightColor();
        }//end

         protected boolean  isUxLockedByQuests ()
        {
            return Global.questManager != null && !Global.questManager.isUXUnlocked(GameQuestManager.QUEST_UX_CROPS_UNLOCKED);
        }//end

        protected void  onContractImageLoaded (LoaderEvent event )
        {
            event.target.removeEventListener(LoaderEvent.LOADED, this.onContractImageLoaded);
            reloadImage();
            return;
        }//end

         protected String  computeStatePhase ()
        {
            if (m_state == STATE_PLANTED)
            {
                return "" + (1 + int(Math.floor(m_growPercentage / 51)));
            }
            return Item.DEFAULT_PHASE;
        }//end

         public Array  getToolTipComponentList ()
        {
            Component _loc_1 =null ;
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                return new Array();
            }
            _loc_1 = buildToolTipComponent(ToolTipDialog.ACTION, this.getToolTipAction() ? (TextFieldUtil.formatSmallCapsString(this.getToolTipAction())) : (null));
            return .get(_loc_1, _loc_1 ? (ASwingHelper.verticalStrut(-4)) : (null), this.getCustomToolTipStatus(), this.getCustomToolTipStarComponent(), ASwingHelper.verticalStrut(5), this.getCustomToolTipLevelBar());
        }//end

         public Component  getCustomToolTipStatus ()
        {
            if (this.m_clearLock)
            {
                return null;
            }
            return buildToolTipComponent(ToolTipDialog.STATUS, getToolTipStatus());
        }//end

         public Component  getCustomToolTipLevelBar ()
        {
            double _loc_2 =0;
            double _loc_3 =0;
            _loc_1 = (MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)
            if (this.harvestingDefinition && _loc_1 && Global.player.isEligibleForMastery(this.harvestingDefinition) && !_loc_1.isMaxLevel(this.harvestingDefinition.name) && !Global.isVisiting())
            {
                _loc_2 = _loc_1.getNextLevelDiff(this.harvestingDefinition.name);
                _loc_3 = _loc_1.getProgressTowardNextLevel(this.harvestingDefinition.name);
                if (!m_levelProgressComponent)
                {
                    m_levelProgressComponent = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 100, 10, 2, -5);
                }
                m_levelProgressComponent.setBgAlpha(1);
                m_levelProgressComponent.setBgColor(2450817);
                m_levelProgressComponent.setProgressRatio(_loc_3, _loc_2);
            }
            else
            {
                m_levelProgressComponent = null;
            }
            return m_levelProgressComponent;
        }//end

        public Component  getCustomToolTipStarComponent ()
        {
            double _loc_2 =0;
            int _loc_3 =0;
            _loc_1 = (MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)
            if (_loc_1 && this.harvestingDefinition && Global.player.isEligibleForMastery(this.harvestingDefinition) && !Global.isVisiting())
            {
                _loc_4 = _loc_1.getLevel(this.harvestingDefinition.name);
                _loc_2 = _loc_4;

                _loc_4 = _loc_1.getMaxLevel(this.harvestingDefinition.name);
                _loc_3 = _loc_4;

                if (!this.m_starRatingContainer)
                {
                    this.m_starRatingContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    this.m_starRatingComponent = new StarRatingComponent(_loc_2, true, DelayedAssetLoader.MASTERY_ASSETS, _loc_3, 20, "cropMastery_smallStar");
                    this.m_starRatingContainer.append(this.m_starRatingComponent);
                }
                this.m_starRatingComponent.setStarRating(_loc_2);
            }
            else
            {
                this.m_starRatingComponent = null;
                this.m_starRatingContainer = null;
            }
            return this.m_starRatingContainer;
        }//end

         public String  getToolTipHeader ()
        {
            _loc_1 = this.getContractFriendlyName();
            if (!_loc_1)
            {
                if (Global.world.getTopGameMode() instanceof GMPlant)
                {
                    if (this.isWrongPlantContract())
                    {
                        return null;
                    }
                }
                return super.getToolTipHeader();
            }
            else
            {
                return _loc_1;
            }
        }//end

        protected String  getVisitToolTipAction ()
        {
            String _loc_1 =null ;
            switch(m_state)
            {
                case STATE_GROWN:
                {
                    break;
                }
                case STATE_PLANTED:
                {
                    break;
                }
                case STATE_WITHERED:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

        protected String  getPlowedToolTipAction ()
        {
            String _loc_1 =null ;
            if (Global.world.getTopGameMode() instanceof GMPlant)
            {
                if (this.isWrongPlantContract())
                {
                    return null;
                }
            }
            else if (!(Global.world.getTopGameMode() instanceof GMRemodel))
            {
                _loc_1 = ZLoc.t("Main", "PlowedPlotAction");
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            Debug.debug4("Plot."+"getToolTipAction");
            String _loc_1 =getGameModeToolTipAction ();
            if (!_loc_1)
            {
                _loc_1 = super.getToolTipAction();
                if (Global.isVisiting())
                {
                    _loc_1 = this.getVisitToolTipAction();
                }
                else
                {
                    if (m_state == STATE_GROWN)
                    {
                        _loc_1 = getGrownToolTipAction();
                        if (!Global.player.checkEnergy(-m_contract.harvestEnergyCost, false))
                        {
                            _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:m_contract.harvestEnergyCost});
                        }
                    }
                    if (m_state == STATE_PLOWED)
                    {
                        _loc_1 = this.getPlowedToolTipAction();
                    }
                    if (m_isHarvesting)
                    {
                        _loc_1 = null;
                    }
                }
            }
            return _loc_1;
        }//end

         public Object  getCustomCursor ()
        {

            Debug.debug4("Plot."+"getCustomCursor");
            ASFont _loc_2 =null ;
            GlowFilter _loc_3 =null ;
            MarginBackground _loc_4 =null ;
            if (m_isHarvesting)
            {
                return null;
            }
            if (!m_customCursor)
            {
                m_customCursor = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                m_customCursorLabel = new JLabel("");
                m_customCursorHolder = new Sprite();
                Global.ui.addChild(m_customCursorHolder);
                m_customCursorWindow = new JWindow(m_customCursorHolder);
                _loc_2 = ASwingHelper.getBoldFont(16);
                _loc_3 = new GlowFilter(0, 1, 1.2, 1.2, 20, BitmapFilterQuality.HIGH);
                m_customCursorLabel.setForeground(new ASColor(16777215));
                m_customCursorLabel.setFont(_loc_2);
                m_customCursorLabel.setTextFilters(.get(_loc_3));
            }
            DisplayObject _loc_1 =null ;
            if (m_state == STATE_GROWN)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_harvest();
                m_customCursorLabel.setText(m_contract.harvestEnergyCost.toString());
                m_customCursorLabel.visible = true;
            }
            else
            {
                return null;
            }
            if (_loc_1)
            {
                _loc_4 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                m_customCursor.setBackgroundDecorator(_loc_4);
                m_customCursor.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            }
            m_customCursor.append(m_customCursorLabel);
            m_customCursorWindow.setContentPane(m_customCursor);
            ASwingHelper.prepare(m_customCursorWindow);
            m_customCursorWindow.show();
            return m_customCursorWindow;
        }//end

        protected Catalog  showCatalogWindow ()
        {
            return UI.displayCatalog(new CatalogParams("plot_contract").setExclusiveCategory(true).setOverrideTitle("plot_contract").setCloseMarket(true), false, true);
        }//end

        protected void  processPlantedState ()
        {
            DisplayObject _loc_3 =null ;
            _loc_1 = Global.player.hasSeenInstantPopup_plot;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_CROPS_SHIPS);
            if (_loc_1 == false && !Global.isVisiting() && Global.player.level >= 6 && _loc_2 > 1)
            {
                _loc_3 = new InstantReadyDialog(this);
                UI.displayPopup(_loc_3, true, "instantReadyCrops", false);
            }
            return;
        }//end

        protected void  processWitheredState ()
        {
            DisplayObject _loc_3 =null ;
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CV_UNWITHER);
            boolean _loc_2 =false ;
            _loc_2 = Global.player.hasSeenWitherPopup_plot;
            if (_loc_2 == false && !Global.isVisiting() && Global.player.level >= 6 && _loc_1 > ExperimentDefinitions.UNWITHER_CONTROL_TWO)
            {
                _loc_3 = new UnwitherDialog(this);
                UI.displayPopup(_loc_3, true, "unwitherCrops", false);
            }
            else if (!this.m_isPlowing)
            {
                this.m_isPlowing = true;
                setHighlighted(false);
                Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);
            }
            return;
        }//end

         public void  onPlayAction ()
        {
            Debug.debug4("Plot."+"onPlayAction");

            double _loc_1 =0;
            if (this.isUxLockedByQuests)
            {
                displayStatus(ZLoc.t("Main", "QuestUXLocked"));
                return;
            }
            if (m_visitReplayLock > 0 || Global.isVisiting())
            {
                return;
            }
            if (!hasValidId())
            {
                showObjectBusy();
                return;
            }
            super.onPlayAction();
            m_actionMode = PLAY_ACTION;
            switch(m_state)
            {
                case STATE_PLOWED:
                {
                    if (Global.guide.isActive())
                    {
                        break;
                    }
                    if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.MARKET_ASSETS))
                    {
                        break;
                    }
                    SetMarketClickedPlot(this);
                    m_catalogWindow = this.showCatalogWindow();
                    if (m_catalogWindow && !m_catalogWindow.hasEventListener(Event.CLOSE))
                    {
                        m_catalogWindow.addEventListener(Event.CLOSE, this.onMarketCloseClick);
                    }
                    break;
                }
                case STATE_PLANTED:
                {
                    this.processPlantedState();
                    break;
                }
                case STATE_GROWN:
                {
                    _loc_1 = m_contract.harvestEnergyCost;
                    if (!Global.player.checkEnergy(-_loc_1))
                    {
                        displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                    }
                    else if (!m_isHarvesting)
                    {
                        if (!isHarvestable())
                        {
                            break;
                        }
                        if (Global.player.commodities.isAtCommodityCapacity(m_contract))
                        {
                            m_firstHarvestAttempt = false;
                            break;
                        }
                        m_isHarvesting = true;
                        setHighlighted(false);
                        Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);
                    }
                    break;
                }
                case STATE_FALLOW:
                case STATE_WITHERED:
                {
                    this.processWitheredState();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         public void  onVisitPlayAction ()
        {
            Debug.debug4("Plot."+"onVisitPlayAction");

            switch(m_state)
            {
                case STATE_PLANTED:
                {
                    Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);

                    m_numVisitorInteractions++;
                    break;
                }
                case STATE_GROWN:
                {
                    m_isHarvesting = true;
                    setHighlighted(false);
                    Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);

                    m_numVisitorInteractions++;
                    break;
                }
                case STATE_WITHERED:
                {
                    Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);

                    m_numVisitorInteractions++;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            Debug.debug4("Plot."+"onVisitReplayAction");

            m_actionMode = VISIT_REPLAY_ACTION;
            m_giveVisitMastery = param1.shouldGiveMastery();
            switch(m_state)
            {
                case STATE_PLANTED:
                {
                    break;
                }
                case STATE_GROWN:
                {
                    if (isHarvestable() && !m_isHarvesting)
                    {
                        m_isHarvesting = true;
                        setHighlighted(false);
                    }
                    else
                    {
                        return 0;
                    }
                    break;
                }
                case STATE_WITHERED:
                {
                    break;
                }
                default:
                {
                    return 0;
                    break;
                }
            }
            if (!this.stopTransaction())
            {
                GameTransactionManager.addTransaction(param1);
            }
            this.getProgressBarEndFunction()();
            return super.onVisitReplayAction(null);
        }//end

        private boolean  stopTransaction ()
        {
            boolean _loc_1 =false ;
            if (!Global.isVisiting() && this.getState() != STATE_PLANTED && this.getState() != STATE_WITHERED)
            {
                if (Global.player.commodities.isAtCommodityCapacity(m_contract, false))
                {
                    _loc_1 = true;
                }
            }
            return _loc_1;
        }//end

         public boolean  isVisitorInteractable ()
        {
            switch(m_state)
            {
                case STATE_PLANTED:
                {
                    return true;
                }
                case STATE_GROWN:
                {
                    if (isHarvestable() && !Global.player.commodities.isAtCommodityCapacity(m_contract))
                    {
                        return true;
                    }
                    return false;
                }
                case STATE_WITHERED:
                {
                    return true;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        protected void  cancelContractHandler (GenericPopupEvent event )
        {
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            GameTransactionManager.addTransaction(new TFactoryContractCancel(this));
            if (m_contract instanceof Item)
            {
                displayStatus(ZLoc.t("Main", "FactoryCancelContractSuccess", {crop:m_contract.localizedName}));
                m_contract = null;
            }
            setState(STATE_PLOWED);
            return;
        }//end

        protected void  onMarketCloseClick (Event event )
        {
            if (m_catalogWindow)
            {
                m_catalogWindow.removeEventListener(Event.CLOSE, this.onMarketCloseClick);
            }
            m_catalogWindow = null;
            return;
        }//end

         public boolean  plow ()
        {
            Debug.debug4("Plot."+"plow");

            int _loc_2 =0;
            int _loc_3 =0;
            boolean _loc_1 =false ;
            if (isWithered())
            {
                m_doobersArray = this.makeDoobers();
                spawnDoobers();
                setState(STATE_PLOWED);
                GameTransactionManager.addTransaction(new TClearWithered(this));
                trackAction(TrackedActionType.CLEAR);
                this.m_isPlowing = false;
                return true;
            }
            if (isPlowable())
            {
                _loc_2 = Global.gameSettings().getInt("plowCost");
                _loc_3 = Global.gameSettings().getInt("plowXp");
                if (Global.player.canBuy(_loc_2))
                {
                    doResourceChanges(0, -_loc_2, _loc_3);
                    setState(STATE_PLOWED);
                    GameTransactionManager.addTransaction(new TPlow(this), true);
                    _loc_1 = true;
                }
                else if (this.m_isNewPlot && m_state == STATE_FALLOW)
                {
                    detach();
                }
            }
            return _loc_1;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            int _loc_3 =0;
            Array _loc_2 =new Array ();
            if (m_state == STATE_WITHERED)
            {
                _loc_3 = this.harvestingDefinition.cost * Global.gameSettings().getNumber("witherRefundMultiplier");
                _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_3), _loc_3));
            }
            else
            {
                _loc_2 = Global.player.processRandomModifiers(this.harvestingDefinition, this, true, m_secureRands);
            }
            return _loc_2;
        }//end

         public boolean  harvest ()
        {

            Debug.debug6("Plot."+"harvest");

            boolean _loc_1 =false ;
            boolean _loc_2 =false ;
            m_isHarvesting = false;
            if (!isHarvestable())
            {
                return false;
            }
            if (!Global.isVisiting())
            {
                _loc_1 = false;
                _loc_2 = false;
                if (m_firstHarvestAttempt && m_actionMode != VISIT_REPLAY_ACTION)
                {
                    _loc_1 = true;
                }
                if (m_actionMode == VISIT_REPLAY_ACTION)
                {
                    _loc_2 = true;
                }
                if (Global.player.commodities.isAtCommodityCapacity(m_contract, _loc_1))
                {
                    m_firstHarvestAttempt = false;
                    if (_loc_2)
                    {
                        displayStatus(ZLoc.t("Main", "NeedMoreStorage"));
                    }
                    return false;
                }
            }
            m_firstHarvestAttempt = true;
            Sounds.playFromSet(Sounds.SET_HARVEST, this);
            if (!Global.isVisiting())
            {
                m_doobersArray = this.makeDoobers();
                spawnDoobers();
                if (m_actionMode != VISIT_REPLAY_ACTION)
                {
                    GameTransactionManager.addTransaction(new THarvest(this));
                    trackAction(TrackedActionType.HARVEST);
                    Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                    this.giveMastery();
                }
                else
                {
                    if (m_giveVisitMastery)
                    {
                        this.giveMastery();
                    }
                    m_giveVisitMastery = false;
                }
            }
            else
            {
                onVisitorHelp(STATE_GROWN);
            }
            setState(STATE_PLOWED);
            plantTime = 0;
            return true;
        }//end

        protected void  giveMastery ()
        {
            _loc_1 = (MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)
            if (_loc_1 && this.harvestingDefinition && Global.player.isEligibleForMastery(this.harvestingDefinition, false))
            {
                _loc_1.updateMastery(this.harvestingDefinition.name, 1);
                if (!_loc_1.isMaxLevel(this.harvestingDefinition.name))
                {
                    showMasteryProgressFloaterText(1, "CropMasteryProgress");
                }
            }
            return;
        }//end

         public boolean  revive ()
        {
            NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_REVIVEDCROPS);
            return super.revive();
        }//end

         public int  getCoinYield ()
        {
            _loc_1 = m_contract!= null ? (m_contract.coinYield) : (m_item.coinYield);
            return Math.floor(_loc_1);
        }//end

         public int  getXpYield ()
        {
            _loc_1 = m_contract!= null ? (m_contract.xpYield) : (m_item.xpYield);
            return Math.floor(_loc_1);
        }//end

         protected int  friendVisitRepGain ()
        {
            return Global.gameSettings().getInt("friendVisitPlotRepGain", 1);
        }//end

         public Object  doHarvestDropOff (boolean param1 =true )
        {
            if (param1 && m_contract)
            {
                displayDelayedResourceChanges();
            }
            m_contract = null;
            return super.doHarvestDropOff(param1);
        }//end

         public boolean  plant (String param1 )
        {

            Debug.debug4("Plot."+"plant");

            Item _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            boolean _loc_8 =false ;
            boolean _loc_2 =false ;
            if (isPlantable(param1))
            {
                _loc_3 = Global.gameSettings().getItemByName(param1);
                if (_loc_3 && _loc_3.type == "plot_contract" || _loc_3.type == "factory_contract")
                {
                    _loc_4 = _loc_3.GetItemSalePrice();
                    _loc_5 = _loc_3.cost;
                    _loc_6 = _loc_3.cash;
                    _loc_7 = _loc_3.plantXp;
                    _loc_8 = false;
                    if (_loc_6 > 0 && Global.player.canBuyCash(_loc_4))
                    {
                        Global.player.cash = Global.player.cash - _loc_4;
                        _loc_8 = true;
                    }
                    else if (_loc_5 > 0 && Global.player.canBuy(_loc_4))
                    {
                        Global.player.gold = Global.player.gold - _loc_4;
                        _loc_8 = true;
                    }
                    if (_loc_8)
                    {
                        Global.player.xp = Global.player.xp + _loc_7;
                        plantTime = GlobalEngine.getTimer();
                        Sounds.playFromSet(Sounds.SET_PLANT);
                        m_contract = _loc_3;
                        m_contractGrowTime = _loc_3.growTime;
                        m_witherOn = _loc_3.allowWither;
                        setState(STATE_PLANTED);
                        GameTransactionManager.addTransaction(new TFactoryContractStart(this), true);
                        trackAction(TrackedActionType.PLANT);
                        _loc_2 = true;
                    }
                }
            }
            else if (!hasValidId())
            {
                showObjectBusy();
            }
            if (_loc_2)
            {
                this.adjustModesAfterPlanting();
            }
            return _loc_2;
        }//end

        protected void  adjustModesAfterPlanting ()
        {
            Plot _loc_2 =null ;
            _loc_1 = Global.world.getObjectsByClass(Plot);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.getItem().type == "plot" && _loc_2.m_state == STATE_PLOWED)
                {
                    return;
                }
            }
            if (Global.world.getTopGameMode() instanceof GMPlant)
            {
                Global.world.addGameMode(new GMPlay());
            }
            return;
        }//end

         protected String  getContractFriendlyName ()
        {
            if (!m_contract)
            {
                return "";
            }
            return ZLoc.t("Items", m_contract.name + "_friendlyName");
        }//end

         public double  getHarvestTime ()
        {
            if (!Global.isVisiting() && m_state == STATE_WITHERED)
            {
                return Global.gameSettings().getNumber("actionBarCropClear");
            }
            return Global.gameSettings().getNumber("actionBarPlotActions");
        }//end

         public String  getActionText ()
        {
            if (m_state == STATE_PLANTED && !(m_actionMode == VISIT_PLAY_ACTION || m_actionMode == VISIT_REPLAY_ACTION))
            {
                return getFinishingText();
            }
            return super.getActionText();
        }//end

         public Function  getProgressBarStartFunction ()
        {
            HarvestableResource harvestResource ;
            harvestResource;
            return boolean  ()
            {
                _loc_1 = undefined;
                switch(m_state)
                {
                    case STATE_PLANTED:
                    {
                        break;
                    }
                    case STATE_GROWN:
                    {
                        if (m_actionMode == VISIT_REPLAY_ACTION)
                        {
                        }
                        if (!Global.player.checkEnergy(-_loc_1))
                        {
                        }
                        if (Global.player.commodities.isAtCommodityCapacity(m_contract))
                        {
                        }
                        break;
                    }
                    case STATE_WITHERED:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                switch(m_state)
                {
                    case STATE_PLANTED:
                    {
                        break;
                    }
                    case STATE_GROWN:
                    {
                        if (Global.player.commodities.isAtCommodityCapacity(m_contract))
                        {
                        }
                        break;
                    }
                    case STATE_WITHERED:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                m_isHarvesting = false;
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                return;
            }//end
            ;
        }//end

         public String  getVisitReplayEquivalentActionString ()
        {
            String _loc_1 ="";
            switch(this.getState())
            {
                case STATE_GROWN:
                {
                    _loc_1 = "harvest";
                    break;
                }
                case STATE_PLANTED:
                case STATE_WITHERED:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

        public static boolean  IsMarketClickedPlotSet ()
        {
            return marketClickedPlot != null;
        }//end

        private static void  SetMarketClickedPlot (Plot param1 )
        {
            marketClickedPlot = param1;
            return;
        }//end

        public static void  ResetMarketClickedPlotSet ()
        {
            marketClickedPlot = null;
            return;
        }//end

        public static void  SetContactToMarketClickedPlot (String param1 )
        {
            marketClickedPlot.plant(param1);
            ResetMarketClickedPlotSet();
            return;
        }//end

    }




