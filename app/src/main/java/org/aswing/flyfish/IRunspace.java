package org.aswing.flyfish;

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
//import flash.system.*;
import org.aswing.*;

    public interface IRunspace
    {

        ApplicationDomain  getApplicationDomain ();

        DisplayObject  getAsset (String param1 );

        Component  getCustomComponent (String param1 );

        Class  getClass (String param1 );

        String  getImageUrl (String param1 );

        String  getSrcUrl (String param1 );

        ILazyLoadManager  lazyLoadManager ();

    }


