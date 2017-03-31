package Classes;

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

import mx.core.*;
//import flash.display.*;

    public class EmbeddedArt_build_onstate_btn extends BitmapAsset
    {

        public  EmbeddedArt_build_onstate_btn ()
        {
            temp = Global.uiinit.m_cache.get( "ssets/hud/tool_build2.png") ;
            if(temp == null) return;
            super.bitmapData = ((Bitmap)temp).bitmapData;
            return;
        }//end

    }


