package Classes.effects.Particle;

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
//import flash.display.*;
//import flash.geom.*;

    public class PixieDustEffect extends ParticleEffect
    {
        protected Point m_previousTargetPos ;
        protected  double TARGET_SPEED_THRESHOLD =300;
        private static  String EFFECT_NAME ="pixieDust";

        public  PixieDustEffect (MapResource param1 )
        {
            super(param1, EFFECT_NAME);
            this.m_previousTargetPos = new Point();
            minVel = 0;
            maxVel = 100;
            minAngle = 0;
            maxAngle = 360;
            minDuration = 0.5;
            maxDuration = 1;
            minRotationVel = -500;
            maxRotationVel = 500;
            minStartScale = 0.5;
            maxStartScale = 1.5;
            endScale = 0;
            startAlpha = 100;
            endAlpha = 0;
            initRotRandomness = 360;
            m_particleContainer.scaleX = 0.5;
            m_particleContainer.scaleY = 0.5;
            return;
        }//end  

         protected void  spawnNewParticles (double param1 )
        {
            super.spawnNewParticles(param1);
            _loc_2 = m_mapResource.displayObject;
            if (m_isActive)
            {
                this.m_previousTargetPos.x = _loc_2.x;
                this.m_previousTargetPos.y = _loc_2.y;
            }
            return;
        }//end  

         protected int  getNumNewParticles (double param1 )
        {
            _loc_2 = m_mapResource.displayObject;
            double _loc_3 =0;
            if (this.m_previousTargetPos.x != _loc_2.x || this.m_previousTargetPos.y != _loc_2.y)
            {
                if (Math.sqrt(Math.pow(this.m_previousTargetPos.x - _loc_2.x, 2) + Math.pow(this.m_previousTargetPos.y - _loc_2.y, 2)) > this.TARGET_SPEED_THRESHOLD)
                {
                    _loc_3 = 1 + Math.floor(2 * Math.random());
                }
                else
                {
                    _loc_3 = Math.floor(1.5 * Math.random());
                }
            }
            return _loc_3;
        }//end  

         public boolean  animate (int param1 )
        {
            _loc_2 = m_mapResource.displayObject;
            if (_loc_2.scaleX == 1)
            {
                m_particleContainer.scaleX = 0.125;
                m_particleContainer.scaleY = 0.125;
            }
            return super.animate(param1);
        }//end  

    }



