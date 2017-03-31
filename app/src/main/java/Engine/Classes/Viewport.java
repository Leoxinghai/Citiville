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
import android.graphics.Canvas;

import com.xiyu.logic.Sprite;
import com.xiyu.util.Array;
import com.xiyu.util.BitmapData;
import com.xiyu.util.Dictionary;

import Engine.Helpers.*;
import Engine.Managers.*;
import root.Config;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import com.xinghai.Debug;
import com.xiyu.util.Event;
import com.xiyu.util.Matrix;
import com.xiyu.util.Rectangle;

import java.util.Vector;

public class Viewport extends Sprite
    {
        protected Vector2 m_position ;
        protected double m_zoom =1;
        protected Bitmap m_bitmap ;
        protected BitmapData m_bitmapData ;
        protected Array m_transformStack ;
        protected Matrix m_absoluteTransformMatrix ;
        protected Vector<ViewportLayer> m_layers;
        protected boolean m_bitmapDirty =true ;

        public  Viewport ()
        {
            this.m_position = new Vector2(0, 0);
            this.m_bitmap = new Bitmap();
            this.m_transformStack = new Array();
            this.m_absoluteTransformMatrix = new Matrix();
            this.m_layers = new Vector<ViewportLayer>();
            addEventListener(Event.RESIZE, this.onResize);
            addEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            addEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            this.initialize();
            InputManager.initializeHandlers(this);
            return;
        }//end

        protected void  initialize ()
        {
            this.addLayer(new ViewportLayer("overlay", -1));
            this.addLayer(new ViewportLayer("object", 0));
            this.addLayer(new ViewportLayer("ui", 1));
            this.addLayer(new ViewportLayer("hud", 2));
            return;
        }//end

        protected void  onAddedToStage (Event event )
        {
            this.stage.addEventListener(Event.RESIZE, this.onResize);
            this.onResize(null);
            return;
        }//end

        public Matrix  getTransformMatrix ()
        {
            return this.m_absoluteTransformMatrix;
        }//end

        public Vector2  getScrollPosition ()
        {
            return this.m_position.cloneVec2();
        }//end

        public void  setScrollPosition (Vector2 param1 )
        {
            this.m_position = param1.cloneVec2();
            this.m_bitmapDirty = true;
            return;
        }//end

        public void  setZoom (double param1 )
        {
            if (this.m_zoom != param1)
            {
                this.m_zoom = param1;
                this.m_bitmapDirty = true;
            }
            return;
        }//end

        public void  setDirty ()
        {
            this.m_bitmapDirty = true;
            return;
        }//end

        public boolean  regenerateBitmapData ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            ViewportLayer _loc_4 =null ;
            boolean _loc_1 =false ;
            if (this.stage != null && this.stage.stageWidth > 0 && this.stage.stageHeight > 0)
            {
                if (this.m_bitmapData == null || this.m_bitmapData.width != this.stage.stageWidth || this.m_bitmapData.height != this.stage.stageHeight)
                {
                    if (this.m_bitmap.parent)
                    {
                        this.m_bitmap.parent.removeChild(this.m_bitmap);
                    }
                    if (this.m_bitmapData)
                    {
                        this.m_bitmapData.dispose();
                        this.m_bitmapData = null;
                    }
                    _loc_2 = 0;
                    this.m_bitmapData = new BitmapData(this.stage.stageWidth, this.stage.stageHeight);
                    this.m_bitmap.bitmapData = this.m_bitmapData;
                    addChildAt(this.m_bitmap, _loc_2);
                    _loc_2++;
                    _loc_3 = 0;
                    while (_loc_3 < this.m_layers.size())
                    {

                        _loc_4 = this.m_layers.get(_loc_3);
                        Debug.debug4("Viewport."+_loc_2+";"+_loc_4.name);
                        if (_loc_4.parent)
                        {
                            _loc_4.parent.removeChild(_loc_4);
                        }


                        	addChildAt(_loc_4, _loc_2);

                        _loc_2++;
                        _loc_3++;
                    }
                }
            }
            return _loc_1;
        }//end

        protected void  addLayer (ViewportLayer param1 )
        {
            this.m_layers.push(param1);
            this.m_layers.sort(this.sortLayer);
            return;
        }//end

        private double  sortLayer (ViewportLayer param1 ,ViewportLayer param2 )
        {
            if (param1.getPriority() > param2.getPriority())
            {
                return 1;
            }
            if (param1.getPriority() < param2.getPriority())
            {
                return -1;
            }
            return 0;
        }//end

        public Sprite  getLayer (String param1 )
        {
            Sprite _loc_2 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_layers.size())
            {

                if (this.m_layers.get(_loc_3).name == param1)
                {
                    _loc_2 =(Sprite) this.m_layers.get(_loc_3);
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        protected boolean  render (boolean param1 =false )
        {
            Matrix _loc_3 =null ;
            boolean _loc_2 =false ;
            if (this.m_bitmapData && (this.m_bitmapDirty || param1))
            {
                this.regenerateBitmapData();
                this.m_absoluteTransformMatrix.identity();
                this.m_absoluteTransformMatrix.translate(this.m_position.x, this.m_position.y);
                this.m_absoluteTransformMatrix.translate((-this.stage.stageWidth) / 2, (-this.stage.stageHeight) / 2);
                this.m_absoluteTransformMatrix.scale(this.m_zoom, this.m_zoom);
                this.m_absoluteTransformMatrix.translate(this.stage.stageWidth / 2, this.stage.stageHeight / 2);
                this.m_bitmapData.lock();
                this.drawBackground(this.m_bitmapData);
                this.m_bitmapData.unlock();
                _loc_3 = this.m_absoluteTransformMatrix.clone();
                this.getLayer("object").transform.matrix = _loc_3;
                this.getLayer("ui").transform.matrix = _loc_3;
                this.getLayer("overlay").transform.matrix = _loc_3;
                this.m_bitmapDirty = false;
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        protected void  drawBackground (BitmapData param1 )
        {
            param1.fillRect(param1.rect, Config.VIEWPORT_CLEAR_COLOR);
            return;
        }//end

        public void  centerViewport ()
        {
            return;
        }//end

        public BitmapData  getBitmapData ()
        {
            return this.m_bitmapData;
        }//end

        public void  clearBitmapData ()
        {
            if (this.m_bitmapData != null)
            {
                this.m_bitmapData.dispose();
                this.m_bitmapData = null;
                this.m_bitmapDirty = true;
            }
            return;
        }//end

        public double  getZoom ()
        {
            return this.m_zoom;
        }//end

        protected void  onResize (Event event )
        {
            this.regenerateBitmapData();
            this.m_bitmapDirty = true;
            return;
        }//end

        protected void  onEnterFrame (Event event )
        {
            if (this.stage != null)
            {
                this.render();
            }
            return;
        }//end

        public void  copyPixels (BitmapData param1 , Rectangle param2 , Point param3 , BitmapData param4 , Point param5 , boolean param6 )
        {
            _loc_7 = this.m_absoluteTransformMatrix.transformPoint(param3 );
            this.m_bitmapData.copyPixels(param1, param2, _loc_7, param4, param5, param6);
            return;
        }//end

        public Sprite  uiBase ()
        {
            return this.getLayer("ui");
        }//end

        public Sprite  overlayBase ()
        {
            return this.getLayer("overlay");
        }//end

        public Sprite  objectBase ()
        {
            return this.getLayer("object");
        }//end

        public Sprite  hudBase ()
        {
            return this.getLayer("hud");
        }//end

    }



