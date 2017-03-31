package Modules.goals;

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

import Modules.goals.mastery.*;
//import flash.utils.*;

    public class GoalManager
    {
        protected Dictionary m_goals ;
        public static  String GOAL_MASTERY ="mastery";

        public  GoalManager ()
        {
            this.m_goals = new Dictionary();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                this.m_goals.put(_loc_2,  this.createGoal(_loc_2, param1.get(_loc_2)));
            }
            return;
        }//end

        public Goal  getGoal (String param1 )
        {
            if (!this.m_goals.get(param1))
            {
                this.m_goals.put(param1,  this.createGoal(param1, null));
            }
            return this.m_goals.get(param1);
        }//end

        protected Goal  createGoal (String param1 ,Object param2 )
        {
            Goal _loc_3 =null ;
            switch(param1)
            {
                case GOAL_MASTERY:
                {
                    _loc_3 = new MasteryGoal(param2);
                    break;
                }
                default:
                {
                    _loc_3 = new Goal(param2);
                    break;
                    break;
                }
            }
            return _loc_3;
        }//end

    }



