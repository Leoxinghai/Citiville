package com.xinghai.weibo;

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

import Engine.Classes.*;
//import flash.events.*;
//import flash.media.*;
//import flash.net.*;
//import flash.utils.*;
    
import com.xinghai.Debug;

    public class WeiboSound
    {
       public Sound soundClip ;
       public boolean isLoaded =false ;

        public  WeiboSound ()
        {
            return;
        }//end  

        public void  init ()
        {
            soundClip = new Sound();
            soundClip.load(new URLRequest("http://155.161.239.162:8080/hashed/54f32a1258f75c007b8ad50b08a90fa4.mp3"));
            soundClip.addEventListener(Event.COMPLETE,this.soundLoaded);
            //soundClip.addEventListener
        }//end  
        
        public void  soundLoaded (Event e )
        {
        	soundClip.play();
        	isLoaded = true;
        }
        
        public void  playIt ()
        {
             soundClip.play();
        }

    }


