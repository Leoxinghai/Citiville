package Display.MarketUI;

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

import com.greensock.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class LargeMarketScrollingList extends MarketScrollingList
    {
        private TweenLite m_delayedPopulateCall ;
        private static  double DELAYED_POPULATE_INTERVAL_SECONDS =0.0166667;

        public  LargeMarketScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2)
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         public void  dispose ()
        {
            if (this.m_delayedPopulateCall != null)
            {
                this.m_delayedPopulateCall.kill();
                this.m_delayedPopulateCall = null;
            }
            super.dispose();
            return;
        }//end

         protected void  makeData ()
        {
            super.makeData();
            this.m_delayedPopulateCall = TweenLite.delayedCall(DELAYED_POPULATE_INTERVAL_SECONDS, this.delayedPopulate);
            return;
        }//end

        private boolean  delayedPopulateOneCell ()
        {
            if (m_curCount < m_data.length())
            {
                m_model.append(m_data.get(m_curCount));
                _loc_4 = m_curCount+1;
                m_curCount = _loc_4;
                return true;
            }
            _loc_1 = m_model.getSize();
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                if (m_model.getElementAt(_loc_2) == null)
                {
                    m_model.replaceAt(_loc_2, m_data.get(_loc_2));
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        private void  delayedPopulate ()
        {
            this.m_delayedPopulateCall = null;
            if (this.delayedPopulateOneCell())
            {
                this.m_delayedPopulateCall = TweenLite.delayedCall(DELAYED_POPULATE_INTERVAL_SECONDS, this.delayedPopulate);
            }
            return;
        }//end

         public int  numItems ()
        {
            return 6;
        }//end

         protected void  initSize ()
        {
            m_dataList.setHGap(7);
            m_dataList.setVGap(4);
            this.setPreferredHeight(345);
            m_scrollPane.setPreferredWidth(650);
            m_scrollPane.setPreferredHeight(345);
            return;
        }//end

         protected Insets  getLeftInsets ()
        {
            return new Insets(0, 3, 17, 3);
        }//end

         protected Insets  getRightInsets ()
        {
            return new Insets(0, 3, 17, 0);
        }//end

        protected double  scrollingListHeight ()
        {
            return this.scrollingListItemHeight * 2;
        }//end

         protected double  scrollingListWidth ()
        {
            return this.scrollingListItemWidth * this.numItems / 2;
        }//end

         protected double  scrollingListItemWidth ()
        {
            return LargeCatalog.CARD_WIDTH + Catalog.CARD_OFFSET + 5;
        }//end

         protected double  scrollingListItemHeight ()
        {
            return LargeCatalog.CARD_HEIGHT + 8;
        }//end

         protected void  moveLeft (AWEvent event )
        {
            _loc_2 = Math.max(m_curIndex -this.numItems ,0);
            this.scrollToItemIndex(_loc_2);
            dispatchEvent(new Event(Event.CHANGE));
            return;
        }//end

         protected void  moveRight (AWEvent event )
        {
            _loc_2 = Math.min(m_curIndex +this.numItems ,(m_data.length -1));
            this.scrollToItemIndex(_loc_2);
            dispatchEvent(new Event(Event.CHANGE));
            return;
        }//end

         public void  scrollToItemIndex (int param1 )
        {
            int _loc_2 =Math.floor(param1 /this.numItems );
            int _loc_6 ;
            param1 = _loc_2 * this.numItems;
            if (param1 == m_curIndex)
            {
                return;
            }
            m_curIndex = param1;
            while (m_curCount < m_curIndex)
            {

                m_model.append(null);
                _loc_6 = m_curCount + 1;
                m_curCount = _loc_6;
            }
            int _loc_3 =Math.min(m_data.length ,m_curIndex +this.numItems );
            int _loc_4 =m_curIndex ;
            while (_loc_4 < _loc_3)
            {

                if (_loc_4 < m_curCount)
                {
                    if (m_model.getElementAt(_loc_4) == null)
                    {
                        m_model.replaceAt(_loc_4, m_data.get(_loc_4));
                    }
                }
                else
                {
                    m_model.append(m_data.get(m_curCount));
                    _loc_6 = m_curCount + 1;
                    m_curCount = _loc_6;
                }
                _loc_4++;
            }
            m_dataList.setViewPosition(new IntPoint(0, this.scrollingListHeight * _loc_2));
            leftBtn.setEnabled(m_curIndex > 0);
            rightBtn.setEnabled(_loc_3 < m_data.length());
            return;
        }//end

    }



