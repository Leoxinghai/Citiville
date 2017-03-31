package Classes.gates;

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
import Display.DialogUI.*;
import Display.GateUI.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.utils.*;
import org.aswing.ext.*;

    public class AbstractGate implements IGate
    {
        protected GateFactory m_factory ;
        protected Object m_keys ;
        protected Object m_virals ;
        protected double m_targetObjectId ;
        protected GenericDialog m_dialog ;
        private Function m_unlockCB =null ;
        protected boolean m_directUnlock =true ;
        protected String m_unlockItemName ;
        protected GridListCellFactory m_cellFactory ;
        protected String m_loadType ;
        protected String m_name ;
        protected String m_locKey ;
        protected String m_subLocKey ;
        protected double m_unlockCost =-1;
        protected String m_iconURL ;
        public static  String LOAD_TYPE_DYNAMIC ="dynamic";
        public static  String LOAD_TYPE_STATIC ="static";
        public static  double GS_VALUE =0.33;
        public static  String SEND_GIFT_ITEM ="send_gift";
        public static  String ASK_BUILDING_BUDDY ="ask_building_buddy";
        public static  String PRE_BUILD_GATE ="pre_build";
        public static  String BUILD_GATE ="build";

        public  AbstractGate (String param1 )
        {
            this.m_keys = new Array();
            this.m_virals = {};
            this.m_unlockItemName = param1;
            return;
        }//end

        public boolean  showBuyAllCost ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUYALL_THRESHOLD );
            return _loc_1 > 2;
        }//end

        public double  discount ()
        {
            return RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 20) / 100;
        }//end

        public double  threshold ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUYALL_THRESHOLD );
            return _loc_1 == ExperimentDefinitions.BUYALL_VARIANT_ALWAYS ? (1) : (_loc_1 == ExperimentDefinitions.BUYALL_VARIANT_SEVENTYFIVE ? (0.75) : (_loc_1 == ExperimentDefinitions.BUYALL_VARIANT_FIFTY ? (0.5) : (0)));
        }//end

        public double  progress ()
        {
            return 1 - this.amountNeeded / this.totalAmount;
        }//end

        public String  unlockItemName ()
        {
            return this.m_unlockItemName;
        }//end

        public void  unlockCallback (Function param1 )
        {
            this.m_unlockCB = param1;
            return;
        }//end

        public void  targetObjectId (double param1 )
        {
            this.m_targetObjectId = param1;
            return;
        }//end

        public String  loadType ()
        {
            return this.m_loadType;
        }//end

        public void  loadType (String param1 )
        {
            this.m_loadType = this.loadType;
            return;
        }//end

        public void  factory (GateFactory param1 )
        {
            this.m_factory = param1;
            return;
        }//end

        public GateFactory  factory ()
        {
            return this.m_factory;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  locKey ()
        {
            return this.m_locKey;
        }//end

        public String  subLocKey ()
        {
            return this.m_subLocKey;
        }//end

        public double  unlockCost ()
        {
            double _loc_1 =0;
            String _loc_2 =null ;
            Item _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            if (this.m_unlockCost > 0)
            {
                return this.m_unlockCost;
            }
            _loc_1 = 0;
            for (_loc_2 in this.m_keys)
            {

                _loc_3 = Global.gameSettings().getItemByName(_loc_2);
                _loc_4 = Global.player.inventory.getItemCount(_loc_3);
                _loc_5 = this.m_keys.get(_loc_2);
                _loc_6 = Math.max(0, _loc_5 - _loc_4);
                _loc_7 = _loc_6 * _loc_3.cash;
                _loc_1 = _loc_1 + _loc_7;
            }
            if (this.progress >= this.threshold)
            {
                return _loc_1;
            }
            return Math.floor(_loc_1 * (1 - this.discount));
        }//end

        public String  iconURL ()
        {
            return this.m_iconURL;
        }//end

        public void  loadFromXML (XML param1 )
        {
            XML _loc_2 =null ;
            this.m_loadType = param1.@loadType;
            this.m_name = String(param1.@name);
            this.m_locKey = String(param1.@locKey);
            this.m_subLocKey = String(param1.@subLocKey);
            this.m_iconURL = String(param1.@iconURL);
            this.m_directUnlock = String(param1.@directUnlock) != "false";
            if (param1.@cashCost && Number(param1.@cashCost) > 0)
            {
                this.m_unlockCost = Number(param1.@cashCost);
            }
            for each (_loc_2 in param1.key)
            {

                this.m_keys.put(_loc_2.@name,  _loc_2.@amount);
                this.m_virals.put(_loc_2.@name,  _loc_2.@viral.toString());
                if (this.m_virals.get(_loc_2.@name) == "")
                {
                    this.m_virals.put(_loc_2.@name,  AskFriendsDialog.REQUEST_BUILDABLE);
                }
            }
            return;
        }//end

        public boolean  unlockGate ()
        {
            if (this.checkForKeys() == true)
            {
                this.takeKeys();
                if (this.m_unlockCB != null)
                {
                    this.m_unlockCB();
                }
                if (this.m_name && this.m_unlockItemName)
                {
                    StatsManager.sample(100, StatsKingdomType.GAME_ACTIONS, this.m_name, StatsPhylumType.VIRAL_UNLOCK, this.m_unlockItemName);
                }
                return true;
            }
            return false;
        }//end

        public void  loadFromObject (Object param1 )
        {
            _loc_2 = null;
            if (param1 == null || param1 == "")
            {
                return;
            }
            this.m_name = String(param1.name);
            for (_loc_2 in param1.keys)
            {

                this.m_keys.put(_loc_2,  param1.keys.get(_loc_2));
                this.m_virals[_loc_2] = param1.virals != null && param1.virals.get(_loc_2) != "" ? (param1.virals.get(_loc_2).toString()) : (AskFriendsDialog.REQUEST_BUILDABLE);
            }
            this.m_loadType = param1.loadType;
            return;
        }//end

        public Object getData ()
        {
            return null;
        }//end

        public int  getKey (String param1 )
        {
            if (this.m_keys.hasOwnProperty(param1))
            {
                return this.m_keys.get(param1);
            }
            return 0;
        }//end

        public void  incrementKey (String param1 )
        {
            return;
        }//end

        public boolean  checkForKeys ()
        {
            return false;
        }//end

        protected void  takeKeys ()
        {
            return;
        }//end

        public int  keyCount ()
        {
            String _loc_2 =null ;
            int _loc_1 =0;
            for (_loc_2 in this.m_keys)
            {

                _loc_1++;
            }
            return _loc_1;
        }//end

        public Array  getKeyArray ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array ();
            for (_loc_2 in this.m_keys)
            {

                _loc_1.push(_loc_2);
            }
            return _loc_1;
        }//end

        public String  getRequirementString ()
        {
            return null;
        }//end

        public int  rawTotalCost ()
        {
            String _loc_4 =null ;
            Item _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_1 =0;
            Dictionary _loc_2 =new Dictionary ();
            boolean _loc_3 =false ;
            for (_loc_4 in this.m_keys)
            {

                _loc_5 = Global.gameSettings().getItemByName(_loc_4);
                _loc_6 = this.m_keys.get(_loc_4);
                _loc_7 = _loc_6 * _loc_5.cash;
                _loc_1 = _loc_1 + _loc_7;
            }
            return _loc_1;
        }//end

        public int  subTotalCost ()
        {
            String _loc_4 =null ;
            Item _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_1 =0;
            Dictionary _loc_2 =new Dictionary ();
            boolean _loc_3 =false ;
            for (_loc_4 in this.m_keys)
            {

                _loc_5 = Global.gameSettings().getItemByName(_loc_4);
                _loc_6 = Global.player.inventory.getItemCount(_loc_5);
                _loc_7 = this.m_keys.get(_loc_4);
                _loc_8 = Math.max(0, _loc_7 - _loc_6);
                _loc_2.put(_loc_4,  _loc_8);
                _loc_9 = _loc_2.get(_loc_4) * _loc_5.cash;
                _loc_1 = _loc_1 + _loc_9;
            }
            return _loc_1;
        }//end

        public int  totalCost ()
        {
            Item _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_1 =0;
            Dictionary _loc_2 =new Dictionary ();
            boolean _loc_3 =false ;
            for (_loc_5 in this.m_keys)
            {

                _loc_4 = Global.gameSettings().getItemByName(_loc_5);
                _loc_6 = Global.player.inventory.getItemCount(_loc_4);
                _loc_7 = this.m_keys.get(_loc_5);
                _loc_8 = Math.max(0, _loc_7 - _loc_6);
                _loc_2.put(_loc_5,  _loc_8);
                _loc_9 = _loc_2.get(_loc_5) * _loc_4.cash;
                _loc_1 = _loc_1 + _loc_9;
            }
            if (this.progress >= this.threshold)
            {
                return _loc_1;
            }
            return Math.floor(_loc_1 * (1 - this.discount));
        }//end

        public int  amountNeeded ()
        {
            int _loc_4 =0;
            String _loc_5 =null ;
            Item _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_1 =0;
            Dictionary _loc_2 =new Dictionary ();
            boolean _loc_3 =false ;
            for (_loc_5 in this.m_keys)
            {

                _loc_6 = Global.gameSettings().getItemByName(_loc_5);
                _loc_7 = Global.player.inventory.getItemCount(_loc_6);
                _loc_8 = this.m_keys.get(_loc_5);
                _loc_4 = _loc_4 + Math.max(0, _loc_8 - _loc_7);
            }
            return _loc_4;
        }//end

        public int  totalAmount ()
        {
            String _loc_4 =null ;
            Item _loc_5 =null ;
            Dictionary _loc_1 =new Dictionary ();
            boolean _loc_2 =false ;
            int _loc_3 =0;
            for (_loc_4 in this.m_keys)
            {

                _loc_5 = Global.gameSettings().getItemByName(_loc_4);
                _loc_3 = _loc_3 + uint(this.m_keys.get(_loc_4));
            }
            return _loc_3;
        }//end

        public boolean  buyAll ()
        {
            Item _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            _loc_1 = this.totalCost ;
            Dictionary _loc_2 =new Dictionary ();
            boolean _loc_3 =false ;
            if (Global.player.canBuyCash(_loc_1))
            {
                if (this.showBuyAllCost && this.progress < this.threshold)
                {
                    GameTransactionManager.addTransaction(new TBuyItemSet(this.m_keys, _loc_1, this.m_name, this.m_unlockItemName));
                    _loc_2 = null;
                    return _loc_3;
                }
                for (_loc_5 in this.m_keys)
                {

                    _loc_4 = Global.gameSettings().getItemByName(_loc_5);
                    _loc_6 = Global.player.inventory.getItemCount(_loc_4);
                    _loc_7 = this.m_keys.get(_loc_5);
                    _loc_8 = Math.max(0, _loc_7 - _loc_6);
                    _loc_2.put(_loc_5,  _loc_8);
                    if (_loc_2.get(_loc_5) > 0)
                    {
                        _loc_4 = Global.gameSettings().getItemByName(_loc_5);
                        GameTransactionManager.addTransaction(new TBuyItem(_loc_4.name, _loc_2.get(_loc_5), this.m_unlockItemName));
                    }
                }
                if (this.m_directUnlock)
                {
                    _loc_3 = this.unlockGate();
                }
                if (this.m_dialog)
                {
                    this.m_dialog.close();
                }
                this.m_dialog = null;
            }
            _loc_2 = null;
            return _loc_3;
        }//end

        public int  keyProgress (String param1 )
        {
            return 0;
        }//end

        public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            return;
        }//end

        protected void  updateItemArrowState ()
        {
            ConstructionSite _loc_2 =null ;
            _loc_1 =Global.world.getObjectsByClass(ConstructionSite );
            for each (_loc_2 in _loc_1)
            {

                _loc_2.forceUpdateArrow();
            }
            return;
        }//end

        public String  getSeenFlag (String param1 ,String param2 )
        {
            return "buyout_" + param1 + "_" + param2;
        }//end

    }

