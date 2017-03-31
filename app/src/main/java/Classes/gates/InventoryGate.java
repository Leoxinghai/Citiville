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
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.GateUI.*;
import Display.GridlistUI.*;
import Display.UpgradesUI.*;
import Engine.Managers.*;
import Init.*;
import Modules.matchmaking.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class InventoryGate extends AbstractGate
    {
        private AskFriendsDialog m_askDialog ;
        protected String m_customViralType ;
        protected boolean m_isModal =true ;
        protected boolean m_isQueued =true ;
        protected Object m_extraDialogParams ;
        public boolean showSendGift =true ;

        public  InventoryGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  loadFromXML (XML param1 )
        {
            if (param1.@showSendGift == "false")
            {
                this.showSendGift = false;
            }
            super.loadFromXML(param1);
            return;
        }//end

         public boolean  checkForKeys ()
        {
            String _loc_1 =null ;
            if (Global.player.getSeenFlag(getSeenFlag(m_unlockItemName, m_name)))
            {
                return true;
            }
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_1 = m_keys.get(i0);

                if (Global.player.inventory.getItemCountByName(_loc_1) < m_keys.get(_loc_1))
                {
                    return false;
                }
            }
            return true;
        }//end

        public boolean  checkWithAdditionalKey (Item param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_2 = m_keys.get(i0);

                _loc_3 = _loc_2 == param1.name ? (1) : (0);
                if (Global.player.inventory.getItemCountByName(_loc_2) + _loc_3 < m_keys.get(_loc_2))
                {
                    return false;
                }
            }
            return true;
        }//end

         public int  keyProgress (String param1 )
        {
            return Math.min(Global.player.inventory.getItemCountByName(param1), m_keys.get(param1));
        }//end

         public String  getRequirementString ()
        {
            String _loc_2 =null ;
            String _loc_1 ="";
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_2 = m_keys.get(i0);

                if (m_keys.get(_loc_2) == 0)
                {
                    continue;
                }
                _loc_1 = _loc_1 + (" " + ZLoc.t("Dialogs", "GateKey", {qty:m_keys.get(_loc_2), item:Global.gameSettings().getItemByName(_loc_2).localizedName}));
            }
            _loc_1 = _loc_1.slice(0, (_loc_1.length - 1));
            return ZLoc.t("Dialogs", "GateRequirementString", {items:_loc_1});
        }//end

        public Object  getExtraDialogParams ()
        {
            return this.m_extraDialogParams;
        }//end

        public boolean  buyKey (Item param1 )
        {
            GateDialog _loc_2 =null ;
            MunInventoryUpgradesDialog _loc_3 =null ;
            if (Global.player.canBuyCash(param1.GetItemSalePrice(), true))
            {
                GameTransactionManager.addTransaction(new TBuyItem(param1.name, 1, m_unlockItemName));
                if (this.checkForKeys())
                {
                    _loc_2 =(GateDialog) m_dialog;
                    if (_loc_2)
                    {
                        _loc_2.activateFinishButton();
                    }
                    _loc_3 =(MunInventoryUpgradesDialog) m_dialog;
                    if (_loc_3)
                    {
                        _loc_3.activateFinishButton();
                    }
                }
                updateItemArrowState();
                return true;
            }
            else if (m_dialog)
            {
                m_dialog.close();
                m_dialog = null;
            }
            return false;
        }//end

        public void  setDisplayProperties (boolean param1 ,boolean param2 )
        {
            this.m_isModal = param1;
            this.m_isQueued = param2;
            return;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            String _loc_5 =null ;
            Array _loc_6 =null ;
            boolean _loc_12 =false ;
            String _loc_13 =null ;
            Class _loc_14 =null ;
            this.m_customViralType = param1;
            m_dialog = null;
            _loc_15 = param3|| {};
            param3 = param3 || {};
            this.m_extraDialogParams = _loc_15;
            Array _loc_4 =new Array();
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_5 = m_keys.get(i0);

                _loc_4.push({item:Global.gameSettings().getItemByName(_loc_5), amountNeeded:m_keys.get(_loc_5), gridCellType:GridCellType.ITEM, buyCallback:this.buyKey, askCallback:this.askForKey});
            }
            _loc_6 = MatchmakingManager.instance.getBuildingBuddies();
            if (_loc_6.length())
            {
                _loc_12 = MatchmakingManager.instance.askForBuildableEnabled();
                _loc_4.push({item:Global.gameSettings().getItemByName(ASK_BUILDING_BUDDY), amountNeeded:0, gridCellType:GridCellType.ASK_BUILDING_BUDDY, canAskBuddy:_loc_12, askBuddyCallback:this.askBuildingBuddies});
            }
            else if (this.showSendGift)
            {
                _loc_4.push({item:Global.gameSettings().getItemByName(SEND_GIFT_ITEM), amountNeeded:0, gridCellType:GridCellType.SEND_GIFT});
            }
            m_cellFactory = new KeyCellItemFactory(_loc_4);
            _loc_7 = Global.gameSettings().getItemByName(m_unlockItemName);
            _loc_8 = param2;
            if (param2 == null)
            {
                _loc_8 = ZLoc.t("Dialogs", "GateDialogTitle", {item:_loc_7.localizedName});
            }
            Function _loc_9 =null ;
            if (m_directUnlock)
            {
                _loc_9 = unlockGate;
            }
            if (this.m_extraDialogParams.get("dialogClass"))
            {
                _loc_13 = this.m_extraDialogParams.get("dialogClass");
                _loc_14 =(Class) getDefinitionByName(_loc_13);
                if (_loc_14)
                {
                    m_dialog = new _loc_14(_loc_4, m_cellFactory, _loc_8, this.getRequirementString(), this.checkForKeys, _loc_9, buyAll, this.m_isModal, this, this.m_extraDialogParams);
                }
            }
            if (!m_dialog)
            {
                m_dialog = new GateDialog(_loc_4, m_cellFactory, _loc_8, this.getRequirementString(), this.checkForKeys, _loc_9, buyAll, 3, 562, 205, this.m_isModal, this, this.m_extraDialogParams);
                ((GateDialog)m_dialog).setGateType(m_name);
            }
            _loc_10 = this.m_extraDialogParams.put("checkDuplicates", "false"? (false) : (true));
            String _loc_11 ="";
            if (_loc_10)
            {
                _loc_11 = "inventoryGate_" + m_unlockItemName;
            }
            StatsManager.milestone("see_gate_for_" + m_unlockItemName);
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "ask_for_parts", "view", m_unlockItemName);
            UI.displayPopup(m_dialog, this.m_isQueued, _loc_11, _loc_10);
            return;
        }//end

         protected void  takeKeys ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_1 = m_keys.get(i0);

                Global.player.inventory.removeItems(_loc_1, m_keys.get(_loc_1));
            }
            return;
        }//end

        protected void  askForKey (Item param1 ,DisplayObject param2 )
        {
            boolean _loc_3 =false ;
            String _loc_5 =null ;
            GenericDialog _loc_6 =null ;
            if (m_dialog)
            {
                m_dialog.close();
                m_dialog = null;
            }
            m_virals.get(_loc_4 = param1.name) ;
            if (m_virals.get(param1.name) == AskFriendsDialog.REQUEST_BUILDABLE || _loc_4 == "")
            {
                if (this.m_customViralType)
                {
                    _loc_3 = Global.world.viralMgr.sendCustomMaterialRequest(this.m_customViralType, Global.player, Global.gameSettings().getItemByName(m_unlockItemName).localizedName, param1);
                }
                else
                {
                    _loc_3 = Global.world.viralMgr.sendMunicipalMaterialRequest(Global.player, Global.gameSettings().getItemByName(m_unlockItemName).localizedName, param1);
                }
            }
            else if (_loc_4 == AskFriendsDialog.FEED_BUILDABLE)
            {
                if (this.m_customViralType)
                {
                    _loc_3 = Global.world.viralMgr.sendCustomMaterialFeed(this.m_customViralType, Global.player, Global.gameSettings().getItemByName(m_unlockItemName).localizedName, param1);
                }
                else
                {
                    _loc_3 = Global.world.viralMgr.sendMunicipalMaterialFeed(Global.player, Global.gameSettings().getItemByName(m_unlockItemName).localizedName, param1);
                }
            }
            if (!_loc_3)
            {
                _loc_5 = ZLoc.t("Dialogs", "MunicipalThrottlingMessage");
                _loc_6 = new GenericDialog(_loc_5);
                UI.displayPopup(_loc_6, false, "alreadyAsked");
            }
            return;
        }//end

        private void  askBuildingBuddies (Event event =null )
        {
            Item _loc_4 =null ;
            String _loc_5 =null ;
            Item _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            if (m_dialog)
            {
                m_dialog.close();
                m_dialog = null;
            }
            _loc_2 = Global.gameSettings;
            _loc_3 = Global.player.inventory;
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_5 = m_keys.get(i0);

                _loc_6 = _loc_2.getItemByName(_loc_5);
                _loc_7 = m_keys.get(_loc_5);
                _loc_8 = _loc_3.getItemCountByName(_loc_5);
                if (_loc_7 > _loc_8)
                {
                    _loc_4 = _loc_6;
                    break;
                }
            }
            if (_loc_4)
            {
                MatchmakingManager.instance.askForBuildable(_loc_4.name, this.m_customViralType);
            }
            return;
        }//end

    }



