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

import com.xiyu.flash.framework.resources.images.ImageInst;
    public class ParticleEmitterDefinition {

        public FloatParameterTrack mClipBottom ;
        public FloatParameterTrack mEmitterPath ;
        public String mImageID ="";
        public FloatParameterTrack mSpawnMinActive ;
        public FloatParameterTrack mParticleAlpha ;
        public int mImageRow ;
        public FloatParameterTrack mParticleBlue ;
        public FloatParameterTrack mParticleScale ;
        public ParticleEmitterType mEmitterType ;
        public FloatParameterTrack mSpawnRate ;
        public String mName ;
        public FloatParameterTrack mEmitterOffsetX ;
        public FloatParameterTrack mEmitterOffsetY ;
        public FloatParameterTrack mClipLeft ;
        public FloatParameterTrack mLaunchSpeed ;
        public FloatParameterTrack mSpawnMaxLaunched ;
        public FloatParameterTrack mCrossFadeDuration ;
        public Array mParticleFields ;
        public FloatParameterTrack mCollisionSpin ;
        public FloatParameterTrack mAnimationRate ;
        public boolean mAnimated ;
        public int mImageCol ;
        public FloatParameterTrack mEmitterRadius ;
        public int mImageFrames ;
        public FloatParameterTrack mLaunchAngle ;
        public ParticleFlags mParticleFlags ;
        public FloatParameterTrack mSpawnMaxActive ;
        public FloatParameterTrack mSystemDuration ;
        public FloatParameterTrack mSystemAlpha ;
        public FloatParameterTrack mParticleBrightness ;
        public ParticleDefinition mEffectDef ;
        public FloatParameterTrack mEmitterSkewX ;
        public FloatParameterTrack mParticleDuration ;
        public FloatParameterTrack mEmitterBoxY ;
        public FloatParameterTrack mParticleGreen ;
        public FloatParameterTrack mEmitterBoxX ;
        public FloatParameterTrack mParticleSpinSpeed ;
        public FloatParameterTrack mEmitterSkewY ;
        public FloatParameterTrack mParticleSpinAngle ;
        public FloatParameterTrack mClipRight ;
        public FloatParameterTrack mCollisionReflect ;
        public ImageInst mImage ;
        public FloatParameterTrack mClipTop ;
        public FloatParameterTrack mParticleStretch ;

        public String  toString (){
            return ((((("[ParticleEmitterDef '" + this.mName) + "' file='") + this.mEffectDef.mFilename) + "']"));
        }

        public FloatParameterTrack mSystemBrightness ;
        public FloatParameterTrack mSystemRed ;
        public FloatParameterTrack mSystemGreen ;
        public Array mSystemFields ;
        public FloatParameterTrack mSystemBlue ;
        public FloatParameterTrack mParticleRed ;

        public  ParticleEmitterDefinition (){
            this.mSystemDuration = new FloatParameterTrack();
            this.mCrossFadeDuration = new FloatParameterTrack();
            this.mSpawnRate = new FloatParameterTrack();
            this.mSpawnMinActive = new FloatParameterTrack();
            this.mSpawnMaxActive = new FloatParameterTrack();
            this.mSpawnMaxLaunched = new FloatParameterTrack();
            this.mEmitterRadius = new FloatParameterTrack();
            this.mEmitterOffsetX = new FloatParameterTrack();
            this.mEmitterOffsetY = new FloatParameterTrack();
            this.mEmitterBoxX = new FloatParameterTrack();
            this.mEmitterBoxY = new FloatParameterTrack();
            this.mEmitterSkewX = new FloatParameterTrack();
            this.mEmitterSkewY = new FloatParameterTrack();
            this.mEmitterPath = new FloatParameterTrack();
            this.mParticleDuration = new FloatParameterTrack();
            this.mLaunchSpeed = new FloatParameterTrack();
            this.mLaunchAngle = new FloatParameterTrack();
            this.mSystemRed = new FloatParameterTrack();
            this.mSystemGreen = new FloatParameterTrack();
            this.mSystemBlue = new FloatParameterTrack();
            this.mSystemAlpha = new FloatParameterTrack();
            this.mSystemBrightness = new FloatParameterTrack();
            this.mParticleRed = new FloatParameterTrack();
            this.mParticleGreen = new FloatParameterTrack();
            this.mParticleBlue = new FloatParameterTrack();
            this.mParticleAlpha = new FloatParameterTrack();
            this.mParticleBrightness = new FloatParameterTrack();
            this.mParticleSpinAngle = new FloatParameterTrack();
            this.mParticleSpinSpeed = new FloatParameterTrack();
            this.mParticleScale = new FloatParameterTrack();
            this.mParticleStretch = new FloatParameterTrack();
            this.mCollisionReflect = new FloatParameterTrack();
            this.mCollisionSpin = new FloatParameterTrack();
            this.mClipTop = new FloatParameterTrack();
            this.mClipBottom = new FloatParameterTrack();
            this.mClipLeft = new FloatParameterTrack();
            this.mClipRight = new FloatParameterTrack();
            this.mAnimationRate = new FloatParameterTrack();
            this.mImageRow = 0;
            this.mImageCol = 0;
            this.mImageFrames = 1;
            this.mAnimated = false;
            this.mEmitterType = ParticleEmitterType.BOX;
            this.mImage = null;
            this.mName = "";
            this.mParticleFlags = new ParticleFlags();
            this.mParticleFields = new Array();
            this.mSystemFields = new Array();
        }
        
        public void setDefault() {
            this.mSystemDuration.setDefault(-1);
            this.mCrossFadeDuration.setDefault(-1);
            this.mSpawnRate.setDefault(-1);
            this.mSpawnMinActive.setDefault(-1);
            this.mSpawnMaxActive.setDefault(-1);
            this.mSpawnMaxLaunched.setDefault(-1);
            this.mEmitterRadius.setDefault(-1);
            this.mEmitterOffsetX.setDefault(-1);
            this.mEmitterOffsetY.setDefault(-1);
            this.mEmitterBoxX.setDefault(-1);
            this.mEmitterBoxY.setDefault(-1);
            this.mEmitterSkewX.setDefault(-1);
            this.mEmitterSkewY.setDefault(-1);
            this.mEmitterPath.setDefault(-1);
            this.mParticleDuration.setDefault(-1);
            this.mLaunchSpeed.setDefault(-1);
            this.mLaunchAngle.setDefault(-1);
            this.mSystemRed.setDefault(-1);
            this.mSystemGreen.setDefault(-1);
            this.mSystemBlue.setDefault(-1);
            this.mSystemAlpha.setDefault(-1);
            this.mSystemBrightness.setDefault(-1);
            this.mParticleRed.setDefault(-1);
            this.mParticleGreen.setDefault(-1);
            this.mParticleBlue.setDefault(-1);
            this.mParticleAlpha.setDefault(-1);
            this.mParticleBrightness.setDefault(-1);
            this.mParticleSpinAngle.setDefault(-1);
            this.mParticleSpinSpeed.setDefault(-1);
            this.mParticleScale.setDefault(-1);
            this.mParticleStretch.setDefault(-1);
            this.mCollisionReflect.setDefault(-1);
            this.mCollisionSpin.setDefault(-1);
            this.mClipTop.setDefault(-1);
            this.mClipBottom.setDefault(-1);
            this.mClipLeft.setDefault(-1);
            this.mClipRight.setDefault(-1);
            this.mAnimationRate.setDefault(-1);
        	
        }
    }


