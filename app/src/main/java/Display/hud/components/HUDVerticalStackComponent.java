package Display.hud.components;

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

import Classes.sim.*;

    public class HUDVerticalStackComponent extends HUDComponentContainer
    {
        private double yPos ;
        private static  double SPACING =10;
        private static  double STANDARD_ICON_HEIGHT =61;

        public  HUDVerticalStackComponent ()
        {
            this.yPos = 0;
            return;
        }//end

         public void  reset ()
        {
            this.adjustStack();
            return;
        }//end

        public void  addComponentAtIndex (double param1 ,HUDComponent param2 )
        {
            param1 = Math.max(0, Math.min(param1, m_components.length()));
            addChild(param2);
            m_components.splice(param1, 0, param2);
            this.adjustStack();
            return;
        }//end

        public void  adjustStack ()
        {
            HUDComponent _loc_2 =null ;
            double _loc_1 =0;
            for(int i0 = 0; i0 < m_components.size(); i0++)
            {
            	_loc_2 = m_components.get(i0);

                _loc_2.y = _loc_1;
                if (_loc_2.height == 0)
                {
                    _loc_1 = _loc_1 + (STANDARD_ICON_HEIGHT + SPACING);
                    continue;
                }
                _loc_1 = _loc_1 + (_loc_2.height + SPACING);
            }
            return;
        }//end

         public void  addComponent (HUDComponent param1 )
        {
            super.addComponent(param1);
            if (!param1.isVisibilityControlledInternally)
            {
                param1.alpha = 1;
            }
            this.adjustStack();
            return;
        }//end

        public void  removeComponent (HUDComponent param1 )
        {
            if (this.contains(param1))
            {
                this.removeChild(param1);
            }
            if (this.m_components.indexOf(param1) >= 0)
            {
                this.m_components.splice(this.m_components.indexOf(param1), 1);
            }
            this.adjustStack();
            return;
        }//end

    }



