package com.xiyu.flash.games.pvz.logic;
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

    public class HitResult {
        private static final int OBJECT_TYPE_PLANT =1;
        private static final int OBJECT_TYPE_SHOVEL =5;
        private static final int OBJECT_TYPE_PROJECTILE =2;
        private static final int OBJECT_TYPE_COIN =3;
        private static final int OBJECT_TYPE_NONE =0;
        private static final int OBJECT_TYPE_SEEDPACKET =4;

        public Object mObject ;
        public int mObjectType ;

    }


