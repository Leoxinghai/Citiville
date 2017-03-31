package com.xiyu.flash.games.pvz.renderables;
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

import com.xiyu.flash.framework.render.Renderable;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;

    public class ReanimationRenderable implements Renderable {

        private static  Matrix mScratchMatrix =new Matrix ();

        public boolean  getIsDisposable (){
            return (this.mReanim.mIsDead);
        }

        private boolean mLerped ;

        public void  draw (Graphics2D g ){
            if (this.mLerped)
            {
                this.mReanim.drawLerp(g, mScratchMatrix, 1);
                return;
            };
            this.mReanim.draw(g);
        }
        public int  getDepth (){
            return (this.mDepth);
        }
        public void  update (){
            this.mReanim.update();
        }

        private Reanimation mReanim ;

        public boolean  getIsVisible (){
            return (true);
        }

        private int mDepth ;

        public String  toString (){
            return (("Reanim@" + this.mDepth));
        }

        public  ReanimationRenderable (Reanimation reanim ,int depth ,boolean lerped){
            this.mReanim = reanim;
            this.mDepth = depth;
            this.mLerped = lerped;
        }
    }


