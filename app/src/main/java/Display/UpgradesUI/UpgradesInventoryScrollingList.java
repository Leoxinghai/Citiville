package Display.UpgradesUI;

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
import com.greensock.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class UpgradesInventoryScrollingList extends JPanel
    {
        public GridList m_dataList ;
        protected JScrollPane m_scrollPane ;
        protected GridListCellFactory m_cellFactory ;
        protected JViewport m_viewport ;
        protected JButton m_upBtn ;
        protected JButton m_downBtn ;
        protected JWindow m_frame ;
        protected int m_rows ;
        protected int m_columns ;
        protected Array m_data ;
        protected TweenLite m_tween ;
        protected int m_activeSlots =0;
        protected int curCount =0;
        protected VectorListModel m_model ;
        protected int m_topCrew =0;
        public static  int ROW_HEIGHT =104;
        public static  int ROW_WIDTH =312;
        public static  int ROWS_PER_PAGE =3;
        public static  int COLUMNS_PER_PAGE =2;

        public  UpgradesInventoryScrollingList (Dictionary param1 ,Array param2 )
        {
            _loc_3 = param2.length >ROWS_PER_PAGE *COLUMNS_PER_PAGE ;
            _loc_4 = _loc_3? (FlowLayout.LEFT) : (FlowLayout.CENTER);
            super(new FlowLayout(_loc_4, 0, 0));
            IntDimension _loc_5 =new IntDimension(ROW_WIDTH ,ROW_HEIGHT );
            this.m_cellFactory = new UpgradesInventoryCellFactory(param2, param1);
            this.m_columns = COLUMNS_PER_PAGE;
            this.m_rows = ROWS_PER_PAGE;
            this.m_data = param2;
            this.makeData();
            this.initSize();
            this.append(this.m_scrollPane);
            if (_loc_3)
            {
                this.append(ASwingHelper.horizontalStrut(10));
                this.append(this.makeNavPane());
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected double  TWEEN_TIME ()
        {
            return 0.2;
        }//end

        protected void  makeData ()
        {
            this.m_activeSlots = this.m_data.length;
            this.m_model = new VectorListModel();
            int _loc_1 =0;
            while (_loc_1 < Math.min(this.m_activeSlots, this.m_data.length()))
            {

                this.m_model.append(this.m_data.get(_loc_1));
                this.curCount++;
                _loc_1++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, this.m_columns, 0);
            this.m_dataList.setVGap(0);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        protected void  initSize ()
        {
            this.setPreferredHeight(ROW_HEIGHT * ROWS_PER_PAGE);
            this.m_dataList.setPreferredWidth(ROW_WIDTH * this.m_columns);
            this.m_dataList.setMinimumWidth(ROW_WIDTH * this.m_columns);
            this.m_dataList.setMaximumWidth(ROW_WIDTH * this.m_columns);
            this.m_dataList.setPreferredHeight(ROW_HEIGHT * ROWS_PER_PAGE);
            return;
        }//end

        protected JPanel  makeNavPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            DisplayObject _loc_2 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "scrollBorder");
            MarginBackground _loc_3 =new MarginBackground(_loc_2 );
            _loc_1.setBackgroundDecorator(_loc_3);
            _loc_1.setPreferredWidth(_loc_2.width);
            _loc_1.setMinimumWidth(_loc_2.width);
            _loc_1.setMaximumWidth(_loc_2.width);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_1.append(this.makeButtons());
            return _loc_1;
        }//end

        protected JPanel  makeButtons ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            DisplayObject _loc_2 =new UpgradesCommonAssets.assetDict.put( "scrollCircle", new UpgradesCommonAssets.assetDict.get( "scrollCircle") );
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_4 = ASwingHelper.centerElement(_loc_3 );
            DisplayObject _loc_5 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "up_disabled");
            DisplayObject _loc_6 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "up_over");
            DisplayObject _loc_7 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "up_up");
            DisplayObject _loc_8 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "down_disabled");
            DisplayObject _loc_9 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "down_over");
            DisplayObject _loc_10 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "down_up");
            this.m_upBtn = new JButton();
            this.m_upBtn.wrapSimpleButton(new SimpleButton(_loc_7, _loc_6, _loc_5, _loc_7));
            ASwingHelper.setEasyBorder(this.m_upBtn, 50, 0, 0, 0);
            this.m_downBtn = new JButton();
            this.m_downBtn.wrapSimpleButton(new SimpleButton(_loc_10, _loc_9, _loc_8, _loc_10));
            this.m_downBtn.addActionListener(this.moveDown, 0, true);
            this.m_upBtn.addActionListener(this.moveUp, 0, true);
            if (this.m_model.size() <= ROWS_PER_PAGE)
            {
                this.m_downBtn.setEnabled(false);
            }
            this.m_upBtn.setEnabled(false);
            _loc_1.setPreferredHeight(ROW_HEIGHT * ROWS_PER_PAGE);
            _loc_1.setPreferredWidth(50);
            _loc_1.appendAll(this.m_upBtn, ASwingHelper.verticalStrut(20), _loc_4, ASwingHelper.verticalStrut(20), this.m_downBtn);
            return _loc_1;
        }//end

        protected void  moveUp (AWEvent event =null )
        {
            if (this.m_topCrew > 0)
            {
                this.setupTween(this.m_topCrew, (this.m_topCrew - 1));
                this.m_topCrew--;
                if (this.m_topCrew == 0)
                {
                    this.m_upBtn.setEnabled(false);
                }
                this.m_downBtn.setEnabled(true);
            }
            return;
        }//end

        protected void  moveDown (AWEvent event =null )
        {
            if (this.m_topCrew < this.m_activeSlots - ROWS_PER_PAGE)
            {
                this.setupTween(this.m_topCrew, (this.m_topCrew + 1));
                this.m_topCrew++;
                if (this.m_topCrew == this.m_activeSlots - ROWS_PER_PAGE)
                {
                    this.m_downBtn.setEnabled(false);
                }
                this.m_upBtn.setEnabled(true);
            }
            return;
        }//end

        protected void  setupTween (int param1 ,int param2 )
        {
            int startPos ;
            int destPos ;
            Object tweenObj ;
            oldIndex = param1;
            newIndex = param2;
            if (this.m_tween && this.m_tween.data)
            {
                tweenObj = this.m_tween.data;
            }
            else
            {
                startPos = oldIndex * ROW_HEIGHT;
                tweenObj;
            }
            destPos = newIndex * ROW_HEIGHT;
            Object tweenProps ;
            this.m_tween = TweenLite.to(tweenObj, this.TWEEN_TIME, tweenProps);
            return;
        }//end

        public void  rebuildCellsAfterBuyAll ()
        {
            (this.m_cellFactory as UpgradesInventoryCellFactory).updateList();
            return;
        }//end

    }



