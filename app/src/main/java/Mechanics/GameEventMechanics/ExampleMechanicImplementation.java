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
import Display.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ExampleMechanicImplementation implements IClickCounter
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;

        public  ExampleMechanicImplementation ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            _loc_3 = (Int)(this.m_owner.getDataForMechanic(this.m_config.type ));
            UI.displayMessage("Number of clicks to this resource: " + _loc_3);
            _loc_3++;
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, param1);
            return new MechanicActionResult(true, false, true, {numClicks:1});
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public int  getClickCount ()
        {
            return int(this.m_owner.getDataForMechanic(this.m_config.type));
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



