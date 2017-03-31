package org.aswing.flyfish.awml;

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

import org.aswing.util.*;
    public class CSSHeader extends Object
    {
        private Array links ;
        public Function headerChangedHandler ;
        public static  String TAG ="css";
        public static  String EXTENSION ="css";

        public  CSSHeader ()
        {
            this.links = new Array();
            return;
        }//end

        public Array  getLinks ()
        {
            return this.links.concat();
        }//end

        public void  contentChanged ()
        {
            this.headerChangedHandler(false);
            return;
        }//end

        public void  setLinks (Array param1 )
        {
            this.links = param1.concat();
            this.headerChangedHandler(true);
            return;
        }//end

        public void  addLink (String param1 )
        {
            this.links.push(param1);
            this.headerChangedHandler(true);
            return;
        }//end

        public boolean  containsLink (String param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.links.size(); i0++)
            {
            		_loc_2 = this.links.get(i0);

                if (param1 == _loc_2)
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  removeLink (String param1 )
        {
            ArrayUtils.removeFromArray(this.links, param1);
            this.headerChangedHandler(true);
            return;
        }//end

        public XML  encode ()
        {
            String _loc_2 =null ;
            XML _loc_1 =new XML("<css></css>");
            for(int i0 = 0; i0 < this.links.size(); i0++)
            {
            		_loc_2 = this.links.get(i0);

                _loc_1.appendChild(new XML("<link>" + _loc_2 + "</link>"));
            }
            return _loc_1;
        }//end

        public void  decode (XML param1 )
        {
            XMLList _loc_2 =null ;
            String _loc_3 =null ;
            this.links = new Array();
            if (param1 !=null)
            {
                _loc_2 = param1.link;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    this.links.push(_loc_3);
                }
            }
            return;
        }//end

    }


