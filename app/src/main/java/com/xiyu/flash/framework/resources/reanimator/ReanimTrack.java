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

    public class ReanimTrack {
        public Array transforms ;
        public String name ;
        public int numTransforms ;

        public  ReanimTrack (){
            this.name = "";
            this.transforms = new Array();
            this.numTransforms = 0;
        }
        
        public ReanimTrack clone() {
        	ReanimTrack tmp = new ReanimTrack();
        	
        	for(int i = 0; i< this.transforms.length();i++) {
        		tmp.transforms.add(i, ((ReanimTransform)this.transforms.elementAt(i)).clone());
        	}
        	
        	tmp.name = this.name;
        	tmp.numTransforms = this.numTransforms;
        	return tmp;
        }
    }


