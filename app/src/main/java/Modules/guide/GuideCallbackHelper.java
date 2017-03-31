package Modules.guide;

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
import Display.*;
import Display.MarketUI.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.ClientDisplayMechanics.*;
import Modules.guide.actions.*;
//import flash.utils.*;

    public class GuideCallbackHelper
    {
        protected Business m_deliveryBusiness =null ;

        public  GuideCallbackHelper ()
        {
            return;
        }//end

        public void  registerHandlers (Guide param1 )
        {
            param1.registerCallback("advanceToCatalogItem", this.advanceToCatalogItem);
            param1.registerCallback("isCatalogOpen", this.isCatalogOpen);
            param1.registerCallback("isCatalogClosed", this.isCatalogClosed);
            param1.registerCallback("closeCatalog", this.closeCatalog);
            param1.registerCallback("openCatalog", this.openCatalog);
            param1.registerCallback("isPlacingResource", this.isPlacingResource);
            param1.registerCallback("isPlantingContract", this.isPlantingContract);
            param1.registerCallback("isPlayMode", this.isPlayMode);
            param1.registerCallback("createRandomNpcs", this.createRandomNpcs);
            param1.registerCallback("noMoreDoobers", this.noMoreDoobers);
            param1.registerCallback("isCatalogTabOpen", this.isCatalogTabOpen);
            param1.registerCallback("spawnedDoobers", this.spawnedDoobers);
            param1.registerCallback("finishedMoveIn", this.finishedMoveIn);
            param1.registerCallback("scrollToIsoPosition", this.scrollToIsoPosition);
            param1.registerCallback("smoothZoom", this.smoothZoom);
            param1.registerCallback("isProgressStarted", this.isProgressStarted);
            param1.registerCallback("isActionMenuOpen", this.isActionMenuOpen);
            param1.registerCallback("isInventoryOpen", this.isInventoryOpen);
            param1.registerCallback("isVisitingFriend", this.isVisitingFriend);
            param1.registerCallback("isFranchiseMenuOpen", this.isFranchiseMenuOpen);
            param1.registerCallback("isModalDialogClosed", this.isModalDialogClosed);
            param1.registerCallback("isWorldLoaded", this.isWorldLoaded);
            param1.registerCallback("isDonePanning", this.isDonePanning);
            param1.registerCallback("advanceNotifyGuideMechanic", NotifyGuideMechanic.onGuideComplete);
            param1.registerCallback("isDialogOpen", this.isDialogOpen);
            param1.registerCallback("hasFlowersPlanted", this.hasFlowersPlanted);
            param1.registerCallback("openMunicipalCenterUpgradeDialog", this.openMunicipalCenterUpgradeDialog);
            param1.registerObject("bottomhud", Global.ui.bottomUI);
            param1.registerObject("stage", Global.stage);
            param1.registerCallback("trackCounter", this.trackCounter);
            return;
        }//end

        public boolean  openMunicipalCenterUpgradeDialog (XMLList param1 )
        {
            MunicipalCenter _loc_3 =null ;
            _loc_2 =Global.world.getObjectsByNames(.get( "city_center_1","city_center_2","city_center_3","city_center_4","city_center_5","city_center_6") );
            if (_loc_2.length == 0)
            {
                Global.questManager.pumpActivePopup("qf_birthday_2011");
                return true;
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                MechanicManager.getInstance().handleAction(_loc_3, MechanicManager.PLAY);
            }
            return true;
        }//end

        public boolean  hasFlowersPlanted (XMLList param1 )
        {
            Array _loc_4 =null ;
            Garden _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            _loc_2 = (int)(param1.@quantity);
            int _loc_3 =0;
            if (_loc_2 > 0)
            {
                _loc_4 = Global.world.getObjectsByClass(Garden);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_6 = _loc_5.slots;
                    if (_loc_5 && _loc_6)
                    {
                        for(int i0 = 0; i0 < _loc_6.size(); i0++)
                        {
                        		_loc_7 = _loc_6.get(i0);

                            if (_loc_6.get(_loc_7))
                            {
                                _loc_3++;
                            }
                            if (_loc_3 >= _loc_2)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }//end

        public boolean  isDialogOpen (XMLList param1 )
        {
            _loc_2 = (String)(param1.@dialogClassName);
            _loc_3 = (String)(param1.@waitForLoad) == "true";
            _loc_4 = getDefinitionByName(_loc_2);
            if (getDefinitionByName(_loc_2) == null)
            {
                return false;
            }
            String _loc_5 ="";
            if (UI.currentPopup)
            {
                _loc_5 = getQualifiedClassName(UI.currentPopup);
                if (_loc_5 == _loc_2 && (!_loc_3 || _loc_3 && UI.currentPopup.isShown))
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  isModalDialogClosed (XMLList param1 )
        {
            if (UI.isModalDialogOpen)
            {
                return false;
            }
            return true;
        }//end

        public boolean  isFranchiseMenuOpen (XMLList param1 )
        {
            if (!Global.ui.franchiseMenu)
            {
                return false;
            }
            return Global.ui.franchiseMenu.visible;
        }//end

        public boolean  isVisitingFriend (XMLList param1 )
        {
            if (!(Global.world.getTopGameMode() instanceof GMVisit))
            {
                return false;
            }
            _loc_2 = param1.@friendId;
            if (Global.getVisiting() != _loc_2)
            {
                return false;
            }
            return true;
        }//end

        public boolean  isActionMenuOpen (XMLList param1 )
        {
            return Global.ui.isActionMenuOpen;
        }//end

        public boolean  isInventoryOpen (XMLList param1 )
        {
            if (!Global.ui.inventoryView)
            {
                return false;
            }
            return Global.ui.inventoryView.isShown;
        }//end

        public boolean  advanceToCatalogItem (XMLList param1 )
        {
            _loc_2 = (String)(param1.@item);
            _loc_3 =Global.gameSettings().getItemByName(_loc_2 );
            _loc_4 = _loc_3.type ;
            _loc_5 = UI.m_catalog;
            if (UI.m_catalog)
            {
                _loc_5.asset.switchType(_loc_4);
            }
            else
            {
                _loc_5 = UI.displayCatalog(new CatalogParams(_loc_4));
            }
            _loc_5.asset.goToItem(_loc_2);
            return true;
        }//end

        public boolean  scrollToIsoPosition (XMLList param1 )
        {
            double _loc_6 =0;
            _loc_2 = (int)(param1.@x);
            _loc_3 = (Int)(param1.@y);
            _loc_4 = param1.@instant;
            boolean _loc_5 =true ;
            if (_loc_4 == "false")
            {
                _loc_5 = false;
            }
            else if (int(_loc_4) == 0)
            {
                _loc_5 = false;
            }
            if (_loc_5)
            {
                (GlobalEngine.viewport as IsoViewport).centerOnTilePos(_loc_2, _loc_3);
            }
            else
            {
                _loc_6 = Number(param1.@time);
                if (_loc_6 == 0)
                {
                    _loc_6 = 1;
                }
                Global.world.centerOnIsoPosition(new Vector3(_loc_2, _loc_3), _loc_6);
            }
            return true;
        }//end

        public boolean  smoothZoom (XMLList param1 )
        {
            _loc_2 = (int)(param1.@start);
            _loc_3 = (Int)(param1.@end);
            _loc_4 =(double)(param1.@time);
            _loc_5 = boolean(param1.@instant) == true;
            Global.world.zoom(_loc_2, _loc_3, _loc_4);
            return true;
        }//end

        public boolean  isCatalogTabOpen (XMLList param1 )
        {
            _loc_2 = param1.@type;
            _loc_3 = UI.m_catalog;
            if (_loc_3 && _loc_3.type == _loc_2)
            {
                return true;
            }
            return false;
        }//end

        public boolean  spawnedDoobers (XMLList param1 )
        {
            return Global.world.dooberManager.getDooberCount() > 0 && Global.world.dooberManager.areDoobersLanded();
        }//end

        public boolean  noMoreDoobers (XMLList param1 )
        {
            return Global.world.dooberManager.getDooberCount() <= 0;
        }//end

        public boolean  finishedMoveIn (XMLList param1 )
        {
            Residence _loc_3 =null ;
            _loc_2 =Global.world.getObjectsByClass(Residence );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.isMovingIn)
                {
                    return false;
                }
            }
            return _loc_2.length > 0;
        }//end

        public boolean  createRandomNpcs (XMLList param1 )
        {
            _loc_2 = (int)(param1.@count);
            Global.world.citySim.npcManager.createBusinessPeepWalkers(_loc_2);
            return true;
        }//end

        public boolean  isCatalogOpen (XMLList param1 )
        {
            return UI.m_catalog != null && UI.m_catalog.asset != null && UI.m_catalog.asset.isInitialized && UI.m_catalog.isShown;
        }//end

        public boolean  isCatalogClosed (XMLList param1 )
        {
            return UI.m_catalog == null || !UI.m_catalog.isShown;
        }//end

        public boolean  openCatalog (XMLList param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (this.isCatalogClosed(param1))
            {
                if (param1.@tabName)
                {
                    _loc_2 = param1.@itemName.toString() != "" ? (param1.@itemName) : (null);
                    _loc_3 = param1.@subTabName;
                    if (_loc_3.length == 0)
                    {
                        _loc_3 = null;
                    }
                    _loc_4 = param1.@tabName;
                    UI.displayCatalog(new CatalogParams(_loc_4).setSubType(_loc_3).setItemName(_loc_2));
                }
            }
            return true;
        }//end

        public boolean  closeCatalog (XMLList param1 )
        {
            if (UI.m_catalog != null)
            {
                UI.m_catalog.close();
                UI.m_catalog = null;
            }
            return true;
        }//end

        public boolean  isPlacingResource (XMLList param1 )
        {
            _loc_2 =Global.world.getTopGameMode ()as GMPlaceMapResource ;
            _loc_3 = (String)(param1.@item);
            return _loc_2 != null && (_loc_3 == "" || _loc_3 == _loc_2.itemName);
        }//end

        public boolean  isPlantingContract (XMLList param1 )
        {
            _loc_2 =Global.world.getTopGameMode ()as GMPlant ;
            _loc_3 = (String)(param1.@item);
            return _loc_2 != null && (_loc_3 == "" || _loc_3 == _loc_2.m_contractType);
        }//end

        public boolean  isPlayMode (XMLList param1 )
        {
            return Global.world.getTopGameMode() instanceof GMPlay;
        }//end

        public boolean  isProgressStarted (XMLList param1 )
        {
            Object def ;
            Class className ;
            GameObject obj ;
            xmldef = param1;
            try
            {
                def = getDefinitionByName("Classes." + xmldef.@className);
                className =(Class) def;


                for(int i0 = 0; i0 < Global.world.getObjectsByClass(className).size(); i0++)
                {
                		obj = Global.world.getObjectsByClass(className).get(i0);


                    if (obj.actionBar)
                    {
                        return true;
                    }
                }
            }
            catch (e:Error)
            {
            }
            return false;
        }//end

        public boolean  isWorldLoaded (XMLList param1 )
        {
            return !Global.isTransitioningWorld;
        }//end

        public boolean  isDonePanning (XMLList param1 )
        {
            return !GAPanMap.isPanningMap;
        }//end

        public boolean  trackCounter (XMLList param1 )
        {
            if (!param1.@counter)
            {
                if (Config.DEBUG_MODE)
                {
                    throw new Error("Invalid Guide Stat: Must specify a counter");
                }
                return true;
            }
            _loc_2 = param1.@counter;
            _loc_3 = param1.@kingdom;
            _loc_4 = param1.@phylum;
            _loc_5 = param1.@zclass;
            _loc_6 = param1.@family;
            _loc_7 = param1.@genus;
            _loc_8 = param1.hasOwnProperty("@value")? (param1.@value) : (1);
            StatsManager.count(_loc_2, _loc_3, _loc_4, _loc_5, _loc_6, _loc_7, _loc_8);
            return true;
        }//end

    }



