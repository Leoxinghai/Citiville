package Classes.inventory;

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

    public class SupplyStorage extends BaseInventory
    {
        protected Object m_capacities ;

        public  SupplyStorage (Object param1)
        {
            String _loc_2 =null ;
            this.m_capacities = new Object();
            if (param1 && param1.capacities)
            {
                for(int i0 = 0; i0 < param1.capacities.size(); i0++)
                {
                		_loc_2 = param1.capacities.get(i0);

                    this.m_capacities.put(_loc_2,  param1.capacities.get(_loc_2));
                }
            }
            super(param1);
            return;
        }//end

         public boolean  isValidName (String param1 )
        {
            _loc_2 = this.getCapacity(param1);
            return _loc_2 > 0;
        }//end

         public int  getCapacity (String param1 )
        {
            int _loc_2 =0;
            if (this.m_capacities.hasOwnProperty(param1))
            {
                _loc_2 = this.m_capacities.get(param1);
            }
            return _loc_2;
        }//end

        public void  setCapacity (String param1 ,int param2 )
        {
            if (param2 == 0 && this.m_capacities.hasOwnProperty(param1))
            {
                delete this.m_capacities.get(param1);
            }
            else
            {
                this.m_capacities.put(param1,  param2);
            }
            return;
        }//end

        public void  setCapacities (Object param1 )
        {
            this.m_capacities = param1;
            return;
        }//end

    }



