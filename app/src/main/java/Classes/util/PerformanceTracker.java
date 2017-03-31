package Classes.util;

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
import Display.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.system.*;
//import flash.text.*;
//import flash.utils.*;

    public class PerformanceTracker extends Sprite
    {
        private double fontSize ;
        private double lastUpdate ;
        private double frameCount ;
        private TextField m_fpsText ;
        private TextField m_memoryText ;
        private TextField m_visibleObjects ;
        private TextField m_transactionsText ;
        private static  double UPDATE_INTERVAL =1000;
        private static  Point TEXT_OFFSET =new Point(250,-150);
        private static int m_randomRollCount ;

        public void  PerformanceTracker (double param1 =16777215,double param2 =12)
        {
            TextFormat _loc_3 =new TextFormat(null ,param2 ,param1 ,true );
            this.m_fpsText = new StrokeTextField(0, 0.8);
            this.m_memoryText = new StrokeTextField(0, 0.8);
            this.m_visibleObjects = new StrokeTextField(0, 0.8);
            this.m_transactionsText = new StrokeTextField(0, 0.8);
            this.m_fpsText.setTextFormat(_loc_3);
            this.m_memoryText.setTextFormat(_loc_3);
            this.m_visibleObjects.setTextFormat(_loc_3);
            this.m_transactionsText.setTextFormat(_loc_3);
            this.m_fpsText.autoSize = TextFieldAutoSize.LEFT;
            this.m_memoryText.autoSize = TextFieldAutoSize.LEFT;
            this.m_visibleObjects.autoSize = TextFieldAutoSize.LEFT;
            this.m_transactionsText.autoSize = TextFieldAutoSize.LEFT;
            this.m_transactionsText.defaultTextFormat = _loc_3;
            this.m_visibleObjects.defaultTextFormat = _loc_3;
            this.m_fpsText.defaultTextFormat = _loc_3;
            this.m_memoryText.defaultTextFormat = _loc_3;
            this.m_fpsText.selectable = false;
            this.m_memoryText.selectable = false;
            this.m_transactionsText.selectable = false;
            this.m_fpsText.mouseEnabled = false;
            this.m_memoryText.mouseEnabled = false;
            this.m_transactionsText.mouseEnabled = false;
            this.updateText(this.frameCount);
            addChild(this.m_fpsText);
            addChild(this.m_memoryText);
            addChild(this.m_visibleObjects);
            addChild(this.m_transactionsText);
            addEventListener(Event.ADDED_TO_STAGE, this.setFPSUpdate);
            addEventListener(Event.REMOVED_FROM_STAGE, this.clearFPSUpdate);
            return;
        }//end

        private void  setFPSUpdate (Event event )
        {
            addEventListener(Event.ENTER_FRAME, this.updateFPS);
            this.frameCount = 0;
            this.updateText(this.frameCount);
            this.lastUpdate = getTimer();
            return;
        }//end

        private void  clearFPSUpdate (Event event )
        {
            removeEventListener(Event.ENTER_FRAME, this.updateFPS);
            return;
        }//end

        private void  updateFPS (Event event )
        {
            _loc_2 = getTimer();
            this.frameCount++;
            if (_loc_2 >= this.lastUpdate + UPDATE_INTERVAL)
            {
                this.lastUpdate = _loc_2;
                this.updateText(this.frameCount);
                this.frameCount = 0;
            }
            return;
        }//end

        private void  updateText (double param1 )
        {
            this.x = TEXT_OFFSET.x;
            this.y = Global.ui.screenHeight + TEXT_OFFSET.y;
            _loc_2 =Global.world ;
            _loc_3 = _loc_2.getNumObjects ();
            _loc_4 = _loc_2.getNumVisibleObjects ();
            _loc_5 = _loc_2.citySim.npcManager.walkerCount ;
            _loc_6 = _loc_2.citySim.npcManager.getVisibleWalkerCount ();
            _loc_7 = double(System.totalMemory/1024/1024);
            _loc_8 = TransactionManager.transactionQueueLength;
            _loc_9 = TransactionManager.maxQueued;
            this.m_fpsText.text = _loc_8 + " / " + _loc_9 + " | " + param1 + " fps | " + _loc_7.toFixed(2) + " MB | " + _loc_4 + "/" + _loc_3 + " objects visible" + " | " + _loc_6 + "/" + _loc_5 + " walkers visible" + "\t(" + (Global.world.defCon + 1) + (GameObjectLayer.useCulling() ? ("c") : ("s")) + ")" ;
            return;
        }//end

        public static void  rollCount (int param1 )
        {
            m_randomRollCount = param1;
            return;
        }//end

    }



