package Modules.franchise.display;

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
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class FranchiseCardScrollingList extends JPanel
    {
        protected VectorListModel m_model ;
        protected int m_columns ;
        protected int m_rows ;
        protected GridListCellFactory m_cellFactory ;
        protected Array m_data ;
        protected GridList m_dataList ;
        protected JScrollPane m_scrollPane ;
        protected JPanel m_navPane ;
        protected JButton upButton ;
        protected JButton downButton ;
        protected int m_curCount =0;
        protected int m_curIndex =0;
        public static  int NUM_ITEMS =4;
public static  int ROW_HEIGHT =59;
public static  int ROW_WIDTH =659;

        public  FranchiseCardScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.LEFT));
            this.m_cellFactory = new param2;
            this.m_columns = param4;
            this.m_rows = param5;
            this.m_data = param1;
            this.makeCards();
            this.makeButtons();
            this.makeNavPane();
            this.append(this.m_scrollPane);
            this.append(ASwingHelper.horizontalStrut(3));
            this.append(this.m_navPane);
            this.setPreferredWidth(ROW_WIDTH);
            this.setMinimumWidth(ROW_WIDTH);
            this.setMaximumWidth(ROW_WIDTH);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeCards ()
        {
            this.m_model = new VectorListModel();
            _loc_1 = Math.min(NUM_ITEMS ,this.m_data.length );
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                this.m_model.append(this.m_data.get(_loc_2));
                this.m_curCount++;
                _loc_2++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, 1, 0);
            this.m_dataList.setVGap(0);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_up_normal");
            DisplayObject _loc_2 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_up_down");
            DisplayObject _loc_3 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_up_over");
            DisplayObject _loc_4 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_down_normal");
            DisplayObject _loc_5 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_down_down");
            DisplayObject _loc_6 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_down_over");
            this.upButton = new JButton();
            this.upButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            this.upButton.setEnabled(false);
            this.downButton = new JButton();
            this.downButton.wrapSimpleButton(new SimpleButton(_loc_4, _loc_6, _loc_5, _loc_4));
            if (this.m_data.length <= NUM_ITEMS)
            {
                this.downButton.setEnabled(false);
            }
            else
            {
                this.downButton.setEnabled(true);
            }
            this.upButton.addActionListener(this.moveUp, 0, true);
            this.downButton.addActionListener(this.moveDown, 0, true);
            return;
        }//end

        protected void  makeNavPane ()
        {
            this.m_navPane = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_navPane.append(this.upButton);
            this.m_navPane.appendAll(ASwingHelper.verticalStrut(150));
            this.m_navPane.append(this.downButton);
            ASwingHelper.prepare(this.m_navPane);
            return;
        }//end

        protected void  moveUp (AWEvent event )
        {
            this.m_curIndex = this.m_curIndex - NUM_ITEMS;
            if (this.m_curIndex <= 0)
            {
                this.upButton.setEnabled(false);
                this.m_curIndex = 0;
            }
            this.m_dataList.scrollVertical((-ROW_HEIGHT) * NUM_ITEMS);
            this.downButton.setEnabled(true);
            return;
        }//end

        public int  currentItem ()
        {
            return this.m_curIndex - NUM_ITEMS;
        }//end

        protected void  moveDown (AWEvent event )
        {
            int _loc_4 =0;
            _loc_2 = this.m_curCount ;
            _loc_3 = Math.min(this.m_data.length ,this.m_curCount +NUM_ITEMS );
            if (_loc_3 - _loc_2 > 0)
            {
                if (this.m_model.getSize() < NUM_ITEMS + this.m_curCount)
                {
                    _loc_4 = _loc_2;
                    while (_loc_4 < _loc_3)
                    {

                        this.m_model.append(this.m_data.get(_loc_4));
                        this.m_curCount++;
                        this.m_curIndex++;
                        _loc_4++;
                    }
                    if (this.m_curIndex + NUM_ITEMS >= (this.m_data.length - 1))
                    {
                        this.downButton.setEnabled(false);
                    }
                }
            }
            else if (this.m_curIndex < this.m_curCount)
            {
                this.m_curIndex = this.m_curIndex + NUM_ITEMS;
                if (this.m_curIndex + NUM_ITEMS >= (this.m_data.length - 1))
                {
                    this.downButton.setEnabled(false);
                }
            }
            this.upButton.setEnabled(true);
            this.m_dataList.scrollVertical(ROW_HEIGHT * NUM_ITEMS);
            return;
        }//end

    }



