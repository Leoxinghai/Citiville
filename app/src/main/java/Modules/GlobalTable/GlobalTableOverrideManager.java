package Modules.GlobalTable;

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

    public class GlobalTableOverrideManager
    {
        private Object tables ;
        private static GlobalTableOverrideManager m_instance ;

        public  GlobalTableOverrideManager ()
        {
            return;
        }//end

        public String  getTables ()
        {
            _loc_1 = this.tables ;
            _loc_2 = (String)Tools.pr(_loc_1,1)
            _loc_2 = _loc_2.split("(string)").join("");
            _loc_2 = _loc_2.split("(object)").join("");
            return _loc_2;
        }//end

        public Array  getGlobalTables (String param1 )
        {
            String _loc_4 =null ;
            Array _loc_2 =new Array();
            if (this.tables == null)
            {
                return _loc_2;
            }
            _loc_3 = this.tables.get(param1) ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        public void  addQuestData (Object param1 ,String param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 !=null)
            {
                _loc_3 = param1.keyword;
                _loc_4 = param1.table;
                _loc_5 = param2;
                GlobalTableOverrideManager.instance.addGlobalData(_loc_3, _loc_4, _loc_5);
            }
            return;
        }//end

        public void  removeQuestData (Object param1 ,String param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 !=null)
            {
                _loc_3 = param1.keyword;
                _loc_4 = param1.table;
                _loc_5 = param2;
                GlobalTableOverrideManager.instance.removeGlobalData(_loc_3, _loc_4, _loc_5);
            }
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            this.tables = param1;
            return;
        }//end

        private boolean  isEmpty (Object param1 )
        {
            Object _loc_2 =null ;
            if (param1 == null)
            {
                return true;
            }
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                return false;
            }
            return true;
        }//end

        public void  addGlobalData (String param1 ,String param2 ,String param3 )
        {
            if (this.tables == null)
            {
                this.tables = new Object();
            }
            if (this.isEmpty(this.tables.get(param1)))
            {
                this.tables.put(param1,  new Object());
            }
            if (!(this.tables.get(param1).get(param2) instanceof String))
            {
                this.tables.get(param1).put(param2,  "");
            }
            if (this.tables.get(param1).get(param2) == "")
            {
                this.tables.get(param1).put(param2,  param3);
            }
            else
            {
                this.tables.get(param1).put(param2,  this.tables.get(param1).get(param2) + ("," + param3));
            }
            return;
        }//end

        public void  removeGlobalData (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = this.tables.get(param1).get(param2) ;
            _loc_5 = this.tables.get(param1).get(param2).split(",");
            this.tables.get(param1).get(param2).split(",").splice(this.tables.get(param1).get(param2).split(",").indexOf(param3), 1);
            _loc_4 = _loc_5.join(",");
            this.tables.get(param1).put(param2,  _loc_4);
            if (this.tables.get(param1).get(param2) == "")
            {
                delete this.tables.get(param1).get(param2);
                if (this.isEmpty(this.tables.get(param1)))
                {
                    delete this.tables.get(param1);
                }
            }
            return;
        }//end

        public static GlobalTableOverrideManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new GlobalTableOverrideManager;
            }
            return m_instance;
        }//end

    }



