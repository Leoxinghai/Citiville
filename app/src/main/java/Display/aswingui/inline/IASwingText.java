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
    public interface IASwingText extends IASwingNode
    {

//        public  IASwingText ();

        IASwingText  style (IASwingStyle param1 );

        IASwingText  textKey (String param1 );

        IASwingText  strings (String param1 );

        IASwingText  replacements (Object param1 );

        IASwingText  position (int param1 ,int param2 );

        IASwingText  size (int param1 ,int param2 );

        IASwingText  selectable (boolean param1 =true );

    }


