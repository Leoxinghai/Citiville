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

    public class TBuyItem extends Transaction
    {
        private String m_itemName ;
        private int m_itemAmount ;
        private boolean m_success ;
        private String m_unlockItemName ;
        private boolean m_showCongrats ;

        public  TBuyItem (String param1 ,int param2 ,String param3 =null ,boolean param4 =false ,boolean param5 =true )
        {
            Item _loc_6 =null ;
            GenericDialog _loc_7 =null ;
            this.m_itemName = param1;
            this.m_itemAmount = param2;
            this.m_unlockItemName = param3;
            this.m_showCongrats = param4;
            this.m_success = Global.player.inventory.addItems(this.m_itemName, this.m_itemAmount);
            if (this.m_success)
            {
                _loc_6 = Global.gameSettings().getItemByName(this.m_itemName);
                _loc_6.m_isFranchise = param5;
                if (_loc_6.cost > 0)
                {
                    Global.player.gold = Global.player.gold - _loc_6.GetItemSalePrice() * this.m_itemAmount;
                }
                else if (_loc_6.cash > 0)
                {
                    Global.player.cash = Global.player.cash - _loc_6.GetItemSalePrice() * this.m_itemAmount;
                }
                else
                {
                    GlobalEngine.msg(this.m_itemName + " costs nothing, make sure this is intentional behavior.");
                }
                if (this.m_showCongrats)
                {
                    _loc_7 = new GenericDialog(ZLoc.t("Dialogs", "visit_buy_congrats_body"), "congratulations", GenericDialogView.TYPE_CUSTOM_OK, null, "congratulations", "assets/dialogs/payroll_sam_happyPigeon.png", true, 0, "", null, ZLoc.t("Dialogs", "Great"));
                    UI.displayPopup(_loc_7);
                }
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
                signedCall("UserService.buyConsumable", this.m_itemName, this.m_itemAmount, this.m_unlockItemName);
            }
            return;
        }//end

    }



