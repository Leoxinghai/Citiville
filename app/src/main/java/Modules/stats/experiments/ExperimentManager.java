package Modules.stats.experiments;

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

//import flash.external.*;
//import flash.utils.*;

    public class ExperimentManager
    {
        private Dictionary m_experiments ;

        public  ExperimentManager ()
        {
            this.m_experiments = new Dictionary();
            return;
        }//end

        public void  init ()
        {
            _loc_1 = ExternalInterface.call("getExperiments");
            this.loadExperiments(_loc_1);
            return;
        }//end

        public Experiment  getExperiment (String param1 )
        {
            return this.m_experiments.get(param1) as Experiment;
        }//end

        public int  getVariant (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.getExperiment(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.variant;
            }
            return _loc_2;
        }//end

        public boolean  inVariant (String param1 ,int param2 )
        {
            return this.getVariant(param1) == param2;
        }//end

        public boolean  inPerfExperiment (String param1 ,String param2 )
        {
            Experiment _loc_5 =null ;
            boolean _loc_3 =false ;
            _loc_4 = this.getExperiment(param1 );
            if (this.getExperiment(param1) && _loc_4.variant)
            {
                _loc_5 = this.getExperiment(param2);
                _loc_3 = _loc_5 && _loc_5.variant;
            }
            return _loc_3;
        }//end

        public XMLList  filterXmlByExperiment (XMLList param1 )
        {
            _loc_3 = null;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            XMLList _loc_2 =new XMLList ();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                if (_loc_3.hasOwnProperty("@_zexp"))
                {
                    if (_loc_3.hasOwnProperty("@_zvar"))
                    {
                        _loc_4 = String(_loc_3.@_zexp);
                        _loc_5 = String(this.getVariant(_loc_4));
                        _loc_6 = String(_loc_3.@_zvar);
                        if (_loc_6.indexOf(",") != -1)
                        {
                            _loc_7 = _loc_6.split(",");
                            if (_loc_7.indexOf(_loc_5) == -1)
                            {
                                continue;
                            }
                        }
                        else if (_loc_6 != _loc_5)
                        {
                            continue;
                        }
                    }
                }
                _loc_2 = _loc_2 + _loc_3;
            }
            return _loc_2;
        }//end

        private void  loadExperiments (Array param1 )
        {
            Object _loc_2 =null ;
            Experiment _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = Experiment.loadObject(_loc_2);
                this.addExperiment(_loc_3);
            }
            return;
        }//end

        public void  addExperiment (Experiment param1 )
        {
            this.m_experiments.put(param1.name,  param1);
            return;
        }//end

        public void  removeExperiment (String param1 )
        {
            delete this.m_experiments.get(param1);
            return;
        }//end

    }



