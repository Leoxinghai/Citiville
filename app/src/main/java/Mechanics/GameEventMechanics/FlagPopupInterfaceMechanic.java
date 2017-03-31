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

import Display.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.display.*;

    public class FlagPopupInterfaceMechanic extends DialogGenerationMechanic
    {
        protected String m_flag ;

        public  FlagPopupInterfaceMechanic ()
        {
            return;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            this.m_flag = m_config.params.hasOwnProperty("flag") ? (m_config.params.get("flag")) : (null);
            return;
        }//end

         public boolean  canPopDialog ()
        {
            _loc_1 =Global.player.getSeenFlag(this.m_flag );
            if (this.m_flag != null && !Global.player.getSeenFlag(this.m_flag))
            {
                return true;
            }
            return false;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            DisplayObject _loc_3 =null ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (this.canPopDialog())
            {
                _loc_3 = instantiateDialog();
                _loc_4 = true;
                if (m_config.params.get("popImmediately") == "true")
                {
                    _loc_4 = false;
                }
                UI.displayPopup(_loc_3, _loc_4, m_config.type, true);
                if (this.m_flag != null)
                {
                    Global.player.setSeenFlag(this.m_flag);
                }
                trackDialog();
                _loc_5 = m_config.params.get("proceed") == "false" ? (false) : (true);
                return new MechanicActionResult(true, _loc_5, false);
            }
            else
            {
                return new MechanicActionResult(false, true, false);
            }
        }//end

    }



