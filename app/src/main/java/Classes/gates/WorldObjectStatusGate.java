package Classes.gates;

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

import Classes.*;
    public class WorldObjectStatusGate extends AbstractGate
    {

        public  WorldObjectStatusGate (String param1 )
        {
            super(param1);
            return;
        }//end

         protected void  takeKeys ()
        {
            return;
        }//end

         public int  keyProgress (String param1 )
        {
            int _loc_2 =0;
            _loc_3 =Global.world.getObjectById(m_targetObjectId )as MapResource ;
            if (_loc_3.get(param1))
            {
                _loc_2 = int(_loc_3.get(param1));
            }
            return Math.min(_loc_2, m_keys.get(param1));
        }//end

         public boolean  checkForKeys ()
        {
            String _loc_3 =null ;
            boolean _loc_1 =true ;
            _loc_2 =Global.world.getObjectById(m_targetObjectId )as MapResource ;
            for(int i0 = 0; i0 < m_keys.size(); i0++) 
            {
            		_loc_3 = m_keys.get(i0);

                if (!_loc_2.get(_loc_3) && Config.DEBUG_MODE)
                {
                }
                if (!_loc_2.get(_loc_3) || Number(_loc_2.get(_loc_3)) < m_keys.get(_loc_3))
                {
                    _loc_1 = false;
                    break;
                }
            }
            return _loc_1;
        }//end

    }


