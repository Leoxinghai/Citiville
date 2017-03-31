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
//import flash.display.*;
//import flash.utils.*;

    public class CustomDialogGenerationMechanic extends DialogGenerationMechanic
    {
        protected String m_currentGameEvent ;

        public  CustomDialogGenerationMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            this.m_currentGameEvent = param1;
            return super.hasOverrideForGameAction(param1);
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            this.m_currentGameEvent = param1;
            return super.executeOverrideForGameEvent(param1, param2);
        }//end

         public DisplayObject  instantiateDialog ()
        {
            DisplayObject _loc_4 =null ;
            _loc_1 = m_config.params.get( "dialogToPop") ;
            _loc_2 = _loc_1? (_loc_1.substr((_loc_1.lastIndexOf(".") + 1))) : ("");
            _loc_3 = (Class)getDefinitionByName(_loc_1)
            _loc_5 = m_config.params.get( "titleKey") ? (m_config.params.get("titleKey")) : ("");
            _loc_6 = m_config.params.get( "textKey") ? (m_config.params.get("textKey")) : ("");
            _loc_7 = m_config.params.get( "dialogName") ? (m_config.params.get("dialogName")) : (_loc_5);
            _loc_8 = _loc_6? (ZLoc.t("Dialogs", _loc_6)) : ("");
            _loc_9 = m_config.params.get( "dialogType") ? (m_config.params.get("dialogType")) : (0);
            _loc_10 = m_config.params.get( "iconURL") ? (m_config.params.get("iconURL")) : (null);
            switch(_loc_2)
            {
                case "CharacterDialog":
                {
                    _loc_4 = new _loc_3(_loc_8, _loc_5, 0, null, null, true, _loc_10);
                    break;
                }
                default:
                {
                    _loc_4 = new _loc_3(_loc_8, _loc_7, _loc_9, null, _loc_5, _loc_10, true);
                    break;
                    break;
                }
            }
            return _loc_4;
        }//end

         public boolean  canPopDialog ()
        {
            String _loc_1 =null ;
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            IActionGameMechanic _loc_4 =null ;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            if (super.canPopDialog())
            {
                if (m_config.params.get("mechanicOverrideToCheck") && this.m_currentGameEvent)
                {
                    _loc_1 = m_config.params.get("mechanicOverrideToCheck");
                    if (m_owner.hasMechanicAvailable(_loc_1))
                    {
                        _loc_2 = m_config.params.get("overrideComparison") == "false" ? (false) : (true);
                        _loc_3 = m_config.params.get("mechanicOverrideProperty");
                        _loc_4 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(m_owner, _loc_1, this.m_currentGameEvent);
                        _loc_5 = false;
                        if (_loc_3)
                        {
                            if (((Object)_loc_4).hasOwnProperty(_loc_3))
                            {
                                _loc_6 =(Object) _loc_4;
                                if (_loc_6.get(_loc_3) instanceof Function)
                                {
                                    _loc_7 = _loc_6;
                                    _loc_5 = _loc_7._loc_6.get(_loc_3)();
                                }
                                else
                                {
                                    _loc_5 = _loc_6.get(_loc_3);
                                }
                            }
                        }
                        else
                        {
                            _loc_5 = _loc_4.hasOverrideForGameAction(this.m_currentGameEvent);
                        }
                        return _loc_2 == _loc_5;
                    }
                }
                return true;
            }
            return false;
        }//end

    }



