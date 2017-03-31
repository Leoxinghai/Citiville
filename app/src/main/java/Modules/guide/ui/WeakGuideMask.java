package Modules.guide.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.xiyu.util.Dictionary;

import Modules.guide.*;
//import flash.events.*;

    public class WeakGuideMask extends GuideMask
    {
        protected Guide m_guide ;
        protected GuideSequence m_activeSequence ;

        public  WeakGuideMask (Guide param1 )
        {
            this.m_guide = param1;
            this.m_activeSequence = param1.getActiveSequence();
            return;
        }//end  

         protected void  onMaskClicked (MouseEvent event )
        {
            event.stopPropagation();
            this.m_activeSequence.stop(true);
            return;
        }//end  

    }



