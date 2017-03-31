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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class AbstractCountableMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gameEvent ="GMPlay";

        public  AbstractCountableMechanic ()
        {
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return (this.shouldExecute || this.shouldSkip) && this.doesDataStateMatch;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Object _loc_3 ={operation "execute"};
            _loc_4 = this.m_config.params.get( "blockOthers") =="true"? (false) : (true);
            this.m_owner.setDataForMechanic(this.m_config.type, (this.timesExecuted + 1), this.m_gameEvent);
            return new MechanicActionResult(true, _loc_4, true, _loc_3);
        }//end

        protected boolean  shouldExecute ()
        {
            int _loc_4 =0;
            boolean _loc_1 =true ;
            _loc_2 = this.m_config.params.get( "skip") ? (this.m_config.params.get("skip")) : (0);
            _loc_3 = this.m_config.params.get( "show") ? (this.m_config.params.get("show")) : (-1);
            if (_loc_3 > 0)
            {
                _loc_4 = this.timesExecuted;
                if (_loc_2 > _loc_4)
                {
                    _loc_1 = false;
                }
                if (_loc_4 >= _loc_2 + _loc_3)
                {
                    _loc_1 = false;
                }
            }
            return _loc_1;
        }//end

        protected boolean  shouldSkip ()
        {
            _loc_1 = this.m_config.params.get( "skip") ? (this.m_config.params.get("skip")) : (0);
            _loc_2 = this.timesExecuted ;
            if (_loc_1 > _loc_2)
            {
                return true;
            }
            return false;
        }//end

        protected boolean  doesDataStateMatch ()
        {
            Object _loc_4 =null ;
            boolean _loc_1 =true ;
            _loc_2 = this.m_config.params[ "isNull"] ? (this.m_config.params.get("isNull") == "true") : (false);
            _loc_3 = this.m_config.params.get( "dataToCheck") ? (this.m_config.params.get("dataToCheck")) : ("");
            if (_loc_3 != "")
            {
                _loc_4 = this.m_owner.getDataForMechanic(_loc_3);
                if (_loc_4 == null != _loc_2)
                {
                    _loc_1 = false;
                }
            }
            return _loc_1;
        }//end

        public int  timesExecuted ()
        {
            return int(this.m_owner.getDataForMechanic(this.m_config.type));
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



