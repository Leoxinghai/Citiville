package Mechanics.GameEventMechanics;

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
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class DictionaryDataMechanicImplementation implements IDictionaryDataMechanic
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;
        private boolean m_sendTransactions ;
        protected String m_gameEvent ="";

        public  DictionaryDataMechanicImplementation ()
        {
            this.m_sendTransactions = true;
            return;
        }//end

        public boolean  add (Object param1 ,Object param2 )
        {
            _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_3)
            {
                _loc_3 = new Object();
            }
            _loc_3.put(param1,  param2);
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, this.m_gameEvent);
            this.sendTransaction("add", param1, param2);
            return true;
        }//end

        public Object  removeAt (Object param1 )
        {
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_2 || !_loc_2.get(param1))
            {
                return null;
            }
            _loc_3 = _loc_2.get(param1);
            delete _loc_2.get(param1);
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_2, this.m_gameEvent);
            this.sendTransaction("removeAt", param1);
            return _loc_3;
        }//end

        public boolean  clear (boolean param1 =true )
        {
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_2 || GameUtil.countObjectLength(_loc_2) <= 0)
            {
                return false;
            }
            _loc_2 = new Object();
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_2, this.m_gameEvent);
            if (param1 !=null)
            {
                this.sendTransaction("clear");
            }
            return true;
        }//end

        public Array  getKeys ()
        {
            Object _loc_3 =null ;
            _loc_1 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_1)
            {
                return null;
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

        public Array  getValues ()
        {
            Object _loc_3 =null ;
            _loc_1 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_1)
            {
                return null;
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

        public Object  getValueAt (Object param1 )
        {
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (!_loc_2 || !_loc_2.get(param1))
            {
                return null;
            }
            return _loc_2.get(param1);
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return this.get(param1) instanceof Function;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true );
            if (this.get(param1) instanceof Function)
            {
                _loc_3 = this.get(param1).apply(this, param2);
            }
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public void  sendTransactions (boolean param1 )
        {
            this.m_sendTransactions = param1;
            return;
        }//end

        protected void  sendTransaction (String param1 ,...args0 )
        {
            if (this.m_sendTransactions)
            {
                GameTransactionManager.addTransaction(new TMechanicAction(this.m_owner, this.m_config.type, MechanicManager.ALL, {operation:param1, args:args0}));
            }
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

    }



