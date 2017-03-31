package Mechanics.ClientDisplayMechanics;

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
import Classes.gates.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;

    public class GateDisplayMechanic implements IGateMechanic, IClientGameMechanic
    {
        protected IMechanicUser m_owner ;
        protected String m_type ;
public static  String GATE_KEY ="gate";
public static  String ITEM_NAME ="itemName";

        public  GateDisplayMechanic ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            return;
        }//end

        public void  detachDisplayObject ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.m_type = param2.type;
            Global.world.addEventListener(FarmGameWorldEvent.WORLD_LOADED, this.onWorldLoaded);
            (this.m_owner as MapResource).addEventListener(MapResource.REPLACE_CONTENT, this.displayGate, false, 0, true);
            (this.m_owner as MechanicMapResource).addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.displayGate, false, 0, true);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return true;
        }//end

        public IGate  getGateInstance (String param1 ,Object param2 )
        {
            return (this.m_owner as MechanicMapResource).getMechanicGate(param1, param2);
        }//end

        protected void  onWorldLoaded (Event event )
        {
            if ((this.m_owner as ItemInstance).isAttached())
            {
                this.displayGate(event);
            }
            Global.world.removeEventListener(FarmGameWorldEvent.WORLD_LOADED, this.displayGate);
            return;
        }//end

        protected void  displayGate (...args )
        {
            IGate _loc_4 =null ;
            String _loc_5 =null ;
            Item _loc_6 =null ;
            Object argsvalue =this.m_owner.getDataForMechanic(this.m_type );
            String _loc_3 =null ;
            if (argsvalue)
            {
                _loc_5 = argsvalue.hasOwnProperty(ITEM_NAME) ? (argsvalue.itemName) : (null);
                _loc_6 = Global.gameSettings().getItemByName(_loc_5);
                if (_loc_6 && _loc_6.getRemodelDefinition())
                {
                    _loc_3 = _loc_6.getRemodelDefinition().gateName;
                }
            }
            if (_loc_3 && _loc_3 && argsvalue && argsvalue.hasOwnProperty(GATE_KEY))
            {
                _loc_4 = this.getGateInstance(_loc_3);
                if (_loc_4)
                {
                    _loc_4.loadFromObject(argsvalue.get(GATE_KEY));
                    _loc_4.displayGate();
                }
            }
            return;
        }//end

    }




