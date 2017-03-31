package GameMode;

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
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Transactions.*;
import Modules.franchise.data.*;
import Modules.guide.ui.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;
//import flash.geom.*;

import com.xinghai.Debug;

    public class GMPlaceMapResource extends GMObjectMove
    {
        protected String m_item ;
        private Class m_itemClass ;
        protected MapResource m_resource ;
        protected ConstructionSite m_constructionSite =null ;
        public Headquarter t_headquarter ;

        public  GMPlaceMapResource (String param1 ,Class param2 ,boolean param3 =false )
        {
            this.m_item = param1;
            this.m_itemClass = param2 || ItemClassDefinitions.getClassByItemName(param1);
            m_isGift = param3;
            _loc_4 =Global.gameSettings().getItemByName(this.m_item );
            if (Global.gameSettings().getItemByName(this.m_item).placeGuide)
            {
                Global.guide.notify(_loc_4.placeGuide);
            }
            if (_loc_4.construction != null)
            {
                this.m_constructionSite = ConstructionSite.createConstructionSite(this.m_item, this.m_itemClass);
            }
            super(null);
            return;
        }//end

        public String  itemName ()
        {
            return this.m_item;
        }//end

        protected Transaction  transaction ()
        {
            return new TPlaceMapResource(this.m_resource, m_isGift, 0, this.m_item);
        }//end

         protected void  replaceResource (MapResource param1 )
        {
            if (param1 != null)
            {
                this.m_resource = param1;
            }
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            boolean _loc_2 =false ;
            Debug.debug6("GMPlaceMapResource.onMouseMove");

            if (!m_selectedObject)
            {
                m_highlightedPoint = getMouseTilePos();
                this.m_resource = new this.m_itemClass(this.m_item);
                this.m_resource.setOuter(Global.world);
                if (this.m_constructionSite)
                {
                    m_selectedObject = this.m_constructionSite;
                    m_selectedObject.setVisible(false);
                    m_selectedObject.addChild(this.m_resource);
                    this.m_resource.setCollisionFlags(Constants.COLLISION_NONE);
                    this.m_resource.lock();
                }
                else
                {
                    m_selectedObject = this.m_resource;
                }
                resetOnPlaceCollisions();
                registerAllOnPlaceCollisions();
                snapPoint(m_highlightedPoint);
                m_selectedObject.setPosition(m_highlightedPoint.x, m_highlightedPoint.y);
                m_objectDragOffset = new Point(0, (-m_selectedObject.getSize().y) * 10 * (GlobalEngine.viewport.getZoom() * 0.25));
                this.m_resource.setBeingRotated(true);
                ((MapResource)m_selectedObject).setBeingRotated(true);
            }
            if (Global.franchiseManager.placementMode)
            {
                this.m_resource.alpha = 1;
            }
            else
            {
                this.m_resource.alpha = 0.5;
            }
            Global.world.citySim.roadManager.updateResource(this.m_resource);
            m_autoRotate = this.m_resource.autoRotate && (m_selectedObject as MapResource).autoRotate;
            if (m_autoRotate)
            {
                rotateToRoad(this.m_resource);
            }
            _loc_2 = super.onMouseMove(event);
            return _loc_2;
        }//end

         public void  disableMode ()
        {
            UI.popupUnlock();
            Debug.debug6("GMPlaceMapResource.disableMode");

            this.m_resource.setBeingRotated(false);
            if (m_selectedObject)
            {
                ((MapResource)m_selectedObject).setBeingRotated(false);
            }
            if (this.m_constructionSite)
            {
                if (m_selectedObject)
                {
                    m_selectedObject.removeChild(this.m_resource);
                    this.m_resource.detach();
                    this.m_resource.cleanUp();
                    m_selectedObject.setVisible(false);
                }
            }
            super.disableMode();
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            UI.popupLock();
            return;
        }//end

         protected boolean  updateHighlightPoint (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (Global.world.isHome())
            {
                return super.updateHighlightPoint(event);
            }
            super.updateHighlightPoint(event);
            _loc_2 = Boolean(this.getLotSiteUnderGameObject(m_selectedObject));
            return _loc_2;
        }//end

        protected LotSite  getLotSiteUnderGameObject (GameObject param1 )
        {

            Debug.debug6("GMPlaceMapResource.getLotSiteUnderGameObject");

            LotSite _loc_2 =null ;
            LotSite _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Vector3 _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            boolean _loc_9 =false ;
            Vector3 _loc_10 =null ;
            Vector3 _loc_11 =null ;
            double _loc_12 =0;
            double _loc_13 =0;
            boolean _loc_14 =false ;
            boolean _loc_15 =false ;
            boolean _loc_16 =false ;
            _loc_3 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.LOT_SITE) );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_5 = _loc_4.getSize();
                _loc_6 = param1.getSize();
                _loc_7 = _loc_5.x - _loc_6.x;
                _loc_8 = _loc_5.y - _loc_6.y;
                _loc_9 = Boolean(_loc_7 >= 0 && _loc_8 >= 0);
                if (_loc_9)
                {
                    _loc_10 = _loc_4.getPosition();
                    _loc_11 = param1.getPosition();
                    _loc_12 = _loc_10.x - _loc_11.x;
                    _loc_13 = _loc_10.y - _loc_11.y;
                    _loc_14 = Boolean(_loc_12 >= -_loc_7 && _loc_12 <= 0);
                    _loc_15 = Boolean(_loc_13 >= -_loc_8 && _loc_13 <= 0);
                    _loc_16 = Boolean(_loc_14 && _loc_15);
                    if (_loc_16)
                    {
                        _loc_2 = _loc_4;
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

         protected void  handleObjectDrop (MouseEvent event =null )
        {
            LotSite _loc_2 =null ;
            GuideTile _loc_3 =null ;
            Vector2 _loc_4 =null ;
            ConstructionSite _loc_5 =null ;
            int _loc_6 =0;
            MapResource _loc_7 =null ;
            Vector3 _loc_8 =null ;

            Debug.debug6("GMPlaceMapResource.handleObjectDrop");

            if (Global.isVisiting() && !(this.m_resource instanceof Business))
            {
                return;
            }
            if (!(this.m_resource instanceof LotSite))
            {
                _loc_2 = this.getLotSiteUnderGameObject(m_selectedObject);
            }
            if (Global.guide.isActive() && Global.guide.guideTiles.length())
            {
                _loc_3 = null;
                _loc_4 = null;
                _loc_5 =(ConstructionSite) m_selectedObject;
                if (_loc_5)
                {
                    if (_loc_5.targetItem.placeableOnly && _loc_5.targetItem.mustOwnToPlace)
                    {
                        if (m_collisions.length == 0)
                        {
                            if (!Global.world.rectInTerritory(new Rectangle(_loc_5.targetItem.mustOwnToPlace.get(0), _loc_5.targetItem.mustOwnToPlace.get(1), 1, 1)))
                            {
                                _loc_3 =(GuideTile) Global.guide.guideTiles.get(0);
                                _loc_4 = new Vector2(_loc_3.origin.x, _loc_3.origin.y);
                                if (m_highlightedPoint.equals(_loc_4))
                                {
                                    UI.displayStatus(ZLoc.t("Main", "BuyExpansionFirstRed"), event.stageX, event.stageY);
                                }
                            }
                        }
                    }
                }
                m_objectPlacedSuccess = false;
                m_positionValid = event ? (this.updateHighlightPoint(event)) : (true);
                _loc_6 = 0;
                while (_loc_6 < Global.guide.guideTiles.length())
                {

                    _loc_3 =(GuideTile) Global.guide.guideTiles.get(_loc_6);
                    _loc_4 = new Vector2(_loc_3.origin.x, _loc_3.origin.y);
                    if (m_highlightedPoint.equals(_loc_4) && m_positionValid)
                    {
                        m_selectedObject.setPosition(_loc_4.x, _loc_4.y);
                        m_selectedObject.setId(this.m_resource.getId());
                        m_selectedObject.onObjectDropPreTansaction(m_objectStartPos);
                        m_selectedObject.conditionallyReattach();
                        m_selectedObject.onObjectDrop(m_objectStartPos);
                        m_objectPlacedSuccess = true;
                        this.completePlaceResource();
                        _loc_7 =(MapResource) m_selectedObject;
                        if (_loc_7)
                        {
                            _loc_7.trackAction(TrackedActionType.PLACE);
                        }
                        Global.guide.guideTiles.splice(_loc_6, 1);
                        _loc_3.cleanup();
                        _loc_3 = null;
                        break;
                    }
                    _loc_6++;
                }
            }
            else if (_loc_2 && Global.isVisiting() && _loc_2 == m_selectedLotSite)
            {
                _loc_8 = _loc_2.getPosition();
                m_selectedObject.setPosition(_loc_8.x, _loc_8.y);
                m_selectedObject.setId(this.m_resource.getId());
                m_selectedObject.onObjectDropPreTansaction(m_objectStartPos);
                m_selectedObject.conditionallyReattach();
                m_selectedObject.onObjectDrop(m_objectStartPos);
                m_objectPlacedSuccess = true;
                Global.franchiseManager.placementMode = false;
                Global.world.popGameMode();
                this.completePlaceResource(_loc_2);
            }
            else
            {
                if (_loc_2 && !Global.isVisiting())
                {
                    return;
                }
                super.handleObjectDrop(event);
            }
            return;
        }//end

         protected void  onObjectDropCompleted ()
        {
            MapResource _loc_1 =null ;
            Debug.debug6("GMPlaceMapResource.onObjectDropCompleted");


            if (m_objectPlacedSuccess)
            {
                this.completePlaceResource();
                if (m_selectedObject instanceof MapResource)
                {
                    _loc_1 =(MapResource) m_selectedObject;
                    _loc_1.onBuildingConstructionCompleted_PreServerUpdate();
                    if (this.m_resource.isRoadVerifiable && !(m_selectedObject as MapResource).isAdjacentToAnyRoad)
                    {
                        Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBadBuildingRoadPlacement"));
                    }
                    _loc_1.trackAction(TrackedActionType.PLACE);
                }
            }
            else
            {
                super.onObjectDropCompleted();
            }
            return;
        }//end

        protected int  getItemCost (Item param1 )
        {
            return param1.cost;
        }//end

        protected void  completePlaceResource (LotSite param1)
        {
            Specials _loc_4 =null ;
            MapResource _loc_5 =null ;
            int _loc_6 =0;
            OwnedFranchiseData _loc_7 =null ;
            FranchiseExpansionData _loc_8 =null ;
            GameMode _loc_9 =null ;
            _loc_2 =Global.gameSettings().getItemByName(this.m_item );

            Debug.debug6("GMPlaceMapResource.completePlaceResource");


            this.m_resource.setPosition(m_selectedObject.getPosition().x, m_selectedObject.getPosition().y, m_selectedObject.getPosition().z);
            this.m_resource.setDirection(m_selectedObject.getDirection());
            if (!param1)
            {
                if (m_isGift)
                {
                    Global.player.inventory.removeItems(this.m_item, 1);
                }
                else
                {
                    _loc_5 = this.m_constructionSite ? (this.m_constructionSite) : (this.m_resource);
                    _loc_6 = _loc_2.GetItemSalePrice();
                    if (_loc_2.cost != this.getItemCost(_loc_2))
                    {
                        _loc_6 = this.getItemCost(_loc_2);
                    }
                    if (_loc_2.cost > 0)
                    {
                        Global.player.gold = Global.player.gold - _loc_6;
                        _loc_5.displayStatus("-" + ZLoc.t("Main", "SpendCoins", {coins:_loc_6}));
                    }
                    else if (_loc_2.cash > 0)
                    {
                        Global.player.cash = Global.player.cash - _loc_6;
                        _loc_5.displayStatus("-" + ZLoc.t("Main", "SpendCash", {cash:_loc_6}));
                    }
                    else if (_loc_2.goods > 0)
                    {
                        Global.player.commodities.remove(Commodities.GOODS_COMMODITY, _loc_2.goods);
                        _loc_5.displayStatus("-" + ZLoc.t("Main", "SpendCommodity", {amount:_loc_2.goods, commodity:Commodities.GOODS_COMMODITY}));
                    }
                    else
                    {
                        GlobalEngine.msg(_loc_2.name + " cost nothing, make sure this instanceof intentional behavior.");
                    }
                    if (_loc_2.plantXp > 0)
                    {
                        Global.player.xp = Global.player.xp + _loc_2.plantXp;
                    }
                    _loc_5.onPurchase();
                }
                _loc_4 = Specials.getInstance();
                if (_loc_4.isValidSpecial(this.m_item) && m_isGift == false)
                {
                    _loc_4.setUserSpecial(this.m_item, 1);
                }
                GameTransactionManager.addTransaction(this.transaction);
                m_selectedObject.setId(this.m_resource.getId());
            }
            else if (!Global.world.isOwnerCitySam)
            {
                Global.world.citySim.lotManager.placeOrder(Global.world.ownerId, param1.getId(), this.m_item);
                m_selectedObject.detach();
                m_selectedObject.cleanup();
                FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_APPROVEBUILDINGREQUEST, this.itemName);
            }
            else
            {
                GameTransactionManager.addTransaction(new TReplaceUserResource(this.m_item, param1));
                Global.world.citySim.lotManager.replaceLotSite(param1.getId(), this.m_item, "", Global.player.uid);
                m_selectedObject.detach();
                m_selectedObject.cleanup();
                _loc_7 = Global.franchiseManager.model.getOwnedFranchise(this.m_item);
                if (_loc_7)
                {
                    _loc_8 = FranchiseExpansionData.loadFirstFranchiseOnCitySamMap(this.m_item);
                    _loc_7.addLocation(_loc_8);
                }
            }
            boolean _loc_3 =false ;
            if (_loc_2.multiplace)
            {
                if (Global.player.gold < _loc_2.cost || Global.player.cash < _loc_2.cash)
                {
                    _loc_3 = true;
                }
                if (m_isGift && Global.player.inventory.getItemCount(_loc_2) <= 0)
                {
                    _loc_3 = true;
                }
            }
            else
            {
                _loc_3 = true;
            }
            if (_loc_3)
            {
                if (Global.isVisiting())
                {
                    if (Global.player.canEnterGMVisitBuy())
                    {
                        _loc_9 = new GMVisitBuy(Global.world.ownerId);
                    }
                    else
                    {
                        _loc_9 = new GMVisit(Global.world.ownerId);
                    }
                }
                else
                {
                    _loc_9 = new GMPlay();
                }
                Global.world.addGameMode(_loc_9);
            }
            else
            {
                Global.world.addGameMode(new GMPlaceMapResource(this.m_item, this.m_itemClass, m_isGift));
            }
            if (this.m_constructionSite)
            {
                m_selectedObject.removeChild(this.m_resource);
                this.m_resource.detach();
                this.m_resource.cleanUp();
                m_selectedObject.setVisible(true);
            }
            return;
        }//end

        public MapResource  displayedMapResource ()
        {
            return this.m_resource;
        }//end

    }



