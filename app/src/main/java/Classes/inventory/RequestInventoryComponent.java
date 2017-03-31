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
    public class RequestInventoryComponent
    {
        private Dictionary m_inventories ;

        public  RequestInventoryComponent ()
        {
            this.m_inventories = new Dictionary();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            BaseInventory _loc_3 =null ;
            if (param1 != null)
            {
                for(int i0 = 0; i0 < param1.get("m_inventories").size(); i0++)
                {
                		_loc_2 = param1.get("m_inventories").get(i0);

                    _loc_3 = new BaseItemInventory(param1.get("m_inventories").get(_loc_2));
                    this.m_inventories.put(_loc_2,  _loc_3);
                }
            }
            return;
        }//end

        public BaseInventory  create (String param1 )
        {
            BaseItemInventory _loc_2 =new BaseItemInventory(null );
            m_inventories.put(param1, _loc_2);
            return _loc_2;
        }//end

        public void  remove (String param1 )
        {
            delete this.m_inventories.get(param1);
            return;
        }//end

        public BaseInventory  (String param1 )
        {
            return (BaseInventory)this.m_inventories.get(param1);
        }//end

        public boolean  addItem (String param1 ,int param2 ,String param3 )
        {
            _loc_4 = this.get(param3);
            if (this.get(param3) == null)
            {
                _loc_4 = this.create(param3);
            }
            return _loc_4.add(param1, param2);
        }//end

        public boolean  removeItem (String param1 ,int param2 ,String param3 )
        {
            boolean _loc_4 =false ;
            _loc_5 = this.get(param3);
            if (this.get(param3) != null)
            {
                _loc_4 = _loc_5.remove(param1, param2);
                if (_loc_5.getTotalCount() == 0)
                {
                    this.remove(param3);
                }
            }
            return _loc_4;
        }//end

        public int  getItemCount (String param1 ,String param2 )
        {
            int _loc_3 =0;
            _loc_4 = this.get(param2);
            if (this.get(param2) != null)
            {
                _loc_3 = _loc_4.getCount(param1);
            }
            return _loc_3;
        }//end

    }



