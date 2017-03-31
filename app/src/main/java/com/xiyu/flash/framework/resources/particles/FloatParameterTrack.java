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

    public class FloatParameterTrack {
        public Array mNodes ;

        public double curveEvaluate (double time ,double start ,double end ,CurveType curve ){
            double aWarpedTime =0;
            if(curve == CurveType.CONSTANT) {
                    aWarpedTime = 0;
            } else if(curve == CurveType.LINEAR) {
                    aWarpedTime = time;
            } else if(curve == CurveType.EASE_IN) {
                    aWarpedTime = this.curveQuad(time);
            } else if(curve == CurveType.EASE_OUT) {
                    aWarpedTime = this.curveInvQuad(time);
            } else if(curve == CurveType.EASE_IN_OUT) {
                    aWarpedTime = this.curveS(this.curveS(time));
            } else if(curve == CurveType.EASE_IN_OUT_WEAK) {
                    aWarpedTime = this.curveS(time);
            } else if(curve == CurveType.FAST_IN_OUT) {
                    aWarpedTime = this.curveInvQuadS(this.curveInvQuadS(time));
            } else if(curve == CurveType.FAST_IN_OUT_WEAK) {
                    aWarpedTime = this.curveInvQuadS(time);
            } else if(curve == CurveType.BOUNCE) {
                    aWarpedTime = this.curveBounce(time);
            } else if(curve == CurveType.BOUNCE_FAST_MIDDLE) {
                    aWarpedTime = this.curveQuad(this.curveBounce(time));
            } else if(curve == CurveType.BOUNCE_SLOW_MIDDLE) {
                    aWarpedTime = this.curveInvQuad(this.curveBounce(time));
            } else if(curve == CurveType.SIN_WAVE) {
                    aWarpedTime = Math.sin(((time * Math.PI) * 2));
            } else if(curve == CurveType.EASE_SIN_WAVE) {
                    aWarpedTime = Math.sin(((this.curveS(time) * Math.PI) * 2));
            } else{
                      //throw (new Error((("Unknown curve type '" + curve) + "'")));
            };
            double result = ((start + ((end - start) * aWarpedTime)));
//            System.out.println("curveEvaluate."+start+":"+end+"::"+curve.name+":"+result);
            return result;
        }
        private double curveInvQuad (double time ){
            return (((2 * time) - (time * time)));
        }
        public void  scale (int scale ){
            FloatParameterTrackNode aNode ;
            int aNumNodes =this.mNodes.length() ;
            int i =0;
            while (i < aNumNodes)
            {
            	aNode=(FloatParameterTrackNode)this.mNodes.elementAt(i);
                aNode.mHighValue = (aNode.mHighValue * scale);
                aNode.mLowValue = (aNode.mLowValue * scale);
                i++;
            };
        }
        private double curveS (double time ){
            return ((((3 * time) * time) - (((2 * time) * time) * time)));
        }
        private double curveQuadS (double time ){
            if (time <= 0.5)
            {
                return ((this.curveQuad((time * 2)) * 0.5));
            };
            return (((this.curveInvQuad(((time - 0.5) * 2)) * 0.5) + 0.5));
        }
        public double  evaluateLast (int time ,double interp ){
            if (time < 0)
            {
                return (0);
            };
            return (this.evaluate(time, interp));
        }
        private double curveInvQuadS (double time ){
            if (time <= 0.5)
            {
                return ((this.curveInvQuad((time * 2)) * 0.5));
            };
            return (((this.curveQuad(((time - 0.5) * 2)) * 0.5) + 0.5));
        }
        public boolean  isConstantZero (){
            if (this.mNodes.length() == 0)
            {
                return (true);
            };
            if (this.mNodes.length() != 1)
            {
                return (false);
            };
            FloatParameterTrackNode aNode=(FloatParameterTrackNode)this.mNodes.elementAt(0);
            if ((((aNode.mLowValue == 0)) && ((aNode.mHighValue == 0))))
            {
                return (true);
            };
            return (false);
        }
        public boolean  isSet (){
            if (this.mNodes.length() == 0)
            {
                return (false);
            };
            if(((FloatParameterTrackNode)this.mNodes.elementAt(0)).mCurveType==CurveType.CONSTANT)
            {
                return (false);
            };
            return (true);
        }
        public double evaluate (double time ,double interp ){
            FloatParameterTrackNode aRightNode ;
            FloatParameterTrackNode aLeftNode ;
            double aTimeFraction ;
            double aLeftValue ;
            double aRightValue ;

            if (this.mNodes.length() == 0)
            {
                return (0);
            };
            FloatParameterTrackNode first=(FloatParameterTrackNode)this.mNodes.elementAt(0);
            if (time < first.mTime)
            {
                return (this.curveEvaluate(interp, first.mLowValue, first.mHighValue, first.mDistribution));
            };
            int aNodeCount =this.mNodes.length() ;
            int i =1;
            while (i < aNodeCount)
            {
            	aRightNode=(FloatParameterTrackNode)this.mNodes.elementAt(i);
                if (time > aRightNode.mTime)
                {
                }
                else
                {
                	aLeftNode=(FloatParameterTrackNode)this.mNodes.elementAt((i-1));
                    aTimeFraction = ((time - aLeftNode.mTime) / (aRightNode.mTime - aLeftNode.mTime));
                    aLeftValue = this.curveEvaluate(interp, aLeftNode.mLowValue, aLeftNode.mHighValue, aLeftNode.mDistribution);
                    aRightValue = this.curveEvaluate(interp, aRightNode.mLowValue, aRightNode.mHighValue, aRightNode.mDistribution);
                    //System.out.println("evaluate "+aTimeFraction+":"+aLeftValue+":"+aRightValue+":"+aLeftNode.mCurveType);
                    return (this.curveEvaluate(aTimeFraction, (int)aLeftValue, (int)aRightValue, aLeftNode.mCurveType));
                };
                i++;
            };
            FloatParameterTrackNode last=(FloatParameterTrackNode)this.mNodes.elementAt((aNodeCount-1));
            return (this.curveEvaluate(interp, last.mLowValue, last.mHighValue, last.mDistribution));
        }
        public String  toString (){
            return ((("[FloatParameterTrack -- " + this.mNodes) + "]"));
        }
        private double curveQuad (double time ){
            return ((time * time));
        }
        private double curveBounce (double time ){
            return ((1 - Math.abs((1 - (time * 2)))));
        }
        public void  setDefault (int value ){
            if (((!((this.mNodes.length() == 0))) || ((value == 0))))
            {
                return;
            };
            FloatParameterTrackNode aNode =new FloatParameterTrackNode ();
            aNode.mTime = 0;
            aNode.mLowValue = value;
            aNode.mHighValue = value;
            aNode.mCurveType = CurveType.CONSTANT;
            aNode.mDistribution = CurveType.LINEAR;
            this.mNodes.push(aNode);
        }

        public  FloatParameterTrack (){
            this.mNodes = new Array();
        }
    }

