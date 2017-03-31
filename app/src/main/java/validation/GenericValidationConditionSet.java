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


    public class GenericValidationConditionSet implements IGenericValidationCondition
    {
        protected int m_style ;
        protected Array m_conditions ;
        public static  int EVALUATE_AND =0;
        public static  int EVALUATE_OR =1;

        public  GenericValidationConditionSet (int param1 =0)
        {
            this.m_conditions = new Array();
            this.m_style = param1;
            return;
        }//end

        public boolean  hasValidationUtilType (String param1 )
        {
            IGenericValidationCondition _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_conditions.size(); i0++)
            {
            		_loc_2 = this.m_conditions.get(i0);

                if (_loc_2.hasValidationUtilType(param1))
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  getValidationConditionsByType (String param1 ,Vector param2 .<GenericValidationCondition >)
        {
            IGenericValidationCondition _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_conditions.size(); i0++)
            {
            		_loc_3 = this.m_conditions.get(i0);

                _loc_3.getValidationConditionsByType(param1, param2);
            }
            return;
        }//end

        public void  add (IGenericValidationCondition param1 )
        {
            this.m_conditions.push(param1);
            return;
        }//end

        public boolean  evaluate (Object param1)
        {
            boolean _loc_2 =false ;
            IGenericValidationCondition _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_conditions.size(); i0++)
            {
            		_loc_3 = this.m_conditions.get(i0);

                switch(this.m_style)
                {
                    case EVALUATE_AND:
                    {
                        _loc_2 = _loc_3.evaluate(param1);
                        if (!_loc_2)
                        {
                            return _loc_2;
                        }
                        break;
                    }
                    case EVALUATE_OR:
                    {
                        _loc_2 = _loc_3.evaluate(param1);
                        if (_loc_2)
                        {
                            return _loc_2;
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

    }



