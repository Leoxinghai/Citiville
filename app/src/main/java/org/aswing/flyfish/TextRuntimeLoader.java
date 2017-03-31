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

import org.aswing.flyfish.css.*;
import org.aswing.util.*;

    public class TextRuntimeLoader extends Object
    {
        private HashMap cssCacheMap ;

        public  TextRuntimeLoader ()
        {
            this.cssCacheMap = new HashMap();
            return;
        }//end

        public void  clearCache ()
        {
            this.cssCacheMap.clear();
            return;
        }//end

        public StyleSheetList  getCache (String param1 )
        {
            return this.cssCacheMap.getValue(param1);
        }//end

        public void  loadFiles (Array param1 ,Function param2 )
        {
            new QueueLoader(this, param1, param2);
            return;
        }//end

    }

import flash.events.*;
import org.aswing.flyfish.*;
import flash.display.*;
import flash.events.*;
import flash.net.*;
import flash.system.*;
import flash.utils.*;


class QueueLoader extends Object
    private TextRuntimeLoader owner ;
    private Array paths ;
    private Function callback ;
    private int index ;
    private Array values ;
    private URLLoader loader ;

     QueueLoader (TextRuntimeLoader param1 ,Array param2 ,Function param3 )
    {
        this.owner = param1;
        this.paths = param2.concat();
        this.callback = param3;
        this.index = -1;
        this.values = new Array();
        this.loadNext();
        return;
    }//end

    private void  loadNext ()
    {

        this.index++;

        if (this.index >= this.paths.length())
        {
            this.callback(this.values);
            return;
        }
        _loc_1 = this.paths.get(this.index) ;
        _loc_2 = this.owner.getCache(_loc_1 );
        if (_loc_2)
        {
            this.values.push(_loc_2);
            this.loadNext();
        }
        else
        {
            this.loader = new URLLoader();
            this.loader.dataFormat = URLLoaderDataFormat.TEXT;
            this.loader.addEventListener(Event.COMPLETE, this.__complete);
            this.loader.addEventListener(IOErrorEvent.IO_ERROR, this.__ioerror);
            this.loader.load(new URLRequest(_loc_1));
        }
        return;
    }//end

    protected void  __ioerror (IOErrorEvent event )
    {
        this.callback(null);
        return;
    }//end

    protected void  __complete (Event event )
    {
        _loc_2 = this.loader.data ;
        this.values.push(_loc_2);
        this.loadNext();
        return;
    }//end

}



