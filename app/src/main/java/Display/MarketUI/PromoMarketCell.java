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

import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class PromoMarketCell extends MarketCell
    {
public static  int CELL_WIDTH =110;
public static  int CELL_HEIGHT =80;

        public  PromoMarketCell (LayoutManager param1)
        {
            super(param1);
            return;
        }//end

        private DisplayObject  generateBackground ()
        {
            DisplayObject _loc_1 =null ;
            if (m_item.startDate && m_assetDict.hasOwnProperty("market2_leCard"))
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_leCard");
            }
            else if (m_item.isNew)
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_newCard");
            }
            else if (this.m_itemLocked)
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_lockedCard");
            }
            else
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_marketCard");
            }
            return _loc_1;
        }//end

         protected void  insertCountDownTimer ()
        {
            if (m_item.startDate && m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                addCountdownTimer(this, CELL_WIDTH);
            }
            if (m_item.extraText && m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                addExtraText(this, CELL_WIDTH);
            }
            return;
        }//end

         protected void  setCellBackground ()
        {
            DisplayObject _loc_1 =null ;
            m_content = this.generateBackground();
            ASwingHelper.setBackground(m_alignmentContainer, m_content);
            m_alignmentContainer.setPreferredWidth(CELL_WIDTH);
            m_alignmentContainer.setPreferredHeight(CELL_HEIGHT);
            return;
        }//end

         protected void  revertBG (MouseEvent event )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

         protected void  switchBG (MouseEvent event )
        {
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            dispatchEvent(new DataItemEvent(DataItemEvent.SHOW_TOOLTIP, this.m_item, _loc_3, true));
            return;
        }//end

         protected void  buyItem (MouseEvent event )
        {
            StatsManager.count("market_views", "click_best_seller", m_item.name);
            super.buyItem(event);
            return;
        }//end

    }



