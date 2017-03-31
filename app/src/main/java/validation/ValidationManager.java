package validation;

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

import Modules.franchise.validation.*;
//import flash.utils.*;
import validation.util.*;

    public class ValidationManager
    {
        protected Dictionary m_utilClasses ;
        private Dictionary m_globalValidators ;

        public  ValidationManager ()
        {
            this.m_globalValidators = new Dictionary();
            this.loadUtilClasses();
            return;
        }//end

        public void  addValidator (GenericValidationScript param1 )
        {
            this.m_globalValidators.put(param1.name,  param1);
            return;
        }//end

        public GenericValidationScript  getValidator (String param1 )
        {
            return this.m_globalValidators.get(param1) as GenericValidationScript;
        }//end

        public Function  getValidationFunction (String param1 ,String param2 )
        {
            if (this.m_utilClasses.[param1] != null)
            {
                return (this.m_utilClasses.get(param1) as IValidationUtilClass).getValidationCallback(param2);
            }
            return null;
        }//end

        protected void  loadUtilClasses ()
        {
            this.m_utilClasses = new Dictionary();
            this.m_utilClasses.put("MapResourceValidationUtil",  new MapResourceValidationUtil());
            this.m_utilClasses.put("ItemValidationUtil",  new ItemValidationUtil());
            this.m_utilClasses.put("FranchiseValidationUtil",  new FranchiseValidationUtil());
            this.m_utilClasses.put("RollCallValidationUtil",  new RollCallValidationUtil());
            this.m_utilClasses.put("GlobalValidationUtil",  new GlobalValidationUtil());
            this.m_utilClasses.put("QuestValidationUtil",  new QuestValidationUtil());
            this.m_utilClasses.put("ExperimentValidationUtil",  new ExperimentValidationUtil());
            this.m_utilClasses.put("PaymentsSaleValidationUtil",  new PaymentSaleValidationUtil());
            this.m_utilClasses.put("FriendValidationUtil",  new FriendValidationUtil());
            this.m_utilClasses.put("TicketValidationUtil",  new TicketValidationUtil());
            this.m_utilClasses.put("SunsetValidationUtil",  new SunsetValidationUtil());
            this.m_utilClasses.put("ScriptingConditionsUtil",  new ScriptingConditionsUtil());
            this.m_utilClasses.put("WonderValidationUtil",  new WonderValidationUtil());
            return;
        }//end

    }



