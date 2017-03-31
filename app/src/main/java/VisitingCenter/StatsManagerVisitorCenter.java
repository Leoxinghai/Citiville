package VisitingCenter;

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
    public class StatsManagerVisitorCenter
    {
        private static int m_num_residences ;
        private static int m_num_businesses ;
        private static int m_num_landmarks ;
        private static int m_num_municipals ;
        private static int m_num_farmPlots ;
        private static int m_num_empty_franchise ;
        private static int m_area ;
        private static int m_roads ;
        private static double m_market_value ;
        private static double m_wilderness ;
        private static boolean initialized =false ;

        public  StatsManagerVisitorCenter ()
        {
            return;
        }//end

        public static int  residences ()
        {
            return m_num_residences;
        }//end

        public static String  businesses ()
        {
            return m_num_businesses + "";
        }//end

        public static String  landmarks ()
        {
            return m_num_landmarks + "";
        }//end

        public static String  municipals ()
        {
            return m_num_municipals + "";
        }//end

        public static String  farmPlots ()
        {
            return m_num_farmPlots + "";
        }//end

        public static String  empty_franchise ()
        {
            return m_num_empty_franchise + "";
        }//end

        public static int  area ()
        {
            return m_area;
        }//end

        public static String  wilderness ()
        {
            return m_wilderness + "";
        }//end

        public static int  roads ()
        {
            return m_roads;
        }//end

        public static String  marketValue ()
        {
            return m_market_value + "";
        }//end

        public static void  generateData ()
        {
            ItemInstance _loc_2 =null ;
            m_num_residences = 0;
            m_num_businesses = 0;
            m_num_landmarks = 0;
            m_num_municipals = 0;
            m_num_farmPlots = 0;
            m_num_empty_franchise = 0;
            m_area = 0;
            m_roads = 0;
            m_market_value = 0;
            m_wilderness = 0;
            initialized = true;
            _loc_1 = Global.world.getObjects();
            int _loc_6 ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2 is Residence)
                {
                    m_num_residences++;
                }
                else if (_loc_2 is Business)
                {
                    m_num_businesses++;
                }
                else if (_loc_2 is Landmark)
                {
                    m_num_landmarks++;
                }
                else if (_loc_2 is Municipal)
                {
                    m_num_municipals++;
                }
                else if (_loc_2.getItem().type == "plot")
                {

                    m_num_farmPlots++;
                }
                else if (_loc_2 is LotSite)
                {

                    m_num_empty_franchise++;
                }
                else if (_loc_2 is Wilderness)
                {

                    m_wilderness++;
                }
                else if (_loc_2 is Road)
                {

                    m_roads++;
                }
                if (_loc_2 is Residence || _loc_2 is Business || _loc_2 is Landmark || _loc_2 is Municipal || _loc_2 is Plot || _loc_2 is LotSite)
                {
                    m_area = m_area + _loc_2.sizeX * _loc_2.sizeY;
                    m_market_value = m_market_value + (((MapResource)_loc_2).getItem().cost as Number);
                    m_market_value = m_market_value + (((MapResource)_loc_2).getItem().cash as Number) * 200;
                }
            }
            return;
        }//end

    }




