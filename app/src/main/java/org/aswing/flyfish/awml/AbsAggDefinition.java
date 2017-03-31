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

import org.aswing.flyfish.*;
import org.aswing.util.*;

    public class AbsAggDefinition extends Object
    {
        public String sortValue ;
        protected Object xml ;
        protected String name ;
        protected String className ;
        protected String shortClassName ;
        protected Class clazz ;
        protected AbsAggDefinition superDef ;
        protected ArrayList properties ;

        public  AbsAggDefinition (Object param1 ,AbsAggDefinition param2 )
        {
            _loc_5 = null;
            this.xml = param1;
            this.superDef = param2;
            this.name = param1.@name;
            this.sortValue = this.name;
            this.className = param1.@clazz;
            this.clazz = ResourceManager.ins.getClass(this.className);
            _loc_3 = this.className.split(".");
            this.shortClassName = _loc_3.get((_loc_3.length - 1));
            this.properties = new ArrayList();
            if (param2)
            {
                this.properties.appendAll(param2.getProperties());
            }
            _loc_4 = param1.Property ;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                this.addProDef(new ProDefinition(_loc_5));
            }
            return;
        }//end

        private void  addProDef (ProDefinition param1 )
        {
            ProDefinition _loc_3 =null ;
            _loc_2 = this.properties.size ()-1;
            while (_loc_2 >= 0)
            {

                _loc_3 = this.properties.get(_loc_2);
                if (_loc_3.getName() == param1.getName())
                {
                    this.properties.setElementAt(_loc_2, param1);
                    return;
                }
                _loc_2 = _loc_2 - 1;
            }
            this.properties.append(param1);
            return;
        }//end

        public String  getName ()
        {
            return this.name;
        }//end

        public String  getClassName ()
        {
            return this.className;
        }//end

        public String  getShortClassName ()
        {
            return this.shortClassName;
        }//end

        public AbsAggDefinition  getSuperDefinition ()
        {
            return this.superDef;
        }//end

        public Class  getClass ()
        {
            return this.clazz;
        }//end

        public Array  getProperties ()
        {
            return this.properties.toArray();
        }//end

    }


