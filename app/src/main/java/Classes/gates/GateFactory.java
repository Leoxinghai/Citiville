package Classes.gates;

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
//import flash.utils.*;

    public class GateFactory
    {
        private Dictionary m_registeredGates ;
        private Object m_thing ;

        public  GateFactory ()
        {
            String _loc_1 =null ;
            this.m_thing = {crew:CrewGate, inventory:InventoryGate, composite:CompositeGate, aoe:AOEGate, status:WorldObjectStatusGate, mastery:MasteryGate, construction:ConstructionGate, ownership:OwnershipGate};
            this.m_registeredGates = new Dictionary();
            for(int i0 = 0; i0 < this.m_thing.size(); i0++)
            {
            		_loc_1 = this.m_thing.get(i0);

                this.m_registeredGates.put(_loc_1,  this.m_thing.get(_loc_1));
            }
            return;
        }//end

        public void  cleanUp ()
        {
            Object _loc_1 =null ;
            if (this.m_registeredGates)
            {
                for(int i0 = 0; i0 < this.m_registeredGates.size(); i0++)
                {
                		_loc_1 = this.m_registeredGates.get(i0);

                    delete this.m_registeredGates.get(_loc_1);
                }
            }
            this.m_registeredGates = null;
            return;
        }//end

        private IGate  createGate (String param1 ,String param2 ,double param3 )
        {
            IGate _loc_4 =null ;
            _loc_5 =(Class) this.m_registeredGates.get(param1);
            if ((Class)this.m_registeredGates.get(param1))
            {
                _loc_4 = new _loc_5(param2);
                _loc_4.targetObjectId = param3;
                _loc_4.factory = this;
            }
            return _loc_4;
        }//end

        public void  register (String param1 ,Class param2 )
        {
            this.m_registeredGates.put(param1,  param2);
            return;
        }//end

        public IGate  loadGateFromXML (Item param1 ,ItemInstance param2 ,String param3 ,Function param4 )
        {
            XML _loc_7 =null ;
            String _loc_8 =null ;
            IGate _loc_5 =null ;
            _loc_6 = param1.gatesXml.get( "gate") ;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                if (_loc_7.@name == param3)
                {
                    _loc_8 = _loc_7.@type || GateType.INVENTORY;
                    _loc_5 = this.createGate(_loc_8, param1.name, param2.getId());
                    _loc_5.loadFromXML(_loc_7);
                    _loc_5.unlockCallback = param4;
                    break;
                }
            }
            return _loc_5;
        }//end

        public IGate  loadGateFromObject (Object param1 ,Item param2 ,ItemInstance param3 ,Function param4 )
        {
            Object _loc_7 =null ;
            boolean _loc_8 =false ;
            XML _loc_9 =null ;
            String _loc_10 =null ;
            IGate _loc_5 =null ;
            _loc_6 = param2.gatesXml.get( "gate") ;
            if (param1.gates)
            {
                for(int i0 = 0; i0 < param1.gates.size(); i0++)
                {
                		_loc_7 = param1.gates.get(i0);

                    _loc_8 = false;
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    		_loc_9 = _loc_6.get(i0);

                        if (_loc_9.@name == _loc_7.name)
                        {
                            _loc_8 = true;
                            break;
                        }
                    }
                    if (!_loc_8)
                    {
                        continue;
                    }
                    _loc_10 = _loc_7.type || GateType.INVENTORY;
                    _loc_5 = this.createGate(_loc_10, param2.name, param3.getId());
                    _loc_5.loadFromObject(_loc_7);
                    _loc_5.unlockCallback = param4;
                }
            }
            return _loc_5;
        }//end

    }


