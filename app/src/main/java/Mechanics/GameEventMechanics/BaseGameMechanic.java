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

import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class BaseGameMechanic implements IActionGameMechanic, IToolTipModifier, IMultiPickSupporter
    {
        public  String PICK_ATTR ="pick";
        public  String TOOLTIP_ATTR ="tooltipkey";
        public  String TOOLTIP_FILE ="tooltipfile";
        protected IMechanicUser m_owner ;
        protected MechanicConfigData m_config ;
        protected boolean m_blocksOthers =false ;

        public  BaseGameMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            throw new Error("Need to implement hasOverrideForGameAction in " + param1);
        }//end

        final public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =null ;
            _loc_4 = param1"on"+;
            _loc_5 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (this.hasOwnProperty(_loc_4) && this.get(_loc_4) as Function)
            {
                //_loc_3 =(Function(_loc_5, param2) as MechanicActionResult) this.get(_loc_4);
            }
            else if (Config.DEBUG_MODE)
            {
                throw new Error("Your mechanic needs to implement " + _loc_4);
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_5, param1);
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.m_config = param2;
            this.m_blocksOthers = this.m_config.params["blockOthers"] == "true" || this.m_config.params.get("blocksOthers") == "true";
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_blocksOthers;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get(this.PICK_ATTR);
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipStatus ()
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_1 =null ;
            if (this.m_config.params.get(this.TOOLTIP_ATTR))
            {
                _loc_2 =(Object) this.m_owner.getDataForMechanic(this.m_config.type);
                _loc_3 = this.m_config.params.get(this.TOOLTIP_FILE) ? (this.m_config.params.get(this.TOOLTIP_FILE)) : ("Items");
                _loc_1 = ZLoc.t(_loc_3, this.m_config.params.get(this.TOOLTIP_ATTR), _loc_2);
            }
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            return null;
        }//end

    }



