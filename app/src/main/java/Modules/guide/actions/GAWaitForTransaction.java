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

import Classes.util.*;
import Engine.Transactions.*;
//import flash.utils.*;

    public class GAWaitForTransaction extends GuideAction implements ITransactionManagerListener
    {
        protected Function m_predicate =null ;
        protected int m_requiredSuccessCount =1;
        protected int m_runningSuccessCount =0;

        public  GAWaitForTransaction ()
        {
            return;
        }//end

        public void  beforeTransactionAdded (Transaction param1 ,boolean param2 )
        {
            return;
        }//end

        public void  afterTransactionAdded (Transaction param1 ,boolean param2 )
        {
            if (this.m_predicate(param1))
            {
                this.m_runningSuccessCount++;
            }
            if (this.m_runningSuccessCount >= this.m_requiredSuccessCount)
            {
                removeState(this);
            }
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"transaction");
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = (String)(_loc_2.@className);
            _loc_4 = int(_loc_2.@count);
            this.m_predicate = makeClassTestFn(_loc_3);
            if (_loc_4 > 0)
            {
                this.m_requiredSuccessCount = _loc_4;
            }
            return true;
        }//end

         public void  enter ()
        {
            super.enter();
            GameTransactionManager.addListener(this);
            return;
        }//end

         public void  reenter ()
        {
            GameTransactionManager.addListener(this);
            return;
        }//end

         public void  exit ()
        {
            GameTransactionManager.removeListener(this);
            return;
        }//end

         public void  removed ()
        {
            GameTransactionManager.removeListener(this);
            return;
        }//end

        public static Function  makeClassTestFn (String param1 )
        {
            className = param1;
            return boolean  (Transaction param1 )
            {
                _loc_2 = getQualifiedClassName(param1);
                return _loc_2 == className;
            }//end
            ;
        }//end


    }



