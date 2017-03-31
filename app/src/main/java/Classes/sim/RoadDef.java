package Classes.sim;

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
    public class RoadDef
    {
        public Road road ;
        public Sidewalk sidewalk ;
        public int x ;
        public int y ;
        public boolean isSidewalk =false ;
        public RoadGraph graph ;
        public Array roads ;
        public Array buildings ;
        public double distance ;
        public RoadDef backPtr ;
        public double gScore ;
        public double hScore ;
        public double fScore ;

        public  RoadDef (Road param1 ,int param2 ,int param3 ,boolean param4 ,Sidewalk param5 =null )
        {
            this.road = param1;
            this.sidewalk = param5;
            this.isSidewalk = param4;
            this.x = param2;
            this.y = param3;
            this.roads = new Array();
            this.buildings = new Array();
            this.graph = null;
            this.distance = 0;
            return;
        }//end

        public MapResource  tile ()
        {
            return this.road ? (this.road) : (this.sidewalk);
        }//end

        public double  ComputeBuildingScore ()
        {
            MapResource _loc_4 =null ;
            Neighborhood _loc_5 =null ;
            Mall _loc_6 =null ;
            double _loc_1 =0;
            _loc_2 =Global.gameSettings().getNumber("carHarvestContainerBuildingModifier",1);
            int _loc_3 =0;
            while (_loc_3 < this.buildings.length())
            {

                _loc_4 = this.buildings.get(_loc_3);
                if (_loc_4 instanceof Residence)
                {
                    _loc_1 = _loc_1 + 1;
                }
                if (_loc_4 instanceof Business || _loc_4 instanceof SocialBusiness || _loc_4 instanceof Attraction)
                {
                    _loc_1 = _loc_1 + 1;
                }
                if (_loc_4 instanceof Municipal)
                {
                    _loc_1 = _loc_1 + 1;
                }
                if (_loc_4 instanceof Neighborhood)
                {
                    _loc_5 =(Neighborhood) _loc_4;
                    _loc_1 = _loc_1 + _loc_5.slots.length * _loc_2;
                }
                if (_loc_4 instanceof Mall)
                {
                    _loc_6 =(Mall) _loc_4;
                    _loc_1 = _loc_1 + _loc_6.slots.length * _loc_2;
                }
                _loc_3++;
            }
            return _loc_1;
        }//end

        public static Road  extractRoad (RoadDef param1 ,...args )
        {
            return param1.road;
        }//end

    }


