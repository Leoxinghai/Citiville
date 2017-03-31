package com.xiyu.flash.framework.resources.music;
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

//import flash.events.IOErrorEvent;
//import flash.events.Event;
//import flash.media.Sound;
//import flash.media.SoundChannel;
//import flash.media.SoundTransform;
//import flash.utils.Dictionary;
//import flash.net.URLRequest;
//import flash.media.SoundLoaderContext;
import com.xiyu.flash.framework.AppBase;

    public class MusicManager {

        private int mFadeVolume =0;

        public void  resumeMusic (){
            this.mPlayingPosition = 0;
            this.mFailed = false;
        }

        private int mPlayingPosition ;
        private boolean mQueuedLoop =false ;

        public void  pauseMusic (){
        }
        public void  update (){
            if (this.mFadeTimer > 0)
            {
                this.mFadeTimer--;
                this.mFadeVolume = (this.mFadeTimer / this.mFadeTime);
                if (this.mFadeTimer == 0)
                {
                    this.playMusic(this.mQueuedId, this.mQueuedLoop, 0);
                };
            }
            else
            {
                this.mFadeVolume = 1;
            };
            if (!this.mFailed)
            {
                return;
            };
            this.resumeMusic();
        }

        private Dictionary mSongs ;

        public void  stopMusic (MusicId id ){
        }
        public void  playMusic (MusicId id ,boolean loop,int fadeTime){
            this.mFailed = false;
        }

        private boolean mFailed =false ;

        public boolean  registerMusic (MusicId id ,String filename ){
            this.mIDs.push(id);
            return (true);
        }
        public void  setVolume (int volume ){
        }

        private int mFadeTimer =0;

        public void  mute (){
            this.mMuted = true;
            this.setVolume(0);
        }

        private boolean mIdle =true ;

        public void  stopAllMusic (){
            MusicId id ;
			for(int i =0; i<this.mIDs.length();i++)
			{
				id = (MusicId)this.mIDs.elementAt(i);
                this.stopMusic(id);
            };
        }

        private Array mIDs ;

        public boolean  isMuted (){
            return (this.mMuted);
        }

        private boolean mLoop =false ;

        public void  unmute (){
            this.mMuted = ((false) || (this.mApp.mMasterMute));
            this.setVolume(this.mFadeVolume);
        }

        private AppBase mApp ;
        private int mFadeTime =0;

        public MusicId  getPlayingId (){
            return (this.mPlayingId);
        }

        private MusicId mQueuedId =null ;
        private MusicId mPlayingId ;
        private boolean mMuted =false ;

        public  MusicManager (AppBase app ){
            this.mApp = app;
            this.mIDs = new Array();
            this.mSongs = new Dictionary();
        }
    }


