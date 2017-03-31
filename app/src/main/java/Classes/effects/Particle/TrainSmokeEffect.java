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
import Engine.Helpers.*;

    public class TrainSmokeEffect extends ParticleEffect
    {
        private double m_trainMaxSpeed =1;
        private Vector2 m_smokeOffset ;
        private static  String EFFECT_NAME ="trainSmoke";
        private static  double BASE_SPAWN_PERIOD =1;
        private static  double TRAIN_SPEED_MULT =0.8;

        public  TrainSmokeEffect (MapResource param1 )
        {
            Train _loc_2 =null ;
            XMLList _loc_3 =null ;
            super(param1, EFFECT_NAME);
            spawnPeriod = BASE_SPAWN_PERIOD;
            minVel = 10;
            maxVel = 25;
            minAngle = 255;
            maxAngle = 285;
            minDuration = 1.5;
            maxDuration = 4;
            minRotationVel = 0;
            maxRotationVel = 0;
            loopParticleAnimation = false;
            if (param1 && param1 instanceof Train)
            {
                _loc_2 =(Train) param1;
                _loc_3 = _loc_2.getItem().smokeXml;
                this.m_trainMaxSpeed = _loc_2.getItem().navigateXml.runSpeed;
                this.m_smokeOffset = new Vector2(_loc_3.@offsetX, _loc_3.@offsetY);
            }
            return;
        }//end

         protected Vector2  getTargetOffset ()
        {
            if (this.m_smokeOffset)
            {
                return this.m_smokeOffset;
            }
            return new Vector2(0, 0);
        }//end

        public void  adjustForTrainSpeed (double param1 )
        {
            spawnPeriod = BASE_SPAWN_PERIOD - param1 / this.m_trainMaxSpeed * TRAIN_SPEED_MULT;
            return;
        }//end

    }



