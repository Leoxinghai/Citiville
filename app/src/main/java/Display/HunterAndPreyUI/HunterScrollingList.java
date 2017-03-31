package Display.HunterAndPreyUI;

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
import Modules.bandits.*;
import Modules.workers.*;
import com.greensock.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class HunterScrollingList extends JPanel
    {
        protected GridList m_dataList ;
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
        public static  int ROW_HEIGHT =50;
        public static  int ROW_WIDTH =610;
        public static  int USABLE_ROWS =6;

        public  HunterScrollingList ()
        {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            _loc_1 =Global.world.citySim.preyManager.getWorkerManagerByGroup(HunterDialog.groupId ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            IntDimension _loc_2 =new IntDimension(ROW_WIDTH ,ROW_HEIGHT );
            this.m_cellFactory = new HunterCellFactory(HunterDialog.assetDict, _loc_2);
            this.m_columns = 1;
            this.m_rows = 5;
            this.m_data = _loc_1.getAllCopData();
            this.makeData();
            this.initSize();
            this.prepare();
            return;
        }//end

        protected double  TWEEN_TIME ()
        {
            return 0.2;
        }//end

        protected void  makeData ()
        {
            this.m_activeSlots = Global.gameSettings().getHubSlots(HunterDialog.groupId, PreyUtil.getHubLevel(HunterDialog.groupId));
            _loc_1 = Math.min(this.m_data.length ,this.m_activeSlots );
            _loc_2 = USABLE_ROWS-_loc_1;
            this.m_model = new VectorListModel();
            int _loc_3 =0;
            while (_loc_3 < _loc_1)
            {

                this.m_model.append(this.m_data.get(_loc_3));
                this.curCount++;
                _loc_3++;
            }
            _loc_3 = 0;
            while (_loc_3 < _loc_2)
            {

                this.m_model.append("empty");
                this.curCount++;
                _loc_3++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, this.m_columns, 0);
            this.m_dataList.setColumns(1);
            this.m_dataList.setVGap(1);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        public void  updateCell (int param1 ,String param2 ,boolean param3 =false )
        {
            HunterData _loc_5 =null ;
            HunterCell _loc_6 =null ;
            int _loc_4 =0;
            while (_loc_4 < this.m_data.length())
            {

                _loc_5 = this.m_data.get(_loc_4);
                if (_loc_5.getPosition() == param1)
                {
                    _loc_6 =(HunterCell) this.m_dataList.getCellByIndex(_loc_4);
                    _loc_6.forceRebuildCell(param2, param3);
                }
                _loc_4++;
            }
            return;
        }//end

        protected void  initSize ()
        {
            this.setPreferredHeight(360);
            this.m_dataList.setPreferredWidth(ROW_WIDTH);
            this.m_dataList.setMinimumWidth(ROW_WIDTH);
            this.m_dataList.setMaximumWidth(ROW_WIDTH);
            this.m_dataList.setPreferredHeight(335);
            return;
        }//end

        protected void  makeButtons ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,30);
            ASwingHelper.setBackground(_loc_1, (DisplayObject)new HunterDialog.assetDict.get("police_scrollContainer"));
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_border");
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_dot");
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_top_up");
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_top_over");
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_bottom_up");
            DisplayObject _loc_7 =(DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_bottom_over");
            this.m_upBtn = new JButton();
            this.m_upBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_5, _loc_4));
            ASwingHelper.setEasyBorder(this.m_upBtn, 110, 0, 0, 0);
            this.m_downBtn = new JButton();
            this.m_downBtn.wrapSimpleButton(new SimpleButton(_loc_6, _loc_7, _loc_7, _loc_6));
            ASwingHelper.setEasyBorder(this.m_downBtn, 0, 0, 0);
            this.m_downBtn.addActionListener(this.moveDown, 0, true);
            this.m_upBtn.addActionListener(this.moveUp, 0, true);
            AssetPane _loc_8 =new AssetPane((DisplayObject)new HunterDialog.assetDict.get( "conventionCenter_pagination_dot") );
            _loc_9 = ASwingHelper.centerElement(_loc_8 );
            _loc_1.setPreferredHeight(335);
            _loc_1.setPreferredWidth(50);
            _loc_1.appendAll(this.m_upBtn, _loc_9, this.m_downBtn);
            this.appendAll(_loc_1);
            return;
        }//end

        protected void  prepare ()
        {
            this.append(this.m_scrollPane);
            this.makeButtons();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  moveUp (AWEvent event =null )
        {
            if (this.m_topCrew > 0)
            {
                this.setupTween(this.m_topCrew, (this.m_topCrew - 1));
                this.m_topCrew--;
            }
            return;
        }//end

        protected void  moveDown (AWEvent event =null )
        {
            if (this.m_topCrew < this.m_activeSlots - USABLE_ROWS)
            {
                this.setupTween(this.m_topCrew, (this.m_topCrew + 1));
                this.m_topCrew++;
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
                startPos = oldIndex * (ROW_HEIGHT + 11);
                tweenObj;
            }
            destPos = newIndex * (ROW_HEIGHT + 11);
            Object tweenProps ;
            this.m_tween = TweenLite.to(tweenObj, this.TWEEN_TIME, tweenProps);
            return;
        }//end

    }



