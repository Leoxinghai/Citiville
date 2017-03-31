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

import java.util.Vector;


public class GenericValidationScript
    {
        protected String m_name ;
        protected GenericValidationConditionSet m_conditionSet ;
        private Vector<GenericValidationCondition> m_vector;

        public  GenericValidationScript (String param1 )
        {
            this.m_vector = new Vector<GenericValidationCondition>();
            this.m_name = param1;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public void  conditionSet (GenericValidationConditionSet param1 )
        {
            this.m_conditionSet = param1;
            return;
        }//end

        public boolean  hasValidationUtilType (String param1 )
        {
            return this.m_conditionSet.hasValidationUtilType(param1);
        }//end

        public Vector<GenericValidationCondition> getValidationConditionsByType (String param1 )
        {
            this.m_vector.clear();
            this.m_conditionSet.getValidationConditionsByType(param1, this.m_vector);
            return this.m_vector.size() > 0 ? (this.m_vector) : (null);
        }//end

        public boolean  validate (Object param1)
        {
            return this.m_conditionSet.evaluate(param1);
        }//end

    }



