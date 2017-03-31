package Classes.sim;

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
import Classes.actions.*;
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.orders.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.TrainUI.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.trains.*;
import Modules.workers.*;
import Modules.workers.transactions.*;
import Transactions.train.*;

import com.greensock.*;
//import flash.events.*;
//import flash.geom.*;

    public class TrainManager extends EventDispatcher implements IGameWorldUpdateObserver
    {
        private GameWorld m_world ;
        private TrainStation m_trainStation ;
        private TrainTracks m_trainTracks ;
        private Array m_crossings ;
        private Train m_train ;
        private double m_minTileX =0;
        private double m_minTileY =0;
        private double m_maxTileX =0;
        private double m_maxTileY =0;
        private Vector<TrainTrip> m_tripQueue;
        private TrainTrip m_currentTrip ;
        private double m_trainTerminalVelocity =1;
        private int m_trainArrivalTime =0;
        private int m_trainRewardAmount =0;
        private double m_trainSpeed =0;
        private boolean m_trainHasClearance =false ;
        private double m_trainDecelerationDistanceFromStation =0;
        private boolean m_hasTrainBegunStop =false ;
        private int m_trainState =0;
        private Vector3 m_trainStartPos ;
        private Vector3 m_trainEndPos ;
        private TrainStationDialog m_trainStationDialog ;
        private Item m_purchaseCandidateItem ;
        private Vector<TrainStopCell> m_trainStopCells;
        private TrainStopCell m_lastTrainStopCellRolledOver ;
        private TrainStopTooltip m_trainStopCellTooltip ;
        private double m_lastWorkerSyncTime ;
        private boolean m_hasPerformedAnticipatedArrivalSync =false ;
        public static  double TRAIN_FADE_TIME =1;
        public static  double TRAIN_ARRIVAL_TIME =4;
        public static  double TRAIN_VELOCITY_DELTA =2;
        public static  double SCROLL_TO_TRAIN_STATION_DURATION =1;
        public static  String TRAIN_SOUND_LEAVES ="train_leaves";
        public static  String TRAIN_SOUND_MOVING ="train_moving";
        public static  String TRAIN_SOUND_ARRIVES ="train_arrives";
        public static  int WORKER_SYNC_INTERVAL =30;
        public static  int WORKER_SYNC_BEFORE_ARRIVAL =30;
        public static  int TRAIN_STATE_IDLE_AWAY =0;
        public static  int TRAIN_STATE_EN_ROUTE =1;
        public static  int TRAIN_STATE_IDLE_AT_STATION =2;
        public static  int TRAIN_STATE_DEPARTING =3;
        public static  int TRAIN_STATE_ARRIVING =4;
        public static  int TRAIN_STATE_AWAITING_CLEARANCE =5;

        public  TrainManager (GameWorld param1 )
        {
            this.m_crossings = new Array();
            this.m_trainStopCells = new Vector<TrainStopCell>();
            this.m_world = param1;
            this.m_world.addObserver(this);
            this.m_tripQueue = new Vector<TrainTrip>();
            return;
        }//end

        public int  trainState ()
        {
            return this.m_trainState;
        }//end

        public boolean  isNeighborGated ()
        {
            return Global.player.neighbors.length < Global.gameSettings().getInt("trainRequiredNeighbors", 0);
        }//end

        public boolean  isBuyAllowed ()
        {
            return !Global.isVisiting() && Global.player.level >= Global.gameSettings().getInt("trainBuyMinLevel", 0) && !this.isNeighborGated;
        }//end

        public boolean  isSellAllowed ()
        {
            return !Global.isVisiting() && Global.player.level >= Global.gameSettings().getInt("trainSellMinLevel", 0) && !this.isNeighborGated;
        }//end

        public boolean  isOrderAllowed ()
        {
            return this.isBuyAllowed && this.isSellAllowed && !this.isWaitingForTrain;
        }//end

        public int  trainArrivalTime ()
        {
            return this.m_trainArrivalTime;
        }//end

        public int  trainRewardAmount ()
        {
            return this.m_trainRewardAmount;
        }//end

        public boolean  isWaitingForTrain ()
        {
            return this.m_currentTrip != null;
        }//end

        public boolean  isTrainAwaitingClearance ()
        {
            return this.m_trainState == TRAIN_STATE_AWAITING_CLEARANCE;
        }//end

        public Item  purchaseCandidateItem ()
        {
            return this.m_purchaseCandidateItem;
        }//end

        public TrainTrip  currentTrip ()
        {
            return this.m_currentTrip;
        }//end

        public boolean  canBuyNewStations ()
        {
            return this.isWaitingForTrain && !this.m_currentTrip.isWelcomeTrip && this.m_currentTrip.item.workers.amount > this.m_currentTrip.stopIds.length;
        }//end

        public boolean  canSpeedUp ()
        {
            return this.isWaitingForTrain && !this.m_currentTrip.isWelcomeTrip && (this.m_trainState == TRAIN_STATE_EN_ROUTE || this.m_trainState == TRAIN_STATE_DEPARTING);
        }//end

        public Array  trainStopData ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            Vector _loc_5.<String >=null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            Object _loc_9 =null ;
            Array _loc_1 =new Array();
            if (this.m_currentTrip && !this.m_currentTrip.isWelcomeTrip)
            {
                _loc_2 = this.m_currentTrip.item.trainPayout;
                _loc_3 = 0;
                _loc_4 = this.m_currentTrip.maxStops + 1;
                _loc_5 = this.m_currentTrip.stopIds;
                _loc_6 = 0;
                while (_loc_6 < _loc_4)
                {

                    _loc_7 = Global.gameSettings().getTieredInt(_loc_2, _loc_6);
                    _loc_8 = _loc_7 - _loc_3;
                    _loc_3 = _loc_7;
                    _loc_9 = {};
                    _loc_9.put("commodityType",  this.m_currentTrip.transactionType == TrainWorkers.OP_BUY ? ("goods") : ("coins"));
                    _loc_9.put("commodityIncrement",  _loc_8 >= 0 ? ("+" + _loc_8) : ("" + _loc_8));
                    if (_loc_6 == 0)
                    {
                        _loc_9.put("uid",  "-1");
                    }
                    else if (_loc_5.length > (_loc_6 - 1))
                    {
                        if (parseFloat(_loc_5.get((_loc_6 - 1))) < 0)
                        {
                            _loc_9.put("uid",  "-1");
                        }
                        else
                        {
                            _loc_9.put("uid",  _loc_5.get((_loc_6 - 1)));
                        }
                    }
                    _loc_1.push(_loc_9);
                    _loc_6++;
                }
            }
            return _loc_1;
        }//end

        public Array  candidateTrainStopData ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            Object _loc_8 =null ;
            Array _loc_1 =new Array();
            if (this.m_purchaseCandidateItem)
            {
                _loc_2 = this.m_purchaseCandidateItem.trainPayout;
                _loc_3 = 0;
                _loc_4 = this.m_purchaseCandidateItem.workers.amount + 1;
                _loc_5 = 0;
                while (_loc_5 < _loc_4)
                {

                    _loc_6 = Global.gameSettings().getTieredInt(_loc_2, _loc_5);
                    _loc_7 = _loc_6 - _loc_3;
                    _loc_3 = _loc_6;
                    _loc_8 = {};
                    _loc_8.put("commodityType",  this.m_purchaseCandidateItem.goods > 0 ? ("coins") : ("goods"));
                    _loc_8.put("commodityIncrement",  _loc_7 >= 0 ? ("+" + _loc_7) : ("" + _loc_7));
                    if (_loc_5 == 0)
                    {
                        _loc_8.put("uid",  "-1");
                    }
                    _loc_1.push(_loc_8);
                    _loc_5++;
                }
            }
            return _loc_1;
        }//end

        private void  createTrain ()
        {
            if (!this.m_train)
            {
                this.m_train = new Train("train", false);
                this.m_train.setOuter(this.m_world);
                this.m_train.attach();
                this.m_train.setDirection(Constants.DIRECTION_SW);
                this.m_train.setPosition(999, 999);
                this.m_train.displayObject.alpha = 0;
                this.m_train.getStateMachine().addActions(new NPCAction(this.m_train));
                this.m_trainTerminalVelocity = this.m_train.getItem().navigateXml.child("runSpeed");
            }
            return;
        }//end

        private void  syncWorkers ()
        {
            TrainWorkers _loc_1 =null ;
            TrainTrip _loc_2 =null ;
            if (Global.trainWorkerManager && (!this.m_currentTrip || this.m_currentTrip && !this.m_currentTrip.isWelcomeTrip))
            {
                _loc_1 =(TrainWorkers) Global.trainWorkerManager.getWorkers(getWorkerBucket());
                if (_loc_1)
                {
                    _loc_2 = new TrainTrip();
                    _loc_2.loadFromWorker(_loc_1);
                    this.m_currentTrip = _loc_2;
                }
            }
            return;
        }//end

        public void  registerTrainStopCell (TrainStopCell param1 )
        {
            this.m_trainStopCells.push(param1);
            return;
        }//end

        public void  grantTrainClearance ()
        {
            if (this.m_trainState == TRAIN_STATE_AWAITING_CLEARANCE)
            {
                this.m_trainHasClearance = true;
                TrainUI.showTrainAtStationHUDIcon(false, this);
            }
            return;
        }//end

        public void  speedUpCurrentTrain ()
        {
            TrainWorkers _loc_1 =null ;
            if (this.canSpeedUp)
            {
                if (Global.player.canBuyCash(this.m_currentTrip.item.trainSpeedUpCost, true))
                {
                    TransactionManager.addTransaction(new TSpeedUpTrain());
                    Global.player.cash = Global.player.cash - this.m_currentTrip.item.trainSpeedUpCost;
                    _loc_1 =(TrainWorkers) Global.trainWorkerManager.addAndGetWorkers(getWorkerBucket());
                    _loc_1.timeSent = _loc_1.timeSent - this.m_currentTrip.timeLeft;
                    this.m_currentTrip.loadFromWorker(_loc_1);
                }
            }
            return;
        }//end

        public void  purchaseNewStation ()
        {
            TrainWorkers _loc_1 =null ;
            if (this.canBuyNewStations && this.currentTrip.item.workers.amount > this.currentTrip.stopIds.length())
            {
                if (Global.player.canBuyCash(this.m_currentTrip.item.workers.cashCost, true))
                {
                    GameTransactionManager.addTransaction(new TWorkerPurchase(WorkerManager.FEATURE_TRAINS, getWorkerBucket()));
                    Global.player.cash = Global.player.cash - this.m_currentTrip.item.workers.cashCost;
                    _loc_1 =(TrainWorkers) Global.trainWorkerManager.addAndGetWorkers(getWorkerBucket());
                    _loc_1.addWorker(String(-(_loc_1.numPurchasedWorkers + 1)));
                    _loc_2 = _loc_1;
                    _loc_3 = _loc_1.numPurchasedWorkers +1;
                    _loc_2.numPurchasedWorkers = _loc_3;
                    this.m_currentTrip.loadFromWorker(_loc_1);
                    dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATION_LIST_UPDATE));
                }
            }
            return;
        }//end

        public void  scrollToTrainStation ()
        {
            this.m_world.centerOnObject(this.m_trainStation, SCROLL_TO_TRAIN_STATION_DURATION);
            return;
        }//end

        public void  showTrainStationDialog ()
        {
            if (this.m_trainState == TRAIN_STATE_ARRIVING || this.m_trainState == TRAIN_STATE_IDLE_AT_STATION)
            {
                return;
            }
            this.m_trainStopCells = new Vector<TrainStopCell>();
            this.m_trainStationDialog = new TrainStationDialog();
            this.m_trainStationDialog.addEventListener(Event.CLOSE, this.trainStationDialogCloseHandler);
            UI.displayPopup(this.m_trainStationDialog);
            if (this.m_trainState != TRAIN_STATE_IDLE_AWAY)
            {
                this.updateWorkers();
            }
            return;
        }//end

        public void  showTrainScheduleCatalog ()
        {
            if (!this.isNeighborGated)
            {
                if (!this.isWaitingForTrain)
                {
                    UI.displayCatalog(new CatalogParams("train").setExclusiveCategory(true).setOverrideTitle("train").setCloseMarket(true), false, true);
                }
                else
                {
                    this.showTrainStationDialog();
                }
            }
            return;
        }//end

        public void  purchaseWelcomeTrain ()
        {
            TrainTrip _loc_1 =new TrainTrip ();
            _loc_1.isWelcomeTrip = true;
            _loc_1.commodityType = Doober.DOOBER_KDBOMB;//Commodities.GOODS_COMMODITY;
            _loc_1.commodityAmount = 10;   //Global.gameSettings().getInt("welcomeTrainQuestAmount", 100);
            this.scrollToTrainStation();
            this.setCurrentTrip(_loc_1, true);

            TrainTrip _loc_2 =new TrainTrip ();
            _loc_2.isWelcomeTrip = true;
            _loc_2.commodityType = Doober.DOOBER_LRBOMB;
            _loc_2.commodityAmount = 10;
            this.setCurrentTrip(_loc_2, true);

            return;
        }//end

        public void  proposeSchedulePurchase (String param1 )
        {
            StatsManager.sample(100, "trains", "choose_train", param1);
            this.m_purchaseCandidateItem = Global.gameSettings().getItemByName(param1);
            this.showTrainStationDialog();
            return;
        }//end

        public void  performSchedulePurchase ()
        {
            TrainWorkers _loc_1 =null ;
            TrainTrip _loc_2 =null ;
            if (this.m_purchaseCandidateItem && this.isBuyAllowed && this.m_purchaseCandidateItem.name != "")
            {
                Global.trainWorkerManager.clearWorkers(getWorkerBucket());
                _loc_1 =(TrainWorkers) Global.trainWorkerManager.addAndGetWorkers(getWorkerBucket());
                _loc_1.itemName = this.m_purchaseCandidateItem.name;
                _loc_1.commodityType = Commodities.GOODS_COMMODITY;
                _loc_1.timeSent = GlobalEngine.getTimer() / 1000;
                if (this.m_purchaseCandidateItem.goods > 0)
                {
                    _loc_1.operation = TrainWorkers.OP_SELL;
                    Global.player.commodities.remove(Commodities.GOODS_COMMODITY, this.m_purchaseCandidateItem.goods);
                }
                else
                {
                    _loc_1.operation = TrainWorkers.OP_BUY;
                    Global.player.gold = Global.player.gold - this.m_purchaseCandidateItem.cost;
                }
                GameTransactionManager.addTransaction(new TSendTrain(_loc_1), true);
                _loc_2 = new TrainTrip();
                _loc_2.loadFromWorker(_loc_1);
                this.setCurrentTrip(_loc_2);
                this.trainDepart();
            }
            return;
        }//end

        public void  popRequestStationsFeed ()
        {
            String _loc_3 =null ;
            _loc_1 = this.m_currentTrip.itemName? (this.m_currentTrip.itemName) : (this.m_purchaseCandidateItem.name);
            _loc_2 = Global.world.viralMgr.sendTrainFeed(_loc_1,Global.player.cityName,getWorkerBucket());
            if (!_loc_2)
            {
                _loc_3 = ZLoc.t("Dialogs", "TrainUI_alreadyAsked_message");
                UI.displayMessage(_loc_3, GenericDialogView.TYPE_OK, null, "trainUIAlreadyAsked", false, null, "TrainUI_alreadyAsked");
            }
            return;
        }//end

        private void  setCurrentTrip (TrainTrip param1 ,boolean param2 =false )
        {
            switch(this.m_trainState)
            {
                case TRAIN_STATE_ARRIVING:
                case TRAIN_STATE_IDLE_AT_STATION:
                {
                    m_tripQueue.push(param1);
                    break;
                }
                default:
                {
                    m_tripQueue.push(param1);
                    break;
                }
            }
            this.pumpTrips();
            return;
        }//end

        private void  pumpTrips ()
        {
            if (this.m_tripQueue.length > 0 && !this.m_currentTrip)
            {
                this.m_currentTrip = this.m_tripQueue.shift();
                dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATION_LIST_UPDATE));
            }
            return;
        }//end

        private void  unsetCurrentTrip ()
        {
            this.m_currentTrip = null;
            this.m_trainStation.forceUpdateArrow();
            this.m_hasPerformedAnticipatedArrivalSync = false;
            return;
        }//end

        private void  completeCurrentTrip ()
        {
            TrainWorkers _loc_1 =null ;
            if (this.m_currentTrip)
            {
                if (this.m_currentTrip.isWelcomeTrip)
                {
                    GameTransactionManager.addTransaction(new TTrainWelcomeOrder());
                }
                else
                {
                    _loc_1 =(TrainWorkers) Global.trainWorkerManager.getWorkers(getWorkerBucket());
                    if (_loc_1)
                    {
                        GameTransactionManager.addTransaction(new TAcceptTrain());
                        Global.trainWorkerManager.clearWorkers(getWorkerBucket());
                    }
                }
            }
            return;
        }//end

        private void  spawnRewardDoobers ()
        {
            Array _loc_1 =null ;
            if (this.m_currentTrip)
            {
                if (this.m_world.dooberManager.isDoobersEnabled())
                {
                    _loc_1 = this.generateDoobers(this.m_currentTrip.coins, 0, this.m_currentTrip.commodityAmount, this.m_currentTrip.commodityType);
                    this.m_world.dooberManager.createBatchDoobers(_loc_1, null, this.m_trainStation.positionX, this.m_trainStation.positionY);
                }
                else
                {
                    if (this.m_currentTrip.commodityAmount >= 0)
                    {
                        Global.player.commodities.add(this.m_currentTrip.commodityType, this.m_currentTrip.commodityAmount);
                    }
                    this.m_trainStation.doResourceChanges(0, this.m_currentTrip.coins, 0, this.m_currentTrip.commodityAmount, this.m_currentTrip.commodityType);
                }
            }
            return;
        }//end

        private void  updateWorkers (boolean param1 =false )
        {
            TWorkersSync _loc_2 =null ;
            if (param1 || this.m_lastWorkerSyncTime + WORKER_SYNC_INTERVAL < GlobalEngine.getTimer() / 1000)
            {
                this.m_lastWorkerSyncTime = GlobalEngine.getTimer() / 1000;
                _loc_2 = new TWorkersSync(WorkerManager.FEATURE_TRAINS, Global.trainWorkerManager);
                _loc_2.addEventListener(Event.COMPLETE, this.workerSyncHandler);
                GameTransactionManager.addTransaction(_loc_2, true);
            }
            return;
        }//end

        private void  trainAwaitClearance ()
        {
            this.m_trainState = TRAIN_STATE_AWAITING_CLEARANCE;
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            this.m_trainStation.forceUpdateArrow();
            if (this.m_currentTrip)
            {
                if (this.m_currentTrip.isWelcomeTrip)
                {
                    this.grantTrainClearance();
                }
                else
                {
                    this.updateWorkers(true);
                    TrainUI.showTrainAtStationHUDIcon(true, this);
                }
            }
            return;
        }//end

        private void  trainArrive ()
        {
            this.m_trainSpeed = this.m_trainTerminalVelocity;
            this.m_trainState = TRAIN_STATE_ARRIVING;
            this.m_train.displayObject.alpha = 0;
            this.m_hasTrainBegunStop = false;
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            this.m_trainStation.forceUpdateArrow();
            this.m_trainEndPos = this.getStationDockingPosition(this.m_train);
            _loc_1 = this.m_trainTracks.getSize();
            this.m_trainStartPos = this.m_trainEndPos.clone();
            int _loc_2 =-1;
            if (_loc_1.x > _loc_1.y)
            {
                this.m_trainStartPos.x = this.m_maxTileX;
                _loc_2 = Constants.DIRECTION_SW;
            }
            else
            {
                this.m_trainStartPos.y = this.m_maxTileY;
                _loc_2 = Constants.DIRECTION_SE;
            }
            _loc_3 = this.m_trainSpeed*TRAIN_ARRIVAL_TIME;
            _loc_4 = this.m_trainStartPos.subtract(this.m_trainEndPos);
            if (this.m_trainStartPos.subtract(this.m_trainEndPos).length() > _loc_3)
            {
                _loc_4 = _loc_4.normalize();
                _loc_4 = _loc_4.scale(_loc_3);
                this.m_trainStartPos = this.m_trainEndPos.add(_loc_4);
            }
            this.m_train.setPosition(this.m_trainStartPos.x, this.m_trainStartPos.y);
            if (_loc_2 >= 0 && _loc_2 != this.m_train.getDirection())
            {
                this.m_train.setDirection(_loc_2);
                this.m_train.reloadImage();
            }
            this.m_trainDecelerationDistanceFromStation = Math.pow(this.m_trainTerminalVelocity, 2) / (2 * TRAIN_VELOCITY_DELTA);
            TweenLite.to(this.m_train.displayObject, TRAIN_FADE_TIME, {alpha:1});
            this.m_train.setSmokeEnabled(true);
            Sounds.play(TRAIN_SOUND_MOVING);
            if (this.m_currentTrip)
            {
                this.completeCurrentTrip();
            }
            return;
        }//end

        private void  trainIdleAtStation ()
        {
            this.m_trainSpeed = 0;
            this.m_trainState = TRAIN_STATE_IDLE_AT_STATION;
            this.m_train.displayObject.alpha = 1;
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            this.m_trainStation.forceUpdateArrow();
            this.m_trainStartPos = this.getStationDockingPosition(this.m_train);
            this.m_train.setPosition(this.m_trainStartPos.x, this.m_trainStartPos.y);
            this.m_train.setSmokeEnabled(false);
            return;
        }//end

        private void  trainDepart ()
        {
            this.m_trainSpeed = 0;
            this.m_trainState = TRAIN_STATE_DEPARTING;
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            this.m_trainStation.forceUpdateArrow();
            this.m_trainStartPos = this.getStationDockingPosition(this.m_train);
            _loc_1 = this.m_trainTracks.getSize();
            this.m_trainEndPos = this.m_trainStartPos.clone();
            int _loc_2 =-1;
            if (_loc_1.x > _loc_1.y)
            {
                this.m_trainEndPos.x = this.m_minTileX;
                _loc_2 = Constants.DIRECTION_SW;
            }
            else
            {
                this.m_trainEndPos.y = this.m_minTileY;
                _loc_2 = Constants.DIRECTION_SE;
            }
            this.m_train.setPosition(this.m_trainStartPos.x, this.m_trainStartPos.y);
            if (_loc_2 >= 0 && _loc_2 != this.m_train.getDirection())
            {
                this.m_train.setDirection(_loc_2);
                this.m_train.reloadImage();
            }
            TweenLite.to(this.m_train.displayObject, TRAIN_FADE_TIME, {alpha:1});
            this.m_train.setSmokeEnabled(true);
            Sounds.play(TRAIN_SOUND_LEAVES);
            Sounds.play(TRAIN_SOUND_MOVING);
            return;
        }//end

        private void  trainIdleAway ()
        {
            this.m_trainSpeed = 0;
            this.m_trainState = TRAIN_STATE_IDLE_AWAY;
            this.m_train.setSmokeEnabled(false);
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            this.m_trainStation.forceUpdateArrow();
            TweenLite.to(this.m_train.displayObject, TRAIN_FADE_TIME, {alpha:0});
            return;
        }//end

        private void  trainEnRoute ()
        {
            this.trainIdleAway();
            this.m_trainState = TRAIN_STATE_EN_ROUTE;
            this.m_trainStation.forceUpdateArrow();
            dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TRAIN_STATE_UPDATE));
            return;
        }//end

        private void  enterTrainStateIdleAway (double param1 )
        {
            if (this.m_currentTrip)
            {
                this.trainEnRoute();
            }
            else
            {
                this.pumpTrips();
            }
            return;
        }//end

        private void  enterTrainStateEnRoute (double param1 )
        {
            if (this.m_currentTrip && (this.m_currentTrip.isWelcomeTrip || this.m_currentTrip.timeLeft <= 0))
            {
                this.trainAwaitClearance();
            }
            return;
        }//end

        private void  enterTrainStateAwaitingClearance (double param1 )
        {
            if (this.m_trainHasClearance || this.m_currentTrip.isWelcomeTrip)
            {
                this.m_trainHasClearance = false;
                this.trainArrive();
            }
            return;
        }//end

        private void  enterTrainStateArriving (double param1 )
        {
            _loc_2 = this.m_trainEndPos.subtract(this.m_train.getPositionNoClone());
            if (_loc_2.length() < this.m_trainDecelerationDistanceFromStation)
            {
                if (!this.m_hasTrainBegunStop)
                {
                    Sounds.play(TRAIN_SOUND_ARRIVES);
                    Sounds.stop(TRAIN_SOUND_MOVING);
                    this.m_hasTrainBegunStop = true;
                }
                this.m_trainSpeed = Math.max(0, this.m_trainSpeed - TRAIN_VELOCITY_DELTA * param1);
            }
            _loc_3 = this.m_trainEndPos.subtract(this.m_trainStartPos).normalize(this.m_trainSpeed*param1);
            _loc_4 = this.m_train.getPositionNoClone().add(_loc_3);
            this.m_train.setPosition(_loc_4.x, _loc_4.y, _loc_4.z);
            this.m_train.conditionallyReattach();
            this.m_train.adjustSmokeForTrainSpeed(this.m_trainSpeed);
            if (this.m_trainSpeed <= 0)
            {
                this.trainIdleAtStation();
            }
            return;
        }//end

        private void  enterTrainStateIdleAtStation (double param1 )
        {
            if (this.m_currentTrip)
            {
                this.spawnRewardDoobers();
                this.unsetCurrentTrip();
                this.pumpTrips();
                if (this.m_currentTrip)
                {
                    this.trainEnRoute();
                }
                else
                {
                    this.trainDepart();
                }
            }
            return;
        }//end

        private void  enterTrainStateDeparting (double param1 )
        {
            this.m_trainSpeed = Math.min(this.m_trainTerminalVelocity, this.m_trainSpeed + TRAIN_VELOCITY_DELTA * param1);
            _loc_2 = this.m_trainEndPos.subtract(this.m_trainStartPos).normalize(this.m_trainSpeed*param1);
            _loc_3 = this.m_train.getPositionNoClone().add(_loc_2);
            this.m_train.setPosition(_loc_3.x, _loc_3.y, _loc_3.z);
            this.m_train.conditionallyReattach();
            this.m_train.adjustSmokeForTrainSpeed(this.m_trainSpeed);
            if (this.m_currentTrip && (this.m_currentTrip.isWelcomeTrip || this.m_currentTrip.timeLeft <= 0))
            {
                this.trainEnRoute();
                return;
            }
            if (_loc_3.x < this.m_trainEndPos.x || _loc_3.y < this.m_trainEndPos.y)
            {
                if (this.m_currentTrip)
                {
                    this.trainEnRoute();
                }
                else
                {
                    this.trainIdleAway();
                }
            }
            return;
        }//end

        protected Array  generateDoobers (int param1 ,int param2 ,int param3 ,String param4 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_11 =0;
            Array _loc_5 =new Array ();
            _loc_8 = Global.gameSettings().getInt("trainAmountPerDoober",3);
            _loc_9 = Global.gameSettings().getInt("trainMaxDoobers",6);
            int _loc_10 =0;
            if (param1 > 0)
            {
                _loc_6 = Math.floor(Math.min(param1 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param1 / _loc_6);
                _loc_11 = param1 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_11), _loc_11));
                }
            }
            if (param2 > 0)
            {
                _loc_6 = Math.floor(Math.min(param2 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param2 / _loc_6);
                _loc_11 = param2 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_11), _loc_11));
                }
            }
            if (param3 > 0)
            {
                _loc_6 = Math.floor(Math.min(param3 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param3 / _loc_6);
                _loc_11 = param3 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_11), _loc_11));
                }
            }
            return _loc_5;
        }//end

        public Vector3  getStationDockingPosition (NPC param1 )
        {
            _loc_2 = this.m_trainTracks.getPosition();
            _loc_3 = this.m_trainStation.getPosition();
            _loc_6 = _loc_3;
            _loc_7 = _loc_3.x+1;
            _loc_6.x = _loc_7;
            _loc_6 = _loc_3;
            _loc_7 = _loc_3.y + 1;
            _loc_6.y = _loc_7;
            _loc_4 = param1.roadOffset;
            _loc_5 = this.m_trainTracks.getSize();
            if (this.m_trainTracks.getSize().x > _loc_5.y)
            {
                _loc_2.y = _loc_2.y + _loc_5.y * 0.5;
                _loc_2.y = _loc_2.y + _loc_4 * _loc_5.y;
                _loc_2.x = _loc_3.x;
            }
            else
            {
                _loc_2.x = _loc_2.x + _loc_5.x * 0.5;
                _loc_2.x = _loc_2.x + _loc_4 * _loc_5.x;
                _loc_2.y = _loc_3.y;
            }
            return _loc_2;
        }//end

        private void  updateTrain (double param1 )
        {
            switch(this.m_trainState)
            {
                case TRAIN_STATE_IDLE_AWAY:
                {
                    this.enterTrainStateIdleAway(param1);
                    break;
                }
                case TRAIN_STATE_EN_ROUTE:
                {
                    this.enterTrainStateEnRoute(param1);
                    break;
                }
                case TRAIN_STATE_AWAITING_CLEARANCE:
                {
                    this.enterTrainStateAwaitingClearance(param1);
                    break;
                }
                case TRAIN_STATE_ARRIVING:
                {
                    this.enterTrainStateArriving(param1);
                    break;
                }
                case TRAIN_STATE_IDLE_AT_STATION:
                {
                    this.enterTrainStateIdleAtStation(param1);
                    break;
                }
                case TRAIN_STATE_DEPARTING:
                {
                    this.enterTrainStateDeparting(param1);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        private void  updateAndSync ()
        {
            TrainStopCell _loc_1 =null ;
            TrainStopCell _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            Point _loc_5 =null ;
            if (this.m_currentTrip && !this.m_currentTrip.isWelcomeTrip)
            {
                _loc_3 = this.m_currentTrip.timeLeft;
                if (_loc_3 != this.m_trainArrivalTime)
                {
                    this.m_trainArrivalTime = _loc_3;
                    dispatchEvent(new TrainManagerEvent(TrainManagerEvent.TIME_UPDATE));
                }
                _loc_4 = this.m_currentTrip.transactionType == TrainWorkers.OP_BUY ? (this.m_currentTrip.commodityAmount) : (this.m_currentTrip.coins);
                if (_loc_4 != this.m_trainRewardAmount)
                {
                    this.m_trainRewardAmount = _loc_4;
                    dispatchEvent(new TrainManagerEvent(TrainManagerEvent.REWARD_UPDATE));
                }
                if (!this.m_hasPerformedAnticipatedArrivalSync && this.m_currentTrip.timeLeft < WORKER_SYNC_BEFORE_ARRIVAL)
                {
                    this.m_hasPerformedAnticipatedArrivalSync = true;
                    this.updateWorkers(true);
                }
            }
            for(int i0 = 0; i0 < this.m_trainStopCells.size(); i0++)
            {
            	_loc_2 = this.m_trainStopCells.get(i0);

                if (_loc_2.hitTestMouse())
                {
                    _loc_1 = _loc_2;
                    break;
                }
            }
            if (_loc_1 != this.m_lastTrainStopCellRolledOver)
            {
                if (this.m_trainStopCellTooltip && this.m_trainStopCellTooltip.parent)
                {
                    this.m_trainStopCellTooltip.parent.removeChild(this.m_trainStopCellTooltip);
                }
                this.m_lastTrainStopCellRolledOver = _loc_1;
                if (this.m_trainStationDialog && !this.m_trainStationDialog.hasOverlay && this.m_lastTrainStopCellRolledOver && this.m_lastTrainStopCellRolledOver.friend)
                {
                    this.m_trainStopCellTooltip = new TrainStopTooltip();
                    this.m_trainStopCellTooltip.changeInfo(this.m_lastTrainStopCellRolledOver.friend.name);
                    _loc_5 = this.m_lastTrainStopCellRolledOver.parent.localToGlobal(new Point(this.m_lastTrainStopCellRolledOver.x, this.m_lastTrainStopCellRolledOver.y));
                    this.m_trainStopCellTooltip.x = _loc_5.x - this.m_trainStopCellTooltip.width / 2 + this.m_lastTrainStopCellRolledOver.width / 2;
                    this.m_trainStopCellTooltip.y = _loc_5.y - this.m_trainStopCellTooltip.height;
                    Global.stage.addChild(this.m_trainStopCellTooltip);
                }
            }
            return;
        }//end

        private void  updateBounds ()
        {
            Point _loc_3 =null ;
            if (this.m_maxTileX > 0)
            {
                return;
            }
            _loc_1 = Global.world.overlayBackgroundRect;
            if (!_loc_1 || !this.m_trainTracks)
            {
                return;
            }
            _loc_2 = this.m_trainStation.getPosition();
            _loc_2.x = _loc_2.x - 3;
            _loc_2.y = _loc_2.y - 3;
            int _loc_4 =1;
            while (_loc_4 < 150)
            {

                _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x + _loc_4, _loc_2.y, 0, true);
                if (_loc_3.x < _loc_1.right && _loc_3.y > _loc_1.top)
                {
                    this.m_maxTileX = _loc_2.x + _loc_4;
                }
                _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x, _loc_2.y + _loc_4, 0, true);
                if (_loc_3.x > _loc_1.left && _loc_3.y > _loc_1.top)
                {
                    this.m_maxTileY = _loc_2.y + _loc_4;
                }
                _loc_4++;
            }
            _loc_4 = 1;
            while (_loc_4 < 150)
            {

                _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x - _loc_4, _loc_2.y, 0, true);
                if (_loc_3.x > _loc_1.left && _loc_3.y < _loc_1.bottom)
                {
                    this.m_minTileX = _loc_2.x - _loc_4;
                }
                _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x, _loc_2.y - _loc_4, 0, true);
                if (_loc_3.x > _loc_1.left && _loc_3.y < _loc_1.bottom)
                {
                    this.m_minTileY = _loc_2.y - _loc_4;
                }
                _loc_4++;
            }
            return;
        }//end

        public void  generateCrossings ()
        {
            TrainCrossing _loc_1 =null ;
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            Vector3 _loc_7 =null ;
            Vector3 _loc_8 =null ;
            int _loc_9 =0;
            Array _loc_10 =null ;
            boolean _loc_11 =false ;
            String _loc_12 =null ;
            int _loc_13 =0;
            boolean _loc_14 =false ;
            Road _loc_15 =null ;
            TrainCrossing _loc_16 =null ;
            if (!this.m_trainTracks)
            {
                this.onGameLoaded(null);
            }
            for(int i0 = 0; i0 < this.m_crossings.size(); i0++)
            {
            	_loc_1 = this.m_crossings.get(i0);

                _loc_1.detach();
            }
            this.m_crossings = new Array();
            if (this.m_trainTracks)
            {
                _loc_2 = this.m_trainTracks.getPosition();
                _loc_3 = this.m_trainTracks.getSize();
                _loc_4 = _loc_3.y;
                _loc_5 = 0;
                _loc_6 = 1;
                if (_loc_3.x > _loc_3.y)
                {
                    _loc_4 = _loc_3.x;
                    _loc_5 = 1;
                    _loc_6 = 0;
                }
                _loc_7 = new Vector3();
                _loc_8 = new Vector3();
                _loc_9 = 0;
                while (_loc_9 < _loc_4)
                {

                    _loc_2.x = _loc_2.x + _loc_5;
                    _loc_2.y = _loc_2.y + _loc_6;
                    _loc_7.x = _loc_2.x + _loc_6 * Road.SIZE_X;
                    _loc_7.y = _loc_2.y + _loc_5 * Road.SIZE_Y;
                    _loc_8.x = _loc_2.x - _loc_6 * Road.SIZE_X;
                    _loc_8.y = _loc_2.y - _loc_5 * Road.SIZE_Y;
                    _loc_10 = this.m_world.getCollisionMap().getObjectsByPosition(_loc_7.x, _loc_7.y);
                    _loc_11 = false;
                    _loc_12 = "";
                    _loc_13 = 0;
                    while (_loc_13 < _loc_10.length())
                    {

                        _loc_15 =(Road) _loc_10.get(_loc_13);
                        if (_loc_15 && _loc_15.getPositionNoClone().equals(_loc_7))
                        {
                            _loc_11 = true;
                            _loc_12 = _loc_15.baseRoadName();
                        }
                        _loc_13++;
                    }
                    _loc_10 = this.m_world.getCollisionMap().getObjectsByPosition(_loc_8.x, _loc_8.y);
                    _loc_14 = false;
                    _loc_13 = 0;
                    while (_loc_13 < _loc_10.length())
                    {

                        _loc_15 =(Road) _loc_10.get(_loc_13);
                        if (_loc_15 && _loc_15.getPositionNoClone().equals(_loc_8))
                        {
                            _loc_14 = true;
                        }
                        _loc_13++;
                    }
                    if (!_loc_14)
                    {
                        _loc_8.x = _loc_8.x - _loc_6;
                        _loc_8.y = _loc_8.y - _loc_5;
                        _loc_10 = this.m_world.getCollisionMap().getObjectsByPosition(_loc_8.x, _loc_8.y);
                        _loc_13 = 0;
                        while (_loc_13 < _loc_10.length())
                        {

                            _loc_15 =(Road) _loc_10.get(_loc_13);
                            if (_loc_15 && _loc_15.getPositionNoClone().equals(_loc_8))
                            {
                                if (_loc_6 > 0 && _loc_15.sizeX > Road.SIZE_X)
                                {
                                    _loc_14 = true;
                                }
                                if (_loc_5 > 0 && _loc_15.sizeY > Road.SIZE_Y)
                                {
                                    _loc_14 = true;
                                }
                            }
                            _loc_13++;
                        }
                    }
                    if (_loc_11 && _loc_14)
                    {
                        _loc_16 = new TrainCrossing(_loc_12);
                        this.m_crossings.push(_loc_16);
                        _loc_16.setPosition(_loc_2.x, _loc_2.y);
                        _loc_16.setOuter(this.m_world);
                        _loc_16.attach();
                        _loc_16.m_orientation_X = _loc_6 != 0;
                    }
                    _loc_9++;
                }
            }
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  stopObserving ()
        {
            this.m_world.removeObserver(this);
            return;
        }//end

        public void  update (double param1 )
        {
            this.updateBounds();
            this.updateTrain(param1);
            this.updateAndSync();
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            GameObject _loc_3 =null ;
            this.createTrain();
            this.syncWorkers();
            TrainUI.showTrainAtStationHUDIcon(false, this);
            _loc_2 = this.m_world.getObjectsByTypes(.get(WorldObjectTypes.TRAIN_STATION));
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof TrainTracks)
                {
                    this.m_trainTracks =(TrainTracks) _loc_3;
                    continue;
                }
                if (_loc_3 instanceof TrainStation)
                {
                    this.m_trainStation =(TrainStation) _loc_3;
                }
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        private void  trainStationDialogCloseHandler (Event event )
        {
            this.m_trainStationDialog.removeEventListener(Event.CLOSE, this.trainStationDialogCloseHandler);
            this.m_trainStationDialog = null;
            this.m_trainStopCells = new Vector<TrainStopCell>();
            return;
        }//end

        private void  workerSyncHandler (Event event )
        {
            ((TWorkersSync)event.currentTarget).removeEventListener(Event.COMPLETE, this.workerSyncHandler);
            this.syncWorkers();
            return;
        }//end

        public void  accepted (TrainOrder param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  declined (TrainOrder param1 ,int param2 )
        {
            return;
        }//end

        public Point  getLoadingUIPos ()
        {
            return new Point();
        }//end

        public void  later (TrainOrder param1 ,int param2 )
        {
            return;
        }//end

        public void  onMFSLoaded (boolean param1 )
        {
            return;
        }//end

        public void  onNeighborAdded ()
        {
            return;
        }//end

        public void  placeInitialTrainOrder (String param1 ,int param2 ,String param3 )
        {
            return;
        }//end

        public void  updateAmountAccepted (TrainOrder param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public static String  getWorkerBucket ()
        {
            return HarvestableResource.WORKER_BUCKET_PREFIX + "0";
        }//end

    }




