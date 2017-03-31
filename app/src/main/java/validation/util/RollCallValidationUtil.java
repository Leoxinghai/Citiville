package validation.util;

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

import Classes.Managers.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.utils.*;
import validation.*;

    public class RollCallValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  RollCallValidationUtil ()
        {
            this.loadValidators();
            return;
        }//end

        public Function  getValidationCallback (String param1 )
        {
            return this.m_validators.get(param1);
        }//end

        protected void  loadValidators ()
        {
            this.m_validators = new Dictionary();
            this .m_validators.put( "canPerformRollCall", boolean  (Object param1 );
            {
                if (ReactivationManager.isPlayerReactive())
                {
                    return false;
                }
                _loc_2 = (IMechanicUser)MechanicManager.getInstance().getMechanicInstance(this
                return _loc_2 is RollCallDataMechanic && (_loc_2 as RollCallDataMechanic).canPerformRollCall();
            }//end
            ;
            this .m_validators.put( "rollCallProgressLessThan", boolean  (Object param1 );
            {
                _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(thisasIMechanicUser,"rollCall","all")
                _loc_3 = param1.value ;
                return _loc_2 && _loc_2.getRollCallTimeLeft() < _loc_3;
            }//end
            ;
            this .m_validators.put( "rollCallProgressGreaterThan", boolean  (Object param1 );
            {
                _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(thisasIMechanicUser,"rollCall","all")
                _loc_3 = param1.value ;
                return _loc_2 && _loc_2.getRollCallTimeLeft() > _loc_3;
            }//end
            ;
            this .m_validators.put( "rollCallActive", boolean  (Object param1 );
            {
                _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(thisasIMechanicUser,"rollCall","all")
                _loc_3 = _loc_2.getState ();
                return _loc_3 == RollCallDataMechanic.STATE_IN_PROGRESS || _loc_3 == RollCallDataMechanic.STATE_FINISHED;
            }//end
            ;
            this .m_validators.put( "isRollCallComplete", boolean  (Object param1 );
            {
                _loc_2 = (IMechanicUser)MechanicManager.getInstance().getMechanicInstance(this
                return _loc_2 is RollCallDataMechanic && (_loc_2 as RollCallDataMechanic).isRollCallComplete();
            }//end
            ;
            this .m_validators.put( "isActiveObject", boolean  (Object param1 );
            {
                _loc_2 = (IMechanicUser)MechanicManager.getInstance().getMechanicInstance(this
                return _loc_2 is RollCallDataMechanic && (_loc_2 as RollCallDataMechanic).isActiveObject();
            }//end
            ;
            this .m_validators.put( "isNotActiveObject", boolean  (Object param1 );
            {
                _loc_2 = (IMechanicUser)MechanicManager.getInstance().getMechanicInstance(this
                return !(_loc_2 is RollCallDataMechanic && (_loc_2 as RollCallDataMechanic).isActiveObject());
            }//end
            ;
            this .m_validators.put( "canCheckIn", boolean  (Object param1 );
            {
                _loc_2 = param1.zid ? (param1.zid) : (Global.player.uid);
                _loc_3 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(thisasIMechanicUser,"rollCall","all")
                return _loc_3 && _loc_3.canCheckIn(_loc_2);
            }//end
            ;
            this .m_validators.put( "canCollect", boolean  (Object param1 );
            {
                RollCallDataMechanic _loc_4 =null ;
                boolean _loc_2 =false ;
                _loc_3 = (IMechanicUser)MechanicManager.getInstance().getMechanicInstance(this
                if (_loc_3 is RollCallDataMechanic)
                {
                    _loc_4 =(RollCallDataMechanic) _loc_3;
                    _loc_2 = _loc_4.canCollect(Global.player.uid);
                }
                return _loc_2;
            }//end
            ;
            return;
        }//end

    }



