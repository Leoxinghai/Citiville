package Classes.sim;

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

import Classes.*;
import Engine.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import com.xinghai.Debug;

    public class WaterManager
    {
        protected GameWorld m_world ;
        private Loader m_loader =null ;
        private Bitmap m_collBitmap ;
        private Item m_item ;
        private String m_state ;
        private Bitmap m_collisionBitmap ;
        private Shape m_dbgCollisionShape =null ;
        private String expansionCollisionfile ="collisionExpanded";
        private int xOffset =0;
        private int yOffset =0;
        public static  double WATER_COLOR =0;
        public static  double LAND_COLOR =16777215;
        public static  double SAND_COLOR =16776960;
        public static  double RIGHT_SIDE_LAND_COLOR =255;
        public static  double NO_TREE_LAND_COLOR =16711680;
        public static  double RIGHT_SIDE_NO_TREE_LAND_COLOR =16711935;
        public static  int MINIMUM_VALID_SQUARES_TO_EXPAND =4;

        public boolean m_drawn =false ;

        public  WaterManager (GameWorld param1 )
        {
            this.m_world = param1;
            return;
        }//end

        public boolean  showingDbgOverlay ()
        {
            return this.m_dbgCollisionShape != null;
        }//end

        public void  showDbgOverlay (boolean param1 )
        {
            if (param1 == this.showingDbgOverlay)
            {
                return;
            }
            if (param1 !=null)
            {
                this.dbgCollision();
            }
            else if (this.m_dbgCollisionShape)
            {
                GlobalEngine.viewport.objectBase.removeChild(this.m_dbgCollisionShape);
                this.m_dbgCollisionShape = null;
            }
            return;
        }//end

        public void  init ()
        {
            XMLList _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            MovieClip _loc_9 =null ;
            int _loc_10 =0;
            Bitmap _loc_11 =null ;
            XML _loc_1 =Global.gameSettings().getXML ();
            XMLList _loc_2 =_loc_1.collisionBackground.layer ;
            XML _loc_3 =_loc_2.get(0) ;
            if (_loc_3 == null)
            {
                return;
            }
            Debug.debug4("WaterManager.init"+_loc_3.toString());
            this.m_item = Global.gameSettings().getItemByName(_loc_3.item.@name);
            this.m_state = _loc_3.item.@state;
            _loc_4 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1 );
            if (_loc_4 > 0)
            {
                this.m_state = this.expansionCollisionfile;
                _loc_6 = _loc_1.collisionBackground.expansion;
                if (_loc_6 != null)
                {
                    _loc_7 = _loc_6.@xoffset;
                    _loc_8 = _loc_6.@yoffset;
                    if (_loc_7 != null)
                    {
                        this.xOffset = int(_loc_7);
                        this.yOffset = int(_loc_8);
                    }
                }
            }
            _loc_5 = this.m_item.getCachedImage(this.m_state );
            if (_loc_5)
            {
                _loc_9 =(MovieClip) _loc_5.image;
                if (_loc_9)
                {
                    _loc_10 = 0;
                    while (_loc_10 < _loc_9.numChildren)
                    {

                        _loc_11 =(Bitmap) _loc_9.getChildAt(_loc_10);
                        if (_loc_11)
                        {
                            this.m_collBitmap = _loc_11;
                        }
                        _loc_10++;
                    }
                }
            }
            if (this.m_collBitmap)
            {
                this.onCollisionLoaded(null);
            }
            else
            {
                Debug.debug4("WaterManger.item.load" + m_item.constructionXml);
                this.m_item.addEventListener(LoaderEvent.LOADED, this.onCollisionLoaded);
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        private void  onCollisionLoaded (Event event )
        {
            ItemImageInstance _loc_2 =null ;
            MovieClip _loc_3 =null ;
            int _loc_4 =0;
            Bitmap _loc_5 =null ;
            Debug.debug4("WaterManager.onCollisionLoaded" + m_item.constructionXml);
            this.m_item.removeEventListener(LoaderEvent.LOADED, this.onCollisionLoaded);
            if (this.m_collBitmap == null)
            {
                _loc_2 = this.m_item.getCachedImage(this.m_state);
                if (_loc_2)
                {
                    _loc_3 =(MovieClip) _loc_2.image;
                    if (_loc_3)
                    {
                        _loc_4 = 0;
                        while (_loc_4 < _loc_3.numChildren)
                        {

                            _loc_5 =(Bitmap) _loc_3.getChildAt(_loc_4);
                            if (_loc_5)
                            {
                                this.m_collBitmap = _loc_5;
                            }
                            _loc_4++;
                        }
                    }
                }
            }
            this.setCollision(this.m_collBitmap);
            Global.world.createOverlayBackground();
            return;
        }//end

        public void  setCollision (Bitmap param1 )
        {
            if (param1 !=null)
            {
                this.m_collisionBitmap = param1;
            }
            return;
        }//end

        public boolean  waterDataLoaded ()
        {
            return this.m_collisionBitmap != null;
        }//end

        public boolean  positionOnValidLand (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left ;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (!this.testValidLand(_loc_2, _loc_3))
                    {
                        return false;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return true;
        }//end

        public boolean  positionValidForTree (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left ;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (!this.testValidTree(_loc_2, _loc_3))
                    {
                        return false;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return true;
        }//end

        public boolean  positionValidForExpansion (Rectangle param1 )
        {
            int _loc_5 =0;
            int _loc_2 =0;
            _loc_3 = MINIMUM_VALID_SQUARES_TO_EXPAND;
            _loc_4 = param1.left ;
            while (_loc_4 < param1.left + param1.width)
            {

                _loc_5 = param1.top;
                while (_loc_5 < param1.top + param1.height)
                {

                    if (this.testValidExpansion(_loc_4, _loc_5))
                    {
                        _loc_2++;
                    }
                    _loc_5++;
                }
                _loc_4++;
            }
            return _loc_2 >= _loc_3;
        }//end

        public boolean  positionValidPierOnWater (Rectangle param1 ,String param2 ,int param3 )
        {
            String _loc_8 =null ;
            Array _loc_9 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_4 = param1.top;
            while (_loc_4 < param1.top + param1.height)
            {

                _loc_5 = param1.left - param3;
                while (_loc_5 < param1.left)
                {

                    if (!this.testWater(_loc_5, _loc_4))
                    {
                        return false;
                    }
                    _loc_5++;
                }
                _loc_4++;
            }
            _loc_6 = param2.split(";");
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 = _loc_6.get(_loc_7);
                _loc_9 = _loc_8.split("|");
                _loc_5 = param1.right + int(_loc_9.get(0));
                _loc_4 = param1.top + int(_loc_9.get(1));
                if (!this.testWater(_loc_5, _loc_4))
                {
                    return true;
                }
                _loc_7++;
            }
            return false;
        }//end

        public boolean  positionValidShipOnWater (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left ;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (!this.testWater(_loc_2, _loc_3))
                    {
                        return false;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return true;
        }//end

        public boolean  testValidLand (int param1 ,int param2 )
        {
            if (this.m_collisionBitmap == null)
            {
                return false;
            }
            _loc_3 = this.m_collisionBitmap.bitmapData.rect.width ;
            _loc_4 = this.m_collisionBitmap.bitmapData.rect.height ;
            _loc_5 = param1+_loc_3 /2+this.getXOffset ();
            _loc_6 = _loc_4/2-param2 -1+this.getYOffset ();
            if (_loc_5 < 0 || _loc_5 >= _loc_3 || _loc_6 < 0 || _loc_6 >= _loc_4)
            {
                if (_loc_5 < 0 || _loc_6 >= _loc_3)
                {
                    return false;
                }
                return true;
            }
            _loc_7 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 );
            _loc_8 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 )==WaterManager.LAND_COLOR ;
            if (_loc_7 == WaterManager.NO_TREE_LAND_COLOR)
            {
                _loc_8 = true;
            }
            if (Global.player.allowRightSideBuild())
            {
                if (_loc_7 == WaterManager.RIGHT_SIDE_LAND_COLOR)
                {
                    _loc_8 = true;
                }
                if (_loc_7 == WaterManager.RIGHT_SIDE_NO_TREE_LAND_COLOR)
                {
                    _loc_8 = true;
                }
            }
            return _loc_8;
        }//end

        public boolean  testValidExpansion (int param1 ,int param2 )
        {
            if (this.m_collisionBitmap == null)
            {
                return false;
            }
            _loc_3 = this.m_collisionBitmap.bitmapData.rect.width ;
            _loc_4 = this.m_collisionBitmap.bitmapData.rect.height ;
            _loc_5 = param1+_loc_3 /2+this.getXOffset ();
            _loc_6 = _loc_4/2-param2 -1+this.getYOffset ();
            if (_loc_5 < 0 || _loc_5 >= _loc_3 || _loc_6 < 0 || _loc_6 >= _loc_4)
            {
                if (_loc_5 < 0 || _loc_6 >= _loc_3)
                {
                    return false;
                }
                return true;
            }
            _loc_7 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 );
            _loc_8 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 )==WaterManager.LAND_COLOR ;
            if (_loc_7 == WaterManager.NO_TREE_LAND_COLOR)
            {
                _loc_8 = true;
            }
            if (Global.player.allowRightSideBuild())
            {
                if (_loc_7 == WaterManager.RIGHT_SIDE_LAND_COLOR)
                {
                    _loc_8 = true;
                }
                if (_loc_7 == WaterManager.RIGHT_SIDE_NO_TREE_LAND_COLOR)
                {
                    _loc_8 = true;
                }
            }
            return _loc_8;
        }//end

        public boolean  testValidTree (int param1 ,int param2 )
        {
            if (this.m_collisionBitmap == null)
            {
                return false;
            }
            _loc_3 = this.m_collisionBitmap.bitmapData.rect.width ;
            _loc_4 = this.m_collisionBitmap.bitmapData.rect.height ;
            _loc_5 = param1+_loc_3 /2+this.getXOffset ();
            _loc_6 = _loc_4/2-param2 -1+this.getYOffset ();
            if (_loc_5 < 0 || _loc_5 >= _loc_3 || _loc_6 < 0 || _loc_6 >= _loc_4)
            {
                if (_loc_5 < 0 || _loc_6 >= _loc_3)
                {
                    return false;
                }
                return true;
            }
            _loc_7 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 );
            _loc_8 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 )==WaterManager.LAND_COLOR ;
            if (_loc_7 == WaterManager.RIGHT_SIDE_LAND_COLOR)
            {
                _loc_8 = true;
            }
            if (_loc_7 == WaterManager.NO_TREE_LAND_COLOR)
            {
                _loc_8 = false;
            }
            return _loc_8;
        }//end

        public boolean  testWater (int param1 ,int param2 )
        {
            if (this.m_collisionBitmap == null)
            {
                return false;
            }
            _loc_3 = this.m_collisionBitmap.bitmapData.rect.width ;
            _loc_4 = this.m_collisionBitmap.bitmapData.rect.height ;
            _loc_5 = param1+_loc_3 /2+this.getXOffset ();
            _loc_6 = _loc_4/2-param2 -1+this.getYOffset ();
            if (_loc_5 < 0 || _loc_5 >= _loc_3 || _loc_6 < 0 || _loc_6 >= _loc_4)
            {
                if (_loc_5 < 0 || _loc_6 >= _loc_3)
                {
                    return true;
                }
                return false;
            }
            _loc_7 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 );
            _loc_8 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 )==WATER_COLOR ;
            return this.m_collisionBitmap.bitmapData.getPixel(_loc_5, _loc_6) == WATER_COLOR;
        }//end

        private double  getXOffset ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1 );
            if (_loc_1 > 0)
            {
                return this.xOffset;
            }
            return 0;
        }//end

        private double  getYOffset ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1 );
            if (_loc_1 > 0)
            {
                return this.yOffset;
            }
            return 0;
        }//end

        public boolean  testSand (int param1 ,int param2 )
        {
            if (this.m_collisionBitmap == null)
            {
                return false;
            }
            _loc_3 = this.m_collisionBitmap.bitmapData.rect.width ;
            _loc_4 = this.m_collisionBitmap.bitmapData.rect.height ;
            _loc_5 = param1+_loc_3 /2+this.getXOffset ();
            _loc_6 = _loc_4/2-param2 -1+this.getYOffset ();
            if (_loc_5 < 0 || _loc_5 >= _loc_3 || _loc_6 < 0 || _loc_6 >= _loc_4)
            {
                return false;
            }
            _loc_7 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 );
            _loc_8 = this.m_collisionBitmap.bitmapData.getPixel(_loc_5 ,_loc_6 )==SAND_COLOR ;
            return this.m_collisionBitmap.bitmapData.getPixel(_loc_5, _loc_6) == SAND_COLOR;
        }//end

        public void  dbgCollision ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            boolean _loc_3 =false ;
            Point _loc_4 =null ;
            this.m_dbgCollisionShape = new Shape();
            GlobalEngine.viewport.objectBase.addChild(this.m_dbgCollisionShape);
            Debug.debug4("dbgCollision.m_collisionBitmap " + (m_collisionBitmap==null?"null":" not null"));
            if(this.m_collisionBitmap == null) return ;
            if(m_drawn) return;
            m_drawn = true;
            _loc_1 = (-this.m_collisionBitmap.bitmapData.rect.width) / 2;
            while (_loc_1 < this.m_collisionBitmap.bitmapData.rect.width / 2)
            {

                _loc_2 = (-this.m_collisionBitmap.bitmapData.rect.height) / 2;
                while (_loc_2 < this.m_collisionBitmap.bitmapData.rect.height / 2)
                {

                    _loc_3 = this.testWater(_loc_1, _loc_2);
                    this.m_dbgCollisionShape.graphics.lineStyle(0.5, 4278255615);
                    if ((_loc_1 == (-this.m_collisionBitmap.bitmapData.rect.width) / 2 || _loc_1 == this.m_collisionBitmap.bitmapData.rect.width / 2) && (_loc_2 == (-this.m_collisionBitmap.bitmapData.rect.height) / 2 || _loc_2 == this.m_collisionBitmap.bitmapData.rect.height / 2))
                    {
                        _loc_3 = true;
                        this.m_dbgCollisionShape.graphics.lineStyle(0.5, 4294901760);
                    }
                    if (_loc_3)
                    {
                        _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, _loc_2);
                        this.m_dbgCollisionShape.graphics.moveTo(_loc_4.x, _loc_4.y);
                        _loc_4 = IsoMath.tilePosToPixelPos((_loc_1 + 1), _loc_2);
                        this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                        _loc_4 = IsoMath.tilePosToPixelPos((_loc_1 + 1), (_loc_2 + 1));
                        this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                        _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, (_loc_2 + 1));
                        this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                        _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, _loc_2);
                        this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                    }
                    else
                    {
                        _loc_3 = this.testValidTree(_loc_1, _loc_2);
                        if (!_loc_3)
                        {
                            if (this.testSand(_loc_1, _loc_2))
                            {
                                this.m_dbgCollisionShape.graphics.lineStyle(1, 4294967040);
                            }
                            else
                            {
                                this.m_dbgCollisionShape.graphics.lineStyle(1, 4278255360);
                            }
                            _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, _loc_2);
                            this.m_dbgCollisionShape.graphics.moveTo(_loc_4.x, _loc_4.y);
                            _loc_4 = IsoMath.tilePosToPixelPos(_loc_1 + 0.9, _loc_2);
                            this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                            _loc_4 = IsoMath.tilePosToPixelPos(_loc_1 + 0.9, _loc_2 + 0.9);
                            this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                            _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, _loc_2 + 0.9);
                            this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                            _loc_4 = IsoMath.tilePosToPixelPos(_loc_1, _loc_2);
                            this.m_dbgCollisionShape.graphics.lineTo(_loc_4.x, _loc_4.y);
                        }
                    }
                    _loc_2++;
                }
                _loc_1++;
            }
            return;
        }//end

        protected void  doGenerateWater ()
        {
            this.dbgCollision();
            return;
        }//end

        public boolean  positionValidOnLandAndWater (Rectangle param1 ,int param2 =0,int param3 =0)
        {
            int _loc_7 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_6 = param1.left ;
            while (_loc_6 < param1.left + param1.width)
            {

                _loc_7 = param1.top;
                while (_loc_7 < param1.top + param1.height)
                {

                    if (this.testValidLand(_loc_6, _loc_7))
                    {
                        _loc_4++;
                    }
                    if (this.testWater(_loc_6, _loc_7))
                    {
                        _loc_5++;
                    }
                    if (_loc_5 >= param3 && _loc_4 >= param2)
                    {
                        return true;
                    }
                    _loc_7++;
                }
                _loc_6++;
            }
            return false;
        }//end

    }


