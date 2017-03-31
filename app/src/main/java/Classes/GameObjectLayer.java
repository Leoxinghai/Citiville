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

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.utils.*;
import Classes.*;
import com.xinghai.Debug;

    public class GameObjectLayer Layer
    {
        public boolean recomputeNextUpdateTime =false ;
        private Dictionary cache =null ;
        private static boolean m_optimizeOnupdateCulling =true ;
        private static int overrideCulling =0;
        private static  double INV_1000 =0.001;

        public  GameObjectLayer (String param1 ,double param2)
        {
            super(param1, param2);

            return;
        }//end

         public void  update ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            WorldObject _loc_5 =null ;
            double _loc_6 =0;


            if (useCulling())
            {
                _loc_1 = GlobalEngine.currentTime;
                _loc_2 = Global.world.defCon;
                _loc_3 = m_children.length;
                _loc_4 = 0;
                while (_loc_4 < _loc_3)
                {

                    _loc_5 =(WorldObject) m_children.get(_loc_4);


                    if(_loc_5 instanceof Train) {




			    Sprite test0 =Sprite(_loc_5.getDisplayObject ());

			    if(test0.y <-200) {
			       test0.y = -200;
			       Global.circle2.addChild(test0);

			     } else {
			        test0.y = test0.y +1;
			     }

*/

                    }

                    else if(_loc_5 instanceof DesirePeep) {
			    Sprite test1 =Sprite(_loc_5.getDisplayObject ());

                    }

                    */


                    if (_loc_5)
                    {
                        if (this.recomputeNextUpdateTime)
                        {
                            _loc_5.calcNextUpdateTime(_loc_2);
                        }
                        if (_loc_1 >= _loc_5.nextUpdateTime)
                        {
                            _loc_6 = _loc_1 - _loc_5.lastUpdateTime;
                            _loc_5.updateDelta(_loc_6 * INV_1000);
                            _loc_5.calcNextUpdateTime(_loc_2);
                        }
                    }
                    _loc_4 = _loc_4 + 1;
                }
                this.recomputeNextUpdateTime = false;
            }
            else
            {
                super.update();
            }
            return;
        }//end

         public void  updateObjectInDepthArray (WorldObject param1 )
        {
            DisplayObject _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            if (param1.isAttached())
            {
                _loc_2 = param1.getDisplayObject();
                if (_loc_2 && _loc_2.parent)
                {
                    _loc_3 = _loc_2.parent.getChildIndex(_loc_2);
                    _loc_4 = -1;
                    _loc_5 = 2;
                    this.cache = new Dictionary();
                    if (_loc_3 != -1)
                    {
                        _loc_6 = _loc_3 > 0 ? (this.memoizedCompareWorldObjects(param1, WorldObject(m_children.get((_loc_3 - 1))))) : (0);
                        _loc_7 = _loc_3 < (m_children.length - 1) ? (this.memoizedCompareWorldObjects(param1, WorldObject(m_children.get((_loc_3 + 1))))) : (0);
                        if (_loc_6 >= 0 && _loc_7 <= 0)
                        {
                            return;
                        }
                        m_children.splice(_loc_3, 1);
                        _loc_4 = this.searchWithinBounds(param1, _loc_3 - _loc_5 - 1, _loc_3 + _loc_5);
                    }
                    if (_loc_4 == -1)
                    {
                        _loc_4 = binarySearch(param1);
                    }
                    m_children.splice(_loc_4, 0, param1);
                    _loc_2.parent.setChildIndex(_loc_2, _loc_4);
                }
                else
                {
                    insertObjectIntoDepthArray(param1);
                }
            }
            return;
        }//end

        protected int  searchWithinBounds (WorldObject param1 ,int param2 ,int param3 )
        {
            _loc_4 = param2-1;
            _loc_5 = param3+1;
            _loc_6 = this.binarySearchWithinBounds(param1,_loc_4,_loc_5);
            if (this.binarySearchWithinBounds(param1, _loc_4, _loc_5) == _loc_4)
            {
                return this.binarySearchWithinBounds(param1, 0, _loc_6);
            }
            if (_loc_6 == _loc_5)
            {
                return this.binarySearchWithinBounds(param1, _loc_6, m_children.length());
            }
            return _loc_6;
        }//end

        private int  binarySearchWithinBounds (WorldObject param1 ,int param2 ,int param3 )
        {
            double _loc_5 =0;
            if (param2 < 0)
            {
                param2 = 0;
            }
            if (param3 > m_children.length())
            {
                param3 = m_children.length;
            }
            _loc_4 = param2+param3 >>1;
            while (param2 < param3)
            {

                _loc_5 = this.memoizedCompareWorldObjects(param1, WorldObject(m_children.get(_loc_4)));
                if (_loc_5 < 0)
                {
                    param3 = _loc_4;
                    _loc_4 = param2 + param3 >> 1;
                    continue;
                }
                if (_loc_5 > 0)
                {
                    param2 = _loc_4 + 1;
                    _loc_4 = param2 + param3 >> 1;
                    continue;
                }
                break;
            }
            return _loc_4;
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
            _loc_3 = param1.getDepthPriority();
            _loc_4 = param2.getDepthPriority();
            _loc_5 = param2.depthIndex-param1.depthIndex;
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

        private double  memoizedCompareWorldObjects (WorldObject param1 ,WorldObject param2 )
        {
            double _loc_4 =0;
            _loc_3 = param2;
            if (this.cache.get(_loc_3))
            {
                return this.cache.get(_loc_3);
            }
            _loc_4 = this.compareWorldObjects(param1, param2);
            this.cache.put(_loc_3,  _loc_4);
            return _loc_4;
        }//end

        public static void  toggleCulling ()
        {
            if (overrideCulling++ > 2)
            {
                overrideCulling = 1;
            }
            return;
        }//end

        public static boolean  useCulling ()
        {
            return overrideCulling >= 2 || m_optimizeOnupdateCulling && overrideCulling == 0;
        }//end

    }



