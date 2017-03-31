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

import Classes.sim.*;
import Engine.Helpers.*;

    public class Helicopter extends Vehicle
    {
        protected FauxDecoration m_shadow =null ;
        protected boolean m_alwaysOnTop =false ;
        protected boolean m_showShadow =true ;
        protected MapResource m_depthSwapObject ;
        protected double m_depthIndexOffset =0;
public static int m_numCopters =0;
public static  double MIN_SHADOW_ALPHA =0.35;
public static  double SHADOW_ELEVATION_THRESHHOLD =10;
public static  String SHADOW_NAME ="prop_helicopter_shadow";

        public  Helicopter (String param1 ,boolean param2 )
        {
            super(param1, param2);
            this.m_shadow = new FauxDecoration(Helicopter.SHADOW_NAME);
            this.m_shadow.setOuter(Global.world);
            this.m_shadow.alpha = MIN_SHADOW_ALPHA;
            this.m_depthIndexOffset = Helicopter.m_numCopters;
            _loc_3 = Helicopter;
            _loc_4 = Helicopter.m_numCopters+1;
            _loc_3.m_numCopters = _loc_4;
            this.actionSelection = new CopHelicopterActionSelection(this);
            ((CopHelicopterActionSelection)actionSelection).soundLoopName = "helicopter";
            ((CopHelicopterActionSelection)actionSelection).idleHotspot = "helipad";
            return;
        }//end

        public MapResource  depthSwapObject ()
        {
            return this.m_depthSwapObject;
        }//end

        public void  depthSwapObject (MapResource param1 )
        {
            this.m_depthSwapObject = param1;
            return;
        }//end

        public boolean  alwaysOnTop ()
        {
            return this.m_alwaysOnTop;
        }//end

        public void  alwaysOnTop (boolean param1 )
        {
            this.m_alwaysOnTop = param1;
            return;
        }//end

        public boolean  showShadow ()
        {
            return this.m_showShadow;
        }//end

        public void  showShadow (boolean param1 )
        {
            this.m_showShadow = param1;
            return;
        }//end

        public void  addScene (Vector3 param1 )
        {
            ((CopHelicopterActionSelection)this.actionSelection).addTargetScene(param1);
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (this.m_shadow)
            {
                this.m_shadow.setPosition(m_position.x, m_position.y, 0);
                this.m_shadow.conditionallyReattach(true);
            }
            return;
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            super.setPosition(param1, param2, param3);
            if (this.m_shadow)
            {
                this.m_shadow.setPosition(param1, param2, 0);
                this.m_shadow.alpha = this.m_showShadow ? (MIN_SHADOW_ALPHA + Math.max(0, 1 - MIN_SHADOW_ALPHA) * (1 - Math.min(1, param3 / SHADOW_ELEVATION_THRESHHOLD))) : (0);
                this.m_shadow.conditionallyReattach(true);
            }
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            if (this.m_shadow)
            {
                this.m_shadow.setPosition(m_position.x, m_position.y, 0);
                this.m_shadow.conditionallyReattach(true);
            }
            return;
        }//end

         public void  attach ()
        {
            super.attach();
            if (this.m_shadow)
            {
                this.m_shadow.attach();
            }
            return;
        }//end

         public void  detach ()
        {
            super.detach();
            if (this.m_shadow)
            {
                this.m_shadow.detach();
            }
            return;
        }//end

         public double  depthIndex ()
        {
            if (this.m_alwaysOnTop)
            {
                return -100000 - this.m_depthIndexOffset;
            }
            if (this.m_depthSwapObject)
            {
                return super.depthIndex;
            }
            return super.depthIndex;
        }//end

    }



