package com.xiyu.flash.framework.resources.reanimator;
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

import com.xinghai.debug.Debug;

    public class ReanimFrameTime {

        public int mFraction =0;
		public boolean mHold =false ;

        public void  update (double animTime ,int startFrame ,int frameCount ,boolean hold){
            int aFrameCount =frameCount ;
            int aAnimPosition =(int)(startFrame +(aFrameCount *animTime ));
            int aAnimFrameBefore =aAnimPosition;
            int aLastFrame =((startFrame +frameCount )-1);
            this.mFraction = (aAnimPosition - aAnimFrameBefore);
            this.mFrameBefore = aAnimFrameBefore;
            this.mFrameAfter = (this.mFrameBefore + 1);
            
            if (((hold) && ((animTime == 1))) || mHold)
            {
                this.mFrameBefore = aLastFrame;
                this.mFrameAfter = aLastFrame;
            };
        }

		public void  holdFrame (int iframe ){
			this.mHold = true;
			this.mFrameBefore = iframe;
			this.mFrameAfter = iframe;
		}

        public int mFrameAfter =0;
        public int mFrameBefore =0;

    }


