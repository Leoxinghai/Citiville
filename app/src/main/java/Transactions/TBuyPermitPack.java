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
import Display.MarketUI.*;
import Engine.Transactions.*;

    public class TBuyPermitPack extends Transaction
    {
        private String m_itemName ;
        private Item m_itemObject ;
        private Item m_permitItem ;

        public  TBuyPermitPack (String param1 )
        {
            this.m_itemName = param1;
            this.m_itemObject = Global.gameSettings().getItemByName(this.m_itemName);
            Global.player.cash = Global.player.cash - this.m_itemObject.GetItemSalePrice();
            _loc_2 = this.m_itemObject.itemRewards.length ;
            param1 = this.m_itemObject.itemRewards.get(0);
            this.m_permitItem = Global.gameSettings().getItemByName(param1);
            Global.player.inventory.addItems(this.m_permitItem.name, _loc_2, false, false);
            MarketCell.reLockCells(this.m_itemObject.name);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 != null)
            {
                MarketCell.reLockCells(this.m_itemObject.name);
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.buyPermitPack", this.m_itemName);
            return;
        }//end

    }



