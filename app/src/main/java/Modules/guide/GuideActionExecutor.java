package Modules.guide;

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

import Engine.Classes.*;
import Modules.guide.actions.*;

    public class GuideActionExecutor extends StateMachine
    {

        public  GuideActionExecutor ()
        {
            return;
        }//end  

        public void  addActions (...args )
        {
            argsvalue = undefined;
            GuideAction _loc_3 =null ;
            for(int i0 = 0; i0 < args.size(); i0++) 
            {
            		argsvalue = args.get(i0);
                
                _loc_3 =(GuideAction) argsvalue;
                if (_loc_3 != null)
                {
                    this.addState(_loc_3, true);
                    continue;
                }
                throw new Error("GuideActionExecutor - Failed to add action " + args);
            }
            return;
        }//end  

    }



