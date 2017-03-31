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
import Display.DialogUI.*;
import Engine.Transactions.*;

    public class TBuyMysteryCrate extends Transaction
    {
        private String m_itemName ;
        private int m_itemAmount ;
        private boolean m_success ;
        private String m_unlockItemName ;

        public  TBuyMysteryCrate (Item param1 ,int param2 ,String param3 =null )
        {
            this.m_itemName = param1.name;
            this.m_itemAmount = param2;
            this.m_unlockItemName = param3;
            if (Global.player.inventory.spareCapacity <= 0)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "InventoryFull"));
            }
            else if (Global.player.cash - param1.cash > -1 && param1.cash != 0)
            {
                Global.player.cash = Global.player.cash - param1.GetItemSalePrice() * this.m_itemAmount;
                this.m_success = true;
            }
            else if (param1.cost > 0)
            {
                Global.player.gold = Global.player.gold - param1.GetItemSalePrice() * this.m_itemAmount;
                this.m_success = true;
            }
            return;
        }//end

         public void  perform ()
        {
            if (this.m_success)
            {
                signedCall("UserService.buyConsumable", this.m_itemName, this.m_itemAmount, this.m_unlockItemName);
            }
            return;
        }//end

         protected void  onAmfComplete (Object param1 )
        {
            Dialog _loc_2 =null ;
            Item _loc_3 =null ;
            Item _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            MysteryCrateDialog _loc_7 =null ;
            super.onAmfComplete(param1);
            if (param1 && param1.data && param1.data.itemName)
            {
                this.m_itemName = param1.data.itemName;
                Global.player.inventory.addItems(this.m_itemName, this.m_itemAmount);
                _loc_2 = UI.currentPopup;
                _loc_3 = Global.gameSettings().getItemByName(this.m_itemName);
                if (_loc_2 && _loc_2 is MysteryCrateDialog)
                {
                    MysteryCrateDialog(UI.currentPopup).showWinnings(_loc_3);
                }
                else
                {
                    _loc_4 = Global.gameSettings().getItemByName(_loc_3.name);
                    _loc_5 = ZLoc.t("Items", _loc_3.name + "_friendlyName");
                    _loc_6 = ZLoc.t("Dialogs", "Mystery_Crate_anticipation", {item:_loc_5});
                    _loc_7 = new MysteryCrateDialog(_loc_6, "mysteryCrateDialog", GenericDialogView.TYPE_CUSTOM_OK, null, "Mystery_Crate", _loc_4.iconRelative, true, GenericDialogView.ICON_POS_LEFT, "", null, ZLoc.t("Dialogs", "Mystery_Crate_ok"));
                    UI.displayPopup(_loc_7, true);
                    _loc_7.showWinnings(_loc_3);
                }
            }
            return;
        }//end

    }



