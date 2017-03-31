package Modules.workers;

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
//import flash.utils.*;

    public class HunterData
    {
        public String m_state ;
        public int m_timestamp ;
        public String m_ZID ;
        public int m_position ;
        public NPC m_hunterReference ;
        public static  String STATE_PATROLLING ="state_patrolling";
        public static  String STATE_SLEEPING ="state_sleeping";
        public static  String COP_STATE ="state";
        public static  String COP_REF ="copref";
        public static  String TIMESTAMP ="ts";

        public  HunterData (Dictionary param1 ,String param2 ,int param3 )
        {
            this.m_state = param1.get(COP_STATE);
            this.m_timestamp = param1.get(TIMESTAMP);
            this.m_hunterReference = param1.get(COP_REF);
            this.m_position = param3;
            this.m_ZID = param2;
            return;
        }//end

        public NPC  getCopReference ()
        {
            return this.m_hunterReference;
        }//end

        public void  setCopReference (NPC param1 )
        {
            this.m_hunterReference = param1;
            return;
        }//end

        public String  getState ()
        {
            return this.m_state;
        }//end

        public void  setState (String param1 )
        {
            this.m_state = param1;
            return;
        }//end

        public int  getTimestamp ()
        {
            return this.m_timestamp;
        }//end

        public void  setTimestamp (int param1 )
        {
            this.m_timestamp = param1;
            return;
        }//end

        public String  getZID ()
        {
            return this.m_ZID;
        }//end

        public int  getPosition ()
        {
            return this.m_position;
        }//end

    }



