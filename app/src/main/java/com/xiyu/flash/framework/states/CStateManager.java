package com.xiyu.flash.framework.states;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

import com.xiyu.flash.framework.graphics.Graphics2D;
    public class CStateManager implements IStateManager {

        public void  popState (){
            IState newState ;
            if (this.stack.length() == 0)
            {
                return;
            };
            IState oldState =(IState)this.stack.pop();
            oldState.onExit();
            if (this.stack.length() > 0)
            {
            	newState=(IState)this.stack.elementAt((this.stack.length()-1));
                newState.onPop();
            };
        }
        public void  changeState (String id ){


            IState state ;
            IState newState=(IState)this.states.get(id);
            if (newState == null)
            {
                ////throw (new ArgumentError((("ID " + id) + " instanceof unbound, cannot change states.")));
            };
            int aNumStates =this.stack.length() ;
            int i =(aNumStates -1);
            while (i >= 0)
            {
            	state=(IState)this.stack.elementAt(i);
                state.onExit();
                i--;
            };
            this.stack = new Array();
            this.stack.push(newState);
            newState.setView();
            newState.onEnter();
            System.out.println("CStateManager.changeState"+":"+":"+stack.length());

        }

        private Dictionary states ;
        private Array stack ;

        public void  draw (Graphics2D g ){
            IState state ;
            int aNumStates =this.stack.length() ;
            int i =0;
            while (i < aNumStates)
            {
            	state=(IState)this.stack.elementAt(i);
                state.draw(g);
                i++;
            };
        }

        public void  bindState (String id ,IState state ){
        	this.states.put(id,state);
        }

        public void  pushState (String id ){
            IState oldState ;
            IState newState=(IState)this.states.elementAt(id);
            if (newState == null)
            {
                //throw (new ArgumentError((("ID " + id) + " instanceof unbound, cannot push onto stack.")));
            };
            if (this.stack.length() > 0)
            {
            	oldState=(IState)this.stack.elementAt((this.stack.length()-1));
                oldState.onPush();
            };
            this.stack.push(newState);
            newState.onEnter();
            System.out.println("CStateManager.pushState"+":"+":"+stack.length());
        }
        public void  update (){
            int index =(this.stack.length() -1);
            if (index < 0)
            {
                return;
            };
            IState state=(IState)this.stack.elementAt(index);
            state.update();
        }

        public  CStateManager (){
            this.states = new Dictionary();
            this.stack = new Array();
        }
    }


