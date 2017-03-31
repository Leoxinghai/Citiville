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
    public interface IASwingButton extends IASwingNode
    {

//        public  IASwingButton ();

        IASwingButton  style (IASwingStyle param1 );

        IASwingButton  textKey (String param1 );

        IASwingButton  strings (String param1 );

        IASwingButton  replacements (Object param1 );

        IASwingButton  position (int param1 ,int param2 );

        IASwingButton  size (int param1 ,int param2 );

        IASwingButton  enable (boolean param1 );

    }


