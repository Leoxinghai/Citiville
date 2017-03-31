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
    public class WorkerManager
    {
        private Dictionary m_workerMap ;
        private String m_featureName ;
        public static  String FEATURE_FACTORIES ="factories";
        public static  String FEATURE_COPS_N_BANDITS ="copsNBandits";
        public static  String FEATURE_ANIMAL_RESCUE ="animalRescue";
        public static  String FEATURE_TRAINS ="trains";

        public  WorkerManager (String param1)
        {
            this.m_workerMap = new Dictionary();
            this.m_featureName = param1;
            return;
        }//end

        public String  featureName ()
        {
            return this.m_featureName;
        }//end

        public void  cleanUp ()
        {
            String _loc_1 =null ;
            Workers _loc_2 =null ;
            if (this.m_workerMap)
            {
                for(int i0 = 0; i0 < this.m_workerMap.size(); i0++)
                {
                		_loc_1 = this.m_workerMap.get(i0);

                    _loc_2 = this.m_workerMap.get(_loc_1);
                    if (_loc_2)
                    {
                        _loc_2.cleanUp();
                    }
                    delete this.m_workerMap.get(_loc_1);
                }
            }
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            Workers _loc_4 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_2 = param1.get(i0);

                    _loc_3 = param1.get(_loc_2);
                    _loc_4 = this.createWorkers(this.m_featureName);
                    _loc_4.loadObject(_loc_3);
                    this.m_workerMap.put(_loc_2,  _loc_4);
                }
            }
            return;
        }//end

        public void  preserveAndLoadObject (Object param1 )
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            Workers _loc_4 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_2 = param1.get(i0);

                    if (this.m_workerMap.get(_loc_2))
                    {
                        (this.m_workerMap.get(_loc_2) as Workers).preserveAndLoadObject(param1.get(_loc_2));
                        continue;
                    }
                    _loc_3 = param1.get(_loc_2);
                    _loc_4 = this.createWorkers(this.m_featureName);
                    _loc_4.loadObject(_loc_3);
                    this.m_workerMap.put(_loc_2,  _loc_4);
                }
            }
            return;
        }//end

        public Workers  getWorkers (String param1 )
        {
            Workers _loc_2 =null ;
            if (this.m_workerMap.hasOwnProperty(param1))
            {
                _loc_2 = this.m_workerMap.get(param1);
            }
            return _loc_2;
        }//end

        public boolean  addWorkers (String param1 ,Workers param2 )
        {
            if (this.m_workerMap.hasOwnProperty(param1))
            {
                return false;
            }
            this.m_workerMap.put(param1,  param2);
            return true;
        }//end

        public Workers  addAndGetWorkers (String param1 )
        {
            _loc_2 = this.getWorkers(param1 );
            if (!_loc_2)
            {
                _loc_2 = this.createWorkers(this.m_featureName);
                this.addWorkers(param1, _loc_2);
            }
            return _loc_2;
        }//end

        public boolean  updateWorkers (String param1 ,Workers param2 )
        {
            if (!this.m_workerMap.hasOwnProperty(param1))
            {
                return false;
            }
            this.m_workerMap.put(param1,  param2);
            return true;
        }//end

        public boolean  hasWorkers (String param1 )
        {
            _loc_2 = this.getWorkers(param1 );
            return _loc_2 && _loc_2.getWorkerCount() > 0;
        }//end

        public Workers  resetWorkers (String param1 ,boolean param2 =true )
        {
            Workers _loc_3 =null ;
            if (this.m_workerMap.hasOwnProperty(param1))
            {
                _loc_3 = this.m_workerMap.get(param1);
                this.m_workerMap.get(param1).cleanUp(param2);
            }
            return _loc_3;
        }//end

        public Workers  clearWorkers (String param1 )
        {
            Workers _loc_2 =null ;
            if (this.m_workerMap.hasOwnProperty(param1))
            {
                _loc_2 = this.m_workerMap.get(param1);
                delete this.m_workerMap.get(param1);
            }
            return _loc_2;
        }//end

        protected Workers  createWorkers (String param1 )
        {
            Workers _loc_2 =null ;
            switch(param1)
            {
                case FEATURE_FACTORIES:
                {
                    _loc_2 = new FactoryWorkers();
                    break;
                }
                case FEATURE_COPS_N_BANDITS:
                case FEATURE_ANIMAL_RESCUE:
                {
                    _loc_2 = new HunterPreyWorkers();
                    break;
                }
                case FEATURE_TRAINS:
                {
                    _loc_2 = new TrainWorkers();
                    break;
                }
                default:
                {
                    _loc_2 = new Workers();
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

    }



