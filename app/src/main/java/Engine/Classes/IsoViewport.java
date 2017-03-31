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

import Engine.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.geom.*;
import com.xinghai.Debug;

    public class IsoViewport extends Viewport
    {
        protected Array m_objectLayers ;
        public static  String LOADED ="loaded";

        public  IsoViewport ()
        {
            this.m_objectLayers = new Array();
            return;
        }//end

        public void  addObjectLayer (ObjectLayer param1 )
        {
            this.m_objectLayers.push(param1);
            this.m_objectLayers.sortOn("priority", Array.NUMERIC);
            while (objectBase.numChildren > 0)
            {

                objectBase.removeChildAt(0);
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_objectLayers.length())
            {


	        objectBase.addChild(this.m_objectLayers.get(_loc_2).getDisplayObject());
                _loc_2++;
            }
            return;
        }//end

        public void  removeObjectLayer (ObjectLayer param1 )
        {
            DisplayObject _loc_3 =null ;
            _loc_2 = this.m_objectLayers.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_objectLayers.splice(_loc_2, 1);
                _loc_3 = param1.getDisplayObject();
                if (_loc_3 && _loc_3.parent)
                {
                    _loc_3.parent.removeChild(_loc_3);
                }
            }
            return;
        }//end

         protected void  drawBackground (BitmapData param1 )
        {
            param1.fillRect(param1.rect, Config.VIEWPORT_CLEAR_COLOR);
            _loc_2 = World.getInstance();
            _loc_3 = _loc_2.getTileMap ();
            _loc_3.render(this);
            _loc_2.updateCulling();
            return;
        }//end

         protected boolean  render (boolean param1 =false )
        {
            _loc_2 = super.render(param1);
            if (_loc_2)
            {
                World.getInstance().updateCulling();
            }
            return _loc_2;
        }//end

         public void  centerViewport ()
        {
            _loc_1 = World.getInstance();
            _loc_2 = _loc_1.getTileMap ();
            this.centerOnTilePos(_loc_2.getWidth() / 2, _loc_2.getHeight() / 2);
            return;
        }//end

        public void  centerOnTilePos (double param1 ,double param2 )
        {
            Vector2 _loc_3 =new Vector2 ();
            _loc_4 = World.getInstance();
            _loc_5 = World.getInstance().getTileMap();
            _loc_6 = IsoMath.getPixelDeltaFromTileDelta(_loc_5.getWidth (),0);
            _loc_7 = IsoMath.getPixelDeltaFromTileDelta(0,_loc_5.getHeight ());
            _loc_8 = _loc_6.add(_loc_7);
            _loc_9 = param1-_loc_5.getWidth ()/2;
            _loc_10 = param2-_loc_5.getHeight()/2;
            _loc_11 = IsoMath.getPixelDeltaFromTileDelta(_loc_9 ,0);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(0,_loc_10 );
            _loc_13 = _loc_11.add(_loc_12);
            if (this.stage != null)
            {
                _loc_3.x = (this.stage.stageWidth - (_loc_6.x - _loc_7.x)) / 2 - _loc_13.x;
                _loc_3.y = (this.stage.stageHeight + _loc_8.y) / 2 - _loc_13.y;
                setScrollPosition(_loc_3);
            }
            return;
        }//end

    }



