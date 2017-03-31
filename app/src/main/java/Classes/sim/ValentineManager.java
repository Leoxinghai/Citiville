package Classes.sim;

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
import Classes.orders.*;
import Classes.orders.Valentines2011.*;
import Classes.util.*;
import Display.ValentineUI.*;
import Transactions.*;

    public class ValentineManager
    {
        private static Array m_valentines ;
        private static Array m_admirers ;
        public static boolean m_shownIneligible =false ;

        public  ValentineManager ()
        {
            return;
        }//end

        public static Array  getNewValentines ()
        {
            ValentineOrder _loc_2 =null ;
            ValentineManager.initValentineOrders();
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < ValentineManager.m_valentines.size(); i0++)
            {
            	_loc_2 = ValentineManager.m_valentines.get(i0);

                _loc_1 = _loc_1.concat(_loc_2.getNewCards());
            }
            return _loc_1;
        }//end

        private static void  initValentineOrders ()
        {
            if (ValentineManager.m_valentines == null || ValentineManager.m_valentines.length == 0)
            {
                ValentineManager.m_valentines = Global.world.orderMgr.getOrdersByType(OrderType.VALENTINE_2011);
            }
            return;
        }//end

        private static void  initAdmirers ()
        {
            ValentineOrder _loc_1 =null ;
            Player _loc_2 =null ;
            Admirer _loc_3 =null ;
            ValentineManager.initValentineOrders();
            if (ValentineManager.m_admirers == null || ValentineManager.m_admirers.length == 0)
            {
                ValentineManager.m_admirers = new Array();
                for(int i0 = 0; i0 < ValentineManager.m_valentines.size(); i0++)
                {
                	_loc_1 = ValentineManager.m_valentines.get(i0);

                    _loc_2 = Global.player.findFriendById(_loc_1.getSenderID());
                    if (_loc_2 != null)
                    {
                        _loc_3 = new Admirer(_loc_2.snUser.firstName, _loc_2.snUser.picture, _loc_1.getCardCount(), _loc_1.hasNewCards(), _loc_2.snUser.uid);
                        ValentineManager.m_admirers.push(_loc_3);
                    }
                }
            }
            return;
        }//end

        public static Array  getValentinesFromUID (String param1 )
        {
            _loc_2 =Global.world.orderMgr.getOrdersByUID(param1 ,OrderType.VALENTINE_2011 ,OrderStatus.RECEIVED ).get(0) ;
            return _loc_2.getAllCards();
        }//end

        public static int  getValentineCountFromUID (String param1 )
        {
            _loc_2 =Global.world.orderMgr.getOrdersByUID(param1 ,OrderType.VALENTINE_2011 ,OrderStatus.RECEIVED ).get(0) ;
            return _loc_2.getCardCount();
        }//end

        public static int  getTotalAdmirers ()
        {
            ValentineManager.initValentineOrders();
            return ValentineManager.m_valentines.length;
        }//end

        public static boolean  checkUIDForNewCards (String param1 )
        {
            _loc_2 =Global.world.orderMgr.getOrdersByUID(param1 ,OrderType.VALENTINE_2011 ,OrderStatus.RECEIVED ).get(0) ;
            return _loc_2.hasNewCards();
        }//end

        public static Array  getAdmirers ()
        {
            ValentineManager.initAdmirers();
            return ValentineManager.m_admirers;
        }//end

        public static Admirer  getAdmirerByID (String param1 )
        {
            Admirer _loc_2 =null ;
            ValentineManager.initAdmirers();
            for(int i0 = 0; i0 < ValentineManager.m_admirers.size(); i0++)
            {
            	_loc_2 = ValentineManager.m_admirers.get(i0);

                if (_loc_2.uid == param1)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public static void  sendValentineCard (int param1 ,int param2 ,int param3 ,String param4 )
        {
            FrameManager.navigateTo("valentine.php?lID=" + param1 + "&mID=" + param2 + "&rID=" + param3 + "&msg=" + escape(param4));
            return;
        }//end

        public static int  getTotalCards ()
        {
            Admirer _loc_2 =null ;
            ValentineManager.initAdmirers();
            int _loc_1 =0;
            for(int i0 = 0; i0 < ValentineManager.m_admirers.size(); i0++)
            {
            	_loc_2 = ValentineManager.m_admirers.get(i0);

                _loc_1 = _loc_1 + _loc_2.numCards;
            }
            return _loc_1;
        }//end

        public static void  markCardsAsSeen (String param1 ,Array param2 )
        {
            ValentineCard _loc_4 =null ;
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_4 = param2.get(i0);

                _loc_4.setSeen();
                _loc_3.push(_loc_4.id);
            }
            GameTransactionManager.addTransaction(new TValentineMarkRead(param1, _loc_3));
            return;
        }//end

        public static void  markAllAsSeen (String param1 )
        {
            Admirer _loc_2 =null ;
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            ValentineCard _loc_5 =null ;
            for(int i0 = 0; i0 < ValentineManager.m_admirers.size(); i0++)
            {
            		_loc_2 = ValentineManager.m_admirers.get(i0);

                if (_loc_2.uid == param1)
                {
                    _loc_2.newCard = false;
                    _loc_3 = ValentineManager.getValentinesFromUID(param1);
                    _loc_4 = new Array();
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    	_loc_5 = _loc_3.get(i0);

                        _loc_5.setSeen();
                        if (_loc_5.id.charAt(0) != "i")
                        {
                            _loc_4.push("i" + _loc_5.id);
                        }
                    }
                    GameTransactionManager.addTransaction(new TValentineMarkRead(param1, _loc_4));
                    break;
                }
            }
            return;
        }//end

        public static boolean  checkEligibility ()
        {
            if (Global.player.level < Global.gameSettings().getNumber("vDayMinEligibleLevel", 5))
            {
                return false;
            }
            return true;
        }//end

        public static void  removeValentineCard (String param1 ,String param2 )
        {
            _loc_3 =Global.world.orderMgr.getOrdersByUID(param1 ,OrderType.VALENTINE_2011 ,OrderStatus.RECEIVED ).get(0) ;
            _loc_4 = _loc_3.deleteCard(param2 );
            if (_loc_3.deleteCard(param2) == true)
            {
                if (_loc_3.getCardCount() <= 0)
                {
                    Global.world.orderMgr.removeOrder(_loc_3);
                }
                else
                {
                    Global.world.orderMgr.updateOrder(_loc_3);
                }
                ValentineManager.m_valentines = null;
                ValentineManager.m_admirers = null;
            }
            return;
        }//end

    }



