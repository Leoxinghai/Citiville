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

//
import java.util.Vector;

    public class ConditionSet
    {
        protected Vector<Condition> m_conditions=null ;

        public  ConditionSet ()
        {
            this.m_conditions = new Vector<Condition>();
            return;
        }//end

        public void  add (Condition param1 )
        {
            this.m_conditions.add(param1);
            return;
        }//end

        public boolean  evaluate ()
        {
            Condition _loc_2 =null ;
            boolean _loc_1 =false ;
            for(int i0 = 0; i0 < this.m_conditions.size(); i0++) 
            {
            		_loc_2 = this.m_conditions.get(i0);

                _loc_1 = _loc_2.evaluate();
                if (!_loc_1)
                {
                    break;
                }
            }
            return _loc_1;
        }//end

    }



