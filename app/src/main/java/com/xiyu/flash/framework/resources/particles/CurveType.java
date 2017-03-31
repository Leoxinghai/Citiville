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

import com.xiyu.flash.framework.utils.CEnum;
    public class CurveType extends CEnum {

        public static CurveType  fromUInt (int value ){
            switch (value)
            {
                case 0:
                    return (CONSTANT);
                case 1:
                    return (LINEAR);
                case 2:
                    return (EASE_IN);
                case 3:
                    return (EASE_OUT);
                case 4:
                    return (EASE_IN_OUT);
                case 5:
                    return (EASE_IN_OUT_WEAK);
                case 6:
                    return (FAST_IN_OUT);
                case 7:
                    return (FAST_IN_OUT_WEAK);
                case 8:
                    return (BOUNCE);
                case 9:
                    return (BOUNCE_FAST_MIDDLE);
                case 10:
                    return (BOUNCE_SLOW_MIDDLE);
                case 11:
                    return (SIN_WAVE);
                case 12:
                    return (EASE_SIN_WAVE);
            };
            return null;
//            //throw (new ArgumentError("Value does not map to a CurveType"));
        }

        public static  CurveType EASE_IN_OUT =new CurveType(4);
        public static  CurveType BOUNCE =new CurveType(8);

        public static int  toUInt (CurveType value ){
            if (value == CONSTANT)
            {
                return (0);
            };
            if (value == LINEAR)
            {
                return (1);
            };
            if (value == EASE_IN)
            {
                return (2);
            };
            if (value == EASE_OUT)
            {
                return (3);
            };
            if (value == EASE_IN_OUT)
            {
                return (4);
            };
            if (value == EASE_IN_OUT_WEAK)
            {
                return (5);
            };
            if (value == FAST_IN_OUT)
            {
                return (6);
            };
            if (value == FAST_IN_OUT_WEAK)
            {
                return (7);
            };
            if (value == BOUNCE)
            {
                return (8);
            };
            if (value == BOUNCE_FAST_MIDDLE)
            {
                return (9);
            };
            if (value == BOUNCE_SLOW_MIDDLE)
            {
                return (10);
            };
            if (value == SIN_WAVE)
            {
                return (11);
            };
            if (value == EASE_SIN_WAVE)
            {
                return (12);
            };
            
            return -1;
            //throw (new Error("How did I get here?"));
        }

        public static final CurveType EASE_IN =new CurveType(2);
        public static final CurveType FAST_IN_OUT =new CurveType(6);
        public static final CurveType EASE_SIN_WAVE =new CurveType(12);
        public static final CurveType BOUNCE_SLOW_MIDDLE =new CurveType(10);
        public static final CurveType EASE_OUT =new CurveType(3);
        public static final CurveType FAST_IN_OUT_WEAK =new CurveType(7);
        public static final CurveType BOUNCE_FAST_MIDDLE =new CurveType(9);
        public static final CurveType CONSTANT =new CurveType(0);
        public static final CurveType LINEAR =new CurveType(1);
        public static final CurveType SIN_WAVE =new CurveType(11);
        public static final CurveType EASE_IN_OUT_WEAK =new CurveType(5);

        public String  toString (){
            return (name);
        }

        public CurveType() {
//        	CEnum.InitEnumConstants(CurveType);
        }
        
        private int type;
        public CurveType(int _type) {
        	this.type = _type;
        	this.name = "type "+this.type;
        }
/*
        {
            CEnum.InitEnumConstants(CurveType);
        }
*/
    }


