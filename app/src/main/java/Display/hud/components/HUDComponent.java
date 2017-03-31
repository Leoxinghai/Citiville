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

import Display.*;
import Display.aswingui.*;
import Display.hud.*;
//import flash.display.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDComponent extends Sprite
    {
        protected JWindow m_jWindow ;
        protected JPanel m_jPanel ;
        protected ToolTip m_toolTip ;
        protected HUDCounter m_counter ;
        protected HUDCounter m_counter1 ;
        protected HUDCounter m_counter2 ;

        public  HUDComponent ()
        {
            this.m_jWindow = new JWindow(this);
            this.buildComponent();
            this.showComponent();
            return;
        }//end

        public void  initWithXML (XML param1 )
        {
            return;
        }//end

        public boolean  isVisibilityControlledInternally ()
        {
            return false;
        }//end

        protected void  showComponent ()
        {
            this.m_jWindow.setContentPane(this.m_jPanel);
            ASwingHelper.prepare(this.m_jWindow);
            this.m_jWindow.cacheAsBitmap = true;
            this.m_jWindow.show();
            this.alpha = 0;
            if (!this.isVisibilityControlledInternally)
            {
                Z_TweenLite.to(this, 1, {alpha:1});
            }
            this.attachToolTip();
            if (this.doubleBarComponent)
            {
                this.setCounter1();
                this.setCounter2();
            }
            else
            {
                this.setCounter();
            }
            return;
        }//end

        protected void  buildComponent ()
        {
            return;
        }//end

        public void  doBuildComponent ()
        {
            this.buildComponent();
            return;
        }//end

        public boolean  isVisible ()
        {
            return true;
        }//end

        public void  reset ()
        {
            return;
        }//end

        public boolean  doubleBarComponent ()
        {
            return false;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        protected void  attachToolTip ()
        {
            return;
        }//end

        public void  refresh (boolean param1 )
        {
            if (this.m_counter)
            {
                this.m_counter.update(param1);
            }
            if (this.m_counter1)
            {
                this.m_counter1.update(param1);
            }
            if (this.m_counter2)
            {
                this.m_counter2.update(param1);
            }
            return;
        }//end

        protected void  setCounter ()
        {
            return;
        }//end

        protected void  setCounter1 ()
        {
            return;
        }//end

        protected void  setCounter2 ()
        {
            return;
        }//end

        public void  updateCounter (...args )
        {
            if (this.m_counter.value != args.get(0))
            {
                this.m_counter.value = args.get(0);
            }
            this.refresh(false);
            return;
        }//end

        public void  updateCounterCapacity (...args )
        {
            HUDCapacityCounter _loc_1 =null ;
            if (this.m_counter instanceof HUDCapacityCounter)
            {
                _loc_1 =(HUDCapacityCounter) this.m_counter;
                if (_loc_1.capacity != args.get(0))
                {
                    _loc_1.capacity = args.get(0);
                }
                this.refresh(false);
            }
            return;
        }//end

        public void  updateCounter1 (...args )
        {
            if (!this.doubleBarComponent)
            {
                return;
            }
            if (this.m_counter1.value != args.get(0))
            {
                this.m_counter1.value = args.get(0);
            }
            this.refresh(false);
            return;
        }//end

        public void  updateCounterCapacity1 (...args )
        {
            HUDCapacityCounter _loc_1 =null ;
            if (!this.doubleBarComponent)
            {
                return;
            }
            if (this.m_counter1 instanceof HUDCapacityCounter)
            {
                _loc_1 =(HUDCapacityCounter) this.m_counter1;
                if (_loc_1.capacity != args.get(0))
                {
                    _loc_1.capacity = args.get(0);
                }
                this.refresh(false);
            }
            return;
        }//end

        public void  updateCounter2 (...args )
        {
            if (!this.doubleBarComponent)
            {
                return;
            }
            if (this.m_counter2.value != args.get(0))
            {
                this.m_counter2.value = args.get(0);
            }
            this.refresh(false);
            return;
        }//end

        public void  updateCounterCapacity2 (...args )
        {
            HUDCapacityCounter _loc_1 =null ;
            if (!this.doubleBarComponent)
            {
                return;
            }
            if (this.m_counter2 instanceof HUDCapacityCounter)
            {
                _loc_1 =(HUDCapacityCounter) this.m_counter2;
                if (_loc_1.capacity != args.get(0))
                {
                    _loc_1.capacity = args.get(0);
                }
                this.refresh(false);
            }
            return;
        }//end

    }



