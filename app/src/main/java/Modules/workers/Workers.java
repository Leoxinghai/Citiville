package Modules.workers;

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

    public class Workers
    {
        protected Dictionary m_attributes ;
        protected Vector<WorkerInfo> m_workers;
        public static  String NUM_PURCHASED_WORKERS ="numPurchasedWorkers";

        public  Workers ()
        {
            this.m_attributes = new Dictionary();
            this.m_workers = new Vector<WorkerInfo>();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            WorkerInfo _loc_4 =null ;
            for(int i0 = 0; i0 < param1.attributes.size(); i0++)
            {
            		_loc_2 = param1.attributes.get(i0);

                this.m_attributes.put(_loc_2,  param1.attributes.get(_loc_2));
            }
            for(int i0 = 0; i0 < param1.members.size(); i0++)
            {
            		_loc_3 = param1.members.get(i0);

                _loc_4 = new WorkerInfo();
                _loc_4.loadObject(_loc_3);
                this.m_workers.push(_loc_4);
            }
            return;
        }//end

        public void  preserveAndLoadObject (Object param1 )
        {
            String _loc_2 =null ;
            WorkerInfo _loc_3 =null ;
            Object _loc_4 =null ;
            boolean _loc_5 =false ;
            WorkerInfo _loc_6 =null ;
            for(int i0 = 0; i0 < param1.attributes.size(); i0++)
            {
            		_loc_2 = param1.attributes.get(i0);

                this.m_attributes.put(_loc_2,  param1.attributes.get(_loc_2));
            }
            for(int i0 = 0; i0 < param1.members.size(); i0++)
            {
            		_loc_4 = param1.members.get(i0);

                _loc_3 = new WorkerInfo();
                _loc_3.loadObject(_loc_4);
                _loc_5 = false;
                for(int i0 = 0; i0 < this.m_workers.size(); i0++)
                {
                		_loc_6 = this.m_workers.get(i0);

                    if (_loc_6.zid == _loc_3.zid)
                    {
                        _loc_5 = true;
                        break;
                    }
                }
                if (_loc_5 == false)
                {
                    this.m_workers.push(_loc_3);
                }
            }
            return;
        }//end

        public void  cleanUp (boolean param1 =false )
        {
            String _loc_2 =null ;
            WorkerInfo _loc_3 =null ;
            if (!param1)
            {
                if (this.m_attributes)
                {
                    for(int i0 = 0; i0 < this.m_attributes.size(); i0++)
                    {
                    		_loc_2 = this.m_attributes.get(i0);

                        delete this.m_attributes.get(_loc_2);
                    }
                }
                this.m_attributes = new Dictionary();
            }
            if (this.m_workers)
            {
                for(int i0 = 0; i0 < this.m_workers.size(); i0++)
                {
                		_loc_3 = this.m_workers.get(i0);

                    if (_loc_3)
                    {
                        _loc_3.cleanUp();
                    }
                }
            }
            this.m_workers = new Vector<WorkerInfo>();
            return;
        }//end

        public boolean  doesWorkerExist (String param1 )
        {
            return this.getWorkerPosition(param1) >= 0;
        }//end

        public int  getWorkerPosition (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_workers.length())
            {

                if (this.m_workers.get(_loc_2) && this.m_workers.get(_loc_2).zid == param1)
                {
                    return _loc_2;
                }
                _loc_2++;
            }
            return -1;
        }//end

        public boolean  canAddWorker (String param1 ,boolean param2 =false )
        {
            if (this.getRemainingSpots() > 0)
            {
                if (param1 && !param2)
                {
                    return !this.doesWorkerExist(param1);
                }
                return true;
            }
            return false;
        }//end

        public int  addWorker (String param1 ,boolean param2 =false )
        {
            WorkerInfo _loc_4 =null ;
            int _loc_3 =-1;
            if (this.canAddWorker(param1, param2))
            {
                _loc_4 = new WorkerInfo(param1);
                this.m_workers.push(_loc_4);
                _loc_3 = this.m_workers.length - 1;
            }
            return _loc_3;
        }//end

        public boolean  removeWorkerById (String param1 ,boolean param2 =true )
        {
            _loc_3 = this.getWorkerPosition(param1 );
            return this.removeWorkerByPosition(_loc_3, param2);
        }//end

        public boolean  removeWorkerByPosition (int param1 ,boolean param2 =true )
        {
            if (param1 >= this.m_workers.length())
            {
                return false;
            }
            if (param2)
            {
                this.m_workers.splice(param1, 1);
            }
            else
            {
                this.m_workers.put(param1,  null);
            }
            return true;
        }//end

        public int  getRemainingSpots ()
        {
            _loc_1 = this.getWorkerCount ();
            return Math.max(this.maxWorkers - _loc_1, 0);
        }//end

        public int  getWorkerCount (Function param1)
        {
            Vector _loc_3.<String >=null ;
            int _loc_2 =0;
            if (param1 == null)
            {
                _loc_2 = this.m_workers.length;
            }
            else
            {
                _loc_3 = this.getWorkerIds(param1);
                _loc_2 = _loc_3.length;
            }
            return _loc_2;
        }//end

        public String Vector  getWorkerIds (Function param1).<>
        {
            WorkerInfo _loc_3 =null ;
            Vector<String> _loc_2 =new Vector<String>();
            for(int i0 = 0; i0 < this.m_workers.size(); i0++)
            {
            		_loc_3 = this.m_workers.get(i0);

                if (_loc_3 && (param1 == null || param1(_loc_3.zid, _loc_3.data)))
                {
                    _loc_2.push(_loc_3.zid);
                }
            }
            return _loc_2;
        }//end

        public void  purgeWorkers (Function param1)
        {
            WorkerInfo _loc_3 =null ;
            _loc_2 = this.m_workers.length -1;
            while (_loc_2 >= 0)
            {

                _loc_3 = this.m_workers.get(_loc_2);
                if (!_loc_3 || param1 == null || param1(_loc_3.zid, _loc_3.data))
                {
                    this.m_workers.splice(_loc_2, 1);
                }
                _loc_2 = _loc_2 - 1;
            }
            return;
        }//end

        public int  maxWorkers ()
        {
            return 0;
        }//end

        public int  numPurchasedWorkers ()
        {
            return this.getAttribute(NUM_PURCHASED_WORKERS, 0);
        }//end

        public void  numPurchasedWorkers (int param1 )
        {
            this.setAttribute(NUM_PURCHASED_WORKERS, param1);
            return;
        }//end

        protected  getAttribute (String param1 , Object param2) *
        {
            _loc_3 = param2;
            if (this.m_attributes.hasOwnProperty(param1))
            {
                _loc_3 = this.m_attributes.get(param1);
            }
            return _loc_3;
        }//end

        protected void  setAttribute (String param1 , Object param2)
        {
            this.m_attributes.put(param1,  param2);
            return;
        }//end

        protected  getWorkerData (int param1 ,String param2 ,*)param3 *
        {
            _loc_4 = param3;
            if (param1 < this.m_workers.length && this.m_workers.get(param1) != null)
            {
                if (this.m_workers.get(param1).data.hasOwnProperty(param2))
                {
                    _loc_4 = this.m_workers.get(param1).data.get(param2);
                }
            }
            return _loc_4;
        }//end

        protected boolean  setWorkerData (int param1 ,String param2 ,*)param3
        {
            boolean _loc_4 =false ;
            if (param1 < this.m_workers.length && this.m_workers.get(param1) != null)
            {
                this.m_workers.get(param1).data.put(param2,  param3);
                _loc_4 = true;
            }
            return _loc_4;
        }//end

        protected Dictionary  getAllWorkerData (int param1 , Object param2)
        {
            _loc_3 = param2;
            if (param1 < this.m_workers.length && this.m_workers.get(param1) != null)
            {
                _loc_3 = this.m_workers.get(param1).data;
            }
            return _loc_3;
        }//end

        protected void  setAllWorkerData (int param1 ,Dictionary param2 )
        {
            if (param1 < this.m_workers.length && this.m_workers.get(param1) != null)
            {
                this.m_workers.get(param1).data = param2;
            }
            return;
        }//end

        public Object  getRawAttributes ()
        {
            String _loc_2 =null ;
            Object _loc_1 ={};
            for(int i0 = 0; i0 < this.m_attributes.size(); i0++)
            {
            		_loc_2 = this.m_attributes.get(i0);

                _loc_1.put(_loc_2,  this.m_attributes.get(_loc_2));
            }
            return _loc_1;
        }//end

    }



import flash.utils.*;

class WorkerInfo
    public String zid ;
    public Dictionary data ;

     WorkerInfo (String param1 ="")
    {
        this.zid = param1;
        this.data = new Dictionary();
        return;
    }//end

    public void  loadObject (Object param1 )
    {
        String _loc_2 =null ;
        this.zid = param1.zid;
        for(int i0 = 0; i0 < param1.data.size(); i0++)
        {
        		_loc_2 = param1.data.get(i0);

            this.data.put(_loc_2,  param1.data.get(_loc_2));
        }
        return;
    }//end

    public void  cleanUp ()
    {
        String _loc_1 =null ;
        this.zid = "";
        for(int i0 = 0; i0 < this.data.size(); i0++)
        {
        		_loc_1 = this.data.get(i0);

            delete this.data.get(_loc_1);
        }
        this.data = new Dictionary();
        return;
    }//end



