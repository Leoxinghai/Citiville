package Modules.guide;

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

import Modules.guide.actions.*;
    public class GuideSequence
    {
        public String m_name ="";
        public String m_trigger ;
        public Array m_actions ;
        public GuideActionExecutor m_actionExec ;
        private int m_currentAction =-1;
        protected boolean m_completed =false ;
        protected boolean m_active =false ;
        protected Guide m_guide =null ;

        public  GuideSequence (Guide param1 )
        {
            this.m_guide = param1;
            this.m_actions = new Array();
            this.m_actionExec = new GuideActionExecutor();
            return;
        }//end

        public void  setActive (boolean param1 )
        {
            this.m_active = param1;
            return;
        }//end

        public boolean  isActive ()
        {
            return this.m_active;
        }//end

        public boolean  isComplete ()
        {
            return this.m_completed;
        }//end

        public GuideActionExecutor  getActionExec ()
        {
            return this.m_actionExec;
        }//end

        public void  addAction (GuideAction param1 )
        {
            if (param1 != null)
            {
                this.m_actions.push(param1);
            }
            return;
        }//end

        public GuideAction  nextAction ()
        {
            this.m_currentAction++;
            if (this.m_currentAction < this.m_actions.length())
            {
                return this.m_actions.get(this.m_currentAction);
            }
            return null;
        }//end

        public GuideAction  currentAction ()
        {
            return this.m_actions.get(this.m_currentAction);
        }//end

        public void  update (double param1 )
        {
            GuideAction _loc_2 =null ;
            if (!this.m_active)
            {
                return;
            }
            if (this.m_actionExec.getState() == null)
            {
                _loc_2 = this.nextAction();
                if (_loc_2 != null)
                {
                    GlobalEngine.log("Guide", "New action: " + this.m_name + " / " + _loc_2);
                    this.m_actionExec.addState(_loc_2);
                }
                else
                {
                    this.m_completed = true;
                    this.m_active = false;
                }
            }
            this.m_actionExec.update(param1);
            return;
        }//end

        public void  cleanup ()
        {
            this.m_actionExec.removeAllStates();
            return;
        }//end

        public void  start ()
        {
            this.m_active = true;
            _loc_1 = this.nextAction ();
            if (_loc_1 != null)
            {
                this.m_actionExec.addState(_loc_1);
            }
            else
            {
                this.resetActionIndex();
            }
            return;
        }//end

        public void  stop (boolean param1 =false )
        {
            if (param1 !=null)
            {
                this.m_guide.removeDialogs();
                this.m_guide.removeMask();
                this.m_guide.removeArrows();
                this.m_guide.removeGuideTiles();
            }
            this.m_active = false;
            this.m_actionExec.removeAllStates();
            this.resetActionIndex();
            return;
        }//end

        public boolean  createFromXml (XML param1 )
        {
            String name ;
            String trigger ;
            xml = param1;
            if (xml == null)
            {
                return false;
            }
            try
            {
                name = xml.@name;
                trigger = xml.@trigger;
                if (name.length > 0)
                {
                    this.m_name = name;
                }
                else
                {
                    return false;
                }
                if (trigger.length > 0)
                {
                    this.m_trigger = trigger;
                }
                else
                {
                    return false;
                }
            }
            catch (err:Error)
            {
                return false;
            }
            return true;
        }//end

        private void  resetActionIndex ()
        {
            this.m_currentAction = -1;
            return;
        }//end

    }



