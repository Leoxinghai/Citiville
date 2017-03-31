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

    public class TMechanicDataTransfer extends TFarmTransaction
    {
        protected MechanicMapResource m_ownerObject ;
        protected Array m_params ;

        public  TMechanicDataTransfer (MechanicMapResource param1 )
        {
            this.m_ownerObject = param1;
            this.m_params = new Array();
            return;
        }//end  

        public Array  params ()
        {
            return this.m_params;
        }//end  

        public MechanicMapResource  getOwner ()
        {
            return this.m_ownerObject;
        }//end  

        public void  addDataTransfer (String param1 ,String param2 ,*param3 ,*)param4 
        {
            if (!this.pruneParams(param1, param2, param3, param4))
            {
                this.m_params.push({source:param1, dest:param2, target:param3, extraParams:param4});
            }
            return;
        }//end  

        private boolean  pruneParams (String param1 ,String param2 ,*param3 ,*)param4 
        {
            String _loc_6 =null ;
            Object _loc_7 =null ;
            boolean _loc_5 =false ;
            for(int i0 = 0; i0 < this.m_params.size(); i0++) 
            {
            		_loc_6 = this.m_params.get(i0);
                
                _loc_7 = this.m_params.get(_loc_6);
                if (param3 == _loc_7.target && param4 == _loc_7.extraParams && param2 == _loc_7.source && param1 == _loc_7.dest)
                {
                    this.m_params.splice(_loc_6, 1);
                    _loc_5 = true;
                }
            }
            return _loc_5;
        }//end  

         public void  perform ()
        {
            signedCall("GameMechanicService.mechanicDataTransfer", this.m_ownerObject.getId(), this.m_params);
            return;
        }//end  

    }



