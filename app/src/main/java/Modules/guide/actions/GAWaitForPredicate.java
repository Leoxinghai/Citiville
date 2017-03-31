﻿package Modules.guide.actions;

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
    public class GAWaitForPredicate extends GuideAction
    {
        protected Function m_predicate ;
        protected XMLList m_xmldef ;

        public  GAWaitForPredicate ()
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
                ErrorManager.addError("Tutorial WaitForPredicate got invalid callback: " + _loc_2.@name);
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
            return;
        }//end  

    }



