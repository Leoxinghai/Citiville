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

//import flash.events.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.event.*;
import org.aswing.util.*;

    public class LazyLoadRequestList extends EventDispatcher
    {
        private ArrayList list ;
        private int count ;
        private boolean started =false ;

        public  LazyLoadRequestList ()
        {
            this.list = new ArrayList();
            return;
        }//end

        public void  startLoad (ILazyLoadManager param1 )
        {
            LazyLoadRequest _loc_3 =null ;
            if (this.started)
            {
                throw new Error("The LazyLoadRequestList has already started!");
            }
            this.started = true;
            this.count = 0;
            _loc_2 = this.list.toArray ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_3.addEventListener(LazyLoadEvent.LOAD_COMPLETE, this.__complete);
                _loc_3.addEventListener(LazyLoadEvent.LOAD_ERROR, this.__error);
                _loc_3.startLoad(param1);
            }
            return;
        }//end

        public Array  getRequests ()
        {
            return this.list.toArray();
        }//end

        public boolean  isFinish ()
        {
            return this.started && this.list.size() <= this.count;
        }//end

        public void  addRequest (LazyLoadRequest param1 )
        {
            if (param1 !=null)
            {
                this.list.append(param1);
            }
            return;
        }//end

        public void  combine (LazyLoadRequestList param1 )
        {
            if (param1 == null)
            {
                return;
            }
            if (!param1.isEmpty())
            {
                this.list.appendList(param1.list);
            }
            return;
        }//end

        public boolean  isEmpty ()
        {
            return this.list.isEmpty();
        }//end

        protected void  __error (LazyLoadEvent event )
        {
            dispatchEvent(event.clone());
            AGLog.warn("Can not load " + event.request.getFile());
            this.countLoad();
            return;
        }//end

        protected void  __complete (LazyLoadEvent event )
        {
            dispatchEvent(event.clone());
            this.countLoad();
            return;
        }//end

        private void  countLoad ()
        {

            this.count++;

            if (this.count == this.list.size())
            {
                dispatchEvent(new LazyLoadEvent(LazyLoadEvent.LOAD_LIST_FINISH, null));
            }
            return;
        }//end

    }


