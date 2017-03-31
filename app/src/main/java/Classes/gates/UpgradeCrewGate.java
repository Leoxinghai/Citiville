package Classes.gates;

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
import Display.GateUI.*;
import Display.UpgradesUI.*;
import Engine.Managers.*;
import Modules.crew.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;


    public class UpgradeCrewGate extends CrewGate
    {
        public static  String GATE_NAME ="pre_upgrade";

        public  UpgradeCrewGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            String _loc_15 =null ;
            Object _loc_16 =null ;
            Player _loc_17 =null ;
            Player _loc_18 =null ;
            m_dialog = null;
            Array _loc_4 =new Array ();
            _loc_5 = Global.crews.getCrewById(m_targetObjectId);
            _loc_6 = this.getCrewNames();
            _loc_7 = Global.player.inventory.getItemCountByName("crew_bonus");
            _loc_8 = m_keys.get(REQUIRED_CREW);
            int _loc_9 =0;
            while (_loc_9 < _loc_8)
            {

                _loc_15 = _loc_5 && _loc_5.count > _loc_9 ? (_loc_5.list.get(_loc_9)) : (null);
                _loc_15 = normalizeUid(_loc_15);
                _loc_16 = new Object();
                _loc_17 = _loc_15 ? (Global.player.findFriendById(_loc_15)) : (null);
                _loc_18 = Global.player.findFriendById("-1");
                if (_loc_15)
                {
                    _loc_16.url = _loc_17 ? (_loc_17.snUser.picture) : (_loc_18.snUser.picture);
                    _loc_16.count = 1;
                    _loc_16.amountNeeded = 1;
                    _loc_16.footerText = _loc_17 ? (_loc_17.firstName) : (_loc_18.firstName);
                }
                else if (_loc_7 > 0)
                {
                    _loc_16.url = _loc_18.snUser.picture;
                    _loc_16.count = 1;
                    _loc_16.amountNeeded = 1;
                    _loc_16.footerText = ZLoc.t("Items", "crew_bonus_friendlyName");
                    _loc_7 = _loc_7 - 1;
                }
                else
                {
                    _loc_16.displayObjectClass = EmbeddedArt.hud_no_profile_pic;
                    _loc_16.cost = getCrewCost(m_unlockItemName, GATE_NAME);
                    _loc_16.count = 0;
                    _loc_16.amountNeeded = 1;
                    _loc_16.buyCallback = this.buyCrew;
                    _loc_16.footerText = ZLoc.t("Dialogs", "fillPosition");
                }
                _loc_16.name = _loc_6.get(_loc_9);
                _loc_16.backgroundClass = EmbeddedArt.crewFriendBackground;
                _loc_4.push(_loc_16);
                _loc_9++;
            }
            m_cellFactory = new KeyCellCrewFactory(_loc_4);
            _loc_10 = Global.gameSettings().getItemByName(m_unlockItemName);
            _loc_11 = ZLoc.t("Dialogs","CrewGateDialogTitle",{item_loc_10.localizedName});
            _loc_12 = Global.gameSettings().getItemByName(m_unlockItemName);
            _loc_13 = Global.gameSettings().getItemByName(m_unlockItemName).type;
            if (m_dialog == null)
            {
                if (_loc_13.toLowerCase() == "municipal")
                {
                    m_dialog = UI.displayMunicipalUpgradesDialog(m_unlockItemName, _loc_4, checkForKeys, gateFinishHandler, this.buyRemainingCrew, m_targetObjectId);
                }
            }
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.VIEW, _loc_10.upgrade.newItemName);
            _loc_14 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS) == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                FrameManager.preloadTray("crew.php?mId=" + m_targetObjectId.toString(), true);
            }
            return;
        }//end

         protected String Vector  getCrewNames ().<>
        {
            Vector<String> result;
            Item item ;
            XMLList gatesxml ;
            XML member ;
            String nameToken ;
            double amount ;
            String name ;
            int i ;
            result = new Vector<String>();
            item = Global.gameSettings().getItemByName(m_unlockItemName);
            int _loc_3 =0;
            _loc_4 = item.gatesXml.gate;
            XMLList _loc_2 =new XMLList("");
            Object _loc_5;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);


                with (_loc_5)
                {
                    if (@name == GATE_NAME)
                    {
                        _loc_2.put(_loc_3++,  _loc_5);
                    }
                }
            }
            gatesxml = _loc_2;


            for(int i0 = 0; i0 < gatesxml.get("key").get("member").size(); i0++)
            {
            	member = gatesxml.get("key").get("member").get(i0);


                nameToken = member.@name;
                amount = member.@amount;
                name = ZLoc.t("Items", nameToken);
                i = 0;
                while (i < amount)
                {

                    result.push(name);
                    i = (i + 1);
                }
            }
            return result;
        }//end

         protected void  askForCrew (String param1)
        {
            _loc_2 = Global.gameSettings().getItemByName(m_unlockItemName);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.HIRE, _loc_2.upgrade.newItemName);
            super.askForCrew(GATE_NAME);
            return;
        }//end

         protected boolean  buyCrew (double param1 )
        {
            MapResource _loc_4 =null ;
            boolean _loc_2 =false ;
            _loc_3 = Global.gameSettings().getItemByName(m_unlockItemName);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.FILL_ONE, _loc_3.upgrade.newItemName);
            if (Global.player.canBuyCash(param1, true))
            {
                _loc_2 = true;
                GameTransactionManager.addTransaction(new TBuyCrew(m_targetObjectId, 0, param1, GATE_NAME));
                if (checkForKeys() == true)
                {
                    ((MunicipalUpgradesDialog)m_dialog).activateFinishButton();
                }
                _loc_4 =(MapResource) Global.world.getObjectById(m_targetObjectId);
                if (_loc_4 != null)
                {
                    _loc_4.refreshArrow();
                }
            }
            return _loc_2;
        }//end

         protected boolean  buyRemainingCrew (Array param1 )
        {
            int _loc_7 =0;
            MapResource _loc_8 =null ;
            boolean _loc_2 =false ;
            _loc_3 = getCrewCost(m_unlockItemName,GATE_NAME);
            _loc_4 = param1.length-CrewGate.getFilledPositions(param1);
            _loc_5 = _loc_3*_loc_4 ;
            if (_loc_4 / param1.length >= RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100)
            {
                _loc_5 = Math.floor(_loc_5 * (100 - RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 20)) / 100);
            }
            _loc_6 = Global.gameSettings().getItemByName(m_unlockItemName);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.FILL_ALL, _loc_6.upgrade.newItemName);
            if (Global.player.canBuyCash(_loc_5, true))
            {
                Global.player.cash = Global.player.cash - _loc_5;
                _loc_2 = true;
                _loc_7 = 0;
                while (_loc_7 < _loc_4)
                {

                    Global.crews.addCrewById(m_targetObjectId, "-1");
                    _loc_7++;
                }
                GameTransactionManager.addTransaction(new TBuyRemainingCrew(m_targetObjectId, GATE_NAME));
                _loc_8 =(MapResource) Global.world.getObjectById(m_targetObjectId);
                if (_loc_8 != null)
                {
                    _loc_8.refreshArrow();
                }
            }
            return _loc_2;
        }//end

    }



