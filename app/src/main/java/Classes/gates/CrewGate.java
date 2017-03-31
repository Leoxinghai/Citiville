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
import Display.GridlistUI.*;
import Engine.Managers.*;
import Modules.crew.*;
import Modules.matchmaking.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;

//import flash.events.*;

    public class CrewGate extends AbstractGate
    {
public static  String REQUIRED_CREW ="required_crew";

        public  CrewGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public boolean  checkForKeys ()
        {
            _loc_1 = super.checkForKeys();
            _loc_2 = Global.player.inventory.getItemCountByName("crew_bonus");
            _loc_3 = Global.crews.getCrewById(m_targetObjectId);
            _loc_4 = m_keys.get(REQUIRED_CREW);
            if (_loc_3)
            {
                _loc_1 = _loc_3.count + _loc_2 >= _loc_4;
            }
            else if (_loc_2 >= _loc_4)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

         public int  amountNeeded ()
        {
            _loc_1 = Global.crews.getCrewById(m_targetObjectId);
            _loc_2 = m_keys.get(REQUIRED_CREW);
            _loc_3 = Global.player.inventory.getItemCountByName("crew_bonus");
            _loc_4 = _loc_1&& _loc_1.list ? (_loc_1.list.length()) : (0);
            return _loc_2 - _loc_4 - _loc_3;
        }//end

         protected void  takeKeys ()
        {
            GameObject _loc_5 =null ;
            _loc_1 =Global.player.inventory.getItemCountByName("crew_bonus");
            _loc_2 =Global.crews.getCrewById(m_targetObjectId );
            _loc_3 = m_keys.get(REQUIRED_CREW);
            int _loc_4 =0;
            if (_loc_2)
            {
                _loc_4 = _loc_3 - _loc_2.count;
            }
            else if (_loc_1 >= _loc_3)
            {
                _loc_4 = _loc_3;
            }
            Global.player.inventory.removeItems("crew_bonus", _loc_4);
            while (_loc_4 > 0)
            {

                Global.crews.addCrewById(m_targetObjectId, "-1");
                _loc_5 =(GameObject) Global.world.getObjectById(m_targetObjectId);
                if (_loc_5)
                {
                    _loc_5.onAddCrewMember();
                }
                _loc_4 = _loc_4 - 1;
            }
            return;
        }//end

         public String  getRequirementString ()
        {
            return null;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            String _loc_13 =null ;
            Object _loc_14 =null ;
            Player _loc_15 =null ;
            Player _loc_16 =null ;
            Array _loc_17 =null ;
            int _loc_18 =0;
            int _loc_19 =0;
            int _loc_20 =0;
            int _loc_21 =0;
            int _loc_22 =0;
            boolean _loc_23 =false ;
            Item _loc_24 =null ;
            m_dialog = null;
            Array _loc_4 =new Array();
            _loc_5 = Global.crews.getCrewById(m_targetObjectId);
            _loc_6 = this.getCrewNames();
            _loc_7 = Global.player.inventory.getItemCountByName("crew_bonus");
            _loc_8 = m_keys.get(REQUIRED_CREW);
            int _loc_9 =0;
            while (_loc_9 < _loc_8)
            {

                _loc_13 = _loc_5 && _loc_5.count > _loc_9 ? (_loc_5.list.get(_loc_9)) : (null);
                _loc_13 = this.normalizeUid(_loc_13);
                _loc_14 = {};
                _loc_15 = _loc_13 ? (Global.player.findFriendById(_loc_13)) : (null);
                _loc_16 = Global.player.findFriendById("-1");
                if (_loc_13)
                {
                    _loc_14.url = _loc_15 ? (_loc_15.snUser.picture) : (_loc_16.snUser.picture);
                    _loc_14.count = 1;
                    _loc_14.amountNeeded = 1;
                    _loc_14.footerText = _loc_15 ? (_loc_15.firstName) : (_loc_16.firstName);
                    _loc_14.gridCellType = GridCellType.HIRED_CREW;
                }
                else if (_loc_7 > 0)
                {
                    _loc_14.url = _loc_16.snUser.picture;
                    _loc_14.count = 1;
                    _loc_14.amountNeeded = 1;
                    _loc_14.footerText = ZLoc.t("Items", "crew_bonus_friendlyName");
                    _loc_14.gridCellType = GridCellType.BONUS_CREW;
                    _loc_7 = _loc_7 - 1;
                }
                else
                {
                    _loc_14.url = null;
                    _loc_14.displayObjectClass = EmbeddedArt.hud_no_profile_pic;
                    _loc_14.cost = getCrewCost(m_unlockItemName);
                    _loc_14.count = 0;
                    _loc_14.amountNeeded = 1;
                    _loc_14.buyCallback = this.buyCrew;
                    _loc_14.footerText = ZLoc.t("Dialogs", "EmptyCrewCell");
                    _loc_14.gridCellType = GridCellType.ADD_CREW;
                }
                _loc_14.picUrl = _loc_14.url;
                _loc_14.friendName = _loc_14.footerText;
                _loc_14.name = _loc_6.get(_loc_9);
                _loc_14.backgroundClass = EmbeddedArt.crewFriendBackground;
                _loc_4.push(_loc_14);
                _loc_9++;
            }
            m_cellFactory = new KeyCellCrewFactory(_loc_4);
            _loc_10 = Global.gameSettings().getItemByName(m_unlockItemName);
            _loc_11 = ZLoc.t("Dialogs","CrewGateDialogTitle",{item_loc_10.localizedName});
            if (_loc_10.name == "warehouse_deluxe")
            {
                m_dialog = new CrewGateDialog_2(this.getInstructions(), _loc_4, m_cellFactory, _loc_11, this.getRequirementString(), this.checkForKeys, this.gateFinishHandler, 4, 155, true, m_unlockItemName, this.buyRemainingCrew);
            }
            else
            {
                _loc_17 = MatchmakingManager.instance.getBuildingBuddies();
                if (_loc_17.length())
                {
                    _loc_23 = MatchmakingManager.instance.askForCrewEnabled();
                    _loc_24 = Global.gameSettings().getItemByName(ASK_BUILDING_BUDDY);
                    _loc_4.push({url:_loc_24.icon, item:_loc_24, amountNeeded:0, gridCellType:GridCellType.ASK_BUILDING_BUDDY, canAskBuddy:_loc_23, backgroundClass:EmbeddedArt.crewFriendBackground, askBuddyCallback:this.askBuildingBuddies});
                }
                _loc_18 = _loc_4.length;
                _loc_19 = _loc_18 % 2;
                _loc_20 = _loc_18 % 3;
                _loc_21 = _loc_18 < 5 ? (_loc_18) : (!_loc_19 ? (Math.ceil(_loc_18 / 2)) : (!_loc_20 ? (Math.ceil(_loc_18 / 3)) : (_loc_20 <= _loc_19 ? (Math.ceil(_loc_18 / 2)) : (Math.ceil(_loc_18 / 3)))));
                _loc_21 = Math.min(5, Math.max(3, _loc_21));
                _loc_22 = 152;
                m_dialog = new CrewGateDialog(this.getInstructions(), _loc_4, m_cellFactory, _loc_11, this.getRequirementString(), this.checkForKeys, this.gateFinishHandler, _loc_21, _loc_22, true, m_unlockItemName, this.buyRemainingCrew);
            }
            m_dialog.addEventListener("friendClicked", this.gateFinishHandler, false, 0, true);
            UI.displayPopup(m_dialog);
            _loc_12 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS) == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                FrameManager.preloadTray("crew.php?mId=" + m_targetObjectId.toString(), true);
            }
            return;
        }//end

        protected Object  getCrewData (Array param1 )
        {
            Object _loc_2 =new Object ();
            _loc_3 = getCrewCost(m_unlockItemName);
            _loc_4 = CrewGate.getNeededPositions(param1);
            _loc_5 = CrewGate.getNeededPositions(param1)-CrewGate.getFilledPositions(param1);
            _loc_6 = _loc_3*_loc_5 ;
            if (_loc_5 / _loc_4 >= CrewGate.BUY_ALL_CREW_DISCOUNT_THRESHOLD)
            {
                _loc_6 = Math.floor(_loc_6 * CrewGate.BUY_ALL_DISCOUNT_RATE);
            }
            _loc_2.totalCost = _loc_6;
            _loc_2.listData = param1;
            _loc_2.positionsOpen = _loc_5;
            return _loc_2;
        }//end

        protected boolean  buyRemainingCrew (Array param1 )
        {
            String _loc_4 =null ;
            int _loc_6 =0;
            MapResource _loc_7 =null ;
            GateDialog _loc_8 =null ;
            boolean _loc_2 =false ;
            _loc_3 = this.getCrewData(param1 );
            _loc_5 =Global.gameSettings().getItemByName(m_unlockItemName );
            if (Global.gameSettings().getItemByName(m_unlockItemName).upgrade)
            {
                _loc_4 = _loc_5.upgrade.newItemName;
            }
            else
            {
                _loc_4 = _loc_5.licenseName;
            }
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.FILL_ALL, _loc_4);
            if (Global.player.canBuyCash(_loc_3.totalCost, true))
            {
                Global.player.cash = Global.player.cash - _loc_3.totalCost;
                _loc_2 = true;
                _loc_6 = 0;
                while (_loc_6 < _loc_3.positionsOpen)
                {

                    Global.crews.addCrewById(m_targetObjectId, "-1");
                    _loc_6++;
                }
                GameTransactionManager.addTransaction(new TBuyRemainingCrew(m_targetObjectId, null, _loc_3.totalCost));
                _loc_7 =(MapResource) Global.world.getObjectById(m_targetObjectId);
                if (_loc_7 != null)
                {
                    _loc_7.refreshArrow();
                }
                if (this.checkForKeys() == true)
                {
                    _loc_8 =(GateDialog) m_dialog;
                    if (_loc_8 != null)
                    {
                        _loc_8.activateFinishButton();
                    }
                }
            }
            return _loc_2;
        }//end

        protected String  normalizeUid (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = param1;
            if (param1 !=null)
            {
                _loc_3 = parseFloat(param1);
                if (_loc_3 < 0)
                {
                    _loc_3 = -1;
                }
                _loc_2 = _loc_3.toString();
            }
            return _loc_2;
        }//end

        protected void  gateFinishHandler (Event event =null )
        {
            if (this.checkForKeys())
            {
                unlockGate();
                m_dialog.close();
            }
            else
            {
                this.askForCrew();
            }
            return;
        }//end

         public int  keyProgress (String param1 )
        {
            _loc_2 = Global.crews.getCrewById(m_targetObjectId);
            _loc_3 = m_keys.get(REQUIRED_CREW);
            return int(int(param1) >= _loc_2.count);
        }//end

        protected void  askForCrew (String param1)
        {
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_POPUP_MFS);
            _loc_3 = m_targetObjectId"crew.php?mId="+.toString();
            if (param1 != null && param1 != "")
            {
                _loc_3 = _loc_3 + ("&gname=" + param1);
            }
            if (_loc_2 == ExperimentDefinitions.TRAY_POPUP_MFS)
            {
                FrameManager.showTray(_loc_3);
                ((GateDialog)m_dialog).close();
            }
            else
            {
                FrameManager.navigateTo(_loc_3);
            }
            return;
        }//end

        protected boolean  buyCrew (double param1 )
        {
            boolean _loc_2 =false ;
            if (Global.player.canBuyCash(param1, true))
            {
                _loc_2 = true;
                GameTransactionManager.addTransaction(new TBuyCrew(m_targetObjectId, 0, param1));
                if (this.checkForKeys() == true)
                {
                    ((GateDialog)m_dialog).activateFinishButton();
                }
            }
            updateItemArrowState();
            return _loc_2;
        }//end

        protected String Vector  getCrewNames ().<>
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            double _loc_5 =0;
            String _loc_6 =null ;
            int _loc_7 =0;
            Vector _loc_1.<String >=new Vector<String>();
            _loc_2 = Global.gameSettings().getItemByName(m_unlockItemName);
            for(int i0 = 0; i0 < _loc_2.gatesXml.get("gate").get("key").get("member").size(); i0++)
            {
            	_loc_3 = _loc_2.gatesXml.get("gate").get("key").get("member").get(i0);

                _loc_4 = _loc_3.@name;
                _loc_5 = _loc_3.@amount;
                _loc_6 = ZLoc.t("Items", _loc_4);
                _loc_7 = 0;
                while (_loc_7 < _loc_5)
                {

                    _loc_1.push(_loc_6);
                    _loc_7++;
                }
            }
            return _loc_1;
        }//end

        protected String  getInstructions ()
        {
            XML _loc_3 =null ;
            String _loc_1 ="";
            _loc_2 = Global.gameSettings().getItemByName(m_unlockItemName);
            for(int i0 = 0; i0 < _loc_2.gatesXml.get("gate").size(); i0++)
            {
            	_loc_3 = _loc_2.gatesXml.get("gate").get(i0);

                if (_loc_3 && _loc_3.@instructions == true)
                {
                    _loc_1 = ZLoc.t("Dialogs", "crewInstructionsLine1", {item:_loc_2.localizedName});
                    _loc_1 = _loc_1 + ("\n" + ZLoc.t("Dialogs", "crewInstructionsLine2", {item:_loc_2.localizedName}));
                }
            }
            return _loc_1;
        }//end

        private void  askBuildingBuddies (Event event =null )
        {
            if (m_dialog)
            {
                m_dialog.close();
                m_dialog = null;
            }
            _loc_2 = Global.gameSettings().getItemByName(m_unlockItemName);
            MatchmakingManager.instance.askForCrew(true, null, "gate", _loc_2);
            return;
        }//end

        public static double  BUY_ALL_CREW_DISCOUNT_THRESHOLD ()
        {
            return RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100;
        }//end

        public static double  BUY_ALL_DISCOUNT_RATE ()
        {
            return (100 - RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 25)) / 100;
        }//end

        public static double  getCrewCost (String param1 ,String param2 )
        {
            XMLList gates ;
            XML key ;
            String keyName ;
            itemName = param1;
            gateName = param2;
            result = Global.gameSettings().getNumber("crewMemberCashCost");
            item = Global.gameSettings().getItemByName(itemName);
            if (gateName == null)
            {
                gates = item.gatesXml.get("gate").get("key");
            }
            else
            {
                int _loc_5 =0;
                _loc_6 = item.gatesXml.get("gate");
                XMLList _loc4 =new XMLList("");
                Object _loc_7;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);

                    with (_loc_7)
                    {
                        if (attribute("name") == gateName)
                        {
                            _loc4.put(_loc_5++,  _loc_7);


                        }
                    }
                }
                gates = _loc4.get("key");
            }


            for(int i0 = 0; i0 < gates.size(); i0++)
            {
            	key = gates.get(i0);


                keyName = key.@name;
                if (keyName == REQUIRED_CREW)
                {
                    if (key.hasOwnProperty("@cashCost"))
                    {
                        result = Number(key.@cashCost);
                        break;
                    }
                }
            }
            return result;
        }//end

        public static int  getFilledPositions (Array param1 )
        {
            Object _loc_3 =null ;
            int _loc_2 =0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                if (!isNaN(_loc_3.count))
                {
                    _loc_2 = _loc_2 + _loc_3.count;
                }
            }
            return _loc_2;
        }//end

        public static double  getNeededPositions (Array param1 )
        {
            Object _loc_3 =null ;
            int _loc_2 =0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                _loc_2 = _loc_2 + _loc_3.amountNeeded;
            }
            return _loc_2;
        }//end

    }



