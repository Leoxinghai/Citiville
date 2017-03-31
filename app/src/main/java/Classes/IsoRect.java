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
//import flash.geom.*;

    public class IsoRect
    {
        private Point _left ;
        private Point _right ;
        private Point _top ;
        private Point _bottom ;
        private double _width =0;
        private double _height =0;
        public static double TILE_HEIGHT =16;
        public static double TILE_SIZE =4;

        public  IsoRect (Point param1 ,Point param2 ,Point param3 =null ,Point param4 =null )
        {
            this._left = param1;
            this._right = param2;
            this._top = param3;
            this._bottom = param4;
            this.setWidth();
            this.setHeight();
            return;
        }//end

        public Point  left ()
        {
            return this._left;
        }//end

        public void  left (Point param1 )
        {
            this._left = param1;
            this.setWidth();
            return;
        }//end

        public Point  right ()
        {
            return this._right;
        }//end

        public void  right (Point param1 )
        {
            this._right = param1;
            this.setWidth();
            return;
        }//end

        public Point  top ()
        {
            return this._top;
        }//end

        public void  top (Point param1 )
        {
            this._top = param1;
            this.setHeight();
            return;
        }//end

        public Point  bottom ()
        {
            return this._bottom;
        }//end

        public void  bottom (Point param1 )
        {
            this._bottom = param1;
            this.setHeight();
            return;
        }//end

        public double  width ()
        {
            return this._width;
        }//end

        private void  setWidth ()
        {
            this._width = this._right && this._left ? (this._right.x - this._left.x) : (0);
            return;
        }//end

        public double  height ()
        {
            return this._height;
        }//end

        private void  setHeight ()
        {
            this._height = this._bottom && this._top ? (this._bottom.y - this._top.y) : (0);
            return;
        }//end

        public static IsoRect  getIsoRectFromSize (Vector3 param1 )
        {
            _loc_2 = tilePosToPixelPos(0,0);
            _loc_3 = tilePosToPixelPos(param1.x*TILE_SIZE,(-param1.y)*TILE_SIZE);
            _loc_4 = tilePosToPixelPos(param1.x*TILE_SIZE,0);
            _loc_5 = tilePosToPixelPos(0,(-param1.y)*TILE_SIZE);
            IsoRect _loc_6 =new IsoRect(_loc_2 ,_loc_3 ,_loc_4 ,_loc_5 );
            return new IsoRect(_loc_2, _loc_3, _loc_4, _loc_5);
        }//end

        public static Vector2  tilePosToPixelPos (double param1 ,double param2 ,double param3 =0)
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Vector2 _loc_6 =new Vector2(Constants.TILE_WIDTH /2,Constants.TILE_HEIGHT );
            _loc_7 = IsoMath.DIRECTION_VECTORS.get(Constants.ROTATION_0) ;
            _loc_4 = (IsoMath.DIRECTION_VECTORS.get(Constants.ROTATION_0).x.x * param1 + _loc_7.y.x * param2) * (Constants.TILE_WIDTH / 2);
            _loc_5 = (_loc_7.x.y * param1 + _loc_7.y.y * param2) * (Constants.TILE_HEIGHT / 2);
            _loc_5 = _loc_5 + param3 * TILE_HEIGHT;
            return new Vector2(_loc_4, _loc_5);
        }//end

    }


