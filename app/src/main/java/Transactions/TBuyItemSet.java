package Transactions;

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

    public class TBuyItemSet extends TFarmTransaction
    {
        private Object m_itemSet ;
        private Array m_itemArr ;
        private boolean m_success ;
        private String m_unlockItemName ;
        private int m_itemSetCashCost =0;
        private String m_gateName ="";

        public  TBuyItemSet (Object param1 ,int param2 ,String param3 ,String param4 =null )
        {
            Item _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            String _loc_9 =null ;
            this.m_itemArr = new Array();
            if (!Global.player.canBuyCash(param2))
            {
                this.m_success = false;
                return;
            }
            this.m_gateName = param3;
            this.m_success = true;
            this.m_itemSet = param1;
            this.m_itemSetCashCost = param2;
            this.m_unlockItemName = param4;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_9 = param1.get(i0);

                this.m_itemArr.push(_loc_9);
                _loc_5 = Global.gameSettings().getItemByName(_loc_9);
                _loc_6 = Global.player.inventory.getItemCount(_loc_5);
                _loc_7 = param1.get(_loc_9);
                _loc_8 = Math.max(0, _loc_7 - _loc_6);
                if (_loc_8 > 0)
                {
                    this.m_success = Global.player.inventory.canAddItems(_loc_9, _loc_8) && this.m_success;
                }
            }
            if (this.m_success)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_9 = param1.get(i0);

                    _loc_5 = Global.gameSettings().getItemByName(_loc_9);
                    _loc_6 = Global.player.inventory.getItemCount(_loc_5);
                    _loc_7 = param1.get(_loc_9);
                    _loc_8 = Math.max(0, _loc_7 - _loc_6);
                    Global.player.inventory.addItems(_loc_9, _loc_8);
                }
                Global.player.cash = Global.player.cash - param2;
            }
            else
            {
                UI.displayMessage(ZLoc.t("Dialogs", "InventoryFull"));
            }
            return;
        }//end

         public void  perform ()
        {
            if (this.m_success)
            {
                signedCall("UserService.buyDiscountConsumableSet", this.m_itemArr, this.m_itemSetCashCost, this.m_gateName, this.m_unlockItemName);
            }
            return;
        }//end

    }



