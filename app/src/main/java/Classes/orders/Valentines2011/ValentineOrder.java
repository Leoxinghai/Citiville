package Classes.orders.Valentines2011;

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
    public class ValentineOrder extends AbstractOrder
    {
        private Array m_cards ;
        private static  String CARDS ="cards";
        private static  String CARD_SEEN ="card_seen";
        private static  String CARD_DATA ="card_data";
        private static  String CARD_TOTAL ="card_total";
        private static  String CARD_ID ="id";

        public  ValentineOrder (String param1 ,String param2 )
        {
            super(param1, param2, OrderType.VALENTINE_2011, null, null);
            this.m_cards = new Array();
            return;
        }//end

         public void  setParams (Object param1 )
        {
            Object _loc_2 =null ;
            m_params.put(ValentineOrder.CARDS,  param1.get(ValentineOrder.CARDS));
            m_params.put(ValentineOrder.CARD_TOTAL,  param1.get(ValentineOrder.CARD_TOTAL));
            for(int i0 = 0; i0 < m_params.get(ValentineOrder.CARDS).size(); i0++)
            {
            		_loc_2 = m_params.get(ValentineOrder.CARDS).get(i0);

                this.m_cards.push(new ValentineCard(_loc_2.get(ValentineOrder.CARD_DATA), Boolean(_loc_2.get(ValentineOrder.CARD_SEEN)), getSenderID(), this, String(_loc_2.get(ValentineOrder.CARD_ID))));
            }
            return;
        }//end

        public Array  getNewCards ()
        {
            ValentineCard _loc_2 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_cards.size(); i0++)
            {
            		_loc_2 = this.m_cards.get(i0);

                if (_loc_2.isNew())
                {
                    _loc_1.push(_loc_2);
                }
            }
            return _loc_1;
        }//end

        public Array  getAllCards ()
        {
            return this.m_cards;
        }//end

        public int  getCardCount ()
        {
            return m_params.get(ValentineOrder.CARD_TOTAL);
        }//end

        public boolean  hasNewCards ()
        {
            return this.getNewCards().length > 0;
        }//end

        public void  setCardAsSeen (String param1 )
        {
            ValentineCard _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_cards.size(); i0++)
            {
            		_loc_2 = this.m_cards.get(i0);

                if (_loc_2.id == param1)
                {
                    _loc_2.setSeen();
                    return;
                }
            }
            return;
        }//end

        public boolean  deleteCard (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_cards.length())
            {

                if (this.m_cards.get(_loc_2).id == param1)
                {
                    this.m_cards.splice(_loc_2, 1);
                    (m_params.get(CARD_TOTAL) - 1);
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

    }


