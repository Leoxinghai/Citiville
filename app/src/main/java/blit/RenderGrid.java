package blit;

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

import Engine.Classes.*;
import de.polygonal.ds.*;
//import flash.geom.*;

    public class RenderGrid
    {
        private double m_width ;
        private double m_height ;
        private double m_xstep ;
        private double m_ystep ;
        private double m_div ;
        private DLinkedList m_dirtyRects ;
        private DLinkedList m_intersectingRects ;
        private int m_numIntersectingRects ;
        private boolean m_created =false ;

        public  RenderGrid ()
        {
            this.m_dirtyRects = new DLinkedList();
            this.m_intersectingRects = new DLinkedList();
            return;
        }//end

        public void  init (Point param1 ,int param2 ,int param3 ,int param4 )
        {
            this.m_created = true;
            return;
        }//end

        public void  clearGrid ()
        {
            this.m_dirtyRects.clear();
            this.m_intersectingRects.clear();
            return;
        }//end

        public boolean  created ()
        {
            return this.m_created;
        }//end

        public DLinkedList  dirtyRects ()
        {
            return this.m_dirtyRects;
        }//end

        public void  addDirtyRect (Rectangle param1 ,boolean param2 =false )
        {
            Rectangle _loc_3 =null ;
            Rectangle _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            DListNode _loc_7 =null ;
            DListIterator _loc_8 =null ;
            if (!param2)
            {
                this.m_dirtyRects.append(param1);
            }
            else
            {
                _loc_3 = param1.clone();
                _loc_5 = false;
                _loc_6 = false;
                while (!_loc_5)
                {

                    _loc_7 = this.m_dirtyRects.head;
                    while (_loc_7)
                    {

                        _loc_4 =(Rectangle) _loc_7.data;
                        if (_loc_3.intersects(_loc_4))
                        {
                            _loc_3 = _loc_4.union(_loc_3);
                            _loc_8 = new DListIterator(this.m_dirtyRects, _loc_7);
                            _loc_8.remove();
                            _loc_6 = true;
                            break;
                        }
                        _loc_7 = _loc_7.next;
                    }
                    if (!_loc_6)
                    {
                        this.m_dirtyRects.append(param1);
                        _loc_5 = true;
                    }
                }
            }
            return;
        }//end

        public void  markDirtyRegion (PositionedObject param1 ,Rectangle param2 )
        {
            return;
        }//end

        public int  intersectDirtyRegion (PositionedObject param1 ,Rectangle param2 )
        {
            this.m_numIntersectingRects = 0;
            return this.m_numIntersectingRects;
        }//end

    }



