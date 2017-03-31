package org.aswing;

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

//import flash.display.*;
//import flash.events.*;

    public class AssetLoader extends Loader implements IAssetLazyLoader
    {

        public  AssetLoader ()
        {
            return;
        }//end  

        public void  addCompleteEventListener (Function param1 )
        {
            contentLoaderInfo.addEventListener(Event.COMPLETE, param1);
            return;
        }//end  

        public void  addIOErrorEventListener (Function param1 )
        {
            contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, param1);
            return;
        }//end  

    }


