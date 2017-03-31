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

import org.aswing.*;
import org.aswing.flyfish.awml.*;

    public class FlyFishPane extends Object
    {
        protected XML awml ;
        protected FileModel model ;

        public  FlyFishPane (XML param1 )
        {
            this.awml = param1;
            this.model = FileModel.parseXML(param1);
            return;
        }//end

        public Object getComponent (String param1 )
        {
            return this.search(this.model.getRootComponent(), param1);
        }//end

        public Component  getRoot ()
        {
            return this.model.getRootComponent().getDisplay();
        }//end

        public FileModel  getModel ()
        {
            return this.model;
        }//end

        private Object search (ComModel param1 ,String param2 )
        {
            ComModel _loc_5 =null ;
            if (param1.getID() == param2)
            {
                return param1.getDisplay();
            }
            _loc_3 = null;
            _loc_4 = param1.getChildren ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_3 = this.search(_loc_5, param2);
                if (_loc_3)
                {
                    return _loc_3;
                }
            }
            return null;
        }//end

    }


