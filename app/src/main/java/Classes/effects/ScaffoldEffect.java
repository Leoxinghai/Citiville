package Classes.effects;

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
//import flash.display.*;
//import flash.geom.*;

    public class ScaffoldEffect extends MapResourceEffect
    {
        private  String TARGET_BUILDING_NAME ="targetBuilding";
        private int m_currentStage ;
        private int m_maxStage ;
        private int m_startStage ;
        private int m_endStage ;
        private String m_targetBuildingName ;
        private boolean m_isLoading =false ;
        private boolean m_isItemImagesLoaded =false ;
        private Object m_itemImageLoadedHash ;
        private Object m_bitmapDataHash ;
        private boolean m_isAnimationCompleted =false ;
        private int m_currentNumFloors ;
        private int m_maxNumFloors ;
        private Bitmap m_backBitmap ;
        private Bitmap m_frontBitmap ;
        private int m_lastRenderedNumFloors =-1;
        private int m_targetBuildingOffsetX ;
        private int m_targetBuildingOffsetY ;
        private int m_targetBuildingDirection ;
        private BitmapData m_originalContentBitmapData ;
        private Point m_originalContentPosition ;
        private Function m_animationCompleteCallback =null ;

        public  ScaffoldEffect (MapResource param1 ,int param2 =1,int param3 =-1,int param4 =3,String param5 ="")
        {
            super(param1, 1);
            this.m_maxStage = param4;
            this.m_startStage = param2;
            this.m_endStage = param3;
            this.m_targetBuildingName = param5;
            this.validateProperties();
            this.m_currentStage = this.m_startStage;
            this.animate(1);
            return;
        }//end

        public int  startStage ()
        {
            return this.m_startStage;
        }//end

        public void  startStage (int param1 )
        {
            this.m_startStage = param1;
            return;
        }//end

        public int  endStage ()
        {
            return this.m_endStage;
        }//end

        public void  endStage (int param1 )
        {
            this.m_endStage = param1;
            return;
        }//end

        public int  maxStage ()
        {
            return this.m_maxStage;
        }//end

        public void  maxStage (int param1 )
        {
            this.m_maxStage = param1;
            return;
        }//end

        public String  targetBuildingName ()
        {
            return this.m_targetBuildingName;
        }//end

        public void  targetBuildingName (String param1 )
        {
            this.m_targetBuildingName = param1;
            return;
        }//end

        public Function  animationCompleteCallback ()
        {
            return this.m_animationCompleteCallback;
        }//end

        public void  animationCompleteCallback (Function param1 )
        {
            this.m_animationCompleteCallback = param1;
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (this.m_isAnimationCompleted)
            {
                return false;
            }
            if (isMapResourceLoaded && !this.m_isLoading && !this.m_isItemImagesLoaded)
            {
                this.loadAnimationEffect();
                if (this.startStage != this.endStage)
                {
                    return true;
                }
                this.m_isAnimationCompleted = true;
                if (this.m_animationCompleteCallback != null)
                {
                    this.m_animationCompleteCallback();
                }
                return false;
            }
            else
            {
                if (this.m_isLoading && !this.m_isItemImagesLoaded)
                {
                    return true;
                }
                if (this.m_isItemImagesLoaded)
                {
                    (this.m_currentStage + 1);
                    if (this.m_currentStage > this.m_endStage)
                    {
                        this.m_currentStage = this.m_endStage;
                        this.m_isAnimationCompleted = true;
                        if (this.m_animationCompleteCallback != null)
                        {
                            this.m_animationCompleteCallback();
                            this.m_currentStage = this.m_maxStage + 1;
                        }
                    }
                    if (this.m_currentStage > this.m_maxStage)
                    {
                        this.cleanUpBitmaps();
                        return false;
                    }
                    this.reattach();
                }
            }
            return true;
        }//end

         public void  cleanUp ()
        {
            this.m_itemImageLoadedHash = null;
            this.m_bitmapDataHash = null;
            this.cleanUpBitmaps();
            this.m_isAnimationCompleted = true;
            m_isCompleted = true;
            return;
        }//end

         public boolean  allowReattachOnReplaceContent ()
        {
            return true;
        }//end

         public void  reattach ()
        {
            Vector3 _loc_6 =null ;
            IsoRect _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            double _loc_13 =0;
            double _loc_14 =0;
            double _loc_15 =0;
            double _loc_16 =0;
            double _loc_17 =0;
            double _loc_18 =0;
            double _loc_19 =0;
            BitmapData _loc_20 =null ;
            BitmapData _loc_21 =null ;
            Point _loc_22 =null ;
            double _loc_23 =0;
            double _loc_24 =0;
            Point _loc_25 =null ;
            BitmapData _loc_26 =null ;
            double _loc_27 =0;
            double _loc_28 =0;
            String _loc_29 =null ;
            int _loc_30 =0;
            int _loc_31 =0;
            double _loc_32 =0;
            String _loc_33 =null ;
            int _loc_34 =0;
            Sprite _loc_35 =null ;
            int _loc_36 =0;
            BitmapData _loc_37 =null ;
            Bitmap _loc_38 =null ;
            BitmapData _loc_39 =null ;
            if (!this.m_isItemImagesLoaded)
            {
                return;
            }
            if (this.m_targetBuildingName && this.m_targetBuildingDirection != m_mapResource.getDirection())
            {
                this.loadAnimationEffect();
                return;
            }
            this.validateProperties();
            double _loc_1 =4;
            if (this.m_currentStage <= this.m_maxNumFloors)
            {
                this.m_currentNumFloors = this.m_currentStage;
            }
            else
            {
                this.m_currentNumFloors = 1;
            }
            if (this.m_currentStage > this.m_maxStage)
            {
                this.m_currentNumFloors = 0;
                this.cleanUpBitmaps();
            }
            if (this.m_currentNumFloors > 0 && this.m_currentNumFloors != this.m_lastRenderedNumFloors)
            {
                this.cleanUpBitmaps();
                _loc_6 = m_mapResource.getReference().getSize();
                _loc_7 = IsoRect.getIsoRectFromSize(_loc_6);
                _loc_8 = 16;
                _loc_9 = 20;
                _loc_10 = _loc_8 >> 1;
                _loc_11 = _loc_9 >> 1;
                _loc_12 = this.m_bitmapDataHash.get("1SW").width;
                _loc_13 = this.m_bitmapDataHash.get("1SW").height;
                _loc_14 = _loc_12 - _loc_8;
                _loc_15 = _loc_13 - _loc_9;
                _loc_16 = 0;
                _loc_17 = _loc_7.height - _loc_7.bottom.y + _loc_15 - _loc_9 * (-(this.m_currentNumFloors - 2));
                _loc_18 = _loc_7.width + _loc_10 - _loc_1;
                _loc_19 = _loc_7.height + _loc_9 * this.m_currentNumFloors + (_loc_15 >> 1) - _loc_1;
                _loc_20 = new BitmapData(_loc_18, _loc_19, true, 0);
                _loc_21 = _loc_20.clone();
                _loc_22 = new Point();
                _loc_23 = _loc_6.x * IsoRect.TILE_SIZE;
                _loc_24 = (-_loc_6.y) * IsoRect.TILE_SIZE;
                _loc_25 = new Point();
                _loc_27 = 0;
                _loc_28 = 0;
                _loc_30 = 0;
                while (_loc_30 < this.m_currentNumFloors)
                {

                    _loc_32 = -_loc_30;
                    _loc_33 = _loc_32 ? ("2") : ("1");
                    _loc_29 = _loc_33 + "SW";
                    _loc_26 = this.m_bitmapDataHash.get(_loc_29);
                    _loc_27 = -_loc_10;
                    _loc_28 = -_loc_15 + _loc_11 - _loc_1;
                    _loc_31 = _loc_6.x;
                    if (_loc_31 > 0)
                    {
                        while (_loc_31--)
                        {

                            _loc_25 = IsoRect.tilePosToPixelPos(_loc_31 * IsoRect.TILE_SIZE, _loc_24, _loc_32);
                            _loc_22.x = _loc_25.x + _loc_16 + _loc_27;
                            _loc_22.y = _loc_25.y + _loc_17 + _loc_28;
                            _loc_21.copyPixels(_loc_26, _loc_26.rect, _loc_22, null, null, true);
                        }
                    }
                    _loc_29 = _loc_33 + "NW";
                    _loc_26 = this.m_bitmapDataHash.get(_loc_29);
                    _loc_27 = 0;
                    _loc_28 = 0;
                    _loc_31 = _loc_6.y;
                    if (_loc_31 > 0)
                    {
                        while (_loc_31--)
                        {

                            _loc_25 = IsoRect.tilePosToPixelPos(0, (-((_loc_6.y - 1) - _loc_31)) * IsoRect.TILE_SIZE, _loc_32);
                            _loc_22.x = _loc_25.x + _loc_16 + _loc_27;
                            _loc_22.y = _loc_25.y + _loc_17 + _loc_28;
                            _loc_21.copyPixels(_loc_26, _loc_26.rect, _loc_22, null, null, true);
                        }
                    }
                    _loc_29 = _loc_33 + "NE";
                    _loc_26 = this.m_bitmapDataHash.get(_loc_29);
                    _loc_27 = 0;
                    _loc_28 = -_loc_15 + _loc_11;
                    _loc_31 = _loc_6.x;
                    if (_loc_31 > 0)
                    {
                        while (_loc_31--)
                        {

                            _loc_25 = IsoRect.tilePosToPixelPos(_loc_31 * IsoRect.TILE_SIZE, 0, _loc_32);
                            _loc_22.x = _loc_25.x + _loc_16 + _loc_27;
                            _loc_22.y = _loc_25.y + _loc_17 + _loc_28;
                            _loc_20.copyPixels(_loc_26, _loc_26.rect, _loc_22, null, null, true);
                        }
                    }
                    _loc_29 = _loc_33 + "SE";
                    _loc_26 = this.m_bitmapDataHash.get(_loc_29);
                    _loc_27 = -_loc_10;
                    _loc_28 = _loc_1;
                    _loc_31 = _loc_6.y;
                    if (_loc_31 > 0)
                    {
                        while (_loc_31--)
                        {

                            _loc_25 = IsoRect.tilePosToPixelPos(_loc_23, (-((_loc_6.y - 1) - _loc_31)) * IsoRect.TILE_SIZE, _loc_32);
                            _loc_22.x = _loc_25.x + _loc_16 + _loc_27;
                            _loc_22.y = _loc_25.y + _loc_17 + _loc_28;
                            _loc_20.copyPixels(_loc_26, _loc_26.rect, _loc_22, null, null, true);
                        }
                    }
                    _loc_30++;
                }
                this.m_frontBitmap = new Bitmap(_loc_21);
                this.m_frontBitmap.smoothing = true;
                this.m_frontBitmap.pixelSnapping = PixelSnapping.AUTO;
                this.m_backBitmap = new Bitmap(_loc_20);
                this.m_backBitmap.smoothing = true;
                this.m_backBitmap.pixelSnapping = PixelSnapping.AUTO;
                this.m_lastRenderedNumFloors = this.m_currentNumFloors;
            }
            _loc_2 = m_mapResource(-.displayObjectOffsetX)/m_mapResource.displayObject.scaleX;
            _loc_3 = m_mapResource(-.displayObjectOffsetY)/m_mapResource.displayObject.scaleY;
            _loc_4 = IsoMath.getPixelDeltaFromTileDelta(0,m_mapResource.sizeY );
            _loc_5 = IsoMath.getPixelDeltaFromTileDelta(0,m_mapResource.sizeY ).x /m_mapResource.displayObject.scaleX ;
            if (this.m_currentNumFloors > 0)
            {
                _loc_34 = 2;
                _loc_40 = _loc_2+_loc_5-_loc_34;
                this.m_backBitmap.x = _loc_40;
                this.m_frontBitmap.x = _loc_40;
                _loc_40 = _loc_3 - this.m_backBitmap.height + _loc_34;
                this.m_backBitmap.y = _loc_40;
                this.m_frontBitmap.y = _loc_40;
                _loc_35 =(Sprite) m_mapResource.getDisplayObject();
                _loc_35.addChild(this.m_frontBitmap);
                if (_loc_35.getChildIndex(m_mapResource.content)-1 < 0)
                {
                    //_loc_35.getChildIndex(m_mapResource.content) = 0;
                }
                _loc_35.addChildAt(this.m_backBitmap, _loc_35.getChildIndex(m_mapResource.content) -1 );
            }
            if (this.m_targetBuildingName && this.m_currentStage >= this.m_maxStage)
            {
                _loc_37 = this.m_bitmapDataHash.get(this.TARGET_BUILDING_NAME);
                if (m_mapResource.content instanceof Bitmap)
                {
                    _loc_38 =(Bitmap) m_mapResource.content;
                    _loc_39 = _loc_38.bitmapData;
                    if (_loc_39 != _loc_37)
                    {
                        this.m_originalContentBitmapData = _loc_39;
                        this.m_originalContentPosition = new Point(_loc_38.x, _loc_38.y);
                    }
                }
                if (_loc_37 && _loc_38)
                {
                    _loc_38.bitmapData = _loc_37;
                    _loc_38.smoothing = true;
                    _loc_38.pixelSnapping = PixelSnapping.AUTO;
                    _loc_38.x = _loc_2 + _loc_5 + this.m_targetBuildingOffsetX;
                    _loc_38.y = _loc_3 - _loc_38.height + this.m_targetBuildingOffsetY;
                }
            }
            return;
        }//end

        protected void  validateProperties ()
        {
            if (this.m_maxStage < 2)
            {
                this.m_maxStage = 2;
            }
            if (this.m_endStage < this.m_startStage)
            {
                this.m_endStage = this.m_startStage;
            }
            this.m_maxNumFloors = Math.max(1, (this.m_maxStage - 1));
            if (this.m_currentStage < this.m_startStage)
            {
                this.m_currentStage = this.m_startStage;
            }
            else if (this.m_currentStage > this.m_endStage)
            {
                this.m_currentStage = this.m_endStage;
            }
            return;
        }//end

        protected void  cleanUpBitmaps ()
        {
            if (this.m_frontBitmap)
            {
                if (this.m_frontBitmap.parent)
                {
                    this.m_frontBitmap.parent.removeChild(this.m_frontBitmap);
                }
                this.m_frontBitmap.bitmapData.dispose();
                this.m_frontBitmap = null;
            }
            if (this.m_backBitmap)
            {
                if (this.m_backBitmap.parent)
                {
                    this.m_backBitmap.parent.removeChild(this.m_backBitmap);
                }
                this.m_backBitmap.bitmapData.dispose();
                this.m_backBitmap = null;
            }
            this.m_lastRenderedNumFloors = -1;
            return;
        }//end

        protected void  loadAnimationEffect ()
        {
            XML imageXML ;
            String imageName ;
            ItemImage itemImage ;
            String imageDirection ;
            XML targetBuildingXML ;
            XMLList targetBuildingImagesXMList ;
            XMLList targetBuildingImageXMLList ;
            XML entry ;
            this.m_isItemImagesLoaded = false;
            this.m_itemImageLoadedHash = {};
            this.m_bitmapDataHash = {};
            XMLList imageXMLList =Global.gameSettings().getScaffoldSetByName("scaffoldA");
            int _loc_2 =0;
            XMLList _loc_3 =imageXMLList ;
            for(int i0 = 0; i0 < imageXMLList.size(); i0++)
            {
            	imageXML = imageXMLList.get(i0);


                imageName = imageXML.@name;
                this.m_itemImageLoadedHash.put(imageName,  false);
            }
            if (this.m_targetBuildingName)
            {
                this.m_targetBuildingDirection = m_mapResource.getDirection();
                imageDirection = ItemImage.getDirectionCode(this.m_targetBuildingDirection);
                targetBuildingXML = Global.gameSettings().getItemByName(this.m_targetBuildingName).xml;
                int _loc_31 =0;
                _loc_41 = targetBuildingXML.image;
                XMLList _loc_21 =new XMLList("");
                Object _loc_51;
                for(int i0 = 0; i0 < _loc_41.size(); i0++)
                {
                		_loc_51 = _loc_41.get(i0);


                    with (_loc_51)
                    {
                        if (@name == "planted")
                        {
                            _loc_21.put(_loc_31++,  _loc_51);
                        }
                    }
                }
                targetBuildingImagesXMList = _loc_21;
                int _loc_32 =0;
                _loc_42 = targetBuildingXML.image;
                XMLList _loc_22 =new XMLList("");
                Object _loc_52;
                for(int i0 = 0; i0 < _loc_42.size(); i0++)
                {
                		_loc_52 = _loc_42.get(i0);


                    with (_loc_52)
                    {
                        if (@name == "planted" && @direction == imageDirection)
                        {
                            _loc_22.put(_loc_32++,  _loc_52);
                        }
                    }
                }
                targetBuildingImageXMLList = _loc_22;
                if (!targetBuildingImageXMLList || !targetBuildingImageXMLList.length())
                {
                    int _loc_33 =0;
                    _loc_43 = targetBuildingXML.image;
                    XMLList _loc_23 =new XMLList("");
                    Object _loc_53;
                    for(int i0 = 0; i0 < _loc_43.size(); i0++)
                    {
                    	_loc_53 = _loc_43.get(i0);


                        with (_loc_53)
                        {
                            if (@name == "planted" && @direction == "ALL")
                            {
                                _loc_23.put(_loc_33++,  _loc_53);
                            }
                        }
                    }
                    targetBuildingImageXMLList = _loc_23;
                }
                if (!targetBuildingImageXMLList || !targetBuildingImageXMLList.length())
                {
                    targetBuildingImageXMLList = targetBuildingXML.image;
                }
                imageXML = targetBuildingImageXMLList.get(0);
                int _loc_24 =0;
                _loc_34 = targetBuildingImageXMLList;
                for(int i0 = 0; i0 < targetBuildingImageXMLList.size(); i0++)
                {
                	entry = targetBuildingImageXMLList.get(i0);


                    if (String(entry.@name) == "static" && String(entry.@direction) == imageDirection)
                    {
                        imageXML = entry;
                    }
                }
                imageXML = imageXML.copy();
                imageXML.@name = this.TARGET_BUILDING_NAME;
                this.m_itemImageLoadedHash.get(this.TARGET_BUILDING_NAME) = false;
                itemImage = new ItemImage(imageXML);
                itemImage.name = this.TARGET_BUILDING_NAME;
                itemImage.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                itemImage.load();
                this.m_targetBuildingOffsetX = itemImage.offsetX;
                this.m_targetBuildingOffsetY = itemImage.offsetY;
            }


            for(int i0 = 0; i0 < imageXMLList.size(); i0++)
            {
            	imageXML = imageXMLList.get(i0);


                itemImage = new ItemImage(imageXML);
                itemImage.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                itemImage.load();
            }
            this.m_isLoading = true;
            return;
        }//end

        protected void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            Bitmap _loc_5 =null ;
            String _loc_6 =null ;
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            if (!this.m_isItemImagesLoaded)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                if (m_isCompleted)
                {
                    return;
                }
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                _loc_4 =(DisplayObject) _loc_2.getInstance().image;
                if (_loc_4 instanceof Bitmap)
                {
                    _loc_5 =(Bitmap) _loc_4;
                }
                else if (_loc_4 instanceof CompositeItemImage)
                {
                    _loc_5 =(Bitmap)(CompositeItemImage).getBuildingImageByType(ItemImageInstance.BUILDING_IMAGE)) (_loc_4;
                }
                this.m_bitmapDataHash.put(_loc_2.name,  _loc_5.bitmapData);
                _loc_6 = _loc_2.name;
                this.m_itemImageLoadedHash.put(_loc_6,  true);
                _loc_7 = true;
                for(int i0 = 0; i0 < this.m_itemImageLoadedHash.size(); i0++)
                {
                	_loc_8 = this.m_itemImageLoadedHash.get(i0);

                    if (!_loc_8)
                    {
                        _loc_7 = false;
                        break;
                    }
                }
                if (_loc_7)
                {
                    this.m_isLoading = false;
                    this.m_isItemImagesLoaded = true;
                    this.reattach();
                }
            }
            return;
        }//end

    }




