package Modules.guide.actions;

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

import Engine.Managers.*;
//import flash.events.*;

    public class GAWaitForPredicateOrClickAnywhere extends GuideAction
    {
        protected Function m_predicate ;
        protected XMLList m_xmldef ;
        protected boolean m_updated =false ;

        public  GAWaitForPredicateOrClickAnywhere ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"function");
            if (!_loc_2)
            {
                return false;
            }
            this.m_xmldef = _loc_2;
            this.m_predicate = m_guide.verifyCallback(String(_loc_2.@name));
            if (this.m_predicate == null)
            {
                ErrorManager.addError("Tutorial WaitForPredicateOrClickAnywhere got invalid callback: " + _loc_2.@name);
                return false;
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            boolean _loc_2 =false ;
            super.update(param1);
            if (this.m_predicate != null)
            {
                _loc_2 = this.m_predicate(this.m_xmldef);
                if (_loc_2)
                {
                    removeState(this);
                }
            }
            this.m_updated = true;
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            this.m_updated = false;
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            this.m_updated = false;
            return;
        }//end

         public void  exit ()
        {
            super.exit();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            this.m_updated = false;
            return;
        }//end

         public void  removed ()
        {
            super.removed();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            this.m_updated = false;
            return;
        }//end

        protected void  stageClickHandler (MouseEvent event )
        {
            if (!this.m_updated)
            {
                return;
            }
            _loc_2 = this.m_predicate(this.m_xmldef );
            if (!_loc_2)
            {
                m_seq.stop();
            }
            return;
        }//end

    }



