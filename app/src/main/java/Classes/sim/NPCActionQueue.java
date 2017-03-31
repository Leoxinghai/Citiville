package Classes.sim;

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

import Classes.actions.*;
import Engine.Classes.*;
import com.xinghai.Debug;

    public class NPCActionQueue extends StateMachine
    {

        public  NPCActionQueue ()
        {
            return;
        }//end

        public void  addActions (...args )
        {
            args_1 = undefined;
            BaseAction _loc_3 =null ;
            for(int i0 = 0; i0 < args.size(); i0++) 
            {
            	args_1 = args.get(i0);

                _loc_3 =(BaseAction) args_1;

                if (_loc_3 != null)
                {
                    this.addState(_loc_3, true);
                    continue;
                }
                throw new Error("Failed to add action " + args + ", expected BaseAction");
            }
            return;
        }//end

        public void  addActionsArray (Array param1 )
        {
            _loc_2 = null;
            BaseAction _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            	_loc_2 = param1.get(i0);

                _loc_3 =(BaseAction) _loc_2;
                if (_loc_3 != null)
                {
                    this.addState(_loc_3, true);
                    continue;
                }
                throw new Error("Failed to add action " + _loc_2 + ", expected BaseAction");
            }
            return;
        }//end

        public void  insertActionsArray (int param1 ,Array param2 )
        {
            _loc_4 = null;
            BaseAction _loc_5 =null ;
            _loc_3 = param1;
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            	_loc_4 = param2.get(i0);

                _loc_5 =(BaseAction) _loc_4;
                if (_loc_5 != null)
                {
                    m_states.splice(_loc_3, 0, _loc_5);
                    m_states.get(_loc_3).added();
                    continue;
                }
                throw new Error("Failed to add action " + _loc_4 + ", expected BaseAction");
            }
            return;
        }//end

    }



