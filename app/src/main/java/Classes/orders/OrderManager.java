package Classes.orders;

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

//import flash.utils.*;
    public class OrderManager
    {
        private Dictionary m_orders ;

        public  OrderManager ()
        {
            this.m_orders = new Dictionary();
            return;
        }//end

        private boolean  updateOrderParams (String param1 ,String param2 ,String param3 ,String param4 ,Object param5 )
        {
            Object _loc_6 =null ;
            AbstractOrder _loc_7 =null ;
            if (this.m_orders.get(param1))
            {
                if (this.m_orders.get(param1).get(param2))
                {
                    for(int i0 = 0; i0 < this.m_orders.get(param1).get(param2).size(); i0++)
                    {
                    		_loc_6 = this.m_orders.get(param1).get(param2).get(i0);

                        if (_loc_6 instanceof AbstractOrder)
                        {
                            _loc_7 =(AbstractOrder) _loc_6;
                            if (_loc_7.equals(new AbstractOrder(param3, param4, param1, param2)))
                            {
                                _loc_7.updateOrder(param5);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }//end

        public int  addOrdersByType (String param1 ,Object param2 ,boolean param3 =false )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_4 =0;
            if (!this.m_orders.get(param1))
            {
                this.m_orders.put(param1,  new Dictionary());
            }
            for(int i0 = 0; i0 < param2.get(param1).size(); i0++)
            {
            		_loc_5 = param2.get(param1).get(i0);

                if (!this.m_orders.get(param1).get(_loc_5))
                {
                    this.m_orders.get(param1).put(_loc_5,  new Array());
                }
                for(int i0 = 0; i0 < param2.get(param1).get(_loc_5).size(); i0++)
                {
                		_loc_6 = param2.get(param1).get(_loc_5).get(i0);

                    for(int i0 = 0; i0 < param2.get(param1).get(_loc_5).get(_loc_6).size(); i0++)
                    {
                    		_loc_7 = param2.get(param1).get(_loc_5).get(_loc_6).get(i0);

                        _loc_8 = _loc_5 == OrderStatus.RECEIVED ? (Global.player.snUser.uid) : (_loc_7);
                        _loc_9 = _loc_5 == OrderStatus.RECEIVED ? (_loc_7) : (Global.player.snUser.uid);
                        if (param3 || !this.updateOrderParams(param1, _loc_5, _loc_8, _loc_9, param2.get(param1).get(_loc_5).get(_loc_6).get(_loc_7)))
                        {
                            _loc_4 = _loc_4 + AbstractOrder.parseOrder(param1, _loc_5, _loc_6, _loc_7, param2, this.m_orders);
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public void  addOrders (Object param1 ,boolean param2 =false )
        {
            String _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                this.addOrdersByType(_loc_3, param1, param2);
            }
            return;
        }//end

        public void  initializeOrders (Object param1 )
        {
            this.m_orders = new Dictionary();
            this.addOrders(param1, true);
            return;
        }//end

        public Array  getOrders (String param1 ,String param2 ,String param3 =null )
        {
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            AbstractOrder _loc_7 =null ;
            String _loc_8 =null ;
            _loc_4 = this.m_orders.get(param1);
            if (this.m_orders.get(param1))
            {
                _loc_5 = _loc_4.get(param2);
                if (_loc_5)
                {
                    if (!param3)
                    {
                        return _loc_5;
                    }
                    _loc_6 = new Array();
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_7 = _loc_5.get(i0);

                        _loc_8 = _loc_7.getState();
                        if (_loc_7.getState() == param3)
                        {
                            _loc_6.push(_loc_7);
                        }
                    }
                    return _loc_6;
                }
            }
            return new Array();
        }//end

        public Array  getOrdersByType (String param1 )
        {
            Array _loc_4 =null ;
            Array _loc_2 =new Array ();
            _loc_3 = this.m_orders.get(param1);
            if (_loc_3)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_2 = _loc_2.concat(_loc_4);
                }
            }
            return _loc_2;
        }//end

        public Array  getOrdersByStatus (String param1 )
        {
            Dictionary _loc_3 =null ;
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < this.m_orders.size(); i0++)
            {
            		_loc_3 = this.m_orders.get(i0);

                if (_loc_3.get(param1) instanceof Array)
                {
                    _loc_2 = _loc_2.concat(_loc_3.get(param1));
                }
            }
            return _loc_2;
        }//end

        public void  placeOrder (AbstractOrder param1 )
        {
            _loc_2 = param1.getType();
            _loc_3 = param1.getTransmissionStatus();
            if (this.m_orders.get(_loc_2) == null)
            {
                this.m_orders.put(_loc_2,  new Dictionary());
            }
            if (this.m_orders.get(_loc_2).get(_loc_3) == null)
            {
                this.m_orders.get(_loc_2).put(_loc_3,  new Array());
            }
            param1.setTimeSent(uint(GlobalEngine.getTimer() / 1000));
            this.m_orders.get(_loc_2).get(_loc_3).push(param1);
            return;
        }//end

        public Array  getOrdersByUID (String param1 ,String param2 ="",String param3 ="")
        {
            Dictionary _loc_5 =null ;
            AbstractOrder _loc_6 =null ;
            String _loc_7 =null ;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            Array _loc_4 =new Array ();
            for(int i0 = 0; i0 < this.m_orders.size(); i0++)
            {
            		_loc_5 = this.m_orders.get(i0);

                if (_loc_5.get(param3) instanceof Array)
                {
                    for(int i0 = 0; i0 < _loc_5.get(param3).size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(param3).get(i0);

                        _loc_7 = param3 == OrderStatus.RECEIVED ? (_loc_6.getSenderID()) : (_loc_6.getRecipientID());
                        _loc_8 = Boolean(_loc_7 == param1);
                        _loc_9 = param2 ? (Boolean(param2 == _loc_6.getType())) : (true);
                        if (_loc_8 && _loc_9)
                        {
                            _loc_4 = _loc_4.concat(_loc_6);
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public void  updateOrder (AbstractOrder param1 )
        {
            _loc_2 = param1.getType();
            _loc_3 = param1.getTransmissionStatus();
            if (this.m_orders.get(_loc_2) == null || this.m_orders.get(_loc_2).get(_loc_3))
            {
                return;
            }
            int _loc_4 =0;
            while (_loc_4 < this.m_orders.get(_loc_2).get(_loc_3).length())
            {

                if (this.m_orders.get(_loc_2).get(_loc_3).get(_loc_4).getSenderID() == param1.getSenderID())
                {
                    this.m_orders.get(_loc_2).get(_loc_3).put(_loc_4,  param1);
                    break;
                }
                _loc_4++;
            }
            return;
        }//end

        public void  removeOrder (AbstractOrder param1 )
        {
            _loc_2 = param1.getType();
            _loc_3 = param1.getTransmissionStatus();
            if (this.m_orders.get(_loc_2) == null || this.m_orders.get(_loc_2).get(_loc_3) == null)
            {
                return;
            }
            int _loc_4 =0;
            while (_loc_4 < this.m_orders.get(_loc_2).get(_loc_3).length())
            {

                if (this.m_orders.get(_loc_2).get(_loc_3).get(_loc_4).equals(param1))
                {
                    this.m_orders.get(_loc_2).get(_loc_3).splice(_loc_4, 1);
                    break;
                }
                _loc_4++;
            }
            return;
        }//end

        public void  cleanUp ()
        {
            this.m_orders = null;
            this.m_orders = new Dictionary();
            return;
        }//end

    }



