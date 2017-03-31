package Modules.landmarks;

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

import Display.DialogUI.*;
import Display.aswingui.*;

import com.greensock.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class LandmarksTechTreeScrollingList extends JPanel
    {
        public GridList m_dataList ;
        protected JScrollPane m_scrollPane ;
        protected GridListCellFactory m_cellFactory ;
        protected JViewport m_viewport ;
        protected JButton m_leftBtn ;
        protected JButton m_rightBtn ;
        protected JWindow m_frame ;
        protected Vector<WonderDatum> m_data;
        protected TweenLite m_tween ;
        protected int curCount =0;
        protected VectorListModel m_model ;
        protected Dictionary m_assetDict ;
        public static  int ROW_HEIGHT =460;
        public static  int ROW_WIDTH =710;

        public  LandmarksTechTreeScrollingList (Dictionary param1 ,Vector param2 .<WonderDatum >,GenericDialogView param3 )
        {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            this.m_assetDict = param1;
            this.m_cellFactory = new LandmarksTechTreeCellFactory(param1, param3);
            this.m_data = param2;
            this.makeData();
            this.initSize();
            this.append(this.m_scrollPane);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected double  TWEEN_TIME ()
        {
            return 0.2;
        }//end

        protected void  makeData ()
        {
            this.m_model = new VectorListModel();
            _loc_1 = this.m_data.length ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                this.m_model.append(this.m_data.get(_loc_2));
                this.curCount++;
                _loc_2++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, 0, 1);
            this.m_dataList.setHGap(0);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        protected void  initSize ()
        {
            ASwingHelper.setForcedHeight(this, ROW_HEIGHT);
            ASwingHelper.setForcedHeight(this.m_dataList, ROW_HEIGHT);
            this.m_dataList.setPreferredWidth(660);
            return;
        }//end

        protected JPanel  makeLeftButton ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "btn_left_down");
            _loc_4 =(DisplayObject) new this.m_assetDict.get( "btn_left_over");
            _loc_5 =(DisplayObject) new this.m_assetDict.get( "btn_left_normal");
            this.m_leftBtn = new JButton();
            this.m_leftBtn.wrapSimpleButton(new SimpleButton(_loc_5, _loc_4, _loc_3, _loc_5));
            this.m_leftBtn.setEnabled(false);
            _loc_2.append(this.m_leftBtn);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeRightButton ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "btn_right_down");
            _loc_4 =(DisplayObject) new this.m_assetDict.get( "btn_right_over");
            _loc_5 =(DisplayObject) new this.m_assetDict.get( "btn_right_normal");
            this.m_rightBtn = new JButton();
            this.m_rightBtn.wrapSimpleButton(new SimpleButton(_loc_5, _loc_4, _loc_3, _loc_5));
            this.m_rightBtn.setEnabled(false);
            _loc_2.append(this.m_rightBtn);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

    }



