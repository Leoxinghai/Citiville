package Display.aswingui.inline;

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

import Display.aswingui.inline.style.*;
    public interface IASwingImage extends IASwingNode
    {

//        public  IASwingImage ();

        IASwingImage  style (IASwingStyle param1 );

        IASwingImage  position (int param1 ,int param2 );

        IASwingImage  size (int param1 ,int param2 );

        IASwingImage  priority (int param1 );

        IASwingImage  source (Object param1 );

        IASwingImage  alternateSource (Object param1 );

        IASwingImage  scaleMode (int param1 );

    }


