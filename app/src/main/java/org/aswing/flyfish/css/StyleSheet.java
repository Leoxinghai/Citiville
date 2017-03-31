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

import org.aswing.*;
import org.aswing.util.*;

    public class StyleSheet extends Object implements IStyleSheet
    {
        protected ISelector selector ;
        protected HashMap styles ;

        public  StyleSheet (ISelector param1 )
        {
            this.selector = param1;
            this.styles = new HashMap();
            return;
        }//end

        public void  combine (IStyleSheet param1 )
        {
            this.putStyles(param1.getStyles());
            return;
        }//end

        public IStyleSheet  clone ()
        {
            _loc_1 = new StyleSheet(this.selector );
            _loc_1.styles = this.styles.clone();
            return _loc_1;
        }//end

        public Array  getStyles ()
        {
            return this.styles.values();
        }//end

        public void  putStyle (IStyle param1 )
        {
            StyleDefinition _loc_2 =null ;
            IStyle _loc_3 =null ;
            if (param1.definition.isMember())
            {
                _loc_2 = param1.definition.getHolder();
                _loc_3 = this.styles.getValue(_loc_2.getName());
                if (_loc_3 == null)
                {
                    _loc_3 = new Style(_loc_2.getName(), null, _loc_2);
                    this.styles.put(_loc_3.name, _loc_3);
                }
                _loc_3.putMember(param1);
            }
            else
            {
                this.styles.put(param1.name, param1);
            }
            return;
        }//end

        public void  putStyles (Array param1 )
        {
            IStyle _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                this.putStyle(_loc_2);
            }
            return;
        }//end

        public LazyLoadRequestList  apply (Component param1 )
        {
            LazyLoadRequestList _loc_2 =null ;
            Array _loc_3 =null ;
            IStyle _loc_4 =null ;
            if (this.selector.isSelected(param1))
            {
                _loc_2 = new LazyLoadRequestList();
                _loc_3 = this.styles.values();
                for(int i0 = 0; i0 < _loc_3.size(); i0++) 
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_2.addRequest(_loc_4.apply(param1));
                }
                return _loc_2;
            }
            return null;
        }//end

        public int  getWeight ()
        {
            return this.selector.getWeight();
        }//end

        public ISelector  getSelector ()
        {
            return this.selector;
        }//end

    }


