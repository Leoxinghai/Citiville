package Display.FactoryUI;

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
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class SuppliesDialog extends GenericDialog
    {
        protected Array m_items ;
        protected String m_contractItemType ;
        public static Dictionary assetDict ;

        public  SuppliesDialog (String param1 ,String param2 ="",String param3 ="contract",int param4 =0,Function param5 =null ,boolean param6 =true ,int param7 =0)
        {
            super(param1, param2, param4, param5, param2, "", true);
            this.m_contractItemType = param3;
            return;
        }//end

         protected void  loadAssets ()
        {
            _loc_1 = Global.getAssetURL("assets/dialogs/SuppliesAssets.swf");
            LoadingManager.load(_loc_1, this.onAssetsLoaded);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            _loc_2 = (LoaderInfo)event.target
            SuppliesDialog.assetDict = new Dictionary(true);
            _loc_3 = _loc_2.content;
            SuppliesDialog.assetDict.put("close_button",  _loc_3.close_button);
            SuppliesDialog.assetDict.put("left_green_arrow",  _loc_3.left_green_arrow);
            SuppliesDialog.assetDict.put("radial_button_selected",  _loc_3.radial_button_selected);
            SuppliesDialog.assetDict.put("radial_button_unselected",  _loc_3.radial_button_unselected);
            SuppliesDialog.assetDict.put("right_green_arrow",  _loc_3.right_green_arrow);
            SuppliesDialog.assetDict.put("supplies_background",  _loc_3.supplies_background);
            SuppliesDialog.assetDict.put("supplies_card",  _loc_3.supplies_card);
            SuppliesDialog.assetDict.put("whats_this_button",  _loc_3.whats_this_button);
            SuppliesDialog.assetDict.put("star",  _loc_3.star);
            SuppliesDialog.assetDict.put("icon_bg",  _loc_3.icon_bg);
            SuppliesDialog.assetDict.put("coin_small",  _loc_3.coin_small);
            SuppliesDialog.assetDict.put("coin_big",  _loc_3.coin_big);
            SuppliesDialog.assetDict.put("truck",  _loc_3.truck);
            SuppliesDialog.assetDict.put("forklift",  _loc_3.forklift);
            SuppliesDialog.assetDict.put("supplies_lockedcard",  _loc_3.supplies_lockedcard);
            SuppliesDialog.assetDict.put("slot_locked",  _loc_3.slot_locked);
            SuppliesDialog.assetDict.put("slot_cash",  _loc_3.slot_cash);
            SuppliesDialog.assetDict.put("lock_bg",  _loc_3.lock_bg);
            SuppliesDialog.assetDict.put("cash",  _loc_3.cash);
            this.m_items = Global.gameSettings().getBuyableItems(this.m_contractItemType);
            m_jpanel = new SuppliesDialogView(SuppliesDialog.assetDict, this.m_items, m_title, m_callback);
            m_jpanel.addEventListener(DataItemEvent.MARKET_BUY, this.onContractClick, false, 0, true);
            m_jpanel.addEventListener(Event.CLOSE, this.onCloseClick, false, 0, true);
            finalizeAndShow();
            return;
        }//end

        private void  onCloseClick (Event event =null )
        {
            this.close();
            m_jpanel.removeEventListener(DataItemEvent.MARKET_BUY, this.onContractClick);
            m_jpanel.removeEventListener(Event.CLOSE, this.onCloseClick);
            return;
        }//end

        private void  onContractClick (DataItemEvent event )
        {
            SoundManager.chooseAndPlaySound(.get("click1", "click2", "click3"));
            _loc_2 = event.item;
            _loc_3 = _loc_2? (_loc_2) : (null);
            if (this.canBuy(_loc_3))
            {
                this.onBuy(_loc_3);
            }
            return;
        }//end

        public boolean  canBuy (Item param1 )
        {
            return Global.player.checkGate(param1.unlock, param1.name);
        }//end

        public void  onBuy (Item param1 )
        {
            if (!param1)
            {
                return;
            }
            boolean _loc_2 =false ;
            if (param1.cash > 0)
            {
                _loc_2 = Global.player.canBuyCash(param1.cash, false);
            }
            else
            {
                _loc_2 = Global.player.canBuy(param1.cost, true);
            }
            if (!_loc_2)
            {
                if (param1.cash > 0)
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                }
                else if (param1.cost > 0)
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_COINS);
                }
                return;
            }
            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.CONTRACT, param1.name, true));
            dispatchEvent(new Event(Event.CLOSE, true));
            this.onCloseClick();
            return;
        }//end

    }




