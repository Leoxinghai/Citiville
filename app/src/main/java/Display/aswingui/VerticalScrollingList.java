package Display.aswingui;

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
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class VerticalScrollingList extends JPanel
    {
        protected Dictionary m_assetDict ;
        protected VectorListModel m_dataList ;
        protected GridListCellFactory m_cellFactory ;
        protected int m_columns ;
        protected int m_rows ;
        protected int m_preferredWidth ;
        protected IntDimension m_prefSize ;
        protected boolean m_rounded ;
        protected GridList m_gridList ;
        protected JPanel m_navPanel ;
        protected int m_scrollWidth ;
        protected int m_currentIndex ;
        public static  String EVENT_UPDATEBUTTONS ="VerticalScrollingListEvent_UpdateButtons";

        public  VerticalScrollingList (Dictionary param1 ,VectorListModel param2 ,GridListCellFactory param3 ,int param4 =0,int param5 =0,int param6 =0,int param7 =0,boolean param8 =false ,int param9 =0)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.m_assetDict = param1;
            this.m_dataList = param2;
            this.m_cellFactory = param3;
            this.m_columns = param4;
            this.m_rows = param5;
            this.m_prefSize = new IntDimension(param6, param7);
            this.m_rounded = param8;
            this.m_scrollWidth = param9;
            this.makeUI();
            ASwingHelper.prepare(this);
            this.m_currentIndex = 0;
            return;
        }//end

        public int  numElements ()
        {
            if (this.m_gridList.getModel())
            {
                return this.m_gridList.getModel().getSize();
            }
            return 0;
        }//end

        public void  setScrollBarWidth (int param1 )
        {
            this.m_navPanel.setPreferredWidth(param1);
            return;
        }//end

        protected void  makeUI ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            this.m_gridList = new GridList(this.m_dataList, this.m_cellFactory, this.m_columns, 0);
            _loc_2 = this.makeNavPanel ();
            _loc_3 = this.makeGridListPanel ();
            _loc_1.appendAll(_loc_3, _loc_2);
            this.append(_loc_1);
            return;
        }//end

        protected JPanel  makeGridListPanel ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            this.m_gridList.setSelectable(false);
            this.m_gridList.setVGap(0);
            if (this.m_prefSize)
            {
                _loc_2 = this.m_prefSize.width - this.m_navPanel.getWidth();
                _loc_3 = this.m_prefSize.height;
                this.m_gridList.setPreferredWidth(_loc_2);
                this.m_gridList.setPreferredHeight(_loc_3);
                this.m_gridList.setTileWidth(_loc_2 / this.m_columns);
                this.m_gridList.setTileHeight(_loc_3 / this.m_rows);
            }
            this.dispatchEvent(new Event(EVENT_UPDATEBUTTONS));
            _loc_1.append(this.m_gridList);
            ASwingHelper.prepare(this.m_gridList);
            return _loc_1;
        }//end

        protected JPanel  makeNavPanel ()
        {
            MarginBackground _loc_12 =null ;
            this.m_navPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_1 =(DisplayObject) new this.m_assetDict.get( "pic_dot");
            _loc_2 =(DisplayObject) new this.m_assetDict.get( "btn_up_normal");
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "btn_up_down");
            _loc_4 =(DisplayObject) new this.m_assetDict.get( "btn_up_over");
            _loc_5 =(DisplayObject) new this.m_assetDict.get( "btn_down_normal");
            _loc_6 =(DisplayObject) new this.m_assetDict.get( "btn_down_down");
            _loc_7 =(DisplayObject) new this.m_assetDict.get( "btn_down_over");
            JButton _loc_8 =new JButton ();
            _loc_8.wrapSimpleButton(new SimpleButton(_loc_2, _loc_4, _loc_3, _loc_2));
            _loc_8.addActionListener(this.moveUp, 0, true);
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_10 =new AssetPane(_loc_1 );
            _loc_9.append(_loc_10);
            JButton _loc_11 =new JButton ();
            _loc_11.wrapSimpleButton(new SimpleButton(_loc_5, _loc_7, _loc_6, _loc_5));
            _loc_11.addActionListener(this.moveDown, 0, true);
            this.m_navPanel.appendAll(_loc_8, ASwingHelper.verticalStrut(50), _loc_9, ASwingHelper.verticalStrut(50), _loc_11);
            if (this.m_rounded)
            {
                _loc_12 = new MarginBackground(new (DisplayObject)this.m_assetDict.get("vertical_scrollBar_border"));
                this.m_navPanel.setBackgroundDecorator(_loc_12);
            }
            else
            {
                this.m_navPanel.setBorder(new LineBorder(null, new ASColor(EmbeddedArt.lightBlueTextColor), 2));
            }
            if (this.m_scrollWidth != 0)
            {
                this.m_navPanel.setPreferredWidth(this.m_scrollWidth);
            }
            if (this.numElements <= this.m_rows)
            {
                _loc_8.setEnabled(false);
                _loc_11.setEnabled(false);
            }
            else
            {
                _loc_8.setEnabled(true);
                _loc_11.setEnabled(true);
            }
            ASwingHelper.prepare(this.m_navPanel);
            return this.m_navPanel;
        }//end

        protected void  moveUp (AWEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_currentIndex > 0)
            {
                this.m_gridList.scrollVertical(-this.m_gridList.getTileHeight());
                this.m_currentIndex--;
            }
            this.dispatchEvent(new Event(EVENT_UPDATEBUTTONS));
            return;
        }//end

        protected void  moveDown (AWEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_currentIndex < this.numElements - this.m_rows)
            {
                this.m_gridList.scrollVertical(this.m_gridList.getTileHeight());
                this.m_currentIndex++;
            }
            this.dispatchEvent(new Event(EVENT_UPDATEBUTTONS));
            return;
        }//end

    }



