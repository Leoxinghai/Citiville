package Engine;

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

import Engine.Classes.*;
import Engine.Helpers.*;
import root.GlobalEngine;
//import flash.display.*;
//import flash.geom.*;

    public class IsoMath
    {
        public static  Array DIRECTION_VECTORS =.get( {x new Vector2(1,-1),y Vector2 (-1,-1)},{x Vector2(1,1),y Vector2(1,-1)},{x Vector2 (-1,1),y Vector2(1,1)},{x Vector2 (-1,-1),y Vector2 (-1,1)}) ;

        public  IsoMath ()
        {
            return;
        }//end

        public static Vector2  tilePosToPixelPos (double param1 ,double param2 ,double param3,boolean param4)
        {
            double _loc_5 =0;
            double _loc_6 =0;
            World _loc_7 = World.getInstance();
            Vector2 _loc_8 = new Vector2(0, 0);
            int _loc_9 = param4? (_loc_7.getRotation()) : (Constants.ROTATION_0);
            int _loc_10 = _loc_7.getGridWidth ();
            int _loc_11 = _loc_7.getGridHeight();
            switch(_loc_9)
            {
                case Constants.ROTATION_0:
                {
                    _loc_8.x = _loc_10 * Constants.TILE_WIDTH / 2;
                    _loc_8.y = _loc_11 * Constants.TILE_HEIGHT;
                    break;
                }
                case Constants.ROTATION_90:
                {
                    _loc_8.x = 0;
                    _loc_8.y = _loc_11 * Constants.TILE_HEIGHT / 2;
                    break;
                }
                case Constants.ROTATION_180:
                {
                    _loc_8.x = _loc_10 * Constants.TILE_WIDTH / 2;
                    _loc_8.y = 0;
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_8.x = _loc_10 * Constants.TILE_WIDTH;
                    _loc_8.y = _loc_11 * Constants.TILE_HEIGHT / 2;
                    break;
                }
                default:
                {
                    break;
                }
            }
            Object _loc_12 = DIRECTION_VECTORS.get(_loc_9);
            _loc_5 = (DIRECTION_VECTORS.get(_loc_9).x.x * param1 + _loc_12.y.x * param2) * (Constants.TILE_WIDTH / 2) + _loc_8.x;
            _loc_6 = (_loc_12.x.y * param1 + _loc_12.y.y * param2) * (Constants.TILE_HEIGHT / 2) + _loc_8.y;
            if (Vector3.optimizeMemoryUse)
            {
                _loc_8.x = _loc_5;
                _loc_8.y = _loc_6;
                return _loc_8;
            }
            return new Vector2(_loc_5, _loc_6);
        }//end

        public static Vector2  screenPosToTilePos (double param1 ,double param2 ,boolean param3 =false )
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Point _loc_6 = IsoMath.stageToViewport(new Point(param1 ,param2 ));
            param1 = IsoMath.stageToViewport(new Point(param1, param2)).x;
            param2 = _loc_6.y;
            World _loc_7 = World.getInstance();
            int _loc_8 = World.getInstance().getGridWidth();
            int _loc_9 = _loc_7.getGridHeight ();
            int _loc_10 = param3? (_loc_7.getRotation()) : (Constants.ROTATION_0);
            Vector2 _loc_11 =new Vector2 ();
            switch(_loc_10)
            {
                case Constants.ROTATION_0:
                {
                    _loc_11.x = _loc_8 * Constants.TILE_WIDTH / 2;
                    _loc_11.y = _loc_9 * Constants.TILE_HEIGHT;
                    break;
                }
                case Constants.ROTATION_90:
                {
                    _loc_11.x = 0;
                    _loc_11.y = _loc_9 * Constants.TILE_HEIGHT / 2;
                    break;
                }
                case Constants.ROTATION_180:
                {
                    _loc_11.x = _loc_8 * Constants.TILE_WIDTH / 2;
                    _loc_11.y = 0;
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_11.x = _loc_8 * Constants.TILE_WIDTH;
                    _loc_11.y = _loc_9 * Constants.TILE_HEIGHT / 2;
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_12 = DIRECTION_VECTORS.get(_loc_10);
            param1 = (param1 - _loc_11.x) / (Constants.TILE_WIDTH / 2);
            param2 = (param2 - _loc_11.y) / (Constants.TILE_HEIGHT / 2);
            _loc_5 = (param2 / _loc_12.x.y - param1 / _loc_12.x.x) / (_loc_12.y.y / _loc_12.x.y - _loc_12.y.x / _loc_12.x.x);
            _loc_4 = (param1 - _loc_12.y.x * _loc_5) / _loc_12.x.x;
            if (Vector3.optimizeMemoryUse)
            {
                _loc_11.x = _loc_4;
                _loc_11.y = _loc_5;
                return _loc_11;
            }
            return new Vector2(_loc_4, _loc_5);
        }//end

        public static Vector2  getPixelDeltaFromTileDelta (double param1 ,double param2 ,boolean param3 =true )
        {
            World _loc_4 = World.getInstance();
            Vector2 _loc_5 =new Vector2 (0, 0);
            if(param3) {
            switch(_loc_4.getRotation())
            {
                case Constants.ROTATION_0:
                {
                    _loc_5.x = (((param1 - param2) / 2) * Constants.TILE_WIDTH);
                    _loc_5.y = ((-((param1 + param2)) / 2) * Constants.TILE_HEIGHT);

                    break;
                }
                case Constants.ROTATION_90:
                {
                    _loc_5.x = (((param1 + param2) / 2) * Constants.TILE_WIDTH);
                    _loc_5.y = (((param1 - param2) / 2) * Constants.TILE_HEIGHT);
                    break;
                }
                case Constants.ROTATION_180:
                {
                    _loc_5.x = (((param2 - param1) / 2) * Constants.TILE_WIDTH);
                    _loc_5.y = (((param1 + param2) / 2) * Constants.TILE_HEIGHT);
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_5.x = ((-((param1 + param2)) / 2) * Constants.TILE_WIDTH);
                    _loc_5.y = (((param2 - param1) / 2) * Constants.TILE_HEIGHT);
                    break;
                }
                default:
                {
                    break;
                }
            }
            } else {
                    _loc_5.x = (((param1 - param2) / 2) * Constants.TILE_WIDTH);
                    _loc_5.y = ((-((param2 - param1)) / 2) * Constants.TILE_HEIGHT);
	    }
            return _loc_5;

        }//end

        public static int  getRotatedDirection (int param1 ,boolean param2 =false )
        {
            int _loc_3 =0;
            _loc_4 = World.getInstance();
            if (param2 == false)
            {
                _loc_3 = param1 - _loc_4.getRotation();
                if (_loc_3 < 0)
                {
                    _loc_3 = _loc_3 + 4;
                }
            }
            else
            {
                _loc_3 = param1 + _loc_4.getRotation();
            }
            return _loc_3 % 4;
        }//end

        public static Vector3  getRotatedPosition (Vector3 param1 ,boolean param2)
        {
            Vector3 _loc_3 =new Vector3 (0, 0, 0);
            World _loc_4 = World.getInstance();
            int _loc_5 = World.getInstance().getGridWidth();
            int _loc_6 = _loc_4.getGridHeight ();
            switch(_loc_4.getRotation())
            {
                case Constants.ROTATION_0:
                {
                    _loc_3.x = param1.x;
                    _loc_3.y = param1.y;
                    break;
                }
                case Constants.ROTATION_90:
                {
                    _loc_3.x = param1.y;
                    _loc_3.y = (_loc_6 - 1) - param1.x;
                    break;
                }
                case Constants.ROTATION_180:
                {
                    _loc_3.x = (_loc_5 - 1) - param1.x;
                    _loc_3.y = (_loc_6 - 1) - param1.y;
                    break;
                }
                case Constants.ROTATION_270:
                {
                    _loc_3.x = (_loc_5 - 1) - param1.y;
                    _loc_3.y = param1.x;
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_3.z = param1.z;
            if (param2)
            {
                if (_loc_3.x < 0)
                {
                    _loc_3.x = 0;
                }
                if (_loc_3.y < 0)
                {
                    _loc_3.y = 0;
                }
            }
            return _loc_3;
        }//end

        public static Matrix  generateIsoTileMatrix (DisplayObject param1 , Vector2 param2 )
        {
            World _loc_3 = World.getInstance();
            if (param2 == null)
            {
                param2 = new Vector2(1, 1);
            }
            param2.x = param2.x * Constants.TILE_WIDTH;
            param2.y = param2.y * Constants.TILE_HEIGHT;
            Point _loc_4 =new Point(param2.x /(param1.width *Math.SQRT2 ),param2.y /(param1.height *Math.SQRT2 ));
            Matrix _loc_5 =new Matrix ();
            _loc_5.translate((-param1.width) / 2, (-param1.height) / 2);
            _loc_5.rotate((-45 + _loc_3.getRotation() * 90) * Math.PI / 180);
            _loc_5.translate(param1.width / 2 * Math.SQRT2, param1.height / 2 * Math.SQRT2);
            _loc_5.scale(_loc_4.x, _loc_4.y);
            return _loc_5;
        }//end

        public static Point  stageToViewport (Point param1 )
        {
            _loc_2 = GlobalEngine.viewport.getTransformMatrix ().clone ();
            _loc_2.invert();
            _loc_3 = param1.clone ();
            _loc_3 = _loc_2.transformPoint(_loc_3);
            return _loc_3;
        }//end

        public static Point  viewportToStage (Point param1 )
        {
            Point _loc_2 = param1.clone();
            _loc_2 = GlobalEngine.viewport.getTransformMatrix().transformPoint(_loc_2);
            return _loc_2;
        }//end

    }



