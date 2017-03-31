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

    public class HUDComponentContainer extends HUDComponent
    {
        protected Vector<HUDComponent> m_components;

        public  HUDComponentContainer ()
        {
            this.m_components = new Vector<HUDComponent>();
            return;
        }//end

        public void  addComponent (HUDComponent param1 )
        {
            this.m_components.push(param1);
            addChild(param1);
            return;
        }//end

         public void  cleanUp ()
        {
            HUDComponent _loc_1 =null ;
            super.cleanUp();
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_1 = this.m_components.get(i0);

                _loc_1.cleanUp();
                if (_loc_1.parent)
                {
                    _loc_1.parent.removeChild(_loc_1);
                }
            }
            this.m_components = new Vector<HUDComponent>();
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            HUDComponent _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_2 = this.m_components.get(i0);

                _loc_2.refresh(param1);
            }
            return;
        }//end

    }



