package Mechanics.ClientDisplayMechanics;

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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import validation.*;

    public class NotifyGuideMechanic implements IClientGameMechanic, IValidationMechanic
    {
        protected MechanicMapResource m_owner ;
        protected String m_type ;
        protected GenericValidationScript m_validation ;
        protected String m_triggerName ;
        protected String m_event ;
        protected int m_priority ;
        private static Dictionary m_eventTriggers =null ;

        public  NotifyGuideMechanic ()
        {
            return;
        }//end

        public void  detachDisplayObject ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            return;
        }//end

        public boolean  isPixelInside (Point param1 )
        {
            return false;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_type = param2.params.get("type");
            this.m_event = param2.params.get("event");
            this.m_triggerName = param2.params.get("triggerName");
            this.m_priority = param2.params.get("priority");
            if (param2.params.get("validate"))
            {
                this.m_validation = this.m_owner.getItem().getValidation(param2.params.get("validate"));
            }
            register(this.m_event, this.m_triggerName, this.m_priority, this);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return true;
        }//end

        public boolean  isValid ()
        {
            boolean _loc_1 =true ;
            if (this.m_validation)
            {
                _loc_1 = this.m_validation.validate(this.m_owner);
            }
            return _loc_1;
        }//end

        private static void  register (String param1 ,String param2 ,int param3 ,NotifyGuideMechanic param4 )
        {
            Object _loc_5 ={name param2 ,priority ,mechanic };
            if (!m_eventTriggers)
            {
                m_eventTriggers = new Dictionary();
                Global.world.addEventListener(param1, onTriggerEvent, false, 0, true);
            }
            if (!m_eventTriggers.get(param1))
            {
                m_eventTriggers.put(param1,  new Array());
            }
            boolean _loc_6 =false ;
            int _loc_7 =0;
            while (_loc_7 < m_eventTriggers.get(param1).length())
            {

                if (m_eventTriggers.get(param1).get(_loc_7).priority > param3)
                {
                    m_eventTriggers.get(param1).splice(_loc_7, 0, _loc_5);
                    _loc_6 = true;
                }
                _loc_7++;
            }
            if (!_loc_6)
            {
                m_eventTriggers.get(param1).push(_loc_5);
            }
            return;
        }//end

        private static Object  buildEntry (String param1 ,int param2 ,NotifyGuideMechanic param3 )
        {
            return {name:param1, priority:param2, mechanic:param3};
        }//end

        private static void  onTriggerEvent (Event event )
        {
            dequeueTrigger(event.type);
            return;
        }//end

        public static void  onGuideComplete (XMLList param1 )
        {
            _loc_2 = (String)(param1.@event);
            if (_loc_2 && _loc_2.length > 0)
            {
                dequeueTrigger(_loc_2);
            }
            return;
        }//end

        private static void  dequeueTrigger (String param1 )
        {
            boolean _loc_3 =false ;
            int _loc_4 =0;
            _loc_2 = m_eventTriggers.get(param1);
            if (_loc_2)
            {
                _loc_3 = false;
                _loc_4 = 0;
                while (_loc_4 < _loc_2.length())
                {

                    if (_loc_2.get(_loc_4).mechanic.isValid())
                    {
                        Global.guide.notify(_loc_2.get(_loc_4).name);
                        _loc_3 = true;
                        break;
                    }
                    _loc_4++;
                }
                if (_loc_3)
                {
                    m_eventTriggers.put(param1,  _loc_2.splice(_loc_4, 1));
                }
            }
            return;
        }//end

    }



