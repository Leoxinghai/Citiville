package com.xiyu.flash.framework.resources.reanimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
//import android.graphics.*;
import android.graphics.Point;

import com.xiyu.util.*;
import com.xiyu.flash.framework.AppBase;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.images.ImageData;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopAlways;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopOnceAndDie;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopOnceAndHold;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xinghai.debug.Debug;


//import flash.display.BitmapData;
//import flash.geom.Matrix;
//import flash.geom.Point;
//import flash.geom.Rectangle;
//import flash.geom.Transform;
//import flash.utils.Dictionary;

    public class Reanimation {

        public static  ReanimLoopType LOOP_TYPE_ONCE_AND_DIE =new ReanimLoopOnceAndDie ();
        public static  ReanimLoopType LOOP_TYPE_ONCE_AND_HOLD =new ReanimLoopOnceAndHold ();
        public static  double SECONDS_PER_UPDATE = 0.01;//0.01
        public static  ReanimLoopType LOOP_TYPE_ALWAYS =new ReanimLoopAlways ();

        private  Matrix scratchMatrix =new Matrix ();

        private   Matrix identityMatrix =new Matrix ();

        private int locatorY ;
        
        private float scale;

        public ReanimLoopType  loopType (){
            return (this.mLoopType);
        }

        public void  overrideImage (String trackName ,ImageInst image ){
            ReanimTrack aTrack =this.getTrack(trackName );
            int aNumTransforms =aTrack.numTransforms;
            int i =0;
            while (i < aNumTransforms )
            {
                ReanimTransform aTrans;
                aTrans=(ReanimTransform)aTrack.transforms.elementAt(i);
                if (aTrans.image != null)
                {
                    aTrans.image = image;
//                    aTrack.transforms.add(i,aTrans);
                };
                i++;
            };
        }
        public boolean  shouldTriggerTimedEvent (double time ){
            if (this.mFrameCount == 0)
            {
                return (false);
            };
            if (this.mLastAnimTime < 0)
            {
                return (false);
            };
            if (this.mAnimRate <= 0)
            {
                return (false);
            };
            if (this.mAnimTime >= this.mLastAnimTime)
            {
                if ((((time >= this.mLastAnimTime)) && ((time < this.mAnimTime))))
                {
                    return (true);
                };
            }
            else
            {
                if ((((time >= this.mLastAnimTime)) || ((time < this.mAnimTime))))
                {
                    return (true);
                };
            };
            return (false);
        }

        private int locatorX ;
        
        private int mX =0;
        private int mY =0;

        private void  drawTrackLerp (Graphics2D g ,ReanimTrack track ,double alpha ){
            Matrix init ;

            if( this.mVisibleMap.get(track.name) !=null && ((Boolean)this.mVisibleMap.get(track.name)).booleanValue()==false)
            {
                return;
            };

          ReanimTransform a=(ReanimTransform)track.transforms.elementAt(this.mFrameTime.mFrameBefore);
//          if(a.imageID != null) {
//        	  System.out.println(a+":1"+":"+this.mFrameTime.mFrameBefore);
//          }
          if("IMAGE_REANIM_CRAZYDAVE_OUTERFINGER1".equals(a.imageID)) {
//	            System.out.println(":this.mFrameTime.mFrameBefore "+this.mFrameTime.mFrameBefore +":"+this.mFrameTime.mFrameAfter);
          }
            ReanimTransform b=(ReanimTransform)track.transforms.elementAt(this.mFrameTime.mFrameAfter);
            double t =this.mFrameTime.mFraction ;

            if (a.frame < 0)
            {
                return;
            };
            if (b.frame < 0)
            {
                b = a;
            };
            double kX =this.lerp(a.kX ,b.kX ,t );
            double kY =this.lerp(a.kY ,b.kY ,t );
            double sX =this.lerp(a.sX ,b.sX ,t );
            double sY =this.lerp(a.sY ,b.sY ,t );
            double tX =this.lerp(a.tX ,b.tX ,t );
            double tY =this.lerp(a.tY ,b.tY ,t );
            double tA =this.lerp(a.alpha ,b.alpha ,t );

			Matrix matrix =scratchMatrix ;

            matrix.a = (Math.cos(kX) * sX);
            matrix.b = (-(Math.sin(kX)) * sX);
            matrix.c = (Math.sin(kY) * sY);
            matrix.d = (Math.cos(kY) * sY);
            matrix.tx = (int)tX;
            matrix.ty = (int)tY;

            if (track.name == this.childTrack)
            {
            	init=((ReanimTransform)track.transforms.elementAt(this.mStartFrame)).matrix;
                this.locatorX = (this.mX + (matrix.tx - init.tx));
                this.locatorY = (this.mY + (matrix.ty - init.ty));
            };
            ImageInst img =a.image ;
            if (img == null)
            {
                return;
            };

            img.useColor = true;
            img.setColor((alpha * tA), (this.overrideColor.red + this.additiveColor.red), (this.overrideColor.green + this.additiveColor.green), (this.overrideColor.blue + this.additiveColor.blue));
            g.pushState();

            scale = 2.0f;
            
            android.graphics.Matrix mm = new android.graphics.Matrix();
            //mm.setValues(new float[]{(float)(Math.cos(Math.PI*a.okX/180)*sX) ,(float)(-Math.sin(Math.PI*a.okX/180)*sX),(float)0f,(float)(Math.sin(Math.PI*a.okY/180)*sY),(float)(Math.cos(Math.PI*a.okY/180)*sY),(float)0f,0,0,1f});
//            mm.setSinCos((float)(Math.sin(Math.PI*a.okX/180)*sX), (float)(Math.cos(Math.PI*a.okX/180)*sX), (float)0, (float)0);
//            mm.postRotate((float)a.okX, (float)((this.mX+tX)*PVZApp.mDesity), (float)((this.mY+tY)*PVZApp.mDesity));
            mm.postRotate((float)a.okX, this.mX / scale +(float)((tX)*PVZApp.mDesity), this.mY / scale +(float)((tY)*PVZApp.mDesity));
            
//            mm.postScale((float)(0.3), (float)(0.3));
//            mm.postRotate((float)a.okX, (float)(0), (float)(0));
//            mm.mapPoints(pts);
            android.graphics.RectF rect = new android.graphics.RectF();
            rect.left = 0f;
            rect.top = 0f;
            rect.right = 20f;
            rect.bottom = 20f;
            mm.mapRect(rect);
//            mm.setValues(new float[]{0.5f ,0f,0f,(float)0f,0.5f, 0f,0,0,1f});

//            System.out.println("draw.kX " + track.name+":"+kX+":"+kY);
            //mm.setRotate((float)kX*2);//(float)a.okY);
//            mm.setSinCos((float)kX, (float)kY, (float)matrix.tx, (float)matrix.ty);
//            mm.setSinCos((float)Math.sin(kX)*2, (float)Math.cos(kX)*2);
//            mm.postRotate((float)kX, (float)kX, (float)kY);
            if("zombie_football_leftarm_lower".equals(track.name)) {
//            	mm.setRotate(20);
            }
//            if(kX !=0) {
//            	mm.postRotate((float)kX);
//            	mm.setScale((float)(sX*0.5),(float)(sY*0.5));
//            	mm.setSinCos((float)Math.sin(a.okX), (float)Math.sin(a.okY));
//            	mm.setSinCos((float)Math.sin(a.okX), (float)1f, (float)(sX*0.5),(float)(sY*0.5));
//            }
//            mm.postTranslate(this.mX, this.mY);
//            mm.setScale((float)sX, (float)sY);
//            mm.postRotate((float)45);

//            mm.postTranslate(this.mX, this.mY);
//            mm.setSinCos(sinValue, cosValue);
//            g.setTransform(matrix);
//            g.drawImage(img, this.mX, this.mY);
//            mm.setSinCos((float)Math.sin(Math.PI*a.okX/180)*1f, (float)Math.cos(Math.PI*a.okX/180)*1f);
//            mm.postScale((float)0.5, (float)0.5);
//            mm.postRotate((float)kX*4);
            
//            mm.postScale(0.667f, 0.667f);
            mm.postScale(scale, scale);
            g.state.affineMatrix.copyAndroidMatrix(mm);
//            g.scale(0.5, 0.5);
//            g.rotate(kX);
            if(track.name!=null && track.name.toUpperCase().indexOf("ZOMBIE_FOOTBALL_RIGHTLEG_FOOT")>=0) {
//            	System.out.println("frame:"+this.mFrameTime.mFrameBefore+"kX"+a.okX+":"+tX+":"+tY+":"+Math.sin(Math.PI*a.okX/180) +":"+Math.cos(Math.PI*a.okX/180));
//	            g.drawImage(img, this.mX+tX, this.mY+tY);
            }
//            if("IMAGE_REANIM_ZOMBIE_BODY".equals(a.imageID)) {
            if(tY>100) {
//            	g.drawImage(img, (this.mX+tX)*3.3, (this.mY+tY)*3.3);
            } else {
//            	g.drawImage(img, (this.mX+tX)*3.3, (this.mY+tY)*3.3);
            }
//        	g.drawImage(img, (this.mX+tX)*3.3, (this.mY+tY)*3.3);
//        	g.drawImage(img, (this.mX+tX)*PVZApp.mDesity, (this.mY+tY)*PVZApp.mDesity);
            g.drawImage(img, this.mX / scale +(tX)*PVZApp.mDesity, this.mY / scale + (tY)*PVZApp.mDesity );
//            g.drawImage(img, this.mX, this.mY);
//	            System.out.println(a.imageID+":1"+":"+this.mX+":"+this.mY+":"+":"+tX+":"+tY+":frame:"+this.mFrameTime.mFrameBefore+":animTime:"+this.mAnimTime);
//            }
            g.popState();
        }

        private String childTrack ;

        public double  animRate (){
            return (this.mAnimRate);
        }

        private double mAnimTime =0;

        public void  loopType (ReanimLoopType value ){
            this.mLoopType = value;
        }

        private ReanimTrack  getTrack (String trackName ){
            ReanimTrack aTrack=(ReanimTrack)this.mDefinition.trackNameMap.elementAt(trackName);
            if (aTrack == null)
            {
                //throw (new Error((("Track '" + trackName) + "' does not exist!")));
            };
            return (aTrack);
        }

        public Color overrideColor ;
        private ReanimLoopType mLoopType ;
        public boolean useColor =false ;
        private double mAnimRate =0;
        private ReanimDefinition mDefinition =null ;

        public double getTrackVelocity (String trackName ,boolean allFrames){
            ReanimTransform aTransBefore ;
            ReanimTransform aTransAfter ;
            double velo ;
            ReanimTrack aTrack =this.getTrack(trackName );
            Array aList =aTrack.transforms ;
            if (!allFrames)
            {
            	aTransBefore=(ReanimTransform)aList.elementAt(this.mFrameTime.mFrameBefore);
            	aTransAfter=(ReanimTransform)aList.elementAt(this.mFrameTime.mFrameAfter);
            }
            else
            {
            	aTransBefore=(ReanimTransform)aList.elementAt(this.mStartFrame);
            	aTransAfter=(ReanimTransform)aList.elementAt(((this.mStartFrame+this.mFrameCount)-1));
            };
            if (aTransAfter == null)
            {
                aTransAfter = aTransBefore;
            };
            Matrix aMatrixBefore =aTransBefore.matrix ;
            Matrix aMatrixAfter =aTransAfter.matrix ;
            int aXBefore =aMatrixBefore.tx ;
            int aXAfter =aMatrixAfter.tx ;
            if (!allFrames)
            {
                velo = (((aXAfter - aXBefore) * SECONDS_PER_UPDATE) * this.mAnimRate);
            }
            else
            {
                velo = (aXAfter - aXBefore);
            };
            return (velo);
        }

        public int  frameCount (){
            return (this.mFrameCount);
        }

        private Reanimation child ;
        public Color additiveColor ;


		public Rectangle  getBoundsForFrame (){
            ReanimTrack track ;
            int numTransforms ;
            int j =0;
            ReanimTransform transform ;
            Matrix matrix ;
            ImageInst img ;
            BitmapData src ;
            Point p0 ;
            Point p1 ;
            Point p2 ;
            Point p3 ;
            Rectangle aBounds =new Rectangle ();
            int left =NumberX.MAX_VALUE ;
            int right =NumberX.MIN_VALUE ;
            int top =NumberX.MAX_VALUE ;
            int bottom =NumberX.MIN_VALUE ;
            Array tracks =this.mDefinition.tracks ;
            int aNumTracks =tracks.length();
            int i =0;
            while (i < aNumTracks)
            {
            	track=(ReanimTrack)tracks.elementAt(i);
                numTransforms = track.numTransforms;
                j = 0;
                while (j < numTransforms)
                {
                	transform=(ReanimTransform)track.transforms.elementAt(j);
                    matrix = transform.matrix;
                    img = transform.image;
                    if (img == null)
                    {
                    }
                    else
                    {
                        src = img.pixels();
                        p0 = new Point(0, 0);
                        p1 = new Point(src.width, 0);
                        p2 = new Point(src.width, src.height);
                        p3 = new Point(0, src.height);
                        p0 = matrix.transformPoint(p0);
                        p1 = matrix.transformPoint(p1);
                        p2 = matrix.transformPoint(p2);
                        p3 = matrix.transformPoint(p3);
                        left = (int)Math.min(left, Math.floor(Math.min(Math.min(p0.x, p1.x), Math.min(p2.x, p3.x))));
                        right = (int)Math.max(right, Math.ceil(Math.max(Math.max(p0.x, p1.x), Math.max(p2.x, p3.x))));
                        top = (int)Math.min(top, Math.floor(Math.min(Math.min(p0.y, p1.y), Math.min(p2.y, p3.y))));
                        bottom = (int)Math.max(bottom, Math.ceil(Math.max(Math.max(p0.y, p1.y), Math.max(p2.y, p3.y))));
                    };
                    j++;
                };
                i++;
            };
            aBounds.left(left);
            aBounds.right(right);
            aBounds.top(top);
            aBounds.bottom(bottom);
            return (aBounds);
        }

        public void  animRate (double value ){
            this.mAnimRate = value;
        }
        public void  currentTrack (String value ){
            this.playTrack(value);
            this.mCurrentTrack = value;
        }

        public void  attachReanimation (Reanimation child ,String trackName ,int offsetX,int offsetY){
            this.child = child;
            this.child.x(this.mX);
            this.child.y(this.mY);
            this.mChildOffsetX = offsetX;
            this.mChildOffsetY = offsetY;
            this.childTrack = trackName;
        }
        public void  setTrackVisible (String trackName ,boolean isVisible ){
        	this.mVisibleMap.put(trackName,isVisible);
        }
        private void  playTrack (String trackName ){
            ReanimTransform trans ;
            ReanimTrack anim=(ReanimTrack)this.mDefinition.trackNameMap.get(trackName);
            if (anim == null)
            {
                return;
				//throw (new Error((("Track '" + trackName) + "' does not exist!")));
            };
            this.mCurrentTrack = trackName;
            this.mAnimTime = 0;
            int i =0;
            int startFrame =-1;
            int endFrame =-1;
            Array transforms =anim.transforms ;
            int numTransforms =transforms.length();
            i = 0;
            while (i < numTransforms)
            {
            	trans=(ReanimTransform)transforms.elementAt(i);
                if (trans.frame >= 0)
                {
                    startFrame = i;
                    break;
                };
                i++;
            };
            while (i < numTransforms)
            {
            	trans=(ReanimTransform)transforms.elementAt(i);
                if (trans.frame >= 0)
                {
                    endFrame = i;
                };
                i++;
            };
            this.mStartFrame = (int)startFrame;
            this.mFrameCount = (int)(endFrame - startFrame);
//            System.out.println(""+trackName+":::startFrame:"+this.mStartFrame+":this.mFrameCount="+this.mFrameCount);
        }

        private double mLastAnimTime =0;

        public void  draw (Graphics2D g ){
            ReanimTrack track ;
            if (this.mIsDead)
            {
                return;
            };
            if (!AppBase.LOW_QUALITY)
            {
                this.drawLerp(g, identityMatrix,0);
                return;
            };
            Array tracks =this.mDefinition.tracks ;
            int aNumTracks =tracks.length();
            int i = 0;
            while (i < aNumTracks)
            {
            	track=(ReanimTrack)tracks.elementAt(i);

                this.drawTrack(g, track);
                i++;
            };
            if (this.child != null)
            {
                this.child.useColor = this.useColor;
                this.child.additiveColor.copy(this.additiveColor);
                this.child.overrideColor.copy(this.overrideColor);
                this.child.x((this.locatorX + this.mChildOffsetX));
                this.child.y ((this.locatorY + this.mChildOffsetY));
                this.child.draw(g);
            };
        }
        public void  update (){
            if (this.mCurrentTrack == null)
            {
                return;
            };
            if (this.mFrameCount == 0)
            {
                return;
            };
            if (this.mIsDead)
            {
                return;
            };
            this.mLastAnimTime = this.mAnimTime;
            this.mAnimTime = (this.mAnimTime + ((SECONDS_PER_UPDATE * this.mAnimRate) / this.mFrameCount));
//            System.out.println("r.update0 "+this.mFrameTime.mFrameBefore +":"+this.mFrameTime.mFrameAfter);
//            System.out.println("this.mFrameTime:"+this.mFrameTime.mFrameBefore+":"+this.mFrameTime.mFrameAfter+":"+this.mAnimTime+":"+this.mLastAnimTime);

            if (this.mAnimRate > 0)
            {
                this.mLoopType.updatePositive(this);
            }
            else
            {
                this.mLoopType.updateNegative(this);
            };
			if(!this.mHold) {
            	this.mFrameTime.update(this.mAnimTime, this.mStartFrame, this.mFrameCount, this.mLoopType.doHold());
//                System.out.println("r.update1 "+this.mFrameTime.mFrameBefore +":"+this.mFrameTime.mFrameAfter);

			} else {
				this.mFrameTime.holdFrame(this.mHoldFrame);
			}
            if (this.child != null)
            {
                this.child.update();
            };
        }
        private double  lerp (double zero ,double one ,double time ){
            return ((zero + ((one - zero) * time)));
        }

        private int  lerp (int zero ,int one ,int time ){
            return ((zero + ((one - zero) * time)));
        }

        public String  currentTrack (){
            return (this.mCurrentTrack);
        }
        public ReanimDefinition  definition (){
            return (this.mDefinition);
        }

        private Dictionary mVisibleMap ;

        public void  drawLerp (Graphics2D g ,Matrix transform ,double alpha){
            ReanimTrack track ;
            if (this.mIsDead)
            {
                return;
            };
            g.pushState();
            if (transform != null)
            {
                g.transform(transform);
            };
            Array tracks =this.mDefinition.tracks ;
            int aNumTracks =tracks.length();
            int i =0;

            while (i < aNumTracks)
            {
            	track=(ReanimTrack)tracks.elementAt(i);
                this.drawTrackLerp(g, track, alpha);
                if ((((track.name == this.childTrack)) && (!((this.child == null)))))
                {
                    this.child.useColor = this.useColor;
                    this.child.additiveColor.copy(this.additiveColor);
                    this.child.overrideColor.copy(this.overrideColor);
                    this.child.x((this.locatorX + this.mChildOffsetX));
                    this.child.y((this.locatorY + this.mChildOffsetY));
                    this.child.drawLerp(g, transform, alpha);
                };
                i++;
            };
            g.popState();
        }

        private String mCurrentTrack =null ;

        public Point  getTrackPosition (String trackName ){
            ReanimTrack aTrack =this.getTrack(trackName );
            ReanimTransform aTrans=(ReanimTransform)aTrack.transforms.elementAt(this.mFrameTime.mFrameBefore);
            return (new Point((int)aTrans.kX, (int)aTrans.kY));
        }
        private void  drawTrack (Graphics2D g ,ReanimTrack track ){
            Matrix init ;
            if(this.mVisibleMap.get(track.name)==null)
            {
                return;
            };
            ReanimTransform transform=(ReanimTransform)track.transforms.elementAt(this.mFrameTime.mFrameBefore);
            if (transform.frame < 0)
            {
                return;
            };
            Matrix matrix =transform.matrix ;
            if (track.name == this.childTrack)
            {
            	init=((ReanimTransform)track.transforms.elementAt(this.mStartFrame)).matrix;
                this.locatorX = (this.mX + (matrix.tx - init.tx));
                this.locatorY = ((this.mY + (matrix.ty - init.ty)) + 1);
            };

            ImageInst cache =transform.cache ;
            if (cache != null)
            {
                cache.useColor = this.useColor;
                if (cache.useColor)
                {
                    cache.setColor((this.overrideColor.alpha + this.additiveColor.alpha), (this.overrideColor.red + this.additiveColor.red), (this.overrideColor.green + this.additiveColor.green), (this.overrideColor.blue + this.additiveColor.blue));
                };
                g.blitImage(cache, this.mX, this.mY);
                return;
            };
            ImageInst img =transform.image ;
            if (img == null)
            {
                return;
            };
            BitmapData src =img.pixels() ;
            Point p0 =new Point(0,0);
            Point p1 =new Point(src.width ,0);
            Point p2 =new Point(src.width ,src.height);
            Point p3 =new Point(0,src.height);
            p0 = matrix.transformPoint(p0);
            p1 = matrix.transformPoint(p1);
            p2 = matrix.transformPoint(p2);
            p3 = matrix.transformPoint(p3);
            int left =(int)Math.floor(Math.min(Math.min(p0.x ,p1.x ),Math.min(p2.x ,p3.x )));
            int right =(int)Math.ceil(Math.max(Math.max(p0.x ,p1.x ),Math.max(p2.x ,p3.x )));
            int top =(int)Math.floor(Math.min(Math.min(p0.y ,p1.y ),Math.min(p2.y ,p3.y )));
            int bottom =(int)Math.ceil(Math.max(Math.max(p0.y ,p1.y ),Math.max(p2.y ,p3.y )));
            matrix.translate(-(left), -(top));
            BitmapData aData =new BitmapData ((right -left ),(bottom -top ),true ,0);
            ImageInst aCache =new ImageInst(new ImageData(aData ));
            aCache.destPt.x = (int)left;
            aCache.destPt.y = (int)top;
            aData.draw(src, matrix, null, null, null, true);
            transform.cache = aCache;
            this.drawTrack(g, track);
        }

        public ReanimFrameTime mFrameTime ;
        public int mFrameCount =0;

        public void  y (int value ){
            this.mY = value;
        }

        private int mChildOffsetX =0;
        private int mChildOffsetY =0;
        public int mStartFrame =0;
        public boolean mIsDead =false ;


		public boolean mHold =false ;
		public int mHoldFrame =0;

		public void  setHoldFrame (int iFrame ){
			mHold = true;
			mHoldFrame = iFrame;
		}

		public void  releaseHold (){
			mHold = false;
			this.mFrameTime.mHold = false;
		}

        public void  animTime (double value ){
            this.mAnimTime = value;
        }

        public double  animTime (){
            return (this.mAnimTime);
        }

        public void  x (int value ){
            this.mX = value;
        }

		public ReanimTrack  getTrackById (int id ){
			Array tracks =this.mDefinition.tracks ;
			ReanimTrack track ;
			if(id<0 || id>tracks.length()) return null;
			track=(ReanimTrack)tracks.elementAt(id);
			return track;
		}

		public void  overrideTransform (int trackid ,int frameid ,int x1 ,int y1 ,int kX ,int kY ,double sX ,double sY ){
			Array tracks =this.mDefinition.tracks ;
			ReanimTrack track ;
			ReanimTransform transform ;
			track=(ReanimTrack)tracks.elementAt(trackid);
			transform=(ReanimTransform)track.transforms.elementAt(frameid);
			transform.kX = (int)(((-2 * Math.PI) * kX) / 360);
			transform.kY = (int)(((-2 * Math.PI) * kY) / 360);
			transform.okX = kX;
			transform.okY = kY;
			transform.tX = x1;
			transform.tY = y1;
			transform.sX = sX;
			transform.sY = sY;
			transform.calcMatrix();
			transform.cache = null;
			track.transforms.add(frameid,transform);
		    tracks.add(trackid,track);
		}

		public  Reanimation (ReanimDefinition definition ){
            super();
            this.mFrameTime = new ReanimFrameTime();
            this.mLoopType = LOOP_TYPE_ALWAYS;
            this.mVisibleMap = new Dictionary();
            this.mDefinition = definition.clone();
            this.overrideColor = Color.ARGB(1, 1, 1, 1);
            this.additiveColor = Color.ARGB(1, 0, 0, 0);
            this.useColor = false;
        }
    }


