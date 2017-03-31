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

import Classes.*;
//import flash.utils.*;

    public class HunterPreyWorkers extends Workers
    {
        public static  String FEATURE_HUNTER_PREY_BUCKET ="cops";

        public  HunterPreyWorkers ()
        {
            return;
        }//end

        public String  getState (int param1 )
        {
            return getWorkerData(param1, HunterData.COP_STATE, "");
        }//end

        public void  setState (int param1 ,String param2 )
        {
            setWorkerData(param1, HunterData.COP_STATE, param2);
            return;
        }//end

        public int  getTimestamp (int param1 )
        {
            return uint(getWorkerData(param1, HunterData.TIMESTAMP, 0));
        }//end

        public void  setTimestamp (int param1 ,int param2 )
        {
            setWorkerData(param1, HunterData.TIMESTAMP, param2);
            return;
        }//end

        public void  setNpcOwner (int param1 ,NPC param2 )
        {
            setWorkerData(param1, HunterData.COP_REF, param2);
            return;
        }//end

        public HunterData  getNpcOwner (NPC param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < getWorkerCount())
            {

                if (m_workers.get(_loc_2).data.get(HunterData.COP_REF) == param1)
                {
                    return this.getHunterData(_loc_2);
                }
                _loc_2++;
            }
            return null;
        }//end

        public Array  getMultipleNpcOwners (Array param1 )
        {
            NPC _loc_3 =null ;
            HunterData _loc_4 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_4 = this.getNpcOwner(_loc_3);
                if (_loc_4 != null)
                {
                    _loc_2.push(_loc_4);
                }
            }
            return _loc_2;
        }//end

        public Array  getAllCopData ()
        {
            Dictionary _loc_3 =null ;
            HunterData _loc_4 =null ;
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            while (_loc_2 < getWorkerCount())
            {

                _loc_3 = m_workers.get(_loc_2).data;
                _loc_4 = new HunterData(_loc_3, m_workers.get(_loc_2).zid, _loc_2);
                _loc_1.push(_loc_4);
                _loc_2++;
            }
            return _loc_1;
        }//end

        public HunterData  getHunterData (int param1 )
        {
            return param1 > -1 ? (new HunterData(m_workers.get(param1).data, m_workers.get(param1).zid, param1)) : (null);
        }//end

        public void  setHunterData (HunterData param1 )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put(HunterData.COP_STATE,  param1.getState());
            _loc_2.put(HunterData.TIMESTAMP,  param1.getTimestamp());
            _loc_2.put(HunterData.COP_REF,  param1.getCopReference());
            m_workers.get(param1.getPosition()).data = _loc_2;
            return;
        }//end

    }



