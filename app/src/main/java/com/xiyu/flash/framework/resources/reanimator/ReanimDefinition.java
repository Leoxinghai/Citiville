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

//import flash.utils.Dictionary;
    public class ReanimDefinition {

        public Dictionary trackNameMap ;
        public Array tracks ;
        public int fps ;
        public int numTracks ;

        public  ReanimDefinition (){
            this.fps = 0;
            this.tracks = new Array();
            this.trackNameMap = new Dictionary();
            this.numTracks = 0;
        }
        public ReanimDefinition clone() {
        	ReanimDefinition def = new ReanimDefinition();
        	//def.trackNameMap = this.trackNameMap;
        	def.trackNameMap = this.trackNameMap.clone();
//        	def.tracks = this.tracks.clone();
        	for(int i=0; i<tracks.length();i++) {
        		ReanimTrack track = (ReanimTrack)this.tracks.elementAt(i);
        		def.tracks.add(i,track.clone());
        	}
        	def.fps = this.fps;
        	def.numTracks = this.numTracks;
        	return def;
        }
    }


