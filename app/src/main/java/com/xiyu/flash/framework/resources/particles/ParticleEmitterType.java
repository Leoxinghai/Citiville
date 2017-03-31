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
    public class ParticleEmitterType extends CEnum {

        public static ParticleEmitterType  fromUInt (int value ){
            switch (value)
            {
                case 0:
                    return (CIRCLE);
                case 1:
                    return (BOX);
                case 2:
                    return (BOX_PATH);
                case 3:
                    return (CIRCLE_PATH);
                case 4:
                    return (CIRCLE_EVEN_SPACING);
            };
            return (null);
        }

        public static  ParticleEmitterType BOX =new ParticleEmitterType();
        public static  ParticleEmitterType CIRCLE_EVEN_SPACING =new ParticleEmitterType();
        public static  ParticleEmitterType CIRCLE =new ParticleEmitterType();
        public static  ParticleEmitterType CIRCLE_PATH =new ParticleEmitterType();

        public static int  toUInt (ParticleEmitterType value ){
            if (value == CIRCLE)
            {
                return (0);
            };
            if (value == BOX)
            {
                return (1);
            };
            if (value == BOX_PATH)
            {
                return (2);
            };
            if (value == CIRCLE_PATH)
            {
                return (3);
            };
            if (value == CIRCLE_EVEN_SPACING)
            {
                return (4);
            };
            return -1;
            //throw (new Error("How did I get here?"));
        }

        public static  ParticleEmitterType BOX_PATH =new ParticleEmitterType();

        public static ParticleEmitterType  fromString (String str ){
            if(str.equals("Circle")) {
                    return (CIRCLE);
            } else if(str.equals("Box")) {
                    return (BOX);
            } else if(str.equals("BoxPath")) {
                    return (BOX_PATH);
            } else if(str.equals("CirclePath")) {
                    return (CIRCLE_PATH);
            } else if(str.equals("CircleEvenSpacing")) {
                    return (CIRCLE_EVEN_SPACING);
            } else {
                	return null;
                    ////throw (new ArgumentError((("Unknown ParticleEmitterType '" + str) + "'")));
            }
        }

        {
//            CEnum.InitEnumConstants(ParticleEmitterType);
        }
    }


