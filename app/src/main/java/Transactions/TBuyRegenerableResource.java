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
//import flash.utils.*;

    public class TBuyRegenerableResource extends Transaction
    {
        private String m_itemName ;

        public  TBuyRegenerableResource (String param1 ,boolean param2 )
        {
            String _loc_5 =null ;
            boolean _loc_6 =false ;
            this.m_itemName = param1;
            _loc_3 =Global.gameSettings().getItemByName(this.m_itemName );
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
                _loc_6 = Global.player.inventory.removeItems(this.m_itemName, 1);
                if (!_loc_6)
                {
                    ErrorManager.addError("Item " + _loc_3.localizedName + " not in inventory!");
                }
            }
            _loc_4 = _loc_3.regenerableRewards ;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                Global.player.updateRegenerableResource(_loc_5, _loc_4.get(_loc_5));
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.buyRegenerableResource", this.m_itemName);
            return;
        }//end

    }



