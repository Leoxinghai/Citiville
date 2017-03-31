package Classes.actions;

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
    public class ActionPlayAnimation extends NPCAction
    {
        protected double m_actionTime =0;
        protected String m_animation =null ;
        protected String m_originalAnimation ="static";

        public  ActionPlayAnimation (NPC param1 ,String param2 ,double param3 )
        {
            super(param1);
            this.m_animation = param2;
            this.m_actionTime = param3;
            return;
        }//end  

         public void  update (double param1 )
        {
            this.m_actionTime = this.m_actionTime - param1;
            if (this.m_actionTime <= 0)
            {
                m_npc.getStateMachine().removeState(this);
            }
            return;
        }//end  

         public void  enter ()
        {
            super.enter();
            this.m_originalAnimation = m_npc.animation;
            m_npc.animation = this.m_animation;
            return;
        }//end  

         public void  reenter ()
        {
            super.reenter();
            this.m_originalAnimation = m_npc.animation;
            m_npc.animation = this.m_animation;
            return;
        }//end  

         public void  removed ()
        {
            m_npc.animation = this.m_originalAnimation;
            return;
        }//end  

    }



