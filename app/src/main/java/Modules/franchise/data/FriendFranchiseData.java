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

import Modules.franchise.util.*;
    public class FriendFranchiseData
    {
        private double m_starRating ;
        private String m_franchiseName ;
        private double m_commodityLeft ;

        public  FriendFranchiseData (double param1 ,String param2 ,double param3 )
        {
            this.m_starRating = param1;
            this.m_franchiseName = param2;
            this.m_commodityLeft = param3;
            return;
        }//end

        public double  starRating ()
        {
            return this.m_starRating;
        }//end

        public String  franchiseName ()
        {
            return this.m_franchiseName;
        }//end

        public double  commodityLeft ()
        {
            return this.m_commodityLeft;
        }//end

        public void  commodityLeft (double param1 )
        {
            this.m_commodityLeft = param1;
            return;
        }//end

        public static FriendFranchiseData  loadObject (Object param1 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            String _loc_5 =null ;
            double _loc_6 =0;
            FriendFranchiseData _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_3 = 0;
                _loc_4 = 0;
                _loc_5 = null;
                if (param1 !=null)
                {
                    _loc_3 = param1.get("star_rating");
                    _loc_4 = param1.get("commodity_left");
                    _loc_5 = param1.get("franchise_name");
                }
                _loc_6 = FranchiseUtil.formatServerStarRating(_loc_3);
                _loc_2 = new FriendFranchiseData(_loc_6, _loc_5, _loc_4);
            }
            return _loc_2;
        }//end

    }



