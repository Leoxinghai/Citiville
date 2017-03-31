package Classes.util;

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
import Classes.doobers.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import GameMode.GameMode;
import com.adobe.utils.*;
//import flash.geom.*;

import com.xinghai.Debug;

    public class TransparencyUtil
    {

        public  TransparencyUtil ()
        {
            return;
        }//end

        public static boolean  pointingToObjBase (GameObject param1 ,Vector2 param2 )
        {
            _loc_3 = param1.getPositionNoClone ();
            _loc_4 = param1.getSizeNoClone ();
            return _loc_3.y <= param2.y && param2.y <= _loc_3.y + _loc_4.y && _loc_3.x <= param2.x && param2.x <= _loc_3.x + _loc_4.x;
        }//end

        public static boolean  isTransparencyType (GameObject param1 )
        {
            return !(param1 instanceof Doober) && !(param1 instanceof Plot) && !(param1 instanceof NPC) && !(param1 instanceof Road) && !(param1 instanceof Sidewalk);
        }//end

        public static Array  getObjectListFromPoint (Point param1 )
        {
            WorldObject _loc_5 =null ;
            _loc_2 = IsoMath.stageToViewport(param1 );
            Array _loc_3 =Global.world.getPickObjects(_loc_2 ,Constants.WORLDOBJECT_ALL );
            _loc_4 =Global.world.getCollisionMap ().getObjectsByPosition(Math.floor(GameMode.GameMode.getMouseTilePos ().x ),Math.floor(GameMode.GameMode.getMouseTilePos ().y ));

            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

	        Debug.debug7("TransparencyUtil.getObjectListFromPoint " + _loc_5);

                if (_loc_5 instanceof MapResource && (_loc_5 as MapResource).usingTilePicking())
                {

	            Debug.debug7("TransparencyUtil.getObjectListFromPoint.2 " + _loc_3.length());
                    if (!ArrayUtil.arrayContainsValue(_loc_3, _loc_5))
                    {

	                Debug.debug7("TransparencyUtil.getObjectListFromPoint.3 ");
                        _loc_3.push(_loc_5);
                    }
                }
            }


            return _loc_3;
        }//end

    }



