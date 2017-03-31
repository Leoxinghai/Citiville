package scripting;

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

import validation.*;
    public class Script
    {
        protected ConditionSet m_conditions ;
        protected IScriptingContext m_context ;

        public  Script (IScriptingContext param1 )
        {
            this.m_context = param1;
            this.m_conditions = new ConditionSet();
            return;
        }//end

        public boolean  validates ()
        {
            if (this.m_conditions.evaluate())
            {
                this.m_context.onValidate();
                return true;
            }
            return false;
        }//end

        public void  addCondition (String param1 ,Object param2 )
        {
            _loc_3 =Global.scriptingManager.getConditionFunction(param1 ,this.m_context );
            if (_loc_3 != null)
            {
                this.m_conditions.add(new Condition(_loc_3, param2));
            }
            return;
        }//end

        public void  addValidator (String param1 )
        {
            validatorName = param1;
            func = function(param1Object)
            {
                _loc_2 =Global.validationManager.getValidator(validatorName );
                if (_loc_2 == null)
                {
                    return false;
                }
                return _loc_2.validate();
            }//end
            ;
            this.m_conditions.add(new Condition(func));
            return;
        }//end

    }



