package Classes.gates;

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
import Modules.stats.experiments.*;
//import flash.utils.*;

    public class AOEGate extends AbstractGate
    {
        private static int m_optimizeAOEMap =-1;

        public  AOEGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public boolean  checkForKeys ()
        {
            String _loc_3 =null ;
            boolean _loc_1 =true ;
            _loc_2 = this.getAOEMap ();
            for(int i0 = 0; i0 < m_keys.size(); i0++) 
            {
            		_loc_3 = m_keys.get(i0);

                if (_loc_2.get(_loc_3) == null || _loc_2.get(_loc_3) < m_keys.get(_loc_3))
                {
                    _loc_1 = false;
                    break;
                }
            }
            return _loc_1;
        }//end

        protected Dictionary  getAOEMap ()
        {
            Array _loc_3 =null ;
            MapResource _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            String _loc_9 =null ;
            _loc_1 =Global.world.getObjectById(m_targetObjectId )as MapResource ;
            _loc_2 =(double)(_loc_1.getItem ().shockwaveXml.@radius);
            _loc_4 = this.getKeyArray ();
            if (m_optimizeAOEMap < 0)
            {
                m_optimizeAOEMap = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_GET_OBJECTS);
            }
            if (m_optimizeAOEMap > 0)
            {
                _loc_7 = _loc_1.centerPosition.x;
                _loc_8 = _loc_1.centerPosition.y;
                _loc_3 = Global.world.getCollisionMap().getObjectsInRect(_loc_7 - _loc_2, _loc_8 - _loc_2, _loc_7 + _loc_2 + 1, _loc_8 + _loc_2 + 1);
            }
            else
            {
                _loc_3 = Global.world.getObjects();
            }
            Dictionary _loc_5 =new Dictionary ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_6 = _loc_3.get(i0);

                if (ItemBonus.getDistanceBetweenObjs(_loc_6, _loc_1) <= _loc_2)
                {
                    for(int i0 = 0; i0 < _loc_6.getItem().getItemKeywords().size(); i0++) 
                    {
                    		_loc_9 = _loc_6.getItem().getItemKeywords().get(i0);

                        if (_loc_4.indexOf(_loc_9) >= 0)
                        {
                            _loc_5.put(_loc_9,  _loc_5.get(_loc_9) ? ((_loc_5.get(_loc_9) + 1)) : (1));
                        }
                    }
                }
            }
            return _loc_5;
        }//end

         public int  keyProgress (String param1 )
        {
            _loc_2 = this.getAOEMap ();
            return Math.min(_loc_2.get(param1), m_keys.get(param1));
        }//end

         protected void  takeKeys ()
        {
            return;
        }//end

    }


