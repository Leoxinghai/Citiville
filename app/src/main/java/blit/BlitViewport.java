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
import com.xiyu.util.BitmapData;
import com.xiyu.util.Dictionary;

import Classes.*;
import Engine.Classes.*;
import Engine.Helpers.*;

import de.polygonal.ds.*;
import root.Global;
//import flash.display.*;
//import flash.geom.*;
import com.xiyu.util.Rectangle;
import com.zynga.skelly.render.*;

import java.util.Vector;

public class BlitViewport extends IsoViewport implements IRenderer
    {
        private Vector<BitmapData> m_buffers;
        private BitmapData m_scratchAlphaBuffer ;
        private Vector<BlitGameObjectLayer> m_gameLayers;
        private Bitmap m_blitViewportBitmap ;
        private boolean m_setupBuffers =true ;
        private Array m_alphaArray ;
        private RenderContext m_context ;
        private RenderGrid m_renderGrid ;
        private DLinkedList m_tmpRectList ;
        private boolean once =true ;
        private Point zeroPt ;
        public static  int BACKGROUND =0;
        public static  int ROADS =1;
        public static  int BUILDINGS =2;
        public static  int ALPHAMASK =3;
        public static  int COMPOSITE =4;
        public static  int OUTPUT =5;
        public static  int NUMBUFFERS =6;

        public  BlitViewport ()
        {
            this.m_buffers = new Vector<BitmapData>(NUMBUFFERS);
            this.m_scratchAlphaBuffer = new BitmapData(256, 256, true, 16777215);
            this.m_gameLayers = new Vector<BlitGameObjectLayer>(NUMBUFFERS);
            this.m_blitViewportBitmap = new Bitmap(null, PixelSnapping.NEVER);
            this.m_alphaArray = new Array(256);
            this.m_context = new RenderContext();
            this.m_renderGrid = new RenderGrid();
            this.m_tmpRectList = new DLinkedList();
            this.zeroPt = new Point();
            this.m_alphaArray.add(0,new Long(4278190080L));
            int _loc_1 =1;
            while (_loc_1 < 256)
            {

                this.m_alphaArray.put(_loc_1,  0);
                _loc_1++;
            }
            return;
        }//end

         public void  addObjectLayer (ObjectLayer param1 )
        {
            m_objectLayers.push(param1);
            m_objectLayers.sortOn("priority");
            if (param1.name == "road")
            {
                this.m_gameLayers.put(ROADS,  (BlitGameObjectLayer)param1);
            }
            if (param1.name == "default")
            {
                this.m_gameLayers.put(BUILDINGS,  (BlitGameObjectLayer)param1);
            }
            return;
        }//end

         public boolean  regenerateBitmapData ()
        {
            int _loc_2 =0;
            boolean _loc_1 =false ;
            if (m_bitmapData == null || m_bitmapData.width != this.stage.width || m_bitmapData.height != this.stage.height)
            {
                if (this.stage != null && Global.ui.screenWidth > 0 && Global.ui.screenHeight > 0)
                {
                    if (m_bitmap.parent)
                    {
                        m_bitmap.parent.removeChild(m_bitmap);
                    }
                    if (m_bitmapData)
                    {
                        m_bitmapData.dispose();
                        m_bitmapData = null;
                    }
                    m_bitmapData = new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight);
                    m_bitmap.bitmapData = m_bitmapData;
                    _loc_2 = 0;
                    while (_loc_2 < NUMBUFFERS)
                    {

                        if (this.m_buffers.get(_loc_2))
                        {
                            this.m_buffers.get(_loc_2).dispose();
                            this.m_buffers.put(_loc_2,  null);
                        }
                        _loc_2++;
                    }
                    this.m_buffers.put(BACKGROUND,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, false));
                    this.m_buffers.put(ROADS,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, true, 16777215));
                    this.m_buffers.put(BUILDINGS,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, true, 16777215));
                    this.m_buffers.put(ALPHAMASK,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, true, 16777215));
                    this.m_buffers.put(COMPOSITE,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, true, 16777215));
                    this.m_buffers.put(OUTPUT,  new BitmapData(Global.ui.screenWidth, Global.ui.screenHeight, false));
                    this.initializeBackgroundData(this.m_buffers.get(BACKGROUND));
                    if (this.m_blitViewportBitmap.parent)
                    {
                        this.m_blitViewportBitmap.parent.removeChild(this.m_blitViewportBitmap);
                    }
                    this.m_blitViewportBitmap.bitmapData = this.m_buffers.get(OUTPUT);
                    addChildAt(this.m_blitViewportBitmap, this.numChildren);
                    this.m_context.targetBuffer = this.m_buffers.get(OUTPUT);
                    this.m_context.backBuffer = this.m_buffers.get(COMPOSITE);
                    this.m_context.alphaBuffer = null;
                    _loc_1 = true;
                }
            }
            return _loc_1;
        }//end

         public void  setScrollPosition (Vector2 param1 )
        {
            m_position = param1.cloneVec2();
            m_bitmapDirty = true;
            return;
        }//end

        private void  initializeBackgroundData (BitmapData param1 )
        {
            Point _loc_2 =new Point ((-m_position.x )*4,(-m_position.y )*4);
            param1.perlinNoise(150, 150, 1, 1, false, true, 2 | 4, false, .get(_loc_2));
            return;
        }//end

         protected void  drawBackground (BitmapData param1 )
        {
            return;
        }//end

         protected boolean  render (boolean param1 =false )
        {
            boolean _loc_2 =super.render(param1 );
            if (param1 && stage)
            {
                this.setupTransformMatrix();
                this.m_context.transformMatrix = m_absoluteTransformMatrix.clone();
                this.rebuildBuffers();
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  onRender (int param1 ,int param2 )
        {
            if (Global.world && Global.hud && this.once)
            {
                Global.world.updateWorld();
            }
            this.renderWorldToBuffer();
            return true;
        }//end

        public void  renderWorldToBuffer ()
        {
            if (!stage)
            {
                return;
            }
            if (m_bitmapData && m_bitmapDirty)
            {
                this.setupTransformMatrix();
                this.m_context.transformMatrix = m_absoluteTransformMatrix.clone();
                this.rebuildBuffers();
                m_bitmapDirty = false;
            }
            this.renderToBuffer();
            return;
        }//end

        protected void  setupTransformMatrix ()
        {
            m_absoluteTransformMatrix.identity();
            m_absoluteTransformMatrix.translate(m_position.x, m_position.y);
            m_absoluteTransformMatrix.translate((-Global.ui.screenWidth) / 2, (-Global.ui.screenHeight) / 2);
            m_absoluteTransformMatrix.scale(m_zoom, m_zoom);
            m_absoluteTransformMatrix.translate(Global.ui.screenWidth / 2, Global.ui.screenHeight / 2);
            _loc_1 = m_absoluteTransformMatrix;
            overlayBase.transform.matrix = m_absoluteTransformMatrix;//_loc_1;

            uiBase.transform.matrix = m_absoluteTransformMatrix;//_loc_1;
            objectBase.transform.matrix = m_absoluteTransformMatrix;//_loc_1;
            return;
        }//end

        protected void  rebuildBuffers ()
        {
            this.initializeBackgroundData(this.m_buffers.get(BACKGROUND));
            this.clearBuffersUsedForAlphaMask();
            this.renderLayersToBuffers();
            this.generateAlphaMaskBuffer();
            this.generateCompositeBuffer();
            this.copyCompositeBufferToOutput();
            this.m_gameLayers.get(ROADS).clearDirtyObjectList();
            this.m_gameLayers.get(BUILDINGS).clearDirtyObjectList();
            return;
        }//end

        protected void  renderToBuffer ()
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            Point _loc_6 =null ;
            DLinkedList _loc_7 =null ;
            DListNode _loc_8 =null ;
            NPC _loc_9 =null ;
            Rectangle _loc_10 =null ;
            Rectangle _loc_11 =null ;
            this.m_context.transformMatrix = m_absoluteTransformMatrix.clone();
            this.m_tmpRectList.clear();
            if (this.m_gameLayers.get(ROADS) && this.m_gameLayers.get(ROADS).dirty)
            {
                this.reRenderLayer(this.m_context, this.m_gameLayers.get(ROADS), this.m_buffers.get(ROADS));
            }
            if (this.m_gameLayers.get(BUILDINGS) && this.m_gameLayers.get(BUILDINGS).dirty)
            {
                this.reRenderLayer(this.m_context, this.m_gameLayers.get(BUILDINGS), this.m_buffers.get(BUILDINGS));
            }
            this.reRenderAlphaMask(this.m_tmpRectList);
            this.reRenderCompositeLayer(this.m_tmpRectList);
            this.reRenderCompositeToOutput(this.m_tmpRectList);
            this.m_tmpRectList.clear();
            this.m_context.targetBuffer = this.m_buffers.get(OUTPUT);
            this.m_context.alphaBuffer = this.m_buffers.get(ALPHAMASK);
            this.m_context.backBuffer = this.m_buffers.get(COMPOSITE);
            this.m_context.targetBuffer.lock();
            this.m_renderGrid.clearGrid();
            int _loc_1 =0;
            while (_loc_1 < m_objectLayers.length())
            {

                if (m_objectLayers.get(_loc_1).name == "npc")
                {
                    _loc_3 = m_objectLayers.get(_loc_1).children;
                    _loc_4 = _loc_3.length;
                    _loc_5 = 0;
                    while (_loc_5 < _loc_4)
                    {

                        _loc_9 =(NPC) _loc_3.get(_loc_5);
                        if (_loc_9)
                        {
                            _loc_10 = _loc_9.calcDirtyRect(this.m_context);
                            this.m_renderGrid.addDirtyRect(_loc_10);
                        }
                        _loc_5++;
                    }
                    _loc_6 = new Point();
                    _loc_7 = this.m_renderGrid.dirtyRects;
                    _loc_8 = _loc_7.head;
                    while (_loc_8)
                    {

                        _loc_11 =(Rectangle) _loc_8.data;
                        _loc_11.x = int(_loc_11.x);
                        _loc_11.y = int(_loc_11.y);
                        _loc_6.x = _loc_11.x;
                        _loc_6.y = _loc_11.y;
                        this.m_context.targetBuffer.copyPixels(this.m_context.backBuffer, _loc_11, _loc_6);
                        _loc_8 = _loc_8.next;
                    }
                    _loc_5 = 0;
                    while (_loc_5 < _loc_4)
                    {

                        _loc_3.get(_loc_5).render(this.m_context);
                        _loc_5++;
                    }
                }
                _loc_1++;
            }
            this.m_context.targetBuffer.unlock();
            int _loc_2 =0;
            while (_loc_2 < this.m_gameLayers.length())
            {

                if (this.m_gameLayers.get(_loc_2))
                {
                    this.m_gameLayers.get(_loc_2).postRender();
                }
                _loc_2++;
            }
            return;
        }//end

        protected void  clearBuffersUsedForAlphaMask ()
        {
            this.m_buffers.get(ROADS).fillRect(this.m_buffers.get(ROADS).rect, 16777215);
            this.m_buffers.get(BUILDINGS).fillRect(this.m_buffers.get(BUILDINGS).rect, 16777215);
            this.m_buffers.get(ALPHAMASK).fillRect(this.m_buffers.get(ALPHAMASK).rect, 16777215);
            return;
        }//end

        protected void  renderLayersToBuffers ()
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            Point _loc_1 =new Point ();
            int _loc_2 =0;
            while (_loc_2 < NUMBUFFERS)
            {

                if (this.m_gameLayers.get(_loc_2))
                {
                    this.m_context.targetBuffer = this.m_buffers.get(_loc_2);
                    this.m_context.alphaBuffer = null;
                    _loc_3 = this.m_gameLayers.get(_loc_2).children;
                    _loc_4 = _loc_3.length;
                    _loc_5 = 0;
                    while (_loc_5 < _loc_4)
                    {

                        _loc_3.get(_loc_5).render(this.m_context);
                        _loc_5++;
                    }
                }
                _loc_2++;
            }
            return;
        }//end

        protected void  generateAlphaMaskBuffer ()
        {
            Point _loc_1 =new Point ();
            this.m_buffers.get(ALPHAMASK).copyPixels(this.m_buffers.get(BUILDINGS), this.m_buffers.get(BUILDINGS).rect, _loc_1, this.m_buffers.get(ROADS), _loc_1, false);
            this.m_buffers.get(ALPHAMASK).paletteMap(this.m_buffers.get(ALPHAMASK), this.m_buffers.get(ALPHAMASK).rect, _loc_1, null, null, null, this.m_alphaArray);
            return;
        }//end

        protected void  generateCompositeBuffer ()
        {
            Point _loc_1 =new Point ();
            this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(BACKGROUND), this.m_buffers.get(BACKGROUND).rect, _loc_1);
            this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(ROADS), this.m_buffers.get(ROADS).rect, _loc_1, null, null, true);
            this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(BUILDINGS), this.m_buffers.get(BUILDINGS).rect, _loc_1, null, null, true);
            return;
        }//end

        protected void  copyCompositeBufferToOutput ()
        {
            Point _loc_1 =new Point ();
            this.m_buffers.get(OUTPUT).copyPixels(this.m_buffers.get(COMPOSITE), this.m_buffers.get(COMPOSITE).rect, _loc_1);
            return;
        }//end

        protected void  reRenderLayer (RenderContext param1 ,BlitGameObjectLayer param2 ,BitmapData param3 )
        {
            Rectangle _loc_5 =null ;
            MapResource _loc_6 =null ;
            _loc_4 = param2.dirtyObjectList;
            _loc_7 = param2.dirtyObjectList.head;
            while (_loc_7)
            {

                _loc_6 =(MapResource) _loc_7.data;
                if (_loc_6)
                {
                    _loc_5 = _loc_6.calcDirtyRectMapResource(param1);
                    this.m_tmpRectList.append(_loc_5);
                    param3.fillRect(_loc_5, 16777215);
                }
                _loc_7 = _loc_7.next;
            }
            this.renderLayerToBuffer(param2, param3);
            _loc_7 = _loc_4.head;
            while (_loc_7)
            {

                _loc_6 =(MapResource) _loc_7.data;
                if (_loc_6)
                {
                    _loc_5 = _loc_6.calcDirtyRectMapResource(param1);
                    this.m_tmpRectList.append(_loc_5);
                }
                _loc_7 = _loc_7.next;
            }
            param2.clearDirtyObjectList();
            return;
        }//end

        protected void  renderLayerToBuffer (BlitGameObjectLayer param1 ,BitmapData param2 )
        {
            this.m_context.targetBuffer = param2;
            this.m_context.alphaBuffer = null;
            _loc_3 = param1.children;;
            _loc_4 = _loc_3.length;;
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_3.get(_loc_5).render(this.m_context);
                _loc_5++;
            }
            return;
        }//end

        protected void  reRenderAlphaMask (DLinkedList param1 )
        {
            Point _loc_2 =null ;
            Rectangle _loc_3 =null ;
            Point _loc_4 =null ;
            Rectangle _loc_5 =null ;
            DListNode _loc_6 =null ;
            if (param1.size)
            {
                _loc_2 = new Point();
                _loc_4 = new Point();
                _loc_5 = new Rectangle();
                _loc_6 = param1.head;
                while (_loc_6)
                {

                    _loc_3 =(Rectangle) _loc_6.data;
                    _loc_3.x = int(_loc_3.x);
                    _loc_3.y = int(_loc_3.y);
                    _loc_2.x = _loc_3.x;
                    _loc_2.y = _loc_3.y;
                    _loc_5.width = _loc_3.width;
                    _loc_5.height = _loc_3.height;
                    this.m_scratchAlphaBuffer.fillRect(_loc_5, 4294967295);
                    this.m_scratchAlphaBuffer.copyPixels(this.m_buffers.get(BUILDINGS), _loc_3, _loc_4, this.m_buffers.get(ROADS), _loc_2, false);
                    this.m_scratchAlphaBuffer.paletteMap(this.m_scratchAlphaBuffer, _loc_5, _loc_4, null, null, null, this.m_alphaArray);
                    this.m_buffers.get(ALPHAMASK).copyPixels(this.m_scratchAlphaBuffer, _loc_5, _loc_2);
                    _loc_6 = _loc_6.next;
                }
            }
            return;
        }//end

        protected void  reRenderCompositeLayer (DLinkedList param1 )
        {
            Point _loc_2 =null ;
            Rectangle _loc_3 =null ;
            DListNode _loc_4 =null ;
            if (param1.size)
            {
                _loc_2 = new Point();
                _loc_4 = param1.head;
                while (_loc_4)
                {

                    _loc_3 =(Rectangle) _loc_4.data;
                    _loc_3.x = int(_loc_3.x);
                    _loc_3.y = int(_loc_3.y);
                    _loc_2.x = _loc_3.x;
                    _loc_2.y = _loc_3.y;
                    this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(BACKGROUND), _loc_3, _loc_2);
                    this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(ROADS), _loc_3, _loc_2, null, null, true);
                    this.m_buffers.get(COMPOSITE).copyPixels(this.m_buffers.get(BUILDINGS), _loc_3, _loc_2, null, null, true);
                    _loc_4 = _loc_4.next;
                }
            }
            return;
        }//end

        protected void  reRenderCompositeToOutput (DLinkedList param1 )
        {
            Point _loc_2 =null ;
            Rectangle _loc_3 =null ;
            DListNode _loc_4 =null ;
            if (param1.size)
            {
                _loc_2 = new Point();
                _loc_4 = param1.head;
                while (_loc_4)
                {

                    _loc_3 =(Rectangle) _loc_4.data;
                    _loc_3.x = int(_loc_3.x);
                    _loc_3.y = int(_loc_3.y);
                    _loc_2.x = _loc_3.x;
                    _loc_2.y = _loc_3.y;
                    this.m_buffers.get(OUTPUT).copyPixels(this.m_buffers.get(COMPOSITE), _loc_3, _loc_2);
                    _loc_4 = _loc_4.next;
                }
            }
            return;
        }//end

        protected void  iterateDisplayList (DisplayObjectContainer param1 ,String param2 )
        {
            int _loc_3 =0;
            DisplayObject _loc_4 =null ;
            while (_loc_3 < param1.numChildren)
            {

                _loc_4 = param1.getChildAt(_loc_3);
                if (_loc_4)
                {
                }
                param2 = param2 + " ";
                if (_loc_4 && _loc_4 instanceof DisplayObjectContainer)
                {
                    this.iterateDisplayList((DisplayObjectContainer)_loc_4, param2);
                }
                _loc_3++;
            }
            return;
        }//end

    }






