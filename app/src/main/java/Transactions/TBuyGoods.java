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
import Classes.doobers.*;
import Classes.inventory.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Engine.Transactions.*;

    public class TBuyGoods extends Transaction
    {
        private String m_itemName ;
        private boolean m_doTransaction =true ;

        public  TBuyGoods (String param1 ,boolean param2 )
        {
            StorageDialog _loc_7 =null ;
            boolean _loc_8 =false ;
            this.m_itemName = param1;
            _loc_3 =Global.gameSettings().getItemByName(this.m_itemName );
            _loc_4 =Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY );
            _loc_5 =Global.player.commodities.getCount(Commodities.GOODS_COMMODITY );
            _loc_6 =             !isNaN(_loc_3.goodsReward) ? (_loc_3.goodsReward) : (0);
            this.m_doTransaction = _loc_5 + _loc_6 <= _loc_4;
            if (!this.m_doTransaction)
            {
                _loc_7 = new StorageDialog(StorageDialog.TYPE_NEED_MORE_STORAGE);
                UI.displayPopup(_loc_7);
                return;
            }
            if (_loc_3.cost > 0)
            {
                Global.player.gold = Global.player.gold - _loc_3.cost;
            }
            else if (_loc_3.cash > 0)
            {
                Global.player.cash = Global.player.cash - _loc_3.cash;
            }
            else
            {
                GlobalEngine.msg(this.m_itemName + " cost nothing, make sure this is intentional behavior.");
            }
            if (param2)
            {
                _loc_8 = Global.player.inventory.removeItems(this.m_itemName, 1);
                if (!_loc_8)
                {
                    ErrorManager.addError("Item " + _loc_3.localizedName + " not in inventory!");
                }
            }
            Global.player.commodities.add(Doober.DOOBER_GOODS, _loc_3.goodsReward);
            return;
        }//end

         public void  perform ()
        {
            if (this.m_doTransaction)
            {
                signedCall("UserService.buyGoods", this.m_itemName);
            }
            return;
        }//end

    }



