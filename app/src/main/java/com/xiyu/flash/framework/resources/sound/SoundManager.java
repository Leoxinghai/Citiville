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

import com.xiyu.flash.framework.AppBase;
    public class SoundManager {

        private SoundData  getData (String id ){
            Object obj =this.mApp.resourceManager().getResource(id );
            SoundDescriptor desc =(SoundDescriptor)obj;
            if (desc != null)
            {
                obj = desc.createData();
                this.mApp.resourceManager().setResource(id, obj);
            };
            SoundData data =(SoundData)obj;
            if (data == null)
            {
                //throw (new Error((("Sound '" + id) + "' instanceof not loaded.")));
            };
            return (data);
        }
        public boolean  isMuted (){
            return (this.mMuted);
        }
        public SoundInst  playSound (String id ,int numPlays){
            SoundData data =this.getData(id );
            SoundInst anInst =this.getSoundInst(data ,numPlays );
            anInst.play(this.mGlobalVolume,1);
            return (anInst);
        }
        public void  resumeAll (){
            SoundInst inst ;
            int len =this.mInstPool.length() ;
            int i =0;
            while (i < len)
            {
            	inst=(SoundInst)this.mInstPool.elementAt(i);
                inst.resume();
                i++;
            };
        }
        private SoundInst  getSoundInst (SoundData data ,int numPlays ){
            SoundInst inst =null;
            SoundInst probe ;
            int len =this.mInstPool.length() ;
            int i =0;
            while (i < len)
            {
            	probe=(SoundInst)this.mInstPool.elementAt(i);
                if (probe.isDead())
                {
                    inst = probe;
                    break;
                };
                i++;
            };
            if (inst == null)
            {
                inst = new SoundInst();
                this.mInstPool.push(inst);
            };
            inst.mDead = false;
            inst.mData = data;
            inst.mNumPlays = numPlays;
            data.mRefCount = (data.mRefCount + 1);
            return (inst);
        }
        public void  setVolume (int volume ){
            SoundInst inst ;
            this.mGlobalVolume = volume;
            if (this.mMuted)
            {
                this.mGlobalVolume = 0;
            };
            int len =this.mInstPool.length() ;
            int i =0;
            while (i < len)
            {
            	inst=(SoundInst)this.mInstPool.elementAt(i);
                inst.setVolume(this.mGlobalVolume);
                i++;
            };
        }

        private int mGlobalVolume =1;

        public void  pauseAll (){
            SoundInst inst ;
            int len =this.mInstPool.length() ;
            int i =0;
            while (i < len)
            {
            	inst=(SoundInst)this.mInstPool.elementAt(i);
                inst.pause();
                i++;
            };
        }
        public void  toggleMute (){
            if (this.mMuted)
            {
                this.unmute();
            }
            else
            {
                this.mute();
            };
        }

        private Array mInstPool ;

        public void  addDescriptor (String id ,SoundDescriptor desc ){
            this.mApp.resourceManager().setResource(id, desc);
        }
        public void  unmute (){
            this.mMuted = ((false) || (this.mApp.mMasterMute));
            this.setVolume(1);
        }

        private AppBase mApp ;

        public int  getNumPlaying (String id ){
            SoundData data =this.getData(id );
            return (data.mRefCount);
        }
        public void  mute (){
            this.mMuted = true;
            this.setVolume(0);
        }

        private boolean mMuted =false ;

        public void  stopAll (){
            SoundInst inst ;
            int len =this.mInstPool.length() ;
            int i =0;
            while (i < len)
            {
            	inst=(SoundInst)this.mInstPool.elementAt(i);
                inst.stop();
                i++;
            };
        }

        public  SoundManager (AppBase app ){
            super();
            this.mInstPool = new Array();
            this.mApp = app;
        }
    }


