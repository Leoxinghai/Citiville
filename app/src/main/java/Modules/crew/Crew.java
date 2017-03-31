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

import Classes.util.*;


    public class Crew
    {
        private Vector<String> m_crewList;

        public  Crew ()
        {
            this.m_crewList = new Vector<String>();
            return;
        }//end

        public String Vector  list ().<>
        {
            return this.m_crewList;
        }//end

        public double  count ()
        {
            return this.m_crewList ? (this.m_crewList.length()) : (0);
        }//end

        public void  cleanUp ()
        {
            Object _loc_1 =null ;
            if (this.m_crewList)
            {
                for(int i0 = 0; i0 < this.m_crewList.size(); i0++)
                {
                		_loc_1 = this.m_crewList.get(i0);

                    delete this.m_crewList.get(_loc_1);
                }
            }
            this.m_crewList = null;
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = GameUtil.formatServerUid(_loc_2);
                this.add(_loc_3);
            }
            return;
        }//end

        public void  add (String param1 )
        {
            this.m_crewList.push(param1);
            return;
        }//end

        public void  remove (String param1 )
        {
            _loc_2 = this.m_crewList.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_crewList.splice(_loc_2, 1);
            }
            return;
        }//end

    }



