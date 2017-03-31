package com.xiyu.flash.framework.widgets.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

    public interface IButtonListener {
        void  buttonPress (int _arg1 );
        void  buttonMouseMove (int _arg1 ,int _arg2 ,int _arg3 );
        void  buttonMouseEnter (int _arg1 );
        void  buttonDownTick (int _arg1 );
        void  buttonMouseLeave (int _arg1 );
        void  buttonRelease (int _arg1 );

    }


