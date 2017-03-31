package Engine.Init;

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

import com.xiyu.logic.EventDispatcher;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;
import com.xiyu.util.Event;

//import flash.events.*;
    public class InitializationAction extends EventDispatcher
    {
        protected String _id ;
        protected Array _dependencies ;
        public int startTime ;

        public InitializationAction (String param1 )
        {
            this._id = param1;
            this._dependencies = new Array();
            return;
        }//end

        public void  addDependency (String param1 )
        {
            if (this._dependencies.indexOf(param1) < 0)
            {
                this._dependencies.push(param1);
            }
            return;
        }//end

        public Array  getDependencies ()
        {
            return this._dependencies;
        }//end

        public String  getActionId ()
        {
            return this._id;
        }//end

        public boolean  isReady ()
        {
            boolean _loc_1 =true ;
            int _loc_2 =0;
            while (_loc_2 < this._dependencies.length())
            {

                if (!InitializationManager.getInstance().hasActionCompleted((String)this._dependencies.get(_loc_2)))
                {
                    _loc_1 = false;
                    break;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        public boolean  hasCompleted ()
        {
            return InitializationManager.getInstance().hasActionCompleted(this._id);
        }//end

        public void  execute ()
        {
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



