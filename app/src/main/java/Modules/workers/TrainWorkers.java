package Modules.workers;

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
import Classes.inventory.*;
import Classes.util.*;
import Modules.trains.*;

    public class TrainWorkers extends Workers
    {
        private int m_xp =0;
        private int m_coins =0;
        private int m_commodity =0;
        private boolean m_autoAccept =false ;
        private boolean m_isWelcomeWorker =false ;
        public static  String OP_BUY ="buy";
        public static  String OP_SELL ="sell";
        private static  String TRAIN_NAME ="trainName";
        private static  String OPERATION ="operation";
        private static  String COMMODITY_NAME ="commodityName";
        private static  String TIME_SENT ="timeSent";
        private static  String PAYOUT_BONUS_UNLOCK_FLAG ="cux_train_bonus";

        public  TrainWorkers ()
        {
            this.recalculateRewards();
            return;
        }//end

        public int  xp ()
        {
            return this.m_xp;
        }//end

        public void  xp (int param1 )
        {
            this.m_xp = param1;
            return;
        }//end

        public int  coins ()
        {
            return this.m_coins;
        }//end

        public void  coins (int param1 )
        {
            this.m_coins = param1;
            return;
        }//end

        public int  commodity ()
        {
            return this.m_commodity;
        }//end

        public void  commodity (int param1 )
        {
            this.m_commodity = param1;
            return;
        }//end

        public String  commodityType ()
        {
            return getAttribute(COMMODITY_NAME, Commodities.GOODS_COMMODITY);
        }//end

        public void  commodityType (String param1 )
        {
            setAttribute(COMMODITY_NAME, param1);
            return;
        }//end

        public String  itemName ()
        {
            return getAttribute(TRAIN_NAME, "");
        }//end

        public void  itemName (String param1 )
        {
            setAttribute(TRAIN_NAME, param1);
            this.recalculateRewards();
            return;
        }//end

        public String  operation ()
        {
            return getAttribute(OPERATION, OP_BUY);
        }//end

        public void  operation (String param1 )
        {
            setAttribute(OPERATION, param1);
            this.recalculateRewards();
            return;
        }//end

        public int  timeSent ()
        {
            return getAttribute(TIME_SENT, 0);
        }//end

        public void  timeSent (int param1 )
        {
            setAttribute(TIME_SENT, param1);
            return;
        }//end

        private void  recalculateRewards ()
        {
            String _loc_2 =null ;
            this.m_xp = 0;
            this.m_coins = 0;
            this.m_commodity = 0;
            _loc_1 =Global.gameSettings().getItemByName(this.itemName );
            if (_loc_1)
            {
                _loc_2 = _loc_1.trainPayout;
                switch(this.operation)
                {
                    case OP_BUY:
                    {
                        this.m_commodity = Math.ceil(Global.gameSettings().getTieredInt(_loc_2, getWorkerCount()) * this.getPayoutBonus());
                        break;
                    }
                    case OP_SELL:
                    {
                        this.m_coins = Math.ceil(Global.gameSettings().getTieredInt(_loc_2, getWorkerCount()) * this.getPayoutBonus());
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        private double  getPayoutBonus ()
        {
            double _loc_1 =1;
            _loc_2 =Global.player.getSeenFlag(PAYOUT_BONUS_UNLOCK_FLAG )|| Global.player.getSeenSessionFlag(PAYOUT_BONUS_UNLOCK_FLAG);
            if (getWorkerCount() > 0 && _loc_2)
            {
                _loc_1 = Global.gameSettings().getNumber("trainBonusMult", 1.1);
            }
            return _loc_1;
        }//end

         public int  maxWorkers ()
        {
            _loc_1 =Global.gameSettings().getItemByName(this.itemName );
            int _loc_2 =0;
            if (_loc_1.workers)
            {
                _loc_2 = _loc_1.workers.amount;
            }
            return _loc_2;
        }//end

         public int  addWorker (String param1 ,boolean param2 =false )
        {
            _loc_3 = super.addWorker(param1,param2);
            this.recalculateRewards();
            return _loc_3;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.recalculateRewards();
            return;
        }//end

        public void  speedUp ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            _loc_1 =Global.gameSettings().getItemByName(this.itemName );
            if (_loc_1)
            {
                _loc_2 = uint(GlobalEngine.getTimer() / 1000);
                _loc_3 = _loc_1.trainTripTime;
                this.timeSent = this.timeSent - (_loc_3 - (_loc_2 - this.timeSent));
            }
            GameTransactionManager.addTransaction(new TSpeedUpTrain());
            return;
        }//end

    }



