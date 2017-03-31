package com.xiyu.flash.framework.resources.particles;
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

//import flash.geom.Point;
    public class Particle {

        public static final int TRACK_PARTICLE_STRETCH =8;
        public static final int TRACK_PARTICLE_SCALE =7;
        public static final int NUM_PARTICLE_TRACKS =16;
        public static final int TRACK_PARTICLE_COLLISION_REFLECT =9;
        public static final int TRACK_PARTICLE_RED =0;
        public static final int TRACK_PARTICLE_CLIP_TOP =11;
        public static final int TRACK_PARTICLE_COLLISION_SPIN =10;
        public static final int TRACK_PARTICLE_CLIP_BOTTOM =12;
        public static final int TRACK_PARTICLE_ALPHA =3;
        public static final int TRACK_PARTICLE_BLUE =2;
        public static final int TRACK_PARTICLE_ANIMATION_RATE =15;
        public static final int TRACK_PARTICLE_CLIP_RIGHT =14;
        public static final int TRACK_PARTICLE_BRIGHTNESS =4;
        public static final int TRACK_PARTICLE_SPIN_SPEED =5;
        public static final int TRACK_PARTICLE_CLIP_LEFT =13;
        public static final int TRACK_PARTICLE_SPIN_ANGLE =6;
        public static final int TRACK_PARTICLE_GREEN =1;

        public int mParticleDuration ;
        public int mParticleLastTimeValue ;
        public double mSpinVelocity ;
        public int mAnimationTimeValue ;
        public int mParticleTimeValue ;
        public Particle mCrossFadeParticle ;
        public Array mParticleInterp ;
        public ParticleEmitter mParticleEmitter ;
        public double mSpinPosition ;
        public Point mOffset ;
        public Array mParticleFieldInterp ;
        public int mCrossFadeDuration ;
        public int mImageFrame ;
        public Point mVelocity ;
        public int mParticleAge ;
        public Point mPosition ;

        public  Particle (){
            this.mPosition = new Point();
            this.mOffset = new Point();
            this.mVelocity = new Point();
            this.mParticleInterp = new Array();
            this.mParticleFieldInterp = new Array();
        }
    }


