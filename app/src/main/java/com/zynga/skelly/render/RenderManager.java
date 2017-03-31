package com.zynga.skelly.render;

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

import com.zynga.skelly.animation.*;
import de.polygonal.ds.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class RenderManager extends EventDispatcher
    {
        public static Dictionary animationSchedulesByFPS =new Dictionary(true );
        private static int beginFrameTimeMs ;
        private static int beginFrameDeltaMs ;
        private static IRenderer m_mainRenderer ;
        private static Stage m_stage ;
        private static Timer m_beginFrameTimer ;
        private static boolean m_bShouldRender ;
        private static int m_iLastFrameBeginTime ;
        private static int m_uFrameCount =0;
        private static Dictionary m_dictFrameBeginUpdaters =new Dictionary ();
        private static Array m_delayedFunctionCalls =new Array ();
        private static EventDispatcher eventDispatcher =new EventDispatcher ();
        private static SLinkedList m_animSchedules =new SLinkedList ();
        private static SListIterator iter ;
        private static int _frameRate =60;
        private static boolean m_useFrameRenders =false ;
        private static boolean m_dontRunScheduledAnims =false ;
        private static Array rates =.get(1 ,12,20) ;

        public  RenderManager ()
        {
            return;
        }//end

        public static void  init (IRenderer param1 ,Stage param2 ,int param3 =-1,boolean param4 =false )
        {
            m_mainRenderer = param1;
            m_useFrameRenders = param4;
            m_stage = param2;
            frameRate = param3 < 0 ? (60) : (param3);
            m_beginFrameTimer = new Timer(0, 1);
            m_beginFrameTimer.addEventListener(TimerEvent.TIMER, handleBeginFrameTimer, false, 0, true);
            m_stage.addEventListener(Event.ENTER_FRAME, handleEnterFrame, false, 0, true);
            m_stage.addEventListener(Event.RENDER, handleRenderEvent, false, 9999, true);
            m_stage.invalidate();
            trace("@RenderManager::init..." + getTimer());
            return;
        }//end

        public static void  dontRunScheduledAnimations (boolean param1 )
        {
            m_dontRunScheduledAnims = param1;
            return;
        }//end

        public static boolean  dontRunScheduledAnimations ()
        {
            return m_dontRunScheduledAnims;
        }//end

        public static void  renderer (IRenderer param1 )
        {
            m_mainRenderer = param1;
            return;
        }//end

        public static void  frameRate (int param1 )
        {
            _loc_2 = param1;
            m_stage.frameRate = param1;
            _frameRate = _loc_2;
            initAnimSchedules();
            return;
        }//end

        public static int  frameRate ()
        {
            return _frameRate;
        }//end

        private static void  initAnimSchedules ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            for(int i0 = 0; i0 < rates.size(); i0++)
            {
            		_loc_1 = rates.get(i0);

                _loc_2 = Math.ceil(frameRate / _loc_1);
                _loc_3 = Math.ceil(1000 / _loc_1);
                _loc_4 = Math.ceil(frameRate / _loc_2);
                if (!animationSchedulesByFPS.get(_loc_4))
                {
                    trace("creating animationSchedule fps = ", _loc_1, ", FramesPerProcess = ", _loc_2, ", FrameTimeMs = ", _loc_3, ", numProcessingLists = ", _loc_4);
                    animationSchedulesByFPS.put(_loc_4,  m_animSchedules.append(new AnimationSchedule(_loc_2, _loc_3)).data);
                }
            }
            iter = m_animSchedules.getListIterator();
            return;
        }//end

        public static AnimationSchedule  findNearestByFps (int param1 ,IAnimated param2 )
        {
            AnimationSchedule _loc_4 =null ;
            AnimationSchedule _loc_5 =null ;
            int _loc_8 =0;
            _loc_3 = Math.ceil(frameRate /param1 );
            iter.start();
            _loc_6 = int.MAX_VALUE;
            int _loc_7 =1000;
            while (iter.data != null)
            {

                _loc_5 = iter.data;
                _loc_8 = Math.abs(_loc_5.framesPerProcess - _loc_3);
                if (_loc_8 < _loc_6)
                {
                    _loc_4 = _loc_5;
                    _loc_6 = _loc_8;
                }
                _loc_5.remove(param2);
                iter.forth();
            }
            return _loc_4;
        }//end

        public static AnimationList  addAnimationByFPS (int param1 ,IAnimated param2 )
        {
            _loc_3 = findNearestByFps(param1,param2);
            if (_loc_3)
            {
                return _loc_3.add(param2);
            }
            return null;
        }//end

        public static void  removeAnimationByFPS (int param1 ,IAnimated param2 )
        {
            _loc_3 = findNearestByFps(param1,param2);
            return;
        }//end

        public static void  handleRenderEvent (Object param1)
        {
            if (!m_useFrameRenders)
            {
                m_bShouldRender = true;
                m_beginFrameTimer.start();
                if (m_mainRenderer instanceof INotifyBeforeRender)
                {
                    ((INotifyBeforeRender)m_mainRenderer).onNotifyBeforeRender();
                }
            }
            return;
        }//end



        private static void  processAnimationSchedules (int param1 )
        {
            AnimationSchedule _loc_2 =null ;
            if (m_dontRunScheduledAnims)
            {
                return;
            }
            iter.start();
            while (iter.data != null)
            {

                _loc_2 = iter.data;
                _loc_2.process(param1);
                iter.forth();
            }
            return;
        }//end

        public static int  framecount ()
        {
            return m_uFrameCount;
        }//end

        public static void  addDelayedFunctionCall (Function param1 )
        {
            m_delayedFunctionCalls.push(param1);
            return;
        }//end

        public static void  addFrameBeginUpdater (IRenderer param1 )
        {
            m_dictFrameBeginUpdaters.put(param1,  param1);
            return;
        }//end

        public static void  removeFrameBeginUpdater (IRenderer param1 )
        {
            delete m_dictFrameBeginUpdaters.get(param1);
            return;
        }//end

        private static void  handleEnterFrame (Event event )
        {
            if (m_useFrameRenders)
            {
                m_bShouldRender = true;
            }
            if (m_mainRenderer instanceof INotifyBeforeRender)
            {
                ((INotifyBeforeRender)m_mainRenderer).onNotifyBeforeRender();
            }
            if (m_bShouldRender)
            {
                m_stage.invalidate();
                m_bShouldRender = false;
                FrameRateGraph.annotate(2261060);
                doRender();
            }
            return;
        }//end

        private static void  handleBeginFrameTimer (Event event )
        {
            if (m_bShouldRender)
            {
                m_stage.invalidate();
                m_bShouldRender = false;
                FrameRateGraph.annotate(56576);
                doRender();
            }
            return;
        }//end

        private static void  doRender ()
        {
            _loc_3 = null;
            IRenderer _loc_4 =null ;
            boolean _loc_5 =false ;
            beginFrameTimeMs = getTimer();
            _loc_1 = beginFrameTimeMs-m_iLastFrameBeginTime;
            _loc_7 = m_uFrameCount+1;
            m_uFrameCount = _loc_7;
            Function _loc_2 =m_delayedFunctionCalls.shift ();
            if (_loc_2 != null)
            {
                _loc_2();
            }
            for(int i0 = 0; i0 < m_dictFrameBeginUpdaters.size(); i0++)
            {
            		_loc_3 = m_dictFrameBeginUpdaters.get(i0);

                _loc_4 =(IRenderer) _loc_3;
                if (_loc_4)
                {
                    _loc_5 = _loc_4.onRender(beginFrameTimeMs, _loc_1);
                    if (!_loc_5)
                    {
                        delete m_dictFrameBeginUpdaters.get(_loc_3);
                    }
                    continue;
                }
                delete m_dictFrameBeginUpdaters.get(_loc_3);
            }
            if (m_mainRenderer)
            {
                m_mainRenderer.onRender(beginFrameTimeMs, _loc_1);
            }
            processAnimationSchedules(beginFrameTimeMs);
            m_iLastFrameBeginTime = beginFrameTimeMs;
            return;
        }//end

    }


