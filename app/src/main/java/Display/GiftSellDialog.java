package Display;

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
import Classes.util.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class GiftSellDialog extends SlotDialog
    {
        private  int NUM_SLOTS =6;
        private Object m_usedTypes =null ;
        protected Array m_buttons ;
        private GenericButton m_sellAll ;
        private GenericButton m_sendGifts ;
        private double m_totalGiftPrices ;
        private Object m_itemObject ;

        public  GiftSellDialog ()
        {
            this.m_buttons = new Array();
            m_dialogAsset = "assets/dialogs/FV_GiftsSell.swf";
            return;
        }//end

         protected int  getNumSlots ()
        {
            return this.NUM_SLOTS;
        }//end

         protected void  onLoadComplete ()
        {
            m_window =(MovieClip) m_loader.content;
            this.m_itemObject = new Object();
            m_window.gifts_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCloseClick);
            m_window.gifts_mc.arrowLt_bt.addEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.gifts_mc.arrowRt_bt.addEventListener(MouseEvent.CLICK, onRtArrowClick);
            this.m_sellAll = new GenericButton(m_window.gifts_mc.sellAll_bt, this.onSellAllClick);
            this.m_sellAll.text = ZLoc.t("Dialogs", "SellAll");
            this.m_sendGifts = new GenericButton(m_window.gifts_mc.sendGift_bt, this.onSendGiftsClick);
            this.m_sendGifts.text = ZLoc.t("Dialogs", "SendGifts");
            m_tiles.push(m_window.gifts_mc.giftPanel1, m_window.gifts_mc.giftPanel2, m_window.gifts_mc.giftPanel3, m_window.gifts_mc.giftPanel4, m_window.gifts_mc.giftPanel5, m_window.gifts_mc.giftPanel6);
            removeTiles();
            this.pushNewData();
            int _loc_1 =0;
            while (_loc_1 < this.NUM_SLOTS && _loc_1 < m_data.length())
            {

                this.addComponent(_loc_1);
                _loc_1++;
            }
            addChild(m_window);
            return;
        }//end

        private void  pushNewData ()
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            Item _loc_5 =null ;
            m_data = new Array();
            m_tileSet = 0;
            this.m_totalGiftPrices = 0;
            this.m_usedTypes = new Object();
            _loc_1 =Global.player.getGifts ();
            if (_loc_1)
            {
                _loc_2 = 0;
                while (_loc_2 < _loc_1.length())
                {

                    _loc_3 = new Object();
                    _loc_4 = _loc_1.get(_loc_2);
                    if (_loc_4.item)
                    {
                        _loc_5 = _loc_4.item;
                        this.m_totalGiftPrices = this.m_totalGiftPrices + _loc_5.sellPrice * _loc_4.num;
                        if (this.m_usedTypes.get(_loc_4.item.name) == null)
                        {
                            this.m_usedTypes.get(_loc_4.item.name) = _loc_4.num;
                            _loc_3.name = _loc_5.name;
                            _loc_3.itemName = _loc_5.name;
                            _loc_3.className = _loc_5.className;
                            _loc_3.rank = 0;
                            _loc_3.type = _loc_5.type;
                            _loc_3.localizedName = _loc_5.localizedName;
                            _loc_3.cost = _loc_5.cost;
                            if (_loc_4.senderArray && _loc_4.senderArray.get(0))
                            {
                                _loc_3.giftSenderId = _loc_4.senderArray.get(0);
                            }
                            else
                            {
                                _loc_3.giftSenderId = 0;
                            }
                            _loc_3.compId = _loc_2;
                            if (_loc_5.coinYield)
                            {
                                _loc_3.coinYield = _loc_5.coinYield;
                            }
                            if (_loc_5.growTime)
                            {
                                _loc_3.harvestTime = _loc_5.growTime;
                            }
                            if (_loc_5.sizeX)
                            {
                                _loc_3.sizeX = _loc_5.sizeX;
                            }
                            if (_loc_5.sizeY)
                            {
                                _loc_3.sizeX = _loc_5.sizeY;
                            }
                            m_data.push(_loc_3);
                        }
                    }
                    _loc_2++;
                }
            }
            m_data.sort(this.getRankComparison);
            this.fixArrows();
            return;
        }//end

        private int  getRankComparison (Object param1 ,Object param2 )
        {
            int _loc_3 =0;
            if (param1.hasOwnProperty("rank") && param2.hasOwnProperty("rank") && param1.rank != param2.rank)
            {
                if (param2.rank > param1.rank)
                {
                    _loc_3 = 1;
                }
                else
                {
                    _loc_3 = -1;
                }
            }
            return _loc_3;
        }//end

         protected void  fixArrows ()
        {
            m_window.gifts_mc.arrowLt_bt.visible = m_tileSet > 0 ? (true) : (false);
            m_window.gifts_mc.arrowRt_bt.visible = (m_tileSet + 1) * this.getNumSlots() < m_data.length ? (true) : (false);
            return;
        }//end

         protected void  addComponent (int param1 ,int param2)
        {
            MovieClip mc ;
            double sellBackRatio ;
            String url ;
            Loader icon ;
            String tempName ;
            Array senderName ;
            compId = param1;
            index = param2;
            if (m_tiles && m_data.get(index + compId) != null)
            {
                mc =(MovieClip) m_tiles.get(compId);
                m_tiles.get(compId).visible = true;
                m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                if (m_data.get(index + compId).giftSenderId && m_data.get(index + compId).giftSenderId != -1)
                {
                    tempName = Global.player.getFriendName(m_data.get(index + compId).giftSenderId);
                    if (tempName)
                    {
                        senderName = tempName.split(" ");
                        if (senderName.get(0))
                        {
                            m_tiles.get(compId).friendName_tf.text = senderName.get(0);
                        }
                        else
                        {
                            m_tiles.get(compId).friendName_tf.text = tempName;
                        }
                        if (this.m_usedTypes.get(m_data.get(index + compId).name) > 1)
                        {
                            m_tiles.get(compId).friendName_tf.text = m_tiles.get(compId).friendName_tf.text + (" " + ZLoc.t("Dialogs", "SellAnd"));
                        }
                    }
                    else
                    {
                        m_tiles.get(compId).friendName_tf.text = ZLoc.t("Dialogs", "SellFV");
                    }
                }
                else
                {
                    m_tiles.get(compId).friendName_tf.text = ZLoc.t("Dialogs", "SellFV");
                }
                sellBackRatio = Global.gameSettings().getNumber("sellBackRatio");
                m_tiles.get(compId).hoverText_mc.yield_tf.visible = true;
                m_tiles.get(compId).hoverText_mc.yield_tf.text = ZLoc.t("Dialogs", "Yield", {coins:Math.floor(m_data.get(index + compId).cost * sellBackRatio)});
                if (m_data.get(index + compId).harvestTime.toString() != "")
                {
                    m_tiles.get(compId).hoverText_mc.growth_tf.visible = true;
                    m_tiles.get(compId).hoverText_mc.growth_tf.text = ZLoc.t("Dialogs", "GrowthDays", {time:m_data.get(index + compId).harvestTime});
                }
                else
                {
                    m_tiles.get(compId).hoverText_mc.growth_tf.visible = false;
                }
                m_tiles.get(compId).giftAmount_tf.text = ZLoc.t("Dialogs", "GiftAmount", {amount:this.m_usedTypes.get(m_data.get(index + compId).name)});
                m_tiles.get(compId).hoverText_mc.visible = false;
                m_tiles.get(compId).icon.addEventListener(MouseEvent.MOUSE_OUT, this.onOut);
                m_tiles.get(compId).icon.addEventListener(MouseEvent.MOUSE_OVER, this.onOver);
                this.m_buttons.get(compId) = new Array();
                this.m_buttons.get(compId).get(0) = new GenericButton(m_tiles.get(compId).use_bt, this.onUseGift);
                this.m_buttons.get(compId).get(0).text = ZLoc.t("Dialogs", "Use");
                this.m_buttons.get(compId).get(1) = new GenericButton(m_tiles.get(compId).sell_bt, this.onSellGift);
                this.m_buttons.get(compId).get(1).text = ZLoc.t("Dialogs", "Sell");
                url = Global.gameSettings().getImageByName(m_data.get(index + compId).name, "icon");
                icon =LoadingManager .load (url ,void  (Event event )
            {
                _loc_2 = null;
                if (icon && icon.content)
                {
                    if (m_tiles.get(compId))
                    {
                        Utilities.removeAllChildren(m_tiles.get(compId).icon);
                    }
                    _loc_2 = icon.content;
                    _loc_2.width = 50;
                    _loc_2.height = 50;
                    m_tiles.get(compId).icon.addChild(_loc_2);
                }
                return;
            }//end
            );
            }
            return;
        }//end

         protected void  postRemoveTiles (int param1 )
        {
            if (m_tiles.get(param1).use_bt)
            {
                m_tiles.get(param1).use_bt.removeEventListener(MouseEvent.CLICK, this.onUseGift);
            }
            if (m_tiles.get(param1).sell_bt)
            {
                m_tiles.get(param1).sell_bt.removeEventListener(MouseEvent.CLICK, this.onSellGift);
            }
            return;
        }//end

        private void  onCloseClick (MouseEvent event )
        {
            removeTiles(false);
            this.m_usedTypes = null;
            m_window.gifts_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
            m_window.gifts_mc.arrowLt_bt.removeEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.gifts_mc.arrowRt_bt.removeEventListener(MouseEvent.CLICK, onRtArrowClick);
            event.stopPropagation();
            close();
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            return;
        }//end

        private void  onSellAllClick (MouseEvent event )
        {
            this.disableButtons();
            if (Global.player.getGiftCount() > 0)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "SellAllGifts", {total:this.m_totalGiftPrices}), GenericPopup.TYPE_YESNO, this.sellAllGiftsHandler, "sellAllGiftsHandler", true);
            }
            else
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_impression");
                UI.displayMessage(ZLoc.t("Dialogs", "NoMoreGiftsToSell"), GenericPopup.TYPE_YESNO, this.noMoreGiftsHandler, "noMoreGiftsHandler", true);
            }
            return;
        }//end

        private void  onSendGiftsClick (MouseEvent event )
        {
            this.disableButtons();
            GlobalEngine.socialNetwork.redirect("gifts.php?ref=send_gift_box");
            return;
        }//end

        private void  onUseGift (MouseEvent event )
        {
            _loc_2 = event.target.parent.name ;
            _loc_3 = parseInt(_loc_2.charAt ((_loc_2.length -1)))-1;
            _loc_4 = _loc_3+m_tileSet *this.NUM_SLOTS ;
            _loc_5 = ];
            _loc_6 = ].type;
            _loc_7 = _loc_5.name ;
            _loc_8 = _loc_5.className;
            StatsManager.count("farmville_sell_gift_screen", "click_use_item", _loc_7);
            switch(_loc_6)
            {
                case "contract":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_GIFT, MarketEvent.CONTRACT, _loc_7));
                    break;
                }
                case "residence":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_GIFT, MarketEvent.RESIDENCE, _loc_7));
                    break;
                }
                case "business":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_GIFT, MarketEvent.BUSINESS, _loc_7));
                    break;
                }
                case "factory":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_GIFT, MarketEvent.GENERIC, _loc_7));
                    break;
                }
                case "decoration":
                {
                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_GIFT, MarketEvent.GENERIC, _loc_7));
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.onCloseClick(event);
            return;
        }//end

        private void  onSellGift (MouseEvent event )
        {
            _loc_2 = event.target.parent.name ;
            _loc_3 = parseInt(_loc_2.charAt ((_loc_2.length -1)))-1;
            _loc_4 = _loc_3+m_tileSet *this.NUM_SLOTS ;
            this.disableButtons();
            this.m_itemObject = m_data.get(_loc_4);
            _loc_5 =Global.gameSettings().getItemByName(this.m_itemObject.itemName );
            UI.displayMessage(ZLoc.t("Main", "SellObjectSpecific", {item:_loc_5.localizedName, coins:_loc_5.sellPrice}), GenericPopup.TYPE_YESNO, this.sellOneGiftHandler, "sellOneGiftHandler", true);
            return;
        }//end

        protected void  sellAllGiftsHandler (GenericPopupEvent event )
        {
            _loc_2 = event.button ==GenericPopup.YES ;
            if (_loc_2 == true)
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "all_item");
                GameTransactionManager.addTransaction(new TSellStoredItem(null, true, this.onSellComplete), true, true);
                Global.player.removeAllGifts();
                this.onCloseClick(new MouseEvent(MouseEvent.CLICK));
            }
            else
            {
                this.disableButtons(false);
            }
            return;
        }//end

        protected void  noMoreGiftsHandler (GenericPopupEvent event )
        {
            _loc_2 = event.button ==GenericPopup.YES ;
            if (_loc_2 == true)
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_accept");
                GlobalEngine.socialNetwork.redirect("gifts.php?ref=empty_gift_box");
            }
            else
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_cancel");
                this.disableButtons(false);
            }
            return;
        }//end

        protected void  sellOneGiftHandler (GenericPopupEvent event )
        {
            MapResource _loc_3 =null ;
            _loc_2 = event.button ==GenericPopup.YES ;
            if (_loc_2 == true)
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_item", this.m_itemObject.itemName);
                GameTransactionManager.addTransaction(new TSellStoredItem(this.m_itemObject, false, this.onSellComplete), true, true);
                _loc_3 = new MapResource(this.m_itemObject.itemName);
                Global.player.removeGift(_loc_3);
                if (Global.player.getGiftCount() == 0)
                {
                    this.onCloseClick(new MouseEvent(MouseEvent.CLICK));
                }
            }
            else
            {
                this.disableButtons(false);
            }
            return;
        }//end

        private void  onSellComplete (Object param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            this.disableButtons(false);
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.gifts.size(); i0++)
                {
                	_loc_2 = param1.gifts.get(i0);

                    Global.player.gifts.put(_loc_2,  param1.gifts.get(_loc_2));
                }
                removeTiles();
                this.pushNewData();
                _loc_3 = 0;
                while (_loc_3 < this.NUM_SLOTS && _loc_3 < m_data.length())
                {

                    this.addComponent(_loc_3);
                    _loc_3++;
                }
                if (Global.player.getGiftCount() == 0)
                {
                    StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_impression");
                }
            }
            return;
        }//end

        private void  disableButtons (boolean param1 =true )
        {
            this.m_sellAll.disabled = param1;
            this.m_sendGifts.disabled = param1;
            if (param1 !=null)
            {
                m_window.gifts_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
            }
            else
            {
                m_window.gifts_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCloseClick);
            }
            if (m_window.gifts_mc.arrowLt_bt && m_window.gifts_mc.arrowLt_bt)
            {
                m_window.gifts_mc.arrowLt_bt.enabled = !param1;
                m_window.gifts_mc.arrowLt_bt.mouseEnabled = !param1;
            }
            if (m_window.gifts_mc.arrowRt_bt && m_window.gifts_mc.arrowRt_bt)
            {
                m_window.gifts_mc.arrowRt_bt.enabled = !param1;
                m_window.gifts_mc.arrowRt_bt.mouseEnabled = !param1;
            }
            int _loc_2 =0;
            while (_loc_2 < this.NUM_SLOTS && _loc_2 < m_data.length())
            {

                this.m_buttons.get(_loc_2).get(0).toggleDisable(!param1);
                this.m_buttons.get(_loc_2).get(1).toggleDisable(!param1);
                _loc_2++;
            }
            return;
        }//end

        private void  onOut (MouseEvent event )
        {
            _loc_2 = event.target.parent.name ;
            _loc_3 = parseInt(_loc_2.charAt ((_loc_2.length -1)))-1;
            _loc_4 = _loc_3+m_tileSet *this.NUM_SLOTS ;
            m_tiles.get(_loc_4 % this.NUM_SLOTS).hoverText_mc.visible = false;
            return;
        }//end

        private void  onOver (MouseEvent event )
        {
            _loc_2 = event.target.parent.name ;
            _loc_3 = parseInt(_loc_2.charAt ((_loc_2.length -1)))-1;
            _loc_4 = _loc_3+m_tileSet *this.NUM_SLOTS ;
            m_tiles.get(_loc_4 % this.NUM_SLOTS).hoverText_mc.visible = true;
            return;
        }//end

    }



