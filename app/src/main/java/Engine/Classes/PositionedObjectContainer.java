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
import Engine.Interfaces.*;

    public class PositionedObjectContainer extends SavedObject
    {
        protected int m_redrawLock ;
        protected Array m_children ;
        private Array m_allDescendents ;
        private boolean m_allDescendentsDirty =true ;
        protected Array m_deletionList ;
        protected IntVector2 m_size ;
        protected Array m_animationBuckets ;

        public  PositionedObjectContainer (int param1 ,int param2 )
        {
            this.m_children = new Array();
            this.m_allDescendents = new Array();
            this.m_deletionList = new Array();
            this.m_size = new IntVector2();
            this.m_animationBuckets = new Array();
            this.m_size.x = param1;
            this.m_size.y = param2;
            double _loc_3 =0;
            while (_loc_3 < 100)
            {

                this.m_animationBuckets.put(_loc_3,  new Array());
                _loc_3 = _loc_3 + 1;
            }
            return;
        }//end

         public Object  getSaveObject ()
        {
            Object _loc_4 =null ;
            _loc_1 = super.getSaveObject();
            Array _loc_2 =new Array ();
            int _loc_3 =0;
            while (_loc_3 < this.m_children.length())
            {

                _loc_4 = this.m_children.get(_loc_3).getSaveObject();
                if (_loc_4 != null)
                {
                    _loc_2.push(this.m_children.get(_loc_3).getSaveObject());
                }
                _loc_3++;
            }
            _loc_1.objects = _loc_2;
            return _loc_1;
        }//end

        public void  setGridSize (int param1 ,int param2 )
        {
            this.m_size.x = param1;
            this.m_size.y = param2;
            return;
        }//end

        public int  getGridWidth ()
        {
            return this.m_size.x;
        }//end

        public int  getGridHeight ()
        {
            return this.m_size.y;
        }//end

        public void  lockRedraw ()
        {
            this.m_redrawLock++;
            return;
        }//end

        public void  unlockRedraw ()
        {
            this.m_redrawLock--;
            if (this.isRedrawLocked() == false)
            {
                this.conditionallyRedrawAllObjects();
            }
            return;
        }//end

        final public Array  getObjects ()
        {
            int _loc_1 =0;
            if (this.m_allDescendentsDirty)
            {
                this.m_allDescendents = new Array();
                _loc_1 = 0;
                while (_loc_1 < this.m_children.length())
                {

                    if (this.m_children.get(_loc_1) instanceof IEngineObject)
                    {
                        this.m_allDescendents = this.m_allDescendents.concat(this.m_children.get(_loc_1).children);
                    }
                    _loc_1++;
                }
                this.m_allDescendentsDirty = false;
            }
            return this.m_allDescendents;
        }//end

        final public void  setObjectsDirty ()
        {
            this.m_allDescendentsDirty = true;
            return;
        }//end

        public boolean  isRedrawLocked ()
        {
            return this.m_redrawLock != 0;
        }//end

        public void  conditionallyRedrawAllObjects ()
        {
            return;
        }//end

        public int  getNumObjects ()
        {
            return this.getObjects().length;
        }//end

        public int  getNumVisibleObjects ()
        {
            PositionedObject _loc_4 =null ;
            int _loc_1 =0;
            _loc_2 = this.getObjects ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 =(PositionedObject) _loc_2.get(_loc_3);
                if (_loc_4 && _loc_4.isVisible())
                {
                    _loc_1++;
                }
                _loc_3++;
            }
            return _loc_1;
        }//end

        public void  addToDeletionList (PositionedObject param1 )
        {
            if (this.m_deletionList.indexOf(param1) == -1)
            {
                this.m_deletionList.push(param1);
            }
            return;
        }//end

        public void  removeFromDeletionList (PositionedObject param1 )
        {
            _loc_2 = this.m_deletionList.indexOf(param1 );
            if (_loc_2 != -1)
            {
                this.m_deletionList.splice(_loc_2, 1);
            }
            return;
        }//end

        public Array  animationBuckets ()
        {
            return this.m_animationBuckets;
        }//end

        public void  resetBuckets ()
        {
            this.m_animationBuckets = new Array();
            double _loc_1 =0;
            while (_loc_1 < 100)
            {

                this.m_animationBuckets.put(_loc_1,  new Array());
                _loc_1 = _loc_1 + 1;
            }
            return;
        }//end

        public int  insertItemIntoAnimationBucket (PositionedObject param1 )
        {
            _loc_2 = Math.floor(Math.random ()*100);
            _loc_3 = this.m_animationBuckets.get(_loc_2) ;
            _loc_3.push(param1);
            return _loc_2;
        }//end

        public void  removeItemFromAnimationBucket (PositionedObject param1 ,int param2 )
        {
            _loc_3 = this.m_animationBuckets.get(param2) ;
            _loc_4 = _loc_3.indexOf(param1 );
            _loc_3.splice(_loc_4, 1);
            return;
        }//end

    }



