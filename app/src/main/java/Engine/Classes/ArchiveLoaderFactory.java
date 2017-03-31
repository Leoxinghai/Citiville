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

//import flash.display.*;
//import flash.net.*;

    public class ArchiveLoaderFactory
    {

        public  ArchiveLoaderFactory ()
        {
            throw new Error("Trying to instantiate a static class");
        }//end

        public static Loader  createLoader (String param1 ,String param2 )
        {
            return createInternal(param1, param2, false) as Loader;
        }//end

        public static URLLoader  createURLLoader (String param1 ,String param2 )
        {
            return createInternal(param1, param2, true) as URLLoader;
        }//end

        private static Object createInternal (String param1 ,String param2 ,boolean param3 )
        {
            _loc_4 = null;
            _loc_5 = param2;
            if (param2.lastIndexOf("/") >= 0)
            {
                _loc_5 = _loc_5.substr((_loc_5.lastIndexOf("/") + 1), _loc_5.length());
            }
            _loc_6 = param1.substr ((param1.lastIndexOf(".")+1),param1.length );
            switch(_loc_6)
            {
                case "zip":
                {
                    if (param3)
                    {
                        _loc_4 = new ZipURLLoader(_loc_5);
                    }
                    else
                    {
                        _loc_4 = new ZipLoader(_loc_5);
                    }
                    break;
                }
                default:
                {
                    throw new Error("Unsupported resource type");
                    break;
                }
            }
            return _loc_4;
        }//end

    }



