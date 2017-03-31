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

import Engine.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
//import flash.display.*;

    public class TrainTracks extends MapResource
    {
        private  String TRAIN_TRACKS ="trainTracks";
        protected  String STATE_STATIC ="static";
        protected Sprite m_sprite =null ;
        protected Bitmap m_bitmap =null ;
        protected ItemImageInstance m_savImageClass =null ;

        public  TrainTracks (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.TRAIN_STATION;
            setState(this.STATE_STATIC);
            m_typeName = this.TRAIN_TRACKS;
            m_ownable = false;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            return;
        }//end

         protected String  getLayerName ()
        {
            return "road";
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public void  drawDisplayObject ()
        {
            Bitmap _loc_2 =null ;
            super.drawDisplayObject();
            if (this.m_sprite == null)
            {
                if (m_itemImage instanceof Bitmap)
                {
                    _loc_2 =(Bitmap) m_itemImage;
                }
                else if (m_itemImage instanceof CompositeItemImage)
                {
                    _loc_2 =(Bitmap)(CompositeItemImage).getBuildingImageByType(ItemImageInstance.BUILDING_IMAGE)) (m_itemImage;
                }
                if (_loc_2 != null)
                {
                    this.initializeTrainTracksSprite(_loc_2);
                }
            }
            _loc_1 = (Sprite)m_displayObject
            if (_loc_1 != null && this.m_sprite != null && !_loc_1.contains(this.m_sprite))
            {
                replaceContent(this.m_sprite);
            }
            return;
        }//end

        private void  initializeTrainTracksSprite (Bitmap param1 )
        {
            Vector2 _loc_3 =null ;
            int _loc_4 =0;
            Bitmap _loc_8 =null ;
            this.m_bitmap = param1;
            this.m_sprite = new Sprite();
            this.m_sprite.addChild(m_itemImage);
            m_itemImage.x = 0;
            m_itemImage.y = 0;
            _loc_2 = m_itemImage.width;
            if (m_item.sizeX > m_item.sizeY)
            {
                _loc_3 = IsoMath.getPixelDeltaFromTileDelta(m_item.sizeY, 0);
                _loc_4 = m_item.sizeX / m_item.sizeY;
            }
            else
            {
                _loc_3 = IsoMath.getPixelDeltaFromTileDelta(0, m_item.sizeX);
                _loc_4 = m_item.sizeY / m_item.sizeX;
            }
            _loc_3.x = _loc_3.x / m_displayObject.scaleX;
            _loc_3.y = _loc_3.y / m_displayObject.scaleY;
            int _loc_5 =0;
            _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1 );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1) > 0)
            {
                _loc_5 = 2;
            }
            int _loc_7 =1;
            while (_loc_7 < _loc_4 + _loc_5)
            {

                _loc_8 = new Bitmap(param1.bitmapData, "auto", true);
                _loc_8.x = _loc_3.x * _loc_7;
                _loc_8.y = _loc_3.y * _loc_7;
                this.m_sprite.addChild(_loc_8);
                _loc_7++;
            }
            return;
        }//end

         protected void  adjustSize (Vector3 param1 )
        {
            if (param1.x > param1.y)
            {
                param1.x = param1.y;
            }
            else
            {
                param1.y = param1.x;
            }
            return;
        }//end

         public double  getDepthPriority ()
        {
            return -1;
        }//end

         public void  updateDisplayObjectTransform ()
        {
            if (m_imageClass != null)
            {
                this.m_savImageClass = m_imageClass;
            }
            boolean _loc_1 =false ;
            if (m_imageClass == null)
            {
                _loc_1 = true;
                m_imageClass = this.m_savImageClass;
            }
            super.updateDisplayObjectTransform();
            if (_loc_1)
            {
                m_imageClass = null;
            }
            return;
        }//end

    }


