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
import Classes.inventory.*;
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class StorageMechanicImplementation extends BaseInventory implements IStorageMechanic
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;
        private boolean m_sendTransactions ;
        private Array m_restrictedKeywords ;
        protected String m_gameEvent ="";

        public  StorageMechanicImplementation ()
        {
            this.m_sendTransactions = true;
            super(null);
            return;
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

         protected void  loadObject (Object param1 )
        {
            Object _loc_2 ={storage param1 };
            super.loadObject(_loc_2);
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            this.loadObject(this.m_owner.getDataForMechanic(this.m_config.type));
            this.m_restrictedKeywords = ((String)this.m_config.params.get("restrictByKeywords")).split(",");
            return;
        }//end

        public void  sendTransactions (boolean param1 )
        {
            this.m_sendTransactions = param1;
            return;
        }//end

         protected void  onDataChange (String param1 )
        {
            this.applyChanges();
            return;
        }//end

        public void  applyChanges ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            Object _loc_1 ={};
            if (m_storage && _loc_1)
            {
                for(int i0 = 0; i0 < m_storage.size(); i0++)
                {
                		_loc_2 = m_storage.get(i0);

                    _loc_3 = int(m_storage.get(_loc_2));
                    _loc_1.put(_loc_2,  _loc_3);
                }
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_1, this.m_gameEvent);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

         public boolean  isValidName (String param1 )
        {
            String _loc_3 =null ;
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2)
            {
                for(int i0 = 0; i0 < this.m_restrictedKeywords.size(); i0++)
                {
                		_loc_3 = this.m_restrictedKeywords.get(i0);

                    if (_loc_2.itemHasKeyword(_loc_3))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

         public int  getCapacity (String param1 )
        {
            int _loc_2 =0;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3 && _loc_3.inventoryLimit)
            {
                _loc_2 = _loc_3.inventoryLimit;
            }
            return _loc_2;
        }//end

        public Array  restrictedKeywords ()
        {
            return this.m_restrictedKeywords;
        }//end

         public boolean  add (String param1 ,int param2 )
        {
            _loc_3 = super.add(param1,param2);
            if (_loc_3)
            {
                this.sendTransaction("add", param1, param2);
            }
            return _loc_3;
        }//end

         public boolean  clear ()
        {
            _loc_1 = super.clear();
            if (_loc_1)
            {
                this.sendTransaction("clear");
            }
            return _loc_1;
        }//end

         public boolean  remove (String param1 ,int param2 )
        {
            _loc_3 = super.remove(param1,param2);
            if (_loc_3)
            {
                this.sendTransaction("remove", param1, param2);
            }
            return _loc_3;
        }//end

        public boolean  purchase (String param1 ,int param2 ,String param3 )
        {
            boolean _loc_4 =false ;
            _loc_5 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1) && Global.player.canBuyCash(_loc_5.cash * param2, true))
            {
                Global.player.cash = Global.player.cash - _loc_5.cash * param2;
                _loc_4 = super.add(param1, param2);
            }
            if (_loc_4)
            {
                this.sendTransaction("purchase", param1, param2, param3);
            }
            return _loc_4;
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
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



