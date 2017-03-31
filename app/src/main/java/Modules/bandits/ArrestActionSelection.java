package Modules.bandits;

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
import Classes.actions.*;
import Classes.sim.*;

    public class ArrestActionSelection implements IActionSelection
    {
        public boolean m_isCop ;
        public NPC m_npc ;
        public String m_state ;
        public String m_arrestState ;
        public String m_defaultState ="idle";
        public static  String STATE_LOOPING ="looping";
        public static  String STATE_ARREST ="arrest";
        public static  String STATE_DONE ="done";

        public  ArrestActionSelection (NPC param1 ,boolean param2 ,String param3 ,String param4 ="idle")
        {
            this.m_isCop = param2;
            this.m_npc = param1;
            this.m_state = STATE_LOOPING;
            this.m_arrestState = param3;
            this.m_defaultState = param4;
            return;
        }//end

        public Array  getNextActions ()
        {
            _loc_1 = this.m_isCop ? (this.m_arrestState) : (this.m_defaultState);
            return .get(new ActionPlayAnimationOneLoop(this.m_npc, _loc_1));
        }//end

    }



