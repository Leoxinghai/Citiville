package Modules.mechanics.MarketUI.model;

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
import Classes.effects.*;
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.GridlistUI.model.*;
import Events.*;
import GameMode.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.crew.*;
import Modules.mechanics.MarketUI.view.*;
import Modules.mechanics.ui.*;
import Modules.mechanics.ui.items.*;
import Transactions.*;
//import flash.geom.*;
import org.aswing.geom.*;

    public class SlottedContainerCellModel extends ItemInstanceCellModel
    {
        protected SlottedContainerConfig m_config ;
        protected String m_crewMember ;

        public  SlottedContainerCellModel (SlottedContainerConfig param1 )
        {
            this.m_config = param1;
            return;
        }//end

         protected void  loadAssets ()
        {
            this.m_assetsToLoad = 3;
            Global.delayedAssets.get(DelayedAssetLoader.MARKET_ASSETS, onAssetsLoaded);
            Global.delayedAssets.get(DelayedAssetLoader.SLOTTED_CONTAINER_ASSETS, onAssetsLoaded);
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, onAssetsLoaded);
            return;
        }//end

        public void  onRemove ()
        {
            _loc_1 = (SlottedContainerCellView)getCellComponent(()
            _loc_2 = this.m_config.storageMechanic ;
            _loc_3 = _loc_1.getDataForMechanic(_loc_2.type );
            _loc_4 = this.m_config.slotsPerTab ;
            _loc_5 = (SlottedContainerCellView)getCellComponent(()
            _loc_6 = _loc_4*_loc_5 +m_index ;
            if (m_index < 0 || _loc_6 < 0 || _loc_6 >= _loc_3.length())
            {
                return;
            }
            Global.world.addGameMode(new GMMechanicPlaceFromStorage(_loc_1, _loc_6, m_value.data as ItemInstance));
            return;
        }//end

        public void  onAskCrew ()
        {
            _loc_1 = (MechanicMapResource)getCellComponent(()asSlottedContainerCellView).generator
            _loc_2 = this.m_config.slotGate ;
            _loc_3 = _loc_1"crew.php?mId="+.getId ().toString ()+"&gname="+_loc_2.name ;
            FrameManager.navigateTo(_loc_3);
            return;
        }//end

        public void  onBuyCrew ()
        {
            MechanicMapResource mechanicUser ;
            GateItem crewGate ;
            int crewCost ;
            mechanicUser =(SlottedContainerCellView).generator as MechanicMapResource) (getCellComponent();
            itemName = mechanicUser.getItemName();
            crewGate = this.m_config.slotGate;
            crewCost = CrewGate.getCrewCost(itemName, crewGate.name);
            buy = this.m_config.getTitleZlocItem("buy");
            UI .displayMessage (ZLoc .t (buy .pkg ,buy .key ,{crewCost amount }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (Global.player.canBuyCash(crewCost, true))
                    {
                        GameTransactionManager.addTransaction(new TBuyCrew(mechanicUser.getId(), 0, crewCost, crewGate.name), true);
                        Global.world.addGameMode(new GMPlay());
                    }
                }
                return;
            }//end
            );
            return;
        }//end

        public String  getCrewMemberForIdx (int param1 ,int param2 )
        {
            _loc_3 = (MechanicMapResource)getCellComponent(()asSlottedContainerCellView).generator
            _loc_4 = GameWorld.CITY_SAM_OWNER_ID;
            _loc_5 =Global.crews.getCrewById(_loc_3.getId ());
            _loc_6 = _loc_3.getItem ().storageInitCapacity ;
            _loc_7 = this.m_config.slotsPerTab ;
            _loc_8 = param2+param1*_loc_7-_loc_6;
            if (_loc_5 && _loc_8 >= 0 && _loc_8 < _loc_5.count)
            {
                if (Number(_loc_5.list.get(_loc_8)) > 0)
                {
                    _loc_4 = _loc_5.list.get(_loc_8);
                }
            }
            return _loc_4;
        }//end

        public void  onDisplayFilledCellTooltip ()
        {
            _loc_1 = (MapResource)getCellValue()
            if (!_loc_1)
            {
                return;
            }
            _loc_2 = (SlottedContainerSlotCellView)getCellComponent()
            _loc_3 = _loc_2.tabId ;
            _loc_4 = _loc_2.slotId ;
            _loc_5 = _loc_2.getGlobalLocation ();
            Point _loc_6 =new Point(_loc_5.x ,_loc_5.y );
            int _loc_7 =-1;
            if (_loc_1.canCountUpgradeActions())
            {
                _loc_7 = _loc_1.upgradeActions.getTotal();
            }
            _loc_8 = this.m_config.getTooltipItem("filled");
            Object _loc_9 =new Object ();
            _loc_9.put("type",  _loc_8.type);
            _loc_9.put("title",  _loc_1.getItem().localizedName);
            _loc_9.put("item",  _loc_1.getItem());
            _loc_9.put("crewName",  Global.player.getFriendName(this.getCrewMemberForIdx(_loc_3, _loc_4)));
            _loc_9.put("upgradeLevel",  _loc_1.getItem().level);
            _loc_9.put("upgradeProgress",  _loc_7);
            _loc_9.put("population",  _loc_1.getPopulationYield());
            Point _loc_10 =new Point(15,-20);
            DataItemEvent _loc_11 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,null ,_loc_6 ,true );
            _loc_11.setParams(_loc_9);
            _loc_11.setOffset(_loc_10);
            _loc_2.dispatchEvent(_loc_11);
            return;
        }//end

        public void  onDisplayEmptyCellTooltip ()
        {
            _loc_1 = (SlottedContainerSlotCellView)getCellComponent()
            _loc_2 = _loc_1.tabId ;
            _loc_3 = _loc_1.slotId ;
            _loc_4 = _loc_1.getGlobalLocation ();
            Point _loc_5 =new Point(_loc_4.x ,_loc_4.y );
            _loc_6 = this.getCrewMemberForIdx(_loc_2 ,_loc_3 );
            _loc_7 =Global.player.getFriendName(_loc_6 );
            _loc_8 = this.m_config.getTooltipItem("empty");
            Object _loc_9 =new Object ();
            _loc_9.put("type",  _loc_8.type);
            _loc_9.put("title",  ZLoc.t(_loc_8.title.pkg, _loc_8.title.key));
            _loc_9.put("description",  ZLoc.t(_loc_8.text.pkg, _loc_8.text.key));
            if (_loc_7 != null)
            {
                _loc_9.put("requirement",  ZLoc.t(_loc_8.gate.pkg, _loc_8.gate.key, {crew:_loc_7}));
            }
            Point _loc_10 =new Point(15,-20);
            DataItemEvent _loc_11 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,null ,_loc_5 ,true );
            _loc_11.setParams(_loc_9);
            _loc_11.setOffset(_loc_10);
            _loc_1.dispatchEvent(_loc_11);
            return;
        }//end

        public void  onDisplayMysteryCellTooltip ()
        {
            _loc_1 = (SlottedContainerMysteryCellView)getCellComponent()
            _loc_2 = _loc_1.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            _loc_4 = (SlottedContainerCellView)getCellComponent(()
            _loc_5 = (ISlottedContainer)getCellComponent(()asSlottedContainerCellView).generator
            int _loc_6 =0;
            _loc_7 = _loc_4(+1)*this.m_config.slotsPerTab ;
            if (_loc_5.slots)
            {
                _loc_6 = _loc_5.slots.length;
            }
            TooltipItem _loc_8 =null ;
            Object _loc_9 ={};
            if (_loc_5.hasMysteryItemInInventory(_loc_4))
            {
                _loc_8 = this.m_config.getTooltipItem("mysteryawarded");
                _loc_9.put("type",  _loc_8.type);
                _loc_9.put("title",  ZLoc.t(_loc_8.title.pkg, _loc_8.title.key));
                _loc_9.put("description",  ZLoc.t(_loc_8.text.pkg, _loc_8.text.key));
            }
            else if (_loc_6 < _loc_7)
            {
                _loc_8 = this.m_config.getTooltipItem("mysterylocked");
                _loc_9.put("type",  _loc_8.type);
                _loc_9.put("title",  ZLoc.t(_loc_8.title.pkg, _loc_8.title.key));
                _loc_9.put("description",  ZLoc.t(_loc_8.text.pkg, _loc_8.text.key));
                _loc_9.put("requirement",  ZLoc.t(_loc_8.gate.pkg, _loc_8.gate.key, {num:_loc_7}));
            }
            else
            {
                _loc_8 = this.m_config.getTooltipItem("mysteryunlocked");
                _loc_9.put("type",  _loc_8.type);
                _loc_9.put("title",  ZLoc.t(_loc_8.title.pkg, _loc_8.title.key));
                _loc_9.put("description",  ZLoc.t(_loc_8.text.pkg, _loc_8.text.key));
            }
            Point _loc_10 =new Point(17,-20);
            DataItemEvent _loc_11 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,null ,_loc_3 ,true );
            _loc_11.setParams(_loc_9);
            _loc_11.setOffset(_loc_10);
            _loc_1.dispatchEvent(_loc_11);
            return;
        }//end

        public void  onDisplayCrewableCellTooltip ()
        {
            _loc_1 = (SlottedContainerCrewableCellView)getCellComponent()
            _loc_2 = _loc_1.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            _loc_4 = this.m_config.getTooltipItem("crewable");
            Object _loc_5 =new Object ();
            _loc_5.put("type",  _loc_4.type);
            _loc_5.put("title",  ZLoc.t(_loc_4.title.pkg, _loc_4.title.key));
            _loc_5.put("description",  ZLoc.t(_loc_4.text.pkg, _loc_4.text.key));
            _loc_5.put("requirement",  ZLoc.t(_loc_4.gate.pkg, _loc_4.gate.key));
            Point _loc_6 =new Point(15,-20);
            DataItemEvent _loc_7 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,null ,_loc_3 ,true );
            _loc_7.setParams(_loc_5);
            _loc_7.setOffset(_loc_6);
            _loc_1.dispatchEvent(_loc_7);
            return;
        }//end

        public void  onEnterStoreMode ()
        {
            _loc_1 = (SlottedContainerCellView)getCellComponent(()
            Global.world.addGameMode(new GMMechanicStore(_loc_1));
            return;
        }//end

        public void  onExitStoreMode ()
        {
            Global.world.addGameMode(new GMPlay());
            return;
        }//end

        public void  onUpgrade ()
        {
            Object _loc_3 =null ;
            TStoredObjectAction _loc_4 =null ;
            _loc_1 = (MapResource)getCellValue()
            _loc_2 = (MapResource)getCellComponent(()asSlottedContainerCellView).generator
            if (_loc_2 && _loc_1)
            {
                if (_loc_2 instanceof MunicipalCenter)
                {
                    _loc_1.upgradeBuildingIfPossible();
                    return;
                }
                _loc_3 = new Object();
                _loc_3.put("storageId",  _loc_2.getId());
                _loc_3.put("storedObjectId",  _loc_1.getId());
                if (_loc_1.isUpgradePossible())
                {
                    _loc_4 = new TStoredObjectAction(TStoredObjectAction.UPGRADE, null, _loc_3);
                    _loc_1.upgradeBuildingIfPossible(false, _loc_4, false);
                    this.playUpgradeEffects();
                }
                Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.STORED_ITEM_UPGRADE, null));
            }
            return;
        }//end

        private void  playUpgradeEffects ()
        {
            MapResource slottedContainerMapResource ;
            slottedContainerMapResource =(SlottedContainerCellView).generator as MapResource) (getCellComponent();
            int effectCount ;
            int effectInterval ;
            Sounds.play("cruise_fireworks");
            int i ;
            while (i <= effectCount)
            {

                TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(slottedContainerMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , i * effectInterval);
                i = (i + 1);
            }
            return;
        }//end

    }



