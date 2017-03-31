package com.zynga.skelly.animation;

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

import de.polygonal.ds.*;
    public class AnimationSchedule
    {
        private DLinkedList m_listOfLists ;
        private AnimationList m_nextListToProcess ;
        public int framesPerProcess ;

        public void  AnimationSchedule (int param1 ,int param2 )
        {
            AnimationList _loc_5 =null ;
            DListNode _loc_6 =null ;
            DListNode _loc_7 =null ;
            DListNode _loc_8 =null ;
            this.framesPerProcess = param1;
            this.m_listOfLists = new DLinkedList();
            int _loc_3 =0;
            while (_loc_3 < param1)
            {

                this.m_listOfLists.append(new AnimationList(param1, param2, _loc_3));
                _loc_3++;
            }
            _loc_4 = this.m_listOfLists.getListIterator ();
            this.m_listOfLists.getListIterator().start();
            while (_loc_4.data != null)
            {

                _loc_5 = _loc_4.data;
                _loc_6 = _loc_4.node;
                _loc_7 = null;
                if (_loc_6.next != null)
                {
                    _loc_7 = _loc_6.next;
                }
                if (!_loc_7)
                {
                    _loc_7 = this.m_listOfLists.head;
                }
                _loc_5.nextList =(AnimationList) _loc_7.data;
                _loc_8 = null;
                if (_loc_6.prev != null)
                {
                    _loc_8 = _loc_6.prev;
                }
                if (!_loc_8)
                {
                    _loc_8 = this.m_listOfLists.tail;
                }
                _loc_5.prevList =(AnimationList) _loc_8.data;
                if (_loc_4.next() == null)
                {
                    break;
                }
            }
            this.m_nextListToProcess =(AnimationList) this.m_listOfLists.head.data;
            return;
        }//end

        public boolean  remove (IAnimated param1 )
        {
            AnimationList _loc_3 =null ;
            _loc_2 = this.m_listOfLists.getListIterator ();
            _loc_2.start();
            while (_loc_2.data != null)
            {

                _loc_3 = _loc_2.data;
                if (_loc_3.contains(param1))
                {
                    _loc_3.remove(param1);
                    return true;
                }
                _loc_2.next();
            }
            return false;
        }//end

        public AnimationList  add (IAnimated param1 )
        {
            int _loc_7 =0;
            _loc_2 = this.m_nextListToProcess.prevList ;
            _loc_3 = _loc_2.m_animations.size ;
            _loc_4 = this.m_listOfLists.size ;
            _loc_5 = _loc_2.prevList ;
            int _loc_6 =0;
            while (_loc_3 && _loc_6 < _loc_4)
            {

                _loc_7 = _loc_5.m_animations.size;
                if (_loc_7 < _loc_3)
                {
                    _loc_2 = _loc_5;
                    _loc_3 = _loc_7;
                }
                _loc_6++;
                _loc_5 = _loc_5.prevList;
            }
            _loc_2.add(param1);
            this.m_nextListToProcess = _loc_2;
            return _loc_2;
        }//end

        public void  process (int param1 )
        {
            this.m_nextListToProcess.process(param1);
            this.m_nextListToProcess = this.m_nextListToProcess.nextList;
            return;
        }//end

    }


