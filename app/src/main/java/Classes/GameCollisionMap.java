package Classes;

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

//import flash.geom.*;

    public class GameCollisionMap extends CollisionMap
    {

        public  GameCollisionMap ()
        {
            return;
        }//end

        public void  setOwnedArea (Vector param1 .<Rectangle >)
        {
            Rectangle _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            	_loc_2 = param1.get(i0);

                _loc_3 = _loc_2.x;
                _loc_4 = _loc_2.y;
                _loc_5 = _loc_2.x + _loc_2.width;
                _loc_6 = _loc_2.y + _loc_2.height;
                _loc_7 = _loc_3;
                while (_loc_7 < _loc_5)
                {

                    _loc_8 = _loc_4;
                    while (_loc_8 < _loc_6)
                    {

                        m_cells.get(_loc_7).get(_loc_8).isPathable = true;
                        _loc_8++;
                    }
                    _loc_7++;
                }
            }
            return;
        }//end

        public Rectangle  boundingRectangle ()
        {
            return new Rectangle(m_xStart, m_yStart, m_xEnd - m_xStart, m_yEnd - m_yStart);
        }//end

    }



