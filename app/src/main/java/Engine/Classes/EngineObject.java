package Engine.Classes;

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

import Engine.Events.*;
import Engine.Managers.*;

//import flash.events.*;
//import flash.utils.*;

    public class EngineObject extends SavedObject implements IEventDispatcher
    {
        protected Dictionary m_components ;
        protected Dictionary m_events ;

        public  EngineObject ()
        {
            this.m_components = new Dictionary();
            this.m_events = new Dictionary();
            this.addComponents();
            this.initializeComponents();
            return;
        }//end

         public Object  getSaveObject ()
        {
            Object _loc_3 =null ;
            EngineObjectComponent _loc_4 =null ;
            _loc_1 = super.getSaveObject();
            Object _loc_2 =new Object ();
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_4 = this.m_components.get(i0);

                _loc_3 = _loc_4.getSaveObject();
                if (_loc_3)
                {
                    _loc_2.put(_loc_4.name,  _loc_3);
                }
            }
            _loc_1.components = _loc_2;
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            EngineObjectComponent _loc_3 =null ;
            super.loadObject(param1);
            _loc_2 = param1.components ;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < this.m_components.size(); i0++)
                {
                	_loc_3 = this.m_components.get(i0);

                    if (_loc_2.hasOwnProperty(_loc_3.name))
                    {
                        _loc_3.loadObject(_loc_2.get(_loc_3.name));
                    }
                }
            }
            return;
        }//end

        protected void  addComponents ()
        {
            return;
        }//end

        final protected boolean  addComponent (EngineObjectComponent param1 )
        {
            boolean _loc_2 =false ;
            if (!param1.owner)
            {
                if (!this.m_components.hasOwnProperty(param1.name))
                {
                    this.m_components.put(param1.name,  param1);
                    param1.register(this);
                    _loc_2 = true;
                }
                else
                {
                    ErrorManager.addError("A engine object component with name " + param1.name + " already exists on this engine object.");
                }
            }
            else
            {
                ErrorManager.addError("The engine object component " + param1.name + " already has an owner.");
            }
            return _loc_2;
        }//end

        final protected void  removeComponent (EngineObjectComponent param1 )
        {
            if (param1.owner == this)
            {
                if (this.m_components.get(param1.name))
                {
                    delete this.m_components.get(param1.name);
                    param1.unregister();
                }
                else
                {
                    ErrorManager.addError("The engine object component " + param1.name + " was not found on this engine object.");
                }
            }
            else
            {
                ErrorManager.addError("The engine object component " + param1.name + " instanceof not owned by this engine object.");
            }
            return;
        }//end

        final protected void  initializeComponents ()
        {
            EngineObjectComponent _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_1 = this.m_components.get(i0);

                _loc_1.initializeComponent();
            }
            return;
        }//end

        public Dictionary  components ()
        {
            return this.m_components;
        }//end

        public EngineObjectComponent  lookupComponentByName (String param1 )
        {
            EngineObjectComponent _loc_2 =null ;
            if (this.m_components.get(param1))
            {
                _loc_2 = this.m_components.get(param1);
            }
            return _loc_2;
        }//end

        public void  onUpdate (double param1 )
        {
            this.dispatchEvent(new UpdateEvent(UpdateEvent.UPDATE, param1));
            return;
        }//end

        public void  onMouseOver (MouseEvent event )
        {
            this.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OVER));
            return;
        }//end

        public void  onMouseOut ()
        {
            this.dispatchEvent(new MouseEvent(MouseEvent.MOUSE_OUT));
            return;
        }//end

        public void  onClick ()
        {
            this.dispatchEvent(new MouseEvent(MouseEvent.CLICK));
            return;
        }//end

        public Object getProperty (PropertyReference param1 ,Object param2 )
        {
            EngineObjectComponent component ;
            reference = param1;
            defaultVal = param2;
            Object result;
            if (reference != null && reference.property != null && reference.property != "")
            {
                try
                {
                    component = this.lookupComponentByName(reference.propertyParent);
                    result = component.get(reference.propertyName);
                }
                catch (error:Error)
                {
                    result;
                }
            }
            if (result == null)
            {
                result = defaultVal;
            }
            return result;
        }//end

        public void  setProperty (PropertyReference param1 , Object param2)
        {
            EngineObjectComponent component ;
            reference = param1;
            value = param2;
            if (reference != null && reference.property != null && reference.property != "")
            {
                try
                {
                    component = this.lookupComponentByName(reference.propertyParent);
                    component.put(reference.propertyName,  value);
                }
                catch (error:Error)
                {
                }
            }
            return;
        }//end

        public void  callFunction (FunctionReference param1 ,...args )
        {

            EngineObjectComponent component ;
            reference = param1;
            args = args;
            Object result;
            /*
            if ( functionParent != null && functionPath != null && functionPath != "")
            {
                try
                {
                    component = this.lookupComponentByName(functionParent);
                    if (length == 0)
                    {
                        result = this.[functionName] as Function();
                    }
                    else if (length == 1)
                    {
                        result = this.[functionName] as Function([0]);
                    }
                    else
                    {
                        result = (.get(functionName) as Function).apply(, );
                    }
                }
                catch (error:Error)
                {
                }
            }
            */
            return ;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,int param4 =0,boolean param5 =false )
        {
            if (this.m_events.hasOwnProperty(param1) == false)
            {
                this.m_events.put(param1,  new Vector<Function>);
            }
            (this.m_events.get(param1) as Vector<Function>).push(param2);
            return;
        }//end

        public boolean  dispatchEvent (Event event )
        {
            Vector _loc_3.<Function >=null ;
            int _loc_4 =0;
            int _loc_5 =0;
            boolean _loc_2 =false ;
            if (this.m_events.hasOwnProperty(event.type) == true)
            {
                _loc_3 = (Vector<Function>)this.m_events.get(event.type) ;
                _loc_4 = _loc_3.length;
                _loc_5 = 0;
                while (_loc_5 < _loc_4)
                {


                    _loc_3.get(_loc_5)(event);
                    _loc_5 = _loc_5 + 1;
                }
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  hasEventListener (String param1 )
        {
            return this.m_events.hasOwnProperty(param1);
        }//end

        public void  removeEventListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            Vector _loc_4.<Function >=null ;
            int _loc_5 =0;
            int _loc_6 =0;
            if (this.m_events.hasOwnProperty(param1) == true)
            {
                _loc_4 = (Vector<Function>)this.m_events.get(param1);
                _loc_5 = _loc_4.length;
                _loc_6 = 0;
                while (_loc_6 < _loc_5)
                {

                    if (_loc_4.get(_loc_6) == param2)
                    {
                        _loc_4 = _loc_4.splice(_loc_6, 1);
                        break;
                    }
                    _loc_6 = _loc_6 + 1;
                }
            }
            return;
        }//end

        public boolean  willTrigger (String param1 )
        {
            GlobalEngine.error("EngineObject", "willTrigger() function not currently supported");
            return false;
        }//end

    }



