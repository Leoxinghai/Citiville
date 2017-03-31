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
import Display.*;
import Display.GateUI.*;
import Display.GridlistUI.*;
import Engine.Managers.*;
//import flash.events.*;

    public class UpgradeInventoryGate extends InventoryGate
    {
        public static  String GATE_NAME ="pre_upgrade";

        public  UpgradeInventoryGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  loadFromXML (XML param1 )
        {
            super.loadFromXML(param1);
            return;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            m_customViralType = param1;
            m_dialog = null;
            Array _loc_4 =new Array();
            _loc_5 =Global.player.getFakeFriend ();
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_6 = m_keys.get(i0);

                _loc_4.push({item:Global.gameSettings().getItemByName(_loc_6), amountNeeded:m_keys.get(_loc_6), buyCallback:buyKey, askCallback:askForKey, gridCellType:GridCellType.ITEM});
            }
            if (showSendGift)
            {
                _loc_4.push({item:Global.gameSettings().getItemByName(SEND_GIFT_ITEM), amountNeeded:0, gridCellType:GridCellType.SEND_GIFT});
            }
            m_cellFactory = new KeyCellItemFactory(_loc_4);
            _loc_7 =Global.gameSettings().getItemByName(m_unlockItemName );
            _loc_8 =Global.gameSettings().getItemByName(m_unlockItemName );
            _loc_9 =Global.gameSettings().getItemByName(m_unlockItemName ).type ;
            if (m_dialog == null)
            {
                if (_loc_9.toLowerCase() == "municipal")
                {
                    m_dialog = UI.displayMunInventoryUpgradesDialog(m_unlockItemName, _loc_4, checkForKeys, this.gateFinishHandler, this.buyAllInventory, m_targetObjectId, this);
                }
            }
            StatsManager.milestone("see_gate_for_" + m_unlockItemName);
            return;
        }//end

        protected boolean  buyAllInventory (Array param1 )
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_2 =0;
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_3 = m_keys.get(i0);

                _loc_4 = Global.gameSettings().getItemByName(_loc_3);
                _loc_5 = Global.player.inventory.getItemCount(_loc_4);
                _loc_6 = m_keys.get(_loc_3);
                _loc_7 = Math.max(0, _loc_6 - _loc_5);
                _loc_8 = _loc_7 * _loc_4.cash;
                _loc_2 = _loc_2 + _loc_8;
            }
            if (Global.player.canBuyCash(_loc_2))
            {
                buyAll();
                ((KeyCellItemFactory)m_cellFactory).updateList();
                return true;
            }
            return false;
        }//end

        protected void  gateFinishHandler (Event event =null )
        {
            if (checkForKeys())
            {
                unlockGate();
                m_dialog.close();
            }
            else
            {
                m_dialog.close();
            }
            return;
        }//end

    }


