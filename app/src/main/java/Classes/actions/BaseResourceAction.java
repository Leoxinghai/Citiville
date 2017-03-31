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
import Engine.Classes.*;
//import flash.geom.*;

    public class BaseResourceAction extends NPCAction
    {
        protected MapResource m_resource ;
        protected boolean m_actionStarted ;
        protected double m_actionTime =0;

        public  BaseResourceAction (NPC param1 ,MapResource param2 )
        {
            super(param1);
            this.m_resource = param2;
            return;
        }//end

        public MapResource  getResource ()
        {
            return this.m_resource;
        }//end

         public void  added ()
        {
            super.added();
            if (this.m_resource)
            {
                this.m_resource.lock();
            }
            return;
        }//end

         public void  removed ()
        {
            super.removed();
            if (this.m_resource)
            {
                this.m_resource.unlock();
            }
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.goToActionPosition();
            this.m_actionStarted = true;
            return;
        }//end

         public void  reenter ()
        {
            this.goToActionPosition();
            this.m_actionStarted = true;
            return;
        }//end

         public void  exit ()
        {
            if (m_npc)
            {
                m_npc.setActionProgress(false);
                m_npc.setActionBarOffset(0, 0);
            }
            super.exit();
            return;
        }//end

        protected void  goToActionPosition ()
        {
            _loc_1 = this.getActionPosition ();
            if (_loc_1 != null && m_npc)
            {
                m_npc.setPosition(_loc_1.x, _loc_1.y);
            }
            return;
        }//end

        protected Point  getActionPosition ()
        {
            return this.m_resource != null ? (this.m_resource.getPositionNoClone().toVector2()) : (new Point());
        }//end

         public int  getInterrupt ()
        {
            return this.m_actionStarted ? (State.NO_INTERRUPT) : (State.NORMAL_INTERRUPT);
        }//end

    }



