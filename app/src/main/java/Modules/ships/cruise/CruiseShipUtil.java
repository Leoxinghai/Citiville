package Modules.ships.cruise;

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
import Engine.Helpers.*;


    public class CruiseShipUtil
    {

        public  CruiseShipUtil ()
        {
            return;
        }//end

        public static Array  getAllDockBuildings ()
        {
            return Global.world.getObjectsByNames(.get("dock_house"));
        }//end

        public static MapResource  getDockBuilding ()
        {
            Array _loc_1 =getAllDockBuildings ();
            return _loc_1.length == 0 ? (null) : (MathUtil.randomElement(_loc_1) as MapResource);
        }//end

        public static Vector3  getPiersideDockEntrance (MapResource param1 )
        {
            return getEntrance(param1, true);
        }//end

        public static Vector3  getRoadsideDockEntrance (MapResource param1 )
        {
            Vector3 _loc_2 =getEntrance(param1 ,false );
            (_loc_2.x + 1);
            return _loc_2;
        }//end

        public static Vector3  getEntrance (MapResource param1 ,boolean param2 )
        {
            _loc_3 = param1.getHotspots ();
            if (_loc_3 == null || _loc_3.length == 0)
            {
                return null;
            }
            _loc_4 = param2? (0) : ((_loc_3.length - 1));
            return _loc_3.get(_loc_4);
        }//end



    }



