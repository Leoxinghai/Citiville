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
import Display.Toaster.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ToasterMechanic extends AbstractCountableMechanic implements IToasterMechanic
    {

        public  ToasterMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            return this.canPopToaster() || shouldSkip && doesDataStateMatch;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =null ;
            ItemToaster _loc_4 =null ;
            if (this.canPopToaster())
            {
                _loc_4 = this.instantiateToaster();
                Global.ui.toaster.show(_loc_4);
                _loc_3 = super.executeOverrideForGameEvent(param1, param2);
            }
            else if (shouldSkip)
            {
                _loc_3 = super.executeOverrideForGameEvent(param1, param2);
            }
            else
            {
                _loc_3 = new MechanicActionResult(false, true, false);
            }
            return _loc_3;
        }//end

        protected ItemToaster  instantiateToaster ()
        {
            ItemToaster _loc_1 =null ;
            Item _loc_2 =null ;
            Object _loc_3 =null ;
            String _loc_4 =null ;
            if (m_config.params.get("toaster"))
            {
                _loc_2 = m_owner.getItem();
                _loc_3 = _loc_2.getToasterInfo(String(m_config.params.get("toaster")));
                _loc_4 = _loc_3.asset ? (Global.getAssetURL(_loc_3.asset)) : (null);
                _loc_1 = new ItemToaster(ZLoc.t("Main", _loc_3.title), ZLoc.t("Main", _loc_3.text), _loc_4, null, _loc_3.duration);
            }
            return _loc_1;
        }//end

        public boolean  canPopToaster ()
        {
            return shouldExecute && doesDataStateMatch;
        }//end

    }



