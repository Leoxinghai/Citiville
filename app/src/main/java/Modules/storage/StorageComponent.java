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

    public class StorageComponent
    {
        private Dictionary m_storage ;

        public  StorageComponent ()
        {
            this.m_storage = new Dictionary();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            StorageType _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Object _loc_7 =null ;
            BaseStorageUnit _loc_8 =null ;
            if (param1 !=null)
            {
                _loc_2 = param1.get("m_storage");
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_4 = StorageType.createEnum(_loc_3);
                    this.m_storage.put(_loc_4,  new Dictionary());
                    _loc_5 = _loc_2.get(_loc_3);
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);

                        _loc_7 = _loc_5.get(_loc_6);
                        _loc_8 = new BaseStorageUnit();
                        _loc_8.loadObject(_loc_7);
                        this.m_storage.get(_loc_4).put(_loc_6,  _loc_8);
                    }
                }
            }
            return;
        }//end

        public int  size ()
        {
            BaseStorageUnit _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.getAllStorageUnits ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.size;
            }
            return _loc_1;
        }//end

        public boolean  validate (ItemInstance param1 ,StorageType param2 ,String param3 )
        {
            _loc_4 = param1.getItem ().storable ;
            _loc_5 = StorageType.getValidateFunction(param2);
            return _loc_4 && _loc_5(param1, param3);
        }//end

        public StorageId Vector  getValidStorageUnits (ItemInstance param1 ).<>
        {
            StorageType _loc_4 =null ;
            Vector _loc_5.<String >=null ;
            String _loc_6 =null ;
            Vector<StorageId> _loc_2 =new Vector<StorageId>();
            _loc_3 = this.getStorageTypes ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = this.getKeysByType(_loc_4);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    if (this.validate(param1, _loc_4, _loc_6))
                    {
                        _loc_2.push(new StorageId(_loc_4, _loc_6));
                    }
                }
            }
            return _loc_2;
        }//end

        public BaseStorageUnit  addStorageUnit (StorageType param1 ,String param2 )
        {
            BaseStorageUnit _loc_3 =null ;
            if (this.m_storage.get(param1) == null)
            {
                this.m_storage.put(param1,  new Dictionary());
            }
            _loc_4 =(Dictionary) this.m_storage.get(param1);
            if ((this.m_storage.get(param1) as Dictionary).get(param2) == null)
            {
                _loc_3 = new BaseStorageUnit();
                _loc_4.put(param2,  _loc_3);
            }
            else
            {
                _loc_3 = _loc_4.get(param2);
            }
            return _loc_3;
        }//end

        public void  removeStorageUnit (StorageType param1 ,String param2 )
        {
            int _loc_4 =0;
            _loc_3 = this.m_storage.get(param1) ;
            if (_loc_3 != null)
            {
                delete _loc_3.get(param2);
                _loc_4 = 0;
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		param2 = _loc_3.get(i0);

                    _loc_4++;
                }
                if (_loc_4 == 0)
                {
                    delete this.m_storage.get(param1);
                }
            }
            return;
        }//end

        public ItemInstance Vector  getItemsByName (String param1 ).<>
        {
            BaseStorageUnit _loc_4 =null ;
            Vector<ItemInstance> _loc_5 =null ;
            Vector<ItemInstance> _loc_2 = new Vector<ItemInstance>();
            _loc_3 = this.getAllStorageUnits ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4 != null)
                {
                    _loc_5 = _loc_4.getItemsByName(param1);
                    if (_loc_5 != null)
                    {
                        _loc_2 = _loc_2.concat(_loc_5);
                    }
                }
            }
            return _loc_2;
        }//end

        public ItemInstance Vector  getItemsByType (String param1 ).<>
        {
            BaseStorageUnit _loc_4 =null ;
            _loc_2 = this.getAllStorageUnits ();
            Vector<ItemInstance> _loc_3 =new Vector<ItemInstance>();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                if (_loc_4 != null)
                {
                    _loc_3 = _loc_3.concat(_loc_4.getItemsByType(param1));
                }
            }
            return _loc_3;
        }//end

        public BaseStorageUnit  getStorageUnit (StorageType param1 ,String param2 )
        {
            BaseStorageUnit _loc_3 =null ;
            _loc_4 = this.m_storage.get(param1) ;
            if (this.m_storage.get(param1) != null)
            {
                _loc_3 =(BaseStorageUnit) _loc_4.get(param2);
            }
            return _loc_3;
        }//end

        public StorageType Vector  getStorageTypes ().<>
        {
            Object _loc_2 =null ;
            StorageType _loc_3 =null ;
            Vector<StorageType> _loc_1 =new Vector<StorageType>();
            for(int i0 = 0; i0 < this.m_storage.size(); i0++)
            {
            		_loc_2 = this.m_storage.get(i0);

                _loc_3 =(StorageType) _loc_2;
                _loc_1.push(_loc_3);
            }
            return _loc_1;
        }//end

        public BaseStorageUnit Vector  getAllStorageUnits ().<>
        {
            StorageType _loc_3 =null ;
            Vector<BaseStorageUnit> _loc_1 =new Vector<BaseStorageUnit>();
            _loc_2 = this.getStorageTypes ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1.concat(this.getStorageUnitsByType(_loc_3));
            }
            return _loc_1;
        }//end

        public BaseStorageUnit Vector  getStorageUnitsByType (StorageType param1 ).<>
        {
            BaseStorageUnit _loc_4 =null;
            Vector<BaseStorageUnit> _loc_2 =new Vector<BaseStorageUnit>();
            _loc_3 = this.getStorageTypeComponent(param1 );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        public String Vector  getKeysByType (StorageType param1 ).<>
        {
            String _loc_4 =null ;
            Vector<String> _loc_2 =new Vector<String>();
            _loc_3 = this.m_storage.get(param1) ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        private Dictionary  getStorageTypeComponent (StorageType param1 )
        {
            return this.m_storage.get(param1) as Dictionary;
        }//end

    }



