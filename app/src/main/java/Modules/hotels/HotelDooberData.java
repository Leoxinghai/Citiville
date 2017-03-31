package Modules.hotels;

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
import Classes.doobers.*;

    public class HotelDooberData
    {
        private String m_type ="";
        private int m_amount =0;
        private String m_itemtype ="";

        public  HotelDooberData (String param1 ,int param2 ,String param3 )
        {
            this.m_type = param1;
            this.m_amount = param2;
            this.m_itemtype = param3;
            return;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public int  amount ()
        {
            return this.m_amount;
        }//end

        public String  itemtype ()
        {
            return this.m_itemtype;
        }//end

        public boolean  equals (HotelDooberData param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_type == param1.type && this.m_amount == param1.amount && this.m_itemtype == param1.itemtype)
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public String  pictureURL ()
        {
            Item _loc_4 =null ;
            if (this.type == Doober.DOOBER_ITEM)
            {
                _loc_4 = Global.gameSettings().getItemByName(this.itemtype);
                if (_loc_4 == null)
                {
                    return null;
                }
                return _loc_4.iconRelative;
            }
            _loc_1 =Global.gameSettings().getDooberFromType(this.type ,this.amount );
            _loc_2 =Global.gameSettings().getItemByName(_loc_1 );
            _loc_3 = (String)(_loc_2.rawImageXml.@url);
            return _loc_3;
        }//end

        public String  rewardText ()
        {
            Item _loc_1 =null ;
            if (this.type == Doober.DOOBER_ITEM)
            {
                _loc_1 = Global.gameSettings().getItemByName(this.itemtype);
                return _loc_1.localizedName;
            }
            if (this.type == Doober.DOOBER_COIN)
            {
                return ZLoc.t("Dialogs", "NumCoins", {num:this.amount.toString()});
            }
            if (this.type == Doober.DOOBER_XP)
            {
                return ZLoc.t("Dialogs", "NumXP", {num:this.amount.toString()});
            }
            if (this.type == Doober.DOOBER_GOODS)
            {
                return ZLoc.t("Dialogs", "NumGoods", {num:this.amount.toString()});
            }
            if (this.type == Doober.DOOBER_ENERGY)
            {
                return ZLoc.t("Dialogs", "NumEnergy", {num:this.amount.toString()});
            }
            return this.amount.toString();
        }//end

    }



