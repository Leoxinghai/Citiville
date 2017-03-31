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

import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.PVZApp;

    public class CutScene {

        public int mCrazyDaveLastTalkIndex ;
        public Reanimation mZombiesWonReanim ;
        public boolean mPreloaded ;
        public int mCutsceneTime ;
        public int mSodTime ;
        public boolean mSeedChoosing ;
        public boolean mUpsellHideBoard ;
        public int mLawnMowerTime ;
        public boolean mPreUpdatingBoard ;
        public int mCrazyDaveCountDown ;
        public int mGraveStoneTime ;
        public int mCrazyDaveTime ;
        public boolean mPlacedZombies ;
        public int mBossTime ;
        public int mCrazyDaveDialogStart ;
        public int mReadySetPlantTime ;
        public boolean mPlacedLawnItems ;
        public Board mBoard ;
        public PVZApp mApp ;
        public int mFogTime ;

        public  CutScene (PVZApp app ,Board theBoard ){
            this.mApp = app;
            this.mBoard = theBoard;
            this.mCutsceneTime = 0;
            this.mSodTime = 0;
            this.mFogTime = 0;
            this.mBossTime = 0;
            this.mCrazyDaveTime = 0;
            this.mGraveStoneTime = 0;
            this.mReadySetPlantTime = 0;
            this.mLawnMowerTime = 0;
            this.mCrazyDaveDialogStart = -1;
            this.mSeedChoosing = false;
            this.mZombiesWonReanim = null;
            this.mPreloaded = false;
            this.mPlacedZombies = false;
            this.mPlacedLawnItems = false;
            this.mCrazyDaveCountDown = 0;
            this.mCrazyDaveLastTalkIndex = -1;
            this.mUpsellHideBoard = false;
            this.mPreUpdatingBoard = false;
        }
    }


