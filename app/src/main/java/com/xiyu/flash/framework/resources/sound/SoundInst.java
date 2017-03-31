package com.xiyu.flash.framework.resources.sound;
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

//import flash.events.Event;
//import flash.media.SoundChannel;
//import flash.media.SoundTransform;

    public class SoundInst {

        private void  die (){
            this.mDead = true;
            this.mData.mRefCount--;
        }
        private void  handleComplete (){
            this.play(1,1);
        }

        public int mNumPlays ;

        public void  stop (){
            this.die();
        }
        public void  resume (){
            if (this.mDead)
            {
                return;
            };
        }

        public boolean mDead =false ;

        public void  play (int volume,int position){
            if (this.mDead)
            {
                return;
            };
        }
        public boolean  isDead (){
            return (this.mDead);
        }

        public SoundData mData ;

        public void  pause (){
        }


        public void  setVolume (int volume ){
        }

        private int mPosition =0;

        public  SoundInst (){
        }
    }


