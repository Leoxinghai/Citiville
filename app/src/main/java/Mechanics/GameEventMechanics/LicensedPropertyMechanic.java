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
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class LicensedPropertyMechanic implements IGameMechanic
    {
        protected IMechanicUser m_owner ;
        protected MechanicConfigData m_config ;

        public  LicensedPropertyMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public String  getProperty ()
        {
            return this.m_owner.getDataForMechanic(this.m_config.type);
        }//end

        public void  setProperty (String param1 )
        {
            param1 = this.validatePropertyName(param1);
            this.m_owner.setDataForMechanic(this.m_config.type, param1, MechanicManager.ALL);
            GameTransactionManager.addTransaction(new TMechanicAction(this.m_owner as MechanicMapResource, this.m_config.type, MechanicManager.ALL, {operation:"setProperty", propertyName:param1}));
            return;
        }//end

        protected String  validatePropertyName (String param1 )
        {
            Player _loc_4 =null ;
            Array _loc_5 =null ;
            if (param1 == null || param1.length == 0)
            {
                return "";
            }
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2 == null)
            {
                return "";
            }
            if (itemNeedsLicense(_loc_2))
            {
                _loc_4 = Global.player;
                if (_loc_4 == null || !_loc_4.checkLicense(param1))
                {
                    return "";
                }
            }
            _loc_3 = this.m_config.params.get( "restrictByKeywords") ;
            if (_loc_3 != null && _loc_3.length > 0)
            {
                _loc_5 = _loc_3.split(",");
                if (_loc_5.length > 0 && !itemHasAnyOfTheseKeywords(_loc_2, _loc_5))
                {
                    return "";
                }
            }
            return param1;
        }//end

        public static boolean  itemNeedsLicense (Item param1 )
        {
            return param1.unlockCostCash > 0 || param1.unlockCostCoin > 0;
        }//end

        public static boolean  itemHasAnyOfTheseKeywords (Item param1 ,Array param2 )
        {
            String _loc_3 =null ;
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_3 = param2.get(i0);

                if (param1.itemHasKeyword(_loc_3))
                {
                    return true;
                }
            }
            return false;
        }//end

    }



