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

import Classes.*;
    public class TrainOrder extends AbstractOrder
    {
        protected boolean m_autoComplete =false ;
        protected boolean m_isFakeOrder =false ;
        protected String m_customPickUrl ;
        public static  String WELCOME_TRAIN_ID ="-2";
        public static  String AMBIENT_TRAIN_ID ="-3";
        public static  String BUY ="buy";
        public static  String SELL ="sell";
        public static  String NON_STOP ="nonStop";
        public static  String ORDER_ACTION ="orderAction";
        public static  String COMMODITY_TYPE ="orderCommodity";
        public static  String AMOUNT_PROPOSED ="amountProposed";
        public static  String AMOUNT_FINAL ="amountFinal";
        public static  String TRAIN_ITEM_NAME ="trainItemName";
        public static  double DEFAULT_PRICE =50;

        public  TrainOrder (String param1 ,String param2 ,String param3 =null ,String param4 =null ,int param5 =0,String param6 ="",String param7 =null ,String param8 =null )
        {
            super(param1, param2, OrderType.TRAIN, param7);
            this.setAction(param3);
            this.setCommodityType(param4);
            this.setAmountProposed(param5);
            setState(OrderStates.PENDING);
            this.setTrainItemName(param6);
            this.m_customPickUrl = param8;
            return;
        }//end

        public void  autoComplete (boolean param1 )
        {
            this.m_autoComplete = param1;
            return;
        }//end

        public boolean  autoComplete ()
        {
            return this.m_autoComplete;
        }//end

        public void  fakeTrainOrder (boolean param1 )
        {
            this.m_isFakeOrder = param1;
            return;
        }//end

        public boolean  fakeTrainOrder ()
        {
            return this.m_isFakeOrder;
        }//end

        public String  customPickUrl ()
        {
            return this.m_customPickUrl;
        }//end

        public boolean  baseOrder ()
        {
            return this.getRecipientID() == Player.FAKE_USER_ID_STRING;
        }//end

        public int  getCoinCostProposed ()
        {
            return Math.floor(m_params.get(AMOUNT_PROPOSED) * getCostPerCommodity(m_params.get(COMMODITY_TYPE), this.getTrainItemName(), this.getAction()));
        }//end

        public int  getCoinCostFinal ()
        {
            return Math.floor(m_params.get(AMOUNT_FINAL) * getCostPerCommodity(m_params.get(COMMODITY_TYPE), this.getTrainItemName(), this.getAction()));
        }//end

        public String  getAction ()
        {
            return m_params.get(ORDER_ACTION);
        }//end

        public String  getCommodityType ()
        {
            return m_params.get(COMMODITY_TYPE);
        }//end

        public int  getAmountProposed ()
        {
            return m_params.get(AMOUNT_PROPOSED);
        }//end

        public void  setAmountProposed (int param1 )
        {
            m_params.put(AMOUNT_PROPOSED,  param1);
            return;
        }//end

        public void  setAction (String param1 )
        {
            m_params.put(ORDER_ACTION,  param1);
            return;
        }//end

        public void  setCommodityType (String param1 )
        {
            m_params.put(COMMODITY_TYPE,  param1);
            return;
        }//end

        public int  getAmountFinal ()
        {
            return m_params.get(AMOUNT_FINAL);
        }//end

        public void  setAmountFinal (int param1 )
        {
            m_params.put(AMOUNT_FINAL,  param1);
            return;
        }//end

        public void  setTrainItemName (String param1 )
        {
            m_params.put(TRAIN_ITEM_NAME,  param1);
            return;
        }//end

        public String  getTrainItemName ()
        {
            return m_params.get(TRAIN_ITEM_NAME);
        }//end

        public boolean  hasExpired ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.m_isFakeOrder)
            {
                return true;
            }
            _loc_1 = Global.gameSettings().getItemByName(this.getTrainItemName());
            if (_loc_1)
            {
                _loc_2 = uint(GlobalEngine.getTimer() / 1000);
                _loc_3 = _loc_1.trainTripTime;
                return _loc_2 - getTimeSent() >= _loc_3;
            }
            return true;
        }//end

         public Object  getParams ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put(SENDER_ID,  m_params.get(SENDER_ID));
            _loc_1.put(RECIPIENT_ID,  m_params.get(RECIPIENT_ID));
            _loc_1.put(TIME_SENT,  m_params.get(TIME_SENT));
            _loc_1.put(AMOUNT_PROPOSED,  m_params.get(AMOUNT_PROPOSED));
            _loc_1.put(AMOUNT_FINAL,  m_params.get(AMOUNT_FINAL));
            _loc_1.put(COMMODITY_TYPE,  m_params.get(COMMODITY_TYPE));
            _loc_1.put(ORDER_ACTION,  m_params.get(ORDER_ACTION));
            _loc_1.put(ORDER_TYPE,  m_params.get(ORDER_TYPE));
            _loc_1.put(TRAIN_ITEM_NAME,  m_params.get(TRAIN_ITEM_NAME));
            return _loc_1;
        }//end

         public boolean  equals (AbstractOrder param1 )
        {
            TrainOrder _loc_2 =null ;
            if (param1 instanceof TrainOrder)
            {
                _loc_2 =(TrainOrder) param1;
                return super.equals(param1) && this.fakeTrainOrder == _loc_2.fakeTrainOrder;
            }
            return false;
        }//end

        public static double  getCostPerCommodity (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = Global.gameSettings().getItemByName(param2);
            _loc_5 = Global.gameSettings().getCommodityXMLByName(param1);
            _loc_6 = DEFAULT_PRICE;
            switch(param3)
            {
                case BUY:
                {
                    break;
                }
                case SELL:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_5)
            {
            }
            return _loc_6;
        }//end

        public static int  getCostPerCommodityFakeBuy ()
        {
            return Global.gameSettings().getInt("trainOrderFakeBuyCostPerCommodity", 60);
        }//end

        public static int  getCostPerCommodityFakeSell ()
        {
            return Global.gameSettings().getInt("trainOrderFakeSellCostPerCommodity", 40);
        }//end

    }



