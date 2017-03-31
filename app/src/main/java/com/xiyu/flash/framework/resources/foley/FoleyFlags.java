package com.xiyu.flash.framework.resources.foley;
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

import com.xiyu.flash.framework.utils.Flags;
    public class FoleyFlags extends Flags {

        public static final int LOOP =1;
        public static final int USES_MUSIC_VOLUME =8;
        public static final int DONT_REPEAT =16;
        public static final int MUTE_ON_PAUSE =4;
        public static final int ONE_AT_A_TIME =2;
        public static  FoleyFlags NONE =new FoleyFlags(0);

        public  FoleyFlags (int flags){
            setFlags(flags);
        }
    }


