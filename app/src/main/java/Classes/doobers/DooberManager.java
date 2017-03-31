package Classes.doobers;

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
import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Transactions.*;
import com.adobe.utils.*;
//import flash.geom.*;
//import flash.utils.*;
import Classes.sim.*;

    public class DooberManager implements IGameWorldStateObserver
    {
        protected GameWorld m_world ;
        protected Array m_doobers ;
        protected Dictionary m_batchRemaining ;
        private boolean m_doobersEnabled ;
        private double m_lastDooberClearTime =0;
        private  double DOOBER_RADIUS =25;
        private  double DOOBER_ELLIPSE_A =50;
        private  double DOOBER_GAP =15;
        private  double DOOBER_DELAY =250;
        private  double DOOBER_INITIAL_TIMEOUT =10000;
        private  double DOOBER_FTUE_INITIAL_TIMEOUT =10000000;
        private  double DOOBER_TIMEOUT_INCR =500;
        private  double DOOBER_VELOCITY_RANGE =5;
        private  double DOOBER_VELOCITY_MIN =10;
        public boolean optimizeDooberSpawn =false ;
        private static int m_lastDooberBatchId =0;

        public  DooberManager (GameWorld param1 )
        {
            this.m_batchRemaining = new Dictionary();
            this.m_world = param1;
            param1.addObserver(this);
            this.optimizeDooberSpawn = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MEMORY_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_DOOBER_SPAWN);
            return;
        }//end

        public void  initialize ()
        {
            this.m_doobers = new Array();
            this.m_doobersEnabled = true;
            return;
        }//end

        public Array  doobers ()
        {
            return this.m_doobers;
        }//end

        public void  cleanUp ()
        {
            Doober _loc_1 =null ;
            if (this.m_doobers.length > 0)
            {
                for(int i0 = 0; i0 < this.m_doobers.size(); i0++) 
                {
                		_loc_1 = this.m_doobers.get(i0);

                    _loc_1.cleanUp();
                    _loc_1 = null;
                }
            }
            this.m_doobers = null;
            return;
        }//end

        public int  getDooberCount ()
        {
            return this.m_doobers.length;
        }//end

        public boolean  areDoobersLanded ()
        {
            Doober _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_doobers.size(); i0++) 
            {
            		_loc_1 = this.m_doobers.get(i0);

                if (_loc_1.getState() == Doober.STATE_INITIAL)
                {
                    return false;
                }
            }
            return true;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        protected Doober  createDoober (int param1 ,MapResource param2 ,String param3 ,int param4 ,Item param5 ,double param6 ,double param7 ,double param8 ,double param9 ,double param10 ,int param11 ,double param12 ,Function param13 =null )
        {
            Doober newDoober ;
            batchId = param1;
            spawner = param2;
            dooberName = param3;
            amount = param4;
            ownerItem = param5;
            positionX = param6;
            positionY = param7;
            endPosX = param8;
            endPosY = param9;
            yVelocity = param10;
            timeOut = param11;
            spawnDelay = param12;
            onCollectCallback = param13;
            newDoober = new Doober(spawner, dooberName, amount, ownerItem, new Point(positionX, positionY), new Point(endPosX, endPosY), yVelocity, timeOut, false, null, onCollectCallback, batchId);
            newDoober.addEventListener(Doober.CLEAR_EVENT, this.onDooberClear);
            variant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DOOBER_TRACKING);
            if (variant)
            {
                StatsManager.count("doobers", "doober_creation", "client", dooberName, amount.toString());
            }
            newDoober.setPosition(positionX, positionY);
            this.m_doobers.push(newDoober);
            TimerUtil .callLater (void  ()
            {
                newDoober.setOuter(Global.world);
                newDoober.attach();
                if (!optimizeDooberSpawn)
                {
                    GlobalEngine.viewport.setDirty();
                }
                return;
            }//end
            , spawnDelay);
            return newDoober;
        }//end

        private void  onDooberClear (DooberEvent event )
        {
            _loc_2 = event.batchId ;
            _loc_3 = this.m_batchRemaining.get(_loc_2) ;
            _loc_4 = this.m_batchRemaining.get(_loc_2).batchRemaining -1;
            _loc_3.batchRemaining = _loc_4;
            if (this.m_batchRemaining.get(_loc_2).batchRemaining <= 0)
            {
                if (this.m_batchRemaining.get(_loc_2).callback != null)
                {
                    this.m_batchRemaining.get(_loc_2).callback();
                }
                delete this.m_batchRemaining.get(_loc_2);
            }
            return;
        }//end

        public void  createBatchDoobers (Array param1 ,Item param2 ,double param3 ,double param4 ,boolean param5 =false ,Function param6 =null )
        {
            Point _loc_16 =null ;
            double _loc_17 =0;
            double _loc_18 =0;
            double _loc_19 =0;
            Point _loc_20 =null ;
            Doober _loc_21 =null ;
            _loc_7 = dooberBatchId;
            if (!this.m_doobersEnabled || param1 == null || param1.length <= 0)
            {
                return;
            }
            this.m_batchRemaining.put(_loc_7,  {batchRemaining:param1.length, callback:param6});
            _loc_8 = param1.length ;
            _loc_9 = this.DOOBER_ELLIPSE_A *2/_loc_8 ;
            _loc_10 = this.DOOBER_INITIAL_TIMEOUT ;
            if (Global.guide.isActive())
            {
                _loc_10 = this.DOOBER_FTUE_INITIAL_TIMEOUT;
            }
            double _loc_11 =0;
            double _loc_12 =-1;
            double _loc_13 =0;
            boolean _loc_14 =false ;
            int _loc_15 =0;
            while (_loc_15 < _loc_8)
            {

                if (param1.get(_loc_15).get(1) > 0)
                {
                    _loc_14 = true;
                    _loc_16 = IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(param3, param4));
                    _loc_17 = Math.random() * _loc_9 + (_loc_16.x - this.DOOBER_ELLIPSE_A + _loc_11);
                    if (_loc_17 - _loc_12 < this.DOOBER_GAP)
                    {
                        if (_loc_17 + this.DOOBER_GAP <= _loc_16.x + this.DOOBER_ELLIPSE_A)
                        {
                            _loc_17 = _loc_17 + this.DOOBER_GAP;
                        }
                        else
                        {
                            _loc_17 = _loc_16.x + this.DOOBER_ELLIPSE_A;
                            _loc_11 = -_loc_9;
                        }
                    }
                    _loc_18 = this.getEndEllipticalPos(_loc_16, _loc_17, this.DOOBER_ELLIPSE_A, this.DOOBER_ELLIPSE_A / 2);
                    _loc_19 = -1 * (Math.random() * this.DOOBER_VELOCITY_RANGE + this.DOOBER_VELOCITY_MIN);
                    _loc_20 = IsoMath.screenPosToTilePos(_loc_17, _loc_18);
                    _loc_21 = this.createDoober(_loc_7, param1.get(_loc_15).get(2), param1.get(_loc_15).get(0), Math.ceil(param1.get(_loc_15).get(1)), param2, param3, param4, _loc_20.x, _loc_20.y, _loc_19, _loc_10, _loc_13);
                    if (param5)
                    {
                        _loc_21.isStreanBonusEnabled = false;
                    }
                    _loc_11 = _loc_11 + _loc_9;
                    _loc_12 = _loc_17;
                    _loc_13 = _loc_13 + this.DOOBER_DELAY;
                    _loc_10 = _loc_10 + this.DOOBER_TIMEOUT_INCR;
                }
                _loc_15++;
            }
            if (_loc_14)
            {
                Sounds.play("doober_drop");
            }
            return;
        }//end

        public void  removeDoober (Doober param1 )
        {
            if (!this.m_doobers)
            {
                return;
            }
            _loc_2 = this.m_doobers.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_doobers.splice(_loc_2, 1);
            }
            if (this.m_doobers.length == 0)
            {
                this.m_lastDooberClearTime = GlobalEngine.getTimer();
            }
            if (this.m_lastDooberClearTime != 0)
            {
                this.m_lastDooberClearTime = 0;
                GameTransactionManager.addTransaction(new TUpdateEnergy(), true);
            }
            return;
        }//end

        public void  autoCollect ()
        {
            Doober _loc_2 =null ;
            if (!this.m_doobers)
            {
                return;
            }
            _loc_1 = ArrayUtil.copyArray(this.m_doobers);
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            		_loc_2 = _loc_1.get(i0);

                _loc_2.collectIfIdle();
            }
            return;
        }//end

        public void  createDummyDoober (String param1 ,Point param2 ,Point param3 )
        {
            Doober _loc_4 =new Doober(null ,param1 ,0,null ,param2 ,null ,0,0,true ,param3 );
            _loc_5 = IsoMath.screenPosToTilePos(param2.x ,param2.y );
            _loc_4.setPosition(_loc_5.x, _loc_5.y);
            this.m_doobers.push(_loc_4);
            _loc_4.setOuter(Global.world);
            _loc_4.attach();
            return;
        }//end

        public void  toogleEnableDoobers ()
        {
            this.m_doobersEnabled = !this.m_doobersEnabled;
            return;
        }//end

        public boolean  isDoobersEnabled ()
        {
            return this.m_doobersEnabled;
        }//end

        protected double  getEndPosYAroundBuilding (Point param1 ,double param2 ,double param3 )
        {
            return Math.sqrt(Math.pow(param2, 2) - Math.pow(param3 - param1.x, 2)) + param1.y;
        }//end

        protected double  getEndEllipticalPos (Point param1 ,double param2 ,double param3 ,double param4 )
        {
            return Math.sqrt((Math.pow(param3, 2) * Math.pow(param4, 2) - Math.pow(param4, 2) * Math.pow(param2 - param1.x, 2)) / Math.pow(param3, 2)) + param1.y;
        }//end

        private static int  dooberBatchId ()
        {
            _loc_1 = DooberManager;
            _loc_1.m_lastDooberBatchId = DooberManager.m_lastDooberBatchId + 1;
            return DooberManager.m_lastDooberBatchId++;
        }//end

    }



