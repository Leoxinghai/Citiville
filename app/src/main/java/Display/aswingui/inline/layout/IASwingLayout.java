package Display.aswingui.inline.layout;

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

import Display.aswingui.inline.*;
import org.aswing.*;

    public interface IASwingLayout
    {

//        public  IASwingLayout ();

        IASwingLayout  gap (int param1 );

        IASwingLayout  align (int param1 );

        IASwingLayout  horizontalGap (int param1 );

        IASwingLayout  verticalGap (int param1 );

        IASwingLayout  horizontalAlign (int param1 );

        IASwingLayout  verticalAlign (int param1 );

        IASwingLayout  margin (boolean param1 );

        LayoutManager  manager ();

        Object  constrain (IASwingNode param1 );

    }


