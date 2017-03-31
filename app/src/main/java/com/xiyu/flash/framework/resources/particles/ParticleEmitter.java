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
import android.graphics.Point;
//import android.graphics.*;


import com.xiyu.util.*;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.graphics.Color;
//import flash.geom.Point;
import com.xiyu.flash.games.pvz.PVZApp;

    public class ParticleEmitter {

        public static  double ONE_OVER_32K =3.05185094E-5;
        private static  int TRACK_SYSTEM_ALPHA =8;
        private static  int TRACK_SYSTEM_BRIGHTNESS =9;
        private static  int TRACK_EMITTER_PATH =4;
        private static  int TRACK_SYSTEM_BLUE =7;
        private static  int TRACK_SPAWN_RATE =0;
        private static  int TRACK_SPAWN_MAX_ACTIVE =2;
        public static  double CIRCLE_FULL =6.28318530717959;
        private static  int TRACK_SPAWN_MAX_LAUNCHED =3;
        private static  int TRACK_SYSTEM_GREEN =6;
        private static  int TRACK_SPAWN_MIN_ACTIVE =1;
        private static  int TRACK_SYSTEM_RED =5;
        public static  double SECONDS_PER_UPDATE =0.01;
        private static  int NUM_SYSTEM_TRACKS =10;

        private boolean  crossFadeParticle (Particle particle ,ParticleEmitter emitter ){
        	return false;
            //throw (new Error("Not implemented yet!"));
        }

        public ParticleEmitterDefinition mEmitterDef ;

        private void  updateSystemField (ParticleField field ,double time ,int index ){
            int aLastX ;
            int aLastY ;
            int aInterpX=((Integer)((Array)this.mSystemFieldInterp.elementAt(index)).elementAt(0)).intValue();
            int aInterpY=((Integer)((Array)this.mSystemFieldInterp.elementAt(index)).elementAt(1)).intValue();
            int x =(int)field.mX.evaluate(time ,aInterpX );
            int y =(int)field.mY.evaluate(time ,aInterpY );
            if (field.mFieldType == ParticleFieldType.SYSTEM_POSITION)
            {
                aLastX = (int)field.mX.evaluate(this.mSystemLastTimeValue, aInterpX);
                aLastY = (int)field.mY.evaluate(this.mSystemLastTimeValue, aInterpY);
                this.mSystemCenter.x = (this.mSystemCenter.x + (x - aLastX));
                this.mSystemCenter.y = (this.mSystemCenter.y + (y - aLastY));
            }
            else
            {
                if (field.mFieldType != ParticleFieldType.INVALID)
                {
                    //throw (new Error(("Bad system field type: " + field.mFieldType)));
                };
            };
        }

        public double mSystemTimeValue ;
        public double mSystemLastTimeValue ;

        private void  deleteNonCrossFading (){
            Particle aParticle ;
            int aNumParticles =this.mParticleList.length() ;
            int i =0;
            while (i < aNumParticles)
            {
            	aParticle=(Particle)this.mParticleList.elementAt(i);
                if (aParticle.mCrossFadeDuration > 0)
                {
                }
                else
                {
                    this.deleteParticle(aParticle);
                    i--;
                    aNumParticles--;
                };
                i++;
            };
        }

        public Array mTrackInterp ;

        private double  evalSystemTrack (FloatParameterTrack track ,int index ){
        	double value=track.evaluate(this.mSystemTimeValue,((Double)this.mTrackInterp.elementAt(index)).doubleValue());
            return (value);
        }

        public Array mSystemFieldInterp ;
        public ParticleSystem mParticleSystem ;

        private Particle  spawnParticle (int index ,int spawnCount ){
            double aLaunchAngle;
            int aEmitterRadiusInterp;
            double aRadius;
            int aEmitterBoxXInterp;
            int aEmitterBoxYInterp;
            ParticleEmitterType aType =this.mEmitterDef.mEmitterType ;
            Particle aParticle =new Particle ();
            int aParticleFieldCount =this.mEmitterDef.mParticleFields.length();
            int i =0;
            while (i < aParticleFieldCount)
            {
            	aParticle.mParticleFieldInterp.add(i,new Array());
            	((Array)aParticle.mParticleFieldInterp.elementAt(i)).add(0,Math.random());
            	((Array)aParticle.mParticleFieldInterp.elementAt(i)).add(1,Math.random());
                i++;
            };
            i = 0;
            while (i < Particle.NUM_PARTICLE_TRACKS)
            {
            	aParticle.mParticleInterp.add(i,Math.random());
                i++;
            };
            double aParticleDurationInterp =Math.random ();
            double aLaunchSpeedInterp =Math.random ();
            double aEmitterOffsetXInterp =Math.random ();
            double aEmitterOffsetYInterp =Math.random ();
            aParticle.mParticleDuration = (int)(this.mEmitterDef.mParticleDuration.evaluate(this.mSystemTimeValue, aParticleDurationInterp));
            aParticle.mParticleDuration = (((1)>aParticle.mParticleDuration) ? 1 : aParticle.mParticleDuration);
            aParticle.mParticleAge = 0;
            aParticle.mParticleEmitter = this;
            aParticle.mParticleTimeValue = -1;
            aParticle.mParticleLastTimeValue = -1;
            if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.RANDOM_START_TIME))
            {
                aParticle.mParticleAge = (int)(Math.random() * aParticle.mParticleDuration);
            }
            else
            {
                aParticle.mParticleAge = 0;
            };
            int aLaunchSpeed =(int)(this.mEmitterDef.mLaunchSpeed.evaluate(this.mSystemTimeValue ,aLaunchSpeedInterp )*SECONDS_PER_UPDATE );
            int aLaunchAngleInterp =(int)Math.random ();
            if (aType == ParticleEmitterType.CIRCLE_PATH)
            {
            	aLaunchAngle=(int)(this.mEmitterDef.mEmitterPath.evaluate(this.mSystemTimeValue,((Double)this.mTrackInterp.elementAt(TRACK_EMITTER_PATH)).doubleValue())*CIRCLE_FULL);
                aLaunchAngle = (int)(aLaunchAngle + this.mEmitterDef.mLaunchAngle.evaluate(this.mSystemTimeValue, aLaunchAngleInterp));
            }
            else
            {
                if (aType == ParticleEmitterType.CIRCLE_EVEN_SPACING)
                {
                    aLaunchAngle = (int)((CIRCLE_FULL * index) / spawnCount);
                    aLaunchAngle = (int)(aLaunchAngle + this.mEmitterDef.mLaunchAngle.evaluate(this.mSystemTimeValue, aLaunchAngleInterp));
                }
                else
                {
                    if (this.mEmitterDef.mLaunchAngle.isConstantZero())
                    {
                        aLaunchAngle = (int)(Math.random() * CIRCLE_FULL);
                    }
                    else
                    {
                        aLaunchAngle = (int)this.mEmitterDef.mLaunchAngle.evaluate(this.mSystemTimeValue, aLaunchAngleInterp);
                    };
                };
            };
            int aPosX =0;
            int aPosY =0;
            if (aType == ParticleEmitterType.CIRCLE || aType == ParticleEmitterType.CIRCLE_PATH || aType == ParticleEmitterType.CIRCLE_EVEN_SPACING)
            {
                aEmitterRadiusInterp = (int)Math.random();
                aRadius = this.mEmitterDef.mEmitterRadius.evaluate(this.mSystemTimeValue, aEmitterRadiusInterp);
                aPosX = (int)(Math.sin(aLaunchAngle) * aRadius);
                aPosY = (int)(Math.cos(aLaunchAngle) * aRadius);
            }
            else
            {
                if (aType == ParticleEmitterType.BOX)
                {
                    aEmitterBoxXInterp = (int)Math.random();
                    aEmitterBoxYInterp = (int)Math.random();
                    aPosX = (int)this.mEmitterDef.mEmitterBoxX.evaluate(this.mSystemTimeValue, aEmitterBoxXInterp);
                    aPosY = (int)this.mEmitterDef.mEmitterBoxY.evaluate(this.mSystemTimeValue, aEmitterBoxYInterp);
                }
                else
                {
                    if (aType == ParticleEmitterType.BOX_PATH)
                    {
                        //throw (new Error("Not implemented yet!"));
                    };
                    //throw (new Error((("Unsupported emitter type '" + aType) + "'")));
                };
            };
            int aEmitterSkewXInterp = (int)Math.random ();
            int aEmitterSkewYInterp = (int)Math.random ();
            int aSkewX =(int)this.mEmitterDef.mEmitterSkewX.evaluate(this.mSystemTimeValue ,aEmitterSkewXInterp );
            int aSkewY =(int)this.mEmitterDef.mEmitterSkewY.evaluate(this.mSystemTimeValue ,aEmitterSkewYInterp );
            aParticle.mPosition.x = ((this.mSystemCenter.x + aPosX) + (aPosY * aSkewX));
            aParticle.mPosition.y = ((this.mSystemCenter.y + aPosY) + (aPosX * aSkewY));
            aParticle.mVelocity.x = (int)(Math.sin(aLaunchAngle) * aLaunchSpeed);
            aParticle.mVelocity.y = (int)(Math.cos(aLaunchAngle) * aLaunchSpeed);
            int aEmitterOffsetX =(int)this.mEmitterDef.mEmitterOffsetX.evaluate(this.mSystemTimeValue ,aEmitterOffsetXInterp );
            int aEmitterOffsetY =(int)this.mEmitterDef.mEmitterOffsetY.evaluate(this.mSystemTimeValue ,aEmitterOffsetYInterp );
            aParticle.mPosition.x = (aParticle.mPosition.x + aEmitterOffsetX);
            aParticle.mPosition.y = (aParticle.mPosition.y + aEmitterOffsetY);
            aParticle.mAnimationTimeValue = 0;
            if (this.mEmitterDef.mAnimated || this.mEmitterDef.mAnimationRate.isSet())
            {
                aParticle.mImageFrame = 0;
            }
            else
            {
                aParticle.mImageFrame = (int)((Math.random() * this.mEmitterDef.mImageFrames));
            };
            if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.RANDOM_LAUNCH_SPIN))
            {
                aParticle.mSpinPosition = (Math.random() * CIRCLE_FULL);
            }
            else
            {
                if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.ALIGN_LAUNCH_SPIN))
                {
                    aParticle.mSpinPosition = aLaunchAngle;
                }
                else
                {
                    aParticle.mSpinPosition = 0;
                };
            };
            aParticle.mSpinVelocity = 0;
            aParticle.mCrossFadeDuration = 0;
            this.mParticleList.unshift(aParticle);
            this.mParticlesSpawned++;
            this.updateParticle(aParticle);
            return (aParticle);
        }
        public void  setPosition (int x ,int y ){
            int aNumParticles ;
            int i =0;
            Particle aParticle ;
            int deltaX =(x -this.mSystemCenter.x );
            int deltaY =(y -this.mSystemCenter.y );
            if ((((deltaX == 0)) && ((deltaY == 0))))
            {
                return;
            };
            this.mSystemCenter.x = x;
            this.mSystemCenter.y = y;
            if (!this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.PARTICLES_DONT_FOLLOW))
            {
                aNumParticles = this.mParticleList.length();
                i = 0;
                while (i < aNumParticles)
                {
                	aParticle=(Particle)this.mParticleList.elementAt(i);
                    aParticle.mPosition.x = (aParticle.mPosition.x + deltaX);
                    aParticle.mPosition.y = (aParticle.mPosition.y + deltaY);
                    i = (i + 1);
                };
            };
        }

        public double mSystemDuration ;

        private boolean  getRenderParams (Particle particle ,ParticleRenderParams params ){
            int aFraction;
            ParticleEmitter aEmitter =particle.mParticleEmitter;
            ParticleEmitterDefinition aDef =aEmitter.mEmitterDef;
            params.mRedIsSet = false;
            params.mRedIsSet = ((aDef.mSystemRed.isSet()) || (params.mRedIsSet));
            params.mRedIsSet = ((aDef.mParticleRed.isSet()) || (params.mRedIsSet));
            params.mRedIsSet = ((!((aEmitter.mColorOverride.red == 1))) || (params.mRedIsSet));
            params.mGreenIsSet = false;
            params.mGreenIsSet = ((aDef.mSystemGreen.isSet()) || (params.mGreenIsSet));
            params.mGreenIsSet = ((aDef.mParticleGreen.isSet()) || (params.mGreenIsSet));
            params.mGreenIsSet = ((!((aEmitter.mColorOverride.green == 1))) || (params.mGreenIsSet));
            params.mBlueIsSet = false;
            params.mBlueIsSet = ((aDef.mSystemBlue.isSet()) || (params.mBlueIsSet));
            params.mBlueIsSet = ((aDef.mParticleBlue.isSet()) || (params.mBlueIsSet));
            params.mBlueIsSet = ((!((aEmitter.mColorOverride.blue == 1))) || (params.mBlueIsSet));
            params.mAlphaIsSet = false;
            params.mAlphaIsSet = ((aDef.mSystemAlpha.isSet()) || (params.mAlphaIsSet));
            params.mAlphaIsSet = ((aDef.mParticleAlpha.isSet()) || (params.mAlphaIsSet));
            params.mAlphaIsSet = ((!((aEmitter.mColorOverride.alpha == 1))) || (params.mAlphaIsSet));
            params.mParticleScaleIsSet = false;
            params.mParticleScaleIsSet = ((params.mParticleScaleIsSet) || (aDef.mParticleScale.isSet()));
            params.mParticleStretchIsSet = aDef.mParticleStretch.isSet();
            params.mSpinPositionIsSet = false;
            params.mSpinPositionIsSet = ((params.mSpinPositionIsSet) || (aDef.mParticleSpinSpeed.isSet()));
            params.mSpinPositionIsSet = ((params.mSpinPositionIsSet) || (aDef.mParticleSpinAngle.isSet()));
            params.mSpinPositionIsSet = ((params.mSpinPositionIsSet) || (aDef.mParticleFlags.hasFlags(ParticleFlags.RANDOM_LAUNCH_SPIN)));
            params.mSpinPositionIsSet = ((params.mSpinPositionIsSet) || (aDef.mParticleFlags.hasFlags(ParticleFlags.ALIGN_LAUNCH_SPIN)));
            params.mPositionIsSet = false;
            params.mPositionIsSet = ((params.mPositionIsSet) || ((aDef.mParticleFields.length() > 0)));
            params.mPositionIsSet = ((params.mPositionIsSet) || (aDef.mEmitterRadius.isSet()));
            params.mPositionIsSet = ((params.mPositionIsSet) || (aDef.mEmitterOffsetX.isSet()));
            params.mPositionIsSet = ((params.mPositionIsSet) || (aDef.mEmitterOffsetY.isSet()));
            params.mPositionIsSet = ((params.mPositionIsSet) || (aDef.mEmitterBoxX.isSet()));
            params.mPositionIsSet = ((params.mPositionIsSet) || (aDef.mEmitterBoxY.isSet()));
            int aSystemRed =(int)this.evalSystemTrack(aDef.mSystemRed ,TRACK_SYSTEM_RED );
            int aSystemGreen =(int)this.evalSystemTrack(aDef.mSystemGreen ,TRACK_SYSTEM_GREEN );
            int aSystemBlue =(int)this.evalSystemTrack(aDef.mSystemBlue ,TRACK_SYSTEM_BLUE );
            int aSystemAlpha =(int)this.evalSystemTrack(aDef.mSystemAlpha ,TRACK_SYSTEM_ALPHA );
            int aSystemBrightness =(int)this.evalSystemTrack(aDef.mSystemBrightness ,TRACK_SYSTEM_BRIGHTNESS );
            int aParticleRed =(int)this.evalParticleTrack(aDef.mParticleRed ,particle ,Particle.TRACK_PARTICLE_RED );
            int aParticleGreen =(int)this.evalParticleTrack(aDef.mParticleGreen ,particle ,Particle.TRACK_PARTICLE_GREEN );
            int aParticleBlue =(int)this.evalParticleTrack(aDef.mParticleBlue ,particle ,Particle.TRACK_PARTICLE_BLUE );
            int aParticleAlpha =(int)this.evalParticleTrack(aDef.mParticleAlpha ,particle ,Particle.TRACK_PARTICLE_ALPHA );
            int aParticleBrightness =(int)this.evalParticleTrack(aDef.mParticleBrightness ,particle ,Particle.TRACK_PARTICLE_BRIGHTNESS );
            int aBrightness =(aParticleBrightness *aSystemBrightness );
            params.mRed = (((aParticleRed * aSystemRed) * aEmitter.mColorOverride.red) * aBrightness);
            params.mGreen = (((aParticleGreen * aSystemGreen) * aEmitter.mColorOverride.green) * aBrightness);
            params.mBlue = (((aParticleBlue * aSystemBlue) * aEmitter.mColorOverride.blue) * aBrightness);
            params.mAlpha = (((aParticleAlpha * aSystemAlpha) * aEmitter.mColorOverride.alpha) * aBrightness);
            params.mPosX = particle.mPosition.x;
            params.mPosY = particle.mPosition.y;
            params.mParticleScale = this.evalParticleTrack(aDef.mParticleScale, particle, Particle.TRACK_PARTICLE_SCALE);
            params.mParticleStretch = this.evalParticleTrack(aDef.mParticleStretch, particle, Particle.TRACK_PARTICLE_STRETCH);
            params.mSpinPosition = particle.mSpinPosition;
            Particle aCrossParticle =particle.mCrossFadeParticle ;
            if (aCrossParticle == null)
            {
                return (true);
            };
            ParticleRenderParams aCrossFadeParams =new ParticleRenderParams ();
            if (this.getRenderParams(aCrossParticle, aCrossFadeParams))
            {
                aFraction = (particle.mParticleAge / (aCrossParticle.mCrossFadeDuration - 1));
                params.mAlpha = (int)this.crossFadeLerp(aCrossFadeParams.mAlpha, params.mAlpha, aCrossFadeParams.mAlphaIsSet, params.mAlphaIsSet, aFraction);
                params.mRed = (int)this.crossFadeLerp(aCrossFadeParams.mRed, params.mRed, aCrossFadeParams.mRedIsSet, params.mRedIsSet, aFraction);
                params.mGreen = (int)this.crossFadeLerp(aCrossFadeParams.mGreen, params.mGreen, aCrossFadeParams.mGreenIsSet, params.mGreenIsSet, aFraction);
                params.mBlue = (int)this.crossFadeLerp(aCrossFadeParams.mBlue, params.mBlue, aCrossFadeParams.mBlueIsSet, params.mBlueIsSet, aFraction);
                params.mParticleScale = this.crossFadeLerp(aCrossFadeParams.mParticleScale, params.mParticleScale, aCrossFadeParams.mParticleScaleIsSet, params.mParticleScaleIsSet, aFraction);
                params.mParticleStretch = this.crossFadeLerp(aCrossFadeParams.mParticleStretch, params.mParticleStretch, aCrossFadeParams.mParticleStretchIsSet, params.mParticleStretchIsSet, aFraction);
                params.mSpinPosition = this.crossFadeLerp(aCrossFadeParams.mSpinPosition, params.mSpinPosition, aCrossFadeParams.mSpinPositionIsSet, params.mSpinPositionIsSet, aFraction);
                params.mPosX = (int)this.crossFadeLerp(aCrossFadeParams.mPosX, params.mPosX, aCrossFadeParams.mPositionIsSet, params.mPositionIsSet, aFraction);
                params.mPosY = (int)this.crossFadeLerp(aCrossFadeParams.mPosY, params.mPosY, aCrossFadeParams.mPositionIsSet, params.mPositionIsSet, aFraction);
                params.mAlphaIsSet = ((params.mAlphaIsSet) || (aCrossFadeParams.mAlphaIsSet));
                params.mRedIsSet = ((params.mRedIsSet) || (aCrossFadeParams.mRedIsSet));
                params.mGreenIsSet = ((params.mGreenIsSet) || (aCrossFadeParams.mGreenIsSet));
                params.mBlueIsSet = ((params.mBlueIsSet) || (aCrossFadeParams.mBlueIsSet));
                params.mParticleScaleIsSet = ((params.mParticleScaleIsSet) || (aCrossFadeParams.mParticleScaleIsSet));
                params.mParticleStretchIsSet = ((params.mParticleStretchIsSet) || (aCrossFadeParams.mParticleStretchIsSet));
                params.mSpinPositionIsSet = ((params.mSpinPositionIsSet) || (aCrossFadeParams.mSpinPositionIsSet));
                params.mPositionIsSet = ((params.mPositionIsSet) || (aCrossFadeParams.mPositionIsSet));
            };
            return true;
        }
        private double  evalParticleTrack (FloatParameterTrack track ,Particle particle ,int index ){
        	double value=track.evaluate(particle.mParticleTimeValue,((Double)particle.mParticleInterp.elementAt(index)).doubleValue());
            return (value);
        }
        private int  clamp (int value ,int min ,int max ){
            int tmp = value < max ? value : max;
            return (( tmp > min) ? tmp : min);
        }
        private void  renderParticle (Graphics2D g ,Particle particle ,ParticleRenderParams params ){
            ParticleEmitter anEmitter =particle.mParticleEmitter ;
            ParticleEmitterDefinition aDef =anEmitter.mEmitterDef ;
            ImageInst aImage =aDef.mImage ;
            if (aImage == null)
            {
                return;
            };
            int aFrame =0;
            int aNumFrames =this.mEmitterDef.mImageFrames ;
            if (this.mEmitterDef.mAnimationRate.isSet())
            {
                aFrame = (int)((particle.mAnimationTimeValue * aNumFrames));
                aFrame = this.clamp(aFrame, 0, (aNumFrames - 1));
            }
            else
            {
                if (this.mEmitterDef.mAnimated)
                {
                    aFrame = (int)((particle.mParticleTimeValue * aNumFrames));
                    aFrame = this.clamp(aFrame, 0, (aNumFrames - 1));
                }
                else
                {
                    aFrame = particle.mImageFrame;
                };
            };
            aImage.setFrame(aFrame, aDef.mImageCol, aDef.mImageRow);
            
/*            
            Matrix aTransform =new Matrix ();
            aTransform.translate((-(aImage.width()) / 2), (-(aImage.height()) / 2));
            if (params.mSpinPositionIsSet)
            {
                aTransform.rotate(params.mSpinPosition);
            };
            if (params.mParticleScaleIsSet)
            {
                aTransform.scale(params.mParticleScale, params.mParticleScale);
            };
            if (params.mParticleStretchIsSet)
            {
                aTransform.scale(1, params.mParticleStretch);
            };
*/            
            aImage.useColor = ((((((params.mAlphaIsSet) || (params.mRedIsSet))) || (params.mGreenIsSet))) || (params.mBlueIsSet));
            aImage.setColor(((params.mAlphaIsSet) ? params.mAlpha : 1), ((params.mRedIsSet) ? params.mRed : 1), ((params.mGreenIsSet) ? params.mGreen : 1), ((params.mBlueIsSet) ? params.mBlue : 1));
//            aTransform.translate(params.mPosX, params.mPosY);
            
            g.pushState();
//            g.setTransform(aTransform);

            
            float scale = 1.0f;
            android.graphics.Matrix mm = new android.graphics.Matrix();
            //mm.setValues(new float[]{(float)(Math.cos(Math.PI*a.okX/180)*sX) ,(float)(-Math.sin(Math.PI*a.okX/180)*sX),(float)0f,(float)(Math.sin(Math.PI*a.okY/180)*sY),(float)(Math.cos(Math.PI*a.okY/180)*sY),(float)0f,0,0,1f});
//            mm.setSinCos((float)(Math.sin(Math.PI*a.okX/180)*sX), (float)(Math.cos(Math.PI*a.okX/180)*sX), (float)0, (float)0);
//            mm.postRotate((float)a.okX, (float)((this.mX+tX)*PVZApp.mDesity), (float)((this.mY+tY)*PVZApp.mDesity));
//            mm.postRotate((float)a.okX, this.mX / scale +(float)((tX)*PVZApp.mDesity), this.mY / scale +(float)((tY)*PVZApp.mDesity));
            
//            mm.postScale((float)(0.3), (float)(0.3));
//            mm.postRotate((float)a.okX, (float)(0), (float)(0));
//            mm.mapPoints(pts);
            android.graphics.RectF rect = new android.graphics.RectF();
            rect.left = 0f;
            rect.top = 0f;
            rect.right = 20f;
            rect.bottom = 20f;
            mm.mapRect(rect);
//            mm.postRotate((float)params.mSpinPosition);
            mm.setTranslate(params.mPosX, params.mPosY);
            
            mm.postScale(scale, scale);
            
            if (params.mSpinPositionIsSet)
            {
            	mm.postRotate((float)params.mSpinPosition);
            };
            /*
            if (params.mParticleScaleIsSet)
            {
            	mm.postScale((float)params.mParticleScale, (float)params.mParticleScale);
            };
            if (params.mParticleStretchIsSet)
            {
            	mm.postScale(1, (float)params.mParticleStretch);
            };
*/
            
            g.state.affineMatrix.copyAndroidMatrix(mm);

            
            g.drawImage(aImage, 0, 0);
            g.popState();
        }
        public void  update (){
            ParticleEmitter aEmitterCrossFade ;
            ParticleField aParticleField ;
            Particle aParticle ;
            if (this.mDead)
            {
                return;
            };
            this.mSystemAge++;
            boolean aDie =false;
            if (this.mSystemAge >= this.mSystemDuration)
            {
                if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.SYSTEM_LOOPS))
                {
                    this.mSystemAge = 0;
                }
                else
                {
                    this.mSystemAge = (this.mSystemDuration - 1);
                    aDie = true;
                };
            };
            if (this.mEmitterCrossFadeCountDown > 0)
            {
                this.mEmitterCrossFadeCountDown--;
                if (this.mEmitterCrossFadeCountDown == 0)
                {
                    aDie = true;
                };
            };
            if (this.mCrossFadeEmitter != null)
            {
                aEmitterCrossFade = this.mCrossFadeEmitter;
                if ((((aEmitterCrossFade == null)) || (aEmitterCrossFade.mDead)))
                {
                    aDie = true;
                };
            };
            this.mSystemTimeValue = (this.mSystemAge / (this.mSystemDuration - 1));

            int aSystemFieldCount =this.mEmitterDef.mSystemFields.length();
            int i =0;
            while (i < aSystemFieldCount)
            {
            	aParticleField=(ParticleField)this.mEmitterDef.mSystemFields.elementAt(i);
                this.updateSystemField(aParticleField, this.mSystemTimeValue, i);
                i++;
            };
            int aNumParticles =this.mParticleList.length();
            int k =0;
            while (k < aNumParticles)
            {
            	aParticle=(Particle)this.mParticleList.elementAt(k);
                if (!this.updateParticle(aParticle))
                {
                    this.deleteParticle(aParticle);
                    k--;
                    aNumParticles--;
                };
                k++;
            };
            this.updateSpawning();
            if (aDie)
            {
                this.deleteNonCrossFading();
                if (this.mParticleList.length() == 0)
                {
                    this.mDead = true;
                    return;
                };
            };
            this.mSystemLastTimeValue = this.mSystemTimeValue;
        }

        public Array mParticleList ;

        private void  updateParticleField (Particle particle ,ParticleField field ,int time ,int index ){
            int aInterpX;
            int aInterpY;
            int x;
            int y;
            int _local9;
            int _local10;
            int _local11;
            int _local12;
            double aCollisionReflect;
            double aCollisionSpin;
            aInterpX=((Double)((Array)particle.mParticleFieldInterp.elementAt(index)).elementAt(0)).intValue();
            aInterpY=((Double)((Array)particle.mParticleFieldInterp.elementAt(index)).elementAt(1)).intValue();
            x = (int)field.mX.evaluate(time, aInterpX);
            y = (int)field.mY.evaluate(time, aInterpY);
            if (field.mFieldType == ParticleFieldType.ACCELERATION) {
                    particle.mVelocity.x = (int)(particle.mVelocity.x + (x * SECONDS_PER_UPDATE));
                    particle.mVelocity.y = (int)(particle.mVelocity.y + (y * SECONDS_PER_UPDATE));
            } else if (field.mFieldType == ParticleFieldType.FRICTION) {
                    particle.mVelocity.x = (particle.mVelocity.x * (1 - x));
                    particle.mVelocity.y = (particle.mVelocity.y * (1 - y));
            } else if (field.mFieldType == ParticleFieldType.ATTRACTOR) {
                    _local9 = (x - (particle.mPosition.x - this.mSystemCenter.x));
                    _local10 = (y - (particle.mPosition.y - this.mSystemCenter.y));
                    particle.mVelocity.x = (int)(particle.mVelocity.x + (_local9 * SECONDS_PER_UPDATE));
                    particle.mVelocity.y = (int)(particle.mVelocity.y + (_local10 * SECONDS_PER_UPDATE));
            } else if (field.mFieldType == ParticleFieldType.MAX_VELOCITY) {
                    particle.mVelocity.x = this.clamp(particle.mVelocity.x, -(x), x);
                    particle.mVelocity.y = this.clamp(particle.mVelocity.y, -(y), y);
            } else if (field.mFieldType == ParticleFieldType.VELOCITY) {
                    particle.mPosition.x = (int)(particle.mPosition.x + (x * SECONDS_PER_UPDATE));
                    particle.mPosition.y = (int)(particle.mPosition.y + (y * SECONDS_PER_UPDATE));
            } else if (field.mFieldType == ParticleFieldType.POSITION) {
                    _local11 = (int)field.mX.evaluate(particle.mParticleLastTimeValue, aInterpX);
                    _local12 = (int)field.mY.evaluate(particle.mParticleLastTimeValue, aInterpY);
                    particle.mPosition.x = (particle.mPosition.x + (x - _local11));
                    particle.mPosition.y = (particle.mPosition.y + (y - _local12));
            } else if (field.mFieldType == ParticleFieldType.GROUND_CONSTRAINT) {
                    if (particle.mPosition.y > (this.mSystemCenter.y + y))
                    {
                        particle.mPosition.y = (this.mSystemCenter.y + y);
                        aCollisionReflect=this.mEmitterDef.mCollisionReflect.evaluate(time,((Double)particle.mParticleInterp.elementAt(Particle.TRACK_PARTICLE_COLLISION_REFLECT)).doubleValue());
                        aCollisionSpin=(this.mEmitterDef.mCollisionSpin.evaluate(time, ((Double) particle.mParticleInterp.elementAt(Particle.TRACK_PARTICLE_COLLISION_SPIN)).doubleValue() )/1000);
                        particle.mSpinVelocity = (particle.mVelocity.y * aCollisionSpin);
                        particle.mVelocity.x = (int)(particle.mVelocity.x * aCollisionReflect);
                        particle.mVelocity.y = (int)(-(particle.mVelocity.y) * aCollisionReflect);
                    };
            } else if (field.mFieldType == ParticleFieldType.SHAKE) {
                    particle.mPosition.x = (particle.mPosition.x - particle.mOffset.x);
                    particle.mPosition.y = (particle.mPosition.y - particle.mOffset.y);
                    particle.mOffset.x = (int)(x * (((Math.random() * ONE_OVER_32K) * 2) - 1));
                    particle.mOffset.y = (int)(y * (((Math.random() * ONE_OVER_32K) * 2) - 1));
                    particle.mPosition.x = (particle.mPosition.x + particle.mOffset.x);
                    particle.mPosition.y = (particle.mPosition.y + particle.mOffset.y);
            } else {
                    //throw (new Error((("Unsupported field type '" + field.mFieldType) + "'")));
            };
        }
        public void  draw (Graphics2D g ){
            Particle aParticle ;
            if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.HARDWARE_ONLY))
            {
                return;
            };
            int aNumParticles =this.mParticleList.length() ;
            int i =0;
            g.pushState();
            while (i < aNumParticles)
            {
            	aParticle=(Particle)this.mParticleList.elementAt(i);
                this.drawParticle(g, aParticle);
                i++;
            };
            g.popState();
        }
        private void  deleteParticle (Particle particle ){
            Particle aCrossParticle =particle.mCrossFadeParticle ;
            if (aCrossParticle != null)
            {
                aCrossParticle.mParticleEmitter.deleteParticle(aCrossParticle);
                particle.mCrossFadeParticle = null;
            };
            int index =this.mParticleList.indexOf(particle );
            if (index > -1)
            {
                this.mParticleList.splice(index, 1);
            };
        }

        public boolean mImageOverride ;
        public double mSystemAge ;
        public Color mColorOverride ;

        private void  drawParticle (Graphics2D g ,Particle particle ){
            Particle aCrossFadeParticle ;

            if (particle.mCrossFadeDuration > 0)
            {
                return;
            };
            ParticleRenderParams aParams =new ParticleRenderParams ();

            if (!this.getRenderParams(particle, aParams))
            {
                //return;
            };
            if (aParams.mAlpha == 0)
            {
//                return;
            };
            aParams.mPosX = (aParams.mPosX + g.getTransform().tx);
            aParams.mPosY = (aParams.mPosY + g.getTransform().ty);


            Particle aImageParticle = particle;
            if (this.mImageOverride || this.mEmitterDef.mImage != null)
            {
                aImageParticle = particle;
            }
            else
            {
                aCrossFadeParticle = null;
                if (aCrossFadeParticle != null)
                {
                    aImageParticle = aCrossFadeParticle;
                }
                else
                {
                    return;
                };
            };
            this.renderParticle(g, aImageParticle, aParams);
        }

        public int mSpawnAccum ;

        public void  initFromDef (ParticleEmitterDefinition emitterDef ,ParticleSystem system ){
            double aSystemDurationInterp ;
            this.mEmitterDef = emitterDef;
            this.mParticleSystem = system;
            this.mSpawnAccum = 0;
            this.mParticlesSpawned = 0;
            this.mSystemAge = -1;
            this.mDead = false;
            this.mSystemTimeValue = -1;
            this.mSystemLastTimeValue = -1;
            this.mSystemDuration = 0;
            this.mSystemCenter = new Point();
            this.mParticleList = new Array();
            if (this.mEmitterDef.mSystemDuration.isSet())
            {
                aSystemDurationInterp = Math.random();
                this.mSystemDuration = (int)this.mEmitterDef.mSystemDuration.evaluate(0, aSystemDurationInterp);
                System.out.println("initDef1. "+aSystemDurationInterp+":"+this.mSystemDuration);
            }
            else
            {
                this.mSystemDuration = (int)this.mEmitterDef.mParticleDuration.evaluate(0, 1);
                System.out.println("initDef2. "+":"+this.mSystemDuration);
            };
            this.mSystemDuration = (((this.mSystemDuration)>1) ? this.mSystemDuration : 1);
            int aSystemFieldCount =this.mEmitterDef.mSystemFields.length() ;
            this.mSystemFieldInterp = new Array();
            int i =0;
            while (i < aSystemFieldCount)
            {
            	this.mSystemFieldInterp.add(i,new Array());
            	((Array)this.mSystemFieldInterp.elementAt(i)).add(0,Math.random());
            	((Array)this.mSystemFieldInterp.elementAt(i)).add(1,Math.random());
                i++;
            };
            this.mTrackInterp = new Array(NUM_SYSTEM_TRACKS);
            int aNumInterps = NUM_SYSTEM_TRACKS;//this.mTrackInterp.length() ;
            int k =0;
            while (k < aNumInterps)
            {
            	this.mTrackInterp.add(k,Math.random());
                k++;
            };
            this.update();
        }
        private boolean  updateParticle (Particle particle ){
            ParticleField aField ;
            if (particle.mParticleAge >= particle.mParticleDuration)
            {
                if (this.mEmitterDef.mParticleFlags.hasFlags(ParticleFlags.PARTICLE_LOOPS))
                {
                    particle.mParticleAge = 0;
                }
                else
                {
                    if (particle.mCrossFadeDuration > 0)
                    {
                        particle.mParticleAge = (particle.mParticleDuration - 1);
                    }
                    else
                    {
                        return (false);
                    };
                };
            };
            //add by xinghai
            if(particle.mParticleDuration != 1)
            	particle.mParticleTimeValue = (particle.mParticleAge / (particle.mParticleDuration - 1));
            else
            	particle.mParticleTimeValue = 0;

            int aFieldCount = this.mEmitterDef.mParticleFields.length() ;
            int i = 0;
            while (i < aFieldCount)
            {
            	aField=(ParticleField)this.mEmitterDef.mParticleFields.elementAt(i);
                this.updateParticleField(particle, aField, particle.mParticleTimeValue, i);
                i++;
            };
            particle.mPosition.x = ((int)(particle.mPosition.x) + (int)(particle.mVelocity.x));
            particle.mPosition.y = (particle.mPosition.y + particle.mVelocity.y);
            double aSpinSpeed=(this.mEmitterDef.mParticleSpinSpeed.evaluate(particle.mParticleTimeValue,((Double)particle.mParticleInterp.elementAt(Particle.TRACK_PARTICLE_SPIN_SPEED)).doubleValue() )*SECONDS_PER_UPDATE);
            double aSpinAngle=this.mEmitterDef.mParticleSpinAngle.evaluate(particle.mParticleTimeValue,((Double)particle.mParticleInterp.elementAt(Particle.TRACK_PARTICLE_SPIN_ANGLE)).doubleValue() );
            double aLastSpinAngle=this.mEmitterDef.mParticleSpinAngle.evaluateLast(particle.mParticleLastTimeValue,((Double)particle.mParticleInterp.elementAt(Particle.TRACK_PARTICLE_SPIN_ANGLE)).doubleValue());
            particle.mSpinPosition = (particle.mSpinPosition + ((aSpinSpeed + aSpinAngle) - aLastSpinAngle));
            particle.mSpinPosition = (particle.mSpinPosition + particle.mSpinVelocity);
            if (this.mEmitterDef.mAnimationRate.isSet())
            {
                //throw (new Error("Not implemented!"));
            };
            particle.mParticleLastTimeValue = particle.mParticleTimeValue;
            particle.mParticleAge++;
            return (true);
        }
        private void  updateSpawning (){
            int aSpawnMinimum = 0;
            int aMaxCanSpawn = 0;
            int aSpawnMaxLaunched = 0;
            int aMaxCanSpawn2 = 0;
            Particle aParticle;
            ParticleEmitter aSpawnEmitter = this;
            ParticleEmitter aCrossFadeEmitter =this.mCrossFadeEmitter;
            if (aCrossFadeEmitter != null)
            {
                aSpawnEmitter = aCrossFadeEmitter;
            };
            ParticleEmitterDefinition aDef =aSpawnEmitter.mEmitterDef;
            FloatParameterTrack aTrack;
            double aTime = 0;
            double anInterp = 0;
            aTrack = aDef.mSpawnRate;
            aTime = aSpawnEmitter.mSystemTimeValue;
            anInterp=((Double)aSpawnEmitter.mTrackInterp.elementAt(TRACK_SPAWN_RATE)).doubleValue();

            this.mSpawnAccum = (int)(this.mSpawnAccum + (aTrack.evaluate(aTime, anInterp) * SECONDS_PER_UPDATE));
            int aSpawnCount =(int)(this.mSpawnAccum );
            this.mSpawnAccum = (this.mSpawnAccum - aSpawnCount);
            aTrack = aDef.mSpawnMinActive;
            aTime = aSpawnEmitter.mSystemTimeValue;
            anInterp=((Double)aSpawnEmitter.mTrackInterp.elementAt(TRACK_SPAWN_MIN_ACTIVE)).doubleValue();
            int aSpawnMinActive =(int)(aTrack.evaluate(aTime ,anInterp ));
            if (aSpawnMinActive >= 0)
            {
                aSpawnMinimum = (aSpawnMinActive - this.mParticleList.length());
                if (aSpawnCount < aSpawnMinimum)
                {
                    aSpawnCount = aSpawnMinimum;
                };
            };
            aTrack = aDef.mSpawnMaxActive;
            aTime = aSpawnEmitter.mSystemTimeValue;
            anInterp=((Double)aSpawnEmitter.mTrackInterp.elementAt(TRACK_SPAWN_MAX_ACTIVE)).doubleValue();
            int aSpawnMaxActive =(int)(aTrack.evaluate(aTime ,anInterp ));
            if (aSpawnMaxActive >= 0)
            {
                aMaxCanSpawn = (aSpawnMaxActive - this.mParticleList.length());
                if (aSpawnCount > aMaxCanSpawn)
                {
                    aSpawnCount = aMaxCanSpawn;
                };
            };

            if (aDef.mSpawnMaxLaunched.isSet())
            {
                aTrack = aDef.mSpawnMaxLaunched;
                aTime = aSpawnEmitter.mSystemTimeValue;
                anInterp=((Double)aSpawnEmitter.mTrackInterp.elementAt(TRACK_SPAWN_MAX_LAUNCHED)).doubleValue();
                aSpawnMaxLaunched = (int)(aTrack.evaluate(aTime, anInterp));
                aMaxCanSpawn2 = (aSpawnMaxLaunched - this.mParticlesSpawned);
                if (aSpawnCount > aMaxCanSpawn2)
                {
                    aSpawnCount = aMaxCanSpawn2;
                };
            };
            int i = 0;

            //add by xinghai
            //aSpawnCount =2;
            while (i < aSpawnCount)
            {
                aParticle = this.spawnParticle(i, aSpawnCount);
                if (aCrossFadeEmitter!=null)
                {
                    this.crossFadeParticle(aParticle, aCrossFadeEmitter);
                };
                i++;
            };
        }

        public boolean mDead ;

        private double crossFadeLerp (double from ,double to ,boolean fromIsSet ,boolean toIsSet ,int fraction ){
            if (!fromIsSet)
            {
                return (to);
            };
            if (!toIsSet)
            {
                return (from);
            };
            return ((from + ((to - from) * fraction)));
        }

        public int mEmitterCrossFadeCountDown ;
        public Point mSystemCenter ;
        public int mParticlesSpawned ;

        public void  deleteAll (){
            this.mParticleList = new Array();
        }

        public ParticleEmitter mCrossFadeEmitter ;

        public  ParticleEmitter (){
            super();
            this.mColorOverride = Color.ARGB(1, 1, 1, 1);
        }
    }


