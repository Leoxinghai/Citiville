package Modules.crew;

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

    public class CrewMap
    {
        private Dictionary m_crewMap ;

        public  CrewMap ()
        {
            this.m_crewMap = new Dictionary();
            return;
        }//end

        public void  cleanUp ()
        {
            Object _loc_1 =null ;
            Crew _loc_2 =null ;
            if (this.m_crewMap)
            {
                for(int i0 = 0; i0 < this.m_crewMap.size(); i0++) 
                {
                		_loc_1 = this.m_crewMap.get(i0);

                    _loc_2 =(Crew) this.m_crewMap.get(_loc_1);
                    if (_loc_2)
                    {
                        _loc_2.cleanUp();
                    }
                    delete this.m_crewMap.get(_loc_1);
                }
            }
            this.m_crewMap = null;
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            Crew _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = param1.get(_loc_2);
                _loc_4 = new Crew();
                _loc_4.loadObject(_loc_3);
                this.m_crewMap.put(_loc_2,  _loc_4);
            }
            return;
        }//end

        public Crew  getCrewByObject (GameObject param1 )
        {
            return this.getCrewById(param1.getId());
        }//end

        public Crew  getCrewById (double param1 )
        {
            return this.m_crewMap.get(param1) as Crew;
        }//end

        public int  getCrewCountById (double param1 )
        {
            _loc_2 = this.getCrewById(param1 );
            return _loc_2 ? (_loc_2.count) : (0);
        }//end

        public int  getCrewCountByObject (GameObject param1 )
        {
            return this.getCrewCountById(param1.getId());
        }//end

        public void  addCrewById (double param1 ,String param2 )
        {
            Crew _loc_4 =null ;
            _loc_3 = this.getCrewById(param1 );
            if (_loc_3)
            {
                _loc_3.add(param2);
            }
            else
            {
                _loc_4 = new Crew();
                _loc_4.add(param2);
                this.m_crewMap.put(param1,  _loc_4);
            }
            return;
        }//end

        public void  addCrewByObject (GameObject param1 ,String param2 )
        {
            this.addCrewById(param1.getId(), param2);
            return;
        }//end

    }



