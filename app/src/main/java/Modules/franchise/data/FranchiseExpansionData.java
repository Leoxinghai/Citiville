package Modules.franchise.data;

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

import Classes.orders.*;
import Modules.franchise.util.*;

    public class FranchiseExpansionData
    {
        private String m_franchiseType ;
        private String m_locationUid ;
        private double m_starRating ;
        private double m_commodityLeft ;
        private double m_commodityMax ;
        private double m_customersServed ;
        private double m_moneyCollected ;
        private double m_objectId ;
        private double m_timeLastCollected ;
        private double m_timeLastOperated ;
        private double m_timeLastSupplied ;

        public  FranchiseExpansionData (String param1 ,String param2 ,double param3 ,double param4 ,double param5 ,double param6 ,double param7 ,double param8 ,double param9 ,double param10 ,double param11 )
        {
            this.m_franchiseType = param1;
            this.m_locationUid = param2;
            this.m_starRating = param3;
            this.m_commodityLeft = param4;
            this.m_commodityMax = param5;
            this.m_customersServed = param6;
            this.m_moneyCollected = param7;
            this.m_objectId = param8;
            this.m_timeLastCollected = param9;
            this.m_timeLastOperated = param10;
            this.m_timeLastSupplied = param11;
            return;
        }//end

        public String  franchiseType ()
        {
            return this.m_franchiseType;
        }//end

        public String  locationUid ()
        {
            return this.m_locationUid;
        }//end

        public double  starRating ()
        {
            return this.m_starRating;
        }//end

        public double  commodityLeft ()
        {
            return this.m_commodityLeft;
        }//end

        public double  commodityMax ()
        {
            return this.m_commodityMax;
        }//end

        public double  customersServed ()
        {
            return this.m_customersServed;
        }//end

        public double  moneyCollected ()
        {
            return this.m_moneyCollected;
        }//end

        public double  objectId ()
        {
            return this.m_objectId;
        }//end

        public double  timeLastCollected ()
        {
            return this.m_timeLastCollected;
        }//end

        public double  timeLastOperated ()
        {
            return this.m_timeLastOperated;
        }//end

        public double  timeLastSupplied ()
        {
            return this.m_timeLastSupplied;
        }//end

        public void  timeLastSupplied (double param1 )
        {
            this.m_timeLastSupplied = param1;
            return;
        }//end

        public boolean  isOpen ()
        {
            return this.m_timeLastOperated > 0;
        }//end

        public static FranchiseExpansionData  loadObject (Object param1 )
        {
            return loadObjectWithTypeAndLocation(null, null, param1);
        }//end

        public static FranchiseExpansionData  loadObjectWithTypeAndLocation (String param1 ,String param2 ,Object param3 )
        {
            int _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            double _loc_13 =0;
            double _loc_14 =0;
            FranchiseExpansionData _loc_4 =null ;
            if (param3)
            {
                _loc_5 = param3.get("star_rating");
                _loc_6 = FranchiseUtil.formatServerStarRating(_loc_5);
                _loc_7 = param3.get("commodity_left");
                _loc_8 = param3.hasOwnProperty("commodity_max") ? (param3.get("commodity_max")) : (1);
                _loc_9 = param3.get("customers_served");
                _loc_10 = param3.get("money_collected");
                _loc_11 = param3.get("obj_id");
                _loc_12 = param3.get("time_last_collected") ? (param3.get("time_last_collected")) : (0);
                _loc_13 = param3.get("time_last_operated") ? (param3.get("time_last_operated")) : (0);
                _loc_14 = param3.get("time_last_supplied") ? (param3.get("time_last_supplied")) : (0);
                _loc_4 = new FranchiseExpansionData(param1, param2, _loc_6, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13, _loc_14);
            }
            return _loc_4;
        }//end

        public static FranchiseExpansionData  loadDummyObject (double param1 )
        {
            return new FranchiseExpansionData(null, null, param1, 0, 0, 0, 0, 0, 0, 0, 0);
        }//end

        public static FranchiseExpansionData  loadPendingObject (LotOrder param1 )
        {
            FranchiseExpansionData _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = new FranchiseExpansionData(param1.getResourceType(), param1.getRecipientID(), 0, 1, 0, 0, 0, 0, 0, 0, 0);
            }
            return _loc_2;
        }//end

        public static FranchiseExpansionData  loadCitySamObject (String param1 ,int param2 )
        {
            return new FranchiseExpansionData(param1, param2.toString(), param2 % 5 + 1, 0, 1, 1337, 10000, 1, 0, 0, 1);
        }//end

        public static FranchiseExpansionData  loadFirstFranchiseOnCitySamMap (String param1 )
        {
            _loc_2 = Math.floor(GlobalEngine.getTimer ()/1000);
            FranchiseExpansionData _loc_3 =new FranchiseExpansionData(param1 ,"-1",1,0,1,0,500,0,_loc_2 ,_loc_2 ,0);
            return _loc_3;
        }//end

    }



