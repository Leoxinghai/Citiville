package org.aswing.flyfish.css;

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

import org.aswing.flyfish.css.property.*;
    public class StyleDefinition extends Object
    {
        private String name ;
        private ValueDecoder decoder ;
        private ValueInjector injector ;
        private StyleDefinition holder ;
        private Array members ;

        public  StyleDefinition (String param1 ,ValueDecoder param2 ,ValueInjector param3 )
        {
            this.name = param1;
            this.decoder = param2;
            this.injector = param3;
            this.members = new Array();
            return;
        }//end

        public void  addMember (StyleDefinition param1 )
        {
            this.members.push(param1);
            return;
        }//end

        public void  setHolder (StyleDefinition param1 )
        {
            this.holder = param1;
            return;
        }//end

        public String  getName ()
        {
            return this.name;
        }//end

        public ValueDecoder  getDecoder ()
        {
            return this.decoder;
        }//end

        public ValueInjector  getInjector ()
        {
            return this.injector;
        }//end

        public boolean  isAggregation ()
        {
            return this.members.length > 0;
        }//end

        public boolean  isMember ()
        {
            return this.holder != null;
        }//end

        public StyleDefinition  getHolder ()
        {
            return this.holder;
        }//end

        public Array  getMembers ()
        {
            return this.members;
        }//end

    }


