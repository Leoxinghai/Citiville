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

    public class ItemDefinitionHotel
    {
        protected int m_customerCapacity ;
        protected int m_guestFillCost ;
        protected int m_maxFloors ;
        protected Array m_floors ;

        public  ItemDefinitionHotel ()
        {
            return;
        }//end

        public void  init (XML param1 )
        {
            this.m_customerCapacity = param1.customerCapacity.length() > 0 ? (int(param1.customerCapacity)) : (0);
            this.m_guestFillCost = param1.guestFillCost.length() > 0 ? (int(param1.guestFillCost)) : (0);
            this.m_maxFloors = param1.maxFloors.length() > 0 ? (int(param1.maxFloors)) : (0);
            this.m_floors = ItemDefinitionHotelFloor.initHotelFloors(param1);
            return;
        }//end

        public int  customerCapacity ()
        {
            return this.m_customerCapacity;
        }//end

        public int  guestFillCost ()
        {
            return this.m_guestFillCost;
        }//end

        public int  maxFloors ()
        {
            return this.m_maxFloors;
        }//end

        public Array  floors ()
        {
            return this.m_floors;
        }//end

        public ItemDefinitionHotelFloor  getFloor (int param1 )
        {
            if (param1 >= 0 && param1 <= this.m_maxFloors)
            {
                return (ItemDefinitionHotelFloor)this.m_floors.get(param1);
            }
            return null;
        }//end

    }



