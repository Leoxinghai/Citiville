package validation;

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


    public class GenericValidationCondition implements IGenericValidationCondition
    {
        protected Function m_callback ;
        protected Array m_arguments ;
        protected boolean m_serverOnly =false ;
        protected String m_className ;
        protected String m_funcName ;

        public  GenericValidationCondition (String param1 ,String param2 ,...args )
        {
            argsvalue = null;
            this.m_className = param1;
            this.m_funcName = param2;
            this.m_callback = Global.validationManager.getValidationFunction(param1, param2);
            this.m_arguments = args;
            if (this.m_arguments.length > 0)
            {
                for(int i0 = 0; i0 < this.m_arguments.size(); i0++)
                {
                		argsvalue = this.m_arguments.get(i0);

                    if (argsvalue.serverOnly == "true")
                    {
                        this.m_serverOnly = true;
                    }
                }
            }
            return;
        }//end

        public boolean  hasValidationUtilType (String param1 )
        {
            return param1 == this.m_className;
        }//end

        public void  getValidationConditionsByType (String param1 ,Vector param2 .<GenericValidationCondition >)
        {
            if (param1 == this.m_className)
            {
                param2.push(this);
            }
            return;
        }//end

        public String  getArgument (String param1 )
        {
            if (this.m_arguments.length > 0)
            {
                return (String)this.m_arguments.get(0).get(param1);
            }
            return null;
        }//end

        public boolean  evaluate (Object param1)
        {
            boolean _loc_2 =false ;
            if (this.m_serverOnly)
            {
                _loc_2 = true;
            }
            if (!this.m_serverOnly && this.m_callback != null)
            {
                _loc_2 = this.m_callback.apply(param1, this.m_arguments);
            }
            return _loc_2;
        }//end

        public String  className ()
        {
            return this.m_className;
        }//end

        public String  funcName ()
        {
            return this.m_funcName;
        }//end

    }



