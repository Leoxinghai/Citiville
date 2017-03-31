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
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.geom.*;

    public class PositionedObject extends EngineObject
    {
        protected int m_objectType =1;
        protected int m_objectFlags =0;
        protected Vector3 m_position ;
        protected Point m_screenPosition ;
        protected Vector3 m_size ;
        protected DisplayObject m_displayObject =null ;
        protected boolean m_displayObjectDirty =false ;
        protected boolean m_transformDirty =false ;
        protected boolean m_visible =true ;
        protected Vector3 m_attachPosition =null ;
        protected Vector3 m_attachSize =null ;
        protected boolean m_highlighted ;
        protected boolean m_culled ;
        protected Vector3 m_endPosition ;

        public void  PositionedObject ()
        {
            this.m_position = new Vector3();
            this.m_size = new Vector3();
            this.m_endPosition = new Vector3();
            return;
        }//end

        public boolean  isPickable ()
        {
            return true;
        }//end

         public Object  getSaveObject ()
        {
            _loc_1 = super.getSaveObject();
            _loc_1.position = this.m_position;
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_position = new Vector3(param1.position.x, param1.position.y, param1.position.z);
            return;
        }//end

        public int  getObjectType ()
        {
            return this.m_objectType;
        }//end

        public PositionedObject  getReference ()
        {
            return this;
        }//end

        public boolean  checkObjectType (int param1 )
        {
            return (this.m_objectType & param1) > 0;
        }//end

        public boolean  checkObjectFlags (int param1 ,boolean param2 =true )
        {
            if (param2)
            {
                return (this.m_objectFlags & param1) == param1;
            }
            return (this.m_objectFlags & param1) > 0;
        }//end

        final public Vector3  getPosition ()
        {
            return this.m_position.clone();
        }//end

        final public double  positionX ()
        {
            return this.m_position.x;
        }//end

        final public double  positionY ()
        {
            return this.m_position.y;
        }//end

        final public double  positionZ ()
        {
            return this.m_position.z;
        }//end

        final public Vector3  getPositionNoClone ()
        {
            if (Vector3.optimizeMemoryUse)
            {
                return this.m_position;
            }
            return this.m_position.clone();
        }//end

        public Vector3  getEndPosition ()
        {
            this.m_endPosition.x = this.m_position.x + this.m_size.x;
            this.m_endPosition.y = this.m_position.y + this.m_size.y;
            this.m_endPosition.z = this.m_position.z + this.m_size.z;
            return this.m_endPosition;
        }//end

        public Point  getScreenPosition ()
        {
            return this.m_screenPosition.clone();
        }//end

        final public Vector3  getSizeNoClone ()
        {
            if (Vector3.optimizeMemoryUse)
            {
                return this.m_size;
            }
            return this.m_size.clone();
        }//end

        final public Vector3  getSize ()
        {
            return this.m_size.clone();
        }//end

        final public double  sizeX ()
        {
            return this.m_size.x;
        }//end

        final public double  sizeY ()
        {
            return this.m_size.y;
        }//end

        final public double  sizeZ ()
        {
            return this.m_size.z;
        }//end

        public Box3D  getBoundingBox ()
        {
            return new Box3D(this.m_position, this.m_size);
        }//end

        public boolean  isWithinViewport ()
        {
            Rectangle _loc_2 =null ;
            boolean _loc_1 =false ;
            if (this.m_displayObject)
            {
                _loc_1 = true;
                _loc_2 = this.m_displayObject.getRect(GlobalEngine.viewport);
                if (_loc_2.size.x > 0 && _loc_2.size.y > 0)
                {
                    if (_loc_2.right <= 0 || _loc_2.bottom <= 0 || GlobalEngine.stage.stageWidth <= _loc_2.x || GlobalEngine.stage.stageHeight <= _loc_2.y)
                    {
                        _loc_1 = false;
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  isPixelInside (Point param1 )
        {
            boolean _loc_2 =false ;
            Rectangle rect1 =this.m_displayObject.getRect(this.m_displayObject.parent );
            if (this.m_displayObject)
            {
                if (this.m_displayObject.getRect(this.m_displayObject.parent).containsPoint(param1))
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public boolean  isWithinBounds (PositionedObject param1 )
        {
            _loc_2 = this.getPositionNoClone ();
            _loc_3 = this.getEndPosition ();
            _loc_4 = param1.getPositionNoClone ();
            _loc_5 = param1.getEndPosition ();
            return _loc_2.x <= _loc_4.x && _loc_2.y <= _loc_4.y && _loc_3.x >= _loc_5.x && _loc_3.y >= _loc_5.y;
        }//end

        public void  updateCulling ()
        {
            this.m_culled = this.isWithinViewport() == false;
            this.setVisible(this.m_visible);
            return;
        }//end

        public void  setVisible (boolean param1 )
        {
            this.m_visible = param1;
            if (this.m_displayObject)
            {
                this.m_displayObject.visible = param1 && this.m_culled == false;
            }
            return;
        }//end

        public boolean  isVisible ()
        {
            return this.m_visible && this.m_culled == false;
        }//end

        public void  alpha (double param1 )
        {
            if (this.m_displayObject)
            {
                this.m_displayObject.alpha = param1;
            }
            return;
        }//end

        public double  alpha ()
        {
            double _loc_1 =1;
            if (this.m_displayObject)
            {
                _loc_1 = this.m_displayObject.alpha;
            }
            return _loc_1;
        }//end

        public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            Vector3 _loc_4 =null ;
            if (this.m_position.x != param1 || this.m_position.y != param2 || this.m_position.z != param3)
            {
                _loc_4 = new Vector3(param1 - this.m_position.x, param2 - this.m_position.y, param3 - this.m_position.z);
                this.m_position = new Vector3(param1, param2, param3);
                this.m_transformDirty = true;
            }
            return;
        }//end

        public void  translate (Vector3 param1 )
        {
            this.setPosition(this.m_position.x + param1.x, this.m_position.y + param1.y, this.m_position.z + param1.z);
            return;
        }//end

        public void  setSize (double param1 ,double param2 ,double param3 =0)
        {
            if (this.m_size.x != param1 || this.m_size.y != param2 || this.m_size.z != param3)
            {
                this.m_size.x = param1;
                this.m_size.y = param2;
                this.m_size.z = param3;
                this.m_transformDirty = true;
            }
            return;
        }//end

        public boolean  isAttached ()
        {
            return this.m_attachPosition != null;
        }//end

        public Vector3  getAttachPosition ()
        {
            return this.m_attachPosition.clone();
        }//end

        public Vector3  getAttachPositionNoClone ()
        {
            return this.m_attachPosition;
        }//end

        public Vector3  getAttachSize ()
        {
            return this.m_attachSize.clone();
        }//end

        public Vector3  getAttachSizeNoClone ()
        {
            return this.m_attachSize;
        }//end

        public boolean  intersects (PositionedObject param1 )
        {
            _loc_2 = this.getPositionNoClone ();
            _loc_3 = this.getEndPosition ();
            _loc_4 = param1.getPositionNoClone ();
            _loc_5 = param1.getEndPosition ();
            boolean _loc_6 =true ;
            if (_loc_3.x <= _loc_4.x || _loc_2.x >= _loc_5.x || _loc_3.y <= _loc_4.y || _loc_2.y >= _loc_5.y || _loc_3.z != _loc_2.z && _loc_4.z != _loc_5.z && (_loc_3.z < _loc_4.z || _loc_2.z > _loc_5.z))
            {
                _loc_6 = false;
            }
            return _loc_6;
        }//end

        public boolean  isCurrentPositionValid ()
        {
            return true;
        }//end

        public Class  getCursor ()
        {
            return null;
        }//end

        public void  setHighlighted (boolean param1 ,int param2 =16755200)
        {
            if (this.m_displayObject)
            {
                this.m_highlighted = param1;
                if (param1 !=null)
                {
                    this.m_displayObject.filters = .get(new GlowFilter(param2, 1, 16, 16, 4));
                }
                else
                {
                    this.m_displayObject.filters = new Array();
                }
            }
            return;
        }//end

        public boolean  isHighlighted ()
        {
            return this.m_highlighted;
        }//end

        public boolean  isInteractable ()
        {
            return (this.m_objectFlags & Constants.OBJECTFLAG_INTERACTIVE) > 0;
        }//end

        public boolean  hasMouseOver ()
        {
            return false;
        }//end

        public PositionedObject  pickObject (Point param1 ,int param2 =16777215,int param3 =0)
        {
            PositionedObject _loc_4 =null ;
            if (this.isPickable() && this.checkObjectType(param2) && this.checkObjectFlags(param3))
            {
                if (this.isVisible() && this.isPixelInside(param1))
                {
                    _loc_4 = this.getReference();
                }
            }
            return _loc_4;
        }//end

        public void  drawDisplayObject ()
        {
            return;
        }//end

        public DisplayObject  createDisplayObject ()
        {
            return null;
        }//end

        public void  updateDisplayObjectTransform ()
        {
            return;
        }//end

        public void  deleteDisplayObject ()
        {
            if (this.m_displayObject != null)
            {
                if (this.m_displayObject.parent != null)
                {
                    this.m_displayObject.parent.removeChild(this.m_displayObject);
                }
                this.m_displayObject = null;
                this.m_displayObjectDirty = true;
            }
            return;
        }//end

        public DisplayObject getDisplayObject ()
        {
            return this.m_displayObject;
        }//end

        public boolean  isDisplayObjectDirty ()
        {
            return this.m_displayObjectDirty || this.m_displayObject == null;
        }//end

        public void  setDisplayObjectDirty (boolean param1 )
        {
            this.m_displayObjectDirty = param1;
            return;
        }//end

        public Vector2  getActualDisplayObjectSize ()
        {
            Vector2 _loc_1 =new Vector2 ();
            if (this.m_displayObject != null)
            {
                _loc_1.x = this.m_displayObject.width;
                _loc_1.y = this.m_displayObject.height;
            }
            return _loc_1;
        }//end

    }



