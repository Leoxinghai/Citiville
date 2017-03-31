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
    public class ParticleFieldType extends CEnum {

        public static  ParticleFieldType ACCELERATION =new ParticleFieldType();
        public static  ParticleFieldType FRICTION =new ParticleFieldType();
        public static  ParticleFieldType ATTRACTOR =new ParticleFieldType();

        public static ParticleFieldType  fromUInt (int value ){
            switch (value)
            {
                case 0:
                    return (INVALID);
                case 1:
                    return (FRICTION);
                case 2:
                    return (ACCELERATION);
                case 3:
                    return (ATTRACTOR);
                case 4:
                    return (MAX_VELOCITY);
                case 5:
                    return (VELOCITY);
                case 6:
                    return (POSITION);
                case 7:
                    return (SYSTEM_POSITION);
                case 8:
                    return (GROUND_CONSTRAINT);
                case 9:
                    return (SHAKE);
                case 10:
                    return (CIRCLE);
                case 11:
                    return (AWAY);
            };
            return (null);
        }

        public static  ParticleFieldType CIRCLE =new ParticleFieldType ();
        public static  ParticleFieldType GROUND_CONSTRAINT =new ParticleFieldType();
        public static  ParticleFieldType INVALID =new ParticleFieldType();

        public static ParticleFieldType  fromString (String str ){
            if(str.equals("Friction")) {
                    return (FRICTION);
            } else if(str.equals("Acceleration")) {
                    return (ACCELERATION);
            } else if(str.equals("Attractor")) {
                    return (ATTRACTOR);
            } else if(str.equals("MaxVelocity")) {
                    return (MAX_VELOCITY);
            } else if(str.equals("Velocity")) {
                    return (VELOCITY);
            } else if(str.equals("Position")) {
                    return (POSITION);
            } else if(str.equals("SystemPosition")) {
                    return (SYSTEM_POSITION);
            } else if(str.equals("GroundConstraint")) {
                    return (GROUND_CONSTRAINT);
            } else if(str.equals("Shake")) {
                    return (SHAKE);
            } else if(str.equals("Circle")) {
                    return (CIRCLE);
            } else if(str.equals("Away")) {
                    return (AWAY);
            } else {
            	return null;
                    //throw (new ArgumentError(("Unknown ParticleFieldType " + str)));
            }
        }

        public static  ParticleFieldType VELOCITY =new ParticleFieldType();

        public static int  toUInt (ParticleFieldType value ){
            if (value == INVALID)
            {
                return (0);
            };
            if (value == FRICTION)
            {
                return (1);
            };
            if (value == ACCELERATION)
            {
                return (2);
            };
            if (value == ATTRACTOR)
            {
                return (3);
            };
            if (value == MAX_VELOCITY)
            {
                return (4);
            };
            if (value == VELOCITY)
            {
                return (5);
            };
            if (value == POSITION)
            {
                return (6);
            };
            if (value == SYSTEM_POSITION)
            {
                return (7);
            };
            if (value == GROUND_CONSTRAINT)
            {
                return (8);
            };
            if (value == SHAKE)
            {
                return (9);
            };
            if (value == CIRCLE)
            {
                return (10);
            };
            if (value == AWAY)
            {
                return (11);
            };
            return -1;
            //throw (new Error("How did I get here?"));
        }

        public static  ParticleFieldType SHAKE =new ParticleFieldType();
        public static  ParticleFieldType POSITION =new ParticleFieldType();
        public static  ParticleFieldType AWAY =new ParticleFieldType();
        public static  ParticleFieldType MAX_VELOCITY =new ParticleFieldType();
        public static  ParticleFieldType SYSTEM_POSITION =new ParticleFieldType();

        public String  toString (){
            return (name);
        }

        {
//            CEnum.InitEnumConstants(ParticleFieldType);
        }
    }


