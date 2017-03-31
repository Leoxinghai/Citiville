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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Events.*;
import GameMode.*;
import Mechanics.*;
import Modules.quest.Helpers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class Ship extends HarvestableResource
    {
        private  String SHIP ="ship";
        private  int WITHERED_HIGHLIGHT_COLOR =11141120;
        private String m_itemImageName ;
        private boolean m_isNewShip =true ;
        protected SeaVehicle m_shipNPC ;
        private double m_PathLength =0;
        private double m_ReturnTime =0;
        private boolean m_ShipReturnTrigger =false ;
        public static  String STATE_EMPTY ="plowed";
        public static  String STATE_AWAY ="planted";
        public static  String STATE_LOADED ="grown";
        private static int shippingLaneX =0;
        private static int shippingLaneYLeft =0;
        private static int shippingLaneYRight =0;
        private static IPlantable marketClickedShip =null ;
        private static int intShipNPCCount =0;
        public static  String IS_SAILING ="planting";
        public static  String IS_UNLOADING ="harvesting";
        public static  String IS_CLEANING ="plowing";
        private static Catalog m_catalogWindow ;

        public  Ship (String param1 ,int param2)
        {
            if (shippingLaneX == 0)
            {
                shippingLaneX = Global.gameSettings().getInt("shippingLaneX");
            }
            if (shippingLaneYLeft == 0)
            {
                shippingLaneYLeft = Global.gameSettings().getInt("shippingLaneYLeft");
            }
            if (shippingLaneYRight == 0)
            {
                shippingLaneYRight = Global.gameSettings().getInt("shippingLaneYRight");
            }
            super(param1);
            m_objectType = WorldObjectTypes.SHIP;
            m_typeName = this.SHIP;
            this.m_PathLength = 0;
            return;
        }//end

         public Item  harvestingDefinition ()
        {
            return m_contract != null ? (m_contract) : (m_item);
        }//end

         protected String  getLayerName ()
        {
            return this.getShipLayerBasedOnPierBerth();
        }//end

         public Object  getSaveObject ()
        {
            this.m_isNewShip = false;
            _loc_1 = super.getSaveObject();
            if (m_contract)
            {
                _loc_1.contractName = m_contract.name;
            }
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            this.m_isNewShip = false;
            m_contract = Global.gameSettings().getItemByName(param1.contractName);
            m_contractGrowTime = m_contract ? (m_contract.growTime) : (0);
            super.loadObject(param1);
            return;
        }//end

         public boolean  isWitherOn ()
        {
            if (Global.world.citySim.unwitherManager.isWitheringDisabledGlobally)
            {
                return false;
            }
            return super.isWitherOn;
        }//end

        public boolean  isUnloadable ()
        {
            return super.isHarvestable();
        }//end

        public boolean  isEmpty ()
        {
            return super.isPlantable(null);
        }//end

        public boolean  isAway ()
        {
            return super.isPlanted();
        }//end

        public boolean  isCleanable ()
        {
            return super.isPlowable();
        }//end

        public boolean  isLoaded ()
        {
            return super.isGrown();
        }//end

        public boolean  goodsRunInProgress ()
        {
            return this.m_shipNPC != null;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            return super.getCurrentImage();
        }//end

        private void  resetNPCPathData ()
        {
            this.m_PathLength = 0;
            this.m_ReturnTime = 0;
            this.m_ShipReturnTrigger = false;
            return;
        }//end

         public void  setFullGrown ()
        {
            super.setFullGrown();
            this.cancelShipNPC();
            return;
        }//end

         protected Class  getPlowedCursor ()
        {
            return EmbeddedArt.hud_biz_supply;
        }//end

         protected Class  getGrownCursor ()
        {
            if (!m_isHarvesting && Global.player.checkEnergy(-m_contract.harvestEnergyCost, false))
            {
                return EmbeddedArt.hud_biz_supply;
            }
            return null;
        }//end

         public boolean  isHighlightable ()
        {
            return super.isHighlightable && (m_state != STATE_PLANTED || Global.world.isEditMode);
        }//end

         protected Class  getWitheredCursor ()
        {
            return EmbeddedArt.hud_act_clean;
        }//end

         protected boolean  isWrongPlantContract ()
        {
            _loc_1 = GMPlant(Global.world.getTopGameMode());
            if (_loc_1.m_contractClass == "ship_contract")
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

         protected void  onStateChanged (String param1 ,String param2 )
        {
            Array _loc_3 =null ;
            Pier _loc_4 =null ;
            super.onStateChanged(param1, param2);
            if (param1 != param2)
            {
                _loc_3 = Global.world.getObjectsByClass(Pier);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    if (this.connectedToPierNonExclusive(_loc_4))
                    {
                        MechanicManager.setForceDisplayRefresh("pierpick", _loc_4.getId(), true);
                    }
                }
            }
            return;
        }//end

        protected void  onContractImageLoaded (LoaderEvent event )
        {
            event.target.removeEventListener(LoaderEvent.LOADED, this.onContractImageLoaded);
            reloadImage();
            return;
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

         public Object  getCustomCursor ()
        {
            DisplayObject _loc_1 =null ;
            MarginBackground _loc_2 =null ;
            ASFont _loc_3 =null ;
            GlowFilter _loc_4 =null ;
            if (m_state == STATE_AWAY && !(Global.world.getTopGameMode() instanceof GMPlant))
            {
                if (!m_customCursor)
                {
                    m_customCursor = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    m_customCursorLabel = new JLabel("");
                    m_customCursorHolder = new Sprite();
                    Global.ui.addChild(m_customCursorHolder);
                    m_customCursorWindow = new JWindow(m_customCursorHolder);
                    _loc_3 = ASwingHelper.getBoldFont(16);
                    _loc_4 = new GlowFilter(0, 1, 1.2, 1.2, 20, BitmapFilterQuality.HIGH);
                    m_customCursorLabel.setForeground(new ASColor(16777215));
                    m_customCursorLabel.setFont(_loc_3);
                    m_customCursorLabel.setTextFilters(.get(_loc_4));
                }
                _loc_1 = null;
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_zap();
                m_customCursorLabel.setText(m_contract.instantFinishEnergyCost.toString());
                m_customCursorLabel.visible = true;
                _loc_2 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                m_customCursor.setBackgroundDecorator(_loc_2);
                m_customCursor.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.append(m_customCursorLabel);
                m_customCursorWindow.setContentPane(m_customCursor);
                ASwingHelper.prepare(m_customCursorWindow);
                m_customCursorWindow.show();
                return m_customCursorWindow;
            }
            return null;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = super.getToolTipAction();
            switch(m_state)
            {
                case STATE_LOADED:
                {
                    break;
                }
                case STATE_AWAY:
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
            if (m_state == STATE_WITHERED)
            {
            }
            if (m_state == STATE_LOADED)
            {
                if (!Global.player.checkEnergy(-m_item.harvestEnergyCost, false))
                {
                }
                else
                {
                }
            }
            if (m_state == STATE_EMPTY)
            {
                if (Global.world.getTopGameMode() instanceof GMPlant)
                {
                    if (this.isWrongPlantContract())
                    {
                    }
                }
            }
            return _loc_1;
        }//end

         public void  onPlayAction ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            if (m_visitReplayLock > 0)
            {
                return;
            }
            super.onPlayAction();
            if (Global.isVisiting())
            {
                return;
            }
            if (!hasValidId())
            {
                showObjectBusy();
                return;
            }
            m_actionMode = PLAY_ACTION;
            switch(m_state)
            {
                case STATE_EMPTY:
                {
                    if (Global.guide.isActive())
                    {
                        break;
                    }
                    if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.MARKET_ASSETS))
                    {
                        break;
                    }
                    SetMarketClickedShip(this);
                    m_catalogWindow = UI.displayCatalog(new CatalogParams("ship_contract").setExclusiveCategory(true).setOverrideTitle("ship_contract").setCloseMarket(true).setIgnoreExcludeExperiments(true), false, true);
                    _loc_1 = 1 + m_item.harvestMultiplier / 100;
                    m_catalogWindow.updatePriceMultiplier(_loc_1);
                    m_catalogWindow.setFirstDataMultiplier(_loc_1);
                    if (!m_catalogWindow.hasEventListener(Event.CLOSE))
                    {
                        m_catalogWindow.addEventListener(Event.CLOSE, this.onMarketCloseClick);
                    }
                    break;
                }
                case STATE_AWAY:
                {
                    this.processAwayState();
                    break;
                }
                case STATE_LOADED:
                {
                    _loc_2 = m_contract.harvestEnergyCost;
                    if (!Global.player.checkEnergy(-_loc_2))
                    {
                        displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                    }
                    else if (!m_isHarvesting)
                    {
                        if (!isHarvestable())
                        {
                            break;
                        }
                        if (m_firstHarvestAttempt && Global.player.commodities.isAtCommodityCapacity(m_contract))
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
                    this.plow();
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
            m_actionMode = VISIT_PLAY_ACTION;
            switch(m_state)
            {
                case STATE_AWAY:
                {
                    Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);
                    break;
                }
                case STATE_LOADED:
                {
                    if (isHarvestable())
                    {
                        m_isHarvesting = true;
                        setHighlighted(false);
                        Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);
                    }
                    break;
                }
                case STATE_WITHERED:
                {
                    Global.world.citySim.pickupManager.enqueue("NPC_farmer", this);
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
            m_actionMode = VISIT_REPLAY_ACTION;
            switch(m_state)
            {
                case STATE_AWAY:
                {
                    break;
                }
                case STATE_LOADED:
                {
                    if (isHarvestable())
                    {
                        m_isHarvesting = true;
                        setHighlighted(false);
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
            GameTransactionManager.addTransaction(param1);
            this.getProgressBarEndFunction()();
            return super.onVisitReplayAction(null);
        }//end

         public boolean  isVisitorInteractable ()
        {
            switch(m_state)
            {
                case STATE_AWAY:
                {
                    return true;
                }
                case STATE_LOADED:
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
            this.cancelShipNPC();
            if (m_contract instanceof Item)
            {
                displayStatus(ZLoc.t("Main", "ShipCancelContractSuccess", {crop:m_contract.localizedName}));
                m_contract = null;
            }
            setState(STATE_EMPTY);
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
            DisplayObject _loc_4 =null ;
            int _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            boolean _loc_1 =false ;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CV_UNWITHER);
            boolean _loc_3 =false ;
            if (isWithered())
            {
                _loc_3 = Global.player.hasSeenWitherPopup_ship;
                if (_loc_3 == false && !Global.isVisiting() && Global.player.level >= 6 && _loc_2 > ExperimentDefinitions.UNWITHER_CONTROL_TWO)
                {
                    _loc_4 = new UnwitherDialog(this);
                    UI.displayPopup(_loc_4, true, "unwitherCrops", false);
                    return true;
                }
                if (m_contract)
                {
                    _loc_5 = Global.gameSettings().getInt("plowXp");
                    _loc_6 = Global.gameSettings().getNumber("witherRefundMultiplier", 0);
                    _loc_7 = Math.floor(m_contract.cost * _loc_6);
                    doResourceChanges(0, _loc_7, _loc_5);
                }
                Sounds.play("ships_unloaded");
                setState(STATE_EMPTY);
                GameTransactionManager.addTransaction(new TClearWithered(this));
                setHighlighted(true);
                trackAction(TrackedActionType.CLEAR);
                return true;
            }
            if (this.isCleanable())
            {
                _loc_8 = Global.gameSettings().getInt("plowCost");
                _loc_9 = Global.gameSettings().getInt("plowXp");
                if (Global.player.canBuy(_loc_8))
                {
                    doResourceChanges(0, -_loc_8, _loc_9);
                    setState(STATE_EMPTY);
                    GameTransactionManager.addTransaction(new TPlow(this), true);
                    _loc_1 = true;
                }
                else if (this.m_isNewShip && m_state == STATE_FALLOW)
                {
                    detach();
                }
            }
            return _loc_1;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            if (this.isAway())
            {
                this.calculateShipReturnTime();
                this.checkAndDoShipReturns();
            }
            return;
        }//end

         public boolean  harvest ()
        {
            return this.doHarvest(true);
        }//end

        public boolean  doHarvest (boolean param1 )
        {
            Pier _loc_3 =null ;
            m_isHarvesting = false;
            if (!this.isUnloadable())
            {
                return false;
            }
            if (!Global.isVisiting() && m_firstHarvestAttempt)
            {
                if (Global.player.commodities.isAtCommodityCapacity(m_contract, param1))
                {
                    m_firstHarvestAttempt = false;
                    return false;
                }
            }
            m_firstHarvestAttempt = true;
            Sounds.play("ships_unloaded");
            _loc_2 =Global.world.getObjectsByClass(Pier );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (this.connectedToPierNonExclusive(_loc_3))
                {
                    _loc_3.incrementUpgradeActionCount(false);
                }
            }
            if (!Global.isVisiting())
            {
                m_doobersArray = Global.player.processRandomModifiers(this.harvestingDefinition, this, true, m_secureRands);
                Global.world.dooberManager.createBatchDoobers(m_doobersArray, this.harvestingDefinition, m_position.x, m_position.y);
                if (m_actionMode != VISIT_REPLAY_ACTION)
                {
                    GameTransactionManager.addTransaction(new THarvest(this));
                    trackAction(TrackedActionType.HARVEST);
                    Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                }
            }
            else
            {
                onVisitorHelp(STATE_GROWN);
            }
            setState(STATE_EMPTY);
            plantTime = 0;
            return true;
        }//end

         public boolean  revive ()
        {
            NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_CLEANEDSHIPS);
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
            return Global.gameSettings().getInt("friendVisitShipRepGain", 1);
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
            Item _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            boolean _loc_2 =false ;
            if (this.isEmpty())
            {
                _loc_3 = Global.gameSettings().getItemByName(param1);
                if (_loc_3 && _loc_3.type == "ship_contract")
                {
                    _loc_4 = Math.round(_loc_3.cost * (1 + m_item.harvestMultiplier / 100));
                    _loc_5 = _loc_3.plantXp;
                    if (Global.player.canBuy(_loc_4))
                    {
                        Global.player.gold = Global.player.gold - _loc_4;
                        plantTime = GlobalEngine.getTimer();
                        Sounds.play("ship_leaves");
                        m_contract = _loc_3;
                        m_contractGrowTime = _loc_3.growTime;
                        m_witherOn = _loc_3.allowWither;
                        setState(STATE_AWAY);
                        GameTransactionManager.addTransaction(new TFactoryContractStart(this), true);
                        trackAction(TrackedActionType.PLANT);
                        this.startEmptyShipNPC();
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

         public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            String _loc_1 =null ;
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                switch(m_state)
                {
                    case STATE_EMPTY:
                    {
                        _loc_1 = ZLoc.t("Main", "EmptyShip");
                        break;
                    }
                    case STATE_AWAY:
                    {
                        _loc_2 = GameUtil.formatMinutesSeconds(getGrowTimeLeft());
                        _loc_1 = ZLoc.t("Main", "AwayShip", {time:_loc_2});
                        break;
                    }
                    case STATE_LOADED:
                    {
                        _loc_1 = ZLoc.t("Main", "LoadedShip");
                        break;
                    }
                    case STATE_WITHERED:
                    {
                        _loc_3 = Global.gameSettings().getInt("plowCost");
                        if (Global.player.canBuy(_loc_3, false))
                        {
                            _loc_1 = ZLoc.t("Main", "WitheredGoods");
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "WitheredGoods_NoMoney");
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  connectedToPierNonExclusive (Pier param1 )
        {
            return param1.isValidShipBerth(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y));
        }//end

        public boolean  connectedToPier (Pier param1 )
        {
            Pier _loc_3 =null ;
            if (!param1.isValidShipBerth(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)))
            {
                return false;
            }
            _loc_2 =Global.world.getObjectsByClass(Pier );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3 == param1)
                {
                    continue;
                }
                if (_loc_3.isValidShipBerth(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)))
                {
                    return false;
                }
            }
            return true;
        }//end

        private boolean  checkCollisionWithShipSquare (double param1 ,double param2 )
        {
            int _loc_4 =0;
            _loc_3 = m_position.x;
            while (_loc_3 < m_position.x + m_size.x)
            {

                _loc_4 = m_position.y;
                while (_loc_4 < m_position.y + m_size.y)
                {

                    if (param1 == _loc_3 && param2 == _loc_4)
                    {
                        return true;
                    }
                    _loc_4++;
                }
                _loc_3++;
            }
            return false;
        }//end

        public boolean  collidedWithShip (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (this.checkCollisionWithShipSquare(_loc_2, _loc_3))
                    {
                        return true;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return false;
        }//end

        public boolean  isValidShipPosition ()
        {
            Pier _loc_3 =null ;
            Ship _loc_4 =null ;
            Pier _loc_5 =null ;
            _loc_1 = Global.world.getObjectsByClass(Pier);
            _loc_2 = Global.world.getObjectsByClass(Ship);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                if (_loc_3.shipCollidedWithDock(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)))
                {
                    return false;
                }
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                if (_loc_4 == this)
                {
                    continue;
                }
                if (_loc_4.collidedWithShip(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)))
                {
                    return false;
                }
            }
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_5 = _loc_1.get(i0);

                if (_loc_5.isValidShipBerth(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)))
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  forceSell ()
        {
            super.sellNow();
            this.cancelShipNPC();
            return;
        }//end

        public Rectangle  getShipRectangle ()
        {
            return new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y);
        }//end

        public void  forceMove (Vector3 param1 )
        {
            _loc_2 = thisas MapResource ;
            Object _loc_3 =new Object ();
            _loc_3.x = Math.round(this.m_position.x - param1.x);
            _loc_3.y = Math.round(this.m_position.y - param1.y);
            _loc_3.state = _loc_2.getState();
            _loc_3.direction = _loc_2.getDirection();
            _loc_3 = _loc_2.addTMoveParams(_loc_3);
            GameTransactionManager.addTransaction(new TMove(_loc_2, _loc_3));
            this.setPosition(_loc_3.x, _loc_3.y);
            this.conditionallyReattach();
            if (this.m_shipNPC && this.m_ShipReturnTrigger)
            {
                this.cancelShipNPC();
            }
            return;
        }//end

        public void  forceCurrentPosition ()
        {
            _loc_1 = thisas MapResource ;
            Object _loc_2 =new Object ();
            _loc_2.x = Math.round(this.m_position.x);
            _loc_2.y = Math.round(this.m_position.y);
            _loc_2.state = _loc_1.getState();
            _loc_2.direction = _loc_1.getDirection();
            _loc_2 = _loc_1.addTMoveParams(_loc_2);
            GameTransactionManager.addTransaction(new TMove(_loc_1, _loc_2));
            this.setPosition(_loc_2.x, _loc_2.y);
            this.conditionallyReattach();
            if (this.m_shipNPC && this.m_ShipReturnTrigger)
            {
                this.cancelShipNPC();
            }
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            this.resetShipGameLayer();
            _loc_2 =Global.world.getTopGameMode ();
            return;
        }//end

        private double  getShippingLaneExitY (double param1 )
        {
            if (param1 < 0)
            {
                return shippingLaneYRight;
            }
            return shippingLaneYLeft;
        }//end

        private void  calculateShipReturnTime ()
        {
            Array _loc_1 =null ;
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            NPC _loc_4 =null ;
            if (this.m_PathLength == 0)
            {
                _loc_1 = new Array();
                _loc_1.push(new Vector3(shippingLaneX, this.getShippingLaneExitY(this.m_position.y), 0));
                _loc_1.push(new Vector3(shippingLaneX, this.m_position.y, 0));
                _loc_1.push(new Vector3(this.m_position.x, this.m_position.y, 0));
                _loc_2 = new Vector3(shippingLaneX, this.getShippingLaneExitY(this.m_position.y), 0);
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_3 = _loc_1.get(i0);

                    this.m_PathLength = this.m_PathLength + MathUtil.distance(new Point(_loc_3.x, _loc_3.y), new Point(_loc_2.x, _loc_2.y));
                    _loc_2 = _loc_3;
                }
                _loc_4 = new NPC(this.m_item.loadedNPCType, false);
                this.m_ReturnTime = this.m_PathLength / _loc_4.velocityWalk;
            }
            return;
        }//end

        private void  checkAndDoShipReturns ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            if (plantTime > 0)
            {
                _loc_1 = GlobalEngine.getTimer() - plantTime;
                _loc_2 = getGrowTimeDelta();
                if (_loc_1 < _loc_2)
                {
                    _loc_3 = (_loc_2 - _loc_1) / 1000;
                    if (_loc_3 < this.m_ReturnTime && !this.m_ShipReturnTrigger)
                    {
                        this.startLoadedShipNPC();
                    }
                }
            }
            return;
        }//end

        private void  startEmptyShipNPC ()
        {
            Array _loc_1 =new Array ();
            _loc_1.push(new Vector3(shippingLaneX, this.m_position.y, 0));
            _loc_1.push(new Vector3(shippingLaneX, this.getShippingLaneExitY(this.m_position.y), 0));
            this.m_shipNPC = Global.world.citySim.npcManager.createShipByNameAtPosition(this.m_item.emptyNPCType, this.m_position, _loc_1, false);
            _loc_3 = intShipNPCCount+1;
            intShipNPCCount = _loc_3;
            return;
        }//end

        public boolean  playShipLoopSound ()
        {
            return intShipNPCCount > 0;
        }//end

        private void  startLoadedShipNPC ()
        {
            this.cancelShipNPC();
            this.m_ShipReturnTrigger = true;
            Vector3 _loc_1 =new Vector3(shippingLaneX ,this.getShippingLaneExitY(this.m_position.y ),0);
            Array _loc_2 =new Array ();
            _loc_2.push(new Vector3(shippingLaneX, this.m_position.y, 0));
            _loc_2.push(new Vector3(this.m_position.x, this.m_position.y, 0));
            this.m_shipNPC = Global.world.citySim.npcManager.createShipByNameAtPosition(this.m_item.loadedNPCType, _loc_1, _loc_2, false);
            this.m_shipNPC.setIsIncoming();
            _loc_4 = intShipNPCCount+1;
            intShipNPCCount = _loc_4;
            return;
        }//end

        private void  cancelShipNPC ()
        {
            if (this.goodsRunInProgress())
            {
                Global.world.citySim.npcManager.removeShip(this.m_shipNPC);
                this.resetNPCPathData();
                this.m_shipNPC = null;
            }
            return;
        }//end

         public String  getActionText ()
        {
            if (m_state == STATE_PLANTED && !(m_actionMode == VISIT_PLAY_ACTION || m_actionMode == VISIT_REPLAY_ACTION))
            {
                return getFinishingText();
            }
            return super.getActionText();
        }//end

         public String  getWateringText ()
        {
            return ZLoc.t("Main", "Guiding");
        }//end

         public String  getRevivingText ()
        {
            return ZLoc.t("Main", "Salvaging");
        }//end

         public String  getHarvestingText ()
        {
            return ZLoc.t("Main", "Unloading");
        }//end

         public double  getHarvestTime ()
        {
            return Global.gameSettings().getNumber("actionBarPlotSpeedup");
        }//end

         public Function  getProgressBarStartFunction ()
        {
            HarvestableResource harvestResource ;
            harvestResource;
            return boolean  ()
            {
                _loc_1 = null;
                switch(m_state)
                {
                    case STATE_AWAY:
                    {
                        break;
                    }
                    case STATE_LOADED:
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
                    default:
                    {
                        break;
                    }
                }
                switch(m_state)
                {
                    case STATE_AWAY:
                    {
                        break;
                    }
                    case STATE_LOADED:
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

        private String  getShipLayerBasedOnPierBerth ()
        {
            Pier _loc_2 =null ;
            _loc_1 = Global.world.getObjectsByClass(Pier);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.getShipLayer(new Rectangle(m_position.x, m_position.y, m_size.x, m_size.y)) == 0)
                {
                    return "backship";
                }
            }
            return "frontship";
        }//end

        public void  resetShipGameLayer ()
        {
            this.detatch();
            this.attach();
            return;
        }//end

         public String  getVisitReplayEquivalentActionString ()
        {
            String _loc_1 ="";
            switch(this.getState())
            {
                case STATE_LOADED:
                {
                    _loc_1 = "harvest";
                    break;
                }
                case STATE_AWAY:
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

        private void  processAwayState ()
        {
            DisplayObject _loc_3 =null ;
            _loc_1 = Global.player.hasSeenInstantPopup_ship;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_CROPS_SHIPS);
            if (_loc_1 == false && !Global.isVisiting() && Global.player.level >= 6 && _loc_2 > 1)
            {
                _loc_3 = new InstantReadyDialog(this);
                UI.displayPopup(_loc_3, true, "instantReadyShips", false);
            }
            return;
        }//end

        public static void  decrementShipCounter (SeaVehicle param1 )
        {
            if (intShipNPCCount > 0)
            {
                _loc_3 = intShipNPCCount-1;
                intShipNPCCount = _loc_3;
            }
            if (intShipNPCCount == 0)
            {
                Sounds.stop("ship_sailing");
            }
            if (param1.isIncoming())
            {
                Sounds.play("ship_approaches");
            }
            return;
        }//end

        public static void  placeFreeShip (String param1 ,double param2 ,double param3 ,double param4 )
        {
            Ship _loc_5 =new Ship(param1 );
            _loc_5.setPosition(Math.round(param2), Math.round(param3), 0);
            _loc_5.setDirection(Math.round(param4));
            _loc_5.setOuter(Global.world);
            _loc_5.setVisible(true);
            if (_loc_5.isValidShipPosition())
            {
                GameTransactionManager.addTransaction(new TPlaceMapResource(_loc_5));
                _loc_5.attach();
            }
            return;
        }//end

        public static boolean  IsMarketClickedShipSet ()
        {
            return marketClickedShip != null;
        }//end

        public static void  SetMarketClickedShip (IPlantable param1 )
        {
            marketClickedShip = param1;
            return;
        }//end

        public static void  ResetMarketClickedShipSet ()
        {
            marketClickedShip = null;
            return;
        }//end

        public static void  SetContactToMarketClickedShip (String param1 )
        {
            marketClickedShip.plant(param1);
            ResetMarketClickedShipSet();
            return;
        }//end

    }




