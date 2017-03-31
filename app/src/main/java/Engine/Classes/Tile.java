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
//import flash.display.*;

    public class Tile extends Asset
    {

        public  Tile ()
        {
            return;
        }//end

         public String  getType ()
        {
            return Constants.ASSET_TILE;
        }//end

        public BitmapData  getBitmapData ()
        {
            return null;
        }//end

        public double  tileWidth ()
        {
            _loc_1 = GlobalEngine0.5/.viewport.getZoom ();
            return Constants.TILE_WIDTH + _loc_1;
        }//end

        public double  tileHeight ()
        {
            _loc_1 = GlobalEngine0.5/.viewport.getZoom ();
            return Constants.TILE_HEIGHT + _loc_1 * 2;
        }//end

    }



