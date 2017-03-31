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
import Modules.workers.*;


    public class TrainTrip
    {
        private int m_beginTime ;
        private int m_duration ;
        private String m_transactionType ;
        private boolean m_isWelcomeTrip =false ;
        private String m_itemName ;
        private Item m_item ;
        private String m_commodityType ;
        private int m_commodityAmount ;
        private int m_coins ;
        private int m_maxStops ;
        private Vector<String> m_stopIds;

        public  TrainTrip ()
        {
            return;
        }//end

        public int  beginTime ()
        {
            return this.m_beginTime;
        }//end

        public void  beginTime (int param1 )
        {
            this.m_beginTime = param1;
            return;
        }//end

        public int  duration ()
        {
            return this.m_duration;
        }//end

        public void  duration (int param1 )
        {
            this.m_duration = param1;
            return;
        }//end

        public int  timeLeft ()
        {
            return Math.max(0, this.m_beginTime + this.m_duration - GlobalEngine.getTimer() / 1000);
        }//end

        public String  transactionType ()
        {
            return this.m_transactionType;
        }//end

        public void  transactionType (String param1 )
        {
            this.m_transactionType = param1;
            return;
        }//end

        public boolean  isWelcomeTrip ()
        {
            return this.m_isWelcomeTrip;
        }//end

        public void  isWelcomeTrip (boolean param1 )
        {
            this.m_isWelcomeTrip = param1;
            return;
        }//end

        public String  itemName ()
        {
            return this.m_itemName;
        }//end

        public void  itemName (String param1 )
        {
            this.m_itemName = param1;
            this.m_item = Global.gameSettings().getItemByName(this.m_itemName);
            return;
        }//end

        public Item  item ()
        {
            return this.m_item;
        }//end

        public String  commodityType ()
        {
            return this.m_commodityType;
        }//end

        public void  commodityType (String param1 )
        {
            this.m_commodityType = param1;
            return;
        }//end

        public int  commodityAmount ()
        {
            return this.m_commodityAmount;
        }//end

        public void  commodityAmount (int param1 )
        {
            this.m_commodityAmount = param1;
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

        public int  maxStops ()
        {
            return this.m_maxStops;
        }//end

        public void  maxStops (int param1 )
        {
            this.m_maxStops = param1;
            return;
        }//end

        public String Vector  stopIds ().<>
        {
            return this.m_stopIds;
        }//end

        public void  loadFromWorker (TrainWorkers param1 )
        {
            this.itemName = param1.itemName;
            this.m_duration = this.m_item.trainTripTime;
            this.m_beginTime = param1.timeSent;
            this.m_transactionType = param1.operation;
            this.m_commodityType = param1.commodityType;
            this.m_commodityAmount = param1.commodity;
            this.m_coins = param1.coins;
            this.m_maxStops = param1.maxWorkers;
            this.m_stopIds = param1.getWorkerIds();
            return;
        }//end

    }


