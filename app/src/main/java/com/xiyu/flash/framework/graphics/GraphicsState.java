package com.xiyu.flash.framework.graphics;
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
//import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.*;

//import flash.geom.Rectangle;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.resources.fonts.FontInst;

    public class GraphicsState {

        public Rectangle clipRect ;
        public com.xiyu.util.Matrix affineMatrix ;
        public FontInst font =null ;

        public  GraphicsState (){
            this.clipRect = new Rectangle();
            this.affineMatrix = new com.xiyu.util.Matrix();
            this.font = null;
        }
    }


