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

//import flash.geom.*;
    public class DisplayLocationsDefinition
    {
        private Array m_mapDisplayLocations ;
        private Array m_dialogDisplayLocations ;

        public  DisplayLocationsDefinition ()
        {
            this.m_mapDisplayLocations = new Array();
            this.m_dialogDisplayLocations = new Array();
            return;
        }//end

        public Array  mapDisplayLocations ()
        {
            return this.m_mapDisplayLocations;
        }//end

        public Array  dialogDisplayLocations ()
        {
            return this.m_dialogDisplayLocations;
        }//end

        public void  loadObject (XML param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 = param1.child("location");
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.attribute("id").get(0);
                if (_loc_3.attribute("mapX") && _loc_3.attribute("mapY"))
                {
                    this.m_mapDisplayLocations.put(_loc_4,  new Point(parseInt(_loc_3.attribute("mapX")), parseInt(_loc_3.attribute("mapY"))));
                }
                if (_loc_3.attribute("dialogX") && _loc_3.attribute("dialogY"))
                {
                    this.m_dialogDisplayLocations.put(_loc_4,  new Point(parseInt(_loc_3.attribute("dialogX")), parseInt(_loc_3.attribute("dialogY"))));
                }
            }
            return;
        }//end

        public Point  getMapDisplayLocationById (String param1 )
        {
            return this.m_mapDisplayLocations.get(param1);
        }//end

        public Point  getDialogDisplayLocationById (String param1 )
        {
            return this.m_dialogDisplayLocations.get(param1);
        }//end

    }




