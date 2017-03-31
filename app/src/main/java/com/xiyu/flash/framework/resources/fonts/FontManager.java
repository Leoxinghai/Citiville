package com.xiyu.flash.framework.resources.fonts;
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

import com.xiyu.flash.framework.AppBase;
//import flash.utils.Dictionary;

    public class FontManager {

        public void  addDescriptor (String id ,FontDescriptor fontDesc ){
            this.mApp.resourceManager().setResource(id, fontDesc);
        }

        private AppBase mApp ;
        private Dictionary mFontDataMap ;

        public FontInst  getFontInst (String id ){
            FontData data = new FontData();
            FontInst anInst =new FontInst(data );
            return anInst;
        	/*
            Object obj =this.mApp.resourceManager().getResource(id );
            FontDescriptor desc =(FontDescriptor)obj;
            if (desc != null)
            {
                obj = desc.createFontData(this.mApp);
                this.mApp.resourceManager().setResource(id, obj);
            };
            FontData data =(FontData)obj;
            if (data == null)
            {
                //throw (new Error((("Font '" + id) + "' instanceof not loaded.")));
            };
            FontInst anInst =new FontInst(data );
            return (anInst);
            */
        }

        private Dictionary mFontTypeMap ;

        public  FontManager (AppBase app ){
            this.mApp = app;
            this.mFontTypeMap = new Dictionary();
            this.mFontDataMap = new Dictionary();
        }
    }


