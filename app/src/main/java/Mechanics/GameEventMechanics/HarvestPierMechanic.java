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
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Modules.stats.types.*;


    public class HarvestPierMechanic extends BaseGameMechanic
    {

        public  HarvestPierMechanic ()
        {
            return;
        }//end

         public String  getToolTipStatus ()
        {
            return ZLoc.t("Items", "HarvestAllShips");
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            Ship _loc_3 =null ;
            _loc_2 = Pier(m_owner).getAllConnectedShips();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.isHarvestable())
                {
                    return true;
                }
            }
            return false;
        }//end

        public MechanicActionResult  onGMPlay (Object param1 , Object param2)
        {
            Vector<Ship> harvestableShips;
            mechanicData = param1;
            params = param2;
            harvestableShips = this.getAllHarvestableConnectedShips();
            UI .displayPopup (new GenericDialog (ZLoc .t ("Dialogs","HarvestAllShips_areYouSure",{harvestableShips num .length }),"",GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                _loc_2 = null;
                _loc_3 = null;
                switch(event.button)
                {
                    case GenericDialogView.YES:
                    {
                        if (hasCapacityToCollect(harvestableShips))
                        {
                            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "pier", "collect_all", "", harvestableShips.length.toString());
                            _loc_2 = 0;
                            for(int i0 = 0; i0 < harvestableShips.size(); i0++)
                            {
                            		_loc_3 = harvestableShips.get(i0);

                                if (_loc_3.isHarvestable())
                                {
                                    _loc_3.doHarvest(false);
                                    _loc_2 = _loc_2 + 1;
                                }
                            }
                            Pier(m_owner).doEnergyChanges(-_loc_2, ["energy", "expenditures", "harvest_pier"]);
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                return;
            }//end
            ));
            boolean blockOthers ;
            if (m_config.params.get("blockOthers") == "true")
            {
                blockOthers;
            }
            return new MechanicActionResult(true, !blockOthers, false, {});
        }//end

        private Ship Vector  getAllHarvestableConnectedShips ().<>
        {
            return Pier (m_owner ).getAllConnectedShips ().filter (boolean  (Ship param1 ,int param2 ,Vector param3 .<Ship >)
            {
                return param1.isHarvestable();
            }//end
            );
        }//end

        private boolean  hasCapacityToCollect (Vector param1 .<Ship >)
        {
            Ship _loc_3 =null ;
            Vector _loc_2.<Item >=new Vector<Item >(0);
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                if (_loc_3.isHarvestable() && _loc_3.contract != null)
                {
                    _loc_2.push(_loc_3.contract);
                }
            }
            return !Global.player.commodities.isAtCommodityCapacityForBulkCollection(_loc_2);
        }//end

    }



