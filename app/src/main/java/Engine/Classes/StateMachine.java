package Engine.Classes;

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

//import flash.events.*;
//import flash.utils.*;
import Classes.actions.*;

import com.xinghai.Debug;

    public class StateMachine extends EventDispatcher
    {
        protected Array m_states ;

        public  StateMachine ()
        {
            this.m_states = new Array();

            return;
        }//end

        public boolean  addState (State param1 ,boolean param2 =false )
        {

            Array _loc_6 =null ;
            _loc_3 = this.getState ();
            _loc_4 = State.NORMAL_INTERRUPT;
            boolean _loc_5 =false ;

            if (param2)
            {
                _loc_6 = .get(param1);
                this.m_states = _loc_6.concat(this.m_states);
                param1.added();
                if (_loc_3 == null)
                {
                    param1.enter();
                }
                _loc_5 = true;
            }
            else
            {
                if (_loc_3)
                {
                    _loc_4 = _loc_3.getInterrupt();
                    if (_loc_4 != State.NO_INTERRUPT)
                    {
                        if (_loc_4 == State.FULL_INTERRUPT)
                        {
                            this.removeState(_loc_3);
                        }
                        else
                        {
                            _loc_3.exit();
                        }
                        _loc_5 = true;
                    }
                }
                if (_loc_4 != State.NO_INTERRUPT)
                {
                    this.m_states.push(param1);
                    param1.added();
                    param1.enter();
                    _loc_5 = true;
                }
            }
            if (_loc_5)
            {
                dispatchEvent(new Event(Event.CHANGE));
            }
            if (param1 !=null)
            {
                GlobalEngine.log("StateMachine", "Add: " + getQualifiedClassName(param1) + " (states: " + this.m_states.length + ")");
            }
            return _loc_5;
        }//end

        public void  removeState (State param1 )
        {


            State _loc_4 =null ;
            _loc_2 = this.getState ();
            int _loc_3 =0;
            while (_loc_3 < this.m_states.length())
            {

                if (this.m_states.get(_loc_3) == param1)
                {
                    if (this.m_states.get(_loc_3) == _loc_2)
                    {
                        this.m_states.get(_loc_3).exit();
                    }
                    param1.removed();
                    this.m_states.splice(_loc_3, 1);
                    _loc_4 = this.getState();
                    if (_loc_4 && _loc_4 != _loc_2)
                    {
                        _loc_4.reenter();
                    }
                    dispatchEvent(new Event(Event.CHANGE));
                    break;
                }
                _loc_3++;
            }
            if (param1 !=null)
            {
                GlobalEngine.log("StateMachine", "Remove: " + getQualifiedClassName(param1) + " (states: " + this.m_states.length + ")");
            }
            return;
        }//end

        public void  removeAllStates ()
        {
            State _loc_3 =null ;
            _loc_1 = this.getState ();
            if (_loc_1)
            {
                _loc_1.exit();
                dispatchEvent(new Event(Event.CHANGE));
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_states.length())
            {

                _loc_3 =(State) this.m_states.get(_loc_2);
                _loc_3.removed();
                _loc_2++;
            }
            this.m_states = new Array();
            GlobalEngine.log("StateMachine", "Remove all states");
            return;
        }//end

        public State  getState ()
        {
            State _loc_1 =null ;
            if (this.m_states.length())
            {
                _loc_1 = this.m_states.get((this.m_states.length - 1));
            }
            if(_loc_1 instanceof ActionRentPickup) {
            	Debug.debug6("StateMachine.getState" +_loc_1);
            }
            return _loc_1;
        }//end

        public Array  getStates ()
        {
            return this.m_states;
        }//end

        public void  popState ()
        {

            if (this.m_states.length > 0)
            {
                this.removeState(this.m_states.get((this.m_states.length - 1)) as State);
            }
            return;
        }//end

        public void  update (double param1 )
        {
            _loc_2 = this.getState ();
            if (_loc_2)
            {
                _loc_2.update(param1);
            }
            return;
        }//end

    }



