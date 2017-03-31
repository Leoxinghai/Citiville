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
import Engine.*;

    public class POI
    {
        private MapResource m_mapResource ;
        private int m_attraction ;
        private double m_probability ;
        private int m_influenceRadius ;
        private int m_maxNPCs ;
        private int m_maxLoiterActions ;
        private Array m_actions ;

        public  POI (MapResource param1 ,int param2 ,int param3 ,int param4 ,int param5 )
        {
            this.m_mapResource = param1;
            this.m_attraction = param2;
            this.m_influenceRadius = param3;
            this.m_maxNPCs = param4;
            this.m_maxLoiterActions = param5;
            return;
        }//end

        public void  addAction (String param1 )
        {
            if (this.m_actions == null)
            {
                this.m_actions = new Array();
            }
            this.m_actions.push(param1);
            return;
        }//end

        public int  attraction ()
        {
            return this.m_attraction;
        }//end

        public int  influenceRadius ()
        {
            return this.m_influenceRadius;
        }//end

        public int  maxNPCs ()
        {
            return this.m_maxNPCs;
        }//end

        public double  probability ()
        {
            return this.m_probability;
        }//end

        public void  setProbability (double param1 )
        {
            if (param1 > 0)
            {
                this.m_probability =(Number) this.m_attraction / param1;
            }
            else
            {
                this.m_probability = 0;
            }
            return;
        }//end

        public String  action ()
        {
            return MathUtil.randomElement(this.m_actions);
        }//end

        public MapResource  resource ()
        {
            return this.m_mapResource;
        }//end

        public int  maxLoiterActions ()
        {
            return this.m_maxLoiterActions;
        }//end

        public void  calculateAttraction (double param1 )
        {
            _loc_2 = param1/this.m_influenceRadius ;
            this.m_attraction = this.m_attraction * (1 - _loc_2);
            return;
        }//end

        public POI  clone ()
        {
            POI _loc_1 =new POI(this.m_mapResource ,this.m_attraction ,this.m_influenceRadius ,this.m_maxNPCs ,this.m_maxLoiterActions );
            int _loc_2 =0;
            while (_loc_2 < this.m_actions.length())
            {

                _loc_1.addAction(this.m_actions.get(_loc_2));
                _loc_2++;
            }
            return _loc_1;
        }//end

    }


