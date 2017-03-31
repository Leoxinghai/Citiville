package Mechanics.GameEventMechanics;

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
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Modules.stats.types.*;

import Mechanics.GameMechanicInterfaces.*;

    public class SendOffPierMechanic extends BaseGameMechanic implements IPlantable
    {

        public  SendOffPierMechanic ()
        {
            return;
        }//end

         public String  getToolTipStatus ()
        {
            return ZLoc.t("Items", "SendOfAllShips");
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            Ship _loc_3 =null ;
            _loc_2 = Pier(m_owner).getAllConnectedShips();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.state == HarvestableResource.STATE_PLOWED)
                {
                    return true;
                }
            }
            return false;
        }//end

        public MechanicActionResult  onGMPlay (Object param1 , Object param2)
        {
            Catalog catalog ;
            Ship ship ;
            mechanicData = param1;
            params = param2;
            boolean blockOthers ;
            if (m_config.params.get("blockOthers") == "true")
            {
                blockOthers;
            }
            if (Global.guide.isActive())
            {
                return new MechanicActionResult(false, !blockOthers, false);
            }
            if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.MARKET_ASSETS))
            {
                return new MechanicActionResult(false, !blockOthers, false);
            }
            Ship.SetMarketClickedShip(this);
            catalog = UI.displayCatalog(new CatalogParams("ship_contract").setExclusiveCategory(true).setOverrideTitle("ship_contract").setCloseMarket(true).setIgnoreExcludeExperiments(true), false, true);
            double goodsMultiplier ;
            connectedShips = Pier(m_owner).getAllConnectedShips();
            int _loc_4 =0;
            _loc_5 = connectedShips;
            for(int i0 = 0; i0 < connectedShips.size(); i0++)
            {
            		ship = connectedShips.get(i0);


                if (ship.state == HarvestableResource.STATE_PLOWED)
                {
                    goodsMultiplier = goodsMultiplier + (1 + ship.getItem().harvestMultiplier / 100);
                }
            }
            catalog.updatePriceMultiplier(Math.round(goodsMultiplier));
            catalog.setFirstDataMultiplier(Math.round(goodsMultiplier));
            catalog .addEventListener (CloseEvent .CLOSE ,void  (CloseEvent event )
            {
                event.target.removeEventListener(CloseEvent.CLOSE, arguments.callee);
                catalog.setFirstDataMultiplier(1);
                return;
            }//end
            );
            return new MechanicActionResult(true, !blockOthers, false, {});
        }//end

        public boolean  plant (String param1 )
        {
            Ship _loc_3 =null ;
            _loc_2 = Pier(m_owner).getAllConnectedShips();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.state == HarvestableResource.STATE_PLOWED)
                {
                    _loc_3.plant(param1);
                }
            }
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "pier", "launch_all", param1, _loc_2.length.toString());
            return true;
        }//end

    }



