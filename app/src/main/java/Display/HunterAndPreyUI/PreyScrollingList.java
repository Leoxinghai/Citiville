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

import Display.ValentineUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;

    public class PreyScrollingList extends AchievementsScrollingList
    {

        public  PreyScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =1)
        {
            super(param1, param2, param3, param4, param5);
            this.addBorder();
            return;
        }//end

        protected void  addBorder ()
        {
            Sprite _loc_1 =new Sprite ();
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_innerBorder");
            addChild(_loc_2);
            _loc_2.width = 580;
            _loc_2.height = 311;
            _loc_2.x = 41;
            _loc_2.y = -1;
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            _loc_3.x = 184;
            _loc_3.y = 30;
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            (new HunterDialog.assetDict.get("police_divider")).x = 331;
            _loc_4.y = 30;
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            (new HunterDialog.assetDict.get("police_divider")).x = 479;
            _loc_5.y = 30;
            _loc_1.addChild(_loc_2);
            _loc_1.addChild(_loc_3);
            _loc_1.addChild(_loc_4);
            _loc_1.addChild(_loc_5);
            addChild(_loc_1);
            boolean _loc_6 =false ;
            _loc_1.mouseEnabled = false;
            _loc_1.mouseChildren = _loc_6;
            return;
        }//end

         protected void  makeSize ()
        {
            this.setPreferredHeight(PreyCell.BANDIT_CELL_HEIGHT);
            ASwingHelper.setForcedWidth(m_scrollPane, this.getScrollWidth());
            m_dataList.setHGap(0);
            return;
        }//end

         public int  getScrollWidth ()
        {
            return PreyCell.BANDIT_CELL_WIDTH * m_columns;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_up");
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_down");
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_over");
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_up");
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_down");
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_over");
            prevButton = new JButton();
            prevButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            prevButton.setEnabled(false);
            nextButton = new JButton();
            nextButton.wrapSimpleButton(new SimpleButton(_loc_4, _loc_6, _loc_5, _loc_4));
            if (m_data.length <= NUM_ITEMS)
            {
                nextButton.setEnabled(false);
            }
            else
            {
                nextButton.setEnabled(true);
            }
            prevButton.addActionListener(moveLeft, 0, true);
            nextButton.addActionListener(moveRight, 0, true);
            return;
        }//end

    }



