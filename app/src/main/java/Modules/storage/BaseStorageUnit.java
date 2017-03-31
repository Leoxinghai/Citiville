package Modules.storage;

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

import Classes.*;

//import flash.utils.*;

    public class BaseStorageUnit implements IStorageUnit
    {
        private Dictionary m_storage ;
        private int m_capacity ;
        private int m_maxCapacity ;

        public  BaseStorageUnit ()
        {
            this.m_storage = new Dictionary();
            this.m_capacity = 0;
            this.m_maxCapacity = 0;
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_3 =null ;
            Array _loc_4 =null ;
            Object _loc_5 =null ;
            ItemInstance _loc_6 =null ;
            _loc_2 = param1.get("m_storage") ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 =(Array) _loc_2.get(_loc_3);
                this.m_storage.put(_loc_3,  new Vector<ItemInstance>);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_6 =(ItemInstance) Global.world.loadObjectInstance(_loc_5);
                    _loc_6.loadObject(_loc_5);
                    this.m_storage.get(_loc_3).push(_loc_6);
                }
            }
            this.m_capacity = int(param1.get("m_capacity"));
            this.m_maxCapacity = int(param1.get("m_maxCapacity"));
            return;
        }//end

        public int  capacity ()
        {
            return this.m_capacity;
        }//end

        public void  capacity (int param1 )
        {
            if (param1 > this.m_maxCapacity)
            {
                param1 = this.m_maxCapacity;
            }
            this.m_capacity = param1;
            return;
        }//end

        public int  maxCapacity ()
        {
            return this.m_maxCapacity;
        }//end

        public void  maxCapacity (int param1 )
        {
            this.m_maxCapacity = param1;
            return;
        }//end

        public int  size ()
        {
            String _loc_2 =null ;
            Vector _loc_3.<ItemInstance >=null ;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.m_storage.size(); i0++)
            {
            		_loc_2 = this.m_storage.get(i0);

                _loc_3 = (Vector<ItemInstance>)this.m_storage.get(_loc_2);
                _loc_1 = _loc_1 + _loc_3.length;
            }
            return _loc_1;
        }//end

        public boolean  add (ItemInstance param1 )
        {
            Item _loc_3 =null ;
            String _loc_4 =null ;
            Vector _loc_5.<ItemInstance >=null ;
            boolean _loc_2 =false ;
            if (this.size < this.capacity)
            {
                _loc_3 = param1.getItem();
                _loc_4 = _loc_3.name;
                if (this.m_storage.get(_loc_4) == null)
                {
                    this.m_storage.put(_loc_4,  new Vector<ItemInstance>);
                }
                _loc_5 = (Vector<ItemInstance>)this.m_storage.get(_loc_4);
                if (_loc_5.indexOf(param1) < 0)
                {
                    _loc_5.push(param1);
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public boolean  remove (ItemInstance param1 )
        {
            int _loc_6 =0;
            boolean _loc_2 =false ;
            _loc_3 = param1.getItem ();
            _loc_4 = _loc_3.name ;
            _loc_5 = this.m_storage.get(_loc_4) ;
            if (this.m_storage.get(_loc_4) != null)
            {
                _loc_6 = _loc_5.indexOf(param1);
                if (_loc_6 >= 0)
                {
                    _loc_5.splice(_loc_6, 1);
                    _loc_2 = true;
                }
                if (_loc_5.length == 0)
                {
                    delete this.m_storage.get(_loc_4);
                }
            }
            return _loc_2;
        }//end

        public ItemInstance Vector  getAllItems ().<>
        {
            Vector<ItemInstance> _loc_2 =null ;
            ItemInstance _loc_3 =null ;
            Vector<ItemInstance> _loc_1 =new Vector<ItemInstance>();
            for(int i0 = 0; i0 < this.m_storage.size(); i0++)
            {
            		_loc_2 = this.m_storage.get(i0);

                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_1.push(_loc_3);
                }
            }
            return _loc_1;
        }//end

        public String Vector  getItemNames ().<>
        {
            String _loc_2 =null ;
            Vector<String> _loc_1 =new Vector<String>();
            for(int i0 = 0; i0 < this.m_storage.size(); i0++)
            {
            		_loc_2 = this.m_storage.get(i0);

                _loc_1.push(_loc_2);
            }
            return _loc_1;
        }//end

        public ItemInstance Vector  getItemsByName (String param1 ).<>
        {
            return this.m_storage ? (this.m_storage.get(param1)) : (null);
        }//end

        public ItemInstance Vector  getItemsByType (String param1 ).<>
        {
            String _loc_3 =null ;
            Vector<ItemInstance> _loc_4 = null;
            Vector<ItemInstance> _loc_2 = new Vector<ItemInstance>();
            for(int i0 = 0; i0 < this.m_storage.size(); i0++)
            {
            		_loc_3 = this.m_storage.get(i0);

                _loc_4 = (Vector<ItemInstance>)this.m_storage.get(_loc_3);
                if (_loc_4 && _loc_4.length > 0 && param1 == _loc_4.get(0).getItem().type)
                {
                    _loc_2 = _loc_2.concat(_loc_4);
                }
            }
            return _loc_2;
        }//end

        public int  getItemCountByName (String param1 )
        {
            _loc_2 = this.getItemsByName(param1 );
            return _loc_2 ? (_loc_2.length()) : (0);
        }//end

    }



