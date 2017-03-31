package com.xiyu.flash.games.pvz;
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

//import flash.external.////ExternalInterface;
import com.xiyu.flash.framework.AppUtils;
import com.xiyu.flash.framework.AppBase;

    public class MSNAdAPI {

        public static  int DEFAULT_LOAD_BROADCAST_RATE =3000;
        private static  int MILLIS_PER_UPDATE =10;
        public static  int DEFAULT_SCORE_BROADCAST_RATE =3000;
        public static  int MIN_BROADCAST_RATE =1000;

        private void  onCustomReturn (String sXML){
//            this.mCustomEventCallback();
//            this.mCustomEventCallback = null;
        }

        private boolean mPlaying =false ;

        private void  onGamePause (String str ){
            boolean isPaused =this.onOffAsBoolean(str );
            this.togglePause(isPaused);
        }
/*        
        public void  CustomEvent (XML data ,Function callback ){
  
            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                (callback());
                return;
            };
            this.mCustomEventCallback = callback;
            String xml ="<data>DeluxeDownload</data>";
  
            ////ExternalInterface.call("CustomEvent", xml);
        }
*/
        private int mNumBreaks =0;
        private int mLoadBroadcastRate =3000;
        private boolean mLoading =true ;

        public void  init (){
            boolean result ;
/*            
            this.mEnabled = ((AppUtils.asBoolean(this.mApp.getProperties().MSN.enabled)) && (////ExternalInterface.available));
            this.mLoadBroadcastRate = AppUtils.asNumber(this.mApp.getProperties().MSN.loadBroadcastRate, DEFAULT_LOAD_BROADCAST_RATE);
            this.mLoadBroadcastRate = Math.max(this.mLoadBroadcastRate, MIN_BROADCAST_RATE);
            this.mScoreBroadcastRate = AppUtils.asNumber(this.mApp.getProperties().MSN.scoreBroadcastRate, DEFAULT_SCORE_BROADCAST_RATE);
            this.mScoreBroadcastRate = Math.max(this.mScoreBroadcastRate, MIN_BROADCAST_RATE);
*/            
            if (!this.mEnabled)
            {
                return;
            };
            /*
            if (ExternalInterface.available)
            {
                ////ExternalInterface.addCallback("onGameMenu", this.onGameMenu);
                ////ExternalInterface.addCallback("onGameMute", this.onGameMute);
                ////ExternalInterface.addCallback("onMuteOn", this.onMuteOn);
                ////ExternalInterface.addCallback("onMuteOff", this.onMuteOff);
                ////ExternalInterface.addCallback("onGamePause", this.onGamePause);
                ////ExternalInterface.addCallback("onPauseOn", this.onPauseOn);
                ////ExternalInterface.addCallback("onPauseOff", this.onPauseOff);
                ////ExternalInterface.addCallback("onGameContinue", this.onGameContinue);
                ////ExternalInterface.addCallback("onGameStart", this.onGameStart);
                ////ExternalInterface.addCallback("onSessionStart", this.onSessionStart);
                ////ExternalInterface.addCallback("onCustomReturn", this.onCustomReturn);
                result = ////ExternalInterface.call("isProxyReady");
                if (result == true)
                {
                    ////ExternalInterface.call("setSWFIsReady");
                }
                else
                {
                    this.mEnabled = false;
                };
            };
            */
            
        }
        private void  togglePause (boolean isPaused ){
            if (isPaused)
            {
                this.mPlaying = false;
            }
            else
            {
                this.mPlaying = true;
            };
            this.mApp.togglePause(isPaused);
        }
        private void  toggleMute (boolean isMuted ){
            this.mApp.toggleMute(isMuted, true);
        }
        public void  GameError (String str ){
        	/*
            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                return;
            };
            */
            ////ExternalInterface.call("GameError", str);
        }
        private void  onMuteOn (){
            this.toggleMute(true);
        }
        private void  onPauseOff (){
            this.togglePause(false);
        }

        private boolean mEnabled =false ;
        private int mScoreBroadcastRate =3000;

        private void  onPauseOn (){
            this.togglePause(true);
        }

  //      private Function mSessionReadyCallback ;

        public void  resumeBroadcast (){
            this.mPlaying = true;
        }

        private int mScore =0;
    //    public Function handleMenu =null ;

        public void  ScoreSubmit (){
        	/*
        	if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                return;
            };
            */
//            String xml =(((("<game><score>"+this.mScore )+"</score><time>")+int ((this.mGameTime /1000)))+"</time></game>");
            ////ExternalInterface.call("ScoreSubmit", xml);
        }

  //      private Function mGameBreakCallback ;

        public void  GameBreak (int levelCompleted ) {//,Function callback ){
            this.mPlaying = false;
            this.mNumBreaks = (this.mNumBreaks + 1);
            /*            
            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                this.mPlaying = true;
                (callback());
                return;
            };
            */
//            this.mGameBreakCallback = callback;
            String xml =(("<data><breakpoint>"+this.mNumBreaks )+"</breakpoint></data>");
            ////ExternalInterface.call("GameBreak", xml);
        }
        
        public void  GameReady (int mode ,int level ){ //,Function callback ){
            this.mGameTime = 0;
            /*
            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                this.mGameTime = 0;
                this.mPlaying = true;
                (callback());
                return;
            };
            */
//            this.mGameReadyCallback = callback;
            String xml =(((("<data><mode>"+mode )+"</mode><startlevel>")+level )+"</startlevel></data>");
            ////ExternalInterface.call("GameReady", xml);
        }
        
        private void  onMuteOff (){
            this.toggleMute(false);
        }
        private void  onGameMute (String str ){
            this.toggleMute(this.onOffAsBoolean(str));
        }
        public void  setLoadPercent (int loadPercent ){
            this.mLoadPercent = (((loadPercent)<0) ? 0 : loadPercent);
            this.mLoadPercent = (((this.mLoadPercent)>100) ? 100 : this.mLoadPercent);
            this.mLoadPercent = (int)(this.mLoadPercent);
        }
        private void  onGameContinue (String sXML){
            this.mPlaying = true;
//            this.mGameBreakCallback();
//            this.mGameBreakCallback = null;
        }

        public boolean  enabled (){
            return (this.mEnabled);
        }

  //      private Function mGameReadyCallback ;
/*
        public void  SessionReady (Function callback ){
            this.mLoading = false;
            String xml =(("<data><percentcomplete>"+this.mLoadPercent )+"</percentcomplete></data>");
            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
            {
                (callback());
                return;
            };
            this.mSessionReadyCallback = callback;
            ////ExternalInterface.call("LoadBroadcast", xml);
            ////ExternalInterface.call("SessionReady", "<data></data>");
        }
*/        
        public void  setScore (int score ){
            this.mScore = score;
        }

        private int mLoadPercent =0;
        private int mAppTime =0;

        private boolean  onOffAsBoolean (String str ){
            if (str == "on")
            {
                return (true);
            };
            return (false);
        }
        public void  GameEnd (){
            this.mPlaying = false;
//            if (((!(this.mEnabled)) || (!(////ExternalInterface.available))))
//            {
                return;
    //        };
            ////ExternalInterface.call("GameEnd", "<gamedata></gamedata>");
        }
        public void  pauseBroadcast (){
            this.mPlaying = false;
        }

        private int mGameTime =0;
  //      private Function mCustomEventCallback ;

        private void  onGameStart (){
            this.mGameTime = 0;
            this.mPlaying = true;
//            this.mGameReadyCallback();
//            this.mGameReadyCallback = null;
        }
        private void  onSessionStart (){
//            this.mSessionReadyCallback();
//            this.mSessionReadyCallback = null;
        }

        private AppBase mApp ;

        public void  update (){
            String xml ;
            boolean isLoadBroadcastTime ;
            boolean isTime ;
            if (!this.mEnabled)
            {
                return;
            };
            this.mAppTime = (this.mAppTime + MILLIS_PER_UPDATE);
            if (this.mLoading)
            {
                isLoadBroadcastTime = ((this.mAppTime % this.mLoadBroadcastRate) == 0);
                if (isLoadBroadcastTime)
                {
                    xml = (("<data><percentcomplete>" + this.mLoadPercent) + "</percentcomplete></data>");
/*                    
                    if (////ExternalInterface.available)
                    {
                        ////ExternalInterface.call("LoadBroadcast", xml);
                    };
*/                    
                };
            };
            if (((this.mPlaying) && (!(this.mApp.isPaused()))))
            {
                this.mGameTime = (this.mGameTime + MILLIS_PER_UPDATE);
                isTime = ((this.mGameTime % this.mScoreBroadcastRate) == 0);
                if (isTime)
                {
                    xml = (((("<game><score>" + this.mScore) + "</score><time>") + (int)((this.mGameTime / 1000))) + "</time></game>");
                    /*
                    if (////ExternalInterface.available)
                    {
                        ////ExternalInterface.call("ScoreBroadcast", xml);
                    };
                    */
                };
            };
        }
        private void  onGameMenu (){
            this.mPlaying = false;
/*            
            if (this.handleMenu != null)
            {
                this.handleMenu();
            };
*/            
        }

        public  MSNAdAPI (AppBase app ){
            this.mApp = app;
//            this.mApp.addUpdateHook(this.update);
        }
    }


