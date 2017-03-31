package Modules.quest.guide;

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

import Modules.guide.*;
import Modules.guide.actions.*;
import Modules.quest.Managers.*;

    public class QuestGuideSequence extends GuideSequence
    {
        protected GameQuest m_quest ;

        public  QuestGuideSequence (Guide param1 ,GameQuest param2 )
        {
            super(param1);
            this.m_quest = param2;
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
                    m_name = name;
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

         public void  stop (boolean param1 =false )
        {
            super.stop(true);
            return;
        }//end

         public void  update (double param1 )
        {
            GuideAction _loc_2 =null ;
            if (!m_active)
            {
                return;
            }
            if (m_actionExec.getState() == null)
            {
                _loc_2 = nextAction();
                if (_loc_2 != null)
                {
                    GlobalEngine.log("Guide", "New action: " + m_name + " / " + _loc_2);
                    m_actionExec.addState(_loc_2);
                }
                else
                {
                    this.startNextGuide();
                    m_completed = true;
                    m_active = false;
                }
            }
            m_actionExec.update(param1);
            return;
        }//end

        protected void  startNextGuide ()
        {
            _loc_1 = this.m_quest.getFirstIncompleteTask ();
            if (!_loc_1 || !_loc_1.guide)
            {
                return;
            }
            if (_loc_1.guide == m_name)
            {
                return;
            }
            GANotify _loc_2 =new GANotify ();
            _loc_2.setGuide(m_guide, this);
            _loc_2.notify = _loc_1.guide;
            GlobalEngine.log("Guide", "New action: " + m_name + " / " + _loc_2);
            m_actionExec.addState(_loc_2);
            return;
        }//end

    }



