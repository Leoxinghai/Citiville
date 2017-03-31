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

//import flash.utils.*;
    public class BaseInventory
    {
        protected Dictionary m_storage ;

        public  BaseInventory (Object param1 )
        {
            this.m_storage = new Dictionary();
            this.loadObject(param1);
            return;
        }//end

        protected void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            if (param1 == null || param1.storage == null)
            {
                return;
            }
            for(int i0 = 0; i0 < param1.storage.size(); i0++) 
            {
            		_loc_2 = param1.storage.get(i0);

                _loc_3 = int(param1.storage.get(_loc_2));
                this.m_storage.put(_loc_2,  _loc_3);
            }
            return;
        }//end

        public boolean  isValidName (String param1 )
        {
            return false;
        }//end

        public int  getCapacity (String param1 )
        {
            return 0;
        }//end

        protected void  onDataChange (String param1 )
        {
            return;
        }//end

        public int  getSpareCapacity (String param1 )
        {
            return this.getCapacity(param1) - this.getCount(param1);
        }//end

        public int  getCount (String param1 )
        {
            if (this.m_storage.get(param1) != null)
            {
                return int(this.m_storage.get(param1));
            }
            return 0;
        }//end

        public int  getTotalCount ()
        {
            int _loc_2 =0;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.m_storage.size(); i0++) 
            {
            		_loc_2 = this.m_storage.get(i0);

                _loc_1 = _loc_1 + int(_loc_2);
            }
            return _loc_1;
        }//end

        public int  getAddedCount (Array param1 )
        {
            String _loc_3 =null ;
            int _loc_2 =0;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_2 = _loc_2 + this.getCount(_loc_3);
            }
            return _loc_2;
        }//end

        public boolean  add (String param1 ,int param2 )
        {
            if (!this.isValidName(param1) || param2 <= 0 || this.getSpareCapacity(param1) < param2)
            {
                return false;
            }
            if (this.m_storage.get(param1) == null)
            {
                this.m_storage.put(param1,  0);
            }
            this.m_storage.put(param1,  this.m_storage.get(param1) + param2);
            this.onDataChange(param1);
            return true;
        }//end

        public boolean  remove (String param1 ,int param2 )
        {
            if (!this.isValidName(param1) || param2 <= 0 || param2 > this.getCount(param1))
            {
                return false;
            }
            this.m_storage.put(param1,  this.m_storage.get(param1) - param2);
            if (this.m_storage.get(param1) == 0)
            {
                delete this.m_storage.get(param1);
            }
            this.onDataChange(param1);
            return true;
        }//end

        public boolean  clear ()
        {
            this.m_storage = new Dictionary();
            return true;
        }//end

    }


