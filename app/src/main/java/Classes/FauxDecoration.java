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

import Engine.*;
import Engine.Helpers.*;

    public class FauxDecoration extends Decoration
    {
        private double DEPTH_SIZE_ADJUSTMENT =1;

        public  FauxDecoration (String param1)
        {
            super(param1);
            m_collisionFlags = Constants.COLLISION_NONE;
            _loc_2 = getItem().xml;
            _loc_3 = _loc_2.depthSizeAdjustment;
            _loc_2 = _loc_3.get(0);
            if (_loc_2)
            {
                this.DEPTH_SIZE_ADJUSTMENT = _loc_2.@factor;
                if (this.DEPTH_SIZE_ADJUSTMENT == 0)
                {
                    this.DEPTH_SIZE_ADJUSTMENT = 1;
                }
            }
            return;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public String  getToolTipHeader ()
        {
            return null;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public String  getGameModeToolTipAction ()
        {
            return null;
        }//end

         public Vector3  getDepthSize ()
        {
            _loc_1 = super.getDepthSize();
            _loc_1.x = _loc_1.x * this.DEPTH_SIZE_ADJUSTMENT;
            _loc_1.y = _loc_1.y * this.DEPTH_SIZE_ADJUSTMENT;
            return _loc_1;
        }//end

    }



