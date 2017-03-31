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

    public class SlotMechanicImplementation implements ISlotMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected boolean m_sendTransactions ;
        protected String m_gameEvent ="";

        public  SlotMechanicImplementation ()
        {
            this.m_sendTransactions = true;
            return;
        }//end

        public int  numSlots ()
        {
            return this.m_config.params.get("numSlots");
        }//end

        public int  numEmptySlots ()
        {
            int _loc_3 =0;
            int _loc_1 =0;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type )as Array ;
            if (_loc_2)
            {
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    if (_loc_2.get(_loc_3) == null)
                    {
                        _loc_1++;
                    }
                    _loc_3++;
                }
            }
            return _loc_1;
        }//end

        public int  numFilledSlots ()
        {
            return this.numSlots - this.numEmptySlots;
        }//end

        public String  getSlot (int param1 )
        {
            String _loc_2 =null ;
            Array _loc_3 =this.m_owner.getDataForMechanic(this.m_config.type )as Array ;

            if (param1 >= 0 && param1 < this.numSlots && _loc_3)
            {
                _loc_2 = _loc_3.get(param1);
            }
            return _loc_2;
        }//end

        public boolean  isValidData (Object param1)
        {
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            _loc_2 =Global.gameSettings().getItemByName(String(param1 ));
            if (_loc_2)
            {
                _loc_3 = this.m_config.params.get("restrictByKeywords");
                _loc_4 = _loc_3.split(",");
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    if (_loc_2.itemHasKeyword(_loc_5))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public int  fillNextSlot (Object param1)
        {
            int _loc_4 =0;
            int _loc_2 =-1;
            if (!this.isValidData(param1))
            {
                return _loc_2;
            }
            _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type )as Array ;
            if (_loc_3)
            {
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length())
                {

                    if (this.canAdd(_loc_4, _loc_3, param1))
                    {
                        _loc_3.put(_loc_4,  param1);
                        _loc_2 = _loc_4;
                        break;
                    }
                    _loc_4++;
                }
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, this.m_gameEvent);
            if (_loc_2 != -1)
            {
                this.sendTransaction("fillNextSlot", param1);
            }
            return _loc_2;
        }//end

        public boolean  canAdd (int param1 ,Array param2 ,*)param3
        {
            return param2.get(param1) == null;
        }//end

        public boolean  isSlotFilled (int param1 )
        {
            return this.getSlot(param1) != null;
        }//end

        public boolean  emptySlot (int param1 )
        {
            boolean _loc_2 =false ;
            if (this.isSlotFilled(param1))
            {
                _loc_2 = this.setSlot(param1, null);
            }
            if (_loc_2)
            {
                this.sendTransaction("emptySlot", param1);
            }
            return _loc_2;
        }//end

        public boolean  fillSlot (int param1 , Object param2)
        {
            boolean _loc_3 =false ;
            if (this.isValidData(param2))
            {
                if (!this.isSlotFilled(param1))
                {
                    _loc_3 = this.setSlot(param1, param2);
                }
                if (_loc_3)
                {
                    this.sendTransaction("fillSlot", param1, param2);
                }
            }
            return _loc_3;
        }//end

        protected boolean  setSlot (int param1 , Object param2)
        {
            boolean _loc_3 =false ;
            _loc_4 = this.m_owner.getDataForMechanic(this.m_config.type )as Array ;
            _loc_5 = param1>=0&& param1 < this.numSlots;
            if (param1 >= 0 && param1 < this.numSlots && _loc_4)
            {
                _loc_4.put(param1,  param2);
                _loc_3 = true;
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_4, this.m_gameEvent);
            return _loc_3;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            this.m_gameEvent = param1;
            return this.get(param1) instanceof Function;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            this.m_gameEvent = param1;
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true );
            if (this.get(param1) instanceof Function)
            {
                _loc_3 = this.get(param1).apply(this, param2);
            }
            return _loc_3;
        }//end

        public int  getNumItems (String param1 )
        {
            String _loc_4 =null ;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type )as Array ;
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                if (_loc_4 == param1)
                {
                    _loc_3++;
                }
            }
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            int _loc_4 =0;
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type )as Array ;
            if (_loc_3 == null)
            {
                _loc_3 = new Array();
                _loc_4 = 0;
                while (_loc_4 < this.numSlots)
                {

                    _loc_3.put(_loc_4,  null);
                    _loc_4++;
                }
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, this.m_gameEvent);
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
            return true;
        }//end

    }



