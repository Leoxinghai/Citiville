package com.lia.utils;

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

//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.ui.*;
//import flash.utils.*;


    public class SWFProfiler
    {
        private static int itvTime ;
        private static int initTime ;
        private static int currentTime ;
        private static int frameCount ;
        private static int totalCount ;
        public static double minFps ;
        public static double maxFps ;
        public static double minMem ;
        public static double maxMem ;
        public static int history =60;
        public static double refreshRate =1;
        public static Array fpsList =new Array();
        public static Array memList =new Array();
        private static boolean displayed =false ;
        private static boolean started =false ;
        private static boolean inited =false ;
        private static Sprite frame ;
        private static Stage stage ;
        private static ProfilerContent content ;
        private static ContextMenuItem ci ;

        public  SWFProfiler ()
        {
            return;
        }//end

        public static void  init (Stage param1 ,InteractiveObject param2 )
        {
            if (inited)
            {
                return;
            }
            inited = true;
            stage = param1;
            content = new ProfilerContent();
            frame = new Sprite();
            minFps = Number.MAX_VALUE;
            maxFps = Number.MIN_VALUE;
            minMem = Number.MAX_VALUE;
            maxMem = Number.MIN_VALUE;
            ContextMenu _loc_3 =new ContextMenu ();
            _loc_3.hideBuiltInItems();
            ci = new ContextMenuItem("Show Profiler", true);
            _loc_3.customItems = .get(ci);
            param2.contextMenu = _loc_3;
            addEvent(ci, ContextMenuEvent.MENU_ITEM_SELECT, onSelect);
            start();
            return;
        }//end

        public static void  start ()
        {
            if (started)
            {
                return;
            }
            started = true;
            _loc_1 = getTimer();
            itvTime = getTimer();
            initTime = _loc_1;
            _loc_1 = 0;
            frameCount = 0;
            totalCount = _loc_1;
            addEvent(frame, Event.ENTER_FRAME, draw);
            return;
        }//end

        public static void  stop ()
        {
            if (!started)
            {
                return;
            }
            started = false;
            removeEvent(frame, Event.ENTER_FRAME, draw);
            return;
        }//end

        public static void  gc ()
        {
            try
            {
                new LocalConnection().connect("foo");
                new LocalConnection().connect("foo");
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        public static double  currentFps ()
        {
            return frameCount / intervalTime;
        }//end

        public static double  currentMem ()
        {
            return System.totalMemory / 1024 / 1024;
        }//end

        public static double  averageFps ()
        {
            return totalCount / runningTime;
        }//end

        private static double  runningTime ()
        {
            return (currentTime - initTime) / 1000;
        }//end

        private static double  intervalTime ()
        {
            return (currentTime - itvTime) / 1000;
        }//end

        private static void  onSelect (ContextMenuEvent event )
        {
            if (!displayed)
            {
                show();
            }
            else
            {
                hide();
            }
            return;
        }//end

        public static void  show ()
        {
            ci.caption = "Hide Profiler";
            displayed = true;
            stage.addChild(content);
            updateDisplay();
            return;
        }//end

        public static void  hide ()
        {
            ci.caption = "Show Profiler";
            displayed = false;
            stage.removeChild(content);
            return;
        }//end

        private static void  draw (Event event =null )
        {
            currentTime = getTimer();
            _loc_3 = frameCount+1;
            frameCount = _loc_3;
            _loc_3 = totalCount + 1;
            totalCount = _loc_3;
            if (intervalTime >= refreshRate)
            {
                if (displayed)
                {
                    updateDisplay();
                }
                else
                {
                    updateMinMax();
                }
                fpsList.unshift(currentFps);
                memList.unshift(currentMem);
                if (fpsList.length > history)
                {
                    fpsList.pop();
                }
                if (memList.length > history)
                {
                    memList.pop();
                }
                itvTime = currentTime;
                frameCount = 0;
            }
            return;
        }//end

        private static void  updateDisplay ()
        {
            updateMinMax();
            content.update(runningTime, minFps, maxFps, minMem, maxMem, currentFps, currentMem, averageFps, fpsList, memList, history);
            return;
        }//end

        private static void  updateMinMax ()
        {
            if (currentFps <= 0)
            {
                return;
            }
            minFps = Math.min(currentFps, minFps);
            maxFps = Math.max(currentFps, maxFps);
            minMem = Math.min(currentMem, minMem);
            maxMem = Math.max(currentMem, maxMem);
            return;
        }//end

        private static void  addEvent (EventDispatcher param1 ,String param2 ,Function param3 )
        {
            param1.addEventListener(param2, param3, false, 0, true);
            return;
        }//end

        private static void  removeEvent (EventDispatcher param1 ,String param2 ,Function param3 )
        {
            param1.removeEventListener(param2, param3);
            return;
        }//end

    }

import flash.display.*;
import flash.events.Event;
import flash.text.*;

internal class ProfilerContent extends flash.display.Sprite
    private TextField minFpsTxtBx ;
    private TextField maxFpsTxtBx ;
    private TextField minMemTxtBx ;
    private TextField maxMemTxtBx ;
    private TextField infoTxtBx ;
    private Shape box ;
    private Shape fps ;
    private Shape mb ;

    void  ProfilerContent ()
    {
        this.fps = new Shape();
        this.mb = new Shape();
        this.box = new Shape();
        this.mouseChildren = false;
        this.mouseEnabled = false;
        this.fps.x = 65;
        this.fps.y = 45;
        this.mb.x = 65;
        this.mb.y = 90;
        _loc_1 = new TextFormat("_sans",9,11184810);
        this.infoTxtBx = new TextField();
        this.infoTxtBx.autoSize = TextFieldAutoSize.LEFT;
        this.infoTxtBx.defaultTextFormat = new TextFormat("_sans", 11, 13421772);
        this.infoTxtBx.y = 98;
        this.minFpsTxtBx = new TextField();
        this.minFpsTxtBx.autoSize = TextFieldAutoSize.LEFT;
        this.minFpsTxtBx.defaultTextFormat = _loc_1;
        this.minFpsTxtBx.x = 7;
        this.minFpsTxtBx.y = 37;
        this.maxFpsTxtBx = new TextField();
        this.maxFpsTxtBx.autoSize = TextFieldAutoSize.LEFT;
        this.maxFpsTxtBx.defaultTextFormat = _loc_1;
        this.maxFpsTxtBx.x = 7;
        this.maxFpsTxtBx.y = 5;
        this.minMemTxtBx = new TextField();
        this.minMemTxtBx.autoSize = TextFieldAutoSize.LEFT;
        this.minMemTxtBx.defaultTextFormat = _loc_1;
        this.minMemTxtBx.x = 7;
        this.minMemTxtBx.y = 83;
        this.maxMemTxtBx = new TextField();
        this.maxMemTxtBx.autoSize = TextFieldAutoSize.LEFT;
        this.maxMemTxtBx.defaultTextFormat = _loc_1;
        this.maxMemTxtBx.x = 7;
        this.maxMemTxtBx.y = 50;
        addChild(this.box);
        addChild(this.infoTxtBx);
        addChild(this.minFpsTxtBx);
        addChild(this.maxFpsTxtBx);
        addChild(this.minMemTxtBx);
        addChild(this.maxMemTxtBx);
        addChild(this.fps);
        addChild(this.mb);
        this.addEventListener(Event.ADDED_TO_STAGE, this.added, false, 0, true);
        this.addEventListener(Event.REMOVED_FROM_STAGE, this.removed, false, 0, true);
        return;
    }//end

    public void  update (double param1 ,double param2 ,double param3 ,double param4 ,double param5 ,double param6 ,double param7 ,double param8 ,Array param9 ,Array param10 ,int param11 )
    {
        double _loc_19 =0;
        if (param1 >= 1)
        {
            this.minFpsTxtBx.text = param2.toFixed(3) + " Fps";
            this.maxFpsTxtBx.text = param3.toFixed(3) + " Fps";
            this.minMemTxtBx.text = param4.toFixed(3) + " Mb";
            this.maxMemTxtBx.text = param5.toFixed(3) + " Mb";
        }
        this.infoTxtBx.text = "Current Fps " + param6.toFixed(3) + "   |   Average Fps " + param8.toFixed(3) + "   |   Memory Used " + param7.toFixed(3) + " Mb";
        this.infoTxtBx.x = stage.stageWidth - this.infoTxtBx.width - 20;
        _loc_12 = this.fps.graphics ;
        this.fps.graphics.clear();
        _loc_12.lineStyle(1, 3407616, 0.7);
        int _loc_13 =0;
        _loc_14 = param9.length;
        int _loc_15 =35;
        _loc_16 = stage.stageWidth-80;
        _loc_17 = stage(.stageWidth-80)/(param11-1);
        _loc_18 = param3-param2;
        _loc_13 = 0;
        while (_loc_13 < _loc_14)
        {

            _loc_19 = (param9.get(_loc_13) - param2) / _loc_18;
            if (_loc_13 == 0)
            {
                _loc_12.moveTo(0, (-_loc_19) * _loc_15);
            }
            else
            {
                _loc_12.lineTo(_loc_13 * _loc_17, (-_loc_19) * _loc_15);
            }
            _loc_13++;
        }
        _loc_12 = this.mb.graphics;
        _loc_12.clear();
        _loc_12.lineStyle(1, 26367, 0.7);
        _loc_13 = 0;
        _loc_14 = param10.length;
        _loc_18 = param5 - param4;
        _loc_13 = 0;
        while (_loc_13 < _loc_14)
        {

            _loc_19 = (param10.get(_loc_13) - param4) / _loc_18;
            if (_loc_13 == 0)
            {
                _loc_12.moveTo(0, (-_loc_19) * _loc_15);
            }
            else
            {
                _loc_12.lineTo(_loc_13 * _loc_17, (-_loc_19) * _loc_15);
            }
            _loc_13++;
        }
        return;
    }//end

    private void  added (Event event )
    {
        this.resize();
        stage.addEventListener(Event.RESIZE, this.resize, false, 0, true);
        return;
    }//end

    private void  removed (Event event )
    {
        stage.removeEventListener(Event.RESIZE, this.resize);
        return;
    }//end

    private void  resize (Event event =null )
    {
        _loc_2 = this.box.graphics ;
        _loc_2.clear();
        _loc_2.beginFill(0, 0.9);
        _loc_2.drawRect(0, 0, stage.stageWidth, 120);
        _loc_2.lineStyle(1, 16777215, 0.2);
        _loc_2.moveTo(65, 45);
        _loc_2.lineTo(65, 10);
        _loc_2.moveTo(65, 45);
        _loc_2.lineTo(stage.stageWidth - 15, 45);
        _loc_2.moveTo(65, 90);
        _loc_2.lineTo(65, 55);
        _loc_2.moveTo(65, 90);
        _loc_2.lineTo(stage.stageWidth - 15, 90);
        _loc_2.endFill();
        this.infoTxtBx.x = stage.stageWidth - this.infoTxtBx.width - 20;
        return;
    }//end


