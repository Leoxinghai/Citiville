package Modules.sale.payments;

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
import Display.DialogUI.*;
import Engine.Managers.*;
import Modules.flashsale.ui.*;
import Modules.freegiftsale.ui.*;
import Modules.sale.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.external.*;
//import flash.utils.*;

    public class PaymentsSale
    {
        protected String m_experiment ;
        protected int m_variant =0;
        protected int m_coolDownPeriod =-1;
        protected int m_displayCoolDownPeriod =-1;
        protected int m_duration =-1;
        protected double m_endDate =-1;
        protected int m_minLevel =0;
        protected int m_maxLevel =-1;
        protected String m_name ;
        protected String m_onPurchase ;
        protected Class m_startupDialogClass ;
        protected String m_startupDialogName ;
        protected Class m_idleDialogClass ;
        protected String m_idleDialogName ;
        protected Object m_sale =null ;
        private static Array allowedDialogClasses =.get(FlashSaleDialog ,FlashSaleMiniDialog ,TimedFlashSaleDialog ,FreeGiftSaleDialog ,GetCashDialog ,EoQFlashSaleDialog) ;
        public static  int INVALID_PACKAGE_SET =-1;
        public static  int NOT_SET =-1;
        public static  int TIMESTAMP_TTL =604800;

        public  PaymentsSale (Object param1 )
        {
            this.init(param1);
            return;
        }//end

        public String  type ()
        {
            return this.m_sale.type;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  experiment ()
        {
            return this.m_experiment;
        }//end

        public int  saleVariant ()
        {
            return this.m_variant;
        }//end

        public boolean  isCooldownActive ()
        {
            int _loc_2 =0;
            boolean _loc_1 =false ;
            if (this.m_sale && this.m_displayCoolDownPeriod != NOT_SET)
            {
                if (this.m_sale.type == PaymentsSaleManager.TYPE_EOQ_SALE)
                {
                }
                _loc_2 = uint(GlobalEngine.getTimer() / 1000);
                _loc_1 = _loc_2 - this.m_displayCoolDownPeriod <= Global.player.getLastActivationTime(this.m_name + "_lastSeen");
            }
            return _loc_1;
        }//end

        public double  endDate ()
        {
            _loc_1 =Global.player.getLastActivationTime(this.m_name +"_finish");
            if (_loc_1 == -1 && this.m_duration != NOT_SET)
            {
                _loc_1 = uint(GlobalEngine.getTimer() / 1000 + this.m_duration);
            }
            if (this.m_duration != NOT_SET && this.m_endDate != NOT_SET)
            {
                return Math.min(_loc_1, this.m_endDate);
            }
            if (this.m_duration != NOT_SET)
            {
                return _loc_1;
            }
            if (this.m_endDate != NOT_SET)
            {
                return this.m_endDate;
            }
            return 0;
        }//end

        public int  timeRemaining ()
        {
            _loc_1 = int(GlobalEngine.getTimer()/1000);
            _loc_2 = this.endDate ;
            if (_loc_2 <= 0)
            {
                return 0;
            }
            return this.endDate - _loc_1;
        }//end

        public int  duration ()
        {
            return this.m_duration;
        }//end

        public int  cooldownPeriod ()
        {
            return this.m_coolDownPeriod;
        }//end

        public boolean  doesUseFeaturedSales (String param1 )
        {
            switch(param1)
            {
                case PaymentsSaleManager.TYPE_OUT_OF_CASH_SALE:
                {
                    return false;
                }
                default:
                {
                    break;
                }
            }
            return true;
        }//end

        public boolean  canShowSale (boolean param1 =true ,String param2)
        {
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            boolean _loc_7 =false ;
            double _loc_8 =0;
            double _loc_9 =0;
            boolean _loc_10 =false ;
            double _loc_11 =0;
            boolean _loc_3 =false ;
            if (this.m_sale)
            {
                _loc_4 = !Global.player.isNewPlayer && Global.player.level >= this.m_minLevel && (this.m_maxLevel == NOT_SET || Global.player.level <= this.m_maxLevel);
                _loc_5 = this.m_variant > 0;
                _loc_6 = param1 == false || this.isCooldownActive == false;
                _loc_8 = uint(GlobalEngine.getTimer() / 1000);
                _loc_9 = Global.player.getLastActivationTime(this.m_name + "_finish");
                if (this.m_duration != NOT_SET)
                {
                    _loc_7 = _loc_9 != -1 && _loc_8 > _loc_9;
                }
                else
                {
                    _loc_7 = false;
                }
                if (this.m_endDate != NOT_SET)
                {
                    _loc_7 = _loc_7 || _loc_8 > this.m_endDate;
                }
                _loc_10 = true;
                if (this.m_coolDownPeriod != NOT_SET)
                {
                    _loc_11 = Global.player.getLastActivationTime(this.m_name + "_cooldown");
                    _loc_10 = _loc_11 == -1 || _loc_9 != -1 && _loc_8 < _loc_9 || (_loc_8 < _loc_9 || _loc_8 > _loc_11);
                }
                else
                {
                    _loc_10 = true;
                }
                if (this.doesUseFeaturedSales(param2) == false)
                {
                    _loc_3 = _loc_4 && _loc_5 && _loc_6 && _loc_10 && !_loc_7;
                }
                else
                {
                    _loc_3 = _loc_4 && _loc_5 && _loc_6 && _loc_10 && !_loc_7 && this.getSales().length > 0;
                }
            }
            return _loc_3;
        }//end

        public void  init (Object param1 )
        {
            XMLList typesXml ;
            XMLList matchingTypes ;
            XML saleTypeXml ;
            XMLList matchingNames ;
            XML saleNameXml ;
            config = param1;
            Object _loc_7;
            if (this.requiresRefresh(config))
            {
                this.m_sale = config;
                this.m_name = config.name;
                this.m_variant = Global.experimentManager.getVariant(config._zexp);
                this.m_experiment = config._zexp;
                if (config.duration)
                {
                    this.m_duration = config.duration * DateUtil.SECONDS_PER_HOUR;
                }
                if (config.endDateVar)
                {
                    this.m_endDate = DateFormatter.parseTimeString(RuntimeVariableManager.getString(config.endDateVar, "01/01/2011")) / 1000;
                }
                if (config.cooldown)
                {
                    this.m_coolDownPeriod = config.cooldown * DateUtil.SECONDS_PER_HOUR;
                }
                if (config.displayCooldown)
                {
                    this.m_displayCoolDownPeriod = config.displayCooldown * DateUtil.SECONDS_PER_HOUR;
                }
                if (config.displayCooldownVar && config.displayCooldown)
                {
                    this.m_displayCoolDownPeriod = RuntimeVariableManager.getInt(config.displayCoolDownVar, 10) * int(config.displayCooldown) * DateUtil.SECONDS_PER_HOUR;
                }
                if (config.minLevel)
                {
                    this.m_minLevel = config.minLevel;
                }
                if (config.maxLevel)
                {
                    this.m_maxLevel = config.maxLevel;
                }
                typesXml = Global.gameSettings().saleSettingsXML.type;
                int _loc_4 =0;
                _loc_5 = typesXml;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == m_sale.type)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                matchingTypes = _loc_3;
                saleTypeXml = matchingTypes.length() > 0 ? (matchingTypes.get(0)) : (null);
                if (saleTypeXml)
                {
                    this.m_onPurchase = String(saleTypeXml.onPurchase);
                    _loc_4 = 0;
                    _loc_5 = saleTypeXml.saleDef;
                    _loc_3 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@name == m_sale.name)
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    matchingNames = _loc_3;
                    saleNameXml = matchingNames.length() > 0 ? (matchingNames.get(0)) : (null);
                    this.initSaleNameSettings(saleNameXml);
                }
            }
            return;
        }//end

        protected void  initSaleNameSettings (XML param1 )
        {
            XML dialogEntry ;
            saleNameSettings = param1;
            dialogs = saleNameSettings.dialog;
            int _loc_4 =0;
            _loc_5 = dialogs;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == "startup")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            if (_loc_3.length() > 0)
            {
                _loc_4 = 0;
                _loc_5 = dialogs;
                _loc_3 = new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@type == "startup")
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                dialogEntry = _loc_3.get(0);
                this.m_startupDialogClass = getDefinitionByName(String(dialogEntry.@className)) as Class;
                this.m_startupDialogName = String(dialogEntry.@name);
            }
            _loc_4 = 0;
            _loc_5 = dialogs;
            _loc_3 = new XMLList("");
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == "idle")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            if (_loc_3.length() > 0)
            {
                _loc_4 = 0;
                _loc_5 = dialogs;
                _loc_3 = new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@type == "idle")
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                dialogEntry = _loc_3.get(0);
                this.m_idleDialogClass = getDefinitionByName(String(dialogEntry.@className)) as Class;
                this.m_idleDialogName = String(dialogEntry.@name);
            }
            _loc_4 = 0;
            _loc_5 = dialogs;
            _loc_3 = new XMLList("");
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@type == "manual")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            if (_loc_3.length() > 0)
            {
                _loc_4 = 0;
                _loc_5 = dialogs;
                _loc_3 = new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@type == "manual")
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                dialogEntry = _loc_3.get(0);
                this.m_idleDialogClass = getDefinitionByName(String(dialogEntry.@className)) as Class;
                this.m_idleDialogName = String(dialogEntry.@name);
            }
            return;
        }//end

        protected boolean  requiresRefresh (Object param1 )
        {
            return param1 && (!this.m_sale || param1._zexp != this.m_sale._zexp || param1._zvar != this.m_sale._zvar || param1.ifTrue != this.m_sale.ifTrue || param1.priority != this.m_sale.priority);
        }//end

        public GenericDialog  createStartupDialog ()
        {
            GenericDialog _loc_1 =null ;
            if (this.m_startupDialogClass)
            {
                _loc_1 = new this.m_startupDialogClass(this, "", this.m_startupDialogName, GenericDialogView.TYPE_MODAL, this.onCloseStartup, this.m_startupDialogName, "", true, 0, "", this.onCloseStartup, "");
                this.onShowSale();
            }
            return _loc_1;
        }//end

        public GenericDialog  createIdleDialog ()
        {
            GenericDialog _loc_1 =null ;
            if (this.m_idleDialogClass)
            {
                _loc_1 = new this.m_idleDialogClass(this, "", this.m_idleDialogName, GenericDialogView.TYPE_MODAL, null, this.m_idleDialogName, "", true, 0, "", null, "");
                this.onShowSale();
            }
            return _loc_1;
        }//end

        public GenericDialog  createManualDialog (...args )
        {
            //args = null;
            GenericDialog result =null ;
            if (this.m_idleDialogClass)
            {
                result = new this.m_idleDialogClass(this, args);
                this.onShowSale();
            }
            return result;
        }//end

        public Array  getSales ()
        {
            if (!this.m_sale)
            {
                return new Array();
            }
            return this.m_sale.featuredSales;
        }//end

        public int  getPaymentsPackageSet ()
        {
            if (!this.m_sale)
            {
                return INVALID_PACKAGE_SET;
            }
            return this.m_sale.packageSetId;
        }//end

        public int  getSaleDiscount ()
        {
            if (!this.m_sale)
            {
                return 0;
            }
            return this.m_sale.featuredSales.get(0).discount;
        }//end

        public void  performPurchaseAction (int param1 =0,String param2 ="",int param3 =0)
        {
            _loc_4 = param2"money.php?ref="+;
            if (param1 != 0)
            {
                _loc_4 = _loc_4 + ("&selid=" + param1.toString());
            }
            switch(this.m_onPurchase)
            {
                case "popup":
                {
                    StatsManager.count(StatsCounterType.GAME_ACTIONS, "payments_page", this.statsName, "viewed");
                    ExternalInterface.call("fbc_popup_open", String(param3 + ":" + param1), param2);
                    break;
                }
                default:
                {
                    GlobalEngine.socialNetwork.redirect(_loc_4);
                    break;
                    break;
                }
            }
            return;
        }//end

        private void  onCloseStartup (Object param1)
        {
            double _loc_2 =0;
            if (this.m_sale)
            {
                _loc_2 = uint(GlobalEngine.getTimer() / 1000);
                Global.player.setLastActivationTime(this.m_name + "_lastSeen", _loc_2);
            }
            return;
        }//end

        protected void  onShowSale ()
        {
            if (this.m_sale && Global.player.getLastActivationTime(this.m_name + "_start") == -1)
            {
                this.onStartSale();
            }
            return;
        }//end

        protected void  onStartSale ()
        {
            StatsManager.count(StatsCounterType.GAME_ACTIONS, this.statsName, "started");
            GameTransactionManager.addTransaction(new TStartSale(this), true);
            return;
        }//end

        public String  statsName ()
        {
            return this.name + "_" + this.type;
        }//end

    }



