package Mechanics.Transactions;

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
import Transactions.*;

    public class TMechanicAction extends TFarmTransaction
    {
        protected MechanicMapResource m_ownerObject ;
        protected String m_mechanicType ;
        protected String m_callingGameMode ;
        protected Object m_params ;
        protected String m_method ;
        public static  String PERFORM_MECHANIC_ACTION ="performMechanicAction";
        public static  String PERFORM_NEIGHBOR_MECHANIC_ACTION ="performNeighborMechanicAction";

        public  TMechanicAction (MechanicMapResource param1 ,String param2 ,String param3 ,Object param4 =null )
        {
            this.m_ownerObject = param1;
            this.m_mechanicType = param2;
            this.m_callingGameMode = param3;
            if (param4)
            {
                this.m_params = param4;
            }
            else
            {
                this.m_params = {};
            }
            this.m_method = PERFORM_MECHANIC_ACTION;
            if (Global.isVisiting())
            {
                this.m_method = PERFORM_NEIGHBOR_MECHANIC_ACTION;
                this.m_params.put("neighbor",  Global.getVisiting());
            }
            return;
        }//end  

        public MechanicMapResource  ownerObject ()
        {
            return this.m_ownerObject;
        }//end  

        public String  mechanicType ()
        {
            return this.m_mechanicType;
        }//end  

        public String  callingGameMode ()
        {
            return this.m_callingGameMode;
        }//end  

        public Object  method ()
        {
            return this.m_method;
        }//end  

         public void  preAddTransaction ()
        {
            super.preAddTransaction();
            this.m_params.put("clientEnqueueTime",  m_clientEnqueueTime);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("GameMechanicService." + this.m_method, this.m_ownerObject.getId(), this.m_mechanicType, this.m_callingGameMode, this.m_params);
            return;
        }//end  

        public Object  getParams ()
        {
            return this.m_params;
        }//end  

        public void  setParams (Object param1 )
        {
            this.m_params = param1;
            return;
        }//end  

    }



