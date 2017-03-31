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
import Classes.MiniQuest.*;
import Classes.orders.*;
import Classes.util.*;
import Display.FactoryUI.*;
import Display.LotUI.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.franchise.data.*;
import Modules.stats.types.*;
import Transactions.lot.*;

//import flash.display.*;
//import flash.utils.*;

    public class LotManager implements IGameWorldStateObserver
    {
        protected GameWorld m_world ;
        protected Array m_lotSites ;
        protected Array m_sentPendingOrders ;
        protected Array m_receivedPendingOrders ;
        protected Dictionary m_pickGroupDictionary ;
        protected boolean m_isInitialized =false ;

        public  LotManager (GameWorld param1 )
        {
            this.m_world = param1;
            this.m_world.addObserver(this);
            return;
        }//end

        public Array  sentPendingOrders ()
        {
            return this.m_sentPendingOrders;
        }//end

        public Array  receivedPendingOrders ()
        {
            return this.m_receivedPendingOrders;
        }//end

        public Array  lotSites ()
        {
            return this.m_lotSites;
        }//end

        public void  initLots ()
        {
            this.cleanUp();
            this.completeAcceptedOrders();
            this.m_lotSites = Global.world.getObjectsByTypes(.get(WorldObjectTypes.LOT_SITE));
            this.m_sentPendingOrders = Global.world.orderMgr.getOrders(OrderType.LOT, OrderStatus.SENT, OrderStates.PENDING);
            this.m_receivedPendingOrders = Global.world.orderMgr.getOrders(OrderType.LOT, OrderStatus.RECEIVED, OrderStates.PENDING);
            this.m_pickGroupDictionary = new Dictionary(true);
            this.drawView();
            this.m_isInitialized = true;
            return;
        }//end

        public void  placeOrder (String param1 ,int param2 ,String param3 )
        {
            String _loc_4 =null ;
            MapResource _loc_9 =null ;
            _loc_5 =Global.gameSettings().getItemByName(param3 );
            if (Global.gameSettings().getItemByName(param3).type == "business")
            {
                _loc_4 = Global.franchiseManager.getFranchiseName(_loc_5.name, Global.player.uid);
            }
            else
            {
                _loc_4 = "";
            }
            TLotOrderPlace _loc_6 =new TLotOrderPlace(param1 ,param2 ,param3 ,_loc_4 ,0,this.placeOrderCompleteHandler );
            GameTransactionManager.addTransaction(_loc_6);
            LotOrder _loc_7 =new LotOrder(param1 ,Global.player.uid ,param2 ,param3 ,_loc_4 ,0,OrderStatus.SENT );
            this.m_sentPendingOrders.put(this.m_sentPendingOrders.length,  _loc_7);
            _loc_8 =Global.world.getObjectById(param2 );
            if (Global.world.getObjectById(param2))
            {
                _loc_9 = this.replaceLotSite(param2, param3, _loc_4, Global.player.uid);
                _loc_9.itemOwner = Global.player.uid;
                _loc_9.isPendingOrder = true;
                Global.world.citySim.roadManager.updateRoads(_loc_9);
            }
            if (Global.franchiseManager.isFranchiseOrder(_loc_7))
            {
                StatsManager.social(StatsCounterType.FRANCHISES, param1, "place_pending_franchise", _loc_7.getResourceType());
            }
            return;
        }//end

        public boolean  checkLotOrderAvailablity ()
        {
            LotOrder _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_1 =true ;
            _loc_2 =Global.world.ownerId ;
            for(int i0 = 0; i0 < this.m_sentPendingOrders.size(); i0++)
            {
            	_loc_3 = this.m_sentPendingOrders.get(i0);

                _loc_4 = _loc_3.getRecipientID();
                if (_loc_4 == _loc_2)
                {
                    _loc_1 = false;
                    break;
                }
            }
            return _loc_1;
        }//end

        public MapResource  accepted (LotOrder param1 )
        {
            Vector3 _loc_3 =null ;
            MapResource _loc_4 =null ;
            TLotOrderAccept _loc_5 =null ;
            _loc_2 = this.m_receivedPendingOrders ? (this.m_receivedPendingOrders.indexOf(param1)) : (-1);
            if (_loc_2 >= 0)
            {
                _loc_3 = new Vector3(0, 0);
                _loc_4 = this.replaceLotSite(param1.getLotId(), param1.getResourceType(), param1.getOrderResourceName(), param1.getSenderID(), _loc_3);
                param1.setXOffset(_loc_3.x);
                param1.setYOffset(_loc_3.y);
                _loc_5 = new TLotOrderAccept(param1.getSenderID(), param1.getLotId());
                param1.setState(OrderStates.ACCEPTED);
                Global.world.orderMgr.removeOrder(param1);
                GameTransactionManager.addTransaction(_loc_5);
                this.m_receivedPendingOrders.splice(this.m_receivedPendingOrders.indexOf(param1), 1);
                this.redrawView();
                Global.world.citySim.roadManager.updateRoads(_loc_4);
                if (Global.franchiseManager.isFranchiseOrder(param1))
                {
                    StatsManager.social(StatsCounterType.FRANCHISES, param1.getSenderID(), "accept_franchise");
                }
                if (this.m_receivedPendingOrders.length == 0)
                {
                    Global.world.citySim.miniQuestManager.endMiniQuest(PendingFranchiseMQ.QUEST_NAME);
                }
                return _loc_4;
            }
            return null;
        }//end

        public void  later (LotOrder param1 )
        {
            DisplayObject _loc_4 =null ;
            SlidePickGroup _loc_5 =null ;
            SlidePick _loc_6 =null ;
            _loc_2 = param1.getLotId ();
            _loc_3 = this.getLotSite(_loc_2 );
            if (_loc_3)
            {
                _loc_4 = _loc_3.getPick();
                if (_loc_4 instanceof SlidePickGroup)
                {
                    _loc_5 =(SlidePickGroup) _loc_4;
                    _loc_6 = _loc_5.getPickByPropValue("order", param1);
                    if (_loc_6)
                    {
                        _loc_5.removePick(_loc_6);
                        _loc_6 = this.createLotSlidePick(param1);
                        _loc_5.addPick(_loc_6);
                        _loc_5.redraw();
                    }
                }
            }
            return;
        }//end

        public void  declined (LotOrder param1 )
        {
            TLotOrderDeny _loc_3 =null ;
            _loc_2 = this.m_receivedPendingOrders ? (this.m_receivedPendingOrders.indexOf(param1)) : (-1);
            if (_loc_2 >= 0)
            {
                _loc_3 = new TLotOrderDeny(param1);
                GameTransactionManager.addTransaction(_loc_3);
                this.m_receivedPendingOrders.splice(this.m_receivedPendingOrders.indexOf(param1), 1);
                if (Global.franchiseManager.isFranchiseOrder(param1))
                {
                    StatsManager.social(StatsCounterType.FRANCHISES, param1.getSenderID(), "decline_franchise");
                }
                if (this.m_receivedPendingOrders.length == 0)
                {
                    Global.world.citySim.miniQuestManager.endMiniQuest(PendingFranchiseMQ.QUEST_NAME);
                }
            }
            this.removePick(param1);
            return;
        }//end

        public void  cancel (LotOrder param1 )
        {
            TLotOrderCancel _loc_3 =null ;
            Item _loc_4 =null ;
            _loc_2 = this.m_sentPendingOrders ? (this.m_sentPendingOrders.indexOf(param1)) : (-1);
            if (_loc_2 >= 0)
            {
                _loc_3 = new TLotOrderCancel(param1);
                GameTransactionManager.addTransaction(_loc_3);
                this.m_sentPendingOrders.splice(this.m_sentPendingOrders.indexOf(param1), 1);
                if (Global.franchiseManager.isFranchiseOrder(param1))
                {
                    StatsManager.social(StatsCounterType.FRANCHISES, param1.getSenderID(), param1.getRecipientID(), "cancel_franchise");
                }
                _loc_4 = Global.gameSettings().getItemByName(param1.getResourceType());
                Global.player.gold = Global.player.gold + _loc_4.cost;
                this.returnLotSite(param1.getLotId(), param1.getResourceType(), param1.getSenderID());
                this.redrawView();
            }
            return;
        }//end

        protected void  removePick (LotOrder param1 )
        {
            DisplayObject _loc_4 =null ;
            SlidePickGroup _loc_5 =null ;
            SlidePick _loc_6 =null ;
            _loc_2 = param1.getLotId ();
            _loc_3 = this.getLotSite(_loc_2 );
            if (_loc_3)
            {
                _loc_4 = _loc_3.getPick();
                if (_loc_4 instanceof SlidePickGroup)
                {
                    _loc_5 =(SlidePickGroup) _loc_4;
                    _loc_6 = _loc_5.getPickByPropValue("order", param1);
                    if (_loc_6)
                    {
                        _loc_5.removePick(_loc_6);
                    }
                    if (_loc_5.numPicks <= 0)
                    {
                        _loc_3.removePick();
                        this.m_pickGroupDictionary.put(_loc_3,  null);
                    }
                    else
                    {
                        _loc_5.redraw();
                    }
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
            Object _loc_1 =null ;
            SlidePickGroup _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_pickGroupDictionary.size(); i0++)
            {
            	_loc_1 = this.m_pickGroupDictionary.get(i0);

                _loc_2 = this.m_pickGroupDictionary.get(_loc_1);
                if (_loc_2)
                {
                    _loc_2.cleanUp();
                }
                delete this.m_pickGroupDictionary.get(_loc_1);
            }
            this.m_pickGroupDictionary = new Dictionary(true);
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            if (this.m_isInitialized)
            {
                this.redrawView();
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  removeLotSite (LotSite param1 )
        {
            _loc_2 = this.m_lotSites.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_lotSites.splice(_loc_2, 1);
            }
            param1.remove();
            this.cleanUp();
            return;
        }//end

        public void  addLotSite (LotSite param1 )
        {
            if (!this.m_lotSites || this.m_lotSites.length <= 0)
            {
                this.cleanUp();
                this.redrawView();
            }
            else if (this.m_lotSites)
            {
                this.m_lotSites.put(this.m_lotSites.length,  param1);
            }
            return;
        }//end

        private void  adjustInLotForRoads (MapResource param1 ,Vector3 param2 ,Vector3 param3 ,Vector3 param4 =null )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            Global.world.citySim.roadManager.updateResource(param1);
            if (param4)
            {
                param4.x = 0;
                param4.y = 0;
            }
            if (!param1.isAdjacentToAnyRoad)
            {
                _loc_5 = 0;
                while (_loc_5 <= param3.x - param1.sizeX)
                {

                    _loc_6 = 0;
                    while (_loc_6 <= param3.y - param1.sizeY)
                    {

                        param1.setPosition(param2.x + _loc_5, param2.y + _loc_6, param2.z);
                        Global.world.citySim.roadManager.updateResource(param1);
                        if (param1.isAdjacentToAnyRoad)
                        {
                            if (param4)
                            {
                                param4.x = _loc_5;
                                param4.y = _loc_6;
                            }
                            break;
                        }
                        _loc_6++;
                    }
                    _loc_5++;
                }
            }
            return;
        }//end

        public MapResource  replaceLotSite (double param1 ,String param2 ,String param3 ="",String param4 ="",Vector3 param5 =null )
        {
            MapResource _loc_12 =null ;
            Class _loc_13 =null ;
            boolean _loc_6 =false ;
            _loc_7 = this.getLotSite(param1 );
            _loc_8 = this.getLotSite(param1 ).getPosition ();
            _loc_9 = _loc_7.getDirection ();
            _loc_10 = _loc_7.getSize ();
            _loc_11 =Global.gameSettings().getItemByName(param2 );
            if (Global.gameSettings().getItemByName(param2).construction != null && _loc_6)
            {
                _loc_12 = ConstructionSite.createConstructionSite(param2);
            }
            else
            {
                _loc_13 = ItemClassDefinitions.getClassByItem(_loc_11);
                _loc_12 = new _loc_13(_loc_11.name);
            }
            _loc_12.itemOwner = param4;
            _loc_12.setOuter(Global.world);
            _loc_12.onObjectDrag(_loc_8);
            _loc_12.setPosition(_loc_8.x, _loc_8.y, _loc_8.z);
            _loc_12.rotateToDirection(_loc_9);
            this.adjustInLotForRoads(_loc_12, _loc_8, _loc_10, param5);
            _loc_12.setId(param1);
            _loc_12.conditionallyReattach(true);
            _loc_12.onBuildingConstructionCompleted_PreServerUpdate();
            if (param3)
            {
                _loc_12.initFriendData(param3);
            }
            this.removeLotSite(_loc_7);
            return _loc_12;
        }//end

        public void  returnLotSite (double param1 ,String param2 ,String param3 ="")
        {
            _loc_4 =Global.franchiseManager.getPendingFranchiseFromTypeAndOwner(param2 ,param3 );
            _loc_5 =Global.franchiseManager.getPendingFranchiseFromTypeAndOwner(param2 ,param3 ).getPosition ();
            _loc_4.detach();
            LotSite _loc_6 =new LotSite(LotSite.LOT_NAME );
            _loc_6.setId(param1);
            _loc_6.setOuter(Global.world);
            _loc_6.onObjectDrag(_loc_5);
            _loc_6.setPosition(_loc_5.x, _loc_5.y, _loc_5.z);
            this.adjustInLotForRoads(_loc_6, _loc_6.getPosition(), _loc_6.getSize());
            _loc_6.conditionallyReattach(true);
            this.addLotSite(_loc_6);
            return;
        }//end

        protected void  completeAcceptedOrders ()
        {
            LotOrder _loc_2 =null ;
            TLotOrderComplete _loc_3 =null ;
            _loc_1 =Global.world.orderMgr.getOrders(OrderType.LOT ,OrderStatus.SENT ,OrderStates.ACCEPTED );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = new TLotOrderComplete(_loc_2);
                GameTransactionManager.addTransaction(_loc_3);
            }
            return;
        }//end

        protected void  drawView ()
        {
            if (Global.isVisiting())
            {
                this.drawVisitView();
            }
            else
            {
                this.drawHomeView();
            }
            return;
        }//end

        protected void  redrawView ()
        {
            this.m_lotSites = Global.world.getObjectsByTypes(.get(WorldObjectTypes.LOT_SITE));
            this.drawView();
            return;
        }//end

        protected void  drawVisitView ()
        {
            LotSite _loc_1 =null ;
            double _loc_2 =0;
            MapResource _loc_3 =null ;
            LotSlidePick _loc_5 =null ;
            SlidePickGroup _loc_6 =null ;
            LotOrder _loc_7 =null ;
            Vector _loc_8.<FranchiseExpansionData >=null ;
            FranchiseExpansionData _loc_9 =null ;
            boolean _loc_10 =false ;
            String _loc_11 =null ;
            if (Global.world.isOwnerCitySam)
            {
                _loc_8 = Global.franchiseManager.model.getFranchisesByLocation(GameWorld.CITY_SAM_OWNER_ID);
                if (_loc_8.length())
                {
                    _loc_9 = _loc_8.get(0);
                    for(int i0 = 0; i0 < this.m_lotSites.size(); i0++)
                    {
                    	_loc_1 = this.m_lotSites.get(i0);

                        _loc_10 = Boolean(_loc_1.targetType == "business");
                        if (_loc_10)
                        {
                            _loc_3 = this.replaceLotSite(_loc_1.getId(), _loc_9.franchiseType, "", Global.player.uid);
                            if (_loc_3)
                            {
                                Global.world.citySim.roadManager.updateRoads(_loc_3);
                            }
                            break;
                        }
                    }
                }
                return;
            }
            Object _loc_4 ={};
            for(int i0 = 0; i0 < this.m_lotSites.size(); i0++)
            {
            	_loc_1 = this.m_lotSites.get(i0);

                _loc_2 = _loc_1.getId();
                if (_loc_2 > 0)
                {
                    _loc_4.put(_loc_2,  _loc_1);
                }
            }
            for(int i0 = 0; i0 < this.m_sentPendingOrders.size(); i0++)
            {
            		_loc_7 = this.m_sentPendingOrders.get(i0);

                _loc_11 = _loc_7.getRecipientID();
                if (_loc_11 == Global.world.ownerId)
                {
                    _loc_2 = _loc_7.getLotId();
                    _loc_1 = _loc_4.get(_loc_2);
                    if (!_loc_1 && this.m_lotSites.length > 0)
                    {
                        _loc_1 = this.getDefaultLotSite();
                    }
                    if (_loc_1)
                    {
                        _loc_3 = this.replaceLotSite(_loc_1.getId(), _loc_7.getResourceType(), _loc_7.getOrderResourceName(), _loc_7.getSenderID());
                        _loc_3.itemOwner = _loc_7.getSenderID();
                        _loc_3.isPendingOrder = true;
                    }
                }
            }
            if (_loc_3)
            {
                Global.world.citySim.roadManager.updateRoads(_loc_3);
            }
            return;
        }//end

        public LotSite  getDefaultLotSite ()
        {
            LotSite _loc_1 =null ;
            if (this.m_lotSites && this.m_lotSites.length > 0)
            {
                _loc_1 = this.m_lotSites.get(0);
            }
            return _loc_1;
        }//end

        protected void  drawHomeView ()
        {
            LotSite _loc_3 =null ;
            double _loc_4 =0;
            LotSlidePick _loc_5 =null ;
            SlidePickGroup _loc_6 =null ;
            LotOrder _loc_7 =null ;
            Object _loc_8 =null ;
            LotSite _loc_9 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_lotSites.length())
            {

                _loc_9 = this.m_lotSites.get(_loc_1);
                if (_loc_9.getId() == 0)
                {
                    this.m_lotSites.splice(_loc_1, 1);
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1++;
            }
            if (!this.m_lotSites && this.m_lotSites.length <= 0 && !this.m_receivedPendingOrders && this.m_receivedPendingOrders.length <= 0)
            {
                return;
            }
            Object _loc_2 ={};
            for(int i0 = 0; i0 < this.m_lotSites.size(); i0++)
            {
            	_loc_3 = this.m_lotSites.get(i0);

                _loc_4 = _loc_3.getId();
                if (_loc_4 > 0)
                {
                    _loc_2.put(_loc_4,  _loc_3);
                }
            }
            for(int i0 = 0; i0 < this.m_receivedPendingOrders.size(); i0++)
            {
            	_loc_7 = this.m_receivedPendingOrders.get(i0);

                _loc_4 = _loc_7.getLotId();
                _loc_3 = _loc_2.get(_loc_4) || this.m_lotSites.get(0);
                if (_loc_3)
                {
                    _loc_6 = this.m_pickGroupDictionary.get(_loc_3);
                    if (!_loc_6)
                    {
                        _loc_6 = new SlidePickGroup(this);
                        this.m_pickGroupDictionary.put(_loc_3,  _loc_6);
                    }
                    _loc_7.setLotId(_loc_3.getId());
                    _loc_5 = this.createLotSlidePick(_loc_7);
                    _loc_6.addPick(_loc_5);
                }
            }
            for(int i0 = 0; i0 < this.m_pickGroupDictionary.size(); i0++)
            {
            		_loc_8 = this.m_pickGroupDictionary.get(i0);

                _loc_6 = this.m_pickGroupDictionary.get(_loc_8);
                _loc_6.init();
                _loc_3 =(LotSite) _loc_8;
                _loc_3.setPick(_loc_6);
            }
            return;
        }//end

        protected LotSlidePick  createLotSlidePick (LotOrder param1 )
        {
            LotSlidePick _loc_2 =new LotSlidePick(this ,param1 ,true );
            return _loc_2;
        }//end

        public LotSite  getLotSite (double param1 )
        {
            LotSite _loc_2 =null ;
            LotSite _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_lotSites.size(); i0++)
            {
            	_loc_3 = this.m_lotSites.get(i0);

                if (_loc_3.getId() == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            return _loc_2;
        }//end

        private void  placeOrderCompleteHandler (Object param1 )
        {
            return;
        }//end

    }



