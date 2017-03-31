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

    public class ItemGroup
    {
        protected XML m_data ;
        protected String m_name ;
        protected String m_icon ;
        protected Array m_items ;

        public  ItemGroup (XML param1 )
        {
            XML _loc_2 =null ;
            this.m_name = param1.@name;
            this.m_icon = Global.getAssetURL(param1.@icon);
            this.m_items = new Array();
            for(int i0 = 0; i0 < param1.setItem.size(); i0++) 
            {
            		_loc_2 = param1.setItem.get(i0);

                this.m_items.push(_loc_2.@name);
            }
            this.m_data = param1;
            return;
        }//end

        public String  getName ()
        {
            return this.m_name;
        }//end

        public String  getIcon ()
        {
            return this.m_icon;
        }//end

        public int  getTotalNumItems ()
        {
            return this.m_items.length;
        }//end

        public Array  getItems ()
        {
            return this.m_items;
        }//end

        public boolean  isItemInGroup (String param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_items.size(); i0++) 
            {
            		_loc_2 = this.m_items.get(i0);

                if (_loc_2 == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

    }


