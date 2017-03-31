package Classes;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Init.*;
import Modules.sunset.*;
import Transactions.*;
import ZLocalization.*;
//import flash.utils.*;
import validation.*;

    public class TicketManager
    {
        protected Dictionary m_tickets ;
        private static Dictionary m_ticketThemeToInfo =new Dictionary ();
public static  int SHARE_TICKET_POPUP_THROTTLE =20;
        public static  int MAX_TICKETS =300;

        public  TicketManager ()
        {
            this.m_tickets = new Dictionary();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.m_tickets.put(_loc_2,  param1.get(_loc_2));
            }
            return;
        }//end

        public void  showTicketPopup (String param1 ,boolean param2 =false )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            double _loc_3 =-1;
            if (this.m_tickets && this.m_tickets.hasOwnProperty(param1) && this.m_tickets.get(param1).get("lastViewedPopup") != null)
            {
                _loc_3 = this.m_tickets.get(param1).lastViewedPopup;
            }
            if (_loc_3 != -1 && (GlobalEngine.getTimer() - _loc_3) / 1000 < SHARE_TICKET_POPUP_THROTTLE)
            {
                return;
            }
            if (!param2)
            {
                this.displayTicketPopup(param1);
            }
            else
            {
                this.displayAtCapacityPopup(param1);
            }
            this.m_tickets.get(param1).lastViewedPopup = GlobalEngine.getTimer();
            return;
        }//end

        private void  displayTicketPopup (String param1 )
        {
            Sunset _loc_17 =null ;
            double _loc_18 =0;
            String _loc_19 =null ;
            _loc_2 = param1"feed_dialog_"+;
            _loc_3 = GenericDialogView.TYPE_CUSTOM_OK;
            _loc_4 = Delegate.create(this,this.showTicketFeedCallback,.get(param1));
            _loc_5 = param1"ticket_booth_feed_dialog_"+;
            String _loc_6 ="assets/dialogs/genericDialog_samCongrats.png";
            boolean _loc_7 =true ;
            int _loc_8 =0;
            String _loc_9 ="";
            Function _loc_10 =null ;
            _loc_11 = ZLoc.t("Dialogs","ticket_booth_feed_dialog_"+param1+"_button");
            boolean _loc_12 =true ;
            _loc_13 = ZLoc.tk("Tickets",param1,"",Global.ticketManager.getCount(param1));
            Object _loc_14 ={};
            _loc_14.put("number", Global.ticketManager.getCount(param1));
            _loc_14.put("city_name", Global.player.cityName);
            _loc_14.put(param1, _loc_13);
            _loc_15 = ZLoc.t("Dialogs","ticket_booth_feed_dialog_"+param1+"_message",_loc_14);
            if (isThemeValid(param1))
            {
                _loc_17 = Global.sunsetManager.getSunsetByThemeName(param1);
                if (_loc_17 && _loc_17.isInSunsetInterval())
                {
                    _loc_18 = (_loc_17.endDate - GameSettingsInit.getCurrentTime()) / (1000 * 3600 * 24);
                    if (_loc_18 < 1)
                    {
                        _loc_19 = ZLoc.t("Tickets", param1 + "_time_left_singular");
                    }
                    else
                    {
                        _loc_19 = ZLoc.t("Tickets", param1 + "_time_left_plural", {days_left:Math.round(_loc_18)});
                    }
                    _loc_15 = _loc_15 + ("\n\n" + _loc_19);
                }
            }
            CharacterResponseDialog _loc_16 =new CharacterResponseDialog(_loc_15 ,_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 ,_loc_9 ,_loc_10 ,_loc_11 ,_loc_12 );
            UI.displayPopup(_loc_16);
            return;
        }//end

        private void  displayAtCapacityPopup (String param1 )
        {
            _loc_2 = param1"feed_dialog_capacity_"+;
            _loc_3 = GenericDialogView.TYPE_CUSTOM_OK;
            _loc_4 = Delegate.create(this,this.showTicketFeedCallback,.get(param1));
            _loc_5 = param1"ticket_booth_feed_dialog_capacity_"+;
            String _loc_6 ="assets/dialogs/genericDialog_samCongrats.png";
            boolean _loc_7 =true ;
            int _loc_8 =0;
            String _loc_9 ="";
            Function _loc_10 =null ;
            _loc_11 = ZLoc.t("Dialogs","ticket_booth_feed_dialog_capacity_"+param1+"_button");
            boolean _loc_12 =true ;
            _loc_13 = TicketManager.getCurrencyForTheme(param1);
            _loc_14 = Global.gameSettings().getItemByName(_loc_13);
            _loc_15 = ZLoc.tk("Items",_loc_13);
            Object _loc_16 ={};
            _loc_16.put("basic_currency", _loc_14.localizedName);
            _loc_16.put("currency", _loc_15);
            _loc_16.put("capacity", TicketManager.getCapacityForTheme(param1));
            _loc_17 = ZLoc.t("Dialogs","ticket_booth_feed_dialog_capacity_"+param1+"_message",_loc_16);
            CharacterResponseDialog _loc_18 =new CharacterResponseDialog(_loc_17 ,_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 ,_loc_9 ,_loc_10 ,_loc_11 ,_loc_12 );
            UI.displayPopup(_loc_18);
            return;
        }//end

        private void  showTicketFeedCallback (GenericPopupEvent event ,String param2 )
        {
            if (!this.m_tickets.get(param2))
            {
                this.initalizeTicketDictionary(param2);
            }
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendTicketFeed("ticket_" + param2);
            }
            return;
        }//end

        public boolean  redeemTickets (String param1 ,String param2 )
        {
            Object _loc_5 =null ;
            String _loc_8 =null ;
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            _loc_3 = this.getTicketRewards(param1);
            Object _loc_4 =null ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_5 = _loc_3.get(i0);

                _loc_8 = _loc_5.get("rewardName");
                if (_loc_8 == param2)
                {
                    _loc_4 = _loc_5;
                    break;
                }
            }
            if (_loc_4 == null)
            {
                return false;
            }
            _loc_6 = _loc_4.get("rewardCost") ;
            _loc_7 = Global.ticketManager.getCount(param1);
            if (_loc_6 > _loc_7)
            {
                this.handleNotEnoughTickets(param1, _loc_8);
                return false;
            }
            this.handlePurchaseWithTickets(param1, _loc_8, _loc_6);
            return true;
        }//end

        protected void  handleNotEnoughTickets (String param1 ,String param2 )
        {
            _loc_3 = Global.gameSettings().getItemByName(param2);
            _loc_4 = getCurrencyForTheme(param1);
            _loc_5 = ZLoc.tk("Items",_loc_4);
            _loc_6 = ZLoc.tk("Items",param2,"",1);
            String _loc_7 ="buyPrizeWithCash";
            _loc_8 = ZLoc.t("Dialogs","buyPrizeWithCash_message",{currency_loc_5,rewardItemName});
            _loc_9 = _loc_3;
            _loc_10 = Delegate.create(this,this.onBuyPrizeWithCash,param2);
            boolean _loc_11 =false ;
            UI.displayItemCashDialog(_loc_7, _loc_8, _loc_9, _loc_10, _loc_11);
            return;
        }//end

        private void  onBuyPrizeWithCash (GenericPopupEvent event ,String param2 )
        {
            Item _loc_3 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_3 = Global.gameSettings().getItemByName(param2);
                if (Global.player.canBuyCash(_loc_3.cash))
                {
                    GameTransactionManager.addTransaction(new TBuyItem(_loc_3.name, 1));
                    this.displayBuyWithCashCongratulations(_loc_3.name, _loc_3.cash);
                }
            }
            return;
        }//end

        protected void  handlePurchaseWithTickets (String param1 ,String param2 ,int param3 )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            this.updateCount(param1, -1 * param3);
            Global.player.inventory.addItems(param2, 1);
            Object _loc_4 =new Object ();
            _loc_4.put("theme",  param1);
            _loc_4.put("rewardName",  param2);
            TTicket _loc_5 =new TTicket(TTicket.REDEEM_TICKETS ,_loc_4 );
            TransactionManager.addTransaction(_loc_5);
            this.displayRedemptionCongratulations(param1, param2, param3);
            return;
        }//end

        public int  getTicketCountByLevel (String param1 )
        {
            _loc_2 = this.getTicketLevels(param1 );
            return _loc_2.get((this.getTicketLevel(param1) - 1));
        }//end

        private void  displayRedemptionCongratulations (String param1 ,String param2 ,int param3 )
        {
            _loc_4 = TicketManager.getCurrencyForTheme(param1);
            _loc_5 =Global.gameSettings().getItemByName(param2 );
            _loc_6 = ZLoc.tk("Items",_loc_4 );
            _loc_7 = ZLoc.tk("Items",param2 ,"",1);
            String _loc_8 ="congratulations";
            _loc_9 = ZLoc.t("Dialogs","ticket_booth_redeem_message",{numparam3,currency,rewardItemName});
            _loc_10 = _loc_5.iconRelative;
            GenericDialog _loc_11 =new GenericDialog(_loc_9 ,_loc_8 ,GenericDialogView.TYPE_OK ,null ,_loc_8 ,_loc_10 );
            UI.displayPopup(_loc_11, false, _loc_8);
            return;
        }//end

        private void  displayBuyWithCashCongratulations (String param1 ,int param2 )
        {
            _loc_3 = Global.gameSettings().getItemByName(param1);
            _loc_4 = ZLoc.tk("Items",param1,"",1);
            String _loc_5 ="congratulations";
            _loc_6 = ZLoc.t("Dialogs","ticket_booth_buy_with_cash_message",{rewardItemName_loc_4,num});
            _loc_7 = _loc_3.iconRelative;
            GenericDialog _loc_8 =new GenericDialog(_loc_6 ,_loc_5 ,GenericDialogView.TYPE_OK ,null ,_loc_5 ,_loc_7 );
            UI.displayPopup(_loc_8, false, _loc_5);
            return;
        }//end

        public int  getTicketLevel (String param1 )
        {
            String _loc_3 =null ;
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            _loc_2 = this.getTicketLevels(param1);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_2.get(_loc_3) == _loc_2.get((_loc_2.length - 1)))
                {
                    return (int(_loc_3) + 1);
                }
                if (_loc_2.get(_loc_3) <= this.getCount(param1) && _loc_2.get((int(_loc_3) + 1)) > this.getCount(param1))
                {
                    return (int(_loc_3) + 1);
                }
            }
            return 1;
        }//end

        public void  updateLevel (String param1 )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            this.m_tickets.get(param1).lastLevel = this.getTicketLevel(param1);
            this.m_tickets.get(param1).lastCount = this.getCount(param1);
            return;
        }//end

        public boolean  canAddTicket (String param1 ,int param2 )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            _loc_3 = this.m_tickets.get(param1).count;
            _loc_4 = getCapacityForTheme(param1);
            return _loc_3 + param2 <= _loc_4;
        }//end

        public void  updateCount (String param1 ,int param2 ,boolean param3 =true )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            this.m_tickets.get(param1).count = this.m_tickets.get(param1).count + param2;
            this.m_tickets.get(param1).lastRequest = GlobalEngine.getTimer();
            if (param2 != 0 && Global.questManager != null && Global != null)
            {
                Global.questManager.refreshAllQuests();
            }
            return;
        }//end

        public int  getCount (String param1 )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            int _loc_2 =0;
            if (this.m_tickets.get(param1))
            {
                _loc_2 = this.m_tickets.get(param1).count;
            }
            return _loc_2;
        }//end

        public Object  getTicket (String param1 )
        {
            if (!this.m_tickets.get(param1))
            {
                this.initalizeTicketDictionary(param1);
            }
            return this.m_tickets.get(param1);
        }//end

        private void  initalizeTicketDictionary (String param1 )
        {
            Object _loc_2 =new Object ();
            _loc_2.put("count", 0);
            _loc_2.put("lastRequest", 0);
            _loc_2.put("lastLevel", 0);
            _loc_2.put("lastCount", 0);
            m_tickets.put(param1, _loc_2);
            return;
        }//end

        public Array  getTicketLevels (String param1 )
        {
            Array _loc_2 =new Array ();
            _loc_3 = TicketManager.getTicketInfoByTheme(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.levels;
            }
            return _loc_2;
        }//end

        public Array  getTicketRewards (String param1 )
        {
            Array _loc_2 =new Array ();
            _loc_3 = TicketManager.getTicketInfoByTheme(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.rewards;
            }
            return _loc_2;
        }//end

        public Array  getTicketBundles (String param1 )
        {
            Array _loc_2 =new Array ();
            _loc_3 = TicketManager.getTicketInfoByTheme(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.bundles;
            }
            return _loc_2;
        }//end

        private static void  initializeTicketInfoDictionary (String param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_5 =null ;
            XML _loc_6 =null ;
            Array _loc_7 =null ;
            XML _loc_8 =null ;
            Array _loc_9 =null ;
            XML _loc_10 =null ;
            String _loc_11 =null ;
            TicketInfo _loc_12 =null ;
            Object _loc_13 =null ;
            _loc_2 = Global.gameSettings().getTickets();
            for(int i0 = 0; i0 < _loc_2.children().size(); i0++)
            {
            	_loc_3 = _loc_2.children().get(i0);

                if (_loc_3.attribute("name") == param1)
                {
                    _loc_4 = String(_loc_3.currency.@itemName);
                    _loc_5 = new Array();
                    for(int i0 = 0; i0 < _loc_3.levels.children().size(); i0++)
                    {
                    	_loc_6 = _loc_3.levels.children().get(i0);

                        _loc_5.put(int(_loc_6.attribute("id")),  _loc_6.attribute("count").toString());
                    }
                    _loc_7 = new Array();
                    for(int i0 = 0; i0 < _loc_3.rewards.children().size(); i0++)
                    {
                    	_loc_8 = _loc_3.rewards.children().get(i0);

                        _loc_13 = {rewardName:_loc_8.attribute("name").toString(), rewardCost:_loc_8.attribute("cost").toString()};
                        _loc_7.put(int(_loc_8.attribute("id")),  _loc_13);
                    }
                    _loc_9 = new Array();
                    for(int i0 = 0; i0 < _loc_3.bundles.children().size(); i0++)
                    {
                    	_loc_10 = _loc_3.bundles.children().get(i0);

                        _loc_9.put(int(_loc_10.attribute("id")),  _loc_10.attribute("name").toString());
                    }
                    _loc_11 = String(_loc_3.limited.@endDate);
                    _loc_12 = new TicketInfo(param1, _loc_4, _loc_5, _loc_7, _loc_9, _loc_11);
                    m_ticketThemeToInfo.put(param1,  _loc_12);
                }
            }
            return;
        }//end

        public static TicketInfo  getTicketInfoByTheme (String param1 )
        {
            if (!m_ticketThemeToInfo.get(param1))
            {
                TicketManager.initializeTicketInfoDictionary(param1);
            }
            _loc_2 = m_ticketThemeToInfo.get(param1);
            return _loc_2;
        }//end

        public static String  getCurrencyForTheme (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = TicketManager.getTicketInfoByTheme(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.currency;
            }
            return _loc_2;
        }//end

        public static int  getCapacityForTheme (String param1 )
        {
            _loc_2 = TicketManager.MAX_TICKETS;
            _loc_3 = TicketManager.getTicketInfoByTheme(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.capacity;
            }
            return _loc_2;
        }//end

        public static boolean  isThemeValid (String param1 )
        {
            _loc_2 = Global.validationManager.getValidator(param1+"Sunset");
            return _loc_2 && _loc_2.validate();
        }//end

    }




