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

import Engine.Helpers.*;
    public class PathElement
    {
        public int type ;
        public Vector3 basePosition ;
        public Vector3 offsetPosition ;
        public static  int TYPE_ROAD =0;
        public static  int TYPE_NONROAD =1;

        public  PathElement (Vector3 param1 ,Vector3 param2 ,int param3 =0)
        {
            this.type = param3;
            this.basePosition = param1.clone();
            this.offsetPosition = param2 != null ? (param2.clone()) : (param1.clone());
            return;
        }//end

        public static PathElement  createInstance (Vector3 param1 ,...args )
        {
            return new PathElement(param1);
        }//end

        public static Vector3  extractBasePosition (PathElement param1 ,...args )
        {
            return param1.basePosition;
        }//end

        public static Vector3  extractOffsetPosition (PathElement param1 ,...args )
        {
            return param1.offsetPosition;
        }//end

    }


