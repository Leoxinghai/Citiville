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
    public class ProModel extends Object
    {
        protected ProDefinition def ;
        protected Model owner ;
        protected ValueModel value ;
        protected boolean noneValue ;
        protected ValueModel defaultValue ;
        protected XML valueXML ;
        protected PropertySerializer valueSerializer ;
        public static  SimpleValue NONE_VALUE_SET =new SimpleValue(null );

        public  ProModel (ProDefinition param1 )
        {
            this.def = param1;
            this.noneValue = true;
            this.valueSerializer = param1.createPropertySerializer();
            return;
        }//end

        public ProDefinition  getDef ()
        {
            return this.def;
        }//end

        public void  valueChanged (ValueModel param1 )
        {
            if (param1 === NONE_VALUE_SET)
            {
                this.value = undefined;
                this.noneValue = true;
                this.owner.applyProperty(this.def.getName(), this.defaultValue, this.def.getAction());
            }
            else
            {
                this.value = param1;
                this.noneValue = false;
                this.owner.applyProperty(this.def.getName(), param1, this.def.getAction());
            }
            return;
        }//end

        public void  setNoneValue (boolean param1 )
        {
            if (param1 !=null)
            {
                this.value = undefined;
                this.noneValue = true;
            }
            else
            {
                if (this.value == null)
                {
                    this.value = this.captureDefaultProperty(this.def.getName(), true);
                }
                this.noneValue = false;
            }
            return;
        }//end

        public ValueModel  getValue ()
        {
            if (this.noneValue)
            {
                return NONE_VALUE_SET;
            }
            return this.value;
        }//end

        public boolean  isNoValueSet ()
        {
            return this.noneValue;
        }//end

        public void  bindTo (Model param1 ,boolean param2 )
        {
            this.owner = param1;
            this.defaultValue = this.captureDefaultProperty(this.def.getName());
            _loc_3 = this.def.getDefaultValue ();
            if (_loc_3 != null && param2)
            {
                this.valueChanged(this.valueSerializer.decodeValue(_loc_3, this));
            }
            return;
        }//end

        public void  parse (XML param1 )
        {
            this.valueChanged(this.valueSerializer.decodeValue(param1, this));
            return;
        }//end

        public XML  encodeXML ()
        {
            if (this.noneValue)
            {
                return null;
            }
            return this.valueSerializer.encodeValue(this.value, this);
        }//end

        public Model  getOwner ()
        {
            return this.owner;
        }//end

        protected ValueModel  captureDefaultProperty (String param1 ,boolean param2 =false )
        {
            Object o ;
            DefaultValueHelper helper ;
            name = param1;
            forceGet = param2;
            if (!forceGet && this.valueSerializer is DefaultValueHelper)
            {
                helper =(DefaultValueHelper) this.valueSerializer;
                if (helper.isNeedHelp(name, this.owner))
                {
                    return helper.getDefaultValue(name, this.owner);
                }
            }
            o = this.owner.getValue();
            v = undefined;
            try
            {
                v = FlyFishUtils.getValue(o, name);
            }
            catch (e:Error)
            {
                try
                {
                    v = FlyFishUtils.isValue(o, name);
                }
                catch (e:Error)
                {
                v=null;
                }
             }
              return new SimpleValue(v);
        }//end

        protected boolean  isNoneCodeNeed ()
        {
            ComModel _loc_1 =null ;
            if (this.getName() == ComModel.LOCATION_NAME || this.getName() == ComModel.SIZE_NAME)
            {
                if (this.owner && this.owner is ComModel)
                {
                    _loc_1 =(ComModel) this.owner;
                    if (!_loc_1.isInEmptyLayout())
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public Array  getCodeLines ()
        {
            if (this.noneValue || this.isNoneCodeNeed())
            {
                return null;
            }
            return this.valueSerializer.getCodeLines(this.value, this);
        }//end

        public String  isSimpleOneLine ()
        {
            if (this.noneValue || this.isNoneCodeNeed())
            {
                return null;
            }
            return this.valueSerializer.isSimpleOneLine(this.value, this);
        }//end

        public String  getLabel ()
        {
            return this.def.getLabel();
        }//end

        public String  getName ()
        {
            return this.def.getName();
        }//end

    }


