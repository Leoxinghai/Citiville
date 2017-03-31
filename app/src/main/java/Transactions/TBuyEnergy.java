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
import Engine.Managers.*;
import Engine.Transactions.*;

    public class TBuyEnergy extends Transaction
    {
        private String m_itemName ;
        private boolean m_doEnergyUpgrade ;
        private boolean m_isPurchase ;

        public  TBuyEnergy (String param1 ,boolean param2 )
        {
            boolean _loc_4 =false ;
            this.m_itemName = param1;
            this.m_doEnergyUpgrade = true;
            this.m_isPurchase = true;
            _loc_3 =Global.gameSettings().getItemByName(this.m_itemName );
            if (Global.player.checkForEnergyOverflow(Global.player.energy + _loc_3.energyRewards, true))
            {
                Global.player.showEnergyCanNotBeAddedDialog();
                this.m_doEnergyUpgrade = false;
                return;
            }
            if (_loc_3.cost > 0)
            {
                Global.player.gold = Global.player.gold - _loc_3.GetItemSalePrice();
            }
            else if (_loc_3.cash > 0)
            {
                Global.player.cash = Global.player.cash - _loc_3.GetItemSalePrice();
            }
            else
            {
                GlobalEngine.msg(this.m_itemName + " cost nothing, make sure this is intentional behavior.");
            }
            if (param2)
            {
                _loc_4 = Global.player.inventory.removeItems(this.m_itemName, 1);
                this.m_isPurchase = false;
                if (!_loc_4)
                {
                    ErrorManager.addError("Item " + _loc_3.localizedName + " not in inventory!");
                }
            }
            Global.player.updateEnergy(_loc_3.energyRewards, new Array("energy", param2 ? ("earnings") : ("purchases"), param1, ""), this.m_isPurchase);
            return;
        }//end

         public void  perform ()
        {
            if (this.m_doEnergyUpgrade)
            {
                signedCall("UserService.buyEnergy", this.m_itemName);
            }
            return;
        }//end

    }



