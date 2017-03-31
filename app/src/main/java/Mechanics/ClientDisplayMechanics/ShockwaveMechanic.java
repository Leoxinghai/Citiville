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
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ShockwaveMechanic implements IClientGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected String m_type ;
        protected Array m_acceptedEvents ;

        public  ShockwaveMechanic ()
        {
            this.m_acceptedEvents = new Array();
            return;
        }//end

        public void  detachDisplayObject ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            return;
        }//end

        public void  onMechanicChange (GenericObjectEvent event )
        {
            if (this.m_acceptedEvents.indexOf(event.obj + event.subType) >= 0)
            {
                if (this.m_owner instanceof IMerchant)
                {
                    (this.m_owner as IMerchant).crowdManager.performShockwave();
                }
            }
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            XML _loc_3 =null ;
            this.m_owner =(MechanicMapResource) param1;
            this.m_type = param2.params.get("type");
            if (param2.rawXMLConfig.hasOwnProperty("gameEvents"))
            {
                for(int i0 = 0; i0 < param2.rawXMLConfig.gameEvents.event.size(); i0++)
                {
                		_loc_3 = param2.rawXMLConfig.gameEvents.event.get(i0);

                    this.m_acceptedEvents.push(String(_loc_3.@name) + String(_loc_3.@gameEvent));
                }
            }
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicChange);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



