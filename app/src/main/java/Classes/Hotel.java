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
import Classes.inventory.*;
import Classes.orders.Hotel.*;
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Display.Toaster.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.guide.ui.*;
import Modules.hotels.*;
import Modules.quest.Managers.*;
import Modules.stats.types.*;

//import flash.events.*;
//import flash.geom.*;

    public class Hotel extends CheckinMechanicResource implements IMerchant
    {
        protected int m_resortRadius =0;
        protected HotelCrowdManager m_crowdManager =null ;
        protected Array m_commodities ;
        protected GuideArrow m_guideArrow ;
        public static  String STATE_CLOSED ="closed";
        public static  String STATE_OPEN ="open";
        public static  String STATE_HARVESTABLE ="harvestable";
public static  int VIPFLOORID =0;

        public  Hotel (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.HOTEL;
            this.m_crowdManager = new HotelCrowdManager(this);
            m_isRoadVerifiable = true;
            m_maxNumVisitorInteractions = 2000000;
            if (m_item && m_item.shockwaveXml.length())
            {
                this.m_resortRadius = int(m_item.shockwaveXml.@radius);
            }
            this.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end

         public void  onMechanicDataChanged (GenericObjectEvent event )
        {
            boolean _loc_2 =false ;
            if (HotelsDialogView.getInstance())
            {
                HotelsDialogView.getInstance().updateMechanicText();
            }
            if (Global.isVisiting())
            {
                _loc_2 = false;
                if (this.getState() == STATE_OPEN)
                {
                    if (visitorCheckedIntoHotelFloor(Global.getVisiting(), Global.player.uid) != VIPFLOORID)
                    {
                        _loc_2 = true;
                    }
                }
                if (_loc_2)
                {
                    if (!hasAnimatedEffects())
                    {
                        addAnimatedEffect(EffectType.SPARKLE);
                    }
                }
                else
                {
                    this.removeAnimatedEffects();
                }
            }
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (param1.mechanicData.harvestState)
            {
                mechanicData.put("harvestState", param1.mechanicData.harvestState);
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
            return;
        }//end

        public boolean  isAcceptingVisits ()
        {
            return this.isRouteable();
        }//end

        public void  planVisit (Peep param1 )
        {
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
                if (this.getState() == STATE_OPEN && !hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid))
                {
                    return ZLoc.t("Dialogs", "Hotel_clickToCheckIn");
                }
                if (this.getState() == STATE_OPEN && hasVisitorCheckedIntoHotel(Global.getVisiting(), Global.player.uid) && getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_NOTREQUESTED)
                {
                    return ZLoc.t("Dialogs", "Hotel_clickToRequestVIP");
                }
                if (this.getState() == STATE_HARVESTABLE)
                {
                    return ZLoc.t("Dialogs", "Hotel_Full");
                }
                if (this.getState() == STATE_CLOSED)
                {
                    return ZLoc.t("Dialogs", "Hotel_Closed");
                }
            }
            return super.getToolTipAction();
        }//end

         public String  getToolTipHeader ()
        {
            _loc_1 = getCustomName();
            return _loc_1;
        }//end

        public void  introduceHotel ()
        {
            this.showGuideArrow();
            this.showIntroToaster();
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

        public void  showIntroToaster ()
        {
            _loc_1 = Global.world.ownerId;
            _loc_2 = Global.player.getFriendCityName(_loc_1);
            String _loc_3 ="";
            _loc_4 = ZLoc.t("Dialogs","HotelToasterIntroText",{cityname_loc_2});
            _loc_5 = Global.getAssetURL("assets/dialogs/toaster_edgar.png");
            ItemToaster _loc_6 =new ItemToaster(_loc_3 ,_loc_4 ,_loc_5 );
            _loc_6.duration = 10000;
            Global.ui.toaster.show(_loc_6);
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
            this.introduceHotel();
            return;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            Array _loc_2 =null ;
            MapResource _loc_3 =null ;
            int _loc_4 =0;
            ItemToaster _loc_5 =null ;
            super.onMouseOver(event);
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                this.m_crowdManager.startWavePreview();
                _loc_2 = Global.world.citySim.resortManager.getBusinessesForHotel(getId());
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_4 = _loc_2.get(i0);

                    _loc_3 =(MapResource) Global.world.getObjectById(_loc_4);
                    _loc_3.enableGlow();
                }
                if (!Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_HOTEL_TOASTER))
                {
                    _loc_5 = new ItemToaster(ZLoc.t("Dialogs", "HotelToasterTitle"), ZLoc.t("Dialogs", "HotelToasterHint"), Global.gameSettings().getImageByName(m_item.name, "icon"));
                    Global.ui.toaster.show(_loc_5, true);
                }
            }
            return;
        }//end

         public void  onMouseOut ()
        {
            Array _loc_1 =null ;
            MapResource _loc_2 =null ;
            int _loc_3 =0;
            super.onMouseOut();
            if (this.m_crowdManager)
            {
                this.m_crowdManager.endWavePreview();
            }
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                _loc_1 = Global.world.citySim.resortManager.getBusinessesForHotel(getId());
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_3 = _loc_1.get(i0);

                    _loc_2 =(MapResource) Global.world.getObjectById(_loc_3);
                    _loc_2.disableGlow();
                }
                Global.ui.toaster.hide();
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

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            Global.world.citySim.resortManager.updateAllBusinesses();
            super.onBuildingConstructionCompleted_PreServerUpdate();
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
            return;
        }//end

         public void  onObjectDropPreTansaction (Vector3 param1 )
        {
            Global.world.citySim.resortManager.updateAllBusinesses();
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

         public int  getPopularity ()
        {
            return 0;
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
            return m_item.commodityReq;
        }//end

        public int  resortRadius ()
        {
            return this.m_resortRadius;
        }//end

        public void  onWaveFinished ()
        {
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            super.onUpgrade(param1, param2);
            if (m_item && m_item.shockwaveXml.length())
            {
                this.m_resortRadius = int(m_item.shockwaveXml.@radius);
            }
            this.m_crowdManager.cleanup();
            this.m_crowdManager = null;
            this.m_crowdManager = new HotelCrowdManager(this);
            Global.world.citySim.resortManager.resetAndParse();
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "hotel", "upgrade_from", param1.name);
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "hotel", "upgrade_to", param2.name);
            return;
        }//end

         public void  notifyUpgrade (Object param1 )
        {
            Global.world.citySim.resortManager.resetAndParse();
            return;
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

         public Class  getCursor ()
        {
            _loc_1 = super.getCursor();
            if (this.getState() == STATE_CLOSED && !isNeedingRoad)
            {
                _loc_1 = EmbeddedArt.hud_biz_supply;
            }
            else
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

         public boolean  isUpgradePossible ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            ItemImage _loc_4 =null ;
            _loc_1 = super.isUpgradePossible();
            if (_loc_1 == true)
            {
                _loc_2 = Global.gameSettings().getEffectByName("fireworks");
                _loc_3 = _loc_2.image.get(0);
                _loc_4 = new ItemImage(_loc_3);
                _loc_4.load();
                Sounds.loadSoundByName("cruise_fireworks");
            }
            return _loc_1;
        }//end

         public String  closedFlyoutText ()
        {
            return ZLoc.t("Main", "HotelClosed");
        }//end

         public String  fullFlyoutText ()
        {
            return ZLoc.t("Main", "HotelFull");
        }//end

    }




