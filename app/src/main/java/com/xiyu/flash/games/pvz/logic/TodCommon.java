package com.xiyu.flash.games.pvz.logic;
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
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.*;
import java.lang.Math;

import com.xiyu.flash.framework.graphics.Color;

    public class TodCommon {

        public static final int CURVE_WEAK_FAST_IN_OUT =8;
        public static final int CURVE_SIN_WAVE =12;
        public static final int CURVE_BOUNCE_FAST_MIDDLE =10;
        public static final int CURVE_CONSTANT =0;
        public static final int CURVE_LINEAR =1;
        public static final int CURVE_EASE_IN_OUT =4;
        public static final double CIRCLE_QUARTER =(Math.PI *0.5);//1.5707963265
        public static final double CIRCLE_FULL =(Math.PI *2);//6.283185306
        public static final int CURVE_EASE_IN =2;
        public static final double SECONDS_PER_UPDATE =0.01;

        public static double  ClampFloat (double num ,double minNum ,double maxNum ){
            if (num <= minNum)
            {
                return (minNum);
            };
            if (num >= maxNum)
            {
                return (maxNum);
            };
            return (num);
        }
        public static int  TodPickFromArray (Array theArray ,int theCount ){
            int aPick =(int)(Math.random ()*theCount );
            return aPick;
            //return ((Integer)(theArray.elementAt(aPick))).intValue();
        }
        public static int  TodPickFromArray2 (Dictionary theArray ,int theCount ){
            int aPick =(int)(Math.random ()*theCount );
            return ((Integer)(theArray.elementAt(aPick))).intValue();
        }
        public static double  TodCurveEvaluate (double theTime ,double thePositionStart ,int thePositionEnd ,int theCurve ){
            double aWarpedTime ;
            switch (theCurve)
            {
                case CURVE_CONSTANT:
                    aWarpedTime = 0;
                    break;
                case CURVE_LINEAR:
                    aWarpedTime = theTime;
                    break;
                case CURVE_EASE_IN:
                    aWarpedTime = TodCurveQuad(theTime);
                    break;
                case CURVE_EASE_OUT:
                    aWarpedTime = TodCurveInvQuad(theTime);
                    break;
                case CURVE_EASE_IN_OUT:
                    aWarpedTime = TodCurveS(TodCurveS(theTime));
                    break;
                case CURVE_EASE_IN_OUT_WEAK:
                    aWarpedTime = TodCurveS(theTime);
                    break;
                case CURVE_FAST_IN_OUT:
                    aWarpedTime = TodCurveInvQuadS(TodCurveInvQuadS(theTime));
                    break;
                case CURVE_FAST_IN_OUT_WEAK:
                    aWarpedTime = TodCurveInvQuadS(theTime);
                    break;
                case CURVE_BOUNCE:
                    aWarpedTime = TodCurveBounce(theTime);
                    break;
                case CURVE_BOUNCE_FAST_MIDDLE:
                    aWarpedTime = TodCurveQuad(TodCurveBounce(theTime));
                    break;
                case CURVE_BOUNCE_SLOW_MIDDLE:
                    aWarpedTime = TodCurveInvQuad(TodCurveBounce(theTime));
                    break;
                case CURVE_SIN_WAVE:
                    aWarpedTime = Math.sin((theTime * CIRCLE_FULL));
                    break;
                case CURVE_EASE_SIN_WAVE:
                    aWarpedTime = Math.sin((TodCurveS(theTime) * CIRCLE_FULL));
                    break;
                default:
                    aWarpedTime = 0;
            };
            return (FloatLerp(thePositionStart, thePositionEnd, aWarpedTime));
        }

        public static  double CIRCLE_EIGHTH =(Math.PI *0.25);//0.78539816325

        public static double  TodCalcSmoothWeight (double aWeight ,int aLastPicked ,int aSecondLastPicked ){
            if (aWeight < EPSILON)
            {
                return (0);
            };
            int aSmoothFactor =2;
            double aExpectedLength1 =(1/aWeight );
            double aExpectedLength2 =(aExpectedLength1 *2);
            double aDelta1 =((aLastPicked +1)-aExpectedLength1 );
            double aDelta2 =((aSecondLastPicked +1)-aExpectedLength2 );
            double aFactor1 =(1+((aDelta1 /aExpectedLength1 )*aSmoothFactor ));
            double aFactor2 =(1+((aDelta2 /aExpectedLength2 )*aSmoothFactor ));
            double aFactorFinal =TodCommon.ClampFloat (((aFactor1 *0.75)+(aFactor2 *0.25)),0.01,100);
            return ((aWeight * aFactorFinal));
        }

        public static  double CIRCLE_SIXTH =(Math.PI *0.333333333);//1.0471975499528

        public static WeightedGridArray  TodPickFromWeightedGridArray (Array theArray ,int theCount ){
            int aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=(aTotalWeight+((WeightedGridArray)theArray.elementAt(i)).mWeight);
                i++;
            };
            int aPick =(int)(Math.random ()*aTotalWeight );
            int aAcculumatedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aAcculumatedWeight=aAcculumatedWeight+((WeightedGridArray)theArray.elementAt(i)).mWeight;
                if (aPick < aAcculumatedWeight)
                {
                	return (WeightedGridArray)(theArray.elementAt(i));
                };
                i++;
            };
            return (WeightedGridArray)(theArray.elementAt(0));
        }

        public static WeightedGridArray  TodPickFromWeightedGridArray (Dictionary theArray ,int theCount ){
            int aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=(aTotalWeight+((WeightedGridArray)theArray.elementAt(i)).mWeight);
                i++;
            };
            int aPick =(int)(Math.random ()*aTotalWeight );
            int aAcculumatedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aAcculumatedWeight=aAcculumatedWeight+((WeightedGridArray)theArray.elementAt(i)).mWeight;
                if (aPick < aAcculumatedWeight)
                {
                	return (WeightedGridArray)(theArray.elementAt(i));
                };
                i++;
            };
            return (WeightedGridArray)(theArray.elementAt(0));
        }

        public static double  TodCurveQuad (double theTime ){
            return ((theTime * theTime));
        }
        public static int  TodPickFromWeightedArray (Array theArray ,int theCount ){
            WeightedArray aArrayItem =TodPickArrayItemFromWeightedArray(theArray ,theCount );
            return (aArrayItem.mItem);
        }
        public static int  TodPickFromWeightedArray2 (Dictionary theArray ,int theCount ){
            WeightedArray aArrayItem =TodPickArrayItemFromWeightedArray2(theArray ,theCount );
            return (aArrayItem.mItem);
        }
        public static double  TodCurveInvQuad (double theTime ){
            return (((2 * theTime) - (theTime * theTime)));
        }
        public static double  TodCurveEvaluateClamped (double theTime ,double thePositionStart ,int thePositionEnd ,int theCurve ){
            if (theTime <= 0)
            {
                return (thePositionStart);
            };
            if (theTime >= 1)
            {
                if ((((((((((theCurve == CURVE_BOUNCE)) || ((theCurve == CURVE_BOUNCE_SLOW_MIDDLE)))) || ((theCurve == CURVE_BOUNCE_FAST_MIDDLE)))) || ((theCurve == CURVE_SIN_WAVE)))) || ((theCurve == CURVE_EASE_SIN_WAVE))))
                {
                    return (thePositionStart);
                };
                return (thePositionEnd);
            };
            return (TodCurveEvaluate(theTime, thePositionStart, thePositionEnd, theCurve));
        }
        public static double  TodAnimateCurveFloat (int theTimeStart ,int theTimeEnd ,double theTimeAge ,double thePositionStart ,int thePositionEnd ,int theCurve ){
            double aElapse =(theTimeAge -theTimeStart );
            double aMoveTime =(theTimeEnd -theTimeStart );
            double aTime = aElapse / aMoveTime;
            return (TodCurveEvaluateClamped(aTime, thePositionStart, thePositionEnd, theCurve));
        }
        public static double  TodCurveQuadS (int theTime ){
            if (theTime <= 0.5)
            {
                return ((TodCurveQuad((theTime * 2)) * 0.5));
            };
            return (TodCurveInvQuad((((theTime - (0 * 2)) * 0.5) + 0.5)));
        }

        public static  double CIRCLE_HALF =Math.PI ;//3.141592653
        public static  double EPSILON =0.000001;//Math.E;

        public static double  RandRangeFloat (double theMin ,double theMax ){
            return ((theMin + (Math.random() * (theMax - theMin))));
        }

        public static final int CURVE_EASE_OUT =3;
        public static final int CURVE_FAST_IN_OUT_WEAK =7;
        public static final int CURVE_BOUNCE_SLOW_MIDDLE =11;

        public static int  RandRangeInt (int theMin ,int theMax ){
            return (int)((theMin + (Math.random() * ((theMax - theMin) + 1))));
        }
        public static void  TodUpdateSmoothArrayPick (Array theArray ,int theCount ,int thePickIndex ){
            int i = 0;
            while (i < theCount)
            {
            	if(((SmoothArray)theArray.elementAt(i)).mWeight>0)
                {
					((SmoothArray)theArray.elementAt(i)).mLastPicked++;
					((SmoothArray)theArray.elementAt(i)).mSecondLastPicked++;
                };
                i++;
            };
            ((SmoothArray)theArray.elementAt(thePickIndex)).mSecondLastPicked=((SmoothArray)theArray.elementAt(thePickIndex)).mLastPicked;
            ((SmoothArray)theArray.elementAt(thePickIndex)).mLastPicked=0;
        }
        public static void  TodUpdateSmoothArrayPick2 (Dictionary theArray ,int theCount ,int thePickIndex ){
            int i = 0;
            while (i < theCount)
            {
            	if(((SmoothArray)theArray.elementAt(i)).mWeight>0)
                {
					((SmoothArray)theArray.elementAt(i)).mLastPicked++;
					((SmoothArray)theArray.elementAt(i)).mSecondLastPicked++;
                };
                i++;
            };
            ((SmoothArray)theArray.elementAt(thePickIndex)).mSecondLastPicked=((SmoothArray)theArray.elementAt(thePickIndex)).mLastPicked;
            ((SmoothArray)theArray.elementAt(thePickIndex)).mLastPicked=0;
        }
        public static int  ClampInt (int num ,int minNum ,int maxNum ){
            if (num <= minNum)
            {
                return (minNum);
            };
            if (num >= maxNum)
            {
                return (maxNum);
            };
            return (num);
        }
        public static double  TodCurveS (double theTime ){
            return ((((3 * theTime) * theTime) - (((2 * theTime) * theTime) * theTime)));
        }
        public static WeightedArray  TodPickArrayItemFromWeightedArray (Array theArray ,int theCount ){
            int aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=aTotalWeight+((WeightedArray)theArray.elementAt(i)).mWeight;
                i++;
            };
            int aPick =(int)(Math.random ()*aTotalWeight );
            int aAcculumatedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aAcculumatedWeight=aAcculumatedWeight+((WeightedArray)theArray.elementAt(i)).mWeight;
                if (aPick < aAcculumatedWeight)
                {
                	return(WeightedArray)(theArray.elementAt(i));
                };
                i++;
            };
            return(WeightedArray)(theArray.elementAt(0));
        }
        public static WeightedArray  TodPickArrayItemFromWeightedArray2 (Dictionary theArray ,int theCount ){
            int aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=aTotalWeight+((WeightedArray)theArray.elementAt(i)).mWeight;
                i++;
            };
            int aPick =(int)(Math.random ()*aTotalWeight );
            int aAcculumatedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aAcculumatedWeight=aAcculumatedWeight+((WeightedArray)theArray.elementAt(i)).mWeight;
                if (aPick < aAcculumatedWeight)
                {
                	return(WeightedArray)(theArray.elementAt(i));
                };
                i++;
            };
            return(WeightedArray)(theArray.elementAt(0));
        }
        
        public static int  TodAnimateCurve (int theTimeStart ,int theTimeEnd ,int theTimeAge ,int thePositionStart ,int thePositionEnd ,int theCurve ){
            return (int)(Math.round(TodAnimateCurveFloat(theTimeStart, theTimeEnd, theTimeAge, thePositionStart, thePositionEnd, theCurve)));
        }

        public static final int CURVE_EASE_SIN_WAVE =13;
        public static final int CURVE_EASE_IN_OUT_WEAK =5;
        public static final int CURVE_FAST_IN_OUT =6;

        public static double  TodCurveInvQuadS (double theTime ){
            if (theTime <= 0.5)
            {
                return ((TodCurveInvQuad((theTime * 2)) * 0.5));
            };
            return (((TodCurveQuad(((theTime - 0.5) * 2)) * 0.5) + 0.5));
        }

        private static Color gFlashingColor =new Color ();

        public static double  FloatLerp (double theZeroValue ,double theOneValue ,double theTime ){
            double value ;
            value = (theZeroValue + ((theOneValue - theZeroValue) * theTime));
            return (value);
        }
        public static double  TodCurveBounce (double theTime ){
            return ((1 - Math.abs((1 - (theTime * 2)))));
        }
        public static double  TodPickFromSmoothArray (Array theArray ,int theCount ){
            int aPickIndex ;
            SmoothArray aItem ;
            double aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=aTotalWeight+((SmoothArray)theArray.elementAt(i)).mWeight;
                i++;
            };
            double aNormalizeFactor =(1/aTotalWeight );
            double aTotalAdjustedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aTotalAdjustedWeight=aTotalAdjustedWeight + TodCalcSmoothWeight( ((SmoothArray)theArray.elementAt(i)).mWeight*aNormalizeFactor, ((SmoothArray)theArray.elementAt(i)).mLastPicked,((SmoothArray)theArray.elementAt(i)).mSecondLastPicked);
                i++;
            };
            double aRandWeight =(Math.random ()*aTotalAdjustedWeight );
            double aAcculumatedWeight =0;
            aPickIndex = 0;
            while (aPickIndex < (theCount - 1))
            {
            	aItem=(SmoothArray)theArray.elementAt(aPickIndex);
                aAcculumatedWeight = (aAcculumatedWeight + TodCalcSmoothWeight((aItem.mWeight * aNormalizeFactor), aItem.mLastPicked, aItem.mSecondLastPicked));
                if (aRandWeight <= aAcculumatedWeight)
                {
                    break;
                };
                aPickIndex++;
            };
            i = 0;
            while (i < theCount)
            {
            	if(((SmoothArray)theArray.elementAt(i)).mWeight>0)
                {
            		((SmoothArray)theArray.elementAt(i)).mLastPicked++;
            		((SmoothArray)theArray.elementAt(i)).mSecondLastPicked++;
                };
                i++;
            };
            ((SmoothArray)theArray.elementAt(aPickIndex)).mSecondLastPicked=((SmoothArray)theArray.elementAt(aPickIndex)).mLastPicked;
            ((SmoothArray)theArray.elementAt(aPickIndex)).mLastPicked=0;
            TodUpdateSmoothArrayPick(theArray, theCount, aPickIndex);
            return(((SmoothArray)theArray.elementAt(aPickIndex)).mItem);
        }

        public static double  TodPickFromSmoothArray2 (Dictionary theArray ,int theCount ){
            int aPickIndex ;
            SmoothArray aItem ;
            double aTotalWeight =0;
            int i =0;
            while (i < theCount)
            {
            	aTotalWeight=aTotalWeight+((SmoothArray)theArray.elementAt(i)).mWeight;
                i++;
            };
            double aNormalizeFactor =(1/aTotalWeight );
            double aTotalAdjustedWeight =0;
            i = 0;
            while (i < theCount)
            {
            	aTotalAdjustedWeight=aTotalAdjustedWeight + TodCalcSmoothWeight( ((SmoothArray)theArray.elementAt(i)).mWeight*aNormalizeFactor, ((SmoothArray)theArray.elementAt(i)).mLastPicked,((SmoothArray)theArray.elementAt(i)).mSecondLastPicked);
                i++;
            };
            double aRandWeight =(Math.random ()*aTotalAdjustedWeight );
            double aAcculumatedWeight =0;
            aPickIndex = 0;
            while (aPickIndex < (theCount - 1))
            {
            	aItem=(SmoothArray)theArray.elementAt(aPickIndex);
                aAcculumatedWeight = (aAcculumatedWeight + TodCalcSmoothWeight((aItem.mWeight * aNormalizeFactor), aItem.mLastPicked, aItem.mSecondLastPicked));
                if (aRandWeight <= aAcculumatedWeight)
                {
                    break;
                };
                aPickIndex++;
            };
            i = 0;
            while (i < theCount)
            {
            	if(((SmoothArray)theArray.elementAt(i)).mWeight>0)
                {
            		((SmoothArray)theArray.elementAt(i)).mLastPicked++;
            		((SmoothArray)theArray.elementAt(i)).mSecondLastPicked++;
                };
                i++;
            };
            ((SmoothArray)theArray.elementAt(aPickIndex)).mSecondLastPicked=((SmoothArray)theArray.elementAt(aPickIndex)).mLastPicked;
            ((SmoothArray)theArray.elementAt(aPickIndex)).mLastPicked=0;
            TodUpdateSmoothArrayPick2(theArray, theCount, aPickIndex);
            return(((SmoothArray)theArray.elementAt(aPickIndex)).mItem);
        }
        public static final int CURVE_BOUNCE =9;
        public static final double PI =3.141592653;

        public static Color  GetFlashingColor (int theCounter ,int theFlashTime ){
            int aFlash = (int)((theCounter % theFlashTime));
            int aHalfFlashTime =(theFlashTime >>1);
            int aGrayColor =ClampInt ((55+((Math.abs ((aHalfFlashTime -aFlash ))*200)/aHalfFlashTime )),0,0xFF );
            gFlashingColor.alpha = 1;
            gFlashingColor.red = (aGrayColor / 0xFF);
            gFlashingColor.green = (aGrayColor / 0xFF);
            gFlashingColor.blue = (aGrayColor / 0xFF);
            return (gFlashingColor);
        }

        public static final int TICKS_PER_SECOND =100;

    }


