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

import Classes.inventory.*;
import Classes.orders.Hotel.*;
import Classes.sim.*;
import Classes.virals.*;
import Engine.*;
import Engine.Helpers.*;
import Events.*;
import Modules.guide.ui.*;

//import flash.events.*;
//import flash.geom.*;

    public class SocialBusiness extends CheckinMechanicResource implements IMerchant
    {
        protected Array m_commodities ;
        protected MerchantCrowdManager m_crowdManager =null ;
        protected GuideArrow m_guideArrow ;
        protected int m_requiredCustomers =0;
        public static  String STATE_CLOSED ="closed";
        public static  String STATE_OPEN ="open";
        public static  String STATE_HARVESTABLE ="harvestable";

        public  SocialBusiness (String param1 )
        {
            super(param1);
            this.m_crowdManager = new MerchantCrowdManager(this);
            m_objectType = WorldObjectTypes.SOCIAL_BUSINESS;
            m_isRoadVerifiable = true;
            m_maxNumVisitorInteractions = 2000000;
            this.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end

         public void  onMechanicDataChanged (GenericObjectEvent event )
        {
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (param1.mechanicData.harvestState)
            {
                mechanicData.put("harvestState",  param1.mechanicData.harvestState);
            }
            if (this.isRouteable())
            {
                this.updatePeepSpawning();
            }
            updateSparkleEffect();
            return;
        }//end

        public void  updatePeepSpawning ()
        {
            int _loc_1 =0;
            Object _loc_2 =null ;
            if (this.getState() == STATE_OPEN)
            {
                _loc_1 = 0;
                if (this.m_requiredCustomers == 0)
                {
                    if (mechanicData.get("harvestState"))
                    {
                        _loc_2 = mechanicData.get("harvestState");
                        if (_loc_2.hasOwnProperty("customers") && _loc_2.hasOwnProperty("customersReq"))
                        {
                            this.m_requiredCustomers = _loc_2.get("customersReq") - _loc_2.get("customers");
                            _loc_1 = this.m_requiredCustomers;
                        }
                    }
                }
                Global.world.citySim.npcManager.startSpawningBusinessPeeps(_loc_1);
            }
            else
            {
                this.m_requiredCustomers = 0;
                Global.world.citySim.npcManager.stopSpawningBusinessPeeps();
            }
            return;
        }//end

        public boolean  isAcceptingVisits ()
        {
            return this.isRouteable();
        }//end

        public void  planVisit (Peep param1 )
        {
            this.updatePeepSpawning();
            return;
        }//end

        public void  performVisit (Peep param1 )
        {
            return;
        }//end

        public void  performVisitAnimation (Peep param1 )
        {
            return;
        }//end

         public String  getToolTipStatus ()
        {
            Object _loc_1 =null ;
            int _loc_2 =0;
            int _loc_3 =0;
            if (!Global.isVisiting())
            {
                if (mechanicData.get("harvestState"))
                {
                    _loc_1 = mechanicData.get("harvestState");
                    if (_loc_1.hasOwnProperty("customers") && _loc_1.hasOwnProperty("customersReq"))
                    {
                        _loc_2 = _loc_1.get("customers");
                        _loc_3 = _loc_1.get("customersReq");
                        return ZLoc.t("Dialogs", "HotelCustomers", {served:_loc_2, max:_loc_3});
                    }
                }
            }
            return super.getToolTipStatus();
        }//end

         public String  getToolTipAction ()
        {
            Object _loc_1 =null ;
            int _loc_2 =0;
            int _loc_3 =0;
            if (!Global.isVisiting())
            {
                if (mechanicData.get("harvestState"))
                {
                    _loc_1 = mechanicData.get("harvestState");
                    if (_loc_1.hasOwnProperty("customers") && _loc_1.hasOwnProperty("customersReq"))
                    {
                        _loc_2 = _loc_1.get("customers");
                        _loc_3 = _loc_1.get("customersReq");
                        if (_loc_2 < _loc_3)
                        {
                            if (Global.world.viralMgr.getTimeTillCanPost(ViralType.HOTEL_CHECKIN) <= 0)
                            {
                                return ZLoc.t("Dialogs", "Hotel_clickToInviteGuests");
                            }
                            return ZLoc.t("Dialogs", "Hotel_clickToViewGuests");
                        }
                    }
                }
            }
            else
            {
                if (this.isOpenForFriend())
                {
                    return ZLoc.t("Dialogs", "Hotel_clickToCheckIn");
                }
                if (this.isCheckedIn())
                {
                    return ZLoc.t("Dialogs", "Hotel_clickToRequestVIP");
                }
                if (this.getState() == STATE_CLOSED)
                {
                    return ZLoc.t("Dialogs", "SocialBusiness_Closed");
                }
                if (this.isFriendFull())
                {
                    return ZLoc.t("Dialogs", "SocialBusiness_Full");
                }
            }
            return super.getToolTipAction();
        }//end

         public String  getToolTipHeader ()
        {
            _loc_1 = getCustomName();
            return _loc_1;
        }//end

        public void  onWaveFinished ()
        {
            return;
        }//end

         public boolean  isVisitorInteractable ()
        {
            return true;
        }//end

         public boolean  doesVisitActionCostEnergy ()
        {
            return false;
        }//end

         public void  onVisitPlayAction ()
        {
            return;
        }//end

         public void  onPanned ()
        {
            this.introduceSocialBusiness();
            return;
        }//end

        public void  introduceSocialBusiness ()
        {
            this.showGuideArrow();
            return;
        }//end

        public void  showGuideArrow ()
        {
            this.clearGuideArrow();
            _loc_1 = Global.getAssetURL("assets/hud/tutorialArrow.swf");
            _loc_2 = IsoMath.tilePosToPixelPos(Math.floor(getPosition().x+2*getSize().x),Math.floor(getPosition().y+2*getSize().y));
            this.m_guideArrow = Global.guide.displayLotArrow(_loc_1, _loc_2.x, _loc_2.y, 0, 0, 3, true);
            if (Global.stage)
            {
                Global.stage.addEventListener(MouseEvent.MOUSE_DOWN, this.onMouseClickStage, false, 0, true);
            }
            return;
        }//end

        public void  clearGuideArrow ()
        {
            if (this.m_guideArrow)
            {
                this.m_guideArrow.release();
                this.m_guideArrow = null;
            }
            return;
        }//end

        public void  onMouseClickStage (MouseEvent event )
        {
            this.clearGuideArrow();
            if (Global.stage)
            {
                Global.stage.removeEventListener(MouseEvent.MOUSE_DOWN, this.onMouseClickStage);
            }
            return;
        }//end

         public void  onObjectDropPreTansaction (Vector3 param1 )
        {
            Global.world.citySim.resortManager.updateBusiness(this);
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            reloadImage();
            super.onObjectDrop(param1);
            if (!isNeedingRoad)
            {
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            this.updatePeepSpawning();
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PreServerUpdate();
            Global.world.citySim.resortManager.updateBusiness(this);
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.world.citySim.resortManager.removeBusiness(this);
            return;
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            super.prepareForStorage(param1);
            Global.world.citySim.resortManager.removeBusiness(this);
            return;
        }//end

         public String  getState ()
        {
            if (mechanicData.get("harvestState") == null)
            {
                return STATE_CLOSED;
            }
            if (mechanicData.get("harvestState").get("customers") < mechanicData.get("harvestState").get("customersReq"))
            {
                return STATE_OPEN;
            }
            return STATE_HARVESTABLE;
        }//end

         public boolean  isFriendFull ()
        {
            Object _loc_1 =null ;
            if (!isRoomOnFloor(1) && !hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid))
            {
                return true;
            }
            if (mechanicData.get("harvestState"))
            {
                _loc_1 = mechanicData.get("harvestState");
                if (_loc_1.hasOwnProperty("cashFilled") && _loc_1.get("cashFilled"))
                {
                    return true;
                }
            }
            return false;
        }//end

         public boolean  isVIPRequested ()
        {
            if ((this.getState() == Hotel.STATE_OPEN || this.getState() == Hotel.STATE_HARVESTABLE) && hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_REQUESTED)
            {
                return true;
            }
            return false;
        }//end

         public boolean  isVIP ()
        {
            if ((this.getState() == Hotel.STATE_OPEN || this.getState() == Hotel.STATE_HARVESTABLE) && hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && (getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_GRANTED || getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_ACCEPTED))
            {
                return true;
            }
            return false;
        }//end

         public boolean  isCheckedIn ()
        {
            if ((this.getState() == Hotel.STATE_OPEN || this.getState() == Hotel.STATE_HARVESTABLE) && hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_NOTREQUESTED)
            {
                return true;
            }
            return false;
        }//end

         public boolean  isOpenForFriend ()
        {
            Object _loc_2 =null ;
            boolean _loc_1 =false ;
            if (mechanicData.get("harvestState"))
            {
                _loc_2 = mechanicData.get("harvestState");
                if (_loc_2.hasOwnProperty("cashFilled") && _loc_2.get("cashFilled"))
                {
                    _loc_1 = true;
                }
            }
            if (this.getState() != Hotel.STATE_CLOSED && !hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && isRoomOnFloor(1) && !_loc_1)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isRouteable ()
        {
            boolean _loc_1 =false ;
            if (isOpenForNPC() && !isNeedingRoad)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public boolean  hasHotspot ()
        {
            boolean _loc_1 =false ;
            if (getHotspot() != null)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public MapResource  getMapResource ()
        {
            return this;
        }//end

        public void  makePeepEnterTarget (Peep param1 )
        {
            this.m_crowdManager.makeNpcEnterMerchant(param1);
            return;
        }//end

        public MerchantCrowdManager  crowdManager ()
        {
            return this.m_crowdManager;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.m_crowdManager.onUpdate(param1);
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage("static",this,m_direction,m_statePhase);
            return _loc_1;
        }//end

         public boolean  isCurrentImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading("static", m_direction, m_statePhase);
        }//end

         public boolean  isDrawImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading("static", m_direction, m_statePhase);
        }//end

         protected Vector3 Vector  computeDefaultHotspots ().<>
        {
            _loc_1 = m_item.getCachedImageHotspots("static",m_direction);
            return _loc_1 != null ? (_loc_1) : (super.computeDefaultHotspots());
        }//end

         public boolean  deferProgressBarToNPC ()
        {
            return false;
        }//end

         public Array  npcNames ()
        {
            _loc_1 = this.m_item.getRandomSpawnNpc();
            return _loc_1 != null ? (.get(_loc_1)) : (super.npcNames);
        }//end

        public double  guestCapacity ()
        {
            return 0;
        }//end

        public Array  commodities ()
        {
            Object _loc_1 =null ;
            if (!this.m_commodities)
            {
                this.m_commodities = new Array();
                for(int i0 = 0; i0 < m_item.commodities.size(); i0++)
                {
                		_loc_1 = m_item.commodities.get(i0);

                    this.m_commodities.push(_loc_1.name);
                }
            }
            return this.m_commodities;
        }//end

        public boolean  acceptsAsSupplies (String param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.commodities.size(); i0++)
            {
            	_loc_2 = this.commodities.get(i0);

                if (param1 == _loc_2)
                {
                    return true;
                }
            }
            return false;
        }//end

        private boolean  checkIfCanSupplyPremiumGoods ()
        {
            if (!this.acceptsAsSupplies(Commodities.PREMIUM_GOODS_COMMODITY))
            {
                return false;
            }
            if (Global.isVisiting())
            {
                return false;
            }
            if (Global.player.commodities.getCount(Commodities.PREMIUM_GOODS_COMMODITY) > 0)
            {
                return true;
            }
            return false;
        }//end

        public double  maxPossibleHarvestBonus ()
        {
            ItemDefinitionHotelFloor _loc_3 =null ;
            double _loc_1 =0;
            _loc_2 = m_item.hotel.floors.length;
            int _loc_4 =0;
            while (_loc_4 < _loc_2)
            {

                _loc_3 = getFloor(_loc_4);
                _loc_1 = _loc_1 + _loc_3.roomCount * _loc_3.socialBusinessHarvestBonus;
                _loc_4++;
            }
            return _loc_1;
        }//end

        public double  currentHarvestBonus ()
        {
            int _loc_3 =0;
            ItemDefinitionHotelFloor _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_1 = mechanicData.get("harvestState") ;
            double _loc_2 =0;
            if (_loc_1.hasOwnProperty("cashFilled") && _loc_1.get("cashFilled") == true)
            {
                _loc_2 = this.maxPossibleHarvestBonus;
            }
            else
            {
                _loc_3 = m_item.hotel.floors.length;
                _loc_6 = 0;
                while (_loc_6 < _loc_3)
                {

                    _loc_4 = getFloor(_loc_6);
                    _loc_5 = getHotelVisitorCountForFloor(_loc_6);
                    _loc_2 = _loc_2 + _loc_5 * _loc_4.socialBusinessHarvestBonus;
                    _loc_6++;
                }
            }
            return _loc_2;
        }//end

         public String  closedFlyoutText ()
        {
            return ZLoc.t("Main", "SocialBusinessClosed");
        }//end

         public String  fullFlyoutText ()
        {
            return ZLoc.t("Main", "SocialBusinessFull");
        }//end

         public String  getDefaultUGCCheckinMessage (int param1 )
        {
            _loc_2 = (String)(param1 );
            if (param1 == 0)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("VIP_Location"));
            }
            else if (param1 == 1)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("Floor1_Location"));
            }
            else if (param1 == 2)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("Floor2_Location"));
            }
            return ZLoc.t("Dialogs", "FriendNPC_tonga_tooltip_noUgcDefaultMsg", {seattype:_loc_2});
        }//end

         public String  getFloorCaption (int param1 )
        {
            _loc_2 = ZLoc.t("Dialogs","HotelDialog_floorText",{floorID param1 });
            if (param1 == 0)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("VIP_Location"));
            }
            else if (param1 == 1)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("Floor1_Location"));
            }
            else if (param1 == 2)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyFloorName("Floor2_Location"));
            }
            return _loc_2;
        }//end

        public String  getLocKeyFloorName (String param1 )
        {
            _loc_2 = getItem().getUI_skin_param("floor_name_loc_override");
            if (_loc_2)
            {
                return _loc_2 + "_" + param1;
            }
            return "SocialBusiness_" + param1;
        }//end

    }



