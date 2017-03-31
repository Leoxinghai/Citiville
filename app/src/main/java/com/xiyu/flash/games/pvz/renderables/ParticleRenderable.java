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
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.*;

import com.xiyu.flash.framework.render.Renderable;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;

    public class ParticleRenderable implements Renderable {

        public boolean  getIsDisposable (){
            return (this.mEffect.mDead);
        }
        public void  draw (Graphics2D g ){
            this.mEffect.draw(g);
        }
        public void  update (){
            this.mEffect.update();
        }
        public boolean  getIsVisible (){
            return (true);
        }
        public int  getDepth (){
            return (this.mDepth);
        }

        private int mDepth ;

        public String  toString (){
            return (("Particle@" + this.mDepth));
        }

        private ParticleSystem mEffect ;

        public  ParticleRenderable (ParticleSystem effect ,int depth){
            this.mEffect = effect;
            this.mDepth = depth;
        }
    }


