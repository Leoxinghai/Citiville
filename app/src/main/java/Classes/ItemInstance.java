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

import Engine.Events.*;
//import flash.events.*;
import com.xinghai.Debug;

    public class ItemInstance extends GameObject
    {
        protected Item m_item =null ;

        public  ItemInstance (String param1 )
        {
            if (param1 !=null)
            {
                this.setItem(Global.gameSettings().getItemByName(param1));
            }
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (param1.itemName)
            {
                this.setItem(Global.gameSettings().getItemByName(param1.itemName));
            }
            return;
        }//end

        public void  setItem (Item param1 )
        {
            if (param1 != this.m_item)
            {
                if (this.m_item)
                {
                    this.m_item.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                }
                this.m_item = param1;
                if (this.m_item)
                {
                    this.m_item.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                    this.updateSize();
                }
                setDisplayObjectDirty(true);
            }
            return;
        }//end

        protected void  updateSize ()
        {
            if (this.isRotated())
            {
                m_size.x = this.m_item.sizeY;
                m_size.y = this.m_item.sizeX;
                m_size.z = 0;
            }
            else
            {
                m_size.x = this.m_item.sizeX;
                m_size.y = this.m_item.sizeY;
                m_size.z = 0;
            }
            return;
        }//end

        protected boolean  isRotated ()
        {
            return m_direction % 2 == 1;
        }//end

        public Item  getItem ()
        {
            return this.m_item;
        }//end

        public String  getItemFriendlyName ()
        {
            String _loc_1 ="";
            if (this.m_item)
            {
                _loc_1 = this.m_item.localizedName;
            }
            return _loc_1;
        }//end

        public String  getItemName ()
        {
            String _loc_1 ="";
            if (this.m_item)
            {
                _loc_1 = this.m_item.name;
            }
            return _loc_1;
        }//end

         public Object  getSaveObject ()
        {
            _loc_1 = super.getSaveObject();
            if (this.m_item)
            {
                _loc_1.itemName = this.m_item.name;
            }
            else
            {
                _loc_1.itemName = null;
            }
            return _loc_1;
        }//end

        protected void  onItemImageLoaded (Event event )
        {
            Debug.debug4("ItemInstance.onItemImageLoaded");
            conditionallyRedraw(true);
            return;
        }//end

         public void  cleanUp ()
        {
            if (this.m_item)
            {
                this.m_item.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                this.m_item = null;
            }
            super.cleanUp();
            return;
        }//end

        public boolean  isEmptyLot ()
        {
            return false;
        }//end

    }



