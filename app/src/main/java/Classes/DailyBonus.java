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

    public class DailyBonus
    {
        private int m_day =0;
        private int m_recoveryCash =0;
        private boolean m_isSurprise =false ;
        private int m_coin =0;
        private int m_energy =0;
        private int m_food =0;
        private int m_goods =0;
        private String m_item ="";
        private int m_xp =0;
        private Array m_items ;

        public  DailyBonus (XML param1 )
        {
            this.m_items = new Array();
            this.parseFromXml(param1);
            return;
        }//end

        public int  day ()
        {
            return this.m_day;
        }//end

        public int  recoveryCash ()
        {
            return this.m_recoveryCash;
        }//end

        public boolean  isSurprise ()
        {
            return this.m_isSurprise;
        }//end

        public int  coin ()
        {
            return this.m_coin;
        }//end

        public int  energy ()
        {
            return this.m_energy;
        }//end

        public int  food ()
        {
            return this.m_food;
        }//end

        public int  goods ()
        {
            return this.m_goods;
        }//end

        public String  item ()
        {
            return this.m_item;
        }//end

        public int  xp ()
        {
            return this.m_xp;
        }//end

        public Array  items ()
        {
            return this.m_items;
        }//end

        private void  parseFromXml (XML param1 )
        {
            XML _loc_2 =null ;
            this.m_day = int(Number(param1.@number));
            this.m_recoveryCash = int(Number(param1.@recoveryCash));
            this.m_isSurprise = Boolean(param1.@surprise == "true");
            if (this.m_isSurprise)
            {
                this.m_coin = param1.randomchance.hasOwnProperty("coin") ? (int(Number(param1.randomchance.coin.@amount))) : (0);
                this.m_energy = param1.randomchance.hasOwnProperty("energy") ? (int(Number(param1.randomchance.energy.@amount))) : (0);
                this.m_food = param1.randomchance.hasOwnProperty("food") ? (int(Number(param1.randomchance.food.@amount))) : (0);
                this.m_goods = param1.randomchance.hasOwnProperty("goods") ? (int(Number(param1.randomchance.goods.@amount))) : (0);
                if (param1.randomchance.hasOwnProperty("item"))
                {
                    for(int i0 = 0; i0 < param1.randomchance.item.size(); i0++) 
                    {
                    	_loc_2 = param1.randomchance.item.get(i0);

                        this.m_items.push(String(_loc_2.@name));
                    }
                }
                this.m_xp = param1.randomchance.hasOwnProperty("xp") ? (int(Number(param1.randomchance.xp.@amount))) : (0);
                return;
            }
            this.m_coin = param1.hasOwnProperty("coin") ? (int(Number(param1.coin.get(0).@amount))) : (0);
            this.m_energy = param1.hasOwnProperty("energy") ? (int(Number(param1.energy.get(0).@amount))) : (0);
            this.m_food = param1.hasOwnProperty("food") ? (int(Number(param1.food.get(0).@amount))) : (0);
            this.m_goods = param1.hasOwnProperty("goods") ? (int(Number(param1.goods.get(0).@amount))) : (0);
            this.m_item = param1.hasOwnProperty("item") ? (String(param1.item.get(0).@name)) : ("");
            this.m_xp = param1.hasOwnProperty("xp") ? (int(Number(param1.xp.get(0).@amount))) : (0);
            return;
        }//end

    }




