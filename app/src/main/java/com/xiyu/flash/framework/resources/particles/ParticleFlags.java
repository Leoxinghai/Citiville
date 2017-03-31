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

    public class ParticleFlags {
        public static final int SOFTWARE_ONLY =0x0400 ;
        public static final int RANDOM_LAUNCH_SPIN =1;
        public static final int ADDITIVE =0x0100 ;
        public static final int DIE_IF_OVERLOADED =128;
        public static final int SYSTEM_LOOPS =8;
        public static final int HARDWARE_ONLY =0x0800 ;
        public static final int RANDOM_START_TIME =64;
        public static final int PARTICLES_DONT_FOLLOW =32;
        public static final int ALIGN_LAUNCH_SPIN =2;
        public static final int PARTICLE_LOOPS =16;
        public static final int FULLSCREEN =0x0200 ;
        public static final int ALIGN_TO_PIXEL =4;

        public ParticleFlags(String str) {
        	this.mFlags = fromString(str);
        }

        public ParticleFlags() {
        }
        
        public static int  fromString (String str ){
            if (str.equals("RandomLaunchSpin"))
            {
                    return (RANDOM_LAUNCH_SPIN);
            } else if (str.equals("AlignLaunchSpin")) {
                    return (ALIGN_LAUNCH_SPIN);
            } else if (str.equals("AlignToPixel")) {
                    return (ALIGN_TO_PIXEL);
            } else if (str.equals("SystemLoops")) {
                    return (SYSTEM_LOOPS);
            } else if (str.equals("ParticleLoops")) {
                    return (PARTICLE_LOOPS);
            } else if (str.equals("ParticlesDontFollow")) {
                    return (PARTICLES_DONT_FOLLOW);
            } else if (str.equals("RandomStartTime")) {
                    return (RANDOM_START_TIME);
            } else if (str.equals("DieIfOverloaded")) {
                    return (DIE_IF_OVERLOADED);
            } else if (str.equals("Additive")) {
                    return (ADDITIVE);
            } else if (str.equals("FullScreen")) {
                    return (FULLSCREEN);
            } else if (str.equals("SoftwareOnly")) {
                    return (SOFTWARE_ONLY);
            } else if (str.equals("HardwareOnly")) {
                    return (HARDWARE_ONLY);
            }
            return -1;
                    //throw (new ArgumentError((("Unknown ParticleFlags type '" + str) + "'")));
        }


        private int mFlags =0;

        public void  setFlags (int flags ){
            this.mFlags = (this.mFlags | flags);
        }
        public void  clearFlags (int flags ){
            this.mFlags = (this.mFlags & ~(flags));
        }
        public void  fromUInt (int value ){
            this.mFlags = value;
        }
        public int  toUInt (){
            return (this.mFlags);
        }
        public boolean  hasFlags (int testFlags ){
            return (!(((this.mFlags & testFlags) == 0)));
        }
        public String  toString (){
            String str =("["+this.mFlags );
            if (this.hasFlags(RANDOM_LAUNCH_SPIN))
            {
                str = (str + "|RANDOM_LAUNCH_SPIN");
            };
            if (this.hasFlags(ALIGN_LAUNCH_SPIN))
            {
                str = (str + "|ALIGN_LAUNCH_SPING");
            };
            if (this.hasFlags(SYSTEM_LOOPS))
            {
                str = (str + "|SYSTEM_LOOPS");
            };
            if (this.hasFlags(PARTICLE_LOOPS))
            {
                str = (str + "|PARTICLE_LOOPS");
            };
            if (this.hasFlags(PARTICLES_DONT_FOLLOW))
            {
                str = (str + "|PARTICLES_DONT_FOLLOW");
            };
            if (this.hasFlags(RANDOM_START_TIME))
            {
                str = (str + "|RANDOM_START_TIME");
            };
            if (this.hasFlags(DIE_IF_OVERLOADED))
            {
                str = (str + "|DIE_IF_OVERLOADED");
            };
            if (this.hasFlags(ADDITIVE))
            {
                str = (str + "|ADDITIVE");
            };
            if (this.hasFlags(FULLSCREEN))
            {
                str = (str + "|FULLSCREEN");
            };
            if (this.hasFlags(SOFTWARE_ONLY))
            {
                str = (str + "|SOFTWARE_ONLY");
            };
            if (this.hasFlags(HARDWARE_ONLY))
            {
                str = (str + "|HARDWARE_ONLY");
            };
            str = (str + "]");
            return (str);
        }

    }


