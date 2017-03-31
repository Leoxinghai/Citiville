package Classes.effects;

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
import Engine.Helpers.*;
//import flash.geom.*;

    public class ArcMotion
    {
        protected GameObject m_gameObject ;
        protected Point m_endPosition ;
        protected Point m_startPosition ;
        protected Point m_startVelocity ;
        protected double m_gravity ;
        protected Point m_velocity ;
        protected boolean m_complete ;
        protected double m_newPosX ;
        protected double m_newPosY ;
        protected  double DOOBER_DROP_SPEED_FACTOR =1;

        public  ArcMotion (GameObject param1 ,Point param2 ,Point param3 ,double param4 ,double param5 )
        {
            this.m_gameObject = param1;
            this.m_startPosition = param2;
            this.m_endPosition = param3;
            this.m_startVelocity = new Point(0, param4);
            this.m_gravity = param5;
            this.init();
            return;
        }//end

        public void  init ()
        {
            _loc_1 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(this.m_startPosition.x ,this.m_startPosition.y ));
            _loc_2 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(this.m_endPosition.x ,this.m_endPosition.y ));
            _loc_3 = _loc_2.y -_loc_1.y ;
            _loc_4 = this.m_startVelocity.y ;
            _loc_5 = this(-.m_startVelocity.y +Math.sqrt(Math.pow(_loc_4 ,2)-4*0.5*this.m_gravity *(-_loc_3 )))/(2*0.5*this.m_gravity );
            _loc_6 = _loc_2(.x -_loc_1.x )/_loc_5 ;
            this.m_startVelocity.x = _loc_6;
            this.m_velocity = this.m_startVelocity;
            this.m_complete = false;
            return;
        }//end

        public void  update (double param1 )
        {
            double _loc_2 =0;
            Vector3 _loc_3 =null ;
            Point _loc_4 =null ;
            int _loc_5 =0;
            double _loc_6 =0;
            Point _loc_7 =null ;
            Point _loc_8 =null ;
            if (!this.m_complete)
            {
                _loc_2 = param1 * 33;
                _loc_2 = _loc_2 * this.DOOBER_DROP_SPEED_FACTOR;
                if (_loc_2 < 1)
                {
                    _loc_2 = 1;
                }
                _loc_3 = this.m_gameObject.getPosition();
                _loc_4 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(this.m_endPosition.x, this.m_endPosition.y));
                _loc_5 = 0;
                while (_loc_5 < _loc_2)
                {

                    _loc_6 = _loc_2 - _loc_5;
                    if (_loc_6 > 1)
                    {
                        _loc_6 = 1;
                    }
                    this.m_velocity.y = this.m_velocity.y + this.m_gravity * _loc_6;
                    _loc_7 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(_loc_3.x, _loc_3.y));
                    this.m_newPosX = _loc_7.x + this.m_velocity.x * _loc_6;
                    this.m_newPosY = _loc_7.y + this.m_velocity.y * _loc_6;
                    _loc_8 = IsoMath.screenPosToTilePos(this.m_newPosX, this.m_newPosY);
                    _loc_3.x = _loc_8.x;
                    _loc_3.y = _loc_8.y;
                    if (this.m_newPosY >= _loc_4.y)
                    {
                        break;
                    }
                    _loc_5++;
                }
                this.m_gameObject.setPosition(_loc_8.x, _loc_8.y);
                this.m_gameObject.conditionallyReattach();
                this.checkComplete();
            }
            return;
        }//end

        public void  checkComplete ()
        {
            _loc_1 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(this.m_endPosition.x ,this.m_endPosition.y ));
            if (this.m_newPosY >= _loc_1.y)
            {
                this.onComplete();
            }
            return;
        }//end

        public void  onComplete ()
        {
            this.m_complete = true;
            return;
        }//end

        public boolean  isComplete ()
        {
            return this.m_complete;
        }//end

    }



