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

import com.xiyu.logic.DisplayObject;
import com.xiyu.logic.Sprite;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.*;
import Engine.Helpers.*;

//import flash.display.*;
//import flash.geom.*;
import Engine.Interfaces.*;

import Classes.*;
import root.GlobalEngine;

import com.xinghai.Debug;

import java.util.Vector;

public class WorldObject extends PositionedObject implements IEngineObject, IRenderObject
    {
        protected String m_typeName ;
        protected World m_outer ;
        protected int m_direction ;
        protected int m_depthIndex =0;
        protected int m_collisionFlags =32;
        protected WorldObject m_parent =null ;
        protected Array m_children ;
        public boolean renderBufferDirty =true ;
        private int m_shard =0;
        private int m_distribution ;
        protected int m_animationBucket ;
        public int lastUpdateTime ;
        public int nextUpdateTime ;
        private static Vector3 scratchv3 =new Vector3(0, 0, 0);
        private static int m_distributionPool =0;
        public static Vector ONSCREEN_UPDATE_HIGH = new Vector(12);
        public static  Vector ONSCREEN_UPDATE_NORM = new Vector(12);
        public static  Vector OFFSCREEN_UPDATE_TIME = new Vector(12);

        public  WorldObject ()
        {
            this.m_children = new Array();
            this.m_distribution = m_distributionPool + 1;
            this.lastUpdateTime = GlobalEngine.currentTime;
            this.nextUpdateTime = GlobalEngine.currentTime;
            return;
        }//end

        public Array  children ()
        {
            return new Array();
        }//end

        public String  getTypeName ()
        {
            return this.m_typeName;
        }//end

        public Array  getReferencedAssets ()
        {
            return new Array();
        }//end

         public Object  getSaveObject ()
        {
            Object _loc_1 = super.getSaveObject();
            _loc_1.direction = this.m_direction;
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_direction = param1.direction;
            return;
        }//end

        public void  cleanUp ()
        {
            this.m_outer = null;
            this.m_parent = null;
            this.m_children = new Array();
            return;
        }//end

        public int  shardingCategory ()
        {
            return ShardScheduler.CATEGORY_LOW_PRIORITY;
        }//end

        final public void  update ()
        {
            Sharder _loc_2 =null ;
            double _loc_3 =0;
            if (this.m_outer)
            {
                _loc_2 = this.m_outer.getSharder(this.shardingCategory);
                _loc_3 = _loc_2.getShardDelta(this.m_shard);
                if (_loc_3 >= 0)
                {
                    onUpdate(_loc_3);
                }
            }
            int _loc_1 =0;
            while (_loc_1 < this.m_children.length())
            {

                this.m_children.get(_loc_1).update();
                _loc_1++;
            }
            return;
        }//end

        final public void  updateDelta (double param1 )
        {
            int _loc_2 =0;
            this.lastUpdateTime = GlobalEngine.currentTime;
            if (this.m_outer)
            {
                onUpdate(param1);
                _loc_2 = 0;
                while (_loc_2 < this.m_children.length())
                {

                    this.m_children.get(_loc_2).updateDelta(param1);
                    _loc_2++;
                }
            }
            return;
        }//end

        public void  calcNextUpdateTime (int param1 )
        {
            int _loc_2 =0;
            if (this.shardingCategory == ShardScheduler.CATEGORY_HIGH_PRIORITY)
            {
                _loc_2 = isVisible() ? (ONSCREEN_UPDATE_HIGH.get(this.shardingCategory + param1)) : (OFFSCREEN_UPDATE_TIME.get(this.shardingCategory + param1));
            }
            else
            {
                _loc_2 = isVisible() ? (ONSCREEN_UPDATE_NORM.get(this.shardingCategory + param1)) : (OFFSCREEN_UPDATE_TIME.get(this.shardingCategory + param1));
            }
            _loc_3 = _loc_2*(this.m_distribution & 7) * 0.125;
            this.nextUpdateTime = (1 + int((GlobalEngine.currentTime - _loc_3) / _loc_2)) * _loc_2 + _loc_3;
            return;
        }//end

        public void  render (RenderContext param1 )
        {
            return;
        }//end

        protected void  setObjectsDirty ()
        {
            if (this.m_outer)
            {
                this.m_outer.setObjectsDirty();
            }
            return;
        }//end

         public void  markForDeletion (boolean param1 )
        {
            super.markForDeletion(param1);
            if (param1)
            {
                this.m_outer.addToDeletionList(this);
            }
            else
            {
                this.m_outer.removeFromDeletionList(this);
            }
            return;
        }//end

        public void  incrementTransactionsForChildren ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_children.length())
            {

                this.m_children.get(_loc_1).incrementTransactions();
                _loc_1++;
            }
            return;
        }//end

        public void  decrementTransactionsForChildren ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_children.length())
            {

                this.m_children.get(_loc_1).decrementTransactions();
                _loc_1++;
            }
            return;
        }//end

        public boolean  isIntersecting (WorldObject param1 )
        {
            _loc_2 = this.getDepthPositionNoClone ();
            _loc_3 = this.getDepthSize ();
            _loc_4 = param1.getDepthPositionNoClone ();
            _loc_5 = param1.getDepthSize ();
            _loc_6 = _loc_2.add(_loc_3 );
            _loc_7 = _loc_4.add(_loc_5 );
            boolean _loc_8 =false ;
            if (_loc_6.x < _loc_4.x || _loc_2.x > _loc_7.x || _loc_6.y < _loc_4.y || _loc_2.y > _loc_7.y || _loc_6.z < _loc_4.z || _loc_2.z > _loc_7.z)
            {
                _loc_8 = false;
            }
            else if (_loc_3.x == 0 && _loc_5.x > 0 && (_loc_4.x >= _loc_2.x || _loc_7.x <= _loc_2.x) || _loc_3.y == 0 && _loc_5.y > 0 && (_loc_4.y >= _loc_2.y || _loc_7.y <= _loc_2.y) || _loc_5.x == 0 && _loc_3.x > 0 && (_loc_2.x >= _loc_4.x || _loc_6.x <= _loc_4.x) || _loc_5.y == 0 && _loc_3.y > 0 && (_loc_2.y >= _loc_4.y || _loc_6.y <= _loc_4.y))
            {
                _loc_8 = false;
            }
            else
            {
                _loc_8 = true;
            }
            return _loc_8;
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {

            Vector3 _loc_4 =null ;
            int _loc_5 =0;
            WorldObject _loc_6 =null ;
            if (m_position.x != param1 || m_position.y != param2 || m_position.z != param3)
            {
                _loc_4 = new Vector3(param1 - m_position.x, param2 - m_position.y, param3 - m_position.z);
                m_position = new Vector3(param1, param2, param3);
                if (this.m_outer != null)
                {
                    m_screenPosition = IsoMath.tilePosToPixelPos(m_position.x, m_position.y, m_position.z, true);
                }
                m_transformDirty = true;
                _loc_5 = 0;
                while (_loc_5 < this.m_children.length())
                {

                    _loc_6 =(WorldObject) this.m_children.get(_loc_5);
                    _loc_6.translate(_loc_4);
                    _loc_5++;
                }
            }
            return;
        }//end

        public void  setDirection (int param1 )
        {
            this.m_direction = param1;
            return;
        }//end

        public void  setCollisionFlags (int param1 )
        {
            this.m_collisionFlags = param1;
            return;
        }//end

        public int  getCollisionFlags (int param1 ,int param2 )
        {
            return this.m_collisionFlags;
        }//end

        public boolean  checkInternalCollision (int param1 ,int param2 ,int param3 )
        {
            return CollisionMap.compareCollisionFlags(this.getCollisionFlags(param1, param2), param3);
        }//end

        public void  setOuter (World param1 )
        {
            this.m_outer = param1;
            if (param1 != null)
            {
                this.m_shard = this.m_outer.getSharder(this.shardingCategory).getRandomShard();
            }
            return;
        }//end

        public PositionedObjectContainer  getOuter ()
        {
            return this.m_outer;
        }//end

        public int  getDirection ()
        {
            return this.m_direction;
        }//end

        public Vector3  getDepthSize ()
        {
            return m_size.clone();
        }//end

        public Vector3  getDepthSizeNoClone ()
        {
            return m_size;
        }//end

        public double  getDepthPriority ()
        {
            return 0;
        }//end

        public Vector3  getDepthPosition ()
        {
            return m_position.clone();
        }//end

        public Vector3  getDepthPositionNoClone ()
        {
            return m_position;
        }//end

        public boolean  isCollidable ()
        {
            return this.m_collisionFlags != 0;
        }//end

        protected String  getLayerName ()
        {
            return null;
        }//end

        public void  attach ()
        {
            GlobalEngine.assert(this.m_outer != null, "Outer for WorldObject instanceof null!");
            if (m_attachPosition == null)
            {
                this.calculateDepthIndex();
                this.m_outer.insertObjectIntoDepthArray(this, this.getLayerName());
                this.setObjectsDirty();
                this.m_outer.insertObjectIntoCollisionMap(this);
                this.m_animationBucket = this.m_outer.insertItemIntoAnimationBucket(this);
                updateDisplayObjectTransform();
                this.updateCulling();
                m_attachPosition = m_position.clone();
                m_attachSize = m_size.clone();
                m_transformDirty = false;
            }
            return;
        }//end

        public void  detach ()
        {
            if (m_attachPosition != null && this.m_outer != null)
            {
                this.m_outer.removeObjectFromCollisionMap(this);
                this.m_outer.removeObjectFromDepthArray(this);
                this.m_outer.removeItemFromAnimationBucket(this, this.m_animationBucket);
                m_attachPosition = null;
                m_attachSize = null;
                m_displayObjectDirty = true;
                this.setObjectsDirty();
            }
            return;
        }//end

        public void  detatch ()
        {
            this.detach();
            return;
        }//end

        public void  conditionallyRedraw (boolean param1 =false )
        {
            if (this.m_outer != null && this.m_outer.isRedrawLocked() == false && (m_displayObjectDirty || param1))
            {
                drawDisplayObject();
                this.updateCulling();
                this.toggleAnimation();
                m_displayObjectDirty = false;
            }
            return;
        }//end

        public void  conditionallyReattach (boolean param1 =false )
        {
            WorldObject _loc_3 =null ;
            if (isAttached() == false)
            {
                this.attach();
            }
            else if (param1 || m_transformDirty || m_displayObjectDirty || m_attachPosition == null)
            {
                this.m_outer.removeObjectFromCollisionMap(this);
                this.setObjectsDirty();
                if (m_displayObjectDirty)
                {
                    drawDisplayObject();
                }
                else
                {
                    updateDisplayObjectTransform();
                }
                this.calculateDepthIndex();
                this.m_outer.updateObjectInDepthArray(this);
                this.m_outer.insertObjectIntoCollisionMap(this);
                this.updateCulling();
                m_attachPosition = m_position.clone();
                m_attachSize = m_size.clone();
                m_transformDirty = false;
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_children.length())
            {

                _loc_3 =(WorldObject) this.m_children.get(_loc_2);
                _loc_3.conditionallyReattach();
                _loc_2++;
            }
            return;
        }//end

         public void  updateCulling ()
        {
            PositionedObject _loc_2 =null ;
            super.updateCulling();
            this.calcNextUpdateTime(World.getInstance().defCon);
            int _loc_1 =0;
            while (_loc_1 < this.m_children.length())
            {

                _loc_2 =(PositionedObject) this.m_children.get(_loc_1);
                if (_loc_2 != null)
                {
                    _loc_2.updateCulling();
                }
                _loc_1++;
            }
            return;
        }//end

        public DisplayObject getBoundingBoxSprite (Sprite param1)
        {
            if (param1 == null)
            {
                param1 = new Sprite();
            }
            Vector2 _loc_2 = IsoMath.tilePosToPixelPos(m_position.x ,m_position.y ,m_position.z, 0, false);
            Vector2 _loc_3 = IsoMath.tilePosToPixelPos(m_position.x +m_size.x ,m_position.y ,m_position.z , 0, false);
            Vector2 _loc_4 = IsoMath.tilePosToPixelPos(m_position.x ,m_position.y +m_size.y ,m_position.z , 0, false);
            Vector2 _loc_5 = IsoMath.tilePosToPixelPos(m_position.x +m_size.x ,m_position.y +m_size.y ,m_position.z , 0, false);
            param1.graphics.lineStyle(3, 16737792);
            param1.graphics.moveTo(_loc_2.x, _loc_2.y);
            param1.graphics.lineTo(_loc_2.x, _loc_2.y - m_size.z);
            param1.graphics.lineTo(_loc_4.x, _loc_4.y - m_size.z);
            param1.graphics.lineTo(_loc_4.x, _loc_4.y);
            param1.graphics.lineTo(_loc_2.x, _loc_2.y);
            return param1;
        }//end

        public Vector3  getFurthestTilePosition ()
        {
            Vector3 _loc_1 = this.getDepthPositionNoClone ();
            Vector3 _loc_2 = this.getDepthSize ();
            World _loc_3 = World.getInstance();
            Vector3 _loc_4 = scratchv3;
            int _loc_5 =0;
            scratchv3.z = 0;
            scratchv3.y = 0;
            scratchv3.x = 0;
            switch(_loc_3.getRotation())
            {
                case Constants.ROTATION_0:
                {
                    _loc_4.x = _loc_2.x;
                    _loc_4.y = _loc_2.y;
                    break;
                }
                case Constants.ROTATION_90:
                {
                    _loc_4.y = _loc_2.y;
                    break;
                }
                case Constants.ROTATION_180:
                {
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_4.x = _loc_2.x;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1.add(_loc_4);
        }//end

        protected void  calculateDepthIndex ()
        {
            Vector3 _loc_1 = this.getNearestTilePosition ();
            Vector3 _loc_2 = this.getFurthestTilePosition ();
            double _loc_3 = (_loc_1.x +_loc_2.x )/2;
            this.m_depthIndex = (int)((_loc_3 + (_loc_1.y + _loc_2.y) / 2) * 1000 + _loc_3);
            return;
        }//end

        public double  depthIndex ()
        {
            return this.m_depthIndex;
        }//end

        public Vector3  getNearestTilePosition ()
        {
            return this.getOriginPoint(this.m_outer.getRotation());
        }//end

        public Vector3  getOriginPoint (int param1 )
        {
            Vector3 _loc_2 = this.getDepthPositionNoClone ();
            Vector3 _loc_3 = this.getDepthSizeNoClone ();
            Vector3 _loc_4 = scratchv3;
            int _loc_5 =0;
            scratchv3.z = 0;
            scratchv3.y = 0;
            scratchv3.x = 0;
            switch(param1)
            {
                case Constants.ROTATION_0:
                    break;
                case Constants.ROTATION_90:
                {
                    _loc_4.x = Math.max(0, _loc_3.x);
                    break;
                }
                case Constants.ROTATION_180:
                {
                    _loc_4.x = Math.max(0, _loc_3.x);
                    _loc_4.y = Math.max(0, _loc_3.y);
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_4.y = Math.max(0, _loc_3.y);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_2.add(_loc_4);
        }//end

        public int  compareDepth (WorldObject param1 )
        {
            double _loc_5 =0;
            double _loc_6 =0;
            int _loc_2 =0;
            if (param1.getParent() == this)
            {
                return 1;
            }
            if (this.getParent() == param1)
            {
                return -1;
            }
            if (this.isIntersecting(param1))
            {
                _loc_5 = this.getDepthPriority();
                _loc_6 = param1.getDepthPriority();
                if (_loc_5 < _loc_6)
                {
                    return 1;
                }
                if (_loc_5 > _loc_6)
                {
                    return -1;
                }
            }
            int _loc_3 = this.depthIndex ;
            int _loc_4 = param1.depthIndex ;
            if (_loc_3 < _loc_4)
            {
                _loc_2 = -1;
            }
            else if (_loc_4 < _loc_3)
            {
                _loc_2 = 1;
            }
            else
            {
                _loc_2 = 0;
            }
            return _loc_2;
        }//end

        public Array  getChildren ()
        {
            return this.m_children.concat(.get());
        }//end

        public void  setParent (WorldObject param1 )
        {
            if (this.m_parent != param1)
            {
                if (this.m_parent != null)
                {
                    this.m_parent.removeChild(this);
                }
                this.m_parent = param1;
                if (param1 != null)
                {
                    param1.addChild(this);
                }
                m_transformDirty = true;
                this.onParentChanged();
            }
            return;
        }//end

        public WorldObject  getParent ()
        {
            return this.m_parent;
        }//end

        public void  addChild (WorldObject param1 )
        {
            if (this.m_children.indexOf(param1) == -1)
            {
                this.m_children.push(param1);
                if (param1.getParent() != this)
                {
                    param1.setParent(this);
                }
            }
            return;
        }//end

        public void  removeChild (WorldObject param1 )
        {
            _loc_2 = this.m_children.indexOf(param1 );
            if (_loc_2 != -1)
            {
                this.m_children.splice(_loc_2, 1);
                if (param1.getParent() == this)
                {
                    param1.setParent(null);
                }
            }
            return;
        }//end

        public void  removeAllChildren ()
        {
            WorldObject _loc_3 =null ;
            Array _loc_1 = this.getChildren ();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(WorldObject) _loc_1.get(_loc_2);
                _loc_3.setParent(null);
                _loc_2++;
            }
            this.m_children = new Array();
            return;
        }//end

        public boolean  isChild (WorldObject param1 )
        {
            return this.m_children.indexOf(param1) != -1;
        }//end

        public void  onParentChanged ()
        {
            return;
        }//end

        public void  toggleAnimation ()
        {
            return;
        }//end

        public void  centerOn ()
        {
            World.getInstance().centerOnObject(this);
            return;
        }//end

        public void  cleanup ()
        {
            return;
        }//end

    }



