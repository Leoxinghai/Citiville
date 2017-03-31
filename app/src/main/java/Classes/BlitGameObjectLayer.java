package Classes;

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

import com.xiyu.logic.DLinkedList;
import com.xiyu.logic.DisplayObject;
import com.xiyu.logic.Layer;
import com.xiyu.logic.Sprite;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.Classes.*;
import Engine.Helpers.*;
import de.polygonal.ds.*;
import root.GlobalEngine;
//import flash.display.*;
//import flash.geom.*;

    public class BlitGameObjectLayer extends ObjectLayer
    {
        private DLinkedList m_dirtyObjectList ;
        private static int debugCounter =0;

        public  BlitGameObjectLayer (String param1 ,double param2)
        {
            super(param1, param2);
            this.m_dirtyObjectList = new DLinkedList();
            return;
        }//end

        public void  clearDirtyObjectList ()
        {
            this.m_dirtyObjectList.clear();
            return;
        }//end

        public boolean  dirty ()
        {
            return this.m_dirtyObjectList.size() > 0 ? (true) : (false);
        }//end

        public DLinkedList  dirtyObjectList ()
        {
            return this.m_dirtyObjectList;
        }//end

         public Array  children ()
        {
            return m_children;
        }//end

         public void  cleanUp ()
        {
            WorldObject _loc_3 =null ;
            _loc_1 = m_children.concat([]);
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(WorldObject) _loc_1.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_3.detatch();
                    _loc_3.cleanUp();
                }
                _loc_2++;
            }
            m_displayObject = new Sprite();
            return;
        }//end

         public void  attach ()
        {
            Viewport _loc_1 = (IsoViewport) GlobalEngine.viewport;
            _loc_1.addObjectLayer(this);
            return;
        }//end

         public void  detach ()
        {
            IsoViewport _loc_1 = (IsoViewport)GlobalEngine.viewport;
            _loc_1.removeObjectLayer(this);
            return;
        }//end

         public void  render (RenderContext param1 )
        {
            int _loc_2 = m_children.length();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                m_children.get(_loc_3).render(param1);
                _loc_3++;
            }
            return;
        }//end

        public void  postRender ()
        {
            this.clearDirtyObjectList();
            return;
        }//end

         public void  insertObjectIntoDepthArray (WorldObject param1 ,String param2 )
        {
            Sprite _loc_5 =null ;
            _loc_3 = this.findInsertionPoint(param1);
            m_children.splice(_loc_3, 0, param1);
            DisplayObject _loc_4 = param1.getDisplayObject();
            if (param1.getDisplayObject() == null)
            {
                _loc_5 =(Sprite) this.getDisplayObject();
                _loc_5.addChildAt(param1.createDisplayObject(), _loc_3);
            }
            else if (_loc_4.parent == null)
            {
                _loc_5 =(Sprite) this.getDisplayObject();
                _loc_5.addChildAt(_loc_4, _loc_3);
            }
            else
            {
                _loc_4.parent.setChildIndex(_loc_4, _loc_3);
            }
            return;
        }//end

         public void  updateObjectInDepthArray (WorldObject param1 )
        {
            DisplayObject _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            if (param1.isAttached())
            {
                _loc_2 = param1.getDisplayObject();
                if (_loc_2 && _loc_2.parent)
                {
                    _loc_3 = m_children.indexOf(param1);
                    if (_loc_3 >= 0 && _loc_3 < m_children.length())
                    {
                        m_children.splice(_loc_3, 1);
                    }
                    _loc_4 = this.findInsertionPoint(param1);
                    m_children.splice(_loc_4, 0, param1);
                    _loc_2.parent.setChildIndex(_loc_2, _loc_4);
                }
                else
                {
                    this.insertObjectIntoDepthArray(param1);
                }
            }
            this.m_dirtyObjectList.append(param1);
            return;
        }//end

        private int  findInsertionPoint (PositionedObject param1 )
        {
            PositionedObject _loc_6 =null ;
            int _loc_2 =0;
            _loc_3 = m_children.length;
            _loc_4 = _loc_2+_loc_3 >>1;
            int _loc_5 =0;
            while (_loc_2 < _loc_3)
            {

                _loc_6 =(PositionedObject) m_children.get(_loc_4);
                _loc_5 = this.sortObjects(param1, _loc_6);
                if (_loc_5 == -1)
                {
                    _loc_3 = _loc_4;
                    _loc_4 = _loc_2 + _loc_3 >> 1;
                    continue;
                }
                if (_loc_5 == 1)
                {
                    _loc_2 = _loc_4 + 1;
                    _loc_4 = _loc_2 + _loc_3 >> 1;
                    continue;
                }
                if (_loc_5 == 0)
                {
                    break;
                }
            }
            return _loc_4;
        }//end

         public boolean  checkObjectDepthLocation (DisplayObject param1 ,int param2 )
        {
            return param1 == param1.parent.getChildAt(param2);
        }//end

        protected int  sortObjects (Object param1 ,Object param2 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            WorldObject _loc_5 = (WorldObject)param1;
            WorldObject _loc_6 = (WorldObject)param2;
            Vector3 _loc_7 = _loc_5.getDepthPositionNoClone();
            Vector3 _loc_8 = _loc_6.getDepthPositionNoClone();
            Vector3 _loc_9 = _loc_5.getEndPosition();
            Vector3 _loc_10 = _loc_6.getEndPosition();
            if (_loc_6.getDepthPriority() > _loc_5.getDepthPriority())
            {
                _loc_4 = -1;
            }
            else if (_loc_6.getDepthPriority() < _loc_5.getDepthPriority())
            {
                _loc_4 = 1;
            }
            if ((_loc_8.x >= _loc_9.x && (_loc_8.y < _loc_9.y && _loc_10.y > _loc_7.y) || _loc_8.y >= _loc_9.y && (_loc_8.x < _loc_9.x && _loc_10.x > _loc_7.x)) && _loc_4 == 0)
            {
                _loc_3 = 1;
            }
            else if ((_loc_7.x >= _loc_10.x && (_loc_7.y < _loc_10.y && _loc_9.y > _loc_8.y) || _loc_7.y >= _loc_10.y && (_loc_7.x < _loc_10.x && _loc_9.x > _loc_8.x)) && _loc_4 == 0)
            {
                _loc_3 = -1;
            }
            else if (_loc_6.depthIndex > _loc_5.depthIndex)
            {
                _loc_3 = 1;
            }
            else if (_loc_6.depthIndex < _loc_5.depthIndex)
            {
                _loc_3 = -1;
            }
            return _loc_3;
        }//end

         public void  removeObjectFromDepthArray (WorldObject param1 )
        {
            DisplayObject _loc_3 =null ;
            int _loc_2 = m_children.indexOf(param1);
            if (_loc_2 >= 0 && _loc_2 < m_children.length())
            {
                m_children.splice(_loc_2, 1);
                _loc_3 = param1.getDisplayObject();
                if (_loc_3 != null && _loc_3.parent != null)
                {
                    _loc_3.parent.removeChild(_loc_3);
                }
            }
            return;
        }//end

         public PositionedObject  pickObject (Point param1 ,int param2 =1.67772e +007,int param3 =0)
        {
            int _loc_4 =0;
            PositionedObject _loc_6 =null ;
            PositionedObject _loc_5 =null ;
            _loc_4 = m_children.length - 1;
            while (_loc_4 >= 0)
            {

                _loc_6 =(PositionedObject) m_children.get(_loc_4);
                _loc_5 =(PositionedObject) _loc_6.pickObject(param1, param2, param3);
                if (_loc_5 != null)
                {
                    break;
                }
                _loc_4 = _loc_4 - 1;
            }
            return _loc_5;
        }//end

    }




