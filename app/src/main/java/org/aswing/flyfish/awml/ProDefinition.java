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

import org.aswing.flyfish.util.*;
    public class ProDefinition extends Object
    {
        private XML xml ;
        private String name ;
        private String label ;
        private String type ;
        private String action ;
        private XML defaultValue ;
        private String editorParam ;
        private String tooltip ;
        private String category ;
        private int order ;

        public  ProDefinition (XML param1 )
        {
            this.xml = param1;
            this.label = param1.@label;
            this.name = param1.@name;
            this.type = param1.@type;
            this.action = param1.@action;
            this.editorParam = param1.@editorParam;
            _loc_2 = param1.Value.get(0) ;
            if (_loc_2)
            {
                this.defaultValue = _loc_2.@value.get(0);
            }
            this.tooltip = param1.@tooltip;
            this.category = param1.@category;
            this.order = MathUtils.parseInteger(param1.@order);
            if (this.editorParam == "")
            {
                this.editorParam = null;
            }
            if (this.tooltip == "")
            {
                this.tooltip = null;
            }
            if (this.category == "")
            {
                this.category = null;
            }
            return;
        }//end

        public String  getName ()
        {
            return this.name;
        }//end

        public String  getLabel ()
        {
            return this.label;
        }//end

        public XML  getDefaultValue ()
        {
            return this.defaultValue;
        }//end

        public String  getAction ()
        {
            return this.action;
        }//end

        public String  getEditorParam ()
        {
            return this.editorParam;
        }//end

        public String  getTooltip ()
        {
            return this.tooltip;
        }//end

        public String  getCategory ()
        {
            return this.category;
        }//end

        public int  getOrder ()
        {
            return this.order;
        }//end

        public PropertySerializer  createPropertySerializer ()
        {
            _loc_1 = Definition.getIns().getProTypeDefinition(this.type).getSerializerClass();
            return new (PropertySerializer)_loc_1;
        }//end

        public PropertyEditor  createPropertyEditor ()
        {
            _loc_1 = Definition.getIns().getProTypeDefinition(this.type).getEditorClass();
            _loc_2 =(PropertyEditor) new _loc_1;
            _loc_2.setSerializer(this.createPropertySerializer());
            return _loc_2;
        }//end

    }


