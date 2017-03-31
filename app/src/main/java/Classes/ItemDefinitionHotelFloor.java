package Classes;

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

//import flash.geom.*;
    public class ItemDefinitionHotelFloor
    {
        protected int m_floorID ;
        protected String m_imageURL ;
        protected String m_rewardImageURL ;
        protected int m_roomCount ;
        protected int m_upgradeCost ;
        protected int m_upgradeCash ;
        protected int m_guestUpgradeBonus ;
        protected String m_randomModifierTable ;
        protected int m_socialBusinessHarvestBonus ;
        protected Array m_windows ;

        public  ItemDefinitionHotelFloor ()
        {
            return;
        }//end

        public void  init (XML param1 )
        {
            XML _loc_2 =null ;
            Rectangle _loc_3 =null ;
            this.m_floorID = param1.@id.length() > 0 ? (int(param1.@id)) : (0);
            this.m_imageURL = param1.@image.length() > 0 ? (String(param1.@image)) : ("");
            this.m_rewardImageURL = param1.@rewardimage.length() > 0 ? (String(param1.@rewardimage)) : ("");
            this.m_roomCount = param1.@roomCount.length() > 0 ? (int(param1.@roomCount)) : (0);
            this.m_upgradeCost = param1.@upgradeCost.length() > 0 ? (int(param1.@upgradeCost)) : (0);
            this.m_upgradeCash = param1.@upgradeCash.length() > 0 ? (int(param1.@upgradeCash)) : (0);
            this.m_guestUpgradeBonus = param1.@guestUpgradeBonus.length() > 0 ? (int(param1.@guestUpgradeBonus)) : (0);
            this.m_randomModifierTable = String(param1.@randomModifier);
            this.m_socialBusinessHarvestBonus = param1.@socialBusinessHarvestBonus.length() > 0 ? (int(param1.@socialBusinessHarvestBonus)) : (0);
            this.m_windows = new Array();
            for(int i0 = 0; i0 < param1.windows.window.size(); i0++) 
            {
            		_loc_2 = param1.windows.window.get(i0);

                _loc_3 = new Rectangle(Number(_loc_2.@x), Number(_loc_2.@y), Number(_loc_2.@width), Number(_loc_2.@height));
                this.m_windows.push(_loc_3);
            }
            return;
        }//end

        public int  floorID ()
        {
            return this.m_floorID;
        }//end

        public String  imageURL ()
        {
            return this.m_imageURL;
        }//end

        public String  rewardImageURL ()
        {
            return this.m_rewardImageURL;
        }//end

        public int  roomCount ()
        {
            return this.m_roomCount;
        }//end

        public int  upgradeCost ()
        {
            return this.m_upgradeCost;
        }//end

        public int  upgradeCash ()
        {
            return this.m_upgradeCash;
        }//end

        public int  guestUpgradeBonus ()
        {
            return this.m_guestUpgradeBonus;
        }//end

        public String  randomModifierTable ()
        {
            return this.m_randomModifierTable;
        }//end

        public int  socialBusinessHarvestBonus ()
        {
            return this.m_socialBusinessHarvestBonus;
        }//end

        public Array  windows ()
        {
            return this.m_windows;
        }//end

        public static Array  initHotelFloors (XML param1 )
        {
            XML _loc_4 =null ;
            ItemDefinitionHotelFloor _loc_5 =null ;
            Array _loc_2 =new Array ();
            if (!param1.hasOwnProperty("floors"))
            {
                return _loc_2;
            }
            _loc_3 = param1.floors.children().length();
            for(int i0 = 0; i0 < param1.floors.floor.size(); i0++) 
            {
            		_loc_4 = param1.floors.floor.get(i0);

                _loc_5 = new ItemDefinitionHotelFloor;
                _loc_5.init(_loc_4);
                _loc_2.push(_loc_5);
            }
            return _loc_2;
        }//end

    }



