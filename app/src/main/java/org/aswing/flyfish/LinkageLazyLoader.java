package org.aswing.flyfish;

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
//import flash.system.*;
import org.aswing.flyfish.css.*;
import org.aswing.flyfish.util.*;
import org.aswing.IAssetLazyLoader;

    public class LinkageLazyLoader extends Shape implements IAssetLazyLoader
    {
        private String swf ;
        private String linkage ;
        private DisplayObject symbol ;

        public  LinkageLazyLoader (String param1 ,String param2 )
        {
            this.swf = param1;
            this.linkage = param2;
            return;
        }//end

        public void  startLoad ()
        {
            ResourceManager.ins.getWorkspace().lazyLoadManager().loadSwf(this.swf, this.__loaded);
            return;
        }//end

        public DisplayObject  content ()
        {
            return this.symbol;
        }//end

        public void  unload ()
        {
            this.symbol = null;
            return;
        }//end

        public void  addCompleteEventListener (Function param1 )
        {
            addEventListener(Event.COMPLETE, param1);
            return;
        }//end

        public void  addIOErrorEventListener (Function param1 )
        {
            addEventListener(IOErrorEvent.IO_ERROR, param1);
            return;
        }//end

        private void  __loaded (ApplicationDomain param1 )
        {
            if (param1 !=null)
            {
                this.symbol = LazyLoadData.getAssetInDomain(param1, this.linkage);
                if (this.symbol == null)
                {
                    this.symbol = DisplayUtils.createNoneAssetShape();
                }
                dispatchEvent(new Event(Event.COMPLETE));
            }
            else
            {
                dispatchEvent(new IOErrorEvent(IOErrorEvent.IO_ERROR, false, false, "load error : " + this.swf));
            }
            return;
        }//end

    }


