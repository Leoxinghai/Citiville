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

import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class GameObject {

        public static final int RENDER_LAYER_GROUND =200000;
        public static final int RENDER_LAYER_UI_TOP =700000;
        public static final int RENDER_LAYER_LAWN =300000;
        public static final int RENDER_LAYER_FOG =500000;
        public static final int RENDER_LAYER_ABOVE_UI =800000;
        public static final int RENDER_LAYER_COIN_BANK =600000;
        public static final int RENDER_LAYER_GRAVE_STONE =(RENDER_LAYER_LAWN +1000);//301000
        public static final int RENDER_LAYER_SCREEN_FADE =900000;
        public static final int RENDER_LAYER_ZOMBIE =(RENDER_LAYER_LAWN +3000);//303000
        public static final int RENDER_LAYER_TOP =400000;
        public static final int RENDER_LAYER_UI_BOTTOM =100000;
        public static final int RENDER_LAYER_PROJECTILE =(RENDER_LAYER_LAWN +5000);//305000
        public static final int RENDER_LAYER_LAWN_MOWER =(RENDER_LAYER_LAWN +6000);//306000
        public static final int RENDER_LAYER_PLANT =(RENDER_LAYER_LAWN +2000);//302000
        public static final int RENDER_LAYER_PARTICLE =(RENDER_LAYER_LAWN +7000);//307000

        
        public Board mBoard ;
        public PVZApp app ;
        public int mRenderOrder ;
        public boolean mVisible ;
        public int mX ;
        public int mY ;
        public int mRow ;
        public int mHeight ;

        public void  MakeParentGraphicsFrame (Graphics2D g ){
        }

        public int mWidth ;

        public boolean  BeginDraw (Graphics2D g ){
            if (!this.mVisible)
            {
                return (false);
            };
            g.translate(this.mX, this.mY);
            return (true);
        }
        public void  EndDraw (Graphics2D g ){
            g.translate(-(this.mX), -(this.mY));
        }

    }


