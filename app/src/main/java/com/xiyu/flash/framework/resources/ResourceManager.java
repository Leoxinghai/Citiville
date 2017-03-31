package com.xiyu.flash.framework.resources;
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

import com.xiyu.flash.framework.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;
import com.xinghai.debug.*;

    public class ResourceManager
    {
        private boolean mIsLoading ;
        private static ResourceLibrary mLibrary ;
		private static ResourceLibrary mLibrary2 ;

        private AppBase mApp ;
        private static Dictionary mResources ;

        public  ResourceManager (AppBase p1 )
        {
            this.mApp = p1;
            this.init();
            return;
        }//end

        public Object  getResource (String p1 )
        {
	        String _loc_2;
	        Object _loc_3 = null;

            if (this.mLibrary == null)
            {
            		return this.mResources.elementAt(p1);
            }


            if(this.mResources.elementAt(p1)!= null)
            {
				return this.mResources.elementAt(p1);
            }


            return _loc_3;

        }//end

        public boolean  isLoading ()
        {
            return this.mIsLoading;
        }//end

        public void  loadResourceLibrary (String p )
        {
            return;
        }//end

        private void  init ()
        {
            this.mResources = new Dictionary();
            this.mIsLoading = false;
            return;
        }//end


        public void  setResource (String p1 ,Object p2 )
        {
        	this.mResources.put(p1, p2);
        	return;
        }//end

		public void  setLibrary2 (ResourceLibrary p1 )
		{
			this.mLibrary2 = p1;
			return;
		}//end

    }

