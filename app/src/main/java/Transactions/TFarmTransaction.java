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

import Classes.util.*;
import Engine.Transactions.*;
//import flash.utils.*;
import plugin.*;

    public class TFarmTransaction extends Transaction
    {
        protected int m_expectedEnergy ;
        protected int m_clientEnqueueTime =0;
        public static Object m_gamedata ;
        public static Object m_pushdata ;
        public static Object m_statdata ;
        public static Object m_rolldata ;

        public  TFarmTransaction ()
        {
            funName = "TFarmTransaction";
            return;
        }//end

        protected void  beforeAmfComplete (Object param1 )
        {
            m_pushdata = null;
            m_gamedata = null;
            m_statdata = null;
            m_rolldata = null;
            if (param1 && param1.metadata)
            {
                if (param1.metadata.gamedata)
                {
                    m_gamedata = param1.metadata.gamedata;
                }
                if (param1.metadata.pushdata)
                {
                    m_pushdata = param1.metadata.pushdata;
                }
                if (param1.metadata.statdata)
                {
                    m_statdata = param1.metadata.statdata;
                    m_statdata.put("transaction",  getQualifiedClassName(this));
                    printStats();
                }
                if (param1.metadata.rolldata)
                {
                    m_rolldata = param1.metadata.rolldata;
                    m_rolldata.put("transaction",  getQualifiedClassName(this));
                    printRolls();
                }
            }
            return;
        }//end

        final  protected void  onAmfComplete (Object param1 )
        {
            this.beforeAmfComplete(param1);
            super.onAmfComplete(param1);
            _loc_2 = TFarmTransaction.gamedata;
            if (_loc_2 != null)
            {
            }
            return;
        }//end

        public void  onAdd ()
        {
            this.m_expectedEnergy = Global.player.energy;
            return;
        }//end

         public void  preAddTransaction ()
        {
            super.preAddTransaction();
            this.m_clientEnqueueTime = DateUtil.getUnixTime();
            return;
        }//end

        public static Object  gamedata ()
        {
            return m_gamedata;
        }//end

        public static Object  pushdata ()
        {
            return m_pushdata;
        }//end

        public static Object  statdata ()
        {
            return m_statdata;
        }//end

        public static Object  rolldata ()
        {
            return m_rolldata;
        }//end

        public static void  printRolls ()
        {
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_1 = ConsoleStub.getInstance();
            if (_loc_1 != null)
            {
                _loc_1.channel("Rolls", "[" + rolldata["transaction"] + "]", 3);
                _loc_2 = false;
                if (Global.player.rollCounter - rolldata.get("rollCounter"))
                {
                    _loc_1.channel("Rolls", "[RollCounter] c: " + Global.player.rollCounter + " / s: " + rolldata["rollCounter"], 3);
                    _loc_2 = true;
                }
                for(int i0 = 0; i0 < Global.player.rollCounterMap.size(); i0++) 
                {
                		_loc_3 = Global.player.rollCounterMap.get(i0);

                    _loc_4 = Global.player.rollCounterMap.get(_loc_3);
                    _loc_5 = rolldata.get("rollCounterMap").get(_loc_3);
                    if (_loc_4 != _loc_5)
                    {
                        _loc_1.channel("Rolls", "[RollCounterMap][" + _loc_3 + "] c: " + _loc_4 + " / s: " + _loc_5, 3);
                        _loc_2 = true;
                    }
                }
                if (!_loc_2)
                {
                    _loc_1.channel("Rolls", "No differences were found!", 3);
                }
            }
            return;
        }//end

        public static void  printStats ()
        {
            _loc_1 = ConsoleStub.getInstance();
            if (_loc_1 != null)
            {
                _loc_1.channel("Stats", "[" + statdata["transaction"] + "]", 3);
                _loc_1.channel("Stats", "          " + commify(statdata["gold"]) + (Global.player.gold - statdata["gold"] ? ("*") : (" ")) + "      |      " + commify(statdata["cash"]) + (Global.player.cash - statdata["cash"] ? ("*") : (" ")) + "      |      " + commify(statdata["energy"]) + (Global.player.energy - statdata["energy"] ? ("*") : (" ")) + "      |      " + commify(statdata.get("premiumGoods")) + "   /   " + commify(statdata.get("goods")) + "      |      " + commify(statdata.get("xp")) + (Global.player.xp - statdata.get("xp") ? ("*") : (" ")) + "      |      " + commify(statdata.get("level")) + (Global.player.level - statdata.get("level") ? ("*") : (" ")), 3);
            }
            return;
        }//end

        private static String  commify (double param1 )
        {
            double _loc_4 =0;
            _loc_2 = param1.toString ();
            Array _loc_3 =new Array();
            _loc_5 = _loc_2.length ;
            while (_loc_5 > 0)
            {

                _loc_4 = Math.max(_loc_5 - 3, 0);
                _loc_3.unshift(_loc_2.slice(_loc_4, _loc_5));
                _loc_5 = _loc_4;
            }
            return _loc_3.join(",");
        }//end

    }



