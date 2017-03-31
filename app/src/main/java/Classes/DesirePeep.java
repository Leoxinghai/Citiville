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

import Classes.Desires.*;
import Classes.sim.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class DesirePeep extends Peep
    {
        protected Array m_desires ;
        public boolean vacationer =false ;
        public boolean wanderer =false ;
        public boolean stubborn =false ;

        public  DesirePeep (String param1 ,boolean param2 )
        {
            super(param1, param2);

            this.m_desires = new Array();
            return;
        }//end

        public Array  getDesires ()
        {
            return this.m_desires;
        }//end

        public void  setDesires (Array param1 )
        {

            int ii =param1.length -1;
            this.m_desires = param1;
            return;
        }//end

        public Desire  findDesireByType (String param1 )
        {
            Desire _loc_3 =null ;
            _loc_2 = (Class)getDefinitionByName("Classes.Desires."+param1)


            for(int i0 = 0; i0 < this.m_desires.size(); i0++)
            {
            		_loc_3 = this.m_desires.get(i0);

                if (_loc_3 instanceof _loc_2)
                {
                    return _loc_3;
                }
            }
            return null;
        }//end

        public boolean  setResistThresholdByType (String param1 ,int param2 )
        {
            Desire _loc_4 =null ;


            //add by xinghai
            //param1 = "DWanderRoads";

            _loc_3 = (Class)getDefinitionByName("Classes.Desires."+param1)



            for(int i0 = 0; i0 < this.m_desires.size(); i0++)
            {
            	_loc_4 = this.m_desires.get(i0);

                if (_loc_4 instanceof _loc_3)
                {
                    _loc_4.resistThreshold = param2;
                    return true;
                }
            }
            return false;
        }//end

        public boolean  insertDesireBeforePivot (Desire param1 ,String param2 )
        {
            _loc_3 = (Class)getDefinitionByName("Classes.Desires."+param2)
            int _loc_4 =0;


            while (_loc_4 < this.m_desires.length())
            {

                if (this.m_desires.get(_loc_4) instanceof _loc_3)
                {
                    this.m_desires.splice(_loc_4, 0, param1);
                    return true;
                }
                _loc_4++;
            }
            return false;
        }//end

        public boolean  insertDesireBeforeMultiplePivots (Desire param1 ,Array param2 )
        {
            String _loc_4 =null ;
            boolean _loc_3 =false ;

            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_4 = param2.get(i0);

                _loc_3 = this.insertDesireBeforePivot(param1, _loc_4);
                if (_loc_3)
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  insertDesireAfterPivot (Desire param1 ,String param2 )
        {

            _loc_3 = (Class)getDefinitionByName("Classes.Desires."+param2)
            int _loc_4 =0;
            while (_loc_4 < this.m_desires.length())
            {

                if (this.m_desires.get(_loc_4) instanceof _loc_3)
                {
                    this.m_desires.splice((_loc_4 + 1), 0, param1);
                    return true;
                }
                _loc_4++;
            }
            return false;
        }//end

         protected IActionSelection  makeActionSelection ()
        {
            return new DesireActionSelection(this);
        }//end

    }




