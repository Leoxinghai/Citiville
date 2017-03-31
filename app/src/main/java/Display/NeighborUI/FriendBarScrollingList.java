package Display.NeighborUI;

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
import Display.aswingui.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.util.*;

    public class FriendBarScrollingList extends JPanel
    {
        protected GridList m_dataList ;
        protected JScrollPane m_scrollPane ;
        protected GridListCellFactory m_cellFactory ;
        protected JViewport m_viewport ;
        protected int m_curLeftScrollNdx =-1;
        protected JWindow m_frame ;
        protected int m_rows ;
        protected int m_columns ;
        protected Array m_data ;
        protected int curCount =0;
        protected VectorListModel m_model ;
        public static  int SCROLL_VERTICAL =0;
        public static  int SCROLL_HORIZONTAL =1;
        public static  int NUM_ITEMS =6;
        public static  int ITEM_WIDTH =76;
        public static  int ITEM_OFFSET =4;

        public  FriendBarScrollingList (Array param1 )
        {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            this.m_cellFactory = new FriendCellFactory();
            this.m_columns = NUM_ITEMS;
            this.m_rows = 1;
            this.m_data = param1;
            this.makeData();
            this.initSize();
            this.prepare();
            return;
        }//end

        protected void  makeData ()
        {
            _loc_1 = Math.max ((NUM_ITEMS +1),this.m_data.length );
            this.m_model = new VectorListModel();
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                this.m_model.append(this.m_data.get(_loc_2));
                this.curCount++;
                _loc_2++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, this.m_columns, this.m_rows);
            this.m_dataList.setHGap(1);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            this.m_scrollPane.setPreferredWidth(NUM_ITEMS * (ITEM_WIDTH + ITEM_OFFSET));
            this.m_scrollPane.setMaximumWidth(NUM_ITEMS * (ITEM_WIDTH + ITEM_OFFSET));
            this.m_scrollPane.setMinimumWidth(NUM_ITEMS * (ITEM_WIDTH + ITEM_OFFSET));
            this.m_curLeftScrollNdx = 0;
            this.moveRight(this.m_data.length - NUM_ITEMS);
            return;
        }//end

        public Array  data ()
        {
            return this.m_data;
        }//end

        public void  friendVisited (String param1 )
        {
            FriendCell _loc_4 =null ;
            _loc_2 = this.m_dataList.getCells ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.size())
            {

                _loc_4 = _loc_2.elementAt(_loc_3);
                if (_loc_4.m_friend.m_uid == param1)
                {
                    _loc_4.clearNewFriend();
                }
                _loc_3++;
            }
            return;
        }//end

        public void  data (Array param1 )
        {
            this.m_data = param1;
            this.curCount = 0;
            this.m_model.clear();
            _loc_2 = this.m_data.length ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                this.m_model.append(this.m_data.get(_loc_3));
                this.curCount++;
                _loc_3++;
            }
            _loc_4 = this.m_data.length -NUM_ITEMS ;
            this.m_curLeftScrollNdx = _loc_4;
            this.moveRight(_loc_4);
            return;
        }//end

        protected void  prepare ()
        {
            this.append(this.m_scrollPane);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  initSize ()
        {
            return;
        }//end

        public void  removeListeners ()
        {
            return;
        }//end

        protected void  offsetAndloadPortraits (int param1 )
        {
            FriendCell _loc_7 =null ;
            this.m_curLeftScrollNdx = this.m_curLeftScrollNdx + param1;
            if (this.m_curLeftScrollNdx > this.m_data.length - NUM_ITEMS)
            {
                this.m_curLeftScrollNdx = this.m_data.length - NUM_ITEMS;
            }
            if (this.m_curLeftScrollNdx < 0)
            {
                this.m_curLeftScrollNdx = 0;
            }
            _loc_2 = NUM_ITEMS;
            _loc_3 = this.m_curLeftScrollNdx +NUM_ITEMS +_loc_2 ;
            _loc_4 = this.m_dataList.getCells ();
            if (_loc_3 > _loc_4.size())
            {
                _loc_3 = _loc_4.size();
            }
            _loc_5 = this.m_curLeftScrollNdx -_loc_2 ;
            if (this.m_curLeftScrollNdx - _loc_2 < 0)
            {
                _loc_5 = 0;
            }
            _loc_6 = _loc_5;
            while (_loc_6 < _loc_3)
            {

                _loc_7 = _loc_4.elementAt(_loc_6);
                _loc_7.loadNeighborPortrait();
                _loc_6++;
            }
            return;
        }//end

        public void  moveLeft (int param1 )
        {
            int _loc_2 =0;
            if (param1 == 0)
            {
                _loc_2 = this.m_curLeftScrollNdx;
                this.m_dataList.scrollHorizontal((-(ITEM_WIDTH + ITEM_OFFSET)) * _loc_2);
                this.offsetAndloadPortraits(-_loc_2);
            }
            else
            {
                this.m_dataList.scrollHorizontal((-(ITEM_WIDTH + ITEM_OFFSET)) * param1);
                this.offsetAndloadPortraits(-param1);
            }
            return;
        }//end

        public void  moveRight (int param1 )
        {
            int _loc_2 =0;
            if (param1 == 0)
            {
                _loc_2 = this.m_data.length - NUM_ITEMS;
                this.m_dataList.scrollHorizontal((ITEM_WIDTH + ITEM_OFFSET) * _loc_2);
                this.offsetAndloadPortraits(_loc_2);
            }
            else
            {
                this.m_dataList.scrollHorizontal((ITEM_WIDTH + ITEM_OFFSET) * param1);
                this.offsetAndloadPortraits(param1);
            }
            return;
        }//end

        public void  updateFriend (Friend param1 )
        {
            Object _loc_4 =null ;
            FriendCell _loc_5 =null ;
            _loc_2 = this.m_dataList.getCells ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.size())
            {

                _loc_4 = _loc_2.get(_loc_3);
                if (_loc_4 && _loc_4 instanceof FriendCell)
                {
                    _loc_5 =(FriendCell) _loc_4;
                    if (_loc_5.m_friend.uid == param1.uid)
                    {
                        _loc_5.removeAll();
                        _loc_5.setCellValue(param1);
                        _loc_5.showPic();
                        break;
                    }
                }
                _loc_3++;
            }
            return;
        }//end

    }



