package Engine.Classes;

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

import Engine.Helpers.*;
//import flash.display.*;
//import flash.geom.*;
import Engine.Interfaces.*;
import Classes.Decoration;
import Classes.*;

import com.xinghai.Debug;

    public class ObjectLayer extends PositionedObject implements IEngineObject, IRenderObject
    {
        protected Array m_children ;
        public double priority ;
        public String name ;

        public  ObjectLayer (String param1 ,double param2)
        {
            this.m_children = new Array();
            this.name = param1;
            this.priority = param2;
            this.m_displayObject = new Sprite();
            return;
        }//end

        public Array  children ()
        {
            return this.m_children;
        }//end

        public void  cleanUp ()
        {
            WorldObject _loc_3 =null ;
            _loc_1 = this.m_children.concat([] );
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(WorldObject) _loc_1.get(_loc_2);
                if (_loc_3 != null)
                {
                    _loc_3.detach();
                    _loc_3.cleanUp();
                }
                _loc_2++;
            }
            m_displayObject = new Sprite();
            return;
        }//end

        public void  update ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_children.length())
            {

                WorldObject(this.m_children.get(_loc_1)).update();
                _loc_1++;
            }
            return;
        }//end

        public void  attach ()
        {
            _loc_1 = IsoViewport(GlobalEngine.viewport);
            _loc_1.addObjectLayer(this);

            return;
        }//end

        public void  detach ()
        {
            _loc_1 = IsoViewport(GlobalEngine.viewport);
            _loc_1.removeObjectLayer(this);
            return;
        }//end

        public void  render (RenderContext param1 )
        {
            _loc_2 = this.m_children.length ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                WorldObject(this.m_children.get(_loc_3)).render(param1);
                _loc_3++;
            }
            return;
        }//end

        public void  insertObjectIntoDepthArray (WorldObject param1 ,String param2 )
        {


	   int ii =GlobalEngine.viewport.objectBase.numChildren ;
            for(;ii > 0;ii--) {
            	Debug.debug4("objectBase."+GlobalEngine.viewport.objectBase.getChildAt(ii-1).name);
            }

	    ii = GlobalEngine.viewport.numChildren;
            for(;ii > 0;ii--) {
            	Debug.debug4("viewport.children "+GlobalEngine.viewport.getChildAt(ii-1).name);
            }
*/


            int _loc_3 =this.binarySearch(param1 );
            this.m_children.splice(_loc_3, 0, param1);
            _loc_4 = param1.getDisplayObject ();
            Sprite _loc_5 =Sprite(m_displayObject );//Sprite(getDisplayObject ());



            if (_loc_4 == null)
            {


		Sprite loc_4 =Sprite(param1.createDisplayObject ());



		_loc_5.addChildAt(loc_4, _loc_3);

                //_loc_5.addChildAt(param1.createDisplayObject(), _loc_3);



                //GlobalEngine.viewport.objectBase.addChildAt(param1.createDisplayObject(), 6);
            }
            else if (_loc_4.parent == null)
            {
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
                    _loc_3 = this.m_children.indexOf(param1);
                    if (_loc_3 != -1)
                    {
                        this.m_children.splice(_loc_3, 1);
                    }
                    _loc_4 = this.binarySearch(param1);
                    this.m_children.splice(_loc_4, 0, param1);
                    _loc_2.parent.setChildIndex(_loc_2, _loc_4);
                }
                else
                {
                    this.insertObjectIntoDepthArray(param1);
                }
            }
            return;
        }//end

        protected int  binarySearch (WorldObject param1 )
        {
            double _loc_5 =0;
            int _loc_2 =0;
            int _loc_3 =this.m_children.length ;
            Debug.debug4("binarySearch.1.  "+_loc_3);
            _loc_4 = _loc_3>>1;
            while (_loc_2 < _loc_3)
            {

                _loc_5 = this.compareWorldObjects(param1, WorldObject(this.m_children.get(_loc_4)));
                if (_loc_5 < 0)
                {
                    _loc_3 = _loc_4;
                    _loc_4 = _loc_2 + _loc_3 >> 1;
                    continue;
                }
                if (_loc_5 > 0)
                {
                    _loc_2 = _loc_4 + 1;
                    _loc_4 = _loc_2 + _loc_3 >> 1;
                    continue;
                }
                break;
            }
            Debug.debug4("binarySearch.2.  "+_loc_4);
            return _loc_4;
        }//end

        public boolean  checkObjectDepthLocation (DisplayObject param1 ,int param2 )
        {
            return param1 == param1.parent.getChildAt(param2);
        }//end

        protected double  compareWorldObjects (WorldObject param1 ,WorldObject param2 )
        {
            Vector3 _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            Vector3 _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            Vector3 _loc_12 =null ;
            double _loc_13 =0;
            double _loc_14 =0;
            Vector3 _loc_15 =null ;
            double _loc_16 =0;
            double _loc_17 =0;
            _loc_3 = param1.getDepthPriority ();
            _loc_4 = param2.getDepthPriority ();
            _loc_5 = param2.depthIndex -param1.depthIndex ;
            if (_loc_4 == _loc_3)
            {
                _loc_6 = param1.getDepthPositionNoClone();
                _loc_7 = _loc_6.x;
                _loc_8 = _loc_6.y;
                _loc_9 = param1.getEndPosition();
                _loc_10 = _loc_9.x;
                _loc_11 = _loc_9.y;
                _loc_12 = param2.getDepthPositionNoClone();
                _loc_13 = _loc_12.x;
                _loc_14 = _loc_12.y;
                _loc_15 = param2.getEndPosition();
                _loc_16 = _loc_15.x;
                _loc_17 = _loc_15.y;
                if (_loc_5 <= 0 && (_loc_13 >= _loc_10 && _loc_14 < _loc_11 && _loc_17 > _loc_8 || _loc_14 >= _loc_11 && _loc_13 < _loc_10 && _loc_16 > _loc_7))
                {
                    _loc_5 = 1;
                }
                else if (_loc_5 >= 0 && (_loc_7 >= _loc_16 && _loc_8 < _loc_17 && _loc_11 > _loc_14 || _loc_8 >= _loc_17 && _loc_7 < _loc_16 && _loc_10 > _loc_13))
                {
                    _loc_5 = -1;
                }
            }
            return _loc_5;
        }//end

        public void  removeObjectFromDepthArray (WorldObject param1 )
        {
            DisplayObject _loc_3 =null ;
            _loc_2 = this.m_children.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_children.splice(_loc_2, 1);
                _loc_3 = param1.getDisplayObject();
                if (_loc_3 && _loc_3.parent)
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
            _loc_4 = this.m_children.length - 1;
            while (_loc_4 >= 0)
            {

                _loc_6 =(PositionedObject) this.m_children.get(_loc_4);
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



