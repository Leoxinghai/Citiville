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

import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;

    public interface IASwingPanel extends IASwingNode
    {

 //       public  IASwingPanel ();

        IASwingPanel  position (int param1 ,int param2 );

        IASwingPanel  size (int param1 ,int param2 );

        IASwingPanel  strings (String param1 );

        IASwingPanel  replacements (Object param1 );

        IASwingPanel  style (IASwingStyle param1 );

        IASwingPanel  layout (IASwingLayout param1 );

        IASwingPanel  add (IASwingNode param1 );

        IASwingNode  (String param1 );

    }


