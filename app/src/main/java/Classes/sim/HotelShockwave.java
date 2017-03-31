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

import Classes.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import com.greensock.*;
//import flash.events.*;

    public class HotelShockwave extends ConsumerShockwave
    {

        public  HotelShockwave (MapResource param1 ,double param2 ,double param3 ,Function param4 =null ,Function param5 =null ,boolean param6 =false ,int param7 =16755200)
        {
            super(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public Array  businessesInRange ()
        {
            corner = m_origin.getPositionNoClone();
            size = m_origin.getSizeNoClone();
            Vector3 center =new Vector3(corner.x +size.x /2,corner.y +size.y /2);
            checker = function(param1WorldObject)
            {
                if (!(param1 instanceof Business))
                {
                    return false;
                }
                _loc_2 = param1as Business ;
                return _loc_2.getPositionNoClone().subtract(m_center).length() <= m_maxRadius;
            }//end
            ;
            return Global.world.getObjectsByPredicate(checker);
        }//end

         protected void  onEnterFrame (Event event )
        {
            Business business ;
            double fadeout ;
            event = event;
            if (m_origin == null)
            {
                stop();
                return;
            }
            updateVisuals();
            checker = function(param1WorldObject)
            {
                if (!(param1 instanceof Business))
                {
                    return false;
                }
                _loc_2 = param1as Business ;
                return _loc_2.getPositionNoClone().subtract(m_center).length() <= m_tileRadius;
            }//end
            ;
            businesses = Global.world.getObjectsByPredicate(checker);
            if (businesses.length > 0)
            {
                int _loc_3 =0;
                _loc_4 = businesses;
                for(int i0 = 0; i0 <  i0 = 0; i0 < ss in businesses.size(); i0++.size(); i0++)
                {
                		ss =  i0 = 0; i0 < ss in businesses.size(); i0++.get(i0);
                	business = ss in businesses.get(i0);


                    business.enableGlow();
                }
            }
            if (m_fadeHalfway && m_tileRadius >= m_maxRadius / 2)
            {
                fadeout;
                TweenLite.to(m_animation, fadeout, {alpha:0});
                m_fadeHalfway = false;
            }
            if (m_tileRadius >= m_maxRadius)
            {
                if (m_endCallback != null)
                {
                    m_endCallback();
                }
                else
                {
                    stop();
                }
            }
            return;
        }//end

    }



